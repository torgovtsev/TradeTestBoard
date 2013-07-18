package com.google.code.jskills.business.services.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.google.code.jskills.business.services.PermissionService;
import com.google.code.jskills.dal.providers.PermissionDataProvider;
import com.google.code.jskills.domain.Permission;
import com.google.code.jskills.domain.Role;

public class PermissionServiceImpl implements PermissionService{

	@Inject
	private transient PermissionDataProvider PermissionDataProvider;
	
	@Override
	public List<Permission> getAllPermissions() {
		return PermissionDataProvider.getAllPermissions();
	}

	@Override
	public Permission getPermissionById(Integer id) {
		return PermissionDataProvider.getPermissionById(id);
	}

	@Override
	public Permission getPermissionByName(String name) {
		return PermissionDataProvider.getPermissionByName(name);
	}

	@Override
	public void deletePermission(Permission permission) {
		PermissionDataProvider.deletePermission(permission);
	}

	@Override
	public void updatePermission(Permission permission) {
		PermissionDataProvider.updatePermission(permission);
		
	}

	@Override
	public void savePermission(Permission permission) {
		PermissionDataProvider.savePermission(permission);
		
	}

	@Override
	public Integer getNumberOfPermissions() {
		return PermissionDataProvider.getNumberOfPermissions();
	}

	@Override
	public Set<Permission> getPermissionsForRole(Role role) {
		return PermissionDataProvider.getPermissionsForRole(role);
	}

	@Override
	public void updatePermissionsForRole(Role role, List<Permission> rolePermissions) {
		PermissionDataProvider.updatePermissionsForRole(role, rolePermissions);
	}

}
