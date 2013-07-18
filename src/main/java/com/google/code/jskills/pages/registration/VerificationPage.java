package com.google.code.jskills.pages.registration;

import java.util.Date;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.MainApplication;
import com.google.code.jskills.business.services.UserService;
import com.google.code.jskills.business.services.VerificationService;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.domain.Verification;
import com.google.code.jskills.pages.Index;
import com.google.code.jskills.pages.master.MasterPage;
import com.google.code.jskills.utils.VerificationMailEnum;

public class VerificationPage extends MasterPage {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient VerificationService verificationService;

	@Inject
	private transient UserService userService;
  
	private static final Logger LOG = LoggerFactory
			.getLogger(MainApplication.class);

	private VerificationMailEnum type;
	private String email;
	private String uuid;

	@Override
	protected void onInitialize() {

		super.onInitialize();

		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		Form<String> verificationForm = new Form<String>("verificationForm");
		add(verificationForm);

		/*
		 * E-mail block
		 */
		verificationForm.add(new Label("emailLabel", "E-mail: "));
		EmailTextField mailField = new EmailTextField("email",
				new PropertyModel<String>(this, "email"));
		mailField.setRequired(true);
		mailField.add(EmailAddressValidator.getInstance());
		mailField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			protected void onUpdate(AjaxRequestTarget target) {
				if (verificationService.getUserByEmail(email)) {
					feedbackPanel.info("User is good boy!");
					LOG.info("All clear!!");
				} else {
					feedbackPanel.error("User is bad boy!");
					LOG.error("Enter wrong e-mail");
				}
				target.add(feedbackPanel);
			}
		});

		verificationForm.add(mailField);

		/*
		 * UUID block
		 */
		verificationForm.add(new Label("uuidLabel", "UUID"));
		TextField<String> uuidField = new TextField<String>("uuid",
				new PropertyModel<String>(this, "uuid"));
		verificationForm.add(uuidField);

		/*
		 * submit button block
		 */
		AjaxButton submitButton = new AjaxButton("submitButton") {

			private static final long serialVersionUID = 1L;

			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				User user = userService.findUserByEmail(email);
				Date nowDate = new Date();
				Verification verification = user.getUserVerificationEntity();
				if (verification.getValidTo().getTime() >= nowDate.getTime()) {
					if (verification.getAttemptsCount() != 0) {
						if (verification.getVerificationInfo().equals(uuid)) {
							user.setIsVerified(true);							
							userService.updateUser(user);
							verificationService.deleteVerification(verification);
							type = VerificationMailEnum.DONE;
							verificationService.sendMail(user, type.getValue());
							LOG.info("All clear");
							setResponsePage(Index.class);
						} else {
							verification.setAttemptsCount(verification
									.getAttemptsCount() - 1);
							if (verification.getAttemptsCount() < 1) {								
								userService.deleteUserByID(user.getId());
								type = VerificationMailEnum.ENDATTEMPTSCOUNT;
								verificationService.sendMail(user, type.getValue());
								feedbackPanel
										.error("We are sorry, but your number of attempts to enter expired.\n"
												+ " You need to repeat the registration procedure.");
								LOG.error("the end of the number of times");
							} else {
								userService.updateUser(user);
								type = VerificationMailEnum.ERRORUUID;
								verificationService.sendMail(user, type.getValue());
								feedbackPanel
										.error("Enter wrong UUID. Please try again. You have "
												+ verification
														.getAttemptsCount()
												+ " attempts count");
								LOG.error("Enter wrong UUID!");
							}
						}
					} else {
						userService.deleteUserByID(user.getId());
						type = VerificationMailEnum.ENDATTEMPTSCOUNT;
						verificationService.sendMail(user, type.getValue());
						feedbackPanel
								.error("We are sorry, but your number of attempts to enter expired.\n"
										+ " You need to repeat the registration procedure.");
						LOG.error("the end of the number of times");
					}
				} else {
					type = VerificationMailEnum.ENDDATE;
					verificationService.sendMail(user, type.getValue());
					userService.deleteUserByID(user.getId());
					feedbackPanel
							.error("We are sorry, but your date of validity has expired.\n"
									+ "You need to repeat the registration procedure.");
					LOG.error("date of validity has expired");
				}
				target.add(feedbackPanel);
			};

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		};
		verificationForm.add(submitButton);
	}
}
