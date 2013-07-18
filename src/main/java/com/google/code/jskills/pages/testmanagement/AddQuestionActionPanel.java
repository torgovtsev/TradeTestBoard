package com.google.code.jskills.pages.testmanagement;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.google.code.jskills.domain.Test;


public class  AddQuestionActionPanel extends Panel{
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory
			.getLogger(TestListPage.class);
	
	private Test selectedTest;

	public AddQuestionActionPanel(String id, final IModel<Test> model) {

		super(id);

		/*final ModalWindow modalWindow = new ModalWindow("testPageEdit");
		modalWindow.setCookieName("profilePageEdit-1");
		modalWindow.setAutoSize(true);

		add(modalWindow);*/

		add(new AjaxLink<Test>("addQuestion", model) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				
				 
		    	
		    	selectedTest = getModelObject();
		    	LOG.info("Test with id: " + getModelObject().getId() + " is selected");
		    	setResponsePage(new PickTestQuestion(selectedTest));
				/*selectedTest = getModelObject();
				PageParameters parameters = new PageParameters();
				parameters.add("testId", selectedTest.getId());
				setResponsePage(EditTest.class, parameters);*/
			}
		});
	}

}