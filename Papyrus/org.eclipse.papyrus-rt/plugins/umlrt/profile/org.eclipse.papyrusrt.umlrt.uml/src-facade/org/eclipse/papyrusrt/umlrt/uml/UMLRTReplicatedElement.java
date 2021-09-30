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

import org.eclipse.uml2.uml.Property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replicated Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An abstract representation of UML-RT concepts that are
 * replicated elements.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement#getReplicationFactor <em>Replication Factor</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement#getReplicationFactorAsString <em>Replication Factor As String</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement#isSymbolicReplicationFactor <em>Symbolic Replication Factor</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getReplicatedElement()
 * @generated
 */
public interface UMLRTReplicatedElement extends UMLRTNamedElement {
	/**
	 * Returns the value of the '<em><b>Replication Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element's replication factor.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Replication Factor</em>' attribute.
	 * @see #setReplicationFactor(int)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getReplicatedElement_ReplicationFactor()
	 * @generated
	 */
	int getReplicationFactor();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement#getReplicationFactor <em>Replication Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Replication Factor</em>' attribute.
	 * @see #getReplicationFactor()
	 * @generated
	 */
	void setReplicationFactor(int value);

	/**
	 * Returns the value of the '<em><b>Replication Factor As String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The replication factor represented as a string, either encoding
	 * the numeric replication factor or else the expression that
	 * computes it.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Replication Factor As String</em>' attribute.
	 * @see #setReplicationFactorAsString(String)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getReplicatedElement_ReplicationFactorAsString()
	 * @generated
	 */
	String getReplicationFactorAsString();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement#getReplicationFactorAsString <em>Replication Factor As String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Replication Factor As String</em>' attribute.
	 * @see #getReplicationFactorAsString()
	 * @generated
	 */
	void setReplicationFactorAsString(String value);

	/**
	 * Returns the value of the '<em><b>Symbolic Replication Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the replication factor is modelled as some kind of
	 * expression, or else it is a literal numeric value.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Symbolic Replication Factor</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getReplicatedElement_SymbolicReplicationFactor()
	 * @generated
	 */
	boolean isSymbolicReplicationFactor();

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
	 * Obtains the representation of the UML-RT element
	 * in the underlying UML model.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	Property toUML();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the capsule that contains the replicated element.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	UMLRTCapsule getCapsule();

} // UMLRTReplicatedElement
