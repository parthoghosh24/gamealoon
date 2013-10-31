package com.gamealoon.database.daos;

import java.util.Date;
import java.util.HashMap;
import org.bson.types.ObjectId;
import play.data.DynamicForm;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.AchievementInterface;
import com.gamealoon.models.Achievement;
import com.gamealoon.utility.Utility;
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
	
	@Override
	public HashMap<String, String> createOrUpdateAchievement(DynamicForm requestData) {
		HashMap<String, String> response = new HashMap<>();
		response.put("status", "fail");
		Achievement achievement = createOrUpdateAchievementInstance(requestData);
		if(achievement!=null)
		{
			save(achievement);
			response.put("status", "success");
		}
		return response;
	}
	
	@Override
	public Achievement getById(String id) {		
		return gloonDatastore.get(Achievement.class, new ObjectId(id));
	}
	
	/**
	 * Create or update achievement
	 * 
	 * @param requestData
	 * @return
	 */
	private Achievement createOrUpdateAchievementInstance(DynamicForm requestData) {
		String id = requestData.get("id");
		String title = requestData.get("title");
		String description=requestData.get("description");
		String achievementImage = requestData.get("achievementImage");
		Achievement achievement = null;
		Date time = new Date();
		if(id.isEmpty())
		{
			achievement = new Achievement();
			achievement.setInsertTime(Utility.convertDateToString(time));
			achievement.setTimestamp(time.getTime());
			achievement.setAchievementImage(achievementImage);
		}
		else
		{
			achievement = getById(id);
			achievement.setUpdateTime(Utility.convertDateToString(time));
		}
		achievement.setTitle(title);
		achievement.setDescription(description);
		achievement.setAchievementImage(achievementImage);
		return achievement;
	}

	/**
	 * Utility method to found all User Count
	 * 
	 * @return
	 */
	public Long allAchievementCount()
	{		
		return gloonDatastore.createQuery(Achievement.class).countAll();
				
	}

	

	

}
