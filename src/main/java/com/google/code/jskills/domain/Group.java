package com.google.code.jskills.domain;

import java.io.Serializable;

public class Group implements Serializable{

	private static final long serialVersionUID = 6639350160808657468L;

	private Integer id;
	private String name;
	private String description;
	
	public Group(){
		
	}
	
	public Group(Integer id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
