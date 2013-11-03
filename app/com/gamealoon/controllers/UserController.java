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

public class UserController extends Controller{

	private static final UserDAO userDaoInstance = UserDAO.instantiateDAO();	
	
	public static Result getUser(String usernameOrId, Integer mode, String username)
	{
		HashMap<String, Object> userMap= getUserMap(usernameOrId, mode, username);		
		return ok(toJson(userMap));
	}		
	
	public static Result getLoggedInUser()
	{
		DynamicForm requestData = form().bindFromRequest();
		String usernameOrEmail=requestData.get("usernameOrEmail");
		String password=requestData.get("password");
		HashMap<String, Object> userObject=getLoggedInUserMap(usernameOrEmail, password) ;
		return ok(toJson(userObject));
	}
	
	public static Result registerUser()
	{
		DynamicForm requestData = form().bindFromRequest();
		String firstName = requestData.get("firstName");
		String lastName = requestData.get("lastName");
		String username=requestData.get("username");		
		String password = requestData.get("password");
		String email = requestData.get("email");
		HashMap<String, Object> registeredUser=registerUserMap(username, password, email, firstName, lastName) ;
		return ok(toJson(registeredUser));
		
	} 
	
	public static Result saveOrUpdateUserInterest(String userName)
	{
		DynamicForm requestData = form().bindFromRequest();
	    Integer type = Integer.parseInt(requestData.get("type"));
	    String content =requestData.get("content");
	    HashMap<String, Object> response = saveOrUpdateUserInterestMap(userName, type, content);
	    return ok(toJson(response));
	}
	
	public static Result saveOrUpdateUser(String userName)	
	{
		DynamicForm requestData = form().bindFromRequest();
		HashMap<String, Object> response = saveOrUpdateUserMap(userName, requestData);
		return ok(toJson(response));
	}
	
	public static Result resetPassword(String username)
	{
		DynamicForm requestData = form().bindFromRequest();
		HashMap<String, Object> response = resetPasswordMap(username, requestData);
		return ok(toJson(response));
	}
	
	public static Result addOrRemoveBuddy()
	{
		DynamicForm requestData = form().bindFromRequest();
		String originalUsername = requestData.get("originalUser");
		String buddyUsername = requestData.get("buddyUser");
		String type=requestData.get("type");
		HashMap<String, String> response = addOrRemoveBuddyMap(originalUsername, buddyUsername, type);
		return ok(toJson(response));
	}
	
	public static Result blockOrUnblockBuddy()
	{
		DynamicForm requestData = form().bindFromRequest();
		String originalUsername = requestData.get("originalUser");
		String buddyUsername = requestData.get("buddyUser");
		String type=requestData.get("type");
		HashMap<String,String> response = blockOrUnblockBuddyMap(originalUsername, buddyUsername, type);
		return ok(toJson(response));
	}
	
	public static Result addOrRemoveInterestedGames()
	{
		DynamicForm requestData = form().bindFromRequest();
		String originalUsername = requestData.get("originalUser");
		String gameId = requestData.get("gameId");
		String type=requestData.get("type");
		HashMap<String, String> response = addOrRemoveInterestedGamesMap(originalUsername, gameId, type);
		return ok(toJson(response));
	}
	public static Result checkStatus(String userName, String mediaId)
	{		
		 	response().setHeader("Access-Control-Allow-Origin", "*");       // Need to add the correct domain in here!!
		    response().setHeader("Access-Control-Allow-Methods", "POST");   // Only allow POST
		    response().setHeader("Access-Control-Max-Age", "300");          // Cache response for 5 minutes
		    response().setHeader("Access-Control-Allow-Headers", "accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");
//		    response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");         // Ensure this header is also allowed!  
		    return ok();
	}
		
	public static Result saveOrUpdateUserAvatar(String username, String mediaId)
	{	
		Logger.info("Save or updated called");		
		MultipartFormData body = request().body().asMultipartFormData();				
		FilePart avatarPart = body.getFile("userAvatarFile");		
		HashMap<String,String> response = saveOrUpdateUserAvatarMap(mediaId,username,avatarPart);
		response().setHeader("Access-Control-Allow-Origin", "*");       // Need to add the correct domain in here!!
	    response().setHeader("Access-Control-Allow-Methods", "POST");   // Only allow POST
	    response().setHeader("Access-Control-Max-Age", "300");          // Cache response for 5 minutes
	    response().setHeader("Access-Control-Allow-Headers", "accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");		
		return ok(toJson(response));
	}
	
	public static Result validateEmail(String email)
	{
		HashMap<String,String> response = validateEmailMap(email);
		return ok(toJson(response));
	}
	
	

	public static Result validateUsername(String username)
	{
		HashMap<String,String> response = validateUsernameMap(username);
		return ok(toJson(response));
	}
	
	public static Result getAllUsers()
	{
		List<HashMap<String, Object>> allTopUsers= new ArrayList<>();
		try {
			allTopUsers = getAllTopUsers();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok(toJson(allTopUsers));
	}

	

	/**
	 * Save or update user avatar image
	 * 
	 * @param username
	 * @param requestData
	 * @return
	 */
	private static HashMap<String, String> saveOrUpdateUserAvatarMap(String mediaId, String username, FilePart avatarPart) {				
		return userDaoInstance.saveOrUpdateUserAvatar(mediaId,username, avatarPart);
	}

	/**
	 *	Block or unblock an user
	 * 
	 * @param originalUsername
	 * @param buddyUsername
	 * @param type
	 * @return
	 */
	private static HashMap<String, String> blockOrUnblockBuddyMap(String originalUsername, String buddyUsername, String type)
	{
		return userDaoInstance.blockOrUnblockBuddy(originalUsername, buddyUsername, Integer.parseInt(type));
	}
	
	/**
	 * Add or remove buddy for an user
	 * 
	 * @param originalUsername
	 * @param buddyUsername
	 * @return
	 */
	private static HashMap<String, String> addOrRemoveBuddyMap(String originalUsername, String buddyUsername, String type)
	{
		return userDaoInstance.addOrRemoveBuddy(originalUsername, buddyUsername, Integer.parseInt(type));
	}
	
	/**
	 * Add or remove interested game for an user
	 * 
	 * @param originalUsername
	 * @param buddyUsername
	 * @return
	 */
	private static HashMap<String, String> addOrRemoveInterestedGamesMap(String originalUsername, String gameId, String type)
	{
		return userDaoInstance.addOrRemoveInterestedGames(originalUsername, gameId, Integer.parseInt(type));
	}
	/**
	 * Reset user for a particular user
	 * 
	 * @param username
	 * @param requestData
	 * @return
	 */
	private static HashMap<String, Object> resetPasswordMap(String username,DynamicForm requestData) {		
		return userDaoInstance.resetPassword(username, requestData);
	}

	/**
	 * Save or update user Information
	 * 
	 * @param userName
	 * @param requestData
	 * @return
	 */
	private static HashMap<String, Object> saveOrUpdateUserMap(String userName,DynamicForm requestData) {		
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
	private static HashMap<String, Object> saveOrUpdateUserInterestMap(String userName, Integer type, String content) {
		
		return userDaoInstance.saveOrUpdateUserInterest(userName, type, content.split(","));
	}

	/**
	 * Get user based on userName or userid
	 * 
	 * @param gloonDatastore
	 * @param username
	 * @return
	 */
	private static HashMap<String, Object> getUserMap(String usernameOrId, Integer mode, String username)
	{
		HashMap<String, Object> response = new HashMap<>();
		try {
			response= userDaoInstance.getUser(usernameOrId, mode, username);
		} catch (MalformedURLException e) {
			Logger.error("Some thing wrong happened while fetching user "+e.fillInStackTrace());
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
	private static HashMap<String, Object> getLoggedInUserMap(String usernameOrEmail, String password)
	{
		HashMap<String, Object> response= new HashMap<>();
		try {
			response= userDaoInstance.getLoggedInUser(usernameOrEmail, password);
		} catch (MalformedURLException e) {
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
	private static HashMap<String, Object> registerUserMap(String username, String password, String email, String firstName, String lastName) {		
		return userDaoInstance.registerUser(username, password, email, firstName, lastName);
	}
	
	/**
	 * Validate whether email exists or not
	 * 
	 * @param email
	 * @return
	 */
	private static HashMap<String, String> validateEmailMap(String email) {
		
		return userDaoInstance.validateEmail(email);
	}
	
	/**
	 * Validate whether username exists or not
	 * 
	 * @param username
	 * @return
	 */
	private static HashMap<String, String> validateUsernameMap(String username) {
		
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

