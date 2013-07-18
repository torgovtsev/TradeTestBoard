package com.google.code.jskills.dal.providers;

import java.util.List;

import com.google.code.jskills.domain.Competences;
import com.google.code.jskills.domain.Level;

public interface CompetencesDataProvider {
	
	Competences getCompetencesById(int id);
	
	List<Level> getLevelByLevelType(int levelType);
	
	List<Competences> getCompetencesByParentId(int parentId);

	List<Competences> getRootCompetences();
	
	void saveCompetence(Competences competences);
	
}
