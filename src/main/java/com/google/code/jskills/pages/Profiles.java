package com.google.code.jskills.pages;

import java.lang.annotation.Target;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import com.google.code.jskills.pages.master.MasterPage;
import com.google.code.jskills.pages.profile.ProfilesList;

@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class Profiles extends MasterPage {

	private static final long serialVersionUID = -6567180313769786297L;

	public Profiles(/*PageParameters param*/) {
		/*String val = param.get("shouldBeActive").toOptionalString();
		param.set("shouldBeActive", true);*/

		add(new Label("message", ""));
		add(new AjaxLink("profilesListPage") {
			private static final long serialVersionUID = -83165869298504489L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(ProfilesList.class);
			}
		});
	}

}
