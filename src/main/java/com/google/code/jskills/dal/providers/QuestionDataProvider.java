package com.google.code.jskills.dal.providers;

import java.util.List;
import java.util.Set;

import com.google.code.jskills.domain.Permission;
import com.google.code.jskills.domain.Question;
import com.google.code.jskills.domain.Test;

public interface QuestionDataProvider {

	void create(Question question);
	
	void update(Question question);
	
	void delete(Question question);
	
	List<Question> findAll();
	
	Set<Question> getQuestionForTest(Test test);
	
	 void updateQuestionForTest(Test role, List<Question> rolePermissions);
}
