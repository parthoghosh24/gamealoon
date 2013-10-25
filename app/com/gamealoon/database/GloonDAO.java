package com.gamealoon.database;

import play.Logger;

import com.google.code.morphia.Datastore;

/**
 * This is the application data access layer.
 * DB and Datastore initiation takes place over here.
 * 
 * 
 * @author Partho
 *
 */
public class GloonDAO{
		
	private static GloonDatabase gloonDatabaseInstance=null;
	private static Datastore gloonDatastore=null;
	protected GloonDAO()
	{		
	    if(gloonDatabaseInstance == null)
	    {
	    	Logger.debug("In GLOONDAO ...");
	    	gloonDatabaseInstance = GloonDatabase.instantiate();
	    	gloonDatastore=gloonDatabaseInstance.gloonDatastoreInstance();	    	
	    }
		
	}		
	 
	/**
	 * 
	 * gloonDatastore instantiated
	 * @return gloonDatastore
	 */
	protected Datastore initDatastore()
	{								
		return gloonDatastore;
		
	}	
	
	/**
	 * Returns an instance of GloonDatabase
	 * 
	 * @return
	 */
	protected GloonDatabase getDatabaseInstance()
	{
		return gloonDatabaseInstance;
	}
	

}
