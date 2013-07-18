package com.google.code.jskills.pages.competence;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
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
	private String Level1Desc;
	private String Level2Desc;
	private String Level3Desc;
	private String Level4Desc;

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

		Form<String> addForm = new Form<String>("addForm");
		add(addForm);

		addForm.add(new Label("competencesDescLable",
				"Competences description: "));
		TextField<String> compDesc = new TextField<String>("compDesc",
				new PropertyModel<String>(this, "competencesDescription"));
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

		addForm.add(new Label("rootLabel", "Root competences:"));
		final IModel<Boolean> flag = Model.of(Boolean.FALSE);
		AjaxCheckBox checkRoot = new AjaxCheckBox("checkRoot", flag) {

			private static final long serialVersionUID = -2656540417738369978L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (flag.getObject().booleanValue()) {
					target.add(competences.setEnabled(false));
					target.add(parentLabel.setEnabled(false));
				} else {
					target.add(competences.setEnabled(true));
					target.add(parentLabel.setEnabled(true));
				}
			}
		};
		addForm.add(checkRoot);
		
		addForm.add(new Label("level1Label", "Description (Level 1):"));
		TextField<String> level1 = new TextField<String>("level1", new PropertyModel<String>(this, "Level1Desc"));
		addForm.add(level1);

		addForm.add(new Label("level2Label", "Description (Level 2):"));
		TextField<String> level2 = new TextField<String>("level2", new PropertyModel<String>(this, "Level2Desc"));
		addForm.add(level2);

		addForm.add(new Label("level3Label", "Description (Level 3):"));
		TextField<String> level3 = new TextField<String>("level3", new PropertyModel<String>(this, "Level3Desc"));
		addForm.add(level3);

		addForm.add(new Label("level4Label", "Description (Level 4):"));
		TextField<String> level4 = new TextField<String>("level4", new PropertyModel<String>(this, "Level4Desc"));
		addForm.add(level4);
		
		AjaxLink<Void> save = new AjaxLink<Void>("save") {

			private static final long serialVersionUID = 6835058105654334743L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				Set<Level> levels = new HashSet<Level>();
				Level level1 = new Level(selectCompetences, Level1Desc, 1);
				Level level2 = new Level(selectCompetences, Level2Desc, 2);
				Level level3 = new Level(selectCompetences, Level3Desc, 3);
				Level level4 = new Level(selectCompetences, Level4Desc, 4);
				levels.add(level1);
				levels.add(level2);
				levels.add(level3);
				levels.add(level4);
				Competences competence = new Competences(selectCompetences, competencesDescription, levels);
				competencesService.saveCompetences(competence);
			}
		};
		add(save);
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
