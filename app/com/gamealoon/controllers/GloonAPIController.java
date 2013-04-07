package com.gamealoon.controllers;



import java.util.ArrayList;
import java.util.List;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.models.Game;
import com.gamealoon.models.User;
import com.google.code.morphia.Datastore;

import static play.libs.Json.toJson;
import play.mvc.Controller;
import play.mvc.Result;

public class GloonAPIController extends Controller{
	
	static GloonDAO daoInstance = GloonDAO.instantiateDAO();
	
	
	public static Result getGames()
	{
		Datastore gloonDatastore = daoInstance.initDatastore();
		List<Game> games = daoInstance.allGames(gloonDatastore);
		if(games.size()>0)
		{
			return ok(toJson(games));
		}
		else
		{
			return ok("Sorry no data found!!!");
		}
		
	}
	
	public User getUser(String name)
	{
		//TODO fetch user based on name
		return null;
	}
	
	public ArrayList<User> getUsers(long number)
	{
		//TODO fetch certain number of users
		return null;
	}
	
	

}
