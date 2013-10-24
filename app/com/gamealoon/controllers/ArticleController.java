package com.gamealoon.controllers;

import java.util.HashMap;
import java.util.List;
import com.gamealoon.database.daos.ArticleDAO;
import static play.libs.Json.toJson;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import static play.data.Form.*;

public class ArticleController extends Controller{
	
	
	private static final ArticleDAO articleDaoInstance = ArticleDAO.instantiateDAO();	
	
	public static Result getArticle(String username, String articleTitleOrId)
	{		
		HashMap<String, Object> articleMap = getArticleMap(username, articleTitleOrId);				
		return ok(toJson(articleMap));
	}
	
	public static Result getNArticlesByCarouselSelectorAndCategory(String platform, String category, String timestamp, String mode)
	{			
		List<HashMap<String, Object>> articleListMap  = getNArticlesByCarouselSelectorAndCategoryMap(platform, category, timestamp, Integer.parseInt(mode));				
		return ok(toJson(articleListMap));
	}
	
	public static Result saveOrUpdateArticle()
	{
		DynamicForm requestData = form().bindFromRequest();		
		HashMap<String, Object> response = saveArticle(requestData);
		return ok(toJson(response));
	}
	
	
	public static Result createOrUpdateCoolOrNotCoolState()
	{
		DynamicForm requestData = form().bindFromRequest();		
		HashMap<String, Object> response = createOrUpdateVotingMap(requestData);
		return ok(toJson(response));
	}
	
	public static Result updateAverageTimeSpent()
	{
		DynamicForm requestData = form().bindFromRequest();
		HashMap<String, Object> response = updateArticleAverageTimeSpentMap(requestData);
		return ok(toJson(response));
	}

	/**
	 * Fetch single article by username and article title
	 * 
	 * 
	 * @param gloonDatastore
	 * @param userName
	 * @param articleTitle
	 * @return
	 */
	private static HashMap<String, Object> getArticleMap(String userName, String articleTitleOrId)
	{
		return articleDaoInstance.getArticle(userName, articleTitleOrId);
	}
	
	/**
	 * Fetch all articles for a single key: key can be user or category for now
	 * 
	 * 
	 * @param gloonDatastore
	 * @param key
	 * @return
	 */	
	private static List<HashMap<String, Object>> getNArticlesByCarouselSelectorAndCategoryMap(String platform, String category, String timestamp, Integer mode)
	{
		
		return articleDaoInstance.getNArticlesByCarouselSelectorAndCategory(platform, category, Long.parseLong(timestamp), mode);
	}
	
	/**
	 *  Save or updated a article in system and return a status
	 * 
	 * @param requestData
	 * @return
	 */
	private static HashMap<String, Object> saveArticle(DynamicForm requestData)
	{
		return articleDaoInstance.saveOrUpdateArticle(requestData);
	}
	
	/**
	 * Create Or update cool or not cool state for an article
	 * 
	 * @param requestData
	 * @return
	 */
	private static HashMap<String, Object> createOrUpdateVotingMap(DynamicForm requestData) {
		
		String userName = requestData.get("username");
		String articleId = requestData.get("articleId");
		Integer type = Integer.parseInt(requestData.get("type")); 
		return articleDaoInstance.createOrUpdateCoolUncoolValue(userName, articleId, type);
	}
	
	/**
	 * Update Average Time spent value of article
	 * 
	 * @param requestData
	 * @return
	 */
	private static HashMap<String, Object> updateArticleAverageTimeSpentMap(DynamicForm requestData)
	{
		String articleId= requestData.get("articleId");
		Double timeSpent= Double.parseDouble(requestData.get("timeSpent"));
		Logger.debug("timespent "+timeSpent);
		return articleDaoInstance.updateArticleAverageTimeSpent(articleId, timeSpent);
	}
}
