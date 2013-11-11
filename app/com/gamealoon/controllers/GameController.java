package com.gamealoon.controllers;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.gamealoon.database.daos.GameDAO;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import static play.data.Form.form;
import static play.libs.Json.toJson;

public class GameController extends Controller{

	private static final GameDAO gameDaoInstance = GameDAO.instantiateDAO();
	public static Result getGame(String urlOrid, String username)
	{
		HashMap<String, Object> gameMap = getGameMap(urlOrid, username);
		
		return ok(toJson(gameMap));
	}
	
	public static Result getGamesByTerm(String term)
	{		
		ArrayList<HashMap<String, Object>> gameMaps = getGameMaps(term);
		return ok(toJson(gameMaps));
	}
	
	public static Result getAllGames()
	{
		List<HashMap<String, Object>> gameMaps=getAllRecentPlatformGames("all");
		return ok(toJson(gameMaps));
	}
	
	public static Result getNGames(String timeStamp)
	{
		List<HashMap<String, Object>> gameMaps = new ArrayList<>();
		try {
			gameMaps= gameDaoInstance.getNGames(10, Long.valueOf(timeStamp), "all");
		} catch (MalformedURLException e) {
			Logger.error("Error in Game controller getAdminData: ",e.fillInStackTrace());
			e.printStackTrace();
		}
		return ok(toJson(gameMaps));
	}
	
	public static Result createOrUpdateGame()
	{
		DynamicForm requestData = form().bindFromRequest();
		HashMap<String, String> response = gameDaoInstance.createOrUpdateGame(requestData);
		return ok(toJson(response));
	}
	
	/**
	 * Get Game Maps based on term
	 * 
	 * @param term
	 * @return
	 */
	private static ArrayList<HashMap<String, Object>> getGameMaps(String term) {		
		ArrayList<HashMap<String, Object>> response = new ArrayList<>();
		try {
			response=gameDaoInstance.findAllByTerm(term);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Fetch Game by Id
	 * 
	 * @param id
	 * @return
	 */
	private static HashMap<String, Object> getGameMap(String urlOrid, String username) {		 
		HashMap<String, Object> response = new HashMap<>();
		try {
			response = gameDaoInstance.getById(urlOrid, username);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * This method returns all top/trending 5 games
	 * 
	 * @return
	 */
	private static List<HashMap<String, Object>> getAllRecentPlatformGames(String platform) {
		List <HashMap<String, Object>> response = new ArrayList<>();
		try {
			response=gameDaoInstance.getRecentNGames(0, platform);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
}
