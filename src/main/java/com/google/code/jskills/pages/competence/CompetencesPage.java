package com.google.code.jskills.pages.competence;

import javax.inject.Inject;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation;
import org.apache.wicket.extensions.markup.html.tree.table.IColumn;
import org.apache.wicket.extensions.markup.html.tree.table.PropertyRenderableColumn;
import org.apache.wicket.extensions.markup.html.tree.table.PropertyTreeColumn;
import org.apache.wicket.extensions.markup.html.tree.table.TreeTable;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Alignment;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Unit;
import com.google.code.jskills.business.services.CompetencesService;

@SuppressWarnings("deprecation")
public class CompetencesPage extends BaseTree {

	private static final long serialVersionUID = 2078985690466403355L;

	@Inject
	private transient CompetencesService competencesService;

	private final TreeTable tree;

	public CompetencesPage() {
		IColumn columns[] = new IColumn[] {new PropertyRenderableColumn<String>(new ColumnLocation(
				Alignment.LEFT, 2, Unit.EM), "", "userObject.property1"),
				new PropertyTreeColumn<String>(new ColumnLocation(
						Alignment.MIDDLE, 36, Unit.PROPORTIONAL), "Competences",
						"userObject.property2"),
				new PropertyRenderableColumn<String>(new ColumnLocation(
						Alignment.MIDDLE, 15, Unit.PROPORTIONAL), "Level 1",
						"userObject.property3"),
				new PropertyRenderableColumn<String>(new ColumnLocation(
						Alignment.MIDDLE, 15, Unit.PROPORTIONAL), "Level 2",
						"userObject.property4"),
				new PropertyRenderableColumn<String>(new ColumnLocation(
						Alignment.MIDDLE, 15, Unit.PROPORTIONAL), "Level 3",
						"userObject.property5"),
				new PropertyRenderableColumn<String>(new ColumnLocation(
						Alignment.MIDDLE, 15, Unit.PROPORTIONAL), "Level 4",
					"userObject.property6") };

		tree = new TreeTable("treeTable", createTreeModel(), columns);
		tree.getTreeState().setAllowSelectMultiple(true);
		add(tree);
		tree.getTreeState().collapseAll();
		getTree().setRootLess(!getTree().isRootLess());
		
		/*
		 * create Modal window for addCompetences page
		 */		
		final ModalWindow addModalWindow = new ModalWindow("addWindow");
		add(addModalWindow);

		addModalWindow.setPageCreator(new ModalWindow.PageCreator() {

			private static final long serialVersionUID = 2059202159344169787L;

			@Override
			public Page createPage() {
				return new AddCompetences(addModalWindow);
			}
		});
		
		AjaxLink<Void> addLink = new AjaxLink<Void>("addLink") {

			private static final long serialVersionUID = 5051161796874814817L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				addModalWindow.show(target);				
			}
		};
		add(addLink);
	}

	@Override
	protected AbstractTree getTree() {
		return tree;
	}
}
