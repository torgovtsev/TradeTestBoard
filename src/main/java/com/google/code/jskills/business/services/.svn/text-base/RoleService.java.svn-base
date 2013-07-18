package com.google.code.jskills.business.services;

import java.util.List;
import java.util.Set;

import com.google.code.jskills.domain.Role;
import com.google.code.jskills.domain.User;

public interface RoleService {
	
	List<Role> getAllRoles();
	
	Role getRoleById(Integer id);
	
	Role getRoleByName(String name);
	
	void deleteRole(Role role);
	
	void updateRole(Role role);
	
	void saveRole(Role role);
	
	Integer getNumberOfRoles();
	
	Set<Role> getRolesForUser(User user);
	
	void updateRolesForUser(User user, List<Role> userRoles);
}
