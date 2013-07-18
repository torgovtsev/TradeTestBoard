package com.google.code.jskills.dal.providers.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.code.jskills.business.services.MapperService;
import com.google.code.jskills.dal.entities.UserEntity;
import com.google.code.jskills.dal.entities.UserUuidEntity;
import com.google.code.jskills.dal.providers.UserUuidDataProvider;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.domain.UserUuid;


@Stateless
public class UserUuidDataProviderImpl implements UserUuidDataProvider {
	@PersistenceContext(unitName = "jskillsDB")
	private EntityManager entityManager;

	@Inject
	private MapperService mapperService;

	
	@Override
	@TransactionAttribute
	public void saveUserUuid(UserUuid userUuid) {
		final UserUuidEntity dataEntity = mapperService.mapTo(userUuid,
				UserUuidEntity.class);
		entityManager.persist(dataEntity);
	}
	
	@Override
	@TransactionAttribute
	public void saveUserUuid(Integer id_user, String uuid) {
		Query q = entityManager.createNativeQuery("insert into user_uuid(user_id, uuid) values( :id_user, :uuid)");
		q.setParameter("id_user", id_user);
		q.setParameter("uuid", uuid);
		int i = q.executeUpdate();
	}

	@Override
	@TransactionAttribute
	public UserUuid getUserUuid(User user, String uuid) {
		final UserUuid result;
		try {
			final Query query = entityManager
					.createQuery("select u from UserUuidEntity u where u.userEntity = :user and u.uuid = :uuid");
			
			UserEntity userEntity = mapperService.mapTo(user, UserEntity.class);
			query.setParameter("user", userEntity);
			query.setParameter("uuid", uuid);

			final UserUuidEntity userUuid = (UserUuidEntity) query.getSingleResult();
			result = mapperService.mapTo(userUuid, UserUuid.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}

	@Override
	@TransactionAttribute
	public void deleteUserUuid(UserUuid userUuid) {
		final UserUuidEntity dataEntity = mapperService.mapTo(userUuid,
				UserUuidEntity.class);
		entityManager.remove(dataEntity);
		
	}

	@Override
	@TransactionAttribute
	public UserUuid getUserUuidByUuid(String uuid) {
		final UserUuid result;
		try {
			String string = "select u from UserUuidEntity u where u.uuid = :uuid";
			final Query query = entityManager
					.createQuery(string);
			query.setParameter("uuid", uuid);

			final UserUuidEntity userUuid = (UserUuidEntity) query.getSingleResult();
			result = mapperService.mapTo(userUuid, UserUuid.class);
		} catch (Exception e) {
			return null;
		}
		return result;
	}

	
	@Override
	@TransactionAttribute
	public User getUserByEmail(String email) {
		final User result;
		try {
			String string = "select u from UserEntity u where u.email = :email";
			final Query query = entityManager
					.createQuery(string);
			query.setParameter("email", email);

			final UserEntity user = (UserEntity) query.getSingleResult();
			result = mapperService.mapTo(user, User.class);
		} catch (Exception e) {
			return null;
		}

		return result;
	}

	



}
