package com.gamealoon.database.daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;

import com.gamealoon.algorithm.RankAlgorithm;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.ArticleInterface;
import com.gamealoon.models.Article;
import com.gamealoon.models.Category;
import com.gamealoon.models.Game;
import com.gamealoon.models.Platform;
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

public class ArticleDAO extends GloonDAO implements ArticleInterface{
		
	private static final ArticleDAO DATA_ACCESS_LAYER=new ArticleDAO();
	private RankAlgorithm scoreAlgorithm = new RankAlgorithm();
	private static final GameDAO gameDaoInstance = GameDAO.instantiateDAO();
	private static final ConversationDAO conversationDaoInstance = ConversationDAO.instantiateDAO();
	private Datastore gloonDatastore=null;	
	private ArticleDAO()
	{
		super();
		gloonDatastore=initDatastore();		
	}
	
	/**
	 * Singleton way to instantiate Gloon DAO
	 * @return
	 */
	public static ArticleDAO instantiateDAO()
	{								
		return DATA_ACCESS_LAYER;
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
	public HashMap<String, Object> getAllArticlesForCarousel(String type) {
	
		List<User> topUsers= getTopUsers(gloonDatastore,0);
		HashMap<String, Object> allArticlesForCarouselMap= new HashMap<String,Object>();
		
		//Generating reviews		
		allArticlesForCarouselMap.put("carouselReviews", getReviews(topUsers, type) );		
				
		//Generating feature
		topUsers= getTopUsers(gloonDatastore,5);		
		allArticlesForCarouselMap.put("carouselFeatures", getArticles(topUsers, type, Category.Feature));		
							
		//Generating news
		topUsers= getTopUsers(gloonDatastore,5);			
		allArticlesForCarouselMap.put("carouselNews", getArticles(topUsers, type, Category.News));		
		
		
		//Generating gloonicle
		topUsers= getTopUsers(gloonDatastore,5);               
        allArticlesForCarouselMap.put("carouselGloonicles", getArticles(topUsers, type, Category.Gloonicle));		
		
		return allArticlesForCarouselMap;
	}
	
	
	@Override
	public HashMap<String, Object> getRecentAllNArticles(int limit, String platform) {		 
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
				  allArticleMap.put("articleSubTitle", article.getSubtitle());
				  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
				  allArticleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				  allArticleMap.put("articlePublishDate", article.getPublishDate());
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
				  allArticleMap.put("articleSubTitle", article.getSubtitle());
				  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
				  allArticleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				  allArticleMap.put("articlePublishDate", article.getPublishDate());
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
				  allArticleMap.put("articleSubTitle", article.getSubtitle());
				  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
				  allArticleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				  allArticleMap.put("articlePublishDate", article.getPublishDate());
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
				  allArticleMap.put("articleSubTitle", article.getSubtitle());
				  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
				  allArticleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				  allArticleMap.put("articlePublishDate", article.getPublishDate());
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
				  allArticleMap.put("articleSubTitle", article.getSubtitle());
				  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
				  allArticleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				  allArticleMap.put("articlePublishDate", article.getPublishDate());
				  allGloonicles.add(allArticleMap);
			  }
			  recentNArticles.put("recentGloonicles", allGloonicles);
		  }		  
		  
		   return recentNArticles;
	}
	
	@Override
	public HashMap<String, Object> getArticle(String userName, String titleOrId) {
		Article article = getArticleData(gloonDatastore,userName,titleOrId);
		HashMap<String , Object> response = new HashMap<>();
		if(article!=null)
		{
			response.put("articleId",article.getId().toString());
			response.put("articleTitle", article.getTitle());
			response.put("articleSubTitle", article.getSubtitle());
			response.put("articleBody", article.getBody());			
			response.put("articleCategory", Utility.capitalizeString(article.getCategory().toString()));
			response.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
			response.put("articlePublishDate", article.getPublishDate());
			HashMap<String, Object> userMap = new HashMap<>();
			User author = article.getAuthor();
			userMap.put("articleAuthorUsername", author.getUsername());
			userMap.put("articleAuthorGameBio", author.getGameBio());
			userMap.put("articleAuthorTotalAchievements", author.getAchievements().size());
			userMap.put("articleAuthorTotalFollowers", author.getFollowedBy().size());
			userMap.put("articleAuthorTotalFollowing", author.getFollowing().size());
			response.put("articleAuthor", userMap);			
			if(article.getGame()!=null)
			{
				response.put("articleGame",article.getGame().getTitle());
			}
			else
			{
				response.put("articleGame","");
			}
			ArrayList<HashMap<String, Object>> platforms = new ArrayList<>();
			for(Platform platform: article.getPlatforms())
			{
				HashMap<String, Object> platformMap = new HashMap<>();
				platformMap.put("platformTitle", platform.getTitle());
				platformMap.put("platformShortTitle", platform.getShortTitle());
				platforms.add(platformMap);
			}
			response.put("articlePlatforms", platforms);
			response.put("articleState", article.getState());
			ArrayList<HashMap<String, Object>> comments = conversationDaoInstance.getComments(article.getId().toString());
			response.put("articleComments",comments);			
		}
		return response;
	}
	
	
	
	@Override
	public List<HashMap<String, Object>> getAllArticlesByKey(String key, String sortField) {
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
					response.put("articleSubTitle", article.getSubtitle());
					response.put("articleBody", article.getBody());
					response.put("articleCategory", Utility.capitalizeString(article.getCategory().toString()));
					response.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
					response.put("articlePublishDate", article.getPublishDate());				
					response.put("articleAuthor", article.getAuthor().getUsername());
					if(article.getGame()!=null)
					{
						response.put("articleGame",article.getGame().getTitle());
					}
					else
					{
						response.put("articleGame","");
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
	public void updateArticleCoolUncoolScore(String urlTitle, String type) {
		
		
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
	public void updateArticlePageHitCount(String urlTitle) {		
		ObjectId _id =Utility.fetchIdFromTitle(urlTitle);
		Query<Article> article= gloonDatastore.createQuery(Article.class).field(Mapper.ID_KEY).equal(_id);		
		UpdateOperations<Article> update=gloonDatastore.createUpdateOperations(Article.class).inc("pageHitCount");
		gloonDatastore.update(article, update);				
		double articlePageHit = article.get().getPageHitCount();
		double totalPageHits = getTotalPageHits();
		updateArticleWilsonScore(article, gloonDatastore, articlePageHit, (totalPageHits-articlePageHit), "pagehit");			
	}
	
	@Override
	public void save(Article article) {
		gloonDatastore.save(article);
		
	}
	
	@Override
	public HashMap<String, Object> saveArticle(String id,String articleTitle,
			String articleSubTitle, String articleBody, String category,
			String username, String platforms, String featuredImagePath,
			String game, String state) {
		
		     HashMap<String, Object> response = new HashMap<>();
		     response.put("status", "fail");		     
		     String[] platformList = Utility.stringToList(platforms);		     
		     Article article = createOrUpdateArticle(id,
		    		 articleTitle,
		    		 articleSubTitle,
		    		 articleBody,
		    		 category,
		    		 username,
		    		 platformList,
		    		 featuredImagePath,
		    		 game, 
		    		 Integer.parseInt(state)
		    		 );
		     try
		     {
		    	 save(article);
		    	 response.put("status", "success");
		     }catch(Exception e)
		     {
		    	 e.printStackTrace();
		    	 response.put("status", "fail");
		     }
		     System.out.println("Status: "+response.get("status"));
		return response;
	}
		
	@Override
	public ArrayList<HashMap<String, Object>> getArticleListForUser(User user)
	{
		
		   List<Article> articles = new ArrayList<>();
		   articles = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).order("-insertTime").limit(10).asList();
		   ArrayList<HashMap<String,Object>> articleMapList = new ArrayList<>();
		   for(Article article: articles)
		   {
			   HashMap<String, Object> articleMap = new HashMap<>();
			   articleMap.put("articleId", article.getId().toString());
			   articleMap.put("articleTitle", article.getTitle());
			   articleMap.put("articleStrippedTitle", article.getTitle().substring(0, 15)+"...");
			   articleMap.put("articleSubTitle", article.getSubtitle());
			   articleMap.put("articleBody", article.getBody());
			   articleMap.put("articleCategory", article.getCategory().toString());
			   articleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
			   articleMap.put("articleState", article.getState());
			   articleMap.put("articleInsertTime", article.getInsertTime());
			   articleMap.put("articleUpdateTime", article.getUpdateTime());
			   articleMap.put("articlePublishDate", article.getPublishDate());
			   articleMap.put("articlePlatforms", Utility.titleList(article.getPlatforms()));
			   if(article.getGame()!=null)
				{
				   articleMap.put("articleGame",article.getGame().getTitle());
				}
				else
				{
					articleMap.put("articleGame","");
				}
			   articleMapList.add(articleMap);
			   
		   }
		   
		   return articleMapList;
	}
	
	/**
	 * Count All Published Articles. If no user passed, then total Published article count returned.
	 * 
	 */
	public Long allPublishedArticlesCount(User user)
	{		
		if(user == null)
		{			
			return gloonDatastore.createQuery(Article.class).filter("state", Article.PUBLISH).countAll();
		}
		else
		{
			return gloonDatastore.createQuery(Article.class).filter("state", Article.PUBLISH).filter("author.username",user.getUsername()).countAll();
		}
		
	}
		
	/**
	 * Create or update Article.
	 * 
	 * @param id
	 * @param articleTitle
	 * @param articleSubTitle
	 * @param articleBody
	 * @param category
	 * @param username
	 * @param platformList
	 * @param featuredImagePath
	 * @param game
	 * @param state
	 * @return
	 */
	private Article createOrUpdateArticle(String id, 
			String articleTitle, 
			String articleSubTitle, 
			String articleBody, 
			String category, 
			String username, 
			String[] platformList, 
			String featuredImagePath, 
			String game, 
			int state
			) 
	{
		Article article=null;
		String dateTime = Utility.convertDateToString(new Date());
		if(!id.isEmpty())
		{
			ObjectId _id = new ObjectId(id);
			article = gloonDatastore.createQuery(Article.class).filter("_id", _id).get();
			if(state ==Article.PUBLISH)
			{
				article.setUpdateTime(dateTime);
				article.setPublishDate(dateTime); 
			}
			else
			{
				article.setUpdateTime(dateTime);
			}
		}
		else
		{
			article = new Article();
			
			article.setInsertTime(dateTime);
	        article.setUpdateTime(dateTime);
	        article.setPublishDate("");
		}		
		article.setTitle(articleTitle);
		article.setSubtitle(articleSubTitle);
		article.setBody(articleBody);
		article.setCategory(Category.valueOf(category));        
        User author = gloonDatastore.createQuery(User.class).filter("username", username).get();
        article.setAuthor(author);
        ArrayList<Platform> platforms = new ArrayList<>();
        for(String platformName:platformList)
        {
        	System.out.println("Platform Name: "+platformName);
        	Platform platform = gloonDatastore.createQuery(Platform.class).filter("shortTitle", platformName.trim()).get();
        	System.out.println("Platform Object: "+platform);
        	if(platform!=null)
        	{
        		platforms.add(platform);
        	}
        	
        }
        article.setPlatforms(platforms);        
        article.setFeaturedImagePath(featuredImagePath);
        if(!game.isEmpty())
        {
        	Game fetchedGame =gloonDatastore.createQuery(Game.class).filter("title", game).get();
        	if(fetchedGame!=null)
        	{
        		article.setGame(fetchedGame);        		
        		
        	}
        	
        }
        article.setState(state);        
        
		return article;
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
	 * Get total page hits of All articles
	 * 
	 * @param articleInstance
	 * @return
	 */
	public double getTotalPageHits()
	{
		double totalPageHits=0.0;
	    Mongo instance = getDatabaseInstance().getMongoInstance();
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
	
	/**
	 * Get Single Article Based on Article Author and Article Encoded title or id
	 * 
	 * @param gloonDatastore
	 * @param userName
	 * @param title
	 * @return
	 */
	private Article getArticleData(Datastore gloonDatastore, String userName, String titleOrId)
	{
		if(!titleOrId.contains("-"))
		{
			return gloonDatastore.createQuery(Article.class).filter("author.username", userName).filter("_id", new ObjectId(titleOrId)).get();
		}		
		ObjectId _id = Utility.fetchIdFromTitle(titleOrId);
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
		List<Game> recentlyReleased5Games= gameDaoInstance.getRecentReleasedGames(5);
		
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
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("category", Category.Review).filter("state", Article.PUBLISH).get();
						break;
					case "ps3":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","Playstation 3").filter("category", Category.Review).filter("state", Article.PUBLISH).get();
						break;
					case "xbox360":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","Xbox 360").filter("category", Category.Review).filter("state", Article.PUBLISH).get();
						break;
					case "ps4":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","Playstation 4").filter("category", Category.Review).filter("state", Article.PUBLISH).get();
						break;
					case "xboxOne":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","Xbox One").filter("category", Category.Review).filter("state", Article.PUBLISH).get();
						break;	
					case "wiiu":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","WII-U").filter("category", Category.Review).filter("state", Article.PUBLISH).get();
						break;
					case "pc":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","PC").filter("category", Category.Review).filter("state", Article.PUBLISH).get();
						break;
					case "ios":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","IOS").filter("category", Category.Review).filter("state", Article.PUBLISH).get();
						break;
					case "android":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","Android").filter("category", Category.Review).filter("state", Article.PUBLISH).get();
						break;
					case "3ds":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","3DS").filter("category", Category.Review).filter("state", Article.PUBLISH).get();
						break;	
					case "vita":
						article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("game.title", game.getTitle()).filter("platforms.title","PS-VITA").filter("category", Category.Review).filter("state", Article.PUBLISH).get();
						break;	
						
					}
					if(article!=null)
					{
						HashMap<String, Object> articleMap = new HashMap<String, Object>();
						articleMap.put("articleCarouselCategory", Category.Review);
						articleMap.put("articleTitle", article.getTitle());
						articleMap.put("articleSubTitle", article.getSubtitle());
						articleMap.put("articleBody", article.getBody());
						articleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
						articleMap.put("articleAuthor", article.getAuthor().getUsername());
						if(article.getGame()!=null)
						{
							articleMap.put("articleGame", article.getGame().getTitle());
						}
						else
						{
							articleMap.put("articleGame","");
						}
						articleMap.put("articlepublishDate", article.getPublishDate());
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
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("category", category).filter("state", Article.PUBLISH).get();
				break;
			case "ps3":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","Playstation 3").filter("category", category).filter("state", Article.PUBLISH).get();
				break;
			case "xbox360":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","Xbox 360").filter("category", category).filter("state", Article.PUBLISH).get();
				break;
			case "ps4":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","Playstation 4").filter("category", category).filter("state", Article.PUBLISH).get();
				break;
			case "xboxOne":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","Xbox One").filter("category", category).filter("state", Article.PUBLISH).get();
				break;	
			case "wiiu":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","WII-U").filter("category", category).filter("state", Article.PUBLISH).get();
				break;
			case "pc":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","PC").filter("category", category).filter("state", Article.PUBLISH).get();
				break;
			case "ios":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","IOS").filter("category", category).filter("state", Article.PUBLISH).get();
				break;
			case "android":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","Android").filter("category", category).filter("state", Article.PUBLISH).get();
				break;
			case "3ds":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","3DS").filter("category", category).filter("state", Article.PUBLISH).get();
				break;	
			case "vita":
				article = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).filter("platforms.title","PS-VITA").filter("category", category).filter("state", Article.PUBLISH).get();
				break;	
				
			}
									
			if(article!=null)
			{
				HashMap<String, Object> articleMap = new HashMap<String, Object>();
				articleMap.put("articleCarouselCategory", category);
				articleMap.put("articleTitle", article.getTitle());
				articleMap.put("articleSubTitle", article.getSubtitle());
				articleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				articleMap.put("articleBody", article.getBody());
				articleMap.put("articleAuthor", article.getAuthor().getUsername());
				if(article.getGame()!=null)
				{
					articleMap.put("articleGame", article.getGame().getTitle());
				}
				else
				{
					articleMap.put("articleGame", "");
				}
				
				
				articleMap.put("articlepublishDate", article.getPublishDate());
				articleMap.put("articleFeaturedImage",article.getFeaturedImagePath());
				articleMap.put("articlePlatforms",Utility.titleList(article.getPlatforms()));
				fetchedArticles.add(articleMap);			    
			}
		}		
		return fetchedArticles;
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
				recent10Articles=gloonDatastore.createQuery(Article.class).order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "pc":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","PC").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "ps3":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","Playstation 3").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "xbox360":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","Xbox 360").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "ps4":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","Playstation 4").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "xboxOne":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","Xbox One").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;	
			case "wiiu":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","WII-U").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "ios":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","IOS").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "android":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","Android").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "vita":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","PS-VITA").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "3ds":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("platforms.title","3DS").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			}			
		}
		
		
		if("reviews".equals(type))
		{
			switch(platform)
			{
			case "all":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Review).order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "pc":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Review).filter("platforms.title","PC").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "ps3":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Review).filter("platforms.title","Playstation 3").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "xbox360":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Review).filter("platforms.title","Xbox 360").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "ps4":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Review).filter("platforms.title","Playstation 4").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "xboxOne":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Review).filter("platforms.title","Xbox One").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;	
			case "wiiu":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Review).filter("platforms.title","WII-U").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "ios":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Review).filter("platforms.title","IOS").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "android":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Review).filter("platforms.title","Android").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "vita":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Review).filter("platforms.title","PS-VITA").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "3ds":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Review).filter("platforms.title","3DS").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			}
			
		}
		
		
		if("features".equals(type))
		{
			
			switch(platform)
			{
			case "all":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Feature).order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "pc":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Feature).filter("platforms.title","PC").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "ps3":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Feature).filter("platforms.title","Playstation 3").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "xbox360":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Feature).filter("platforms.title","Xbox 360").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "ps4":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Feature).filter("platforms.title","Playstation 4").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "XboxOne":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Feature).filter("platforms.title","Xbox One").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;	
			case "wiiu":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Feature).filter("platforms.title","WII-U").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "ios":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Feature).filter("platforms.title","IOS").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "android":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Feature).filter("platforms.title","Android").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "vita":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Feature).filter("platforms.title","PS-VITA").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			case "3ds":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Feature).filter("platforms.title","3DS").order("-publishDate").limit(limit).filter("state", Article.PUBLISH).asList();
				break;
			}
			
		}
		
		
		
		if("news".equals(type))
		{
			switch(platform)
			{
			case "all":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.News).order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "pc":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.News).filter("platforms.title","PC").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "ps3":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.News).filter("platforms.title","Playstation 3").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "xbox360":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.News).filter("platforms.title","Xbox 360").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "ps4":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.News).filter("platforms.title","Playstation 4").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "xboxOne":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.News).filter("platforms.title","Xbox One").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;	
			case "wiiu":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.News).filter("platforms.title","WII-U").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "ios":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.News).filter("platforms.title","IOS").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "android":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.News).filter("platforms.title","Android").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "vita":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.News).filter("platforms.title","PS-VITA").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "3ds":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.News).filter("platforms.title","3DS").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			}
			
		}
		
		
		if("gloonicles".equals(type))
		{
			switch(platform)
			{
			case "all":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Gloonicle).order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "pc":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Gloonicle).filter("platforms.title","PC").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "ps3":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Gloonicle).filter("platforms.title","Playstation 3").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "xbox360":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Gloonicle).filter("platforms.title","Xbox 360").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "ps4":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Gloonicle).filter("platforms.title","Playstation 4").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "xboxOne":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Gloonicle).filter("platforms.title","Xbox One").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;	
			case "wiiu":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Gloonicle).filter("platforms.title","WII-U").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "ios":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Gloonicle).filter("platforms.title","IOS").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "android":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Gloonicle).filter("platforms.title","Android").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "vita":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Gloonicle).filter("platforms.title","PS-VITA").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			case "3ds":
				recent10Articles=gloonDatastore.createQuery(Article.class).filter("category", Category.Gloonicle).filter("platforms.title","3DS").order("-publishDate").filter("state", Article.PUBLISH).limit(limit).asList();
				break;
			}			
		}				
		
		return recent10Articles;
	}

	@Override
	public List<Article> findAllPublishedArticlesByGame(String gameId) {
		
		return gloonDatastore.createQuery(Article.class).filter("game._id", new ObjectId(gameId)).filter("state", Article.PUBLISH).order("-publishDate").asList();
	}
	
	

	

	
	

}
