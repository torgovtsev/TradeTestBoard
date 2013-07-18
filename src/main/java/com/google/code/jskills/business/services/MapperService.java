package com.google.code.jskills.business.services;

/**
 * Service provides method(s) for fast mapping data entities to domain entities
 * and vise versa
 * 
 * @author iternovy
 * 
 */
public interface MapperService {
	
	/**
	 * Maps give object to destination class
	 */
	<T> T mapTo(Object source, Class<?> destinationClass);
}
