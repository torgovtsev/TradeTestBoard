package com.google.code.jskills.business.services;

import java.util.List;
import java.util.Set;

import com.google.code.jskills.domain.Permission;
import com.google.code.jskills.domain.Role;

public interface PermissionService {

	List<Permission> getAllPermissions();
	
	Permission getPermissionById(Integer id);
	
	Permission getPermissionByName(String name);
	
	void deletePermission(Permission permission);
	
	void updatePermission(Permission permission);
	
	void savePermission(Permission permission);
	
	Integer getNumberOfPermissions();
	
	Set<Permission> getPermissionsForRole(Role role);
	
	void updatePermissionsForRole(Role role, List<Permission> rolePermissions);
}
