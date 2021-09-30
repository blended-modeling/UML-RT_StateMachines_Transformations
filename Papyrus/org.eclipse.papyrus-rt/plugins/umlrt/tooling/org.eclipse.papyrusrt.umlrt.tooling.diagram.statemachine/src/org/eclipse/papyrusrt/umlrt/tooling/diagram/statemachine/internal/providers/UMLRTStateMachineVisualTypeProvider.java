/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.service.visualtype.IVisualTypeProvider;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLVisualTypeProvider;
import org.eclipse.papyrusrt.umlrt.core.utils.TransitionUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateMachineDiagramVisualID;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.types.RTStateMachineTypes;

/**
 * Specific {@link IVisualTypeProvider} for RT State machines, mainly for state machines.
 */
public class UMLRTStateMachineVisualTypeProvider extends UMLVisualTypeProvider {

	/**
	 * Constructor.
	 */
	public UMLRTStateMachineVisualTypeProvider() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IElementType getElementType(Diagram diagram, String viewType) {
		if (RTStateMachineDiagramVisualID.INTERNAL_TRANSITION_LABEL.equals(viewType)) {
			return RTStateMachineTypes.INTERNAL_TRANSITION_LABEL.getType();
		}
		return super.getElementType(diagram, viewType);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeType(View parentView, EObject element) {
		if (TransitionUtils.isInternalTransition(element)) {
			if (parentView != null && RTStateMachineDiagramVisualID.INTERNAL_TRANSITION_COMPARTMENT.equals(parentView.getType())) {
				return RTStateMachineDiagramVisualID.INTERNAL_TRANSITION_LABEL;
			}
		}
		return super.getNodeType(parentView, element);
	}

}
