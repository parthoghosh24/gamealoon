package com.gamealoon.core.common;

/**
 * Post hotness level is a ranking system for post which tells how much a Post is popular
 * 
 * @author Partho
 *
 */
public enum PostHotness {
	
	/**
	 * Cold
	 */
	COLD(0),
	
	/**
	 * Warm
	 */
	WARM(1),
	
	/**
	 * Ignited
	 */
	IGNITED(2),
	
	/**
	 * Rising Heat
	 */
	RISING_HEAT(3),
	
	/**
	 * Hot
	 */
	HOT(4),
	
	/**
	 * Super Hot
	 */
	SUPER_HOT(5),
	
	/**
	 * Red Hot
	 */
	RED_HOT(6);
	
	
	/**
	 * 
	 */
	private int hotnessValue;
	
	private PostHotness(final int hotnessValue)
	{
		this.hotnessValue = hotnessValue;
	}
	
	public int getHotnessValue()
	{
		return hotnessValue;
	}

}
