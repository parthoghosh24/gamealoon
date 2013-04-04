package com.gamealoon.controllers;



import java.util.ArrayList;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.models.Game;
import com.gamealoon.models.User;
import com.google.code.morphia.Datastore;

import static play.libs.Json.toJson;
import play.mvc.Controller;
import play.mvc.Result;

public class GloonAPI extends Controller{
	
	static GloonDAO daoInstance = GloonDAO.instantiateDAO();	
	
	public static Result getGame(String title)
	{
		System.out.println("title-> "+title);		
		Datastore gloonDatastore = daoInstance.initDatastore();
		System.out.println("gloonDatastore ->"+gloonDatastore);
		System.out.println("gloonDatastore count-> "+gloonDatastore.getCount(Game.class));		
		Game game = gloonDatastore.find(Game.class,"title",title).get();
		System.out.println("Game-------> "+game);
		
		if(game!=null)
		{
			return ok(toJson(game));
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
