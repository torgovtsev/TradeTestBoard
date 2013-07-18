package com.google.code.jskills.dal.providers;

import java.util.List;

import com.google.code.jskills.domain.Answer;

public interface AnswerDataProvider {

	void create(Answer answer);

	void update(Answer answer);

	void delete(Answer answer);

	List<Answer> findAll();

}
