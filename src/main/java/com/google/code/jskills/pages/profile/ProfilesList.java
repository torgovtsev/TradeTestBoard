package com.google.code.jskills.pages.profile;

import java.util.ArrayList;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import com.google.code.jskills.business.services.ProfileService;
import com.google.code.jskills.domain.Profile;
import com.google.code.jskills.pages.master.MasterPage;

@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class ProfilesList extends MasterPage {

	private static final long serialVersionUID = 7041277259543143524L;

	@Inject
	private transient ProfileService profileService;

	public ProfilesList() {
		super();

		/**
		 * 
		 * Create profiles form
		 */

		Form<String> profilesForm = new Form<String>("profilesListForm");
		profilesForm.setOutputMarkupId(true);
		add(profilesForm);

		profilesForm.add(new Label("message", "Profiles / Profiles List"));

		/**
		 * Enable sorting for dataTable columns, except description column (for
		 * example)
		 */
		final ProfilesProvider profilesProvider = new ProfilesProvider(
				profileService);

		List<IColumn<Profile, String>> columns = new ArrayList<IColumn<Profile, String>>();
		columns.add(new PropertyColumn<Profile, String>(Model.of("Id"), "id",
				"id"));
		columns.add(new PropertyColumn<Profile, String>(Model.of("Name"),
				"name", "name"));
		columns.add(new PropertyColumn<Profile, String>(
				Model.of("Description"), "description"));

		columns.add(new ProfileActionsColumn(new Model<String>("Edit Profile")));

		DefaultDataTable<Profile, String> profilesTable = new DefaultDataTable<Profile, String>(
				"datatable", columns, profilesProvider, 10);
		profilesTable.setOutputMarkupId(true);

		profilesForm.add(profilesTable);

		/**
		 * Create profile modal window
		 */

		final ModalWindow profileModalWindow = new ModalWindow(
				"profileModalWindow");
		profileModalWindow.setCookieName("profileModalWindow-1");

		profileModalWindow.setResizable(false);
		profileModalWindow.setInitialWidth(30);
		profileModalWindow.setInitialHeight(15);
		profileModalWindow.setWidthUnit("em");
		profileModalWindow.setHeightUnit("em");

		profileModalWindow.setCssClassName(ModalWindow.CSS_CLASS_GRAY);

		// profileModalWindow.setAutoSize(true);
		add(profileModalWindow);

		profileModalWindow.add(new Behavior() {
			private static final long serialVersionUID = 1L;

			public void onComponentTag(Component component, ComponentTag tag) {
				tag.put("style", "background-color:HotPink");

			}

		});

		profileModalWindow.setPageCreator(new ModalWindow.PageCreator() {

			private static final long serialVersionUID = 3931559851604247281L;

			@Override
			public Page createPage() {

				return new CreateProfile(ProfilesList.this.getPageReference(),
						profileModalWindow);
				/*
				 * return new CreateProfile(new PageReference(getPageId()),
				 * createModalWindow);
				 */
			}
		});

		/*
		 * profileModalWindow .setCloseButtonCallback(new
		 * ModalWindow.CloseButtonCallback() { public boolean
		 * onCloseButtonClicked(AjaxRequestTarget target) {
		 * target.appendJavaScript
		 * ("alert('You can\\'t close this modal window using close button." +
		 * " Use the link inside the window instead.');"); return false; } });
		 */

		/**
		 * Create profile submit button
		 */

		AjaxButton createProfileBtn = new AjaxButton("createProfile") {

			private static final long serialVersionUID = -8279337477161538822L;

			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				profileModalWindow.show(target);
				// setResponsePage(CreateProfile.class);

			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				setResponsePage(ProfilesList.class);

			}
		};

		profilesForm.add(createProfileBtn);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				ProfilesList.class, "../master/bootstrap.css")));

	}
}

class ProfileActionsColumn extends AbstractColumn<Profile, String> {

	private static final long serialVersionUID = 1L;

	public ProfileActionsColumn(IModel<String> displayModel) {
		super(displayModel);
	}

	@Override
	public void populateItem(Item<ICellPopulator<Profile>> cellItem,
			String componentId, IModel<Profile> rowModel) {
		cellItem.add(new ProfileActionPanel(componentId, rowModel));
	}
}
