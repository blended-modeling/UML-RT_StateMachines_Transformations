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

import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTVertexImpl;
import org.eclipse.uml2.uml.Vertex;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vertex</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation of the UML-RT concept of <em>Vertex</em> in a state machine.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getState <em>State</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getIncomings <em>Incoming</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getOutgoings <em>Outgoing</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getRedefinedVertex <em>Redefined Vertex</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getStateMachine <em>State Machine</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getVertex()
 * @generated
 */
public interface UMLRTVertex extends UMLRTNamedElement {
	/**
	 * Obtains the canonical instance of the façade for a state machine vertex.
	 *
	 * @param vertex
	 *            a vertex in a state machine in the UML model
	 *
	 * @return its façade, or {@code null} if the vertex is not a valid UML-RT state machine vertex
	 */
	public static UMLRTVertex getInstance(Vertex vertex) {
		return UMLRTVertexImpl.getInstance(vertex);
	}

	/**
	 * Returns the value of the '<em><b>State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getSubvertices <em>Subvertex</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The composite state that contains the vertex, in which case
	 * it does not have a <code>stateMachine</code>.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>State</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getVertex_State()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getSubvertices
	 * @generated
	 */
	UMLRTState getState();

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The transitions outgoing from this vertex.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getVertex_Outgoing()
	 * @generated
	 */
	List<UMLRTTransition> getOutgoings();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>' from the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOutgoings()
	 * @generated
	 */
	UMLRTTransition getOutgoing(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>' from the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOutgoings()
	 * @generated
	 */
	UMLRTTransition getOutgoing(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The transitions incoming to this vertex.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getVertex_Incoming()
	 * @generated
	 */
	List<UMLRTTransition> getIncomings();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>' from the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getIncomings()
	 * @generated
	 */
	UMLRTTransition getIncoming(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>' from the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getIncomings()
	 * @generated
	 */
	UMLRTTransition getIncoming(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Redefined Vertex</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinedElement() <em>Redefined Element</em>}'</li>
	 * </ul>
	 * This feature is a derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The vertex in the redefined state machine or composite state
	 * that this vertex redefines.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefined Vertex</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getVertex_RedefinedVertex()
	 * @generated
	 */
	UMLRTVertex getRedefinedVertex();

	/**
	 * Returns the value of the '<em><b>State Machine</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getVertices <em>Vertex</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The state machine that directly contains the vertex, if it is not
	 * in a composite state.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>State Machine</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getVertex_StateMachine()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getVertices
	 * @generated
	 */
	UMLRTStateMachine getStateMachine();

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
	 * Obtains the underlying UML representation of this vertex.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	Vertex toUML();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries the state machine that contains this vertex.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	UMLRTStateMachine containingStateMachine();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new transition from this vertex to the <code>target</code>.
	 *
	 * @param target
	 *            The target vertex of the transition to create.
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTTransition createTransitionTo(UMLRTVertex target);

} // UMLRTVertex
