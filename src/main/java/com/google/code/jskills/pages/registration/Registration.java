package com.google.code.jskills.pages.registration;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.MainApplication;
import com.google.code.jskills.business.services.RegistrationService;
import com.google.code.jskills.business.services.UserService;
import com.google.code.jskills.business.services.VerificationService;
import com.google.code.jskills.domain.Country;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.domain.Verification;
import com.google.code.jskills.pages.profile.ProfilesList;
import com.google.code.jskills.ui.components.MaskedInput;

public class Registration extends WebPage {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient RegistrationService registrationService;

	@Inject
	private transient VerificationService verificationService;

	@Inject
	private transient UserService userService;

	private static final Logger LOG = LoggerFactory
			.getLogger(MainApplication.class);

	private static final List<String> SEXTYPES = Arrays.asList(new String[] {
			"Male", "Female" });

	/*
	 * 1 digit, 1 lower, 1 upper, from 6 to 20
	 */
	private static final String PASS_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

	private CaptchaImageResource captchaImageResource;

	private String Password = StringUtils.EMPTY;
	private String hashPassword = StringUtils.EMPTY;
	private String hashSecretAnswer = StringUtils.EMPTY;
	private String confPassword = StringUtils.EMPTY;
	private String firstName = StringUtils.EMPTY;
	private String secondName = StringUtils.EMPTY;
	private String secretQuestion = StringUtils.EMPTY;
	private String secretAnswer = StringUtils.EMPTY;
	private String telephone = StringUtils.EMPTY;
	private Integer age;
	private String email = StringUtils.EMPTY;
	private String selectedSex = "Male";
	private String uuid = StringUtils.EMPTY;
	private String captcha = StringUtils.EMPTY;
	private String captchaOnImage = StringUtils.EMPTY;

	private Country selectedCountry;

	private FeedbackPanel feedbackPanel;

	@Override
	protected void onInitialize() {

		super.onInitialize();

		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		Form<String> regForm = new Form<String>("regForm");
		add(regForm);

		/*
		 * Country block
		 */
		regForm.add(new Label("countryLabel", "Select country: "));
		regForm.add(addCountryList());

		/*
		 * Email block
		 */
		regForm.add(new Label("emailLabel", "E-mail: "));
		regForm.add(addEmailField());

		/*
		 * Password block
		 */
		regForm.add(new Label("passwordLabel", "Password: "));
		final PasswordTextField passField = new PasswordTextField("password",
				new PropertyModel<String>(this, "Password"));
		passField.add(new PatternValidator(PASS_PATTERN));
		regForm.add(passField);

		/*
		 * Confirm password block
		 */
		regForm.add(new Label("confpasswordLabel", "Confirm password: "));
		PasswordTextField confPassField = new PasswordTextField("confpassword",
				new PropertyModel<String>(this, "confPassword"));
		regForm.add(confPassField);
		regForm.add(new EqualPasswordInputValidator(passField, confPassField));

		/*
		 * First name block
		 */
		regForm.add(new Label("firstnameLabel", "First name: "));
		regForm.add(addStringField("firstname", "firstName"));

		/*
		 * Second name block
		 */
		regForm.add(new Label("secondnameLabel", "Second name: "));
		regForm.add(addStringField("secondname", "secondName"));

		/*
		 * Secret question block
		 */
		regForm.add(new Label("secretQstLabel", "Secret question: "));
		regForm.add(addStringField("secretquestion", "secretQuestion"));

		/*
		 * Secret answer block
		 */
		regForm.add(new Label("secretAsrLabel", "Secret answer: "));
		regForm.add(addStringField("secretanswer", "secretAnswer"));

		/*
		 * Telephone block
		 */
		regForm.add(new Label("telephoneLabel",
				"Telephone (example: +7 (900) 000-0000): "));
		regForm.add(addMaskedInputField("telephone", "telephone",
				"~9 (999) 999-9999"));

		/*
		 * Sex o,o block
		 */
		regForm.add(new Label("sexLabel", "Sex: "));
		RadioChoice<String> sexChoise = new RadioChoice<String>("sex",
				new PropertyModel<String>(this, "selectedSex"), SEXTYPES);
		regForm.add(sexChoise);

		/*
		 * Age block
		 */
		regForm.add(new Label("ageLabel", "Age: "));
		regForm.add(addMaskedInputField("age", "age", "99"));

		/*
		 * Captcha block
		 */
		final Image image = new Image("captchaImage") {
			private static final long serialVersionUID = 1L;

			@Override
			protected IResource getImageResource() {
				setCaptchaImageResource(randomString(6, 8));
				return getCaptchaImageResource();
			}
		};
		image.setOutputMarkupId(true);
		regForm.add(image);

		/*
		 * text field for input captcha
		 */
		final RequiredTextField<String> captchaField = new RequiredTextField<String>(
				"captcha", new PropertyModel<String>(this, "captcha"));
		captchaField.setOutputMarkupId(true);
		regForm.add(captchaField);

		/*
		 * Link for update captcha
		 */
		regForm.add(new AjaxFallbackLink<String>("linkUpdate") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				captcha = "";
				target.add(image, captchaField);
			}

		});

		/*
		 * submit button
		 */
		AjaxButton submitButton = new AjaxButton("submitButton") {

			private static final long serialVersionUID = 1L;

			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				captchaOnImage = getCaptchaImageResource()
						.getChallengeIdModel().getObject();
				if (!captchaOnImage.equals(captcha)) {
					feedbackPanel.error("Captcha password is wrong.\n");
					captcha = "";
					LOG.error("Enter wrong captcha");
				} else {
					hashPassword = registrationService.computeHash(Password);
					hashSecretAnswer = registrationService
							.computeHash(secretAnswer);
					uuid = registrationService.generateUUID();
					final Verification verification = new Verification(uuid, 3);
					final User user = new User(hashPassword, firstName,
							secondName, secretQuestion, hashSecretAnswer,
							telephone, age, email, selectedSex, selectedCountry);
					user.setUserVerificationEntity(verification);
					verification.setUserEntity(user);
					registrationService.saveUser(user);
					LOG.info("User has been saved successfully");
					registrationService.sendMail(user, uuid, Password,
							secretAnswer);
					LOG.info("E-mail has been sent successfully");
					setResponsePage(DoneRegistration.class);
				}
				target.add(feedbackPanel);
			};

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
				LOG.error("Incorrect data entered registration");
			}
		};
		regForm.add(submitButton);

	}

	/*
	 * getters and setters for captchaImageResource
	 */
	public CaptchaImageResource getCaptchaImageResource() {
		return captchaImageResource;
	}

	public void setCaptchaImageResource(String imagePass) {
		this.captchaImageResource = new CaptchaImageResource(imagePass);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				ProfilesList.class, "../master/bootstrap.css")));
	}

	/*
	 * stuff methods for captchaImageResource
	 */
	private static int randomInt(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	private static String randomString(int min, int max) {
		int num = randomInt(min, max);
		byte b[] = new byte[num];
		for (int i = 0; i < num; i++)
			b[i] = (byte) randomInt('a', 'z');
		return new String(b);
	}

	private DropDownChoice<Country> addCountryList() {
		final List<Country> countriesList = registrationService
				.getAllCountries();
		DropDownChoice<Country> countries = new DropDownChoice<Country>(
				"country", new PropertyModel<Country>(this, "selectedCountry"),
				new ListModel<Country>(countriesList),
				new ChoiceRenderer<Country>("name"));
		return countries;
	}

	private EmailTextField addEmailField() {
		EmailTextField mailField = new EmailTextField("email",
				new PropertyModel<String>(this, "email"));
		mailField.setRequired(true);
		mailField.add(EmailAddressValidator.getInstance());
		mailField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			protected void onUpdate(AjaxRequestTarget target) {
				if (registrationService.findUserByEmail(email)) {
					feedbackPanel.error("This e-mail is busy!");
					LOG.error("Enter busy e-mail");
				} else {
					feedbackPanel.info("This e-mail is free!");
					LOG.info("All clear!");
				}
				target.add(feedbackPanel);
			}
		});

		return mailField;
	}

	private TextField<String> addStringField(String id, String expression) {
		final TextField<String> result = new TextField<String>(id,
				new PropertyModel<String>(this, expression));
		result.setRequired(true);
		result.setOutputMarkupId(true);

		return result;
	}

	private MaskedInput<String> addMaskedInputField(String id,
			String expression, String mask) {
		final MaskedInput<String> result = new MaskedInput<String>(id,
				new PropertyModel<String>(this, expression), mask);
		return result;
	}
}
