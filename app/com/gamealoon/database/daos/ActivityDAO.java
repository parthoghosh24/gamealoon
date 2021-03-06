package com.gamealoon.database.daos;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.gamealoon.models.InterestedGame;
import com.gamealoon.models.Media;
import com.gamealoon.models.User;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.StringUtil;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

public class ActivityDAO extends GloonDAO<Activity> implements ActivityInterface {

	private static final ActivityDAO DATA_ACCESS_LAYER = new ActivityDAO();
	private static final ArticleDAO articleDAO = ArticleDAO.instantiateDAO();
	private static final MediaDAO mediaDaoInstance = MediaDAO.instantiateDAO();
	private Datastore gloonDatastore = null;

	private ActivityDAO() {
		super();
		gloonDatastore = initDatastore();
	}

	/**
	 * Singleton way to instantiate Gloon DAO
	 * 
	 * @return
	 */
	public static ActivityDAO instantiateDAO() {
		return DATA_ACCESS_LAYER;
	}

	@Override
	public HashMap<String, String> createOrUpdateActivity(final HashMap<String, String> activityMap) {
		final HashMap<String, String> response = new HashMap<>();
		response.put("status", "fail");
		final Activity activity = createOrUpdateActivityInstance(activityMap);
		if (activity != null) {
			save(activity);
			response.put("status", "success");
		}
		return response;

	}

	/**
	 * Create or update activity instance
	 * 
	 * @param activityMap
	 * @return
	 */
	private Activity createOrUpdateActivityInstance(final HashMap<String, String> activityMap) {
		final String username = activityMap.get("username");
		final String entityId = activityMap.get("entityId");
		final int type = Integer.parseInt(activityMap.get("type"));
		final int visbility = Integer.parseInt(activityMap.get("visibility"));
		final Date time = new Date();
		Activity activity = null;
		if (Activity.ACTIVITY_FOLLOW_GAME == type || Activity.ACTIVITY_UNFOLLOW_GAME == type || Activity.ACTIVITY_USER_FOLLOWS == type
				|| Activity.ACTIVITY_USER_UNFOLLOWS == type || Activity.ACTIVITY_POST_COOL == type
				|| Activity.ACTIVITY_POST_NOT_COOL == type) {
			activity = getActivityByUserNameAndEntityIdAndType(username, entityId, type);
			if (activity != null) {
				activity.setUpdateTime(Utility.convertDateToString(time));
			} else {
				activity = new Activity();
			}

		} else {
			activity = new Activity();
		}
		activity.setTimestamp(time.getTime());
		activity.setInsertTime(Utility.convertDateToString(time));
		activity.setType(type);
		activity.setUsername(username);
		activity.setEntityId(entityId);
		activity.setVisibility(visbility);
		return activity;
	}

	/**
	 * Fetch activity by Username, EntityId and Type
	 * 
	 * @return
	 */
	private Activity getActivityByUserNameAndEntityIdAndType(final String username, final String entityId, final int type) {
		Activity activity = null;
		activity = gloonDatastore.createQuery(Activity.class).filter("username", username).filter("entityId", entityId)
				.filter("type", type).get();
		return activity;
	}

	@Override
	public ArrayList<HashMap<String, Object>> getActivities(final User user) {
		ArrayList<HashMap<String, Object>> activites = new ArrayList<>();
		List<Activity> allActivites = new ArrayList<>();
		String userName = "";
		if (user == null) {
			allActivites = gloonDatastore.createQuery(Activity.class).filter("visibility", Activity.PUBLIC).order("-insertTime")
					.asList();
		} else {
			userName = user.getUsername();
			allActivites = gloonDatastore.createQuery(Activity.class).filter("username", user.getUsername()).order("-insertTime")
					.asList();

			List<String> circleUsers = new ArrayList<>(); 
			for (final Buddy followingUser : user.getFollowing()) {				
				Logger.debug("Set User: " + followingUser.getUserName());
				circleUsers.add(followingUser.getUserName());				
			}
			Logger.debug("circleUsers: "+ circleUsers);
			List<String> circleGames = new ArrayList<>();
			for(final InterestedGame game: user.getFollowingGames())
			{
				Logger.debug("Set User: " + game.getGameId());
				circleGames.add(game.getGameId());
			}
						
			List<Activity> circleUserActivites= new ArrayList<>();	
			
			if(circleUsers.size()>0)
			{
				circleUserActivites=gloonDatastore.createQuery(Activity.class)
						.filter("username in", circleUsers).filter("visibility", Activity.PUBLIC).order("-insertTime").asList();
			}
			
			
			final List<Activity> gameActivities = fetchGameActivites(circleGames);
			
			allActivites.addAll(circleUserActivites);
			allActivites.addAll(gameActivities);
			
			Collections.sort(allActivites, new Comparator<Activity>() {

				@Override
				public int compare(final Activity o1, final Activity o2) {
					// TODO Auto-generated method stub
					return o2.getTimestamp().compareTo(o1.getTimestamp());
				}
			});
		}
		try {
			activites = getActivityMaps(allActivites, userName);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activites;
	}

	@Override
	public ArrayList<HashMap<String, Object>> getPublicActivitiesForUser(final User user) {
		ArrayList<HashMap<String, Object>> activites = new ArrayList<>();
		final List<Activity> allActivites = gloonDatastore.createQuery(Activity.class).filter("username", user.getUsername())
				.filter("visibility", Activity.PUBLIC).order("-insertTime").limit(10).asList();		
		try {
			activites = getActivityMaps(allActivites, user.getUsername());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activites;
	}

	@Override
	public ArrayList<HashMap<String, Object>> getActivitiesForGame(final Game game) {
		ArrayList<HashMap<String, Object>> activites = new ArrayList<>();
		try {
			activites = getGameActivityMaps(fetchGameActivites(game), game);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activites;
	}
	
	/**
	 * Fetching all Game Activities. PUBLIC ones for a game
	 * 
	 * @param game
	 * @return
	 */
	private List<Activity> fetchGameActivites(final Game game)
	{
		final List<Activity> allActivites = new ArrayList<>();
		final List<Article> allGameArticles = articleDAO.findAllPublishedArticlesByGame(game.getId().toString(), "all");
		Logger.debug("All game articles: "+ allGameArticles.size());
		final List<String> articleIds=new ArrayList<>();
		
		for (final Article article : allGameArticles) {
			Logger.debug("ARTICLE ID: " + article.getId().toString());
			articleIds.add(article.getId().toString());
			
			
		}
		List<Activity> tempActivities= new ArrayList<>();
		if(articleIds.size()>0)
		{
			tempActivities= gloonDatastore.createQuery(Activity.class)
					.filter("entityId in", articleIds).filter("visibility", Activity.PUBLIC).order("-insertTime")
					.limit(10).asList();
			
			Logger.debug("Temp activities size   " + tempActivities.size());
		}
		
		if (tempActivities.size() > 0) {
			allActivites.addAll(tempActivities);
		}
		
		final List<Activity> gameActivities = gloonDatastore.createQuery(Activity.class).filter("entityId", game.getId().toString())
				.filter("visibility", Activity.PUBLIC).order("-insertTime").limit(10).asList();
		
		if (gameActivities.size() > 0) {
			allActivites.addAll(gameActivities);
		}
		
		return allActivites;
		
	}
	
	/**
	 * Fetching all Game Activities. PUBLIC ones for a game
	 * 
	 * @param game
	 * @return
	 */
	private List<Activity> fetchGameActivites(final List<String> gameIds)
	{
		final List<Activity> allActivites = new ArrayList<>();
		
		List<Article> allGameArticles = new ArrayList<>();
		List<Activity> gameActivities = new ArrayList<>();
		if(gameIds.size()>0)
		{
			allGameArticles = articleDAO.findAllPublishedArticlesByGames(gameIds, "all");
			gameActivities = gloonDatastore.createQuery(Activity.class).filter("entityId in", gameIds)
					.filter("visibility", Activity.PUBLIC).order("-insertTime").limit(10).asList();
		}

		final List<String> articleIds=new ArrayList<>();
		
		
		for (final Article article : allGameArticles) {
			Logger.debug("ARTICLE ID: " + article.getId().toString());
			articleIds.add(article.getId().toString());
			
			
		}
		
		List<Activity> tempActivities= new ArrayList<>();
		if(articleIds.size()>0)
		{
			tempActivities = gloonDatastore.createQuery(Activity.class)
					.filter("entityId in", articleIds).filter("visibility", Activity.PUBLIC).order("-insertTime")
					.limit(10).asList();
		}
		
		
		Logger.debug("Temp activities size   " + tempActivities.size());
		if (tempActivities.size() > 0) {
			allActivites.addAll(tempActivities);
		}
		
		
		if (gameActivities.size() > 0) {
			allActivites.addAll(gameActivities);
		}
		
		return allActivites;
		
	}
	

	@Override
	public Activity getById(final String id) {
		return gloonDatastore.get(Activity.class, new ObjectId(id));
	}

	/**
	 * Bake Raw activities into hashmaps for users
	 * 
	 * @param activityList
	 * @param userName
	 * @return
	 * @throws MalformedURLException
	 */
	private ArrayList<HashMap<String, Object>> getActivityMaps(final List<Activity> activityList, final String userName)
			throws MalformedURLException {
		final ArrayList<HashMap<String, Object>> activites = new ArrayList<>();
		for (final Activity activity : activityList) {
			final HashMap<String, Object> activityMap = new HashMap<>();
			final int activityType = activity.getType();
			final User activityUser = gloonDatastore.createQuery(User.class).filter("username", activity.getUsername()).get();
			activityMap.put("activityTimestamp", activity.getTimestamp());
			switch (activityType) {
				case Activity.ACTIVITY_POST_PUBLISH:
					Media media=null;
					final Article article = gloonDatastore.createQuery(Article.class)
							.filter("_id", new ObjectId(activity.getEntityId())).get();
					activityMap.put("articleCategory", article.getCategory().toString());
					String featuredImage = article.getFeaturedImage();
					if (featuredImage.isEmpty() || "default".equalsIgnoreCase(featuredImage)) {
						activityMap.put("articleFeaturedImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/featuredBg.png");
					} else {
						media = mediaDaoInstance.getById(featuredImage);
						activityMap.put("articleFeaturedImage", media.getUrl());
					}
					activityMap.put("activityOwnerUserName", userName);
					activityMap.put("activityUserName", activityUser.getUsername());					
					String userAvatar = activityUser.getAvatar();
					if (!StringUtil.isNullOrEmpty(userAvatar)) {
						media = mediaDaoInstance.getById(userAvatar);
						activityMap.put("activityUserAvatar", media.getUrl());
					} else {
						activityMap.put("activityUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}					
					activityMap.put("articleTitle", article.getTitle());
					activityMap.put("articleEncodedUrl", Utility.encodeForUrl(article.getTitle()) + "-" + article.getId().toString());
					activityMap.put("activityType", activityType);
					break;

				case Activity.ACTIVITY_USER_FOLLOWS:
					activityMap.put("activityOwnerUserName", userName);
					activityMap.put("activityUserName", activityUser.getUsername());
					String avatar = activityUser.getAvatar();
					if (!StringUtil.isNullOrEmpty(avatar)) {
						media = mediaDaoInstance.getById(avatar);
						activityMap.put("activityUserAvatar", media.getUrl());
					} else {
						activityMap.put("activityUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}
					final User userFollowing = gloonDatastore.createQuery(User.class)
							.filter("_id", new ObjectId(activity.getEntityId())).get();
					activityMap.put("followingUserName", userFollowing.getUsername());
					avatar = userFollowing.getAvatar();
					if (!avatar.isEmpty()) {
						media = mediaDaoInstance.getById(avatar);
						activityMap.put("followingUserAvatar", media.getUrl());
					} else {
						activityMap.put("followingUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}
					activityMap.put("activityType", activityType);
					break;

				case Activity.ACTIVITY_POST_COOL:
					activityMap.put("activityOwnerUserName", userName);
					activityMap.put("activityUserName", activityUser.getUsername());
					avatar = activityUser.getAvatar();
					if (!StringUtil.isNullOrEmpty(avatar)) {
						media = mediaDaoInstance.getById(avatar);
						activityMap.put("activityUserAvatar", media.getUrl());
					} else {
						activityMap.put("activityUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}
					final Article coolArticle = gloonDatastore.get(Article.class, new ObjectId(activity.getEntityId()));
					activityMap.put("articleTitle", coolArticle.getTitle());
					activityMap.put("articleEncodedUrl", Utility.encodeForUrl(coolArticle.getTitle()) + "-"
							+ coolArticle.getId().toString());
					featuredImage = coolArticle.getFeaturedImage();
					if (featuredImage.isEmpty() || "default".equalsIgnoreCase(featuredImage)) {
						activityMap.put("articleFeaturedImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/featuredBg.png");
					} else {
						media = mediaDaoInstance.getById(featuredImage);
						activityMap.put("articleFeaturedImage", media.getUrl());
					}
					activityMap.put("activityType", activityType);
					break;

				case Activity.ACTIVITY_POST_COMMENT:
					activityMap.put("activityOwnerUserName", userName);
					activityMap.put("activityUserName", activityUser.getUsername());
					avatar = activityUser.getAvatar();
					if (!StringUtil.isNullOrEmpty(avatar)) {
						media = mediaDaoInstance.getById(avatar);
						activityMap.put("activityUserAvatar", media.getUrl());
					} else {
						activityMap.put("activityUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}
					activityMap.put("activityType", activityType);
					final Conversation conversation = gloonDatastore.get(Conversation.class, new ObjectId(activity.getEntityId()));
					final String commentMessage = conversation.getMessage();
					if (commentMessage.length() > 20) {
						activityMap.put("activityComment", commentMessage.substring(0, 20));
					} else {
						activityMap.put("activityComment", commentMessage);
					}

					final Article commentArticle = gloonDatastore.get(Article.class, new ObjectId(conversation.getComment()
							.getArticleId()));
					activityMap.put("articleTitle", commentArticle.getTitle());
					activityMap.put("articleEncodedUrl", Utility.encodeForUrl(commentArticle.getTitle()) + "-"
							+ commentArticle.getId().toString());
					featuredImage = commentArticle.getFeaturedImage();
					if (featuredImage.isEmpty() || "default".equalsIgnoreCase(featuredImage)) {
						activityMap.put("articleFeaturedImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/featuredBg.png");
					} else {
						media = mediaDaoInstance.getById(featuredImage);
						activityMap.put("articleFeaturedImage", media.getUrl());
					}
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
					activityMap.put("activityOwnerUserName", userName);
					activityMap.put("activityUserName", activityUser.getUsername());
					activityMap.put("activityType", activityType);
					break;

				case Activity.ACTIVITY_NEW_ACHIEVMENT:
					activityMap.put("activityOwnerUserName", userName);
					activityMap.put("activityUserName", activityUser.getUsername());
					avatar = activityUser.getAvatar();
					if (!StringUtil.isNullOrEmpty(avatar)) {
						media = mediaDaoInstance.getById(avatar);
						activityMap.put("activityUserAvatar", media.getUrl());
					} else {
						activityMap.put("activityUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}
					final Achievement achievement = gloonDatastore.createQuery(Achievement.class)
							.filter("_id", new ObjectId(activity.getEntityId())).get();
					activityMap.put("achievementTitle", achievement.getTitle());
					String achievementImage = achievement.getAchievementImage();
					if (!achievementImage.isEmpty()) {			
						media = mediaDaoInstance.getById(achievementImage);
						try {
							activityMap.put("achievementImage", media.getUrl());
						} catch (MalformedURLException e) {
							activityMap.put("achievementImage", "");
							e.printStackTrace();
						}
					}					
					activityMap.put("activityType", activityType);
					break;

				case Activity.ACTIVITY_USER_UNFOLLOWS:
					activityMap.put("activityOwnerUserName", userName);
					activityMap.put("activityUserName", activityUser.getUsername());
					avatar = activityUser.getAvatar();
					if (!StringUtil.isNullOrEmpty(avatar)) {
						media = mediaDaoInstance.getById(avatar);
						activityMap.put("activityUserAvatar", media.getUrl());
					} else {
						activityMap.put("activityUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}
					final User userNotFollowing = gloonDatastore.createQuery(User.class)
							.filter("_id", new ObjectId(activity.getEntityId())).get();
					activityMap.put("notFollowingUserName", userNotFollowing.getUsername());
					avatar = userNotFollowing.getAvatar();
					if (!StringUtil.isNullOrEmpty(avatar)) {
						media = mediaDaoInstance.getById(avatar);
						activityMap.put("notFollowingUserAvatar", media.getUrl());
					} else {
						activityMap.put("notFollowingUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}
					activityMap.put("activityType", activityType);
					break;

				case Activity.ACTIVITY_BLOCK:
					activityMap.put("message", " successfully blocked ");
					activityMap.put("activityOwnerUserName", userName);
					activityMap.put("activityUserName", activityUser.getUsername());
					final User blockedUser = gloonDatastore.createQuery(User.class)
							.filter("_id", new ObjectId(activity.getEntityId())).get();
					activityMap.put("notFollowingUserName", blockedUser.getUsername());
					activityMap.put("activityType", activityType);
					break;

				case Activity.ACTIVITY_UNBLOCK:
					activityMap.put("message", "successfully unblocked ");
					activityMap.put("activityOwnerUserName", userName);
					activityMap.put("activityUserName", activityUser.getUsername());
					final User unblockedUser = gloonDatastore.createQuery(User.class)
							.filter("_id", new ObjectId(activity.getEntityId())).get();
					activityMap.put("notFollowingUserName", unblockedUser.getUsername());
					activityMap.put("activityType", activityType);
					break;

				case Activity.ACTIVITY_FOLLOW_GAME:
					activityMap.put("activityOwnerUserName", userName);
					activityMap.put("activityUserName", activityUser.getUsername());
					avatar = activityUser.getAvatar();
					if (!StringUtil.isNullOrEmpty(avatar)) {
						media = mediaDaoInstance.getById(avatar);
						activityMap.put("activityUserAvatar", media.getUrl());
					} else {
						activityMap.put("activityUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}
					final Game followedGame = gloonDatastore.createQuery(Game.class)
							.filter("_id", new ObjectId(activity.getEntityId())).get();
					activityMap.put("followingGame", followedGame.getTitle());
					String gameBoxShot = followedGame.getGameBoxShot();
					if (StringUtil.isNullOrEmpty(gameBoxShot)) {
						activityMap.put("gameBoxShot", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/boxShot.png");
					} else {
						media = mediaDaoInstance.getById(gameBoxShot);
						activityMap.put("gameBoxShot", media.getUrl());
					}
					activityMap.put("activityType", activityType);
					activityMap.put("gameEncodedUrl", Utility.encodeForUrl(followedGame.getTitle()) + "-"
							+ followedGame.getId().toString());
					break;

				case Activity.ACTIVITY_UNFOLLOW_GAME:
					activityMap.put("activityOwnerUserName", userName);
					activityMap.put("activityUserName", activityUser.getUsername());
					avatar = activityUser.getAvatar();
					if (!StringUtil.isNullOrEmpty(avatar)) {
						media = mediaDaoInstance.getById(avatar);
						activityMap.put("activityUserAvatar", media.getUrl());
					} else {
						activityMap.put("activityUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}
					final Game unfollowedGame = gloonDatastore.createQuery(Game.class)
							.filter("_id", new ObjectId(activity.getEntityId())).get();
					activityMap.put("notFollowingGame", unfollowedGame.getTitle());
					gameBoxShot = unfollowedGame.getGameBoxShot();
					if (StringUtil.isNullOrEmpty(gameBoxShot)) {
						activityMap.put("gameBoxShot", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/boxShot.png");
					} else {
						media = mediaDaoInstance.getById(gameBoxShot);
						activityMap.put("gameBoxShot", media.getUrl());
					}
					activityMap.put("activityType", activityType);
					activityMap.put("gameEncodedUrl", Utility.encodeForUrl(unfollowedGame.getTitle()) + "-"
							+ unfollowedGame.getId().toString());
					break;
				case Activity.ACTIVITY_POST_NOT_COOL:
					activityMap.put("activityOwnerUserName", userName);
					activityMap.put("activityUserName", activityUser.getUsername());
					avatar = activityUser.getAvatar();
					if (!StringUtil.isNullOrEmpty(avatar)) {
						media = mediaDaoInstance.getById(avatar);
						activityMap.put("activityUserAvatar", media.getUrl());
					} else {
						activityMap.put("activityUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}
					final Article notCoolArticle = gloonDatastore.createQuery(Article.class)
							.filter("_id", new ObjectId(activity.getEntityId())).get();
					activityMap.put("articleTitle", notCoolArticle.getTitle());
					activityMap.put("articleFeaturedImage", notCoolArticle.getFeaturedImage());
					activityMap.put("articleEncodedUrl", Utility.encodeForUrl(notCoolArticle.getTitle()) + "-"
							+ notCoolArticle.getId().toString());
					activityMap.put("activityType", activityType);
					break;
			}
			activites.add(activityMap);
		}
		return activites;
	}

	private ArrayList<HashMap<String, Object>> getGameActivityMaps(final List<Activity> activityList, final Game game) throws MalformedURLException {
		final ArrayList<HashMap<String, Object>> activites = new ArrayList<>();
		for (final Activity activity : activityList) {
			final HashMap<String, Object> gameActivityMap = new HashMap<>();
			final int activityType = activity.getType();
			final User activityUser = gloonDatastore.createQuery(User.class).filter("username", activity.getUsername()).get();
			final Article article = gloonDatastore.createQuery(Article.class).filter("_id", new ObjectId(activity.getEntityId()))
					.get();
			switch (activityType) {

				case Activity.ACTIVITY_FOLLOW_GAME:					
					Media media=null;
					gameActivityMap.put("activityUserName", activityUser.getUsername());
					String userAvatar = activityUser.getAvatar();
					if (!StringUtil.isNullOrEmpty(userAvatar)) {
						media= mediaDaoInstance.getById(userAvatar);
						gameActivityMap.put("activityUserAvatar", media.getUrl());
					} else {
						gameActivityMap.put("activityUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}								
					gameActivityMap.put("followingGame", game.getTitle());
					String gameBoxShot = game.getGameBoxShot();
					if (StringUtil.isNullOrEmpty(gameBoxShot)) {
						gameActivityMap.put("gameBoxShot", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/boxShot.png");
					} else {
						media = mediaDaoInstance.getById(gameBoxShot);
						gameActivityMap.put("gameBoxShot", media.getUrl());
					}
					gameActivityMap.put("activityType", activityType);
					gameActivityMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle()) + "-"
							+ game.getId().toString());					
					break;

				case Activity.ACTIVITY_POST_PUBLISH:
					final Article gameArticle = gloonDatastore.createQuery(Article.class)
							.filter("_id", new ObjectId(activity.getEntityId())).get();
					gameActivityMap.put("articleCategory", gameArticle.getCategory().toString());
					String featuredImage = gameArticle.getFeaturedImage();
					if (featuredImage.isEmpty() || "default".equalsIgnoreCase(featuredImage)) {
						gameActivityMap.put("articleFeaturedImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/featuredBg.png");
					} else {
						media = mediaDaoInstance.getById(featuredImage);
						gameActivityMap.put("articleFeaturedImage", media.getUrl());
					}					
					gameActivityMap.put("activityUserName", activityUser.getUsername());					
					userAvatar = activityUser.getAvatar();
					if (!StringUtil.isNullOrEmpty(userAvatar)) {
						media= mediaDaoInstance.getById(userAvatar);
						gameActivityMap.put("activityUserAvatar", media.getUrl());
					} else {
						gameActivityMap.put("activityUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}
					gameActivityMap.put("articleTitle", article.getTitle());
					gameActivityMap.put("articleEncodedUrl", Utility.encodeForUrl(article.getTitle()) + "-"
							+ article.getId().toString());
					gameActivityMap.put("activityType", activityType);

					break;
					
				case Activity.ACTIVITY_POST_COOL:					
					gameActivityMap.put("activityUserName", activityUser.getUsername());					
					userAvatar = activityUser.getAvatar();
					if (!StringUtil.isNullOrEmpty(userAvatar)) {
						media= mediaDaoInstance.getById(userAvatar);
						gameActivityMap.put("activityUserAvatar", media.getUrl());
					} else {
						gameActivityMap.put("activityUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}
					final Article coolArticle = gloonDatastore.get(Article.class, new ObjectId(activity.getEntityId()));
					gameActivityMap.put("articleTitle", coolArticle.getTitle());
					gameActivityMap.put("articleEncodedUrl", Utility.encodeForUrl(coolArticle.getTitle()) + "-"
							+ coolArticle.getId().toString());
					featuredImage = coolArticle.getFeaturedImage();
					if (featuredImage.isEmpty() || "default".equalsIgnoreCase(featuredImage)) {
						gameActivityMap.put("articleFeaturedImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/featuredBg.png");
					} else {
						media = mediaDaoInstance.getById(featuredImage);
						gameActivityMap.put("articleFeaturedImage", media.getUrl());
					}
					gameActivityMap.put("activityType", activityType);
					break;

			}
			activites.add(gameActivityMap);
		}
		return activites;
	}

}
