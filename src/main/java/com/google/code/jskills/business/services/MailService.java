package com.google.code.jskills.business.services;

import com.google.code.jskills.utils.Message;

/**
 * Service provides method(s) for sending messages
 * 
 * @author bobina_vlada
 * 
 */

public interface MailService {

	void sendMessage(Message message);

}
