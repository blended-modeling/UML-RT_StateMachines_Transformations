/**
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.papyrusrt.codegen.statemachines.transformations

import org.eclipse.papyrusrt.xtumlrt.common.ActionCode
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.common.Parameter
import org.eclipse.papyrusrt.xtumlrt.common.Port
import org.eclipse.papyrusrt.xtumlrt.common.Signal
import org.eclipse.papyrusrt.xtumlrt.statemach.ActionChain
import org.eclipse.papyrusrt.xtumlrt.statemach.ChoicePoint
import org.eclipse.papyrusrt.xtumlrt.statemach.CompositeState
import org.eclipse.papyrusrt.xtumlrt.statemach.DeepHistory
import org.eclipse.papyrusrt.xtumlrt.statemach.EntryPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.ExitPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.Guard
import org.eclipse.papyrusrt.xtumlrt.statemach.InitialPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.JunctionPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.Pseudostate
import org.eclipse.papyrusrt.xtumlrt.statemach.SimpleState
import org.eclipse.papyrusrt.xtumlrt.statemach.State
import org.eclipse.papyrusrt.xtumlrt.statemach.StatemachFactory
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import org.eclipse.papyrusrt.xtumlrt.statemach.Transition
import org.eclipse.papyrusrt.xtumlrt.statemach.Trigger
import org.eclipse.papyrusrt.xtumlrt.statemach.Vertex
import org.eclipse.papyrusrt.xtumlrt.statemachext.EntryAction
import org.eclipse.papyrusrt.xtumlrt.statemachext.ExitAction
import org.eclipse.papyrusrt.xtumlrt.statemachext.StatemachextFactory
import org.eclipse.papyrusrt.xtumlrt.statemachext.TransitionAction
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPort
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTTrigger
import org.eclipse.papyrusrt.xtumlrt.umlrt.UmlrtFactory
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTSMVirtualInheritanceExtensions.*
import org.eclipse.papyrusrt.xtumlrt.statemachext.GuardAction

/**
 * This class provides a function to transform a state machine into an
 * equivalent state machine where the inheritance relation has been flattened.
 *
 * @author Ernesto Posse
 */
class StateMachineInheritanceFlattener extends FunctionalTransformation
{

    override StateMachine transform
    (
        StateMachine stateMachine,
        TransformationContext context
    )
    {
        setup( stateMachine, context )
        getExpanded( stateMachine ) as StateMachine
    }

    dispatch def NamedElement
    create StatemachFactory.eINSTANCE.createStateMachine
    getExpanded( StateMachine stateMachine )
    {
        translator.replace( stateMachine, it )
        flattener.addNewElementCorrespondence( it, stateMachine )
        name = stateMachine.name
        if (stateMachine.top !== null)
            top = getExpanded( stateMachine.top ) as CompositeState
    }

    dispatch def NamedElement
    create StatemachFactory.eINSTANCE.createSimpleState
    getExpanded( SimpleState state )
    {
        translator.replace( state, it )
        flattener.addNewElementCorrespondence( it, state )
        expandConnectionPoints( state, it )
    }

    dispatch def NamedElement
    create StatemachFactory.eINSTANCE.createCompositeState
    getExpanded( CompositeState state )
    {
        translator.replace( state, it )
        flattener.addNewElementCorrespondence( it, state )
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
        newState.entryAction = if (oldState.entryAction !== null) getActionCodeCopy( oldState.entryAction as ActionCode )
        newState.exitAction  = if (oldState.exitAction !== null) getActionCodeCopy( oldState.exitAction as ActionCode )
    }

    dispatch def NamedElement
    create StatemachFactory.eINSTANCE.createInitialPoint
    getExpanded( InitialPoint initialPoint )
    {
        translator.replace( initialPoint, it )
        flattener.addNewElementCorrespondence( it, initialPoint )
        name = initialPoint.name
    }

    dispatch def NamedElement
    create StatemachFactory.eINSTANCE.createDeepHistory
    getExpanded( DeepHistory deepHistory )
    {
        translator.replace( deepHistory, it )
        flattener.addNewElementCorrespondence( it, deepHistory )
        name = deepHistory.name
    }

    dispatch def NamedElement
    create StatemachFactory.eINSTANCE.createChoicePoint
    getExpanded( ChoicePoint choicePoint )
    {
        translator.replace( choicePoint, it )
        flattener.addNewElementCorrespondence( it, choicePoint )
        name = choicePoint.name
    }

    dispatch def NamedElement
    create StatemachFactory.eINSTANCE.createJunctionPoint
    getExpanded( JunctionPoint junctionPoint )
    {
        translator.replace( junctionPoint, it )
        flattener.addNewElementCorrespondence( it, junctionPoint )
        name = junctionPoint.name
    }

    dispatch def NamedElement
    create StatemachFactory.eINSTANCE.createEntryPoint
    getExpanded( EntryPoint entryPoint )
    {
        translator.replace( entryPoint, it )
        flattener.addNewElementCorrespondence( it, entryPoint )
        name = entryPoint.name
    }

    dispatch def NamedElement
    create StatemachFactory.eINSTANCE.createExitPoint
    getExpanded( ExitPoint exitPoint )
    {
        translator.replace( exitPoint, it )
        flattener.addNewElementCorrespondence( it, exitPoint )
        name = exitPoint.name
    }

    dispatch def NamedElement getExpanded( Pseudostate pseudostate )
    {
    }

    def NamedElement
    create StatemachFactory.eINSTANCE.createTransition
    getExpanded( Transition transition, CompositeState container )
    {
        translator.replace( transition, it )
        flattener.addNewElementCorrespondence( it, transition )
        name = transition.name
        val sourceInContainer = transition.getTransitionSourceInState( container as CompositeState )
        val targetInContainer = transition.getTransitionTargetInState( container as CompositeState )
        sourceVertex = getExpanded( sourceInContainer ) as Vertex
        targetVertex = getExpanded( targetInContainer ) as Vertex
        triggers.addAll( transition.allTriggers.map [ it.triggerCopy ] )
        guard = if (transition.guard !== null) getGuardCopy( transition.guard )
        actionChain = if (transition.actionChain !== null) getActionChainCopy( transition.actionChain )
    }

    dispatch def Trigger
    create StatemachFactory.eINSTANCE.createTrigger
    getTriggerCopy( Trigger trigger )
    {
        translator.replace( trigger, it )
        flattener.addNewElementCorrespondence( it, trigger )
        name = trigger.name
    }

    dispatch def RTTrigger
    create UmlrtFactory.eINSTANCE.createRTTrigger
    getTriggerCopy( RTTrigger trigger )
    {
        translator.replace( trigger, it )
        flattener.addNewElementCorrespondence( it, trigger )
        name = trigger.name
        ports.addAll( trigger.ports )   // Don't copy ports and signals because these belong to Capsules and Protocols respectively.
        signal = trigger.signal
        triggerGuard = if (trigger.triggerGuard !== null) getGuardCopy( trigger.triggerGuard )
    }

    def Guard
    create StatemachFactory.eINSTANCE.createGuard
    getGuardCopy( Guard guard )
    {
        translator.replace( guard, it )
        flattener.addNewElementCorrespondence( it, guard )
        name = guard.name
        body = if (guard.body !== null) getActionCodeCopy( guard.body as ActionCode )
    }

    def ActionChain
    create StatemachFactory.eINSTANCE.createActionChain
    getActionChainCopy( ActionChain actionChain )
    {
        flattener.addNewElementCorrespondence( it, actionChain )
        name = actionChain.name
        actions.addAll( actionChain.actions.map [ getActionCodeCopy( it as ActionCode ) as ActionCode ] )
    }

    dispatch def ActionCode
    create CommonFactory.eINSTANCE.createActionCode
    getActionCodeCopy( ActionCode action )
    {
        translator.replace( action, it )
        flattener.addNewElementCorrespondence( it, action )
        name = action.name
        source = action.source
    }

    dispatch def ActionCode
    create StatemachextFactory.eINSTANCE.createEntryAction
    getActionCodeCopy( EntryAction action )
    {
        translator.replace( action, it )
        flattener.addNewElementCorrespondence( it, action )
        name = action.name
        annotations.addAll(action.annotations)
        source = action.source
    }

    dispatch def ActionCode
    create StatemachextFactory.eINSTANCE.createExitAction
    getActionCodeCopy( ExitAction action )
    {
        translator.replace( action, it )
        flattener.addNewElementCorrespondence( it, action )
        name = action.name
        annotations.addAll(action.annotations)
        source = action.source
    }

    dispatch def ActionCode
    create StatemachextFactory.eINSTANCE.createGuardAction
    getActionCodeCopy( GuardAction action )
    {
        translator.replace( action, it )
        flattener.addNewElementCorrespondence( it, action )
        name = action.name
        source = action.source
    }

    dispatch def ActionCode
    create StatemachextFactory.eINSTANCE.createTransitionAction
    getActionCodeCopy( TransitionAction action )
    {
        translator.replace( action, it )
        flattener.addNewElementCorrespondence( it, action )
        name = action.name
        source = action.source
    }

    dispatch def Port
    create CommonFactory.eINSTANCE.createPort
    getPortCopy( Port port )
    {
        translator.replace( port, it )
        flattener.addNewElementCorrespondence( it, port )
        name = port.name
        conjugate = port.conjugate
        type = port.type
    }

    dispatch def RTPort
    create UmlrtFactory.eINSTANCE.createRTPort
    getPortCopy( RTPort port )
    {
        translator.replace( port, it )
        flattener.addNewElementCorrespondence( it, port )
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
        translator.replace( signal, it )
        flattener.addNewElementCorrespondence( it, signal )
        name = signal.name
        parameters.addAll( signal.parameters.map [ getParameterCopy( it ) ] )
    }

    def Parameter
    create CommonFactory.eINSTANCE.createParameter
    getParameterCopy( Parameter parameter )
    {
        translator.replace( parameter, it )
        flattener.addNewElementCorrespondence( it, parameter )
        name = parameter.name
        type = parameter.type
        direction = parameter.direction
    }

}