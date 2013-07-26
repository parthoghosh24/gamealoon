package com.gamealoon.database.daos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import org.bson.types.ObjectId;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.UserInterface;
import com.gamealoon.models.Achievement;
import com.gamealoon.models.Activity;
import com.gamealoon.models.Article;
import com.gamealoon.models.User;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

public class UserDAO extends GloonDAO implements UserInterface{
	
private static final UserDAO DATA_ACCESS_LAYER=new UserDAO();	
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
	public HashMap<String, Object> getUser(String usernameOrId, Integer mode) {
		   User user = getUserData(usernameOrId, mode);
		   HashMap<String, Object> userMap = new HashMap<>();
		   if(user!=null)
		   {
			   userMap.put("userFirstName", user.getFirstName());			   
			   userMap.put("userLastName", user.getLastName());			   
			   userMap.put("userDay", user.getDay());
			   userMap.put("userMonth", user.getMonth());
			   userMap.put("userYear", user.getYear());
			   userMap.put("userEmail",user.getEmail());
			   userMap.put("userUserName", user.getUsername());
			   userMap.put("userAvatar", user.getAvatarPath());
			   userMap.put("userAchievementCount", user.getAchievements().size());
			   userMap.put("totalFollowers", user.getFollowedBy().size());
			   userMap.put("totalFollowing", user.getFollowing().size());
			   userMap.put("userGameBio", user.getGameBio());
			   
			   if(mode == AppConstants.USER_PROFILE)
			   {
				   //10 followers				   
				   ArrayList<HashMap<String,Object>> followedBy = new ArrayList<>();
				   for(User userInstance: user.getFollowedBy())
				   {
					   HashMap<String,Object> followedByMap = new HashMap<>();
					   followedByMap.put("followedByUserName", userInstance.getUsername());
					   followedByMap.put("followedByUserAvatar", userInstance.getAvatarPath());
					   followedBy.add(followedByMap);
				   }
				   if(followedBy.size()>10)
				   {
					   userMap.put("userFollowedBy", followedBy.subList(1, 11));  
				   }
				   else
				   {
					   userMap.put("userFollowedBy", followedBy);
				   }
				   
				   
				   
				   
				   //10 followings				   				   
				   ArrayList<HashMap<String,Object>> followings = new ArrayList<>();
				   for(User userInstance: user.getFollowing())
				   {
					   HashMap<String,Object> followingMap = new HashMap<>();
					   followingMap.put("followingUserName", userInstance.getUsername());
					   followingMap.put("followingUserAvatar", userInstance.getAvatarPath());
					   followings.add(followingMap);
				   }
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
				   List<Article> articles = new ArrayList<>();
				   articles = gloonDatastore.createQuery(Article.class).filter("author.username", user.getUsername()).order("-insertTime").limit(10).asList();
				   ArrayList<HashMap<String,Object>> articleMapList = new ArrayList<>();
				   for(Article article: articles)
				   {
					   HashMap<String, Object> articleMap = new HashMap<>();
					   articleMap.put("articleId", article.getId().toString());
					   articleMap.put("articleTitle", article.getTitle());
					   articleMap.put("articleStrippedTitle", article.getTitle().substring(0, 15)+"...");
					   articleMap.put("articleSubTitle", article.getSubtitle());
					   articleMap.put("articleBody", article.getBody());
					   articleMap.put("articleCategory", article.getCategory().toString());
					   articleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle())+"-"+article.getId().toString());
					   articleMap.put("articleState", article.getState());
					   articleMap.put("articleInsertTime", article.getInsertTime());
					   articleMap.put("articleUpdateTime", article.getUpdateTime());
					   articleMap.put("articlePublishDate", article.getPublishDate());
					   articleMap.put("articlePlatforms", Utility.titleList(article.getPlatforms()));
					   if(article.getGame()!=null)
						{
						   articleMap.put("articleGame",article.getGame().getTitle());
						}
						else
						{
							articleMap.put("articleGame","");
						}
					   articleMapList.add(articleMap);
					   
				   }
				   userMap.put("userPosts", articleMapList);
				   
				   
				   //10 recent activities			
				   ActivityDAO activityDAO = ActivityDAO.instantiateDAO();
				   userMap.put("userActivities", activityDAO.getActivities(user));
			   }
			   if(mode == AppConstants.USER_PAGE)
			   {
				  //TODO User page need to be re-thought... Just another copy of home page will not suffice.... Should be more customizable.  
				   
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
			newUser.setPassword(password);
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
		return gloonDatastore.createQuery(User.class).filter("username", username).filter("password", password).get();		 
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

	

	

	
	

}
