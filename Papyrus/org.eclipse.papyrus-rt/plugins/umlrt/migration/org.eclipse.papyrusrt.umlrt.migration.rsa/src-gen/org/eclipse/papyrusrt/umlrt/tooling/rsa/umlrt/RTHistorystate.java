/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Pseudostate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RT Historystate</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate#getBase_Pseudostate <em>Base Pseudostate</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate#getHistoryKind <em>History Kind</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTHistorystate()
 * @model
 * @generated
 */
public interface RTHistorystate extends EObject {
	/**
	 * Returns the value of the '<em><b>Base Pseudostate</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Pseudostate</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Base Pseudostate</em>' reference.
	 * @see #setBase_Pseudostate(Pseudostate)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTHistorystate_Base_Pseudostate()
	 * @model required="true"
	 * @generated
	 */
	Pseudostate getBase_Pseudostate();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate#getBase_Pseudostate <em>Base Pseudostate</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Base Pseudostate</em>' reference.
	 * @see #getBase_Pseudostate()
	 * @generated
	 */
	void setBase_Pseudostate(Pseudostate value);

	/**
	 * Returns the value of the '<em><b>History Kind</b></em>' attribute.
	 * The default value is <code>"Deep"</code>.
	 * The literals are from the enumeration {@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.HistoryKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>History Kind</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>History Kind</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.HistoryKind
	 * @see #setHistoryKind(HistoryKind)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTHistorystate_HistoryKind()
	 * @model default="Deep"
	 * @generated
	 */
	HistoryKind getHistoryKind();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate#getHistoryKind <em>History Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>History Kind</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.HistoryKind
	 * @see #getHistoryKind()
	 * @generated
	 */
	void setHistoryKind(HistoryKind value);

} // RTHistorystate
