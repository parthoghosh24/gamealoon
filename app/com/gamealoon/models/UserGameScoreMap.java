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
}
