package com.gamealoon.models;


import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.utils.IndexDirection;

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
	private int day;
	private int month;
	private int year;
	private String avatarPath; //path to avatar image file for user	
	private String insertTime;
	private String updateTime;
	private String gameBio; //This is a game related bio for user, which will be shown on article page
    private double articleBasedScore; // This is something like How often articles uploaded+other user score for articles uploaded
    private double videoUploadBasedScore; //This is something like how often videos uploaded+other user score for videos uploaded
    private double achievementsBasedScore; //This is a score based on achievements earned. Also knows as Gloon points!!!
    private double userFollowScore; // will be calculated by considering the reps of users following
    @Indexed(value=IndexDirection.ASC, name="usr_scr")
    private double totalScore;
    
    @Embedded
    private Set<Achievement> achievements = new HashSet<>(); //Achievements earned    
	
    @JsonIgnore
	@Reference
	//User followed by many users- eyed by
	private Set<User> followedBy = new HashSet<>();
	

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
	public Set<User> getFollowedBy() {
		return followedBy;
	}

	/**
	 * @param followedBy the followedBy to set
	 */
	public void setFollowedBy(Set<User> followedBy) {
		this.followedBy = followedBy;
	}
	
	/**
	 * Username is returned as String
	 */
	public String toString()
	{
		return this.username;
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
	 * @return the gameBio
	 */
	public String getGameBio() {
		return gameBio;
	}

	/**
	 * @param gameBio the gameBio to set
	 */
	public void setGameBio(String gameBio) {
		this.gameBio = gameBio;
	}

	/**
	 * @return the articleBasedScore
	 */
	public double getArticleBasedScore() {
		return articleBasedScore;
	}

	/**
	 * @param articleBasedScore the articleBasedScore to set
	 */
	public void setArticleBasedScore(double articleBasedScore) {
		this.articleBasedScore = articleBasedScore;
	}

	/**
	 * @return the videoUploadBasedScore
	 */
	public double getVideoUploadBasedScore() {
		return videoUploadBasedScore;
	}

	/**
	 * @param videoUploadBasedScore the videoUploadBasedScore to set
	 */
	public void setVideoUploadBasedScore(double videoUploadBasedScore) {
		this.videoUploadBasedScore = videoUploadBasedScore;
	}

	/**
	 * @return the achievementsBasedScore
	 */
	public double getAchievementsBasedScore() {
		return achievementsBasedScore;
	}

	/**
	 * @param achievementsBasedScore the achievementsBasedScore to set
	 */
	public void setAchievementsBasedScore(double achievementsBasedScore) {
		this.achievementsBasedScore = achievementsBasedScore;
	}

	/**
	 * @return the achievements
	 */
	public Set<Achievement> getAchievements() {
		return achievements;
	}

	/**
	 * @param achievements the achievements to set
	 */
	public void setAchievements(Set<Achievement> achievements) {
		this.achievements = achievements;
	}

	/**
	 * @return the userFollowScore
	 */
	public double getUserFollowScore() {
		return userFollowScore;
	}

	/**
	 * @param userFollowScore the userFollowScore to set
	 */
	public void setUserFollowScore(double userFollowScore) {
		this.userFollowScore = userFollowScore;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
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

}
