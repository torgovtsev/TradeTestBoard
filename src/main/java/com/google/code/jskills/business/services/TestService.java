package com.google.code.jskills.business.services;

import java.util.List;

import com.google.code.jskills.domain.Test;

public interface TestService {
	void create(Test test);
	
	void update(Test test);
	
	void delete(Test test);
	
	List<Test> findAll();
}
