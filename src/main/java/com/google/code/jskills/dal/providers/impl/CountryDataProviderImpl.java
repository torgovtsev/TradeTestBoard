package com.google.code.jskills.dal.providers.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.code.jskills.business.services.MapperService;
import com.google.code.jskills.dal.entities.CountryEntity;
import com.google.code.jskills.dal.providers.CountryDataProvider;
import com.google.code.jskills.domain.Country;
import com.google.code.jskills.utils.CollectionsUtils;

@Stateless
public class CountryDataProviderImpl implements CountryDataProvider {

	@PersistenceContext(unitName = "jskillsDB")
	private EntityManager entityManager;
	
	@Inject
	private MapperService mapperService;

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Country> getAllCountries() {
		final List<Country> result = new ArrayList<Country>();
		final Query query = entityManager.createQuery("select c from CountryEntity c");

		final List<CountryEntity> country = CollectionsUtils.castList(query.getResultList());
		for (CountryEntity dataEntity : country) {
			Country businessEntity = mapperService.mapTo(dataEntity, Country.class);
			result.add(businessEntity);
		}
		
		return result;
	}

	@Override
	@TransactionAttribute
	public void saveCountry(Country country) {
		final CountryEntity dataEntity = mapperService.mapTo(country, CountryEntity.class);
		dataEntity.setId(null);
		entityManager.persist(dataEntity);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Country findCountryByName(String countryName) {
		final Query query = entityManager.createQuery("select c from CountryEntity c where c.name = :countryName");
		CountryEntity dataEntity = (CountryEntity) query.setParameter("countryName", countryName).getSingleResult();		
		Country businessEntity = mapperService.mapTo(dataEntity, Country.class);				
		return businessEntity;
	}
}
