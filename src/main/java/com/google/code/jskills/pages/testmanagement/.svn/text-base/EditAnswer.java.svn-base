package com.google.code.jskills.pages.testmanagement;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.google.code.jskills.business.services.AnswerService;
import com.google.code.jskills.business.services.TestService;
import com.google.code.jskills.domain.Answer;
import com.google.code.jskills.domain.Test;
import com.google.code.jskills.pages.TestsManagement;
import com.google.code.jskills.pages.master.MasterPage;

public class EditAnswer extends TestsManagement{
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private transient AnswerService answerService;

	private FeedbackPanel feedbackPanel;
	private Answer selectedAnswer;
	
	public EditAnswer() {
		// TODO Auto-generated constructor stub
	}
	
	public EditAnswer(Answer answer){
		this.selectedAnswer = answer;
	}
	
	
	@Override
	protected void onInitialize() {
		
		super.onInitialize();
		
		Form<String> testForm = new Form<String>("editAnswerForm");
		add(testForm);

		/**
		 *  answertext
		 */
		
		testForm.add(new Label("textLabel", "Text:"));
		final TextField<String> testNameField = new TextField<String>(
				"textField", new PropertyModel<String>(this.selectedAnswer,
						"answertext"));
		testNameField.setRequired(true);
		testNameField.setOutputMarkupId(true);
		testForm.add(testNameField);
		
		/**
		 * point
		 */

		testForm.add(new Label("pointLabel",
				"Point: "));
		final TextField<String> testDescription = new TextField<String>(
				"pointField", new PropertyModel<String>(
						this.selectedAnswer, "point"));
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
				
				answerService.update(selectedAnswer);				
				target.add(feedbackPanel);
				setResponsePage(AnswerListPage.class);
			};

		};
		testForm.add(submitButton);
		
		/**
		 * Cancel button
		 */
		AjaxButton cancelButton = new AjaxButton("cancelButton") {
		
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
		testForm.add(cancelButton);
	}


	public Answer getSelectedAnswer() {
		return selectedAnswer;
	}


	public void setSelectedAnswer(Answer selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}
}
