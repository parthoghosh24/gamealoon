package com.gamealoon.algorithm;

/**
 * Algorithm class. This class is meant for group all ranking related algorithm methods.
 * 
 * @author Partho
 *
 */
public class Algorithm {

	
	 private static final double ZSCORE=1.96; //1.96 for confidence level 95%
	 
	 private static final double ARTICLE_WEIGHT=0.75;
	 private static final double FOLLOWER_WEIGHT=0.15;
	 private static final double ACHIEVEMENT_WEIGHT=0.10;
	 
	 /**
	  * This method calculates wilson score interval for up and down vote system.
	  * Actual rights goes to http://www.linkedin.com/search?search=&industry=123&sortCriteria=R&keepFacets=true 
	  * 
	  * @param totalPositives
	  * @param totalNegatives
	  * @return
	  */
	 public double wilsonScoreCalculator(double totalPositives, double totalNegatives)
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
	  * This algorithm calculates the totalScore for article which is required for sorting the articles as well for scoring the respective user.
	  * 
	  * @param coolNotCoolScore
	  * @param pageHitScore
	  * @param comments
	  * @return
	  */
	 public double calculateArticleScore(double coolNotCoolScore, double pageHitScore, double comments)
	 {
		 //TODO improve the algo.
		 return coolNotCoolScore+pageHitScore+comments;
	 }
	 
	 /**
	  * This algorithm calculate the userScore for User which is required to rank users.
	  * 
	  * @param totalArticleScore
	  * @param totalAchievementsScore
	  * @param totalFollowersScore
	  * @return
	  */
	 public double calculateUserScore(double totalArticleScore, double totalAchievementsScore, double totalFollowersScore)
	 {
		 //TODO improve the algo
		 return (totalArticleScore*ARTICLE_WEIGHT)+(totalFollowersScore*FOLLOWER_WEIGHT)+(totalAchievementsScore*ACHIEVEMENT_WEIGHT);
	 }
}
