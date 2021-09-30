/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.utils

import org.eclipse.papyrus.umlrt.statemachine.UMLRealTimeStateMach.RTTrigger
import org.eclipse.uml2.uml.Element
import org.eclipse.uml2.uml.Pseudostate
import org.eclipse.uml2.uml.State
import org.eclipse.uml2.uml.PseudostateKind
import org.eclipse.uml2.uml.Region
import org.eclipse.uml2.uml.StateMachine
import org.eclipse.uml2.uml.Transition

class UMLRealTimeStateMachProfileUtil
{

    static def Iterable<Pseudostate> getChoicePoints( Region region )
    {
        region?.internalPseudostates?.filter[ it.kind == PseudostateKind.CHOICE_LITERAL ]
    }

    static def Iterable<Pseudostate> getChoicePoints( State state )
    {
        state?.ownedRegion?.choicePoints
    }

    static def Iterable<Pseudostate> getChoicePoints( StateMachine stateMachine )
    {
        stateMachine?.ownedRegion?.choicePoints
    }

    static def Pseudostate getDeepHistoryPoint( Region region )
    {
        region?.internalPseudostates?.findFirst[ it.kind == PseudostateKind.DEEP_HISTORY_LITERAL ]
    }

    static def Pseudostate getDeepHistoryPoint( State state )
    {
        state?.ownedRegion?.deepHistoryPoint
    }

    static def Pseudostate getDeepHistoryPoint( StateMachine stateMachine )
    {
        stateMachine?.ownedRegion?.deepHistoryPoint
    }

    static def Iterable<Pseudostate> getEntryPoints( State state )
    {
        state.connectionPoints.filter [ it.kind == PseudostateKind.ENTRY_POINT_LITERAL ]
    }

    static def Iterable<Pseudostate> getExitPoints( State state )
    {
        state.connectionPoints.filter [ it.kind == PseudostateKind.EXIT_POINT_LITERAL ]
    }

    static def Pseudostate getInitialPoint( Region region )
    {
        region?.internalPseudostates?.findFirst[ it.kind == PseudostateKind.INITIAL_LITERAL ]
    }

    static def Pseudostate getInitialPoint( State state )
    {
        state?.ownedRegion?.initialPoint
    }

    static def Pseudostate getInitialPoint( StateMachine stateMachine )
    {
        stateMachine?.ownedRegion?.initialPoint
    }

    static def Iterable<Pseudostate> getInternalPseudostates( Region region )
    {
        region?.subvertices?.filter( Pseudostate )
    }

    static def Iterable<Pseudostate> getInternalPseudostates( State state )
    {
        state?.ownedRegion?.internalPseudostates
    }

    static def Iterable<Pseudostate> getInternalPseudostates( StateMachine stateMachine )
    {
        stateMachine?.ownedRegion?.internalPseudostates
    }

    static def Iterable<Pseudostate> getJunctionPoints( Region region )
    {
        region?.internalPseudostates?.filter[ it.kind == PseudostateKind.JUNCTION_LITERAL ]
    }

    static def Iterable<Pseudostate> getJunctionPoints( State state )
    {
        state?.ownedRegion?.junctionPoints
    }

    static def Iterable<Pseudostate> getJunctionPoints( StateMachine stateMachine )
    {
        stateMachine?.ownedRegion?.junctionPoints
    }

    static def Region getOwnedRegion( State state )
    {
        val regions = state?.regions
        if (regions !== null && !regions.empty)
            regions.get(0)
    }

    static def Region getOwnedRegion( StateMachine stateMachine )
    {
        val regions = stateMachine?.regions
        if (regions !== null && !regions.empty)
            regions.get(0)
    }

    static def RTTrigger getRTTrigger(Element el) {
        val s = el.getAppliedStereotype("UMLRealTime::RTTrigger")
        if (s == null)
        {
            return null
        }
        return el.getStereotypeApplication(s) as RTTrigger
    }

    static def Iterable<State> getSubstates( Region region )
    {
        region?.subvertices?.filter( State )
    }

    static def Iterable<State> getSubstates( State state )
    {
        state?.ownedRegion?.substates
    }

    static def Iterable<State> getSubstates( StateMachine stateMachine )
    {
        stateMachine?.ownedRegion?.substates
    }

    static def Iterable<Transition> getTransitions( Region region )
    {
        region?.subvertices?.filter( Transition )
    }

    static def Iterable<Transition> getTransitions( State state )
    {
        state?.ownedRegion?.transitions
    }

    static def Iterable<Transition> getTransitions( StateMachine stateMachine )
    {
        stateMachine?.ownedRegion?.transitions
    }

    static def boolean isRTTrigger( Element el )
    {
        val s = el.getApplicableStereotype("UMLRealTime::RTTrigger")
        if (s != null)
        {
            return el.isStereotypeApplied(s)
        }
        return false;
    }


}