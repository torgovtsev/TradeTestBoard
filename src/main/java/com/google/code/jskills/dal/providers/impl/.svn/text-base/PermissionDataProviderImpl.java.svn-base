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
import com.google.code.jskills.dal.entities.RoleEntity;
import com.google.code.jskills.dal.entities.RolePermissionEntity;
import com.google.code.jskills.dal.entities.RolePermissionEntityId;
import com.google.code.jskills.dal.providers.PermissionDataProvider;
import com.google.code.jskills.domain.Permission;
import com.google.code.jskills.domain.Role;
import com.google.code.jskills.utils.CollectionsUtils;

@Stateless
public class PermissionDataProviderImpl implements PermissionDataProvider{

	@PersistenceContext(unitName = "jskillsDB")
	private EntityManager entityManager;
	
	@Inject
	private MapperService mapperService;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Permission> getAllPermissions() {
		final List<Permission> result = new ArrayList<Permission>();
		final Query query = entityManager
				.createQuery("select p from PermissionEntity p");
		final List<PermissionEntity> permissions = CollectionsUtils.castList(query.getResultList());
		
		for (PermissionEntity dataEntity : permissions) {
			Permission businessEntity = mapperService.mapTo(dataEntity,
					Permission.class);
			result.add(businessEntity);
		}
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Permission getPermissionById(Integer id) {
		PermissionEntity dataEntity = entityManager.find(PermissionEntity.class, id);
		Permission businessEntity = mapperService.mapTo(dataEntity, Permission.class);				
		return businessEntity;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Permission getPermissionByName(String permissionName) {
		final Query query = entityManager.createQuery("select p from PermissionEntity p where p.name = :permissionName");
		PermissionEntity dataEntity = (PermissionEntity) query.setParameter("permissionName", permissionName).getSingleResult();		
		Permission businessEntity = mapperService.mapTo(dataEntity, Permission.class);				
		return businessEntity;
	}

	@Override
	@TransactionAttribute
	public void savePermission(Permission permission) {
		final PermissionEntity dataEntity = mapperService.mapTo(permission, PermissionEntity.class);
		entityManager.persist(dataEntity);
	}

	@Override
	@TransactionAttribute
	public void updatePermission(Permission permission) {
		final PermissionEntity dataEntity = mapperService.mapTo(permission, PermissionEntity.class);
		entityManager.merge(dataEntity);
	}


	@Override
	@TransactionAttribute
	public void deletePermission(Permission Permission) {
		final PermissionEntity dataEntity = mapperService.mapTo(Permission, PermissionEntity.class);
		entityManager.remove(dataEntity);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Integer getNumberOfPermissions() {
		final Query query = entityManager.createQuery("select count(p) from PermissionEntity p");
		Integer num = new Integer(((Long) query.getSingleResult()).toString());						
		return num;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Set<Permission> getPermissionsForRole(Role selectedRole) {		
		Integer roleId = selectedRole.getId();		
		StringBuilder sb = new StringBuilder();
		Set<Permission> resultPermissions = new HashSet<Permission>();
		
		/**
		 * Get Permissions for Role throw Role_Permission connection
		 */		
		sb.append("SELECT r FROM ");
		sb.append(RoleEntity.class.getName());
		sb.append(" r JOIN FETCH r.rolePermissionEntities rp");
		sb.append(" JOIN FETCH rp.permissionEntity ");
		sb.append(" WHERE r.id = :roleId");		
		String getRoleP = sb.toString();
		
		Query queryUR = entityManager.createQuery(getRoleP).setParameter("roleId", roleId);
		RoleEntity role = null;
		Set<RolePermissionEntity> rolePermissions;
		try{
			role = (RoleEntity) queryUR.getSingleResult();
			rolePermissions = (Set<RolePermissionEntity>)role.getRolePermissionEntities();
		}
		catch(NoResultException e){
			rolePermissions = new HashSet<RolePermissionEntity>();
		}
		
		
		for(RolePermissionEntity rp: rolePermissions){
			Permission businessEntity = mapperService.mapTo(rp.getPermissionEntity(), Permission.class);
			resultPermissions.add(businessEntity);
		}			
		return resultPermissions;
	}

	@Override
	@TransactionAttribute
	public void updatePermissionsForRole(Role role, List<Permission> rolePermissions) {		
		RoleEntity roleEntity = mapperService.mapTo(role, RoleEntity.class);
		
		/**
		 * Removing old RolePermissionEntities
		 */
		Query query = entityManager.createQuery("select rp from " + RolePermissionEntity.class.getName() 
				+ " rp where rp.roleEntity = :roleEntityPar").setParameter("roleEntityPar", roleEntity);	
		List<RolePermissionEntity> old;
		try{
			old = CollectionsUtils.castList(query.getResultList());
		}catch(Exception e){
			old = new ArrayList<RolePermissionEntity>();
		}
		for(RolePermissionEntity ur: old){
			entityManager.remove(ur);
		}
		
		/**
		 * Saving new RolePermissionEntities
		 */
		Set<RolePermissionEntity> resultRolePermissionEntities = new HashSet<RolePermissionEntity>();
		for(Permission r: rolePermissions){
			PermissionEntity permissionEntity = mapperService.mapTo(r, PermissionEntity.class);
			RolePermissionEntityId idEntity = new RolePermissionEntityId(roleEntity.getId(), permissionEntity.getId());
			RolePermissionEntity rolePermissionEntity = new RolePermissionEntity(idEntity, permissionEntity, roleEntity);
			entityManager.persist(rolePermissionEntity);
			resultRolePermissionEntities.add(rolePermissionEntity);
		}
		roleEntity.setRolePermissionEntities(resultRolePermissionEntities);
		entityManager.merge(roleEntity);		
	}

}
