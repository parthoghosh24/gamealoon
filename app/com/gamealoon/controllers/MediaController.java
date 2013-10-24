package com.gamealoon.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import com.gamealoon.database.daos.MediaDAO;

import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import static play.libs.Json.toJson;

public class MediaController extends Controller{

	private static final MediaDAO mediaDAOInstance = MediaDAO.instantiateDAO();
	
	/*public static Result checkStatus(String userName)
	{
		 	response().setHeader("Access-Control-Allow-Origin", "*");       // Need to add the correct domain in here!!
		    response().setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");   // Only allow POST
		    response().setHeader("Access-Control-Max-Age", "300");          // Cache response for 5 minutes
		    response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");         // Ensure this header is also allowed!  
		    return ok();
	}*/
	
	public static Result uploadImage(String userName)
	{
		response().setHeader("Access-Control-Allow-Origin", "*");       // Need to add the correct domain in here!!
	    response().setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");   // Only allow POST
	    response().setHeader("Access-Control-Max-Age", "300");          // Cache response for 5 minutes
	    response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");         // Ensure this header is also allowed!	
	    MultipartFormData body = request().body().asMultipartFormData();				
		FilePart imagePart = body.getFile("previewFile");
		HashMap<String, String> response = uploadImageMap(userName,imagePart);
		return ok(toJson(response));
	}
	
	public static Result fetchImages(String userName,String timeStamp)
	{
		HashMap<String, ArrayList<HashMap<String, String>>> response = fetchImagesMap(userName,Long.parseLong(timeStamp));
		return ok(toJson(response));
	}
	/**
	 * Fetch user images from its uploads dir
	 * 
	 * @param userName
	 * @return
	 */
	private static HashMap<String, ArrayList<HashMap<String, String>>> fetchImagesMap(String userName,Long timeStamp) {		
		return mediaDAOInstance.fetchImageForBrowser(userName,timeStamp);
	}

	/**
	 * Upload Image in user's uploads folder
	 * 
	 * @param userName
	 * @param imagePart
	 * @return
	 */
	private static HashMap<String, String> uploadImageMap(String userName, FilePart imagePart) {		
		return mediaDAOInstance.uploadImage(userName, imagePart);
	}
	
}
