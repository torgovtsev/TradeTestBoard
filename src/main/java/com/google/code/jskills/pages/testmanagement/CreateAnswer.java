package com.google.code.jskills.pages.testmanagement;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.AnswerService;
import com.google.code.jskills.business.services.QuestionService;
import com.google.code.jskills.domain.Answer;
import com.google.code.jskills.domain.Country;
import com.google.code.jskills.domain.Question;
import com.google.code.jskills.pages.TestsManagement;
import com.google.code.jskills.pages.master.MasterPage;

public class CreateAnswer extends TestsManagement{

	private static final long serialVersionUID = 3729694863882682955L;

	private static final Logger LOG = LoggerFactory
			.getLogger(CreateAnswer.class);

	@Inject
	private transient AnswerService answerService;
	
	@Inject
	private transient QuestionService questionService;

	private Answer createdAnswer;
	private Question selectedQuestion;
	private FeedbackPanel feedbackPanel;
	private Form<String> createAnswerForm;
	
	
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		createdAnswer = new Answer();
		
		
		/**
		 * Feedback panel
		 */

		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		createAnswerForm = new Form<String>("createAnswerForm");
		createAnswerForm.setOutputMarkupId(true);
		add(createAnswerForm);
		
		/**
		 * Country name 
		 */
		createAnswerForm.add(new Label("questionLabel", "Question:"));
		final List<Question> questionsList = questionService.findAll();
		DropDownChoice<Question> countries = new DropDownChoice<Question>(
				"questionChoise", new PropertyModel<Question>(this.createdAnswer, "questionEntity"),
				new ListModel<Question>( questionsList),
				new ChoiceRenderer<Question>("questiontext"));
		createAnswerForm.add(countries);
		
		/**
		 * Text
		 */
		createAnswerForm.add(new Label("textLabel", "Answer text:"));
		final TextField<String> testNameField = new TextField<String>(
				"textField", new PropertyModel<String>(this.createdAnswer,
						"answertext"));
		testNameField.setRequired(true);
		testNameField.setOutputMarkupId(true);
		createAnswerForm.add(testNameField);
		
		
		/**
		 * Point
		 */
		createAnswerForm.add(new Label("pointLabel", "Point:"));
		final TextField<String> pointField = new TextField<String>(
				"pointField", new PropertyModel<String>(this.createdAnswer,
						"point"));
		pointField.setRequired(true);
		pointField.setOutputMarkupId(true);
		createAnswerForm.add(pointField);
		
		
		/**
		 * Submit button
		 */
		
		AjaxButton createButton = new AjaxButton("submitButton") {

			private static final long serialVersionUID = -7127248610161307777L;

			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				answerService.create(createdAnswer);
				target.add(feedbackPanel);
				setResponsePage(AnswerListPage.class);
				

			};

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
				super.onError(target, form);
			}

		};

		createButton.setOutputMarkupId(true);
		createAnswerForm.add(createButton);
		
		/**
		 * Cancel button
		 */
		AjaxButton cancelButton = new AjaxButton("cancelButton") {
			
			private static final long serialVersionUID = -2203329028733835935L;
			
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
			
				target.add(feedbackPanel);
				setResponsePage(AnswerListPage.class);
			};		
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);

				setResponsePage(AnswerListPage.class);
			}
		};
		createAnswerForm.add(cancelButton);
		
	}



	public Answer getCreatedAnswer() {
		return createdAnswer;
	}



	public void setCreatedAnswer(Answer createdAnswer) {
		this.createdAnswer = createdAnswer;
	}



	public Question getSelectedQuestion() {
		return selectedQuestion;
	}



	public void setSelectedQuestion(Question selectedQuestion) {
		this.selectedQuestion = selectedQuestion;
	}

}
