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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts;

/**
 * Stores all the Visual IDs used for state machine diagram expansion.
 */
public final class RTStateMachineDiagramVisualID {

	/** Visual hint for the internal transition labels. */
	public static final String INTERNAL_TRANSITION_LABEL = "InternalTransition_Label"; //$NON-NLS-1$

	/** Visual hint for the internal transition compartment. */
	public static final String INTERNAL_TRANSITION_COMPARTMENT = InternalTransitionsCompartmentEditPart.VISUAL_ID;

	/**
	 * Constructor.
	 */
	private RTStateMachineDiagramVisualID() {
		// empty
	}


}
