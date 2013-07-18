package com.google.code.jskills.business.services;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

/*import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;*/
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.code.jskills.dal.providers.CountryDataProvider;
import com.google.code.jskills.domain.Country;

/*@RunWith(Arquillian.class)*/
public class CountryServiceTest {
/*
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(CountryService.class.getPackage())
				.addPackage(CountryDataProvider.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	private EntityManager em;

	@Inject
	private CountryService service;

	@Test
	public void foo() {
		List<Country> countries = service.getAllCountries();
		Assert.assertNotNull(countries);
	}*/

}
