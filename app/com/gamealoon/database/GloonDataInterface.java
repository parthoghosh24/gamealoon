package com.gamealoon.database;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.code.morphia.Datastore;

public interface GloonDataInterface {

	public HashMap<String, Object> getAllArticlesForCarousel(Datastore gloonDatastore, String type);
	public List<HashMap<String, Object>> getTopNGames(Datastore gloonDatastore, int limit, String platform);
	public HashMap<String, Object> getRecentAllNArticles(Datastore gloonDatastore, int limit, String platform);
	public List<HashMap<String, Object>> getTopNUsers(Datastore gloonDatastore, int limit);
	public HashMap<String, Object> getArticle(Datastore gloonDatastore, String userName, String title);	
	public List<HashMap<String, Object>> getAllArticlesByKey(Datastore gloonDatastore, String key, String sortField);	
	public HashMap<String, Object> getUser(Datastore gloonDatastore, String username);
	public void updateArticleCoolUncoolScore(Datastore gloonDatastore, String urlTitle, String type);
	public void updateArticlePageHitCount(Datastore gloonDatastore, String urlTitle);
	public HashMap<String, Object> getSearchResponse(ArrayList<String> keywordList) throws IllegalAccessException, MalformedURLException, IOException;
	public HashMap<String, Object> getLoggedInUser(Datastore gloonDatastore, String username, String password);
	public HashMap<String, Object> registerUser(Datastore gloonDatastore, String username, String password, String email);
}
