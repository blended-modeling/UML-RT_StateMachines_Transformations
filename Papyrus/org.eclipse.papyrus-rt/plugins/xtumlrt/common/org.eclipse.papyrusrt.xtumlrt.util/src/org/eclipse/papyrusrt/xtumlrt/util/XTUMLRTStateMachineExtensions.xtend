/*****************************************************************************
 * Copyright (c) 2017 Zeligsoft and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Ernesto Posse - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.util

import java.util.LinkedHashSet
import java.util.List
import org.eclipse.papyrusrt.xtumlrt.statemach.ChoicePoint
import org.eclipse.papyrusrt.xtumlrt.statemach.CompositeState
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
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTSMVirtualInheritanceExtensions
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTStateMachineUtil.*
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.*

/**
 * This class is intended to replace the extension methods from 
 * {@link XTUMLRTSMVirtualInheritanceExtensions}.
 * 
 * <p>The introduction of the new UML-RT implementation in 
 * {@link org.eclipse.papyrusrt.umlrt.uml} already accounts for inheritance,
 * which means that accessing the owned members of a 
 * {@link org.eclipse.uml2.uml.Namespace}, or more precisely of a
 * {@link org.eclipse.papyrusrt.umlrt.uml.internal.impl#InternalUMLRTNamespace}
 * with {@link org.eclipse.uml2.uml.Namespace#getOwnedMembers} will return not 
 * only the directly owned members but also those inherited, or more precisely, it
 * will return "virtual redefinitions" (aka "shadows") of the inherited members.
 * 
 * <p>Since the translator from UML to XtUML-RT uses 
 * {@link org.eclipse.uml2.uml.Namespace#getOwnedMembers} when translating each
 * element (as well as 
 * {@link org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil#getUMLRTContents})
 * then the resulting XtUML-RT model elements are "flattened", i.e. they already 
 * include all the inherited elements. The consequence of this is that the
 * "virtual inheritance" extension methods from 
 * {@link XTUMLRTSMVirtualInheritanceExtensions} are redundant, and may even 
 * lead to incorrect results if certain elements created during code generation
 * do not have their "refines" reference correctly set, as it happened in Bug 
 * 516446, or if a client access an inherited element which is not a "virtual 
 * redefinition" but the "real" element in a parent classifier. So this class is 
 * intended to override all the {@code getAll*} methods from its superclass to 
 * directly return the corresponding list of members of the relevant XtUML-RT, 
 * since they should already have all inherited elements.
 * 
 * <p>As a result, most methods here are trivial delegation methods, but they
 * are provided nevertheless to support the API that current clients of
 * {@link XTUMLRTSMVirtualInheritanceExtensions} use. Eventually the code
 * generator should replace this API and use directly the API from the new
 * UML-RT implementation.
 * 
 * @author epp
 */
class XTUMLRTStateMachineExtensions extends XTUMLRTSMVirtualInheritanceExtensions {

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
	static dispatch def Iterable<Pseudostate> getAllPseudostates(StateMachine stateMachine) {
		stateMachine.top.allPseudostates
	}

	static dispatch def Iterable<Pseudostate> getAllPseudostates(State state) {
		if(state !== null) #[]
	}

	static dispatch def Iterable<Pseudostate> getAllPseudostates(CompositeState state) {
		state.pseudostates.clone
	}

	/**
	 * Returns the union of:
	 * 
	 * <ol>
	 *   <li> the set inherited connection points, and
	 *   <li> the set of new connection points which are not inherited
	 * </ol>
	 */
	static def Iterable<Pseudostate> getAllConnectionPoints(State state) {
		state.connectionPoints
	}

	/**
	 * Returns the union of:
	 * 
	 * <ol>
	 *   <li> the set inherited entry points, and
	 *   <li> the set of new entry points which are not inherited
	 * </ol>
	 */
	static def Iterable<EntryPoint> getAllEntryPoints(State state) {
		state.entryPoints.clone
	}

	/**
	 * Returns the union of:
	 * 
	 * <ol>
	 *   <li> the set inherited entry points, and
	 *   <li> the set of new entry points which are not inherited
	 * </ol>
	 */
	static def Iterable<ExitPoint> getAllExitPoints(State state) {
		state.exitPoints.clone
	}

	/**
	 * Returns the initial point of the state machine if it has one, or the
	 * inherited initial point, if there is one along the inheritance hierarchy.
	 */
	static dispatch def InitialPoint getActualInitialPoint(StateMachine stateMachine) {
		stateMachine.top.actualInitialPoint
	}

	static dispatch def InitialPoint getActualInitialPoint(CompositeState state) {
		state.initial
	}

	/**
	 * Returns the initial point of the state machine if it has one, or the
	 * inherited initial point, if there is one along the inheritance hierarchy.
	 */
	static dispatch def DeepHistory getActualDeepHistory(StateMachine stateMachine) {
		stateMachine.top.actualDeepHistory
	}

	static dispatch def DeepHistory getActualDeepHistory(CompositeState state) {
		state.deepHistory
	}

	/**
	 * Returns the union of:
	 * 
	 * <ol>
	 *   <li> the set inherited choice points,
	 *   <li> the set of new choice points which are neither inherited, not redefinitions
	 * </ol>
	 */
	static def dispatch Iterable<ChoicePoint> getAllChoicePoints(StateMachine stateMachine) {
		stateMachine.top.allChoicePoints
	}

	static def dispatch Iterable<ChoicePoint> getAllChoicePoints(State state) {
		if(state !== null) #[]
	}

	static def dispatch Iterable<ChoicePoint> getAllChoicePoints(CompositeState state) {
		state.choicePoints.clone
	}

	/**
	 * Returns the union of:
	 * 
	 * <ol>
	 *   <li> the set inherited junction points,
	 *   <li> the set of new junction points which are neither inherited, not redefinitions
	 * </ol>
	 */
	static def dispatch Iterable<JunctionPoint> getAllJunctionPoints(StateMachine stateMachine) {
		stateMachine.top.allJunctionPoints
	}

	static def dispatch Iterable<JunctionPoint> getAllJunctionPoints(State state) {
		if(state !== null) #[]
	}

	static def dispatch Iterable<JunctionPoint> getAllJunctionPoints(CompositeState state) {
		state.junctionPoints.clone
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
		if(stateMachine.top === null) return #[];
		stateMachine.top.allSubstates
	}

	static def dispatch Iterable<State> getAllSubstates(State state) {
		#[]
	}

	static def dispatch Iterable<State> getAllSubstates(CompositeState state) {
		state.actualSubstates
	}

	/**
	 * Returns the set of substates of the given composite state that have not been excluded.
	 */
	static def Iterable<State> getActualSubstates(CompositeState state) {
		val substates = new LinkedHashSet<State>
		substates.addAll(state.substates.filter[!isExcluded])
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
	static def dispatch Iterable<Transition> getAllTransitions(StateMachine stateMachine) {
		if(stateMachine.top === null) return #[];
		stateMachine.top.allTransitions
	}

	static def dispatch Iterable<Transition> getAllTransitions(State state) {
		#[]
	}

	static def dispatch Iterable<Transition> getAllTransitions(CompositeState state) {
		state.actualTransitions
	}

	/**
	 * Returns the set of transitions of the given composite state that have not been excluded.
	 */
	static def Iterable<Transition> getActualTransitions(CompositeState state) {
		val transitions = new LinkedHashSet<Transition>
		transitions.addAll(state.transitions.filter[!isExcluded])
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
	static def Iterable<Trigger> getAllTriggers(Transition transition) {
		transition.actualTriggers
	}

	/**
	 * Returns the set of triggers of the given transition that have not been excluded.
	 */
	static def Iterable<Trigger> getActualTriggers(Transition transition) {
		val triggers = new LinkedHashSet<Trigger>
		triggers.addAll(transition.triggers.filter[!isExcluded])
		triggers
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
	static def Iterable<Transition> getAllDirectIncomingTransitions(Vertex v) {
		v.directIncomingTransitions
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
	static def Iterable<Transition> getAllDirectOutgoingTransitions(Vertex v) {
		v.directOutgoingTransitions
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
	static def Iterable<Transition> getAllIndirectIncomingTransitions(State s) {
		s.indirectIncomingTransitions
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
	static def Iterable<Transition> getAllIndirectOutgoingTransitions(State s) {
		s.indirectOutgoingTransitions
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
	static def Iterable<Transition> getAllIncomingTransitions(State s) {
		val List<Transition> transitions = newArrayList
		transitions.addAll(s.allDirectIncomingTransitions)
		transitions.addAll(s.allIndirectIncomingTransitions)
		transitions
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
	static def Iterable<Transition> getAllOutgoingTransitions(State s) {
		val List<Transition> transitions = newArrayList
		transitions.addAll(s.allDirectOutgoingTransitions)
		transitions.addAll(s.allIndirectOutgoingTransitions)
		transitions
	}

}
