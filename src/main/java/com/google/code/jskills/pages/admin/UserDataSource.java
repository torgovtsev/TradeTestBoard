package com.google.code.jskills.pages.admin;

import java.io.Serializable;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;


import com.google.code.jskills.business.services.UserService;
import com.google.code.jskills.domain.User;

public class UserDataSource extends SortableDataProvider<User,String> {
	
	private static final long serialVersionUID = 6808410212733991141L;
	
	private transient UserService userService;

	class SortableDataProviderComparator implements Comparator<User>, Serializable {

		private static final long serialVersionUID = 3951944364561217994L;

		public int compare(final User o1, final User o2) {
			int result = 0;
			PropertyModel<Comparable> model1 = null;
			PropertyModel<Comparable> model2 = null;
			try{
			model1 = new PropertyModel<Comparable>(o1, (String) getSort().getProperty());
			model2 = new PropertyModel<Comparable>(o2, (String) getSort().getProperty());
			
			result = model1.getObject().compareTo( model2.getObject());
			}catch(NullPointerException e){
				return 0;	
			}
			if (!getSort().isAscending()) {
				result = -result;
			}
			return result;
		}
	}

	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	
	public UserDataSource(UserService userService){
		this.userService = userService;
		setSort("lastName", SortOrder.ASCENDING);
	}

	@Override
	public IModel<User> model(final User object) {
		return new AbstractReadOnlyModel<User>() {
			private static final long serialVersionUID = -1775459499606793769L;

			@Override
			public User getObject() {
				return object;
			}
		};
	}

	public long size() {
		return (long)userService.getNumberOfAllUsers();
	}

	@Override
	public Iterator<User> iterator(long first, long count) {
		
		// Get the data
		List<User> users = userService.getAllUsers();

		// Sort the data
		Collections.sort(users, comparator);
				
		// Return the data for the current page - this can be determined only after sorting
		return users.subList((int)first, (int)(first+count)).iterator();	
	}
}
