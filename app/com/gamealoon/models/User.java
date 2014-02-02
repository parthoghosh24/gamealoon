package com.gamealoon.models;

import java.util.ArrayList;
import org.bson.types.ObjectId;
import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.utils.IndexDirection;

/**
 * Another major entity in Gamealoon framework is User. An user is an entity who registers with the system and then can affect the
 * system in many ways. Following are some of the ways: -> Can be followed by other users and follow other users. -> Will earn
 * reputation. -> Can create, modify and publish articles.
 * 
 * @author partho
 * 
 */

@Entity
public class User {

	@Id
	private ObjectId id;
	private String username;
	private String email; // should be a way to find out whether email is valid or not
	private int birthdayVisibility;
	private String passwordHash;
	private String passwordSalt;
	private String firstName;
	private String lastName;
	private String country;
	private int emailConfirmed;
	private int day;
	private int month;
	private int year;
	private String avatar; // path to avatar image file for user
	private String insertTime;
	private long timestamp;
	private String updateTime;
	private String gameBio;
	private String status;
	private double articlePublishRate; // sum(Ai-Ai+1)/N-1
	private double userFollowScore;
	private double userArticleScore;
	@Indexed(value = IndexDirection.ASC, name = "usr_scr")
	private double totalScore;
	private double userTotalCoolScore;
	private int emailConfirmToken;
	private int passwordResetToken;

	/**
	 * XP is used to calculate user level. Also, users will be ranked on the basis of total XP earned.
	 */
	private long experiencePoints;

	/**
	 * Redeemable points. Users can use GP to redeem xp or gifts.
	 */
	private int gamealoonPoints;

	/**
	 * Levels depict the progress of users.
	 */
	private int level;

	/**
	 * Network Rank is awarded to user when user hits a particular XP checkpoint
	 */
	private int networkRank;
	
	
	//User Page and settings tate
	
	public static final int EMAIL_NOT_CONFIRMED=0;
	public static final int EMAIL_CONFIRMED=1;
	public static final int USER_PAGE=1;
	public static final int USER_PAGE_LOGGED_IN=2;
	public static final int USER_SETTINGS=3;

	// Chat states
	public static final int INVITE = 0;
	public static final int PENDING = 1;
	public static final int BLOCK = 2;

	// For birthday visibility
	public static final int PRIVATE = 1;
	public static final int PUBLIC = 2;

	// User interests
	public static final int PLATFORM_INTEREST = 1;
	public static final int GENRE_INTEREST = 2;

	// Rank Constants

	public static final String[] USER_RANK_MAP = {"New Gloonie", "Gamealoon Yogi", "Gamealoon Big Boss", "Legend of Gamealoonland",
			"Gamealoonova", "God of W...err... Gamealoon", "Gloonie"};

	@Embedded
	private ArrayList<Achievement> achievements = new ArrayList<>(); // Achievements earned

	@Embedded
	// User followed by many users- eyed by
	private ArrayList<Buddy> followedBy = new ArrayList<>();

	@Embedded
	private ArrayList<Buddy> following = new ArrayList<>();

	private ArrayList<InterestedGame> followingGames = new ArrayList<>();

	private ArrayList<Genre> interestedGenres = new ArrayList<>();

	private String[] interestedPlatforms = {};

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
	 * @return the passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
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
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * @return the followedBy
	 */
	public ArrayList<Buddy> getFollowedBy() {
		return followedBy;
	}

	/**
	 * @param followedBy the followedBy to set
	 */
	public void setFollowedBy(ArrayList<Buddy> followedBy) {
		this.followedBy = followedBy;
	}

	/**
	 * Username is returned as String
	 */
	public String toString() {
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
	 * @return the achievements
	 */
	public ArrayList<Achievement> getAchievements() {
		return achievements;
	}

	/**
	 * @param achievements the achievements to set
	 */
	public void setAchievements(ArrayList<Achievement> achievements) {
		this.achievements = achievements;
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

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @return the emailConfirmed
	 */
	public int getEmailConfirmed() {
		return emailConfirmed;
	}

	/**
	 * @param emailConfirmed the emailConfirmed to set
	 */
	public void setEmailConfirmed(int emailConfirmed) {
		this.emailConfirmed = emailConfirmed;
	}

	/**
	 * @return the following
	 */
	public ArrayList<Buddy> getFollowing() {
		return following;
	}

	/**
	 * @param following the following to set
	 */
	public void setFollowing(ArrayList<Buddy> following) {
		this.following = following;
	}

	/**
	 * @return the followingGames
	 */
	public ArrayList<InterestedGame> getFollowingGames() {
		return followingGames;
	}

	/**
	 * @param followingGames the followingGames to set
	 */
	public void setFollowingGames(ArrayList<InterestedGame> followingGames) {
		this.followingGames = followingGames;
	}

	/**
	 * @return the interestedGenres
	 */
	public ArrayList<Genre> getInterestedGenres() {
		return interestedGenres;
	}

	/**
	 * @param interestedGenres the interestedGenres to set
	 */
	public void setInterestedGenres(ArrayList<Genre> interestedGenres) {
		this.interestedGenres = interestedGenres;
	}

	/**
	 * @return the interestedPlatforms
	 */
	public String[] getInterestedPlatforms() {
		return interestedPlatforms;
	}

	/**
	 * @param interestedPlatforms the interestedPlatforms to set
	 */
	public void setInterestedPlatforms(String[] interestedPlatforms) {
		this.interestedPlatforms = interestedPlatforms;
	}

	/**
	 * @return the birthdayVisibility
	 */
	public int getBirthdayVisibility() {
		return birthdayVisibility;
	}

	/**
	 * @param birthdayVisibility the birthdayVisibility to set
	 */
	public void setBirthdayVisibility(int birthdayVisibility) {
		this.birthdayVisibility = birthdayVisibility;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the passwordSalt
	 */
	public String getPasswordSalt() {
		return passwordSalt;
	}

	/**
	 * @param passwordSalt the passwordSalt to set
	 */
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	/**
	 * @return the articlePublishRate
	 */
	public Double getArticlePublishRate() {
		return articlePublishRate;
	}

	/**
	 * @param articlePublishRate the articlePublishRate to set
	 */
	public void setArticlePublishRate(Double articlePublishRate) {
		this.articlePublishRate = articlePublishRate;
	}

	/**
	 * @return the userFollowScore
	 */
	public Double getUserFollowScore() {
		return userFollowScore;
	}

	/**
	 * @param userFollowScore the userFollowScore to set
	 */
	public void setUserFollowScore(Double userFollowScore) {
		this.userFollowScore = userFollowScore;
	}

	/**
	 * @return the userArticleScore
	 */
	public Double getUserArticleScore() {
		return userArticleScore;
	}

	/**
	 * @param userArticleScore the userArticleScore to set
	 */
	public void setUserArticleScore(Double userArticleScore) {
		this.userArticleScore = userArticleScore;
	}

	/**
	 * @return the userTotalCoolScore
	 */
	public Double getUserTotalCoolScore() {
		return userTotalCoolScore;
	}

	/**
	 * @param userTotalCoolScore the userTotalCoolScore to set
	 */
	public void setUserTotalCoolScore(Double userTotalCoolScore) {
		this.userTotalCoolScore = userTotalCoolScore;
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
	 * @return the experiencePoints
	 */
	public long getExperiencePoints() {
		return experiencePoints;
	}

	/**
	 * @param experiencePoints the experiencePoints to set
	 */
	public void setExperiencePoints(long experiencePoints) {
		this.experiencePoints = experiencePoints;
	}

	/**
	 * @return the gamealoonPoints
	 */
	public int getGamealoonPoints() {
		return gamealoonPoints;
	}

	/**
	 * @param gamealoonPoints the gamealoonPoints to set
	 */
	public void setGamealoonPoints(int gamealoonPoints) {
		this.gamealoonPoints = gamealoonPoints;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the networkRank
	 */
	public int getNetworkRank() {
		return networkRank;
	}

	/**
	 * @param networkRank the networkRank to set
	 */
	public void setNetworkRank(int networkRank) {
		this.networkRank = networkRank;
	}

	/**
	 * @return the emailConfirmToken
	 */
	public int getEmailConfirmToken() {
		return emailConfirmToken;
	}

	/**
	 * @param emailConfirmToken the emailConfirmToken to set
	 */
	public void setEmailConfirmToken(int emailConfirmToken) {
		this.emailConfirmToken = emailConfirmToken;
	}

	/**
	 * @return the passwordResetToken
	 */
	public int getPasswordResetToken() {
		return passwordResetToken;
	}

	/**
	 * @param passwordResetToken the passwordResetToken to set
	 */
	public void setPasswordResetToken(int passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}
}
