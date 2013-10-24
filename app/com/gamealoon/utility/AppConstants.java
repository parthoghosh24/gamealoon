package com.gamealoon.utility;

import java.io.IOException;

import play.api.Play;

public class AppConstants {

	//Total article categories
	public static final int TOTAL_CATEGORIES=5;
	public static final String DB_NAME="gloonDb";
	public static final String ALL="all";
	public static final String REVIEW="review";
	public static final String FEATURE="feature";
	public static final String NEWS="news";
	public static final String GLOONICLE="gloonicle";
	public static final String PREVIEW="preview";
	public static final String COOL ="cool";
	public static final String UNCOOL ="uncool";
	public final static String SEEDURL ="http://localhost:8080";	//this is temp
	public final static String APP_BASE_URL="http://192.168.0.103:9000";
	public final static int EMAIL_NOT_CONFIRMED =0;
	public final static int EMAIL_CONFIRMED =1;
	public final static int USER_PROFILE=1;
	public final static int USER_PAGE=2;
	public final static String APP_ABSOLUTE_PATH=returnCanonicalPath();
	public final static String APP_ABSOLUTE_IMAGE_PATH=APP_ABSOLUTE_PATH+"\\public\\images\\";
	public final static String APP_ABSOLUTE_IMAGE_USER_PATH=APP_ABSOLUTE_IMAGE_PATH+"\\user\\";
	public final static String APP_ABSOLUTE_IMAGE_GAME_PATH=APP_ABSOLUTE_IMAGE_PATH+"\\game\\";
	public final static String APP_IMAGE_URL_PATH=APP_BASE_URL+"/assets/images";
	public final static String APP_IMAGE_DEFAULT_URL_PATH=APP_BASE_URL+"/assets/images/default";
	public final static String APP_IMAGE_USER_URL_PATH=APP_BASE_URL+"/assets/images/user";
	public final static String APP_IMAGE_GAME_URL_PATH=APP_BASE_URL+"/assets/images/game";
	
	/**
	 * Time Constants - http://stackoverflow.com/questions/3859288/how-to-calculate-time-ago-in-java
	 */
	 public final static long ONE_SECOND = 1000;
     public final static long SECONDS = 60;
     public final static long ONE_MINUTE = ONE_SECOND * 60;
     public final static long MINUTES = 60;
     public final static long ONE_HOUR = ONE_MINUTE * 60;
     public final static long HOURS = 24;
     public final static long ONE_DAY = ONE_HOUR * 24;
     public final static long ONE_MONTH = ONE_DAY*30;
     public final static long ONE_YEAR = ONE_MONTH*12;
	
	/**
	 * Visibility
	 */
	public final static int PUBLIC=1;
	public final static int PRIVATE=2;
	
	
	/**
	 * Returns app canonical path
	 * 
	 * @return
	 */
	private static String returnCanonicalPath()
	{
		try {
			return Play.current().path().getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	
}
