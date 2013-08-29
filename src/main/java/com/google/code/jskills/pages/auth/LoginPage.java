package com.google.code.jskills.pages.auth;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.pages.Index;
import com.google.code.jskills.pages.forgotpassword.ForgotPassword;
import com.google.code.jskills.pages.master.MasterPage;
import com.google.code.jskills.pages.registration.Registration;

public class LoginPage extends MasterPage {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(LoginPage.class);

	private String userName = StringUtils.EMPTY;

	private String password = StringUtils.EMPTY;

	private FeedbackPanel feedbackPanel;

	@Override
	protected void onInitialize() {
		super.onInitialize();

		addFeedbackPanel();

		Form<String> loginForm = new Form<String>("loginForm");
		add(loginForm);

		loginForm.add(addStringTextField("username", "userName"));
		loginForm.add(addPasswordField());
		loginForm.add(addAjaxButton());

		/*
		 * Registration block
		 */
		addRegistrationWindow();

		/*
		 * Forgot password
		 */
		addForgotPasswordLink();
	}

	private void addFeedbackPanel() {
		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
	}

	private TextField<String> addStringTextField(String id, String expression) {
		TextField<String> result = new TextField<String>(id,
				new PropertyModel<String>(this, expression));
		result.setRequired(true);
		result.setOutputMarkupId(true);
		return result;
	}

	private PasswordTextField addPasswordField() {
		final PasswordTextField passwordField = new PasswordTextField(
				"password", new PropertyModel<String>(this, "password"));
		passwordField.setRequired(true);
		return passwordField;
	}
	
	private AjaxButton addAjaxButton() {
		
		AjaxButton submitButton = new AjaxButton("submitButton") {
			
			private static final long serialVersionUID = 1L;

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
		
		return submitButton;
	}
	
	private void addRegistrationWindow() {
		
		final ModalWindow regModalWindow = new ModalWindow("regWindow");
		regModalWindow.setCookieName("regWindow-1");
		regModalWindow.setAutoSize(true);
		add(regModalWindow);

		regModalWindow.setPageCreator(new ModalWindow.PageCreator() {

			private static final long serialVersionUID = -1770883517308101310L;

			@Override
			public Page createPage() {
				return new Registration();
			}
		});

		add(new AjaxLink<Void>("showRegistration") {

			private static final long serialVersionUID = 4183564356278048563L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				regModalWindow.show(target);
			}
		});
	}
	
	private void addForgotPasswordLink() {
		
		add(new AjaxLink<String>("forgot") {

			private static final long serialVersionUID = 7590240053398332269L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(ForgotPassword.class);
			}
		});
	}
}
