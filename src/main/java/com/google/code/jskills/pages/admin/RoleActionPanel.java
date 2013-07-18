package com.google.code.jskills.pages.admin;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.google.code.jskills.domain.Role;

public class RoleActionPanel extends Panel{

	private static final long serialVersionUID = -5192810707216798537L;

	@SuppressWarnings("serial")
	public RoleActionPanel(String id, final IModel<Role> model) {
		super(id);
		final ModalWindow modalWindow = new ModalWindow("rolePageEdit");
		modalWindow.setCookieName("rolePageEdit-1");
		modalWindow.setAutoSize(true);
		add(modalWindow);

		add(new AjaxLink<Role>("editRole", model) {
			
			@Override
			public void onClick(AjaxRequestTarget target) {
				Role selectedRole = getModelObject();
				Page editRolePage = new EditRole(selectedRole);
				setResponsePage(editRolePage);				
			}
		});
	}
}