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
import com.google.code.jskills.dal.entities.CompetencesEntity;
import com.google.code.jskills.dal.entities.LevelEntity;
import com.google.code.jskills.dal.providers.CompetencesDataProvider;
import com.google.code.jskills.domain.Competences;
import com.google.code.jskills.domain.Level;
import com.google.code.jskills.utils.CollectionsUtils;

@Stateless
public class CompetencesDataProviderImpl implements CompetencesDataProvider{

	@PersistenceContext(unitName = "jskillsDB")
	private EntityManager entityManager;
	
	@Inject
	private MapperService mapperService;
	
	@Override
	public Competences getCompetencesById(int id) {
		final Competences result;
		final Query query = entityManager
				.createQuery("select c from CompetencesEntity c where c.id = :id");
		query.setParameter("id", id);

		final CompetencesEntity dataProvider = (CompetencesEntity) query
				.getSingleResult();
		result = mapperService.mapTo(dataProvider, Competences.class);

	return result;
	}

	@Override
	public List<Level> getLevelByLevelType(int levelType) {
		final List<Level> result = new ArrayList<Level>();
		final Query query = entityManager.createQuery("select l from LevelEntity l where l.levelType = :levelType");
		query.setParameter("levelType", levelType);

		final List<LevelEntity> level = CollectionsUtils.castList(query.getResultList());
		for (LevelEntity dataEntity : level) {
			Level businessEntity = mapperService.mapTo(dataEntity, Level.class);
			result.add(businessEntity);
		}
		
		return result;
	}

	@Override
	public List<Competences> getCompetencesByParentId(int parentId) {
		final List<Competences> result = new ArrayList<Competences>();
		final Query query = entityManager.createQuery("from CompetencesEntity  where parent_id = :parentId");
		query.setParameter("parentId", parentId);

		final List<CompetencesEntity> competences = CollectionsUtils.castList(query.getResultList());
		for (CompetencesEntity dataEntity : competences) {
			Competences businessEntity = mapperService.mapTo(dataEntity, Competences.class);
			result.add(businessEntity);
		}
		
		return result;
	}
	
	@Override
	public List<Competences> getRootCompetences() {
		final List<Competences> result = new ArrayList<Competences>();
		final Query query = entityManager.createQuery("from CompetencesEntity  where parent_id is null");

		final List<CompetencesEntity> competences = CollectionsUtils.castList(query.getResultList());
		for (CompetencesEntity dataEntity : competences) {
			Competences businessEntity = mapperService.mapTo(dataEntity, Competences.class);
			result.add(businessEntity);
		}
		
		return result;
	}
	
	@Override
	@TransactionAttribute
	public void saveCompetence(Competences competences) {
		final CompetencesEntity dataEntity = mapperService.mapTo(competences, CompetencesEntity.class);
		entityManager.persist(dataEntity);
	}
}
