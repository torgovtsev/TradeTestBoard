package com.google.code.jskills.business.services;

import java.util.List;

import com.google.code.jskills.domain.Competences;
import com.google.code.jskills.domain.Level;

public interface CompetencesService {

	Competences getCompetencesById(int id);
	
	List<Level> getLevelByLevelType(int levelType);
	
	List<Competences> getCompetencesByParentId(int parentId);

	List<Competences> getRootCompetences();
	
	void saveCompetences(Competences competences);

}
