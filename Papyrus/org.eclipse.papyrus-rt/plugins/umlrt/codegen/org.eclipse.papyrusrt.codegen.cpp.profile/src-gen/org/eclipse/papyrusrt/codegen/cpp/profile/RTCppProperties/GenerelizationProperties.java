/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.uml2.uml.Generalization;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generelization Properties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerelizationProperties#isVirtual <em>Virtual</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerelizationProperties#getBase_Generalization <em>Base Generalization</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getGenerelizationProperties()
 * @model
 * @generated
 */
public interface GenerelizationProperties extends EObject {
	/**
	 * Returns the value of the '<em><b>Virtual</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Virtual</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Virtual</em>' attribute.
	 * @see #setVirtual(boolean)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getGenerelizationProperties_Virtual()
	 * @model default="false" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isVirtual();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerelizationProperties#isVirtual <em>Virtual</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Virtual</em>' attribute.
	 * @see #isVirtual()
	 * @generated
	 */
	void setVirtual(boolean value);

	/**
	 * Returns the value of the '<em><b>Base Generalization</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Generalization</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Generalization</em>' reference.
	 * @see #setBase_Generalization(Generalization)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getGenerelizationProperties_Base_Generalization()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Generalization getBase_Generalization();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerelizationProperties#getBase_Generalization <em>Base Generalization</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Generalization</em>' reference.
	 * @see #getBase_Generalization()
	 * @generated
	 */
	void setBase_Generalization(Generalization value);

} // GenerelizationProperties
