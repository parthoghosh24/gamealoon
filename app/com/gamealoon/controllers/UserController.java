package com.gamealoon.controllers;

import java.util.HashMap;
import java.util.List;
import com.gamealoon.database.daos.UserDAO;
import static play.data.Form.*;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import static play.libs.Json.toJson;

public class UserController extends Controller{

	private static final UserDAO userDaoInstance = UserDAO.instantiateDAO();	
	
	public static Result getUser(String usernameOrId, Integer mode, String username)
	{
		HashMap<String, Object> userMap= getUserMap(usernameOrId, mode, username);		
		return ok(toJson(userMap));
	}
	
	public static Result getUsers()
	{
		List<HashMap<String, Object>> userMaps = getUsersMap();
		//TODO add href element to json
		return ok(toJson(userMaps));
	}
	
	public static Result getLoggedInUser()
	{
		DynamicForm requestData = form().bindFromRequest();
		String userName=requestData.get("username");
		String password=requestData.get("password");
		HashMap<String, Object> userObject=getLoggedInUserMap(userName, password) ;
		return ok(toJson(userObject));
	}
	
	public static Result registerUser()
	{
		DynamicForm requestData = form().bindFromRequest();
		String username=requestData.get("username");		
		String password = requestData.get("password");
		String email = requestData.get("email");
		HashMap<String, Object> registeredUser=registerUserMap(username, password, email) ;
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
	
	public static Result addOrRemoveBuddy(String originalUsername, String buddyUsername, String type)
	{
		HashMap<String, String> response = addOrRemoveBuddyMap(originalUsername, buddyUsername, type);
		return ok(toJson(response));
	}
	
	public static Result blockOrUnblockBuddy(String originalUsername, String buddyUsername, String type)
	{
		HashMap<String,String> response = blockOrUnblockBuddyMap(originalUsername, buddyUsername, type);
		return ok(toJson(response));
	}

	/**
	 * Remove buddy for an user
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
	 * Add buddy for an user
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
		return userDaoInstance.getUser(usernameOrId, mode, username);
	}
	
	/**
	 * Get all users sorted by score (High to low)
	 * 
	 * @param gloonDatastore
	 * @return
	 */
	private static List<HashMap<String, Object>> getUsersMap()
	{
		//-1 signifies all
		return userDaoInstance.getTopNUsers(-1);
	}
	
	/**
	 * Check user exist or not. If yes, login the user else send false
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	private static HashMap<String, Object> getLoggedInUserMap(String username, String password)
	{
		return userDaoInstance.getLoggedInUser(username, password);
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
	private static HashMap<String, Object> registerUserMap(String username, String password, String email) {		
		return userDaoInstance.registerUser(username, password, email);
	}

}
