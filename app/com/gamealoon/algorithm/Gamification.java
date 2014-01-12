package com.gamealoon.algorithm;

public class Gamification {

	private static final int LEVEL_CONSTANT = 50;
	private static final int GP_CONSTANT = 300;

	/**
	 * For a user, we are calculating current level via experience points. To calculate level we are using GP of (2^n) where n is level
	 * from 0 to N. Formula to calculate level is if xp >= 2^currLevel * 100 (level_constant), increase level by 1.
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
}
