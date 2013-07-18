package com.google.code.jskills.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import com.google.code.jskills.pages.admin.UsersList;
import com.google.code.jskills.pages.master.MasterPage;
import com.google.code.jskills.pages.testmanagement.AnswerListPage;
import com.google.code.jskills.pages.testmanagement.QuestionListPage;
import com.google.code.jskills.pages.testmanagement.TestListPage;

@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class TestsManagement extends MasterPage{

	public TestsManagement(){
		
		add(new AjaxLink<String>("testListPage") {
	
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(TestListPage.class);				
			}
		});
		
		add(new AjaxLink<String>("questionListPage") {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(QuestionListPage.class);				
			}
		});
		
		add(new AjaxLink<String>("answerListPage") {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(AnswerListPage.class);				
			}
		});

	}
}
