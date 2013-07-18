package com.google.code.jskills.pages.profile;

import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.hibernate.event.RefreshEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.code.jskills.business.services.ProfileService;
import com.google.code.jskills.domain.Profile;

public class CreateProfile extends WebPage {

	private static final long serialVersionUID = 7815007659391596623L;

	private static final Logger LOG = LoggerFactory
			.getLogger(CreateProfile.class);

	@Inject
	private transient ProfileService profileService;

	private Profile createdProfile;
	private FeedbackPanel feedbackPanel;
	private Form<String> createProfileForm;
	final AjaxLink close;

	public Profile getCreatedProfile() {
		return createdProfile;
	}

	public void setCreatedProfile(Profile createdProfile) {
		this.createdProfile = createdProfile;
	}

	/**
	 * 
	 * @param pageReference
	 * @param modalWindowPage
	 * @param window
	 */
	public CreateProfile(PageReference pageReference, final ModalWindow window) {

		close = new AjaxLink<Void>("close") {

			private static final long serialVersionUID = 9205507894949589130L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				window.close(target);
				target.add(feedbackPanel);

			}
		};
		close.setOutputMarkupId(true);
		add(close);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		createdProfile = new Profile();

		/**
		 * Feedback panel
		 */

		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		createProfileForm = new Form<String>("createForm");
		createProfileForm.setOutputMarkupId(true);
		add(createProfileForm);
		/**
		 * Profile Name
		 */

		createProfileForm.add(new Label("profileNameLabel", "Profile name:"));
		final TextField<String> profileNameField = new TextField<String>(
				"profileName", new PropertyModel<String>(this.createdProfile,
						"name"));
		profileNameField.setRequired(true);
		profileNameField.setOutputMarkupId(true);
		createProfileForm.add(profileNameField);

		/**
		 * Description
		 */

		createProfileForm.add(new Label("descriptionLabel",
				"Profile description : "));
		final TextField<String> profileDescription = new TextField<String>(
				"profileDescription", new PropertyModel<String>(
						this.createdProfile, "description"));
		profileDescription.setRequired(true);
		profileDescription.setOutputMarkupId(true);
		createProfileForm.add(profileDescription);

		/**
		 * Submit button
		 */

		AjaxButton createButton = new AjaxButton("createProfile") {

			private static final long serialVersionUID = -7127248610161307777L;

			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				createdProfile.setName(createdProfile.getName());
				createdProfile.setDescription(createdProfile.getDescription());

				profileService.saveProfile(createdProfile);
				target.add(feedbackPanel);
				close.onClick(target);
				// setResponsePage(ProfilesList.class);

			};

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				target.add(feedbackPanel);

			}

		};

		createButton.setOutputMarkupId(true);
		createProfileForm.add(createButton);

	}
}