package com.gamealoon.database.interfaces;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface SearchInterface {
	

	/**
	 * Get Search response from search engine
	 * 
	 * 
	 * @param type
	 * @return
	 */
	
	public HashMap<String, Object> getSearchResponse(ArrayList<String> keywordList) throws IllegalAccessException, MalformedURLException, IOException;
	
	/**
	 * Scrapes page to fetch title, domain url, description and display image using open graph protocol to fetch information
	 * 
	 * @param link
	 * @return
	 */
	public HashMap<String, String> getScrapedPage(String link);

}
