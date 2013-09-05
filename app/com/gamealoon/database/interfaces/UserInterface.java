package com.gamealoon.database.interfaces;

import java.util.HashMap;
import java.util.List;

import play.data.DynamicForm;

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
	
	public HashMap<String, Object> getUser(String usernameOrId, Integer mode, String username);
	
	/**
	 * This method saves or updates user interests
	 * 
	 * @param username
	 * @param type
	 * @param interests
	 * @return
	 */
	public HashMap<String, Object> saveOrUpdateUserInterest(String username,int type, String[] interests);	
	
	/**
	 * Save or update user
	 * 
	 * @param username
	 * @param requestData
	 * @return
	 */
	public HashMap<String, Object> saveOrUpdateUser(String username, DynamicForm requestData);
	
	/**
	 * Reset user password for a user
	 * 
	 * @param username
	 * @param requestData
	 * @return
	 */
	public HashMap<String, Object> resetPassword(String username, DynamicForm requestData);
	
	/**
	 * Find User by username
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);
	
	/**
	 * Add user in following/follower list
	 * 
	 * @param username
	 * @param buddyUsername
	 * @return
	 */
	public HashMap<String, String> addOrRemoveBuddy(String username, String buddyUsername, Integer type);
	
	/**
	 * Remove user from following/follower list
	 * 
	 * @param username
	 * @param buddyUsername
	 * @return
	 */
	public HashMap<String, String> blockOrUnblockBuddy(String username, String buddyUsername, Integer type);
	
	/**
	 * Add or remove interested games from user
	 * 
	 * @param username
	 * @param gameId
	 * @param type
	 * @return
	 */
	public HashMap<String, String> addOrRemoveInterestedGames(String username, String gameId, Integer type);
	
	/**
	 * Get count
	 * 
	 * @return
	 */
	public Long count();
	
}
