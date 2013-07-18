package com.google.code.jskills.pages;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import com.google.code.jskills.pages.admin.PermissionsList;
import com.google.code.jskills.pages.admin.UsersList;
import com.google.code.jskills.pages.admin.RolesList;
import com.google.code.jskills.pages.master.MasterPage;

@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
// @ShiroSecurityConstraint(constraint=ShiroConstraint.HasRole,
// value="SuperAdministrator")
public class AdministrativeTools extends MasterPage {
	private static final long serialVersionUID = -6014806211455015586L;
	public AdministrativeTools() {

		add(new AjaxLink<String>("adminUsersListPage") {
			private static final long serialVersionUID = -7533635526125609450L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(UsersList.class);
			}
		});

		add(new AjaxLink<String>("rolesListPage") {
			private static final long serialVersionUID = -7533635526125609450L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(RolesList.class);
			}
		});

		add(new AjaxLink<String>("permissionsListPage") {
			private static final long serialVersionUID = -7533635526125609450L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(PermissionsList.class);
			}
		});
	}
}
