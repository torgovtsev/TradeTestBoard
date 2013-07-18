package com.google.code.jskills.dal.providers.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.code.jskills.business.services.MapperService;
import com.google.code.jskills.dal.entities.UserVerificationEntity;
import com.google.code.jskills.dal.providers.UserVerificationDataProvider;
import com.google.code.jskills.domain.Verification;

@Stateless
public class UserVerificationDataProviderImpl implements
		UserVerificationDataProvider {

	@PersistenceContext(unitName = "jskillsDB")
	private EntityManager entityManager;

	@Inject
	MapperService mapperService;

	/**
	 * save Verification in database
	 * 
	 * @param Verification verification
	 */
	@Override
	public void saveVerification(Verification verification) {
		final UserVerificationEntity dataEntity = mapperService.mapTo(
				verification, UserVerificationEntity.class);
		entityManager.persist(dataEntity);
	}

	/**
	 * find verification by ID
	 * 
	 * @param int id
	 * 
	 * @return Verification
	 */
	@Override
	public Verification findById(int id) {
		final Verification result;
		try {
			final Query query = entityManager
					.createQuery("select v from UserVerificationEntity v where v.id = :id");
			query.setParameter("id", id);

			final UserVerificationEntity verification = (UserVerificationEntity) query.getSingleResult();
			result = mapperService.mapTo(verification, Verification.class);
		} catch (Exception e) {
			return null;
		}
		return result;
	}
	
	/**
	 * delete verification
	 * 
	 * @param Verification verification
	 */
	@Override
	@TransactionAttribute
	public void deleteVerification(Verification verification) {
		final UserVerificationEntity dataEntity = mapperService.mapTo(
				verification, UserVerificationEntity.class);
		UserVerificationEntity target = entityManager.merge(dataEntity);
		entityManager.remove(target);
	}
}
