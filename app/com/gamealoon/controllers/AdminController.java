package com.gamealoon.controllers;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.gamealoon.database.daos.GameDAO;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import static play.libs.Json.toJson;

public class AdminController extends Controller{

	private static final GameDAO gameDaoInstance = GameDAO.instantiateDAO();	
	
	public static Result getAdminData()
	{
		List<HashMap<String, Object>> gameMaps = new ArrayList<>();
		try {
			gameMaps= gameDaoInstance.getNGames(10, Long.MAX_VALUE, "all");
		} catch (MalformedURLException e) {
			Logger.error("Error in Admin controller getAdminData: ",e.fillInStackTrace());
			e.printStackTrace();
		}
		return ok(toJson(gameMaps));
	}
}
