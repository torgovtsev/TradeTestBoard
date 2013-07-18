package com.google.code.jskills.pages.master;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import com.google.code.jskills.pages.Dashboard;
import com.google.code.jskills.pages.Profiles;
import com.google.code.jskills.pages.Report;
import com.google.code.jskills.pages.TestsExecution;
import com.google.code.jskills.pages.TestsManagement;
import com.google.code.jskills.pages.admin.AdminHome;
import com.google.code.jskills.pages.admin.PermissionsList;
import com.google.code.jskills.pages.admin.RolesList;
import com.google.code.jskills.pages.admin.UsersList;
import com.google.code.jskills.pages.competence.CompetencesPage;
import com.google.code.jskills.pages.profile.ProfilesList;
import com.google.code.jskills.pages.testmanagement.AnswerListPage;
import com.google.code.jskills.pages.testmanagement.QuestionListPage;
import com.google.code.jskills.pages.testmanagement.TestListPage;

public class NavigationPanel extends Panel {
	private static final long serialVersionUID = 1L;
	private PageParameters pars;

	public NavigationPanel(String id) {
		super(id);

		/*
		 * pars = new PageParameters(); pars.add("shouldBeActive", false);
		 * 
		 * String message; if (pars == null ||
		 * pars.get("shouldBeActive").toOptionalString() == null) { message =
		 * "This is the default message"; } else { message =
		 * pars.get("shouldBeActive").toOptionalString(); }
		 */

		final WebMarkupContainer li = new WebMarkupContainer("mainContainer");

		// Add a label to display the message
		// li.add(new Label("messageLabel", message));
		li.setOutputMarkupId(true);

		final BookmarkablePageLink<Void> adminTools = new BookmarkablePageLink<Void>(
				"adminTools", AdminHome.class);
		li.add(adminTools).getPath();
		final BookmarkablePageLink<Void> dashboard = new BookmarkablePageLink<Void>(
				"dashboard", Dashboard.class);
		li.add(dashboard);
		final BookmarkablePageLink<Void> competencesTree = new BookmarkablePageLink<Void>(
				"competencesTree", CompetencesPage.class);
		li.add(competencesTree);
		final BookmarkablePageLink<Void> profile = new BookmarkablePageLink<Void>(
				"profiles", Profiles.class);
		li.add(profile);
		final BookmarkablePageLink<Void> testManagement = new BookmarkablePageLink<Void>(
				"testManagement", TestsManagement.class);
		li.add(testManagement);
		final BookmarkablePageLink<Void> testExecution = new BookmarkablePageLink<Void>(
				"testExecution", TestsExecution.class);
		li.add(testExecution);
		final BookmarkablePageLink<Void> report = new BookmarkablePageLink<Void>(
				"report", Report.class);
		li.add(report);

		adminTools.add(new Behavior() {
			private static final long serialVersionUID = 1L;

			public void onComponentTag(Component component, ComponentTag tag) {
				if (getPage().getClass() == AdminHome.class
						|| getPage().getClass() == UsersList.class
						|| getPage().getClass() == RolesList.class
						|| getPage().getClass() == PermissionsList.class) {
					tag.put("style", "background-color:Gainsboro");

				}
			}

		});

		dashboard.add(new Behavior() {
			private static final long serialVersionUID = 1L;

			public void onComponentTag(Component component, ComponentTag tag) {
				if (getPage().getClass() == Dashboard.class) {
					tag.put("style", "background-color:Gainsboro");

				}
			}

		});

		competencesTree.add(new Behavior() {
			private static final long serialVersionUID = 1L;

			public void onComponentTag(Component component, ComponentTag tag) {
				if (getPage().getClass() == CompetencesPage.class) {
					tag.put("style", "background-color:Gainsboro");

				}
			}

		});

		profile.add(new Behavior() {
			private static final long serialVersionUID = 1L;

			public void onComponentTag(Component component, ComponentTag tag) {
				if ((getPage().getClass() == Profiles.class)
						|| (getPage().getClass() == ProfilesList.class)) {
					tag.put("style", "background-color:Gainsboro");
				}
				// tag.put("class", "active");
			}
		});

		testManagement.add(new Behavior() {
			private static final long serialVersionUID = 1L;

			public void onComponentTag(Component component, ComponentTag tag) {
				if (getPage().getClass() == TestsManagement.class
						|| getPage().getClass() == TestListPage.class
						|| getPage().getClass() == QuestionListPage.class
						|| getPage().getClass() == AnswerListPage.class) {
					tag.put("style", "background-color:Gainsboro");

				}
			}

		});

		testExecution.add(new Behavior() {
			private static final long serialVersionUID = 1L;

			public void onComponentTag(Component component, ComponentTag tag) {
				if (getPage().getClass() == TestsExecution.class) {
					tag.put("style", "background-color:Gainsboro");

				}
			}

		});

		report.add(new Behavior() {
			private static final long serialVersionUID = 1L;

			public void onComponentTag(Component component, ComponentTag tag) {
				if (getPage().getClass() == Report.class) {
					tag.put("style", "background-color:Gainsboro");

				}
			}

		});

		add(li);

	}

	@Override
	public void renderHead(IHeaderResponse response) {

		response.render(CssHeaderItem.forReference(new CssResourceReference(
				NavigationPanel.class, "bootstrap.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				NavigationPanel.class, "add.css")));
		response.render(JavaScriptHeaderItem
				.forReference(new JQueryPluginResourceReference(
						NavigationPanel.class, "bootstrap.js")));

	}
}

/*
 * If needs to rollback!!!
 * 
 * add(new BookmarkablePageLink("adminTools", AdministrativeTools.class));
 * add(new BookmarkablePageLink("adminTools", AdminHome.class, pars)); add(new
 * BookmarkablePageLink("dashboard", Dashboard.class, pars)); add(new
 * BookmarkablePageLink("competencesTree", CompetencesTree.class, pars));
 */

/*
 * add(new BookmarkablePageLink("profiles", Profiles.class, pars))
 * .setOutputMarkupId(true); ; add( new BookmarkablePageLink("testManagement",
 * TestsManagement.class, pars)).setOutputMarkupId(true); ;
 */

// add(new BookmarkablePageLink("testManagement", TestListPage.class));
/*
 * add(new BookmarkablePageLink("testExecution", TestsExecution.class)); add(new
 * BookmarkablePageLink("report", Report.class)) .setOutputMarkupId(true);
 */