package com.gamealoon.database.interfaces;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import play.mvc.Http.MultipartFormData.FilePart;

import com.gamealoon.models.Media;

public interface MediaInterface {

	/**
	 * save media instance
	 * 
	 * @param media
	 */
	public void save(Media media);
	
	/**
	 * Get media by id
	 * 
	 * @param id
	 * @return
	 */
	public Media getById(String id);
	
	/**
	 * Find all media by parent id
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Media> findAllByParent(String parentId);
	
	/**
	 * Create or update Media based on the parent
	 * 
	 * @param mediaId
	 * @param parentId
	 */
	public HashMap<String, String> createOrUpdateMedia(String mediaId, FilePart filePart, String username, String mediaOwnerType);	
	
	/**
	 * Upload image in uploads folder of user
	 * 
	 * @param userName
	 * @param imagePart
	 * @return
	 */
	public HashMap<String, String> uploadMedia(Media media);
	
	/**
	 * Fetch images from uploads folder of a particular user
	 * 
	 * @param userName
	 * @return
	 */
	public HashMap<String, ArrayList<HashMap<String,String>>> fetchImageForBrowser(String userName,Long timeStamp) throws MalformedURLException;
}
