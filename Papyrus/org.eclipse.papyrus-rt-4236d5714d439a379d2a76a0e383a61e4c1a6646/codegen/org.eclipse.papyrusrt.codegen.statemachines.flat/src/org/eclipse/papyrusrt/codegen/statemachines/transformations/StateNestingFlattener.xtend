/*******************************************************************************
* Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.codegen.statemachines.transformations

import org.eclipse.papyrusrt.xtumlrt.common.ActionCode
import org.eclipse.papyrusrt.xtumlrt.common.ActionChain
import org.eclipse.papyrusrt.xtumlrt.common.ChoicePoint
import org.eclipse.papyrusrt.xtumlrt.common.CompositeState
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory
import org.eclipse.papyrusrt.xtumlrt.common.DeepHistory
import org.eclipse.papyrusrt.xtumlrt.common.EntryPoint
import org.eclipse.papyrusrt.xtumlrt.common.ExitPoint
import org.eclipse.papyrusrt.xtumlrt.common.Guard
import org.eclipse.papyrusrt.xtumlrt.common.Pseudostate
import org.eclipse.papyrusrt.xtumlrt.common.SimpleState
import org.eclipse.papyrusrt.xtumlrt.common.State
import org.eclipse.papyrusrt.xtumlrt.common.StateMachine
import org.eclipse.papyrusrt.xtumlrt.common.Transition
import org.eclipse.papyrusrt.xtumlrt.common.Vertex
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SaveHistory
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelFactory
import org.eclipse.papyrusrt.codegen.statemachines.transformations.GlobalConstants
import org.eclipse.papyrusrt.codegen.CodeGenPlugin
import static extension org.eclipse.papyrusrt.codegen.utils.QualifiedNames.*
import static extension org.eclipse.papyrusrt.codegen.utils.XTUMLRTStateMachineUtil.*
import static extension org.eclipse.papyrusrt.codegen.utils.XTUMLRTSMVirtualInheritanceExtensions.*
import java.util.Collection
import java.util.HashSet
import java.util.Set
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * This class contains the transformation for flattening UML-RT state machines
 *
 * It implements the algorithms described in the technical report
 *
 * E. Posse. "Flattening UML-RT State Machines".
 * Technical Report ZTR-2014-EP-001, Version 2, Zeligsoft, Sep 2014.
 *
 * @author eposse
 */
class StateNestingFlattener implements InPlaceTransformation
{

    @Data static class FlatteningTransformationContext implements TransformationContext
    {
        Collection<State> discardedStates
    }

    var Collection<State> discardedStates

    public static val UNVISITED = CommonFactory.eINSTANCE.createSimpleState

    var freshNameCounter = 0
    var freshEntryPointNameCounter = 0
    var freshExitPointNameCounter = 0
    var freshChoicePointNameCounter = 0
    var freshJunctionPointNameCounter = 0
    var freshTransitionNameCounter = 0

    new ()
    {
        UNVISITED.name = GlobalConstants.UNVISITED
    }

    /**
     * Flattens a UML-RT state machine, removing composite states, and making
     * explicit transitions implied by group transitions, history points and
     * initial points.
     *
     * This is the 'main' method of the transformer. It performs the
     * transformation in-place.
     *
     * @param m a {@link StateMachine}
     * @return the flat {@link StateMachine} m with all composite states, group
     *          transitions, history and initial pseudo-states removed.
     */
    override boolean transformInPlace
    (
        StateMachine stateMachine,
        TransformationContext context
    )
    {
        if (stateMachine === null
            || context === null
            || !(context instanceof FlatteningTransformationContext))
            return false
        this.discardedStates = (context as FlatteningTransformationContext).discardedStates
        try
        {
            phase1( stateMachine )
            phase2( stateMachine )
            return true
        }
        catch (Exception e)
        {
            CodeGenPlugin.error("[Flattener] error during state machine flattening", e)
            return false
        }
    }

    /**
     * Makes explicit transitions implied by group transitions, history points
     * and initial points.
     */
    protected def phase1( StateMachine m )
    {
        val states = m.top?.getAllSubstates
        if (states === null) return;
        for (State s: states)
        {
            phase1ProcessState( s )
        }
    }

    /**
     * Creates entry transitions for incoming arrows ending at the composite
     * state's boundary or history pseudo-state. Creates exit transitions
     * implied by group transitions.
     */
    protected def void phase1ProcessState( State state )
    {
        moveEntryActionsToIncomingTransitions( state )
        moveExitActionsToOutgoingTransitions( state )
        if (state instanceof CompositeState)
        {
            // Deal with incoming transitions
            redirectIncomingTransitionsToHistory( state )
            replaceHistoryPoint( state )
            removeInitialTransition( state )
            // Deal with outgoing transitions
            annotateExplicitExitTransitions( state )
            createExitPoints( state )
            createExitTransitions( state )
            // Process sub-states
            for (subState: state.substates)
            {
                phase1ProcessState( subState )
            }
        }
    }

    /**
     * Appends the state's entry action to each (direct or indirect) incoming transition which
     * is not owned by the state.
     */
    protected def moveEntryActionsToIncomingTransitions( State state )
    {
        for (t : state.allIncomingTransitions)
        {
            val owner = t.ownerState
            if ( owner !== null && owner != state && state.entryAction !== null)
            {
                t.actionChain.addLastAction( state.entryAction )
            }
        }
    }

    /**
     * Prepends the state's exit action to each (direct or indirect) outgoing transition which
     * is not owned by the state.
     */
    protected def moveExitActionsToOutgoingTransitions( State state )
    {
        for (t : state.allOutgoingTransitions)
        {
            val owner = t.ownerState
            if ( owner !== null && owner != state && state.exitAction !== null)
            {
                t.actionChain.addFirstAction( state.exitAction )
            }
        }
    }

    /**
     * We redirect all incoming transitions to the composite state's deep history pseudostate.
     *
     * <p>There are several possible incoming transitions:
     *
     * <p><ul>
     *   <li> Direct incoming transitions from the outside ending at the state's boundary
     *   <li> Direct incoming transitions from the inside ending at the state's boundary
     *   <li> Indirect incoming transitions from outside the state ending in an entry point
     *          without continuation
     *   <li> Indirect incoming transitions from inside the state ending in an exit point
     *          without continuation
     * </ul>
     *
     * <p> When redirecting indirect incoming transitions, it also removes the connection point.
     *
     * <p>To guarantee the semantics specified in the profile we assume that the preprocessor has
     * created a {@link DeepHistory} element even for composite states that didn't have one.
     */
    protected def redirectIncomingTransitionsToHistory( CompositeState state )
    {
        for (t : state.allDirectIncomingTransitions)
        {
            t.targetVertex = state.deepHistory // This will be null if the preprocessor failed to create the DeepHistory point for the state.
        }
        for (p : state.allConnectionPoints)
        {
            if (p.allDirectOutgoingTransitions.isEmpty)
            {
                for (t : p.allDirectIncomingTransitions)
                {
                    t.targetVertex = state.deepHistory
                }
                state.removeConnectionPoint(p)
            }
        }
    }

    /**
     * Replaces the (deep)history pseudo-state with a choice point with transitions
     * to each sub-state with conditions checking the value of the history table
     * entry for this composite-state.
     */
    protected def ChoicePoint replaceHistoryPoint(CompositeState state) {
        // Create new choice point.
        val c = CommonFactory.eINSTANCE.createChoicePoint
        c.name = "deephistory"
        state.addChoicePoint(c)
        c.cachedFullSMName    // Cache the fully qualified name for code generation 
        // Redirect all transitions ending in the history pseudo-state to the
        // new choice point.
        if (state.hasDeepHistory)
        {
            for (Transition t: state.deepHistory.allDirectIncomingTransitions)
            {
                t.targetVertex = c
            }
        }
        // Create transitions from the choice point to each sub-state with the
        // appropriate guard.
        for (State subState: state.allSubstates)
        {
            var Vertex targetVertex
            if (subState instanceof SimpleState)
                targetVertex = subState
            else
            {
                val targetComposite = subState as CompositeState
                if (targetComposite.hasDeepHistory)
                    targetVertex = targetComposite.deepHistory
                else
                    targetVertex = targetComposite
            }
            val newTransition = CommonFactory.eINSTANCE.createTransition
            newTransition.name = newFreshTransitionName
            newTransition.sourceVertex = c
            newTransition.targetVertex = targetVertex
            newTransition.guard = makeCheckHistoryGuard( state, subState )
            newTransition.actionChain = CommonFactory.eINSTANCE.createActionChain
            state.addTransition( newTransition )
            newTransition.cachedFullSMName
            newTransition.actionChain.cachedFullSMName
        }
        // If there was an initial pseudo-state, create a transition from the
        // choice point to the initial state where the guard will be true if
        // the composite state has not been visited before.
        if (state.hasInitial && !state.initial.allDirectOutgoingTransitions.isEmpty)
        {
            // There should be exactly one initial transition
            val initialTransition = state.initial.allDirectOutgoingTransitions.get(0)
            val newTransition = CommonFactory.eINSTANCE.createTransition
            newTransition.name = newFreshTransitionName
            newTransition.sourceVertex = c
            newTransition.targetVertex = initialTransition.targetVertex
            newTransition.triggers.addAll( initialTransition.triggers )
            newTransition.guard = makeCheckHistoryGuard( state, UNVISITED )
            newTransition.actionChain = initialTransition.actionChain.copyActionChain
            state.addTransition( newTransition )
            newTransition.cachedFullSMName
            newTransition.actionChain.cachedFullSMName
        }
        // If there was no initial pseudo-state, create a 'dummy' simple state
        // representing "staying at the boundary", and create a transition from
        // the choice point to this dummy state, where the guard is true if
        // the state has not been visited before.
        else
        {
            // The name should be different name because one of the sub-states
            // may have the same name as the composite.
            val border = CommonFactory.eINSTANCE.createSimpleState
            border.name = "boundary"
            border.entryAction = state.entryAction
            border.exitAction = state.exitAction
            // TODO: check if we need to add also the original state's connection points and
            // their incoming and outgoing transitions.
            state.addSubstate( border )
            border.cachedFullSMName
            val newTransition = CommonFactory.eINSTANCE.createTransition
            newTransition.name = newFreshTransitionName + "_to_" + border.name
            newTransition.sourceVertex = c
            newTransition.targetVertex = border
            newTransition.guard = makeCheckHistoryGuard( state, UNVISITED )
            newTransition.actionChain = CommonFactory.eINSTANCE.createActionChain
            state.addTransition( newTransition )
            newTransition.cachedFullSMName
            newTransition.actionChain.cachedFullSMName
        }
        // Remove the history pseudo-state.
        state.removeDeepHistory
        return c
    }

    /**
     * Remove the initial pseudo-state and transition.
     */
    protected def removeInitialTransition( CompositeState state )
    {
        if (state.hasInitial)
        {
            if (state.initial.allDirectOutgoingTransitions.isEmpty)
            {
                state.removeInitial
            }
            else if (state.initial.allDirectIncomingTransitions.isEmpty)
            {
                val initialTransition = state.initial.allDirectOutgoingTransitions.get(0) // There should be exactly one initial transition
                state.removeTransition( initialTransition )
                //initialTransition.targetVertex.directIncomingTransitions.remove( initialTransition ) // TODO: this might not be enough if the initial transition ends in a pseudo-state
                state.removeInitial
            }
        }
    }

    /**
     * Annotate explicit exit transitions (transitions from a sub-state to an
     * exit point) with a "history saving action", an action that sets the
     * history table entry for the composite state according to the sub-state
     * that is exited.
     */
    // TODO: we probably need to do this also with transitions owned by the composite state which end in the deep history point.
    protected def annotateExplicitExitTransitions( CompositeState state )
    {
        for (ExitPoint p: state.allExitPoints)
        {
            if (!p.allDirectIncomingTransitions.isEmpty)
            {
                for (Transition t: getExitingTransitionsFromSubstates(p))
                {
                    val savingAction = makeSaveHistoryAction( state, t.sourceState )
                    t.actionChain.addLastAction( savingAction )
                }
            }
        }
    }

    /**
     * Computes the set of outgoing transitions from sub-states which are the
     * first transition segment of a transition chain ending in the given exit
     * point.
     *
     * This function must work backwards searching for the states which are
     * "points of departure".
     *
     * The method caches previous results to speed up the operation when a given
     * exit point is queried several times.
     *
     * The search algorithm used is DFS (depth-first search).
     */
    protected def
    create set: new HashSet<Transition>
    getExitingTransitionsFromSubstates( ExitPoint point )
    {
        val explorer = new ExitingTransitionDiscoverer(set)
        explorer.dfs(point)
    }

    static class ExitingTransitionDiscoverer
    {

        val Set<Pseudostate> visitedPseudostates = newLinkedHashSet
        var Set<Transition> exitingTransitions

        new (HashSet<Transition> transitions)
        {
            exitingTransitions = transitions
        }

        def void dfs( Pseudostate p )
        {
            visitedPseudostates.add( p )
            for (trans : p.allDirectIncomingTransitions)
            {
                val source = trans.sourceVertex
                if ( source instanceof State || source instanceof ExitPoint )
                {
                    exitingTransitions.add(trans)
                }
                else if ( !visitedPseudostates.contains( source ) )
                {
                    dfs( source as Pseudostate )
                }
            }
        }

    }

    /**
     * Create exit points for each direct outgoing transition (transition leaving
     * directly from the composite state's boundary).
     */
    protected def createExitPoints( CompositeState state )
    {
        for (Transition t: state.allDirectOutgoingTransitions)
        {
            val p = CommonFactory.eINSTANCE.createExitPoint
            p.name = newFreshExitPointName
            state.addExitPoint( p )
            p.cachedFullSMName
            t.sourceVertex = p
        }
    }

    /**
     * Create explicit exit transitions implied by group transitions. For each
     * group transition creates a transition from every sub-state to the
     * group-transitions's source exit point. It annotates these transitions
     * with a "history saving action", an action which stores the sub-state
     * in the history table entry for this composite state.
     */
    protected def createExitTransitions( CompositeState state )
    {
        // Iterate over exit points, looking for group transitions.
        for (ExitPoint p: state.allExitPoints)
        {
            // If the exit point doesn't have incoming transitions then there
            // is at least one group transition from it.
            if (p.allDirectIncomingTransitions.isEmpty)
            {
                // Create explicit transitions from every sub-state to this
                // exit point...
                for (State subState: state.allSubstates)
                {
                    // ...for each group transition leaving the exit point.
                    for (Transition t: p.allDirectOutgoingTransitions)
                    {
                        // Create the history saving action.
                        val savingAction = makeSaveHistoryAction( state, subState )
                        // Append the history saving action and the state's
                        // exit action.
                        val newChain = CommonFactory.eINSTANCE.createActionChain
                        newChain.addLastAction( savingAction )
                        val newTransition = CommonFactory.eINSTANCE.createTransition
                        newTransition.name = newFreshTransitionName
                        newTransition.sourceVertex = subState
                        newTransition.targetVertex = p
                        newTransition.triggers.addAll( EcoreUtil.copyAll( t.triggers ) )
                        newTransition.guard = t.guard
                        newTransition.actionChain = newChain
//                        newTransition.nestingDepth = t.nestingDepth
                        state.addTransition( newTransition )
                        newTransition.cachedFullSMName
                        newChain.cachedFullSMName
                    }
                }
                // Remove the trigger and guard from the old group transition,
                // but leave the transition.
                // TODO: we must replace the direct group transition with an indirect group transition leaving from the exit point
                for (Transition t: p.allDirectOutgoingTransitions)
                {
                    t.removeTriggers
                    t.removeGuard
                }
            }
        }
    }

    /**
     * Qualifies names and removes composite states.
     */
    protected def void phase2( StateMachine m )
    {
        val states = m.top?.getAllSubstates
        if (states === null) return;
        for (State s: states.clone)
        {
            phase2ProcessState( s )
            if (s instanceof CompositeState)
            {
                m.moveContents( s )
                discardedStates.add( s )
                m.removeState( s )
            }
        }
    }

    /**
     * Removes entry and exit points of a simple state, making all transitions
     * to and from it, direct.
     */
    protected def dispatch void phase2ProcessState( SimpleState state )
    {
        state.name = state.cachedFullSMName
        for (Transition t: state.allIndirectIncomingTransitions)
        {
            t.targetVertex = state      // TODO: Potential problem: depending on the set implementation we may be modifying the iterator
        }
        for (Transition t: state.allOutgoingTransitions)
        {
            t.sourceVertex = state
        }
        for (EntryPoint p: state.allEntryPoints)
        {
            state.removeEntryPoint( p )
        }
        for (ExitPoint p: state.allExitPoints)
        {
            state.removeExitPoint( p )
        }
    }

    /**
     * Replaces all entry and exit points of the composite state with junction
     * points, and recursively apply this to sub-states.
     */
    protected def dispatch void phase2ProcessState( CompositeState state )
    {
        state.name = state.cachedFullSMName
        // Replace connection points with junction points
        for (Pseudostate p: state.allConnectionPoints)
        {
            val j = CommonFactory.eINSTANCE.createJunctionPoint
            j.name = p.name
            state.addJunctionPoint( j )
            j.cachedFullSMName
            j.cachedFullName
            for (Transition t: p.allDirectIncomingTransitions)
            {
                t.targetVertex = j
            }
            for (Transition t: p.allDirectOutgoingTransitions)
            {
                t.sourceVertex = j
            }
            if (p instanceof EntryPoint)
            {
                state.removeEntryPoint(p)
            }
            else if (p instanceof ExitPoint)
            {
                state.removeExitPoint(p)
            }
        }
        // Process substates
        
        for (State subState: state.allSubstates.clone)
        {
            phase2ProcessState( subState )
            if (subState instanceof CompositeState)
            {
                state.moveContents( subState )
                discardedStates.add( subState )
                state.removeSubstate( subState )
            }
        }
    }

    /**
     * Auxiliary methods
     */

    // TODO: check that the new name doesn't clash with an existing name
    private def String getNewFreshName() {
        freshNameCounter ++
        return GlobalConstants.FRESH_NAME_PREFIX + freshNameCounter
    }

    private def String getNewFreshEntryPointName() {
        freshEntryPointNameCounter ++
        return GlobalConstants.FRESH_ENTRYPOINT_NAME_PREFIX + freshEntryPointNameCounter
    }

    private def String getNewFreshExitPointName() {
        freshExitPointNameCounter ++
        return GlobalConstants.FRESH_EXITPOINT_NAME_PREFIX + freshExitPointNameCounter
    }

    private def String getNewFreshChoicePointName() {
        freshChoicePointNameCounter ++
        return GlobalConstants.FRESH_CHOICEPOINT_NAME_PREFIX + freshChoicePointNameCounter
    }

    private def String getNewFreshJunctionPointName() {
        freshJunctionPointNameCounter ++
        return GlobalConstants.FRESH_JUNCTIONPOINT_NAME_PREFIX + freshJunctionPointNameCounter
    }

    private def String getNewFreshTransitionName() {
        freshTransitionNameCounter ++
        return GlobalConstants.FRESH_TRANSITION_NAME_PREFIX +freshTransitionNameCounter
    }

    private def Guard makeCheckHistoryGuard
    (
        CompositeState compositeState,
        State subState
    )
    {
        val checkHistory = SmflatmodelFactory.eINSTANCE.createCheckHistory
        checkHistory.compositeState = compositeState
        checkHistory.subState = subState
        val guard = CommonFactory.eINSTANCE.createGuard
        guard.body = checkHistory
        guard
    }

    private def SaveHistory makeSaveHistoryAction
    (
        CompositeState compositeState,
        State subState
    )
    {
        val saveHistory = SmflatmodelFactory.eINSTANCE.createSaveHistory
        saveHistory.compositeState = compositeState
        saveHistory.subState = subState
        saveHistory
    }

    private static def ActionChain copyActionChain( ActionChain actionChain )
    {
        val newActionChain = CommonFactory.eINSTANCE.createActionChain
        newActionChain.name = actionChain.name
        newActionChain.actions.addAll( actionChain.actions.map [ it.copyAction ] )
        newActionChain
    }

    private static def ActionCode copyAction( ActionCode action )
    {
        val newAction = CommonFactory.eINSTANCE.createActionCode
        newAction.source = action.source
        newAction
    }

    private static def moveContents( CompositeState stateTo, CompositeState stateFrom )
    {
        for (s : stateFrom.allSubstates.clone)
        {
            stateFrom.removeSubstate( s )
            stateTo.addSubstate( s )
        }
        for (j : stateFrom.allJunctionPoints.clone)
        {
            stateFrom.removeJunctionPoint( j )
            stateTo.addJunctionPoint( j )
        }
        for (c : stateFrom.allChoicePoints.clone)
        {
            stateFrom.removeChoicePoint( c )
            stateTo.addChoicePoint( c )
        }
        for (t : stateFrom.allTransitions.clone)
        {
            stateFrom.removeTransition( t )
            stateTo.addTransition( t )
        }
    }

    private static def moveContents( StateMachine stateMachineTo, CompositeState stateFrom )
    {
        stateMachineTo.top?.moveContents( stateFrom )
    }

}
