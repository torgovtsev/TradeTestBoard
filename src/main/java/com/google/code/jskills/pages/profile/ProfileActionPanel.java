package com.google.code.jskills.pages.profile;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.google.code.jskills.domain.Profile;

public class ProfileActionPanel extends Panel{
	private static final long serialVersionUID = 1L;

	private Profile selectedProfile;

	public ProfileActionPanel(String id, final IModel<Profile> model) {

		super(id);

		/*final ModalWindow modalWindow = new ModalWindow("profilePageEdit");
		modalWindow.setCookieName("profilePageEdit-1");
		modalWindow.setAutoSize(true);

		add(modalWindow);*/

		add(new AjaxLink<Profile>("editProfile", model) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				selectedProfile = getModelObject();
				PageParameters parameters = new PageParameters();
				parameters.add("profileId", selectedProfile.getId());
				setResponsePage(EditProfile.class, parameters);
			}
		});
	}

}
