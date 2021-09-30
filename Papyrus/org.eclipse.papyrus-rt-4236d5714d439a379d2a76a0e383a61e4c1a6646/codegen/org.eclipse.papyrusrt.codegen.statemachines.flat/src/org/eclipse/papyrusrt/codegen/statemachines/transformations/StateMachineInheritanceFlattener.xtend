/**
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.papyrusrt.codegen.statemachines.transformations

import org.eclipse.papyrusrt.xtumlrt.common.ActionChain
import org.eclipse.papyrusrt.xtumlrt.common.ActionCode
import org.eclipse.papyrusrt.xtumlrt.common.ChoicePoint
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory
import org.eclipse.papyrusrt.xtumlrt.common.CompositeState
import org.eclipse.papyrusrt.xtumlrt.common.DeepHistory
import org.eclipse.papyrusrt.xtumlrt.common.EntryPoint
import org.eclipse.papyrusrt.xtumlrt.common.ExitPoint
import org.eclipse.papyrusrt.xtumlrt.common.Guard
import org.eclipse.papyrusrt.xtumlrt.common.InitialPoint
import org.eclipse.papyrusrt.xtumlrt.common.JunctionPoint
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.common.Parameter
import org.eclipse.papyrusrt.xtumlrt.common.Port
import org.eclipse.papyrusrt.xtumlrt.common.Pseudostate
import org.eclipse.papyrusrt.xtumlrt.common.Signal
import org.eclipse.papyrusrt.xtumlrt.common.SimpleState
import org.eclipse.papyrusrt.xtumlrt.common.State
import org.eclipse.papyrusrt.xtumlrt.common.StateMachine
import org.eclipse.papyrusrt.xtumlrt.common.Transition
import org.eclipse.papyrusrt.xtumlrt.common.Vertex
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPort
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTTrigger
import org.eclipse.papyrusrt.xtumlrt.umlrt.UmlrtFactory
import static extension org.eclipse.papyrusrt.codegen.utils.XTUMLRTStateMachineUtil.*
import static extension org.eclipse.papyrusrt.codegen.utils.XTUMLRTSMVirtualInheritanceExtensions.*
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelFactory
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.EntryAction
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.ExitAction
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.TransitionAction
import org.eclipse.papyrusrt.xtumlrt.common.Trigger

/**
 * This class provides a function to transform a state machine into an
 * equivalent state machine where the inheritance relation has been flattened.
 *
 * @author Ernesto Posse
 */
class StateMachineInheritanceFlattener implements FunctionalTransformation
{

    override StateMachine transform
    (
        StateMachine stateMachine,
        TransformationContext context
    )
    {
        getExpanded( stateMachine ) as StateMachine
    }

    dispatch def NamedElement
    create CommonFactory.eINSTANCE.createStateMachine
    getExpanded( StateMachine stateMachine )
    {
        name = stateMachine.name
        if (stateMachine.top !== null)
            top = getExpanded( stateMachine.top ) as CompositeState
    }

    dispatch def NamedElement
    create CommonFactory.eINSTANCE.createSimpleState
    getExpanded( SimpleState state )
    {
        expandConnectionPoints( state, it )
    }

    dispatch def NamedElement
    create CommonFactory.eINSTANCE.createCompositeState
    getExpanded( CompositeState state )
    {
        expandConnectionPoints( state, it )
        val init = state.actualInitialPoint
        if (init !== null)
            initial     = getExpanded( init ) as InitialPoint
        if (deepHistory !== null)
            deepHistory = getExpanded( state.actualDeepHistory ) as DeepHistory
        choicePoints   .addAll( state.allChoicePoints.   map [ getExpanded( it ) as ChoicePoint ] )
        junctionPoints .addAll( state.allJunctionPoints. map [ getExpanded( it ) as JunctionPoint ] )
        substates      .addAll( state.allSubstates.      map [ getExpanded( it ) as State ] )
        transitions    .addAll( state.allTransitions.    map [ getExpanded( it, state ) as Transition ] )
    }

    private def void expandConnectionPoints( State oldState, State newState )
    {
        newState.name = oldState.name
        newState.entryPoints .addAll( oldState.allEntryPoints. map [ getExpanded( it ) as EntryPoint ] )
        newState.exitPoints  .addAll( oldState.allExitPoints.  map [ getExpanded( it ) as ExitPoint ] )
        newState.entryAction = if (oldState.entryAction !== null) getActionCodeCopy( oldState.entryAction )
        newState.exitAction  = if (oldState.exitAction !== null) getActionCodeCopy( oldState.exitAction )
    }

    dispatch def NamedElement
    create CommonFactory.eINSTANCE.createInitialPoint
    getExpanded( InitialPoint initialPoint )
    {
        name = initialPoint.name
    }

    dispatch def NamedElement
    create CommonFactory.eINSTANCE.createDeepHistory
    getExpanded( DeepHistory deepHistory )
    {
        name = deepHistory.name
    }

    dispatch def NamedElement
    create CommonFactory.eINSTANCE.createChoicePoint
    getExpanded( ChoicePoint choicePoint )
    {
        name = choicePoint.name
    }

    dispatch def NamedElement
    create CommonFactory.eINSTANCE.createJunctionPoint
    getExpanded( JunctionPoint junctionPoint )
    {
        name = junctionPoint.name
    }

    dispatch def NamedElement
    create CommonFactory.eINSTANCE.createEntryPoint
    getExpanded( EntryPoint entryPoint )
    {
        name = entryPoint.name
    }

    dispatch def NamedElement
    create CommonFactory.eINSTANCE.createExitPoint
    getExpanded( ExitPoint exitPoint )
    {
        name = exitPoint.name
    }

    dispatch def NamedElement getExpanded( Pseudostate pseudostate )
    {
    }

    def NamedElement
    create CommonFactory.eINSTANCE.createTransition
    getExpanded( Transition transition, CompositeState container )
    {
        name = transition.name
        val sourceInContainer = transition.getTransitionSourceInState( container as CompositeState )
        val targetInContainer = transition.getTransitionTargetInState( container as CompositeState )
        sourceVertex = getExpanded( sourceInContainer ) as Vertex
        targetVertex = getExpanded( targetInContainer ) as Vertex
        triggers.addAll( transition.triggers.map [ it.triggerCopy ] )
        guard = if (transition.guard !== null) getGuardCopy( transition.guard )
        actionChain = if (transition.actionChain !== null) getActionChainCopy( transition.actionChain )
    }

    dispatch def Trigger
    create CommonFactory.eINSTANCE.createTrigger
    getTriggerCopy( Trigger trigger )
    {
        name = trigger.name
    }

    dispatch def RTTrigger
    create UmlrtFactory.eINSTANCE.createRTTrigger
    getTriggerCopy( RTTrigger trigger )
    {
        name = trigger.name
        ports.addAll( trigger.ports )   // Don't copy ports and signals because these belong to Capsules and Protocols respectively.
        signal = trigger.signal
    }

    def Guard
    create CommonFactory.eINSTANCE.createGuard
    getGuardCopy( Guard guard )
    {
        name = guard.name
        body = if (guard.body !== null) getActionCodeCopy( guard.body )
    }

    def ActionChain
    create CommonFactory.eINSTANCE.createActionChain
    getActionChainCopy( ActionChain actionChain )
    {
        name = actionChain.name
        actions.addAll( actionChain.actions.map [ getActionCodeCopy( it ) as ActionCode ] )
    }

    dispatch def ActionCode
    create CommonFactory.eINSTANCE.createActionCode
    getActionCodeCopy( ActionCode action )
    {
        name = action.name
        source = action.source
    }

    dispatch def ActionCode
    create SmflatmodelFactory.eINSTANCE.createEntryAction
    getActionCodeCopy( EntryAction action )
    {
        name = action.name
        source = action.source
    }

    dispatch def ActionCode
    create SmflatmodelFactory.eINSTANCE.createExitAction
    getActionCodeCopy( ExitAction action )
    {
        name = action.name
        source = action.source
    }

    dispatch def ActionCode
    create SmflatmodelFactory.eINSTANCE.createTransitionAction
    getActionCodeCopy( TransitionAction action )
    {
        name = action.name
        source = action.source
    }

    dispatch def Port
    create CommonFactory.eINSTANCE.createPort
    getPortCopy( Port port )
    {
        name = port.name
        conjugate = port.conjugate
        type = port.type
    }

    dispatch def RTPort
    create UmlrtFactory.eINSTANCE.createRTPort
    getPortCopy( RTPort port )
    {
        name = port.name
        conjugate = port.conjugate
        type = port.type
        notification = port.notification
        publish = port.publish
        wired = port.wired
        registration = port.registration
        registrationOverride = port.registrationOverride
    }

    def Signal
    create CommonFactory.eINSTANCE.createSignal
    getSignalCopy( Signal signal )
    {
        name = signal.name
        parameters.addAll( signal.parameters.map [ getParameterCopy( it ) ] )
    }

    def Parameter
    create CommonFactory.eINSTANCE.createParameter
    getParameterCopy( Parameter parameter )
    {
        name = parameter.name
        type = parameter.type
        direction = parameter.direction
    }

}