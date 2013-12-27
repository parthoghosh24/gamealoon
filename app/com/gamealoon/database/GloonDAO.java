package com.gamealoon.database;

import org.bson.types.ObjectId;

import play.Logger;

import com.gamealoon.database.mongo.api.MongoDao;
import com.gamealoon.utility.ClassUtil;
import com.google.code.morphia.Datastore;

/**
 * This is the application data access layer. DB and Datastore initiation takes place over here.
 * 
 * 
 * @author Partho
 * 
 */
public abstract class GloonDAO<T> implements MongoDao<T> {

	private static GloonDatabase gloonDatabaseInstance = null;
	private static Datastore gloonDatastore = null;

	private Class<? extends T> entityClass;

	protected GloonDAO() {
		if (gloonDatabaseInstance == null) {
			Logger.debug("In GLOONDAO ...");
			gloonDatabaseInstance = GloonDatabase.instantiate();
			gloonDatastore = gloonDatabaseInstance.gloonDatastoreInstance();
		}
	}

	/**
	 * gloonDatastore instantiated
	 * 
	 * @return gloonDatastore
	 */
	protected Datastore initDatastore() {
		return gloonDatastore;
	}

	/**
	 * Returns an instance of GloonDatabase
	 * 
	 * @return
	 */
	protected GloonDatabase getDatabaseInstance() {
		return gloonDatabaseInstance;
	}

	public void save(final T entity) {
		gloonDatastore.save(entity);
	}

	public T getById(final String id) {
		return gloonDatastore.get(getEntityClass(), new ObjectId(id));
	}

	// public T getById(final Class<T> clazz, final String id) {
	// return gloonDatastore.get(clazz, new ObjectId(id));
	// }

	protected Class<? extends T> getEntityClass() {
		if (this.entityClass == null) {
			this.entityClass = ClassUtil.getEntityClass(this);
		}
		return this.entityClass;
	}
}
