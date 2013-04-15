package com.gamealoon.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.gamealoon.models.Platform;

/**
 * Utility class which stores
 * 
 * @author Partho
 *
 */
public class Utility {
	
	/**
	 * This small utility method generates hexaString
	 * 
	 * @param key
	 * @return
	 */
	public static String generateHexaString(int key)
	{
		return Integer.toHexString(key);
	}
	
	/**
	 * This utility method converts date into string into yyyy-mm-dd HH:mm:ss format
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public static String convertDateToString(Date date)
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	
	/**
	 * This method converts date to string without time details. Returns only in yyy-MM-dd format.
	 * 
	 * @param date
	 * @return
	 */
	public static String convertDateToStringWithoutTime(Date date)
	{
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
		
		
	public static List<String> titleList(Set<Platform> platforms)
	{
		List<String> titleList = new ArrayList<>();
		for(Platform platform: platforms)
		{
			
				titleList.add(platform.getTitle());			
		}
		return titleList;
	}
	
}
