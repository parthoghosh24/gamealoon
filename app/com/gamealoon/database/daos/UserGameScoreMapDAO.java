package com.gamealoon.database.daos;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.UserGameScoreMapInterface;
import com.gamealoon.models.UserGameScoreMap;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

public class UserGameScoreMapDAO extends GloonDAO implements UserGameScoreMapInterface{
	
	private static final UserGameScoreMapDAO DATA_ACCESS_LAYER=new UserGameScoreMapDAO();
	private Datastore gloonDatastore=null;
	private UserGameScoreMapDAO()
	{
		super();
		gloonDatastore=initDatastore();		
	}
	
	/**
	 * Singleton way to instantiate Gloon DAO
	 * @return
	 */
	public static UserGameScoreMapDAO instantiateDAO()
	{								
		return DATA_ACCESS_LAYER;
	}

	@Override
	public void save(UserGameScoreMap scoreMap) {
		gloonDatastore.save(scoreMap);
		
	}

	@Override
	public HashMap<String, String> createOrUpdateScoreMap(String id, String gameId, String username, Double gameScore, Double networkUserWeight) {
		HashMap<String, String> response = new HashMap<>();
		
		UserGameScoreMap userGameScoreMap = createOrUpdateScoreMapInstance(id,gameId, username, gameScore, networkUserWeight);
		if(userGameScoreMap!=null)
		{
			save(userGameScoreMap);
			response.put("status", "success");
		}
		
		
		
		return response;
	}

	private UserGameScoreMap createOrUpdateScoreMapInstance(String id,String gameId, String username, Double gameScore,Double networkUserWeight) {
		UserGameScoreMap scoreMap = null;
		Date time = new Date();
		if(id.isEmpty())
		{
			scoreMap=new UserGameScoreMap();
			scoreMap.setInsertTime(Utility.convertDateToString(time));
			scoreMap.setTimestamp(time.getTime());
		}
		else
		{
			scoreMap=getById(id);
			scoreMap.setUpdateTime(Utility.convertDateToString(time));
		}
		scoreMap.setUsername(username);
		scoreMap.setGameId(gameId);
		scoreMap.setGameScore(gameScore);
		scoreMap.setNetworkUserWeight(networkUserWeight);
		return scoreMap;
	}

	@Override
	public List<UserGameScoreMap> findAllByGameId(String gameId) {		
		return gloonDatastore.createQuery(UserGameScoreMap.class).filter("gameId", gameId).asList();
	}

	@Override
	public List<UserGameScoreMap> findAllByUserId(String username) {	
		return gloonDatastore.createQuery(UserGameScoreMap.class).filter("username", username).asList();
	}

	@Override
	public UserGameScoreMap findByUserAndGame(String username, String gameId) {
		
		return gloonDatastore.createQuery(UserGameScoreMap.class).filter("gameId", gameId).filter("username", username).get();
	}

	@Override
	public UserGameScoreMap getById(String id) {		
		return gloonDatastore.get(UserGameScoreMap.class, new ObjectId(id));
	}

}
