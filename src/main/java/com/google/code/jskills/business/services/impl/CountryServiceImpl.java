package com.google.code.jskills.business.services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.google.code.jskills.business.services.CountryService;
import com.google.code.jskills.dal.providers.CountryDataProvider;
import com.google.code.jskills.domain.Country;

@Stateless
public class CountryServiceImpl implements CountryService {

	@Inject
	private transient CountryDataProvider dataProvider;

	@Override
	public List<Country> getAllCountries() {
		return dataProvider.getAllCountries();
	}

	@Override
	public void saveCountry(final Country country) {
		dataProvider.saveCountry(country);
	}

	@Override
	public Country findCountryByName(String countryName) {

		if (StringUtils.isBlank(countryName)) {
			return new Country();
		}

		return dataProvider.findCountryByName(countryName);
	}

}
