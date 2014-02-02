package com.gamealoon.database.interfaces;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

import play.data.DynamicForm;
import play.mvc.Http.MultipartFormData.FilePart;

import com.gamealoon.core.common.XPTriggerPoints;
import com.gamealoon.database.mongo.api.MongoDao;
import com.gamealoon.models.User;

public interface UserInterface extends MongoDao<User> {

	/**
	 * Fetch all Top N Users
	 * 
	 * @param type
	 * @return
	 */

	public List<HashMap<String, Object>> getTopNUsers(int limit) throws MalformedURLException;

	/**
	 * Get Status of Logged in User.
	 * 
	 * 
	 * @param type
	 * @return
	 */
	public HashMap<String, Object> getLoggedInUser(String username, String password) throws MalformedURLException;

	/**
	 * Register a user and insert into database on success.
	 * 
	 * 
	 * @param type
	 * @return
	 */
	public HashMap<String, Object> registerUser(String username, String password, String email, String firstName, String lastName);

	/**
	 * Fetch Single user by username or Id
	 * 
	 * 
	 * @param type
	 * @return
	 */

	public HashMap<String, Object> getUser(String usernameOrId, Integer mode, String username) throws MalformedURLException;

	/**
	 * This method saves or updates user interests
	 * 
	 * @param username
	 * @param type
	 * @param interests
	 * @return
	 */
	public HashMap<String, Object> saveOrUpdateUserInterest(String username, int type, String[] interests);

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
	 * Find User by email
	 * 
	 * @param username
	 * @return
	 */
	public User findByEmail(String email);

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
	 * Save or update user avatar image
	 * 
	 * @param username
	 * @param requestData
	 * @return
	 */
	public HashMap<String, String> saveOrUpdateUserAvatar(String mediaId, String username, FilePart avatarPart);

	/**
	 * Get count
	 * 
	 * @return
	 */
	public Long count();

	/**
	 * Fetch N top Users based on Limit
	 * 
	 * @param gloonDatastore
	 * @param limit
	 * @return
	 */
	public List<User> getTopUsers(int limit);

	/**
	 * Validate email whether email exists or not
	 * 
	 * @param email
	 * @return
	 */
	public HashMap<String, String> validateEmail(String email);

	/**
	 * Validate email whether username exists or not
	 * 
	 * @param username
	 * @return
	 */
	public HashMap<String, String> validateUsername(String username);
	
	/**
	 * Fetch user's social information that is, followers, followings and interested games
	 * 
	 * @param username
	 * @return
	 */
	public HashMap<String, Object> fetchSocial(String username) throws MalformedURLException;
	
	/**
	 * Fetch user's stats which includes awards (achievements), XP and level info
	 * 
	 * @param username
	 * @return
	 */
	public HashMap<String, Object> fetchStats(String username);
	
	/**
	 * Update a user's trigger points
	 * 
	 * @param username
	 * @param xpTriggerPoints
	 */
	public void updateUserXP(final User user, XPTriggerPoints xpTriggerPoints);
	
	/**
	 * Fetch top 3 users reverse sorted by XP
	 * 
	 * @return
	 */
	public List<HashMap<String, Object>> fetchTopThreeUsers()throws MalformedURLException ;
	
	/**
	 * Send a confirm mail to user to get user's email confirmed. 
	 */
	public void sendConfirmMail(User user);
	
	/**
	 * Check user's confirmation status. Confirmation Token will be checked with database value and if there is match then it is a success.
	 * 
	 * @return
	 */
	public HashMap<String, String> checkConfirmStatusForUser(String username, String confirmationToken);
	
	/**
	 * Confirm user exist against provide emailId and if yes, send mail for password confirmation
	 * 
	 * @param emailId
	 * @return
	 */
	public HashMap<String, String> confirmAndSendMailForPasswordReset(String emailId);
	
	/**
	 * Update password after checking user exists and the passwordResetToken matches
	 * 
	 * @param emailId
	 * @param token
	 * @return
	 */
	public HashMap<String, String> checkAndUpdatePassword(String emailId, String token, String password);
}
