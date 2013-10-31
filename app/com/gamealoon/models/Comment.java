package com.gamealoon.models;

import com.google.code.morphia.annotations.Embedded;

/**
 * Comment Class. It handles all information related to comments made in system. Now every comment is made against a single article.
 * 
 * @author Partho
 *
 */

@Embedded
public class Comment {

	private String commentId;
	private String articleId;	
	private double commentScore; //calculated by counting up and down votes
	private double spamScore;// calculated by counting spams.
	private int isReply;
	private String conversationId;
	private String insertTime;
	private Long timestamp;
	
	public static final int NO_REPLY=0;
	public static final int REPLY=1;

	/**
	 * @return the articleId
	 */
	public String getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return the commentScore
	 */
	public double getCommentScore() {
		return commentScore;
	}

	/**
	 * @param commentScore the commentScore to set
	 */
	public void setCommentScore(double commentScore) {
		this.commentScore = commentScore;
	}

	/**
	 * @return the spamScore
	 */
	public double getSpamScore() {
		return spamScore;
	}

	/**
	 * @param spamScore the spamScore to set
	 */
	public void setSpamScore(double spamScore) {
		this.spamScore = spamScore;
	}

	/**
	 * @return the commentId
	 */
	public String getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	/**
	 * @return the isReply
	 */
	public int getIsReply() {
		return isReply;
	}

	/**
	 * @param isReply the isReply to set
	 */
	public void setIsReply(int isReply) {
		this.isReply = isReply;
	}

	/**
	 * @return the conversationId
	 */
	public String getConversationId() {
		return conversationId;
	}

	/**
	 * @param conversationId the conversationId to set
	 */
	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
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
}
