package com.gamealoon.database.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import com.gamealoon.database.mongo.api.MongoDao;
import com.gamealoon.models.Activity;
import com.gamealoon.models.Game;
import com.gamealoon.models.User;

public interface ActivityInterface extends MongoDao<Activity> {

	/**
	 * Create and save new activity
	 * 
	 * @param type
	 * @param username
	 * @param entityId
	 * @param visbility
	 * @param insertTime
	 */
	public HashMap<String, String> createOrUpdateActivity(HashMap<String, String> activityMap);

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
