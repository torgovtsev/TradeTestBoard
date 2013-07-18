package com.google.code.jskills.pages.testmanagement;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.google.code.jskills.domain.Question;
import com.google.code.jskills.domain.Test;


public class  QuestionActionPanel extends Panel{
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory
			.getLogger(QuestionListPage.class);
	
	private Question selectedQuestion;

	public QuestionActionPanel(String id, final IModel<Question> model) {

		super(id);

		add(new AjaxLink<Question>("editQuestion", model) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				
				 
		    	
		    	selectedQuestion = getModelObject();
		    	LOG.info("Question with id: " + getModelObject().getId() + " is selected");
		    	setResponsePage(new EditQuestion(selectedQuestion));
				/*selectedTest = getModelObject();
				PageParameters parameters = new PageParameters();
				parameters.add("testId", selectedTest.getId());
				setResponsePage(EditTest.class, parameters);*/
			}
		});
	}

}