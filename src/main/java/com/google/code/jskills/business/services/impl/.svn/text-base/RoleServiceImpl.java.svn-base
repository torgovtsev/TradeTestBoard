package com.google.code.jskills.business.services.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.google.code.jskills.business.services.RoleService;
import com.google.code.jskills.dal.providers.RoleDataProvider;
import com.google.code.jskills.domain.Role;
import com.google.code.jskills.domain.User;

public class RoleServiceImpl implements RoleService{

	@Inject
	private transient RoleDataProvider roleDataProvider;
	
	@Override
	public List<Role> getAllRoles() {
		return roleDataProvider.getAllRoles();
	}

	@Override
	public Role getRoleById(Integer id) {
		return roleDataProvider.getRoleById(id);
	}

	@Override
	public Role getRoleByName(String name) {
		return roleDataProvider.getRoleByName(name);
	}

	@Override
	public void deleteRole(Role role) {
		roleDataProvider.deleteRole(role);
	}

	@Override
	public void updateRole(Role role) {
		roleDataProvider.updateRole(role);
		
	}

	@Override
	public void saveRole(Role role) {
		roleDataProvider.saveRole(role);
		
	}

	@Override
	public Integer getNumberOfRoles() {
		return roleDataProvider.getNumberOfRoles();
	}

	@Override
	public Set<Role> getRolesForUser(User user) {
		return roleDataProvider.getRolesForUser(user);
	}

	@Override
	public void updateRolesForUser(User user, List<Role> userRoles) {
		roleDataProvider.updateRolesForUser(user, userRoles);
	}

}
