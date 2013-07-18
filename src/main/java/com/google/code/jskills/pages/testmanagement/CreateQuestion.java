package com.google.code.jskills.pages.testmanagement;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.QuestionService;
import com.google.code.jskills.domain.Question;
import com.google.code.jskills.pages.TestsManagement;

public class CreateQuestion extends TestsManagement{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(CreateTest.class);

	@Inject
	private transient QuestionService questionService;

	private Question createdQuestion;
	private FeedbackPanel feedbackPanel;
	private Form<String> createQuestionForm;
	

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		createdQuestion = new Question();
		
		/**
		 * Feedback panel
		 */

		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		createQuestionForm = new Form<String>("createQuestionForm");
		createQuestionForm.setOutputMarkupId(true);
		add(createQuestionForm);
		
		/**
		 * Text
		 */
		createQuestionForm.add(new Label("textLabel", "Question text:"));
		final TextField<String> testNameField = new TextField<String>(
				"textField", new PropertyModel<String>(this.createdQuestion,
						"questiontext"));
		testNameField.setRequired(true);
		testNameField.setOutputMarkupId(true);
		createQuestionForm.add(testNameField);
		/**
		 * Submit button
		 */
		
		AjaxButton createButton = new AjaxButton("submitButton") {

			private static final long serialVersionUID = -7127248610161307777L;

			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				questionService.create(createdQuestion);
				target.add(feedbackPanel);
				setResponsePage(QuestionListPage.class);
				

			};

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
				super.onError(target, form);
			}

		};

		createButton.setOutputMarkupId(true);
		createQuestionForm.add(createButton);
		
		/**
		 * Cancel button
		 */
		AjaxButton cancelButton = new AjaxButton("cancelButton") {

			private static final long serialVersionUID = -7102206967781963706L;
			
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
			
				target.add(feedbackPanel);
				setResponsePage(QuestionListPage.class);
			};		
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);

				setResponsePage(QuestionListPage.class);
			}
		};
		createQuestionForm.add(cancelButton);
		
		
		
		
		
	}


	public Question getCreatedQuestion() {
		return createdQuestion;
	}


	public void setCreatedQuestion(Question createdQuestion) {
		this.createdQuestion = createdQuestion;
	}

}
