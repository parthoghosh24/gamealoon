package com.gamealoon.database;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.gamealoon.models.Article;
import com.gamealoon.models.Category;
import com.gamealoon.models.Game;
import com.gamealoon.models.User;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

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
	
		List<User> topUsers= getTopUsers(gloonDatastore,0);
		List<HashMap<String, Object>> allArticlesForCarousel= new ArrayList<HashMap<String,Object>>();
		
		//Generating reviews
		HashMap<String, Object> reviewsMap = new HashMap<>();
		reviewsMap.put("category", "review");
		reviewsMap.put("articles", getReviews(topUsers, type) );
		allArticlesForCarousel.add(reviewsMap);
				
		//Generating feature
		topUsers= getTopUsers(gloonDatastore,5);
		HashMap<String, Object> featuresMap = new HashMap<>();
		featuresMap.put("category", "feature");
		featuresMap.put("articles", getArticles(topUsers, type, Category.Feature));
		allArticlesForCarousel.add(featuresMap);
							
		//Generating news
		topUsers= getTopUsers(gloonDatastore,5);
		HashMap<String, Object> newsMap = new HashMap<>();
		newsMap.put("category", "news");
		newsMap.put("articles", getArticles(topUsers, type, Category.News));		
		allArticlesForCarousel.add(newsMap);
		
		//Generating gloonicle
		topUsers= getTopUsers(gloonDatastore,5);
        HashMap<String, Object> glooniclesMap = new HashMap<>();
        glooniclesMap.put("category", "gloonicle");
        glooniclesMap.put("articles", getArticles(topUsers, type, Category.Gloonicle));
		allArticlesForCarousel.add(glooniclesMap);
		
		return allArticlesForCarousel;
	}
	
	
	/**
	 * Fetch 5 Reviews of recently released games based on user ratings
	 * 
	 * @param topUsers
	 * @param type
	 * @return
	 */
	private List<HashMap<String, Object>> getReviews(List<User> topUsers, String type)
	{
		List<Game> recentlyReleased5Games= getRecentReleasedGames(gloonDatastore,5);
		
		List<HashMap<String, Object>> fetchedReviews = new ArrayList<>();
		
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
						HashMap<String, Object> articleMap = new HashMap<String, Object>();
						articleMap.put("articleTitle", article.getTitle());
						articleMap.put("articleBody", article.getBody());
						articleMap.put("articleAuthor", article.getAuthor().getUsername());
						if(article.getGame()!=null)
						{
							articleMap.put("articleGame", article.getGame());
						}
						
						articleMap.put("articleCreationDate", article.getCreationDate());
						articleMap.put("articleFeaturedImage",article.getFeaturedImagePath());
						articleMap.put("articlePlatforms",  Utility.titleList(article.getPlatforms()));
						fetchedReviews.add(articleMap);
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
	 * Fetch 5 Features/News/Gloonicles of recently released games based on user ratings
	 * 
	 * @param topUsers
	 * @param type
	 * @return
	 */
	private List<HashMap<String, Object>> getArticles(List<User> topUsers, String type, Category category)
	{		
		
		List<HashMap<String, Object>> fetchedArticles = new ArrayList<>();				
			
		//TODO Handle all types
		for(User user: topUsers)
		{			
			Article article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("category", category).order("-creationDate").get();			
			if(article!=null)
			{
				HashMap<String, Object> articleMap = new HashMap<String, Object>();
				articleMap.put("articleTitle", article.getTitle());
				articleMap.put("articleBody", article.getBody());
				articleMap.put("articleAuthor", article.getAuthor().getUsername());
				if(article.getGame()!=null)
				{
					articleMap.put("articleGame", article.getGame());
				}
				
				articleMap.put("articleCreationDate", article.getCreationDate());
				articleMap.put("articleFeaturedImage",article.getFeaturedImagePath());
				articleMap.put("articlePlatforms",Utility.titleList(article.getPlatforms()));
				fetchedArticles.add(articleMap);			    
			}
		}		
		return fetchedArticles;
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
	 * Get N recently released games
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
	 * Get N recent released games
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
	
	/**
	 * Get 10 recent article of all types
	 * 
	 * @param gloonDatastore
	 * @param limit
	 * @return
	 */
	public List<Article> get10RecentArticles(Datastore gloonDatastore, int limit, String type)
	{
		List<Article> recent10Articles=new ArrayList<Article>();
		if("all".equals(type))
		{
			recent10Articles=gloonDatastore.createQuery(Article.class).order("-creationDate").limit(limit).asList();
		}
		if("reviews".equals(type))
		{
			recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Review).order("-creationDate").limit(limit).asList();
		}
		if("features".equals(type))
		{
			recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Feature).order("-creationDate").limit(limit).asList();
		}
		if("news".equals(type))
		{
			recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.News).order("-creationDate").limit(limit).asList();
		}
		if("gloonicles".equals(type))
		{
			recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Gloonicle).order("-creationDate").limit(limit).asList();	
		}				
		
		return recent10Articles;
	}

}
