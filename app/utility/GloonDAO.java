package utility;

import java.net.UnknownHostException;

import models.Article;
import models.Game;
import models.Platform;
import models.User;

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
		return gloonDatastore;
	}
	
	
	

}
