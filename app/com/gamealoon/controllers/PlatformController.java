package com.gamealoon.controllers;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.gamealoon.database.daos.ArticleDAO;
import com.gamealoon.database.daos.PlatformDAO;
import com.gamealoon.database.daos.UserDAO;
import com.gamealoon.models.Article;
import com.gamealoon.utility.AppConstants;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import static play.data.Form.form;
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
	private static final PlatformDAO platformDaoInstance = PlatformDAO.instantiateDAO();
	  
	public static Result getPlatformData(String platform, String category) {

		HashMap<String, Object> carouselArticleMaps = getAllArticlesForPlatformCarousel(platform);				
		List<HashMap<String, Object>> top3Users= new ArrayList<>();
		try {
			top3Users = getTop3Users();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HashMap<String, Object> platformDataMap = new HashMap<>();
		platformDataMap.put("carouselArticles", carouselArticleMaps);
		try {
			platformDataMap.put("recentNArticles", getRecentNPlatformArticles(platform,category));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		platformDataMap.put("top3Users", top3Users);
		platformDataMap.put("platform", platform);
		System.out.println( AppConstants.APP_BASE_URL);
		platformDataMap.put("baseUrl", AppConstants.APP_BASE_URL); // a hack to determine the base url to set resources in HTML
		return ok(toJson(platformDataMap));
	}
	
	public static Result getNPlatforms(String timestamp)
	{
		List<HashMap<String, Object>> platforms = platformDaoInstance.getNPlatforms(10, Long.valueOf(timestamp));
		return ok(toJson(platforms));
	}
	
	public static Result createOrUpdatePlatform()
	{
		DynamicForm requestData = form().bindFromRequest();
		HashMap<String, String> response = platformDaoInstance.createOrUpdatePlatform(requestData);
		return ok(toJson(response));
	}

	/**
	 * This method generates all carousel data.
	 * 
	 * @return
	 */
	private static HashMap<String, Object> getAllArticlesForPlatformCarousel(String platform) 
	{
		HashMap<String, Object> response = new HashMap<>();

		try {
			response= articleDaoInstance.getAllArticlesForCarousel(platform);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * This method returns all recent 10 articles in all categories
	 * 
	 * @return
	 * @throws MalformedURLException 
	 */
	private static List<HashMap<String, Object>> getRecentNPlatformArticles(String platform, String category) throws MalformedURLException {
		return articleDaoInstance.getNArticlesByCarouselSelectorAndCategory(platform, category, new Date().getTime(), Article.PLATFORM);
	}

	/**
	 * This method returns top 5 users
	 * 
	 * @return
	 * @throws MalformedURLException 
	 */
	private static List<HashMap<String, Object>> getTop3Users() throws MalformedURLException {
		return userDaoInstance.fetchTopThreeUsers();
	}
}
