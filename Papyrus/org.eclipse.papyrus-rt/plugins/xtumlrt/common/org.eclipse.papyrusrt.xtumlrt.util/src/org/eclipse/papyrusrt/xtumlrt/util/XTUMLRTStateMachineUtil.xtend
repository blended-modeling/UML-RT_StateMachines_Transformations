/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.util

import org.eclipse.papyrusrt.xtumlrt.common.AbstractAction
import org.eclipse.papyrusrt.xtumlrt.statemach.ActionChain
import org.eclipse.papyrusrt.xtumlrt.statemach.CompositeState
import org.eclipse.papyrusrt.xtumlrt.statemach.ChoicePoint
import org.eclipse.papyrusrt.xtumlrt.statemach.DeepHistory
import org.eclipse.papyrusrt.xtumlrt.statemach.EntryPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.ExitPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.Guard
import org.eclipse.papyrusrt.xtumlrt.statemach.InitialPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.JunctionPoint
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.statemach.Pseudostate
import org.eclipse.papyrusrt.xtumlrt.statemach.State
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import org.eclipse.papyrusrt.xtumlrt.statemach.Transition
import org.eclipse.papyrusrt.xtumlrt.statemach.Trigger
import org.eclipse.papyrusrt.xtumlrt.statemach.Vertex
import static extension org.eclipse.papyrusrt.xtumlrt.util.GeneralUtil.*
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.*
import java.util.List
import org.eclipse.papyrusrt.xtumlrt.common.ActionCode
import org.eclipse.papyrusrt.xtumlrt.statemachext.EntryAction
import org.eclipse.papyrusrt.xtumlrt.statemachext.ExitAction
import org.eclipse.papyrusrt.xtumlrt.statemachext.TransitionAction

class XTUMLRTStateMachineUtil
{

    static val INSTANCE = new XTUMLRTStateMachineUtil

    static def void addChoicePoint( CompositeState state, ChoicePoint point )
    {
        state.choicePoints.add( point )
    }

    static def void addEntryPoint( State state, EntryPoint point )
    {
        state.entryPoints.add( point )
    }

    static def void addExitPoint( State state, ExitPoint point )
    {
        state.exitPoints.add( point )
    }

    static def void addFirstAction( ActionChain chain, AbstractAction action )
    {
        chain.actions.add( 0, action )
    }

    static def void addJunctionPoint( CompositeState state, JunctionPoint point )
    {
        state.junctionPoints.add( point )
    }

    static def void addLastAction( ActionChain chain, AbstractAction action )
    {
        chain.actions.add( action )
    }

    static def void addSubstate( CompositeState state, State s )
    {
        state.substates.add( s )
    }

    static def void addTransition( CompositeState state, Transition t )
    {
        state.transitions.add( t )
    }

    /**
     * Returns the nesting depth of an element inside a state machine.
     *
     * @param element   a {@link NamedElement}
     * @return
     *   <ul>
     *     <li>-1 if element is not a state machine element
     *     <li> 0 if element is null
     *     <li> > 0 the nesting depth of the state machine element
     *   </ul>
     */
    def int create
    {
        if (element === null) 0
        else if (!element.isStateMachineElement ) -1
        else getCachedDepthOf( element.ownerState ) + 1
    }
    getCachedDepthOf( NamedElement element )
    {
    }

    static def getCachedDepth( NamedElement element )
    {
        INSTANCE.getCachedDepthOf( element )
    }

    def setCachedDepthOf( NamedElement element, int depth )
    {
        val key = newArrayList( element )
        _createCache_getCachedDepthOf.put( key, depth )
    }

    static def setCachedDepth( NamedElement element, int depth )
    {
        INSTANCE.setCachedDepthOf( element, depth )
    }

    /**
     * Returns the union of the state's owned exit and entry points.
     */
    static def Iterable<Pseudostate> getConnectionPoints( State state )
    {
        val List<Pseudostate> connectionPoints = newArrayList
        connectionPoints.addAll( state.entryPoints )
        connectionPoints.addAll( state.exitPoints )
        connectionPoints
    }

    /**
     * Returns the nesting depth of an element inside a state machine.
     */
    static def int getDepth( NamedElement element )
    {
        if (element == null || !element.isStateMachineElement ) 0
        else element.ownerState.depth + 1
    }

    /**
     * Returns the transitions that end directly on the state, rather than one
     * of its entry points.
     *
     * <p>Note: this includes transitions owned by the state.
     */
    static dispatch def Iterable<Transition> getDirectIncomingTransitions( State state )
    {
        val List<Transition> transitions = newArrayList
        val owner = state.ownerState as CompositeState
        // Look for transitions in the container state if it's not the top state
        if (owner !== null)
        {
            for (t : owner.transitions)
            {
                if (t.targetVertex == state)
                    transitions.add( t )
            }
        }
        // Look for inner transitions if it's a composite state
        if (state instanceof CompositeState)
        {
            for (t : state.transitions)
            {
                if (t.targetVertex == state)
                    transitions.add( t )
            }
        }
        transitions
    }

    static dispatch def Iterable<Transition> getDirectIncomingTransitions( Pseudostate pseudostate )
    {
        val List<Transition> transitions = newArrayList
        val owner = pseudostate.ownerState
        if (owner !== null)
        {
            // Look for transitions in the container state that end in the
            // pseudostate
            if (owner instanceof CompositeState)
            {
                for (t : owner.transitions)
                {
                    if (t.targetVertex == pseudostate)
                        transitions.add( t )
                }
            }
            // If it is a connection point, look for 'outside' transitions, i.e.
            // transitions in the composite state that contains the pseudostate's
            // owner.
            if (pseudostate.isConnectionPoint || pseudostate instanceof DeepHistory)
            {
                val ownersOwner = owner.ownerState as CompositeState
                if (ownersOwner !== null)
                {
                    for (t : ownersOwner.transitions)
                    {
                        if (t.targetVertex == pseudostate)
                            transitions.add( t )
                    }
                }
            }
        }
        transitions
    }

    /**
     * Returns the transitions that leave directly from the state, rather than
     * from one of its exit points.
     *
     * <p>Note: this includes transitions owned by the state.
     */
    static dispatch def Iterable<Transition> getDirectOutgoingTransitions( State state )
    {
        val List<Transition> transitions = newArrayList
        val owner = state.ownerState as CompositeState
        // Look for transitions in the container state if it's not the top state
        if (owner !== null)
        {
            for (t : owner.transitions)
            {
                if (t.sourceVertex == state)
                    transitions.add( t )
            }
        }
        // Look for inner transitions if it's a composite state
        if (state instanceof CompositeState)
        {
            for (t : state.transitions)
            {
                if (t.sourceVertex == state)
                    transitions.add( t )
            }
        }
        transitions
    }

    static dispatch def Iterable<Transition> getDirectOutgoingTransitions( Pseudostate pseudostate )
    {
        val List<Transition> transitions = newArrayList
        val owner = pseudostate.ownerState
        if (owner !== null)
        {
            // Look for transitions in the container state that leave the
            // pseudostate
            if (owner instanceof CompositeState)
            {
                for (t : owner.transitions)
                {
                    if (t.sourceVertex == pseudostate)
                        transitions.add( t )
                }
            }
            // If it is a connection point, look for 'outside' transitions, i.e.
            // transitions in the composite state that contains the pseudostate's
            // owner.
            if (pseudostate.isConnectionPoint)
            {
                val ownersOwner = owner.ownerState as CompositeState
                if (ownersOwner !== null)
                {
                    for (t : ownersOwner.transitions)
                    {
                        if (t.sourceVertex == pseudostate)
                            transitions.add( t )
                    }
                }
            }
        }
        transitions
    }


    /**
     * Returns the transitions that end in one of the entry points of the state
     * rather than directly on the state itself.
     *
     * <p>Note: this includes transitions owned by the state.
     */
    static def Iterable<Transition> getIndirectIncomingTransitions( State state )
    {
        val List<Transition> transitions = newArrayList
        for (p : state.connectionPoints)
        {
            transitions.addAll( p.directIncomingTransitions )
        }
        transitions
    }

    /**
     * Returns the transitions that leave from one of the exit points of the
     * state rather than directly from the state itself.
     *
     * <p>Note: this includes transitions owned by the state.
     */
    static def Iterable<Transition> getIndirectOutgoingTransitions( State state )
    {
        val List<Transition> transitions = newArrayList
        for (p : state.connectionPoints)
        {
            transitions.addAll( p.directOutgoingTransitions )
        }
        transitions
    }

    static def Iterable<Pseudostate> getInnerPseudostates( CompositeState state )
    {
        val List<Pseudostate> pseudostates = newArrayList
        if (state !== null)
        {
            pseudostates.addIfNotNull( state.initial )
            pseudostates.addIfNotNull( state.deepHistory )
            pseudostates.addAll( state.choicePoints )
            pseudostates.addAll( state.junctionPoints )
        }
        pseudostates
    }

    static dispatch def State getOwnerState( Trigger trigger )
    {
        if (trigger !== null 
            && trigger.owner !== null
            && trigger.owner.owner != null
            && trigger.owner.owner instanceof State)
            trigger.owner.owner as State
        else
            null
    }

    static dispatch def State getOwnerState( Guard guard )
    {
        if (guard !== null 
            && guard.owner !== null
            && guard.owner.owner != null
            && guard.owner.owner instanceof State)
            guard.owner.owner as State
        else
            null
    }

    static dispatch def State getOwnerState( EntryAction action )
    {
        if (action !== null
            && action.owner !== null
            && action.owner instanceof State)
            action.owner as State
        else
            null
    }

    static dispatch def State getOwnerState( ExitAction action )
    {
        if (action !== null
            && action.owner !== null
            && action.owner instanceof State)
            action.owner as State
        else
            null
    }

    static dispatch def State getOwnerState( TransitionAction action )
    {
        if (action !== null
            && action.owner !== null
            && action.owner instanceof ActionChain)
            action.owner.ownerState
        else
            null
    }

    static dispatch def State getOwnerState( ActionChain actionChain )
    {
        if (actionChain !== null 
            && actionChain.owner !== null
            && actionChain.owner.owner != null
            && actionChain.owner.owner instanceof State)
            actionChain.owner.owner as State
        else
            null
     }

    static dispatch def State getOwnerState( Vertex v )
    {
        if (v !== null && v.owner !== null && v.owner instanceof State)
            v.owner as State
        else
            null
    }

    static dispatch def CompositeState getOwnerState( Transition t )
    {
        if (t !== null && t.owner !== null && t.owner instanceof CompositeState)
            t.owner as CompositeState
        else
            null
    }

    static def StateMachine getOwnerStateMachine( NamedElement element )
    {
        var StateMachine result = null
        if (element !== null)
        {
            var stateMachineElement = element
            while (!(stateMachineElement instanceof StateMachine))
            {
                stateMachineElement = stateMachineElement.owner
            }
            if (stateMachineElement instanceof StateMachine)
            {
                result = stateMachineElement
            }
        }
        result
    }

    /**
     * Returns the set of all possible vertices that could be an end-point (source or target)
     * of the given transition. This is, if the transition is inside a composite state S,
     * the set contains the following (and only these):
     * 
     * <ul>
     *   <li>S
     *   <li>All substates of S
     *   <li>All pseudo-states owned by S (entry, exit, initial, history, choice, junction and terminate points)
     *   <li>All the connection points (entry or exit points) of all the substates of S
     * </ul>
     */
    static def Iterable<Vertex> getPossibleEndpoints( Transition transition )
    {
        val List<Vertex> vertices = newArrayList
        if (transition !== null)
        {
            val state = transition.ownerState as CompositeState
            if (state !== null)
            {
                vertices.addAll( state.substates )
                vertices.addAll( state.pseudostates )
                vertices.addAll( state.substates.map[connectionPoints] )
            }
        }
        vertices
    }

    static def Iterable<Pseudostate> getPseudostates( CompositeState state )
    {
        val List<Pseudostate> pseudostates = newArrayList
        if (state !== null)
        {
            pseudostates.addAll( state.innerPseudostates )
            pseudostates.addAll( state.connectionPoints )
        }
        pseudostates
    }

    /**
     * Returns the source state of a transition: if the source vertex is a state
     * it returns this vertex, but if it is a connection point, it returns the
     * state that owns the connection point.
     */
    static def State getSourceState( Transition transition )
    {
        val v = transition.sourceVertex
        if (v === null)
            null
        else if (v instanceof State)
            v as State
        else
            v.ownerState
    }

    /**
     * Returns the source state of a transition: if the source vertex is a state
     * it returns this vertex, but if it is a connection point, it returns the
     * state that owns the connection point.
     */
    static def State getTargetState( Transition transition )
    {
        val v = transition.targetVertex
        if (v === null)
            null
        else if (v instanceof State)
            v as State
        else
            v.ownerState
    }

    static def boolean hasDeepHistory( CompositeState state )
    {
        state !== null && state.deepHistory !== null
    }

    static def boolean hasInitial( CompositeState state )
    {
        state !== null && state.initial !== null
    }

    static def boolean isConnectionPoint( NamedElement element )
    {
        element instanceof EntryPoint || element instanceof ExitPoint
    }

    static def boolean isStateMachineElement( NamedElement element )
    {
        element instanceof Vertex
        || element instanceof Transition
        || element instanceof ActionChain
        || element instanceof AbstractAction
        || element instanceof Trigger
        || element instanceof Guard
    }

    static def void removeChoicePoint( CompositeState state, ChoicePoint choicePoint )
    {
        state.choicePoints.remove( choicePoint )
    }

    static dispatch def void removeConnectionPoint( State state, EntryPoint connectionPoint )
    {
        state.entryPoints.remove( connectionPoint )
    }

    static dispatch def void removeConnectionPoint( State state, ExitPoint connectionPoint )
    {
        state.exitPoints.remove( connectionPoint )
    }

    static def void removeDeepHistory( CompositeState state )
    {
        state.deepHistory = null
    }

    static def void removeEntryPoint( State state, EntryPoint point )
    {
        state.entryPoints.remove( point )
    }

    static def void removeExitPoint( State state, ExitPoint point )
    {
        state.exitPoints.remove( point )
    }


    static def void removeGuard( Transition transition )
    {
        transition.guard = null
    }

    static def void removeInitial( CompositeState state )
    {
        state.initial = null
    }

    static def void removeJunctionPoint( CompositeState state, JunctionPoint junctionPoint )
    {
        state.junctionPoints.remove( junctionPoint )
    }

    static def void removeTransition( CompositeState state, Transition transition )
    {
        state.transitions.remove( transition )
    }

    static def void removeTriggers( Transition transition )
    {
        for (trigger : transition.triggers.clone)
        {
            transition.triggers.remove( trigger )
        }
    }

    static def void removeSubstate( CompositeState state, State subState )
    {
        state.substates.remove( subState )
    }

    static def void removeState( StateMachine stateMachine, State subState )
    {
        stateMachine.top.removeSubstate( subState )
    }

    static dispatch def removePseudostate( CompositeState state, ChoicePoint pseudostate )
    {
        removeChoicePoint( state, pseudostate )
    }

    static dispatch def removePseudostate( CompositeState state, DeepHistory pseudostate )
    {
        removeDeepHistory( state )
    }

    static dispatch def removePseudostate( CompositeState state, EntryPoint pseudostate )
    {
        removeEntryPoint( state, pseudostate )
    }

    static dispatch def removePseudostate( CompositeState state, ExitPoint pseudostate )
    {
        removeExitPoint( state, pseudostate )
    }

    static dispatch def removePseudostate( CompositeState state, InitialPoint pseudostate )
    {
        removeInitial( state )
    }

    static dispatch def removePseudostate( CompositeState state, JunctionPoint pseudostate )
    {
        removeJunctionPoint( state, pseudostate )
    }

}