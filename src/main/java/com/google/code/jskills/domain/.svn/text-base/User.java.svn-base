package com.google.code.jskills.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.code.jskills.dal.entities.CountryEntity;
import com.google.code.jskills.dal.entities.UserGroupEntity;
import com.google.code.jskills.dal.entities.UserRoleEntity;
import com.google.code.jskills.dal.entities.UserUuidEntity;
import com.google.code.jskills.dal.entities.UserVerificationEntity;

public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public User() {
		
	}
	
	public User(String password, String firstName,
			String secondName, String secretQuestion, String secretAnswer, String mobile, Integer age,
			String selectedEmail, String selectedSex, Country selectedCountry) {
		
		this.country = selectedCountry;
		this.firstName = firstName;
		this.lastName = secondName;
		this.email = selectedEmail;
		this.password = password;
		this.secretQuestion = secretQuestion;
		this.secretAnswer = secretAnswer;
		this.mobile = mobile;
		if (selectedSex.equals("Male")) {
			this.sex = true;
		}
		if (selectedSex.equals("Female")) {
				this.sex = false;
		}
		this.age = age;
		this.registrationDate = new Date();
		this.isBlocked = false;
		this.isDeleted = false;
		this.isVerified = false;
	}
	
	private Integer id;
	private Country country;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String secretQuestion;
	private String secretAnswer;
	private String mobile;
	private Boolean sex;
	private int age;
	private Date registrationDate;
	private Boolean isBlocked;
	private Boolean isDeleted;
	private Boolean isVerified;
	private Verification verification;
	private Set<UserUuid> userUuidEntities = new HashSet<UserUuid>(
			0);
	
	public User(String firstName,
			String lastName, String email, String password,
			String secretQuestion, String secretAnswer, String mobile,
			Boolean sex, int age, Date registrationDate, boolean isBlocked,
			boolean isDeleted, boolean isVerified,
			Set<UserUuid> userUuidEntities) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.secretQuestion = secretQuestion;
		this.secretAnswer = secretAnswer;
		this.mobile = mobile;
		this.sex = sex;
		this.age = age;
		this.registrationDate = registrationDate;
		this.isBlocked = isBlocked;
		this.isDeleted = isDeleted;
		this.isVerified = isVerified;
		this.userUuidEntities = userUuidEntities;
	
	}

	
	public Verification getUserVerificationEntity() {
		return verification;
	}

	public void setUserVerificationEntity(Verification verification) {
		this.verification = verification;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Country getCountryEntity() {
		return country;
	}
	public void setCountryEntity(Country country) {
		this.country = country;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSecretQuestion() {
		return secretQuestion;
	}
	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}
	public String getSecretAnswer() {
		return secretAnswer;
	}
	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Boolean getIsBlocked() {
		return isBlocked;
	}
	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Set<UserUuid> getUserUuidEntities() {
		return userUuidEntities;
	}

	public void setUserUuidEntities(Set<UserUuid> userUuidEntities) {
		this.userUuidEntities = userUuidEntities;
	}

}
