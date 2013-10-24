package com.gamealoon.database.daos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;
import play.Logger;
import com.gamealoon.algorithm.RankAlgorithm;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.GameInterface;
import com.gamealoon.models.Article;
import com.gamealoon.models.Category;
import com.gamealoon.models.Game;
import com.gamealoon.models.InterestedGame;
import com.gamealoon.models.InterestedUser;
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
	private static final ActivityDAO activityDAOinstance = ActivityDAO.instantiateDAO();
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
	public List<HashMap<String, Object>> getTopNGames(int limit, String platform) {
		 List <HashMap<String, Object>> topGameMaps = new ArrayList<>();
		  List <Game> topGames = getTopGames(limit);
		  if(topGames.size()>0)
		  {
			  for(Game game: topGames)
			  {
				HashMap<String, Object> gameMap = new HashMap<String, Object>();
				gameMap.put("gameId", game.getId().toString());
				gameMap.put("gameTitle", game.getTitle());
				gameMap.put("gameReleaseDate", game.getReleaseDate());				
				gameMap.put("gameBoxShot", AppConstants.APP_IMAGE_GAME_URL_PATH+"/"+Utility.shortenString(game.getTitle())+"/uploads/boxshot/"+game.getGameBoxShotPath());
				gameMap.put("gameGenere", game.getGenere());
				gameMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle())+"-"+game.getId().toString());
				topGameMaps.add(gameMap);
			  }  
		  }
		  
	 	   return topGameMaps;
	}
	
	@Override
	public List<HashMap<String, Object>> getRecentNGames(int limit,String platform) {
		List <HashMap<String, Object>> recentGameMaps = new ArrayList<>();
		 List <Game> recentGames = getRecentGames(limit);
		  if(recentGames.size()>0)
		  {
			  for(Game game: recentGames)
			  {
				HashMap<String, Object> gameMap = new HashMap<String, Object>();
				gameMap.put("gameId", game.getId().toString());
				gameMap.put("gameTitle", game.getTitle());
				gameMap.put("gameReleaseDate", game.getReleaseDate());				
				gameMap.put("gameBoxShot", AppConstants.APP_IMAGE_GAME_URL_PATH+"/"+Utility.shortenString(game.getTitle())+"/uploads/boxshot/"+game.getGameBoxShotPath());
				gameMap.put("gameGenere", game.getGenere());
				gameMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle())+"-"+game.getId().toString());
				recentGameMaps.add(gameMap);
			  }  
		  }
		return recentGameMaps;
	}
	/**
	 * Get N recent games
	 * 
	 * @param gloonDatastore
	 * @return
	 */	
	private List<Game> getRecentGames(int limit)	
	{
		if(limit >0)
		{
			return gloonDatastore.createQuery(Game.class).limit(limit).order("-releaseDate").asList();
		}
		else
		{
			return gloonDatastore.createQuery(Game.class).order("-releaseDate").asList();
		}
		
	}
	
	/**
	 * Get N top games
	 * 
	 * @param gloonDatastore
	 * @return
	 */	
	private List<Game> getTopGames(int limit)	
	{
		if(limit >0)
		{
			return gloonDatastore.createQuery(Game.class).limit(limit).order("-totalScore").asList();
		}
		else
		{
			return gloonDatastore.createQuery(Game.class).order("-totalScore").asList();
		}
		
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
	public HashMap<String, Object> findById(String urlOrid, String username) {		
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
			gameMap.put("gameBoxShot", AppConstants.APP_IMAGE_GAME_URL_PATH+"/"+Utility.shortenString(game.getTitle())+"/uploads/boxshot/"+game.getGameBoxShotPath());
			gameMap.put("gameGenere", game.getGenere());
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
			int parseFlag = 0;
			try
			{
				String releaseDateText = game.getReleaseDate();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
				Date releaseDate = simpleDateFormat.parse(releaseDateText);
				Date currentDate = new Date();
				
				if(releaseDate.compareTo(currentDate)<=0)
				{
					parseFlag =1;
				}
			}catch(ParseException pe)
			{
			  pe.printStackTrace();
			}
			
			if(parseFlag ==1)
			{
				gameMap.put("gameReleaseState", Game.RELEASED);				
			}
			else
			{
				gameMap.put("gameReleaseState", Game.NOT_RELEASED);
			}	
		
			List<HashMap<String, Object>> platformList = new ArrayList<>();
			for(Platform gamePlatform: game.getPlatforms())
			{
				HashMap<String, Object> platformMap = new HashMap<>();
				platformMap.put("platformTitle", gamePlatform.getTitle());
				platformMap.put("platformShortTitle", gamePlatform.getShortTitle());
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
					String avatarPath = user.getAvatarPath();			
					if(avatarPath.isEmpty())
					{
						interestedInUserMap.put("interestedAvatarPath", AppConstants.APP_IMAGE_DEFAULT_URL_PATH+"/avatar.png");
					}
					else
					{
						interestedInUserMap.put("interestedAvatarPath", AppConstants.APP_IMAGE_USER_URL_PATH+avatarPath);
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
				playedUserMap.put("playedUserAvatar", user.getAvatarPath());
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
				userWhoScoredGameMap.put("userWhoScoredGameUserAvatar", user.getAvatarPath());
				userWhoScoredGameList.add(userWhoScoredGameMap);
			}
			gameMap.put("usersWhoScoredGame", userWhoScoredGameList);
			gameMap.put("totalUsersWhoScoredGame", userWhoScoredGameList.size()); 
			
			List<HashMap<String, Object>> gameArticleList = articleDAOinstance.getNArticlesByCarouselSelectorAndCategory(game.getId().toString(), "all", new Date().getTime(), Article.GAME);			
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
	public ArrayList<HashMap<String, Object>> findAllByTerm(String term) {
		ArrayList<HashMap<String, Object>> gameMaps = new ArrayList<>();
		Pattern titleRegex= Pattern.compile(term, Pattern.CASE_INSENSITIVE);
		List<Game> games = gloonDatastore.createQuery(Game.class).filter("title", titleRegex).asList();		
		if(games.size()>0)
		{
			for(Game game: games)
			{
				System.out.println("Game: "+game);
				HashMap<String, Object> gameMap = new HashMap<>();
				gameMap.put("gameId",game.getId().toString());
				gameMap.put("gameTitle",game.getTitle());
				gameMap.put("value",game.getTitle());
				gameMap.put("gameGenre",game.getGenere());
				gameMap.put("gameBoxShot",AppConstants.APP_IMAGE_GAME_URL_PATH+"/"+Utility.shortenString(game.getTitle())+"/uploads/boxshot/"+game.getGameBoxShotPath());
				List<HashMap<String, Object>> platformList = new ArrayList<>();
				for(Platform gamePlatform: game.getPlatforms())
				{
					HashMap<String, Object> platformMap = new HashMap<>();
					platformMap.put("platformTitle", gamePlatform.getTitle());
					platformMap.put("platformShortTitle", gamePlatform.getShortTitle());
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
