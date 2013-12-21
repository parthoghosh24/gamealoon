package com.gamealoon.database.daos;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import com.gamealoon.algorithm.Search;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.SearchInterface;
import com.gamealoon.utility.AppConstants;

public class SearchDAO extends GloonDAO implements SearchInterface{
	
	private static final SearchDAO DATA_ACCESS_LAYER=new SearchDAO();	
	
	private SearchDAO()
	{
		super();
	}
	
	/**
	 * Singleton way to instantiate Gloon DAO
	 * @return
	 */
	public static SearchDAO instantiateDAO()
	{								
		return DATA_ACCESS_LAYER;
	}
	
	@Override
	public HashMap<String, Object> getSearchResponse(ArrayList<String> keywordList) throws IllegalAccessException, MalformedURLException, IOException {
		/**
		 * Temporary resolution... Need to improve
		 */		
		Search.initAndRefresh(AppConstants.SEEDURL);
		HashMap<String, Object> response=new HashMap<>();
		ArrayList<String> responseUrlList= new ArrayList<>();
		for(String keyword: keywordList)
		{
			ArrayList<String> urlList = Search.lookup(keyword);
			responseUrlList.addAll(urlList);
		}
		response.put("queryResponse", responseUrlList);
		return response;
	}

	@Override
	public HashMap<String, String> getScrapedPage(String link) {
		
		return Search.scrapePage(link);
	}
}
