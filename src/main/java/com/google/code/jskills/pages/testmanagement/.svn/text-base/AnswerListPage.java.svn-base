package com.google.code.jskills.pages.testmanagement;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

import com.google.code.jskills.business.services.AnswerService;
import com.google.code.jskills.business.services.TestService;
import com.google.code.jskills.domain.Answer;
import com.google.code.jskills.domain.Test;
import com.google.code.jskills.pages.TestsManagement;
import com.google.code.jskills.pages.master.MasterPage;


@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class AnswerListPage extends TestsManagement{

	private static final long serialVersionUID = 1L;
	
	private static final int ITEMS_PER_PAGE = 10;
	
	@Inject
	private transient AnswerService answerService;
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form<Void> form = new Form<Void>("form");
		this.add(form);
		
		List<IColumn<Answer, String>> columns = new ArrayList<IColumn<Answer, String>>();

		columns.add(new PropertyColumn<Answer, String>(new Model<String>(
				"№  "), "id", "id"));
		columns.add(new PropertyColumn<Answer, String>(new Model<String>("Text"),
				"answertext", "answertext"));
		columns.add(new PropertyColumn<Answer, String>(new Model<String>(
				"Point"), "point", "point"));
		
		columns.add(new AbstractColumn<Answer, String>( new Model<String>("Edit")) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(Item<ICellPopulator<Answer>> cellItem,
					String componentId, IModel<Answer> rowModel) {
				cellItem.add(new AnswerActionPanel(componentId, rowModel));
				
			}

			
				
			});
		

		AnswerListDataProvider dataProvider = new AnswerListDataProvider();

		DataTable<Answer, String> table = new DefaultDataTable<Answer, String>(
				"answerTable", columns, dataProvider, ITEMS_PER_PAGE);


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
				setResponsePage(CreateAnswer.class);
				
			}
			
		};
		
		form.add(createButton);

		form.add(table);
	}

}
