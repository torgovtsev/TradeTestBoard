package com.google.code.jskills.pages.forgotpassword;

import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;

import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;

import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.IResource;
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

	private String password = StringUtils.EMPTY;
	private String email = StringUtils.EMPTY;

	private CaptchaImageResource captchaImageResource;

	private CaptchaImageResource getCaptchaImageResource() {
		return captchaImageResource;
	}

	private void setCaptchaImageResource(String imagePass) {
		this.captchaImageResource = new CaptchaImageResource(imagePass);
	}

	@Override
	protected void onInitialize() {

		super.onInitialize();

		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		Form<Void> form = new Form<Void>("form");
		add(form);

		form.add(addEmailTextField());
		
		final ImagePanel imagePanel = addImagePanel();
		form.add(imagePanel);

		final RequiredTextField<String> passField = new RequiredTextField<String>(
				"password", new PropertyModel<String>(this, "password"));
		passField.setOutputMarkupId(true);
		form.add(passField);

		form.add(new AjaxFallbackLink<String>("linkUpdate") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				password = StringUtils.EMPTY;
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

				StringBuilder message = new StringBuilder();
				if (!getCaptchaImageResource().getChallengeIdModel()
						.getObject().equals(password)) {

					message.append("Captcha password '");
					message.append(password);
					message.append("' is wrong.\n");
					message.append("Correct password was: ");
					message.append(getCaptchaImageResource()
							.getChallengeIdModel().getObject());
					feedback.error(message.toString());
					password = StringUtils.EMPTY;

				} else {
					User user = userUuidService.getUserByEmail(email);
					if (user != null && !user.getIsBlocked()) {
						String uuid = UUID.randomUUID().toString();

						userUuidService.saveUserUuid(new UserUuid(user, uuid));

						String content = String
								.format("Click on http://localhost:8080/jskills/restorepassword?id=%d&uuid=%s",
										user.getId(), uuid);

						mailService.sendMessage(new Message(email,
								"Restore Password", content));

						message.append("Check your e-mail. If the e-mail address you entered is associated ");
						message.append("with a customer account in our records, you will receive an e-mail from us with instructions ");
						message.append("for resetting your password. If you don't receive this e-mail, please check your junk mail folder!");
						feedback.info(message.toString());
					} else {
						if (user == null) {
							password = StringUtils.EMPTY;
							feedback.error("User dose not exit");
						} else {
							password = StringUtils.EMPTY;
							feedback.info("User blocked");
						}
					}
				}

				// force redrawing
				getCaptchaImageResource().invalidate();

				target.add(form);
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

	private EmailTextField addEmailTextField() {
		final EmailTextField result = new EmailTextField("email",
				new PropertyModel<String>(this, "email"));
		result.setOutputMarkupId(true);

		return result;
	}
	
	private ImagePanel addImagePanel() {
		final ImagePanel imagePanel = new ImagePanel("imagePanel") {

			private static final long serialVersionUID = -6425636582406386893L;

			@Override
			public IResource getImageResource() {
				setCaptchaImageResource(randomString(6, 8));
				return getCaptchaImageResource();
			}
		};
		imagePanel.setOutputMarkupId(true);
		
		return imagePanel;
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
