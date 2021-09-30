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

package org.eclipse.papyrusrt.umlrt.tooling.ui.providers;

import java.util.ArrayList;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;

public class PortContentProvider implements IStaticContentProvider {

	Class capsule;

	public PortContentProvider(Class capsule) {
		this.capsule = capsule;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getElements();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		viewer.refresh();
	}

	/**
	 * Top-level element list
	 *
	 * @return
	 */
	@Override
	public Object[] getElements() {
		ArrayList<Port> events = new ArrayList<>();

		for (Property attribute : capsule.getAllAttributes()) {
			if (attribute instanceof Port) {
				Port port = (Port) attribute;
				if (port.isBehavior()) {
					events.add(port);
				}
			}
		}
		return events.toArray();
	}
}
