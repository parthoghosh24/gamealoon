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

}
