package com.google.code.jskills.pages.forgotpassword;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.IResource;

public abstract class ImagePanel extends Panel {

	private static final long serialVersionUID = 8063586336133844594L;

	public ImagePanel(String id) {
		super(id);
		add(new Image("captchaImage") {
			private static final long serialVersionUID = 1L;

			@Override
			protected IResource getImageResource() {
				return ImagePanel.this.getImageResource();
			}
		});
	}

	public abstract IResource getImageResource();

}
