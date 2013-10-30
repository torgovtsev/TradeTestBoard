package com.google.code.jskills.pages.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.pages.Index;
import com.google.code.jskills.pages.forgotpassword.ForgotPassword;
import com.google.code.jskills.pages.master.MasterPage;
import com.google.code.jskills.pages.registration.Registration;

public class LoginPage extends MasterPage {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(LoginPage.class);

	private String userName;

	private String password;

	private FeedbackPanel feedbackPanel;

	@SuppressWarnings("serial")
	@Override
	protected void onInitialize() {
		super.onInitialize();

		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		Form<String> loginForm = new Form<String>("loginForm");
		add(loginForm);

		TextField<String> usernameField = new TextField<String>("username",
				new PropertyModel<String>(this, "userName"));
		usernameField.setRequired(true);
		loginForm.add(usernameField);

		/*TextField<String> passwordField = new TextField<String>("password",
				new PropertyModel<String>(this, "password"));*/
		final PasswordTextField passwordField = new PasswordTextField("password",
				new PropertyModel<String>(this, "password"));
		passwordField.setRequired(true);
		loginForm.add(passwordField);

		AjaxButton submitButton = new AjaxButton("submitButton") {
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				UsernamePasswordToken token = new UsernamePasswordToken(
						userName, password);
				token.setRememberMe(true);
				try {
					SecurityUtils.getSubject().login(token);
					setResponsePage(Index.class);
				} catch (AuthenticationException e) {
					error(e);
					LOG.error("Authentication error", e);

					target.add(feedbackPanel);
				}
			};

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		};
		loginForm.add(submitButton);

		/*
		 * Registration block
		 */

		final ModalWindow regModalWindow = new ModalWindow("regWindow");
		regModalWindow.setCookieName("regWindow-1");
		regModalWindow.setAutoSize(true);
		add(regModalWindow);

		regModalWindow.setPageCreator(new ModalWindow.PageCreator() {

			@Override
			public Page createPage() {
				return new Registration();
			}
		});

		add(new AjaxLink<Void>("showRegistration") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				regModalWindow.show(target);
			}
		});

		/*
		 * Fogot password
		 */

		add(new Link<String>("forgot") {
			@Override
			public void onClick() {
				// throw new
				// RestartResponseAtInterceptPageException(ForgotPasswordPage.class);
				this.getPage().setResponsePage(ForgotPassword.class);
			}
		});
	}
}
