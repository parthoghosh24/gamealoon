package com.gamealoon.database.interfaces;

import java.util.HashMap;
import java.util.List;

import play.data.DynamicForm;

import com.gamealoon.database.mongo.api.MongoDao;
import com.gamealoon.models.Achievement;

public interface AchievementInterface extends MongoDao<Achievement> {

	/**
	 * Create or update achievement data
	 * 
	 * @param requestData
	 * @return
	 */
	public HashMap<String, String> createOrUpdateAchievement(DynamicForm requestData);

	/**
	 * Find Achievement by Title
	 * 
	 * @return
	 */
	public Achievement findByTitle(String title);

	/**
	 * Get N achievements
	 * 
	 * @param limit
	 * @param timestamp
	 * @return
	 */
	public List<HashMap<String, Object>> getNAchievements(int limit, long timestamp);

	/**
	 * Get games
	 * 
	 * @return
	 */
	public List<Achievement> getAchievements();
}
