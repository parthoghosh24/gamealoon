package com.gamealoon.database.interfaces;

import java.util.HashMap;
import java.util.List;

import com.gamealoon.models.User;

public interface UserInterface {

	
	/**
	 * Save User Instance
	 * 
	 * @param user
	 */
	public void save(User user);	

	/**
	 * Fetch all Top N Users
	 * 
	 * 
	 * @param type
	 * @return
	 */
	
	public List<HashMap<String, Object>> getTopNUsers(int limit);
	
	/**
	 * Get Status of Logged in User.
	 * 
	 * 
	 * @param type
	 * @return
	 */
	public HashMap<String, Object> getLoggedInUser(String username, String password);
	
	/**
	 * Register a user and insert into database on success.
	 * 
	 * 
	 * @param type
	 * @return
	 */
	public HashMap<String, Object> registerUser(String username, String password, String email);
	
	/**
	 * Fetch Single user by username or Id
	 * 
	 * 
	 * @param type
	 * @return
	 */
	
	public HashMap<String, Object> getUser(String usernameOrId, Integer mode);
	
	/**
	 * Find User by username
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);
	
	/**
	 * Get count
	 * 
	 * @return
	 */
	public Long count();
	
}
