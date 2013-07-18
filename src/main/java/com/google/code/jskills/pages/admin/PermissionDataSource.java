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

import com.google.code.jskills.business.services.PermissionService;
import com.google.code.jskills.domain.Permission;

public class PermissionDataSource extends SortableDataProvider<Permission, String>{

	private static final long serialVersionUID = -1243637493515188022L;
	
	private transient PermissionService permissionService;
	
	class SortablePermissionComparator implements Comparator<Permission>, Serializable{

		private static final long serialVersionUID = 5453759937215412882L;

		@Override
		public int compare(final Permission permission1, final Permission permission2) {
			PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(permission1, (String) getSort().getProperty());
			PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(permission2, (String) getSort().getProperty());
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
	
	public PermissionDataSource(PermissionService permissionService){
		this.permissionService = permissionService;
		setSort("id", SortOrder.ASCENDING);
	}
	
	@Override
	public Iterator<? extends Permission> iterator(long first, long count) {
		
		List<Permission> permissions = permissionService.getAllPermissions();
		
		Collections.sort(permissions, new SortablePermissionComparator());
	
		return permissions.subList((int)first, (int)(first+count)).iterator();
	}

	@Override
	public long size() {
		return (long)permissionService.getNumberOfPermissions();
	}

	@Override
	public IModel<Permission> model(final Permission object) {
		return new AbstractReadOnlyModel<Permission>() {
			private static final long serialVersionUID = -1775459499606793769L;

			@Override
			public Permission getObject() {
				return object;
			}
		};
	}

}

