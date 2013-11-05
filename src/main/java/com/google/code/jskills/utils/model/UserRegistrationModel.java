package com.google.code.jskills.utils.model;

import java.io.Serializable;

import com.google.code.jskills.domain.User;
import com.google.code.jskills.domain.Verification;

public class UserRegistrationModel implements Serializable {

	private static final long serialVersionUID = -1860018462990984471L;

	private Verification verification;
	private User user;

	public Verification getVerification() {
		return verification;
	}

	public void setVerification(Verification verification) {
		this.verification = verification;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
