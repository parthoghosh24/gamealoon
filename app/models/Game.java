package models;

import java.util.ArrayList;

import javax.persistence.Entity;


import play.db.ebean.Model;

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
public class Game extends Model{
	
	private static final long serialVersionUID = -4710363804542236748L;
		
	public long id;
	public String title;
	public String description; //a little description about the game
	public String releaseDate;
	public String price; //keep it in dollars, can modify it in runtime when showing
	public String rating; //mature, adults only, everyone, etc. This should ideally modify according to region. 
	public String publisher;
	public String developer;
	public String Genere;
	public float score; //This will be a calculated value. Need to work on the formula to device the score. Normal distribution might help.
	
	//Game has many plaforms
	public ArrayList<Platform> platforms= new ArrayList<>();
	
	//
	public ArrayList<Article> articles = new ArrayList<>();

}
