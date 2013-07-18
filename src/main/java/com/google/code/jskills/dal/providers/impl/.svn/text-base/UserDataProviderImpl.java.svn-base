package com.google.code.jskills.dal.providers.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.code.jskills.business.services.MapperService;
import com.google.code.jskills.dal.entities.UserEntity;
import com.google.code.jskills.dal.providers.UserDataProvider;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.utils.CollectionsUtils;

@Stateless
public class UserDataProviderImpl implements UserDataProvider {

	@PersistenceContext(unitName = "jskillsDB")
	private EntityManager entityManager;

	@Inject
	private MapperService mapperService;

	@Override
	@TransactionAttribute
	public void saveUser(User user) {
		final UserEntity dataEntity = mapperService.mapTo(user,
				UserEntity.class);
		entityManager.persist(dataEntity);
	}

	/**
	 * find user by E-mail
	 * 
	 * @param String email
	 * 
	 * @return User
	 */
	@Override
	@TransactionAttribute
	public User getUserByEmail(String email) {
		final User result;
		try {
			final Query query = entityManager
					.createQuery("select u from UserEntity u where u.email = :email");
			query.setParameter("email", email);

			final UserEntity user = (UserEntity) query.getSingleResult();
			result = mapperService.mapTo(user, User.class);
		} catch (Exception e) {
			return null;
		}

		return result;
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
		final User result;
		try {
			final Query query = entityManager
					.createQuery("select u from UserEntity u where u.id = :id");
			query.setParameter("id", id);

			final UserEntity user = (UserEntity) query.getSingleResult();
			result = mapperService.mapTo(user, User.class);
		} catch (Exception e) {
			return null;
		}

		return result;
	}
	/**
	 * 
	 * @param id
	 * User id is Integer!
	 * @return
	 */
	@Override
	public User getUserByID(Integer id) {
		final User result;
		try {
			final Query query = entityManager
					.createQuery("select u from UserEntity u where u.id = :id");
			query.setParameter("id", id);

			final UserEntity user = (UserEntity) query.getSingleResult();
			result = mapperService.mapTo(user, User.class);
		} catch (Exception e) {
			return null;
		}

		return result;
	}
	
	/**
	 * delete user
	 * 
	 * @param User user
	 */
	@Override
	@TransactionAttribute
	public void deleteUser(User user) {
		final UserEntity dataEntity = mapperService.mapTo(user,
				UserEntity.class);
		entityManager.remove(entityManager.merge(dataEntity));
	}

	/**
	 * update user
	 * 
	 * @param User user
	 */
	@Override
	public void updateUser(User user) {
		final UserEntity dataEntity = mapperService.mapTo(user,
				UserEntity.class);
		entityManager.merge(dataEntity);
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<User> getAllUsers() {
		final List<User> result = new ArrayList<User>();
		final Query query = entityManager.createQuery("select u from UserEntity u");

		final List<UserEntity> users = CollectionsUtils.castList(query.getResultList());
		for (UserEntity dataEntity : users) {
			User businessEntity = mapperService.mapTo(dataEntity, User.class);
			result.add(businessEntity);
		}
		
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Integer getNumberOfAllUsers() {
		final Query query = entityManager.createQuery("select count(u) from UserEntity u");
		Integer num = new Integer(((Long) query.getSingleResult()).toString());						
		return num;
	}
}
