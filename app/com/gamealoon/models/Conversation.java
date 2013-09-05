package com.gamealoon.models;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.utils.IndexDirection;

/**
 * This is the conversation class of Gameloon App. All the conversations, i.e, chats, comments and discussions will be handled by this class.
 * 
 * @author Partho
 *
 */

@Entity
public class Conversation {

	@Id
	private ObjectId id;
	private String message; //actual message
	private String ownerName; //person creating conversation object
	private int conversationType;
	
	@Embedded
	private Comment comment;
	
	@Embedded
	private Discussion discussion;
	
	@Embedded
	private Chat chat;
	
	private String insertTime;	
	
	@Indexed(value=IndexDirection.ASC, name="convr_tmstmp")
	private long timeStamp;
	
	public static final int COMMENT=1;
	public static final int DISCUSSION=2;
	public static final int CHAT=3;

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}	

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the ownerId
	 */
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * @return the conversationType
	 */
	public int getConversationType() {
		return conversationType;
	}

	/**
	 * @param conversationType the conversationType to set
	 */
	public void setConversationType(int conversationType) {
		this.conversationType = conversationType;
	}

	/**
	 * @return the comment
	 */
	public Comment getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}

	/**
	 * @return the discussion
	 */
	public Discussion getDiscussion() {
		return discussion;
	}

	/**
	 * @param discussion the discussion to set
	 */
	public void setDiscussion(Discussion discussion) {
		this.discussion = discussion;
	}

	/**
	 * @return the chat
	 */
	public Chat getChat() {
		return chat;
	}

	/**
	 * @param chat the chat to set
	 */
	public void setChat(Chat chat) {
		this.chat = chat;
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
	 * @return the timeStamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	

}
