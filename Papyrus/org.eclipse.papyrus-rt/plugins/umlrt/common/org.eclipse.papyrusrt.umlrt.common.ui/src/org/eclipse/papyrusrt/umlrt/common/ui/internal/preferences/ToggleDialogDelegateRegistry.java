/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.common.ui.internal.preferences;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.plugin.RegistryReader;
import org.eclipse.papyrusrt.umlrt.common.ui.Activator;
import org.eclipse.papyrusrt.umlrt.common.ui.preferences.IToggleDialogDelegate;

import com.google.common.base.Strings;

/**
 * A registry of {@link IToggleDialogDelegate} extensions.
 */
public class ToggleDialogDelegateRegistry {
	private static final String EXT_PT = "toggleDialogDelegates"; //$NON-NLS-1$
	private static final String CLASS_ATTR = "class"; //$NON-NLS-1$
	private static final String DELEGATE_ELEM = "toggleDialogDelegate"; //$NON-NLS-1$

	private final Map<String, IToggleDialogDelegate> delegatesByClass = new ConcurrentHashMap<>();

	/**
	 * Initializes me.
	 */
	public ToggleDialogDelegateRegistry() {
		super();

		new RegReader().readRegistry();
	}

	/**
	 * Obtains the registered toggle-dialog delegates.
	 * 
	 * @return an unmodifiable collection of the registered toggle-dialog delegates
	 */
	public Collection<IToggleDialogDelegate> getToggleDialogDelegates() {
		return Collections.unmodifiableCollection(delegatesByClass.values());
	}

	//
	// Nested types
	//

	private class RegReader extends RegistryReader {

		RegReader() {
			super(Platform.getExtensionRegistry(), Activator.PLUGIN_ID, EXT_PT);
		}

		@Override
		protected boolean readElement(IConfigurationElement element, boolean add) {
			boolean result = false;

			if (DELEGATE_ELEM.equals(element.getName())) {
				result = true; // Recognize this element

				String className = element.getAttribute(CLASS_ATTR);
				if (Strings.isNullOrEmpty(className)) {
					logMissingAttribute(element, CLASS_ATTR);
				} else if (!add) {
					// Remove the delegate
					delegatesByClass.remove(className);
				} else {
					// Adding a delegate
					if (delegatesByClass.containsKey(className)) {
						logError(element, "Duplicate registration of toggle-dialog delegate: " + className); //$NON-NLS-1$
					} else {
						try {
							IToggleDialogDelegate delegate = (IToggleDialogDelegate) element.createExecutableExtension(CLASS_ATTR);
							delegatesByClass.put(className, delegate);
						} catch (Exception e) {
							Activator.log.error("Failed to create toggle-dialog delegate", e); //$NON-NLS-1$
						}
					}
				}
			}

			return result;
		}
	}

}
