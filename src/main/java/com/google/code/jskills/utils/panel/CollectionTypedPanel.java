package com.google.code.jskills.utils.panel;

import java.util.Collection;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class CollectionTypedPanel<T> extends Panel {

	private static final long serialVersionUID = 5855853002468008563L;

	public CollectionTypedPanel(String id) {
		super(id);
	}

	public CollectionTypedPanel(String id,
			IModel<? extends Collection<? extends T>> model) {
		super(id, model);
	}

	@SuppressWarnings("unchecked")
	public IModel<? extends Collection<? extends T>> getModel() {
		IModel<?> defaultModel = this.getDefaultModel();
		return defaultModel == null ? null
				: (IModel<? extends Collection<? extends T>>) defaultModel;
	}

	public void setModel(IModel<? extends Collection<? extends T>> model) {
		this.setDefaultModel(model);
	}

	@SuppressWarnings("unchecked")
	public Collection<? extends T> getModelObject() {
		Object defaultModelObject = this.getDefaultModelObject();
		return defaultModelObject == null ? null
				: (Collection<? extends T>) defaultModelObject;
	}

	public void setModelObject(Collection<? extends T> model) {
		this.setDefaultModelObject(model);
	}
}
