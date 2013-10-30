package com.gamealoon.database.interfaces;

import java.util.HashMap;

import play.data.DynamicForm;

import com.gamealoon.models.Achievement;

public interface AchievementInterface {

	/**
	 * Save Achievement instance
	 * 
	 * @param achievement
	 */
	public void save(Achievement achievement);
	
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
	 * Fetch achievement by id
	 * 
	 * @param id
	 * @return
	 */
	public Achievement getById(String id);
}
