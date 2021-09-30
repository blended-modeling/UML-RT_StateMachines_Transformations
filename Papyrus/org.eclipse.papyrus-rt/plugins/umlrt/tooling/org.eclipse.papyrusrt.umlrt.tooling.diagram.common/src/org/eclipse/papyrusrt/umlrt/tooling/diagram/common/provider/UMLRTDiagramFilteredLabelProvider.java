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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.services.labelprovider.service.IFilteredLabelProvider;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTCapsuleStructureDiagramUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTStateMachineDiagramUtils;

/**
 * Label provider used by the label provider service
 */
public class UMLRTDiagramFilteredLabelProvider extends LabelProvider implements IFilteredLabelProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accept(Object element) {
		EObject eObject = EMFHelper.getEObject(element);

		// element should be an UML element at least. UML-RT profile should be present.
		if (!(eObject instanceof Diagram)) {
			return false;
		}

		if (UMLRTCapsuleStructureDiagramUtils.isCapsuleStructureDiagram((Diagram) eObject)) {
			return true;
		}

		if (UMLRTStateMachineDiagramUtils.isRTStateMachineDiagram((Diagram) eObject)) {
			return true;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(Object element) {
		EObject eObject = EMFHelper.getEObject(element);
		if (eObject == null) {
			return super.getText(element);
		}

		if (UMLRTCapsuleStructureDiagramUtils.isCapsuleStructureDiagram((Diagram) eObject)) {
			return UMLRTCapsuleStructureDiagramUtils.getDisplayedCapsuleStructureDiagramName((Diagram) eObject);
		}

		if (UMLRTStateMachineDiagramUtils.isRTStateMachineDiagram((Diagram) eObject)) {
			return UMLRTStateMachineDiagramUtils.getDisplayedDiagramName((Diagram) eObject);
		}

		return super.getText(element);
	}

}
