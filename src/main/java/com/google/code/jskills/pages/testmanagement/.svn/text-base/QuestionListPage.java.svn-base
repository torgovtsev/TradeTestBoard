package com.google.code.jskills.pages.testmanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import com.google.code.jskills.domain.Question;
import com.google.code.jskills.domain.Test;
import com.google.code.jskills.pages.TestsManagement;
import com.google.code.jskills.pages.master.MasterPage;

@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class QuestionListPage extends TestsManagement{


	private static final long serialVersionUID = -4045274379196726674L;
	
	private static final int ITEMS_PER_PAGE = 10;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		// add(new Label("message", "This is TestsList page"));

		List<IColumn<Question, String>> columns = new ArrayList<IColumn<Question, String>>();

		Form<Void> form = new Form<Void>("questionForm");
		this.add(form);

		columns.add(new PropertyColumn<Question, String>(new Model<String>(
				"№  "), "id", "id"));
		columns.add(new PropertyColumn<Question, String>(new Model<String>("Text"),
				"questiontext", "questiontext"));
		columns.add(new AbstractColumn<Question, String>( new Model<String>("Edit")) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(Item<ICellPopulator<Question>> cellItem,
					String componentId, IModel<Question> rowModel) {
				cellItem.add(new QuestionActionPanel(componentId, rowModel));
				
			}

			
		});

		
		QuestionListDataProvider dataProvider = new QuestionListDataProvider();

		DataTable<Question, String> table = new DefaultDataTable<Question, String>("questionTable", columns, dataProvider, ITEMS_PER_PAGE);


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
				setResponsePage(CreateQuestion.class);
				
			}
			
		};
		form.add(createButton);
	

		form.add(table);

	}

}
