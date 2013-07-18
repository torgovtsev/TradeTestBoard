package com.google.code.jskills.domain;

import java.io.Serializable;

public class UserUuid implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private User userEntity;
	private String uuid;
	
	public UserUuid(){
		
	}
	
	public UserUuid(int id, User user, String uuid){
		this.userEntity = user;
		this.uuid = uuid;
		this.id = id;
	}
	
	public UserUuid(User user, String uuid){
		this.userEntity = user;
		this.uuid = uuid;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public User getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(User userEntity) {
		this.userEntity = userEntity;
	}

}
