package com.google.code.jskills.pages.testmanagement;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.cdi.CdiContainer;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.tree.table.IRenderable;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.wicketstuff.egrid.column.EditableTextFieldPropertyColumn;
import org.wicketstuff.egrid.column.IEditableGridColumn;
import org.wicketstuff.egrid.component.EditableDataTable;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import com.google.code.jskills.business.services.ProfileService;
import com.google.code.jskills.business.services.TestService;
import com.google.code.jskills.domain.Test;
import com.google.code.jskills.pages.TestsManagement;
import com.google.code.jskills.pages.master.MasterPage;
import com.google.code.jskills.pages.profile.CreateProfile;
import com.google.code.jskills.pages.profile.ProfileActionPanel;
import com.google.code.jskills.pages.profile.ProfilesList;



@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class TestListPage extends TestsManagement{


	private static final long serialVersionUID = 3987667253362965855L;
	
	private static final int ITEMS_PER_PAGE = 5;
	
	
	@Inject
	private transient TestService testService;
	public TestListPage() {
		CdiContainer.get().getNonContextualManager().inject(this);
	}
	
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		final ModalWindow testModalWindow = new ModalWindow("testModalWindow");
		
		testModalWindow.setCookieName("testModalWindow-1");

		testModalWindow.setResizable(false);
		testModalWindow.setInitialWidth(40);
		testModalWindow.setInitialHeight(40);
		testModalWindow.setWidthUnit("em");
		testModalWindow.setHeightUnit("em");

		testModalWindow.setCssClassName(ModalWindow.CSS_CLASS_GRAY);

		// profileModalWindow.setAutoSize(true);
		add(testModalWindow);

		testModalWindow.add(new Behavior() {
			private static final long serialVersionUID = 1L;

			public void onComponentTag(Component component, ComponentTag tag) {
				tag.put("style", "background-color:HotPink");

			}

		});
		
		testModalWindow.setPageCreator(new ModalWindow.PageCreator() {

			private static final long serialVersionUID = 1L;

			@Override
			public Page createPage() {

				return new CreateTest(TestListPage.this.getPageReference(),
						testModalWindow);
				
			}
		});
		
    
		List<IColumn<Test, String>> columns = new ArrayList<IColumn<Test, String>>();

		Form<Void> form = new Form<Void>("form");
		this.add(form);

		columns.add(new PropertyColumn<Test, String>(new Model<String>(
				"№  "), "id", "id"));
		columns.add(new PropertyColumn<Test, String>(new Model<String>("Name"),
				"name", "name"));
		columns.add(new PropertyColumn<Test, String>(new Model<String>(
				"Description"), "description", "description"));
		
		columns.add(new AbstractColumn<Test, String>( new Model<String>("Edit")) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(Item<ICellPopulator<Test>> cellItem,
					String componentId, IModel<Test> rowModel) {
				cellItem.add(new TestActionPanel(componentId, rowModel));
				
			}
		});
		
		columns.add(new AbstractColumn<Test, String>( new Model<String>("Add Question")) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(Item<ICellPopulator<Test>> cellItem,
					String componentId, IModel<Test> rowModel) {
				cellItem.add(new AddQuestionActionPanel(componentId, rowModel));
				
			}
		});

		
		TestListDataProvider dataProvider = new TestListDataProvider();

		DataTable<Test, String> table = new DefaultDataTable<Test, String>(
				"testTable", columns, dataProvider, ITEMS_PER_PAGE);

		

		/**
		 * Create test submit button
		 */
		AjaxButton createButton = new AjaxButton("createButton") {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				super.onSubmit(target, form);
				testModalWindow.show(target);
				
			}
			
		};
		form.add(createButton);
		form.add(table);

	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				ProfilesList.class, "../master/bootstrap.css")));

	}

}
