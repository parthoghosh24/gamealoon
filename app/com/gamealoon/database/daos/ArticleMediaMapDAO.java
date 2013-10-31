package com.gamealoon.database.daos;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.bson.types.ObjectId;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.ArticleMediaMapInterface;
import com.gamealoon.models.ArticleMediaMap;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

public class ArticleMediaMapDAO extends GloonDAO implements ArticleMediaMapInterface {

	private static final ArticleMediaMapDAO DATA_ACCESS_LAYER=new ArticleMediaMapDAO();		
	private Datastore gloonDatastore=null;
		private ArticleMediaMapDAO()
		{
			super();
			gloonDatastore=initDatastore();		
		}
		
		/**
		 * Singleton way to instantiate Gloon DAO
		 * @return
		 */
		public static ArticleMediaMapDAO instantiateDAO()
		{								
			return DATA_ACCESS_LAYER;
		}

		@Override
		public void save(ArticleMediaMap articleMediaMap) {
			gloonDatastore.save(articleMediaMap);			
		}

		@Override
		public HashMap<String, String> createOrUpdateArticleMediaMap(String id, String articleId, String mediaId) 
		{
			HashMap<String, String> response = new HashMap<>();
			response.put("status", "fail");
			ArticleMediaMap articleMediaMap = createOrUpdateArticleMediaMapInstance(id, articleId, mediaId);
			if(articleMediaMap!=null)
			{
				save(articleMediaMap);
				response.put("status", "success");
			}
			return response;			
		}

		/**
		 * Create or update an ArticleMediaMap instance
		 * 
		 * @param articleId
		 * @param mediaId
		 * @return
		 */
		private ArticleMediaMap createOrUpdateArticleMediaMapInstance(String id, String articleId, String mediaId) {
			ArticleMediaMap articleMediaMap = null;
			Date time = new Date();
			if(id.isEmpty())
			{
				articleMediaMap = new ArticleMediaMap();
				articleMediaMap.setInsertTime(Utility.convertDateToString(time));
				articleMediaMap.setTimestamp(time.getTime());				
			}
			else
			{
				articleMediaMap = getById(id);
				articleMediaMap.setUpdateTime(Utility.convertDateToString(time));
			}
			articleMediaMap.setArticleId(articleId);
			articleMediaMap.setMediaId(mediaId);			
			return articleMediaMap;
			
		}

		@Override
		public List<ArticleMediaMap> findAllMediaByArticle(String articleId) {			
			return gloonDatastore.createQuery(ArticleMediaMap.class).filter("articleId", articleId).asList();
		}

		@Override
		public ArticleMediaMap getById(String id) {			
			return gloonDatastore.get(ArticleMediaMap.class, new ObjectId(id));
		}

		@Override
		public ArticleMediaMap findByArticleAndMedia(String articleId,String mediaId) {
			
			return gloonDatastore.createQuery(ArticleMediaMap.class).filter("articleId", articleId).filter("mediaId", mediaId).get() ;
		}
}
