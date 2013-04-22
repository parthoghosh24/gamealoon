package com.gamealoon.controllers;


import com.gamealoon.database.GloonDAO;
import com.gamealoon.views.html.platforms.platformindex;
import com.google.code.morphia.Datastore;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * This is the platform controller. It exposes user to all platformed filtered information, i.e, all ps3, xbox360, pc, etc. information 
 * 
 * @author Partho
 *
 */
public class PlatformController extends Controller {

	static final GloonDAO gloonDaoInstance = GloonDAO.instantiateDAO();
    static final Datastore gloonDatastore = gloonDaoInstance.initDatastore();
    
    public static Result index()
    {
    	return ok(platformindex.render());
    }
    
    public static Result getPlatformData(String platform)
    {
    	return null;
    }
}
