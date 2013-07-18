package com.google.code.jskills.business.services;

import com.google.code.jskills.domain.User;
import com.google.code.jskills.domain.UserUuid;

public interface UserUuidService {
	void saveUserUuid(UserUuid user);
	
	UserUuid getUserUuid(User user, String uuid);
	
	UserUuid getUserUuidByUuid(String uuid);
	
	User getUserByEmail(String email);
	
	void deleteUserUuid(UserUuid userUuid);
	
	void saveUserUuid(Integer id_user, String uuid);
	
}
