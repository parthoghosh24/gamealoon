package com.gamealoon.database.daos;

import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bson.types.ObjectId;

import play.Logger;
import play.data.DynamicForm;
import play.mvc.Http.MultipartFormData.FilePart;

import com.gamealoon.algorithm.Gamification;
import com.gamealoon.algorithm.RankAlgorithm;
import com.gamealoon.algorithm.SecurePassword;
import com.gamealoon.core.common.GloonNetworkRank;
import com.gamealoon.core.common.XPTriggerPoints;
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
import com.gamealoon.models.Media;
import com.gamealoon.models.User;
import com.gamealoon.models.UserGameScoreMap;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.StringUtil;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.Mongo;
import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

public class UserDAO extends GloonDAO<User> implements UserInterface {

	private static final UserDAO DATA_ACCESS_LAYER = new UserDAO();
	private static final MediaDAO mediaDAOInstance = MediaDAO.instantiateDAO();
	private static final ActivityDAO activityDAOInstance = ActivityDAO.instantiateDAO();
	private static final ArticleDAO articleDao = ArticleDAO.instantiateDAO();
	private static final AchievementDAO achievementDAOInstance = AchievementDAO.instantiateDAO();
	private static final GameDAO gameDAOInstance = GameDAO.instantiateDAO();
	private Datastore gloonDatastore = null;

	private UserDAO() {
		super();
		gloonDatastore = initDatastore();
	}

	/**
	 * Singleton way to instantiate Gloon DAO
	 * 
	 * @return
	 */
	public static UserDAO instantiateDAO() {
		return DATA_ACCESS_LAYER;
	}

	@Override
	public List<HashMap<String, Object>> getTopNUsers(int limit) throws MalformedURLException {
		List<HashMap<String, Object>> userMaps = new ArrayList<>();
		List<User> topUsers = getTopUsers(limit);
		if (topUsers.size() > 0) {
			for (User user : topUsers) {
				HashMap<String, Object> userMap = new HashMap<>();
				userMap.put("userUserName", user.getUsername());
				userMap.put("userXp", user.getExperiencePoints());
				userMap.put("userLevel", user.getLevel());
				userMap.put("userLevel", user.getLevel());
				userMap.put("userNextLevel", user.getLevel() + 1);
				userMap.put("userXp", user.getExperiencePoints());
				userMap.put("userNextLevelPointsToGo", Gamification.pointsToGo(user.getExperiencePoints(), user.getLevel()));
				double levelCompletionRatio = Gamification.levelCompletionRatio(user.getExperiencePoints(), user.getLevel());
				userMap.put("userLevelCompletionRatio", levelCompletionRatio * 100);
				String avatar = user.getAvatar();
				if (!avatar.isEmpty()) {
					Media media = mediaDAOInstance.getById(avatar);
					userMap.put("userAvatar", media.getUrl());
				} else {
					userMap.put("userAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
				}

				userMaps.add(userMap);

			}
		}

		return userMaps;
	}

	@Override
	public HashMap<String, Object> getLoggedInUser(String username, String password) throws MalformedURLException {
		HashMap<String, Object> loggedInUserMap = new HashMap<>();
		loggedInUserMap.put("status", "fail");
		User user = checkUser(username, password);
		if (user != null) {
			loggedInUserMap.put("status", "success");
			loggedInUserMap.put("username", user.getUsername());
			loggedInUserMap.put("firstName", user.getFirstName());
			loggedInUserMap.put("lastName", user.getLastName());
			loggedInUserMap.put("userid", user.getId().toString());
			String avatar = user.getAvatar();
			if (!avatar.isEmpty()) {
				Media media = mediaDAOInstance.getById(avatar);
				loggedInUserMap.put("userAvatar", media.getUrl());
			} else {
				loggedInUserMap.put("userAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
			}

		}
		return loggedInUserMap;
	}

	@Override
	public HashMap<String, Object> registerUser(String username, String password, String email, String firstName, String lastName) {
		HashMap<String, Object> registerUser = new HashMap<>();
		registerUser.put("status", "fail");
		if (registerTheUser(username, password, email, firstName, lastName)) {
			registerUser.put("status", "success");
		}
		return registerUser;
	}

	@Override
	public HashMap<String, Object> getUser(String usernameOrId, Integer mode, String username) throws MalformedURLException {
		Mongo instance = getDatabaseInstance().getMongoInstance();
		User user = null;
		try {
			user = getUserData(usernameOrId);
		} catch (IllegalArgumentException ie) {
			Logger.error("Error in Get user In UserDAO", ie.fillInStackTrace());
		}

		HashMap<String, Object> userMap = new HashMap<>();
		if (user != null) {
			if (mode == User.USER_SETTINGS) {
				userMap.put("userEmailConfirmed", user.getEmailConfirmed());
			}
			if (mode == User.USER_SETTINGS || mode == User.USER_PAGE || mode == User.USER_PAGE_LOGGED_IN) {

				userMap.put("userFirstName", user.getFirstName());
				userMap.put("userLastName", user.getLastName());
				userMap.put("userDay", user.getDay());
				userMap.put("userMonth", user.getMonth());
				userMap.put("userYear", user.getYear());
				userMap.put("userEmail", user.getEmail());
				userMap.put("userBirthdayVisibility", user.getBirthdayVisibility());
				userMap.put("userUserName", user.getUsername());
				userMap.put("userCountry", user.getCountry());
				userMap.put("userGameBio", user.getGameBio());
				String avatar = user.getAvatar();
				if (!avatar.isEmpty()) {
					Logger.debug("Image available.......");
					Media media = mediaDAOInstance.getById(avatar);
					userMap.put("userAvatarUrl", media.getUrl());
					userMap.put("userAvatarId", media.getId().toString());
				} else {
					userMap.put("userAvatarUrl", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					userMap.put("userAvatarId", "none");
				}
			}

			if (mode == User.USER_PAGE || mode == User.USER_PAGE_LOGGED_IN) {
				userMap.put("userNetworkCrown", User.USER_RANK_MAP[user.getNetworkRank()]);
				userMap.put("userNextNetworkCrown", User.USER_RANK_MAP[user.getNetworkRank() + 1]);
				userMap.put("userLeaderboardRank", fetchUserLeaderBoardRank(user.getUsername()));
				userMap.put("userLevel", user.getLevel());
				userMap.put("userNextLevel", user.getLevel() + 1);
				userMap.put("userXp", user.getExperiencePoints());
				userMap.put("userNextLevelPointsToGo", Gamification.pointsToGo(user.getExperiencePoints(), user.getLevel()));
				double levelCompletionRatio = Gamification.levelCompletionRatio(user.getExperiencePoints(), user.getLevel());
				userMap.put("userLevelCompletionRatio", levelCompletionRatio * 100);
				double totalAchievementCount = Achievement.getAllAchievementCount();
				double userAchievementCount = user.getAchievements().size();
				userMap.put("userAchievementRatio",
						new DecimalFormat("###.#").format((userAchievementCount / totalAchievementCount) * 100));

				double followedRatio = 0;
				double followingRatio = 0;
				long userCount = count();
				if (userCount > 1) {
					followedRatio = ((double) user.getFollowedBy().size() / (count() - 1)) * 100;
					followingRatio = ((double) user.getFollowing().size() / (count() - 1)) * 100;
				}
				userMap.put("userFollowersRatio", new DecimalFormat("###.#").format(followedRatio));
				userMap.put("userFollowingRatio", new DecimalFormat("###.#").format(followingRatio));
				long allPublishedArticleCount = Article.allPublishedArticleCount();

				double publishedArticleRatio = 0;
				double networkTotalCoolScore = 0;
				if (allPublishedArticleCount > 0) {
					publishedArticleRatio = ((double) Article.allPublishedArticleCount(user) / allPublishedArticleCount) * 100;
					networkTotalCoolScore = RankAlgorithm.calculateNetworkTotalCoolScore(instance);
				}
				userMap.put("userPublishedRatio", new DecimalFormat("###.#").format(publishedArticleRatio));

				Logger.debug("Network Total Cool Score: " + networkTotalCoolScore);
				Logger.debug("User Total Cool Score: " + user.getUserTotalCoolScore());
				double coolScoreRatio = 0;
				if (networkTotalCoolScore > 0) {

					coolScoreRatio = (user.getUserTotalCoolScore() / networkTotalCoolScore) * 100;
				}
				userMap.put("userCoolScoreRatio", new DecimalFormat("###.#").format(coolScoreRatio));
				userMap.put("userCarouselArticles", articleDao.getAllArticlesForUserCarousel(user.getUsername()));

				if (checkUserBlockedOrNot(user, findByUsername(username))) {
					userMap.put("isBlocked", 1);
				} else {
					userMap.put("isBlocked", 0);
				}

				if (checkUserFollowerOrNot(user, findByUsername(username))) {
					userMap.put("isFollowing", 1);
				} else {
					userMap.put("isFollowing", 0);
				}

				if (mode == User.USER_PAGE_LOGGED_IN) {
					// 10 recent activities
					userMap.put("userActivities", activityDAOInstance.getActivities(user));
				}
				if (mode == User.USER_PAGE) {
					userMap.put("userActivities", activityDAOInstance.getPublicActivitiesForUser(user));
				}
			}

		}

		return userMap;
	}

	@Override
	public User findByUsername(String username) {
		return gloonDatastore.find(User.class, "username", username).get();
	}

	@Override
	public User findByEmail(String email) {

		return gloonDatastore.find(User.class, "email", email).get();
	}

	@Override
	public Long count() {
		return gloonDatastore.getCount(User.class);
	}

	@Override
	public HashMap<String, Object> saveOrUpdateUserInterest(String username, int type, String[] interests) {

		HashMap<String, Object> response = new HashMap<>();
		response.put("status", "fail");
		User user = findByUsername(username);
		try {
			Logger.debug("interests  " + interests);
			if (type == User.PLATFORM_INTEREST) {
				user.setInterestedPlatforms(interests);
				response.put("status", "success");
			} else {
				ArrayList<Genre> userInterestGenres = new ArrayList<>();
				for (String interest : interests) {
					Genre genre = null;
					if (interest.length() > 0) {
						genre = Genre.valueOf(interest);
					}
					if (genre != null) {
						userInterestGenres.add(genre);
					}

				}
				user.setInterestedGenres(userInterestGenres);
				response.put("status", "success");
			}
			save(user);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", "fail");
		}
		return response;
	}

	@Override
	public HashMap<String, Object> saveOrUpdateUser(String username, DynamicForm requestData) {
		HashMap<String, Object> response = new HashMap<>();
		User user = findByUsername(username);
		response.put("status", "fail");
		Date time = new Date();
		try {
			String firstName = requestData.get("firstName");
			user.setFirstName(firstName);
			String lastName = requestData.get("lastName");
			user.setLastName(lastName);
			String country = requestData.get("country");
			user.setCountry(country);
			Integer day = Integer.parseInt(requestData.get("day"));
			user.setDay(day);
			Integer month = Integer.parseInt(requestData.get("month"));
			user.setMonth(month);
			Integer year = Integer.parseInt(requestData.get("year"));
			user.setYear(year);
			Integer birthdayVisibility = Integer.parseInt(requestData.get("birthdayVisibility"));
			user.setBirthdayVisibility(birthdayVisibility);
			String gameBio = requestData.get("gameBio");
			user.setGameBio(gameBio);
			user.setUpdateTime(Utility.convertDateToString(time));
			save(user);
			response.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public HashMap<String, Object> resetPassword(String username, DynamicForm requestData) {
		HashMap<String, Object> response = new HashMap<>();
		response.put("status", "fail");
		User user = findByUsername(username);
		if (user != null) {
			String storedHash = user.getPasswordHash();
			String storedSalt = user.getPasswordSalt();
			String oldPassword = requestData.get("old");
			String newPassword = requestData.get("new");
			try {
				if (SecurePassword.validatePassword(oldPassword, storedHash, storedSalt)) {
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
	public HashMap<String, String> addOrRemoveBuddy(String username, String buddyUsername, Integer type) {
		Mongo instance = getDatabaseInstance().getMongoInstance();
		HashMap<String, String> response = new HashMap<>();
		response.put("status", "fail");
		User user = findByUsername(username);
		User buddy = findByUsername(buddyUsername);
		HashMap<String, String> activityMap = new HashMap<>();
		try {
			if (user != null && buddy != null) {
				Buddy originalUser = null;

				Buddy buddyUser = null;

				if (type == Buddy.ADD) {
					addBuddy(username, buddyUsername, user, buddy, activityMap);
					response.put("status", "success");
				} else {
					ArrayList<Buddy> originalUserFollowing = user.getFollowing();
					ArrayList<Buddy> buddyUserFollowedBy = buddy.getFollowedBy();

					// Fetch buddyUser instance from original user followings
					for (Buddy buddyInstance : originalUserFollowing) {
						if (buddyUsername.equalsIgnoreCase(buddyInstance.getUserName())) {
							buddyUser = buddyInstance;
						}
					}

					// Fetch originalUser instance from buddy user followed-by
					for (Buddy buddyInstance : buddyUserFollowedBy) {
						if (username.equalsIgnoreCase(buddyInstance.getUserName())) {
							originalUser = buddyInstance;
						}
					}

					originalUserFollowing.remove(buddyUser);
					user.setFollowing(originalUserFollowing);
					buddyUserFollowedBy.remove(originalUser);
					buddy.setFollowedBy(buddyUserFollowedBy);
					save(user);
					save(buddy);
					activityMap.put("id", "");
					activityMap.put("username", user.getUsername());
					activityMap.put("entityId", buddy.getId().toString());
					activityMap.put("type", "" + Activity.ACTIVITY_USER_UNFOLLOWS);
					activityMap.put("visibility", "" + Activity.PRIVATE);
					activityDAOInstance.createOrUpdateActivity(activityMap);
					response.put("status", "success");

				}
				if (buddy != null) {
					Double userFollowScore = buddy.getFollowedBy().size() / (count() * 1.0);
					buddy.setUserFollowScore(userFollowScore);
					Double articlePublishRateRatio = RankAlgorithm.calculateUserArticlePublishRateRatio(buddy.getArticlePublishRate(),
							instance);
					Double articleScoreRatio = RankAlgorithm.calculateUserArticleScoreRatio(buddy.getUserArticleScore(), instance);
					Double userTotalScore = RankAlgorithm.calculateUserScore(articlePublishRateRatio, userFollowScore,
							articleScoreRatio);
					buddy.setTotalScore(userTotalScore);
					if (gloonDatastore.getCount(UserGameScoreMap.class) > 0) {
						Query<UserGameScoreMap> updateQuery = gloonDatastore.createQuery(UserGameScoreMap.class).field("username")
								.equal(buddy.getUsername());
						UpdateOperations<UserGameScoreMap> opearations = gloonDatastore.createUpdateOperations(UserGameScoreMap.class)
								.set("networkUserWeight", RankAlgorithm.calculateUserScoreRatio(userTotalScore, instance));
						gloonDatastore.update(updateQuery, opearations);
					}

					save(buddy);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", "fail");
		}
		return response;
	}

	/**
	 * @param username
	 * @param buddyUsername
	 * @param user
	 * @param buddy
	 * @param activityMap
	 */
	private void addBuddy(String username, String buddyUsername, User user, User buddy, HashMap<String, String> activityMap) {
		Date time = new Date();
		Buddy originalUser = new Buddy();
		originalUser.setUserName(username);
		originalUser.setChatState(User.INVITE);
		originalUser.setBlockState(Buddy.UNBLOCK);
		originalUser.setInsertTime(Utility.convertDateToString(time));
		originalUser.setTimestamp(time.getTime());

		Buddy buddyUser = new Buddy();
		buddyUser.setUserName(buddyUsername);
		buddyUser.setChatState(User.INVITE);
		buddyUser.setBlockState(Buddy.UNBLOCK);
		buddyUser.setInsertTime(Utility.convertDateToString(time));
		buddyUser.setTimestamp(time.getTime());

		ArrayList<Buddy> originalUserFollowing = user.getFollowing();
		if (!originalUserFollowing.contains(buddyUser)) {
			originalUserFollowing.add(buddyUser); // Now following buddy user
			user.setFollowing(originalUserFollowing);
		}

		ArrayList<Buddy> buddyUserFollowedBy = buddy.getFollowedBy();
		if (!buddyUserFollowedBy.contains(originalUser)) {
			buddyUserFollowedBy.add(originalUser);// Buddy user getting followed by original user
			updateUserPoints(buddy, XPTriggerPoints.FOLLOWED_BY);
			buddy.setFollowedBy(buddyUserFollowedBy);
		}

		save(user);
		save(buddy);

		activityMap.put("id", "");
		activityMap.put("username", user.getUsername());
		activityMap.put("entityId", buddy.getId().toString());
		activityMap.put("type", "" + Activity.ACTIVITY_USER_FOLLOWS);
		activityMap.put("visibility", "" + Activity.PUBLIC);
		activityDAOInstance.createOrUpdateActivity(activityMap);
	}

	@Override
	public HashMap<String, String> validateEmail(String email) {
		HashMap<String, String> response = new HashMap<>();
		response.put("status", "success");
		User user = gloonDatastore.find(User.class, "email", email).get();
		if (user != null) {
			response.put("status", "fail");
		}
		return response;
	}

	@Override
	public HashMap<String, String> validateUsername(String username) {
		HashMap<String, String> response = new HashMap<>();
		response.put("status", "success");
		User user = gloonDatastore.find(User.class, "username", username).get();
		if (user != null) {
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
	private Boolean checkUserBlockedOrNot(User user, User buddyUser) {
		Boolean response = false;

		if (user != null && buddyUser != null) {
			ArrayList<Buddy> userFollowedBy = user.getFollowedBy();
			for (Buddy userInstance : userFollowedBy) {
				if (userInstance.getUserName().equals(buddyUser.getUsername()) && userInstance.getBlockState() == Buddy.BLOCK) {
					response = true;
					break;
				}
			}

			ArrayList<Buddy> buddyUserFollowedBy = buddyUser.getFollowedBy();
			for (Buddy userInstance : buddyUserFollowedBy) {
				if (userInstance.getUserName().equals(user.getUsername()) && userInstance.getBlockState() == Buddy.BLOCK) {
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
	private Boolean checkUserFollowerOrNot(User user, User buddyUser) {
		Boolean response = false;
		if (user != null && buddyUser != null) {
			ArrayList<Buddy> buddyUserFollowings = buddyUser.getFollowing();
			for (Buddy buddy : buddyUserFollowings) {
				if (user.getUsername().equalsIgnoreCase(buddy.getUserName())) {
					response = true;
					break;
				}
			}
		}

		return response;
	}

	@Override
	public HashMap<String, String> blockOrUnblockBuddy(String username, String buddyUsername, Integer type) {
		HashMap<String, String> response = new HashMap<>();
		response.put("status", "fail");
		User user = findByUsername(username);
		User buddy = findByUsername(buddyUsername);
		HashMap<String, String> activityMap = new HashMap<>();
		try {
			if (user != null && buddy != null) {

				Buddy originalUser = null;
				Buddy buddyUser = null;
				if (type == Buddy.BLOCK) // Block followed by user
				{
					ArrayList<Buddy> originalUserFollowedBy = user.getFollowedBy();
					for (Buddy buddyInstance : originalUserFollowedBy) {
						if (buddyUsername.equalsIgnoreCase(buddyInstance.getUserName())) {
							buddyInstance.setBlockState(Buddy.BLOCK);
							break;
						}
					}

					ArrayList<Buddy> originalUserFollowing = user.getFollowing();
					for (Buddy buddyInstance : originalUserFollowing) {
						if (buddyUsername.equalsIgnoreCase(buddyInstance.getUserName())) {
							buddyUser = buddyInstance;
							break;
						}
					}

					if (buddyUser != null) {
						originalUserFollowing.remove(buddyUser);
						user.setFollowing(originalUserFollowing);
					}

					ArrayList<Buddy> buddyUserFollowing = buddy.getFollowing();
					for (Buddy buddyInstance : buddyUserFollowing) {
						if (username.equalsIgnoreCase(buddyInstance.getUserName())) {
							originalUser = buddyInstance;
							break;
						}
					}

					ArrayList<Buddy> buddyUserFollowedBy = buddy.getFollowedBy();
					if (originalUser != null) {
						buddyUserFollowing.remove(originalUser);// remove original user from buddy's following list
						buddy.setFollowing(buddyUserFollowing);

						if (buddyUserFollowedBy.contains(originalUser)) {
							buddyUserFollowedBy.remove(originalUser);// remove original user from buddy's following list
							buddy.setFollowedBy(buddyUserFollowedBy);
						}
					}
					save(user);
					save(buddy);
					activityMap.put("id", "");
					activityMap.put("username", user.getUsername());
					activityMap.put("entityId", buddy.getId().toString());
					activityMap.put("type", "" + Activity.ACTIVITY_BLOCK);
					activityMap.put("visibility", "" + Activity.PRIVATE);
					activityDAOInstance.createOrUpdateActivity(activityMap);
					response.put("status", "success");
				} else // unblock user
				{
					ArrayList<Buddy> originalUserFollowedBy = user.getFollowedBy();
					for (Buddy buddyInstance : originalUserFollowedBy) {
						if (buddyUsername.equalsIgnoreCase(buddyInstance.getUserName())) {
							buddyInstance.setBlockState(Buddy.UNBLOCK);
							break;
						}
					}

					ArrayList<Buddy> buddyUserFollowing = buddy.getFollowing();
					for (Buddy buddyInstance : originalUserFollowedBy) {
						if (username.equalsIgnoreCase(buddyInstance.getUserName())) {
							originalUser = buddyInstance;
							break;
						}
					}

					if (originalUser != null) {
						buddyUserFollowing.add(originalUser);// remove original user from buddy's following list
						buddy.setFollowing(buddyUserFollowing);
					}
					save(user);
					save(buddy);
					activityMap.put("id", "");
					activityMap.put("username", user.getUsername());
					activityMap.put("entityId", buddy.getId().toString());
					activityMap.put("type", "" + Activity.ACTIVITY_UNBLOCK);
					activityMap.put("visibility", "" + Activity.PRIVATE);
					activityDAOInstance.createOrUpdateActivity(activityMap);
					response.put("status", "success");

				}
				if (originalUser == null) {
					response.put("status", "fail");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", "fail");
		}

		return response;
	}

	@Override
	public HashMap<String, String> addOrRemoveInterestedGames(String username, String gameId, Integer type) {
		HashMap<String, String> response = new HashMap<>();
		response.put("status", "fail");
		User user = findByUsername(username);
		Game game = gloonDatastore.get(Game.class, new ObjectId(gameId));
		Date time = new Date();
		HashMap<String, String> activityMap = new HashMap<>();
		if (user != null || game != null) // add game and user in their respective lists
		{
			InterestedGame interestedGame = null;
			InterestedUser interestedUser = null;
			if (type == InterestedGame.INTERESTED) {
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

				if (!interestedGames.contains(interestedGame)) {
					interestedGames.add(interestedGame);
					user.setFollowingGames(interestedGames);
				}

				if (!interestedUsers.contains(interestedUser)) {
					interestedUsers.add(interestedUser);
					game.setInterestedIn(interestedUsers);
				}

				save(user);
				gloonDatastore.save(game);
				activityMap.put("id", "");
				activityMap.put("username", user.getUsername());
				activityMap.put("entityId", gameId);
				activityMap.put("type", "" + Activity.ACTIVITY_FOLLOW_GAME);
				activityMap.put("visibility", "" + Activity.PUBLIC);
				activityDAOInstance.createOrUpdateActivity(activityMap);
				response.put("status", "success");

			} else // remove game and user from their respective lists
			{
				ArrayList<InterestedGame> interestedGames = user.getFollowingGames();
				ArrayList<InterestedUser> interestedUsers = game.getInterestedIn();

				for (InterestedGame gameInstance : interestedGames) {
					if (gameId.equals(gameInstance.getGameId())) {
						interestedGame = gameInstance;
						break;
					}
				}
				interestedGames.remove(interestedGame);
				user.setFollowingGames(interestedGames);

				for (InterestedUser userInstance : interestedUsers) {
					if (username.equals(userInstance.getUserName())) {
						interestedUser = userInstance;
						break;
					}
				}
				interestedUsers.remove(interestedUser);
				game.setInterestedIn(interestedUsers);
				save(user);
				gloonDatastore.save(game);
				activityMap.put("id", "");
				activityMap.put("username", user.getUsername());
				activityMap.put("entityId", gameId);
				activityMap.put("type", "" + Activity.ACTIVITY_UNFOLLOW_GAME);
				activityMap.put("visibility", "" + Activity.PRIVATE);
				activityDAOInstance.createOrUpdateActivity(activityMap);
				response.put("status", "success");
			}

			if (interestedGame == null || interestedUser == null) {
				response.put("status", "fail");
			}
		}
		return response;
	}

	@Override
	public HashMap<String, String> saveOrUpdateUserAvatar(String mediaId, String username, FilePart avatarPart) {

		HashMap<String, String> response = new HashMap<>();
		response = mediaDAOInstance.createOrUpdateMedia(mediaId, avatarPart, username, Media.USER);
		if (response.get("status").equalsIgnoreCase("success")) {
			User user = findByUsername(username);
			String fetchedMediaId = response.get("mediaId");
			String lastAvatarState = user.getAvatar();
			user.setAvatar(response.get("mediaId"));
			Media media = mediaDAOInstance.getById(fetchedMediaId);
			try {
				response.put("avatarPath", media.getUrl());
				response.put("avatarId", media.getId().toString());
			} catch (MalformedURLException e) {
				response.put("avatarPath", "");
				Logger.error("Url is malformed " + e.fillInStackTrace());
			}
			save(user);
			if (lastAvatarState.isEmpty()) {
				updateUserXP(user, XPTriggerPoints.USER_AVATAR_UPLOADED);
			}
		}

		return response;
	}

	@Override
	public List<User> getTopUsers(int limit) {
		if (limit > 0) {
			return gloonDatastore.createQuery(User.class).order("-experiencePoints").limit(limit).asList();
		} else {
			return gloonDatastore.createQuery(User.class).order("-experiencePoints").asList();
		}
	}

	@Override
	public HashMap<String, Object> fetchSocial(String username) throws MalformedURLException {
		HashMap<String, Object> userSocialMap = new HashMap<>();
		User user = findByUsername(username);
		Logger.debug("User whose social data fetched: " + user);
		MediaDAO mediaDAOInstance = MediaDAO.instantiateDAO();
		userSocialMap.put("status", "fail");
		try {
			// games
			ArrayList<InterestedGame> interestedGames = user.getFollowingGames();
			if (interestedGames != null && interestedGames.size() > 0) {
				Collections.sort(interestedGames, new Comparator<InterestedGame>() {

					@Override
					public int compare(InterestedGame instance1, InterestedGame instance2) {
						return instance2.getTimestamp().compareTo(instance1.getTimestamp());
					}

				});

				ArrayList<HashMap<String, Object>> interestedGameListMaps = new ArrayList<>();
				for (InterestedGame selectedGame : interestedGames) {
					HashMap<String, Object> interestedGameMap = new HashMap<>();
					Game game = gameDAOInstance.getGameById(selectedGame.getGameId());
					interestedGameMap.put("interestedGameId", game.getId().toString());
					interestedGameMap.put("interestedGameTitle", game.getTitle());
					String gameBoxShot = game.getGameBoxShot();
					if (gameBoxShot.isEmpty()) {
						interestedGameMap.put("interestedGameBoxShot", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/boxShot.png");
					} else {
						Media media = mediaDAOInstance.getById(gameBoxShot);
						interestedGameMap.put("interestedGameBoxShot", media.getUrl());
					}
					interestedGameMap.put("interestedGameURL", Utility.encodeForUrl(game.getTitle()) + "-" + game.getId().toString());
					interestedGameMap.put("interestedGameTimestamp", selectedGame.getTimestamp());
					interestedGameListMaps.add(interestedGameMap);
				}

				userSocialMap.put("userInterestedGames", interestedGameListMaps);

			} else {
				userSocialMap.put("userInterestedGames", new ArrayList<>());
			}

			// followers
			ArrayList<Buddy> followedBy = user.getFollowedBy();

			if (followedBy != null && followedBy.size() > 0) {
				Collections.sort(followedBy, new Comparator<Buddy>() {

					@Override
					public int compare(Buddy instance1, Buddy instance2) {
						return instance2.getTimestamp().compareTo(instance1.getTimestamp());
					}

				});

				ArrayList<HashMap<String, Object>> buddyListMaps = new ArrayList<>();
				for (Buddy buddy : followedBy) {
					HashMap<String, Object> buddyMap = new HashMap<>();
					User buddyUser = findByUsername(buddy.getUserName());
					buddyMap.put("buddyUserId", buddyUser.getId().toString());
					buddyMap.put("buddyUsername", buddyUser.getUsername());
					String buddyAvatarImage = buddyUser.getAvatar();
					if (buddyAvatarImage.isEmpty()) {
						buddyMap.put("buddyUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					} else {
						Media media = mediaDAOInstance.getById(buddyAvatarImage);
						buddyMap.put("buddyUserAvatar", media.getUrl());
					}
					buddyMap.put("buddyUserTimestamp", buddy.getTimestamp());
					buddyListMaps.add(buddyMap);
				}

				userSocialMap.put("userFollowedBy", buddyListMaps);

			} else {
				userSocialMap.put("userFollowedBy", new ArrayList<>());
			}

			// followings
			ArrayList<Buddy> followings = user.getFollowing();

			if (followings != null && followings.size() > 0) {
				Collections.sort(followings, new Comparator<Buddy>() {

					@Override
					public int compare(Buddy instance1, Buddy instance2) {
						return instance2.getTimestamp().compareTo(instance1.getTimestamp());
					}

				});
				ArrayList<HashMap<String, Object>> buddyListMaps = new ArrayList<>();
				for (Buddy buddy : followings) {
					HashMap<String, Object> buddyMap = new HashMap<>();
					User buddyUser = findByUsername(buddy.getUserName());
					buddyMap.put("buddyUserId", buddyUser.getId().toString());
					buddyMap.put("buddyUsername", buddyUser.getUsername());
					String buddyAvatarImage = buddyUser.getAvatar();
					if (buddyAvatarImage.isEmpty()) {
						buddyMap.put("buddyUserAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					} else {
						Media media = mediaDAOInstance.getById(buddyAvatarImage);
						buddyMap.put("buddyUserAvatar", media.getUrl());
					}
					buddyMap.put("buddyUserTimestamp", buddy.getTimestamp());
					buddyListMaps.add(buddyMap);
				}

				userSocialMap.put("userFollowingOthers", buddyListMaps);

			} else {
				userSocialMap.put("userFollowingOthers", new ArrayList<>());
			}
			userSocialMap.put("status", "success");
		} catch (Exception exception) {
			Logger.error("Error in Fetching social information for user", exception.fillInStackTrace());
			exception.printStackTrace();
		}

		return userSocialMap;
	}

	@Override
	public HashMap<String, Object> fetchStats(String username) {
		HashMap<String, Object> userStatsMap = new HashMap<>();
		userStatsMap.put("status", "fail");
		final User user = findByUsername(username);
		try {
			ArrayList<Achievement> userAchievements = user.getAchievements();
			if (userAchievements != null && userAchievements.size() > 0) {
				userStatsMap.put("userAchievements", userAchievements);

			} else {
				userStatsMap.put("userAchievements", new ArrayList<>());
			}
			userStatsMap.put("status", "success");
		} catch (Exception exception) {
			Logger.error("Error in Fetching User stats", exception.fillInStackTrace());
			exception.printStackTrace();
		}

		return userStatsMap;
	}

	@Override
	public void updateUserXP(User user, XPTriggerPoints xpTriggerPoints) {

		if (user != null) {
			Logger.debug("User: " + user);
			updateUserPoints(user, xpTriggerPoints);
			Logger.debug("Points: " + user.getExperiencePoints());
			save(user);
		}

	}

	@Override
	public List<HashMap<String, Object>> fetchTopThreeUsers() throws MalformedURLException {
		List<HashMap<String, Object>> topperMaps = new ArrayList<>();
		List<User> users = getTopUsers(3);

		if (users.size() > 0 || users != null) {
			for (User user : users) {
				HashMap<String, Object> userMap = new HashMap<>();
				userMap.put("userName", user.getUsername());
				userMap.put("userXp", user.getExperiencePoints());
				String userAvatar = user.getAvatar();
				if (StringUtil.isNullOrEmpty(userAvatar)) {
					userMap.put("userAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
				} else {
					Media media = mediaDAOInstance.getById(userAvatar);
					userMap.put("userAvatar", media.getUrl());
				}
				topperMaps.add(userMap);
			}
		}

		return topperMaps;
	}
	
	@Override
	public HashMap<String, String> checkConfirmStatusForUser(String username, String confirmationToken) {
		HashMap<String, String> response = new HashMap<>();
		response.put("status", "fail");
		User user = findByUsername(username);
		int confirmToken = Integer.parseInt(confirmationToken);
		if(confirmToken!=-1 && confirmToken == user.getEmailConfirmToken())
		{
			user.setEmailConfirmed(User.EMAIL_CONFIRMED);
			user.setEmailConfirmToken(-1);
			save(user);
			response.put("status", "success");
		}
		return response;
	}

	@Override
	public void sendConfirmMail(User user) {
		MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
		int randToken = new Random().nextInt(Integer.MAX_VALUE)+1;
		user.setEmailConfirmToken(randToken);
		save(user);
		mail.setSubject("Welcome to Gamealoon!");
		mail.addRecipient(user.getEmail());
		mail.addFrom("Team Gamealoon <noreply@gamealoon.com>");
		mail.sendHtml("<html><p>Hi "
				+ user.getUsername()
				+ ",</p><p>Gamealoon welcomes you to the fun gameocratic world of video gaming. Tell the world that how much passionate you are about video gaming and also follow other gloonies to find out what they think about gaming. And guess what, doing so will also earn you XP, level up and fetch a rank in leaderboards. That is not all, you don't know, with all those accumulated points, you might be able to unlock a gift or two for you in future. So what are you waiting for, keep gaming and bragging about your gaming passion on Gamealoon. Please click or copy & paste to your browser's URL bar the following link to confirm your email and happy gaming." 
				+"<p><a target='_blank' href='http://www.gamealoon.com/confirmuser/"+user.getUsername()+"/"+user.getEmailConfirmToken()+"'>http://www.gamealoon.com/confirmuser/"+user.getUsername()+"/"+user.getEmailConfirmToken()+"</a></p></p><p>Thanks and Regards,<br> Team Gamealoon<br><a target='_blank' href='http://www.gamealoon.com'>www.gamealoon.com</a></p></html>");
	}

	/**
	 * Register user. simply create new user object and feed username, password and email
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @return
	 */
	private boolean registerTheUser(String username, String password, String email, String firstName, String lastName) {
		Mongo instance = getDatabaseInstance().getMongoInstance();
		Boolean response = false;
		Date time = new Date();
		try {
			User newUser = new User();
			Achievement achievement = achievementDAOInstance.findByTitle("New Gloonie!");
			Logger.debug("Achievement " + achievement);
			if (achievement == null) {
				throw new RuntimeException("Achievement is null");
			} else {
				ArrayList<Achievement> achievements = new ArrayList<>();
				achievements.add(achievement);
				newUser.setAchievements(achievements);
			}
			newUser.setUsername(username);
			HashMap<String, String> securePasswordMap = SecurePassword.createHash(password);
			newUser.setPasswordHash(securePasswordMap.get("hashHex"));
			newUser.setPasswordSalt(securePasswordMap.get("saltHex"));
			newUser.setEmail(email);
			newUser.setEmailConfirmed(User.EMAIL_NOT_CONFIRMED);
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);
			Calendar now = GregorianCalendar.getInstance();
			newUser.setDay(now.get(Calendar.DAY_OF_MONTH));
			newUser.setMonth(now.get(Calendar.MONTH) + 1);
			newUser.setYear(now.get(Calendar.YEAR));
			newUser.setGameBio("I love gaming.");
			newUser.setCountry("India");
			newUser.setBirthdayVisibility(User.PRIVATE);
			newUser.setAvatar("");
			newUser.setArticlePublishRate(0.0);
			newUser.setFollowedBy(new ArrayList<Buddy>());
			newUser.setFollowing(new ArrayList<Buddy>());
			newUser.setFollowingGames(new ArrayList<InterestedGame>());
			newUser.setInterestedGenres(new ArrayList<Genre>());
			String[] interestedPlatforms = {};
			newUser.setInterestedPlatforms(interestedPlatforms);
			newUser.setTotalScore(0.0);
			newUser.setUserArticleScore(0.0);
			newUser.setUserFollowScore(0.0);
			newUser.setUserTotalCoolScore(0.0);
			newUser.setInsertTime(Utility.convertDateToString(time));
			newUser.setTimestamp(time.getTime());
			newUser.setExperiencePoints(0);
			newUser.setLevel(0);
			newUser.setGamealoonPoints(0);
			newUser.setNetworkRank(GloonNetworkRank.NEW_GLOONIE.getRankValue());
			save(newUser);
			//sendConfirmMail(newUser);

			// Reactively update all user scores as no of users increase
			for (User user : getTopUsers(0)) {
				if (!newUser.getUsername().equalsIgnoreCase(user.getUsername())) {
					double userFollowedByScore = user.getFollowedBy().size() / (count() * 1.0);
					user.setUserFollowScore(userFollowedByScore);
					double articlePublishRateRatio = 0;
					if (user.getArticlePublishRate() > 0) {
						articlePublishRateRatio = RankAlgorithm.calculateUserArticlePublishRateRatio(user.getArticlePublishRate(),
								instance);
					}
					double articleScoreRatio = 0;
					if (user.getUserArticleScore() > 0) {
						articleScoreRatio = RankAlgorithm.calculateUserArticleScoreRatio(user.getUserArticleScore(), instance);
					}

					double userTotalScore = RankAlgorithm.calculateUserScore(articlePublishRateRatio, userFollowedByScore,
							articleScoreRatio);
					user.setTotalScore(userTotalScore);
					save(user);
				}

			}
			HashMap<String, String> activityMap = new HashMap<>();
			activityMap.put("id", "");
			activityMap.put("username", newUser.getUsername());
			activityMap.put("entityId", achievement.getId().toString());
			activityMap.put("type", "" + Activity.ACTIVITY_NEW_ACHIEVMENT);
			activityMap.put("visibility", "" + Activity.PRIVATE);
			activityDAOInstance.createOrUpdateActivity(activityMap);
			response = true;
		} catch (Exception ex) {
			Logger.error("Error in UserDAO at registerUser ", ex.fillInStackTrace());
			response = false;
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
	private User checkUser(String usernameOrEmail, String password) {
		User user = findByUsername(usernameOrEmail);
		if (user == null) {
			user = findByEmail(usernameOrEmail);
		}

		if (user != null) {
			String storedHash = user.getPasswordHash();
			String storedSalt = user.getPasswordSalt();
			try {
				if (!SecurePassword.validatePassword(password, storedHash, storedSalt)) {
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
	private User getUserData(String usernameOrId) throws IllegalArgumentException {
		User user = gloonDatastore.createQuery(User.class).filter("username", usernameOrId).get();
		if (user == null) {
			user = gloonDatastore.get(User.class, new ObjectId(usernameOrId));
		}
		return user;
	}

	/**
	 * Updates the the following for user:
	 * <ul>
	 * <li>Experience Points</li>
	 * <li>Level</li>
	 * <li>Gamealoon Points</li>
	 * </ul>
	 * 
	 * @param user {@link User} User for which points are to be updated
	 * @param xpTriggerPoints {@link XPTriggerPoints} Specifies the event through which experience points will be awarded
	 */
	private void updateUserPoints(final User user, final XPTriggerPoints xpTriggerPoints) {
		long originalXP = user.getExperiencePoints();
		long updatedXP = originalXP + xpTriggerPoints.getExperiencePoints();
		user.setExperiencePoints(updatedXP);
		int updatedUserLevel = Gamification.calculateLevel(updatedXP, user.getLevel());
		int updatedGamealoonPoints = Gamification.calculateGP(updatedXP, user.getLevel());
		int updatedUserNetworkRank = Gamification.calculateNetworkRank(updatedUserLevel, user.getNetworkRank());
		user.setLevel(updatedUserLevel);
		user.setGamealoonPoints(updatedGamealoonPoints);
		user.setNetworkRank(updatedUserNetworkRank);
	}

	private int fetchUserLeaderBoardRank(String username) {
		int rank = 0;
		List<User> sortedByXP = getTopUsers(0);
		for (int index = 0; index < sortedByXP.size(); ++index) {
			if (username.equalsIgnoreCase(sortedByXP.get(index).getUsername())) {
				rank = index + 1;
				break;
			}
		}
		return rank;
	}

	

}
