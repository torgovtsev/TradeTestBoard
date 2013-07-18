package com.google.code.jskills.pages.profile;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.google.code.jskills.business.services.ProfileService;
import com.google.code.jskills.domain.Profile;
import com.google.code.jskills.pages.master.MasterPage;

public class EditProfile extends MasterPage {
	private static final long serialVersionUID = 1L;

	@Inject
	private transient ProfileService profileService;

	private FeedbackPanel feedbackPanel;
	private Profile selectedProfile;

	public Profile getSelectedProfile() {
		return selectedProfile;
	}

	public void setSelectedProfile(Profile selectedProfile) {
		this.selectedProfile = selectedProfile;
	}

	public EditProfile() {
	}

	public EditProfile(PageParameters parameters) {

		Integer id = new Integer(parameters.get("profileId").toString());
		selectedProfile = profileService.findProfileById(id);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Label("message", " "));

		Form<String> editProfileForm = new Form<String>("editProfileInfo");
		add(editProfileForm);

		/**
		 * Name
		 */

		editProfileForm.add(new Label("labelName", "Name:"));
		TextField<String> fieldName = new TextField<String>("inputName",
				new PropertyModel<String>(this.selectedProfile, "name"));
		fieldName.setRequired(true);
		fieldName.setOutputMarkupId(true);
		editProfileForm.add(fieldName);

		/**
		 * Description
		 */

		editProfileForm.add(new Label("labelDescription", "Description:"));
		TextField<String> fieldDescription = new TextField<String>(
				"inputDescription", new PropertyModel<String>(
						this.selectedProfile, "description"));
		fieldDescription.setRequired(true);
		fieldDescription.setOutputMarkupId(true);
		editProfileForm.add(fieldDescription);

		/**
		 * Feedback panel
		 */

		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		/**
		 * Button Save
		 */

		AjaxButton saveButton = new AjaxButton("saveButton") {
			private static final long serialVersionUID = 6444026956354436217L;

			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				profileService.updateProfile(selectedProfile);
				target.add(feedbackPanel);
				setResponsePage(ProfilesList.class);
			};

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}

		};
		editProfileForm.add(saveButton);

		/**
		 * ButtonCancel
		 */

		AjaxButton cancelButton = new AjaxButton("cancelButton") {
			private static final long serialVersionUID = 6252514382674810306L;

			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
				setResponsePage(ProfilesList.class);
			};

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				setResponsePage(ProfilesList.class);
			}
		};

		editProfileForm.add(cancelButton);
	}
}
