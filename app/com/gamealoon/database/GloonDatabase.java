package com.gamealoon.database;

import java.net.UnknownHostException;
import play.Logger;
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
	private final String DB_NAME=ConfigFactory.load().getString("gloon.database.name");
	private final String DB_USERNAME=ConfigFactory.load().getString("gloon.database.username");
	private final String DB_PASSWORD=ConfigFactory.load().getString("gloon.database.password");
	private Mongo result = null;
	private GloonDatabase()
	{
		Logger.debug("IN GLOON DATABSE....");
		String host = ConfigFactory.load().getString("mongo.host"); 
		int port = (int) Long.parseLong(ConfigFactory.load().getString("mongo.port"));
		try {			
			result = new Mongo(host,port);
			Logger.debug("MONGO INSTANCE "+result);
			Morphia gloonMorphiaInstance = new Morphia();
			Logger.debug("GLOON MORPHIA INSTANCE "+gloonMorphiaInstance);		
			Logger.debug("DB NAME"+ConfigFactory.load().getString("gloon.database.name"));
 			Logger.debug("DB "+ DB_NAME);
			Logger.debug("username "+ DB_USERNAME);
			Logger.debug("password "+ DB_PASSWORD);
			gloonDatastoreInstance = gloonMorphiaInstance.createDatastore(result, DB_NAME,DB_USERNAME,DB_PASSWORD.toCharArray());
			Logger.debug("GLOON DATASTORE INSTANCE "+gloonDatastoreInstance);
			
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
