package com.gamealoon.models;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class Chat {

	private String chatId;
	private Long timestamp;
	private String insertTime;

	/**
	 * @return the chatId
	 */
	public String getChatId() {
		return chatId;
	}

	/**
	 * @param chatId the chatId to set
	 */
	public void setChatId(String chatId) {
		this.chatId = chatId;
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
	
}
