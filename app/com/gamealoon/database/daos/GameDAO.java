package com.gamealoon.database.daos;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;
import play.Logger;
import play.data.DynamicForm;
import com.gamealoon.algorithm.RankAlgorithm;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.GameInterface;
import com.gamealoon.models.Article;
import com.gamealoon.models.Category;
import com.gamealoon.models.Game;
import com.gamealoon.models.Genre;
import com.gamealoon.models.InterestedGame;
import com.gamealoon.models.InterestedUser;
import com.gamealoon.models.Media;
import com.gamealoon.models.Platform;
import com.gamealoon.models.User;
import com.gamealoon.models.UserGameScoreMap;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;
import com.mongodb.Mongo;


public class GameDAO extends GloonDAO implements GameInterface{
	
	private static final GameDAO DATA_ACCESS_LAYER=new GameDAO();	
	private static final ArticleDAO articleDAOinstance = ArticleDAO.instantiateDAO();	
	private static final UserDAO userDAOinstance = UserDAO.instantiateDAO();
	private static final MediaDAO mediaDAOinstance = MediaDAO.instantiateDAO();
	private static final ActivityDAO activityDAOinstance = ActivityDAO.instantiateDAO();
	private static final PlatformDAO platformDAOinstance = PlatformDAO.instantiateDAO();
	private Datastore gloonDatastore=null;
	
	private GameDAO()
	{
		super();
		gloonDatastore=initDatastore();
	}
	
	/**
	 * Singleton way to instantiate Gloon DAO
	 * @return
	 */
	public static GameDAO instantiateDAO()
	{								
		return DATA_ACCESS_LAYER;
	}
	

	@Override
	public List<HashMap<String, Object>> getTopNGames(int limit, String platform) throws MalformedURLException {
		 List <HashMap<String, Object>> topGameMaps = new ArrayList<>();
		  List <Game> topGames = getTopGames(limit,platform);
		  if(topGames.size()>0)
		  {
			  for(Game game: topGames)
			  {
				HashMap<String, Object> gameMap = new HashMap<String, Object>();
				gameMap.put("gameId", game.getId().toString());
				gameMap.put("gameTitle", game.getTitle());
				gameMap.put("gameReleaseDate", game.getReleaseDate());				
				String gameBoxShot = game.getGameBoxShot();
				if(gameBoxShot.isEmpty())
				{
					gameMap.put("gameBoxShot",AppConstants.APP_IMAGE_DEFAULT_URL_PATH+"/boxShot.png");
				}
				else
				{
					Media media = mediaDAOinstance.getById(gameBoxShot);
					gameMap.put("gameBoxShot", media.getUrl());
				}
				gameMap.put("gameGenere", game.getGenre().toString().toString());
				gameMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle())+"-"+game.getId().toString());
				topGameMaps.add(gameMap);
			  }  
		  }
		  
	 	   return topGameMaps;
	}
	
	@Override
	public List<HashMap<String, Object>> getRecentNGames(int limit,String platform) throws MalformedURLException {
		List <HashMap<String, Object>> recentGameMaps = new ArrayList<>();
		 List <Game> recentGames = getRecentGames(limit,platform);
		  if(recentGames.size()>0)
		  {
			  for(Game game: recentGames)
			  {
				HashMap<String, Object> gameMap = new HashMap<String, Object>();
				gameMap.put("gameId", game.getId().toString());
				gameMap.put("gameTitle", game.getTitle());
				gameMap.put("gameReleaseDate", game.getReleaseDate());				
				String gameBoxShot = game.getGameBoxShot();
				if(gameBoxShot.isEmpty())
				{
					gameMap.put("gameBoxShot",AppConstants.APP_IMAGE_DEFAULT_URL_PATH+"/boxShot.png");
				}
				else
				{
					Media media = mediaDAOinstance.getById(gameBoxShot);
					gameMap.put("gameBoxShot", media.getUrl());
				}
				gameMap.put("gameGenere", game.getGenre().toString());
				gameMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle())+"-"+game.getId().toString());
				recentGameMaps.add(gameMap);
			  }  
		  }
		return recentGameMaps;
	}
	
	
	@Override
	public void save(Game game) {
		gloonDatastore.save(game);
	}
	
	@Override
	public Game findByTitle(String title) {		
		return gloonDatastore.find(Game.class, "title", title).get();
	}
	
	@Override
	public HashMap<String, Object> getById(String urlOrid, String username) throws MalformedURLException {		
		Mongo instance = getDatabaseInstance().getMongoInstance();
		HashMap<String, Object> gameMap = new HashMap<>();
		Game game = getGameById(urlOrid);
		if(game!=null)
		{
			gameMap.put("gameId", game.getId().toString());
			gameMap.put("gameTitle", game.getTitle());
			gameMap.put("gameDescription", game.getDescription());
			gameMap.put("gamePublisher", game.getPublisher());
			gameMap.put("gameDeveloper", game.getDeveloper());
			gameMap.put("gameReleaseDate", game.getReleaseDate());
			gameMap.put("gameRating", game.getRating());
			String gameBoxShot = game.getGameBoxShot();
			if(gameBoxShot.isEmpty())
			{
				gameMap.put("gameBoxShot",AppConstants.APP_IMAGE_DEFAULT_URL_PATH+"/boxShot.png");
			}
			else
			{
				Media media = mediaDAOinstance.getById(gameBoxShot);
				gameMap.put("gameBoxShot", media.getUrl());
			}
			
			gameMap.put("gameGenere", game.getGenre().toString());
			gameMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle())+"-"+game.getId().toString());
			Double gameScore = 0.0;
			if(gloonDatastore.getCount(UserGameScoreMap.class)>0)
			{							
				gameScore = RankAlgorithm.calculateNetworkGameScore(game.getId().toString(), instance);
				Logger.debug("Game score compare: "+gameScore.compareTo(game.getTotalScore()));
				if(gameScore.compareTo(game.getTotalScore())!=0)
				{
					game.setTotalScore(gameScore);
					save(game);
				}
					
			}			
			gameMap.put("gameScore", gameScore);		
			gameMap.put("gameNetworkScale", Game.getGameNetworkRating(gameScore));
			gameMap.put("gameActivities", activityDAOinstance.getActivitiesForGame(game));
			gameMap.put("gameCarousel", articleDAOinstance.getAllArticlesForGameCarousel(game.getId().toString()));
			try {
				if(Utility.date1BeforeDate2(Utility.convertFromStringToDateFormat2(game.getReleaseDate()), new Date()))
				{
					game.setGameReleaseStatus(Game.RELEASED);
				}
				else
				{
					game.setGameReleaseStatus(Game.NOT_RELEASED);
				}
			} catch (ParseException e) {
				Logger.error("Parsing problem in date conversion");
				e.printStackTrace();
				game.setGameReleaseStatus(Game.NOT_RELEASED);
			}
			
			List<HashMap<String, Object>> platformList = new ArrayList<>();
			for(String gamePlatform: game.getPlatforms())
			{
				HashMap<String, Object> platformMap = new HashMap<>();
				Platform platform = platformDAOinstance.findByShortTitle(gamePlatform);
				platformMap.put("platformTitle", platform.getTitle());
				platformMap.put("platformShortTitle", platform.getShortTitle());
				platformList.add(platformMap);
			}
			gameMap.put("gamePlatforms", platformList);
			
			List<HashMap<String, Object>> interestedUserList = new ArrayList<>();
			ArrayList<InterestedUser> interestedUsers = game.getInterestedIn();
			if(interestedUsers!=null)
			{
				Collections.sort(interestedUsers, new Comparator<InterestedUser>() {

					@Override
					public int compare(InterestedUser instance1, InterestedUser instance2) {					
						return instance2.getTimestamp().compareTo(instance1.getTimestamp());
					}
					
				});
				if(interestedUsers.size()>10)
				{
					interestedUsers=(ArrayList<InterestedUser>) interestedUsers.subList(0, 10);
				}
				for(InterestedUser interestedUser: interestedUsers)
				{
					HashMap<String, Object> interestedInUserMap = new HashMap<>();
					User user = userDAOinstance.findByUsername(interestedUser.getUserName());
					interestedInUserMap.put("interestedUserUserName", user.getUsername());
					String avatarPath = user.getAvatar();			
					if(avatarPath.isEmpty())
					{
						interestedInUserMap.put("interestedAvatarPath", AppConstants.APP_IMAGE_DEFAULT_URL_PATH+"/avatar.png");
					}
					else
					{
						Media media = mediaDAOinstance.getById(avatarPath);
						interestedInUserMap.put("interestedAvatarPath", media.getUrl());
					}					
					interestedUserList.add(interestedInUserMap);
				}
			}
			
			
			gameMap.put("gameInterestedUsers", interestedUserList);
			gameMap.put("totalInterestedUsers", interestedUserList.size());
			
			  
			List<Article> allPublishedArticles = articleDAOinstance.findAllPublishedArticlesByGame(game.getId().toString(),"all");
			List<String> playedTheGame=new ArrayList<>();
			List<String> scoredTheGame = new ArrayList<>();
			for(Article article: allPublishedArticles)
			{
				if(Category.Review.equals(article.getCategory()))
				{
					playedTheGame.add(article.getAuthor());
					scoredTheGame.add(article.getAuthor());
				}
			}
			List<HashMap<String, Object>> playedUserList = new ArrayList<>();
			
			
			
			for(String playedUser: playedTheGame)
			{
				HashMap<String, Object> playedUserMap = new HashMap<>();
				User user = userDAOinstance.findByUsername(playedUser);
				playedUserMap.put("playedUserUserName", user.getUsername());
				playedUserMap.put("playedUserAvatar", user.getAvatar());
				playedUserList.add(playedUserMap);
			}
			gameMap.put("gamePlayedUsers", playedUserList);
			gameMap.put("totalGamePlayedUsers", playedUserList.size()); 
			
			List<HashMap<String, Object>> userWhoScoredGameList = new ArrayList<>();
			for(String userWhoScoredGame: scoredTheGame)
			{
				HashMap<String, Object> userWhoScoredGameMap = new HashMap<>();
				User user = userDAOinstance.findByUsername(userWhoScoredGame);
				userWhoScoredGameMap.put("userWhoScoredGameUserName", user.getUsername());
				userWhoScoredGameMap.put("userWhoScoredGameUserAvatar", user.getAvatar());
				userWhoScoredGameList.add(userWhoScoredGameMap);
			}
			gameMap.put("usersWhoScoredGame", userWhoScoredGameList);
			gameMap.put("totalUsersWhoScoredGame", userWhoScoredGameList.size()); 
			
			List<HashMap<String, Object>> gameArticleList = articleDAOinstance.getNArticlesByCarouselSelectorAndCategory(game.getId().toString(), "all", new Date().getTime(), Article.GAME);		
			Logger.debug("game article list size "+gameArticleList.size());
			gameMap.put("gameArticles", gameArticleList);
			gameMap.put("totalGameArticles", gameArticleList.size());						  
			gameMap.put("gameTotalPublishedArticles",Article.allPublishedArticleCount());
			gameMap.put("gameTotalUsers",userDAOinstance.count());
			if(checkUserFollowingGameOrNot(userDAOinstance.findByUsername(username), game))
			{
				gameMap.put("isInterestedIn", 1);				
			}
			else
			{
				gameMap.put("isInterestedIn", 0);
			}
		}
		return gameMap;
	}
	
	@Override
	public ArrayList<HashMap<String, Object>> findAllByTerm(String term) throws MalformedURLException {
		ArrayList<HashMap<String, Object>> gameMaps = new ArrayList<>();
		Pattern titleRegex= Pattern.compile(term, Pattern.CASE_INSENSITIVE);
		List<Game> games = gloonDatastore.createQuery(Game.class).filter("title", titleRegex).asList();		
		if(games.size()>0)
		{
			for(Game game: games)
			{
				Logger.debug("Game: "+game);
				HashMap<String, Object> gameMap = new HashMap<>();
				gameMap.put("gameId",game.getId().toString());
				gameMap.put("gameTitle",game.getTitle());
				gameMap.put("value",game.getTitle());
				gameMap.put("gameGenre",game.getGenre().toString());
				String gameBoxShot = game.getGameBoxShot();
				if(gameBoxShot.isEmpty())
				{
					gameMap.put("gameBoxShot",AppConstants.APP_IMAGE_DEFAULT_URL_PATH+"/boxShot.png");
				}
				else
				{
					Media media = mediaDAOinstance.getById(gameBoxShot);
					gameMap.put("gameBoxShot", media.getUrl());
				}
				List<HashMap<String, Object>> platformList = new ArrayList<>();
				for(String gamePlatform: game.getPlatforms())
				{
					HashMap<String, Object> platformMap = new HashMap<>();
					Platform platform = platformDAOinstance.findByShortTitle(gamePlatform);
					platformMap.put("platformTitle", platform.getTitle());
					platformMap.put("platformShortTitle", platform.getShortTitle());
					platformList.add(platformMap);
				}
				gameMap.put("gamePlatforms", platformList);
				gameMaps.add(gameMap);
			}
		}
		else
		{
			HashMap<String, Object> gameMap= new HashMap<>();
			gameMap.put("status", "fail");
			gameMaps.add(gameMap);
		}
		
		return gameMaps;
	}
	
	@Override
	public Game getGameById(String urlOrid) {
		Logger.debug("UrlORId: "+urlOrid);
		if(!urlOrid.contains("-"))
		{
			return gloonDatastore.createQuery(Game.class).filter("_id", new ObjectId(urlOrid)).get();
		}
		else
		{
			ObjectId _id = Utility.fetchIdFromTitle(urlOrid);
			return gloonDatastore.createQuery(Game.class).filter("_id",_id).get();
		}
				
	}
	
	@Override
	public HashMap<String, String> createOrUpdateGame(DynamicForm requestData) {
		HashMap<String, String> response =new HashMap<>();
		response.put("status", "fail");
		Game game=null;
		try {
			game = createOrUpdateGameInstance(requestData);
			if(game!=null)
			{
				save(game);
				response.put("status","success");
			}
		} catch (ParseException e) {
			Logger.error("Something wrong happened "+e.fillInStackTrace());
			e.printStackTrace();
		}
		
		return response;
	}
	
	@Override
	public List<HashMap<String, Object>> getNGames(int limit, long timeStamp,String platform) throws MalformedURLException {
		List <HashMap<String, Object>> nGameMaps = new ArrayList<>();
		 List <Game> recentGames = getNGames(limit, platform, timeStamp);
		  if(recentGames.size()>0)
		  {
			  for(Game game: recentGames)
			  {
				HashMap<String, Object> gameMap = new HashMap<String, Object>();
				gameMap.put("gameId", game.getId().toString());
				gameMap.put("gameTitle", game.getTitle());
				gameMap.put("gameDescription", game.getDescription());
				gameMap.put("gameReleaseDate", game.getReleaseDate());
				gameMap.put("gameReleaseTimestamp", game.getReleaseTimeStamp());
				gameMap.put("gameInsertDate", game.getInsertTime());
				gameMap.put("gameInsertTimestamp", game.getTimestamp());
				gameMap.put("gameUpdateDate", game.getUpdateTime());
				gameMap.put("gamePublisher", game.getPublisher());
				gameMap.put("gameDeveloper", game.getDeveloper());
				String gameBoxShot = game.getGameBoxShot();
				if(gameBoxShot.isEmpty())
				{
					gameMap.put("gameBoxShot",AppConstants.APP_IMAGE_DEFAULT_URL_PATH+"/boxShot.png");
				}
				else
				{
					Media media = mediaDAOinstance.getById(gameBoxShot);
					gameMap.put("gameBoxShot", media.getUrl());
					gameMap.put("gameBoxShotId", media.getId().toString());
				}
				gameMap.put("gameRating", game.getRating());
				gameMap.put("gameGenre", game.getGenre().toString());
				gameMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle())+"-"+game.getId().toString());
				gameMap.put("gamePlatforms", game.getPlatforms());
				nGameMaps.add(gameMap);
			  }  
		  }
		return nGameMaps;
	}
	
	@Override
	public List<Game> getGamesForAdmin() {		
		return gloonDatastore.createQuery(Game.class).asList();
	}
	
	/**
	 * Get N recent games
	 * 
	 * @param gloonDatastore
	 * @return
	 */	
	private List<Game> getRecentGames(int limit, String platform)	
	{
		if(limit >0)
		{
			if("all".equalsIgnoreCase(platform))
			{
				return gloonDatastore.createQuery(Game.class).limit(limit).order("-releaseTimeStamp").asList();
			}
			else
			{
				return gloonDatastore.createQuery(Game.class).limit(limit).order("-releaseTimeStamp").filter("platforms", platform).asList();
			}
			
		}
		else
		{
			if("all".equalsIgnoreCase(platform))
			{
				return gloonDatastore.createQuery(Game.class).order("-releaseTimeStamp").asList();
			}
			else
			{
				return gloonDatastore.createQuery(Game.class).order("-releaseTimeStamp").filter("platforms", platform).asList();
			}
			
		}
		
	}
	
	private List<Game> getNGames(int limit, String platform, long timestamp)	
	{
		if(limit >0)
		{
			if("all".equalsIgnoreCase(platform))
			{
				return gloonDatastore.createQuery(Game.class).limit(limit).filter("timestamp <",timestamp).order("-timestamp").asList();
			}
			else
			{
				return gloonDatastore.createQuery(Game.class).limit(limit).filter("timestamp <",timestamp).order("-timestamp").filter("platforms", platform).asList();
			}
			
		}
		else
		{
			if("all".equalsIgnoreCase(platform))
			{
				return gloonDatastore.createQuery(Game.class).filter("timestamp <",timestamp).order("-timestamp").asList();
			}
			else
			{
				return gloonDatastore.createQuery(Game.class).filter("timestamp <",timestamp).order("-timestamp").filter("platforms", platform).asList();
			}
			
		}
		
	}
	
	/**
	 * Get N top games
	 * 
	 * @param gloonDatastore
	 * @return
	 */	
	private List<Game> getTopGames(int limit, String platform)	
	{
		if(limit >0)
		{
			if("all".equalsIgnoreCase(platform))
			{
				return gloonDatastore.createQuery(Game.class).limit(limit).order("-totalScore").asList();
			}
			else
			{
				return gloonDatastore.createQuery(Game.class).limit(limit).order("-totalScore").filter("platforms", platform).asList();
			}
			
		}
		else
		{
			if("all".equalsIgnoreCase(platform))
			{
				return gloonDatastore.createQuery(Game.class).order("-totalScore").asList();
			}
			else
			{
				return gloonDatastore.createQuery(Game.class).order("-totalScore").filter("platforms", platform).asList();
			}
			
		}
		
	}
	/**
	 * Create or update game data and return game instance
	 * 
	 * @param requestData
	 * @return
	 * @throws ParseException 
	 */
	private Game createOrUpdateGameInstance(DynamicForm requestData) throws ParseException
	{
		Game game = null;
		String id = requestData.get("id");
		String title = requestData.get("title");
		String description = requestData.get("description");
		String releaseDate = requestData.get("releaseDate");		
		String publisher = requestData.get("publisher");
		String developer = requestData.get("developer");
		String gameBoxShot = requestData.get("gameBoxShot");
		String genre = requestData.get("genre");
		String platforms = requestData.get("platforms");
		String rating = requestData.get("rating");
		Date time = new Date();
		
		if(id.isEmpty())
		{
			game = new Game();			
			game.setTotalScore(0);			
			game.setInterestedIn(new ArrayList<InterestedUser>());			
			game.setInsertTime(Utility.convertDateToString(time));
			game.setTimestamp(time.getTime());
		}
		else
		{
			 game = getGameById(id);
			 game.setUpdateTime(Utility.convertDateToString(time));
		}
		game.setTitle(title);
		game.setDescription(description);		
		if("noDate".equalsIgnoreCase(releaseDate))
		{
			game.setReleaseDate("TBA");
			game.setReleaseTimeStamp(Long.MAX_VALUE);
		}		
		else
		{
			game.setReleaseDate(releaseDate);
			game.setReleaseTimeStamp(Utility.convertFromStringToDate(releaseDate).getTime());
		}			
		game.setPublisher(publisher);
		game.setDeveloper(developer);
		game.setGameBoxShot(gameBoxShot);
		game.setGenre(Genre.valueOf(genre));
		game.setRating(rating);
		if(Utility.date1BeforeDate2(Utility.convertFromStringToDate(game.getReleaseDate()), new Date()))
		{
			game.setGameReleaseStatus(Game.RELEASED);
		}
		else
		{
			game.setGameReleaseStatus(Game.NOT_RELEASED);
		}
		game.setPlatforms(platforms.split(","));		
		return game;
	}

	/**
	 * Get N recently released games
	 * 
	 * @param gloonDatastore
	 * @return
	 */
	public List<Game> getRecentReleasedGames(int limit)
	{
		if(limit>0)
		{
			return gloonDatastore.createQuery(Game.class).filter("releaseDate <", Utility.convertDateToStringWithoutTime(new Date())).limit(limit).order("-releaseDate").asList();
		}
		else
		{
			return gloonDatastore.createQuery(Game.class).filter("releaseDate <", Utility.convertDateToStringWithoutTime(new Date())).order("-releaseDate").asList();
		}
	  	
	}
	
	/**
	 * Check whether user is interested in game or not
	 * 
	 * @param user
	 * @param game
	 * @return
	 */
	private Boolean checkUserFollowingGameOrNot(User user, Game game)
	{
		Boolean response =false;
		if(user!=null)
		{
			ArrayList<InterestedGame> interestedGames = user.getFollowingGames();
			for(InterestedGame interestedGame: interestedGames)
			{
				if(game.getId().toString().equalsIgnoreCase(interestedGame.getGameId()))
				{
					response = true;
					break;
				}
			}
		}
		
		return response;
	}

	

	

	

	

	

	

	

	
}
