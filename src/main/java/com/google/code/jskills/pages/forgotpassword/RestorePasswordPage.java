package com.google.code.jskills.pages.forgotpassword;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.PatternValidator;

import com.google.code.jskills.business.services.MailRegistration;
import com.google.code.jskills.business.services.MailService;
import com.google.code.jskills.business.services.SecurityService;
import com.google.code.jskills.business.services.UserService;
import com.google.code.jskills.business.services.UserUuidService;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.domain.UserUuid;
import com.google.code.jskills.pages.admin.UsersList;
import com.google.code.jskills.pages.auth.LoginPage;
import com.google.code.jskills.utils.Message;

public class RestorePasswordPage extends WebPage {

	private static final long serialVersionUID = 1L;

	@Inject
	@MailRegistration
	private MailService mailService;

	@Inject
	private UserService userService;

	@Inject
	private UserUuidService userUuidService;
	
	@Inject 
	private SecurityService securityService;
	
	private String pass;
	
	private String reenterPass;
	
	private int user_id;
	
	private String uuid;
	/*
	 * 1 digit, 1 lower, 1 upper, from 6 to 20
	 */
	private static final String PASS_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
	
	public RestorePasswordPage() {
	}
	
	public RestorePasswordPage(PageParameters parameters){
		
		super(parameters);
		this.user_id = Integer.parseInt(parameters.get("id").toString());
		this.uuid = parameters.get("uuid").toString();
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form<String> form = new Form<String>("passForm");
		add(form);
		
		final PasswordTextField passField = new PasswordTextField("pass", new PropertyModel<String>(this, "pass"));
		passField.setOutputMarkupId(true);
		passField.add(new PatternValidator(PASS_PATTERN));
		
		final PasswordTextField reenterField = new PasswordTextField("reenter", new PropertyModel<String>(this, "reenterPass"));
		reenterField.setOutputMarkupId(true);
		reenterField.add(new PatternValidator(PASS_PATTERN));
		EqualPasswordInputValidator passValidator = new EqualPasswordInputValidator(passField, reenterField);
		
		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		
		form.add(passField, reenterField, feedback);
		form.add(passValidator);
		
		form.add(new AjaxFallbackButton("ajaxButton", form) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedback);
				super.onError(target, form);
			}
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.add(passField, reenterField, feedback);
				super.onSubmit(target, form);
				
				if(pass.equals(reenterPass)){
					 
					User user = userService.getUserByID(user_id);
					
					UserUuid userUuid = userUuidService.getUserUuid(user, uuid);
					if(userUuid != null ){
						
					   String hashPass = securityService.computeHash(pass);
					   user.setPassword(hashPass); 
					   userService.updateUser(user);
					 
					   String content = String.format("Your login: %s  YourPassword: %s", user.getEmail(), pass);
					   mailService.sendMessage(new Message(user.getEmail(),"Your New Password", content));
					   
					   //userUuidService.deleteUserUuid(userUuid);
					   feedback.info("You have successfully changed your password. You will receive a letter in the mail.");
					   
					   //setResponsePage(LoginPage.class);
					}
				}
				else{
					pass = "";
					reenterPass = "";
					feedback.info("Please check that your passwords match and try again.");
				}
			}
		} );
	}
}
