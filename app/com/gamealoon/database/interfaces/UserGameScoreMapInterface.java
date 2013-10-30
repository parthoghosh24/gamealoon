package com.gamealoon.database.interfaces;

import java.util.HashMap;
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
	public HashMap<String, String> createOrUpdateScoreMap(String id, String gameId, String username, Double gameScore, Double weightedNetworkGameScore);
	
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
	
	/**
	 * Fetch UserGameScoreMap by id
	 * 
	 * @param id
	 * @return
	 */
	public UserGameScoreMap getById(String id);
	
}
