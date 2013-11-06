package com.google.code.jskills.utils.panel;

import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class ListTypedPanel<T> extends Panel {

	private static final long serialVersionUID = -5853620851727459292L;

	public ListTypedPanel(String id) {
		super(id);
	}

	public ListTypedPanel(String id, IModel<? extends List<? extends T>> model) {
		super(id, model);
	}

	@SuppressWarnings("unchecked")
	public IModel<? extends List<? extends T>> getModel() {
		IModel<?> defaultModel = this.getDefaultModel();
		return defaultModel == null ? null
				: (IModel<? extends List<? extends T>>) defaultModel;
	}

	public void setModel(IModel<? extends List<? extends T>> model) {
		this.setDefaultModel(model);
	}

	@SuppressWarnings("unchecked")
	public List<? extends T> getModelObject() {
		Object defaultModelObject = this.getDefaultModelObject();
		return defaultModelObject == null ? null
				: (List<? extends T>) defaultModelObject;
	}

	public void setModelObject(List<? extends T> model) {
		this.setDefaultModelObject(model);
	}
}
