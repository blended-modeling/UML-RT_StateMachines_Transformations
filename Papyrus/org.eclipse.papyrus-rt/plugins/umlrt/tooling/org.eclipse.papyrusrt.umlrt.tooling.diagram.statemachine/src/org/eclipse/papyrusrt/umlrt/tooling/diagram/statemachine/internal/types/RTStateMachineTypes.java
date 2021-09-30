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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.types;

import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateMachineDiagramVisualID;

/**
 * Stores the element types provided by the RT State Machine Diagram.
 */
public enum RTStateMachineTypes {

	/** internal transition as label. */
	INTERNAL_TRANSITION_LABEL("org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.InternalTransition_Label", RTStateMachineDiagramVisualID.INTERNAL_TRANSITION_LABEL),

	/** pseudo state exit as node. */
	PSEUDOSTATE_EXIT_NODE("org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.RTPseudoState_ExitPointShape", PseudostateExitPointEditPart.VISUAL_ID),

	/** pseudo state entry as node. */
	PSEUDOSTATE_ENTRY_NODE("org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.RTPseudoState_EntryPointShape", PseudostateEntryPointEditPart.VISUAL_ID);

	private String hint;

	private String identifier;

	/**
	 * Constructor.
	 *
	 * @param identifier
	 *            identifier for this element type
	 * @param hint
	 *            semantic hint used for visual representations.
	 */
	private RTStateMachineTypes(String identifier, String hint) {
		this.identifier = identifier;
		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}

	public String getIdentifier() {
		return identifier;
	}

	public IElementType getType() {
		return ElementTypeRegistry.getInstance().getType(identifier);
	}

	@Override
	public String toString() {
		return identifier + "(" + getType() + ") - " + getType();
	}
}
