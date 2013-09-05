package com.gamealoon.database.daos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.GameInterface;
import com.gamealoon.models.Article;
import com.gamealoon.models.Category;
import com.gamealoon.models.Game;
import com.gamealoon.models.InterestedGame;
import com.gamealoon.models.InterestedUser;
import com.gamealoon.models.Platform;
import com.gamealoon.models.User;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

public class GameDAO extends GloonDAO implements GameInterface{
	
	private static final GameDAO DATA_ACCESS_LAYER=new GameDAO();	
	private static final ArticleDAO articleDAOinstance = ArticleDAO.instantiateDAO();	
	private static final UserDAO userDAOinstance = UserDAO.instantiateDAO();
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
		 List <HashMap<String, Object>> topGames = new ArrayList<>();
		  List <Game> recentGames = getRecentGames(limit);
		  if(recentGames.size()>0)
		  {
			  for(Game game: recentGames)
			  {
				HashMap<String, Object> gameMap = new HashMap<String, Object>();
				gameMap.put("gameId", game.getId().toString());
				gameMap.put("gameTitle", game.getTitle());
				gameMap.put("gameReleaseDate", game.getReleaseDate());
				gameMap.put("gameScore", game.getScore());
				gameMap.put("gameBoxShot", game.getGameBoxShotPath());
				gameMap.put("gameGenere", game.getGenere());
				gameMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle())+"-"+game.getId().toString());
				topGames.add(gameMap);
			  }  
		  }
		  
	 	   return topGames;
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
			gameMap.put("gameBoxShot", game.getGameBoxShotPath());
			gameMap.put("gameGenere", game.getGenere());
			gameMap.put("gameEncodedUrl", Utility.encodeForUrl(game.getTitle())+"-"+game.getId().toString());
			gameMap.put("gameScore", game.getScore());			
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
			for(InterestedUser interestedUser: game.getInterestedIn())
			{
				HashMap<String, Object> interestedInUserMap = new HashMap<>();
				interestedInUserMap.put("interestedUserUserName", interestedUser.getUserName());
				interestedInUserMap.put("interestedUserAvatar", interestedUser.getAvatarPath());
				interestedUserList.add(interestedInUserMap);
			}
			gameMap.put("gameInterestedUsers", interestedUserList);
			gameMap.put("totalInterestedUsers", interestedUserList.size());
			
			  
			List<Article> allPublishedArticles = articleDAOinstance.findAllPublishedArticlesByGame(game.getId().toString());
			List<User> playedTheGame=new ArrayList<>();
			List<User> scoredTheGame = new ArrayList<>();
			for(Article article: allPublishedArticles)
			{
				if(Category.Review.equals(article.getCategory()))
				{
					playedTheGame.add(article.getAuthor());
					scoredTheGame.add(article.getAuthor());
				}
			}
			List<HashMap<String, Object>> playedUserList = new ArrayList<>();
			
			
			
			for(User playedUser: playedTheGame)
			{
				HashMap<String, Object> playedUserMap = new HashMap<>();
				playedUserMap.put("playedUserUserName", playedUser.getUsername());
				playedUserMap.put("playedUserAvatar", playedUser.getAvatarPath());
				playedUserList.add(playedUserMap);
			}
			gameMap.put("gamePlayedUsers", playedUserList);
			gameMap.put("totalGamePlayedUsers", playedUserList.size()); 
			
			List<HashMap<String, Object>> userWhoScoredGameList = new ArrayList<>();
			for(User userWhoScoredGame: scoredTheGame)
			{
				HashMap<String, Object> userWhoScoredGameMap = new HashMap<>();
				userWhoScoredGameMap.put("userWhoScoredGameUserName", userWhoScoredGame.getUsername());
				userWhoScoredGameMap.put("userWhoScoredGameUserAvatar", userWhoScoredGame.getAvatarPath());
				userWhoScoredGameList.add(userWhoScoredGameMap);
			}
			gameMap.put("usersWhoScoredGame", userWhoScoredGameList);
			gameMap.put("totalUsersWhoScoredGame", userWhoScoredGameList.size()); 
			
			List<HashMap<String, Object>> gameArticleList = new ArrayList<>();
			for(Article gameArticle: articleDAOinstance.findAllPublishedArticlesByGame(game.getId().toString()))
			{
				   HashMap<String, Object> articleMap = new HashMap<>();				
				   articleMap.put("articleId", gameArticle.getId().toString());
				   articleMap.put("articleTitle", gameArticle.getTitle());
				   
				   HashMap<String, Object> authorMap = new HashMap<>();
				   User author = gameArticle.getAuthor();
				   if(author!= null)
				   {
					   authorMap.put("articleAuthorUserName", author.getUsername());
					   authorMap.put("articleAuthorAvatar", author.getAvatarPath());
				   }
				   articleMap.put("articleAuthor", authorMap);
				   articleMap.put("articleStrippedTitle", gameArticle.getTitle().substring(0, 15)+"...");
				   articleMap.put("articleSubTitle", gameArticle.getSubtitle());
				   articleMap.put("articleBody", gameArticle.getBody());
				   articleMap.put("articleCategory", gameArticle.getCategory().toString());
				   articleMap.put("articleEncodedUrlTitle", Utility.encodeForUrl(gameArticle.getTitle())+"-"+gameArticle.getId().toString());
				   articleMap.put("articleState", gameArticle.getState());
				   articleMap.put("articleInsertTime", gameArticle.getInsertTime());
				   articleMap.put("articleUpdateTime", gameArticle.getUpdateTime());
				   articleMap.put("articlePublishDate", gameArticle.getPublishDate());
				   articleMap.put("articlePlatforms", Utility.titleList(gameArticle.getPlatforms()));
				   gameArticleList.add(articleMap);
			}
			gameMap.put("gameArticles", gameArticleList);
			gameMap.put("totalGameArticles", gameArticleList.size());						  
			gameMap.put("gameTotalPublishedArticles",Article.allPublishedArticleCount());
			gameMap.put("gameTotalUsers",User.getAllUserCount());
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
	
	/**
	 * Return Game found by ID
	 * 
	 * @param id
	 * @return
	 */
	private Game getGameById(String urlOrid) {
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
