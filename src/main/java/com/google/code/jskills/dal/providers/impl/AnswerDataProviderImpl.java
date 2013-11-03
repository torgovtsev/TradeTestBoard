package com.google.code.jskills.dal.providers.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.MapperService;
import com.google.code.jskills.dal.entities.AnswerEntity;
import com.google.code.jskills.dal.providers.AnswerDataProvider;
import com.google.code.jskills.domain.Answer;
import com.google.code.jskills.utils.CollectionsUtils;

@Stateless
public class AnswerDataProviderImpl implements AnswerDataProvider {

	private static final Logger LOG = LoggerFactory
			.getLogger(AnswerDataProviderImpl.class);

	@PersistenceContext(unitName = "jskillsDB")
	private EntityManager entityManager;

	@Inject
	private MapperService mapperService;

	@Override
	public void create(Answer answer) {

		if (answer == null) {
			LOG.error("ANswer can't be null");
			return;
		}

		final AnswerEntity dataEntity = mapperService.mapTo(answer,
				AnswerEntity.class);
		entityManager.persist(dataEntity);
	}

	@Override
	public void update(Answer answer) {

		if (answer == null) {
			LOG.error("ANswer can't be null");
			return;
		}

		final AnswerEntity dataEntity = mapperService.mapTo(answer,
				AnswerEntity.class);
		entityManager.merge(dataEntity);

	}

	@Override
	public void delete(Answer answer) {

		if (answer == null) {
			LOG.error("ANswer can't be null");
			return;
		}

		final AnswerEntity dataEntity = mapperService.mapTo(answer,
				AnswerEntity.class);
		entityManager.remove(dataEntity);
	}

	@Override
	public List<Answer> findAll() {
		List<Answer> answersList = new ArrayList<Answer>();
		Query query = entityManager.createQuery("select a from AnswerEntity a");
		List<AnswerEntity> answerEntityList = CollectionsUtils.castList(query
				.getResultList());

		for (AnswerEntity businessEntity : answerEntityList) {
			Answer answer = mapperService.mapTo(businessEntity, Answer.class);
			answersList.add(answer);
		}

		return answersList;
	}

}
