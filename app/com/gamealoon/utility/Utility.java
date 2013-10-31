package com.gamealoon.utility;

//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
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
	 * Covert String into date into yyyy-MM-dd HH:mm:ss format
	 * 
	 * @param dateText
	 * @return
	 * @throws ParseException
	 */
	public static Date convertFromStringToDate(String dateText) throws ParseException
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
	}
	
	/**
	 * Covert String into date into yyyy-MM-dd format
	 * 
	 * @param dateText
	 * @return
	 * @throws ParseException
	 */
	public static Date convertFromStringToDateFormat2(String dateText) throws ParseException
	{
		return new SimpleDateFormat("yyyy-MM-dd").parse(dateText);
	}
	
	/**
	 * Convert from yyyy-MM-dd HH:mm:ss to dd M, yyyy
	 * 
	 * @param dateText
	 * @return
	 * @throws ParseException 
	 */
	public static String convertFromOneFormatToAnother(String dateText) throws ParseException
	{
		Date fromFormat = convertFromStringToDate(dateText);
		String response = new SimpleDateFormat("dd MMMMM,yyyy h:mm a").format(fromFormat);
		return response;
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
	 * Shorten the string by removing all special characters and whitespaces and converting to text into lower case
	 * 
	 * E.g Grand Theft Auto V = grandtheftautov
	 * 
	 * @param text
	 * @return
	 */
	public static String shortenString(String text)
	{
		String response ="";
		String pattern="([^A-Za-z0-9]+)";
		response = text.replaceAll(pattern, "");
		response =response.toLowerCase();
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
	
	/**
	 * Utility method to copy read file into write file
	 * 
	 * @param readFile
	 * @param writeFile
	 * @return
	 */
	public static boolean mediaCopy(File readFile, File writeFile)
	{
		boolean response = false;
		try {
			InputStream reader = new FileInputStream(readFile);
			OutputStream writer = new FileOutputStream(writeFile);
			byte[] buf =new byte[1024];
			int len;
		      while ((len = reader.read(buf)) > 0) {
		         writer.write(buf, 0, len);
		      }
		      reader.close();
		      writer.close();
		      response=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public static Boolean date1BeforeDate2(Date date1, Date date2)
	{
		return date1.compareTo(date2)<=0;
	}
	
	/**
	 * Milliseconds to human readable format- http://stackoverflow.com/questions/3859288/how-to-calculate-time-ago-in-java
	 * 
	 * @param duration
	 * @return
	 */
	 public static String millisToLongDHMS(long duration) {
	        StringBuffer res = new StringBuffer();
	        long temp = 0;
	        if (duration >= AppConstants.ONE_SECOND) {
	        	
	        	temp = duration / AppConstants.ONE_YEAR;
		          if (temp > 0) {
		            duration -= temp * AppConstants.ONE_YEAR;
		            res.append(temp).append(" year").append(temp > 1 ? "s" : "")
		               .append(duration >= AppConstants.ONE_MINUTE ? ", " : "");
		          }
	        	
	        	temp = duration / AppConstants.ONE_MONTH;
		          if (temp > 0) {
		            duration -= temp * AppConstants.ONE_MONTH;
		            res.append(temp).append(" month").append(temp > 1 ? "s" : "")
		               .append(duration >= AppConstants.ONE_MINUTE ? ", " : "");
		          }
		          
	          temp = duration / AppConstants.ONE_DAY;
	          if (temp > 0) {
	            duration -= temp * AppConstants.ONE_DAY;
	            res.append(temp).append(" day").append(temp > 1 ? "s" : "")
	               .append(duration >= AppConstants.ONE_MINUTE ? ", " : "");
	          }

	          temp = duration / AppConstants.ONE_HOUR;
	          if (temp > 0) {
	            duration -= temp * AppConstants.ONE_HOUR;
	            res.append(temp).append(" hour").append(temp > 1 ? "s" : "")
	               .append(duration >= AppConstants.ONE_MINUTE ? ", " : "");
	          }

	          temp = duration / AppConstants.ONE_MINUTE;
	          if (temp > 0) {
	            duration -= temp * AppConstants.ONE_MINUTE;
	            res.append(temp).append(" minute").append(temp > 1 ? "s" : "");
	          }

	          if (!res.toString().equals("") && duration >= AppConstants.ONE_SECOND) {
	            res.append(" and ");
	          }

	          temp = duration / AppConstants.ONE_SECOND;
	          if (temp > 0) {
	            res.append(temp).append(" second").append(temp > 1 ? "s" : "");
	          }
	          res.append(" ago");
	          return res.toString();
	        } else {
	          return "Just now";
	        }
	      }
	


}
