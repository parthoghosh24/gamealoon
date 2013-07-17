package com.gamealoon.controllers;

import java.util.HashMap;
import java.util.List;
import com.gamealoon.database.daos.ArticleDAO;
import static play.libs.Json.toJson;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import static play.data.Form.*;

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
	
	public static Result saveArticle()
	{
		DynamicForm requestData = form().bindFromRequest();
		String articleTitle =requestData.get("articleTitle");
		String articleSubTitle=requestData.get("articleSubTitle");
		String articleBody =requestData.get("articleBody");
		String articleCategory =requestData.get("articleCategory");
		String articleUsername =requestData.get("articleUsername");
		String articlePlatform =requestData.get("articlePlatform");
		String articleFeaturedImage =requestData.get("articleFeaturedImage");
		String articleGame =requestData.get("articleGame");
		String articleState =requestData.get("articleState");
		
		System.out.println("articleTitle: "+articleTitle);
		System.out.println("articleSubTitle: "+articleSubTitle);
		System.out.println("articleBody: "+articleBody);
		System.out.println("articleCategory: "+articleCategory);
		System.out.println("articleUsername: "+articleUsername);
		System.out.println("articlePlatform: "+articlePlatform);
		System.out.println("articleFeaturedImage: "+articleFeaturedImage);
		System.out.println("articleGame: "+articleGame);
		System.out.println("articleState: "+articleState);
		
		HashMap<String, Object> response = saveArticle(articleTitle, articleSubTitle, articleBody, articleCategory, articleUsername, articlePlatform, articleFeaturedImage, articleGame, articleState);
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
	
	/**
	 * Save a new article in system and return a status
	 * 
	 * @param articleTitle
	 * @param articleSubTitle
	 * @param articleBody
	 * @param category
	 * @param username
	 * @param platforms
	 * @param featuredImagePath
	 * @param game
	 * @param state
	 * @return
	 */
	private static HashMap<String, Object> saveArticle(String articleTitle, String articleSubTitle, String articleBody, String category, String username, String platforms, String featuredImagePath, String game, String state)
	{
		return articleDaoInstance.saveArticle(articleTitle, articleSubTitle, articleBody, category, username, platforms, featuredImagePath, game, state);
	}
}
