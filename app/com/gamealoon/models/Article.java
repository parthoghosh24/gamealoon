package com.gamealoon.models;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import com.gamealoon.database.daos.ArticleDAO;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * This is the Article entity of Gamealoon framework. Whatever content we will be seeing on the website, say review, preview, news,
 * etc... are articles at the end of the day. These articles will be part of category such as review, preview, news and so on. That is
 * why it is independent.
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
	private String body; // snippet Text
	private String publishDate;
	private String insertTime;
	private String updateTime;
	/**
	 * This is the article publish timestamp
	 * 
	 */
	private long timestamp;
	private double coolScore;
	private double notCoolScore;
	private double coolNotCoolwilsonScore;
	private double totalTimeSpent;
	private double averageTimeSpent;
	private double pageHitCount;
	private double commentScore;
	private double totalScore; // final article score
	private String featuredImage;
	private int state; // 1: draft, 2:publish
	private String author;
	private String game;
	private int isPublished;
	private String sourceUrl; // for news
	private String videoUrl; // for video
	private ArrayList<String> sweets = new ArrayList<>(); // In case of Review
	private ArrayList<String> stinks = new ArrayList<>();// In case of Review
	private String playedOnPlatform; // in case of Review
	/**
	 * Article states
	 */
	public final static int DRAFT = 1;
	public final static int PUBLISH = 2;

	/**
	 * Article is published states
	 * 
	 */
	public final static int PUBLISHED = 0;
	public final static int NOT_PUBLISHED = 1;

	/**
	 * Article List extractor modes
	 */
	public final static int PLATFORM = 1;
	public final static int USER = 2;
	public final static int GAME = 3;

	/**
	 * Voting Type
	 * 
	 */
	public static final Integer COOL = 0;
	public static final Integer NOTCOOL = 1;

	private Category category;

	public ObjectId getId() {
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
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the game
	 */
	public String getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(String game) {
		this.game = game;
	}

	/**
	 * Title is returned as String
	 */
	public String toString() {
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
	 * @return the featuredImage
	 */
	public String getFeaturedImage() {
		return featuredImage;
	}

	/**
	 * @param featuredImage the featuredImage to set
	 */
	public void setFeaturedImage(String featuredImage) {
		this.featuredImage = featuredImage;
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

	/**
	 * @return the article publish timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the article publish timestamp to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the totalTimeSpent
	 */
	public double getTotalTimeSpent() {
		return totalTimeSpent;
	}

	/**
	 * @param totalTimeSpent the totalTimeSpent to set
	 */
	public void setTotalTimeSpent(double totalTimeSpent) {
		this.totalTimeSpent = totalTimeSpent;
	}

	/**
	 * @return the averageTimeSpent
	 */
	public double getAverageTimeSpent() {
		return averageTimeSpent;
	}

	/**
	 * @param averageTimeSpent the averageTimeSpent to set
	 */
	public void setAverageTimeSpent(double averageTimeSpent) {
		this.averageTimeSpent = averageTimeSpent;
	}

	/**
	 * Get all publishedArticles
	 * 
	 * @return
	 */
	public static Long allPublishedArticleCount() {
		ArticleDAO articleDAO = ArticleDAO.instantiateDAO();
		return articleDAO.allPublishedArticlesCount(null);

	}

	/**
	 * Get all publishedArticles for Single User
	 * 
	 * @return
	 */
	public static Long allPublishedArticleCount(User user) {

		ArticleDAO articleDAO = ArticleDAO.instantiateDAO();
		return articleDAO.allPublishedArticlesCount(user);

	}

	/**
	 * Fetch Media Is From Body
	 * 
	 * @param body
	 * @return
	 */
	public static ArrayList<String> fetchMediaIdsFromBody(String body) {
		ArrayList<String> mediaIds = new ArrayList<>();
		int startIndex = body.indexOf("<img");
		while (startIndex != -1) {
			int mediaIdIndex = body.indexOf("id=\"", startIndex);
			int endIndex = body.indexOf("\"", mediaIdIndex + 4);
			String mediaId = body.substring(mediaIdIndex + 4, endIndex);
			mediaIds.add(mediaId);
			startIndex = body.indexOf("<img", endIndex);
		}
		return mediaIds;

	}

	/**
	 * @return the isPublished
	 */
	public int getIsPublished() {
		return isPublished;
	}

	/**
	 * @param isPublished the isPublished to set
	 */
	public void setIsPublished(int isPublished) {
		this.isPublished = isPublished;
	}

	/**
	 * @return the sweets
	 */
	public ArrayList<String> getSweets() {
		return sweets;
	}

	/**
	 * @param likes the sweets to set
	 */
	public void setSweets(ArrayList<String> sweets) {
		this.sweets = sweets;
	}

	/**
	 * @return the stinks
	 */
	public ArrayList<String> getStinks() {
		return stinks;
	}

	/**
	 * @param unLikes the stinks to set
	 */
	public void setStinks(ArrayList<String> stinks) {
		this.stinks = stinks;
	}

	/**
	 * @return the sourceUrl
	 */
	public String getSourceUrl() {
		return sourceUrl;
	}

	/**
	 * @param sourceUrl the sourceUrl to set
	 */
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	/**
	 * @return the videoUrl
	 */
	public String getVideoUrl() {
		return videoUrl;
	}

	/**
	 * @param videoUrl the videoUrl to set
	 */
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	/**
	 * @return the playedOnPlatform
	 */
	public String getPlayedOnPlatform() {
		return playedOnPlatform;
	}

	/**
	 * @param playedOnPlatform the playedOnPlatform to set
	 */
	public void setPlayedOnPlatform(String playedOnPlatform) {
		this.playedOnPlatform = playedOnPlatform;
	}

}
