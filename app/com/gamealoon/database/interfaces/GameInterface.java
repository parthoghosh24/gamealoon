package com.gamealoon.database.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gamealoon.models.Game;

public interface GameInterface {
	
	
	/**
	 * Save a Game instance
	 * 
	 * @param game
	 */
	public void save(Game game);
	

	/**
	 * Fetch Top N Games
	 * 
	 * 
	 * @param type
	 * @return
	 */
	
	public List<HashMap<String, Object>> getTopNGames(int limit, String platform);
	
	/**
	 * Fetch recent N games
	 * 
	 * @param limit
	 * @param platform
	 * @return
	 */
	public List<HashMap<String, Object>> getRecentNGames(int limit, String platform);
	
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
	public HashMap<String, Object> findById(String urlOrid, String username);
	
	/**
	 * Find games for autocomplete
	 * 
	 * @param term
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> findAllByTerm(String term);
	
	/**
	 * Find game by id
	 * 
	 * @param urlOrid
	 * @return
	 */
	public Game getGameById(String urlOrid);
}
