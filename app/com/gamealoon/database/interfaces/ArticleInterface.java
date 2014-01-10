package com.gamealoon.database.interfaces;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import play.data.DynamicForm;

import com.gamealoon.database.mongo.api.MongoDao;
import com.gamealoon.models.Article;
import com.gamealoon.models.User;

public interface ArticleInterface extends MongoDao<Article> {

	/**
	 * Fetch all Articles for Carousel based on platform
	 * 
	 * 
	 * @param type
	 * @return
	 */
	public HashMap<String, Object> getAllArticlesForCarousel(String platform) throws MalformedURLException;

	/**
	 * Fetch all Articles for User page carousel. This is different because we will be marking one category as featured based on last
	 * updated.
	 * 
	 * @param username
	 * @return
	 */
	public List<HashMap<String, Object>> getAllArticlesForUserCarousel(String username) throws MalformedURLException;

	/**
	 * Fetch all Articles for Game page carousel.
	 * 
	 * @param gameId
	 * @return
	 */
	public HashMap<String, Object> getAllArticlesForGameCarousel(String gameId) throws MalformedURLException;

	/**
	 * Fetch Single Article
	 * 
	 * 
	 * @param type
	 * @return
	 */

	public HashMap<String, Object> getArticle(String userName, String titleOrId) throws MalformedURLException;

	/**
	 * Fetch all Articles by carousel selector that is platform, game or user and category. Timestamp to fetch data in batch for
	 * pagination.
	 * 
	 * 
	 * @param type
	 * @return
	 */

	public List<HashMap<String, Object>> getNArticlesByCarouselSelectorAndCategory(String categorySelector, String category,
			Long timestamp, Integer mode) throws MalformedURLException;
	
	/**
	 * Fetch all user drafts
	 * 
	 * @param username
	 * @return
	 */
	public List<HashMap<String, Object>> getUserDrafts(String username)throws MalformedURLException;
	

	/**
	 * Saves New Article
	 * 
	 * @return
	 */
	public HashMap<String, Object> createOrUpdateArticle(DynamicForm requestData);

	/**
	 * 10 Posts for one user. This needs to be updated
	 * 
	 * @param user
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getArticleListForUser(User user);

	/**
	 * Find all Published articles for a game by category
	 * 
	 * @param gameId
	 * @return
	 */
	public List<Article> findAllPublishedArticlesByGame(String gameId, String category);

	/**
	 * Fetch all articles by username
	 * 
	 * @param username
	 * @return
	 */
	public List<Article> findAllPublishedArticlesByUser(String username);

	/**
	 * Count All Published Articles. If no user passed, then total Published article count returned.
	 * 
	 */
	public Long allPublishedArticlesCount(User user);

	/**
	 * Create or update cool uncool value. Also this will update coolscore and notcoolscore of article as will as
	 * coolNotCoolWilsonScore for article
	 * 
	 * @param username
	 * @param articleId
	 * @param type
	 * @return
	 */
	public HashMap<String, Object> createOrUpdateCoolUncoolValue(String username, String articleId, Integer type);

	/**
	 * Update average time spent on article.
	 * 
	 */
	public HashMap<String, Object> updateArticleAverageTimeSpent(String articleId, Double timeSpent);

}
