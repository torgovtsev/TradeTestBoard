package com.google.code.jskills.business.services.impl;

import javax.inject.Inject;

import com.google.code.jskills.business.services.ApplicationSettingsService;
import com.google.code.jskills.dal.providers.ApplicationSettingsDataProvider;
import com.google.code.jskills.domain.Settings;

public class ApplicationSettingsServiceImpl implements
		ApplicationSettingsService {

	@Inject
	private ApplicationSettingsDataProvider dataProvider;
	
	@Override
	public Settings getSettingsById(int id) {
		return dataProvider.getSettingsById(id);
	}

}
