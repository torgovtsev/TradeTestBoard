package com.google.code.jskills.business.services.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import com.google.code.jskills.business.services.UserUuidService;
import com.google.code.jskills.dal.providers.UserUuidDataProvider;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.domain.UserUuid;

@Stateless
public class UserUuidServiceImpl implements UserUuidService{
	@Inject
	private transient UserUuidDataProvider dataProvider;

	@Override
	public void saveUserUuid(UserUuid userUuid) {
		dataProvider.saveUserUuid(userUuid);
		
	}

	@Override
	public UserUuid getUserUuid(User user, String uuid) {
		return dataProvider.getUserUuid(user, uuid);
	}

	@Override
	public void deleteUserUuid(UserUuid userUuid) {
		dataProvider.deleteUserUuid(userUuid);
	}

	@Override
	public UserUuid getUserUuidByUuid(String uuid) {
		return dataProvider.getUserUuidByUuid(uuid);
	}

	@Override
	public User getUserByEmail(String email) {
		return dataProvider.getUserByEmail(email);
	}
	@Override
	public void saveUserUuid(Integer id_user, String uuid) {
		 dataProvider.saveUserUuid(id_user, uuid);
	}

}
