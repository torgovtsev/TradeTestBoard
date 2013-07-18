package com.google.code.jskills.business.services.impl;



import javax.ejb.Singleton;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.MapperService;
import com.google.code.jskills.dal.entities.ApplicationSettingsEntity;
import com.google.code.jskills.dal.entities.CountryEntity;
import com.google.code.jskills.dal.entities.QuestionEntity;
import com.google.code.jskills.dal.entities.TestEntity;
import com.google.code.jskills.dal.entities.UserEntity;
import com.google.code.jskills.dal.entities.UserUuidEntity;
import com.google.code.jskills.dal.entities.UserVerificationEntity;
import com.google.code.jskills.domain.Country;
import com.google.code.jskills.domain.Question;
import com.google.code.jskills.domain.Settings;
import com.google.code.jskills.domain.Test;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.domain.UserUuid;
import com.google.code.jskills.domain.Verification;
import com.google.code.jskills.domain.Level;
import com.google.code.jskills.dal.entities.LevelEntity;
import com.google.code.jskills.domain.Competences;
import com.google.code.jskills.dal.entities.CompetencesEntity;

@Singleton
public class MapperServiceImpl implements MapperService {

	private static final Logger LOG = LoggerFactory.getLogger(MapperServiceImpl.class);

	private DozerBeanMapper mapper;

	public MapperServiceImpl() {
		mapper = initMapper();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T mapTo(Object source, Class<?> destinationClass) {
		return (T) mapper.map(source, destinationClass);
	}

	private DozerBeanMapper initMapper() {

		LOG.info("Started DozerBeanMapper initialization");

		BeanMappingBuilder builder = new BeanMappingBuilder() {

			@Override
			protected void configure() {
				mapping(QuestionEntity.class, Question.class);
				mapping(TestEntity.class, Test.class);
				mapping(UserUuidEntity.class, UserUuid.class);
				
				mapping(CountryEntity.class, Country.class);
				mapping(UserEntity.class, User.class);
				mapping(UserVerificationEntity.class, Verification.class);
				mapping(ApplicationSettingsEntity.class, Settings.class);
				mapping(LevelEntity.class, Level.class);
				mapping(CompetencesEntity.class, Competences.class);
			}
		};

		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.addMapping(builder);

		LOG.info("Finished DozerBeanMapper initialization");

		return mapper;
	}

}
