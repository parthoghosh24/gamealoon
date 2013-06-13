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
	private ObjectId id;	
	private String title;	
	private String subtitle;
	private String body;
	private String publishDate;
	private String insertTime;
	private String updateTime;	
	private double coolScore;
	private double notCoolScore;
	private double coolNotCoolwilsonScore; //To be calculated in runtime.......... up/down vote or limit based voting..(e.g star)
	private double pageHitCount; //Count of page hit
	private double pageHitWilsonScore;	
	private double commentScore;
	private double totalScore; //sum of wilson+pagehit+comment
	private String featuredImagePath;
	private int state; //1: draft, 2:publish
	
	@Embedded
	private Set<Platform> platforms = new HashSet<>();
	
	@Embedded
	private User author;
	

	private Category category;
	
	@Embedded
	private Game game;
	
	
	public ObjectId getId()
	{
		return this.id;
	}

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
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * @param subtitle the subtitle to set
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
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
	public String getPublishDate() {
		return publishDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
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
	 * @return the wilson score for cool/not cool
	 */
	public double getCoolNotCoolwilsonScore() {
		return coolNotCoolwilsonScore;
	}

	/**
	 * @param coolNotCoolwilsonScore the score for cool/not cool to set
	 */
	public void setCoolNotCoolwilsonScore(double coolNotCoolwilsonScore) {
		this.coolNotCoolwilsonScore = coolNotCoolwilsonScore;
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

	/**
	 * @return the coolScore
	 */
	public double getCoolScore() {
		return coolScore;
	}

	/**
	 * @param coolScore the coolScore to set
	 */
	public void setCoolScore(double coolScore) {
		this.coolScore = coolScore;
	}

	/**
	 * @return the notCoolScore
	 */
	public double getNotCoolScore() {
		return notCoolScore;
	}

	/**
	 * @param notCoolScore the notCoolScore to set
	 */
	public void setNotCoolScore(double notCoolScore) {
		this.notCoolScore = notCoolScore;
	}

	/**
	 * @return the pageHitScore
	 */
	public double getPageHitWilsonScore() {
		return pageHitWilsonScore;
	}

	/**
	 * @param pageHitWilsonScore the pageHitScore to set
	 */
	public void setPageHitWilsonScore(double pageHitWilsonScore) {
		this.pageHitWilsonScore = pageHitWilsonScore;
	}

	/**
	 * @return the commentScore
	 */
	public double getCommentScore() {
		return commentScore;
	}

	/**
	 * @param commentScore the commentScore to set
	 */
	public void setCommentScore(double commentScore) {
		this.commentScore = commentScore;
	}

	/**
	 * @return the totalScore
	 */
	public double getTotalScore() {
		return totalScore;
	}

	/**
	 * @param totalScore the totalScore to set
	 */
	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	/**
	 * @return the pageviews
	 */
	public double getPageHitCount() {
		return pageHitCount;
	}

	/**
	 * @param pageviews the pageviews to set
	 */
	public void setPageHitCount(double pageHitCount) {
		this.pageHitCount = pageHitCount;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	



}
