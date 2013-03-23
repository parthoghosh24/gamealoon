package models;


import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

/**
 * This is the platform entity of Gamealoon framework. A platform is something on which games run.
 * This can be playstation, xbox, wiiu, mobile, browser and so on. So a platform will have its fare share of articles.
 * This will be maintained by Gamealoon team internally.
 * 
 * @author partho
 *
 */

@Entity
public class Platform extends Model {

	private static final long serialVersionUID = 2241184615513664270L;
	
	@Id
	public long id;
	public String title; //playstation, pc, xbox, browser...
	public String description;
	public String manufacturer; // Sony, Microsoft, Nintendo, Google, Apple
		

}
