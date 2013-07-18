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
import com.google.code.jskills.dal.entities.ProfileEntity;
import com.google.code.jskills.dal.providers.ProfilesDataProvider;
import com.google.code.jskills.domain.Profile;
import com.google.code.jskills.utils.CollectionsUtils;

@Stateless
public class ProfilesDataProviderImpl implements ProfilesDataProvider {

	@PersistenceContext(unitName = "jskillsDB")
	private EntityManager entityManager;

	@Inject
	private MapperService mapperService;

	@Override
	@TransactionAttribute
	public void saveProfile(Profile profile) {
		final ProfileEntity dataEntity = mapperService.mapTo(profile,
				ProfileEntity.class);
		entityManager.persist(dataEntity);

	}

	@Override
	public void updateProfile(Profile profile) {
		final ProfileEntity dataEntity = mapperService.mapTo(profile,
				ProfileEntity.class);
		entityManager.merge(dataEntity);

	}

	@Override
	@TransactionAttribute
	public void deleteProfile(Profile profile) {
		final ProfileEntity dataEntity = mapperService.mapTo(profile,
				ProfileEntity.class);
		entityManager.remove(entityManager.merge(dataEntity));

	}

	@Override
	@TransactionAttribute
	public Profile getProfileByName(String name) {
		final Profile result;
		try {
			final Query query = entityManager
					.createQuery("select p from ProfileEntity p where p.name = :name");
			query.setParameter("name", name);

			final ProfileEntity profile = (ProfileEntity) query.getSingleResult();
			result = mapperService.mapTo(profile, Profile.class);
		} catch (Exception e) {
			return null;
		}

		return result;
	}

	@Override
	public Profile getProfileByID(int id) {
		final Profile result;
		try {
			final Query query = entityManager
					.createQuery("select p from ProfileEntity p where p.id = :id");
			query.setParameter("id", id);

			final ProfileEntity profile = (ProfileEntity) query
					.getSingleResult();
			result = mapperService.mapTo(profile, Profile.class);
		} catch (Exception e) {
			return null;
		}

		return result;
	}

	@Override
	public Profile getProfileByID(Integer id) {
		final Profile result;
		try {
			final Query query = entityManager
					.createQuery("select p from ProfileEntity p where p.id = :id");
			query.setParameter("id", id);

			final ProfileEntity profile = (ProfileEntity) query
					.getSingleResult();
			result = mapperService.mapTo(profile, Profile.class);
		} catch (Exception e) {
			return null;
		}

		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Profile> getAllProfiles() {
		final List<Profile> result = new ArrayList<Profile>();
		final Query query = entityManager
				.createQuery("select p from ProfileEntity p");

		final List<ProfileEntity> profiles = CollectionsUtils.castList(query
				.getResultList());
		for (ProfileEntity dataEntity : profiles) {
			Profile businessEntity = mapperService.mapTo(dataEntity,
					Profile.class);
			result.add(businessEntity);
		}

		return result;
	}

}
