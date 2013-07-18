package com.google.code.jskills.pages.admin;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.IModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import com.google.code.jskills.MainApplication;
import com.google.code.jskills.business.services.UserService;
import com.google.code.jskills.domain.User;
import com.google.code.jskills.pages.AdministrativeTools;

@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class UsersList extends AdministrativeTools {
	
	private static final long serialVersionUID = -6337280449875940701L;

	private static final Logger LOG = LoggerFactory
			.getLogger(UsersList.class);
	
	@Inject
	private transient UserService userService;

	public UsersList() {
		super();
		Form<String> usersListForm = new Form<String>("usersListForm");
		add(usersListForm);
		
		usersListForm.add(new Label("message", "Administrative Tools / Users List"));

		/**
		 * Sortable DataTable
		 */
		final UserDataSource userDataSource = new UserDataSource(userService);
		List<IColumn<User, String>> columns = new ArrayList<IColumn<User, String>>();
		columns.add(new PropertyColumn<User, String>(new Model<String>("ID"), "id", "id"));
		columns.add(new PropertyColumn<User, String>(new Model<String>("First Name"), "firstName", "firstName"));
		columns.add(new PropertyColumn<User, String>(new Model<String>("Last Name"), "lastName", "lastName"));
		columns.add(new PropertyColumn<User, String>(new Model<String>("Age"), "age", "age"));
		columns.add(new PropertyColumn<User, String>(new Model<String>("Email"), "email", "email"));	
		columns.add(new PropertyColumn<User, String>(new Model<String>("Mobile"), "mobile", "mobile"));			
		columns.add(new AbstractColumn <User, String> (new Model<String>("Sex"), "sex") {
			private static final long serialVersionUID = -759624732955405836L;
			@Override
			public void populateItem(Item<ICellPopulator<User>> cellItem,
					String componentId, IModel<User> rowModel) {
				User user = (User)rowModel.getObject();
				if(user.getSex()){
					cellItem.add(new Label(componentId, "male"));
				}else{
					cellItem.add(new Label(componentId, "female"));
				}				
			}
		});
		columns.add(new PropertyColumn<User, String>(new Model<String>("Country"), "country.name", "country.name"));
		columns.add(new PropertyColumn<User, String>(new Model<String>("RegistrationDate"), "registrationDate", "registrationDate"));
		columns.add(new AbstractColumn <User, String> (new Model<String>("Deleted"), "isDeleted") {
			private static final long serialVersionUID = -94839400970825124L;

			public void populateItem(Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> rowModel) {
				User user = (User)rowModel.getObject();
				if(user.getIsDeleted()){
					cellItem.add(new Label(componentId,"Yes"));
				}else{
					cellItem.add(new Label(componentId,"No"));
				}
								
			}
		});
		columns.add(new AbstractColumn <User, String> (new Model<String>("Blocked"), "isBlocked") {
			private static final long serialVersionUID = -94839400970825124L;

			public void populateItem(Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> rowModel) {
				User user = (User)rowModel.getObject();
				if(user.getIsBlocked()){
					cellItem.add(new Label(componentId,"Yes"));
				}else{
					cellItem.add(new Label(componentId,"No"));
				}
								
			}
		});
		columns.add(new AbstractColumn <User, String> (new Model<String>("Verified"), "isVerified") {
			private static final long serialVersionUID = -94839400970825124L;

			public void populateItem(Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> rowModel) {
				User user = (User)rowModel.getObject();
				if(user.getIsVerified()){
					cellItem.add(new Label(componentId,"Yes"));
				}else{
					cellItem.add(new Label(componentId,"No"));
				}
								
			}
		});
		columns.add(new UserActionsColumn(new Model<String>("Edit User")));
		
		DefaultDataTable <User, String> table = new DefaultDataTable <User, String> ("datatable", columns, userDataSource, 5);
		usersListForm.add(table);
		
		/**
		 * Create new User button
		 */
		AjaxButton createUserButton = new AjaxButton("createUserButton") {
			private static final long serialVersionUID = 1L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				LOG.info("Create new User button pressed in AdminUsers Page");
				setResponsePage(CreateUser.class);
			};

		};
		usersListForm.add(createUserButton);
	}
}


class UserActionPanel extends Panel {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory
			.getLogger(MainApplication.class);
	
	public UserActionPanel(String id, final IModel<User> model) {	
		super(id);		
		add(new AjaxLink<User>("editUser", model) {
	    private static final long serialVersionUID = 1L;
	    @Override
	    public void onClick(AjaxRequestTarget target) {
	    	User selectedUser = getModelObject();
	    	LOG.info("User with email: " + getModelObject().getEmail() + " is selected");
	    	Page editUserPage = new EditUser(selectedUser);
	    	setResponsePage(editUserPage);	    	
	    }
	  });
	}
}


class UserActionsColumn extends AbstractColumn<User, String> {
	private static final long serialVersionUID = 1L;	
	public UserActionsColumn(IModel<String> displayModel) {
		super(displayModel);
	}	
	@Override
	public void populateItem(Item<ICellPopulator<User>> cellItem,
			String componentId, IModel<User> rowModel) {
		cellItem.add(new UserActionPanel(componentId, rowModel));
	}
}
