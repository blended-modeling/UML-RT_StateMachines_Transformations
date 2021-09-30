/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Ernesto Posse - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.util

import java.util.Collection
import java.util.LinkedHashSet
import java.util.List
import org.eclipse.papyrusrt.xtumlrt.statemach.CompositeState
import org.eclipse.papyrusrt.xtumlrt.statemach.ChoicePoint
import org.eclipse.papyrusrt.xtumlrt.statemach.DeepHistory
import org.eclipse.papyrusrt.xtumlrt.statemach.EntryPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.ExitPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.InitialPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.JunctionPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.Pseudostate
import org.eclipse.papyrusrt.xtumlrt.statemach.State
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import org.eclipse.papyrusrt.xtumlrt.statemach.Transition
import org.eclipse.papyrusrt.xtumlrt.statemach.Trigger
import org.eclipse.papyrusrt.xtumlrt.statemach.Vertex
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTStateMachineUtil.*
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.*

/**
 * This class provides extension methods that implement "virtual state machine
 * inheritance" as described in the UML-RT profile. This provides operations to,
 * for example, obtain all the states and transitions inside a composite state
 * including those inherited and those refined, as well as operations to obtain
 * the inherited transitions to and from a state.
 */
class XTUMLRTSMVirtualInheritanceExtensions
{

    /**
     * Returns the initial point of the state machine if it has one, or the
     * inherited initial point, if there is one along the inheritance hierarchy.
     */
    static dispatch def InitialPoint getActualInitialPoint( StateMachine stateMachine )
    {
        if (stateMachine.top === null) return null
        stateMachine.top.actualInitialPoint
    }

    static dispatch def InitialPoint getActualInitialPoint( CompositeState state )
    {
        if (state.hasInitial) state.initial
        else
        {
            val refinedState = state.redefinedState
            if (refinedState !== null && refinedState instanceof CompositeState)
                refinedState.actualInitialPoint
        }
    }

    /**
     * Returns the initial point of the state machine if it has one, or the
     * inherited initial point, if there is one along the inheritance hierarchy.
     */
    static dispatch def DeepHistory getActualDeepHistory( StateMachine stateMachine )
    {
        if (stateMachine.top === null) return null
        stateMachine.top.actualDeepHistory
    }

    static dispatch def DeepHistory getActualDeepHistory( CompositeState state )
    {
        if (state.hasDeepHistory) state.deepHistory
        else
        {
            val refinedState = state.redefinedState
            if (refinedState !== null && refinedState instanceof CompositeState)
                refinedState.actualDeepHistory
        }
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited choice points,
     *   <li> the set of new choice points which are neither inherited, not redefinitions
     * </ol>
     */
    static def dispatch Iterable<ChoicePoint> getAllChoicePoints( StateMachine stateMachine )
    {
        if (stateMachine.top === null) return #[];
        stateMachine.top.allChoicePoints
    }

    static def dispatch Iterable<ChoicePoint> getAllChoicePoints( State state )
    {
        #[]
    }

    static def dispatch Iterable<ChoicePoint> getAllChoicePoints( CompositeState state )
    {
        val List<ChoicePoint> allChoicePoints = newArrayList
        if (state !== null)
        {
            allChoicePoints.addAll( state.choicePoints )
            val redefinedState = state.redefinedState
            if (redefinedState !== null)
            {
                allChoicePoints.addAll( redefinedState.allChoicePoints )
            }
        }
        allChoicePoints
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited connection points, and
     *   <li> the set of new connection points which are not inherited
     * </ol>
     */
    static def Iterable<Pseudostate> getAllConnectionPoints( State state )
    {
        val List<Pseudostate> allConnectionPoints = newArrayList
        if (state !== null)
        {
            if (state.connectionPoints !== null)
                allConnectionPoints.addAll( state.connectionPoints )
            val redefinedState = state.redefinedState
            if (redefinedState !== null)
            {
                val inheritedPseudostates = redefinedState.allConnectionPoints
                allConnectionPoints.addAll( inheritedPseudostates )
            }
        }
        allConnectionPoints
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited incoming transitions that have not been
     *          redefined,
     *   <li> the set of incoming transitions that are redefinitions of states
     *          in the extended composite state, and
     *   <li> the set of new incoming transitions which are neither inherited,
     *          not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the vertex container,
     * so this method takes those transitions in that container and adds all
     * inherited transitions for which there is no transition in the container
     * redefining them.
     */
    static def Iterable<Transition> getAllDirectIncomingTransitions( Vertex v )
    {
        val List<Transition> allTransitions = newArrayList
        if (v !== null)
        {
            allTransitions.addAll( v.directIncomingTransitions )
            if (v instanceof State)
            {
                val redefinedState = v.redefines as State
                if (redefinedState !== null)
                {
                    val inheritedTransitions = redefinedState.allDirectIncomingTransitions
                    for (t : inheritedTransitions)
                    {
                        val container = v.ownerState as CompositeState
                        if ( container !== null && !container.redefines(t) )
                            allTransitions.add(t)
                    }
                }
            }
        }
        allTransitions
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited outgoing transitions that have not been
     *          redefined,
     *   <li> the set of outgoing transitions that are redefinitions of states
     *          in the extended composite state, and
     *   <li> the set of new outgoing transitions which are neither inherited,
     *          not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the vertex container,
     * so this method takes those transitions in that container and adds all
     * inherited transitions for which there is no transition in the container
     * redefining them.
     */
    static def Iterable<Transition> getAllDirectOutgoingTransitions( Vertex v )
    {
        val List<Transition> allTransitions = newArrayList
        if (v !== null)
        {
            allTransitions.addAll( v.directOutgoingTransitions )
            if (v instanceof State)
            {
                val redefinedState = v.redefines as State
                if (redefinedState !== null)
                {
                    val inheritedTransitions = redefinedState.allDirectOutgoingTransitions
                    for (t : inheritedTransitions)
                    {
                        val container = v.ownerState as CompositeState
                        if ( container !== null && !container.redefines(t) )
                            allTransitions.add(t)
                    }
                }
            }
        }
        allTransitions
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited entry points, and
     *   <li> the set of new entry points which are not inherited
     * </ol>
     */
    static def Iterable<EntryPoint> getAllEntryPoints( State state )
    {
        val List<EntryPoint> allEntryPoints = newArrayList
        if (state !== null)
        {
            if (state.entryPoints !== null)
                allEntryPoints.addAll( state.entryPoints )
            val redefinedState = state.redefinedState
            if (redefinedState !== null)
            {
                val inheritedPseudostates = redefinedState.allEntryPoints
                allEntryPoints.addAll( inheritedPseudostates )
            }
        }
        allEntryPoints
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited entry points, and
     *   <li> the set of new entry points which are not inherited
     * </ol>
     */
    static def Iterable<ExitPoint> getAllExitPoints( State state )
    {
        val List<ExitPoint> allExitPoints = newArrayList
        if (state !== null)
        {
            if (state.entryPoints !== null)
                allExitPoints.addAll( state.exitPoints )
            val redefinedState = state.redefinedState
            if (redefinedState !== null)
            {
                val inheritedPseudostates = redefinedState.allExitPoints
                allExitPoints.addAll( inheritedPseudostates )
            }
        }
        allExitPoints
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited incoming transitions that have not been
     *          redefined,
     *   <li> the set of incoming transitions that are redefinitions of states
     *          in the extended composite state, and
     *   <li> the set of new incoming transitions which are neither inherited,
     *          not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the vertex container,
     * so this method takes those transitions in that container and adds all
     * inherited transitions for which there is no transition in the container
     * redefining them.
     */
    static def Iterable<Transition> getAllIncomingTransitions( State s )
    {
        val List<Transition> transitions = newArrayList
        transitions.addAll( s.allDirectIncomingTransitions )
        transitions.addAll( s.allIndirectIncomingTransitions )
        transitions
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited incoming transitions that have not been
     *          redefined,
     *   <li> the set of incoming transitions that are redefinitions of states
     *          in the extended composite state, and
     *   <li> the set of new incoming transitions which are neither inherited,
     *          not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the vertex container,
     * so this method takes those transitions in that container and adds all
     * inherited transitions for which there is no transition in the container
     * redefining them.
     */
    static def Iterable<Transition> getAllIndirectIncomingTransitions( State s )
    {
        val List<Transition> allTransitions = newArrayList
        if (s !== null)
        {
            allTransitions.addAll( s.indirectIncomingTransitions )
            val redefinedState = s.redefinedState
            if (redefinedState !== null)
            {
                val inheritedTransitions = redefinedState.allIndirectIncomingTransitions
                for (t : inheritedTransitions)
                {
                    val container = s.ownerState as CompositeState
                    if ( container !== null && !container.redefines(t) )
                        allTransitions.add(t)
                }
            }
        }
        allTransitions
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited outgoing transitions that have not been
     *          redefined,
     *   <li> the set of outgoing transitions that are redefinitions of states
     *          in the extended composite state, and
     *   <li> the set of new outgoing transitions which are neither inherited,
     *          not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the vertex container,
     * so this method takes those transitions in that container and adds all
     * inherited transitions for which there is no transition in the container
     * redefining them.
     */
    static def Iterable<Transition> getAllIndirectOutgoingTransitions( State s )
    {
        val List<Transition> allTransitions = newArrayList
        if (s !== null)
        {
            allTransitions.addAll( s.indirectOutgoingTransitions )
            val redefinedState = s.redefinedState
            if (redefinedState !== null)
            {
                val inheritedTransitions = redefinedState.allIndirectOutgoingTransitions
                for (t : inheritedTransitions)
                {
                    val container = s.ownerState as CompositeState
                    if ( container !== null && !container.redefines(t) )
                        allTransitions.add(t)
                }
            }
        }
        allTransitions
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited junction points,
     *   <li> the set of new junction points which are neither inherited, not redefinitions
     * </ol>
     */
    static def dispatch Iterable<JunctionPoint> getAllJunctionPoints( StateMachine stateMachine )
    {
        if (stateMachine.top === null) return #[];
        stateMachine.top.allJunctionPoints
    }

    static def dispatch Iterable<JunctionPoint> getAllJunctionPoints( State state )
    {
        #[]
    }

    static def dispatch Iterable<JunctionPoint> getAllJunctionPoints( CompositeState state )
    {
        val List<JunctionPoint> allJunctionPoints = newArrayList
        if (state !== null)
        {
            allJunctionPoints.addAll( state.junctionPoints )
            val redefinedState = state.redefinedState
            if (redefinedState !== null)
            {
                allJunctionPoints.addAll( redefinedState.allJunctionPoints )
            }
        }
        allJunctionPoints
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited outgoing transitions that have not been
     *          redefined,
     *   <li> the set of outgoing transitions that are redefinitions of states
     *          in the extended composite state, and
     *   <li> the set of new outgoing transitions which are neither inherited,
     *          not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the vertex container,
     * so this method takes those transitions in that container and adds all
     * inherited transitions for which there is no transition in the container
     * redefining them.
     */
    static def Iterable<Transition> getAllOutgoingTransitions( State s )
    {
        val List<Transition> transitions = newArrayList
        transitions.addAll( s.allDirectOutgoingTransitions )
        transitions.addAll( s.allIndirectOutgoingTransitions )
        transitions
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited (internal) pseudostates, and
     *   <li> the set of new (internal) pseudostates which are not inherited
     * </ol>
     *
     * <p> The last two sets are already present in the given region, so this method takes
     * those states in the current region and adds all inherited states for which there is
     * no state in the given region redefining them.
     */
    static dispatch def Iterable<Pseudostate> getAllPseudostates( StateMachine stateMachine )
    {
        if (stateMachine.top === null) return #[];
        stateMachine.top.allPseudostates
    }

    static dispatch def Iterable<Pseudostate> getAllPseudostates( State state )
    {
        #[]
    }

    static dispatch def Iterable<Pseudostate> getAllPseudostates( CompositeState state )
    {
        val List<Pseudostate> allPseudostates = newArrayList
        if (state !== null)
        {
            allPseudostates.addAll( state.pseudostates )
            val redefinedState = state.redefinedState
            if (redefinedState !== null)
            {
                val inheritedPseudostates = redefinedState.allPseudostates
                allPseudostates.addAll( inheritedPseudostates )
            }
        }
        allPseudostates
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited states that have not been redefined,
     *   <li> the set of states that are redefinitions of states in the extended region, and
     *   <li> the set of new states which are neither inherited, not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the given region, so this method takes
     * those states in the current region and adds all inherited states for which there is
     * no state in the given region redefining them.
     */
    static def dispatch Iterable<State> getAllSubstates( StateMachine stateMachine )
    {
        if (stateMachine.top === null) return #[];
        stateMachine.top.allSubstates
    }

    static def dispatch Iterable<State> getAllSubstates( State state )
    {
        #[]
    }

    static def dispatch Iterable<State> getAllSubstates( CompositeState state )
    {
        val List<State> allStates = newArrayList
        if (state !== null)
        {
            allStates.addAll( state.actualSubstates )
            val redefinedState = state.redefinedState
            if (redefinedState !== null)
            {
                val inheritedStates = redefinedState.allSubstates
                for (s : inheritedStates)
                {
                    if (!state.redefines(s))
                        allStates.add(s)
                }
            }
        }
        allStates
    }

    /**
     * Returns the set of substates of the given composite state that have not been excluded.
     */
    static def Iterable<State> getActualSubstates( CompositeState state )
    {
        val substates = new LinkedHashSet<State>
        substates.addAll( state.substates.filter[ !isExcluded ] )
        substates
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited transitions that have not been redefined,
     *   <li> the set of transitions that are redefinitions of transitions in the extended region, and
     *   <li> the set of new transitions which are neither inherited, not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the given region, so this method takes
     * those transitions in the current region and adds all inherited transitions for which there is
     * no transition in the given region redefining them.
     */
    static def dispatch Iterable<Transition> getAllTransitions( StateMachine stateMachine )
    {
        if (stateMachine.top === null) return #[];
        stateMachine.top.allTransitions
    }

    static def dispatch Iterable<Transition> getAllTransitions( State state )
    {
        #[]
    }

    static def dispatch Iterable<Transition> getAllTransitions( CompositeState state )
    {
        val List<Transition> allTransitions = newArrayList
        if (state !== null)
        {
            allTransitions.addAll( state.actualTransitions )
            val redefinedState = state.redefinedState
            if (redefinedState !== null)
            {
                val inheritedStates = redefinedState.allTransitions
                for (s : inheritedStates)
                {
                    if (!state.redefines(s))
                        allTransitions.add(s)
                }
            }
        }
        allTransitions
    }

    /**
     * Returns the set of transitions of the given composite state that have not been excluded.
     */
    static def Iterable<Transition> getActualTransitions( CompositeState state )
    {
        val transitions = new LinkedHashSet<Transition>
        transitions.addAll( state.transitions.filter[ !isExcluded ] )
        transitions
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited triggers that have not been excluded,
     *   <li> the set of new triggers which are neither inherited, not excluded
     * </ol>
     *
     * <p> The last set is already present in the given transition, so this method takes
     * those triggers in the current transition and adds all inherited triggers that have not
     * been excluded.
     */
    static def Iterable<Trigger> getAllTriggers( Transition transition )
    {
        val List<Trigger> allTriggers = newArrayList
        if (transition !== null)
        {
            allTriggers.addAll( transition.actualTriggers )
            val redefinedTransition = transition.redefinedTransition
            if (redefinedTransition !== null)
            {
                val inheritedTriggers = redefinedTransition.allTriggers
                for (trig : inheritedTriggers)
                {
                    if (!transition.excludes( trig ))
                        allTriggers.add( trig )
                }
            }
        }
        allTriggers
    }

    /**
     * Returns the set of triggers of the given transition that have not been excluded.
     */
    static def Iterable<Trigger> getActualTriggers( Transition transition )
    {
        val triggers = new LinkedHashSet<Trigger>
        triggers.addAll( transition.triggers.filter[ !isExcluded ] )
        triggers
    }


    /**
     * Returns the state redefined by the given state or null if it doesn't
     * redefine any.
     */
    static def State getRedefinedState( State s )
    {
        if (s === null || s.redefines === null || !(s.redefines instanceof State))
            null
        else
            s.redefines as State
    }

    /**
     * Returns a collection of states from the extended composite state that
     * have been redefined in the given composite state.
     */
    static def Iterable<State> getRedefinedStates( CompositeState state )
    {
        state.stateRedefinitions.map [ it.redefinedState ]
    }

    /**
     * Returns the state redefined by the given state or null if it doesn't
     * redefine any.
     */
    static def Transition getRedefinedTransition( Transition t )
    {
        if (t === null || t.redefines === null || !(t.redefines instanceof Transition))
            null
        else
            t.redefines as Transition
    }


    /**
     * Returns a collection of transitions from the extended composite state
     * that have been redefined in the given composite state.
     */
    static def Iterable<Transition> getRedefinedTransitions( CompositeState state )
    {
        state.transitionRedefinitions.map [ it.redefinedTransition ]
    }

    /**
     * Returns the collection of states which are redefinitions of a state in
     * the extended composite state. In other words, it returns the collection
     * of substates of the given composite state which redefine some state.
     */
    static def Collection<State> getStateRedefinitions( CompositeState state )
    {
        val List<State> states = newArrayList
        if (state !== null)
        {
            for (s : state.substates)
            {
                if (s.redefinedState !== null)
                    states.add(s)
            }
        }
        states
    }

    /**
     * Returns the collection of transitions which are redefinitions of a
     * transition in the extended composite state. In other words, it returns
     * the collection of transitions of the given composite state that redefine
     * some transition.
     */
    static def Collection<Transition> getTransitionRedefinitions( CompositeState state )
    {
        val List<Transition> transitions = newArrayList
        if (state !== null)
        {
            for (t : state.transitions)
            {
                if (t.redefinedTransition !== null)
                    transitions.add(t)
            }
        }
        transitions
    }

    /**
     * Returns the source vertex of a given transition inside a given composite
     * state which may be an extended state of the state containing the "real"
     * source of the transition. This is, if transition t is inside (composite)
     * state r because it was inherited, this is, t is defined in and actually
     * belongs to a (composite) state r' redefined by r, then the real source s'
     * of t is in r', but there may be a vertex s in r which redefines s', and
     * therefore it should be considered the source of t in r.
     *
     * <p> The following diagram describes the situation: t in r is inherited
     * from r', and its real source is s' and target is d'. This method returns
     * s, the source of t in r.
     * <p>
     * <pre>
     * <code>
     * parent state      r':    s' --t--> d'
     *                   ^      ^         ^
     *                   |      |         |
     * extended state    r:     s  --t--> d
     * </code>
     * </pre>
     * 
     * <p>Another way of understanding this is this: suppose that transition t
     * is owned by state r which extends state r', and suppose that the real
     * source of t is some vertex s' owned by r', but s' is redefined by a vertex
     * s owned by r:
     * 
     * <pre>
     * <code>
     * parent state      r':    s' -+
     *                   ^      ^   |
     *                   |      |   |
     * extended state    r:     s   +-t-> d
     * </code>
     * </pre>
     * 
     * <p> In such a case, this method reports s as the source of t in r.
     */
    static def Vertex getTransitionSourceInState( Transition t, CompositeState r )
    {
        // Check if the source is one of the pseudostates, owned or inherited by r
        for (s : r.allPseudostates)
        {
            if (t.sourceVertex === s)
                return s
        }
        // Check if the source is one of the states, owned or inherited by r or a connection
        // point for any such state
        val statesToConsider = newArrayList
        statesToConsider.add( r )
        statesToConsider.addAll( r.allSubstates )
        for (s : statesToConsider )
        {
            var v = s
            // Go up the chain of state redefinitions looking for the actual source.
            while (v !== null)
            {
                // Check if the source is the given state...
                if (t.sourceVertex === v)
                    return s
                // Check if it is the deep history pseudostate if the state is composite
                if (v instanceof CompositeState)
                {
                    val h = v.deepHistory
                    if (t.sourceVertex === h)
                        return h
                    
                }
                // ... or if it is one of its connection (exit) points
                if (v.connectionPoints !== null)
                {
                    for (p : v.connectionPoints)
                    {
                        if (t.sourceVertex === p)
                            // p is not owned by r but we still need to report it, and since
                            // p is a pseudostate and non-redefinable, it must be inherited
                            // by s (TODO: unless it has been explicitly excluded, in which
                            // case we should report s)
                            return p
                    }
                }
                // If not, go up to the state that v redefines.
                v = v.redefinedState
            }
        }
    }

    /**
     * Returns the target vertex of a given transition inside a given composite
     * state which may be an extended state of the state containing the "real"
     * target of the transition. This is, if transition t is inside (composite)
     * state r because it was inherited, this is, t is defined in and actually
     * belongs to a (composite) state r' redefined by r, then the real target d'
     * of t is in r', but there may be a vertex d in r which redefines d', and
     * therefore it should be considered the target of t in r.
     *
     * <p> The following diagram describes the situation: t in r is inherited
     * from r', and its real source is s' and target is d'. This method returns
     * d, the target of t in r.
     * <p>
     * <pre>
     * <code>
     * parent state      r':    s' --t--> d'
     *                   ^      ^         ^
     *                   |      |         |
     * extended state    r:     s  --t--> d
     * </code>
     * </pre>
     * 
     * <p>Another way of understanding this is this: suppose that transition t
     * is owned by state r which extends state r', and suppose that the real
     * target of t is some vertex d' owned by r', but d' is redefined by a vertex
     * d owned by r:
     * 
     * <pre>
     * <code>
     * parent state      r':           +-> d'
     *                   ^             |   ^
     *                   |             |   |
     * extended state    r:     s  --t-+   d
     * </code>
     * </pre>
     * 
     * <p> In such a case, this method reports d as the target of t in r.
     * 
     */
    static def Vertex getTransitionTargetInState( Transition t, CompositeState r )
    {
        // Check if the source is one of the pseudostates, owned or inherited by r
        for (s : r.allPseudostates)
        {
            if (t.targetVertex === s)
                return s
        }
        // Check if the target is one of the states, owned or inherited by r or a connection
        // point for any such state
        val statesToConsider = newArrayList
        statesToConsider.add( r )
        statesToConsider.addAll( r.allSubstates )
        for (s : statesToConsider )
        {
            var v = s
            // Go up the chain of state redefinitions looking for the actual target.
            while (v !== null)
            {
                // Check if the source is the given state...
                if (t.targetVertex === v)
                    return s
                // Check if it is the deep history pseudostate if the state is composite
                if (v instanceof CompositeState)
                {
                    val h = v.deepHistory
                    if (t.targetVertex === h)
                        return h
                    
                }
                // ... or if it is one of its connection (exit) points
                if (v.connectionPoints !== null)
                {
                    for (p : v.connectionPoints)
                    {
                        if (t.targetVertex === p)
                            // p is not owned by r but we still need to report it, and since
                            // p is a pseudostate and non-redefinable, it must be inherited
                            // by s (TODO: unless it has been explicitly excluded, in which
                            // case we should report s)
                            return p
                    }
                }
                // If not, go up to the state that v redefines.
                v = v.redefinedState
            }
        }
    }

    /**
     * Returns true is the given state from the region extended by the given region is
     * redefined in the given region.
     *
     * @param compositeState    - A {@link CompositeState}
     * @param state             - A {@link State}
     * @return {@code true} iff:
     *      <ol>
     *          <li> {@code state} is a substate of the composite state that
     *          is extended by the given {@code compositeState} and
     *          <li>  {@code state} is redefined by some state in the given
     *          {@code compositeState}
     *      </ol>
     */
    static def dispatch redefines( CompositeState compositeState, State state )
    {
        compositeState.redefinedStates.exists [it == state]
    }

    /**
     * Returns true is the given transition from the region extended by the given region is
     * redefined in the given region.
     *
     * @param compositeState    - A {@link CompositeState}
     * @param transition        - A {@link Transition}
     * @return {@code true} iff:
     *      <ol>
     *          <li> {@code transition} is a transition in the composite state
     *          extended by the given {@code compositeState} and
     *          <li>  {@code transition} is redefined by some transition in the
     *          given {@code compositeState}
     *      </ol>
     */
    static def dispatch redefines(CompositeState compositeState, Transition transition)
    {
        compositeState.redefinedTransitions.exists [it == transition]
    }

}
