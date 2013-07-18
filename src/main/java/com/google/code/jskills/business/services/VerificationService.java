package com.google.code.jskills.business.services;

import com.google.code.jskills.domain.User;
import com.google.code.jskills.domain.Verification;

public interface VerificationService {
	
	void saveVerification(Verification verification);
	
	boolean getUserByEmail(String email);
	
	void deleteVerification(Verification verification);
	
	void sendMail(User user, int type);
}
