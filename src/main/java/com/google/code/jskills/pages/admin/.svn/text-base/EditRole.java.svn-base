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

public class EditRole extends AdministrativeTools{

	private static final long serialVersionUID = 8087290307835324301L;

	@Inject
	private transient RoleService roleService;
	
	private FeedbackPanel feedbackPanel;
	
	private Role selectedRole;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(EditRole.class);
	
	public EditRole() {
		LOG.info("EditRole page: default constructor");
		
	}
	
	public EditRole(Role selectedRole){
		LOG.info("EditRole page: constructor with selected Role");
		this.selectedRole = selectedRole;
	}
	
	@Override
	protected void onInitialize() {
		LOG.info("EditRole page: onInitialize");
		super.onInitialize();

		add(new Label("contentMessage", "Administrative Tools / Edit Role"));
		Form<String> editRoleForm = new Form<String>("editRoleForm");
		add(editRoleForm);
		
		/**
		 * Role name
		 */
		editRoleForm.add(new Label("roleNameLabel", "Name:"));
		TextField<String> nameField = new TextField<String>("roleNameField",
				new PropertyModel<String>(this.selectedRole, "name"));
		nameField.setRequired(true);
		nameField.setOutputMarkupId(true);
		editRoleForm.add(nameField);
		
		/**
		 * Role description
		 */
		editRoleForm.add(new Label("roleDescriptionLabel", "Description:"));
		TextField<String> descriptionField = new TextField<String>("roleDescriptionField",
				new PropertyModel<String>(this.selectedRole, "description"));
		descriptionField.setRequired(false);
		descriptionField.setOutputMarkupId(true);
		editRoleForm.add(descriptionField);
		
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
				roleService.updateRole(selectedRole);
				target.add(feedbackPanel);
				setResponsePage(RolesList.class);
			};
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		};
		editRoleForm.add(saveButton);
		
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
		editRoleForm.add(cancelButton);
		
		/**
		 * Pick permissions for role button
		 */
		AjaxButton pickPermisionsForRoleButton = new AjaxButton("pickPermissionsForRoleButton") {
			private static final long serialVersionUID = 3890131800821075652L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				LOG.info("pickPermissionsForRoleButton pressed in EditRole");
				target.add(feedbackPanel);
				Page pickRolePermissionsPage = new PickRolePermissions(selectedRole);
				setResponsePage(pickRolePermissionsPage);
			};		
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				target.add(feedbackPanel);
			}
		};
		editRoleForm.add(pickPermisionsForRoleButton);
	}
}
