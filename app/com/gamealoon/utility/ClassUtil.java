package com.gamealoon.utility;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Provides access to class-related utility operations
 * 
 * @author Gameloon
 * @version 1.0
 */
public final class ClassUtil {

	private ClassUtil() {
		// private constructor for utility class
	}

	/**
	 * @param <T> the type
	 * @param target the target
	 * @return the parameterized type of {@code target}'s class
	 */
	public static <T> Class<T> getEntityClass(Object target) {
		Class<?> targetClass = target.getClass();
		return getParameterizedClass(targetClass);
	}

	/**
	 * @param <T> the type of the class
	 * @param targetClass the requested target class
	 * @return the generic type of {@code targetClass}
	 * @throws IllegalArgumentException if the underlying parameterized class could not be inferred via reflection.
	 */
	/**
	 * @param targetClass
	 * @return the generic type of {@code targetClass}
	 * @throws IllegalArgumentException if the underlying parameterized class could not be inferred via reflection.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getParameterizedClass(Class<?> targetClass) throws IllegalArgumentException {
		Type type = targetClass.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType paramType = (ParameterizedType) type;
			return (Class<T>) paramType.getActualTypeArguments()[0];
		}
		Class<?> superClass = targetClass.getSuperclass();
		if (superClass != targetClass) {
			// check that we do not run into recursion
			return getParameterizedClass(superClass);
		}
		throw new IllegalArgumentException("Could not guess entity class by reflection");
	}

	/**
	 * Gets a field value from an <code>Object</code> not caring about its scope. Note that only fields from the <code>Class</code>
	 * directly are accessible with this method, not from any super <code>Class</code>.
	 * 
	 * @param object
	 * @param field
	 * @return the field value
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static Object getField(Object object, String field) throws NoSuchFieldException, IllegalAccessException {
		Field decField = object.getClass().getDeclaredField(field);
		decField.setAccessible(true);
		return decField.get(object);
	}

}
