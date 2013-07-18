package com.google.code.jskills.ui.components;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class MaskedInput<T> extends TextField<T> {

	private static final long serialVersionUID = 1085278111588291688L;
	
	private CharSequence mask;

	public CharSequence getMask() {
		return mask;
	}

	public void setMask(CharSequence mask) {
		this.mask = mask;
	}

	public MaskedInput(String id, IModel<T> model, CharSequence mask) {
		super(id, model);
		setOutputMarkupId(true);
		this.mask = mask;
	}
	
	@Override
	protected void onConfigure() {
		super.onConfigure();
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		
		response.render(JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(MaskedInput.class, "jquery.maskedinput.js")));
		response.render(OnDomReadyHeaderItem.forScript(createJScript()));
	}
	
	/**
	 * create Javascript for maskedinput
	 * 
	 * @return CharSequence
	 */
	private CharSequence createJScript() {
		StringBuilder functionBuilder = new StringBuilder();
		functionBuilder.append("jQuery(function($){$.mask.definitions['~']='[+-]';$(\"#");
		functionBuilder.append(this.getMarkupId());
		functionBuilder.append("\").mask(\"");
		functionBuilder.append(this.getMask());
		functionBuilder.append("\");});");
		return functionBuilder.toString();
	}
}
