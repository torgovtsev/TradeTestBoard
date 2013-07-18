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

import com.google.code.jskills.business.services.PermissionService;
import com.google.code.jskills.domain.Permission;
import com.google.code.jskills.pages.AdministrativeTools;

public class PermissionsList extends AdministrativeTools{

	private static final long serialVersionUID = -5190747353673331079L;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(PermissionsList.class);
	
	@Inject
	private transient PermissionService PermissionService;
	
	public PermissionsList(){
		super();
		LOG.info("Page PermissionsList in AdminPanel loading started");
		Form<String> permissionsListForm = new Form<String>("permissionsListForm");
		add(permissionsListForm);
		
		permissionsListForm.add(new Label("contentMessage", "Administrative Tools / Permissions List"));

		/**
		 * DataTable for Permissions
		 */
		PermissionDataSource PermissionDataSource = new PermissionDataSource(PermissionService);
		List<IColumn<Permission, String>> columns = new ArrayList<IColumn<Permission, String>>();
		columns.add(new PropertyColumn<Permission, String>(new Model<String>("ID"), "id", "id"));
		columns.add(new PropertyColumn<Permission, String>(new Model<String>("Name"), "name", "name"));
		columns.add(new PropertyColumn<Permission, String>(new Model<String>("Description"), "description", "description"));
		columns.add(new PermissionActionColumn(new Model<String>("Edit Permission")));
		DefaultDataTable <Permission, String> PermissionsTable = new DefaultDataTable <Permission, String> ("permissionsTable", columns, PermissionDataSource, 5);
		permissionsListForm.add(PermissionsTable);
		
		/**
		 * Create Permission button
		 */
		AjaxButton createPermissionBtn = new AjaxButton("createPermission") {
			private static final long serialVersionUID = -8279337477161538822L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				setResponsePage(CreatePermission.class);
			}
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
			}
		};
		permissionsListForm.add(createPermissionBtn);
	}

}


class PermissionActionColumn extends AbstractColumn<Permission, String> {

	private static final long serialVersionUID = 8849484575616410595L;

	public PermissionActionColumn(IModel<String> displayModel) {
		super(displayModel);
	}

	@Override
	public void populateItem(Item<ICellPopulator<Permission>> cellItem,
			String componentId, IModel<Permission> rowModel) {
		cellItem.add(new PermissionActionPanel(componentId, rowModel));
	}
}
