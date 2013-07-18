package com.google.code.jskills.dal.providers;

import java.util.List;
import java.util.Set;

import com.google.code.jskills.domain.Role;
import com.google.code.jskills.domain.User;

public interface RoleDataProvider {
	
	List<Role> getAllRoles();
	
	Role getRoleById(Integer  id);
	
	Role getRoleByName(String name);
	
	void saveRole(Role role);
	
	void updateRole(Role role);
	
	void deleteRole(Role role);
	
	Integer getNumberOfRoles();
	
	Set<Role> getRolesForUser(User user);
	
	void updateRolesForUser(User user, List<Role> userRoles);

}
