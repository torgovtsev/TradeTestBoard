package com.google.code.jskills.business.services;

import java.util.List;
import com.google.code.jskills.domain.Answer;

public interface AnswerService {
	void create(Answer answer);

	void update(Answer answer);

	void delete(Answer answer);

	List<Answer> findAll();
}
