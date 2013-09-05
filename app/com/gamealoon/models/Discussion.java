package com.gamealoon.models;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class Discussion {
	
	private String discussionId;
	private int discussionType;
	private String discussionGroupName;
	private String discussionCreator; //Person who created the discussion group
	private String gameId;
	private String platformId;
	private String inserTime;
	private Long timeStamp;

	public final static int GENERAL=1;
	public final static int GAME=2;
	public final static int PLATFORM=3;
	/**
	 * @return the discussionType
	 */
	public int getDiscussionType() {
		return discussionType;
	}
	/**
	 * @param discussionType the discussionType to set
	 */
	public void setDiscussionType(int discussionType) {
		this.discussionType = discussionType;
	}
	/**
	 * @return the discussionGroupName
	 */
	public String getDiscussionGroupName() {
		return discussionGroupName;
	}
	/**
	 * @param discussionGroupName the discussionGroupName to set
	 */
	public void setDiscussionGroupName(String discussionGroupName) {
		this.discussionGroupName = discussionGroupName;
	}
	/**
	 * @return the discussionCreator
	 */
	public String getDiscussionCreator() {
		return discussionCreator;
	}
	/**
	 * @param discussionCreator the discussionCreator to set
	 */
	public void setDiscussionCreator(String discussionCreator) {
		this.discussionCreator = discussionCreator;
	}
	/**
	 * @return the discussionId
	 */
	public String getDiscussionId() {
		return discussionId;
	}
	/**
	 * @param discussionId the discussionId to set
	 */
	public void setDiscussionId(String discussionId) {
		this.discussionId = discussionId;
	}
	/**
	 * @return the gameId
	 */
	public String getGameId() {
		return gameId;
	}
	/**
	 * @param gameId the gameId to set
	 */
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	/**
	 * @return the platformId
	 */
	public String getPlatformId() {
		return platformId;
	}
	/**
	 * @param platformId the platformId to set
	 */
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	/**
	 * @return the inserTime
	 */
	public String getInserTime() {
		return inserTime;
	}
	/**
	 * @param inserTime the inserTime to set
	 */
	public void setInserTime(String inserTime) {
		this.inserTime = inserTime;
	}
	/**
	 * @return the timeStamp
	 */
	public Long getTimeStamp() {
		return timeStamp;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}	
}
