package com.google.code.jskills.utils.panel;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class TypedPanel<T> extends Panel {

	private static final long serialVersionUID = -3251715062105985934L;

	public TypedPanel(String id) {
		super(id);
	}

	public TypedPanel(String id, IModel<T> model) {
		super(id, model);
	}

	@SuppressWarnings("unchecked")
	public IModel<T> getModel() {
		IModel<?> defaultModel = this.getDefaultModel();
		return defaultModel == null ? null : (IModel<T>) defaultModel;
	}

	public void setModel(IModel<T> model) {
		this.setDefaultModel(model);
	}

	@SuppressWarnings("unchecked")
	public T getModelObject() {
		Object defaultModelObject = this.getDefaultModelObject();
		return defaultModelObject == null ? null : (T) defaultModelObject;
	}

	public void setModelObject(T modelObject) {
		this.setDefaultModelObject(modelObject);
	}
}
