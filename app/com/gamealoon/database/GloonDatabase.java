package com.gamealoon.database;

import java.net.UnknownHostException;

import com.gamealoon.models.Article;
import com.gamealoon.models.Game;
import com.gamealoon.models.Platform;
import com.gamealoon.models.User;
import com.gamealoon.utility.AppConstants;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.typesafe.config.ConfigFactory;

/**
 * 
 * This initiates the Mongo DB connection. This is a singleton class 
 * which initiates a MongoDb connection and sets up Datastore after mapping classes and ensuring indices.
 * Method updated-> all db initiation now take place in this class.
 * @author Partho
 *
 */
public class GloonDatabase {

	private static final GloonDatabase INSTANCE =new GloonDatabase();	
	private  Datastore gloonDatastoreInstance =null;
	
	private GloonDatabase()
	{
		String host = ConfigFactory.load().getString("mongo.host"); 
		int port = (int) Long.parseLong(ConfigFactory.load().getString("mongo.port"));
		try {			
			Mongo result = new Mongo(host,port);
			Morphia gloonMorphiaInstance = new Morphia();
			gloonMorphiaInstance.map(User.class).map(Article.class).map(Game.class).map(Platform.class);
			gloonDatastoreInstance = gloonMorphiaInstance.createDatastore(result, AppConstants.DB_NAME);
			gloonDatastoreInstance.ensureIndexes();			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public static GloonDatabase instantiate()
	{
		return INSTANCE;
	}
	
	public Datastore gloonDatastoreInstance()
	{		
		return gloonDatastoreInstance;
	}
	
}
