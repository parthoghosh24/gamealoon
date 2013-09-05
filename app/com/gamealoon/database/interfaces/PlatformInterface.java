package com.gamealoon.database.interfaces;

import com.gamealoon.models.Platform;

public interface PlatformInterface {

	/**
	 * Save Platform Instance
	 * 
	 * @param platform
	 */
	public void save(Platform platform);
	
	/**
	 * Find Platform by Title
	 * 
	 * @return
	 */
	public Platform findByTitle(String title);
	
	/**
	 * Find bt short title
	 * 
	 * @param shortTitle
	 * @return
	 */
	public Platform findByShortTitle(String shortTitle);
}
