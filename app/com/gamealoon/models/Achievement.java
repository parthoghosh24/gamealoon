package com.gamealoon.models;

import org.bson.types.ObjectId;


import com.gamealoon.database.daos.AchievementDAO;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * Achievements earned by user. It is an embedded entity.
 * 
 * @author Partho
 *
 */
@Entity
public class Achievement {

	@Id
	private ObjectId id;
	private String title;
	private String description;
	private String achievementImage;
	private String insertTime;
	private String updateTime;
	private Long timestamp;
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the achievementImage
	 */
	public String getAchievementImage() {
		return achievementImage;
	}
	/**
	 * @param achievementImage the achievementImage to set
	 */
	public void setAchievementImage(String achievementImage) {
		this.achievementImage = achievementImage;
	}
	
	/**
	 * returns achievement title
	 */
	public String toString()
	{
		return this.title;
	}
	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}	
	
	public static Long getAllAchievementCount()
	{		
		AchievementDAO achievementDAO = AchievementDAO.instantiateDAO();
		return achievementDAO.allAchievementCount();
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
