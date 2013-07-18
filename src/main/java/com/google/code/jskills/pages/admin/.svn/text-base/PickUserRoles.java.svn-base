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

import com.google.code.jskills.business.services.RoleService;
import com.google.code.jskills.domain.Role;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.pages.AdministrativeTools;

public class PickUserRoles extends AdministrativeTools{

	private static final long serialVersionUID = -6939324705816357615L;

	@Inject
	private transient RoleService roleService;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(PickUserRoles.class);
	
	private FeedbackPanel feedbackPanel;
	private User selectedUser;
	private List <Role> userRolesList;
	
	public PickUserRoles(){
		
	}
	
	public PickUserRoles(User selectedUser){
		this.selectedUser = selectedUser;
	}
	
	@Override
	protected void onInitialize() {		
		LOG.info("EditRole page: onInitialize");
		super.onInitialize();
		
		Form<String> pickListForm = new Form<String>("pickUserRolesForm");
		add(pickListForm);
		pickListForm.add(new Label("contentMessage", "Administrative Tools / Pick User's Roles"));
		
		/**
		 * Palette(PickList) for user's roles
		 */
		List<Role> allRoles = roleService.getAllRoles();
		IChoiceRenderer<Role> renderer = new ChoiceRenderer<Role>("name", "name");
		Set<Role> userRoles = roleService.getRolesForUser(selectedUser);
		userRolesList = new ArrayList<Role>(userRoles);

		final Palette<Role> palette = new Palette<Role>("pickUserRolesPalette",
				new ListModel<Role>(userRolesList),
				new CollectionModel<Role>(allRoles),renderer, 10, false);
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
				roleService.updateRolesForUser(selectedUser, userRolesList);
				setResponsePage(UsersList.class);
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
				Page page = new EditUser(selectedUser);
				setResponsePage(page);
			};
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
				Page page = new EditUser(selectedUser);
				setResponsePage(page);
			}
		};
		pickListForm.add(cancelButton);
	}
	
	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
}
