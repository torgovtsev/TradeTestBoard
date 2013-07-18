package com.google.code.jskills.business.services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.google.code.jskills.business.services.AnswerService;
import com.google.code.jskills.dal.providers.AnswerDataProvider;
import com.google.code.jskills.domain.Answer;

@Stateless
public class AnswerServiceImpl implements AnswerService{

	@Inject
	private AnswerDataProvider answerDataProvider;
	
	@Override
	public void create(Answer answer) {
		answerDataProvider.create(answer);
		
	}

	@Override
	public void update(Answer answer) {
		answerDataProvider.update(answer);
		
	}

	@Override
	public void delete(Answer answer) {
		answerDataProvider.delete(answer);
		
	}

	@Override
	public List<Answer> findAll() {
		return answerDataProvider.findAll();
	}

}
