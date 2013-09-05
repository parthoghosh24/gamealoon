package com.gamealoon.models;

import java.util.ArrayList;
import org.bson.types.ObjectId;
import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.utils.IndexDirection;


/**
 * This is the game entity in Gamealoon framework. A game entity comprises of all information related to
 * a single game. Will be maintained and modified internally, outside user cannot modify this. 
 * 
 * 
 * 
 * @author partho
 *
 */

@Entity
public class Game{
		
	@Id
	private ObjectId id;
	
	private String title;
	private String description; //a little description about the game
	
	 @Indexed(value=IndexDirection.ASC, name="game_rls_dt")
	private String releaseDate;
	private String price; //keep it in dollars, can modify it in runtime when showing
	private String rating; //mature, adults only, everyone, etc. This should ideally modify according to region. 
	private String publisher;
	private String developer;
	private String genere;
	private String gameBoxShotPath;
	private int gameReleaseStatus;
	
	@Indexed(value=IndexDirection.ASC, name="game_scr")
	private double score; //This is review score.
	
	
	@Embedded
	//Game has many plaforms
	private ArrayList<Platform> platforms= new ArrayList<>();
	
	@Embedded
	private ArrayList<InterestedUser> interestedIn = new ArrayList<>(); //followed by users or users interested in this game
	
	
	
	public static final int RELEASED=0;
	public static final int NOT_RELEASED=1;
	
	public static final String RATING_PENDING="ratingPending";
	public static final String EVERYONE="everyone";
	public static final String EVERYONE_TEN_PLUS="everyoneTenPlus";
	public static final String TEEN="teen";
	public static final String MATURE="mature";
	public static final String ADULTS_ONLY="adultsOnly";
	public static final String EARLY_CHILDHOOD="earlyChildhood";

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
	 * @return the releaseDate
	 */
	public String getReleaseDate() {
		return releaseDate;
	}


	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}


	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}


	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}


	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}


	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}


	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
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
	 * @return the genere
	 */
	public String getGenere() {
		return genere;
	}


	/**
	 * @param genere the genere to set
	 */
	public void setGenere(String genere) {
		this.genere = genere;
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
	 * @return the platforms
	 */
	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}


	/**
	 * @param platforms2 the platforms to set
	 */
	public void setPlatforms(ArrayList<Platform> platforms) {
		this.platforms = platforms;
	}
	

	/**
	 * Title is returned as String
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


	/**
	 * @return the interestedIn
	 */
	public ArrayList<InterestedUser> getInterestedIn() {
		return interestedIn;
	}


	/**
	 * @param interestedIn the interestedIn to set
	 */
	public void setInterestedIn(ArrayList<InterestedUser> interestedIn) {
		this.interestedIn = interestedIn;
	}


	/**
	 * @return the gameBoxShotPath
	 */
	public String getGameBoxShotPath() {
		return gameBoxShotPath;
	}


	/**
	 * @param gameBoxShotPath the gameBoxShotPath to set
	 */
	public void setGameBoxShotPath(String gameBoxShotPath) {
		this.gameBoxShotPath = gameBoxShotPath;
	}


	/**
	 * @return the gameReleaseStatus
	 */
	public int getGameReleaseStatus() {
		return gameReleaseStatus;
	}


	/**
	 * @param gameReleaseStatus the gameReleaseStatus to set
	 */
	public void setGameReleaseStatus(int gameReleaseStatus) {
		this.gameReleaseStatus = gameReleaseStatus;
	}

	
	
}
