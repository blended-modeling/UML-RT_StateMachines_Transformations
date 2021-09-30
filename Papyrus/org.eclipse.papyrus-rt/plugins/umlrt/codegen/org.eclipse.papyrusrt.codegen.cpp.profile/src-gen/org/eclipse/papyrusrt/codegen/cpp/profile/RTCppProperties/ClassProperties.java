/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class Properties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getBase_Class <em>Base Class</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getPrivateDeclarations <em>Private Declarations</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getProtectedDeclarations <em>Protected Declarations</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getPublicDeclarations <em>Public Declarations</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassProperties()
 * @model abstract="true"
 * @generated
 */
public interface ClassProperties extends CppFileProperties {
	/**
	 * Returns the value of the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Class</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Class</em>' reference.
	 * @see #setBase_Class(org.eclipse.uml2.uml.Class)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassProperties_Base_Class()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	org.eclipse.uml2.uml.Class getBase_Class();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getBase_Class <em>Base Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Class</em>' reference.
	 * @see #getBase_Class()
	 * @generated
	 */
	void setBase_Class(org.eclipse.uml2.uml.Class value);

	/**
	 * Returns the value of the '<em><b>Private Declarations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Private Declarations</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Private Declarations</em>' attribute.
	 * @see #setPrivateDeclarations(String)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassProperties_PrivateDeclarations()
	 * @model dataType="org.eclipse.uml2.types.String" ordered="false"
	 * @generated
	 */
	String getPrivateDeclarations();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getPrivateDeclarations <em>Private Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Private Declarations</em>' attribute.
	 * @see #getPrivateDeclarations()
	 * @generated
	 */
	void setPrivateDeclarations(String value);

	/**
	 * Returns the value of the '<em><b>Protected Declarations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protected Declarations</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protected Declarations</em>' attribute.
	 * @see #setProtectedDeclarations(String)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassProperties_ProtectedDeclarations()
	 * @model dataType="org.eclipse.uml2.types.String" ordered="false"
	 * @generated
	 */
	String getProtectedDeclarations();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getProtectedDeclarations <em>Protected Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protected Declarations</em>' attribute.
	 * @see #getProtectedDeclarations()
	 * @generated
	 */
	void setProtectedDeclarations(String value);

	/**
	 * Returns the value of the '<em><b>Public Declarations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Public Declarations</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Public Declarations</em>' attribute.
	 * @see #setPublicDeclarations(String)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassProperties_PublicDeclarations()
	 * @model dataType="org.eclipse.uml2.types.String" ordered="false"
	 * @generated
	 */
	String getPublicDeclarations();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getPublicDeclarations <em>Public Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Public Declarations</em>' attribute.
	 * @see #getPublicDeclarations()
	 * @generated
	 */
	void setPublicDeclarations(String value);

} // ClassProperties
