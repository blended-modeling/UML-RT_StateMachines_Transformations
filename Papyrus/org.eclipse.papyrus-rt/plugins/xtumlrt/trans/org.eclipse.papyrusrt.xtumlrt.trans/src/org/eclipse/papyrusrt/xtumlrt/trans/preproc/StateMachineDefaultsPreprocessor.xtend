/*******************************************************************************
 * Copyright (c) 2014-2016 Zeligsoft (2009) Limited  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.trans.preproc

import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import org.eclipse.papyrusrt.xtumlrt.statemach.CompositeState
import org.eclipse.papyrusrt.xtumlrt.statemach.State
import org.eclipse.papyrusrt.xtumlrt.statemach.Transition
import org.eclipse.papyrusrt.xtumlrt.statemach.SimpleState
import org.eclipse.papyrusrt.xtumlrt.statemach.StatemachFactory
import org.eclipse.papyrusrt.xtumlrt.statemach.InitialPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.DeepHistory
import org.eclipse.papyrusrt.xtumlrt.statemach.ChoicePoint
import org.eclipse.papyrusrt.xtumlrt.statemach.JunctionPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.EntryPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.ExitPoint
import org.eclipse.papyrusrt.xtumlrt.statemachext.StatemachextFactory
import org.eclipse.papyrusrt.xtumlrt.trans.Transformation
import org.eclipse.papyrusrt.xtumlrt.trans.TransformationContext
import org.eclipse.papyrusrt.xtumlrt.util.GlobalConstants
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.papyrusrt.xtumlrt.util.NamesUtil.*

class StateMachineDefaultsPreprocessor implements Transformation<StateMachine, StateMachine> {

	@Accessors boolean createNames = false
	@Accessors boolean createEmptyActions = false
	@Accessors boolean createDefaultGuards = false
	
	override transform(StateMachine input, TransformationContext context) {
		if(input !== null) visit(input)
		return input // This is an in-place transformation
	}

	dispatch def void visit(StateMachine stateMachine) {
		if(stateMachine.top === null)
			stateMachine.top = StatemachFactory.eINSTANCE.createCompositeState
		else
			visit(stateMachine.top)
	}

	protected def void visitState(State state) {
		if(createEmptyActions) {
			if(state.entryAction === null) {
				val newAction = StatemachextFactory.eINSTANCE.createEntryAction
				state.entryAction = newAction
				newAction.setNameIfRequested(GlobalConstants.ENTRY_ACTION_LABEL)
				newAction.source = ""
				val anno = CommonFactory.eINSTANCE.createAnnotation
				newAction.annotations.add(anno)
				anno.setNameIfRequested(GlobalConstants.GENERATED_ACTION)
			}
			if(state.exitAction === null) {
				val newAction = StatemachextFactory.eINSTANCE.createExitAction
				state.exitAction = newAction
				newAction.setNameIfRequested(GlobalConstants.EXIT_ACTION_LABEL)
				newAction.source = ""
				val anno = CommonFactory.eINSTANCE.createAnnotation
				newAction.annotations.add(anno)
				anno.setNameIfRequested(GlobalConstants.GENERATED_ACTION)
			}
		}
	}

	dispatch def void visit(SimpleState simpleState) {
		visitState(simpleState)
	}

	dispatch def void visit(CompositeState compositeState) {
		visitState(compositeState)
		compositeState.initial?.visit
		compositeState.deepHistory?.visit
		compositeState.entryPoints?.forEach[visit]
		compositeState.exitPoints?.forEach[visit]
		compositeState.substates?.forEach[visit]
		compositeState.transitions?.forEach[visit]
	}

	dispatch def void visit(InitialPoint pseudostate) {
		pseudostate.setNameIfRequested( GlobalConstants.INITIAL_LABEL )
	}

	dispatch def void visit(DeepHistory pseudostate) {
		pseudostate.setNameIfRequested( GlobalConstants.DEEP_HISTORY_LABEL )
	}

	dispatch def void visit(ChoicePoint pseudostate) {
		pseudostate.setNameIfRequested( GlobalConstants.CHOICE_POINT_LABEL )
	}

	dispatch def void visit(JunctionPoint pseudostate) {
		pseudostate.setNameIfRequested( GlobalConstants.JUNCTION_POINT_LABEL )
	}

	dispatch def void visit(EntryPoint pseudostate) {
		pseudostate.setNameIfRequested( GlobalConstants.ENTRY_POINT_LABEL )
	}

	dispatch def void visit(ExitPoint pseudostate) {
		pseudostate.setNameIfRequested( GlobalConstants.EXIT_POINT_LABEL )
	}

	dispatch def void visit(Transition transition) {
		transition.setNameIfRequested(GlobalConstants.TRANSITION_LABEL)
		if(createEmptyActions && transition.actionChain === null) {
			val newAction = StatemachFactory.eINSTANCE.createActionChain
			transition.actionChain = newAction
			newAction.setNameIfRequested(GlobalConstants.ACTION_CHAIN_LABEL)
		}
		if(createDefaultGuards && transition.guard === null) {
			val newGuard = StatemachFactory.eINSTANCE.createGuard
			transition.guard = newGuard
			newGuard.setNameIfRequested(GlobalConstants.GUARD_LABEL)
			val newGuardBody = CommonFactory.eINSTANCE.createActionCode
			newGuard.body = newGuardBody
			newGuardBody.setNameIfRequested(GlobalConstants.GUARD_BODY_LABEL)
			newGuardBody.source = "return true;" // TODO: This should create something according to the specific action language, so this must be replaced by a language-independent approach.
		}
	}

	private def setNameIfRequested(NamedElement element, String proposedName) {
		if (createNames && !element.hasName) {
			element.name = element.cachedEffectiveName
		}
	}

}
