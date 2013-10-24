package com.gamealoon.database.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import com.gamealoon.models.Activity;
import com.gamealoon.models.Game;
import com.gamealoon.models.User;

public interface ActivityInterface {

	/**
	 * Save an Activity
	 * 
	 * @param activity
	 */
	public void save(Activity activity);
	
	/**
	 * Create and save new article
	 * 
	 * @param type
	 * @param username
	 * @param entityId
	 * @param visbility
	 * @param insertTime
	 */
	public void create(Integer type, String username, String entityId, Integer visbility, String insertTime, Long timestamp);
	
	/**
	 * Get Activities for one single User. We can fetch the recent activities also if no userId available.
	 * 
	 * @param user
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getActivities(User user);
	
	
	/**
	 * Get User's personal public activities
	 * 
	 * @param user
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getPublicActivitiesForUser(User user);
	
	/**
	 * Get Game's public activities
	 * 
	 * @param user
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getActivitiesForGame(Game game);
}
