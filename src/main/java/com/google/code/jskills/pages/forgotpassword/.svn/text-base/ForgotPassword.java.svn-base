package com.google.code.jskills.pages.forgotpassword;

import java.util.UUID;

import javax.inject.Inject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;

import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import com.google.code.jskills.business.services.MailRegistration;
import com.google.code.jskills.business.services.MailService;
import com.google.code.jskills.business.services.UserService;
import com.google.code.jskills.business.services.UserUuidService;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.domain.UserUuid;
import com.google.code.jskills.pages.master.MasterPage;
import com.google.code.jskills.utils.Message;

/**
 * Fogot password page.
 * 
 * @author Frolova Victoriya
 */
public class ForgotPassword extends MasterPage {

	private static final long serialVersionUID = 1L;

	@Inject
	@MailRegistration
	private MailService mailService;

	@Inject
	private UserService userService;

	@Inject
	private UserUuidService userUuidService;

	//private String password = StringUtils.EMPTY;

	private String password = "";
	private String email = "";

	private CaptchaImageResource captchaImageResource;

	private CaptchaImageResource getCaptchaImageResource() {
		return captchaImageResource;
	}

	private void setCaptchaImageResource(String imagePass) {
		this.captchaImageResource = new CaptchaImageResource(imagePass);
	}

	private void setCaptchaImageResource() {
		this.captchaImageResource = new CaptchaImageResource();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		Form<?> form = new Form<Void>("form");
		add(form);

		final RequiredTextField<String> emailField = new RequiredTextField<String>(
				"email", new PropertyModel<String>(this, "email"));
		emailField.setOutputMarkupId(true);
		emailField.add(EmailAddressValidator.getInstance());

		form.add(emailField);


		final ImagePanel imagePanel = new ImagePanel("imagePanel") {
			
			@Override
			public IResource getImageResource() {
				setCaptchaImageResource(randomString(6, 8));
				return getCaptchaImageResource();
			}
		};
		imagePanel.setOutputMarkupId(true);
		
		/*final AjaxLazyLoadPanel lazyLoadPanel = new AjaxLazyLoadPanel("lazyLoadPanel") {
			
			private static final long serialVersionUID = -7095862490956491267L;

			@Override
			public Component getLazyLoadComponent(String arg0) {
				return new ImagePanel(arg0) {
					
					@Override
					public IResource getImageResource() {
						setCaptchaImageResource(randomString(6, 8));
						return getCaptchaImageResource();
					}
				};
			}
		};
		lazyLoadPanel.setOutputMarkupId(true);*/
		
		form.add(imagePanel);

		final RequiredTextField<String> passField = new RequiredTextField<String>(
				"password", new PropertyModel<String>(this, "password"));
		passField.setOutputMarkupId(true);
		form.add(passField);

		form.add(new AjaxFallbackLink<String>("linkUpdate") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				// setCaptchaImageResource(randomString(6, 8));
				// System.out.println(getCaptchaImageResource().getChallengeIdModel());
				password = "";
				target.add(imagePanel, passField);
			}

		});

		form.add(new AjaxFallbackButton("ajaxButton", form) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedback);
				super.onError(target, form);

			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				if (!getCaptchaImageResource().getChallengeIdModel()
						.getObject().equals(password)) {

					feedback.error("Captcha password '"
							+ password
							+ "' is wrong.\n"
							+ "Correct password was: "
							+ getCaptchaImageResource().getChallengeIdModel()
									.getObject());
					password = "";
					
					
				} else {
					//User user = userService.findUserByEmail(email);

					User user = userUuidService.getUserByEmail(email);
					if (user != null && !user.getIsBlocked()) {
						String uuid = UUID.randomUUID().toString();
     
						userUuidService
								.saveUserUuid(new UserUuid(user, uuid));

						String content = String
								.format("Click on http://localhost:8080/jskills/restorepassword?id=%d&uuid=%s",
										user.getId(), uuid);

						mailService.sendMessage(new Message(email,
								"Restore Password", content));

						feedback.info("Check your e-mail. If the e-mail address you entered is associated "
								+ "with a customer account in our records, you will receive an e-mail from us with instructions "
								+ "for resetting your password. If you don't receive this e-mail, please check your junk mail folder!");
						// this.getPage().setResponsePage(RestorePasswordPage.class);
					} else {
						if (user == null) {
							password = "";
							feedback.error("User dose not exit");
						} else {
							password = "";
							feedback.info("User blocked");
						}
					}
				}

				// force redrawing
				getCaptchaImageResource().invalidate();

				target.add(imagePanel, passField, emailField, feedback);
			}

		});
	}

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

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				ForgotPassword.class, "styleForgotPassword.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				ForgotPassword.class, "../master/bootstrap.min.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				ForgotPassword.class, "../master/bootstrap.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				MasterPage.class, "../master/add.css")));
		
	}

}
