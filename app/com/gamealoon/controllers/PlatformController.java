package com.gamealoon.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
//import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.daos.ArticleDAO;
import com.gamealoon.database.daos.GameDAO;
import com.gamealoon.database.daos.UserDAO;
import com.gamealoon.models.Article;

import play.mvc.Controller;
import play.mvc.Result;
import static play.libs.Json.toJson;

/**
 * This is the platform controller. It exposes user to all platform filtered
 * information, i.e, all ps3, xbox360, pc, etc. information
 * 
 * @author Partho
 * 
 */
public class PlatformController extends Controller {

	private static final ArticleDAO articleDaoInstance = ArticleDAO.instantiateDAO();
	private static final UserDAO userDaoInstance = UserDAO.instantiateDAO();
	private static final GameDAO gameDaoInstance = GameDAO.instantiateDAO();	
	  
	public static Result getPlatformData(String platform, String category) {

		HashMap<String, Object> carouselArticleMaps = getAllArticlesForPlatformCarousel(platform);		
		List<HashMap<String, Object>> top5Games = getTop5PlatformGames(platform);
		List<HashMap<String, Object>> recent5Games = getRecent5PlatformGames(platform);
		List<HashMap<String, Object>> top5Users = getTop5Users();

		HashMap<String, Object> platformDataMap = new HashMap<>();
		platformDataMap.put("carouselArticles", carouselArticleMaps);
		platformDataMap.put("recentNArticles", getRecentNPlatformArticles(platform,category));
		platformDataMap.put("top5Games", top5Games);
		platformDataMap.put("recent5Games", recent5Games);
		platformDataMap.put("top5Users", top5Users);
		platformDataMap.put("platform", platform);
		return ok(toJson(platformDataMap));
	}

	/**
	 * This method generates all carousel data.
	 * 
	 * @return
	 */
	private static HashMap<String, Object> getAllArticlesForPlatformCarousel(String platform) {

		return articleDaoInstance.getAllArticlesForCarousel(platform);
	}

	/**
	 * This method returns all top/trending 5 games
	 * 
	 * @return
	 */
	private static List<HashMap<String, Object>> getTop5PlatformGames(String platform) {
		return gameDaoInstance.getTopNGames(5, platform);
	}
	
	/**
	 * This method returns all top/trending 5 games
	 * 
	 * @return
	 */
	private static List<HashMap<String, Object>> getRecent5PlatformGames(String platform) {
		return gameDaoInstance.getRecentNGames(5, platform);
	}

	/**
	 * This method returns all recent 10 articles in all categories
	 * 
	 * @return
	 */
	private static List<HashMap<String, Object>> getRecentNPlatformArticles(String platform, String category) {
		return articleDaoInstance.getNArticlesByCarouselSelectorAndCategory(platform, category, new Date().getTime(), Article.PLATFORM);
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
