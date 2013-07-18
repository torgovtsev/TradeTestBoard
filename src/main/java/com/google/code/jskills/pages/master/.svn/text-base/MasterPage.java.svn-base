package com.google.code.jskills.pages.master;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.resource.CssResourceReference;

public class MasterPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public MasterPage() {
		add(new Label("header", " "));
		add(new NavigationPanel("navigationPanel"));
		add(new Label("footer", "© T-UNI 2013"));
	}

	@Override
	public void renderHead(IHeaderResponse response) {

		response.render(CssHeaderItem.forReference(new CssResourceReference(
				NavigationPanel.class, "bootstrap.min.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				MasterPage.class, "add.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				MasterPage.class, "bootstrap.css")));

	}
	
}
