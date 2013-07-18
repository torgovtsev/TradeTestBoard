package com.google.code.jskills.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Competences implements Serializable{
	
	private static final long serialVersionUID = 6703405717949778487L;
	
	private Integer id;
	private Competences competences;
	private String description;
	private Set<Level> levels = new HashSet<Level>(0);
	
	public Competences() {
		
	}
	
	public Competences(Competences competences, String description) {
		this.competences = competences;
		this.description = description;
	}
	
	public Set<Level> getLevels() {
		return levels;
	}

	public void setLevels(Set<Level> levels) {
		this.levels = levels;
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
}
