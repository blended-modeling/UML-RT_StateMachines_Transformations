/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.utils

import java.util.Collection
import java.util.List
import org.eclipse.uml2.uml.Pseudostate
import org.eclipse.uml2.uml.Region
import org.eclipse.uml2.uml.State
import org.eclipse.uml2.uml.StateMachine
import org.eclipse.uml2.uml.Transition
import org.eclipse.uml2.uml.Vertex
import org.eclipse.uml2.uml.PseudostateKind

@Deprecated
class UMLRTSMVirtualInheritanceExtensions {

    /**
     * Returns the list of all regions extended by the given region, in order of redefinition.
     */
    static def List<Region> ancestors(Region region) {
        val List<Region> list = newArrayList
        var r = region
        while (r !== null) {
            list.add(r)
            r = r.extendedRegion
        }
        list
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited connection points, and
     *   <li> the set of new connection points which are not inherited
     * </ol>
     */
    static dispatch def Iterable<Pseudostate> getAllConnectionPoints(State state) {
        val List<Pseudostate> allConnectionPoints = newArrayList
        if (state !== null) {
            if (state.composite && state.connectionPoints !== null)
                allConnectionPoints.addAll(state.connectionPoints)
            if (state.redefinedState !== null) {
                val inheritedPseudostates = state.redefinedState.allConnectionPoints
                allConnectionPoints.addAll(inheritedPseudostates)
            }
        }
        allConnectionPoints
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited connection points, and
     *   <li> the set of new connection points which are not inherited
     * </ol>
     */
    static dispatch def Iterable<Pseudostate> getAllConnectionPoints(StateMachine stateMachine) {
        val List<Pseudostate> allConnectionPoints = newArrayList
        if (stateMachine !== null) {
            allConnectionPoints.addAll(stateMachine.connectionPoints)
            if (stateMachine.extendedStateMachines !== null
                && !stateMachine.extendedStateMachines.empty
            ) {
                val extendedStateMachine = stateMachine.getExtendedStateMachine(stateMachine.name)
                if (extendedStateMachine !== null) {
                    val inheritedPseudostates = extendedStateMachine.allConnectionPoints
                    allConnectionPoints.addAll(inheritedPseudostates)
                }
            }
        }
        allConnectionPoints
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited incoming transitions that have not been redefined,
     *   <li> the set of incoming transitions that are redefinitions of states in the extended
     *          region, and
     *   <li> the set of new incoming transitions which are neither inherited, not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the given region, so this method takes
     * those transitions in the current region and adds all inherited states for which there is
     * no transition in the given region redefining them.
     */
    static def dispatch Iterable<Transition> getAllIncomingTransitions(State s) {
        val List<Transition> allTransitions = newArrayList
        if (s !== null) {
            allTransitions.addAll(s.incomings)
            if (s.redefinedState !== null) {
                val inheritedTransitions = s.redefinedState.allIncomingTransitions
                for (t : inheritedTransitions) {
                    if (!s.container.redefines(t))
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
     *   <li> the set inherited incoming transitions that have not been redefined,
     *   <li> the set of incoming transitions that are redefinitions of states in the extended
     *          region, and
     *   <li> the set of new incoming transitions which are neither inherited, not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the given region, so this method takes
     * those transitions in the current region and adds all inherited states for which there is
     * no transition in the given region redefining them.
     */
    static def dispatch Iterable<Transition> getAllIncomingTransitions(Pseudostate s) {
        val List<Transition> allTransitions = newArrayList
        if (s !== null) {
            allTransitions.addAll(s.incomings)
        }
        allTransitions
    }

    /**
     * Returns the set of all incoming transitions of the given state, including inherited ones
     * whose owner is the given region or any of its ancestors.
     *
     * @see #ancestors
     */
    static def dispatch Iterable<Transition> getAllIncomingTransitionsInRegion(State s, Region r) {
        val ancestors = r.ancestors
        s.allIncomingTransitions.filter [ancestors.contains(it.owner)]
    }

    /**
     * Returns the set of all incoming transitions of the given pseudostate, including
     * inherited ones whose owner is the given region or any of its ancestors.
     *
     * @see #ancestors
     */
    static def dispatch Iterable<Transition> getAllIncomingTransitionsInRegion(Pseudostate s, Region r) {
        val ancestors = r.ancestors
        s.allIncomingTransitions.filter [ancestors.contains(it.owner)]
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited (internal) pseudostates, and
     *   <li> the set of new (internal) pseudostates which are not inherited
     * </ol>
     */
    static def dispatch Iterable<Pseudostate> getAllInternalPseudostates(State state) {
        val region = state.ownedRegion
        if (region !== null)
            region.allRegionPseudostates
        else
            #[]
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
    static def dispatch Iterable<Pseudostate> getAllInternalPseudostates(StateMachine stateMachine) {
        val region = stateMachine.ownedRegion
        if (region !== null)
            region.allRegionPseudostates
        else
            #[]
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited outgoing transitions that have not been redefined,
     *   <li> the set of outgoing transitions that are redefinitions of states in the extended
     *          region, and
     *   <li> the set of new outgoing transitions which are neither inherited, not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the given region, so this method takes
     * those transitions in the current region and adds all inherited states for which there is
     * no transition in the given region redefining them.
     */
    static def dispatch Iterable<Transition> getAllOutgoingTransitions(State s) {
        val List<Transition> allTransitions = newArrayList
        if (s !== null) {
            allTransitions.addAll(s.outgoings)
            if (s.redefinedState !== null) {
                val inheritedTransitions = s.redefinedState.allOutgoingTransitions
                for (t : inheritedTransitions) {
                    if (!s.container.redefines(t))
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
     *   <li> the set inherited outgoing transitions that have not been redefined,
     *   <li> the set of outgoing transitions that are redefinitions of states in the extended
     *          region, and
     *   <li> the set of new outgoing transitions which are neither inherited, not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the given region, so this method takes
     * those transitions in the current region and adds all inherited states for which there is
     * no transition in the given region redefining them.
     */
    static def dispatch Iterable<Transition> getAllOutgoingTransitions(Pseudostate s) {
        val List<Transition> allTransitions = newArrayList
        if (s !== null) {
            allTransitions.addAll(s.outgoings)
        }
        allTransitions
    }

    /**
     * Returns the set of all outgoing transitions of the given state, including inherited ones
     * whose owner is the given region or any of its ancestors.
     *
     * @see #ancestors
     */
    static def dispatch Iterable<Transition> getAllOutgoingTransitionsInRegion(State s, Region r) {
        val ancestors = r.ancestors
        s.allOutgoingTransitions.filter [ancestors.contains(it.owner)]
    }

    /**
     * Returns the set of all outgoing transitions of the given pseudostate, including
     * inherited ones whose owner is the given region or any of its ancestors.
     *
     * @see #ancestors
     */
    static def dispatch Iterable<Transition> getAllOutgoingTransitionsInRegion(Pseudostate s, Region r) {
        val ancestors = r.ancestors
        s.allOutgoingTransitions.filter [ancestors.contains(it.owner)]
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
    static def Iterable<Pseudostate> getAllRegionPseudostates(Region region) {
        val List<Pseudostate> allPseudostates = newArrayList
        if (region !== null) {
            allPseudostates.addAll(region.pseudostates)
            if (region.extendedRegion !== null) {
                val inheritedPseudostates = region.extendedRegion.allRegionPseudostates
                allPseudostates.addAll(inheritedPseudostates)  // Pseudostates cannot be redefined
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
    static def Iterable<State> getAllRegionStates(Region region) {
        val List<State> allStates = newArrayList
        if (region !== null) {
            allStates.addAll(region.substates)
            if (region.extendedRegion !== null) {
                val inheritedStates = region.extendedRegion.allRegionStates
                for (s : inheritedStates) {
                    if (!region.redefines(s))
                        allStates.add(s)
                }
            }
        }
        allStates
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited transitions that have not been redefined,
     *   <li> the set of transitions that are redefinitions of states in the extended region, and
     *   <li> the set of new transitions which are neither inherited, not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the given region, so this method takes
     * those transitions in the current region and adds all inherited states for which there is
     * no transition in the given region redefining them.
     */
    static def Iterable<Transition> getAllRegionTransitions(Region region) {
        val List<Transition> allTransitions = newArrayList
        if (region !== null) {
            allTransitions.addAll(region.transitions)
            if (region.extendedRegion !== null) {
                val inheritedTransitions = region.extendedRegion.allRegionTransitions
                for (t : inheritedTransitions) {
                    if (!region.redefines(t))
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
     *   <li> the set inherited (internal) vertices, and
     *   <li> the set of new (internal) vertices which are not inherited
     * </ol>
     *
     * <p> The last two sets are already present in the given region, so this method takes
     * those states in the current region and adds all inherited states for which there is
     * no state in the given region redefining them.
     */
    static def Iterable<Vertex> getAllRegionVertices(Region region) {
        val List<Vertex> allVertices = newArrayList
        allVertices.addAll(region.allRegionStates)
        allVertices.addAll(region.allRegionPseudostates)
        allVertices
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
    static def dispatch Iterable<State> getAllSubstates(State state) {
        val region = state.ownedRegion
        if (region !== null)
            region.allRegionStates
        else
            #[]
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
    static def dispatch Iterable<State> getAllSubstates(StateMachine stateMachine) {
        val region = stateMachine.ownedRegion
        if (region !== null)
            region.allRegionStates
        else
            #[]
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited transitions that have not been redefined,
     *   <li> the set of transitions that are redefinitions of states in the extended region, and
     *   <li> the set of new transitions which are neither inherited, not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the given region, so this method takes
     * those transitions in the current region and adds all inherited states for which there is
     * no transition in the given region redefining them.
     */
    static def dispatch Iterable<Transition> getAllTransitions(State state) {
        val region = state.ownedRegion
        if (region !== null)
            region.allRegionTransitions
        else
            #[]
    }

    /**
     * Returns the union of:
     *
     * <ol>
     *   <li> the set inherited transitions that have not been redefined,
     *   <li> the set of transitions that are redefinitions of states in the extended region, and
     *   <li> the set of new transitions which are neither inherited, not redefinitions
     * </ol>
     *
     * <p> The last two sets are already present in the given region, so this method takes
     * those transitions in the current region and adds all inherited states for which there is
     * no transition in the given region redefining them.
     */
    static def dispatch Iterable<Transition> getAllTransitions(StateMachine stateMachine) {
        val region = stateMachine.ownedRegion
        if (region !== null)
            region.allRegionTransitions
        else
            #[]
    }

    /**
     * Returns the set of all incoming transitions of the given state, including inherited ones
     * whose owner is the given region.
     */
    static def dispatch Iterable<Transition> getIncomingTransitionsInRegion(State s, Region r) {
        s.allIncomingTransitions.filter [it.owner == r]
    }

    /**
     * Returns the set of all incoming transitions of the given pseudostate, including
     * inherited ones whose owner is the given region.
     */
    static def dispatch Iterable<Transition> getIncomingTransitionsInRegion(Pseudostate s, Region r) {
        s.allIncomingTransitions.filter [it.owner == r]
    }

    /**
     * Returns the region of the state machine which extends the given state machine.
     *
     * <p>This region must be the same as the region extended by the state machine's owned
     * region.
     */
    static def getInheritedRegion(StateMachine stateMachine) {
        if (!validateExtendedRegion(stateMachine)) null
        else stateMachine?.ownedRegion?.extendedRegion
    }

    /**
     * Returns the set of pseudostates directly owned by the state (not inherited) which are
     * not connection points owned by the state.
     */
    static def dispatch Collection<Pseudostate> getInternalPseudostates(State state) {
        return state.ownedRegion?.pseudostates
    }

    /**
     * Returns the set of pseudostates directly owned by the state machine (not inherited)
     * which are not connection points owned by the state.
     */
    static def dispatch Collection<Pseudostate> getInternalPseudostates(StateMachine stateMachine) {
        return stateMachine.ownedRegion?.pseudostates
    }

    /**
     * Returns the set of all outgoing transitions of the given state, including inherited ones
     * whose owner is the given region.
     */
    static def dispatch Iterable<Transition> getOutgoingTransitionsInRegion(State s, Region r) {
        s.allOutgoingTransitions.filter [it.owner == r]
    }

    /**
     * Returns the set of all outgoing transitions of the given pseudostate, including
     * inherited ones whose owner is the given region.
     */
    static def dispatch Iterable<Transition> getOutgoingTransitionsInRegion(Pseudostate s, Region r) {
        s.allOutgoingTransitions.filter [it.owner == r]
    }

    /**
     * Returns the set of vertices owned by the region which are pseudostates.
     */
    static def Collection<Pseudostate> getPseudostates(Region region) {
        val List<Pseudostate> pseudostates = newArrayList
        if (region !== null) {
            for (v : region.subvertices) {
                if (v instanceof Pseudostate) {
                    pseudostates.add(v)
                }
            }
        }
        pseudostates
    }

    /**
     * Returns a collection of states from the extended region that have been redefined in the
     * given region.
     */
    static def Iterable<State> getRedefinedStates(Region region) {
        region.stateRedefinitions.map [it.redefinedState]
    }

    /**
     * Returns a collection of transitions from the extended region that have been redefined in
     * the given region.
     */
    static def Iterable<Transition> getRedefinedTransitions(Region region) {
        region.transitionRedefinitions.map [it.redefinedTransition]
    }

    /**
     * Returns the collection of states which are redefinitions of a state in the extended
     * region.
     */
    static def Collection<State> getStateRedefinitions(Region region) {
        val List<State> states = newArrayList
        if (region !== null) {
            for (s : region.substates) {
                if (s.redefinedState !== null)
                    states.add(s)
            }
        }
        states
    }

    /**
     * Returns the set of vertices owned by the region which are states.
     */
    static def Collection<State> getSubstates(Region region) {
        val List<State> states = newArrayList
        if (region !== null) {
            for (v : region.subvertices) {
                if (v instanceof State) {
                    states.add(v)
                }
            }
        }
        states
    }

    /**
     * Returns the collection of transitions which are redefinitions of a transition in the
     * extended region.
     */
    static def Collection<Transition> getTransitionRedefinitions(Region region) {
        val List<Transition> transitions = newArrayList
        if (region !== null) {
            for (t : region.transitions) {
                if (t.redefinedTransition !== null)
                    transitions.add(t)
            }
        }
        transitions
    }

    /**
     * Returns true is the given state from the region extended by the given region is
     * redefined in the given region.
     */
    static def dispatch redefines(Region region, State state) {
        region.redefinedStates.exists [it == state]
    }

    /**
     * Returns true is the given transition from the region extended by the given region is
     * redefined in the given region.
     */
    static def dispatch redefines(Region region, Transition transition) {
        region.redefinedTransitions.exists [it == transition]
    }

    /**
     * Returns the source vertex of a given transition in a given region which may be an
     * extended region of the region containing the "real" source of the transition. This is,
     * if transition t is in region r because it was inherited, this is, t actually belongs
     * to a region r' redefined by r, then the real source s' of t is in r', but there may be a
     * vertex s in r which redefines s', and therefore it should be considered the source of t
     * in r.
     *
     * <p> The following diagram describes the situation: t in r is inherited from r', and its
     * real source is s' and target is d'. This method returns s, the source of t in r.
     * <p>
     * <pre>
     * <code>
     * parent region     r':    s' --t--> d'
     *                   ^      ^         ^
     *                   |      |         |
     * extended region   r:     s  --t--> d
     * </code>
     * </pre>
     */
    static def Vertex getTransitionSourceInRegion(Transition t, Region r) {
        // Check if the source is one of the pseudostates, owned or inherited by r
        for (s : r.allRegionPseudostates) {
            if (t.source === s)
                return s
        }
        // Check if the source is one of the states, owned or inherited by r or a connection
        // point for any such state
        for (s : r.allRegionStates) {
            var v = s
            // Go up the chain of state redefinitions looking for the actual source.
            while (v !== null) {
                // Check if the source is the given state...
                if (t.source === v)
                    return s
                // ... or if it is one of its connection (exit) points
                if (v.connectionPoints !== null) {
                    for (p : v.connectionPoints) {
                        if (t.source === p)
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
     * Returns the source vertex of a given transition in a given (composite) state which may
     * be an redefined state of the state containing the "real" target of the transition. This
     * is, if transition t is inside region r of composite state s because it was inherited,
     * this is, t actually belongs to a region r' redefined by r, then the real source d' of t
     * is in r', but there may be a vertex d in r which redefines d', and therefore it should
     * be considered the source of t in r.
     *
     * <p> The following diagram describes the situation: t in r is inherited from r', and its
     * real source is s' and target is d'. This method returns d, the source of t in r.
     * <p>
     * <pre>
     * <code>
     * parent region     r':    s' --t--> d'
     *                   ^      ^         ^
     *                   |      |         |
     * extended region   r:     s  --t--> d
     * </code>
     * </pre>
     *
     * <p> The source may be the state itself.
     */
    static def Vertex getTransitionSourceInState(Transition t, State s) {
        if (s == null) return null
        // First check if the source is the state itself or any state up the redefinition
        // chain of s.
        var State u = s
        while (u !== null) {
            if (t.source == u)
                return s
            u = u.redefinedState
        }
        // At this point we know that neither s nor any state it redefines is the source,
        // So we check the connection points of s, including those inherited.
        var Vertex v = s.allConnectionPoints.findFirst[it.allOutgoingTransitions.exists[it == t]]
        // If none of the connection points was the source, then we look inside the state
        // region for a source.
        if (v == null && s.composite && s.regions !== null && !s.regions.empty) {
            val r = s.regions.get(0)
            v = getTransitionSourceInRegion(t, r)
        }
        v
    }

    /**
     * Returns the target vertex of a given transition in a given region which may be an
     * extended region of the region containing the "real" target of the transition. This is,
     * if transition t is in region r because it was inherited, this is, t actually belongs
     * to a region r' redefined by r, then the real target d' of t is in r', but there may be a
     * vertex d in r which redefines d', and therefore it should be considered the target of t
     * in r.
     *
     * <p> The following diagram describes the situation: t in r is inherited from r', and its
     * real source is s' and target is d'. This method returns d, the target of t in r.
     * <p>
     * <pre>
     * <code>
     * parent region     r':    s' --t--> d'
     *                   ^      ^         ^
     *                   |      |         |
     * extended region   r:     s  --t--> d
     * </code>
     * </pre>
     */
    static def Vertex getTransitionTargetInRegion(Transition t, Region r) {
        // Check if the target is one of the pseudostates, owned or inherited by r
        for (s : r.allRegionPseudostates) {
            if (t.target === s)
                return s
        }
        // Check if the target is one of the states, owned or inherited by r or a connection
        // point for any such state
        for (s : r.allRegionStates) {
            var v = s
            // Go up the chain of state redefinitions looking for the actual target.
            while (v !== null) {
                // Check if the target is the given state...
                if (t.target === v)
                    return s
                // ... or if it is one of its connection (exit) points
                if (v.connectionPoints !== null) {
                    for (p : v.connectionPoints) {
                        if (t.target === p)
                            // p is not owned by r but we still need to report it, and since
                            // p is a pseudostate and non-redefinable, it must be inherited
                            // by s (TODO: unless it has been explicitly excluded, in which
                            // case we should report s)
                            return p
                    }
                }
                // ... or if s it a composite state and the target is the history point
                if (v.composite && v.ownedRegion !== null) {
                    for (p : v.internalPseudostates) {
                        // analogous to the previous case
                        if (t.target == p && p.kind == PseudostateKind.DEEP_HISTORY_LITERAL)
                            return p
                    }
                }
                // If not, go up to the state that v redefines.
                v = v.redefinedState
            }
        }
    }

    /**
     * Returns the target vertex of a given transition in a given (composite) state which may
     * be an redefined state of the state containing the "real" target of the transition. This
     * is, if transition t is inside region r of composite state s because it was inherited,
     * this is, t actually belongs to a region r' redefined by r, then the real target d' of t
     * is in r', but there may be a vertex d in r which redefines d', and therefore it should
     * be considered the target of t in r.
     *
     * <p> The following diagram describes the situation: t in r is inherited from r', and its
     * real source is s' and target is d'. This method returns d, the target of t in r.
     * <p>
     * <pre>
     * <code>
     * parent region     r':    s' --t--> d'
     *                   ^      ^         ^
     *                   |      |         |
     * extended region   r:     s  --t--> d
     * </code>
     * </pre>
     *
     * <p> The target may be the state itself.
     */
    static def Vertex getTransitionTargetInState(Transition t, State s) {
        if (s == null) return null
        // First check if the target is the state itself or any state up the redefinition
        // chain of s.
        var State u = s
        while (u !== null) {
            if (t.target == u)
                return s
            u = u.redefinedState
        }
        // At this point we know that neither s nor any state it redefines is the target,
        // So we check the connection points of s, including those inherited.
        var Vertex v = s.allConnectionPoints.findFirst[it.allIncomingTransitions.exists[it == t]]
        // If none of the connection points was the source, then we look inside the state
        // region for a source.
        if (v == null && s.composite && s.regions !== null && !s.regions.empty) {
            val r = s.regions.get(0)
            v = getTransitionTargetInRegion(t, r)
        }
        v
    }

    /**
     * Returns the region owned by the state machine. In UML-RT state machines own only one
     * region.
     */
    static def dispatch Region ownedRegion(StateMachine stateMachine) {
        if (stateMachine.regions !== null && !stateMachine.regions.empty)
            stateMachine.regions.get(0) // In UML-RT a state machine has exactly one region
    }

    static def dispatch Region ownedRegion(State state) {
        if (state.regions !== null && !state.regions.empty)
            state.regions.get(0) // In UML-RT a state machine has exactly one region
    }

    /**
     * Check that the region extended by the state machine's owned region is the same as
     * the owned region of the extended state machine with the same name.
     *
     * <p>Note that if the extended state machine has a different name, this method will report
     * the extension as invalid. The reason is that UML allows a state machine to extend
     * multiple state machines, but we need to disambiguate which is the one that we are
     * concerned with, and this is done by selecting the extended machine with the same name
     * as the given state machine.
     */
    static def validateExtendedRegion(StateMachine stateMachine) {
        val extendedStateMachine = stateMachine?.getExtendedStateMachine(stateMachine.name)
        val extendedRegion = extendedStateMachine?.ownedRegion
        val ownedRegion = stateMachine?.ownedRegion
        val redefinedRegion = ownedRegion?.extendedRegion
        extendedRegion == redefinedRegion
    }

}
