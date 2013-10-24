package com.gamealoon.database.interfaces;

import java.util.List;

import com.gamealoon.models.UserGameScoreMap;

public interface UserGameScoreMapInterface {

	/**
	 * Save User Game score map instance
	 * 
	 * @param scoreMap
	 */
	public void save(UserGameScoreMap scoreMap);
	
	/**
	 *  create ScoreMap instance
	 * 
	 * @param gameId
	 * @param userId
	 */
	public void createScoreMap(String gameId, String username, Double gameScore, Double weightedNetworkGameScore);
	
	/**
	 * Find all instances by game id
	 * 
	 * @param gameId
	 * @return
	 */
	public List<UserGameScoreMap> findAllByGameId(String gameId);
	
	/**
	 * Find all instances by user id
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserGameScoreMap> findAllByUserId(String userId);
	
	/**
	 * Find single UserGameScoreMap instance by user and game 
	 * 
	 * @param userId
	 * @param gameId
	 * @return
	 */
	public UserGameScoreMap findByUserAndGame(String userId, String gameId);
	
	
}
