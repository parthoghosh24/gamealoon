package com.gamealoon.database.interfaces;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import play.data.DynamicForm;

import com.gamealoon.models.Game;

public interface GameInterface {
	
	
	/**
	 * Save a Game instance
	 * 
	 * @param game
	 */
	public void save(Game game);

	/**
	 *Create or update game
	 * 
	 * @return
	 */
	public HashMap<String, String> createOrUpdateGame(DynamicForm requestData)throws ParseException;
	
	/**
	 * Fetch Top N Games
	 * 
	 * 
	 * @param type
	 * @return
	 */
	public List<HashMap<String, Object>> getTopNGames(int limit, String platform) throws MalformedURLException;
	
	/**
	 * Fetch recent N games
	 * 
	 * @param limit
	 * @param platform
	 * @return
	 */
	public List<HashMap<String, Object>> getRecentNGames(int limit, String platform)  throws MalformedURLException;
	
	/**
	 * Find game by title
	 * 
	 * @param title
	 * @return
	 */
	public Game findByTitle(String title);
	
	/**
	 * Find game by id
	 * 
	 * @param id
	 * @return
	 */
	public HashMap<String, Object> getById(String urlOrid, String username) throws MalformedURLException;
	
	/**
	 * Find games for autocomplete
	 * 
	 * @param term
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> findAllByTerm(String term) throws MalformedURLException;
	
	/**
	 * Find game by id
	 * 
	 * @param urlOrid
	 * @return
	 */
	public Game getGameById(String urlOrid);
}
