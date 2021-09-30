/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Celine Janssens (ALL4TEC) celine.janssens@all4tec.net
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.copy;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.copy.AbstractCopyStrategy;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.uml2.uml.Collaboration;

/**
 * Copy Strategy for UML RT Elements
 * 
 * @author Céline JANSSENS
 *
 */
public class UmlRTCopyStrategy extends AbstractCopyStrategy {

	@Override
	public void prepareElementsInClipboard(List<EObject> elementsInClipboard, Collection<EObject> selectedElements) {

		for (EObject selection : selectedElements) {
			if (ProtocolUtils.isProtocol(selection)) {
				elementsInClipboard.add(ProtocolUtils.getProtocolContainer((Collaboration) selection));
				elementsInClipboard.remove(selection);
			}


		}

	}

}
