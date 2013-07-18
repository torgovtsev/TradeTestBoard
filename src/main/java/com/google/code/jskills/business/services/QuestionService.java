package com.google.code.jskills.business.services;

import java.util.List;
import java.util.Set;

import com.google.code.jskills.domain.Permission;
import com.google.code.jskills.domain.Question;
import com.google.code.jskills.domain.Role;
import com.google.code.jskills.domain.Test;

public interface QuestionService {
    void create(Question question);
	
	void update(Question question);
	
	void delete(Question question);
	
	List<Question> findAll();
	
	public Set<Question> getQuestionForTest(Test test);
	
	 void updateQuestionForTest(Test role, List<Question> rolePermissions);

}
