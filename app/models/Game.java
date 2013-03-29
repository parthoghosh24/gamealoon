package models;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;


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
	ObjectId id;
	
	private String title;
	private String description; //a little description about the game
	private String releaseDate;
	private String price; //keep it in dollars, can modify it in runtime when showing
	private String rating; //mature, adults only, everyone, etc. This should ideally modify according to region. 
	private String publisher;
	private String developer;
	private String Genere;
	private float score; //This will be a calculated value. Need to work on the formula to device the score. Normal distribution might help.
	
	
	@Embedded
	//Game has many plaforms
	private ArrayList<Platform> platforms= new ArrayList<>();


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
		return Genere;
	}


	/**
	 * @param genere the genere to set
	 */
	public void setGenere(String genere) {
		Genere = genere;
	}


	/**
	 * @return the score
	 */
	public float getScore() {
		return score;
	}


	/**
	 * @param score the score to set
	 */
	public void setScore(float score) {
		this.score = score;
	}


	/**
	 * @return the platforms
	 */
	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}


	/**
	 * @param platforms the platforms to set
	 */
	public void setPlatforms(ArrayList<Platform> platforms) {
		this.platforms = platforms;
	}
	

}
