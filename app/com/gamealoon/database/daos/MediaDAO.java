package com.gamealoon.database.daos;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;

import play.Logger;
import play.mvc.Http.MultipartFormData.FilePart;
import plugins.S3Plugin;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.MediaInterface;
import com.gamealoon.models.Media;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

public class MediaDAO extends GloonDAO<Media> implements MediaInterface {

	private static final MediaDAO DATA_ACCESS_LAYER = new MediaDAO();
	private Datastore gloonDatastore = null;

	private MediaDAO() {
		super();
		gloonDatastore = initDatastore();
	}

	/**
	 * Singleton way to instantiate Gloon DAO
	 * 
	 * @return
	 */
	public static MediaDAO instantiateDAO() {
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
	public HashMap<String, String> createOrUpdateMedia(String mediaId, FilePart filePart, String username, String mediaOwnerType) {

		HashMap<String, String> response = new HashMap<>();
		response.put("status", "fail");
		Media media = createOrUpdateMediaInstance(mediaId, filePart, username, mediaOwnerType);
		if (media != null)// New media getting created
		{
			response = uploadMedia(media);
			save(media);
			response.put("mediaId", media.getId().toString());
		}
		return response;
	}

	/**
	 * Media instance
	 * 
	 * @return
	 */
	private Media createOrUpdateMediaInstance(String mediaId, FilePart filePart, String username, String mediaOwnerType) {
		Logger.debug("MEDIA GETTING CREATED");
		Logger.info("MEDIA GETTING CREATED");
		Media media = null;
		Date time = new Date();
		if (mediaId.isEmpty() || "none".equalsIgnoreCase(mediaId)) {
			media = new Media();
			media.setTimestamp(time.getTime());
			media.setInsertTime(Utility.convertDateToString(time));
		} else {
			media = getById(mediaId);
			media.setUpdateTime(Utility.convertDateToString(time));
		}
		media.setFileName(filePart.getFilename());
		media.setMediaType(Media.IMAGE);
		media.setFile(filePart.getFile());
		media.setUrl("");
		media.setOwner(mediaOwnerType);
		media.setImmediateOwner(username);
		return media;
	}

	@Override
	public HashMap<String, String> uploadMedia(Media media) {

		HashMap<String, String> response = new HashMap<>();
		response.put("status", "fail");
		String actualFileName = media.getFileName();
		String owner = media.getOwner();
		String immediateOwner = media.getImmediateOwner();
		Logger.debug("Actual File name: " + actualFileName);
		File image = media.getFile();
		String subFolder = owner + "/" + immediateOwner + "/uploads/";
		String relativeFileName = subFolder + actualFileName;
		Logger.debug("relativeFileName " + relativeFileName);
		if (S3Plugin.amazonS3 == null) {
			Logger.error("Could not save because amazon S3 instance was null");
			throw new RuntimeException("Could not save");

		} else {
			PutObjectRequest putObjectRequest = new PutObjectRequest(S3Plugin.s3Bucket, relativeFileName, image);
			putObjectRequest.withCannedAcl(CannedAccessControlList.PublicReadWrite);
			S3Plugin.amazonS3.putObject(putObjectRequest);
			Logger.debug("S3 Bucket   " + S3Plugin.s3Bucket);
			Logger.debug("Owner " + owner);
			String url = Media.BASE_AWS_URL + S3Plugin.s3Bucket + "/" + relativeFileName;
			Logger.debug(url);
			media.setUrl(url);
			save(media);
			response.put("status", "success");
		}

		return response;
	}

	@Override
	public HashMap<String, ArrayList<HashMap<String, String>>> fetchImageForBrowser(String userName, Long timeStamp)
			throws MalformedURLException {
		HashMap<String, ArrayList<HashMap<String, String>>> response = new HashMap<>();
		ArrayList<HashMap<String, String>> imageUrls = new ArrayList<>();
		List<Media> mediaList = fetchImageUrls(userName, timeStamp);

		if (mediaList.size() > 0) {
			for (Media media : mediaList) {
				HashMap<String, String> mediaMap = new HashMap<>();
				mediaMap.put("mediaId", media.getId().toString());
				mediaMap.put("mediaUrl", media.getUrl());
				mediaMap.put("mediaTimestamp", media.getTimestamp().toString());
				imageUrls.add(mediaMap);
			}
		}

		response.put("images", imageUrls);
		return response;
	}

	private List<Media> fetchImageUrls(String userName, Long timeStamp) {
		return gloonDatastore.createQuery(Media.class).filter("immediateOwner", userName).order("-insertTime")
				.filter("timestamp >", timeStamp).asList();
	}

}
