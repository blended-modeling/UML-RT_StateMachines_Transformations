/*******************************************************************************
* Copyright (c) 2015 - 2016 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.trans.from.uml

import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrusrt.xtumlrt.common.ActionCode
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement
import org.eclipse.papyrusrt.xtumlrt.common.Signal
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
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import org.eclipse.papyrusrt.xtumlrt.statemach.StatemachFactory
import org.eclipse.papyrusrt.xtumlrt.statemach.Transition
import org.eclipse.papyrusrt.xtumlrt.statemach.Trigger
import org.eclipse.papyrusrt.xtumlrt.statemach.Vertex
import org.eclipse.papyrusrt.xtumlrt.statemachext.EntryAction
import org.eclipse.papyrusrt.xtumlrt.statemachext.ExitAction
import org.eclipse.papyrusrt.xtumlrt.statemachext.StatemachextFactory
import org.eclipse.papyrusrt.xtumlrt.statemachext.TransitionAction
import org.eclipse.papyrusrt.xtumlrt.umlrt.PortKind
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPort
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTTrigger
import org.eclipse.papyrusrt.xtumlrt.umlrt.UmlrtFactory
import org.eclipse.papyrusrt.xtumlrt.util.ActionLangUtils
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTAnnotations
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTLogger
import org.eclipse.uml2.uml.PseudostateKind

import static extension org.eclipse.papyrusrt.xtumlrt.util.GeneralUtil.*
import static extension org.eclipse.papyrusrt.xtumlrt.util.NamesUtil.*
import static extension org.eclipse.papyrusrt.xtumlrt.external.predefined.UMLRTStateMachProfileUtil.*

class UML2xtumlrtSMTranslator extends UML2xtumlrtTranslator
{

    // UML2xtumlrtTranslator provides methods to translate ports and signals, used in triggers
    UML2xtumlrtModelTranslator uml2xtumlrtTranslator

    new (UML2xtumlrtModelTranslator uml2xtumlrtTranslator)
    {
        this.uml2xtumlrtTranslator = uml2xtumlrtTranslator
    }

    dispatch def CommonElement translate( org.eclipse.uml2.uml.Element element )
    {
    }

    /**
     * This is the main method. It creates a dummy composite state to be used
     * as the State Machine's top composite state.
     *
     * @param originalStateMachine  - A {@link org.eclipse.uml2.uml.StateMachine}
     * @return A {@link StateMachine}
     */
    dispatch def StateMachine
    create StatemachFactory.eINSTANCE.createStateMachine
    translate( org.eclipse.uml2.uml.StateMachine originalStateMachine )
    {
        name = originalStateMachine.name
        top = translateElement( originalStateMachine.ownedRegion ) as CompositeState
        translateRedefinableElement( originalStateMachine.ownedRegion, top )
        translateRedefinableElement( originalStateMachine, it )
        XTUMLRTAnnotations.setAnnotations( originalStateMachine, it )
        
    }
    
    /**
     * Translates a region into a composite state. This is because in UML-RT, (composite) states
     * have exactly one region, so there is a one-to-one correspondance between them.
     */
    dispatch def CompositeState
    create
        if (region === null) null
        else StatemachFactory.eINSTANCE.createCompositeState
    translate( org.eclipse.uml2.uml.Region region )
    {
        if (region !== null)
        {
            initial = translateOptionalElement( region.initialPoint ) as InitialPoint
            deepHistory = translateOptionalElement( region.deepHistoryPoint ) as DeepHistory
            for (element : getChoicePoints( region ))
            {
                choicePoints.addIfNotNull( translateElement(element) as ChoicePoint )
            }
            for (element : getJunctionPoints( region ))
            {
                junctionPoints.addIfNotNull( translateElement(element) as JunctionPoint )
            }
            for (element : getSubstates( region ))
            {
                substates.addIfNotNull( translateElement(element) as State )
            }
            for (element : getTransitions( region ) )
            {
                transitions.addIfNotNull( translateElement(element) as Transition )
            }
        }
    }

    /**
     * Translates a state from the source model into a state in the internal
     * representation.
     *
     * Essentially it just decides which translation to apply depending on
     * whether the state is simple or composite (in UML-RT there are no
     * "sub-state-machine" states.)
     *
     * @param originalState - the UML2 state element in the original model
     */
    dispatch def State
    create
        if (originalState.isSimpleState)
            translateSimpleState( originalState )
        else
            translateCompositeState( originalState )
    translate( org.eclipse.uml2.uml.State originalState )
    {
        if (it !== null)
        {
            name = originalState.effectiveName
            XTUMLRTAnnotations.setAnnotations( originalState, it )
            entryAction = translateFeature( originalState, "entry", org.eclipse.uml2.uml.Behavior, EntryAction, true ) as EntryAction
            exitAction = translateFeature( originalState, "exit", org.eclipse.uml2.uml.Behavior, ExitAction, true ) as ExitAction
            for (entryPoint : getEntryPoints( originalState ))
            {
                entryPoints.addIfNotNull( translateElement(entryPoint) as EntryPoint )
            }
            for (exitPoint : getExitPoints( originalState ))
            {
                exitPoints.addIfNotNull( translateElement(exitPoint) as ExitPoint )
            }
            translateRedefinableElement( originalState, it )
        }
    }

    /**
     * Translates a simple state.
     */
    protected def SimpleState translateSimpleState( org.eclipse.uml2.uml.State originalState )
    {
        StatemachFactory.eINSTANCE.createSimpleState
    }

    /**
     * Translates a composite state by translating its sub-elements (vertices
     * and transitions).
     *
     * <p>When traversing the sub-states, the translation is applied recursively.
     */
    protected def CompositeState
    translateCompositeState( org.eclipse.uml2.uml.State originalState )
    {
        translate( originalState.ownedRegion ) as CompositeState
    }

    dispatch def Pseudostate
    create
        if (originalPseudostate === null) null
        else
            switch (originalPseudostate.kind)
            {
                case PseudostateKind.INITIAL_LITERAL:
                    StatemachFactory.eINSTANCE.createInitialPoint
                case PseudostateKind.DEEP_HISTORY_LITERAL:
                    StatemachFactory.eINSTANCE.createDeepHistory
                case PseudostateKind.ENTRY_POINT_LITERAL:
                    StatemachFactory.eINSTANCE.createEntryPoint
                case PseudostateKind.EXIT_POINT_LITERAL:
                    StatemachFactory.eINSTANCE.createExitPoint
                case PseudostateKind.CHOICE_LITERAL:
                    StatemachFactory.eINSTANCE.createChoicePoint
                case PseudostateKind.JUNCTION_LITERAL:
                    StatemachFactory.eINSTANCE.createJunctionPoint
                default:
                    throw new RuntimeException( "Pseudostate '" + originalPseudostate.qualifiedName + "' has unsupported kind: '" + originalPseudostate.kind.toString + "'")
            }
    translate( org.eclipse.uml2.uml.Pseudostate originalPseudostate )
    {
        if (it !== null)
        {
            name = originalPseudostate.effectiveName
        }
    }

    dispatch def ActionCode
    create
        if (behaviour === null) null
        else if (behaviour.owner !== null)
        {
            if (behaviour.owner instanceof org.eclipse.uml2.uml.State)
            {
                val state = behaviour.owner as org.eclipse.uml2.uml.State
                if (behaviour === state.entry)
                    StatemachextFactory.eINSTANCE.createEntryAction
                else if (behaviour === state.exit)
                    StatemachextFactory.eINSTANCE.createExitAction
            }
            else if (behaviour.owner instanceof org.eclipse.uml2.uml.Transition)
            {
                val transition = behaviour.owner as org.eclipse.uml2.uml.Transition
                if (behaviour == transition.effect)
                    StatemachextFactory.eINSTANCE.createTransitionAction
            }
        }
        else
            throw new RuntimeException( "Behavior '" + behaviour.qualifiedName + "' has no owner.")
    translate( org.eclipse.uml2.uml.Behavior behaviour )
    {
        if (it !== null && behaviour !== null)
        {
            name = behaviour.effectiveName
            source = ActionLangUtils.getCode( actionLanguage, behaviour )
        }
    }

    dispatch def Transition
    create StatemachFactory.eINSTANCE.createTransition
    translate( org.eclipse.uml2.uml.Transition originalTransition )
    {
        name = originalTransition.effectiveName
        XTUMLRTAnnotations.setAnnotations( originalTransition, it )
        sourceVertex = translateFeature( originalTransition, "source", org.eclipse.uml2.uml.Vertex, Vertex ) as Vertex
        targetVertex = translateFeature( originalTransition, "target", org.eclipse.uml2.uml.Vertex, Vertex ) as Vertex
        for (trigger : getTriggers( originalTransition ))
        {
            triggers.addIfNotNull( translateElement(trigger) as Trigger )
        }
        guard        = translateFeature( originalTransition, "guard", org.eclipse.uml2.uml.Constraint, Guard, true ) as Guard
        actionChain  = StatemachFactory.eINSTANCE.createActionChain
        actionChain.actions.addIfNotNull( translateFeature( originalTransition, "effect", org.eclipse.uml2.uml.Behavior, TransitionAction, true ) as TransitionAction )
        translateRedefinableElement( originalTransition, it )
    }

    /**
     * @param trigger  - A {@link org.eclipse.uml2.uml.Trigger}
     * @return A {@link Trigger}
     */
    dispatch def Trigger
    create
        // if (trigger.isRTTrigger) // This test should be here, but the current profile doesn't have the RTTrigger stereotype
            translateRTTrigger( trigger )
    translate( org.eclipse.uml2.uml.Trigger trigger )
    {
        name = trigger.effectiveName
    }

    protected def RTTrigger translateRTTrigger( org.eclipse.uml2.uml.Trigger trigger )
    {
        val it = UmlrtFactory.eINSTANCE.createRTTrigger
        // Translate trigger ports
        if (trigger.ports !== null)
        {
            for (port : trigger.ports)
            {
                val triggerPort = uml2xtumlrtTranslator.translateElement(port) as RTPort
                if (PortKind.RELAY == triggerPort.kind)
                {
                    XTUMLRTLogger.warning("Ignoring relay port from the trigger '" + trigger.qualifiedName + "'")
                } 
                else
                {
                    ports.addIfNotNull(triggerPort)
                }
            }
        }
        // Translate trigger event
        val event = trigger.event
        if (event === null)
        {
            XTUMLRTLogger.error( "The trigger '" + trigger.qualifiedName + "' has a null event" )
        }
        if (event instanceof org.eclipse.uml2.uml.AnyReceiveEvent)
        {
            signal = UmlrtFactory.eINSTANCE.createAnyEvent
        }
        else if (event instanceof org.eclipse.uml2.uml.CallEvent)
        {
            val operation = event.operation
            if (operation === null)
            {
                XTUMLRTLogger.error( "The event '" + event.qualifiedName + "' has a null operation" )
            }
            signal = uml2xtumlrtTranslator.translateElement( operation ) as Signal
        }
        else
        {
            XTUMLRTLogger.error( "The event of trigger '" + trigger.qualifiedName + "' is not a CallEvent or AnyReceiveEvent" )
        }
        // Translate trigger guard
        val owner = trigger.owner
        if (owner instanceof org.eclipse.uml2.uml.Transition)
        {
            for (constraint : owner.ownedElements.filter(org.eclipse.uml2.uml.Constraint))
            {
                if (constraint.isRTGuard && constraint.constrainedElements.contains(trigger))
                {
                    triggerGuard = translateElement( constraint ) as Guard
                }
            }
            addExcludedAnnotation( owner, excludedElements( owner ) )
        }
        it
    }

    dispatch def create
        if (originalGuard === null) null
        else StatemachFactory.eINSTANCE.createGuard
    translate( org.eclipse.uml2.uml.Constraint originalGuard )
    {
        if (it !== null)
        {
            name = originalGuard.effectiveName
            body = StatemachextFactory.eINSTANCE.createGuardAction;
            (body as ActionCode).source = ActionLangUtils.getCode( actionLanguage, originalGuard )
        }
    }

    override translateEnum(Enum<?> kind)
    {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }

    override resetTranslateCache(EObject element)
    {
        val key = newArrayList( element )
        _createCache_translate.remove( key )
    }

}