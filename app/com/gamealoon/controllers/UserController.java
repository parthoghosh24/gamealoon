package com.gamealoon.controllers;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.gamealoon.database.daos.UserDAO;
import static play.data.Form.*;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import static play.libs.Json.toJson;

public class UserController extends Controller {

	private static final UserDAO userDaoInstance = UserDAO.instantiateDAO();

	public static Result getUser(final String usernameOrId, final Integer mode, final String username) {
		final HashMap<String, Object> userMap = getUserMap(usernameOrId, mode, username);
		return ok(toJson(userMap));
	}

	public static Result getLoggedInUser() {
		final DynamicForm requestData = form().bindFromRequest();
		final String usernameOrEmail = requestData.get("usernameOrEmail");
		final String password = requestData.get("password");
		final HashMap<String, Object> userObject = getLoggedInUserMap(usernameOrEmail, password);
		return ok(toJson(userObject));
	}

	public static Result registerUser() {
		final DynamicForm requestData = form().bindFromRequest();
		final String firstName = requestData.get("firstName");
		final String lastName = requestData.get("lastName");
		final String username = requestData.get("username");
		final String password = requestData.get("password");
		final String email = requestData.get("email");
		final HashMap<String, Object> registeredUser = registerUserMap(username, password, email, firstName, lastName);
		return ok(toJson(registeredUser));

	}

	public static Result saveOrUpdateUserInterest(final String userName) {
		final DynamicForm requestData = form().bindFromRequest();
		final Integer type = Integer.parseInt(requestData.get("type"));
		final String content = requestData.get("content");
		final HashMap<String, Object> response = saveOrUpdateUserInterestMap(userName, type, content);
		return ok(toJson(response));
	}

	public static Result saveOrUpdateUser(final String userName) {
		final DynamicForm requestData = form().bindFromRequest();
		final HashMap<String, Object> response = saveOrUpdateUserMap(userName, requestData);
		return ok(toJson(response));
	}

	public static Result resetPassword(final String username) {
		final DynamicForm requestData = form().bindFromRequest();
		final HashMap<String, Object> response = resetPasswordMap(username, requestData);
		return ok(toJson(response));
	}

	public static Result addOrRemoveBuddy() {
		final DynamicForm requestData = form().bindFromRequest();
		final String originalUsername = requestData.get("originalUser");
		final String buddyUsername = requestData.get("buddyUser");
		final String type = requestData.get("type");
		final HashMap<String, String> response = addOrRemoveBuddyMap(originalUsername, buddyUsername, type);
		return ok(toJson(response));
	}

	public static Result blockOrUnblockBuddy() {
		final DynamicForm requestData = form().bindFromRequest();
		final String originalUsername = requestData.get("originalUser");
		final String buddyUsername = requestData.get("buddyUser");
		final String type = requestData.get("type");
		final HashMap<String, String> response = blockOrUnblockBuddyMap(originalUsername, buddyUsername, type);
		return ok(toJson(response));
	}

	public static Result addOrRemoveInterestedGames() {
		final DynamicForm requestData = form().bindFromRequest();
		final String originalUsername = requestData.get("originalUser");
		final String gameId = requestData.get("gameId");
		final String type = requestData.get("type");
		final HashMap<String, String> response = addOrRemoveInterestedGamesMap(originalUsername, gameId, type);
		return ok(toJson(response));
	}

	public static Result checkStatus(final String userName, final String mediaId) {
		response().setHeader("Access-Control-Allow-Origin", "*"); // Need to add the correct domain in here!!
		response().setHeader("Access-Control-Allow-Methods", "POST"); // Only allow POST
		response().setHeader("Access-Control-Max-Age", "300"); // Cache response for 5 minutes
		response().setHeader("Access-Control-Allow-Headers",
				"accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");
		// response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept"); // Ensure this
		// header is also allowed!
		return ok();
	}

	public static Result saveOrUpdateUserAvatar(final String username, final String mediaId) {
		Logger.info("Save or updated called");
		final MultipartFormData body = request().body().asMultipartFormData();
		final FilePart avatarPart = body.getFile("userAvatarFile");
		final HashMap<String, String> response = saveOrUpdateUserAvatarMap(mediaId, username, avatarPart);
		response().setHeader("Access-Control-Allow-Origin", "*"); // Need to add the correct domain in here!!
		response().setHeader("Access-Control-Allow-Methods", "POST"); // Only allow POST
		response().setHeader("Access-Control-Max-Age", "300"); // Cache response for 5 minutes
		response().setHeader("Access-Control-Allow-Headers",
				"accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");
		return ok(toJson(response));
	}

	public static Result validateEmail(final String email) {
		final HashMap<String, String> response = validateEmailMap(email);
		return ok(toJson(response));
	}

	public static Result validateUsername(final String username) {
		final HashMap<String, String> response = validateUsernameMap(username);
		return ok(toJson(response));
	}

	public static Result getAllUsers() {
		List<HashMap<String, Object>> allTopUsers = new ArrayList<>();
		try {
			allTopUsers = getAllTopUsers();
		} catch (final MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok(toJson(allTopUsers));
	}
	
	public static Result getUserSocialData(final String username)
	{
		final HashMap<String, Object> response = getUserSocialDataMap(username);
		return ok(toJson(response));
	}
	
	public static Result getUserStats(final String username)
	{
		final HashMap<String, Object> response = getUserStatsMap(username);
		return ok(toJson(response));
	}
	
	public static Result confirmUserEmail(final String username, final String token)
	{
		return ok(toJson(userDaoInstance.checkConfirmStatusForUser(username, token)));
	}
	
	/**
	 * Fetch user stats data i.e awards, XP, levels and so on
	 * 
	 * @param username
	 * @return
	 */
	private static HashMap<String, Object> getUserStatsMap(String username) {

		return  userDaoInstance.fetchStats(username);
	}

	/**
	 * Fetch User social data i.e followers, followings and games
	 * 
	 * @param username
	 * @return
	 */
	private static HashMap<String, Object> getUserSocialDataMap(final String username) {
		HashMap<String, Object> userSocialMap = new HashMap<>();
		try {
			userSocialMap = userDaoInstance.fetchSocial(username);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userSocialMap;
	}

	/**
	 * Save or update user avatar image
	 * 
	 * @param username
	 * @param requestData
	 * @return
	 */
	private static HashMap<String, String> saveOrUpdateUserAvatarMap(final String mediaId, final String username,
			final FilePart avatarPart) {
		return userDaoInstance.saveOrUpdateUserAvatar(mediaId, username, avatarPart);
	}

	/**
	 * Block or unblock an user
	 * 
	 * @param originalUsername
	 * @param buddyUsername
	 * @param type
	 * @return
	 */
	private static HashMap<String, String> blockOrUnblockBuddyMap(final String originalUsername, final String buddyUsername,
			final String type) {
		return userDaoInstance.blockOrUnblockBuddy(originalUsername, buddyUsername, Integer.parseInt(type));
	}

	/**
	 * Add or remove buddy for an user.
	 * 
	 * @param originalUsername
	 * @param buddyUsername
	 * @return
	 */
	private static HashMap<String, String> addOrRemoveBuddyMap(final String originalUsername, final String buddyUsername,
			final String type) {
		return userDaoInstance.addOrRemoveBuddy(originalUsername, buddyUsername, Integer.parseInt(type));
	}

	/**
	 * Add or remove interested game for an user
	 * 
	 * @param originalUsername
	 * @param buddyUsername
	 * @return
	 */
	private static HashMap<String, String> addOrRemoveInterestedGamesMap(final String originalUsername, final String gameId,
			final String type) {
		return userDaoInstance.addOrRemoveInterestedGames(originalUsername, gameId, Integer.parseInt(type));
	}

	/**
	 * Reset user for a particular user
	 * 
	 * @param username
	 * @param requestData
	 * @return
	 */
	private static HashMap<String, Object> resetPasswordMap(final String username, final DynamicForm requestData) {
		return userDaoInstance.resetPassword(username, requestData);
	}

	/**
	 * Save or update user Information
	 * 
	 * @param userName
	 * @param requestData
	 * @return
	 */
	private static HashMap<String, Object> saveOrUpdateUserMap(final String userName, final DynamicForm requestData) {
		return userDaoInstance.saveOrUpdateUser(userName, requestData);
	}

	/**
	 * Save or update User interest
	 * 
	 * @param userName
	 * @param type
	 * @param content
	 * @return
	 */
	private static HashMap<String, Object> saveOrUpdateUserInterestMap(final String userName, final Integer type, final String content) {

		return userDaoInstance.saveOrUpdateUserInterest(userName, type, content.split(","));
	}

	/**
	 * Get user based on userName or userid
	 * 
	 * @param gloonDatastore
	 * @param username
	 * @return
	 */
	private static HashMap<String, Object> getUserMap(final String usernameOrId, final Integer mode, final String username) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response = userDaoInstance.getUser(usernameOrId, mode, username);
		} catch (final MalformedURLException e) {
			Logger.error("Some thing wrong happened while fetching user " + e.fillInStackTrace());
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Check user exist or not. If yes, login the user else send false
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	private static HashMap<String, Object> getLoggedInUserMap(final String usernameOrEmail, final String password) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response = userDaoInstance.getLoggedInUser(usernameOrEmail, password);
		} catch (final MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Register user
	 * 
	 * @param gloondatastore2
	 * @param username
	 * @param password
	 * @param email
	 * @return
	 */
	private static HashMap<String, Object> registerUserMap(final String username, final String password, final String email,
			final String firstName, final String lastName) {
		return userDaoInstance.registerUser(username, password, email, firstName, lastName);
	}

	/**
	 * Validate whether email exists or not
	 * 
	 * @param email
	 * @return
	 */
	private static HashMap<String, String> validateEmailMap(final String email) {

		return userDaoInstance.validateEmail(email);
	}

	/**
	 * Validate whether username exists or not
	 * 
	 * @param username
	 * @return
	 */
	private static HashMap<String, String> validateUsernameMap(final String username) {

		return userDaoInstance.validateUsername(username);
	}

	/**
	 * This method returns all users ordered by score
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	private static List<HashMap<String, Object>> getAllTopUsers() throws MalformedURLException {
		return userDaoInstance.getTopNUsers(0);
	}

}
