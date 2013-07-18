package com.google.code.jskills.business.services.impl;

import java.util.List;

import javax.inject.Inject;

import com.google.code.jskills.business.services.ProfileService;
import com.google.code.jskills.dal.providers.ProfilesDataProvider;
import com.google.code.jskills.domain.Profile;

public class ProfileServiceImpl implements ProfileService {

	@Inject
	private transient ProfilesDataProvider dataProvider;

	@Override
	public void saveProfile(Profile profile) {
		dataProvider.saveProfile(profile);
	}

	@Override
	public void updateProfile(Profile profile) {
		dataProvider.updateProfile(profile);

	}

	@Override
	public void deleteProfileByID(int id) {
		Profile profile = dataProvider.getProfileByID(id);
		dataProvider.deleteProfile(profile);

	}

	@Override
	public Profile findProfileById(int id) {
		return dataProvider.getProfileByID(id);
	}

	@Override
	public List<Profile> getAllProfiles() {
		return dataProvider.getAllProfiles();
	}

}
