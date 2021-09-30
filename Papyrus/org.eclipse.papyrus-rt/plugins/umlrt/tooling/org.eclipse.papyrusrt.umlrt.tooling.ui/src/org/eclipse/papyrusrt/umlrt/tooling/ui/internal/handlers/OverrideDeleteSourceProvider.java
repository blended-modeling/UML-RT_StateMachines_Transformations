/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

import com.google.common.collect.ImmutableMap;

/**
 * A source provider for a variable, intended for use in XML enablement expressions,
 * that indicates via a boolean value whether the standard GMF delete command handler
 * should be overridden to implement UML-RT exclusion instead.
 */
public class OverrideDeleteSourceProvider extends AbstractSourceProvider {
	private static final String OVERRIDE_DELETE = "papyrusrt.override.delete"; //$NON-NLS-1$

	private static final String[] SOURCES = { OVERRIDE_DELETE };

	// The SourceProviderService shifts the priority declared in the plugin.xml by one
	private static final int PRIORITY = ISources.ACTIVE_SITE << 1;

	private static AtomicBoolean overrideEnabled = new AtomicBoolean();

	private static List<OverrideDeleteSourceProvider> instances = new ArrayList<>(1);

	private ISelection selection = StructuredSelection.EMPTY;

	public OverrideDeleteSourceProvider() {
		super();

		instances.add(this);
	}

	@Override
	public String[] getProvidedSourceNames() {
		return SOURCES;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getCurrentState() {
		return overrideEnabled.get()
				? ImmutableMap.of(OVERRIDE_DELETE, true,
						// As explained below, we need this variable to be defined
						ISources.ACTIVE_MENU_SELECTION_NAME, selection)
				: ImmutableMap.of(OVERRIDE_DELETE, false);
	}

	@Override
	public void dispose() {
		instances.remove(this);
	}

	void setSelection(ISelection selection) {
		this.selection = (selection == null) ? StructuredSelection.EMPTY : selection;
	}

	static boolean setEnabled(boolean enableOverride, ISelection selection) {
		boolean result = overrideEnabled.compareAndSet(!enableOverride, enableOverride);

		if (result) {
			if (enableOverride) {
				// Let the active menu selection be defined regardless of whether there
				// is a menu showing so that our handler can actually use that variable
				// for activation to effect its override when triggered by the key binding
				instances.forEach(p -> {
					try {
						p.setSelection(selection);
						p.fireSourceChanged(ISources.ACTIVE_MENU, ISources.ACTIVE_MENU_SELECTION_NAME, selection);
					} finally {
						// Don't leak any selected objects
						p.setSelection(null);
					}
				});
			}

			instances.forEach(p -> p.fireSourceChanged(PRIORITY, OVERRIDE_DELETE, enableOverride));
		}

		return result;
	}
}
