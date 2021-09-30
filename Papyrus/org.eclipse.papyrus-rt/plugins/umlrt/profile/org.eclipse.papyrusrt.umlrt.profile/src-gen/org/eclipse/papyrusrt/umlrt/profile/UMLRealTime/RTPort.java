/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.umlrt.profile.UMLRealTime;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.uml2.uml.Port;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RT Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * isPublish = (base_Port.isService and not isWired)
 * if (base_Port.isService) then
 *    (base_Port.visibility = UML::VisibilityKind::public)
 * else
 *    (base_Port.visibility = UML::VisibilityKind::protected)
 * endif
 * (not isWired) implies (base_Port.isBehavior)
 * (isWired and not base_Port.isBehavior) implies (base_Port.isService)
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort#isNotification <em>Is Notification</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort#isPublish <em>Is Publish</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort#isWired <em>Is Wired</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort#getRegistration <em>Registration</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort#getRegistrationOverride <em>Registration Override</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort#getBase_Port <em>Base Port</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage#getRTPort()
 * @model
 * @generated
 */
public interface RTPort extends EObject {
	/**
	 * Returns the value of the '<em><b>Is Notification</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Notification</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Notification</em>' attribute.
	 * @see #setIsNotification(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage#getRTPort_IsNotification()
	 * @model default="false" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isNotification();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort#isNotification <em>Is Notification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Notification</em>' attribute.
	 * @see #isNotification()
	 * @generated
	 */
	void setIsNotification(boolean value);

	/**
	 * Returns the value of the '<em><b>Is Publish</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Publish</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Publish</em>' attribute.
	 * @see #setIsPublish(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage#getRTPort_IsPublish()
	 * @model default="false" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isPublish();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort#isPublish <em>Is Publish</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Publish</em>' attribute.
	 * @see #isPublish()
	 * @generated
	 */
	void setIsPublish(boolean value);

	/**
	 * Returns the value of the '<em><b>Is Wired</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Wired</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Wired</em>' attribute.
	 * @see #setIsWired(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage#getRTPort_IsWired()
	 * @model default="true" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isWired();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort#isWired <em>Is Wired</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Wired</em>' attribute.
	 * @see #isWired()
	 * @generated
	 */
	void setIsWired(boolean value);

	/**
	 * Returns the value of the '<em><b>Registration</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Registration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Registration</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType
	 * @see #setRegistration(PortRegistrationType)
	 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage#getRTPort_Registration()
	 * @model ordered="false"
	 * @generated
	 */
	PortRegistrationType getRegistration();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort#getRegistration <em>Registration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Registration</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType
	 * @see #getRegistration()
	 * @generated
	 */
	void setRegistration(PortRegistrationType value);

	/**
	 * Returns the value of the '<em><b>Registration Override</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Registration Override</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Registration Override</em>' attribute.
	 * @see #setRegistrationOverride(String)
	 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage#getRTPort_RegistrationOverride()
	 * @model default="" dataType="org.eclipse.uml2.types.String" required="true" ordered="false"
	 * @generated
	 */
	String getRegistrationOverride();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort#getRegistrationOverride <em>Registration Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Registration Override</em>' attribute.
	 * @see #getRegistrationOverride()
	 * @generated
	 */
	void setRegistrationOverride(String value);

	/**
	 * Returns the value of the '<em><b>Base Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Port</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Port</em>' reference.
	 * @see #setBase_Port(Port)
	 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage#getRTPort_Base_Port()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Port getBase_Port();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort#getBase_Port <em>Base Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Port</em>' reference.
	 * @see #getBase_Port()
	 * @generated
	 */
	void setBase_Port(Port value);

} // RTPort
