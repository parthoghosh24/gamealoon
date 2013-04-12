package com.gamealoon.models;



import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;


/**
 * This is the Article entity of Gamealoon framework. Whatever content we will be seeing on the website, say review,
 * preview, news, etc... are articles at the end of the day. These articles will be part of category such as review, preview, 
 * news and so on. That is why it is independent.
 * 
 * 
 * @author partho
 *
 */

@Entity
public class Article {
	
	@Id
	ObjectId id;	
	private String title;
	private String body;
	private String creationDate;
	private String insertTime;
	private String updateTime;	
	private double score; //To be calculated in runtime.......... up/down vote or limit based voting..(e.g star)
	private String featuredImagePath;
	
	@Embedded
	private Set<Platform> platforms = new HashSet<>();
	
	@Embedded
	private User author;
	

	private Category category;
	
	@Embedded
	private Game game;

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
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the creationDate
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the author
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}
	
	/**
	 * Title is returned as String
	 */
	public String toString()
	{
		return this.title;
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

	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * @return the featuredImagePath
	 */
	public String getFeaturedImagePath() {
		return featuredImagePath;
	}

	/**
	 * @param featuredImagePath the featuredImagePath to set
	 */
	public void setFeaturedImagePath(String featuredImagePath) {
		this.featuredImagePath = featuredImagePath;
	}

	/**
	 * @return the platforms
	 */
	public Set<Platform> getPlatforms() {
		return platforms;
	}

	/**
	 * @param platforms the platforms to set
	 */
	public void setPlatforms(Set<Platform> platforms) {
		this.platforms = platforms;
	}

}
