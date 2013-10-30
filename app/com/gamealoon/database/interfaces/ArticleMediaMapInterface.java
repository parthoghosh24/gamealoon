package com.gamealoon.database.interfaces;

import java.util.HashMap;
import java.util.List;

import com.gamealoon.models.ArticleMediaMap;

public interface ArticleMediaMapInterface {

	/**
	 * Article Media Map instance saved
	 * 
	 * @param articleMediaMap
	 */
	public void save(ArticleMediaMap articleMediaMap);
	
	/**
	 * Create or update Article Media Map
	 * 
	 * @param articleId
	 * @param mediaId
	 * @return
	 */
	public HashMap<String, String> createOrUpdateArticleMediaMap(String id,String articleId, String mediaId);
	
	/**
	 * Find All Media By Article
	 * 
	 * @param articleId
	 * @return
	 */
	public List<ArticleMediaMap> findAllMediaByArticle(String articleId);
	
	/**
	 * Find Article Media Map instance by id
	 * 
	 * @param id
	 * @return
	 */
	public ArticleMediaMap getById(String id);
	
	/**
	 * Find ArticleMediaMap instance by article id and media id
	 * 
	 * 
	 * @param articleId
	 * @param mediaId
	 * @return
	 */
	public ArticleMediaMap findByArticleAndMedia(String articleId, String mediaId);		
}
