package com.google.code.jskills.business.services.impl;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.google.code.jskills.business.services.ApplicationSettingsService;
import com.google.code.jskills.business.services.CountryService;
import com.google.code.jskills.business.services.MailRegistration;
import com.google.code.jskills.business.services.MailService;
import com.google.code.jskills.business.services.RegistrationService;
import com.google.code.jskills.business.services.SecurityService;
import com.google.code.jskills.business.services.UserService;
import com.google.code.jskills.domain.Country;
import com.google.code.jskills.domain.Settings;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.utils.Message;

@Stateless
public class RegistrationServiceImpl implements RegistrationService {

	@Inject
	private transient UserService userService;

	@Inject
	private transient CountryService countryService;

	@Inject
	@MailRegistration
	private transient MailService mailService;

	@Inject
	private transient SecurityService securityService;
	
	@Inject
	private transient ApplicationSettingsService settingsService;

	/*
	 * subject for mail
	 */
	private static final String SUBJECT = "Jskills Registration: Account Activation";

	/**
	 * method saveUser
	 * 
	 * @param User
	 *            user
	 */
	@Override
	public void saveUser(User user) {
		userService.saveUser(user);
	}

	/**
	 * method getAllCountries
	 * 
	 * @return List<Country>
	 */
	@Override
	public List<Country> getAllCountries() {
		return countryService.getAllCountries();
	}

	/**
	 * method generateUUID
	 * 
	 * @return String
	 */
	@Override
	public String generateUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * method computerHash
	 * 
	 * @param String
	 *            source
	 * 
	 * @return String
	 */
	@Override
	public String computeHash(String source) {
		return securityService.computeHash(source);
	}

	/**
	 * method sendMail
	 * 
	 * @param User
	 *            user
	 * 
	 * @param String
	 *            uuid
	 */
	@Override
	public void sendMail(User user, String uuid, String password,
			String secretAnswer) {
		Settings site_url = settingsService.getSettingsById(1);
		StringBuilder content = new StringBuilder();
		content.append("Dear " + user.getFirstName() + " " + user.getLastName() + "\n");
		content.append("To activate your account, visit the following link within 4 hours and log in with your UUID:\n");
		content.append(site_url.getValue() + "\n\n");
		content.append("Your UUID: " + uuid + "\n\n");
		content.append("If you don't activate your account within 4 hours, you must re-register.\n\n");
		content.append("Your registration info:\n" + "E-mail: " + user.getEmail() + "\n");
		content.append("Password: " + password + "\n");
		content.append("Secret question: " + user.getSecretQuestion() + "\n");
		content.append("Secret answer: " + secretAnswer + "\n\n");
		content.append("Sincerely,\nJskills Customer Service");
		Message message = new Message();
		message.setTo(user.getEmail());
		message.setSubject(SUBJECT);
		message.setContent(content.toString());
		mailService.sendMessage(message);
	}

	/**
	 * find user by email
	 * 
	 * @param String
	 *            email
	 * 
	 * @return boolean
	 */
	public boolean findUserByEmail(String email) {
		User user = userService.findUserByEmail(email);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

}
