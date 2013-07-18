package com.google.code.jskills.pages.admin;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.markup.html.basic.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.UserService;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.pages.AdministrativeTools;

public class AdminHome extends AdministrativeTools{

	private static final long serialVersionUID = 8990415074757141456L;

	private static final Logger LOG = LoggerFactory
			.getLogger(AdminHome.class);
	
	@Inject
	private transient UserService userService;
	
	public AdminHome(){
		super();
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Label("contentMessage", "Welcome to Administrative Tools"));
		Subject currentUser = SecurityUtils.getSubject();
		String email = currentUser.getPrincipal().toString();
		LOG.info("AdminHome onInitialize: " + email);
		User user = userService.findUserByEmail(email);
		add(new Label("helloMessage", "You have logged in as "
				+ user.getFirstName() + " " + user.getLastName()));
	}
	
}
