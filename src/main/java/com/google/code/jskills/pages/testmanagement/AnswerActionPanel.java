package com.google.code.jskills.pages.testmanagement;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.google.code.jskills.domain.Answer;
import com.google.code.jskills.domain.Test;


public class  AnswerActionPanel extends Panel{
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory
			.getLogger(AnswerListPage.class);
	
	private Answer selectedAnswer;

	public AnswerActionPanel(String id, final IModel<Answer> model) {

		super(id);


		add(new AjaxLink<Answer>("editAnswer", model) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				
				 
		    	
		    	selectedAnswer = getModelObject();
		    	LOG.info("Test with id: " + getModelObject().getId() + " is selected");
		    	setResponsePage(new EditAnswer(selectedAnswer));
				/*selectedTest = getModelObject();
				PageParameters parameters = new PageParameters();
				parameters.add("testId", selectedTest.getId());
				setResponsePage(EditTest.class, parameters);*/
			}
		});
	}

}