package com.google.code.jskills.business.services.impl;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.code.jskills.business.services.MailRegistration;
import com.google.code.jskills.business.services.MailService;
import com.google.code.jskills.utils.Message;

/**
 * MailService implementation
 * in registration context 
 * 
 * @author bobina_vlada
 * 
 */

@Stateless
@MailRegistration
public class MailServiceImpl implements MailService {

	@Resource(name = "java:/Mail")
	private Session session;

	@Override
	public void sendMessage(Message message) {
		try {
			javax.mail.Message msg = new MimeMessage(session);
			InternetAddress[] address = { new InternetAddress(message.getTo()) };
			msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
			msg.setSubject(message.getSubject());
			msg.setText(message.getContent());
			Transport.send(msg);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}
}
