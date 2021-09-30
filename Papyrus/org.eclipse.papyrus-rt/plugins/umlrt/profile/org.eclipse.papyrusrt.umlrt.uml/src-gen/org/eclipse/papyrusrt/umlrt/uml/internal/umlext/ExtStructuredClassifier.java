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

import org.eclipse.emf.ecore.EClass;

import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Structured Classifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStructuredClassifier#getImplicitAttributes <em>Implicit Attribute</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStructuredClassifier#getImplicitConnectors <em>Implicit Connector</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getStructuredClassifier()
 * @model abstract="true"
 * @generated
 */
public interface ExtStructuredClassifier extends ExtNamespace {
	/**
	 * Returns the value of the '<em><b>Implicit Attribute</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.uml.Property}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 *   <li>'{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getImplicitOwnedMembers() <em>Implicit Owned Member</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implicit Attribute</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implicit Attribute</em>' containment reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getStructuredClassifier_ImplicitAttribute()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Property> getImplicitAttributes();

	/**
	 * Creates a new {@link org.eclipse.uml2.uml.Property}, with the specified '<em><b>Name</b></em>', and '<em><b>Type</b></em>', and appends it to the '<em><b>Implicit Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' for the new {@link org.eclipse.uml2.uml.Property}, or <code>null</code>.
	 * @param type The '<em><b>Type</b></em>' for the new {@link org.eclipse.uml2.uml.Property}, or <code>null</code>.
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.uml.Property} to create.
	 * @return The new {@link org.eclipse.uml2.uml.Property}.
	 * @see #getImplicitAttributes()
	 * @generated
	 */
	Property createImplicitAttribute(String name, Type type, EClass eClass);

	/**
	 * Creates a new {@link org.eclipse.uml2.uml.Property}, with the specified '<em><b>Name</b></em>', and '<em><b>Type</b></em>', and appends it to the '<em><b>Implicit Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' for the new {@link org.eclipse.uml2.uml.Property}, or <code>null</code>.
	 * @param type The '<em><b>Type</b></em>' for the new {@link org.eclipse.uml2.uml.Property}, or <code>null</code>.
	 * @return The new {@link org.eclipse.uml2.uml.Property}.
	 * @see #getImplicitAttributes()
	 * @generated
	 */
	Property createImplicitAttribute(String name, Type type);

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Property} with the specified '<em><b>Name</b></em>', and '<em><b>Type</b></em>' from the '<em><b>Implicit Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Property} to retrieve, or <code>null</code>.
	 * @param type The '<em><b>Type</b></em>' of the {@link org.eclipse.uml2.uml.Property} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.uml2.uml.Property} with the specified '<em><b>Name</b></em>', and '<em><b>Type</b></em>', or <code>null</code>.
	 * @see #getImplicitAttributes()
	 * @generated
	 */
	Property getImplicitAttribute(String name, Type type);

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Property} with the specified '<em><b>Name</b></em>', and '<em><b>Type</b></em>' from the '<em><b>Implicit Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Property} to retrieve, or <code>null</code>.
	 * @param type The '<em><b>Type</b></em>' of the {@link org.eclipse.uml2.uml.Property} to retrieve, or <code>null</code>.
	 * @param ignoreCase Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.uml.Property} to retrieve, or <code>null</code>.
	 * @param createOnDemand Whether to create a {@link org.eclipse.uml2.uml.Property} on demand if not found.
	 * @return The first {@link org.eclipse.uml2.uml.Property} with the specified '<em><b>Name</b></em>', and '<em><b>Type</b></em>', or <code>null</code>.
	 * @see #getImplicitAttributes()
	 * @generated
	 */
	Property getImplicitAttribute(String name, Type type, boolean ignoreCase, EClass eClass, boolean createOnDemand);

	/**
	 * Returns the value of the '<em><b>Implicit Connector</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.uml.Connector}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 *   <li>'{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getImplicitOwnedMembers() <em>Implicit Owned Member</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implicit Connector</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implicit Connector</em>' containment reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getStructuredClassifier_ImplicitConnector()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Connector> getImplicitConnectors();

	/**
	 * Creates a new {@link org.eclipse.uml2.uml.Connector}, with the specified '<em><b>Name</b></em>', and appends it to the '<em><b>Implicit Connector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' for the new {@link org.eclipse.uml2.uml.Connector}, or <code>null</code>.
	 * @return The new {@link org.eclipse.uml2.uml.Connector}.
	 * @see #getImplicitConnectors()
	 * @generated
	 */
	Connector createImplicitConnector(String name);

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Connector} with the specified '<em><b>Name</b></em>' from the '<em><b>Implicit Connector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Connector} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.uml2.uml.Connector} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getImplicitConnectors()
	 * @generated
	 */
	Connector getImplicitConnector(String name);

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Connector} with the specified '<em><b>Name</b></em>' from the '<em><b>Implicit Connector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Connector} to retrieve, or <code>null</code>.
	 * @param ignoreCase Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param createOnDemand Whether to create a {@link org.eclipse.uml2.uml.Connector} on demand if not found.
	 * @return The first {@link org.eclipse.uml2.uml.Connector} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getImplicitConnectors()
	 * @generated
	 */
	Connector getImplicitConnector(String name, boolean ignoreCase, boolean createOnDemand);

} // StructuredClassifier
