package com.google.code.jskills.pages.admin;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.CountryService;
import com.google.code.jskills.business.services.UserService;
import com.google.code.jskills.domain.Country;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.pages.AdministrativeTools;

public class EditUser extends AdministrativeTools{

	private static final long serialVersionUID = -4925378466176357769L;
	
	@Inject
	private transient UserService userService;
	
	@Inject
	private transient CountryService countryService;

	private static final Logger LOG = LoggerFactory
			.getLogger(EditUser.class);
	
	private User selectedUser;
	private FeedbackPanel feedbackPanel;
	private String selectedSex;
	private Country selectedCountry;
	
	private static final List<String> SEXTYPES = Arrays.asList(new String[] {
			"Male", "Female" });
	
	public EditUser(){
		LOG.info("EditUser page: default constructor");
	}
	
	public EditUser(User selectedUser){
		LOG.info("EditUser page: constructor with selectedUser");
		this.selectedUser = selectedUser;
		this.selectedCountry = selectedUser.getCountryEntity();
		
		if(selectedUser.getSex()){
			selectedSex = "Male";
		}else{
			selectedSex = "Female";
		}
	}
	
	public User getSelectedUser() {
		return selectedUser;
	}
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	public String getSelectedSex() {
		return selectedSex;
	}

	public void setSelectedSex(String selectedSex) {
		this.selectedSex = selectedSex;
	}
	
	public Country getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(Country selectedCountry) {
		this.selectedCountry = selectedCountry;
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new Label("message", "Administrative Tools / Edit User"));
		
		Form<String> editUserForm = new Form<String>("editUserForm");
		add(editUserForm);

		/**
		 * First Name
		 */
		editUserForm.add(new Label("userFirstNameLabel", "First Name:"));
		final TextField<String> firstNameField = new TextField<String>(
				"userFirstNameField", new PropertyModel<String>(this.selectedUser,
						"firstName"));
		firstNameField.setRequired(true);
		firstNameField.setOutputMarkupId(true);
		editUserForm.add(firstNameField);

		/**
		 * Last Name
		 */
		editUserForm.add(new Label("userLastNameLabel", "Last Name:"));
		final TextField<String> lastNameField = new TextField<String>(
				"userLastNameField", new PropertyModel<String>(this.selectedUser,
						"lastName"));
		lastNameField.setRequired(true);
		lastNameField.setOutputMarkupId(true);
		editUserForm.add(lastNameField);

		/**
		 * Email
		 */
		editUserForm.add(new Label("userEmailLabel", "Email:"));
		final TextField<String> emailField = new TextField<String>(
				"userEmailField",
				new PropertyModel<String>(this.selectedUser, "email"));
		emailField.setRequired(true);
		emailField.setOutputMarkupId(true);
		editUserForm.add(emailField);

		/**
		 * Mobile phone
		 */
		editUserForm.add(new Label("userMobileLabel", "Phone:"));
		final TextField<String> mobileField = new TextField<String>(
				"userMobileField", new PropertyModel<String>(this.selectedUser,
						"mobile"));
		mobileField.setRequired(true);
		mobileField.setOutputMarkupId(true);
		editUserForm.add(mobileField);

		/**
		 * Sex
		 */
		editUserForm.add(new Label("userSexLabel", "Sex:"));
		RadioChoice<String> sexChoise = new RadioChoice<String>("userSexChoise",
				new PropertyModel<String>(this, "selectedSex"), SEXTYPES);
		editUserForm.add(sexChoise);

		/**
		 * Age
		 */
		editUserForm.add(new Label("userAgeLabel", "Age:"));
		NumberTextField<Integer> ageField = new NumberTextField<Integer>("userAgeField",
				new PropertyModel<Integer>(this.selectedUser, "age"), Integer.class);
		editUserForm.add(ageField);

		/**
		 * Country name 
		 * DOES NOT WORK CORRECTLY
		 * DEFAULT CHOISE IS ALWAYS AFGANISTAN
		 */
		editUserForm.add(new Label("userCountryLabel", "Country:"));
		final List<Country> countriesList = countryService
				.getAllCountries();
		DropDownChoice<Country> countries = new DropDownChoice<Country>(
				"userCountryChoise", new PropertyModel<Country>(this, "selectedCountry"),
				new ListModel<Country>(countriesList),
				new ChoiceRenderer<Country>("name"));
		editUserForm.add(countries);
		
		/**
		 * Secret question
		 */
		editUserForm.add(new Label("userSecretQuestionFieldLabel",
				"SecretQuestion:"));
		final TextField<String> secretQuestionField = new TextField<String>(
				"userSecretQuestionField", new PropertyModel<String>(
						this.selectedUser, "secretQuestion"));
		secretQuestionField.setRequired(true);
		secretQuestionField.setOutputMarkupId(true);
		editUserForm.add(secretQuestionField);

		/**
		 * isBlocked
		 */
		editUserForm.add(new Label("userIsBlockedLabel", "IsBlocked:"));
		CheckBox isBlockedCkeckBox = new CheckBox("isBlockedCkeckBox",
				new PropertyModel<Boolean>(this.selectedUser, "isBlocked"));
		editUserForm.add(isBlockedCkeckBox);

		/**
		 * isDeleted
		 */
		editUserForm.add(new Label("userIsDeletedLabel", "IsDeleted:"));
		CheckBox isDeletedCkeckBox = new CheckBox("isDeletedCkeckBox",
				new PropertyModel<Boolean>(this.selectedUser, "isDeleted"));
		editUserForm.add(isDeletedCkeckBox);

		/**
		 * isVerified
		 */
		editUserForm.add(new Label("userIsVerifiedLabel", "IsVerified:"));
		CheckBox isVerifiedCkeckBox = new CheckBox("isVerifiedCkeckBox",
				new PropertyModel<Boolean>(this.selectedUser, "isVerified"));
		editUserForm.add(isVerifiedCkeckBox);

		/**
		 * feedbackpanel
		 */
		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		/**
		 * Save button
		 */
		AjaxButton submitButton = new AjaxButton("submitButton") {
			private static final long serialVersionUID = 1L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				LOG.info("Save button pressed in EditUserWindow");
				
				if(selectedSex.equals("Male")){
					selectedUser.setSex(true);
				}else{
					selectedUser.setSex(false);
				}
				
				Country c = countryService.findCountryByName(selectedCountry.getName());
				selectedUser.setCountryEntity(c);
				
				userService.updateUser(selectedUser);
				
				target.add(feedbackPanel);
				setResponsePage(UsersList.class);
			};

		};
		editUserForm.add(submitButton);
		
		/**
		 * Cancel button
		 */
		AjaxButton cancelButton = new AjaxButton("cancelButton") {
			private static final long serialVersionUID = 3890131800821075652L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				LOG.info("Cancel button pressed in EditUserWindow");
				target.add(feedbackPanel);
				setResponsePage(UsersList.class);
			};		
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				LOG.info("Cancel button pressed on EditUser page");				
				setResponsePage(UsersList.class);
			}
		};
		editUserForm.add(cancelButton);
		
		/**
		 * Edit Roles for user button
		 */
		AjaxButton editRolesForUserButton = new AjaxButton("pickRolesForUserButton") {
			private static final long serialVersionUID = 3890131800821075652L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				LOG.info("EditRolesForUserButton pressed in EditUserWindow");
				target.add(feedbackPanel);
				Page editRolesForUserPage = new PickUserRoles(selectedUser);
				setResponsePage(editRolesForUserPage);
			};		
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				LOG.info("EditRolesForUserButton button pressed on EditUser page");				
			}
		};
		editUserForm.add(editRolesForUserButton);
	}
}





