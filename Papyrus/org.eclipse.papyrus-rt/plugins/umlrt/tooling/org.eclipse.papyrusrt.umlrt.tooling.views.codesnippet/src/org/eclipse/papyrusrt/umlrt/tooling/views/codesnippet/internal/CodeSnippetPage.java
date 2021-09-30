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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.MultipleAdapter;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs.ChoicePointTab;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs.ICodeSnippetTab;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs.OperationTab;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs.StateEntryTab;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs.StateExitTab;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs.TransitionEffectTab;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs.TransitionGuardTab;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs.TriggerGuardTab;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs.descriptors.CodeSnippetTabDescriptor;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs.descriptors.ICodeSnippetTabDescriptor;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.utils.CodeSnippetTabUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;
import org.eclipse.uml2.uml.Pseudostate;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * <p>
 * Code snippet page containing available tabs {@link ICodeSnippetTab}. This
 * page controls life cycle of visible tabs.
 * </p>
 * 
 * @author ysroh
 *
 */
public class CodeSnippetPage extends Page implements ICodeSnippetPage {

	/**
	 * Current workbench selection.
	 */
	private static IStructuredSelection currentWorkbenchSelection = StructuredSelection.EMPTY;

	/**
	 * Selected EObject.
	 */
	private static EObject currentSelectedEObject;

	/**
	 * Main page composite.
	 */
	private Composite pageControl;

	/**
	 * Default empty tab item.
	 */
	private Control defaultPage;

	/**
	 * Available tabs.
	 */
	private List<ICodeSnippetTabDescriptor> tabDescriptors = new ArrayList<>();

	/**
	 * Tab folder composite by element type.
	 */
	private Map<IElementType, Control> elementTypeToPage = new HashMap<IElementType, Control>();

	/**
	 * Tab folder to tabs.
	 */
	private Multimap<CTabFolder, ICodeSnippetTab> tabFolderToTabs = ArrayListMultimap.create();

	/**
	 * Selected tab for tab folder.
	 */
	private Map<CTabFolder, CTabItem> selectedTab = new HashMap<>();
	/**
	 * Listeners.
	 */
	private List<ISelectionChangedListener> listeners = new ArrayList<ISelectionChangedListener>();

	/**
	 * Page book element type.
	 */
	private PageBook pageBook;

	/**
	 * Selection adapter.
	 */
	private SelectionAdapter adapter;

	/**
	 * flag to ignore notification.
	 */
	private boolean ignoreNotification = false;

	/**
	 * Tab selection listener.
	 */
	private SelectionListener tabSelectionListener;

	/**
	 * 
	 * Constructor.
	 *
	 */
	public CodeSnippetPage() {
		adapter = new SelectionAdapter();
		tabSelectionListener = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedTab.put((CTabFolder) e.getSource(), (CTabItem) e.item);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		};
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {

		String partId = part.getSite().getId();
		if (CodeSnippetView.ID.equals(partId)) {
			// Code snippet view selected
			fireSelectionChange();
			return;
		}

		if (!(selection instanceof IStructuredSelection)) {
			return;
		}

		if (currentSelectedEObject != null) {
			ignoreNotification = true;
			currentSelectedEObject.eAdapters().remove(adapter);
			ignoreNotification = false;
		}

		if (!"org.eclipse.ui.views.PropertySheet".equals(partId)) {
			// Save selection to pass on to the property sheet.
			// Ignore selection from the property sheet.
			currentWorkbenchSelection = new StructuredSelection(((IStructuredSelection) selection).toList());
		}

		if (selection.isEmpty()) {
			currentSelectedEObject = null;
		} else {
			EObject eo = EMFHelper.getEObject(((IStructuredSelection) selection).getFirstElement());

			if (eo != null && !eo.equals(currentSelectedEObject)) {
				currentSelectedEObject = eo;
			}
		}

		CTabFolder tabFolder = getPageFolder(currentSelectedEObject);
		CTabItem firstNonEmptyTab = null;
		boolean selectionOverriden = false;
		if (tabFolder != null) {
			tabFolder.removeSelectionListener(tabSelectionListener);
			for (ICodeSnippetTabDescriptor tabDescriptor : tabDescriptors) {
				if (tabDescriptor.shouldShowThisTab(currentSelectedEObject)) {
					ICodeSnippetTab tab = getTabContent(tabFolder, tabDescriptor);
					if (tab != null) {
						tab.setInput(currentSelectedEObject);
						selectionOverriden = selectionOverriden || tab.isDefaultSelected();
						if (firstNonEmptyTab == null && tab.hasCode()) {
							// save first non empty tab
							firstNonEmptyTab = tab.getControl();
						}
					}
				} else {
					// remove tab for this descriptor if exist
					removeTabContent(tabFolder, tabDescriptor);
				}
			}
		}

		if (tabFolder != null && tabFolder.getItemCount() > 0) {
			if (!selectionOverriden && selectedTab.get(tabFolder) == null && firstNonEmptyTab != null) {
				// make first non empty tab visible if user did not make tab
				// selection for this element type.
				tabFolder.setSelection(firstNonEmptyTab);
			}
			if (tabFolder.getSelection() == null) {
				// select first tab if no selection has been made
				tabFolder.setSelection(0);
			}

			if (!selectionOverriden) {
				// add selection listener for user selection
				tabFolder.addSelectionListener(tabSelectionListener);
			}
			pageBook.showPage(tabFolder);
			currentSelectedEObject.eAdapters().add(adapter);
		} else {
			// show default page
			pageBook.showPage(defaultPage);
			currentSelectedEObject = null;
		}
	}

	/**
	 * Get tab for the given tab folder and tab descriptor.
	 * 
	 * @param tabComposite
	 *            tab folder
	 * @param descriptor
	 *            descriptor.
	 * @return tab
	 */
	private ICodeSnippetTab getTabContent(CTabFolder tabComposite, ICodeSnippetTabDescriptor descriptor) {
		ICodeSnippetTab tab = tabFolderToTabs.get(tabComposite).stream().filter(t -> descriptor.isTabInstance(t))
				.findFirst().orElse(null);
		if (tab == null) {
			tab = descriptor.createTab();
			if (tab != null) {
				tab.createControl(tabComposite);
				tabFolderToTabs.put(tabComposite, tab);
			}
		}
		return tab;
	}

	/**
	 * Remove tab from the tab folder.
	 * 
	 * @param tabComposite
	 *            tab folder
	 * @param descriptor
	 *            descriptor.
	 */
	private void removeTabContent(CTabFolder tabComposite, ICodeSnippetTabDescriptor descriptor) {
		ICodeSnippetTab tab = tabFolderToTabs.get(tabComposite).stream().filter(t -> descriptor.isTabInstance(t))
				.findFirst().orElse(null);
		if (tab != null) {
			tabFolderToTabs.remove(tabComposite, tab);
			tab.dispose();
		}
	}

	/**
	 * Fire selection change.
	 */
	private void fireSelectionChange() {
		final SelectionChangedEvent event = new SelectionChangedEvent(this, getSelection());
		for (ISelectionChangedListener l : listeners) {
			SafeRunnable runnable = new SafeRunnable() {

				@Override
				public void run() throws Exception {
					l.selectionChanged(event);
				}
			};
			SafeRunnable.run(runnable);
		}
	}

	/**
	 * Create tab folder for give element type.
	 * 
	 * @param context
	 *            element
	 * @return tab folder composite
	 */
	private CTabFolder getPageFolder(EObject context) {
		if (context == null) {
			return null;
		}
		IElementType type = null;
		try {
			type = ElementTypeRegistry.getInstance().getElementType(context, TypeContext.getContext(context));
		} catch (ServiceException e) {
			Activator.log.error(e);
		}
		Control folder = elementTypeToPage.get(type);
		if (folder != null) {
			return (CTabFolder) folder;
		}

		// create tab folder
		CTabFolder tabFolder = new CTabFolder(pageBook, SWT.BOTTOM);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		elementTypeToPage.put(type, tabFolder);
		return tabFolder;
	}

	@Override
	public void createControl(Composite parent) {

		// ToDo: we can provide extension mechanism if needed
		// but for now it is fairly limited tabs we need.
		tabDescriptors.add(new CodeSnippetTabDescriptor(StateEntryTab.class, e -> {
			return CodeSnippetTabUtil.getState(e) != null;
		}));
		tabDescriptors.add(new CodeSnippetTabDescriptor(StateExitTab.class, e -> {
			return CodeSnippetTabUtil.getState(e) != null;
		}));
		tabDescriptors.add(new CodeSnippetTabDescriptor(TransitionEffectTab.class, e -> {
			return CodeSnippetTabUtil.getTransition(e) != null;
		}));
		tabDescriptors.add(new CodeSnippetTabDescriptor(TransitionGuardTab.class, e -> {
			return CodeSnippetTabUtil.getTransition(e) != null;
		}));
		tabDescriptors.add(new CodeSnippetTabDescriptor(TriggerGuardTab.class, e -> {
			return CodeSnippetTabUtil.getTrigger(e) != null;
		}));
		tabDescriptors.add(new CodeSnippetTabDescriptor(ChoicePointTab.class, e -> {
			return (e instanceof Pseudostate
					&& ElementTypeUtils.matches(e, IUMLRTElementTypes.RT_PSEUDO_STATE_CHOICE_ID));
		}));
		tabDescriptors.add(new CodeSnippetTabDescriptor(OperationTab.class, e -> {
			return CodeSnippetTabUtil.getOperation(e) != null;
		}));

		// compose page control
		pageControl = new Composite(parent, SWT.NONE);
		pageControl.setLayout(new FillLayout());

		pageBook = new PageBook(pageControl, SWT.NONE);

		createDefaultPage();

	}

	/**
	 * Create default empty tab.
	 */
	private void createDefaultPage() {
		Composite page = new Composite(pageBook, SWT.NONE);
		page.setLayoutData(new GridData(GridData.FILL_BOTH));
		page.setLayout(new GridLayout());
		CLabel label = new CLabel(page, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		label.setText("Code snippet view is not available for the current selection.");
		defaultPage = page;
	}

	@Override
	public Control getControl() {
		return pageControl;
	}

	@Override
	public void setFocus() {
		pageControl.setFocus();
	}

	@Override
	public void dispose() {
		if (adapter != null) {
			adapter.dispose();
		}

		tabDescriptors.clear();
		tabFolderToTabs.values().stream().filter(Objects::nonNull).forEach(ICodeSnippetTab::dispose);
		super.dispose();
	}

	@Override
	public ISelection getSelection() {
		ISelection selection = StructuredSelection.EMPTY;
		if (currentWorkbenchSelection != null && !currentWorkbenchSelection.isEmpty()) {
			EObject eo = EMFHelper.getEObject(currentWorkbenchSelection.getFirstElement());
			if (eo == null || eo.eIsProxy() || eo.eResource() == null) {
				currentWorkbenchSelection = null;
			} else {
				selection = currentWorkbenchSelection;
			}
		}
		return selection;
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		// not required for code snippet view.
	}

	/**
	 * Selection adapter.
	 * 
	 * @author ysroh
	 *
	 */
	private class SelectionAdapter extends MultipleAdapter {

		/**
		 * Constructor.
		 *
		 */
		SelectionAdapter() {
			super(1);
		}

		/**
		 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 *
		 * @param msg
		 */
		@Override
		public void notifyChanged(Notification notification) {
			if (!ignoreNotification) {
				if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
					// Handle case when model is closed.
					currentSelectedEObject = null;
					currentWorkbenchSelection = null;
					fireSelectionChange();
				}
			}
		}
	}
}
