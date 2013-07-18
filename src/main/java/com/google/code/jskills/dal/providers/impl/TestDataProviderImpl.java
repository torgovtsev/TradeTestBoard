package com.google.code.jskills.dal.providers.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.cglib.core.CollectionUtils;

import com.google.code.jskills.business.services.MapperService;
import com.google.code.jskills.dal.entities.TestEntity;
import com.google.code.jskills.dal.entities.UserEntity;
import com.google.code.jskills.dal.entities.UserUuidEntity;
import com.google.code.jskills.dal.providers.TestDataProvider;
import com.google.code.jskills.domain.Test;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.domain.UserUuid;
import com.google.code.jskills.utils.CollectionsUtils;

@Stateless
public class TestDataProviderImpl implements TestDataProvider{

	@Inject
	MapperService mapperService;
	
	@PersistenceContext(unitName = "jskillsDB")
	EntityManager entityManager;
	
	@Override
	@TransactionAttribute
	public void create(Test test){
		final TestEntity dataEntity = mapperService.mapTo(test, TestEntity.class);
		entityManager.persist(dataEntity);
		
	}

	@Override
	@TransactionAttribute
	public void update(Test test) {
		final TestEntity dataEntity = mapperService.mapTo(test, TestEntity.class);
		entityManager.merge(dataEntity);
	}

	@Override
	@TransactionAttribute
	public void delete(Test test) {
		final TestEntity dataEntity = mapperService.mapTo(test, TestEntity.class);
		entityManager.remove(dataEntity);
	}

	@Override
	@TransactionAttribute
	public List<Test> findAll() {
		final List<Test> result = new ArrayList<Test>();
	
		final Query query = entityManager
					.createQuery("select t from TestEntity t");
			

		final List<TestEntity> tests= CollectionsUtils.castList(query.getResultList());
		for (TestEntity dataEntity : tests) {
			Test businessEntity = mapperService.mapTo(dataEntity, Test.class);
			result.add(businessEntity);
		}
		
		return result;
	}

}
