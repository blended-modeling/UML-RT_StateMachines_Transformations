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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

/**
 * Interface class for code snippet tab.
 * 
 * @author ysroh
 *
 */
public interface ICodeSnippetTab {

	/**
	 * Create tab composite.
	 * 
	 * @param parent
	 *            container composite
	 * @return composite
	 */
	CTabItem createControl(CTabFolder parent);

	/**
	 * Control created for this tab.
	 * 
	 * @return CTabItem
	 */
	CTabItem getControl();

	/**
	 * Set input.
	 * 
	 * @param input
	 *            input
	 */
	void setInput(EObject input);

	/**
	 * Queries if the composite has been created.
	 * 
	 * @return true if created
	 */
	boolean controlCreated();

	/**
	 * Dispose tab composite.
	 */
	void dispose();

	/**
	 * Return true if this tab has a source code.
	 * 
	 * @return true if this tab has code
	 */
	boolean hasCode();

	/**
	 * Check if this tab is default selected.
	 * 
	 * @return true if this tab selection selected by default
	 */
	boolean isDefaultSelected();
}
