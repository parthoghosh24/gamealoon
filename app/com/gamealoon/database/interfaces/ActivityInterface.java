package com.gamealoon.database.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import com.gamealoon.models.Activity;
import com.gamealoon.models.User;

public interface ActivityInterface {

	/**
	 * Save an Activity
	 * 
	 * @param activity
	 */
	public void save(Activity activity);
	
	/**
	 * Get Activities for one single User. We can fetch the recent activities also if no userId available.
	 * 
	 * @param user
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getActivities(User user);
}
