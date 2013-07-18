package com.google.code.jskills.pages.testmanagement;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.google.code.jskills.business.services.QuestionService;
import com.google.code.jskills.business.services.TestService;
import com.google.code.jskills.domain.Question;
import com.google.code.jskills.domain.Test;
import com.google.code.jskills.pages.TestsManagement;
import com.google.code.jskills.pages.master.MasterPage;

public class EditQuestion extends TestsManagement{
	
	@Inject
	private transient QuestionService questionService;
	
	private FeedbackPanel feedbackPanel;
	private Question selectedQuestion;

	
	
	public EditQuestion() {
		// TODO Auto-generated constructor stub
	}
	
	public EditQuestion(Question question){
		this.selectedQuestion = question;
	}
	
	@Override
	protected void onInitialize() {
		// TODO Auto-generated method stub
		super.onInitialize();
		
		Form<String> testForm = new Form<String>("editQuestionForm");
		add(testForm);

		/**
		 *  Name
		 */
		
		testForm.add(new Label("textLabel", "Text:"));
		final TextField<String> testNameField = new TextField<String>(
				"textField", new PropertyModel<String>(this.selectedQuestion,
						"questiontext"));
		testNameField.setRequired(true);
		testNameField.setOutputMarkupId(true);
		testForm.add(testNameField);
		
		
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
				
				questionService.update(selectedQuestion);				
				target.add(feedbackPanel);
				setResponsePage(QuestionListPage.class);
			};

		};
		testForm.add(submitButton);
		
		/**
		 * Cancel button
		 */
		AjaxButton cancelButton = new AjaxButton("cancelButton") {
		
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
		testForm.add(cancelButton);
		
	}

	public Question getSelectedQuestion() {
		return selectedQuestion;
	}

	public void setSelectedQuestion(Question selectedQuestion) {
		this.selectedQuestion = selectedQuestion;
	}

}
