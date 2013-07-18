package com.google.code.jskills.business.services.impl;

import java.util.List;

import javax.inject.Inject;
import com.google.code.jskills.business.services.CompetencesService;
import com.google.code.jskills.dal.providers.CompetencesDataProvider;
import com.google.code.jskills.domain.Competences;
import com.google.code.jskills.domain.Level;

public class CompetencesServiceImpl implements CompetencesService{
	
	@Inject
	private CompetencesDataProvider dataProvider;
	
	@Override
	public Competences getCompetencesById(int id) {
		return dataProvider.getCompetencesById(id);
	}

	@Override
	public List<Level> getLevelByLevelType(int levelType) {
		return dataProvider.getLevelByLevelType(levelType);
	}

	@Override
	public List<Competences> getCompetencesByParentId(int parentId) {
		return dataProvider.getCompetencesByParentId(parentId);
	}
	
	@Override
	public List<Competences> getRootCompetences() {
		return dataProvider.getRootCompetences();
	}
	
	@Override
	public void saveCompetences(Competences competences) {
		dataProvider.saveCompetence(competences);
	}
	
}
