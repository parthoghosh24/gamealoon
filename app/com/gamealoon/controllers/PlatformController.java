package com.gamealoon.controllers;

import java.util.HashMap;
import java.util.List;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.views.html.platforms.platformindex;
import com.google.code.morphia.Datastore;
import play.mvc.Controller;
import play.mvc.Result;
import static play.libs.Json.toJson;

/**
 * This is the platform controller. It exposes user to all platformed filtered
 * information, i.e, all ps3, xbox360, pc, etc. information
 * 
 * @author Partho
 * 
 */
public class PlatformController extends Controller {

	static final GloonDAO gloonDaoInstance = GloonDAO.instantiateDAO();
	static final Datastore gloonDatastore = gloonDaoInstance.initDatastore();

	public static Result index() {
		return ok(platformindex.render());
	}

	  public static Result checkPlatformState(String platform)
	   {
		   response().setHeader("Access-Control-Allow-Origin", "*");       // Need to add the correct domain in here!!
		    response().setHeader("Access-Control-Allow-Methods", "GET");   // Only allow POST
		    response().setHeader("Access-Control-Max-Age", "300");          // Cache response for 5 minutes
		    response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");         // Ensure this header is also allowed!  
		    return ok();
	   }
	  
	public static Result getPlatformData(String platform) {

		HashMap<String, Object> carouselArticleMaps = getAllArticlesForPlatformCarousel(platform);
		HashMap<String, Object> top10Articles = getAllRecent10PlatformArticles(platform);
		List<HashMap<String, Object>> top10Games = getTop10PlatformGames(platform);
		List<HashMap<String, Object>> top5Users = getTop5Users();

		HashMap<String, Object> platformDataMap = new HashMap<>();
		platformDataMap.put("carouselArticles", carouselArticleMaps);
		platformDataMap.put("top10Articles", top10Articles);
		platformDataMap.put("top10Games", top10Games);
		platformDataMap.put("top5Users", top5Users);
		platformDataMap.put("platform", platform);
		response().setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		//TODO add href element to json
		return ok(toJson(platformDataMap));
	}

	/**
	 * This method generates all carousel data.
	 * 
	 * @return
	 */
	private static HashMap<String, Object> getAllArticlesForPlatformCarousel(
			String platform) {

		return gloonDaoInstance.getAllArticlesForCarousel(gloonDatastore, platform);
	}

	/**
	 * This method returns all top/trending 5 games
	 * 
	 * @return
	 */
	private static List<HashMap<String, Object>> getTop10PlatformGames(String platform) {
		return gloonDaoInstance.getTopNGames(gloonDatastore, 5, platform);
	}

	/**
	 * This method returns all recent 10 articles in all categories
	 * 
	 * @return
	 */
	private static HashMap<String, Object> getAllRecent10PlatformArticles(String platform) {
		return gloonDaoInstance.getRecentAllNArticles(gloonDatastore, 10,platform);
	}

	/**
	 * This method returns top 5 users
	 * 
	 * @return
	 */
	private static List<HashMap<String, Object>> getTop5Users() {
		return gloonDaoInstance.getTopNUsers(gloonDatastore, 5);
	}
}
