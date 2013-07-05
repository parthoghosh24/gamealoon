package com.gamealoon.database.interfaces;

import java.util.HashMap;
import java.util.List;

import com.gamealoon.models.Article;

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
		
	public HashMap<String, Object> getArticle(String userName, String title);	
	
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
}
