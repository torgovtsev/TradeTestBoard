package com.google.code.jskills.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import com.google.code.jskills.pages.master.MasterPage;

@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class TestsExecution extends MasterPage {
	
	public TestsExecution(){
		add(new Label("message", "This is TestsExecution page"));
	}

}
