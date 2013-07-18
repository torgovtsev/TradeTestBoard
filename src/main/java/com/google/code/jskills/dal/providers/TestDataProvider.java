package com.google.code.jskills.dal.providers;

import java.util.List;

import com.google.code.jskills.domain.Test;

public interface TestDataProvider {
	void create(Test test);
	
	void update(Test test);
	
	void delete(Test test);
	
	List<Test> findAll();

}
