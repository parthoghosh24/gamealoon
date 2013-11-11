package com.gamealoon.database.interfaces;

import java.util.HashMap;
import java.util.List;

import play.data.DynamicForm;

import com.gamealoon.models.Platform;

public interface PlatformInterface {

	/**
	 * Save Platform Instance
	 * 
	 * @param platform
	 */
	public void save(Platform platform);
	
	/**
	 * create or updated platform Data
	 * 
	 * @param requestData
	 * @return
	 */
	public HashMap<String, String> createOrUpdatePlatform(DynamicForm requestData); 
	/**
	 * Find Platform by Title
	 * 
	 * @return
	 */
	public Platform findByTitle(String title);
	
	/**
	 * Find by short title
	 * 
	 * @param shortTitle
	 * @return
	 */
	public Platform findByShortTitle(String shortTitle);
	
	/**
	 * Get platform by id
	 * 
	 * @param id
	 * @return
	 */
	public Platform getById(String id);
	
	/**
	 * Get N Platforms
	 * 
	 * @param limit
	 * @param timeStamp
	 * @return
	 */
	public List<HashMap<String, Object>> getNPlatforms(int limit, long timeStamp);
}
