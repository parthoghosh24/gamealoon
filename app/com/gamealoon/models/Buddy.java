package com.gamealoon.models;

import com.google.code.morphia.annotations.Embedded;

/**
 * Buddy class maintains information about followers and followings
 * 
 * @author Partho
 *
 */

@Embedded
public class Buddy {
	private String buddyId;
	private String userName;
	private String avatarPath;
    private int chatState;
    private int blockState;
    private String insertTime;
    private Long timestamp;
    
    //addOrRemove flags
    public static int ADD=0;
    public static int REMOVE=1;
    
    //Block states
    public static int UNBLOCK=0;
    public static int BLOCK=1;        
	/**
	 * @return the buddyId
	 */
	public String getBuddyId() {
		return buddyId;
	}
	/**
	 * @param buddyId the buddyId to set
	 */
	public void setBuddyId(String buddyId) {
		this.buddyId = buddyId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the avatarPath
	 */
	public String getAvatarPath() {
		return avatarPath;
	}
	/**
	 * @param avatarPath the avatarPath to set
	 */
	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}
	/**
	 * @return the chatState
	 */
	public int getChatState() {
		return chatState;
	}
	/**
	 * @param chatState the chatState to set
	 */
	public void setChatState(int chatState) {
		this.chatState = chatState;
	}
	/**
	 * @return the blockState
	 */
	public int getBlockState() {
		return blockState;
	}
	/**
	 * @param blockState the blockState to set
	 */
	public void setBlockState(int blockState) {
		this.blockState = blockState;
	}
	/**
	 * @return the insertTime
	 */
	public String getInsertTime() {
		return insertTime;
	}
	/**
	 * @param insertTime the insertTime to set
	 */
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	/**
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
    
    
}
