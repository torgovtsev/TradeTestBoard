package com.google.code.jskills.business.services;

import java.util.List;

import com.google.code.jskills.domain.Country;
import com.google.code.jskills.domain.User;

public interface RegistrationService {
	
	void saveUser(User user);
	
	List<Country> getAllCountries();
	
	String generateUUID();
	
	String computeHash(String source);
	
	void sendMail(User user, String uuid, String password, String secretAnswer);
	
	boolean findUserByEmail(String email);
	
}
