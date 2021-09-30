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
import org.eclipse.uml2.uml.Message;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RT Message</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage#getReceivePort <em>Receive Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage#getSendPort <em>Send Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage#getBase_Message <em>Base Message</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTMessage()
 * @model
 * @generated
 */
public interface RTMessage extends EObject {
	/**
	 * Returns the value of the '<em><b>Receive Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Receive Port</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Receive Port</em>' attribute.
	 * @see #setReceivePort(String)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTMessage_ReceivePort()
	 * @model
	 * @generated
	 */
	String getReceivePort();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage#getReceivePort <em>Receive Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Receive Port</em>' attribute.
	 * @see #getReceivePort()
	 * @generated
	 */
	void setReceivePort(String value);

	/**
	 * Returns the value of the '<em><b>Send Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Send Port</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Send Port</em>' attribute.
	 * @see #setSendPort(String)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTMessage_SendPort()
	 * @model
	 * @generated
	 */
	String getSendPort();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage#getSendPort <em>Send Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Send Port</em>' attribute.
	 * @see #getSendPort()
	 * @generated
	 */
	void setSendPort(String value);

	/**
	 * Returns the value of the '<em><b>Base Message</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Message</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Base Message</em>' reference.
	 * @see #setBase_Message(Message)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTMessage_Base_Message()
	 * @model required="true"
	 * @generated
	 */
	Message getBase_Message();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage#getBase_Message <em>Base Message</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Base Message</em>' reference.
	 * @see #getBase_Message()
	 * @generated
	 */
	void setBase_Message(Message value);

} // RTMessage
