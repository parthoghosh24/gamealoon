package com.gamealoon.controllers;

import java.util.HashMap;
import java.util.List;
import com.gamealoon.database.GloonDAO;
import com.google.code.morphia.Datastore;
import static play.libs.Json.toJson;
import play.mvc.Controller;
import play.mvc.Result;

public class ArticleController extends Controller{
	
	
	private static final GloonDAO gloonDaoInstance = GloonDAO.instantiateDAO();
	private static final Datastore gloonDataStore = gloonDaoInstance.initDatastore();
	
	public static Result getArticle(String username, String articleTitle)
	{		
		HashMap<String, Object> articleMap = getArticle(gloonDataStore, username, articleTitle);		
		//TODO add href element to json
		return ok(toJson(articleMap));
	}
	
	public static Result getAllArticlesByKey(String key, String sortField)
	{		
		
		List<HashMap<String, Object>> articleListMap  = getArticlesByKey(gloonDataStore, key, sortField);		
		//TODO add href element to json
		return ok(toJson(articleListMap));
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
	private static HashMap<String, Object> getArticle(Datastore gloonDatastore, String userName, String articleTitle)
	{
		return gloonDaoInstance.getArticle(gloonDatastore, userName, articleTitle);
	}
	
	/**
	 * Fetch all articles for a single key: key can be user or category for now
	 * 
	 * @param gloonDatastore
	 * @param key
	 * @return
	 */	
	private static List<HashMap<String, Object>> getArticlesByKey(Datastore gloonDatastore, String key, String sortField)
	{
		return gloonDaoInstance.getAllArticlesByKey(gloonDatastore, key, sortField);
	}
		
}
