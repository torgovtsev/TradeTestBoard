package com.google.code.jskills.pages.admin;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.RoleService;
import com.google.code.jskills.domain.Role;
import com.google.code.jskills.pages.AdministrativeTools;

public class RolesList extends AdministrativeTools{

	private static final long serialVersionUID = -5190747353673331079L;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(RolesList.class);
	
	@Inject
	private transient RoleService roleService;
	
	public RolesList(){
		super();
		LOG.info("Page RolesList in AdminPanel loading started");
		Form<String> rolesListForm = new Form<String>("rolesListForm");
		add(rolesListForm);
		
		rolesListForm.add(new Label("contentMessage", "Administrative Tools / Roles List"));

		/**
		 * DataTable for roles
		 */
		RoleDataSource roleDataSource = new RoleDataSource(roleService);
		List<IColumn<Role, String>> columns = new ArrayList<IColumn<Role, String>>();
		columns.add(new PropertyColumn<Role, String>(new Model<String>("ID"), "id", "id"));
		columns.add(new PropertyColumn<Role, String>(new Model<String>("Name"), "name", "name"));
		columns.add(new PropertyColumn<Role, String>(new Model<String>("Description"), "description", "description"));
		columns.add(new RoleActionColumn(new Model<String>("Edit Role")));
		DefaultDataTable <Role, String> rolesTable = new DefaultDataTable <Role, String> ("rolesTable", columns, roleDataSource, 5);
		rolesListForm.add(rolesTable);
		
		/**
		 * Create role button
		 */
		AjaxButton createRoleBtn = new AjaxButton("createRole") {
			private static final long serialVersionUID = -8279337477161538822L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				setResponsePage(CreateRole.class);
			}
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
			}
		};
		rolesListForm.add(createRoleBtn);
	}

}


class RoleActionColumn extends AbstractColumn<Role, String> {

	private static final long serialVersionUID = 8849484575616410595L;

	public RoleActionColumn(IModel<String> displayModel) {
		super(displayModel);
	}
	@Override
	public void populateItem(Item<ICellPopulator<Role>> cellItem,
			String componentId, IModel<Role> rowModel) {
		cellItem.add(new RoleActionPanel(componentId, rowModel));
	}
}
