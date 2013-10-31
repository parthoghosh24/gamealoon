package com.gamealoon.models;




import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;


/**
 * This is the platform entity of Gamealoon framework. A platform is something on which games run.
 * This can be playstation, xbox, wiiu, mobile, browser and so on. So a platform will have its fare share of articles.
 * This will be maintained by Gamealoon team internally.
 * 
 * @author partho
 *
 */

@Entity
public class Platform{

	
	@Id
	private ObjectId id;
	private String title; //playstation, pc, xbox, browser...
	private String shortTitle;
	private String description;	
	private String manufacturer;
    private String developer;
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
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
	/**
	 * Title is returned as String
	 */
	public String toString()
	{
		return this.title;
	}
	/**
	 * @return the shortTitle
	 */
	public String getShortTitle() {
		return shortTitle;
	}
	/**
	 * @param shortTitle the shortTitle to set
	 */
	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}
	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}
	/**
	 * @return the developer
	 */
	public String getDeveloper() {
		return developer;
	}
	/**
	 * @param developer the developer to set
	 */
	public void setDeveloper(String developer) {
		this.developer = developer;
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
	public void setInsertTime(String inserTime) {
		this.insertTime = inserTime;
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
