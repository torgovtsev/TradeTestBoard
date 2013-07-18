package com.google.code.jskills.domain;

import java.io.Serializable;

public class Settings implements Serializable{

	private static final long serialVersionUID = 2548514937192094579L;
	
	private Integer id;
	private String value;
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
