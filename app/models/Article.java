package models;

import play.db.ebean.Model;

/**
 * This is the Article entity of Gamealoon framework. Whatever content we will be seeing on the website, say review,
 * preview, news, etc... are articles at the end of the day. These articles will be part of category such as review, preview, 
 * news and so on. That is why it is independent.
 * 
 * 
 * @author partho
 *
 */
public class Article extends Model{


	private static final long serialVersionUID = -5537313356638485365L;
	
	public long id;
	public String title;
	public String body;
	public String creationDate;
	public User author;
	public Category category;
	public Game game;

}
