package com.gamealoon.models;
import java.io.File;
import java.net.MalformedURLException;

import org.bson.types.ObjectId;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Transient;

/**
 * Media domain keeps track of image and video uploaded in this system
 * 
 * @author Partho
 *
 */
@Entity
public class Media {

	@Id
	private ObjectId id;	
	private String fileName;		
	private String url;
	private int mediaType;
	private String insertTime;
	private String updateTime;
	private Long timestamp;	
	private String owner;  //parent owner group
	private String immediateOwner; //particular game or user
	
	@Transient
	private File file;
	
	public final static int IMAGE=1;
	public final static int VIDEO=2;
	public final static String BASE_AWS_URL="https://s3.amazonaws.com/";
	
	/**
	 * Media Owner Type
	 * 
	 */
	public final static String USER="user";
	public final static String GAME="game";

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id; 
	}

	/**
	 * @return the mediaType
	 */
	public int getMediaType() {
		return mediaType;
	}

	/**
	 * @param mediaType the mediaType to set
	 */
	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}

	/**
	 * @return the insertTime
	 */
	public String getInsertTime() {
		return insertTime;
	}

	/**
	 * @param insertTime the insertTime to set
	 */
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the url
	 */
	public String getUrl() throws MalformedURLException {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the immediateOwner
	 */
	public String getImmediateOwner() {
		return immediateOwner;
	}

	/**
	 * @param immediateOwner the immediateOwner to set
	 */
	public void setImmediateOwner(String immediateOwner) {
		this.immediateOwner = immediateOwner;
	}
}
