package com.google.code.jskills.pages.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.model.util.ListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.PermissionService;
import com.google.code.jskills.domain.Permission;
import com.google.code.jskills.domain.Role;
import com.google.code.jskills.pages.AdministrativeTools;

public class PickRolePermissions extends AdministrativeTools{

	private static final long serialVersionUID = -6939324705816357615L;

	@Inject
	private transient PermissionService permissionService;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(PickRolePermissions.class);
	
	private FeedbackPanel feedbackPanel;
	private Role selectedRole;
	private List <Permission> rolePermissionsList;
	
	public PickRolePermissions(){
		
	}
	
	public PickRolePermissions(Role selectedRole){
		this.selectedRole = selectedRole;
	}
	
	@Override
	protected void onInitialize() {		
		LOG.info("EditPermission page: onInitialize");
		super.onInitialize();
		
		Form<String> pickListForm = new Form<String>("pickRolePermissionsForm");
		add(pickListForm);
		pickListForm.add(new Label("contentMessage", "Administrative Tools / Pick Role's Permissions"));
		
		/**
		 * Palette(PickList) for Role's Permissions
		 */
		List<Permission> allPermissions = permissionService.getAllPermissions();
		IChoiceRenderer<Permission> renderer = new ChoiceRenderer<Permission>("name", "name");
		Set<Permission> rolePermissions = permissionService.getPermissionsForRole(selectedRole);
		rolePermissionsList = new ArrayList<Permission>(rolePermissions);

		final Palette<Permission> palette = new Palette<Permission>("pickRolePermissionsPalette",
				new ListModel<Permission>(rolePermissionsList),
				new CollectionModel<Permission>(allPermissions),renderer, 10, false);
		pickListForm.add(palette);
		
		/**
		 * Feedback panel
		 */
		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
		/**
		 * Save button
		 */
		AjaxButton saveButton = new AjaxButton("saveButton") {
			private static final long serialVersionUID = 1L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				permissionService.updatePermissionsForRole(selectedRole, rolePermissionsList);
				setResponsePage(RolesList.class);
			};
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		};
		pickListForm.add(saveButton);
		
		/**
		 * Cancel button
		 */
		AjaxButton cancelButton = new AjaxButton("cancelButton") {
			private static final long serialVersionUID = 902652892993225517L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
				Page page = new EditRole(selectedRole);
				setResponsePage(page);
			};
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
				Page page = new EditRole(selectedRole);
				setResponsePage(page);
			}
		};
		pickListForm.add(cancelButton);
	}
	
	public Role getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}
}
