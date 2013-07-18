package com.google.code.jskills.dal.providers;

import java.util.List;
import java.util.Set;

import com.google.code.jskills.domain.Permission;
import com.google.code.jskills.domain.Role;

public interface PermissionDataProvider {

	List<Permission> getAllPermissions();
	
	Permission getPermissionById(Integer  id);
	
	Permission getPermissionByName(String name);
	
	void savePermission(Permission permission);
	
	void updatePermission(Permission permission);
	
	void deletePermission(Permission permission);
	
	Integer getNumberOfPermissions();
	
	Set<Permission> getPermissionsForRole(Role role);
	
	void updatePermissionsForRole(Role role, List<Permission> rolePermissions);
}
