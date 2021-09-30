/*****************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Ltd. and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Ernesto Posse - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrusrt.codegen.statemachines.transformations

import org.eclipse.papyrusrt.codegen.CodeGenPlugin
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.statemach.CompositeState
import org.eclipse.papyrusrt.xtumlrt.statemach.SimpleState
import org.eclipse.papyrusrt.xtumlrt.statemach.State
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import org.eclipse.papyrusrt.xtumlrt.statemach.Transition
import static extension org.eclipse.papyrusrt.xtumlrt.util.ContainmentUtils.*

/**
 * This visitor goes through the state machine before flattening to pre-compute the full
 * containment chain of all elements. This is needed because the flattening process changes, by
 * definition the containment relationships and therefore the qualified names of elements, but
 * the original qualified names are needed to disambiguate element in the generated code.
 * 
 * <p> This is implemented as a simple visitor on state machine elements which invokes the
 * {@link org.eclipse.papyrusrt.xtumlrt.util.ContainmentUtils.cachedContainmentChain} method on 
 * each element. Since that method caches the list, subsequent invocations of that method in the 
 * generator, will return the original qualified name.
 * 
 * @author epp
 */
class ContainmentChainsPreprocessor {

	public static val INSTANCE = new ContainmentChainsPreprocessor

	def boolean cacheAllChains(StateMachine stateMachine) {
		try {
			visit(stateMachine)
			return true
		} catch (Exception e) {
			CodeGenPlugin.error("[QualifiedNamePreprocessor] error preprocessing state machine", e)
			return false
		}
	}

	dispatch def void visit(StateMachine stateMachine) {
		stateMachine.cachedFullContainmentChain
		if (stateMachine.top !== null)
			visit(stateMachine.top)
	}

	dispatch def void visit(CompositeState state) {
		visitState(state)
		state.initial?.cachedFullContainmentChain
		state.deepHistory?.cachedFullContainmentChain
		state.choicePoints.forEach[cachedFullContainmentChain]
		state.junctionPoints.forEach[cachedFullContainmentChain]
		state.substates.forEach[visit]
		state.transitions.forEach[visit]
	}

	dispatch def void visit(SimpleState state) {
		visitState(state)
	}

	private def void visitState(State state) {
		state.cachedFullContainmentChain
		state.entryPoints.forEach[cachedFullContainmentChain]
		state.exitPoints.forEach[cachedFullContainmentChain]
	}

	dispatch def void visit(Transition transition) {
		transition.cachedFullContainmentChain
		transition.triggers.forEach[cachedFullContainmentChain]
		transition.guard?.cachedFullContainmentChain
		transition.actionChain?.cachedFullContainmentChain
	}

	dispatch def void visit(NamedElement element) {
		element?.cachedFullContainmentChain
	}
}
