package com.gamealoon.algorithm;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.gamealoon.database.daos.ConversationDAO;


public class CommentCore{

	private static final int COUNTDOWN = 30000; //30 seconds
	private static final int DELAY = 500; //500 ms
	private static final int PERIOD = 5000; //5 seconds
	
	public static HashMap<String, Object> fetchComment(String articleId, Long timeStamp, ConversationDAO conversationDaoInstance)
	{
		HashMap<String, Object> response = new HashMap<>();
		MyTask checkForCommentTask = new MyTask(COUNTDOWN, conversationDaoInstance, articleId, timeStamp);		
		new Timer().scheduleAtFixedRate(checkForCommentTask, DELAY, PERIOD);
		response = checkForCommentTask.getResponse();
		return response;
	}
	
	static class MyTask extends TimerTask
	{
		private int countDown;		
		private HashMap<String, Object> response;
		private ConversationDAO conversationDaoInstance;
		private String articleId;
		private Long timeStamp;
		
		public MyTask(int countDown, ConversationDAO conversationDaoInstance, String articleId, Long timeStamp)
		{
			this.countDown=countDown;	
			this.conversationDaoInstance =conversationDaoInstance;
			this.articleId = articleId;
			this.timeStamp = timeStamp;
		}

		@Override
		public void run() {		
			countDown--;
			if(countDown == 0 || response.size()>0)
			{
				cancel();
			}
			else
			{
				response=conversationDaoInstance.getCommentByTimestamp(articleId, timeStamp); 
			}
			
		}
		
		public HashMap<String, Object> getResponse()
		{
			return response;
		}
	}
	
}
