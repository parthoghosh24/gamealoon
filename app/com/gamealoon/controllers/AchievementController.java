package com.gamealoon.controllers;

import static play.data.Form.form;
import static play.libs.Json.toJson;
import java.util.HashMap;
import java.util.List;
import com.gamealoon.database.daos.AchievementDAO;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;

public class AchievementController extends Controller{
	
	private static final AchievementDAO achievementDaoInstance = AchievementDAO.instantiateDAO();
	
	public static Result getNAchievements(String timestamp)
	{
		List<HashMap<String, Object>> achievements = achievementDaoInstance.getNAchievements(10, Long.valueOf(timestamp));
		return ok(toJson(achievements));
	}
	
	public static Result createOrUpdateAchievement()
	{
		DynamicForm requestData = form().bindFromRequest();
		HashMap<String, String> response = achievementDaoInstance.createOrUpdateAchievement(requestData);
		return ok(toJson(response));
	}

}
