package com.gamealoon.database.daos;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.AchievementInterface;
import com.gamealoon.models.Achievement;
import com.google.code.morphia.Datastore;

public class AchievementDAO extends GloonDAO implements AchievementInterface {

	
	private static final AchievementDAO DATA_ACCESS_LAYER=new AchievementDAO();	
	private Datastore gloonDatastore=null;
	
	private AchievementDAO()
	{
		super();		
		gloonDatastore=initDatastore();
	}
	
	/**
	 * Singleton way to instantiate Gloon DAO
	 * @return
	 */
	public static AchievementDAO instantiateDAO()
	{								
		return DATA_ACCESS_LAYER;
	}
	
	@Override
	public void save(Achievement achievement) {
		gloonDatastore.save(achievement);
		
	}

	@Override
	public Achievement findByTitle(String title) {		
		return gloonDatastore.find(Achievement.class, "title", title).get();
	}

}
