package com.gamealoon.controllers;

import java.util.HashMap;
import java.util.List;
import com.gamealoon.database.daos.ArticleDAO;
import static play.libs.Json.toJson;
import play.mvc.Controller;
import play.mvc.Result;

public class ArticleController extends Controller{
	
	
	private static final ArticleDAO articleDaoInstance = ArticleDAO.instantiateDAO();	
	
	public static Result getArticle(String username, String articleTitle)
	{		
		HashMap<String, Object> articleMap = getArticleMap(username, articleTitle);		
		//TODO add href element to json
		return ok(toJson(articleMap));
	}
	
	public static Result getAllArticlesByKey(String key, String sortField)
	{		
		
		List<HashMap<String, Object>> articleListMap  = getArticlesByKey(key, sortField);		
		//TODO add href element to json
		return ok(toJson(articleListMap));
	}
	
	public static Result saveOrUpdateArticle()
	{
		return ok();
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
	private static HashMap<String, Object> getArticleMap(String userName, String articleTitle)
	{
		return articleDaoInstance.getArticle(userName, articleTitle);
	}
	
	/**
	 * Fetch all articles for a single key: key can be user or category for now
	 * 
	 * 
	 * @param gloonDatastore
	 * @param key
	 * @return
	 */	
	private static List<HashMap<String, Object>> getArticlesByKey(String key, String sortField)
	{
		return articleDaoInstance.getAllArticlesByKey(key, sortField);
	}
		
}
