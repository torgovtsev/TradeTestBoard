package com.google.code.jskills.pages.registration;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

public class DoneRegistration extends WebPage {

	private final static String TEXT_DONE_REGISTRATION = "Thank You for registration on our site.\n"
			+ "On Your Email we send message with instruction how activate your account!\n";

	private static final long serialVersionUID = 1L;

	@Override
	protected void onInitialize() {

		super.onInitialize();

		Form<String> doneForm = new Form<String>("doneForm");
		add(doneForm);

		doneForm.add(addMultiLineLabel());

	}

	private MultiLineLabel addMultiLineLabel() {
		Model<String> label = new Model<String>(TEXT_DONE_REGISTRATION);
		MultiLineLabel result = new MultiLineLabel("result", label);
		return result;
	}

}
