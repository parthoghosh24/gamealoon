package com.gamealoon.database.daos;

import java.util.List;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.UserGameScoreMapInterface;
import com.gamealoon.models.UserGameScoreMap;
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
	public void createScoreMap(String gameId, String username, Double gameScore, Double networkUserWeight) {
		UserGameScoreMap scoreMap = new UserGameScoreMap();
		scoreMap.setUsername(username);
		scoreMap.setGameId(gameId);
		scoreMap.setGameScore(gameScore);
		scoreMap.setNetworkUserWeight(networkUserWeight);
		save(scoreMap);
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

}
