package com.gamealoon.database.daos;

import java.util.Date;
import java.util.HashMap;
import org.bson.types.ObjectId;
import play.data.DynamicForm;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.PlatformInterface;
import com.gamealoon.models.Platform;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

public class PlatformDAO extends GloonDAO implements PlatformInterface{

	private static final PlatformDAO DATA_ACCESS_LAYER=new PlatformDAO();	
	private Datastore gloonDatastore=null;
		
		private PlatformDAO()
		{
			super();
			gloonDatastore=initDatastore();
		}
		
		/**
		 * Singleton way to instantiate Gloon DAO
		 * @return
		 */
		public static PlatformDAO instantiateDAO()
		{								
			return DATA_ACCESS_LAYER;
		}
		
	@Override
	public void save(Platform platform) {
		gloonDatastore.save(platform);
		
	}

	@Override
	public Platform findByTitle(String title) {
		return gloonDatastore.find(Platform.class, "title", title).get();
	}

	@Override
	public Platform findByShortTitle(String shortTitle) {		
		return gloonDatastore.find(Platform.class, "shortTitle", shortTitle).get();
	}
	
	

	@Override
	public HashMap<String, String> createOrUpdatePlatform(DynamicForm requestData) {
		HashMap<String, String> response = new HashMap<>();
		response.put("status", "fail");
		Platform platform = createOrUpdatePlatformInstance(requestData);
		if(platform!=null)
		{
			save(platform);
			response.put("status", "success");
		}
		return response;
	}
	
	/**
	 * Create or update a platform instance
	 * 
	 * @param requestData
	 * @return
	 */

	private Platform createOrUpdatePlatformInstance(DynamicForm requestData) {
		String id = requestData.get("id");
		String title = requestData.get("title");
		String shortTitle = requestData.get("shortTitle");
		String description = requestData.get("description");
		String manufacturer = requestData.get("manufacturer");
		String developer = requestData.get("developer");
		Platform platform = null;
		Date time = new Date();
		if(id.isEmpty())
		{
			platform = new Platform();
			platform.setInsertTime(Utility.convertDateToString(time));
			platform.setTimestamp(time.getTime());
		}
		else
		{
			platform=getById(id);
			platform.setUpdateTime(Utility.convertDateToString(time));
		}
		platform.setTitle(title);
		platform.setShortTitle(shortTitle);
		platform.setDescription(description);
		platform.setManufacturer(manufacturer);
		platform.setDeveloper(developer);
		return platform;
	}

	@Override
	public Platform getById(String id) {		
		return gloonDatastore.get(Platform.class,new ObjectId(id));
	}

}
