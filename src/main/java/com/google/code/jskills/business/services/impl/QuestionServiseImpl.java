package com.google.code.jskills.business.services.impl;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.google.code.jskills.business.services.QuestionService;
import com.google.code.jskills.dal.providers.QuestionDataProvider;
import com.google.code.jskills.domain.Permission;
import com.google.code.jskills.domain.Question;
import com.google.code.jskills.domain.Test;

@Stateless
public class QuestionServiseImpl implements QuestionService {

	@Inject
	QuestionDataProvider questionDataProvider;
	
	@Override
	public void create(Question question) {
		questionDataProvider.create(question);
		
	}

	@Override
	public void update(Question question) {
		questionDataProvider.update(question);
		
	}

	@Override
	public void delete(Question question) {
		questionDataProvider.delete(question);
		
	}

	@Override
	public List<Question> findAll() {
		return questionDataProvider.findAll();
	}

	@Override
	public Set<Question> getQuestionForTest(Test test) {
		// TODO Auto-generated method stub
		return questionDataProvider.getQuestionForTest(test);
	}

	@Override
	public void updateQuestionForTest(Test role, List<Question> rolePermissions) {
		questionDataProvider.updateQuestionForTest(role, rolePermissions);
		
	}

}
