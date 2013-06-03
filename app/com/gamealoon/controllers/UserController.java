package com.gamealoon.controllers;

import java.util.HashMap;
import java.util.List;

import com.gamealoon.database.GloonDAO;
import com.google.code.morphia.Datastore;
import play.mvc.Controller;
import play.mvc.Result;
import static play.libs.Json.toJson;

public class UserController extends Controller{

	static final GloonDAO gloonDaoInstance = GloonDAO.instantiateDAO();
	static final Datastore gloonDatastore = gloonDaoInstance.initDatastore();
	
	public static Result getUser(String username)
	{
		HashMap<String, Object> userMap= getUser(gloonDatastore, username);
		//TODO add href element to json
		return ok(toJson(userMap));
	}
	
	public static Result getUsers()
	{
		List<HashMap<String, Object>> userMaps = getUsers(gloonDatastore);
		//TODO add href element to json
		return ok(toJson(userMaps));
	}
	
	public static Result getLoggedInUser(String userName, String password)
	{
		HashMap<String, Object> userObject=getLoggedInUser(gloonDatastore,userName, password) ;
		return ok(toJson(userObject));
	}
	/**
	 * Get user based on userName
	 * 
	 * @param gloonDatastore
	 * @param username
	 * @return
	 */
	private static HashMap<String, Object> getUser(Datastore gloonDatastore, String username)
	{
		return gloonDaoInstance.getUser(gloonDatastore, username);
	}
	
	/**
	 * Get all users sorted by score (High to low)
	 * 
	 * @param gloonDatastore
	 * @return
	 */
	private static List<HashMap<String, Object>> getUsers(Datastore gloonDatastore)
	{
		//-1 signifies all
		return gloonDaoInstance.getTopNUsers(gloonDatastore, -1);
	}
	
	/**
	 * Get user information
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	private static HashMap<String, Object> getLoggedInUser(Datastore gloonDatastore, String username, String password)
	{
		return gloonDaoInstance.getLoggedInUser(gloonDatastore, username, password);
	}

}
