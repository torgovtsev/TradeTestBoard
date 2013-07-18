package com.google.code.jskills.dal.providers.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.code.jskills.business.services.MapperService;
import com.google.code.jskills.dal.entities.PermissionEntity;
import com.google.code.jskills.dal.entities.QuestionEntity;
import com.google.code.jskills.dal.entities.RoleEntity;
import com.google.code.jskills.dal.entities.RolePermissionEntity;
import com.google.code.jskills.dal.entities.RolePermissionEntityId;
import com.google.code.jskills.dal.entities.Test2questionEntity;
import com.google.code.jskills.dal.entities.Test2questionEntityId;
import com.google.code.jskills.dal.entities.TestEntity;
import com.google.code.jskills.dal.providers.QuestionDataProvider;
import com.google.code.jskills.domain.Permission;
import com.google.code.jskills.domain.Question;
import com.google.code.jskills.domain.Role;
import com.google.code.jskills.domain.Test;
import com.google.code.jskills.utils.CollectionsUtils;

@Stateless
public class QuestionDataProviderImpl implements QuestionDataProvider {
	
	@Inject
	MapperService mapperService;
	
	@PersistenceContext(unitName = "jskillsDB")
	private EntityManager entityManager;
	
	@Override
	@TransactionAttribute
	public void create(Question question) {
		final QuestionEntity questionEntity = mapperService.mapTo(question, QuestionEntity.class);
		entityManager.persist(questionEntity);		
	}
	
	@Override
	@TransactionAttribute
	public void update(Question question) {
		final QuestionEntity questionEntity = mapperService.mapTo(question, QuestionEntity.class);
		entityManager.merge(questionEntity);
	}
	
	@Override
	@TransactionAttribute
	public void delete(Question question) {
		final QuestionEntity questionEntity = mapperService.mapTo(question, QuestionEntity.class);
		entityManager.remove(questionEntity);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Question> findAll() {
		List<Question> result = new ArrayList<Question>();
		
		final Query query = entityManager.createQuery("select q from QuestionEntity q");
		
		List<QuestionEntity> questions = CollectionsUtils.castList(query.getResultList());
		for(QuestionEntity dataEntity: questions){
			Question q = mapperService.mapTo(dataEntity, Question.class);
			result.add(q);
		}
		
		return result;
	}

	@Override
	public Set<Question> getQuestionForTest(Test selectedTest) {
		Integer roleId = selectedTest.getId();		
		StringBuilder sb = new StringBuilder();
		Set<Question> resultPermissions = new HashSet<Question>();
		
		/**
		 * Get Permissions for Role throw Role_Permission connection
		 */		
		sb.append("SELECT r FROM ");
		sb.append(TestEntity.class.getName());
		sb.append(" r JOIN FETCH r.test2questionEntities rp");
		sb.append(" JOIN FETCH rp.questionEntity ");
		sb.append(" WHERE r.id = :roleId");		
		String getRoleP = sb.toString();
		
		Query queryUR = entityManager.createQuery(getRoleP).setParameter("roleId", roleId);
		TestEntity role = null;
		Set<Test2questionEntity> rolePermissions;
		try{
			role = (TestEntity) queryUR.getSingleResult();
			rolePermissions = (Set<Test2questionEntity>)role.getTest2questionEntities();
		}
		catch(NoResultException e){
			rolePermissions = new HashSet<Test2questionEntity>();
		}
		
		
		for(Test2questionEntity rp: rolePermissions){
			Question businessEntity = mapperService.mapTo(rp.getQuestionEntity(), Question.class);
			resultPermissions.add(businessEntity);
		}			
		return resultPermissions;
	}
	
	
	@Override
	@TransactionAttribute
	public void updateQuestionForTest(Test role, List<Question> rolePermissions) {		
		TestEntity roleEntity = mapperService.mapTo(role, TestEntity.class);
		
		/**
		 * Removing old RolePermissionEntities
		 */
		Query query = entityManager.createQuery("select rp from " + Test2questionEntity.class.getName() 
				+ " rp where rp.testEntity = :roleEntityPar").setParameter("roleEntityPar", roleEntity);	
		List<Test2questionEntity> old;
		try{
			old = CollectionsUtils.castList(query.getResultList());
		}catch(Exception e){
			old = new ArrayList<Test2questionEntity>();
		}
		for(Test2questionEntity ur: old){
			entityManager.remove(ur);
		}
		
		/**
		 * Saving new RolePermissionEntities
		 */
		Set<Test2questionEntity> resultRolePermissionEntities = new HashSet<Test2questionEntity>();
		for(Question r: rolePermissions){
			QuestionEntity permissionEntity = mapperService.mapTo(r, QuestionEntity.class);
			Test2questionEntityId idEntity = new Test2questionEntityId(roleEntity.getId(), permissionEntity.getId());
		    Test2questionEntity rolePermissionEntity = new Test2questionEntity(idEntity, permissionEntity, roleEntity);
			entityManager.persist(rolePermissionEntity);
			resultRolePermissionEntities.add(rolePermissionEntity);
		}
		roleEntity.setTest2questionEntities(resultRolePermissionEntities);
		entityManager.merge(roleEntity);		
	}
	
}
