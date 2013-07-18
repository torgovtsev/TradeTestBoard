package com.google.code.jskills.domain;

import java.io.Serializable;

public class Question implements Serializable{

	private static final long serialVersionUID = 354872207594528399L;

	private Integer id;
	private String questiontext;
	
	public Question() {
		
	}
	
	public Question(Integer id, String text) {
		this.id = id;
		this.setQuestiontext(text);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestiontext() {
		return questiontext;
	}

	public void setQuestiontext(String questiontext) {
		this.questiontext = questiontext;
	}
	
	
	
}
