package com.google.code.jskills.dal.providers;

import com.google.code.jskills.domain.Settings;

public interface ApplicationSettingsDataProvider {

	Settings getSettingsById(int id);

}
