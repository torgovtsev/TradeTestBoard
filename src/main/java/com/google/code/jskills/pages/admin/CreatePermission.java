package com.google.code.jskills.pages.admin;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.PermissionService;
import com.google.code.jskills.domain.Permission;
import com.google.code.jskills.pages.AdministrativeTools;

public class CreatePermission extends AdministrativeTools{

	private static final long serialVersionUID = -5447103845852830836L;
	
	@Inject
	private transient PermissionService permissionService;
	
	private FeedbackPanel feedbackPanel;
	
	private Permission selectedPermission;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(CreatePermission.class);
	
	public CreatePermission() {
		LOG.info("EditPermission page: default constructor");
		selectedPermission = new Permission();
	}
	
	@Override
	protected void onInitialize() {
		LOG.info("EditPermission page: onInitialize");
		super.onInitialize();

		add(new Label("contentMessage", "Administrative Tools / Create Permission"));
		Form<String> createPermissionForm = new Form<String>("createPermissionForm");
		add(createPermissionForm);
		
		/**
		 * Permission name
		 */
		createPermissionForm.add(new Label("permissionNameLabel", "Name:"));
		TextField<String> nameField = new TextField<String>("permissionNameField",
				new PropertyModel<String>(this.selectedPermission, "name"));
		nameField.setRequired(true);
		nameField.setOutputMarkupId(true);
		createPermissionForm.add(nameField);
		
		/**
		 * Permission description
		 */
		createPermissionForm.add(new Label("permissionDescriptionLabel", "Description:"));
		TextField<String> descriptionField = new TextField<String>("permissionDescriptionField",
				new PropertyModel<String>(this.selectedPermission, "description"));
		descriptionField.setRequired(false);
		descriptionField.setOutputMarkupId(true);
		createPermissionForm.add(descriptionField);
		
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
				permissionService.savePermission(selectedPermission);
				target.add(feedbackPanel);
				setResponsePage(PermissionsList.class);
			};
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		};
		createPermissionForm.add(saveButton);
		
		/**
		 * Cancel button
		 */
		AjaxButton cancelButton = new AjaxButton("cancelButton") {
			private static final long serialVersionUID = 902652892993225517L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
				setResponsePage(PermissionsList.class);
			};
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				setResponsePage(PermissionsList.class);
			}
		};
		createPermissionForm.add(cancelButton);
	}
}
