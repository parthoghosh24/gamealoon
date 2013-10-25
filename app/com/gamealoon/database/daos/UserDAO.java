package com.gamealoon.database.daos;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import org.bson.types.ObjectId;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.Http.MultipartFormData.FilePart;

import com.gamealoon.algorithm.RankAlgorithm;
import com.gamealoon.algorithm.SecurePassword;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.UserInterface;
import com.gamealoon.models.Achievement;
import com.gamealoon.models.Activity;
import com.gamealoon.models.Article;
import com.gamealoon.models.Buddy;
import com.gamealoon.models.Game;
import com.gamealoon.models.Genre;
import com.gamealoon.models.InterestedGame;
import com.gamealoon.models.InterestedUser;
import com.gamealoon.models.Platform;
import com.gamealoon.models.User;
import com.gamealoon.models.UserGameScoreMap;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.Mongo;

public class UserDAO extends GloonDAO implements UserInterface{
	
private static final UserDAO DATA_ACCESS_LAYER=new UserDAO();	
private static final MediaDAO mediaDAOInstance = MediaDAO.instantiateDAO();
private static final ActivityDAO activityDAOInstance=ActivityDAO.instantiateDAO();
private static final AchievementDAO achievementDAOInstance=AchievementDAO.instantiateDAO();
private static final GameDAO gameDAOInstance=GameDAO.instantiateDAO();
private Datastore gloonDatastore=null;
	private UserDAO()
	{
		super();
		gloonDatastore=initDatastore();		
	}
	
	/**
	 * Singleton way to instantiate Gloon DAO
	 * @return
	 */
	public static UserDAO instantiateDAO()
	{								
		return DATA_ACCESS_LAYER;
	}
	
	
	@Override
	public List<HashMap<String, Object>> getTopNUsers(int limit) {
		 List<HashMap<String, Object>> userMaps = new ArrayList<>();
		   List<User> topUsers = getTopUsers(limit);
		   if(topUsers.size()>0)
		   {
			   for(User user: topUsers)
			   {
				   HashMap<String, Object> userMap = new HashMap<>();
				   userMap.put("userUserName", user.getUsername());
				   if(!user.getAvatarPath().isEmpty())
				   {
					   userMap.put("userAvatar", AppConstants.APP_IMAGE_USER_URL_PATH+user.getAvatarPath());  
				   }
				   else
				   {					   
					   userMap.put("userAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH+"/avatar.png");
				   }	
				   
				   userMap.put("userAchievementCount", user.getAchievements().size());
				   userMap.put("totalFollowers", user.getFollowedBy().size());
				   userMaps.add(userMap);
						   
			   }  
		   }
		   
		   return userMaps;
	}
	
	@Override
	public HashMap<String, Object> getLoggedInUser(String username, String password) {
		HashMap<String, Object> loggedInUserMap= new HashMap<>();		
		loggedInUserMap.put("status", "fail");
		User user = checkUser(username, password);
        if(user!=null)
        {
        	loggedInUserMap.put("status", "success");
        	loggedInUserMap.put("username", user.getUsername());
        	loggedInUserMap.put("firstName", user.getFirstName());
        	loggedInUserMap.put("lastName", user.getLastName());
        	loggedInUserMap.put("userid",user.getId().toString());
        	 if(!user.getAvatarPath().isEmpty())
			   {
        		 loggedInUserMap.put("userAvatar", AppConstants.APP_IMAGE_USER_URL_PATH+user.getAvatarPath());  
			   }
			   else
			   {
				   loggedInUserMap.put("userAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH+"/avatar.png");
			   }
        	
        }
		return loggedInUserMap;
	}
	
	@Override
	public HashMap<String, Object> registerUser(String username, String password, String email,String firstName, String lastName) {
		HashMap<String, Object> registerUser = new HashMap<>();
		registerUser.put("status", "fail");
		if(registerTheUser(username, password, email, firstName, lastName))
		{
			registerUser.put("status", "success");
		}			
		return registerUser;
	}
	
	@Override
	public HashMap<String, Object> getUser(String usernameOrId, Integer mode, String username) {
		   Mongo instance = getDatabaseInstance().getMongoInstance();	
		   User user = getUserData(usernameOrId);
		   ArticleDAO articleDao = ArticleDAO.instantiateDAO();	
		   ActivityDAO activityDAO = ActivityDAO.instantiateDAO();		   
		   HashMap<String, Object> userMap = new HashMap<>();
		   if(user!=null)
		   {
			   userMap.put("userFirstName", user.getFirstName());			   
			   userMap.put("userLastName", user.getLastName());			   
			   userMap.put("userDay", user.getDay());
			   userMap.put("userMonth", user.getMonth());
			   userMap.put("userYear", user.getYear());
			   userMap.put("userEmail",user.getEmail());
			   userMap.put("userBirthdayVisibility", user.getBirthdayVisibility());
			   userMap.put("userUserName", user.getUsername());
			   if(!user.getAvatarPath().isEmpty())
			   {
				   Logger.debug("Image available.......");
				   userMap.put("userAvatar", AppConstants.APP_IMAGE_USER_URL_PATH+user.getAvatarPath());  
			   }
			   else
			   {
				   userMap.put("userAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH+"/avatar.png");
			   }			   
			   userMap.put("userAchievementCount", user.getAchievements().size());
			   userMap.put("userFollowersCount", user.getFollowedBy().size());
			   userMap.put("userFollowingCount", user.getFollowing().size());
			   userMap.put("userGameBio", user.getGameBio());
			   userMap.put("userTotalCount", count()-1); //N-1 users. You don't wanna count yourself duh!!!			   
			   userMap.put("userTotalAchievements", Achievement.getAllAchievementCount());
			   articleDao = ArticleDAO.instantiateDAO();					   
			   userMap.put("userTotalPublishedArticles",Article.allPublishedArticleCount());
			   userMap.put("userTotalArticlesPublishedByUser",Article.allPublishedArticleCount(user));
			   userMap.put("userTotalCoolScore", user.getUserTotalCoolScore());
			   if(articleDao.allPublishedArticlesCount(null)>0)
			   {
				   userMap.put("userNetworkTotalCoolScore", RankAlgorithm.calculateNetworkTotalCoolScore(instance));  
			   }
			   else
			   {
				   userMap.put("userNetworkTotalCoolScore", 0);
			   }
			   userMap.put("userInterestedPlatforms", user.getInterestedPlatforms());
			   userMap.put("userCountry", user.getCountry());
			   ArrayList<HashMap<String, Object>> genreMapList = new ArrayList<>();
			   for(Genre genre: user.getInterestedGenres())
			   {
				   HashMap<String,Object> genreMap = new HashMap<>();	
				   genreMap.put("genreValue", genre.toString());
				   genreMap.put("genreShortValue", genre);
				   genreMapList.add(genreMap);
			   }			   
			   userMap.put("userInterestedGenres", genreMapList);	
			   
			   ArrayList<InterestedGame> interestedGames = user.getFollowingGames();			   
			   if(interestedGames!=null)
			   {
				   Collections.sort(interestedGames,new Comparator<InterestedGame>() {

					@Override  
					public int compare(InterestedGame instance1, InterestedGame instance2) {						
						return instance2.getTimestamp().compareTo(instance1.getTimestamp());
					}
					   
				   });
				   
				   ArrayList<InterestedGame> selectedInterestedGameList = new ArrayList<>();
				   selectedInterestedGameList=interestedGames;
				   if(interestedGames.size()>5)
				   {
					   selectedInterestedGameList=(ArrayList<InterestedGame>) interestedGames.subList(0, 5);					   
				   }
				   else if(interestedGames.size()>10)
				   {					   					   
					   selectedInterestedGameList=(ArrayList<InterestedGame>) interestedGames.subList(0, 10);					   
				   }
				   
				   ArrayList<HashMap<String, Object>> interestedGameListMaps = new ArrayList<>();
				   for(InterestedGame selectedGame: selectedInterestedGameList)
				   {
					   HashMap<String, Object> interestedGameMap = new HashMap<>();
					   Game game = gameDAOInstance.getGameById(selectedGame.getGameId());
					   interestedGameMap.put("interestedGameId", game.getId().toString());
					   interestedGameMap.put("interestedGameTitle", game.getTitle());
					   String gameBoxShotPath = game.getGameBoxShotPath();
					   if(gameBoxShotPath.isEmpty())
					   {
						   interestedGameMap.put("interestedGameBoxShot", AppConstants.APP_IMAGE_DEFAULT_URL_PATH+"/boxShot.png");
					   }
					   else
					   {
						   interestedGameMap.put("interestedGameBoxShot", AppConstants.APP_IMAGE_GAME_URL_PATH+"/"+Utility.shortenString(game.getTitle())+"/uploads/boxshot/"+gameBoxShotPath);   
					   }	   
					   interestedGameMap.put("interestedGameURL",  Utility.encodeForUrl(game.getTitle())+"-"+game.getId().toString());
					   interestedGameMap.put("interestedGameTimestamp",  selectedGame.getTimestamp());
					   interestedGameListMaps.add(interestedGameMap);
				   }
				   
					   userMap.put("userInterestedGames", interestedGameListMaps);
					   userMap.put("userDashboardInterestedGames",interestedGameListMaps);  
				   
				   
			   }
			   else
			   {
				   userMap.put("userInterestedGames", new ArrayList<>());
				   userMap.put("userDashboardInterestedGames",new ArrayList<>());
			   }
			   
			   
			   //10 followers				   
			   ArrayList<Buddy> followedBy = user.getFollowedBy();		
			   
			   if(followedBy!=null)
			   {
				   Collections.sort(followedBy,new Comparator<Buddy>() {

					@Override
					public int compare(Buddy instance1, Buddy instance2) {						
						return instance2.getTimestamp().compareTo(instance1.getTimestamp());
					}
					   
				   });
				   
				   ArrayList<Buddy> selectedBuddyList = new ArrayList<>();
				   selectedBuddyList=followedBy;
				   if(followedBy.size()>5)
				   {
					   selectedBuddyList=(ArrayList<Buddy>) followedBy.subList(0, 5);					   
				   }
				   else if(followedBy.size()>10)
				   {					   					   
					   selectedBuddyList=(ArrayList<Buddy>) followedBy.subList(0, 10);					   
				   }
				   
				   ArrayList<HashMap<String, Object>> buddyListMaps = new ArrayList<>();
				   for(Buddy buddy: selectedBuddyList)
				   {
					   HashMap<String, Object> buddyMap = new HashMap<>();
					   User buddyUser = findByUsername(buddy.getUserName());
					   buddyMap.put("buddyUserId", buddyUser.getId().toString());
					   buddyMap.put("buddyUsername", buddyUser.getUsername());
					   String buddyAvatarImage = buddyUser.getAvatarPath();
					   if(buddyAvatarImage.isEmpty())
					   {
						   buddyMap.put("buddyUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH+"/avatar.png");
					   }
					   else
					   {
						   buddyMap.put("buddyUserAvatar", AppConstants.APP_IMAGE_USER_URL_PATH+buddyUser.getAvatarPath());  						     
					   }	 
					   buddyMap.put("buddyUserTimestamp", buddy.getTimestamp());
					   buddyListMaps.add(buddyMap);
				   }
				   
					   userMap.put("userFollowedBy", buddyListMaps);
					   userMap.put("userDashboardFollowedBy",buddyListMaps); 
				   
			   }
			   else
			   {
				   userMap.put("userFollowedBy", new ArrayList<>());
				   userMap.put("userDashboardFollowedBy",new ArrayList<>());
			   }
			   			   			   	
			   
			   
			   
			   
			   
			   //10 followings				   		
			   ArrayList<Buddy> followings = user.getFollowing();		
			   
			   if(followings!=null)
			   {
				   Collections.sort(followings,new Comparator<Buddy>() {

					@Override
					public int compare(Buddy instance1, Buddy instance2) {						
						return instance2.getTimestamp().compareTo(instance1.getTimestamp());
					}
					   
				   });
				   
				   ArrayList<Buddy> selectedBuddyList = new ArrayList<>();
				   selectedBuddyList=followings;
				   if(followings.size()>5)
				   {
					   selectedBuddyList=(ArrayList<Buddy>) followings.subList(0, 5);					   
				   }
				   else if(followings.size()>10)
				   {					   					   
					   selectedBuddyList=(ArrayList<Buddy>) followings.subList(0, 10);					   
				   }
				   
				   ArrayList<HashMap<String, Object>> buddyListMaps = new ArrayList<>();
				   for(Buddy buddy: selectedBuddyList)
				   {
					   HashMap<String, Object> buddyMap = new HashMap<>();
					   User buddyUser = findByUsername(buddy.getUserName());
					   buddyMap.put("buddyUserId", buddyUser.getId().toString());
					   buddyMap.put("buddyUsername", buddyUser.getUsername());
					   String buddyAvatarImage = buddyUser.getAvatarPath();
					   if(buddyAvatarImage.isEmpty())
					   {
						   buddyMap.put("buddyUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH+"/avatar.png");
					   }
					   else
					   {
						   buddyMap.put("buddyUserAvatar", AppConstants.APP_IMAGE_USER_URL_PATH+buddyUser.getAvatarPath());  						     
					   }	 
					   buddyMap.put("buddyUserTimestamp", buddy.getTimestamp());
					   buddyListMaps.add(buddyMap);
				   }
				   
					   userMap.put("userFollowingOthers", buddyListMaps);					   
				   
			   }
			   else
			   {
				   userMap.put("userFollowingOthers", new ArrayList<>());				   
			   }

			   //10 achievements				   
			   ArrayList<Achievement> userAchievements = user.getAchievements();		
			   if(userAchievements!=null)
			   {
				   if(userAchievements.size()>10)
				   {
					   userMap.put("userAchievements", userAchievements.subList(1, 11));  
				   }
				   else
				   {
					   userMap.put("userAchievements", userAchievements);
				   }  
			   }
			   else
			   {
				   userMap.put("userAchievements", new ArrayList<>());
			   }
			   
			   if(mode == AppConstants.USER_PROFILE)
			   {
				   
				   //10 recent posts				  
				  userMap.put("userPosts", articleDao.getArticleListForUser(user));
				   
				   
				   //10 recent activities							   
				   userMap.put("userActivities", activityDAO.getActivities(user));
			   }
			   if(mode == AppConstants.USER_PAGE)
			   {
				   				   
				   userMap.put("userCarouselArticles", articleDao.getAllArticlesForUserCarousel(user.getUsername()));
				   userMap.put("userPublicActivities", activityDAOInstance.getPublicActivitiesForUser(user));
				   userMap.put("userRecentArticles", articleDao.getNArticlesByCarouselSelectorAndCategory(user.getUsername(), "all", new Date().getTime(), Article.USER));
				  if(checkUserBlockedOrNot(user, findByUsername(username)))
				  {
					  userMap.put("isBlocked", 1);
				  }
				  else
				  {
					  userMap.put("isBlocked", 0);
				  }
				  
				  if(checkUserFollowerOrNot(user, findByUsername(username)))
				  {
					  userMap.put("isFollowing", 1);
				  }
				  else
				  {
					  userMap.put("isFollowing", 0);
				  }
				   
			   }
		   }
		   
		   
		return userMap;
	}
	
	@Override
	public void save(User user) {
		gloonDatastore.save(user);
		
	}
	
	@Override
	public User findByUsername(String username) {		
		return gloonDatastore.find(User.class, "username", username).get();
	}
	
	@Override
	public User findByEmail(String email) {
		
		return gloonDatastore.find(User.class,"email",email).get();
	}

	@Override
	public Long count() {	
		return gloonDatastore.getCount(User.class);
	}
	
	@Override
	public HashMap<String, Object> saveOrUpdateUserInterest(String username,int type, String[] interests) {	
		
		HashMap<String, Object> response = new HashMap<>();
		PlatformDAO platformDAO = PlatformDAO.instantiateDAO();
		response.put("status", "fail");
		User user = findByUsername(username);
		try
		{
			if(type == User.PLATFORM_INTEREST)
			{
				ArrayList<Platform> userInterestPlatforms= new ArrayList<>();
				for(String interest: interests)
				{
				    	Platform platform = platformDAO.findByShortTitle(interest);				    	
				    	if(platform!=null)
				    	{
				    		userInterestPlatforms.add(platform);
				    	}
				    					    	
				}
				user.setInterestedPlatforms(userInterestPlatforms);
				response.put("status", "success");
			}
			else
			{
				ArrayList<Genre> userInterestGenres= new ArrayList<>();
				for(String interest: interests)
				{
				   Genre genre = null;
				   if(interest.length()>0)
				   {
					   genre=Genre.valueOf(interest);
				   }						  
				   if(genre!=null)
				   {
					   userInterestGenres.add(genre);  
				   }
				   				   
				}
				user.setInterestedGenres(userInterestGenres);
				response.put("status", "success");
			}
			save(user);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.put("status", "fail");
		}		
		return response;
	}
	
	
	@Override
	public HashMap<String, Object> saveOrUpdateUser(String username,DynamicForm requestData) {
		HashMap<String, Object> response = new HashMap<>();
		User user = findByUsername(username);
		response.put("status", "fail");
		try
		{			
			String firstName = requestData.get("firstName");
			user.setFirstName(firstName);
			String lastName = requestData.get("lastName");
			user.setLastName(lastName);
			String country = requestData.get("country");
			user.setCountry(country);
			Integer day =Integer.parseInt(requestData.get("day"));
			user.setDay(day);
			Integer month =Integer.parseInt(requestData.get("month"));
			user.setMonth(month);
			Integer year =Integer.parseInt(requestData.get("year"));
			user.setYear(year);
			Integer birthdayVisibility = Integer.parseInt(requestData.get("birthdayVisibility"));
			user.setBirthdayVisibility(birthdayVisibility);
			String gameBio = requestData.get("gameBio");
			user.setGameBio(gameBio);
			save(user);
			response.put("status", "success");			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.put("status", "fail");
		}
		return response;
	}
	
	@Override
	public HashMap<String, Object> resetPassword(String username,DynamicForm requestData) {
		HashMap<String, Object> response = new HashMap<>();
		response.put("status", "fail");
		User user = findByUsername(username);
		if(user!=null)
		{
			String storedHash=user.getPasswordHash();
			String storedSalt=user.getPasswordSalt();
			String oldPassword=requestData.get("old");
			String newPassword=requestData.get("new");
			try {
				if(SecurePassword.validatePassword(oldPassword, storedHash, storedSalt))
				{				
					HashMap<String, String> newPasswordMap = SecurePassword.createHash(newPassword);
					user.setPasswordHash(newPasswordMap.get("hashHex"));
					user.setPasswordSalt(newPasswordMap.get("saltHex"));
					save(user);
					response.put("status", "success");
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {				
				e.printStackTrace();
				response.put("status", "fail");
			}
		}
		return response;
	}
	
	@Override
	public HashMap<String, String> addOrRemoveBuddy(String username,String buddyUsername, Integer type) {
		Mongo instance = getDatabaseInstance().getMongoInstance();
		HashMap<String, String> response = new HashMap<>();		
		response.put("status", "fail");
		User user = findByUsername(username);
		User buddy = findByUsername(buddyUsername);
		Date time = new Date();
		try
		{
			if(user!= null && buddy!=null)
			{
				Buddy originalUser= null;
				
				Buddy buddyUser= null;
				
				if(type == Buddy.ADD)
				{
					
					originalUser= new Buddy();					
					originalUser.setUserName(username);					
					originalUser.setChatState(User.INVITE);
					originalUser.setBlockState(Buddy.UNBLOCK);
					originalUser.setInsertTime(Utility.convertDateToString(time));
					originalUser.setTimestamp(time.getTime());
					
					buddyUser= new Buddy();					
					buddyUser.setUserName(buddyUsername);					
					buddyUser.setChatState(User.INVITE);
					buddyUser.setBlockState(Buddy.UNBLOCK);
					buddyUser.setInsertTime(Utility.convertDateToString(time));
					buddyUser.setTimestamp(time.getTime());
					
					ArrayList<Buddy> originalUserFollowing =user.getFollowing();					
					originalUserFollowing =user.getFollowing();
					if(!originalUserFollowing.contains(buddyUser))
					{
						originalUserFollowing.add(buddyUser); //Now following buddy user
						user.setFollowing(originalUserFollowing);
					}
					
					
					ArrayList<Buddy> buddyUserFollowedBy =buddy.getFollowedBy();
					if(!buddyUserFollowedBy.contains(originalUser))
					{
						buddyUserFollowedBy.add(originalUser);// Buddy user getting followed by original user
						buddy.setFollowedBy(buddyUserFollowedBy);
					}					
					save(user);
					
					save(buddy);
					activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, user.getUsername(), buddy.getId().toString(), AppConstants.PUBLIC, Utility.convertDateToString(time), time.getTime());
					response.put("status", "success");
				}
				else
				{					
					ArrayList<Buddy> originalUserFollowing =user.getFollowing();
					ArrayList<Buddy> buddyUserFollowedBy = buddy.getFollowedBy();	
					
					//Fetch buddyUser instance from original user followings
					for(Buddy buddyInstance: originalUserFollowing)
					{
						if(buddyUsername.equalsIgnoreCase(buddyInstance.getUserName()))
						{
							buddyUser= buddyInstance;
						}
					}
					
					//Fetch originalUser instance from buddy user followed-by
					for(Buddy buddyInstance: buddyUserFollowedBy)
					{
						if(username.equalsIgnoreCase(buddyInstance.getUserName()))
						{
							originalUser= buddyInstance;
						}
					}
					
					
					originalUserFollowing.remove(buddyUser);					
					user.setFollowing(originalUserFollowing);
					buddyUserFollowedBy.remove(originalUser);
					buddy.setFollowedBy(buddyUserFollowedBy);
					save(user);
					save(buddy);
					activityDAOInstance.create(Activity.ACTIVITY_USER_UNFOLLOWS, user.getUsername(), buddy.getId().toString(), AppConstants.PRIVATE, Utility.convertDateToString(time), time.getTime());
					response.put("status", "success");
					
				}					
				if(buddy!=null)
				{
					Double userFollowScore = buddy.getFollowedBy().size()/(count()*1.0);
					buddy.setUserFollowScore(userFollowScore);
					Double articlePublishRateRatio=RankAlgorithm.calculateUserArticlePublishRateRatio(buddy.getArticlePublishRate(), instance);		
					Double articleScoreRatio = RankAlgorithm.calculateUserArticleScoreRatio(buddy.getUserArticleScore(), instance);					
					Double userTotalScore = RankAlgorithm.calculateUserScore(articlePublishRateRatio, userFollowScore, articleScoreRatio);
					buddy.setTotalScore(userTotalScore);
					if(gloonDatastore.getCount(UserGameScoreMap.class)>0)
					{
						Query<UserGameScoreMap> updateQuery = gloonDatastore.createQuery(UserGameScoreMap.class).field("username").equal(buddy.getUsername());
						UpdateOperations<UserGameScoreMap>  opearations=gloonDatastore.createUpdateOperations(UserGameScoreMap.class).set("networkUserWeight", RankAlgorithm.calculateUserScoreRatio(userTotalScore, instance));
						gloonDatastore.update(updateQuery, opearations);
					}
					
					save(buddy);
				}
				
				
			}			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.put("status", "fail");
		}
		return response;
	}
	
	@Override
	public HashMap<String, String> validateEmail(String email) {
		HashMap<String, String> response= new HashMap<>();
		response.put("status", "success");
		User user = gloonDatastore.find(User.class, "email", email).get();
		if(user!=null)
		{
			response.put("status", "fail");
		}
		return response;
	}

	@Override
	public HashMap<String, String> validateUsername(String username) {
		HashMap<String, String> response= new HashMap<>();
		response.put("status", "success");
		User user = gloonDatastore.find(User.class, "username", username).get();
		if(user!=null)
		{
			response.put("status", "fail");
		}
		return response;
	}
	
	/**
	 * Check whether user and buddyuser blocked either of them or not
	 * 
	 * @param user
	 * @param buddyUser
	 * @return
	 */
	private Boolean checkUserBlockedOrNot(User user, User buddyUser)
	{	
		Boolean response = false;
		
		if(user!=null && buddyUser!= null)
		{
			ArrayList<Buddy> userFollowedBy = user.getFollowedBy();
			for(Buddy userInstance: userFollowedBy)
			{
				if(userInstance.getUserName().equals(buddyUser.getUsername()) && userInstance.getBlockState() == Buddy.BLOCK)
				{
					response = true;
					break;
				}					
			}
			
			ArrayList<Buddy> buddyUserFollowedBy = buddyUser.getFollowedBy();
			for(Buddy userInstance: buddyUserFollowedBy)
			{
				if(userInstance.getUserName().equals(user.getUsername()) && userInstance.getBlockState() == Buddy.BLOCK)
				{
					response = true;
					break;
				}
			}
		}
		
		return response;
	}
	
	/**
	 * Check whether Buddy User is following user or not
	 * 
	 * @param user
	 * @param buddyUser
	 * @return
	 */
	private Boolean checkUserFollowerOrNot(User user, User buddyUser)
	{
		Boolean response =false;
		if(user!=null && buddyUser!=null)
		{
			ArrayList<Buddy> buddyUserFollowings = buddyUser.getFollowing();
			for(Buddy buddy: buddyUserFollowings)
			{
				if(user.getUsername().equalsIgnoreCase(buddy.getUserName()))
				{
					response = true;
					break;
				}
			}
		}
		
		return response;
	}
	
	

	@Override
	public HashMap<String, String> blockOrUnblockBuddy(String username,String buddyUsername, Integer type) {
		HashMap<String, String> response = new HashMap<>();		
		response.put("status", "fail");
		User user = findByUsername(username);
		User buddy = findByUsername(buddyUsername);					
		Date time = new Date();
		try
		{
			if(user!= null && buddy!=null)
			{				
				
				Buddy originalUser=null;				
				Buddy buddyUser = null;
				if(type == Buddy.BLOCK) //Block followed by user
				{
					ArrayList<Buddy> originalUserFollowedBy =user.getFollowedBy();
					for(Buddy buddyInstance: originalUserFollowedBy)
					{
						if(buddyUsername.equalsIgnoreCase(buddyInstance.getUserName()))
						{
							buddyInstance.setBlockState(Buddy.BLOCK);
							break;
						}
					}	
					
					ArrayList<Buddy> originalUserFollowing =user.getFollowing();
					for(Buddy buddyInstance: originalUserFollowing)
					{
						if(buddyUsername.equalsIgnoreCase(buddyInstance.getUserName()))
						{
							buddyUser=buddyInstance;
							break;
						}
					}	
					
					if(buddyUser!= null)
					{
						originalUserFollowing.remove(buddyUser);
						user.setFollowing(originalUserFollowing);
					}
					
					ArrayList<Buddy> buddyUserFollowing =buddy.getFollowing();
					for(Buddy buddyInstance: buddyUserFollowing)
					{
						if(username.equalsIgnoreCase(buddyInstance.getUserName()))
						{
							originalUser=buddyInstance;
							break;
						}
					}
					
					ArrayList<Buddy> buddyUserFollowedBy =buddy.getFollowedBy();
					if(originalUser!= null)
					{
						buddyUserFollowing.remove(originalUser);// remove original user from buddy's following list
						buddy.setFollowing(buddyUserFollowing);
						
						if(buddyUserFollowedBy.contains(originalUser))
						{
							buddyUserFollowedBy.remove(originalUser);// remove original user from buddy's following list
							buddy.setFollowedBy(buddyUserFollowedBy);
						}
					}
					save(user);
					save(buddy);
					activityDAOInstance.create(Activity.ACTIVITY_BLOCK, user.getUsername(), buddy.getId().toString(), AppConstants.PRIVATE, Utility.convertDateToString(time), time.getTime());
					response.put("status", "success");
				}
				else //unblock user
				{
					ArrayList<Buddy> originalUserFollowedBy =user.getFollowedBy();
					for(Buddy buddyInstance: originalUserFollowedBy)
					{
						if(buddyUsername.equalsIgnoreCase(buddyInstance.getUserName()))
						{
							buddyInstance.setBlockState(Buddy.UNBLOCK);
							break;
						}
					}					
					
					ArrayList<Buddy> buddyUserFollowing =buddy.getFollowing();
					for(Buddy buddyInstance: originalUserFollowedBy)
					{
						if(username.equalsIgnoreCase(buddyInstance.getUserName()))
						{
							originalUser=buddyInstance;
							break;
						}
					}
					
					if(originalUser!= null)
					{
						buddyUserFollowing.add(originalUser);// remove original user from buddy's following list
						buddy.setFollowing(buddyUserFollowing);
					}
					save(user);
					save(buddy);
					activityDAOInstance.create(Activity.ACTIVITY_UNBLOCK, user.getUsername(), buddy.getId().toString(), AppConstants.PRIVATE, Utility.convertDateToString(time),time.getTime());
					response.put("status", "success");
				
				} 
				if(originalUser==null)
				{
					response.put("status", "fail");
				}
				
			}			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.put("status", "fail");
		}
		
		return response;
	}
	
	@Override
	public HashMap<String, String> addOrRemoveInterestedGames(String username,String gameId, Integer type) {
		HashMap<String, String> response = new HashMap<>();		
		response.put("status", "fail");
		User user =findByUsername(username);
		Game game = gloonDatastore.get(Game.class, new ObjectId(gameId));
		Date time = new Date();
		if(user!=null || game!=null) //add game and user in their respective lists
		{
			InterestedGame interestedGame=null;
			InterestedUser interestedUser = null;
			if(type == InterestedGame.INTERESTED)
			{
				interestedGame = new InterestedGame();
				interestedGame.setGameId(game.getId().toString());							
				interestedGame.setInsertTime(Utility.convertDateToString(time));
				interestedGame.setTimestamp(time.getTime());
				
				interestedUser = new InterestedUser();				
				interestedUser.setUserName(user.getUsername());				
				interestedUser.setInsertTime(Utility.convertDateToString(time));
				interestedUser.setTimestamp(time.getTime());
				
				ArrayList<InterestedGame> interestedGames = user.getFollowingGames();
				ArrayList<InterestedUser> interestedUsers = game.getInterestedIn();
				
				if(!interestedGames.contains(interestedGame))
				{
					interestedGames.add(interestedGame);
					user.setFollowingGames(interestedGames);
				}
				
				if(!interestedUsers.contains(interestedUser))
				{
					interestedUsers.add(interestedUser);
					game.setInterestedIn(interestedUsers);
				}
				
				save(user);
				gloonDatastore.save(game);
				activityDAOInstance.create(Activity.ACTIVITY_FOLLOW_GAME, user.getUsername(), gameId, AppConstants.PUBLIC, Utility.convertDateToString(time), time.getTime());
				response.put("status", "success");
				
			}
			else //remove game and user from their respective lists
			{
				ArrayList<InterestedGame> interestedGames = user.getFollowingGames();
				ArrayList<InterestedUser> interestedUsers = game.getInterestedIn();
				
				for(InterestedGame gameInstance: interestedGames)
				{
					if(gameId.equals(gameInstance.getGameId()))
					{
						interestedGame=gameInstance;
						break;
					}
				}
				interestedGames.remove(interestedGame);
				user.setFollowingGames(interestedGames);
				
				for(InterestedUser userInstance: interestedUsers)
				{
					if(username.equals(userInstance.getUserName()))
					{
						interestedUser=userInstance;
						break;
					}
				}
				interestedUsers.remove(interestedUser);
				game.setInterestedIn(interestedUsers);
				save(user);
				gloonDatastore.save(game);
				activityDAOInstance.create(Activity.ACTIVITY_UNFOLLOW_GAME, user.getUsername(),gameId, AppConstants.PRIVATE, Utility.convertDateToString(time), time.getTime());
				response.put("status", "success");
			}
			
			if(interestedGame == null || interestedUser == null)
			{
				response.put("status", "fail");
			}
		}
		return response;
	}
	
	@Override
	public HashMap<String, String> saveOrUpdateUserAvatar(String username,FilePart avatarPart) {
		
		HashMap<String, String> response = new HashMap<>();
		response= mediaDAOInstance.uploadImage(username, avatarPart);
		if(response.get("status").equalsIgnoreCase("success"))
		{
			String fileName = avatarPart.getFilename();
			User user = findByUsername(username);
			String avatarRelativePath ="/"+username+"/uploads/"+fileName; 
			user.setAvatarPath(avatarRelativePath);
			response.put("avatarPath", AppConstants.APP_IMAGE_USER_URL_PATH+avatarRelativePath);
			save(user);
		}
		
		return response;
	}
	
	@Override
	public List<User> getTopUsers(int limit) {
		if(limit>0)
		  {
			  return gloonDatastore.createQuery(User.class).order("-totalScore").limit(limit).asList();
		  }
		  else
		  {
			  return gloonDatastore.createQuery(User.class).order("-totalScore").asList();
		  }	 
	}
	
	/**
	 * Register user. simply create new user object and feed username, password and email
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @return
	 */
	private Boolean registerTheUser(String username, String password, String email,String firstName, String lastName)
	{
		Boolean response=false;
		try
		{
			User newUser = new User();
			newUser.setUsername(username);
			HashMap<String, String> securePasswordMap = SecurePassword.createHash(password);
			newUser.setPasswordHash(securePasswordMap.get("hashHex"));
			newUser.setPasswordSalt(securePasswordMap.get("saltHex"));
			newUser.setEmail(email);
			newUser.setEmailConfirmed(AppConstants.EMAIL_NOT_CONFIRMED);
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);
			Calendar now = GregorianCalendar.getInstance();
			newUser.setDay(now.get(Calendar.DAY_OF_MONTH));
			newUser.setMonth(now.get(Calendar.MONTH));
			newUser.setYear(now.get(Calendar.YEAR));
			newUser.setGameBio("I love gaming.");
			newUser.setCountry("India");
			newUser.setBirthdayVisibility(User.PUBLIC);
			newUser.setAvatarPath(""); 			
			Achievement achievement = achievementDAOInstance.findByTitle("New Gloonie!");
			ArrayList<Achievement> achievements = new ArrayList<>();
			achievements.add(achievement);
			newUser.setAchievements(achievements);
			newUser.setArticlePublishRate(0.0);			
			newUser.setFollowedBy(new ArrayList<Buddy>());
			newUser.setFollowing(new ArrayList<Buddy>());
			newUser.setFollowingGames(new ArrayList<InterestedGame>());
			newUser.setInterestedGenres(new ArrayList<Genre>());
			newUser.setInterestedPlatforms(new ArrayList<Platform>());
			newUser.setTotalScore(0.0);
			newUser.setUserArticleScore(0.0);
			newUser.setUserFollowScore(0.0);
			newUser.setUserTotalCoolScore(0.0);
			save(newUser);					
			String uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+username+"\\uploads\\";
			File makeUsernameDir = new File(uptoUsername);
			makeUsernameDir.mkdirs();
			response=true;
		}
		catch(Exception ex)
		{
			Logger.error("Error in UserDAO at registerUser ", ex.fillInStackTrace());
			response=false;
		}		
		return response;
		
	}
	/**
	 * Check whether the user exist or not for login. If exist, return user object else null
	 * 
	 * @param gloonDatastore
	 * @param usernameOrEmail
	 * @param password
	 * @return
	 */
	private User checkUser(String usernameOrEmail, String password)	
	{
		User user =findByUsername(usernameOrEmail);
		if(user==null)
		{
			user = findByEmail(usernameOrEmail);			
		}
		
		if(user!= null)
		{
			String storedHash = user.getPasswordHash();
			String storedSalt = user.getPasswordSalt();
			try {
				if(!SecurePassword.validatePassword(password, storedHash, storedSalt))
				{
					return null;
				}				
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				Logger.error("Error in UserDAO at checkUser ", e.fillInStackTrace());
				e.printStackTrace();
				return null;
			}
		}			
		return user;		 
	}
	 

	
	/**
	 * Get user based on username or id
	 * 
	 * @param gloonDatastore
	 * @param username
	 * @return
	 */
	private User getUserData(String usernameOrId)
	{
		User user= gloonDatastore.createQuery(User.class).filter("username", usernameOrId).get();
		if(user==null)
		{						
			user= gloonDatastore.get(User.class, new ObjectId(usernameOrId));			
		}
		return user;
	}

	

	
	

	

	

	

	

	

	

	
	

}