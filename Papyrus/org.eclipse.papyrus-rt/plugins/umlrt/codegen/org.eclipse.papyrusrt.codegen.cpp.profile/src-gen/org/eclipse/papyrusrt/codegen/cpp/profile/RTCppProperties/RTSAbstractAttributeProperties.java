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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RTS Abstract Attribute Properties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties#isGenerateTypeModifier <em>Generate Type Modifier</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties#getNumElementsFunctionBody <em>Num Elements Function Body</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties#getTypeDescriptor <em>Type Descriptor</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getRTSAbstractAttributeProperties()
 * @model
 * @generated
 */
public interface RTSAbstractAttributeProperties extends EObject {
	/**
	 * Returns the value of the '<em><b>Generate Type Modifier</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate Type Modifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate Type Modifier</em>' attribute.
	 * @see #setGenerateTypeModifier(boolean)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getRTSAbstractAttributeProperties_GenerateTypeModifier()
	 * @model default="true" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isGenerateTypeModifier();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties#isGenerateTypeModifier <em>Generate Type Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate Type Modifier</em>' attribute.
	 * @see #isGenerateTypeModifier()
	 * @generated
	 */
	void setGenerateTypeModifier(boolean value);

	/**
	 * Returns the value of the '<em><b>Num Elements Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Num Elements Function Body</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Num Elements Function Body</em>' attribute.
	 * @see #setNumElementsFunctionBody(String)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getRTSAbstractAttributeProperties_NumElementsFunctionBody()
	 * @model dataType="org.eclipse.uml2.types.String" required="true" ordered="false"
	 * @generated
	 */
	String getNumElementsFunctionBody();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties#getNumElementsFunctionBody <em>Num Elements Function Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Num Elements Function Body</em>' attribute.
	 * @see #getNumElementsFunctionBody()
	 * @generated
	 */
	void setNumElementsFunctionBody(String value);

	/**
	 * Returns the value of the '<em><b>Type Descriptor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Descriptor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Descriptor</em>' attribute.
	 * @see #setTypeDescriptor(String)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getRTSAbstractAttributeProperties_TypeDescriptor()
	 * @model dataType="org.eclipse.uml2.types.String" required="true" ordered="false"
	 * @generated
	 */
	String getTypeDescriptor();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties#getTypeDescriptor <em>Type Descriptor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Descriptor</em>' attribute.
	 * @see #getTypeDescriptor()
	 * @generated
	 */
	void setTypeDescriptor(String value);

} // RTSAbstractAttributeProperties
