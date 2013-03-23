package models;

import play.db.ebean.Model;

/**
 * This is the category entity of gamealoon framework. This will be maintained and updated by gamealoon team internally.
 * Outside user cannot modify this. A category is something which will group the articles created. For example, review, preview,
 * news, etc.
 * 
 * 
 * @author partho
 *
 */
public class Category extends Model{

	private static final long serialVersionUID = 4482487573155059686L;
	
	public long id;
	public String title; //Category name such as review, preview,etc.
	public String description; //Category description
	public String creationDate;
	

	
}
