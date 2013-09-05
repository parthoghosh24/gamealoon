package com.gamealoon.database.interfaces;

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
	
}
