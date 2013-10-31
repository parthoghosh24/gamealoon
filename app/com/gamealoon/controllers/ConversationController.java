package com.gamealoon.controllers;

import static play.data.Form.form;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import static play.libs.Json.toJson;
import com.gamealoon.database.daos.ConversationDAO;
import com.gamealoon.models.Comment;
import com.gamealoon.models.Conversation;
import com.gamealoon.utility.Utility;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.Result;
import play.mvc.Controller;

public class ConversationController extends Controller{
	

	private static final ConversationDAO conversationDaoInstance = ConversationDAO.instantiateDAO();
	
	public static Result addComment()
	{
		DynamicForm requestData = form().bindFromRequest();
		String conversationId ="";
		String commentId ="";
		String commentConversationId="";
		Date time=new Date();
		Long timeStamp = time.getTime();						
		String insertTime =Utility.convertDateToString(time);						
		int conversationType = Conversation.COMMENT;
		int isReply = Comment.NO_REPLY;
		String message = requestData.get("message");
		String userName = requestData.get("userName");
		String articleId = requestData.get("articleId");
		Double commentScore = Double.parseDouble(requestData.get("commentScore"));
		Double spamScore = Double.parseDouble(requestData.get("spamScore"));
		
		System.out.println("conversationId: "+conversationId);
		System.out.println("commentId: "+commentId);
		System.out.println("commentConversationId: "+commentConversationId);
		System.out.println("timeStamp: "+timeStamp);
		System.out.println("insertTime: "+insertTime);
		System.out.println("conversationType: "+conversationType);
		System.out.println("isReply: "+isReply);
		System.out.println("message: "+message);
		System.out.println("userName: "+userName);
		System.out.println("articleId: "+articleId);
		System.out.println("commentScore: "+commentScore);
		System.out.println("spamScore: "+spamScore);
		
		HashMap<String, Object> response = saveCommentConversation(conversationId, message, userName, conversationType, insertTime, timeStamp, commentId, articleId, commentScore, spamScore, isReply, commentConversationId);
		return ok(toJson(response));
	}
	
	public static Result getComment(String articleId, String timeStamp)
	{
		HashMap<String, Object> commentMap = new HashMap<>();
		try {
			commentMap = getCommentMap(articleId, timeStamp);
		} catch (NumberFormatException | ParseException e) {
			Logger.error("Error in Conversation Controller getComment ", e.fillInStackTrace()); 			
		}
		return ok(toJson(commentMap));
	}
	private static HashMap<String, Object> getCommentMap(String articleId, String timeStamp) throws NumberFormatException, ParseException {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response= conversationDaoInstance.getCommentByTimestamp(articleId, Long.parseLong(timeStamp));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Creating or updating comment conversation
	 * 
	 * @param conversationId
	 * @param message
	 * @param userName
	 * @param conversationType
	 * @param insertTime
	 * @param timeStamp
	 * @param commentId
	 * @param articleId
	 * @param commentScore
	 * @param spamScore
	 * @param isReply
	 * @param commentConversationId
	 * @return
	 */
	private static HashMap<String, Object> saveCommentConversation(
			String conversationId, String message, String userName,
			int conversationType, String insertTime, Long timeStamp,
			String commentId, String articleId, Double commentScore,
			Double spamScore, int isReply, String commentConversationId) {
		// TODO Auto-generated method stub
		return conversationDaoInstance.createOrUpdateConversation(conversationId, message, userName, conversationType, insertTime, timeStamp, commentId, articleId, commentScore, spamScore, isReply, commentConversationId);
	}



}
