package com.gamealoon.controllers;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.gamealoon.database.daos.GameDAO;

import play.mvc.Controller;
import play.mvc.Result;
import static play.libs.Json.toJson;

public class GameController extends Controller{

	private static final GameDAO gameDaoInstance = GameDAO.instantiateDAO();
	public static Result getGame(String urlOrid, String username)
	{
		HashMap<String, Object> gameMap = getGameMap(urlOrid, username);
		
		return ok(toJson(gameMap));
	}
	
	public static Result getGames(String term)
	{		
		ArrayList<HashMap<String, Object>> gameMaps = getGameMaps(term);
		return ok(toJson(gameMaps));
	}
	
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
	
}
