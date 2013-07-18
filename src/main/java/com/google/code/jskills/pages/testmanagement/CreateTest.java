package com.google.code.jskills.pages.testmanagement;

import javax.inject.Inject;

import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.TestService;

import com.google.code.jskills.domain.Test;




public class CreateTest extends WebPage{
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory
			.getLogger(CreateTest.class);

	@Inject
	private transient TestService testService;

	private Test createdTest;
	private FeedbackPanel feedbackPanel;
	private Form<String> createTestForm;
	final AjaxLink close;
	
	public Test getCreatedTest() {
		return createdTest;
	}
	public void setCreatedTest(Test createdTest) {
		this.createdTest = createdTest;
	}
	
	/**
	 * 
	 * @param pageReference
	 * @param modalWindowPage
	 * @param window
	 */
	public CreateTest(PageReference pageReference, final ModalWindow window) {

			
		close = new AjaxLink<Void>("close") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				window.close(target);
				target.add(feedbackPanel);

			}
		};
		close.setOutputMarkupId(true);
		add(close);

	}
	
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		createdTest = new Test();
		/**
		 * Feedback panel
		 */

		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		createTestForm = new Form<String>("createTestForm");
		createTestForm.setOutputMarkupId(true);
		add(createTestForm);
		/**
		 * Test Name
		 */
		createTestForm.add(new Label("testNameLabel", "Name:"));
		final TextField<String> testNameField = new TextField<String>(
				"testName", new PropertyModel<String>(this.createdTest,
						"name"));
		testNameField.setRequired(true);
		testNameField.setOutputMarkupId(true);
		createTestForm.add(testNameField);
		
		/**
		 * Description
		 */

		createTestForm.add(new Label("descriptionLabel",
				"Description: "));
		final TextField<String> testDescription = new TextField<String>(
				"testDescription", new PropertyModel<String>(
						this.createdTest, "description"));
		testDescription.setRequired(true);
		testDescription.setOutputMarkupId(true);
		createTestForm.add(testDescription);

		/**
		 * Submit button
		 */
		
		AjaxButton createButton = new AjaxButton("createTest") {

			private static final long serialVersionUID = -7127248610161307777L;

			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				testService.create(createdTest);
				target.add(feedbackPanel);
				close.onClick(target);
				

			};

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
				super.onError(target, form);
			}

		};

		createButton.setOutputMarkupId(true);
		createTestForm.add(createButton);

		
	}




}
