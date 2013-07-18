package com.google.code.jskills.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import com.google.code.jskills.pages.master.MasterPage;

@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class Index extends MasterPage {

	private static final long serialVersionUID = 1L;

	@Override
	protected void onConfigure() {
		add(new Label("message", String.format(" ",
				" ")));
	}

	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
	}
}
