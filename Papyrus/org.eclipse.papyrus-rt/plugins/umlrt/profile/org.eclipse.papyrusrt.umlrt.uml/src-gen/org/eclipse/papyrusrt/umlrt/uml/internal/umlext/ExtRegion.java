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
package org.eclipse.papyrusrt.umlrt.uml.internal.umlext;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Region</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion#getImplicitSubvertices <em>Implicit Subvertex</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion#getImplicitTransitions <em>Implicit Transition</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getRegion()
 * @model
 * @generated
 */
public interface ExtRegion extends ExtNamespace {
	/**
	 * Returns the value of the '<em><b>Implicit Subvertex</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.uml.Vertex}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 *   <li>'{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getImplicitOwnedMembers() <em>Implicit Owned Member</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implicit Subvertex</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implicit Subvertex</em>' containment reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getRegion_ImplicitSubvertex()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Vertex> getImplicitSubvertices();

	/**
	 * Creates a new {@link org.eclipse.uml2.uml.Vertex}, with the specified '<em><b>Name</b></em>', and appends it to the '<em><b>Implicit Subvertex</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' for the new {@link org.eclipse.uml2.uml.Vertex}, or <code>null</code>.
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.uml.Vertex} to create.
	 * @return The new {@link org.eclipse.uml2.uml.Vertex}.
	 * @see #getImplicitSubvertices()
	 * @generated
	 */
	Vertex createImplicitSubvertex(String name, EClass eClass);

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Vertex} with the specified '<em><b>Name</b></em>' from the '<em><b>Implicit Subvertex</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Vertex} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.uml2.uml.Vertex} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getImplicitSubvertices()
	 * @generated
	 */
	Vertex getImplicitSubvertex(String name);

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Vertex} with the specified '<em><b>Name</b></em>' from the '<em><b>Implicit Subvertex</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Vertex} to retrieve, or <code>null</code>.
	 * @param ignoreCase Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.uml.Vertex} to retrieve, or <code>null</code>.
	 * @param createOnDemand Whether to create a {@link org.eclipse.uml2.uml.Vertex} on demand if not found.
	 * @return The first {@link org.eclipse.uml2.uml.Vertex} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getImplicitSubvertices()
	 * @generated
	 */
	Vertex getImplicitSubvertex(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand);

	/**
	 * Returns the value of the '<em><b>Implicit Transition</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.uml.Transition}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 *   <li>'{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getImplicitOwnedMembers() <em>Implicit Owned Member</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implicit Transition</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implicit Transition</em>' containment reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getRegion_ImplicitTransition()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Transition> getImplicitTransitions();

	/**
	 * Creates a new {@link org.eclipse.uml2.uml.Transition}, with the specified '<em><b>Name</b></em>', and appends it to the '<em><b>Implicit Transition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' for the new {@link org.eclipse.uml2.uml.Transition}, or <code>null</code>.
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.uml.Transition} to create.
	 * @return The new {@link org.eclipse.uml2.uml.Transition}.
	 * @see #getImplicitTransitions()
	 * @generated
	 */
	Transition createImplicitTransition(String name, EClass eClass);

	/**
	 * Creates a new {@link org.eclipse.uml2.uml.Transition}, with the specified '<em><b>Name</b></em>', and appends it to the '<em><b>Implicit Transition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' for the new {@link org.eclipse.uml2.uml.Transition}, or <code>null</code>.
	 * @return The new {@link org.eclipse.uml2.uml.Transition}.
	 * @see #getImplicitTransitions()
	 * @generated
	 */
	Transition createImplicitTransition(String name);

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Transition} with the specified '<em><b>Name</b></em>' from the '<em><b>Implicit Transition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Transition} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.uml2.uml.Transition} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getImplicitTransitions()
	 * @generated
	 */
	Transition getImplicitTransition(String name);

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Transition} with the specified '<em><b>Name</b></em>' from the '<em><b>Implicit Transition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Transition} to retrieve, or <code>null</code>.
	 * @param ignoreCase Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.uml.Transition} to retrieve, or <code>null</code>.
	 * @param createOnDemand Whether to create a {@link org.eclipse.uml2.uml.Transition} on demand if not found.
	 * @return The first {@link org.eclipse.uml2.uml.Transition} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getImplicitTransitions()
	 * @generated
	 */
	Transition getImplicitTransition(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand);

} // ExtRegion
