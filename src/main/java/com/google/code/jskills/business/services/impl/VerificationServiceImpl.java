package com.google.code.jskills.business.services.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.google.code.jskills.business.services.ApplicationSettingsService;
import com.google.code.jskills.business.services.MailRegistration;
import com.google.code.jskills.business.services.MailService;
import com.google.code.jskills.business.services.UserService;
import com.google.code.jskills.business.services.VerificationService;
import com.google.code.jskills.dal.providers.UserVerificationDataProvider;
import com.google.code.jskills.domain.Settings;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.domain.Verification;
import com.google.code.jskills.utils.Message;

@Stateless
public class VerificationServiceImpl implements VerificationService{
	
	@Inject
	private transient UserVerificationDataProvider userVerificationDataProvider;
	
	@Inject
	private transient UserService userService;
	
	@Inject
	@MailRegistration
	private transient MailService mailService;
	
	@Inject
	private transient ApplicationSettingsService settingsService;
	
	private Settings siteUrl;
	
	/**
	 * save verification in database method
	 * 
	 * @param Verification verification
	 */
	public void saveVerification(Verification verification) {
		userVerificationDataProvider.saveVerification(verification);
	}
	
	/**
	 * Find user by E-mail
	 * 
	 * @param String email
	 * 
	 * @return boolean
	 */
	public boolean getUserByEmail(String email) {
		User user = userService.findUserByEmail(email);
		
		if(user == null) {
			return false;
		}		
		if(user.getIsVerified()) {
			return false;
		} else {
			return true;
		}		
	}
	
	/**
	 * delete verification from DB
	 * 
	 * @param Verification verification
	 */
	public void deleteVerification(Verification verification) {
		userVerificationDataProvider.deleteVerification(verification);
	}
	
	/**
	 * send user mail notification, need choose type mail notification
	 * 
	 * @param User user, int type
	 */
	public void sendMail(User user, int type) {
		siteUrl = settingsService.getSettingsById(1);
		StringBuilder content = new StringBuilder();
		String subject = "";
		switch (type) {
		case 0:
			subject = "Jskills Registration: Account Activated";
			content.append( "Dear " + user.getFirstName() + " " + user.getLastName());
			content.append("\nYour account has been created.\n\n");
			content.append("Thank you for using our services.");
			break;
		case 1:
			subject = "Jskills Registration: Wrong type UUID";
			content.append("Dear " + user.getFirstName() + " " + user.getLastName());
			content.append("\nYou typed wrong UUID, please visit the following link and try again:\n\n");
			content.append(siteUrl.getValue() + "\n\n");
			content.append("You have" + user.getUserVerificationEntity().getAttemptsCount() + "attempts\n\n");
			content.append("Thank you for using our services.");
			break;
		case 2:
			subject = "Jskills Registration: End count attempts";
			content.append("Dear " + user.getFirstName() + " " + user.getLastName());
			content.append("\nWe are sorry, but your number of attempts to enter expired.\n");
			content.append(" You need to repeat the registration procedure.\n\n");
			content.append("Thank you for using our services.");
			break;
		case 3:
			subject = "Jskills Registration: End date";
			content.append("Dear " + user.getFirstName() + " " + user.getLastName());
			content.append("We are sorry, but your date of validity has expired.\n");
			content.append("You need to repeat the registration procedure.\n\n");
			content.append("Thank you for using our services.");
			break;
		}
		Message message = new Message();
		message.setTo(user.getEmail());
		message.setSubject(subject);
		message.setContent(content.toString());
		mailService.sendMessage(message);
	}
}
