package com.gamealoon.models;


/**
 * This is the category entity of gamealoon framework. This will be maintained and updated by gamealoon team internally.
 * Outside user cannot modify this. A category is something which will group the articles created. For example, review, preview,
 * news, etc. This is a simple enum.
 * 
 * 
 * @author partho
 *
 */
public enum Category {
	
	/**
	 * A review is overall process of judging a game from various perspective.
	 */
	review,  
	/**
	 * A Preview is an early look of a game
	 */
	preview, 
	/**
	 * A News is some new and exciting information about a game
	 */
	news, 
	/**
	 * A Feature is a discussion about a game focusing on some particular aspect of a game
	 */
	feature, 
	/**
	 * Gloonicle is normative in nature. It is generally user's opinion. It can be anything
	 * from my top 10 games to my top 10 moments in max payne 3 to why do we have to starve
	 * for a great cricket game
	 */
	gloonicle 
}
