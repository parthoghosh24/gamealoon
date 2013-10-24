package com.gamealoon.database.interfaces;

import com.gamealoon.models.Article;
import com.gamealoon.models.User;
import com.gamealoon.models.UserArticleVotingMap;

public interface UserArticleVotingMapInterface {

	/**
	 * Save User Article Voting map instance
	 * 
	 * @param articleVotingMap
	 */
	public void save(UserArticleVotingMap articleVotingMap); 
	
	/**
	 * Create or update article voting user map. Set the value of cool or not-cool for article by user
	 * 
	 * @param userId
	 * @param articleId
	 * @param coolState
	 * @param NotCoolState
	 * @return
	 */
	public UserArticleVotingMap createOrUpdateUserArticleVotingMapInstance(User author, Article article, Integer type);
	
	/**
	 * Fetch by username and article
	 * 
	 * @param username
	 * @param article
	 * @return
	 */
	public UserArticleVotingMap fetchByUsernameAndArticle(String username, Article article);
}
