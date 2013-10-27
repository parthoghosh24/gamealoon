package com.gamealoon.algorithm;


import java.util.List;
import play.Logger;
import com.gamealoon.models.Article;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;
import com.typesafe.config.ConfigFactory;

/**
 * RankAlgorithm class. This class is meant for group all ranking related algorithm methods.
 * 
 * @author Partho
 *
 */
public class RankAlgorithm {

	
	 private static final double ZSCORE=1.96; //1.96 for confidence level 95%
	 private static final String DB_NAME=ConfigFactory.load().getString("gloon.database.name");
	 //Article Score weights
	 private static final double ARTICLE_VOTING_WILSON_WEIGHT=0.75;
	 private static final double ARTICLE_AVERAGE_TIME_SPENT_WEIGHT=0.25;
	 
	 //User score weights
	 private static final double USER_ARTICLE_PUBLISH_RATE_RATIO_WEIGHT=0.2;
	 private static final double USER_FOLLOW_SCORE_WEIGHT=0.2;
	 private static final double USER_ARTICLE_SCORE_RATIO=0.6;
	
	 
	 private static DB initDb(Mongo instance)
	 {		  
	     DB db=instance.getDB(DB_NAME);		 
		 return db;
	 }
	 /**
	  * This method calculates wilson score interval for up and down vote system.
	  * Actual rights goes to http://www.linkedin.com/search?search=&industry=123&sortCriteria=R&keepFacets=true 
	  * 
	  * @param totalPositives
	  * @param totalNegatives
	  * @return
	  */
	 public static double wilsonScoreCalculator(double totalPositives, double totalNegatives)
	 {
		 double total=totalPositives+totalNegatives;
		 double score=0.0;
		 if(total==0)
		 {
			 return score; 
		 }
		 //percentage of totalpositves out of total
		 double phat =totalPositives/total;
		 
		 score=(phat + ZSCORE*ZSCORE/(2*total) - ZSCORE * Math.sqrt((phat*(1-phat)+ZSCORE*ZSCORE/(4*total))/total))/(1+ZSCORE*ZSCORE/total); 
		 
		 return score;
	 }
	 
	 /**
	  * Calculate Single Article Score
	  * 
	  * @param votingWilsonScore - What is the wilscon score for cool, not cool values for this article
	  * @param averageTimeSpentRatio- how much average time spent reading this article
	  * @return
	  */
	 public static double calculateArticleScore(double votingWilsonScore, double averageTimeSpentRatio)
	 {
		 return (votingWilsonScore*ARTICLE_VOTING_WILSON_WEIGHT)+(averageTimeSpentRatio*ARTICLE_AVERAGE_TIME_SPENT_WEIGHT);
	 }
	 
	 /**
	  * Calculate User's all Article score
	  * 
	  * @param instance
	  * @param username
	  * @return
	  */
	 public static double calculateUserArticleScore(Mongo instance, String username)
	 {
		 double userArticleScore=0;		 
		 DB db = initDb(instance);
		 DBCollection articleCollection = db.getCollection("Article");
		 String mapFunction="function(){emit(\"total\",this.totalScore);};";
         String reduceFunction="function(key, values){return Array.sum(values);};"; 
		 DBObject query = new BasicDBObject("author",username);
	     MapReduceOutput output = articleCollection.mapReduce(mapFunction, reduceFunction, null, MapReduceCommand.OutputType.INLINE,query);
	     for(DBObject object: output.results())
         {           
	    	 userArticleScore=(double)object.get("value");
         }
		 return userArticleScore;
	 }
	 
	 /**
	  * Calculate User Article Score ratio
	  * 
	  * @param userArticleScore
	  * @param instance
	  * @return
	  */
	 public static double calculateUserArticleScoreRatio(double userArticleScore, Mongo instance)
	 {
		 double totalUserArticleScore = 0.0;
         DB db = initDb(instance);
         DBCollection articleCollection = db.getCollection("User");       
         String mapFunction="function(){emit(\"total\",this.userArticleScore);};";
         String reduceFunction="function(key, values){return Array.sum(values);};";      
         MapReduceOutput output = articleCollection.mapReduce(mapFunction, reduceFunction, null, MapReduceCommand.OutputType.INLINE,null);	            
         for(DBObject object: output.results())
         {           
        	 totalUserArticleScore=(double)object.get("value");
         }
         if(totalUserArticleScore == 0.0)
         {
        	 totalUserArticleScore=1;
         }
         return userArticleScore/totalUserArticleScore;
	 }
	 
	 /**
	  * Calculate Average time spent ratio for an article. Formula is atsForOneArticle/sum(atsForAllArticle)
	  * 
	  * @param averageTimeSpentForArticle
	  * @return
	  */
	 public static double calculateArticleAverageTimeSpentRatio(double averageTimeSpentForArticle, Mongo instance)
	 {
			            	           
	            double totalAverageTimeSpentForAllArticles = 0.0;
	            DB db = initDb(instance);
	            DBCollection articleCollection = db.getCollection("Article");       
	            String mapFunction="function(){emit(\"total\",this.averageTimeSpent);};";
	            String reduceFunction="function(key, values){return Array.sum(values);};";      
	            MapReduceOutput output = articleCollection.mapReduce(mapFunction, reduceFunction, null, MapReduceCommand.OutputType.INLINE,null);	            
	            for(DBObject object: output.results())
	            {           
	                totalAverageTimeSpentForAllArticles=(double)object.get("value");
	            }
	            if(totalAverageTimeSpentForAllArticles == 0.0)
	            {
	            	totalAverageTimeSpentForAllArticles=averageTimeSpentForArticle;
	            }
	            return averageTimeSpentForArticle/totalAverageTimeSpentForAllArticles;
	        
	 }
	 
	 /**
	  * Calculate Single User score
	  * 
	  * @param articlePublishRateRatio- The Rate at which user is publishing article
	  * @param userFollowScore- How many users are following this user
	  * @param articleScoreRatio- User's article Score ratio.
	  * @return
	  */
	 public static double calculateUserScore(double articlePublishRateRatio, double userFollowScore, double articleScoreRatio)
	 {
		 return ((1-articlePublishRateRatio)*USER_ARTICLE_PUBLISH_RATE_RATIO_WEIGHT )+(userFollowScore*USER_FOLLOW_SCORE_WEIGHT)+(articleScoreRatio*USER_ARTICLE_SCORE_RATIO);
	 }
	 
	 /**
	  * Calculate user's article publish rate. That is on average how much user is publishing articles
	  * 
	  * @param articles
	  * @return
	  */
	 public static double calculateUserArticlePublishRate(List<Article> articles)
	 {
		 long diff=0L;
		 double rate=0;
		 if(articles.size()>1)
		 {
			 for(int index = articles.size()-1;index>0;index--)
			 {				 
				 diff+=(articles.get(index).getTimestamp()-articles.get(index-1).getTimestamp())/1000;
			 } 
			 rate=(double)diff/(articles.size()-1);
		 }
		 else
		 {
			 rate=1000.0; //Base score when only 1 article published
		 }
		 Logger.debug("RATE "+rate);
		 Logger.debug("DIFF "+diff);
		 return rate;
	 }
	 
	 /**
	  * Calculate Article publish rate ratio for an user,i.e, articlePublishRateForOneUser/sum(articlePublishRateForAllUsers)
	  * 
	  * @param userArticlePublishRate
	  * @return
	  */
	 public static double calculateUserArticlePublishRateRatio(double userArticlePublishRate, Mongo instance)
	 {
			            	           
	            double totalArticlePublishRate = 0.0;
	            DB db = initDb(instance);
	            DBCollection articleCollection = db.getCollection("User");       
	            String mapFunction="function(){emit(\"total\",this.articlePublishRate);};";
	            String reduceFunction="function(key, values){return Array.sum(values);};";      
	            MapReduceOutput output = articleCollection.mapReduce(mapFunction, reduceFunction, null, MapReduceCommand.OutputType.INLINE,null);	            
	            for(DBObject object: output.results())
	            {           
	            	totalArticlePublishRate=(double)object.get("value");
	            }
	            if(totalArticlePublishRate == 0.0)
	            {
	            	totalArticlePublishRate=userArticlePublishRate;
	            }
	            return userArticlePublishRate/totalArticlePublishRate;
	        
	 }
	 
	 /**
	  * Calculate user score ratio that is userScore/sum(allUserScore)
	  * 
	  * @param userScore
	  * @param instance
	  * @return
	  */
	 public static double calculateUserScoreRatio(double userScore, Mongo instance)
	 {
		 double totalUserScore = 0.0;
         DB db = initDb(instance);
         DBCollection articleCollection = db.getCollection("User");       
         String mapFunction="function(){emit(\"total\",this.totalScore);};";
         String reduceFunction="function(key, values){return Array.sum(values);};";      
         MapReduceOutput output = articleCollection.mapReduce(mapFunction, reduceFunction, null, MapReduceCommand.OutputType.INLINE,null);	            
         for(DBObject object: output.results())
         {           
        	 totalUserScore=(double)object.get("value");
         }
         if(totalUserScore == 0.0)
         {
        	 totalUserScore=userScore;
         }
         return userScore/totalUserScore;
	 }
	 
	 /**
	  * Calculate the network game score. Product of gamescore and network User Weight
	  * 
	  * @param instance
	  * @return
	  */
	 public static double calculateNetworkGameScore(String gameId, Mongo instance)	 
	 {
		 double networkGameScore = 0.0;
		 double finalScore =0.0;
		 double totalWeight = calculateTotalGameNetworkUserWeight(gameId, instance);
		 DB db = initDb(instance);
         DBCollection articleCollection = db.getCollection("UserGameScoreMap");       
         String mapFunction="function(){emit(\"total\",(this.gameScore*this.networkUserWeight));};";
         String reduceFunction="function(key, values){return Array.sum(values);};";      
         DBObject query = new BasicDBObject("gameId",gameId);
         MapReduceOutput output = articleCollection.mapReduce(mapFunction, reduceFunction, null, MapReduceCommand.OutputType.INLINE,query);	            
         for(DBObject object: output.results())
         {           
        	 networkGameScore=(double)object.get("value");
         }         
         if(totalWeight>0)         
         {
        	 finalScore=networkGameScore/totalWeight;
         }
		 return finalScore;
	 }
	 
	 private static double calculateTotalGameNetworkUserWeight(String gameId, Mongo instance)
	 {
		 double totalGameNetworkUserWeight=0;
		 DB db = initDb(instance);
		 DBCollection articleCollection = db.getCollection("UserGameScoreMap");
		 String mapFunction="function(){emit(\"total\",this.networkUserWeight);};";
         String reduceFunction="function(key, values){return Array.sum(values);};"; 
		 DBObject query = new BasicDBObject("gameId",gameId);
	     MapReduceOutput output = articleCollection.mapReduce(mapFunction, reduceFunction, null, MapReduceCommand.OutputType.INLINE,query);
	     for(DBObject object: output.results())
         {           
	    	 totalGameNetworkUserWeight=(double)object.get("value");
         }
		 return totalGameNetworkUserWeight;
	 }
	 
	 /**
	  * Calculate whole network cool score
	  * 
	  * @param instance
	  * @return
	  */
	 public static double calculateNetworkTotalCoolScore(Mongo instance)
	 {
		 double networkTotalCoolScore=0;
		 DB db = initDb(instance);
		 DBCollection articleCollection = db.getCollection("Article");
		 String mapFunction="function(){emit(\"total\",this.coolScore);};";
         String reduceFunction="function(key, values){return Array.sum(values);};"; 		 
	     MapReduceOutput output = articleCollection.mapReduce(mapFunction, reduceFunction, null, MapReduceCommand.OutputType.INLINE,null);
	     for(DBObject object: output.results())
         {           
	    	 networkTotalCoolScore=(double)object.get("value");
         }
		 return networkTotalCoolScore;
		 
	 }
	 
	 /**
	  * Calculate user total cool score achieved
	  * 
	  * @param username
	  * @param instance
	  * @return
	  */
	 public static double calculateUserTotalCoolScore(String username, Mongo instance)
	 {
		 double userTotalCoolScore=0;
		 DB db = initDb(instance);
		 DBCollection articleCollection = db.getCollection("Article");
		 String mapFunction="function(){emit(\"total\",this.coolScore);};";
         String reduceFunction="function(key, values){return Array.sum(values);};"; 
		 DBObject query = new BasicDBObject("author",username);
	     MapReduceOutput output = articleCollection.mapReduce(mapFunction, reduceFunction, null, MapReduceCommand.OutputType.INLINE,query);
	     for(DBObject object: output.results())
         {           
	    	 userTotalCoolScore=(double)object.get("value");
         }
		 return userTotalCoolScore;
		 
	 }

}
