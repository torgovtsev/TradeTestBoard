package com.google.code.jskills.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Verification implements Serializable{

	private static final long serialVersionUID = 7728558796587305899L;
	
	private Integer id;
	private User user;
	private Date initiatedOn;
	private Date validTo;
	private String verificationInfo;
	private int attemptsCount;
	
	public Verification() {
		
	}
	
	public Verification(String verificationInfo, int attemptsCount) {
		this.initiatedOn = new Date();
		Calendar c =  Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, +4);
		this.validTo = c.getTime();
		this.verificationInfo = verificationInfo;
		this.attemptsCount = attemptsCount;
	}
	
	public int getAttemptsCount() {
		return attemptsCount;
	}

	public void setAttemptsCount(int attemptsCount) {
		this.attemptsCount = attemptsCount;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
		
	public User getUserEntity() {
		return user;
	}

	public void setUserEntity(User user) {
		this.user = user;
	}

	public Date getInitiatedOn() {
		return initiatedOn;
	}
	
	public void setInitiatedOn(Date initiatedOn) {
		this.initiatedOn = initiatedOn;
	}
	
	public Date getValidTo() {
		return validTo;
	}
	
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	
	public String getVerificationInfo() {
		return verificationInfo;
	}
	
	public void setVerificationInfo(String verificationInfo) {
		this.verificationInfo = verificationInfo;
	}
	
}
