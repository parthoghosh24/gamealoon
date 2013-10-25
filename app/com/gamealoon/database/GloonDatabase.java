package com.gamealoon.database;

import java.net.UnknownHostException;

import play.Logger;

import com.gamealoon.models.Article;
import com.gamealoon.models.Game;
import com.gamealoon.models.Platform;
import com.gamealoon.models.User;
import com.gamealoon.utility.AppConstants;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.typesafe.config.ConfigFactory;
import play.Play;
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
	private Mongo result = null;
	private GloonDatabase()
	{
		String host = ConfigFactory.load().getString("mongo.host"); 
		int port = (int) Long.parseLong(ConfigFactory.load().getString("mongo.port"));
		try {			
			result = new Mongo(host,port);
			Morphia gloonMorphiaInstance = new Morphia();
			gloonMorphiaInstance.map(User.class).map(Article.class).map(Game.class).map(Platform.class);
			Logger.debug("IS PLAY IN DEV "+Play.isDev());
			Logger.debug("IS PLAY IN POD "+Play.isProd());
			if(Play.isDev())
			{
				gloonDatastoreInstance = gloonMorphiaInstance.createDatastore(result, AppConstants.DB_NAME_DEV);
			}
			if(Play.isTest())
			{
				gloonDatastoreInstance = gloonMorphiaInstance.createDatastore(result, AppConstants.DB_NAME_TEST);
			}
			if(Play.isProd())
			{
				gloonDatastoreInstance = gloonMorphiaInstance.createDatastore(result, AppConstants.DB_NAME_PROD,"gloonAdmin","gamealoon@2013".toCharArray());
			}
			gloonDatastoreInstance.ensureIndexes();			
		} catch (UnknownHostException e) {
			Logger.debug("Some error happened while connection with database:"+e.fillInStackTrace());
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
	
	public Mongo getMongoInstance()
	{
		return result;
	}
}
