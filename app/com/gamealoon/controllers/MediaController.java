package com.gamealoon.controllers;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import com.gamealoon.database.daos.MediaDAO;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import static play.data.Form.form;
import static play.libs.Json.toJson;

public class MediaController extends Controller{

	private static final MediaDAO mediaDAOInstance = MediaDAO.instantiateDAO();
	
	public static Result checkStatus(String userName, String mediaId, String mediaOwnerType)
	{
		
			
		 	response().setHeader("Access-Control-Allow-Origin", "*");       // Need to add the correct domain in here!!
		    response().setHeader("Access-Control-Allow-Methods", "POST"); 
		    response().setHeader("Access-Control-Max-Age", "300");          // Cache response for 5 minutes
		    response().setHeader("Access-Control-Allow-Headers", "accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");
//		    response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");         // Ensure this header is also allowed!  
		    return ok();
	}
		
	public static Result uploadImage(String userName, String mediaId, String mediaOwnerType)
	{					
	    MultipartFormData body = request().body().asMultipartFormData();				
		FilePart imagePart = body.getFile("previewFile");
		HashMap<String, String> response = createOrUpdateImage(mediaId, userName,imagePart,mediaOwnerType);
		response().setHeader("Access-Control-Allow-Origin", "*");       // Need to add the correct domain in here!!
	    response().setHeader("Access-Control-Allow-Methods", "POST,OPTIONS"); 
	    response().setHeader("Access-Control-Max-Age", "300");          // Cache response for 5 minutes
	    response().setHeader("Access-Control-Allow-Headers", "accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");
		return ok(toJson(response));
	}
	
	public static Result fetchImages(String userName,String timeStamp)
	{
		HashMap<String, ArrayList<HashMap<String, String>>> response= new HashMap<>();
		try {
			response = fetchImagesMap(userName,Long.parseLong(timeStamp));
		} catch (NumberFormatException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok(toJson(response));
	}
	
	public static Result createOrUpdateAdminMedia()
	{
		DynamicForm adminMediaData = form().bindFromRequest();
		HashMap<String,String> response = mediaDAOInstance.createOrUpdateAdminMedia(adminMediaData);
		return ok(toJson(response));
	}
	
	/**
	 * Fetch user images from its uploads dir
	 * 
	 * @param userName
	 * @return
	 * @throws MalformedURLException 
	 */
	private static HashMap<String, ArrayList<HashMap<String, String>>> fetchImagesMap(String userName,Long timeStamp) throws MalformedURLException {		
		return mediaDAOInstance.fetchImageForBrowser(userName,timeStamp);
	}

	/**
	 * Upload Image in user's uploads folder
	 * 
	 * @param userName
	 * @param imagePart
	 * @return
	 */
	private static HashMap<String, String> createOrUpdateImage(String mediaId, String userName, FilePart imagePart, String mediaOwnerType) {		
		return mediaDAOInstance.createOrUpdateMedia(mediaId, imagePart, userName, mediaOwnerType);
	}
	
}
