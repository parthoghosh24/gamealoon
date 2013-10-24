package com.gamealoon.database.daos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.bson.types.ObjectId;

import play.Logger;
import play.mvc.Http.MultipartFormData.FilePart;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.MediaInterface;
import com.gamealoon.models.Media;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

public class MediaDAO extends GloonDAO implements MediaInterface {
	
	private static final MediaDAO DATA_ACCESS_LAYER=new MediaDAO();		
	private Datastore gloonDatastore=null;
		private MediaDAO()
		{
			super();
			gloonDatastore=initDatastore();		
		}
		
		/**
		 * Singleton way to instantiate Gloon DAO
		 * @return
		 */
		public static MediaDAO instantiateDAO()
		{								
			return DATA_ACCESS_LAYER;
		}

		@Override
		public void save(Media media) {			
			gloonDatastore.save(media);
		}

		@Override
		public Media getById(String id) {			
			return gloonDatastore.get(Media.class, new ObjectId(id));
		}

		@Override
		public List<Media> findAllByParent(String parentId) {
			// TODO Auto-generated method stub
			return gloonDatastore.createQuery(Media.class).filter("parentId", parentId).asList();
		}

		@Override
		public void createOrUpdateMedia(String mediaId, HashMap<String, String> mediaMap, String parentId) {
			Media media=null;
			Date time = new Date();
			if(mediaId.isEmpty())//New media getting created
			{
				media = new Media();
				media.setMediaType(Media.IMAGE);
				media.setTimestamp(time.getTime());
				media.setInsertTime(Utility.convertDateToString(time));
			}
			else //Media updated
			{
				media = getById(mediaId);
				media.setUpdateTime(Utility.convertDateToString(time));
			}			
			media.setMediaTitle(mediaMap.get("mediaTitle"));
			media.setFileName(mediaMap.get("mediaFileName"));
			media.setParentId(parentId);
			media.setRelativeFilePath(mediaMap.get("mediaRelativeFilePath"));
			
			save(media);
			
			
		}

		@Override
		public HashMap<String, String> uploadImage(String userName,FilePart imagePart) {
			
			HashMap<String, String> response = new HashMap<>();
			response.put("status", "fail");			
			Logger.debug("Actual File name: "+imagePart.getFilename());				
			File image = imagePart.getFile();					
			String requiredFileName = AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+userName+"\\uploads\\"+imagePart.getFilename();
			Logger.debug("requiredFileName "+requiredFileName);			
			File finalImage = new File(requiredFileName);
			Logger.debug("Final image exist: "+finalImage.exists());								     
			try {
			      if(!finalImage.exists())
				  {
			    	  finalImage.createNewFile();
			    	  Utility.mediaCopy(image, finalImage);
				   } 
					  response.put("status", "success");
						
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();					
				}
			return response;
		}

		@Override
		public HashMap<String, ArrayList<HashMap<String,String>>> fetchImageForBrowser(String userName, Long timeStamp) {
			HashMap<String, ArrayList<HashMap<String,String>>> response = new HashMap<>();
			ArrayList<HashMap<String,String>> imageUrls = fetchImageUrls(userName,timeStamp);
			response.put("images", imageUrls);
			return response;
		}
		
		private ArrayList<HashMap<String,String>> fetchImageUrls(String userName, Long timeStamp)
		{
			ArrayList<HashMap<String,String>> urlList = new ArrayList<>();
			String uploadsDirectoryName = AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+userName+"\\uploads";
			File uploadsDirectory = new File(uploadsDirectoryName);
			File[] files =uploadsDirectory.listFiles();
			Arrays.sort(files, new Comparator<File>() {

				@Override
				public int compare(File file1, File file2) {					
					return Long.valueOf(file2.lastModified()).compareTo(Long.valueOf(file1.lastModified()));
				}
			});
			
			if(uploadsDirectory.exists() && uploadsDirectory.isDirectory())
			{
				for(File file: files)
				{
					if(!file.isDirectory() && file.lastModified()>timeStamp)
					{
						String url=AppConstants.APP_IMAGE_USER_URL_PATH+"/"+userName+"/uploads/"+file.getName();
						HashMap<String, String> urlMap = new HashMap<>();
						urlMap.put("url", url);
						urlMap.put("timeStamp", Long.toString(file.lastModified()));
						urlList.add(urlMap);
					}
				}
			}
			return urlList;
		}
	

}
