package com.gamealoon.database.daos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.ActivityInterface;
import com.gamealoon.models.Achievement;
import com.gamealoon.models.Activity;
import com.gamealoon.models.Article;
import com.gamealoon.models.User;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

public class ActivityDAO extends GloonDAO implements ActivityInterface {
	
	private static final ActivityDAO DATA_ACCESS_LAYER=new ActivityDAO();	
	private Datastore gloonDatastore=null;
		
		private ActivityDAO()
		{
			super();
			gloonDatastore=initDatastore();
		}
		
		/**
		 * Singleton way to instantiate Gloon DAO
		 * @return
		 */
		public static ActivityDAO instantiateDAO()
		{								
			return DATA_ACCESS_LAYER;
		}

	@Override
	public void save(Activity activity) {
 
		 gloonDatastore.save(activity);
		
	}
	
	/**
	 * Create and save new article
	 * 
	 * @param type
	 * @param userId
	 * @param entityId
	 * @param visbility
	 * @param insertTime
	 */
	public void create(Integer type, String userId, String entityId, Integer visbility, String insertTime)
	{		
		Activity activity = new Activity();
		activity.setType(type);
		activity.setUserId(userId);
		activity.setEntityId(entityId);
		activity.setVisibility(visbility);		
		activity.setInsertTime(insertTime);
		save(activity);
		
	}
	
	/**
	 * Get Activities for one single User. We can fetch the recent activities also if no userId available.
	 * 
	 * @param userId
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getActivities(User user)
	{
		ArrayList<HashMap<String,Object>> activites = new ArrayList<>();
		List<Activity> allActivites = new ArrayList<>();
		String userName="";
		if(user == null)
		{
			allActivites = gloonDatastore.createQuery(Activity.class).filter("visibility", AppConstants.PUBLIC).order("-insertTime").asList();
		}
		else{
			userName=user.getUsername();
			String userId= user.getId().toString();
			allActivites = gloonDatastore.createQuery(Activity.class).filter("userId", userId).order("-insertTime").asList();
			Set<User> allUsersInCurrentUserCircle = new HashSet<>();			
			allUsersInCurrentUserCircle.addAll(user.getFollowedBy());
			allUsersInCurrentUserCircle.addAll(user.getFollowing());
			
			for(User setUser: allUsersInCurrentUserCircle)
			{
				List<Activity> circleUserActivites = gloonDatastore.createQuery(Activity.class).filter("userId", setUser.getId().toString()).filter("visibility", AppConstants.PUBLIC).order("-insertTime").asList();
				allActivites.addAll(circleUserActivites);
			}		
			
			Collections.sort(allActivites, new Comparator<Activity>() {

				@Override
				public int compare(Activity o1, Activity o2) {
					// TODO Auto-generated method stub
					return o2.getInsertTime().compareTo(o1.getInsertTime());
				}
			});
		}
		
		for(Activity activity: allActivites)
		{
			HashMap<String, Object> activityMap = new HashMap<>();
			int activityType = activity.getType();
			User acitvityUser =gloonDatastore.createQuery(User.class).filter("_id", new ObjectId(activity.getUserId())).get();
			switch(activityType)
			{
			case Activity.ACTIVITY_POST_PUBLISH:
				activityMap.put("message", " published a new Article, ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", acitvityUser.getUsername());
				Article article= gloonDatastore.createQuery(Article.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("articleTitle", article.getTitle());				 
				activityMap.put("articleEncodedUrl", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				activityMap.put("activityType", activityType);				
				break;
				
			case Activity.ACTIVITY_USER_FOLLOWS:
				activityMap.put("message", " now following ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", acitvityUser.getUsername());
				User userFollowing= gloonDatastore.createQuery(User.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("followingUserName", userFollowing.getUsername());				
				activityMap.put("activityType", activityType);
				break;					
				
			case Activity.ACTIVITY_POST_COOL:
				activityMap.put("message", " found article cool ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", acitvityUser.getUsername());
				Article coolArticle= gloonDatastore.createQuery(Article.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("articleTitle", coolArticle.getTitle());
				activityMap.put("articleEncodedUrl", Utility.encodeForUrl(coolArticle.getTitle())+"-"+coolArticle.getId().toString());
				activityMap.put("activityType", activityType);
				break;
				
			case Activity.ACTIVITY_POST_COMMENT:
				activityMap.put("message", " posted a new comment for ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", acitvityUser.getUsername());
				activityMap.put("activityType", activityType);
				break;
			
			case Activity.ACTIVITY_NEW_DISCUSSION:
				activityMap.put("message", " started a new discussion ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", acitvityUser.getUsername());
				activityMap.put("activityType", activityType);
				break;
				
			case Activity.ACTIVITY_JOIN_DISCUSSION:
				activityMap.put("message", " joined discussion ");
				activityMap.put("activityOwnerUserName",userName);
				activityMap.put("activityUserName", acitvityUser.getUsername());
				activityMap.put("activityType", activityType);
				break;
				
			case Activity.ACTIVITY_NEW_ACHIEVMENT:
				activityMap.put("message", " earned new achievement ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", acitvityUser.getUsername());
				Achievement achievement = gloonDatastore.createQuery(Achievement.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("achievementTitle", achievement.getTitle());
				activityMap.put("activityType", activityType);
				break;
			}
			activites.add(activityMap);
		}
		return activites;
	}

}
