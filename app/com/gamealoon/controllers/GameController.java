package com.gamealoon.controllers;

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
		return gameDaoInstance.findAllByTerm(term);
	}

	/**
	 * Fetch Game by Id
	 * 
	 * @param id
	 * @return
	 */
	private static HashMap<String, Object> getGameMap(String urlOrid, String username) {		 
		return gameDaoInstance.findById(urlOrid, username);
	}
	
}
