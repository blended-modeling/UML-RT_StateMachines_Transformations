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

import org.eclipse.uml2.uml.Behavior;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Behaviored Classifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtBehavioredClassifier#getImplicitBehaviors <em>Implicit Behavior</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getBehavioredClassifier()
 * @model abstract="true"
 * @generated
 */
public interface ExtBehavioredClassifier extends ExtNamespace {
	/**
	 * Returns the value of the '<em><b>Implicit Behavior</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.uml.Behavior}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 *   <li>'{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getImplicitOwnedMembers() <em>Implicit Owned Member</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implicit Behavior</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implicit Behavior</em>' containment reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getBehavioredClassifier_ImplicitBehavior()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Behavior> getImplicitBehaviors();

	/**
	 * Creates a new {@link org.eclipse.uml2.uml.Behavior}, with the specified '<em><b>Name</b></em>', and appends it to the '<em><b>Implicit Behavior</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' for the new {@link org.eclipse.uml2.uml.Behavior}, or <code>null</code>.
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.uml.Behavior} to create.
	 * @return The new {@link org.eclipse.uml2.uml.Behavior}.
	 * @see #getImplicitBehaviors()
	 * @generated
	 */
	Behavior createImplicitBehavior(String name, EClass eClass);

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Behavior} with the specified '<em><b>Name</b></em>' from the '<em><b>Implicit Behavior</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Behavior} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.uml2.uml.Behavior} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getImplicitBehaviors()
	 * @generated
	 */
	Behavior getImplicitBehavior(String name);

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Behavior} with the specified '<em><b>Name</b></em>' from the '<em><b>Implicit Behavior</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Behavior} to retrieve, or <code>null</code>.
	 * @param ignoreCase Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.uml.Behavior} to retrieve, or <code>null</code>.
	 * @param createOnDemand Whether to create a {@link org.eclipse.uml2.uml.Behavior} on demand if not found.
	 * @return The first {@link org.eclipse.uml2.uml.Behavior} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getImplicitBehaviors()
	 * @generated
	 */
	Behavior getImplicitBehavior(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand);

} // ExtBehavioredClassifier
