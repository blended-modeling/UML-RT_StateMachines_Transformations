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
package org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs.descriptors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs.ICodeSnippetTab;

/**
 * Interface class for code snippet tab descriptor.
 * 
 * @author ysroh
 *
 */
public interface ICodeSnippetTabDescriptor {

	/**
	 * Create tab composite.
	 * 
	 * @return composite
	 */
	ICodeSnippetTab createTab();

	/**
	 * Check if this tab should be visible.
	 * 
	 * @param context
	 *            context
	 * @return true if tab should be visible. False otherwise.
	 */
	boolean shouldShowThisTab(EObject context);

	/**
	 * Queries if the given tab is the instance of tab content of this
	 * descriptor.
	 * 
	 * @param tab
	 *            tab
	 * @return true if tab matches instanceof descriptor tab conent.
	 */
	boolean isTabInstance(ICodeSnippetTab tab);
}
