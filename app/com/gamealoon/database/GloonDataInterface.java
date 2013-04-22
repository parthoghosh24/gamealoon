package com.gamealoon.database;

import java.util.HashMap;
import java.util.List;
import com.gamealoon.models.Game;
import com.google.code.morphia.Datastore;

public interface GloonDataInterface {

	
	public List<Game> allGames(Datastore gloonDatastore);
	public HashMap<String, Object> getAllArticlesForCarousel(Datastore gloonDatastore, String type);
	public List<HashMap<String, Object>> getTopNGames(Datastore gloonDatastore, int limit, String platform);
	public HashMap<String, Object> getRecentAllNArticles(Datastore gloonDatastore, int limit, String platform);
	public List<HashMap<String, Object>> getTopNUsers(Datastore gloonDatastore, int limit);
}
