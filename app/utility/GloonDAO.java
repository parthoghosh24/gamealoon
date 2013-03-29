package utility;

import java.net.UnknownHostException;

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
	
	private GloonDAO()
	{
	 
		/**
		 * Constructor logic
		 * ->Instantiate mongo
		 * -> create gloonDatastore
		 */
		try {
			mongoInstance = GloonDatabase.instantiate();		
			
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
	 * 
	 * @return gloonDatastore
	 */
	public Datastore initDatastore(Morphia gloonMorphiaInstance)
	{		
		gloonDatastore = gloonMorphiaInstance.createDatastore(mongoInstance, GloonDataInterface.DB_NAME);
		return gloonDatastore;
	}
	
	
	

}
