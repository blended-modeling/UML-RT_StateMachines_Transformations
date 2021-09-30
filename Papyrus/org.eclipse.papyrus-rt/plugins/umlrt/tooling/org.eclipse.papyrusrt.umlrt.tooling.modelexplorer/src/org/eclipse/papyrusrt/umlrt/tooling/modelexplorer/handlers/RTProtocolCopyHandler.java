/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.views.modelexplorer.handler.CopyHandler;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.uml2.uml.Collaboration;

/**
 * Specific copy handler for model explorer
 */
public class RTProtocolCopyHandler extends CopyHandler {

	/**
	 * @see org.eclipse.papyrus.views.modelexplorer.handler.AbstractCommandHandler#getSelectedElements()
	 *
	 * @return
	 */
	@Override
	protected List<EObject> getSelectedElements() {
		List<EObject> result = super.getSelectedElements();
		if (result == null || result.isEmpty()) {
			return result;
		}

		for (EObject selectedElement : result) {
			if (ProtocolUtils.isProtocol(selectedElement)) {
				// a copy list should be created, with protocol replaced by their protocol containers
				return getListOfSelectedElementsWithProtocolReplaced(result);
			}
		}
		return result;
	}

	/**
	 * Copies the given list and replace the protocol inside the list by their protocolcontainers
	 * 
	 * @param selectedElements
	 *            the list to update
	 * @return the new list, with protocol containers rather than protocol
	 */
	protected static List<EObject> getListOfSelectedElementsWithProtocolReplaced(Collection<EObject> selectedElements) {
		List<EObject> result = new ArrayList<EObject>(selectedElements);
		for (EObject object : selectedElements) {
			if (ProtocolUtils.isProtocol(object)) {
				int i = result.indexOf(object);
				result.remove(i);
				result.add(i, ProtocolUtils.getProtocolContainer((Collaboration) object));
			}
		}
		return result;
	}

}