package com.gamealoon.database.daos;

import java.util.Date;

import play.Logger;

import com.gamealoon.algorithm.RankAlgorithm;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.interfaces.UserArticleVotingMapInterface;
import com.gamealoon.models.Activity;
import com.gamealoon.models.Article;
import com.gamealoon.models.User;
import com.gamealoon.models.UserArticleVotingMap;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;
import com.mongodb.Mongo;

public class UserArticleVotingMapDAO extends GloonDAO implements UserArticleVotingMapInterface {

	private static final UserArticleVotingMapDAO DATA_ACCESS_LAYER=new UserArticleVotingMapDAO();
	private static final ActivityDAO activityDaoInstance = ActivityDAO.instantiateDAO();
	private static final UserDAO userDaoInstance = UserDAO.instantiateDAO();
	private Datastore gloonDatastore=null;
	private UserArticleVotingMapDAO()
	{
		super();
		gloonDatastore=initDatastore();		
	}
	
	/**
	 * Singleton way to instantiate Gloon DAO
	 * @return
	 */
	public static UserArticleVotingMapDAO instantiateDAO()
	{								
		return DATA_ACCESS_LAYER;
	}
	
	@Override
	public void save(UserArticleVotingMap articleVotingMap) {
		gloonDatastore.save(articleVotingMap);		
	}

	@Override
	public UserArticleVotingMap createOrUpdateUserArticleVotingMapInstance(User viewer, Article article, Integer type) {
		Mongo instance = getDatabaseInstance().getMongoInstance();
		User author = userDaoInstance.findByUsername(article.getAuthor());
		Date time = new Date();
		UserArticleVotingMap articleVotingMap=null;		
		try
		{
			articleVotingMap = gloonDatastore.createQuery(UserArticleVotingMap.class).filter("username", viewer.getUsername()).filter("articleId", article.getId().toString()).get();			
			Double coolScore = article.getCoolScore();
			Double notCoolScore = article.getNotCoolScore();
			if(articleVotingMap==null)			
			{
				articleVotingMap = new UserArticleVotingMap();
				articleVotingMap.setArticleId(article.getId().toString());
				articleVotingMap.setUsername(viewer.getUsername());
				if(Article.COOL == type)
				{
					articleVotingMap.setCool(UserArticleVotingMap.SET);
					article.setCoolScore(++coolScore);
					author.setUserTotalCoolScore(RankAlgorithm.calculateUserTotalCoolScore(author.getUsername(), instance));
					articleVotingMap.setNotCool(UserArticleVotingMap.UNSET);
					activityDaoInstance.create(Activity.ACTIVITY_POST_COOL, viewer.getUsername(), article.getId().toString(), AppConstants.PUBLIC,Utility.convertDateToString(time), time.getTime());
				}
				else if(Article.NOTCOOL == type)
				{
					articleVotingMap.setNotCool(UserArticleVotingMap.SET);
					article.setNotCoolScore(++notCoolScore);
					articleVotingMap.setCool(UserArticleVotingMap.UNSET);
					activityDaoInstance.create(Activity.ACTIVITY_POST_NOT_COOL, viewer.getUsername(), article.getId().toString(), AppConstants.PRIVATE,Utility.convertDateToString(time), time.getTime());
				}
				gloonDatastore.save(article);				
				gloonDatastore.save(author);
				save(articleVotingMap);				
			}
			else
			{
				if(Article.COOL == type)					
				{
					if(articleVotingMap.getNotCool() == UserArticleVotingMap.SET)
					{
						return null;
						
					}
					else if(articleVotingMap.getNotCool() == UserArticleVotingMap.UNSET)
							{
								if(articleVotingMap.getCool() == UserArticleVotingMap.SET)
								{
									articleVotingMap.setCool(UserArticleVotingMap.UNSET);
									article.setCoolScore(--coolScore);									
								}
								else
								{
									articleVotingMap.setCool(UserArticleVotingMap.SET);
									article.setCoolScore(++coolScore);
								}
								gloonDatastore.save(article);
								author.setUserTotalCoolScore(RankAlgorithm.calculateUserTotalCoolScore(author.getUsername(), instance));								
								gloonDatastore.save(author);
								save(articleVotingMap);			
								activityDaoInstance.create(Activity.ACTIVITY_POST_COOL, viewer.getUsername(), article.getId().toString(), AppConstants.PUBLIC,Utility.convertDateToString(time), time.getTime());
							}	
					
					
				}
				if(Article.NOTCOOL == type)
				{
					if(articleVotingMap.getCool() == UserArticleVotingMap.SET)
					{
						return null;
						
					}
					else if(articleVotingMap.getCool() == UserArticleVotingMap.UNSET)
							{
								if(articleVotingMap.getNotCool() == UserArticleVotingMap.SET)
								{
									articleVotingMap.setNotCool(UserArticleVotingMap.UNSET);
									article.setNotCoolScore(--notCoolScore);
								}
								else
								{
									articleVotingMap.setNotCool(UserArticleVotingMap.SET);
									article.setNotCoolScore(++notCoolScore);
								}
								gloonDatastore.save(article);
								save(articleVotingMap);			
								activityDaoInstance.create(Activity.ACTIVITY_POST_NOT_COOL, viewer.getUsername(), article.getId().toString(), AppConstants.PRIVATE,Utility.convertDateToString(time), time.getTime());
							}
					
				}
			}
			
			
		}
		catch(Exception exception)
		{
			Logger.error("Error In UserArticleVotingMap while creating or updating instance: "+exception.getLocalizedMessage(), exception.fillInStackTrace());			
		}
			
		return articleVotingMap;
	}

	@Override
	public UserArticleVotingMap fetchByUsernameAndArticle(String username,Article article) {		
		return gloonDatastore.createQuery(UserArticleVotingMap.class).filter("username", username).filter("articleId", article.getId().toString()).get();
	}

}
