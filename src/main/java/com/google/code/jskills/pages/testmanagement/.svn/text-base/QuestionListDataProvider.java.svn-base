package com.google.code.jskills.pages.testmanagement;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.cdi.CdiContainer;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.google.code.jskills.business.services.QuestionService;
import com.google.code.jskills.business.services.TestService;
import com.google.code.jskills.domain.Question;
import com.google.code.jskills.domain.Test;
import com.google.code.jskills.pages.testmanagement.TestListDataProvider.SortableDataProviderComparator;

public class QuestionListDataProvider extends SortableDataProvider<Question, String> {

	@Inject
	private QuestionService questionService;

	private transient List<Question> questions = null;
	
	private final SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	
	public QuestionListDataProvider() {
        CdiContainer.get().getNonContextualManager().inject(this);
		
		setSort("id", SortOrder.ASCENDING);
	}
	
	@Override
	public Iterator<? extends Question> iterator(long first, long count) {		
		Collections.sort(questions, comparator);
		return questions.subList((int) first,
				(int) Math.min(first + count, questions.size())).iterator();
		
	}

	@Override
	public IModel<Question> model(Question arg0) {
		return Model.of(arg0);
	}

	@Override
	public long size() {
		//questions = questionService.findAll();
		return loadData().size();
	}
	
	private List<Question> loadData() {
		if (this.questions == null){
			this.questions = questionService.findAll();
		}
		return this.questions;
	}
	
	@Override
	public void detach() {
		super.detach();
		this.questions = null;
	}
	
	@SuppressWarnings("serial")
	class SortableDataProviderComparator implements Comparator<Question>,
			Serializable {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public int compare(final Question o1, final Question o2) {
			
			
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
