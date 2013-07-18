package com.google.code.jskills.pages.testmanagement;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.google.code.jskills.business.services.TestService;
import com.google.code.jskills.domain.Country;
import com.google.code.jskills.domain.Test;
import com.google.code.jskills.pages.TestsManagement;
import com.google.code.jskills.pages.admin.UsersList;
import com.google.code.jskills.pages.master.MasterPage;

public class EditTest  extends TestsManagement{

	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private transient TestService testService;

	private FeedbackPanel feedbackPanel;
	private Test selectedTest;
	
	
	public Test getSelectedTest() {
		return selectedTest;
	}

	public void setSelectedTest(Test selectedTest) {
		this.selectedTest = selectedTest;
	}
	
	public EditTest() {
	}
	
	public EditTest(Test test){
		this.selectedTest = test;
	}

	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		Form<String> testForm = new Form<String>("editTestForm");
		add(testForm);

		/**
		 *  Name
		 */
		
		testForm.add(new Label("testNameLabel", "Name:"));
		final TextField<String> testNameField = new TextField<String>(
				"testNameField", new PropertyModel<String>(this.selectedTest,
						"name"));
		testNameField.setRequired(true);
		testNameField.setOutputMarkupId(true);
		testForm.add(testNameField);
		
		/**
		 * Description
		 */

		testForm.add(new Label("descriptionLabel",
				"Description: "));
		final TextField<String> testDescription = new TextField<String>(
				"testDescription", new PropertyModel<String>(
						this.selectedTest, "description"));
		testDescription.setRequired(true);
		testDescription.setOutputMarkupId(true);
		testForm.add(testDescription);
		
		/**
		 * feedbackpanel
		 */
		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
		/**
		 * Save button
		 */
		AjaxButton submitButton = new AjaxButton("submitButton") {
			private static final long serialVersionUID = 1L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				testService.update(selectedTest);				
				target.add(feedbackPanel);
				setResponsePage(TestListPage.class);
			};

		};
		testForm.add(submitButton);
		
		/**
		 * Cancel button
		 */
		AjaxButton cancelButton = new AjaxButton("cancelButton") {
		
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
			
				target.add(feedbackPanel);
				setResponsePage(TestListPage.class);
			};		
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);

				setResponsePage(TestListPage.class);
			}
		};
		testForm.add(cancelButton);
		

	}


}
