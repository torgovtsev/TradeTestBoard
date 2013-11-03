package com.google.code.jskills.domain;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class Test implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String description;

	public Test() {

	}

	public Test(String name) {
		this.name = name;
	}

	public Test(Integer id, String name, String description) {
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
		if (description == null) {
			return StringUtils.EMPTY;
		} else {
			return description;
		}

	}

	public void setDescription(String description) {
		this.description = description;
	}

}
