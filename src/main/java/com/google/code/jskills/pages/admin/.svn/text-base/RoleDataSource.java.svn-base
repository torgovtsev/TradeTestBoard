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

import com.google.code.jskills.business.services.RoleService;
import com.google.code.jskills.domain.Role;

public class RoleDataSource extends SortableDataProvider<Role, String>{

	private static final long serialVersionUID = -1243637493515188022L;
	
	private transient RoleService roleService;
	
	class SortableRoleComparator implements Comparator<Role>, Serializable{

		private static final long serialVersionUID = 5453759937215412882L;

		@Override
		public int compare(final Role role1, final Role role2) {
			PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(role1, (String) getSort().getProperty());
			PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(role2, (String) getSort().getProperty());
			int result = 0;
			try{
				result = model1.getObject().compareTo(model2.getObject());
			}catch(NullPointerException e){
				if ((model1.getObject() == null) && (model2.getObject() == null)){
					return 0;
				}else{
					if(model1.getObject() == null){
						return 1;
					} else return -1;
				}	
			}

			if (!getSort().isAscending()) {
				result = -result;
			}

			return result;
		}
		
	}
	
	public RoleDataSource(RoleService roleService){
		this.roleService = roleService;
		setSort("id", SortOrder.ASCENDING);
	}
	
	@Override
	public Iterator<? extends Role> iterator(long first, long count) {
		
		List<Role> roles = roleService.getAllRoles();
		
		Collections.sort(roles, new SortableRoleComparator());
	
		return roles.subList((int)first, (int)(first+count)).iterator();
	}

	@Override
	public long size() {
		return (long)roleService.getNumberOfRoles();
	}

	@Override
	public IModel<Role> model(final Role object) {
		return new AbstractReadOnlyModel<Role>() {
			private static final long serialVersionUID = -1775459499606793769L;

			@Override
			public Role getObject() {
				return object;
			}
		};
	}

}

