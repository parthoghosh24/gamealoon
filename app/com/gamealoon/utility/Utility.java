package com.gamealoon.utility;

//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.bson.types.ObjectId;

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
		
	/**
	 * This actually converts Platform object list into list of strings containing platform names
	 * 	
	 * @param arrayList
	 * @return
	 */
	public static List<String> titleList(ArrayList<Platform> arrayList)
	{
		List<String> titleList = new ArrayList<>();
		for(Platform platform: arrayList)
		{
			
				titleList.add(platform.getTitle());			
		}
		return titleList;
	}
	
	/**
	 * Encode string in url and seo friendly way. Removes special chars, lower cases the string and replaces whitespace with -
	 * 
	 * @param title
	 * @return
	 */
	public static String encodeForUrl(String title)
	{
		String response ="";
		
		//Smallify the string
		title = title.toLowerCase();
		
		//remove special characters from string
		String pattern="([^A-Za-z0-9 ]+)";
		title = title.replaceAll(pattern, "");		
		response = title.replace(" ", "-");
		return response;
	}
	
	/**
	 * Fetch id from title
	 * 
	 * @param title
	 * @return
	 */
	public static ObjectId fetchIdFromTitle(String title)
	{
		String id = title.substring(title.lastIndexOf("-")+1);
		ObjectId _id = new ObjectId(id);		
		return _id;
	}
	
	/**
	 * Flatten a list
	 * 
	 * @param list
	 * @return
	 */
	public static ArrayList<Object> flattenList(ArrayList<?> list)
	{
		ArrayList<Object> result = new ArrayList<>();
		for(Object item: list)
		{
			if(item instanceof ArrayList<?>)
			{
				for(Object data: (ArrayList<?>)item)
				{
					result.add(data);
				}
			}
			else
			{
				result.add(item);
			}
		}
		return result;
	}
	
	/**
	 * Query tokenizer. Just built it for fun :D
	 * 
	 * @param input
	 * @return
	 */
	public static ArrayList<String> queryTokenize(String input)
	{
		ArrayList<String> tokens = new ArrayList<>();
		input=input.substring(input.indexOf("q=")+2);
		String keyword="";		
		for(int index=0; index<input.length();++index)
		{		
		    keyword="";		    
			while(index<=input.length()-1 && input.charAt(index)!=' ')
			{ 
				keyword+=input.charAt(index);
				++index;						
			}
			tokens.add(keyword);			
		}
		return tokens;
	}
	
	/**
	 * This method returns captialized String. That is if lets say one passes a string called "review", this method should return "Review"
	 * 
	 * @param input
	 * @return
	 */
	public static String capitalizeString(String input)
	{
		Character firstChar = input.charAt(0);		
		firstChar=Character.toUpperCase(firstChar);		
		String output=input.replace(input.charAt(0), firstChar);		
		return output;
	}
	
	/**
	 * Covert String with a pattern like ["abc","def"] in String[]
	 * 
	 * @param input
	 * @return
	 */
	public static String[] stringToList(String input)
	{		
		String modifiedInput = input.substring(1, input.indexOf("]"));
		modifiedInput = modifiedInput.replace("\"", "");
		return modifiedInput.split(",");		
	}
	/*public static void main(String[] args) throws UnsupportedEncodingException
	{
		String link ="http://www.mysite.com/search/q=Superman";
		System.out.println(URLEncoder.encode(link, "utf-8"));
	}*/


}
