package com.gamealoon.models;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class InterestedGame {

	private String gameId;
	private String gameTitle;
	private String gameReleaseDate;
	private Double gameScore;
	private String insertTime;
	private Long timestamp;
	private int releaseStatus;
	
	public static int INTERESTED=1;
	public static int NOT_INTERESTED=2;
	
	/**
	 * @return the gameId
	 */
	public String getGameId() {
		return gameId;
	}
	/**
	 * @param gameId the gameId to set
	 */
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	/**
	 * @return the gameTitle
	 */
	public String getGameTitle() {
		return gameTitle;
	}
	/**
	 * @param gameTitle the gameTitle to set
	 */
	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}
	/**
	 * @return the gameReleaseDate
	 */
	public String getGameReleaseDate() {
		return gameReleaseDate;
	}
	/**
	 * @param gameReleaseDate the gameReleaseDate to set
	 */
	public void setGameReleaseDate(String gameReleaseDate) {
		this.gameReleaseDate = gameReleaseDate;
	}
	/**
	 * @return the gameScore
	 */
	public Double getGameScore() {
		return gameScore;
	}
	/**
	 * @param gameScore the gameScore to set
	 */
	public void setGameScore(Double gameScore) {
		this.gameScore = gameScore;
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
	 * @return the releaseStatus
	 */
	public int getReleaseStatus() {
		return releaseStatus;
	}
	/**
	 * @param releaseStatus the releaseStatus to set
	 */
	public void setReleaseStatus(int releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
}
