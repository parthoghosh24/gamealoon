package com.gamealoon.controllers;

import java.util.HashMap;
import java.util.List;
//import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.daos.ArticleDAO;
import com.gamealoon.database.daos.GameDAO;
import com.gamealoon.database.daos.UserDAO;
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

//	private static final GloonDAO gloonDaoInstance = GloonDAO.instantiateDAO();
	private static final ArticleDAO articleDaoInstance = ArticleDAO.instantiateDAO();
	private static final UserDAO userDaoInstance = UserDAO.instantiateDAO();
	private static final GameDAO gameDaoInstance = GameDAO.instantiateDAO();	
	  
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

		return articleDaoInstance.getAllArticlesForCarousel(platform);
	}

	/**
	 * This method returns all top/trending 5 games
	 * 
	 * @return
	 */
	private static List<HashMap<String, Object>> getTop10PlatformGames(String platform) {
		return gameDaoInstance.getTopNGames(5, platform);
	}

	/**
	 * This method returns all recent 10 articles in all categories
	 * 
	 * @return
	 */
	private static HashMap<String, Object> getAllRecent10PlatformArticles(String platform) {
		return articleDaoInstance.getRecentAllNArticles(10,platform);
	}

	/**
	 * This method returns top 5 users
	 * 
	 * @return
	 */
	private static List<HashMap<String, Object>> getTop5Users() {
		return userDaoInstance.getTopNUsers(5);
	}
}
