package com.gamealoon.core.common;

/**
 * 
 * 
 * @author Manoj
 * @version 1.0
 * 
 */
public enum XPTriggerPoints {

	/**
	 * 
	 */
	FOLLOWED_BY(5),

	/**
	 * 
	 */
	NEWS_CREATION(5),

	/**
	 * 
	 */
	REVIEW_CREATION(10),

	/**
	 * 
	 */
	VIDEO_CREATION(8),

	/**
	 * 
	 */
	GAMEALOON_CREATION(10),

	/**
	 * 
	 */
	COMMENT_POSTED(2),

	/**
	 * 
	 */
	VOTE_UP(20);

	/**
	 * 
	 */
	private int experiencePoints;

	/**
	 * @param experiencePoints
	 */
	private XPTriggerPoints(final int experiencePoints) {
		this.experiencePoints = experiencePoints;
	}

	/**
	 * @return
	 */
	public int getExperiencePoints() {
		return experiencePoints;
	}
}
