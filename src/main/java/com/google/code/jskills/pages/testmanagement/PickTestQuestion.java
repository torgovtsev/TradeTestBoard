package com.google.code.jskills.pages.testmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.model.util.ListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.PermissionService;
import com.google.code.jskills.business.services.QuestionService;
import com.google.code.jskills.domain.Permission;
import com.google.code.jskills.domain.Question;
import com.google.code.jskills.domain.Role;
import com.google.code.jskills.domain.Test;
import com.google.code.jskills.pages.TestsManagement;
import com.google.code.jskills.pages.admin.EditRole;
import com.google.code.jskills.pages.admin.PickRolePermissions;
import com.google.code.jskills.pages.admin.RolesList;

public class PickTestQuestion extends TestsManagement {

	@Inject
	private transient QuestionService questionService;

	private static final Logger LOG = LoggerFactory
			.getLogger(PickRolePermissions.class);

	private FeedbackPanel feedbackPanel;
	private Test selectedTest;
	private List<Question> questionList;

	public Test getSelectedTest() {
		return selectedTest;
	}

	public void setSelectedTest(Test selectedTest) {
		this.selectedTest = selectedTest;
	}

	public PickTestQuestion() {

	}

	public PickTestQuestion(Test test) {
		this.selectedTest = test;

	}
	
	@Override
	protected void onInitialize() {
		// TODO Auto-generated method stub
		super.onInitialize();
		
		Form<String> pickListForm = new Form<String>("pickRolePermissionsForm");
		add(pickListForm);
		pickListForm.add(new Label("contentMessage", " Pick Tests's Questions"));
		
		/**
		 * Palette(PickList) for Role's Permissions
		 */
		List<Question> allQuestions = questionService.findAll();
		IChoiceRenderer<Question> renderer = new ChoiceRenderer<Question>("questiontext", "questiontext");
		Set<Question> rolePermissions = questionService.getQuestionForTest(selectedTest);
		questionList = new ArrayList<Question>(rolePermissions);

		final Palette<Question> palette = new Palette<Question>("pickRolePermissionsPalette",
				new ListModel<Question>(questionList),
				new CollectionModel<Question>(allQuestions),renderer, 10, false);
		pickListForm.add(palette);
		
		/**
		 * Feedback panel
		 */
		feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
		/**
		 * Save button
		 */
		AjaxButton saveButton = new AjaxButton("saveButton") {
			private static final long serialVersionUID = 1L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				questionService.updateQuestionForTest(selectedTest, questionList);
				setResponsePage(TestListPage.class);
			};
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		};
		pickListForm.add(saveButton);
		
		/**
		 * Cancel button
		 */
		AjaxButton cancelButton = new AjaxButton("cancelButton") {
			private static final long serialVersionUID = 902652892993225517L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
				Page page = new EditTest(selectedTest);
				setResponsePage(page);
			};
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
				Page page = new EditTest(selectedTest);
				setResponsePage(page);
			}
		};
		pickListForm.add(cancelButton);
	}

}
