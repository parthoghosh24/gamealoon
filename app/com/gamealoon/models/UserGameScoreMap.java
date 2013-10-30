package com.gamealoon.models;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity
public class UserGameScoreMap {

	@Id
	private ObjectId id;
	private String username;
	private String gameId;
	private Double gameScore;
	private Double networkUserWeight;
	private String insertTime;
	private String updateTime;
	private Long timestamp;
	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}
	
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
	 * @return the networkUserWeight
	 */
	public Double getNetworkUserWeight() {
		return networkUserWeight;
	}

	/**
	 * @param networkUserWeight the networkUserWeight to set
	 */
	public void setNetworkUserWeight(Double networkUserWeight) {
		this.networkUserWeight = networkUserWeight;
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
