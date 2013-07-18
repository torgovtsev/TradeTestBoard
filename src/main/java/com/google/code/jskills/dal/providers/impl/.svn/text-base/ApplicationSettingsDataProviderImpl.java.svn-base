package com.google.code.jskills.dal.providers.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.code.jskills.business.services.MapperService;
import com.google.code.jskills.dal.entities.ApplicationSettingsEntity;
import com.google.code.jskills.dal.entities.UserEntity;
import com.google.code.jskills.dal.providers.ApplicationSettingsDataProvider;
import com.google.code.jskills.domain.Settings;
import com.google.code.jskills.domain.User;

public class ApplicationSettingsDataProviderImpl implements
		ApplicationSettingsDataProvider {

	@PersistenceContext(unitName = "jskillsDB")
	private EntityManager entityManager;

	@Inject
	private MapperService mapperService;

	/**
	 * get application settings by id
	 * 
	 * @param int id
	 * 
	 * @return Settings
	 */
	public Settings getSettingsById(int id) {
		final Settings result;
			final Query query = entityManager
					.createQuery("select s from ApplicationSettingsEntity s where s.id = :id");
			query.setParameter("id", id);

			final ApplicationSettingsEntity dataProvider = (ApplicationSettingsEntity) query
					.getSingleResult();
			result = mapperService.mapTo(dataProvider, Settings.class);

		return result;
	}

}
