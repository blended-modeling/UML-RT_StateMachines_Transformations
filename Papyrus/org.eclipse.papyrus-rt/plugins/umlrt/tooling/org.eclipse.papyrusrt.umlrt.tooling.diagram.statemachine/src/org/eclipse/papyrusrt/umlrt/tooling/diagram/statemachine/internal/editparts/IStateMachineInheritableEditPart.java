/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts;

import java.util.Optional;
import java.util.function.Function;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrusrt.umlrt.core.utils.Either;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTStateMachineDiagramUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;

/**
 * A specialization of the {@link IInheritableEditPart} protocol for edit-parts
 * in state machine diagrams.
 */
public interface IStateMachineInheritableEditPart extends IInheritableEditPart {

	@Override
	default UMLRTNamedElement getRedefinitionContext(UMLRTNamedElement element) {
		return EditPartInheritanceUtils.getStateMachineContext(element).getEither(UMLRTNamedElement.class);
	}

	@Override
	default Diagram getPrimaryDiagram(UMLRTNamedElement context) {
		Function<State, Diagram> stateDiagram = UMLRTStateMachineDiagramUtils::getStateMachineDiagram;
		Function<StateMachine, Diagram> stateMachineDiagram = UMLRTStateMachineDiagramUtils::getStateMachineDiagram;

		return Either.cast(context, UMLRTState.class, UMLRTStateMachine.class)
				.map(UMLRTState::toUML, UMLRTStateMachine::toUML)
				.flatMap(stateDiagram.andThen(Optional::ofNullable),
						stateMachineDiagram.andThen(Optional::ofNullable))
				.orElse(Diagram.class, null);
	}

}
