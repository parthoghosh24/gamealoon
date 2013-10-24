package com.gamealoon.database.daos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bson.types.ObjectId;
import play.Logger;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.ActivityInterface;
import com.gamealoon.models.Achievement;
import com.gamealoon.models.Activity;
import com.gamealoon.models.Article;
import com.gamealoon.models.Buddy;
import com.gamealoon.models.Conversation;
import com.gamealoon.models.Game;
import com.gamealoon.models.User;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

public class ActivityDAO extends GloonDAO implements ActivityInterface {
	
	private static final ActivityDAO DATA_ACCESS_LAYER=new ActivityDAO();	
	private static final ArticleDAO articleDAO = ArticleDAO.instantiateDAO();
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
	
	
	public void create(Integer type, String username, String entityId, Integer visbility, String insertTime, Long timestamp)
	{		
		Activity activity = new Activity();
		activity.setType(type);
		activity.setUsername(username);
		activity.setEntityId(entityId);
		activity.setVisibility(visbility);		
		activity.setInsertTime(insertTime);
		activity.setTimestamp(timestamp);
		save(activity);
		
	}
	
	@Override
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
			allActivites = gloonDatastore.createQuery(Activity.class).filter("username", user.getUsername()).order("-insertTime").asList();
			Set<Buddy> allUsersInCurrentUserCircle = new HashSet<>();			
			allUsersInCurrentUserCircle.addAll(user.getFollowedBy());
			allUsersInCurrentUserCircle.addAll(user.getFollowing());
			
			for(Buddy setUser: allUsersInCurrentUserCircle)
			{
				List<Activity> circleUserActivites = gloonDatastore.createQuery(Activity.class).filter("username", setUser.getUserName()).filter("visibility", AppConstants.PUBLIC).order("-insertTime").asList();
				allActivites.addAll(circleUserActivites);
			}		
			
			Collections.sort(allActivites, new Comparator<Activity>() {

				@Override
				public int compare(Activity o1, Activity o2) {
					// TODO Auto-generated method stub
					return o2.getTimestamp().compareTo(o1.getTimestamp());
				}
			});
		}	
		activites=getActivityMaps(allActivites, userName);
		return activites;
	}

	@Override
	public ArrayList<HashMap<String, Object>> getPublicActivitiesForUser(User user) {		
		ArrayList<HashMap<String,Object>> activites = new ArrayList<>();
		List<Activity> allActivites = gloonDatastore.createQuery(Activity.class).filter("username", user.getUsername()).filter("visibility", AppConstants.PUBLIC).order("-insertTime").limit(10).asList();
		activites = getActivityMaps(allActivites, user.getUsername());
		return activites;
	}
	
	@Override
	public ArrayList<HashMap<String, Object>> getActivitiesForGame(Game game) {
		ArrayList<HashMap<String,Object>> activites = new ArrayList<>();
		List<Article> allGameArticles = articleDAO.findAllPublishedArticlesByGame(game.getId().toString(),"all");		
		Logger.debug("ALL ARTICLES size: "+allGameArticles.size());
		List<Activity> allActivites= new ArrayList<>();
		Logger.debug("GAME ID "+game.getId().toString());
		for(Article article: allGameArticles)
		{
			Logger.debug("ARTICLE ID: "+article.getId().toString());
			List<Activity> tempActivities= gloonDatastore.createQuery(Activity.class).filter("entityId", article.getId().toString()).filter("visibility", AppConstants.PUBLIC).order("-insertTime").limit(10).asList();
			Logger.debug("Temp activities size   "+tempActivities.size());
			if(tempActivities.size()>0)
			{
				allActivites.addAll(tempActivities);
			}			
		}		
		Logger.debug(allActivites.toString());
		List<Activity> gameActivities= gloonDatastore.createQuery(Activity.class).filter("entityId", game.getId().toString()).filter("visibility", AppConstants.PUBLIC).order("-insertTime").limit(10).asList();
		Logger.debug("All activities size: "+allActivites.size());
		if(gameActivities.size()>0)
		{
			allActivites.addAll(gameActivities);
		}						
		activites = getGameActivityMaps(allActivites, game);
		return activites;
	}
	
	/**
	 * Bake Raw activities into hashmaps for users
	 * 
	 * @param activityList
	 * @param userName
	 * @return
	 */
	private ArrayList<HashMap<String, Object>> getActivityMaps(List<Activity> activityList, String userName)
	{
		ArrayList<HashMap<String,Object>> activites = new ArrayList<>();
		for(Activity activity: activityList)
		{
			HashMap<String, Object> activityMap = new HashMap<>();
			int activityType = activity.getType();
			User activityUser =gloonDatastore.createQuery(User.class).filter("username", activity.getUsername()).get();
			switch(activityType)
			{
			case Activity.ACTIVITY_POST_PUBLISH:
				Article article= gloonDatastore.createQuery(Article.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("message", " published a new "+article.getCategory()+", ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", activityUser.getUsername());				
				activityMap.put("articleTitle", article.getTitle());				 				
				activityMap.put("articleEncodedUrl", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
				activityMap.put("activityType", activityType);				
				break;
				
			case Activity.ACTIVITY_USER_FOLLOWS:
				activityMap.put("message", " now following ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", activityUser.getUsername());
				User userFollowing= gloonDatastore.createQuery(User.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("followingUserName", userFollowing.getUsername());				
				activityMap.put("activityType", activityType);
				break;					
				
			case Activity.ACTIVITY_POST_COOL:
				activityMap.put("message", " found article yeah ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", activityUser.getUsername());
				Article coolArticle= gloonDatastore.get(Article.class, new ObjectId(activity.getEntityId()));
				activityMap.put("articleTitle", coolArticle.getTitle());
				activityMap.put("articleEncodedUrl", Utility.encodeForUrl(coolArticle.getTitle())+"-"+coolArticle.getId().toString());
				activityMap.put("activityType", activityType);
				break;
				
			case Activity.ACTIVITY_POST_COMMENT:
				activityMap.put("message", " posted a new comment for ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", activityUser.getUsername());
				activityMap.put("activityType", activityType);
				Conversation conversation = gloonDatastore.get(Conversation.class, new ObjectId(activity.getEntityId()));				
				String commentMessage= conversation.getMessage();
				if(commentMessage.length()>10)
				{
					activityMap.put("activityComment", commentMessage.substring(0, 10));
				}
				else
				{
					activityMap.put("activityComment", commentMessage);
				}	
				
				Article commentArticle= gloonDatastore.createQuery(Article.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("articleTitle", commentArticle.getTitle());
				activityMap.put("articleEncodedUrl", Utility.encodeForUrl(commentArticle.getTitle())+"-"+commentArticle.getId().toString());
				activityMap.put("activityType", activityType);				
				break;
			
			case Activity.ACTIVITY_NEW_DISCUSSION:
				activityMap.put("message", " started a new discussion ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", activityUser.getUsername());
				activityMap.put("activityType", activityType);
				break;
				
			case Activity.ACTIVITY_JOIN_DISCUSSION:
				activityMap.put("message", " joined discussion ");
				activityMap.put("activityOwnerUserName",userName);
				activityMap.put("activityUserName", activityUser.getUsername());
				activityMap.put("activityType", activityType);
				break;
				
			case Activity.ACTIVITY_NEW_ACHIEVMENT:
				activityMap.put("message", " earned new achievement ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", activityUser.getUsername());
				Achievement achievement = gloonDatastore.createQuery(Achievement.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("achievementTitle", achievement.getTitle());
				activityMap.put("activityType", activityType);
				break;
			
			case Activity.ACTIVITY_USER_UNFOLLOWS:
				activityMap.put("message", " no more following ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", activityUser.getUsername());
				User userNotFollowing= gloonDatastore.createQuery(User.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("notFollowingUserName", userNotFollowing.getUsername());				
				activityMap.put("activityType", activityType);
				break;
				
			case Activity.ACTIVITY_BLOCK:
				activityMap.put("message", " successfully blocked ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", activityUser.getUsername());
				User blockedUser= gloonDatastore.createQuery(User.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("notFollowingUserName", blockedUser.getUsername());				
				activityMap.put("activityType", activityType);
				break;
				
			case Activity.ACTIVITY_UNBLOCK:
				activityMap.put("message", "successfully unblocked ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", activityUser.getUsername());
				User unblockedUser= gloonDatastore.createQuery(User.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("notFollowingUserName", unblockedUser.getUsername());				
				activityMap.put("activityType", activityType);
				break;
				
			case Activity.ACTIVITY_FOLLOW_GAME:
				activityMap.put("message", " interested in ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", activityUser.getUsername());
				Game followedGame= gloonDatastore.createQuery(Game.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("followingGame", followedGame.getTitle());				
				activityMap.put("activityType", activityType);
				activityMap.put("gameEncodedUrl", Utility.encodeForUrl(followedGame.getTitle())+"-"+followedGame.getId().toString());
				break;
				
			case Activity.ACTIVITY_UNFOLLOW_GAME:
				activityMap.put("message", " no more interested in ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", activityUser.getUsername());
				Game unfollowedGame= gloonDatastore.createQuery(Game.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("notFollowingGame", unfollowedGame.getTitle());				
				activityMap.put("activityType", activityType);
				activityMap.put("gameEncodedUrl", Utility.encodeForUrl(unfollowedGame.getTitle())+"-"+unfollowedGame.getId().toString());
				break;	
			case Activity.ACTIVITY_POST_NOT_COOL:
				activityMap.put("message", " found article meh ");
				activityMap.put("activityOwnerUserName", userName);
				activityMap.put("activityUserName", activityUser.getUsername());
				Article notCoolArticle= gloonDatastore.createQuery(Article.class).filter("_id", new ObjectId(activity.getEntityId())).get();
				activityMap.put("articleTitle", notCoolArticle.getTitle());
				activityMap.put("articleEncodedUrl", Utility.encodeForUrl(notCoolArticle.getTitle())+"-"+notCoolArticle.getId().toString());
				activityMap.put("activityType", activityType);
				break;	
			}
			activites.add(activityMap);
		}
		return activites;
	}

	private ArrayList<HashMap<String, Object>> getGameActivityMaps(List<Activity> activityList, Game game)
	{
		ArrayList<HashMap<String,Object>> activites = new ArrayList<>();
		for(Activity activity: activityList)
		{
			HashMap<String, Object> gameActivityMap = new HashMap<>();
			int activityType = activity.getType();			
			User activityUser =gloonDatastore.createQuery(User.class).filter("username",activity.getUsername()).get();
			Article article= gloonDatastore.createQuery(Article.class).filter("_id", new ObjectId(activity.getEntityId())).get();	
			switch(activityType)
			{
			
				case Activity.ACTIVITY_FOLLOW_GAME:
					gameActivityMap.put("message", " is interested in ");					
					gameActivityMap.put("activityUserName", activityUser.getUsername());					
					gameActivityMap.put("followingGame", game.getTitle());				
					gameActivityMap.put("activityType", activityType);
					gameActivityMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle())+"-"+game.getId().toString());
					break;
				
				case Activity.ACTIVITY_POST_PUBLISH:
					gameActivityMap.put("message", " published a new "+article.getCategory()+" for ");			
					gameActivityMap.put("activityUserName", activityUser.getUsername());				
					gameActivityMap.put("articleTitle", article.getTitle());				 				
					gameActivityMap.put("articleEncodedUrl", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
					gameActivityMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle())+"-"+game.getId().toString());
					gameActivityMap.put("articleGame", game.getTitle());
					gameActivityMap.put("activityType", activityType);	
					break;							
				case Activity.ACTIVITY_POST_COOL:
					gameActivityMap.put("message", " found article yeah ");					
					gameActivityMap.put("activityUserName", activityUser.getUsername());
					Article coolArticle= gloonDatastore.createQuery(Article.class).filter("_id", new ObjectId(activity.getEntityId())).get();
					gameActivityMap.put("articleTitle", coolArticle.getTitle());
					gameActivityMap.put("articleEncodedUrl", Utility.encodeForUrl(coolArticle.getTitle())+"-"+coolArticle.getId().toString());
					gameActivityMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle())+"-"+game.getId().toString());
					gameActivityMap.put("articleGame", game.getTitle());
					gameActivityMap.put("activityType", activityType);
					break;				
											
			}
			activites.add(gameActivityMap);
		}		
		return activites;
	}

}
