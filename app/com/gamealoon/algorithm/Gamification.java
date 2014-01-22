package com.gamealoon.algorithm;

import play.Logger;

public class Gamification {

	private static final int LEVEL_CONSTANT = 50;
	private static final int GP_CONSTANT = 1000;
	private static final int[] USER_RANK_UP_MAP={40,400,1000, 5000, 10000, 100000};
	private static final int[] ARTICLE_HOTNESS_UP_MAP={1,5,20,60,120,300};

	/**
	 * For a user, we are calculating current level via experience points. To calculate level we are using GP of (2^n) where n is level
	 * from 0 to N. Formula to calculate level is if xp >= 2^currLevel * 50 (level_constant), increase level by 1.
	 * 
	 * @param experiencePoints
	 * @return
	 */
	public static int calculateLevel(long experiencePoints, int level) {
		long levelNormalizer = (long) Math.pow(2, level);
		long nextLevelScore = levelNormalizer * LEVEL_CONSTANT;
		if (experiencePoints >= nextLevelScore) {
			++level;
		}
		return level;
	}

	/**
	 * For a user, we are calculating Gamealoon points via experience points. User earns 1 GP at 300 XP;
	 * 
	 * @param experiencePoints
	 * @return
	 */
	public static int calculateGP(long experiencePoints, int gamealoonPoints) {
		if (experiencePoints >= GP_CONSTANT) {
			++gamealoonPoints;
		}
		return gamealoonPoints;
	}
	
	/**
	 * Calculate Network Rank for user. User is awarded with a rank one it hits a particular level
	 * 
	 * @param userLevel
	 * @param userNetworkRank
	 * @return
	 */
	public static int calculateNetworkRank(long userLevel, int userNetworkRank)
	{
		if(userLevel >= USER_RANK_UP_MAP[(int) userLevel])
		{
			++userNetworkRank;
		}
		return userNetworkRank;
	}
	
	/**
	 * Calculate Article Hotness level.
	 * 
	 * @param coolCount
	 * @param uniqueCommentCount
	 * @param articleHotness
	 * @return
	 */
	public static int calculateArticleHotness(long uniqueCommentAndCoolSum, int articleHotness)
	{
		if(uniqueCommentAndCoolSum>=ARTICLE_HOTNESS_UP_MAP[(int) uniqueCommentAndCoolSum])
		{
			++articleHotness;
		}
		
		return articleHotness;				
	}
	
	/**
	 * This method calculates points to go to next level. Formula is (2^currentLevel * 50)- XP
	 * 
	 * @param experiencePoints
	 * @param currentLevel
	 * @return
	 */
	public static long pointsToGo(long userXp, int currentLevel)
	{
		Logger.debug("USER XP: "+userXp);
		Logger.debug("CURRENT LEVEL: "+currentLevel);
		long nextLevelXPBase = (long) Math.pow(2, currentLevel)*LEVEL_CONSTANT;
		Logger.debug("nextLevelXPBase: "+nextLevelXPBase);
		return nextLevelXPBase-userXp; 
	}
	
	/**
	 * This method calculates level completion ratio. Formula is (XP - (2^(currentLevel-1)*50))/((2^(currentLevel)*50)-(2^(currentLevel-1)*50))
	 * 
	 * @param userXp
	 * @param currentLevel
	 * @return
	 */
	public static double levelCompletionRatio(long userXp, int currentLevel)
	{
		long nextLevelXPBase = (long) Math.pow(2, currentLevel)*LEVEL_CONSTANT;
		long currentLevelXPBase =0;
		if(currentLevel>0)
		{
			currentLevelXPBase = (long) Math.pow(2, currentLevel-1)*LEVEL_CONSTANT;
		}
		
		double val = ((double)(userXp-currentLevelXPBase))/(nextLevelXPBase-currentLevelXPBase);
		return val;
	}
}
