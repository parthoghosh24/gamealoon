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
	private Genre genre;
	private String insertTime;
	private String updateTime;
	private Long timestamp;
	private String gameBoxShot;
	private int gameReleaseStatus;
	private double totalScore;
	
		
	private String[] platforms= {};
	
	@Embedded
	private ArrayList<InterestedUser> interestedIn = new ArrayList<>(); //users interested in this game
	
	
	
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
	 * Game Network Scale values
	 */
	public static final String DEAD_FISH="Dead Fish X_X";
	public static final String TORUTURE="Torture :'((";
	public static final String EW_DE_BARF="Ew De Barf :&";
	public static final String CRAP="Crap :S";
	public static final String MEH="Meh :'(";
	public static final String BAD="Bad :(";
	public static final String DOH="Doh :|";
	public static final String HMM="Hmm :-/";
	public static final String NICE="Nice :)";
	public static final String SUPERB="Superb :D";
	public static final String GLOONTASTIC="Gloontastic :O";
	public static final String GLOONATIC="Gloonatic :')";

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
	 * @return the genre
	 */
	public Genre getGenere() {
		return genre;
	}


	/**
	 * @param genre the genre to set
	 */
	public void setGenere(Genre genere) {
		this.genre = genere;
	}


	/**
	 * @return the platforms
	 */
	public String[] getPlatforms() {
		return platforms;
	}


	/**
	 * @param platforms2 the platforms to set
	 */
	public void setPlatforms(String[] platforms) {
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
	 * @return the gameBoxShot
	 */
	public String getGameBoxShot() {
		return gameBoxShot;
	}


	/**
	 * @param gameBoxShot the gameBoxShot to set
	 */
	public void setGameBoxShot(String gameBoxShot) {
		this.gameBoxShot = gameBoxShot;
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

	public static String getGameNetworkRating(Double score)
	{
		if(score==0.0)
		{
			return DEAD_FISH;
		}
		else if(score>0 && score<=1)
		{
			return TORUTURE;
		}
		else if(score>1 && score<=2)
		{
			return EW_DE_BARF;			
		}
		else if(score>2 && score<=3)
		{
			return CRAP;
		}
		else if(score>3 && score<=4)
		{
			return MEH;
		}
		else if(score>4 && score<=5)
		{
			return BAD;
		}
		else if(score>5 && score<=6)
		{
			return DOH;
		}
		else if(score>6 && score<=7)
		{
			return HMM;
		}
		else if(score>7 && score<=8)
		{
			return NICE;
		}
		else if(score>8 && score<=9)
		{
			return SUPERB;
		}
		else if(score>9 && score<=10)
		{
			return GLOONTASTIC;
		}
		else
		{
			return GLOONATIC;					
		}		
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
