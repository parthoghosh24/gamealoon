package com.gamealoon.database;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.gamealoon.models.Article;
import com.gamealoon.models.Category;
import com.gamealoon.models.Game;
import com.gamealoon.models.Platform;
import com.gamealoon.models.User;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

/**
 * This is the application data access layer.
 * All DB initiation methods, connection activation and queries will
 * be performed over here.
 * 
 * @author Partho
 *
 */
public class GloonDAO implements GloonDataInterface{
	
	private static final GloonDAO DATA_ACCESS_LAYER=new GloonDAO();	
	private GloonDatabase gloonDatabaseInstance=null;
	private Datastore gloonDatastore=null;
	private GloonDAO()
	{
	 
		gloonDatabaseInstance = GloonDatabase.instantiate();
	}
	
	/**
	 * Singleton way to instantiate Gloon DAO
	 * @return
	 */
	public static GloonDAO instantiateDAO()
	{								
		return DATA_ACCESS_LAYER;
	}
	
	

	
	/**
	 * 
	 * gloonDatastore instantiated
	 * @return gloonDatastore
	 */
	public Datastore initDatastore()
	{				
		gloonDatastore=gloonDatabaseInstance.gloonDatastoreInstance();
		return gloonDatastore;
	}

	@Override
	public List<Game> allGames(Datastore gloonDatastore) {
		List<Game> games = gloonDatastore.find(Game.class).asList();
		if(games.size()>0)
		{
			return games;
		}
		else
		{
			throw new RuntimeException("No games available"); 
		}
		
	}

	/**
	 * This method returns a List of hashTables or maps which is in the following format:
	 * 
	 * category: <Category> //Is one of Review, preview, news, feature and gloonicle	 
	 * articles:[article1, article2, article3, ...]
	 * 
	 * The list will contain 5 hashMaps in general.
	 */
	@Override
	public List<HashMap<String, Object>> getAllArticlesForCarousel(Datastore gloonDatastore,String type) {
	
		List<User> topUsers= getTopUsers(gloonDatastore,5);
		List<HashMap<String, Object>> allArticlesForCarousel= new ArrayList<HashMap<String,Object>>();
		
		//Generating reviews
		HashMap<String, Object> reviewsMap = new HashMap<>();
		reviewsMap.put("category", "review");
		reviewsMap.put("articles", getReviews(topUsers, type) );
		allArticlesForCarousel.add(reviewsMap);
		
		topUsers= getTopUsers(gloonDatastore,5);
		//Generating feature
		HashMap<String, Object> featuresMap = new HashMap<>();
		featuresMap.put("category", "feature");
		featuresMap.put("articles", getFeatures(topUsers, type) );
		allArticlesForCarousel.add(featuresMap);
		
		/*//Generating previews 
		HashMap<String, Object> previewsMap = new HashMap<>();
		previewsMap.put("category", "preview");
		previewsMap.put("articles", getPreviews(topUsers, type) );
		allArticlesForCarousel.add(previewsMap);
		
		
		
		//Generating news
		HashMap<String, Object> newsMap = new HashMap<>();
		newsMap.put("category", "news");
		newsMap.put("articles", getNews(topUsers, type) );		
		allArticlesForCarousel.add(newsMap);
		
		//Generating gloonicle
        HashMap<String, Object> glooniclesMap = new HashMap<>();
        glooniclesMap.put("category", "gloonicle");
        glooniclesMap.put("articles", getGloonicles(topUsers, type) );
		allArticlesForCarousel.add(glooniclesMap);*/
		
		return allArticlesForCarousel;
	}
	
	
	/**
	 * Fetch 5 Reviews of recently released games based on user ratings
	 * 
	 * @param topUsers
	 * @param type
	 * @return
	 */
	private List<Article> getReviews(List<User> topUsers, String type)
	{
		List<Game> recentlyReleased5Games= getRecentReleasedGames(gloonDatastore,5);
		
		List<Article> fetchedReviews = new ArrayList<>();
		
		for(Game game: recentlyReleased5Games)
		{
			if(topUsers.size()>0)
			{
				//TODO Handle all types
				for(User user: topUsers)
				{
					Article article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("category", Category.Review).get();
					if(article!=null)
					{
						fetchedReviews.add(article);
						topUsers.remove(user); //This adds variety to carousel section. This restricts addition of different article by same user in carousel.
						break;
					}
				}
				
			}			
			else
			{
				break;
			}
			
			
		}
		
		return fetchedReviews;
	}
	
	/**
	 * Fetch 5 Features of recently released games based on user ratings
	 * 
	 * @param topUsers
	 * @param type
	 * @return
	 */
	private List<Article> getFeatures(List<User> topUsers, String type)
	{		
		
		List<Article> fetchedFeatures = new ArrayList<>();				
			
		//TODO Handle all types
		for(User user: topUsers)
		{			
			Article article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("category", Category.Feature).order("creationDate").get();			
			if(article!=null)
			{
				fetchedFeatures.add(article);			    
			}
		}
									
			
			
		
		
		return fetchedFeatures;
	}
	
	/**
	 * Get sorted users based on their ranks
	 * 
	 * @param gloonDatastore
	 * @return
	 */
	public List<User> getTopUsers(Datastore gloonDatastore, int limit)
	{
	
	  if(limit>0)
	  {
		  return gloonDatastore.createQuery(User.class).order("-totalScore").limit(limit).asList();
	  }
	  else
	  {
		  return gloonDatastore.createQuery(User.class).order("-totalScore").asList();
	  }	  
	}
	
	/**
	 * Get recent top 5 released games
	 * 
	 * @param gloonDatastore
	 * @return
	 */
	public List<Game> getRecentReleasedGames(Datastore gloonDatastore, int limit)
	{
		if(limit>0)
		{
			return gloonDatastore.createQuery(Game.class).filter("releaseDate <", Utility.convertDateToStringWithoutTime(new Date())).limit(limit).order("-releaseDate").asList();
		}
		else
		{
			return gloonDatastore.createQuery(Game.class).filter("releaseDate <", Utility.convertDateToStringWithoutTime(new Date())).order("-releaseDate").asList();
		}
	  	
	}
	
	/**
	 * 
	 * 
	 * @param gloonDatastore
	 * @return
	 */	
	public List<Game> getRecentGames(Datastore gloonDatastore, int limit)	
	{
		if(limit >0)
		{
			return gloonDatastore.createQuery(Game.class).limit(limit).order("-releaseDate").asList();
		}
		else
		{
			return gloonDatastore.createQuery(Game.class).order("-releaseDate").asList();
		}
		
	}

}
