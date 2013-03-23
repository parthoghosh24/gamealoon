package models;

import java.util.ArrayList;

import play.db.ebean.Model;

/**
 * Another major entity in Gamealoon framework is User. An user is an entity who registers with the system and then can
 * affect the system in many ways. Following are some of the ways:
 * -> Can be followed by other users and follow other users.
 * -> Will earn reputation.
 * -> Can create, modify and publish articles.
 * 
 * @author partho
 *
 */
public class User extends Model {

	private static final long serialVersionUID = 5854422586239724109L;
	
	public long id;
	public String username;
	public String email; //should be a way to find out whether email is valid or not
	public String password; //need to find out a way to encrypt the password
	public String firstName;
	public String middleName;
	public String lastName;
	public String avatarPath; //path to avatar image file for user
	
	public ArrayList<Article> articles = new ArrayList<>();
	
	//User followed by many users
	public ArrayList<User> followedBy = new ArrayList<>();
	
	//User follows many users
	public ArrayList<User> follows = new ArrayList<>();

}
