package com.google.code.jskills.pages.testmanagement;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.cdi.CdiContainer;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.google.code.jskills.business.services.AnswerService;
import com.google.code.jskills.domain.Answer;

public class AnswerListDataProvider extends SortableDataProvider<Answer, String>{

	private static final long serialVersionUID = 1L;

	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	private List<Answer> answers = null;
	
	@Inject
	private AnswerService answerService;
	
	public AnswerListDataProvider() {
		
		CdiContainer.get().getNonContextualManager().inject(this);
		setSort("id", SortOrder.ASCENDING);
	}
	

	@Override
	public Iterator<? extends Answer> iterator(long first, long count) {
		Collections.sort(answers, comparator);
		return answers.subList((int) first,
				(int) Math.min(first + count, answers.size())).iterator();
	}

	@Override
	public IModel<Answer> model(Answer arg0) {
		return Model.of(arg0);
	}

	@Override
	public long size() {
		return dataLoad().size();
	}
	
	
	private List<Answer> dataLoad(){
		if(answers == null){
			answers =  answerService.findAll();
		}
		return answers;
	}
	
	@Override
	public void detach() {
		super.detach();
		answers = null;
	}
	
	
	@SuppressWarnings("serial")
	class SortableDataProviderComparator implements Comparator<Answer>,
			Serializable {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public int compare(final Answer o1, final Answer o2) {
			
			
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
