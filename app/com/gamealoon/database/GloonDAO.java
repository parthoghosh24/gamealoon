package com.gamealoon.database;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;


import com.gamealoon.models.Article;
import com.gamealoon.models.Game;
import com.gamealoon.models.Platform;
import com.gamealoon.models.User;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

/**
 * This is the application data access layer.
 * All DB initiation methods, connection activation and queries will
 * be performed over here.
 * 
 * @author Partho
 *
 */
public class GloonDAO implements GloonDataInterface{
	
	static GloonDAO dataAccessLayer=null;
	private static Mongo mongoInstance=null;
	private Datastore gloonDatastore=null;
	private Morphia gloonMorphiaInstance=null;
	private GloonDAO()
	{
	 
		/**
		 * Constructor logic
		 * ->Instantiate mongo
		 * -> Instantiate morphia
		 */
		try {
			mongoInstance = GloonDatabase.instantiate();		
			gloonMorphiaInstance = new Morphia();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Singleton way to instantiate Gloon DAO
	 * @return
	 */
	public static synchronized GloonDAO instantiateDAO()
	{
		
		
		if(dataAccessLayer == null)
		{			
			dataAccessLayer= new GloonDAO();
		}
		return dataAccessLayer;
	}
	
	

	
	/**
	 * @param gloonMorphiaInstance is the morphia instance required for  datastore creation
	 * morphia instances mapped with classes and datastore intantiated
	 * @return gloonDatastore
	 */
	public Datastore initDatastore()
	{		
		gloonMorphiaInstance.map(User.class).map(Article.class).map(Game.class).map(Platform.class);
		gloonDatastore = gloonMorphiaInstance.createDatastore(mongoInstance, GloonDataInterface.DB_NAME);	
		gloonDatastore.ensureIndexes();		
		return gloonDatastore;
	}

	@Override
	public List<Game> allGames(Datastore gloonDatastore) {
		List<Game> games = gloonDatastore.find(Game.class).asList();
		if(games.size()>0)
		{
			return games;
		}
		else
		{
			throw new RuntimeException("No games available"); 
		}
		
	}

	/**
	 * This method returns 25 Articles to be precise of top users belonging to all categories, i.e, review, preview, etc.
	 * It is somelike following(recent articles):
	 * 
	 * top user 1-> 1 rev, 1 prev, 1 news, 1 feat, 1 gloon
	 * top user 2-> 1 rev, 1 prev, 1 news, 1 feat, 1 gloon
	 * 
	 */
	@Override
	public List<HashMap<String, Object>> getAllArticlesForCarousel(Datastore gloonDatastore,String type) {
	
		List<User> topUsers= getTopUsers(); 
		return null;
	}
	
	
	private List<User> getTopUsers()
	{
	  List<User> topUsers=null;
	  return topUsers;
	}
	
	

}
