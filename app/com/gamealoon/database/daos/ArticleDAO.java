package com.gamealoon.database.daos;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import play.Logger;
import play.data.DynamicForm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamealoon.algorithm.RankAlgorithm;
import com.gamealoon.core.common.PostHotness;
import com.gamealoon.core.common.XPTriggerPoints;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.ArticleInterface;
import com.gamealoon.models.Activity;
import com.gamealoon.models.Article;
import com.gamealoon.models.Category;
import com.gamealoon.models.Game;
import com.gamealoon.models.Media;
import com.gamealoon.models.User;
import com.gamealoon.models.UserArticleVotingMap;
import com.gamealoon.models.UserGameScoreMap;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.StringUtil;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.Mongo;

public class ArticleDAO extends GloonDAO<Article> implements ArticleInterface {

	private static final ArticleDAO DATA_ACCESS_LAYER = new ArticleDAO();
	private static final GameDAO gameDaoInstance = GameDAO.instantiateDAO();
	private static final PlatformDAO platformDaoInstance = PlatformDAO.instantiateDAO();
	private static final UserDAO userDaoInstance = UserDAO.instantiateDAO();
	private static final MediaDAO mediaDaoInstance = MediaDAO.instantiateDAO();
	private static final ArticleMediaMapDAO articleMediaMapDaoInstance = ArticleMediaMapDAO.instantiateDAO();
	private static final ActivityDAO activityDaoInstance = ActivityDAO.instantiateDAO();
	private static final ConversationDAO conversationDaoInstance = ConversationDAO.instantiateDAO();
	private static final UserArticleVotingMapDAO articleVotingMapDAOInstance = UserArticleVotingMapDAO.instantiateDAO();
	private Datastore gloonDatastore = null;

	private ArticleDAO() {
		super();
		gloonDatastore = initDatastore();
	}

	/**
	 * Singleton way to instantiate Gloon DAO
	 * 
	 * @return
	 */
	public static ArticleDAO instantiateDAO() {
		return DATA_ACCESS_LAYER;
	}

	/**
	 * This method returns a List of hashTables or maps which is in the following format:
	 * 
	 * category: <Category> //Is one of Review, video, news, feature and gloonicle articles:[article1, article2, article3, ...]
	 * 
	 * The list will contain upto 5 hashMaps in general.
	 * 
	 * @throws MalformedURLException
	 */
	@Override
	public HashMap<String, Object> getAllArticlesForCarousel(String platform) throws MalformedURLException {

		List<User> topUsers = userDaoInstance.getTopUsers(0);
		HashMap<String, Object> allArticlesForCarouselMap = new HashMap<String, Object>();

		// Generating reviews
		try {
			Logger.debug(topUsers.toString());
			allArticlesForCarouselMap.put("carouselReviews", getReviewsForCarousel(topUsers, platform));
			Logger.debug(topUsers.toString());
			// Generating feature
			allArticlesForCarouselMap.put("carouselFeatures", getArticlesForCarousel(topUsers, platform, Category.Feature));
			Logger.debug(topUsers.toString());
			// Generating news
			allArticlesForCarouselMap.put("carouselNews", getArticlesForCarousel(topUsers, platform, Category.News));

			Logger.debug(topUsers.toString());
			// Generating gloonicle
			allArticlesForCarouselMap.put("carouselGloonicles", getArticlesForCarousel(topUsers, platform, Category.Gloonicle));
			Logger.debug(topUsers.toString());
			// Generating video
			allArticlesForCarouselMap.put("carouselVideos", getArticlesForCarousel(topUsers, platform, Category.Video));
		} catch (ParseException e) {
			Logger.error("Error in ArticleDAO getAllArticlesForCarousel ", e.fillInStackTrace());
		}

		return allArticlesForCarouselMap;
	}

	@Override
	public List<HashMap<String, Object>> getAllArticlesForUserCarousel(String username) throws MalformedURLException {
		List<HashMap<String, Object>> carouselArticles = new ArrayList<>();

		try {
			carouselArticles = getCarouselArticlesForUserMap(username, "all");
		} catch (ParseException e) {
			Logger.error("Error in ArticleDAO getAllArticlesForUSerCarousel ", e.fillInStackTrace());
		}
		return carouselArticles;
	}

	@Override
	public HashMap<String, Object> getAllArticlesForGameCarousel(String gameId) throws MalformedURLException {
		HashMap<String, Object> gameCarouselMap = new HashMap<>();
		List<HashMap<String, Object>> carouselActivities = new ArrayList<>();
		List<HashMap<String, Object>> carouselVideos = new ArrayList<>();
		List<HashMap<String, Object>> carouselArticles = new ArrayList<>();
		try {
			carouselArticles = getGameCarouselArticles(gameId, "all");
			carouselVideos = getGameCarouselArticles(gameId, "video");
			carouselActivities = getGameCarouselActivities(gameId);
			gameCarouselMap.put("all", carouselArticles);
			gameCarouselMap.put("videos", carouselVideos);
		} catch (ParseException e) {
			Logger.error("Error in ArticleDAO in get all articles for game carousel", e.fillInStackTrace());
			e.printStackTrace();
		}

		gameCarouselMap.put("activites", carouselActivities);

		return gameCarouselMap;
	}

	@Override
	public HashMap<String, Object> getArticle(String currentUser, String titleOrId) throws MalformedURLException {
		Logger.debug("Current User: " + currentUser);
		Mongo instance = getDatabaseInstance().getMongoInstance();
		Article article = null;
		try {
			article = getArticleData(gloonDatastore, titleOrId);
		} catch (IllegalArgumentException ie) {
			Logger.error("Error in ArticleDAO getArticle", ie.fillInStackTrace());
		}
		HashMap<String, Object> response = new HashMap<>();
		if (article != null) {
			response.put("articleId", article.getId().toString());
			response.put("articleTitle", article.getTitle());
			response.put("articleBody", article.getBody());
			Category category = article.getCategory();
			response.put("articleCategory", category.toString());
			response.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle()) + "-" + article.getId().toString());
			response.put("articleVideoLink",article.getVideoUrl());			
			response.put("articleHotnessLevel", Article.ARTICLE_HOTNESS_MAP[article.getHotness()]);
			String publishDate = article.getPublishDate();
			try {
				if (publishDate != null) {
					response.put("articlePublishDate", Utility.convertFromOneFormatToAnother(publishDate));
				} else {
					response.put("articlePublishDate", "");
				}
			} catch (ParseException e) {
				response.put("articlePublishDate", article.getPublishDate());
				e.printStackTrace();
			}
			HashMap<String, Object> userMap = new HashMap<>();			
			User author = userDaoInstance.findByUsername(article.getAuthor());
			Logger.debug("USER PUBLISH RATE RATIO "
					+ RankAlgorithm.calculateUserArticlePublishRateRatio(author.getArticlePublishRate(), instance));
			userMap.put("articleAuthorUsername", author.getUsername());
			String authorAvatar = author.getAvatar();
			if (authorAvatar.isEmpty()) {
				userMap.put("articleAuthorAvatarPath", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
			} else {
				Media media = mediaDaoInstance.getById(authorAvatar);
				userMap.put("articleAuthorAvatarPath", media.getUrl());
			}
			response.put("articleAuthor", userMap);
			response.put("articleCoolScore", (int) article.getCoolScore());
			response.put("articleNotCoolScore", (int) article.getNotCoolScore());
			UserArticleVotingMap articleVotingMap = articleVotingMapDAOInstance.fetchByUsernameAndArticle(currentUser, article);
			if (articleVotingMap != null) {
				response.put("articleUserCoolState", articleVotingMap.getCool());
				response.put("articleUserNotCoolState", articleVotingMap.getNotCool());
			} else {
				response.put("articleUserCoolState", UserArticleVotingMap.UNSET);
				response.put("articleUserNotCoolState", UserArticleVotingMap.UNSET);
			}
			try {
				response.put("articleCommentCount", conversationDaoInstance.getComments(article.getId().toString()).size());
			} catch (ParseException parseEx) {
				Logger.error("Error in ArticleDAO getArticle", parseEx.fillInStackTrace());
			}
			String articleFeatureImage = article.getFeaturedImage();
			if (articleFeatureImage.isEmpty() || "default".equalsIgnoreCase(articleFeatureImage)) {
				response.put("articleFeaturedImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/featuredBg.png");
				response.put("articleFeaturedImageId", "default");
			} else {
				Media media = mediaDaoInstance.getById(articleFeatureImage);
				response.put("articleFeaturedImage", media.getUrl());
				response.put("articleFeaturedImageId", media.getId().toString());
			}

			if (Category.News.equals(category) || Category.Video.equals(category)) {
				String gameId = article.getGame();
				if (!StringUtil.isNullOrEmpty(gameId)) {
					Game game = gameDaoInstance.getGameById(article.getGame());
					HashMap<String, Object> gameMap = new HashMap<>();
					gameMap.put("gameId", game.getId().toString());
					gameMap.put("title", game.getTitle());
					gameMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle()) + "-" + game.getId().toString());
					String gameBoxShot = game.getGameBoxShot();
					if (StringUtil.isNullOrEmpty(gameBoxShot)) {
						gameMap.put("boxshotPath", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/boxShot.png");
					} else {
						Media media = mediaDaoInstance.getById(gameBoxShot);
						gameMap.put("boxshotPath", media.getUrl());
					}
					response.put("articleGame", gameMap);
				} else {
					response.put("articleGame", "");
				}
			}
			if (Category.Review.equals(category)) {
				String reviewPlayedOnPlatform = article.getPlayedOnPlatform();
				response.put("articleReviewPlayedOnPlatform", reviewPlayedOnPlatform);
				response.put("articleReviewPlayedOnPlatformFullTitle", platformDaoInstance.findByShortTitle(reviewPlayedOnPlatform)
						.getTitle());
				response.put("articleReviewSweetPoints", article.getSweets());
				response.put("articleReviewStinkPoints", article.getStinks());
				String gameId = article.getGame();
				if (gameId != null) {
					Game game = gameDaoInstance.getGameById(article.getGame());
					HashMap<String, Object> gameMap = new HashMap<>();
					gameMap.put("gameId", game.getId().toString());
					gameMap.put("title", game.getTitle());
					gameMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle()) + "-" + game.getId().toString());
					String gameBoxShot = game.getGameBoxShot();
					if (StringUtil.isNullOrEmpty(gameBoxShot)) {
						gameMap.put("boxshotPath", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/boxShot.png");
					} else {
						Media media = mediaDaoInstance.getById(gameBoxShot);
						gameMap.put("boxshotPath", media.getUrl());
					}
					Double gameScore = 0.0;
					if (gloonDatastore.getCount(UserGameScoreMap.class) > 0) {
						gameScore = RankAlgorithm.calculateNetworkGameScore(game.getId().toString(), instance);
						if (gameScore.compareTo(game.getTotalScore()) != 0) {
							game.setTotalScore(gameScore);
							gameDaoInstance.save(game);
						}

					}
					gameMap.put("gameScore", gameScore);
					gameMap.put("gameNetworkScale", Game.getGameNetworkRating(gameScore));
					UserGameScoreMapDAO userGameMap = UserGameScoreMapDAO.instantiateDAO();
					List<UserGameScoreMap> userWhoScoredGameList = userGameMap.findAllByGameId(gameId);
					gameMap.put("totalUsersWhoScoredGame", userWhoScoredGameList.size());
					response.put("articleGame", gameMap);
					UserGameScoreMap scoreMap = userGameMap.findByUserAndGame(author.getUsername(), game.getId().toString());
					if (scoreMap != null) {
						response.put("articleGameUserScore", scoreMap.getGameScore());
						Double rawScore =scoreMap.getGameScore()*10; 
						response.put("articleGameUserRawScore", rawScore.intValue());
					} else {
						response.put("articleGameUserScore", 0);
					}

				} else {
					response.put("articleGame", "");
				}
			}
			if(Category.News.equals(category))
			{
				response.put("articleNewsLink", article.getSourceUrl());
			}

			response.put("articleState", article.getState());
			ArrayList<HashMap<String, Object>> comments = new ArrayList<>();
			try {
				comments = conversationDaoInstance.getComments(article.getId().toString());
			} catch (ParseException pe) {
				Logger.error("Error in ArticleDAO getArticle", pe.fillInStackTrace());
			}
			response.put("articleComments", comments);
		}
		return response;
	}

	@Override
	public List<HashMap<String, Object>> getNArticlesByCarouselSelectorAndCategory(String carouselSelector, String category,
			Long timestamp, Integer mode) throws MalformedURLException {
		List<Article> articles = getNRecentArticles(gloonDatastore, 10, category, carouselSelector, timestamp, mode);
		List<HashMap<String, Object>> articleLists = new ArrayList<>();
		if (articles.size() > 0) {

			for (Article article : articles) {
				if (article != null) {
					HashMap<String, Object> response = new HashMap<>();
					response.put("articleId", article.getId().toString());
					response.put("articleTitle", article.getTitle());
					response.put("articleBody", article.getBody());
					response.put("articleCategory", article.getCategory().toString());
					response.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle()) + "-" + article.getId().toString());
					response.put("articlePublishDate", article.getPublishDate());
					response.put("articleTimestamp", article.getTimestamp());
					response.put("articleHotnessLevel", Article.ARTICLE_HOTNESS_MAP[article.getHotness()]);
					try {
						response.put("articleTimeSpentFromPublish", Utility.convertFromOneFormatToAnother(article.getPublishDate()));
					} catch (ParseException e1) {
						Logger.error("Error in ArticleDAO ", e1.fillInStackTrace());
						response.put("articleTimeSpentFromPublish", "NaN");
					}
					User author = userDaoInstance.findByUsername(article.getAuthor());
					response.put("articleAuthor", author.getUsername());
					String avatar = author.getAvatar();
					if (!avatar.isEmpty()) {
						Media media = mediaDaoInstance.getById(avatar);
						response.put("articleAuthorAvatar", media.getUrl());
					} else {
						response.put("articleAuthorAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					}

					response.put("articleCoolScore", (int) article.getCoolScore());
					response.put("articleNotCoolScore", (int) article.getNotCoolScore());
					try {
						response.put("articleCommentCount", conversationDaoInstance.getComments(article.getId().toString()).size());
					} catch (ParseException e) {
						Logger.error("Error in ArticleDAO getNArticlesByCarouselSelectorAndCategory", e.fillInStackTrace());
						e.printStackTrace();
					}
					String featuredImage = article.getFeaturedImage();
					if (featuredImage.isEmpty() || "default".equalsIgnoreCase(featuredImage)) {
						response.put("articleFeaturedImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/featuredBg.png");
					} else {
						Media media = mediaDaoInstance.getById(featuredImage);
						response.put("articleFeaturedImage", media.getUrl());
					}

					articleLists.add(response);
				}

			}
		}
		return articleLists;
	}

	
	@Override
	public HashMap<String, Object> createOrUpdateArticle(DynamicForm requestData) {
		Mongo instance = getDatabaseInstance().getMongoInstance();
		UserGameScoreMapDAO scoreMapDAO = UserGameScoreMapDAO.instantiateDAO();
		HashMap<String, Object> response = new HashMap<>();
		response.put("status", "fail");
		Article article = null;
		try {
			article = createOrUpdateArticleInstance(requestData);
			Logger.debug("ARTICLE     " + article);
			Logger.debug("ARTICLE STATE " + article.getState());
			Logger.debug("ARTICLE PUBLISH STATE " + article.getIsPublished());
			article.setHotness(PostHotness.COLD.getHotnessValue());
			save(article);
			User author = userDaoInstance.findByUsername(article.getAuthor());
			if (article != null) {
				if (Article.PUBLISH == article.getState() && Article.NOT_PUBLISHED == article.getIsPublished()) {
					int publishState = article.getIsPublished();
					article.setIsPublished(Article.PUBLISHED);
					
					Logger.debug("User  " + author);
					List<Article> allAuthorArticles = findAllPublishedArticlesByUser(author.getUsername());
					Logger.debug("allAuthorArticles " + allAuthorArticles);
					Double articlePublishRate = 0.0;
					Logger.debug("All article size: " + allAuthorArticles.size());
					if (allAuthorArticles.size() > 0) {
						articlePublishRate = RankAlgorithm.calculateUserArticlePublishRate(findAllPublishedArticlesByUser(author
								.getUsername()));
					}
					author.setArticlePublishRate(articlePublishRate);
					Double articlePublishRateRatio = RankAlgorithm.calculateUserArticlePublishRateRatio(articlePublishRate, instance);
					Double userFollowScore = author.getUserFollowScore();
					Double articleScoreRatio = RankAlgorithm.calculateUserArticleScoreRatio(author.getUserArticleScore(), instance);
					Double totalScore = RankAlgorithm.calculateUserScore(articlePublishRateRatio, userFollowScore, articleScoreRatio);
					author.setTotalScore(totalScore);
					Logger.debug("UserGameScoreMapDao count " + gloonDatastore.getCount(UserGameScoreMapDAO.class));
					if (gloonDatastore.getCount(UserGameScoreMap.class) > 0) {
						Query<UserGameScoreMap> updateQuery = gloonDatastore.createQuery(UserGameScoreMap.class).field("username")
								.equal(author.getUsername());
						Logger.debug("updateQuery " + updateQuery);
						UpdateOperations<UserGameScoreMap> operations = gloonDatastore.createUpdateOperations(UserGameScoreMap.class)
								.set("networkUserWeight", RankAlgorithm.calculateUserScoreRatio(totalScore, instance));
						gloonDatastore.update(updateQuery, operations);
					}
					userDaoInstance.save(author);

					for (User user : userDaoInstance.getTopUsers(0)) {
						if (!author.getUsername().equalsIgnoreCase(user.getUsername())) {
							Double userArticlePublishRateRatio = RankAlgorithm.calculateUserArticlePublishRateRatio(
									user.getArticlePublishRate(), instance);
							Double userArticleScoreRatio = RankAlgorithm.calculateUserArticleScoreRatio(user.getUserArticleScore(),
									instance);
							Double userTotalScore = RankAlgorithm.calculateUserScore(userArticlePublishRateRatio,
									user.getUserFollowScore(), userArticleScoreRatio);
							user.setTotalScore(userTotalScore);
						}
					}

					if (Category.Review.equals(article.getCategory())) {
						String gameId = article.getGame();
						int gameScore = Integer.parseInt(requestData.get("gameScore"));
						Logger.debug("SCORE recieved: " + gameScore);
						if (!StringUtil.isNullOrEmpty(gameId)) {
							Game fetchedGame = gloonDatastore.get(Game.class, new ObjectId(gameId));
							if (fetchedGame != null) {
								if (article.getCategory().equals(Category.Review)) {
									Double refinedGameScore = gameScore / 10.0;
									Double userScoreRatio = RankAlgorithm.calculateUserScoreRatio(author.getTotalScore(), instance);
									Logger.debug("USER SCORE RATIO " + userScoreRatio);
									UserGameScoreMap scoreMap = scoreMapDAO.findByUserAndGame(author.getUsername(),gameId);
									if(scoreMap!=null)
									{
										scoreMap.setGameScore(refinedGameScore);
										scoreMap.setNetworkUserWeight(userScoreRatio);
										scoreMap.setUpdateTime(article.getUpdateTime());
										scoreMapDAO.save(scoreMap);
									}
									else
									{
										scoreMapDAO.createOrUpdateScoreMap("", gameId, author.getUsername(), refinedGameScore,userScoreRatio);
									}
									fetchedGame.setTotalScore(RankAlgorithm.calculateNetworkGameScore(gameId.toString(), instance));
									gameDaoInstance.save(fetchedGame);
								}
								article.setGame(gameId);
								
							}
						}
						userDaoInstance.updateUserXP(author, XPTriggerPoints.REVIEW_CREATION);
					}

					if (Category.Gloonicle.equals(article.getCategory())) {
						String articleBody = article.getBody();
						ArrayList<String> mediaIds = Article.fetchMediaIdsFromBody(articleBody);
						Logger.debug("MediaIds created");
						if (mediaIds.size() > 0) {
							/**
							 * This saves all the images from article body to DB
							 * 
							 */
							for (String mediaId : mediaIds) {
								articleMediaMapDaoInstance.createOrUpdateArticleMediaMap("", article.getId().toString(), mediaId);
							}
						}
						userDaoInstance.updateUserXP(author, XPTriggerPoints.GLOONICLE_CREATION);
					}
					if(Category.News.equals(article.getCategory()))
					{
						userDaoInstance.updateUserXP(author, XPTriggerPoints.NEWS_CREATION);
					}
					if(Category.Video.equals(article.getCategory()))
					{
						userDaoInstance.updateUserXP(author, XPTriggerPoints.VIDEO_CREATION);
					}
					save(article);
					if(Article.NOT_PUBLISHED == publishState)
					{
						HashMap<String, String> activityMap = new HashMap<>();
						activityMap.put("id", "");
						activityMap.put("username", article.getAuthor());
						activityMap.put("entityId", article.getId().toString());
						activityMap.put("type", "" + Activity.ACTIVITY_POST_PUBLISH);
						activityMap.put("visibility", "" + Activity.PUBLIC);
						activityDaoInstance.createOrUpdateActivity(activityMap);
					}
					

				}
				else
				{
					if (Article.DRAFT == article.getState() && Category.Review.equals(article.getCategory())) {						
						String gameId = article.getGame();
						int gameScore = Integer.parseInt(requestData.get("gameScore"));
						Logger.debug("SCORE recieved: " + gameScore);
						if (!StringUtil.isNullOrEmpty(gameId)) {
							Game fetchedGame = gloonDatastore.get(Game.class, new ObjectId(gameId));
							if (fetchedGame != null) {
								if (article.getCategory().equals(Category.Review)) {
									Double refinedGameScore = gameScore / 10.0;
									Double userScoreRatio = RankAlgorithm.calculateUserScoreRatio(author.getTotalScore(), instance);
									Logger.debug("USER SCORE RATIO " + userScoreRatio);
									UserGameScoreMap scoreMap = scoreMapDAO.findByUserAndGame(author.getUsername(),gameId);
									if(scoreMap!=null)
									{
										scoreMap.setGameScore(refinedGameScore);
										scoreMap.setNetworkUserWeight(userScoreRatio);
										scoreMap.setUpdateTime(article.getUpdateTime());
										scoreMapDAO.save(scoreMap);
									}
									else
									{
										scoreMapDAO.createOrUpdateScoreMap("", gameId, author.getUsername(), refinedGameScore,userScoreRatio);
									}																	
									fetchedGame.setTotalScore(RankAlgorithm.calculateNetworkGameScore(gameId.toString(), instance));
									gameDaoInstance.save(fetchedGame);
								}
								article.setGame(gameId);
								save(article);
							}
						}					
					}
				}

				response.put("status", "success");
			}


		} catch (ParseException pe) {
			Logger.error("Error in ArticleDAO saveOrUpdateArticle", pe.fillInStackTrace());
		} catch (Exception e) {
			Logger.error("Error in ArticleDAO saveOrUpdateArticle", e.fillInStackTrace());
		}
		return response;
	}

	@Override
	public ArrayList<HashMap<String, Object>> getArticleListForUser(User user) {

		List<Article> articles = new ArrayList<>();
		articles = gloonDatastore.createQuery(Article.class).filter("author", user.getUsername()).order("-insertTime").asList();
		ArrayList<HashMap<String, Object>> articleMapList = new ArrayList<>();
		for (Article article : articles) {
			HashMap<String, Object> articleMap = new HashMap<>();
			articleMap.put("articleId", article.getId().toString());
			articleMap.put("articleTitle", article.getTitle());
			String title = article.getTitle();
			if (title.length() > 15) {
				articleMap.put("articleStrippedTitle", title.substring(0, 15) + "...");
			} else {
				articleMap.put("articleStrippedTitle", title);
			}
			articleMap.put("articleBody", article.getBody());
			articleMap.put("articleCategory", article.getCategory().toString());
			articleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle()) + "-" + article.getId().toString());
			articleMap.put("articleState", article.getState());
			articleMap.put("articleInsertTime", article.getInsertTime());
			articleMap.put("articleUpdateTime", article.getUpdateTime());
			articleMap.put("articlePublishDate", article.getPublishDate());
			String publishDate = article.getPublishDate();
			try {

				if (publishDate != null) {
					articleMap.put("publishTimeFormatted", Utility.convertFromOneFormatToAnother(publishDate));
					articleMap.put("articleTimeSpentFromPublish", Utility.convertFromOneFormatToAnother(publishDate));
				} else {
					articleMap.put("publishTimeFormatted", "");
					articleMap.put("articleTimeSpentFromPublish", "");
				}

			} catch (ParseException e1) {
				Logger.error("Error in ArticleDAO ", e1.fillInStackTrace());
				articleMap.put("articleTimeSpentFromPublish", "NaN");
				articleMap.put("articleTimeSpentFromPublish", "NaN");
			}
			try {
				articleMap.put("insertTimeFormatted", Utility.convertFromOneFormatToAnother(article.getInsertTime()));
			} catch (ParseException e1) {
				Logger.error("Error in ArticleDAO ", e1.fillInStackTrace());
				articleMap.put("articleTimeSpentFromPublish", "NaN");
			}
			try {
				articleMap.put("updateTimeFormatted", Utility.convertFromOneFormatToAnother(article.getUpdateTime()));
			} catch (ParseException e1) {
				Logger.error("Error in ArticleDAO ", e1.fillInStackTrace());
				articleMap.put("articleTimeSpentFromPublish", "NaN");
			}
			articleMap.put("articleTimestamp", article.getTimestamp());
			articleMap.put("articleCoolScore", (int) article.getCoolScore());
			articleMap.put("articleNotCoolScore", (int) article.getNotCoolScore());
			try {
				articleMap.put("articleCommentCount", conversationDaoInstance.getComments(article.getId().toString()).size());
			} catch (ParseException e) {
				Logger.error("{Parse Error in ArticleDAO getArticleListForUser", e.fillInStackTrace());
			} catch (MalformedURLException e) {
				Logger.error("Malformed Error in ArticleDAO getArticleListForUser", e.fillInStackTrace());

			}
			articleMapList.add(articleMap);

		}

		return articleMapList;
	}

	@Override
	public List<Article> findAllPublishedArticlesByGame(String gameId, String category) {

		if ("all".equalsIgnoreCase(category)) {
			return gloonDatastore.createQuery(Article.class).filter("game", gameId).filter("state", Article.PUBLISH)
					.order("-publishDate").asList();
		} else {
			return gloonDatastore.createQuery(Article.class).filter("game", gameId).filter("state", Article.PUBLISH)
					.filter("category", Utility.capitalizeString(category)).order("-publishDate").asList();
		}

	}
	
	@Override
	public List<Article> findAllPublishedArticlesByGames(List<String> gameIds, String category) {

		if ("all".equalsIgnoreCase(category)) {
			return gloonDatastore.createQuery(Article.class).filter("game in", gameIds).filter("state", Article.PUBLISH)
					.order("-publishDate").asList();
		} else {
			return gloonDatastore.createQuery(Article.class).filter("game in", gameIds).filter("state", Article.PUBLISH)
					.filter("category", Utility.capitalizeString(category)).order("-publishDate").asList();
		}

	}

	@Override
	public Long allPublishedArticlesCount(User user) {
		if (user == null) {
			return gloonDatastore.createQuery(Article.class).filter("state", Article.PUBLISH).countAll();
		} else {
			return gloonDatastore.createQuery(Article.class).filter("state", Article.PUBLISH).filter("author", user.getUsername())
					.countAll();
		}

	}

	@Override
	public HashMap<String, Object> createOrUpdateCoolUncoolValue(String userName, String articleId, Integer type) {
		Mongo instance = getDatabaseInstance().getMongoInstance();
		HashMap<String, Object> response = new HashMap<>();
		response.put("status", "fail");
		Article article = getArticleData(gloonDatastore, articleId);
		User viewer = userDaoInstance.findByUsername(userName);
		UserArticleVotingMap articleVotingMap = articleVotingMapDAOInstance.createOrUpdateUserArticleVotingMapInstance(viewer,
				article, type);
		if (articleVotingMap != null) {
			User author = userDaoInstance.findByUsername(article.getAuthor());
			article.setCoolNotCoolwilsonScore(RankAlgorithm.wilsonScoreCalculator(article.getCoolScore(), article.getNotCoolScore()));
			double averageTimeSpentRatio = RankAlgorithm.calculateArticleAverageTimeSpentRatio(article.getAverageTimeSpent(), instance);
			Logger.debug("ARTICLE TOTAL SCORE: "
					+ RankAlgorithm.calculateArticleScore(article.getCoolNotCoolwilsonScore(), averageTimeSpentRatio));
			article.setTotalScore(RankAlgorithm.calculateArticleScore(article.getCoolNotCoolwilsonScore(), averageTimeSpentRatio));
			save(article);
			Double userArticleScore = RankAlgorithm.calculateUserArticleScore(instance, article.getAuthor());
			author.setUserArticleScore(userArticleScore);
			Double userArticleScoreRatio = RankAlgorithm.calculateUserArticleScoreRatio(userArticleScore, instance);
			Double userArticlePublishRateRatio = RankAlgorithm.calculateUserArticlePublishRateRatio(author.getArticlePublishRate(),
					instance);
			Double userTotalScore = RankAlgorithm.calculateUserScore(userArticlePublishRateRatio, author.getUserFollowScore(),
					userArticleScoreRatio);
			author.setTotalScore(userTotalScore);
			if (gloonDatastore.getCount(UserGameScoreMap.class) > 0) {
				Query<UserGameScoreMap> updateQuery = gloonDatastore.createQuery(UserGameScoreMap.class).field("username")
						.equal(author.getUsername());
				UpdateOperations<UserGameScoreMap> operations = gloonDatastore.createUpdateOperations(UserGameScoreMap.class).set(
						"networkUserWeight", RankAlgorithm.calculateUserScoreRatio(userTotalScore, instance));
				gloonDatastore.update(updateQuery, operations);
			}
			userDaoInstance.save(author);
			// Reactively update all other user article scores because total article score is impacted
			for (User user : userDaoInstance.getTopUsers(0)) {
				if (!author.getUsername().equalsIgnoreCase(user.getUsername())) {
					double fetchedUserArticleScore = RankAlgorithm.calculateUserArticleScore(instance, user.getUsername());
					user.setUserArticleScore(fetchedUserArticleScore);
					double fetchedUserArticleScoreRatio = RankAlgorithm.calculateUserArticleScoreRatio(fetchedUserArticleScore,
							instance);
					double fetchedUserArticlePublishRateRatio = RankAlgorithm.calculateUserArticlePublishRateRatio(
							user.getArticlePublishRate(), instance);
					double fetchedUserTotalScore = RankAlgorithm.calculateUserScore(fetchedUserArticlePublishRateRatio,
							user.getUserFollowScore(), fetchedUserArticleScoreRatio);
					user.setTotalScore(fetchedUserTotalScore);
					userDaoInstance.save(user);
				}

			}
			response.put("status", "success");
		}
		return response;
	}

	@Override
	public HashMap<String, Object> updateArticleAverageTimeSpent(String articleId, Double timeSpent) {
		Mongo instance = getDatabaseInstance().getMongoInstance();
		Logger.debug("Updating average time spent");
		HashMap<String, Object> response = new HashMap<>();
		response.put("status", "fail");
		Article article = getById(articleId);
		if (article != null) {
			Logger.debug("article exist to update ATS: " + article);
			double totalTimeSpent = article.getTotalTimeSpent();
			totalTimeSpent += timeSpent;
			article.setTotalTimeSpent(totalTimeSpent);
			Logger.debug("totalTimeSpent: " + totalTimeSpent);
			double pageHitCount = article.getPageHitCount();
			pageHitCount += 1;
			article.setPageHitCount(pageHitCount);
			Logger.debug("pageHitCount: " + pageHitCount);
			double averageTimeSpentOnArticle = 0.0;
			averageTimeSpentOnArticle = totalTimeSpent / pageHitCount;
			article.setAverageTimeSpent(averageTimeSpentOnArticle);
			Logger.debug("averageTimeSpentOnArticle: " + averageTimeSpentOnArticle);
			save(article);
			double averageTimeSpentRatio = RankAlgorithm.calculateArticleAverageTimeSpentRatio(averageTimeSpentOnArticle, instance);
			Logger.debug("averageTimeSpentRatio: " + averageTimeSpentRatio);
			double totalArticleScore = RankAlgorithm.calculateArticleScore(article.getCoolNotCoolwilsonScore(), averageTimeSpentRatio);
			Logger.debug("total Article Score      " + totalArticleScore);
			article.setTotalScore(totalArticleScore);
			save(article);
			User author = userDaoInstance.findByUsername(article.getAuthor());
			double userArticleScore = RankAlgorithm.calculateUserArticleScore(instance, article.getAuthor());
			author.setUserArticleScore(userArticleScore);
			double userArticleScoreRatio = RankAlgorithm.calculateUserArticleScoreRatio(userArticleScore, instance);
			double userArticlePublishRateRatio = RankAlgorithm.calculateUserArticlePublishRateRatio(author.getArticlePublishRate(),
					instance);
			double userTotalScore = RankAlgorithm.calculateUserScore(userArticlePublishRateRatio, author.getUserFollowScore(),
					userArticleScoreRatio);
			author.setTotalScore(userTotalScore);
			if (gloonDatastore.getCount(UserGameScoreMap.class) > 0) {
				Query<UserGameScoreMap> updateQuery = gloonDatastore.createQuery(UserGameScoreMap.class).field("username")
						.equal(author.getUsername());
				UpdateOperations<UserGameScoreMap> operations = gloonDatastore.createUpdateOperations(UserGameScoreMap.class).set(
						"networkUserWeight", RankAlgorithm.calculateUserScoreRatio(userTotalScore, instance));
				gloonDatastore.update(updateQuery, operations);
			}

			userDaoInstance.save(author);

			// Reactively update all other user article scores because total article score is impacted
			for (User user : userDaoInstance.getTopUsers(0)) {
				if (!author.getUsername().equalsIgnoreCase(user.getUsername())) {
					double fetchedUserArticleScore = RankAlgorithm.calculateUserArticleScore(instance, user.getUsername());
					user.setUserArticleScore(fetchedUserArticleScore);
					double fetchedUserArticleScoreRatio = RankAlgorithm.calculateUserArticleScoreRatio(fetchedUserArticleScore,
							instance);
					double fetchedUserArticlePublishRateRatio = RankAlgorithm.calculateUserArticlePublishRateRatio(
							user.getArticlePublishRate(), instance);
					double fetchedUserTotalScore = RankAlgorithm.calculateUserScore(fetchedUserArticlePublishRateRatio,
							user.getUserFollowScore(), fetchedUserArticleScoreRatio);
					user.setTotalScore(fetchedUserTotalScore);
					userDaoInstance.save(user);
				}

			}
			response.put("status", "success");
		}
		return response;
	}

	@Override
	public List<Article> findAllPublishedArticlesByUser(String username) {
		return gloonDatastore.createQuery(Article.class).filter("author", username).asList();
	}

	@Override
	public List<HashMap<String, Object>> getUserDrafts(String username) throws MalformedURLException {
		List<HashMap<String, Object>> userDrafts = new ArrayList<>();
		Logger.debug("Username: "+username);
		List<Article> userDraftPosts = fetchUserDraftsForUser(username);
		if (userDraftPosts.size() > 0) {
			for (Article draft : userDraftPosts) {
				HashMap<String, Object> draftMap = new HashMap<>();
				draftMap.put("articleId", draft.getId().toString());
				draftMap.put("articleTitle", draft.getTitle());
				draftMap.put("articleBody", draft.getBody());
				draftMap.put("articleCategory", draft.getCategory().toString());
				User author = userDaoInstance.findByUsername(draft.getAuthor());
				draftMap.put("articleAuthor", author.getUsername());
				String avatar = author.getAvatar();
				if (!avatar.isEmpty()) {
					Media media = mediaDaoInstance.getById(avatar);
					draftMap.put("articleAuthorAvatar", media.getUrl());
				} else {
					draftMap.put("articleAuthorAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
				}
				String featuredImage = draft.getFeaturedImage();
				if (featuredImage.isEmpty() || "default".equalsIgnoreCase(featuredImage)) {
					draftMap.put("articleFeaturedImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/featuredBg.png");
				} else {
					Media media = mediaDaoInstance.getById(featuredImage);
					draftMap.put("articleFeaturedImage", media.getUrl());
				}
				userDrafts.add(draftMap);
			}
		}
		return userDrafts;
	}

	private List<Article> fetchUserDraftsForUser(String username) {
		
		return gloonDatastore.createQuery(Article.class).filter("author", username).filter("state", Article.DRAFT)
				.order("-insertTime").asList();
	}

	/**
	 * Create or update Article.
	 * 
	 * @param requestData
	 * @return
	 * @throws ParseException
	 */

	@SuppressWarnings("unchecked")
	private Article createOrUpdateArticleInstance(DynamicForm requestData) throws ParseException {

		Article article = null;
		Date date = new Date();
		String dateTime = Utility.convertDateToString(date);
		String id = requestData.get("articleId");
		int state = Integer.parseInt(requestData.get("articleState"));
		Logger.debug("State recieved: " + state);
		String articleTitle = requestData.get("articleTitle");
		Logger.debug("Title recieved: " + articleTitle);
		String articleBody = requestData.get("articleBody");
		Logger.debug("Body recieved: " + articleBody);
		String category = Utility.capitalizeString(requestData.get("articleCategory"));
		String username = requestData.get("articleUsername");
		Logger.debug("USERNAME:   " + username);
		String videoLink = requestData.get("articleVideoLink");
		Logger.debug("VIDEO Link:   " + videoLink);
		String reviewPlayedOnPlatform = "";
		String sweets = "";
		String stinks = "";
		ArrayList<String> sweetPoints = new ArrayList<>();
		ArrayList<String> stinkPoints = new ArrayList<>();
		String newsLink = "";
		String gameId = "";
		if ("Review".equalsIgnoreCase(category)) {
			reviewPlayedOnPlatform = requestData.get("playedOnPlatform");
			Logger.debug("PLATFORM recieved: " + reviewPlayedOnPlatform);
			int sweetsLength = Integer.parseInt(requestData.get("sweetsLength"));
			int stinksLength = Integer.parseInt(requestData.get("stinksLength"));
			sweets = requestData.get("sweets");
			stinks = requestData.get("stinks");
			ObjectMapper mapper = new ObjectMapper();
			try {
				Map<String, Object> sweetsJson = new HashMap<>();
				sweetsJson = mapper.readValue(sweets, Map.class);
				if (sweetsLength > 0) {
					for (int index = 0; index < sweetsLength; ++index) {
						sweetPoints.add(index, sweetsJson.get("" + index).toString());
					}
				}

				Map<String, Object> stinksJson = new HashMap<>();
				stinksJson = mapper.readValue(stinks, Map.class);
				if (stinksLength > 0) {
					for (int index = 0; index < stinksLength; ++index) {
						stinkPoints.add(index, stinksJson.get("" + index).toString());
					}
				}
				Logger.debug("Sweets: " + sweetPoints);
				Logger.debug("Stinks: " + stinkPoints);

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if ("News".equalsIgnoreCase(category)) {
			newsLink = requestData.get("newsSrcLink");
			Logger.debug("newsLink recieved: " + newsLink);
		}
		if ("Review".equalsIgnoreCase(category) || "News".equalsIgnoreCase(category) || "Video".equalsIgnoreCase(category)) {
			gameId = requestData.get("gameId");
			Logger.debug("gameId: " + gameId);
		}
		String featuredImage = requestData.get("articleFeaturedImage");
		Logger.debug("Featured image: " + featuredImage);
		HashMap<String, String> reviewAttributes = new HashMap<>();
		reviewAttributes.put("author", username);
		reviewAttributes.put("playedOnPlatform", reviewPlayedOnPlatform);
		reviewAttributes.put("gameId", gameId);

		// Guard for checking that review does exist or not
		if(StringUtil.isNullOrEmpty(id))
		{
			if (checkArticleAlreadyExist(Category.Review, reviewAttributes)) {
				return null;
			}
		}
		

		Logger.debug("Article doesnt exist");

		if (!StringUtil.isNullOrEmpty(id)) {
			ObjectId _id = new ObjectId(id);
			article = gloonDatastore.get(Article.class, _id);
			article.setUpdateTime(dateTime);
			if (state == Article.PUBLISH) {

				if (article.getPublishDate() == null || article.getPublishDate().isEmpty()) {
					article.setPublishDate(dateTime);
					article.setTimestamp(Utility.convertFromStringToDate(article.getPublishDate()).getTime());
					article.setState(Article.PUBLISH);					
				}
				article.setIsPublished(Article.PUBLISHED);

			}
			else
			{
				article.setState(Article.DRAFT);
				article.setIsPublished(Article.NOT_PUBLISHED);
			}
		} else {
			article = new Article();
			article.setInsertTime(dateTime);
			article.setUpdateTime(dateTime);
			article.setIsPublished(Article.NOT_PUBLISHED);
			if (state == Article.PUBLISH) {
				article.setState(Article.PUBLISH);
				article.setPublishDate(dateTime);
				article.setTimestamp(Utility.convertFromStringToDate(article.getPublishDate()).getTime());

			}
			else
			{
				article.setState(Article.DRAFT);
				
			}
		}
		article.setTitle(articleTitle);
		article.setBody(articleBody);
		article.setCategory(Category.valueOf(category));
		article.setAuthor(username);
		article.setFeaturedImage(featuredImage);
		article.setState(state);
		article.setVideoUrl(videoLink);


		if ("Review".equalsIgnoreCase(category))
		{
			article.setPlayedOnPlatform(reviewPlayedOnPlatform);
			article.setSweets(sweetPoints);
			article.setStinks(stinkPoints);
		}
		if ("News".equalsIgnoreCase(category)) {
			article.setSourceUrl(newsLink);
		}
		if ("Review".equalsIgnoreCase(category) || "News".equalsIgnoreCase(category) || "Video".equalsIgnoreCase(category)) {
			if (!StringUtil.isNullOrEmpty(gameId) && !gameId.equalsIgnoreCase("noGame")) {
				Game fetchedGame = gloonDatastore.get(Game.class, new ObjectId(gameId));
				if (fetchedGame != null) {
					article.setGame(gameId);
				}
			}
		}
		return article;
	}

	/**
	 * Guard method to check whether Article already exists or not in Db so that it can be restricted for getting created again.
	 * Currently this API works for Review only. In future we may update it for other category based on conditions.
	 * 
	 * @param category
	 * @param attributes
	 * @return
	 */
	private boolean checkArticleAlreadyExist(Category category, HashMap<String, String> attributes) {
		boolean status = false;
		Logger.debug("checking article here");
		if (Category.Review.equals(category)) {
			String author = attributes.get("author");
			String playedOnPlatform = attributes.get("playedOnPlatform");
			String gameId = attributes.get("gameId");
			Article article = gloonDatastore.createQuery(Article.class).filter("category", category).filter("author", author)
					.filter("game", gameId).filter("playedOnPlatform", playedOnPlatform).get();
			status = article != null;

		}
		return status;
	}

	/**
	 * Get Single Article Based on Article Encoded title or id
	 * 
	 * @param gloonDatastore
	 * @param title
	 * @return
	 */
	private Article getArticleData(Datastore gloonDatastore, String titleOrId) throws IllegalArgumentException {
		if (!titleOrId.contains("-")) {
			return gloonDatastore.createQuery(Article.class).filter("_id", new ObjectId(titleOrId)).get();
		}
		ObjectId _id = Utility.fetchIdFromTitle(titleOrId);
		return gloonDatastore.createQuery(Article.class).filter("_id", _id).get();
	}

	/**
	 * Fetch 5 Reviews of recently released games based on user ratings
	 * 
	 * @param topUsers
	 * @param type
	 * @return
	 * @throws ParseException
	 * @throws MalformedURLException
	 */
	private List<HashMap<String, Object>> getReviewsForCarousel(List<User> topUsers, String type) throws ParseException,
			MalformedURLException {
		List<Game> recentlyReleasedNGames = gameDaoInstance.getRecentReleasedGames(0);
		List<User> reviewTopUsers = new ArrayList<>();
		reviewTopUsers.addAll(topUsers);
		int counter = 0;
		List<HashMap<String, Object>> fetchedReviews = new ArrayList<>();
		for (Game game : recentlyReleasedNGames) {
			if (reviewTopUsers.size() > 0 && counter < 5) {
				for (User user : reviewTopUsers) {
					Article article = null;
					if ("all".equalsIgnoreCase(type)) {
						article = gloonDatastore.createQuery(Article.class).order("-publishDate").filter("author", user.getUsername())
								.filter("game", game.getId().toString()).filter("category", Category.Review)
								.filter("state", Article.PUBLISH).get();
					} else {
						article = gloonDatastore.createQuery(Article.class).order("-publishDate").filter("author", user.getUsername())
								.filter("game", game.getId().toString()).filter("platforms", type).filter("category", Category.Review)
								.filter("state", Article.PUBLISH).get();
					}
					if (article != null) {
						HashMap<String, Object> articleMap = new HashMap<String, Object>();
						articleMap.put("articleCarouselCategory", Category.Review);
						articleMap.put("articleTitle", article.getTitle());
						articleMap.put("articleBody", article.getBody());
						articleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle()) + "-"
								+ article.getId().toString());
						User author = userDaoInstance.findByUsername(article.getAuthor());
						articleMap.put("articleAuthor", author.getUsername());
						String avatar = author.getAvatar();
						if (!avatar.isEmpty()) {
							Media media = mediaDaoInstance.getById(avatar);
							articleMap.put("articleAuthorAvatar", media.getUrl());
						} else {
							articleMap.put("articleAuthorAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
						}
						articleMap.put("articlepublishDate", article.getPublishDate());
						String featuredImage = article.getFeaturedImage();
						if (featuredImage.isEmpty() || "default".equalsIgnoreCase(featuredImage)) {
							articleMap.put("articleFeaturedImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/featuredBg.png");
						} else {
							Media media = mediaDaoInstance.getById(featuredImage);
							articleMap.put("articleFeaturedImage", media.getUrl());
						}
						articleMap.put("articleCommentCount", conversationDaoInstance.getComments(article.getId().toString()).size());
						fetchedReviews.add(articleMap);
						counter++;
						reviewTopUsers.remove(user); // This adds variety to carousel section. This restricts addition of multiple
														// reviews by same user in carousel.
						break;
					}
				}

			} else {
				break;
			}

		}

		return fetchedReviews;
	}

	/**
	 * Fetch 5 News/Gloonicles/Videos of recently released games based on user ratings
	 * 
	 * @param topUsers
	 * @param type
	 * @return
	 * @throws ParseException
	 * @throws MalformedURLException
	 */
	private List<HashMap<String, Object>> getArticlesForCarousel(List<User> topUsers, String type, Category category)
			throws ParseException, MalformedURLException {

		Logger.debug("TOP USER SIZE:-> " + topUsers);
		List<HashMap<String, Object>> fetchedArticles = new ArrayList<>();
		int counter = 0;
		for (User user : topUsers) {
			if (counter >= 5) {
				break;
			}
			Article article = null;
			if ("all".equalsIgnoreCase(type)) {
				article = gloonDatastore.createQuery(Article.class).filter("author", user.getUsername()).order("-publishDate")
						.filter("category", category).filter("state", Article.PUBLISH).get();
			} else {
				article = gloonDatastore.createQuery(Article.class).filter("author", user.getUsername()).order("-publishDate")
						.filter("platforms", type).filter("category", category).filter("state", Article.PUBLISH).get();
			}
			if (article != null) {
				HashMap<String, Object> articleMap = new HashMap<String, Object>();
				articleMap.put("articleCarouselCategory", category);
				articleMap.put("articleTitle", article.getTitle());
				articleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle()) + "-" + article.getId().toString());
				articleMap.put("articleBody", article.getBody());
				User author = userDaoInstance.findByUsername(article.getAuthor());
				articleMap.put("articleAuthor", author.getUsername());
				String avatar = author.getAvatar();
				if (!avatar.isEmpty()) {
					Media media = mediaDaoInstance.getById(avatar);
					articleMap.put("articleAuthorAvatar", media.getUrl());
				} else {
					articleMap.put("articleAuthorAvatar", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
				}
				articleMap.put("articlepublishDate", article.getPublishDate());
				String featuredImage = article.getFeaturedImage();
				if (featuredImage.isEmpty() || "default".equalsIgnoreCase(featuredImage)) {
					articleMap.put("articleFeaturedImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/featuredBg.png");
				} else {
					Media media = mediaDaoInstance.getById(featuredImage);
					articleMap.put("articleFeaturedImage", media.getUrl());
				}
				articleMap.put("articleCommentCount", conversationDaoInstance.getComments(article.getId().toString()).size());
				fetchedArticles.add(articleMap);
				counter++;
			}

		}
		return fetchedArticles;
	}

	/**
	 * Get N recent article of all types
	 * 
	 * @param gloonDatastore
	 * @param limit
	 * @param category
	 * @param carouselSelector
	 * @param timestamp
	 * @param mode
	 * @return
	 */
	private List<Article> getNRecentArticles(Datastore gloonDatastore, int limit, String category, String carouselSelector,
			Long timestamp, Integer mode) {
		List<Article> recent10Articles = new ArrayList<Article>();
		if (timestamp < 0) {
			timestamp = new Date().getTime();
		}
		switch (mode) {
			case Article.PLATFORM:
				if ("all".equalsIgnoreCase(category)) {
					if ("all".equalsIgnoreCase(carouselSelector)) {
						recent10Articles = gloonDatastore.createQuery(Article.class).order("-publishDate").limit(limit)
								.filter("timestamp <", timestamp).filter("state", Article.PUBLISH).asList();
					} else {
						recent10Articles = gloonDatastore.createQuery(Article.class).filter("platforms", carouselSelector)
								.order("-publishDate").limit(limit).filter("timestamp <", timestamp).filter("state", Article.PUBLISH)
								.asList();
					}
				} else {
					if ("all".equalsIgnoreCase(carouselSelector)) {
						recent10Articles = gloonDatastore.createQuery(Article.class)
								.filter("category", Utility.capitalizeString(category)).order("-publishDate").limit(limit)
								.filter("timestamp <", timestamp).filter("state", Article.PUBLISH).asList();
					} else {
						recent10Articles = gloonDatastore.createQuery(Article.class)
								.filter("category", Utility.capitalizeString(category)).filter("platforms", carouselSelector)
								.order("-publishDate").limit(limit).filter("timestamp <", timestamp).filter("state", Article.PUBLISH)
								.asList();
					}
				}
				break;
			case Article.USER:
				if ("all".equalsIgnoreCase(category)) {
					recent10Articles = gloonDatastore.createQuery(Article.class).order("-publishDate").limit(limit)
							.filter("timestamp <", timestamp).filter("state", Article.PUBLISH).filter("author", carouselSelector)
							.asList();
				} else {
					recent10Articles = gloonDatastore.createQuery(Article.class)
							.filter("category", Utility.capitalizeString(category)).order("-publishDate").limit(limit)
							.filter("timestamp <", timestamp).filter("state", Article.PUBLISH).filter("author", carouselSelector)
							.asList();

				}
				break;
			case Article.GAME:
				if ("all".equalsIgnoreCase(category)) {
					recent10Articles = gloonDatastore.createQuery(Article.class).order("-publishDate").limit(limit)
							.filter("timestamp <", timestamp).filter("state", Article.PUBLISH).filter("game", carouselSelector)
							.asList();
				} else {
					recent10Articles = gloonDatastore.createQuery(Article.class)
							.filter("category", Utility.capitalizeString(category)).order("-publishDate").limit(limit)
							.filter("timestamp <", timestamp).filter("state", Article.PUBLISH).filter("game", carouselSelector)
							.asList();

				}
				break;
		}

		return recent10Articles;
	}

	/**
	 * Get Carousel articles for User page
	 * 
	 * @param username
	 * @param category
	 * @return
	 */
	private List<Article> getCarouselArticlesForUser(String username, String category) {
		List<Article> articleList = new ArrayList<>();
		if ("all".equalsIgnoreCase(category)) {
			articleList = gloonDatastore.createQuery(Article.class).filter("state", Article.PUBLISH).order("-publishDate").limit(5)
					.filter("author", username).asList();
		} else {
			articleList = gloonDatastore.createQuery(Article.class).filter("category", Utility.capitalizeString(category))
					.filter("state", Article.PUBLISH).order("-publishDate").limit(5).filter("author", username).asList();
		}
		return articleList;
	}

	/**
	 * Bake the user carousel data in Map to send it across
	 * 
	 * @param username
	 * @param category
	 * @return
	 * @throws ParseException
	 * @throws MalformedURLException
	 */
	private List<HashMap<String, Object>> getCarouselArticlesForUserMap(String username, String category) throws ParseException,
			MalformedURLException {
		List<HashMap<String, Object>> carouselArticlesForUser = new ArrayList<>();
		List<Article> carouselArticles = getCarouselArticlesForUser(username, category);
		for (Article article : carouselArticles) {
			HashMap<String, Object> articleMap = new HashMap<String, Object>();
			articleMap.put("articleCategory", article.getCategory().toString());
			articleMap.put("articleTitle", article.getTitle());
			articleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle()) + "-" + article.getId().toString());
			articleMap.put("articleBody", article.getBody());
			articleMap.put("articleAuthor", article.getAuthor());
			articleMap.put("articlepublishDate", article.getPublishDate());
			String featuredImage = article.getFeaturedImage();
			if (featuredImage.isEmpty() || "default".equalsIgnoreCase(featuredImage)) {
				articleMap.put("articleFeaturedImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/featuredBg.png");
			} else {
				Media media = mediaDaoInstance.getById(featuredImage);
				articleMap.put("articleFeaturedImage", media.getUrl());
				articleMap.put("articleFeaturedImageId", media.getId().toString());
			}
			articleMap.put("articleTimestamp", article.getTimestamp());
			articleMap.put("articleCommentCount", conversationDaoInstance.getComments(article.getId().toString()).size());
			carouselArticlesForUser.add(articleMap);
		}
		return carouselArticlesForUser;
	}

	/**
	 * Fetch carousel articles for game
	 * 
	 * @param gameId
	 * @param category
	 * @return
	 * @throws ParseException
	 * @throws MalformedURLException
	 */
	private List<HashMap<String, Object>> getGameCarouselArticles(String gameId, String category) throws ParseException,
			MalformedURLException {
		List<HashMap<String, Object>> carouselArticlesForGame = new ArrayList<>();

		List<Article> gameArticles = findAllPublishedArticlesByGame(gameId, category);
		gameArticles = gameArticles.size() > 5 ? gameArticles.subList(0, 5) : gameArticles;

		for (Article article : gameArticles) {
			HashMap<String, Object> articleMap = new HashMap<String, Object>();
			articleMap.put("articleCarouselCategory", category);
			articleMap.put("articleTitle", article.getTitle());
			articleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(article.getTitle()) + "-" + article.getId().toString());
			articleMap.put("articleBody", article.getBody());
			articleMap.put("articleAuthor", article.getAuthor());
			articleMap.put("articlepublishDate", article.getPublishDate());
			String featuredImage = article.getFeaturedImage();
			if (featuredImage.isEmpty() || "default".equalsIgnoreCase(featuredImage)) {
				articleMap.put("articleFeaturedImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/featuredBg.png");
			} else {
				Media media = mediaDaoInstance.getById(featuredImage);
				articleMap.put("articleFeaturedImage", media.getUrl());
			}
			articleMap.put("articleTimestamp", article.getTimestamp());
			articleMap.put("articleCommentCount", conversationDaoInstance.getComments(article.getId().toString()).size());
			carouselArticlesForGame.add(articleMap);
		}
		return carouselArticlesForGame;
	}

	/**
	 * Fetch activities for carousel for Game
	 * 
	 * @param gameId
	 * @return
	 */
	private List<HashMap<String, Object>> getGameCarouselActivities(String gameId) {
		Game game = gloonDatastore.get(Game.class, new ObjectId(gameId));
		List<HashMap<String, Object>> gameActivities = activityDaoInstance.getActivitiesForGame(game);
		gameActivities = gameActivities.size() > 5 ? gameActivities.subList(0, 5) : gameActivities;
		return gameActivities;
	}

}
