package com.gamealoon.algorithm;

/**
 * Algorithm class. This class is meant for group all ranking related algorithm methods.
 * 
 * @author Partho
 *
 */
public class Algorithm {

	
	 private static final double ZSCORE=1.96; //1.96 for confidence level 95%
	
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
}
