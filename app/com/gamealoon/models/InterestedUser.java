package com.gamealoon.models;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class InterestedUser {

	private String userId;
	private String userName;
	private String avatarPath;
	private String insertTime;
	private Long timestamp;
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
}
