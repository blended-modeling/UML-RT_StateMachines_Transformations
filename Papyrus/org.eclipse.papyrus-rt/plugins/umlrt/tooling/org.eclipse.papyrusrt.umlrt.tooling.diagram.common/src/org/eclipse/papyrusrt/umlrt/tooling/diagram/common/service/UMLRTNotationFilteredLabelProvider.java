/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 492165, 493866
 *  Young-Soo Roh - Bug 497805
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.service;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.NotationFilteredLabelProvider;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTCapsuleStructureDiagramUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTStateMachineDiagramUtils;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Vertex;

/**
 * Specific label provider for UML-RT diagrams, where the name can be null
 */
public class UMLRTNotationFilteredLabelProvider extends NotationFilteredLabelProvider {

	/**
	 * Initializes me.
	 */
	public UMLRTNotationFilteredLabelProvider() {
		super();
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.providers.NotationFilteredLabelProvider#accept(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean accept(Object element) {
		boolean result = super.accept(element);
		if (result) {
			EObject eObject = EMFHelper.getEObject(element);
			result = eObject instanceof Diagram;
		}
		return result;
	}


	@Override
	protected String getText(EObject element) {
		// Be sure to invoke the label provider to get updates from the diagram
		String result = super.getText(element);

		if (element instanceof Diagram) {
			Diagram diagram = (Diagram) element;
			EObject context = diagram.getElement();

			if (context != null) {
				// Be sure to invoke the label provider to get updates from the context
				result = super.getText(context);
			}

			BehavioredClassifier behaviorContext = null;
			if (context instanceof Behavior) {
				behaviorContext = ((Behavior) context).getContext();
			} else if (context instanceof Vertex) {
				behaviorContext = ((Vertex) context).containingStateMachine().getContext();
			}
			if (behaviorContext != null) {
				// Be sure to invoke the label provider to get updates from
				// the behavior context
				super.getText(behaviorContext);
			}

			// if name is null, return the default name of the diagram
			if (UMLRTCapsuleStructureDiagramUtils.isCapsuleStructureDiagram(diagram)) {
				result = UMLRTCapsuleStructureDiagramUtils.getDisplayedCapsuleStructureDiagramName(diagram);
				subscribe(context, diagram);
			} else if (UMLRTStateMachineDiagramUtils.isRTStateMachineDiagram(diagram)) {
				result = UMLRTStateMachineDiagramUtils.getDisplayedDiagramName(diagram);
				subscribe(context, diagram);
				if (behaviorContext != null) {
					subscribe(behaviorContext, diagram);
				}
			} else {
				result = diagram.getName();

				// In case we were deriving a label from them
				unsubscribe(context, diagram);
				if (behaviorContext != null) {
					unsubscribe(behaviorContext, diagram);
				}
			}
		}

		return result;
	}
}
