package models;




import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;


/**
 * This is the platform entity of Gamealoon framework. A platform is something on which games run.
 * This can be playstation, xbox, wiiu, mobile, browser and so on. So a platform will have its fare share of articles.
 * This will be maintained by Gamealoon team internally.
 * 
 * @author partho
 *
 */

@Entity
public class Platform{

	
	@Id
	ObjectId id;
	private String title; //playstation, pc, xbox, browser...
	private String description;	
	private String manufacturer; // Sony, Microsoft, Nintendo, Google, Apple
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
	/**
	 * Title is returned as String
	 */
	public String toString()
	{
		return this.title;
	}

}
