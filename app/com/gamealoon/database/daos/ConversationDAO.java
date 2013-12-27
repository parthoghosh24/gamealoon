package com.gamealoon.database.daos;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bson.types.ObjectId;

import play.Logger;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.ConversationInterface;
import com.gamealoon.models.Activity;
import com.gamealoon.models.Comment;
import com.gamealoon.models.Conversation;
import com.gamealoon.models.Media;
import com.gamealoon.models.User;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

public class ConversationDAO extends GloonDAO<Conversation> implements ConversationInterface {

	private static final ConversationDAO DATA_ACCESS_LAYER = new ConversationDAO();
	private static final UserDAO userDAOInstance = UserDAO.instantiateDAO();
	private static final MediaDAO mediaDAOInstance = MediaDAO.instantiateDAO();
	private static final ActivityDAO activityDaoInstance = ActivityDAO.instantiateDAO();
	private Datastore gloonDatastore = null;

	private ConversationDAO() {
		super();
		gloonDatastore = initDatastore();
	}

	/**
	 * Singleton way to instantiate Gloon DAO
	 * 
	 * @return
	 */
	public static ConversationDAO instantiateDAO() {
		return DATA_ACCESS_LAYER;
	}

	@Override
	public HashMap<String, Object> createOrUpdateConversation(String conversationId, String message, String userName,
			int conversationType, String insertTime, Long timestamp, String commentId, String articleId, double commentScore,
			double spamScore, int isReply, String commentConversationId) {

		HashMap<String, Object> conversationMap = new HashMap<>();
		conversationMap.put("status", "fail");
		Conversation conversation = null;
		if (!conversationId.isEmpty()) {
			conversation = gloonDatastore.get(Conversation.class, new ObjectId(conversationId));
		} else {
			conversation = new Conversation();
		}
		conversation.setConversationType(conversationType);
		conversation.setInsertTime(insertTime);
		conversation.setTimeStamp(timestamp);
		conversation.setMessage(message);
		conversation.setOwnerName(userName);
		save(conversation);

		try {
			Comment comment = null;
			if (conversation.getComment() != null) {
				comment = gloonDatastore.createQuery(Conversation.class).filter("comment.commentId", commentId).get().getComment();
			} else {
				comment = new Comment();
				commentId = "com" + conversation.getId().toString();
				comment.setCommentId(commentId);
				comment.setArticleId(articleId);
			}
			comment.setCommentScore(commentScore);
			comment.setSpamScore(spamScore);
			comment.setIsReply(isReply);
			if (isReply == Comment.REPLY) {
				comment.setConversationId(commentConversationId);
			}
			conversation.setComment(comment);
			save(conversation);

			conversationMap = getComment(conversation.getId().toString());
			conversationMap.put("status", "success");
			HashMap<String, String> activityMap = new HashMap<>();
			activityMap.put("id", "");
			activityMap.put("username", userName);
			activityMap.put("entityId", conversation.getId().toString());
			activityMap.put("type", "" + Activity.ACTIVITY_POST_COMMENT);
			activityMap.put("visibility", "" + Activity.PUBLIC);
			activityDaoInstance.createOrUpdateActivity(activityMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conversationMap;

	}

	@Override
	public HashMap<String, Object> createOrUpdateConversation(String conversationId, String message, String userName,
			int conversationType, String insertTime, Long timestamp, String discussionId, String discussionGroupName,
			String discussionCreatorName, int discussionType, String entityId, String discussionInsertTime, Long discussionTimeStamp) {
		return null;

	}

	@Override
	public HashMap<String, Object> createOrUpdateConversation(String conversationId, String message, String userName,
			int conversationType, String insertTime, Long timestamp, String chatId, String chatInsertTime, Long chatTimestamp) {
		return null;

	}

	@Override
	public ArrayList<HashMap<String, Object>> getComments(String articleId) throws ParseException, MalformedURLException {
		ArrayList<HashMap<String, Object>> commentMaps = new ArrayList<>();
		List<Conversation> conversations = getCommentConversations(Conversation.COMMENT, articleId);
		Logger.debug("conversations size " + conversations.size());
		if (conversations.size() > 0) {
			for (Conversation conversation : conversations) {
				if (conversation != null) {
					HashMap<String, Object> commentMap = new HashMap<>();
					commentMap.put("conversationId", conversation.getId().toString());
					commentMap.put("conversationType", Conversation.COMMENT);
					commentMap.put("ownerName", conversation.getOwnerName());
					commentMap.put("message", conversation.getMessage());
					commentMap.put("conversationInsertTime", conversation.getInsertTime());
					commentMap.put("converstationTimeFormatted", Utility.convertFromOneFormatToAnother(conversation.getInsertTime()));
					commentMap.put("conversationTimeStamp", conversation.getTimeStamp());
					commentMap.put("conversationComment", conversation.getComment());
					User user = userDAOInstance.findByUsername(conversation.getOwnerName());
					String ownerAvatarImage = user.getAvatar();
					if (ownerAvatarImage.isEmpty()) {
						commentMap.put("ownerAvatarImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
					} else {
						Media media = mediaDAOInstance.getById(ownerAvatarImage);
						commentMap.put("ownerAvatarImage", media.getUrl());
					}
					commentMaps.add(commentMap);
				}
			}
		}

		return commentMaps;
	}

	@Override
	public HashMap<String, Object> getCommentByTimestamp(String articleId, Long timeStamp) throws ParseException,
			MalformedURLException {
		HashMap<String, Object> commentMap = new HashMap<>();
		Conversation commentConversation = gloonDatastore.createQuery(Conversation.class).filter("comment.articleId", articleId)
				.filter("timeStamp >", timeStamp).order("-timeStamp").get();

		if (commentConversation != null) {
			commentMap.put("conversationId", commentConversation.getId().toString());
			commentMap.put("conversationType", Conversation.COMMENT);
			commentMap.put("ownerName", commentConversation.getOwnerName());
			commentMap.put("message", commentConversation.getMessage());
			commentMap.put("conversationInsertTime", commentConversation.getInsertTime());
			commentMap.put("converstationTimeFormatted", Utility.convertFromOneFormatToAnother(commentConversation.getInsertTime()));
			commentMap.put("conversationTimeStamp", commentConversation.getTimeStamp());
			commentMap.put("conversationComment", commentConversation.getComment());
			User user = userDAOInstance.findByUsername(commentConversation.getOwnerName());
			String ownerAvatarImage = user.getAvatar();
			if (ownerAvatarImage.isEmpty()) {
				commentMap.put("ownerAvatarImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
			} else {
				Media media = mediaDAOInstance.getById(ownerAvatarImage);
				commentMap.put("ownerAvatarImage", media.getUrl());
			}
		}
		return commentMap;
	}

	@Override
	public HashMap<String, Object> getComment(String conversationId) throws ParseException, MalformedURLException {
		HashMap<String, Object> commentMap = new HashMap<>();
		Conversation commentConversation = gloonDatastore.get(Conversation.class, new ObjectId(conversationId));

		if (commentConversation != null) {
			commentMap.put("conversationId", commentConversation.getId().toString());
			commentMap.put("conversationType", Conversation.COMMENT);
			commentMap.put("ownerName", commentConversation.getOwnerName());
			commentMap.put("message", commentConversation.getMessage());
			commentMap.put("conversationInsertTime", commentConversation.getInsertTime());
			commentMap.put("converstationTimeFormatted", Utility.convertFromOneFormatToAnother(commentConversation.getInsertTime()));
			commentMap.put("conversationTimeStamp", commentConversation.getTimeStamp());
			commentMap.put("conversationComment", commentConversation.getComment());
			User user = userDAOInstance.findByUsername(commentConversation.getOwnerName());
			String ownerAvatarImage = user.getAvatar();
			if (ownerAvatarImage.isEmpty()) {
				commentMap.put("ownerAvatarImage", AppConstants.APP_IMAGE_DEFAULT_URL_PATH + "/avatar.png");
			} else {
				Media media = mediaDAOInstance.getById(ownerAvatarImage);
				commentMap.put("ownerAvatarImage", media.getUrl());
			}

		}
		return commentMap;
	}

	/**
	 * Find all comment conversations for particular article
	 * 
	 * @param comment
	 * @param articleId
	 * @return
	 */
	private List<Conversation> getCommentConversations(int commentType, String articleId) {

		return gloonDatastore.createQuery(Conversation.class).filter("conversationType", commentType)
				.filter("comment.articleId", articleId).order("-timeStamp").asList();
	}

}
