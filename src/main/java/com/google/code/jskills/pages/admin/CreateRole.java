package com.google.code.jskills.pages.admin;

import javax.inject.Inject;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.RoleService;
import com.google.code.jskills.domain.Role;
import com.google.code.jskills.pages.AdministrativeTools;

public class CreateRole extends AdministrativeTools{

	private static final long serialVersionUID = -5447103845852830836L;
	
	@Inject
	private transient RoleService roleService;
	
	private FeedbackPanel feedbackPanel;
	
	private Role selectedRole;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(CreateRole.class);
	
	public CreateRole() {
		LOG.info("EditRole page: default constructor");
		selectedRole = new Role();
	}
	
	@Override
	protected void onInitialize() {
		LOG.info("EditRole page: onInitialize");
		super.onInitialize();

		add(new Label("contentMessage", "Administrative Tools / Create Role"));
		Form<String> createRoleForm = new Form<String>("createRoleForm");
		add(createRoleForm);
		
		/**
		 * Role name
		 */
		createRoleForm.add(new Label("roleNameLabel", "Name:"));
		TextField<String> nameField = new TextField<String>("roleNameField",
				new PropertyModel<String>(this.selectedRole, "name"));
		nameField.setRequired(true);
		nameField.setOutputMarkupId(true);
		createRoleForm.add(nameField);
		
		/**
		 * Role description
		 */
		createRoleForm.add(new Label("roleDescriptionLabel", "Description:"));
		TextField<String> descriptionField = new TextField<String>("roleDescriptionField",
				new PropertyModel<String>(this.selectedRole, "description"));
		descriptionField.setRequired(false);
		descriptionField.setOutputMarkupId(true);
		createRoleForm.add(descriptionField);
		
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
				roleService.saveRole(selectedRole);
				target.add(feedbackPanel);
				setResponsePage(RolesList.class);
			};
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		};
		createRoleForm.add(saveButton);
		
		/**
		 * Cancel button
		 */
		AjaxButton cancelButton = new AjaxButton("cancelButton") {
			private static final long serialVersionUID = 902652892993225517L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
				setResponsePage(RolesList.class);
			};
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				setResponsePage(RolesList.class);
			}
		};
		createRoleForm.add(cancelButton);
		
		/**
		 * Pick Permissions for role button
		 */
		AjaxButton pickPermissionsForRoleButton = new AjaxButton("pickPermissionsForRoleButton") {
			private static final long serialVersionUID = 3890131800821075652L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				LOG.info("pickPermissionsForRoleButton pressed in EditRole");
				roleService.saveRole(selectedRole);
				LOG.info(selectedRole.getName());
				Role role = roleService.getRoleByName(selectedRole.getName());
				Page pickRolePermissionsPage = new PickRolePermissions(role);
				target.add(feedbackPanel);
				setResponsePage(pickRolePermissionsPage);
			};		
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				target.add(feedbackPanel);
			}
		};
		createRoleForm.add(pickPermissionsForRoleButton);
	}
}
