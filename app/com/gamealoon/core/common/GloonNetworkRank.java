package com.gamealoon.core.common;

/**
 * Network Rank checkpoints
 * 
 * @author Partho
 *
 */
public enum GloonNetworkRank {

	/**
	 * New Gloonie
	 */
	NEW_GLOONIE(0),
	
	/**
	 * Gamealoon Yogi
	 */
	G_YOGI(1),
	
	/**
	 * Gamealoon Big Boss
	 */
	BOSS(2),
	
	/**
	 * Legend of Gamealoonland
	 */
	LEGEND(3),
	
	/**
	 * Gamealoonova
	 * 
	 */
	G_NOVA(4),
	
	/**
	 * God of W...err... Gamealoon
	 */
	G_GOD(5),
	
	/**
	 * Gloonie
	 */
	GLOONIE(6);
	
	/**
	 * 
	 */
	private int rankValue;
	
	private GloonNetworkRank(final int rankValue)
	{
		this.rankValue=rankValue;
	}

	/**
	 * @return the rankValue
	 */
	public int getRankValue() {
		return rankValue;
	}
	
	
}
