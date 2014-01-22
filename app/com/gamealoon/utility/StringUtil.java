package com.gamealoon.utility;

/**
 * This class is utility class containing various string related APIs which can be accessed directly.
 * 
 * @author Manoj
 * @version 1.0
 */
public class StringUtil {

	/**
	 * Returns true is passed string is null or empty otherwise false.
	 * 
	 * @param string {@link String} to be checked for null or empty
	 * @return
	 */
	public static boolean isNullOrEmpty(String string) {
		return isNullOrEmpty(string, false);
	}

	/**
	 * Returns true is passed string is null or empty otherwise false.
	 * 
	 * @param string {@link String} to be checked for null or empty
	 * @param spaceAllowed specifies if space is allowed as a valid value
	 * @return returns true if passed string is null or empty otherwise false
	 */
	public static boolean isNullOrEmpty(final String string, final boolean spaceAllowed) {
		if (spaceAllowed) {
			return string == null || string.isEmpty();
		} else {
			return string == null || string.trim().isEmpty();
		}
	}

	/**
	 * API to concatenate a number of objects together in the form of a String.
	 * 
	 * @param objects Objects to be concatenated to form a String.
	 * @return The finally concatenated string.
	 */
	public static String concatenate(final Object... objects) {
		String concatenatedString = null;
		if (null != objects) {
			StringBuilder concatenatedStringBuilder = new StringBuilder();
			for (Object object : objects) {
				concatenatedStringBuilder.append(object);
			}
			concatenatedString = concatenatedStringBuilder.toString();
		}
		return concatenatedString;
	}

	/**
	 * API to concatenate a number of Strings together in the form of a String.
	 * 
	 * @param strings The strings to be concatenated.
	 * @return The finally concatenated string.
	 */
	public static String concatenate(final String... strings) {
		String concatenatedString = null;
		if (null != strings) {
			StringBuilder concatenatedStringBuilder = new StringBuilder();
			for (String string : strings) {
				concatenatedStringBuilder.append(string);
			}
			concatenatedString = concatenatedStringBuilder.toString();
		}
		return concatenatedString;
	}

}
