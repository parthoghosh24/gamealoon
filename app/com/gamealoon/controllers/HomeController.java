package com.gamealoon.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.models.Article;
import com.gamealoon.models.Game;
import com.gamealoon.models.User;
import com.google.code.morphia.Datastore;
import com.gamealoon.views.html.index;
import play.mvc.Controller;
import play.mvc.Result;
import static play.libs.Json.toJson;

/**
 * This is the home page controller. This will be talking with the web-app's home page.
 * 
 * @author Partho
 *
 */
public class HomeController extends Controller{
	
	static final GloonDAO gloonDaoInstance = GloonDAO.instantiateDAO();
    static final Datastore gloonDatastore = gloonDaoInstance.initDatastore();

			
	
  /**
   * This is the action method for fetching all Home content	
   * 
   * @return
   */
    
    
   public static Result index()
   {
	   return ok(index.render("Home Page"));
   }
   public static Result getHomeData()
   {
	   List<HashMap<String, Object>> carouselArticleMaps = getAllArticlesForCarousel();
	   List<HashMap<String, Object>> top10Articles = getAllRecent10Articles();
	   List<HashMap<String, Object>> top10Games= getTop10Games();
	   List<HashMap<String, Object>> top5Users= getTop5Users();
	   
	   HashMap<String, Object> homeMap = new HashMap<>();
	   homeMap.put("carouselArticles", carouselArticleMaps);
	   homeMap.put("top10Articles", top10Articles);
	   homeMap.put("top10Games", top10Games);
	   homeMap.put("top5Users", top5Users);
	   return ok(toJson(homeMap));

	   
   }
   
   /**
	 * This method generates all carousel data.	
	 * 
	 * @return
	 */
   private static List<HashMap<String, Object>> getAllArticlesForCarousel()
   {
	 	   
	   List<HashMap<String, Object>> carouselArticles = gloonDaoInstance.getAllArticlesForCarousel(gloonDatastore, "all");	   
	   return carouselArticles;
   }
   
   /**
    * This method returns all top/trending 5 games
    * 
    * @return
    */
   private static List<HashMap<String, Object>> getTop10Games()
   {
	  List <HashMap<String, Object>> topGames = new ArrayList<>();
	  List <Game> recentGames = gloonDaoInstance.getRecentGames(gloonDatastore, 10);
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
   
   /**
    * This method returns all recent 10 articles in all categories
    * 
    * @return
    */
   private static List<HashMap<String, Object>> getAllRecent10Articles()
   {
	  List<HashMap<String, Object>> allRecentArticles = new ArrayList<>();
	  List<Article> all10Articles = gloonDaoInstance.get10RecentArticles(gloonDatastore, 10, "all");
	  List<Article> all10Reviews = gloonDaoInstance.get10RecentArticles(gloonDatastore, 10, "reviews");
	  List<Article> all10Features = gloonDaoInstance.get10RecentArticles(gloonDatastore, 10, "features");
	  List<Article> all10News = gloonDaoInstance.get10RecentArticles(gloonDatastore, 10, "news");
	  List<Article> all10Gloonicles = gloonDaoInstance.get10RecentArticles(gloonDatastore, 10, "gloonicles");
	  
	  HashMap<String, Object> allCategory = new HashMap<>();	  
	  
	  if(all10Articles.size()>0)
	  {
		  List<HashMap<String, Object>> allArticles = new ArrayList<HashMap<String,Object>>();
		  for(Article article: all10Articles)
		  {
			  HashMap<String, Object> allArticleMap = new HashMap<String, Object>();
			  allArticleMap.put("articleTitle", article.getTitle());
			  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
			  allArticleMap.put("articlePublishDate", article.getCreationDate());
			  allArticles.add(allArticleMap);
		  }
		  allCategory.put("recentAllArticles", allArticles);
	  }
	  
	  allRecentArticles.add(allCategory);
	  
	  
	  
	  HashMap<String, Object> reviewCategory = new HashMap<>();	  
	  
	  if(all10Reviews.size()>0)   
	  {
		  List<HashMap<String, Object>> allReviews = new ArrayList<HashMap<String,Object>>();
		  for(Article article: all10Reviews)
		  {
			  HashMap<String, Object> allArticleMap = new HashMap<String, Object>();
			  allArticleMap.put("articleTitle", article.getTitle());
			  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
			  allArticleMap.put("articlePublishDate", article.getCreationDate());
			  allReviews.add(allArticleMap);
			  
		  } 
		  reviewCategory.put("recentReviews", allReviews);
	  }
	  allRecentArticles.add(reviewCategory);
	  
	  
	  HashMap<String, Object> featureCategory = new HashMap<>();	  
	  
	  if(all10Features.size()>0)
	  {
		  List<HashMap<String, Object>> allFeatures = new ArrayList<HashMap<String,Object>>();
		  for(Article article: all10Features)
		  {
			  HashMap<String, Object> allArticleMap = new HashMap<String, Object>();
			  allArticleMap.put("articleTitle", article.getTitle());
			  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
			  allArticleMap.put("articlePublishDate", article.getCreationDate());
			  allFeatures.add(allArticleMap);
		  }
		  featureCategory.put("recentFeatures", allFeatures);
	  }
	  allRecentArticles.add(featureCategory);
	  
	  
	  HashMap<String, Object> newsCategory = new HashMap<>();	  
	  
	  if(all10News.size()>0)
	  {
		  List<HashMap<String, Object>> allNews = new ArrayList<HashMap<String,Object>>();
		  for(Article article: all10News)
		  {
			  HashMap<String, Object> allArticleMap = new HashMap<String, Object>();
			  allArticleMap.put("articleTitle", article.getTitle());
			  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
			  allArticleMap.put("articlePublishDate", article.getCreationDate());
			  allNews.add(allArticleMap);
		  } 
		  newsCategory.put("recentNews", allNews);
	  }
	  allRecentArticles.add(newsCategory);
	  
	  HashMap<String, Object> gloonicleCategory = new HashMap<>();	  
	 
	  if(all10Gloonicles.size()>0)
	  {
		  List<HashMap<String, Object>> allGloonicles = new ArrayList<HashMap<String,Object>>();
		  for(Article article: all10Gloonicles)
		  {
			  HashMap<String, Object> allArticleMap = new HashMap<String, Object>();
			  allArticleMap.put("articleTitle", article.getTitle());
			  allArticleMap.put("articleAuthor", article.getAuthor().getUsername());
			  allArticleMap.put("articlePublishDate", article.getCreationDate());
			  allGloonicles.add(allArticleMap);
		  }
		  gloonicleCategory.put("recentGloonicles", allGloonicles);
	  }
	  allRecentArticles.add(gloonicleCategory);
	  
	   return allRecentArticles;
   }
   
   /**
    * This method returns top 5 users
    * 
    * @return
    */
   private static List<HashMap<String, Object>> getTop5Users()
   {
	   List<HashMap<String, Object>> userMaps = new ArrayList<>();
	   List<User> topUsers = gloonDaoInstance.getTopUsers(gloonDatastore, 5);
	   if(topUsers.size()>0)
	   {
		   for(User user: topUsers)
		   {
			   HashMap<String, Object> userMap = new HashMap<>();
			   userMap.put("userUserName", user.getUsername());
			   userMap.put("userAvatar", user.getAvatarPath());
			   userMap.put("userAchievementCount", user.getAchievements().size());
			   userMaps.add(userMap);
					   
		   }  
	   }
	   
	   return userMaps;
   }
   
   

}
