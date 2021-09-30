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
import org.eclipse.uml2.uml.Port;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RT Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#getBase_Port <em>Base Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsConjugate <em>Is Conjugate</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsNotification <em>Is Notification</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsPublish <em>Is Publish</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsWired <em>Is Wired</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#getRegistration <em>Registration</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#getRegistrationOverride <em>Registration Override</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTPort()
 * @model
 * @generated
 */
public interface RTPort extends EObject {
	/**
	 * Returns the value of the '<em><b>Base Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Port</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Base Port</em>' reference.
	 * @see #setBase_Port(Port)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTPort_Base_Port()
	 * @model required="true"
	 * @generated
	 */
	Port getBase_Port();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#getBase_Port <em>Base Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Base Port</em>' reference.
	 * @see #getBase_Port()
	 * @generated
	 */
	void setBase_Port(Port value);

	/**
	 * Returns the value of the '<em><b>Is Conjugate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Conjugate</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Is Conjugate</em>' attribute.
	 * @see #setIsConjugate(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTPort_IsConjugate()
	 * @model
	 * @generated
	 */
	boolean isIsConjugate();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsConjugate <em>Is Conjugate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Is Conjugate</em>' attribute.
	 * @see #isIsConjugate()
	 * @generated
	 */
	void setIsConjugate(boolean value);

	/**
	 * Returns the value of the '<em><b>Is Notification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Notification</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Is Notification</em>' attribute.
	 * @see #setIsNotification(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTPort_IsNotification()
	 * @model
	 * @generated
	 */
	boolean isIsNotification();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsNotification <em>Is Notification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Is Notification</em>' attribute.
	 * @see #isIsNotification()
	 * @generated
	 */
	void setIsNotification(boolean value);

	/**
	 * Returns the value of the '<em><b>Is Publish</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Publish</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Is Publish</em>' attribute.
	 * @see #setIsPublish(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTPort_IsPublish()
	 * @model
	 * @generated
	 */
	boolean isIsPublish();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsPublish <em>Is Publish</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Is Publish</em>' attribute.
	 * @see #isIsPublish()
	 * @generated
	 */
	void setIsPublish(boolean value);

	/**
	 * Returns the value of the '<em><b>Is Wired</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Wired</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Is Wired</em>' attribute.
	 * @see #setIsWired(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTPort_IsWired()
	 * @model default="true"
	 * @generated
	 */
	boolean isIsWired();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsWired <em>Is Wired</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Is Wired</em>' attribute.
	 * @see #isIsWired()
	 * @generated
	 */
	void setIsWired(boolean value);

	/**
	 * Returns the value of the '<em><b>Registration</b></em>' attribute.
	 * The default value is <code>"Automatic"</code>.
	 * The literals are from the enumeration {@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.PortRegistrationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Registration</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Registration</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.PortRegistrationType
	 * @see #setRegistration(PortRegistrationType)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTPort_Registration()
	 * @model default="Automatic"
	 * @generated
	 */
	PortRegistrationType getRegistration();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#getRegistration <em>Registration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Registration</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.PortRegistrationType
	 * @see #getRegistration()
	 * @generated
	 */
	void setRegistration(PortRegistrationType value);

	/**
	 * Returns the value of the '<em><b>Registration Override</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Registration Override</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Registration Override</em>' attribute.
	 * @see #setRegistrationOverride(String)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTPort_RegistrationOverride()
	 * @model
	 * @generated
	 */
	String getRegistrationOverride();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#getRegistrationOverride <em>Registration Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Registration Override</em>' attribute.
	 * @see #getRegistrationOverride()
	 * @generated
	 */
	void setRegistrationOverride(String value);

} // RTPort
