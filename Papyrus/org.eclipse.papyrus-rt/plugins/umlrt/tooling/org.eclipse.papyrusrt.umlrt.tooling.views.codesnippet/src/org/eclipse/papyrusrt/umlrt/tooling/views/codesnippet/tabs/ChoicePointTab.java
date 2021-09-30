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
package org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.utils.ModelUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.MultipleAdapter;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Code snippet tab descriptor for choice point.
 * 
 * @author ysroh
 *
 */
public class ChoicePointTab implements ICodeSnippetTab {

	/**
	 * Input choice.
	 */
	protected Pseudostate choice;

	/**
	 * Tab item to {@link ICodeSnippetTab} map.
	 */
	protected Map<CTabItem, ICodeSnippetTab> tabs = new HashMap<>();

	/**
	 * Tab to transition map.
	 */
	protected Map<ICodeSnippetTab, Transition> tabsToTransition = new HashMap<>();

	/**
	 * Default tab.
	 */
	protected CTabItem defaultTabItem;

	/**
	 * Default page.
	 */
	protected Composite defaultTabPage;

	/**
	 * Tab folder.
	 */
	protected CTabFolder tabFolder;

	/**
	 * Keep selected transition for each Choice Point.
	 */
	protected Map<Pseudostate, Transition> selectionMap = new HashMap<>();

	/**
	 * Tab selection listener.
	 */
	private SelectionListener selectionListener;

	/**
	 * Choice listener.
	 */
	private ChoiceAdapter choiceListener;

	/**
	 * flag for tab control is created.
	 */
	private boolean controlCreated = false;

	/**
	 * 
	 * Constructor.
	 *
	 */
	public ChoicePointTab() {
		choiceListener = new ChoiceAdapter();

		selectionListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CTabItem item = (CTabItem) e.item;
				ICodeSnippetTab tab = tabs.get(item);
				Transition selectedTransition = tabsToTransition.get(tab);
				selectionMap.put(choice, selectedTransition);
				choice.eAdapters().add(choiceListener);
			}
		};
	}

	@Override
	public CTabItem createControl(CTabFolder parent) {

		controlCreated = true;

		tabFolder = parent;

		defaultTabPage = new Composite(tabFolder, SWT.NONE);
		defaultTabPage.setLayoutData(new GridData());
		defaultTabPage.setLayout(new GridLayout());
		CLabel label = new CLabel(defaultTabPage, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		label.setText("No outgoing transitions for this choice point.");

		createDefaultTabItem();

		return defaultTabItem;
	}

	/**
	 * Create default empty tab.
	 */
	private void createDefaultTabItem() {
		if (defaultTabItem == null && tabFolder != null) {
			defaultTabItem = new CTabItem(tabFolder, SWT.NONE);

			defaultTabItem.setControl(defaultTabPage);
		}
	}

	@Override
	public void setInput(EObject input) {
		disposeTabs();
		choice = (Pseudostate) input;
		Transition selectedTransition = selectionMap.get(choice);
		CTabItem firstTabWithGuardSet = null;

		// create transition guard tab for each of outgoing transitions.
		for (Transition t : choice.getOutgoings()) {
			ICodeSnippetTab tab = new ChoicePointTransitionGuardTab(choice);

			CTabItem tabItem = tab.createControl(tabFolder);
			tab.setInput(t);

			tabs.put(tabItem, tab);
			tabsToTransition.put(tab, t);

			if (tabFolder.getSelection() == null && firstTabWithGuardSet == null) {
				IDefaultLanguage defaultLanguage = ModelUtils.getDefaultLanguage(choice);
				UMLRTTransition rtTransition = UMLRTFactory.createTransition(t);
				UMLRTGuard guard = rtTransition.getGuard();
				if (guard != null && defaultLanguage != null) {
					// if default language is not null and there exist a guard
					// for this transition
					if (guard.getBodies().get(defaultLanguage.getName()) != null) {
						firstTabWithGuardSet = tabItem;
					}
				}
			}
			if (t.equals(selectedTransition)) {
				// existing selection
				tabFolder.setSelection(tabItem);
			}
		}

		if (tabs.isEmpty()) {
			createDefaultTabItem();
		} else {
			if (tabFolder.getSelection() == null) {
				if (firstTabWithGuardSet != null) {
					// select first transition with guard value.
					tabFolder.setSelection(firstTabWithGuardSet);
				} else {
					// select first tab otherwise.
					tabFolder.setSelection(0);
				}
			}

			// add tab selection listener
			tabFolder.addSelectionListener(selectionListener);
		}

		tabFolder.layout();

	}

	@Override
	public boolean controlCreated() {
		return controlCreated;
	}

	@Override
	public void dispose() {
		choiceListener.dispose();
		disposeTabs();
	}

	/**
	 * Dispose tabs.
	 */
	private void disposeTabs() {

		if (tabFolder != null && !tabFolder.isDisposed()) {
			tabFolder.removeSelectionListener(selectionListener);
		}

		tabs.values().forEach(ICodeSnippetTab::dispose);
		tabs.clear();

		if (defaultTabItem != null) {
			defaultTabItem.dispose();
			defaultTabItem = null;
		}
		tabsToTransition.clear();
	}

	@Override
	public CTabItem getControl() {
		return defaultTabItem;
	}

	@Override
	public boolean hasCode() {
		return false;
	}

	@Override
	public boolean isDefaultSelected() {
		return true;
	}

	/**
	 * Choice listener.
	 * 
	 * @author ysroh
	 *
	 */
	private class ChoiceAdapter extends MultipleAdapter {
		/**
		 * 
		 * Constructor.
		 *
		 */
		ChoiceAdapter() {

		}

		@Override
		public void notifyChanged(Notification notification) {
			int eventType = notification.getEventType();
			Object notifier = notification.getNotifier();
			Object feature = notification.getFeature();
			if (eventType == Notification.REMOVING_ADAPTER) {
				// no need to keep this element in the map if adapter is removed
				// for whatever reason. e.g., model is closed
				selectionMap.remove(notifier);
			} else if (eventType == Notification.SET && UMLPackage.Literals.VERTEX__CONTAINER.equals(feature)) {
				// remove selection if choice removed
				if (notification.getNewValue() == null) {
					selectionMap.remove(notifier);
				}
			}
		}
	}
}
