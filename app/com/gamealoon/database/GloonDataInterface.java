package com.gamealoon.database;

import java.util.HashMap;
import java.util.List;
import com.gamealoon.models.Game;
import com.google.code.morphia.Datastore;

public interface GloonDataInterface {

	
	public List<Game> allGames(Datastore gloonDatastore);
	public List<HashMap<String, Object>> getAllArticlesForCarousel(Datastore gloonDatastore, String type);
}
