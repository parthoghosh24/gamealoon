package com.gamealoon.database.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gamealoon.models.Article;
import com.gamealoon.models.User;

public interface ArticleInterface {
	
	
	/**
	 * Save Article.
	 * 
	 * @param article
	 */
	public void save(Article article);
	

	/**
	 * Fetch all Articles for Carousel
	 * 
	 * 
	 * @param type
	 * @return
	 */
	public HashMap<String, Object> getAllArticlesForCarousel(String type);
	
	/**
	 * Fetch all N Articles
	 * 
	 * 
	 * @param type
	 * @return
	 */
	
	public HashMap<String, Object> getRecentAllNArticles(int limit, String platform);
	
	/**
	 * Fetch Single Article
	 * 
	 * 
	 * @param type
	 * @return
	 */
		
	public HashMap<String, Object> getArticle(String userName, String titleOrId);	
	
	/**
	 * Fetch all Articles by key and sort by sortField
	 * 
	 * 
	 * @param type
	 * @return
	 */
	
	public List<HashMap<String, Object>> getAllArticlesByKey(String key, String sortField);
	
	/**
	 * Update Article's cool or uncool score
	 * 
	 * 
	 * @param type
	 * @return
	 */
	
	public void updateArticleCoolUncoolScore(String urlTitle, String type);
	
	/**
	 * Update Article Page Hit count
	 * 
	 * 
	 * @param type
	 * @return
	 */
	
	public void updateArticlePageHitCount(String urlTitle);
	
	/**
	 * Saves New Article
	 * 
	 * @return
	 */
	public HashMap<String, Object> saveArticle(String id, String articleTitle, String articleSubTitle, String articleBody, String category, String username, String platforms, String featuredImagePath, String game,String state);
	
	
	/**
	 * 10 Posts for one user. This needs to be updated
	 * 
	 * @param user
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getArticleListForUser(User user);
	
	
	/**
	 * Find all Published articles for a game
	 * 
	 * @param gameId
	 * @return
	 */
	public List<Article> findAllPublishedArticlesByGame(String gameId);
	
}
