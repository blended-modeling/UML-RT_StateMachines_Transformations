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

import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.internal.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs.ICodeSnippetTab;

/**
 * Code snippet tab descriptor.
 * 
 * @author ysroh
 *
 */
public class CodeSnippetTabDescriptor implements ICodeSnippetTabDescriptor {

	/**
	 * Code snippet tab class.
	 */
	protected Class<? extends ICodeSnippetTab> clazz;

	/**
	 * Visibility test predicate.
	 */
	protected Predicate<EObject> visibility;

	/**
	 * 
	 * Constructor.
	 *
	 * @param clazz
	 *            tab class
	 * @param visibility
	 *            visibility predicate
	 */
	public CodeSnippetTabDescriptor(Class<? extends ICodeSnippetTab> clazz, Predicate<EObject> visibility) {
		if (clazz == null || visibility == null) {
			throw new IllegalArgumentException("null source");
		}

		this.clazz = clazz;
		this.visibility = visibility;
	}

	@Override
	public ICodeSnippetTab createTab() {
		ICodeSnippetTab tab;
		try {
			tab = clazz.newInstance();
			return tab;
		} catch (ReflectiveOperationException e) {
			Activator.getDefault().error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public boolean isTabInstance(ICodeSnippetTab tab) {
		return clazz.isInstance(tab);
	}

	@Override
	public boolean shouldShowThisTab(EObject context) {
		return visibility.test(context);
	}
}
