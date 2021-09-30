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
import org.eclipse.uml2.uml.CallEvent;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.AbstractEvent#getBase_CallEvent <em>Base Call Event</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getAbstractEvent()
 * @model abstract="true"
 * @generated
 */
public interface AbstractEvent extends EObject {
	/**
	 * Returns the value of the '<em><b>Base Call Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Call Event</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Base Call Event</em>' reference.
	 * @see #setBase_CallEvent(CallEvent)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getAbstractEvent_Base_CallEvent()
	 * @model required="true"
	 * @generated
	 */
	CallEvent getBase_CallEvent();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.AbstractEvent#getBase_CallEvent <em>Base Call Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Base Call Event</em>' reference.
	 * @see #getBase_CallEvent()
	 * @generated
	 */
	void setBase_CallEvent(CallEvent value);

} // AbstractEvent
