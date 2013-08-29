package com.google.code.jskills.domain;

import java.io.Serializable;

public class Level implements Serializable{

	private static final long serialVersionUID = 9101296483085167992L;
	
	private Integer id;
	private Competences competences;
	private String description;
	private int levelType;

	public Level() {
		competences = new Competences();
	}
	
	public Level(String description, int levelType) {
		this.description = description;
		this.levelType = levelType;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Competences getCompetences() {
		return competences;
	}

	public void setCompetences(Competences competences) {
		this.competences = competences;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLevelType() {
		return levelType;
	}

	public void setLevelType(int levelType) {
		this.levelType = levelType;
	}
}
