/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateMachineImpl;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation of the UML-RT concept of <em>State Machine</em>, which
 * defines the behavior of a capsule or a passive class.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getVertices <em>Vertex</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getTransitions <em>Transition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getRedefinedStateMachine <em>Redefined State Machine</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getCapsule <em>Capsule</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getStateMachine()
 * @generated
 */
public interface UMLRTStateMachine extends UMLRTNamedElement {
	/**
	 * Obtains the canonical instance of the façade for a state machine.
	 *
	 * @param stateMachine
	 *            a state machine in the UML model
	 *
	 * @return its façade, or {@code null} if the state machine is not a valid UML-RT state machine
	 */
	public static UMLRTStateMachine getInstance(StateMachine stateMachine) {
		return UMLRTStateMachineImpl.getInstance(stateMachine);
	}

	/**
	 * Does a state machine consistent entirely and only of the default
	 * content created for it via the {@link #ensureDefaultContents()} API?
	 * 
	 * @param stateMachine
	 *            a state machine
	 * @return whether it is in the default configuration
	 * 
	 * @see #ensureDefaultContents()
	 */
	public static boolean isDefaultContent(UMLRTStateMachine stateMachine) {
		return (stateMachine instanceof UMLRTStateMachineImpl)
				&& ((UMLRTStateMachineImpl) stateMachine).isDefaultContent();
	}

	/**
	 * Returns the value of the '<em><b>Vertex</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getStateMachine <em>State Machine</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The vertices at the top level of the state machine.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Vertex</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getStateMachine_Vertex()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getStateMachine
	 * @generated
	 */
	List<UMLRTVertex> getVertices();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} with the specified '<em><b>Name</b></em>' from the '<em><b>Vertex</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getVertices()
	 * @generated
	 */
	UMLRTVertex getVertex(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} with the specified '<em><b>Name</b></em>' from the '<em><b>Vertex</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param eClass
	 *            The Ecore class of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getVertices()
	 * @generated
	 */
	UMLRTVertex getVertex(String name, boolean ignoreCase, EClass eClass);

	/**
	 * Returns the value of the '<em><b>Transition</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getStateMachine <em>State Machine</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The transitions directly contained in the state machine.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Transition</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getStateMachine_Transition()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getStateMachine
	 * @generated
	 */
	List<UMLRTTransition> getTransitions();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>' from the '<em><b>Transition</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getTransitions()
	 * @generated
	 */
	UMLRTTransition getTransition(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>' from the '<em><b>Transition</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getTransitions()
	 * @generated
	 */
	UMLRTTransition getTransition(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Redefined State Machine</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinedElement() <em>Redefined Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The state machine of the superclass capsule that this state machine redefines.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefined State Machine</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getStateMachine_RedefinedStateMachine()
	 * @generated
	 */
	UMLRTStateMachine getRedefinedStateMachine();

	/**
	 * Returns the value of the '<em><b>Capsule</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getStateMachine <em>State Machine</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The capsule for which I define the behavior.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Capsule</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getStateMachine_Capsule()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getStateMachine
	 * @generated
	 */
	UMLRTCapsule getCapsule();

	/**
	 * <p>
	 * This operation redefines the following operations:
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#toUML() <em>To UML</em>}'</li>
	 * </ul>
	 * </p>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the underlying UML representation of this state machine.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	StateMachine toUML();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new state in this state machine.
	 *
	 * @param name
	 *            The name of the state to create.
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTState createState(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new pseudostate in this state machine.
	 *
	 * @param kind
	 *            The kind of pseudostate to create
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTPseudostate createPseudostate(UMLRTPseudostateKind kind);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Ensures that the state machine has the default vertices
	 * and transitions appropriate to its current context, whether
	 * that be as the behavior of a base capsule definition or
	 * a subclass of a capsule that is redefining/extending the
	 * behavior of its superclass.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	void ensureDefaultContents();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the singular UML region of this state machine.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	Region toRegion();

	@Override
	Stream<? extends UMLRTStateMachine> allRedefinitions();

} // UMLRTStateMachine
