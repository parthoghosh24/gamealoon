package com.gamealoon.controllers;

import java.util.HashMap;
import java.util.List;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.models.Article;
import com.gamealoon.models.Game;
import com.gamealoon.models.User;

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
 

			
	
  /**
   * This is the action method for fetching all Home content	
   * 
   * @return
   */
   public static Result index()
   {
	   List<HashMap<String, Object>> carouselArticles = getAllArticlesForCarousel();
	   List<Article> top10Articles = getAllRecent10Articles();
	   List<Game> top10Games= getTop10Games();
	   List<User> top5Users= getTop5Users();
	   
	   HashMap<String, Object> homeMap = new HashMap<>();
	   homeMap.put("carouselArticles", carouselArticles);
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
	  //TODO this method returns all latest articles by top users, i.e, reviews(unique), previews(unique), news, features, gloonicles
	   return null;
   }
   
   /**
    * This method returns all top/trending 5 games
    * 
    * @return
    */
   private static List<Game> getTop10Games()
   {
	   //TODO get all top 10 games
	   return null;
   }
   
   /**
    * This method returns all recent 10 articles in all categories
    * 
    * @return
    */
   private static List<Article> getAllRecent10Articles()
   {
	   //TODO get recent 10 articles for all categories
	   return null;
   }
   
   /**
    * This method returns top 5 users
    * 
    * @return
    */
   private static List<User> getTop5Users()
   {
	   //TODO get top 5 users.
	   return null;
   }
   
   

}
