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
 * A representation of the model object '<em><b>Class Generation Properties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateStateMachine <em>Generate State Machine</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateAssignmentOperator <em>Generate Assignment Operator</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateCopyConstructor <em>Generate Copy Constructor</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateDefaultConstructor <em>Generate Default Constructor</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateDestructor <em>Generate Destructor</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateEqualityOperator <em>Generate Equality Operator</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateExtractionOperator <em>Generate Extraction Operator</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateInequalityOperator <em>Generate Inequality Operator</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateInsertionOperator <em>Generate Insertion Operator</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassGenerationProperties()
 * @model abstract="true"
 * @generated
 */
public interface ClassGenerationProperties extends EObject {
	/**
	 * Returns the value of the '<em><b>Generate State Machine</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate State Machine</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate State Machine</em>' attribute.
	 * @see #setGenerateStateMachine(boolean)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassGenerationProperties_GenerateStateMachine()
	 * @model default="true" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isGenerateStateMachine();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateStateMachine <em>Generate State Machine</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate State Machine</em>' attribute.
	 * @see #isGenerateStateMachine()
	 * @generated
	 */
	void setGenerateStateMachine(boolean value);

	/**
	 * Returns the value of the '<em><b>Generate Assignment Operator</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate Assignment Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate Assignment Operator</em>' attribute.
	 * @see #setGenerateAssignmentOperator(boolean)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassGenerationProperties_GenerateAssignmentOperator()
	 * @model default="true" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isGenerateAssignmentOperator();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateAssignmentOperator <em>Generate Assignment Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate Assignment Operator</em>' attribute.
	 * @see #isGenerateAssignmentOperator()
	 * @generated
	 */
	void setGenerateAssignmentOperator(boolean value);

	/**
	 * Returns the value of the '<em><b>Generate Copy Constructor</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate Copy Constructor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate Copy Constructor</em>' attribute.
	 * @see #setGenerateCopyConstructor(boolean)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassGenerationProperties_GenerateCopyConstructor()
	 * @model default="true" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isGenerateCopyConstructor();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateCopyConstructor <em>Generate Copy Constructor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate Copy Constructor</em>' attribute.
	 * @see #isGenerateCopyConstructor()
	 * @generated
	 */
	void setGenerateCopyConstructor(boolean value);

	/**
	 * Returns the value of the '<em><b>Generate Default Constructor</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate Default Constructor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate Default Constructor</em>' attribute.
	 * @see #setGenerateDefaultConstructor(boolean)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassGenerationProperties_GenerateDefaultConstructor()
	 * @model default="true" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isGenerateDefaultConstructor();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateDefaultConstructor <em>Generate Default Constructor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate Default Constructor</em>' attribute.
	 * @see #isGenerateDefaultConstructor()
	 * @generated
	 */
	void setGenerateDefaultConstructor(boolean value);

	/**
	 * Returns the value of the '<em><b>Generate Destructor</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate Destructor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate Destructor</em>' attribute.
	 * @see #setGenerateDestructor(boolean)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassGenerationProperties_GenerateDestructor()
	 * @model default="true" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isGenerateDestructor();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateDestructor <em>Generate Destructor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate Destructor</em>' attribute.
	 * @see #isGenerateDestructor()
	 * @generated
	 */
	void setGenerateDestructor(boolean value);

	/**
	 * Returns the value of the '<em><b>Generate Equality Operator</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate Equality Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate Equality Operator</em>' attribute.
	 * @see #setGenerateEqualityOperator(boolean)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassGenerationProperties_GenerateEqualityOperator()
	 * @model default="false" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isGenerateEqualityOperator();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateEqualityOperator <em>Generate Equality Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate Equality Operator</em>' attribute.
	 * @see #isGenerateEqualityOperator()
	 * @generated
	 */
	void setGenerateEqualityOperator(boolean value);

	/**
	 * Returns the value of the '<em><b>Generate Extraction Operator</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate Extraction Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate Extraction Operator</em>' attribute.
	 * @see #setGenerateExtractionOperator(boolean)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassGenerationProperties_GenerateExtractionOperator()
	 * @model default="false" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isGenerateExtractionOperator();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateExtractionOperator <em>Generate Extraction Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate Extraction Operator</em>' attribute.
	 * @see #isGenerateExtractionOperator()
	 * @generated
	 */
	void setGenerateExtractionOperator(boolean value);

	/**
	 * Returns the value of the '<em><b>Generate Inequality Operator</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate Inequality Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate Inequality Operator</em>' attribute.
	 * @see #setGenerateInequalityOperator(boolean)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassGenerationProperties_GenerateInequalityOperator()
	 * @model default="false" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isGenerateInequalityOperator();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateInequalityOperator <em>Generate Inequality Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate Inequality Operator</em>' attribute.
	 * @see #isGenerateInequalityOperator()
	 * @generated
	 */
	void setGenerateInequalityOperator(boolean value);

	/**
	 * Returns the value of the '<em><b>Generate Insertion Operator</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate Insertion Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate Insertion Operator</em>' attribute.
	 * @see #setGenerateInsertionOperator(boolean)
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#getClassGenerationProperties_GenerateInsertionOperator()
	 * @model default="false" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isGenerateInsertionOperator();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateInsertionOperator <em>Generate Insertion Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate Insertion Operator</em>' attribute.
	 * @see #isGenerateInsertionOperator()
	 * @generated
	 */
	void setGenerateInsertionOperator(boolean value);

} // ClassGenerationProperties
