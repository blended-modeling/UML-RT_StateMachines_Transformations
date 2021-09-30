/**
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Type;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Interface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtInterface#getImplicitOperations <em>Implicit Operation</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getInterface()
 * @model
 * @generated
 */
public interface ExtInterface extends ExtNamespace {
	/**
	 * Returns the value of the '<em><b>Implicit Operation</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.uml.Operation}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 *   <li>'{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getImplicitOwnedMembers() <em>Implicit Owned Member</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implicit Operation</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implicit Operation</em>' containment reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getInterface_ImplicitOperation()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Operation> getImplicitOperations();

	/**
	 * Creates a new {@link org.eclipse.uml2.uml.Operation}, with the specified '<em><b>Name</b></em>', '<em><b>Owned Parameter Names</b></em>', and '<em><b>Owned Parameter Types</b></em>', and appends it to the '<em><b>Implicit Operation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' for the new {@link org.eclipse.uml2.uml.Operation}, or <code>null</code>.
	 * @param ownedParameterNames The '<em><b>Owned Parameter Names</b></em>' for the new {@link org.eclipse.uml2.uml.Operation}, or <code>null</code>.
	 * @param ownedParameterTypes The '<em><b>Owned Parameter Types</b></em>' for the new {@link org.eclipse.uml2.uml.Operation}, or <code>null</code>.
	 * @return The new {@link org.eclipse.uml2.uml.Operation}.
	 * @see #getImplicitOperations()
	 * @generated
	 */
	Operation createImplicitOperation(String name, EList<String> ownedParameterNames, EList<Type> ownedParameterTypes);

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Operation} with the specified '<em><b>Name</b></em>', '<em><b>Owned Parameter Names</b></em>', and '<em><b>Owned Parameter Types</b></em>' from the '<em><b>Implicit Operation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Operation} to retrieve, or <code>null</code>.
	 * @param ownedParameterNames The '<em><b>Owned Parameter Names</b></em>' of the {@link org.eclipse.uml2.uml.Operation} to retrieve, or <code>null</code>.
	 * @param ownedParameterTypes The '<em><b>Owned Parameter Types</b></em>' of the {@link org.eclipse.uml2.uml.Operation} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.uml2.uml.Operation} with the specified '<em><b>Name</b></em>', '<em><b>Owned Parameter Names</b></em>', and '<em><b>Owned Parameter Types</b></em>', or <code>null</code>.
	 * @see #getImplicitOperations()
	 * @generated
	 */
	Operation getImplicitOperation(String name, EList<String> ownedParameterNames, EList<Type> ownedParameterTypes);

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Operation} with the specified '<em><b>Name</b></em>', '<em><b>Owned Parameter Names</b></em>', and '<em><b>Owned Parameter Types</b></em>' from the '<em><b>Implicit Operation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Operation} to retrieve, or <code>null</code>.
	 * @param ownedParameterNames The '<em><b>Owned Parameter Names</b></em>' of the {@link org.eclipse.uml2.uml.Operation} to retrieve, or <code>null</code>.
	 * @param ownedParameterTypes The '<em><b>Owned Parameter Types</b></em>' of the {@link org.eclipse.uml2.uml.Operation} to retrieve, or <code>null</code>.
	 * @param ignoreCase Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param createOnDemand Whether to create a {@link org.eclipse.uml2.uml.Operation} on demand if not found.
	 * @return The first {@link org.eclipse.uml2.uml.Operation} with the specified '<em><b>Name</b></em>', '<em><b>Owned Parameter Names</b></em>', and '<em><b>Owned Parameter Types</b></em>', or <code>null</code>.
	 * @see #getImplicitOperations()
	 * @generated
	 */
	Operation getImplicitOperation(String name, EList<String> ownedParameterNames, EList<Type> ownedParameterTypes, boolean ignoreCase, boolean createOnDemand);

} // ExtInterface
