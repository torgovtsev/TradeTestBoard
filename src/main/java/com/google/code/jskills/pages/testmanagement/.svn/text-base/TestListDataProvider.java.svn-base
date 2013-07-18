package com.google.code.jskills.pages.testmanagement;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.cdi.CdiContainer;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.google.code.jskills.business.services.TestService;
import com.google.code.jskills.domain.Test;

public class TestListDataProvider extends SortableDataProvider<Test, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private transient TestService testService;

	
	
	private transient List<Test> tests = null;

	private final SortableDataProviderComparator comparator = new SortableDataProviderComparator();

	public TestListDataProvider() {
		CdiContainer.get().getNonContextualManager().inject(this);		
		setSort("id", SortOrder.ASCENDING);
		
	}


	@Override
	public Iterator<Test> iterator(long first, long count) {

		// tests = testService.findAll();

		Collections.sort(tests, comparator);

		return tests.subList((int) first,
				(int) Math.min(first + count, tests.size())).iterator();
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return loadData().size();
	}

	private List<Test> loadData() {
		if (this.tests == null){
			this.tests = testService.findAll();
		}
		return this.tests;
	}
	
	@Override
	public void detach() {
		super.detach();
		this.tests = null;
	}
	
	@Override
	public IModel model(Test object) {
		return Model.of(object);
	}

	@SuppressWarnings("serial")
	class SortableDataProviderComparator implements Comparator<Test>,
			Serializable {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public int compare(final Test o1, final Test o2) {
			
			
			PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(
						o1, getSort().getProperty());
			
			PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(
					o2, getSort().getProperty());
			
				
				int result = model1.getObject().compareTo(model2.getObject());
		
				if (!getSort().isAscending()) {
					result = -result;
				}

				return result;
			
			
		}

	}

}
