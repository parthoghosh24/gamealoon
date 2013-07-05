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
	
	public static Result getUser(String usernameOrId, Integer mode)
	{
		HashMap<String, Object> userMap= getUserMap(usernameOrId, mode);		
		//TODO add href element to json
		return ok(toJson(userMap));
	}
	
	public static Result getUsers()
	{
		List<HashMap<String, Object>> userMaps = getUsersMap();
		//TODO add href element to json
		return ok(toJson(userMaps));
	}
	
	public static Result getLoggedInUser(String userName, String password)
	{
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
	

	/**
	 * Get user based on userName or userid
	 * 
	 * @param gloonDatastore
	 * @param username
	 * @return
	 */
	private static HashMap<String, Object> getUserMap(String usernameOrId, Integer mode)
	{
		return userDaoInstance.getUser(usernameOrId, mode);
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
