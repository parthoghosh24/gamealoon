package com.gamealoon.models;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * Just like any other social crowd powered web-apps, this app also keeps track of user activities.
 * Activity class stores activity related information such as Activity Id, Activity Type, UserID, EntityID
 * @author Partho
 *
 */

@Entity
public class Activity {

	@Id
	private ObjectId id;
	private Integer type;
	private String  username;
	private String entityId;
	private Integer visibility;
	private String insertTime;
	private String updateTime;
	private Long timestamp;
	/**
	 * Activity Types
	 * 
	 */
	public final static int ACTIVITY_POST_PUBLISH=1;
	public final static int ACTIVITY_USER_FOLLOWS=2;	
	public final static int ACTIVITY_POST_COOL=3;
	public final static int ACTIVITY_POST_COMMENT=4;
	public final static int ACTIVITY_NEW_DISCUSSION=5;
	public final static int ACTIVITY_JOIN_DISCUSSION=6;
	public final static int ACTIVITY_NEW_ACHIEVMENT=7;
	public final static int ACTIVITY_USER_UNFOLLOWS=8;
	public final static int ACTIVITY_BLOCK=9;
	public final static int ACTIVITY_UNBLOCK=10;
	public final static int ACTIVITY_FOLLOW_GAME=11;
	public final static int ACTIVITY_UNFOLLOW_GAME=12;
	public final static int ACTIVITY_PUBLISH_FOR_GAME=13;
	public final static int ACTIVITY_POST_NOT_COOL=14;
	
	
	/**
	 * Visibility
	 */
	public final static int PUBLIC=1;
	public final static int PRIVATE=2;

	/**
	 * Returns Activity instance Id
	 * 
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}	

	/**
	 * Get Activity Type
	 * 
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets Activity Type
	 * 
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Get Username
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set Username
	 * 
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get Entity ID such as Article ID, Follower Id, etc...
	 * 
	 * @return the entityId
	 */
	public String getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * Get the visibility of activity instance
	 * 
	 * @return the visibility
	 */
	public Integer getVisibility() {
		return visibility;
	}

	/**
	 * Set the visibility of activity that is PUBLIC or PRIVATE
	 * 
	 * @param visibility the visibility to set
	 */
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
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
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
