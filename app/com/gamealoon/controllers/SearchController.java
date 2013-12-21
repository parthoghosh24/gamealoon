package com.gamealoon.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
//import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.daos.SearchDAO;
import com.gamealoon.utility.Utility;

import play.data.DynamicForm;
import play.mvc.Result;
import play.mvc.Controller;
import static play.data.Form.form;
import static play.libs.Json.toJson;

public class SearchController extends Controller{
	
	private static final SearchDAO searchDAOInstance = SearchDAO.instantiateDAO();
	
	public static Result searchResponse(String query)
	{		
		ArrayList<String> keywordList = Utility.queryTokenize(query);		
		HashMap<String, Object> queryResponse=new HashMap<>();
		try{
			queryResponse = searchDAOInstance.getSearchResponse(keywordList);
		}
		catch(IllegalAccessException ie)
		{
			ie.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ok(toJson(queryResponse));
	}
	
	public static Result scrapePage()
	{
		DynamicForm request = form().bindFromRequest();
		String link = request.get("link");
		return ok(toJson(searchDAOInstance.getScrapedPage(link)));
	}

}
