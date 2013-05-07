package com.gamealoon.database;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;

import com.gamealoon.algorithm.Algorithm;
import com.gamealoon.models.Article;
import com.gamealoon.models.Category;
import com.gamealoon.models.Game;
import com.gamealoon.models.User;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
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
	private Algorithm scoreAlgorithm = new Algorithm();
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

	/**
	 * This method returns a List of hashTables or maps which is in the following format:
	 * 
	 * category: <Category> //Is one of Review, preview, news, feature and gloonicle	 
	 * articles:[article1, article2, article3, ...]
	 * 
	 * The list will contain 5 hashMaps in general.
	 */
	@Override
	public HashMap<String, Object> getAllArticlesForCarousel(Datastore gloonDatastore,String type) {
	
		List<User> topUsers= getTopUsers(gloonDatastore,0);
		HashMap<String, Object> allArticlesForCarouselMap= new HashMap<String,Object>();
		
		//Generating reviews		
		allArticlesForCarouselMap.put("carouselReviews", getReviews(topUsers, type) );		
				
		//Generating feature
		topUsers= getTopUsers(gloonDatastore,5);		
		allArticlesForCarouselMap.put("carouselFeatures", getArticles(topUsers, type, Category.feature));		
							
		//Generating news
		topUsers= getTopUsers(gloonDatastore,5);			
		allArticlesForCarouselMap.put("carouselNews", getArticles(topUsers, type, Category.news));		
		
		
		//Generating gloonicle
		topUsers= getTopUsers(gloonDatastore,5);               
        allArticlesForCarouselMap.put("carouselGloonicles", getArticles(topUsers, type, Category.gloonicle));		
		
		return allArticlesForCarouselMap;
	}
	
	@Override
	public List<HashMap<String, Object>> getTopNGames(Datastore gloonDatastore,int limit, String platform) {
		 List <HashMap<String, Object>> topGames = new ArrayList<>();
		  List <Game> recentGames = getRecentGames(gloonDatastore, limit);
		  if(recentGames.size()>0)
		  {
			  for(Game game: recentGames)
			  {
				HashMap<String, Object> gameMap = new HashMap<String, Object>();
				gameMap.put("gameTitle", game.getTitle());
				gameMap.put("gameReleaseDate", game.getReleaseDate());
				gameMap.put("gameScore", game.getScore());
				topGames.add(gameMap);
			  }  
		  }
		  
	 	   return topGames;
	}
	
	@Override
	public HashMap<String, Object> getRecentAllNArticles(Datastore gloonDatastore, int limit, String platform) {		 
		  List<Article> all10Articles = getNRecentArticles(gloonDatastore, limit, "all",platform);
		  List<Article> all10Reviews = getNRecentArticles(gloonDatastore, limit, "reviews",platform);
		  List<Article> all10Features =getNRecentArticles(gloonDatastore, limit, "features",platform);
		  List<Article> all10News = getNRecentArticles(gloonDatastore, limit, "news",platform);
		  List<Article> all10Gloonicles = getNRecentArticles(gloonDatastore, limit, "gloonicles",platform);
		  
		  HashMap<String, Object> recentNArticles = new HashMap<>();	  
		  
		  if(all10Articles.size()>0)
		  {
			  List<HashMap<String, Object>> allArticles = new ArrayList<HashMap<String,Object>>();
			  for(Article article: all10Articles)
			  {
				  HashMap<String, Object> allArticleMap = new HashMap<String, Object>();
				  allArticleMap.put("articleTitle", article.getTitle());
				  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
				  allArticleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				  allArticleMap.put("articlePublishDate", article.getCreationDate());
				  allArticles.add(allArticleMap);
			  }
			  recentNArticles.put("recentAllArticles", allArticles);
		  }
		  		  
		  
		  if(all10Reviews.size()>0)   
		  {
			  List<HashMap<String, Object>> allReviews = new ArrayList<HashMap<String,Object>>();
			  for(Article article: all10Reviews)
			  {
				  HashMap<String, Object> allArticleMap = new HashMap<String, Object>();
				  allArticleMap.put("articleTitle", article.getTitle());
				  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
				  allArticleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				  allArticleMap.put("articlePublishDate", article.getCreationDate());
				  allReviews.add(allArticleMap);
				  
			  } 
			  recentNArticles.put("recentReviews", allReviews);
		  }		  	 
		  
		  if(all10Features.size()>0)
		  {
			  List<HashMap<String, Object>> allFeatures = new ArrayList<HashMap<String,Object>>();
			  for(Article article: all10Features)
			  {
				  HashMap<String, Object> allArticleMap = new HashMap<String, Object>();
				  allArticleMap.put("articleTitle", article.getTitle());
				  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
				  allArticleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				  allArticleMap.put("articlePublishDate", article.getCreationDate());
				  allFeatures.add(allArticleMap);
			  }
			  recentNArticles.put("recentFeatures", allFeatures);
		  }		  	  
		  
		  if(all10News.size()>0)
		  {
			  List<HashMap<String, Object>> allNews = new ArrayList<HashMap<String,Object>>();
			  for(Article article: all10News)
			  {
				  HashMap<String, Object> allArticleMap = new HashMap<String, Object>();
				  allArticleMap.put("articleTitle", article.getTitle());
				  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
				  allArticleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				  allArticleMap.put("articlePublishDate", article.getCreationDate());
				  allNews.add(allArticleMap);
			  } 
			  recentNArticles.put("recentNews", allNews);
		  }		  	 
		 
		  if(all10Gloonicles.size()>0)
		  {
			  List<HashMap<String, Object>> allGloonicles = new ArrayList<HashMap<String,Object>>();
			  for(Article article: all10Gloonicles)
			  {
				  HashMap<String, Object> allArticleMap = new HashMap<String, Object>();
				  allArticleMap.put("articleTitle", article.getTitle());
				  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
				  allArticleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				  allArticleMap.put("articlePublishDate", article.getCreationDate());
				  allGloonicles.add(allArticleMap);
			  }
			  recentNArticles.put("recentGloonicles", allGloonicles);
		  }		  
		  
		   return recentNArticles;
	}
	
	
	@Override
	public List<HashMap<String, Object>> getTopNUsers(Datastore gloonDatastore,int limit) {
		 List<HashMap<String, Object>> userMaps = new ArrayList<>();
		   List<User> topUsers = getTopUsers(gloonDatastore, limit);
		   if(topUsers.size()>0)
		   {
			   for(User user: topUsers)
			   {
				   HashMap<String, Object> userMap = new HashMap<>();
				   userMap.put("userUserName", user.getUsername());
				   userMap.put("userAvatar", user.getAvatarPath());
				   userMap.put("userAchievementCount", user.getAchievements().size());
				   userMap.put("totalFollowers", user.getFollowedBy().size());
				   userMaps.add(userMap);
						   
			   }  
		   }
		   
		   return userMaps;
	}
	
	@Override
	public HashMap<String, Object> getArticle(Datastore gloonDatastore,String userName, String title) {
		Article article = getArticleData(gloonDatastore,userName,title);
		HashMap<String , Object> response = new HashMap<>();
		if(article!=null)
		{
			response.put("articleTitle", article.getTitle());
			response.put("articleBody", article.getBody());
			response.put("articleCategory", article.getCategory());
			response.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
			response.put("articlePublishDate", article.getCreationDate());
			response.put("articleAuthor", article.getAuthor().getUsername());
			if(article.getGame()!=null)
			{
				response.put("articleGame",article.getGame().getTitle());
			}
			response.put("articlePlatforms", Utility.titleList(article.getPlatforms()));
			response.put("articleScore", article.getTotalScore());
		}
		return response;
	}
	
	
	
	@Override
	public List<HashMap<String, Object>> getAllArticlesByKey(Datastore gloonDatastore, String key, String sortField) {
		List<Article> articles = getArticlesByKey(gloonDatastore, key, sortField);
		List<HashMap<String, Object>> articleLists = new ArrayList<>();
		if(articles.size()>0)
		{
			
			for(Article article: articles)
			{				
				if(article!=null)
				{
					HashMap<String , Object> response = new HashMap<>();
					response.put("articleTitle", article.getTitle());
					response.put("articleBody", article.getBody());
					response.put("articleCategory", article.getCategory());
					response.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
					response.put("articlePublishDate", article.getCreationDate());
					response.put("articleAuthor", article.getAuthor().getUsername());
					if(article.getGame()!=null)
					{
						response.put("articleGame",article.getGame().getTitle());
					}
					response.put("articlePlatforms", Utility.titleList(article.getPlatforms()));
					response.put("articleScore", article.getTotalScore());
					articleLists.add(response);
				}
				
			}
		}
		return articleLists;
	}
	
	@Override
	public HashMap<String, Object> getUser(Datastore gloonDatastore,String username) {
		   User user = getUserData(gloonDatastore, username);
		   HashMap<String, Object> userMap = new HashMap<>();
		   if(user!=null)
		   {
			   userMap.put("userUserName", user.getUsername());
			   userMap.put("userAvatar", user.getAvatarPath());
			   userMap.put("userAchievementCount", user.getAchievements().size());
			   userMap.put("totalFollowers", user.getFollowedBy().size());
		   }
		   
		   
		return userMap;
	}
	
	@Override
	public void updateArticleCoolUncoolScore(Datastore gloonDatastore, String urlTitle, String type) {
		
		
		UpdateOperations<Article> update=null;		
		ObjectId _id =Utility.fetchIdFromTitle(urlTitle);
		Query<Article> article= gloonDatastore.createQuery(Article.class).field(Mapper.ID_KEY).equal(_id);
		Article articleInstance =article.get();		
		if(AppConstants.COOL.equalsIgnoreCase(type))
		{								
			update=gloonDatastore.createUpdateOperations(Article.class).inc("coolScore");								
		}
		else
		{			
			update=gloonDatastore.createUpdateOperations(Article.class).inc("notCoolScore");								
		}
		gloonDatastore.update(article, update);
		updateArticleWilsonScore(article, gloonDatastore, articleInstance.getCoolScore(), articleInstance.getNotCoolScore(), "coolnotcool");
		
	}
	
	
	@Override
	public void updateArticlePageHitCount(Datastore gloonDatastore, String urlTitle) {		
		ObjectId _id =Utility.fetchIdFromTitle(urlTitle);
		Query<Article> article= gloonDatastore.createQuery(Article.class).field(Mapper.ID_KEY).equal(_id);		
		UpdateOperations<Article> update=gloonDatastore.createUpdateOperations(Article.class).inc("pageHitCount");
		gloonDatastore.update(article, update);				
		double articlePageHit = article.get().getPageHitCount();
		double totalPageHits = getTotalPageHits();
		updateArticleWilsonScore(article, gloonDatastore, articlePageHit, (totalPageHits-articlePageHit), "pagehit");			
	}
	
	
	/**
	 * Get total page hits of All articles
	 * 
	 * @param articleInstance
	 * @return
	 */
	public double getTotalPageHits()
	{
		double totalPageHits=0.0;
	    Mongo instance = gloonDatabaseInstance.getMongoInstance();
	    DB db = instance.getDB(AppConstants.DB_NAME);
	    DBCollection articleCollection = db.getCollection("Article");	
	    String mapFunction="function(){emit(\"total\",this.pageHitCount);};";
	    String reduceFunction="function(key, values){return Array.sum(values);};";	    
	    MapReduceOutput output = articleCollection.mapReduce(mapFunction, reduceFunction, null, MapReduceCommand.OutputType.INLINE,null);
	    
	    for(DBObject object: output.results())
	    {	    	
	    	totalPageHits=(double)object.get("value");
	    }
		return totalPageHits;
	}
	
	/**
	 * This method upates Article's wilson score based on cools and not cools
	 * 
	 * @param article
	 * @param gloonDatastore
	 * @param cools
	 * @param uncools
	 * @return
	 */
	private boolean updateArticleWilsonScore(Query<Article> article, Datastore gloonDatastore, double cools, double uncools, String type)
	{	
		UpdateOperations<Article> update=null;
		if("coolnotcool".equalsIgnoreCase(type))
		{
			update = gloonDatastore.createUpdateOperations(Article.class).set("wilsonScore", scoreAlgorithm.wilsonScoreCalculator(cools, uncools));
		}
		else
		{
			update = gloonDatastore.createUpdateOperations(Article.class).set("pageHitScore", scoreAlgorithm.wilsonScoreCalculator(cools, uncools));
		}
	    
	    gloonDatastore.update(article, update);
		return true;
	}
		
	/**
	 * Get user based on username
	 * 
	 * @param gloonDatastore
	 * @param username
	 * @return
	 */
	private User getUserData(Datastore gloonDatastore, String username)
	{
		return gloonDatastore.createQuery(User.class).filter("username", username).get();
	}
	
	/**
	 * Get all articles by key
	 * 
	 * @param gloonDatastore
	 * @param key
	 * @return
	 */
	private List<Article> getArticlesByKey(Datastore gloonDatastore, String key, String sortField)
	{		
		if("all".equalsIgnoreCase(key))
		{			
			return gloonDatastore.createQuery(Article.class).order("-"+sortField).asList();
		}
		if(AppConstants.REVIEW.equalsIgnoreCase(key)|| AppConstants.FEATURE.equalsIgnoreCase(key)|| AppConstants.NEWS.equalsIgnoreCase(key)||AppConstants.GLOONICLE.equalsIgnoreCase(key))
		{
			return gloonDatastore.createQuery(Article.class).filter("category", key).order("-"+sortField).asList();
		}
		else
		{
			return gloonDatastore.createQuery(Article.class).filter("author.username", key).order("-"+sortField).asList();
		}
		
		
	}	
	
	private Article getArticleData(Datastore gloonDatastore, String userName, String title)
	{
		ObjectId _id = Utility.fetchIdFromTitle(title);
		return gloonDatastore.createQuery(Article.class).filter("author.username", userName).filter("_id", _id).get();		
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
					Article article=null;
					switch(type)
					{
					case "all":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("category", Category.review).get();
						break;
					case "ps3":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","Playstation 3").filter("category", Category.review).get();
						break;
					case "xbox360":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","Xbox 360").filter("category", Category.review).get();
						break;
					case "ps4":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","Playstation 4").filter("category", Category.review).get();
						break;
					case "wiiu":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","WII-U").filter("category", Category.review).get();
						break;
					case "pc":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","PC").filter("category", Category.review).get();
						break;
					case "ios":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","IOS").filter("category", Category.review).get();
						break;
					case "android":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","Android").filter("category", Category.review).get();
						break;
					case "3ds":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","3DS").filter("category", Category.review).get();
						break;	
					case "vita":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","PS-VITA").filter("category", Category.review).get();
						break;	
						
					}
					if(article!=null)
					{
						HashMap<String, Object> articleMap = new HashMap<String, Object>();
						articleMap.put("articleCarouselCategory", Category.review);
						articleMap.put("articleTitle", article.getTitle());
						articleMap.put("articleBody", article.getBody());
						articleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
						articleMap.put("articleAuthor", article.getAuthor().getUsername());
						if(article.getGame()!=null)
						{
							articleMap.put("articleGame", article.getGame().getTitle());
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
			Article article=null;
			switch(type)
			{
			case "all":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("category", category).get();
				break;
			case "ps3":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","Playstation 3").filter("category", category).get();
				break;
			case "xbox360":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","Xbox 360").filter("category", category).get();
				break;
			case "ps4":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","Playstation 4").filter("category", category).get();
				break;
			case "wiiu":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","WII-U").filter("category", category).get();
				break;
			case "pc":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","PC").filter("category", category).get();
				break;
			case "ios":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","IOS").filter("category", category).get();
				break;
			case "android":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","Android").filter("category", category).get();
				break;
			case "3ds":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","3DS").filter("category", category).get();
				break;	
			case "vita":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","PS-VITA").filter("category", category).get();
				break;	
				
			}
									
			if(article!=null)
			{
				HashMap<String, Object> articleMap = new HashMap<String, Object>();
				articleMap.put("articleCarouselCategory", category);
				articleMap.put("articleTitle", article.getTitle());
				articleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				articleMap.put("articleBody", article.getBody());
				articleMap.put("articleAuthor", article.getAuthor().getUsername());
				if(article.getGame()!=null)
				{
					articleMap.put("articleGame", article.getGame().getTitle());
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
	private List<User> getTopUsers(Datastore gloonDatastore, int limit)
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
	 * Get N recent games
	 * 
	 * @param gloonDatastore
	 * @return
	 */	
	private List<Game> getRecentGames(Datastore gloonDatastore, int limit)	
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
	private List<Article> getNRecentArticles(Datastore gloonDatastore, int limit, String type, String platform)
	{
		List<Article> recent10Articles=new ArrayList<Article>();
		if("all".equals(type))
		{
			switch(platform)
			{
			case "all":
				recent10Articles=gloonDatastore.createQuery(Article.class).order("-creationDate").limit(limit).asList();
				break;
			case "pc":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","PC").order("-creationDate").limit(limit).asList();
				break;
			case "ps3":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","Playstation 3").order("-creationDate").limit(limit).asList();
				break;
			case "xbox360":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","Xbox 360").order("-creationDate").limit(limit).asList();
				break;
			case "ps4":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","Playstation 4").order("-creationDate").limit(limit).asList();
				break;
			case "wiiu":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","WII-U").order("-creationDate").limit(limit).asList();
				break;
			case "ios":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","IOS").order("-creationDate").limit(limit).asList();
				break;
			case "android":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","Android").order("-creationDate").limit(limit).asList();
				break;
			case "vita":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","PS-VITA").order("-creationDate").limit(limit).asList();
				break;
			case "3ds":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","3DS").order("-creationDate").limit(limit).asList();
				break;
			}			
		}
		
		
		if("reviews".equals(type))
		{
			switch(platform)
			{
			case "all":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.review).order("-creationDate").limit(limit).asList();
				break;
			case "pc":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.review).filter("platforms.title","PC").order("-creationDate").limit(limit).asList();
				break;
			case "ps3":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.review).filter("platforms.title","Playstation 3").order("-creationDate").limit(limit).asList();
				break;
			case "xbox360":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.review).filter("platforms.title","Xbox 360").order("-creationDate").limit(limit).asList();
				break;
			case "ps4":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.review).filter("platforms.title","Playstation 4").order("-creationDate").limit(limit).asList();
				break;
			case "wiiu":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.review).filter("platforms.title","WII-U").order("-creationDate").limit(limit).asList();
				break;
			case "ios":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.review).filter("platforms.title","IOS").order("-creationDate").limit(limit).asList();
				break;
			case "android":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.review).filter("platforms.title","Android").order("-creationDate").limit(limit).asList();
				break;
			case "vita":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.review).filter("platforms.title","PS-VITA").order("-creationDate").limit(limit).asList();
				break;
			case "3ds":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.review).filter("platforms.title","3DS").order("-creationDate").limit(limit).asList();
				break;
			}
			
		}
		
		
		if("features".equals(type))
		{
			
			switch(platform)
			{
			case "all":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.feature).order("-creationDate").limit(limit).asList();
				break;
			case "pc":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.feature).filter("platforms.title","PC").order("-creationDate").limit(limit).asList();
				break;
			case "ps3":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.feature).filter("platforms.title","Playstation 3").order("-creationDate").limit(limit).asList();
				break;
			case "xbox360":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.feature).filter("platforms.title","Xbox 360").order("-creationDate").limit(limit).asList();
				break;
			case "ps4":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.feature).filter("platforms.title","Playstation 4").order("-creationDate").limit(limit).asList();
				break;
			case "wiiu":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.feature).filter("platforms.title","WII-U").order("-creationDate").limit(limit).asList();
				break;
			case "ios":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.feature).filter("platforms.title","IOS").order("-creationDate").limit(limit).asList();
				break;
			case "android":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.feature).filter("platforms.title","Android").order("-creationDate").limit(limit).asList();
				break;
			case "vita":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.feature).filter("platforms.title","PS-VITA").order("-creationDate").limit(limit).asList();
				break;
			case "3ds":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.feature).filter("platforms.title","3DS").order("-creationDate").limit(limit).asList();
				break;
			}
			
		}
		
		
		
		if("news".equals(type))
		{
			switch(platform)
			{
			case "all":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.news).order("-creationDate").limit(limit).asList();
				break;
			case "pc":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.news).filter("platforms.title","PC").order("-creationDate").limit(limit).asList();
				break;
			case "ps3":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.news).filter("platforms.title","Playstation 3").order("-creationDate").limit(limit).asList();
				break;
			case "xbox360":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.news).filter("platforms.title","Xbox 360").order("-creationDate").limit(limit).asList();
				break;
			case "ps4":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.news).filter("platforms.title","Playstation 4").order("-creationDate").limit(limit).asList();
				break;
			case "wiiu":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.news).filter("platforms.title","WII-U").order("-creationDate").limit(limit).asList();
				break;
			case "ios":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.news).filter("platforms.title","IOS").order("-creationDate").limit(limit).asList();
				break;
			case "android":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.news).filter("platforms.title","Android").order("-creationDate").limit(limit).asList();
				break;
			case "vita":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.news).filter("platforms.title","PS-VITA").order("-creationDate").limit(limit).asList();
				break;
			case "3ds":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.news).filter("platforms.title","3DS").order("-creationDate").limit(limit).asList();
				break;
			}
			
		}
		
		
		if("gloonicles".equals(type))
		{
			switch(platform)
			{
			case "all":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.gloonicle).order("-creationDate").limit(limit).asList();
				break;
			case "pc":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.gloonicle).filter("platforms.title","PC").order("-creationDate").limit(limit).asList();
				break;
			case "ps3":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.gloonicle).filter("platforms.title","Playstation 3").order("-creationDate").limit(limit).asList();
				break;
			case "xbox360":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.gloonicle).filter("platforms.title","Xbox 360").order("-creationDate").limit(limit).asList();
				break;
			case "ps4":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.gloonicle).filter("platforms.title","Playstation 4").order("-creationDate").limit(limit).asList();
				break;
			case "wiiu":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.gloonicle).filter("platforms.title","WII-U").order("-creationDate").limit(limit).asList();
				break;
			case "ios":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.gloonicle).filter("platforms.title","IOS").order("-creationDate").limit(limit).asList();
				break;
			case "android":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.gloonicle).filter("platforms.title","Android").order("-creationDate").limit(limit).asList();
				break;
			case "vita":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.gloonicle).filter("platforms.title","PS-VITA").order("-creationDate").limit(limit).asList();
				break;
			case "3ds":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.gloonicle).filter("platforms.title","3DS").order("-creationDate").limit(limit).asList();
				break;
			}			
		}				
		
		return recent10Articles;
	}

	

	

	

	



	

	

	

	

	

}
