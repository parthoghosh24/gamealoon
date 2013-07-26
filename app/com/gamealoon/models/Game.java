package com.gamealoon.models;

import java.util.HashSet;
import java.util.Set;

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
	private double score; //This is review score.
	
	
	@Embedded
	//Game has many plaforms
	private Set<Platform> platforms= new HashSet<>();


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
	public Set<Platform> getPlatforms() {
		return platforms;
	}


	/**
	 * @param platforms2 the platforms to set
	 */
	public void setPlatforms(Set<Platform> platforms2) {
		this.platforms = platforms2;
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
	
}
