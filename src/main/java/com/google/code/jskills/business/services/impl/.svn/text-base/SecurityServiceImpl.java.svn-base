package com.google.code.jskills.business.services.impl;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import com.google.code.jskills.business.services.SecurityService;

@Singleton
public class SecurityServiceImpl implements SecurityService {

	@Override
	@Lock(LockType.READ)
	//method can be accessed by many clients at the same time
	public String computeHash(String source) {
		SimpleHash hash = new Sha512Hash(source);
		return hash.toString();
	}

}
