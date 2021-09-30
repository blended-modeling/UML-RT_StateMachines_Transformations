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

import org.eclipse.uml2.uml.Dependency;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dependency Properties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties#getKindInHeader <em>Kind In Header</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties#getKindInImplementation <em>Kind In Implementation</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties#getBase_Dependency <em>Base Dependency</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getDependencyProperties()
 * @model
 * @generated
 */
public interface DependencyProperties extends EObject {
	/**
	 * Returns the value of the '<em><b>Kind In Header</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind In Header</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind In Header</em>' attribute.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind
	 * @see #setKindInHeader(DependencyKind)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getDependencyProperties_KindInHeader()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	DependencyKind getKindInHeader();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties#getKindInHeader <em>Kind In Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind In Header</em>' attribute.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind
	 * @see #getKindInHeader()
	 * @generated
	 */
	void setKindInHeader(DependencyKind value);

	/**
	 * Returns the value of the '<em><b>Kind In Implementation</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind In Implementation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind In Implementation</em>' attribute.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind
	 * @see #setKindInImplementation(DependencyKind)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getDependencyProperties_KindInImplementation()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	DependencyKind getKindInImplementation();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties#getKindInImplementation <em>Kind In Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind In Implementation</em>' attribute.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind
	 * @see #getKindInImplementation()
	 * @generated
	 */
	void setKindInImplementation(DependencyKind value);

	/**
	 * Returns the value of the '<em><b>Base Dependency</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Dependency</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Dependency</em>' reference.
	 * @see #setBase_Dependency(Dependency)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getDependencyProperties_Base_Dependency()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Dependency getBase_Dependency();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties#getBase_Dependency <em>Base Dependency</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Dependency</em>' reference.
	 * @see #getBase_Dependency()
	 * @generated
	 */
	void setBase_Dependency(Dependency value);

} // DependencyProperties
