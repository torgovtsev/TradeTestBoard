package com.google.code.jskills.pages.admin;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.google.code.jskills.domain.Permission;

public class PermissionActionPanel extends Panel{

	private static final long serialVersionUID = -5192810707216798537L;

	@SuppressWarnings("serial")
	public PermissionActionPanel(String id, final IModel<Permission> model) {
		super(id);
		final ModalWindow modalWindow = new ModalWindow("permissionPageEdit");
		modalWindow.setCookieName("PermissionPageEdit-1");
		modalWindow.setAutoSize(true);
		add(modalWindow);

		add(new AjaxLink<Permission>("editPermission", model) {			
			@Override
			public void onClick(AjaxRequestTarget target) {
				Permission selectedPermission = getModelObject();
				Page editPermissionPage = new EditPermission(selectedPermission);
				setResponsePage(editPermissionPage);				
			}
		});
	}
}