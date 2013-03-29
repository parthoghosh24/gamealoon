package models;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

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

@Entity
public class User {

	@Id
	ObjectId id;
	private String username;
	private String email; //should be a way to find out whether email is valid or not
	private String password; //need to find out a way to encrypt the password
	private String firstName;
	private String middleName;
	private String lastName;
	private String avatarPath; //path to avatar image file for user
		
		
	@Reference
	//User followed by many users
	private ArrayList<User> followedBy = new ArrayList<>();
	
	@Reference
	//User follows many users
	private ArrayList<User> follows = new ArrayList<>();

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the avatarPath
	 */
	public String getAvatarPath() {
		return avatarPath;
	}

	/**
	 * @param avatarPath the avatarPath to set
	 */
	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	/**
	 * @return the followedBy
	 */
	public ArrayList<User> getFollowedBy() {
		return followedBy;
	}

	/**
	 * @param followedBy the followedBy to set
	 */
	public void setFollowedBy(ArrayList<User> followedBy) {
		this.followedBy = followedBy;
	}

	/**
	 * @return the follows
	 */
	public ArrayList<User> getFollows() {
		return follows;
	}

	/**
	 * @param follows the follows to set
	 */
	public void setFollows(ArrayList<User> follows) {
		this.follows = follows;
	}
	
	/**
	 * Name is returned as String
	 */
	public String toString()
	{
		return this.firstName+" "+this.middleName+" "+this.lastName;
	}
	//TODO need to see if we persist something related with reps or need to calculate in runtime

}
