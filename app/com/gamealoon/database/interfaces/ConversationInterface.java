package com.gamealoon.database.interfaces;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import com.gamealoon.database.mongo.api.MongoDao;
import com.gamealoon.models.Conversation;

public interface ConversationInterface extends MongoDao<Conversation> {

	/**
	 * Create or save Comment conversation
	 * 
	 * @param conversationId
	 * @param message
	 * @param userName
	 * @param conversationType
	 * @param insertTime
	 * @param timestamp
	 * @param commentId
	 * @param articleId
	 * @param commentScore
	 * @param spamScore
	 * @param isReply
	 * @param commentConversationId
	 */
	public HashMap<String, Object> createOrUpdateConversation(String conversationId, String message, String userName,
			int conversationType, String insertTime, Long timestamp, String commentId, String articleId, double commentScore,
			double spamScore, int isReply, String commentConversationId);

	/**
	 * Create or save Discussion conversation
	 * 
	 * @param conversationId
	 * @param message
	 * @param userName
	 * @param conversationType
	 * @param insertTime
	 * @param timestamp
	 * @param discussionId
	 * @param discussionGroupName
	 * @param discussionCreatorName
	 * @param discussionType
	 */
	public HashMap<String, Object> createOrUpdateConversation(String conversationId, String message, String userName,
			int conversationType, String insertTime, Long timestamp, String discussionId, String discussionGroupName,
			String discussionCreatorName, int discussionType, String entityId, String discussionInsertTime, Long discussionTimeStamp);

	/**
	 * Create or save Chat conversation
	 * 
	 * @param conversationId
	 * @param message
	 * @param userName
	 * @param conversationType
	 * @param insertTime
	 * @param timestamp
	 * @param chatId
	 * @param chatInsertTime
	 * @param chatTimestamp
	 */
	public HashMap<String, Object> createOrUpdateConversation(String conversationId, String message, String userName,
			int conversationType, String insertTime, Long timestamp, String chatId, String chatInsertTime, Long chatTimestamp);

	/**
	 * Get all comments a single article
	 * 
	 * @param articleId
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getComments(String articleId) throws ParseException, MalformedURLException;

	/**
	 * Get latest comment based on conversationId
	 * 
	 * @return
	 */

	public HashMap<String, Object> getComment(String conversationId) throws ParseException, MalformedURLException;

	/**
	 * Get comment by latest timestamp and articleId.
	 * 
	 * @param timeStamp
	 * @return
	 */
	public HashMap<String, Object> getCommentByTimestamp(String articleId, Long timeStamp) throws ParseException,
			MalformedURLException;

}
