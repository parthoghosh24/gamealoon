package utility;

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

}
