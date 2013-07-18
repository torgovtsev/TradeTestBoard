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
import com.google.code.jskills.dal.entities.GroupRoleEntity;
import com.google.code.jskills.dal.entities.RoleEntity;
import com.google.code.jskills.dal.entities.UserEntity;
import com.google.code.jskills.dal.entities.UserGroupEntity;
import com.google.code.jskills.dal.entities.UserRoleEntity;
import com.google.code.jskills.dal.entities.UserRoleEntityId;
import com.google.code.jskills.dal.providers.RoleDataProvider;
import com.google.code.jskills.domain.Role;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.utils.CollectionsUtils;

@Stateless
public class RoleDataProviderImpl implements RoleDataProvider {

	@PersistenceContext(unitName = "jskillsDB")
	private EntityManager entityManager;
	
	@Inject
	private MapperService mapperService;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Role> getAllRoles() {
		final List<Role> result = new ArrayList<Role>();
		final Query query = entityManager.createQuery("select r from RoleEntity r");

		final List<RoleEntity> roles = CollectionsUtils.castList(query.getResultList());
		
		for (RoleEntity dataEntity : roles) {
			Role businessEntity = mapperService.mapTo(dataEntity, Role.class);
			result.add(businessEntity);
		}		
		return result;
	}

	/**
	 * Check and test!!!
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Role getRoleById(Integer id) {
		RoleEntity dataEntity = entityManager.find(RoleEntity.class, id);
		Role businessEntity = mapperService.mapTo(dataEntity, Role.class);				
		return businessEntity;
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Role getRoleByName(String roleName) {
		final Query query = entityManager.createQuery("select r from RoleEntity r where r.name = :roleName");
		RoleEntity dataEntity = (RoleEntity) query.setParameter("roleName", roleName).getSingleResult();		
		Role businessEntity = mapperService.mapTo(dataEntity, Role.class);				
		return businessEntity;
	}


	@Override
	@TransactionAttribute
	public void saveRole(Role role) {
		final RoleEntity dataEntity = mapperService.mapTo(role, RoleEntity.class);
		entityManager.persist(dataEntity);
	}


	@Override
	@TransactionAttribute
	public void updateRole(Role role) {
		final RoleEntity dataEntity = mapperService.mapTo(role, RoleEntity.class);
		entityManager.merge(dataEntity);
	}


	@Override
	@TransactionAttribute
	public void deleteRole(Role role) {
		final RoleEntity dataEntity = mapperService.mapTo(role, RoleEntity.class);
		entityManager.remove(dataEntity);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Integer getNumberOfRoles() {
		final Query query = entityManager.createQuery("select count(r) from RoleEntity r");
		Integer num = new Integer(((Long) query.getSingleResult()).toString());						
		return num;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Set<Role> getRolesForUser(User selectedUser) {		
		String email = selectedUser.getEmail();		
		StringBuilder sb = new StringBuilder();
		Set<Role> resultRoles = new HashSet<Role>();
		
		/**
		 * Get roles throw user_role connection
		 */		
		sb.append("SELECT u FROM ");
		sb.append(UserEntity.class.getName());
		sb.append(" u JOIN FETCH u.userRoleEntities ur");
		sb.append(" JOIN FETCH ur.roleEntity ");
		sb.append(" WHERE u.email = :email");		
		String getUserUR = sb.toString();
		
		Query queryUR = entityManager.createQuery(getUserUR).setParameter(
				"email", email);
		UserEntity user;
		Set<UserRoleEntity> userRoles;
		try {
			user = (UserEntity) queryUR.getSingleResult();
			userRoles = (Set<UserRoleEntity>) user.getUserRoleEntities();
		} catch (NoResultException e) {
			userRoles = new HashSet<UserRoleEntity>();
		}
		for (UserRoleEntity ur : userRoles) {
			Role businessEntity = mapperService.mapTo(ur.getRoleEntity(),
					Role.class);
			resultRoles.add(businessEntity);
		}	
		
		
		/**
		 * Is there any need to do it here????????????????????????????!
		 * Get roles throw user_group -> group_role connection
		 */
		sb = new StringBuilder();
		sb.append("SELECT u FROM ");
		sb.append(UserEntity.class.getName());
		sb.append(" u JOIN FETCH u.userGroupEntities ug");
		sb.append(" JOIN FETCH ug.groupEntity g");
		sb.append(" JOIN FETCH g.groupRoleEntities gr");
		sb.append(" JOIN FETCH gr.roleEntity");
		sb.append(" WHERE u.email = :email");
		String getUserUGR = sb.toString();
		
		Query queryUGR = entityManager.createQuery(getUserUGR).setParameter("email", email);
		Set<UserGroupEntity> userGroups;
		try {
			user = (UserEntity) queryUGR.getSingleResult();
			userGroups = (Set<UserGroupEntity>) user.getUserGroupEntities();
		} catch (NoResultException e) {
			userGroups = new HashSet<UserGroupEntity>();
		}
		Set<GroupRoleEntity> userGroupRoles;

		for (UserGroupEntity ug : userGroups) {
			userGroupRoles = ug.getGroupEntity().getGroupRoleEntities();
			for (GroupRoleEntity gr : userGroupRoles) {
				Role businessEntity = mapperService.mapTo(gr.getRoleEntity(),
						Role.class);
				resultRoles.add(businessEntity);
			}
		}

		return resultRoles;
	}

	@Override
	@TransactionAttribute
	public void updateRolesForUser(User user, List<Role> userRoles) {		
		UserEntity userEntity = mapperService.mapTo(user, UserEntity.class);
		
		/**
		 * Removing old UserRoleEntities
		 */
		Query query = entityManager.createQuery("select ur from " + UserRoleEntity.class.getName() 
				+ " ur where ur.userEntity = :userEntityPar").setParameter("userEntityPar", userEntity);
		List<UserRoleEntity> old = CollectionsUtils.castList(query.getResultList());		
		for(UserRoleEntity ur: old){
			entityManager.remove(ur);
		}
		
		/**
		 * Saving new UserRoleEntities
		 */
		Set<UserRoleEntity> resultUserRoleEntities = new HashSet<UserRoleEntity>();
		for(Role r: userRoles){
			RoleEntity roleEntity = mapperService.mapTo(r, RoleEntity.class);
			UserRoleEntityId idEntity = new UserRoleEntityId(userEntity.getId(), roleEntity.getId());
			UserRoleEntity userRoleEntity = new UserRoleEntity(idEntity, userEntity, roleEntity);
			entityManager.persist(userRoleEntity);
			resultUserRoleEntities.add(userRoleEntity);
		}
		userEntity.setUserRoleEntities(resultUserRoleEntities);
		entityManager.merge(userEntity);		
	}
}
