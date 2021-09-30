/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml;

import org.eclipse.papyrusrt.umlrt.uml.internal.util.ReificationAdapter;
import org.eclipse.papyrusrt.umlrt.uml.util.ReificationListener;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;

/**
 * @author damus
 *
 */
public interface FacadeObject {

	Element toUML();

	boolean isExcluded();

	/**
	 * Adds a reification {@code listener}. Has no effect if this listener
	 * was already added previously.
	 * 
	 * @param listener
	 *            the listener to receive notifications when I am reified
	 *            or virtualized
	 */
	default void addReificationListener(ReificationListener listener) {
		Element element = toUML();
		if (element instanceof NamedElement) {
			ReificationAdapter adapter = ReificationAdapter.getInstance(element);
			if (adapter != null) {
				adapter.addListener((NamedElement) element, listener);
			}
		}
	}

	/**
	 * Removes a reification {@code listener}. Has no effect if this listener
	 * was not added previously.
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	default void removeReificationListener(ReificationListener listener) {
		Element element = toUML();
		if (element instanceof NamedElement) {
			ReificationAdapter adapter = ReificationAdapter.getInstance(element);
			if (adapter != null) {
				adapter.removeListener((NamedElement) element, listener);
			}
		}
	}

}
