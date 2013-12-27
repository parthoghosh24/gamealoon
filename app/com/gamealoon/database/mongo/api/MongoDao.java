package com.gamealoon.database.mongo.api;

/**
 * Generic interface for data access objects
 * 
 * @param <T> the underlying entity type.
 * 
 * @author Gamealoon
 * @version 1.0
 */
public interface MongoDao<T> {

	/**
	 * @param game
	 */
	void save(T object);

	/**
	 * Get article by id
	 * 
	 * @param identifier
	 */
	T getById(String identifier);
}
