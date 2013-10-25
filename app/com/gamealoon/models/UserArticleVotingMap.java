package com.gamealoon.models;

import org.bson.types.ObjectId;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * This map datastore is built to keep track of cool/Not cool status generated by user for some particular article.
 * 
 * @author Partho
 *
 */

@Entity
public class UserArticleVotingMap {

	@Id
	private ObjectId id;
	private String username;
	private String articleId;
	private Integer cool;
	private Integer notCool;
	
	/**
	 * Cool Not-cool states
	 */
	public static final Integer SET=1;
	public static final Integer UNSET=0;		

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
	public void setUsername(String userName) {
		this.username = userName;
	}

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
	 * @return the cool
	 */
	public Integer getCool() {
		return cool;
	}

	/**
	 * @param cool the cool to set
	 */
	public void setCool(Integer cool) {
		this.cool = cool;
	}

	/**
	 * @return the notCool
	 */
	public Integer getNotCool() {
		return notCool;
	}

	/**
	 * @param notCool the notCool to set
	 */
	public void setNotCool(Integer notCool) {
		this.notCool = notCool;
	}
}