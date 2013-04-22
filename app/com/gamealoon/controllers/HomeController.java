package com.gamealoon.controllers;

import java.util.HashMap;
import java.util.List;
import com.gamealoon.database.GloonDAO;
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

			
	

    
    
   public static Result index()
   {
	   return ok(index.render("Home Page"));
   }
   
   /**
    * This is the action method for fetching all Home content	
    * 
    * @return
    */
   public static Result getHomeData()
   {
	   HashMap<String, Object> carouselArticleMaps = getAllArticlesForCarousel();
	   HashMap<String, Object> top10Articles = getAllRecent10Articles();
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
   private static HashMap<String, Object> getAllArticlesForCarousel()
   {
	 	   
	   return gloonDaoInstance.getAllArticlesForCarousel(gloonDatastore, "all");	   	   
   }
   
   /**
    * This method returns all top/trending 5 games
    * 
    * @return
    */
   private static List<HashMap<String, Object>> getTop10Games()
   {
	 return gloonDaoInstance.getTopNGames(gloonDatastore, 5, "all");
   }
   
   /**
    * This method returns all recent 10 articles in all categories
    * 
    * @return
    */
   private static HashMap<String, Object> getAllRecent10Articles()
   {
	 return gloonDaoInstance.getRecentAllNArticles(gloonDatastore, 10, "all");
   }
   
   /**
    * This method returns top 5 users
    * 
    * @return
    */
   private static List<HashMap<String, Object>> getTop5Users()
   {
	  return gloonDaoInstance.getTopNUsers(gloonDatastore, 5);
   }
   
   

}
