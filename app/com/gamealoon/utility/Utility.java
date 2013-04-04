package com.gamealoon.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	 * This utility method converts date into string into yyyy-mm-dd format
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public static String convertDateToString(Date date)
	{
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/**
	 * This method generates user ids by clubbing various user attributes
	 * 
	 * 
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public static String generateUserId(String firstName, String middleName,
			String lastName, int day, int month, int year) {
		String userId="";
		userId=firstName.substring(0, 1)+lastName.substring(0, 1)+Integer.toString(year)+Integer.toString(month)+Integer.toString(day);
		return userId;
	}

}
