package com.gamealoon.database.interfaces;

import com.gamealoon.models.Achievement;

public interface AchievementInterface {

	/**
	 * Save Achievement instance
	 * 
	 * @param achievement
	 */
	public void save(Achievement achievement);
	
	/**
	 * Find Achievement by Title
	 * 
	 * @return
	 */
	public Achievement findByTitle(String title);
}
