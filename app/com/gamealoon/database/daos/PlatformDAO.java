package com.gamealoon.database.daos;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.PlatformInterface;
import com.gamealoon.models.Platform;
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

}
