package com.gamealoon.database.daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.GameInterface;
import com.gamealoon.models.Game;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

public class GameDAO extends GloonDAO implements GameInterface{
	
	private static final GameDAO DATA_ACCESS_LAYER=new GameDAO();	
	private Datastore gloonDatastore=null;
	
	private GameDAO()
	{
		super();
		gloonDatastore=initDatastore();
	}
	
	/**
	 * Singleton way to instantiate Gloon DAO
	 * @return
	 */
	public static GameDAO instantiateDAO()
	{								
		return DATA_ACCESS_LAYER;
	}
	

	@Override
	public List<HashMap<String, Object>> getTopNGames(int limit, String platform) {
		 List <HashMap<String, Object>> topGames = new ArrayList<>();
		  List <Game> recentGames = getRecentGames(limit);
		  if(recentGames.size()>0)
		  {
			  for(Game game: recentGames)
			  {
				HashMap<String, Object> gameMap = new HashMap<String, Object>();
				gameMap.put("gameTitle", game.getTitle());
				gameMap.put("gameReleaseDate", game.getReleaseDate());
				gameMap.put("gameScore", game.getScore());
				topGames.add(gameMap);
			  }  
		  }
		  
	 	   return topGames;
	}
	
	/**
	 * Get N recent games
	 * 
	 * @param gloonDatastore
	 * @return
	 */	
	private List<Game> getRecentGames(int limit)	
	{
		if(limit >0)
		{
			return gloonDatastore.createQuery(Game.class).limit(limit).order("-releaseDate").asList();
		}
		else
		{
			return gloonDatastore.createQuery(Game.class).order("-releaseDate").asList();
		}
		
	}
	
	@Override
	public void save(Game game) {
		gloonDatastore.save(game);
	}
	
	@Override
	public Game findByTitle(String title) {		
		return gloonDatastore.find(Game.class, "title", title).get();
	}
	
	/**
	 * Get N recently released games
	 * 
	 * @param gloonDatastore
	 * @return
	 */
	public List<Game> getRecentReleasedGames(int limit)
	{
		if(limit>0)
		{
			return gloonDatastore.createQuery(Game.class).filter("releaseDate <", Utility.convertDateToStringWithoutTime(new Date())).limit(limit).order("-releaseDate").asList();
		}
		else
		{
			return gloonDatastore.createQuery(Game.class).filter("releaseDate <", Utility.convertDateToStringWithoutTime(new Date())).order("-releaseDate").asList();
		}
	  	
	}

	

	
}
