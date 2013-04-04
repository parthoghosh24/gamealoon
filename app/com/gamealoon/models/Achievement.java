package com.gamealoon.models;

import org.bson.types.ObjectId;


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
	ObjectId id;
	private String title;
	private String description;
	private String imagePath;
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
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	/**
	 * returns achievement title
	 */
	public String toString()
	{
		return this.title;
	}
}
