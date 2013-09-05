package com.gamealoon.database.daos;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import org.bson.types.ObjectId;

import play.data.DynamicForm;

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
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

public class UserDAO extends GloonDAO implements UserInterface{
	
private static final UserDAO DATA_ACCESS_LAYER=new UserDAO();	
private static final ActivityDAO activityDAOInstance=ActivityDAO.instantiateDAO();
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
				   userMap.put("userAvatar", user.getAvatarPath());
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
		loggedInUserMap.put("available", false);
		User user = checkUser(username, password);
        if(user!=null)
        {
        	loggedInUserMap.put("available", true);
        	loggedInUserMap.put("username", user.getUsername());
        	loggedInUserMap.put("firstName", user.getFirstName());
        	loggedInUserMap.put("lastName", user.getLastName());
        	loggedInUserMap.put("userid",user.getId().toString());
        	
        }
		return loggedInUserMap;
	}
	
	@Override
	public HashMap<String, Object> registerUser(String username, String password, String email) {
		HashMap<String, Object> registerUser = new HashMap<>();
		registerUser.put("Success", registerTheUser(username, password, email));		
		return registerUser;
	}
	
	@Override
	public HashMap<String, Object> getUser(String usernameOrId, Integer mode, String username) {
		   User user = getUserData(usernameOrId, mode);
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
			   userMap.put("userAvatar", user.getAvatarPath());
			   userMap.put("userAchievementCount", user.getAchievements().size());
			   userMap.put("userFollowersCount", user.getFollowedBy().size());
			   userMap.put("userFollowingCount", user.getFollowing().size());
			   userMap.put("userGameBio", user.getGameBio());
			   userMap.put("userTotalCount", User.getAllUserCount()-1); //N-1 users. You don't wanna count yourself duh!!!			   
			   userMap.put("userTotalAchievements", Achievement.getAllAchievementCount());
			   articleDao = ArticleDAO.instantiateDAO();					   
			   userMap.put("userTotalPublishedArticles",Article.allPublishedArticleCount());
			   userMap.put("userTotalArticlesPublishedByUser",Article.allPublishedArticleCount(user));
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
			   if(mode == AppConstants.USER_PROFILE)
			   {
				   //10 followers				   
				   ArrayList<Buddy> followedBy = user.getFollowedBy();				   				   
				   if(followedBy.size()>10)
				   {
					   userMap.put("userFollowedBy", followedBy.subList(1, 11));  
				   }
				   else
				   {
					   userMap.put("userFollowedBy", followedBy);
				   }
				   
				   
				   
				   
				   //10 followings				   				   
				   ArrayList<Buddy> followings = user.getFollowing();				   
				   if(followings.size()>10)
				   {
					   userMap.put("userFollowingOthers", followings.subList(1, 11));  
				   }
				   else
				   {
					   userMap.put("userFollowingOthers", followings);
				   }				   
				   
				   
				   //10 achievements				   
				   ArrayList<Achievement> userAchievements = user.getAchievements();				   
				   if(userAchievements.size()>10)
				   {
					   userMap.put("userAchievements", userAchievements.subList(1, 11));  
				   }
				   else
				   {
					   userMap.put("userAchievements", userAchievements);
				   }	
				   
				   
				   //10 recent posts
				  articleDao = ArticleDAO.instantiateDAO();
				  userMap.put("userPosts", articleDao.getArticleListForUser(user));
				   
				   
				   //10 recent activities			
				   activityDAO = ActivityDAO.instantiateDAO();
				   userMap.put("userActivities", activityDAO.getActivities(user));
			   }
			   if(mode == AppConstants.USER_PAGE)
			   {
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
				    	userInterestPlatforms.add(platform);				    	
				}
				user.setInterestedPlatforms(userInterestPlatforms);
				response.put("status", "success");
			}
			else
			{
				ArrayList<Genre> userInterestGenres= new ArrayList<>();
				for(String interest: interests)
				{
				   Genre genre = Genre.valueOf(interest);				   
				   userInterestGenres.add(genre);				   
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
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.put("status", "fail");
			}
		}
		return response;
	}
	
	@Override
	public HashMap<String, String> addOrRemoveBuddy(String username,String buddyUsername, Integer type) {
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
				Buddy buddyUser=null;
				if(type == Buddy.ADD)
				{
					
					ArrayList<Buddy> originalUserFollowing =user.getFollowing();
					
					originalUser = new Buddy();
					originalUser.setBuddyId(user.getId().toString());
					originalUser.setUserName(username);
					originalUser.setAvatarPath(user.getAvatarPath());
					originalUser.setChatState(User.INVITE);
					originalUser.setBlockState(Buddy.UNBLOCK);
					
					originalUser.setInsertTime(Utility.convertDateToString(time));
					originalUser.setTimestamp(time.getTime());
					
					buddyUser = new Buddy();
					buddyUser.setBuddyId(buddy.getId().toString());
					buddyUser.setUserName(buddyUsername);
					buddyUser.setAvatarPath(buddy.getAvatarPath());
					buddyUser.setChatState(User.INVITE);
					buddyUser.setBlockState(Buddy.UNBLOCK);
					buddyUser.setInsertTime(Utility.convertDateToString(time));
					buddyUser.setTimestamp(time.getTime());
					
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
					activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, user.getId().toString(), buddy.getId().toString(), AppConstants.PRIVATE, Utility.convertDateToString(time), time.getTime());
					response.put("status", "success");
				}
				else
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
					
					originalUser = new Buddy();
					originalUser.setBuddyId(user.getId().toString());
					originalUser.setUserName(username);
					originalUser.setAvatarPath(user.getAvatarPath());
					originalUser.setChatState(User.INVITE);
					originalUser.setBlockState(Buddy.UNBLOCK);
					time= new Date();
					originalUser.setInsertTime(Utility.convertDateToString(time));
					originalUser.setTimestamp(time.getTime());
					
					if(!buddyUserFollowing.contains(originalUser))
					{
						buddyUserFollowing.add(originalUser);
						buddy.setFollowing(buddyUserFollowing);
					}
					save(user);
					save(buddy);
					activityDAOInstance.create(Activity.ACTIVITY_USER_UNFOLLOWS, user.getId().toString(), buddy.getId().toString(), AppConstants.PRIVATE, Utility.convertDateToString(time), time.getTime());
					response.put("status", "success");
					
				}	
				
				
				if(originalUser == null || buddyUser ==null)
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
					activityDAOInstance.create(Activity.ACTIVITY_BLOCK, user.getId().toString(), buddy.getId().toString(), AppConstants.PRIVATE, Utility.convertDateToString(time), time.getTime());
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
					activityDAOInstance.create(Activity.ACTIVITY_UNBLOCK, user.getId().toString(), buddy.getId().toString(), AppConstants.PRIVATE, Utility.convertDateToString(time),time.getTime());
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
		if(user!=null || game!=null) //add game and user in their respective lists
		{
			InterestedGame interestedGame=null;
			InterestedUser interestedUser = null;
			if(type == InterestedGame.INTERESTED)
			{
				interestedGame = new InterestedGame();
				interestedGame.setGameId(game.getId().toString());
				interestedGame.setGameReleaseDate(game.getReleaseDate());
				interestedGame.setGameScore(game.getScore());
				interestedGame.setGameTitle(game.getTitle());
				Date time =new Date();				
				interestedGame.setInsertTime(Utility.convertDateToString(time));
				interestedGame.setTimestamp(time.getTime());
				
				interestedUser = new InterestedUser();
				interestedUser.setUserId(user.getId().toString());
				interestedUser.setUserName(user.getUsername());
				interestedUser.setAvatarPath(user.getAvatarPath());
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
				response.put("status", "success");
			}
			
			if(interestedGame == null || interestedUser == null)
			{
				response.put("status", "fail");
			}
		}
		return response;
	}
	
	/**
	 * Register user. simply create new user object and feed username, password and email
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @return
	 */
	private Boolean registerTheUser(String username, String password, String email)
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
			newUser.setFirstName("First");
			newUser.setLastName("Last");
			Calendar now = GregorianCalendar.getInstance();
			newUser.setDay(now.get(Calendar.DAY_OF_MONTH));
			newUser.setMonth(now.get(Calendar.MONTH));
			newUser.setYear(now.get(Calendar.YEAR));
			newUser.setGameBio("Your Game Bio.");
			newUser.setAvatarPath("System default avatar"); //TODO this need to be converted into actual system avatar			
			gloonDatastore.save(newUser);
			response=true;
		}
		catch(Exception ex)
		{
			response=false;
		}		
		return response;
		
	}
	/**
	 * Check whether the user exist or not for login. If exist, return user object else null
	 * 
	 * @param gloonDatastore
	 * @param username
	 * @param password
	 * @return
	 */
	private User checkUser(String username, String password)	
	{
		User user =findByUsername(username);
		if(user!=null)
		{
			String storedHash = user.getPasswordHash();
			String storedSalt = user.getPasswordSalt();
			try {
				if(!SecurePassword.validatePassword(password, storedHash, storedSalt))
				{
					return null;
				}				
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return user;		 
	}
	

	/**
	 * Get sorted users based on their ranks
	 * 
	 * @param gloonDatastore
	 * @return
	 */
	private List<User> getTopUsers(int limit)
	{
	
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
	 * Get user based on username or id
	 * 
	 * @param gloonDatastore
	 * @param username
	 * @return
	 */
	private User getUserData(String usernameOrId, Integer mode)
	{
		User user= gloonDatastore.createQuery(User.class).filter("username", usernameOrId).get();
		System.out.println(user);
		if(user==null)
		{						
			user= gloonDatastore.get(User.class, new ObjectId(usernameOrId));			
		}
		return user;
	}

	/**
	 * Utility method to found all User Count
	 * 
	 * @return
	 */
	public Long allUserCount()
	{		
		return gloonDatastore.createQuery(User.class).countAll();
				
	}

	

	

	

	

	

	

	
	

}
