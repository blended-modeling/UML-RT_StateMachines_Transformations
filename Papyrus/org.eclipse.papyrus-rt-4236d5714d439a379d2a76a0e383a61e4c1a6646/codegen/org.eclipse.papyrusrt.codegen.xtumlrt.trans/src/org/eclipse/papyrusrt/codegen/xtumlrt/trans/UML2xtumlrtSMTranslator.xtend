/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.codegen.xtumlrt.trans

import static extension org.eclipse.papyrusrt.codegen.utils.UMLRealTimeStateMachProfileUtil.*
import static extension org.eclipse.papyrusrt.codegen.utils.GeneralUtil.*
import org.eclipse.papyrusrt.codegen.utils.UML2CppUtil
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory
import org.eclipse.papyrusrt.xtumlrt.common.CompositeState
import org.eclipse.papyrusrt.xtumlrt.common.ChoicePoint
import org.eclipse.papyrusrt.xtumlrt.common.DeepHistory
import org.eclipse.papyrusrt.xtumlrt.common.EntryPoint
import org.eclipse.papyrusrt.xtumlrt.common.ExitPoint
import org.eclipse.papyrusrt.xtumlrt.common.InitialPoint
import org.eclipse.papyrusrt.xtumlrt.common.JunctionPoint
import org.eclipse.papyrusrt.xtumlrt.common.Signal
import org.eclipse.papyrusrt.xtumlrt.common.SimpleState
import org.eclipse.papyrusrt.xtumlrt.common.State
import org.eclipse.papyrusrt.xtumlrt.common.StateMachine
import org.eclipse.papyrusrt.xtumlrt.common.Transition
import org.eclipse.papyrusrt.xtumlrt.common.Trigger
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTTrigger
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPort
import org.eclipse.papyrusrt.xtumlrt.umlrt.UmlrtFactory
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelFactory

class UML2xtumlrtSMTranslator
{

    // UML2xtumlrtTranslator provides methods to translate ports and signals, used in triggers
    UML2xtumlrtModelTranslator uml2xtumlrtTranslator

    new (UML2xtumlrtModelTranslator uml2xtumlrtTranslator)
    {
        this.uml2xtumlrtTranslator = uml2xtumlrtTranslator
    }

    /**
     * This is the main method. It creates a dummy composite state to be used
     * as the State Machine's top composite state.
     *
     * @param originalStateMachine  - A {@link org.eclipse.uml2.uml.StateMachine}
     * @return A {@link StateMachine}
     */
    def StateMachine
    create CommonFactory.eINSTANCE.createStateMachine
    translateStateMachine( org.eclipse.uml2.uml.StateMachine originalStateMachine )
    {
        name = originalStateMachine.name
        top = translateRegion( originalStateMachine.ownedRegion )
        if (top !== null) top.name = "top"
    }

    /**
     * Translates a region into a composite state. This is because in UML-RT, (composite) states
     * have exactly one region, so there is a one-to-one correspondance between them.
     */
    protected def CompositeState
    create
        if (region == null) null
        else CommonFactory.eINSTANCE.createCompositeState
    translateRegion( org.eclipse.uml2.uml.Region region )
    {
        if (region !== null)
        {
            initial = translateInitialPoint( region.initialPoint )
            deepHistory = translateDeepHistory( region.deepHistoryPoint )
            for (element : region.choicePoints)
            {
                choicePoints.addIfNotNull( translateChoicePoint(element) )
            }
            for (element : region.junctionPoints)
            {
                junctionPoints.addIfNotNull( translateJunctionPoint(element) )
            }
            for (element : region.substates)
            {
                substates.addIfNotNull( translateState(element) )
            }
            for (element : region.transitions)
            {
                transitions.addIfNotNull( translateTransition(element) )
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
    protected def State
    create
        if (originalState.isSimpleState)
            translateSimpleState( originalState )
        else
            translateCompositeState( originalState )
    translateState( org.eclipse.uml2.uml.State originalState )
    {
        if (it !== null)
        {
            name = originalState.name
            entryAction = translateEntryAction( originalState )
            exitAction  = translateExitAction( originalState )
            for (entryPoint : originalState.entryPoints)
            {
                entryPoints.addIfNotNull( translateEntryPoint(entryPoint) )
            }
            for (exitPoint : originalState.exitPoints)
            {
                exitPoints.addIfNotNull( translateExitPoint(exitPoint) )
            }
        }
    }

    def isSimpleState( org.eclipse.uml2.uml.State originalState )
    {
        originalState.simple
        || originalState.composite
            && (originalState.regions === null
                || originalState.regions.empty
                || (originalState.regions.size == 1
                    && (originalState.regions.get(0) as org.eclipse.uml2.uml.Region).isEmpty))
    }

    def boolean isEmpty( org.eclipse.uml2.uml.Region region )
    {
        if (region.extendedRegion === null)
            region.ownedElements === null || region.ownedElements.empty
        else
            (region.ownedElements === null && region.ownedElements.empty) && region.extendedRegion.isEmpty
    }

    /**
     * Translates a simple state.
     */
    protected def SimpleState translateSimpleState( org.eclipse.uml2.uml.State originalState )
    {
        CommonFactory.eINSTANCE.createSimpleState
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
        translateRegion( originalState.ownedRegion )
    }

    protected def InitialPoint
    create
        if (originalPseudostate === null) null
        else CommonFactory.eINSTANCE.createInitialPoint
    translateInitialPoint( org.eclipse.uml2.uml.Pseudostate originalPseudostate )
    {
        if (it !== null) name = originalPseudostate.name
    }

    protected def DeepHistory
    create
        if (originalPseudostate === null) null
        else CommonFactory.eINSTANCE.createDeepHistory
    translateDeepHistory( org.eclipse.uml2.uml.Pseudostate originalPseudostate )
    {
        if (it !== null) name = originalPseudostate.name
    }

    protected def EntryPoint
    create CommonFactory.eINSTANCE.createEntryPoint
    translateEntryPoint( org.eclipse.uml2.uml.Pseudostate originalPseudostate )
    {
        name = originalPseudostate?.name
    }

    protected def ExitPoint
    create CommonFactory.eINSTANCE.createExitPoint
    translateExitPoint( org.eclipse.uml2.uml.Pseudostate originalPseudostate )
    {
        name = originalPseudostate?.name
    }

    protected def ChoicePoint
    create CommonFactory.eINSTANCE.createChoicePoint
    translateChoicePoint( org.eclipse.uml2.uml.Pseudostate originalPseudostate )
    {
        name = originalPseudostate?.name
    }

    protected def JunctionPoint
    create CommonFactory.eINSTANCE.createJunctionPoint
    translateJunctionPoint( org.eclipse.uml2.uml.Pseudostate originalPseudostate )
    {
        name = originalPseudostate?.name
    }

    protected def dispatch translateVertex( org.eclipse.uml2.uml.State originalState )
    {
        translateState(originalState)
    }

    protected def dispatch translateVertex
    (
        org.eclipse.uml2.uml.Pseudostate originalPseudostate
    )
    {
        switch (originalPseudostate.kind)
        {
            case INITIAL_LITERAL:       translateInitialPoint(originalPseudostate)
            case DEEP_HISTORY_LITERAL:  translateDeepHistory(originalPseudostate)
            case CHOICE_LITERAL:        translateChoicePoint(originalPseudostate)
            case JUNCTION_LITERAL:      translateJunctionPoint(originalPseudostate)
            case ENTRY_POINT_LITERAL:   translateEntryPoint(originalPseudostate)
            case EXIT_POINT_LITERAL:    translateExitPoint(originalPseudostate)
            default:                    null
        }
    }

    protected def create
        if (originalState.entry === null) null
        else SmflatmodelFactory.eINSTANCE.createEntryAction
    translateEntryAction( org.eclipse.uml2.uml.State originalState )
    {
        if (it !== null && originalState?.entry !== null)
        {
            name = originalState.entry.name
            source = UML2CppUtil.getCppCode( originalState.entry )
        }
    }

    protected def create
        if (originalState.exit === null) null
        else SmflatmodelFactory.eINSTANCE.createExitAction
    translateExitAction( org.eclipse.uml2.uml.State originalState )
    {
        if (it !== null && originalState?.exit !== null)
        {
            name = originalState.exit.name
            source = UML2CppUtil.getCppCode( originalState.exit )
        }
    }

    protected def Transition
    create CommonFactory.eINSTANCE.createTransition
    translateTransition( org.eclipse.uml2.uml.Transition originalTransition )
    {
        name = originalTransition.name
        sourceVertex = translateVertex( originalTransition.source )
        targetVertex = translateVertex( originalTransition.target )
        for (trigger : originalTransition.triggers)
        {
            triggers.addIfNotNull( translateTransitionTrigger(trigger) )
        }
        guard        = translateTransitionGuard( originalTransition )
        actionChain  = CommonFactory.eINSTANCE.createActionChain
        actionChain.actions.addIfNotNull( translateTransitionAction( originalTransition ) )
    }

    /**
     * @param trigger  - A {@link org.eclipse.uml2.uml.Trigger}
     * @return A {@link Trigger}
     */
    protected def Trigger
    create
        // if (trigger.isRTTrigger) // This test should be here, but the current profile doesn't have the RTTrigger stereotype
            translateRTTrigger( trigger )
    translateTransitionTrigger( org.eclipse.uml2.uml.Trigger trigger )
    {
        name = trigger.name
    }

    protected def RTTrigger translateRTTrigger( org.eclipse.uml2.uml.Trigger trigger )
    {
        val it = UmlrtFactory.eINSTANCE.createRTTrigger
        for (port : trigger.ports)
        {
            ports.addIfNotNull( uml2xtumlrtTranslator.translateElement(port) as RTPort)
        }
        val operation = (trigger.event as org.eclipse.uml2.uml.CallEvent).operation
        signal = uml2xtumlrtTranslator.translateElement( operation ) as Signal
        it
    }

    protected def create
        if (originalTransition.guard === null) null
        else CommonFactory.eINSTANCE.createGuard
    translateTransitionGuard( org.eclipse.uml2.uml.Transition originalTransition )
    {
        if (it !== null)
        {
            name = originalTransition.guard?.name
            body = CommonFactory.eINSTANCE.createActionCode
            body.source = UML2CppUtil.getCppCode( originalTransition.guard )
        }
    }

     protected def create
         if (originalTransition.effect === null) null
         else SmflatmodelFactory.eINSTANCE.createTransitionAction
     translateTransitionAction( org.eclipse.uml2.uml.Transition originalTransition )
     {
        if (it !== null && originalTransition?.effect !== null)
        {
            name = originalTransition.effect?.name
            source = UML2CppUtil.getCppCode( originalTransition.effect )
        }
    }

}