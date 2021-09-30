/*******************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.editor.PapyrusMultiDiagramEditor;
import org.eclipse.papyrus.views.modelexplorer.ModelExplorerPageBookView;
import org.eclipse.papyrusrt.umlrt.tooling.properties.providers.RTNattableSelectionService;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

/**
 * Code snippet view.
 * 
 * @author ysroh
 *
 */
public class CodeSnippetView extends PageBookView implements ISelectionListener, ITabbedPropertySheetPageContributor {

	/**
	 * The ID of the view.
	 */
	public static final String ID = "org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet";

	/**
	 * Papyrus property sheet contributor id.
	 */
	public static final String PAPYRUS_COMMON_CONTRIBUTOR_ID = "TreeOutlinePage";
	/**
	 * Properties page.
	 */
	private CodeSnippetPropertySheetPage propertiesPage;

	/**
	 * Created code snippet pages.
	 */
	private List<CodeSnippetPage> pages = new ArrayList<>();

	/**
	 * 
	 * Constructor.
	 *
	 */
	public CodeSnippetView() {
	}

	@Override
	protected IPage createDefaultPage(PageBook book) {
		IPageBookViewPage page = new EmptyCodeSnippetPage();
		initPage(page);
		page.createControl(book);
		return page;
	}

	@Override
	protected PageRec doCreatePage(IWorkbenchPart part) {
		String id = part.getSite().getId();
		if (ModelExplorerPageBookView.VIEW_ID.equals(id) || PapyrusMultiDiagramEditor.EDITOR_ID.equals(id)
				|| "org.eclipse.ui.views.PropertySheet".equals(id)) {
			CodeSnippetPage page = new CodeSnippetPage();
			initPage(page);
			if (propertiesPage != null) {
				page.addSelectionChangedListener(propertiesPage);
			}
			pages.add(page);
			page.createControl(getPageBook());
			return new PageRec(part, page);
		}
		return null;
	}

	@Override
	protected void doDestroyPage(IWorkbenchPart part, PageRec pageRecord) {
		ICodeSnippetPage page = (ICodeSnippetPage) pageRecord.page;
		pages.remove(page);
		page.dispose();
		pageRecord.dispose();
	}

	@Override
	protected IWorkbenchPart getBootstrapPart() {
		IWorkbenchPage page = getSite().getPage();
		if (page == null) {
			return null;
		}
		ISelection pageSel = page.getSelection();
		IWorkbenchPart activePart = page.getActivePart();
		if (activePart != null && activePart != this) {
			return activePart;
		}
		if (pageSel == null || pageSel.isEmpty()) {
			return null;
		}

		IEditorPart activeEditor = page.getActiveEditor();
		if (activeEditor != null && isImportant(activeEditor)) {
			if (activeEditor.getSite().getSelectionProvider() != null) {
				ISelection selection = activeEditor.getSite().getSelectionProvider().getSelection();
				if (pageSel.equals(selection)) {
					return activeEditor;
				}
			}
		}
		IViewReference[] views = page.getViewReferences();
		for (IViewReference viewRef : views) {
			IWorkbenchPart part = viewRef.getPart(false);
			if (part == null || part == this || !page.isPartVisible(part)) {
				continue;
			}
			if (!isImportant(part) || part.getSite().getSelectionProvider() == null) {
				continue;
			}
			ISelection selection = part.getSite().getSelectionProvider().getSelection();
			if (pageSel.equals(selection)) {
				return part;
			}
		}
		return null;
	}

	@Override
	protected boolean isImportant(IWorkbenchPart part) {
		String partID = part.getSite().getId();
		return !getSite().getId().equals(partID);

	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		site.getPage().addPostSelectionListener(this);
		RTNattableSelectionService.getInstance().addSelectionListener(this);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		ICodeSnippetPage page = (ICodeSnippetPage) getCurrentPage();
		if (part != null && page != null) {
			page.selectionChanged(part, selection);
		}
	}

	@Override
	public void dispose() {
		RTNattableSelectionService.getInstance().removeSelectionListener(this);
		getSite().getPage().removePostSelectionListener(this);
		if (propertiesPage != null) {
			propertiesPage.dispose();
		}
		super.dispose();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getAdapter(Class adapter) {

		if (IPropertySheetPage.class == adapter) {
			if (propertiesPage == null) {
				propertiesPage = new CodeSnippetPropertySheetPage(this);
				propertiesPage.addDisposeListener(e -> {
					// property sheet disposed.
					propertiesPage = null;
					for (CodeSnippetPage page : pages) {
						// add listener
						page.removeSelectionChangedListener(propertiesPage);
					}
				});
				for (CodeSnippetPage page : pages) {
					// add listener
					page.addSelectionChangedListener(propertiesPage);
				}
			}
			return propertiesPage;
		}

		return null;
	}

	@Override
	public String getContributorId() {
		// Both o.e.p.infra.ui.editor.CoreMultiDiagramEditor and
		// o.e.p.views.modelexplorer.ModelExplorerPageBookView use this.
		return PAPYRUS_COMMON_CONTRIBUTOR_ID;
	}
}
