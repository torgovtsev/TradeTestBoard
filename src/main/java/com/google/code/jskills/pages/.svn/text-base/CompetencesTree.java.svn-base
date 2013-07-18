package com.google.code.jskills.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import com.google.code.jskills.pages.competence.CompetencesPage;
import com.google.code.jskills.pages.master.MasterPage;

@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class CompetencesTree extends MasterPage {

	private static final long serialVersionUID = 8844347990840415443L;

	public CompetencesTree() {
		
		add(new AjaxLink<String>("competencesPage") {

			private static final long serialVersionUID = -7984554952482062186L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(CompetencesPage.class);				
			}
		});
	}
}
