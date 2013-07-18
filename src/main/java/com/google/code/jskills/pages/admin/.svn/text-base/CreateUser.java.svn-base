package com.google.code.jskills.pages.admin;

import java.util.Arrays;
import java.util.Date;
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
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.CountryService;
import com.google.code.jskills.business.services.SecurityService;
import com.google.code.jskills.business.services.UserService;
import com.google.code.jskills.domain.Country;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.pages.AdministrativeTools;

public class CreateUser extends AdministrativeTools{

	private static final long serialVersionUID = -7935712527024920387L;
	
	@Inject
	private transient UserService userService;
	
	@Inject
	private transient CountryService countryService;
	
	@Inject
	private transient SecurityService securityService;
	
	/*
	 * 1 digit, 1 lower, 1 upper, from 6 to 20
	 */
	private static final String PASS_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
	/*
	 * 1 sign '+', from 10 to 12
	 */
	private static final String PHONE_PATTERN = "(?=.*[0-9])(?=.+).{10,12}";

	private static final Logger LOG = LoggerFactory
			.getLogger(CreateUser.class);
	
	private static final List<String> SEXTYPES = Arrays.asList(new String[] {
			"Male", "Female" });
	
	private User selectedUser;
	private FeedbackPanel feedbackPanel;
	private String selectedSex;
	private Country selectedCountry;
	private String password;
	private String confPassword;
	
	
	public CreateUser(){
		super();
		LOG.info("CreateUser page: default constructor");
		selectedSex = "Male";
		
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		LOG.info("CreateUser page: onInitialize");		
		selectedUser = new User();
		password = "";
		confPassword = "";
		
		add(new Label("message", "Administrative Tools / Create User"));
		
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
		emailField.add(EmailAddressValidator.getInstance());
		editUserForm.add(emailField);
		
		/**
		 * Password
		 */
		editUserForm.add(new Label("userPasswordLabel", "Password: "));
		final PasswordTextField passField = new PasswordTextField("userPasswordField",
				new PropertyModel<String>(this, "password"));
		passField.add(new PatternValidator(PASS_PATTERN));
		editUserForm.add(passField);
		
		/**
		 * Confirm Password
		 */
		editUserForm.add(new Label("userConfPasswordLabel", "Confirm password: "));
		final PasswordTextField confPassField = new PasswordTextField("userConfPasswordField",
				new PropertyModel<String>(this, "confPassword"));
		passField.add(new PatternValidator(PASS_PATTERN));
		editUserForm.add(confPassField);

		/**
		 * Mobile phone
		 */
		editUserForm.add(new Label("userMobileLabel", "Phone:"));
		final TextField<String> mobileField = new TextField<String>(
				"userMobileField", new PropertyModel<String>(this.selectedUser,
						"mobile"));
		//mobileField.setRequired(true);
		mobileField.add(new PatternValidator(PHONE_PATTERN));
		mobileField.add(StringValidator.maximumLength(12));
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
		ageField.add(RangeValidator.minimum(16));
		editUserForm.add(ageField);

		/**
		 * Country name 
		 */
		editUserForm.add(new Label("userCountryLabel", "Country:"));
		final List<Country> countriesList = countryService
				.getAllCountries();
		DropDownChoice<Country> countries = new DropDownChoice<Country>(
				"userCountryChoise", new PropertyModel<Country>(this.selectedUser, "countryEntity"),
				new ListModel<Country>(countriesList),
				new ChoiceRenderer<Country>("name"));
		editUserForm.add(countries);
		
		/**
		 * Secret question
		 */
		editUserForm.add(new Label("userSecretQuestionLabel",
				"SecretQuestion:"));
		final TextField<String> secretQuestionField = new TextField<String>(
				"userSecretQuestionField", new PropertyModel<String>(
						this.selectedUser, "secretQuestion"));
		secretQuestionField.setRequired(true);
		secretQuestionField.setOutputMarkupId(true);
		editUserForm.add(secretQuestionField);
		
		/**
		 * Secret answer
		 */
		editUserForm.add(new Label("userSecretAnswerLabel",
				"SecretAnswer:"));
		final TextField<String> secretAnswerField = new TextField<String>(
				"userSecretAnswerField", new PropertyModel<String>(
						this.selectedUser, "secretAnswer"));
		secretQuestionField.setRequired(true);
		secretQuestionField.setOutputMarkupId(true);
		editUserForm.add(secretAnswerField);

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
		 * feedback panel
		 */
		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		/**
		 * Save button
		 */
		AjaxButton createButton = new AjaxButton("createButton") {
			private static final long serialVersionUID = 1L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				LOG.info("create user button pressed in CreateUserWindow");
				
				if(!password.equals(confPassword)){
					LOG.info("CreateUserPage: passowrds do not match!");
					feedbackPanel.error("passowrds do not match!\n");
				}else{
					if(selectedSex.equals("Male")){
						selectedUser.setSex(true);
					}else{
						selectedUser.setSex(false);
					}
					selectedUser.setPassword(securityService.computeHash(password));
					selectedUser.setSecretAnswer(securityService.computeHash(selectedUser.getSecretAnswer()));
					selectedUser.setRegistrationDate(new Date());
					userService.saveUser(selectedUser);
					LOG.info("User " + selectedUser.getEmail() + " successfully saved");
					target.add(feedbackPanel);
					setResponsePage(UsersList.class);
				}				
			};
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				target.add(feedbackPanel);
				LOG.error("Incorrect data entered while creating user");
			}
		};
		editUserForm.add(createButton);
		
		/**
		 * Cancel button
		 */
		AjaxButton cancelButton = new AjaxButton("cancelCreationButton") {
			private static final long serialVersionUID = 3890131800821075652L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				LOG.info("Cancel button pressed in CreateUserWindow");
				//target.add(feedbackPanel);
				setResponsePage(UsersList.class);				
			};
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				LOG.info("Cancel button pressed in CreateUserWindow");
				setResponsePage(UsersList.class);
			}		
		};
		editUserForm.add(cancelButton);
		
		/**
		 * Pick Roles for user button
		 */
		AjaxButton editRolesForUserButton = new AjaxButton("pickRolesForUserButton") {
			private static final long serialVersionUID = 3890131800821075652L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				LOG.info("EditRolesForUserButton pressed in CreateUserPage");
				
				//Saving user
				if(!password.equals(confPassword)){
					LOG.info("CreateUserPage: passowrds do not match!");
					feedbackPanel.error("passowrds do not match!\n");
				}else{
					if(selectedSex.equals("Male")){
						selectedUser.setSex(true);
					}else{
						selectedUser.setSex(false);
					}
					selectedUser.setPassword(securityService.computeHash(password));
					selectedUser.setSecretAnswer(securityService.computeHash(selectedUser.getSecretAnswer()));
					selectedUser.setRegistrationDate(new Date());
					userService.saveUser(selectedUser);
					LOG.info("User " + selectedUser.getEmail() + " successfully saved");
					feedbackPanel.info("User has been saved");
					target.add(feedbackPanel);
				}
				User user = userService.findUserByEmail(selectedUser.getEmail());
								
				Page editRolesForUserPage = new PickUserRoles(user);
				target.add(feedbackPanel);
				setResponsePage(editRolesForUserPage);
			};		
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				LOG.info("EditRolesForUserButton button pressed on CreateUser page");				
			}
		};
		editUserForm.add(editRolesForUserButton);
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfPassword() {
		return confPassword;
	}

	public void setConfPassword(String confPassword) {
		this.confPassword = confPassword;
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

}
