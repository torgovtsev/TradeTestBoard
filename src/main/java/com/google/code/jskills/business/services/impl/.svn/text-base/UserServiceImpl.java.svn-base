package com.google.code.jskills.business.services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.google.code.jskills.business.services.UserService;
import com.google.code.jskills.dal.providers.UserDataProvider;
import com.google.code.jskills.domain.User;

@Stateless
public class UserServiceImpl implements UserService {

	@Inject
	private transient UserDataProvider dataProvider;
	
	@Override
	public void saveUser(User user) {
		dataProvider.saveUser(user);
	}
	/**
	 * find user by email
	 * 
	 * @param String email
	 * 
	 * @return boolean
	 */
	@Override
	public User findUserByEmail(String email) {
		return dataProvider.getUserByEmail(email);
	}
	
	/**
	 * Get user by id
	 * 
	 * @param int id
	 * 
	 * @return User
	 */
	@Override
	public User getUserByID(int id) {
		return dataProvider.getUserByID(id);
	}
	
	@Override
	public User getUserByID(Integer id) {
		return dataProvider.getUserByID(id);
	}
	
	/**
	 * delete user by id
	 * 
	 * @param int id
	 */
	@Override
	public void deleteUserByID(int id) {
		User user = dataProvider.getUserByID(id);
		dataProvider.deleteUser(user);
	}
	
	@Override
	public void updateUser(User user) {
		dataProvider.updateUser(user);
	}
	
	@Override
	public List<User> getAllUsers() {
		return dataProvider.getAllUsers();
	}
	@Override
	public Integer getNumberOfAllUsers() {
		return dataProvider.getNumberOfAllUsers();
	}
}
