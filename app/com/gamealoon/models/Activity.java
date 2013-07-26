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
	private String  userId;
	private String entityId;
	private Integer visibility;
	private String insertTime;
	
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
	 * Get User ID
	 * 
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Set User ID
	 * 
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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

}
