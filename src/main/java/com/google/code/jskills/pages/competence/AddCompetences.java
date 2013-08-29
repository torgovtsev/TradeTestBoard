package com.google.code.jskills.pages.competence;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.resource.CssResourceReference;

import com.google.code.jskills.business.services.CompetencesService;
import com.google.code.jskills.domain.Competences;
import com.google.code.jskills.domain.Level;
import com.google.code.jskills.pages.master.MasterPage;
import com.google.code.jskills.pages.master.NavigationPanel;

public class AddCompetences extends WebPage {

	private static final long serialVersionUID = -2763113164553928434L;

	@Inject
	private transient CompetencesService competencesService;

	private Competences selectCompetences;
	
	private String competencesDescription;	
	private String level1Desc;
	private String level2Desc;
	private String level3Desc;
	private String level4Desc;

	public AddCompetences(final ModalWindow window) {

		AjaxLink<Void> cancel = new AjaxLink<Void>("cancel") {

			private static final long serialVersionUID = -7576014680836168005L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				window.close(target);
			}
		};
		add(cancel);
		
		window.setInitialWidth(30);
        window.setInitialHeight(18);
        window.setWidthUnit("em");
        window.setHeightUnit("em");
	}
	
	protected void onInitialize() {

		super.onInitialize();
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("news");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		Form<String> addForm = new Form<String>("addForm");
		add(addForm);

		addForm.add(new Label("competencesDescLable",
				"Competences description: "));
		TextField<String> compDesc = new TextField<String>("compDesc",
				new PropertyModel<String>(this, "competencesDescription"));
		compDesc.setRequired(true);
		addForm.add(compDesc);

		final Label parentLabel = new Label("parentLabel", "Parent competence:");
		parentLabel.setOutputMarkupId(true);
		addForm.add(parentLabel);

		List<Competences> rootCompetences = competencesService
				.getRootCompetences();
		final DropDownChoice<Competences> competences = new DropDownChoice<Competences>(
				"competences", new PropertyModel<Competences>(this,
						"selectCompetences"), new ListModel<Competences>(
						rootCompetences), new ChoiceRenderer<Competences>(
						"description"));
		competences.setOutputMarkupId(true);
		addForm.add(competences);

		addForm.add(new Label("level1Label", "Description (Level 1):"));
		final TextField<String> level1 = new TextField<String>("level1", new PropertyModel<String>(this, "level1Desc"));
		level1.setOutputMarkupId(true);
		addForm.add(level1);

		addForm.add(new Label("level2Label", "Description (Level 2):"));
		final TextField<String> level2 = new TextField<String>("level2", new PropertyModel<String>(this, "level2Desc"));
		level2.setOutputMarkupId(true);
		addForm.add(level2);

		addForm.add(new Label("level3Label", "Description (Level 3):"));
		final TextField<String> level3 = new TextField<String>("level3", new PropertyModel<String>(this, "level3Desc"));
		level3.setOutputMarkupId(true);
		addForm.add(level3);

		addForm.add(new Label("level4Label", "Description (Level 4):"));
		final TextField<String> level4 = new TextField<String>("level4", new PropertyModel<String>(this, "level4Desc"));
		level4.setOutputMarkupId(true);
		addForm.add(level4);
		
		addForm.add(new Label("rootLabel", "Root competences:"));
		final IModel<Boolean> flag = Model.of(Boolean.FALSE);
		AjaxCheckBox checkRoot = new AjaxCheckBox("checkRoot", flag) {

			private static final long serialVersionUID = -2656540417738369978L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (flag.getObject().booleanValue()) {
					target.add(competences.setEnabled(false));
					target.add(parentLabel.setEnabled(false));
					target.add(level1.setEnabled(false));
					target.add(level2.setEnabled(false));
					target.add(level3.setEnabled(false));
					target.add(level4.setEnabled(false));
				} else {
					target.add(competences.setEnabled(true));
					target.add(parentLabel.setEnabled(true));
					target.add(level1.setEnabled(true));
					target.add(level2.setEnabled(true));
					target.add(level3.setEnabled(true));
					target.add(level4.setEnabled(true));
				}
			}
		};
		addForm.add(checkRoot);
		
		AjaxButton save = new AjaxButton("save"){

			private static final long serialVersionUID = 6835058105654334743L;

			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Set<Level> levels = new HashSet<Level>();
				Competences competence = new Competences(selectCompetences, competencesDescription);
				Level level1 = new Level(level1Desc, 1);
				level1.setCompetences(competence);
				Level level2 = new Level(level2Desc, 2);
				level2.setCompetences(competence);
				Level level3 = new Level(level3Desc, 3);
				level3.setCompetences(competence);
				Level level4 = new Level(level4Desc, 4);
				level4.setCompetences(competence);
				levels.add(level1);
				levels.add(level2);
				levels.add(level3);
				levels.add(level4);
				competence.setLevels(levels);
				competencesService.saveCompetences(competence);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		};
		addForm.add(save);
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				NavigationPanel.class, "bootstrap.min.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				MasterPage.class, "add.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				MasterPage.class, "bootstrap.css")));
	}
}
