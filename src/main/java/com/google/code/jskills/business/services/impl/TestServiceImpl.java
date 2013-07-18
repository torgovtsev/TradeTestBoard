package com.google.code.jskills.business.services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.google.code.jskills.business.services.TestService;
import com.google.code.jskills.dal.providers.TestDataProvider;
import com.google.code.jskills.domain.Test;

@Stateless
public class TestServiceImpl implements TestService{

	@Inject
	private transient TestDataProvider testDataProvider;
	
	@Override
	public void create(Test test) {
		testDataProvider.create(test);
		
	}

	@Override
	public void update(Test test) {
		testDataProvider.update(test);
		
	}

	@Override
	public void delete(Test test) {
		testDataProvider.delete(test);
		
	}

	@Override
	public List<Test> findAll() {
		return testDataProvider.findAll();
	}

}
