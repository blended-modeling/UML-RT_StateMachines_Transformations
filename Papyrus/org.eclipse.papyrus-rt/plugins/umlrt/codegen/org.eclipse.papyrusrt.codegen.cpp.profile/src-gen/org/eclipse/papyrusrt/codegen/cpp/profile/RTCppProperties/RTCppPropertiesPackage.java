/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * RTCapsule stereotype not applied
 * <!-- end-model-doc -->
 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesFactory
 * @model kind="package"
 * @generated
 */
public interface RTCppPropertiesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "RTCppProperties";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/umlrt/cppproperties";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "RTCppProperties";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RTCppPropertiesPackage eINSTANCE = org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassGenerationPropertiesImpl <em>Class Generation Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassGenerationPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getClassGenerationProperties()
	 * @generated
	 */
	int CLASS_GENERATION_PROPERTIES = 5;

	/**
	 * The feature id for the '<em><b>Generate State Machine</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_GENERATION_PROPERTIES__GENERATE_STATE_MACHINE = 0;

	/**
	 * The feature id for the '<em><b>Generate Assignment Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_GENERATION_PROPERTIES__GENERATE_ASSIGNMENT_OPERATOR = 1;

	/**
	 * The feature id for the '<em><b>Generate Copy Constructor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_GENERATION_PROPERTIES__GENERATE_COPY_CONSTRUCTOR = 2;

	/**
	 * The feature id for the '<em><b>Generate Default Constructor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_GENERATION_PROPERTIES__GENERATE_DEFAULT_CONSTRUCTOR = 3;

	/**
	 * The feature id for the '<em><b>Generate Destructor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_GENERATION_PROPERTIES__GENERATE_DESTRUCTOR = 4;

	/**
	 * The feature id for the '<em><b>Generate Equality Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_GENERATION_PROPERTIES__GENERATE_EQUALITY_OPERATOR = 5;

	/**
	 * The feature id for the '<em><b>Generate Extraction Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_GENERATION_PROPERTIES__GENERATE_EXTRACTION_OPERATOR = 6;

	/**
	 * The feature id for the '<em><b>Generate Inequality Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_GENERATION_PROPERTIES__GENERATE_INEQUALITY_OPERATOR = 7;

	/**
	 * The feature id for the '<em><b>Generate Insertion Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_GENERATION_PROPERTIES__GENERATE_INSERTION_OPERATOR = 8;

	/**
	 * The number of structural features of the '<em>Class Generation Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_GENERATION_PROPERTIES_FEATURE_COUNT = 9;

	/**
	 * The number of operations of the '<em>Class Generation Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_GENERATION_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl <em>Passive Class Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getPassiveClassProperties()
	 * @generated
	 */
	int PASSIVE_CLASS_PROPERTIES = 0;

	/**
	 * The feature id for the '<em><b>Generate State Machine</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__GENERATE_STATE_MACHINE = CLASS_GENERATION_PROPERTIES__GENERATE_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Generate Assignment Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__GENERATE_ASSIGNMENT_OPERATOR = CLASS_GENERATION_PROPERTIES__GENERATE_ASSIGNMENT_OPERATOR;

	/**
	 * The feature id for the '<em><b>Generate Copy Constructor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__GENERATE_COPY_CONSTRUCTOR = CLASS_GENERATION_PROPERTIES__GENERATE_COPY_CONSTRUCTOR;

	/**
	 * The feature id for the '<em><b>Generate Default Constructor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__GENERATE_DEFAULT_CONSTRUCTOR = CLASS_GENERATION_PROPERTIES__GENERATE_DEFAULT_CONSTRUCTOR;

	/**
	 * The feature id for the '<em><b>Generate Destructor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__GENERATE_DESTRUCTOR = CLASS_GENERATION_PROPERTIES__GENERATE_DESTRUCTOR;

	/**
	 * The feature id for the '<em><b>Generate Equality Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__GENERATE_EQUALITY_OPERATOR = CLASS_GENERATION_PROPERTIES__GENERATE_EQUALITY_OPERATOR;

	/**
	 * The feature id for the '<em><b>Generate Extraction Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__GENERATE_EXTRACTION_OPERATOR = CLASS_GENERATION_PROPERTIES__GENERATE_EXTRACTION_OPERATOR;

	/**
	 * The feature id for the '<em><b>Generate Inequality Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__GENERATE_INEQUALITY_OPERATOR = CLASS_GENERATION_PROPERTIES__GENERATE_INEQUALITY_OPERATOR;

	/**
	 * The feature id for the '<em><b>Generate Insertion Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__GENERATE_INSERTION_OPERATOR = CLASS_GENERATION_PROPERTIES__GENERATE_INSERTION_OPERATOR;

	/**
	 * The feature id for the '<em><b>Header Preface</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__HEADER_PREFACE = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Header Ending</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__HEADER_ENDING = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Implementation Preface</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_PREFACE = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Implementation Ending</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_ENDING = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__BASE_CLASS = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Private Declarations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__PRIVATE_DECLARATIONS = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Protected Declarations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__PROTECTED_DECLARATIONS = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Public Declarations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__PUBLIC_DECLARATIONS = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Generate Header</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__GENERATE_HEADER = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Generate Implementation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__GENERATE_IMPLEMENTATION = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Generate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__GENERATE = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__KIND = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Implementation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_TYPE = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 12;

	/**
	 * The number of structural features of the '<em>Passive Class Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES_FEATURE_COUNT = CLASS_GENERATION_PROPERTIES_FEATURE_COUNT + 13;

	/**
	 * The number of operations of the '<em>Passive Class Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_CLASS_PROPERTIES_OPERATION_COUNT = CLASS_GENERATION_PROPERTIES_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CppFilePropertiesImpl <em>Cpp File Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CppFilePropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getCppFileProperties()
	 * @generated
	 */
	int CPP_FILE_PROPERTIES = 2;

	/**
	 * The feature id for the '<em><b>Header Preface</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPP_FILE_PROPERTIES__HEADER_PREFACE = 0;

	/**
	 * The feature id for the '<em><b>Header Ending</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPP_FILE_PROPERTIES__HEADER_ENDING = 1;

	/**
	 * The feature id for the '<em><b>Implementation Preface</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPP_FILE_PROPERTIES__IMPLEMENTATION_PREFACE = 2;

	/**
	 * The feature id for the '<em><b>Implementation Ending</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPP_FILE_PROPERTIES__IMPLEMENTATION_ENDING = 3;

	/**
	 * The number of structural features of the '<em>Cpp File Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPP_FILE_PROPERTIES_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Cpp File Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPP_FILE_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassPropertiesImpl <em>Class Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getClassProperties()
	 * @generated
	 */
	int CLASS_PROPERTIES = 1;

	/**
	 * The feature id for the '<em><b>Header Preface</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PROPERTIES__HEADER_PREFACE = CPP_FILE_PROPERTIES__HEADER_PREFACE;

	/**
	 * The feature id for the '<em><b>Header Ending</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PROPERTIES__HEADER_ENDING = CPP_FILE_PROPERTIES__HEADER_ENDING;

	/**
	 * The feature id for the '<em><b>Implementation Preface</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PROPERTIES__IMPLEMENTATION_PREFACE = CPP_FILE_PROPERTIES__IMPLEMENTATION_PREFACE;

	/**
	 * The feature id for the '<em><b>Implementation Ending</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PROPERTIES__IMPLEMENTATION_ENDING = CPP_FILE_PROPERTIES__IMPLEMENTATION_ENDING;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PROPERTIES__BASE_CLASS = CPP_FILE_PROPERTIES_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Private Declarations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PROPERTIES__PRIVATE_DECLARATIONS = CPP_FILE_PROPERTIES_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Protected Declarations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PROPERTIES__PROTECTED_DECLARATIONS = CPP_FILE_PROPERTIES_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Public Declarations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PROPERTIES__PUBLIC_DECLARATIONS = CPP_FILE_PROPERTIES_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Class Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PROPERTIES_FEATURE_COUNT = CPP_FILE_PROPERTIES_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Class Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PROPERTIES_OPERATION_COUNT = CPP_FILE_PROPERTIES_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.GenerationPropertiesImpl <em>Generation Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.GenerationPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getGenerationProperties()
	 * @generated
	 */
	int GENERATION_PROPERTIES = 3;

	/**
	 * The feature id for the '<em><b>Generate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATION_PROPERTIES__GENERATE = 0;

	/**
	 * The number of structural features of the '<em>Generation Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATION_PROPERTIES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Generation Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATION_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.FileGenerationPropertiesImpl <em>File Generation Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.FileGenerationPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getFileGenerationProperties()
	 * @generated
	 */
	int FILE_GENERATION_PROPERTIES = 4;

	/**
	 * The feature id for the '<em><b>Generate Header</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_GENERATION_PROPERTIES__GENERATE_HEADER = 0;

	/**
	 * The feature id for the '<em><b>Generate Implementation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_GENERATION_PROPERTIES__GENERATE_IMPLEMENTATION = 1;

	/**
	 * The number of structural features of the '<em>File Generation Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_GENERATION_PROPERTIES_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>File Generation Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_GENERATION_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.AttributePropertiesImpl <em>Attribute Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.AttributePropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getAttributeProperties()
	 * @generated
	 */
	int ATTRIBUTE_PROPERTIES = 6;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_PROPERTIES__KIND = 0;

	/**
	 * The feature id for the '<em><b>Volatile</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_PROPERTIES__VOLATILE = 1;

	/**
	 * The feature id for the '<em><b>Initialization</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_PROPERTIES__INITIALIZATION = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_PROPERTIES__TYPE = 3;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_PROPERTIES__SIZE = 4;

	/**
	 * The feature id for the '<em><b>Points To Const Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_PROPERTIES__POINTS_TO_CONST_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Points To Volatile Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_PROPERTIES__POINTS_TO_VOLATILE_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Points To Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_PROPERTIES__POINTS_TO_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Base Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_PROPERTIES__BASE_PROPERTY = 8;

	/**
	 * The number of structural features of the '<em>Attribute Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_PROPERTIES_FEATURE_COUNT = 9;

	/**
	 * The number of operations of the '<em>Attribute Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CapsulePropertiesImpl <em>Capsule Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CapsulePropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getCapsuleProperties()
	 * @generated
	 */
	int CAPSULE_PROPERTIES = 7;

	/**
	 * The feature id for the '<em><b>Header Preface</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PROPERTIES__HEADER_PREFACE = CLASS_PROPERTIES__HEADER_PREFACE;

	/**
	 * The feature id for the '<em><b>Header Ending</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PROPERTIES__HEADER_ENDING = CLASS_PROPERTIES__HEADER_ENDING;

	/**
	 * The feature id for the '<em><b>Implementation Preface</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PROPERTIES__IMPLEMENTATION_PREFACE = CLASS_PROPERTIES__IMPLEMENTATION_PREFACE;

	/**
	 * The feature id for the '<em><b>Implementation Ending</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PROPERTIES__IMPLEMENTATION_ENDING = CLASS_PROPERTIES__IMPLEMENTATION_ENDING;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PROPERTIES__BASE_CLASS = CLASS_PROPERTIES__BASE_CLASS;

	/**
	 * The feature id for the '<em><b>Private Declarations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PROPERTIES__PRIVATE_DECLARATIONS = CLASS_PROPERTIES__PRIVATE_DECLARATIONS;

	/**
	 * The feature id for the '<em><b>Protected Declarations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PROPERTIES__PROTECTED_DECLARATIONS = CLASS_PROPERTIES__PROTECTED_DECLARATIONS;

	/**
	 * The feature id for the '<em><b>Public Declarations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PROPERTIES__PUBLIC_DECLARATIONS = CLASS_PROPERTIES__PUBLIC_DECLARATIONS;

	/**
	 * The feature id for the '<em><b>Generate Header</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PROPERTIES__GENERATE_HEADER = CLASS_PROPERTIES_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Generate Implementation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PROPERTIES__GENERATE_IMPLEMENTATION = CLASS_PROPERTIES_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Capsule Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PROPERTIES_FEATURE_COUNT = CLASS_PROPERTIES_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Capsule Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PROPERTIES_OPERATION_COUNT = CLASS_PROPERTIES_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.DependencyPropertiesImpl <em>Dependency Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.DependencyPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getDependencyProperties()
	 * @generated
	 */
	int DEPENDENCY_PROPERTIES = 8;

	/**
	 * The feature id for the '<em><b>Kind In Header</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_PROPERTIES__KIND_IN_HEADER = 0;

	/**
	 * The feature id for the '<em><b>Kind In Implementation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_PROPERTIES__KIND_IN_IMPLEMENTATION = 1;

	/**
	 * The feature id for the '<em><b>Base Dependency</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_PROPERTIES__BASE_DEPENDENCY = 2;

	/**
	 * The number of structural features of the '<em>Dependency Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_PROPERTIES_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Dependency Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.EnumerationPropertiesImpl <em>Enumeration Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.EnumerationPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getEnumerationProperties()
	 * @generated
	 */
	int ENUMERATION_PROPERTIES = 9;

	/**
	 * The feature id for the '<em><b>Header Preface</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_PROPERTIES__HEADER_PREFACE = CPP_FILE_PROPERTIES__HEADER_PREFACE;

	/**
	 * The feature id for the '<em><b>Header Ending</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_PROPERTIES__HEADER_ENDING = CPP_FILE_PROPERTIES__HEADER_ENDING;

	/**
	 * The feature id for the '<em><b>Implementation Preface</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_PROPERTIES__IMPLEMENTATION_PREFACE = CPP_FILE_PROPERTIES__IMPLEMENTATION_PREFACE;

	/**
	 * The feature id for the '<em><b>Implementation Ending</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_PROPERTIES__IMPLEMENTATION_ENDING = CPP_FILE_PROPERTIES__IMPLEMENTATION_ENDING;

	/**
	 * The feature id for the '<em><b>Generate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_PROPERTIES__GENERATE = CPP_FILE_PROPERTIES_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Base Enumeration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_PROPERTIES__BASE_ENUMERATION = CPP_FILE_PROPERTIES_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Enumeration Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_PROPERTIES_FEATURE_COUNT = CPP_FILE_PROPERTIES_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Enumeration Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_PROPERTIES_OPERATION_COUNT = CPP_FILE_PROPERTIES_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.OperationPropertiesImpl <em>Operation Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.OperationPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getOperationProperties()
	 * @generated
	 */
	int OPERATION_PROPERTIES = 10;

	/**
	 * The feature id for the '<em><b>Inline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_PROPERTIES__INLINE = 0;

	/**
	 * The feature id for the '<em><b>Polymorphic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_PROPERTIES__POLYMORPHIC = 1;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_PROPERTIES__KIND = 2;

	/**
	 * The feature id for the '<em><b>Generate Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_PROPERTIES__GENERATE_DEFINITION = 3;

	/**
	 * The feature id for the '<em><b>Base Operation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_PROPERTIES__BASE_OPERATION = 4;

	/**
	 * The number of structural features of the '<em>Operation Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_PROPERTIES_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Operation Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.TypePropertiesImpl <em>Type Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.TypePropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getTypeProperties()
	 * @generated
	 */
	int TYPE_PROPERTIES = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_PROPERTIES__NAME = 0;

	/**
	 * The feature id for the '<em><b>Definition File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_PROPERTIES__DEFINITION_FILE = 1;

	/**
	 * The number of structural features of the '<em>Type Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_PROPERTIES_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Type Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ParameterPropertiesImpl <em>Parameter Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ParameterPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getParameterProperties()
	 * @generated
	 */
	int PARAMETER_PROPERTIES = 12;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_PROPERTIES__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Base Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_PROPERTIES__BASE_PARAMETER = 1;

	/**
	 * The feature id for the '<em><b>Points To Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_PROPERTIES__POINTS_TO_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Points To Const</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_PROPERTIES__POINTS_TO_CONST = 3;

	/**
	 * The feature id for the '<em><b>Points To Volatile</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_PROPERTIES__POINTS_TO_VOLATILE = 4;

	/**
	 * The number of structural features of the '<em>Parameter Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_PROPERTIES_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Parameter Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.GenerelizationPropertiesImpl <em>Generelization Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.GenerelizationPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getGenerelizationProperties()
	 * @generated
	 */
	int GENERELIZATION_PROPERTIES = 13;

	/**
	 * The feature id for the '<em><b>Virtual</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERELIZATION_PROPERTIES__VIRTUAL = 0;

	/**
	 * The feature id for the '<em><b>Base Generalization</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERELIZATION_PROPERTIES__BASE_GENERALIZATION = 1;

	/**
	 * The number of structural features of the '<em>Generelization Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERELIZATION_PROPERTIES_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Generelization Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERELIZATION_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSAbstractAttributePropertiesImpl <em>RTS Abstract Attribute Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSAbstractAttributePropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSAbstractAttributeProperties()
	 * @generated
	 */
	int RTS_ABSTRACT_ATTRIBUTE_PROPERTIES = 15;

	/**
	 * The feature id for the '<em><b>Generate Type Modifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__GENERATE_TYPE_MODIFIER = 0;

	/**
	 * The feature id for the '<em><b>Num Elements Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__NUM_ELEMENTS_FUNCTION_BODY = 1;

	/**
	 * The feature id for the '<em><b>Type Descriptor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__TYPE_DESCRIPTOR = 2;

	/**
	 * The number of structural features of the '<em>RTS Abstract Attribute Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ABSTRACT_ATTRIBUTE_PROPERTIES_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>RTS Abstract Attribute Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ABSTRACT_ATTRIBUTE_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSAttributePropertiesImpl <em>RTS Attribute Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSAttributePropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSAttributeProperties()
	 * @generated
	 */
	int RTS_ATTRIBUTE_PROPERTIES = 14;

	/**
	 * The feature id for the '<em><b>Generate Type Modifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ATTRIBUTE_PROPERTIES__GENERATE_TYPE_MODIFIER = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__GENERATE_TYPE_MODIFIER;

	/**
	 * The feature id for the '<em><b>Num Elements Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ATTRIBUTE_PROPERTIES__NUM_ELEMENTS_FUNCTION_BODY = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__NUM_ELEMENTS_FUNCTION_BODY;

	/**
	 * The feature id for the '<em><b>Type Descriptor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ATTRIBUTE_PROPERTIES__TYPE_DESCRIPTOR = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__TYPE_DESCRIPTOR;

	/**
	 * The feature id for the '<em><b>Generate Descriptor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ATTRIBUTE_PROPERTIES__GENERATE_DESCRIPTOR = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Base Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ATTRIBUTE_PROPERTIES__BASE_PROPERTY = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>RTS Attribute Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ATTRIBUTE_PROPERTIES_FEATURE_COUNT = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>RTS Attribute Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ATTRIBUTE_PROPERTIES_OPERATION_COUNT = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSDescriptorPropertiesImpl <em>RTS Descriptor Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSDescriptorPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSDescriptorProperties()
	 * @generated
	 */
	int RTS_DESCRIPTOR_PROPERTIES = 16;

	/**
	 * The feature id for the '<em><b>Generate Descriptor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR = 0;

	/**
	 * The number of structural features of the '<em>RTS Descriptor Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_DESCRIPTOR_PROPERTIES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>RTS Descriptor Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_DESCRIPTOR_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassifierPropertiesImpl <em>RTS Classifier Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassifierPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSClassifierProperties()
	 * @generated
	 */
	int RTS_CLASSIFIER_PROPERTIES = 18;

	/**
	 * The feature id for the '<em><b>Copy Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASSIFIER_PROPERTIES__COPY_FUNCTION_BODY = 0;

	/**
	 * The feature id for the '<em><b>Decode Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASSIFIER_PROPERTIES__DECODE_FUNCTION_BODY = 1;

	/**
	 * The feature id for the '<em><b>Destroy Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASSIFIER_PROPERTIES__DESTROY_FUNCTION_BODY = 2;

	/**
	 * The feature id for the '<em><b>Encode Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASSIFIER_PROPERTIES__ENCODE_FUNCTION_BODY = 3;

	/**
	 * The feature id for the '<em><b>Init Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASSIFIER_PROPERTIES__INIT_FUNCTION_BODY = 4;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASSIFIER_PROPERTIES__VERSION = 5;

	/**
	 * The number of structural features of the '<em>RTS Classifier Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASSIFIER_PROPERTIES_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>RTS Classifier Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASSIFIER_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassPropertiesImpl <em>RTS Class Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSClassProperties()
	 * @generated
	 */
	int RTS_CLASS_PROPERTIES = 17;

	/**
	 * The feature id for the '<em><b>Copy Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASS_PROPERTIES__COPY_FUNCTION_BODY = RTS_CLASSIFIER_PROPERTIES__COPY_FUNCTION_BODY;

	/**
	 * The feature id for the '<em><b>Decode Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASS_PROPERTIES__DECODE_FUNCTION_BODY = RTS_CLASSIFIER_PROPERTIES__DECODE_FUNCTION_BODY;

	/**
	 * The feature id for the '<em><b>Destroy Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASS_PROPERTIES__DESTROY_FUNCTION_BODY = RTS_CLASSIFIER_PROPERTIES__DESTROY_FUNCTION_BODY;

	/**
	 * The feature id for the '<em><b>Encode Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASS_PROPERTIES__ENCODE_FUNCTION_BODY = RTS_CLASSIFIER_PROPERTIES__ENCODE_FUNCTION_BODY;

	/**
	 * The feature id for the '<em><b>Init Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASS_PROPERTIES__INIT_FUNCTION_BODY = RTS_CLASSIFIER_PROPERTIES__INIT_FUNCTION_BODY;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASS_PROPERTIES__VERSION = RTS_CLASSIFIER_PROPERTIES__VERSION;

	/**
	 * The feature id for the '<em><b>Generate Descriptor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASS_PROPERTIES__GENERATE_DESCRIPTOR = RTS_CLASSIFIER_PROPERTIES_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Descriptor Hint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASS_PROPERTIES__TYPE_DESCRIPTOR_HINT = RTS_CLASSIFIER_PROPERTIES_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASS_PROPERTIES__BASE_CLASS = RTS_CLASSIFIER_PROPERTIES_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>RTS Class Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASS_PROPERTIES_FEATURE_COUNT = RTS_CLASSIFIER_PROPERTIES_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>RTS Class Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_CLASS_PROPERTIES_OPERATION_COUNT = RTS_CLASSIFIER_PROPERTIES_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSEnumerationPropertiesImpl <em>RTS Enumeration Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSEnumerationPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSEnumerationProperties()
	 * @generated
	 */
	int RTS_ENUMERATION_PROPERTIES = 19;

	/**
	 * The feature id for the '<em><b>Copy Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_PROPERTIES__COPY_FUNCTION_BODY = RTS_CLASSIFIER_PROPERTIES__COPY_FUNCTION_BODY;

	/**
	 * The feature id for the '<em><b>Decode Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_PROPERTIES__DECODE_FUNCTION_BODY = RTS_CLASSIFIER_PROPERTIES__DECODE_FUNCTION_BODY;

	/**
	 * The feature id for the '<em><b>Destroy Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_PROPERTIES__DESTROY_FUNCTION_BODY = RTS_CLASSIFIER_PROPERTIES__DESTROY_FUNCTION_BODY;

	/**
	 * The feature id for the '<em><b>Encode Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_PROPERTIES__ENCODE_FUNCTION_BODY = RTS_CLASSIFIER_PROPERTIES__ENCODE_FUNCTION_BODY;

	/**
	 * The feature id for the '<em><b>Init Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_PROPERTIES__INIT_FUNCTION_BODY = RTS_CLASSIFIER_PROPERTIES__INIT_FUNCTION_BODY;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_PROPERTIES__VERSION = RTS_CLASSIFIER_PROPERTIES__VERSION;

	/**
	 * The feature id for the '<em><b>Generate Descriptor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_PROPERTIES__GENERATE_DESCRIPTOR = RTS_CLASSIFIER_PROPERTIES_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Base Enumeration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_PROPERTIES__BASE_ENUMERATION = RTS_CLASSIFIER_PROPERTIES_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>RTS Enumeration Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_PROPERTIES_FEATURE_COUNT = RTS_CLASSIFIER_PROPERTIES_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>RTS Enumeration Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_PROPERTIES_OPERATION_COUNT = RTS_CLASSIFIER_PROPERTIES_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSEnumerationLiteralPropertiesImpl <em>RTS Enumeration Literal Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSEnumerationLiteralPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSEnumerationLiteralProperties()
	 * @generated
	 */
	int RTS_ENUMERATION_LITERAL_PROPERTIES = 20;

	/**
	 * The feature id for the '<em><b>Generate Type Modifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_LITERAL_PROPERTIES__GENERATE_TYPE_MODIFIER = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__GENERATE_TYPE_MODIFIER;

	/**
	 * The feature id for the '<em><b>Num Elements Function Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_LITERAL_PROPERTIES__NUM_ELEMENTS_FUNCTION_BODY = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__NUM_ELEMENTS_FUNCTION_BODY;

	/**
	 * The feature id for the '<em><b>Type Descriptor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_LITERAL_PROPERTIES__TYPE_DESCRIPTOR = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__TYPE_DESCRIPTOR;

	/**
	 * The feature id for the '<em><b>Generate Descriptor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_LITERAL_PROPERTIES__GENERATE_DESCRIPTOR = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Base Enumeration Literal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_LITERAL_PROPERTIES__BASE_ENUMERATION_LITERAL = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>RTS Enumeration Literal Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_LITERAL_PROPERTIES_FEATURE_COUNT = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>RTS Enumeration Literal Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RTS_ENUMERATION_LITERAL_PROPERTIES_OPERATION_COUNT = RTS_ABSTRACT_ATTRIBUTE_PROPERTIES_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ArtifactPropertiesImpl <em>Artifact Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ArtifactPropertiesImpl
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getArtifactProperties()
	 * @generated
	 */
	int ARTIFACT_PROPERTIES = 21;

	/**
	 * The feature id for the '<em><b>Base Artifact</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_PROPERTIES__BASE_ARTIFACT = 0;

	/**
	 * The feature id for the '<em><b>Include File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_PROPERTIES__INCLUDE_FILE = 1;

	/**
	 * The feature id for the '<em><b>Source File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_PROPERTIES__SOURCE_FILE = 2;

	/**
	 * The number of structural features of the '<em>Artifact Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_PROPERTIES_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Artifact Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassKind <em>Class Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassKind
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getClassKind()
	 * @generated
	 */
	int CLASS_KIND = 22;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeKind <em>Attribute Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeKind
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getAttributeKind()
	 * @generated
	 */
	int ATTRIBUTE_KIND = 23;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.InitializationKind <em>Initialization Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.InitializationKind
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getInitializationKind()
	 * @generated
	 */
	int INITIALIZATION_KIND = 24;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind <em>Dependency Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getDependencyKind()
	 * @generated
	 */
	int DEPENDENCY_KIND = 25;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationKind <em>Operation Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationKind
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getOperationKind()
	 * @generated
	 */
	int OPERATION_KIND = 26;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.PassiveClassProperties <em>Passive Class Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Passive Class Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.PassiveClassProperties
	 * @generated
	 */
	EClass getPassiveClassProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.PassiveClassProperties#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.PassiveClassProperties#getKind()
	 * @see #getPassiveClassProperties()
	 * @generated
	 */
	EAttribute getPassiveClassProperties_Kind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.PassiveClassProperties#getImplementationType <em>Implementation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation Type</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.PassiveClassProperties#getImplementationType()
	 * @see #getPassiveClassProperties()
	 * @generated
	 */
	EAttribute getPassiveClassProperties_ImplementationType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties <em>Class Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties
	 * @generated
	 */
	EClass getClassProperties();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getBase_Class <em>Base Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Class</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getBase_Class()
	 * @see #getClassProperties()
	 * @generated
	 */
	EReference getClassProperties_Base_Class();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getPrivateDeclarations <em>Private Declarations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Private Declarations</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getPrivateDeclarations()
	 * @see #getClassProperties()
	 * @generated
	 */
	EAttribute getClassProperties_PrivateDeclarations();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getProtectedDeclarations <em>Protected Declarations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Protected Declarations</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getProtectedDeclarations()
	 * @see #getClassProperties()
	 * @generated
	 */
	EAttribute getClassProperties_ProtectedDeclarations();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getPublicDeclarations <em>Public Declarations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Public Declarations</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties#getPublicDeclarations()
	 * @see #getClassProperties()
	 * @generated
	 */
	EAttribute getClassProperties_PublicDeclarations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties <em>Cpp File Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cpp File Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties
	 * @generated
	 */
	EClass getCppFileProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties#getHeaderPreface <em>Header Preface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header Preface</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties#getHeaderPreface()
	 * @see #getCppFileProperties()
	 * @generated
	 */
	EAttribute getCppFileProperties_HeaderPreface();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties#getHeaderEnding <em>Header Ending</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header Ending</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties#getHeaderEnding()
	 * @see #getCppFileProperties()
	 * @generated
	 */
	EAttribute getCppFileProperties_HeaderEnding();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties#getImplementationPreface <em>Implementation Preface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation Preface</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties#getImplementationPreface()
	 * @see #getCppFileProperties()
	 * @generated
	 */
	EAttribute getCppFileProperties_ImplementationPreface();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties#getImplementationEnding <em>Implementation Ending</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation Ending</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties#getImplementationEnding()
	 * @see #getCppFileProperties()
	 * @generated
	 */
	EAttribute getCppFileProperties_ImplementationEnding();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerationProperties <em>Generation Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generation Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerationProperties
	 * @generated
	 */
	EClass getGenerationProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerationProperties#isGenerate <em>Generate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerationProperties#isGenerate()
	 * @see #getGenerationProperties()
	 * @generated
	 */
	EAttribute getGenerationProperties_Generate();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.FileGenerationProperties <em>File Generation Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File Generation Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.FileGenerationProperties
	 * @generated
	 */
	EClass getFileGenerationProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.FileGenerationProperties#isGenerateHeader <em>Generate Header</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Header</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.FileGenerationProperties#isGenerateHeader()
	 * @see #getFileGenerationProperties()
	 * @generated
	 */
	EAttribute getFileGenerationProperties_GenerateHeader();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.FileGenerationProperties#isGenerateImplementation <em>Generate Implementation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Implementation</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.FileGenerationProperties#isGenerateImplementation()
	 * @see #getFileGenerationProperties()
	 * @generated
	 */
	EAttribute getFileGenerationProperties_GenerateImplementation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties <em>Class Generation Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class Generation Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties
	 * @generated
	 */
	EClass getClassGenerationProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateStateMachine <em>Generate State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate State Machine</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateStateMachine()
	 * @see #getClassGenerationProperties()
	 * @generated
	 */
	EAttribute getClassGenerationProperties_GenerateStateMachine();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateAssignmentOperator <em>Generate Assignment Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Assignment Operator</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateAssignmentOperator()
	 * @see #getClassGenerationProperties()
	 * @generated
	 */
	EAttribute getClassGenerationProperties_GenerateAssignmentOperator();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateCopyConstructor <em>Generate Copy Constructor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Copy Constructor</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateCopyConstructor()
	 * @see #getClassGenerationProperties()
	 * @generated
	 */
	EAttribute getClassGenerationProperties_GenerateCopyConstructor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateDefaultConstructor <em>Generate Default Constructor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Default Constructor</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateDefaultConstructor()
	 * @see #getClassGenerationProperties()
	 * @generated
	 */
	EAttribute getClassGenerationProperties_GenerateDefaultConstructor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateDestructor <em>Generate Destructor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Destructor</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateDestructor()
	 * @see #getClassGenerationProperties()
	 * @generated
	 */
	EAttribute getClassGenerationProperties_GenerateDestructor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateEqualityOperator <em>Generate Equality Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Equality Operator</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateEqualityOperator()
	 * @see #getClassGenerationProperties()
	 * @generated
	 */
	EAttribute getClassGenerationProperties_GenerateEqualityOperator();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateExtractionOperator <em>Generate Extraction Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Extraction Operator</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateExtractionOperator()
	 * @see #getClassGenerationProperties()
	 * @generated
	 */
	EAttribute getClassGenerationProperties_GenerateExtractionOperator();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateInequalityOperator <em>Generate Inequality Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Inequality Operator</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateInequalityOperator()
	 * @see #getClassGenerationProperties()
	 * @generated
	 */
	EAttribute getClassGenerationProperties_GenerateInequalityOperator();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateInsertionOperator <em>Generate Insertion Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Insertion Operator</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties#isGenerateInsertionOperator()
	 * @see #getClassGenerationProperties()
	 * @generated
	 */
	EAttribute getClassGenerationProperties_GenerateInsertionOperator();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties <em>Attribute Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties
	 * @generated
	 */
	EClass getAttributeProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#getKind()
	 * @see #getAttributeProperties()
	 * @generated
	 */
	EAttribute getAttributeProperties_Kind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#isVolatile <em>Volatile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Volatile</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#isVolatile()
	 * @see #getAttributeProperties()
	 * @generated
	 */
	EAttribute getAttributeProperties_Volatile();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#getInitialization <em>Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initialization</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#getInitialization()
	 * @see #getAttributeProperties()
	 * @generated
	 */
	EAttribute getAttributeProperties_Initialization();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#getType()
	 * @see #getAttributeProperties()
	 * @generated
	 */
	EAttribute getAttributeProperties_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#getSize()
	 * @see #getAttributeProperties()
	 * @generated
	 */
	EAttribute getAttributeProperties_Size();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#isPointsToConstType <em>Points To Const Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Points To Const Type</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#isPointsToConstType()
	 * @see #getAttributeProperties()
	 * @generated
	 */
	EAttribute getAttributeProperties_PointsToConstType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#isPointsToVolatileType <em>Points To Volatile Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Points To Volatile Type</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#isPointsToVolatileType()
	 * @see #getAttributeProperties()
	 * @generated
	 */
	EAttribute getAttributeProperties_PointsToVolatileType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#isPointsToType <em>Points To Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Points To Type</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#isPointsToType()
	 * @see #getAttributeProperties()
	 * @generated
	 */
	EAttribute getAttributeProperties_PointsToType();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#getBase_Property <em>Base Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Property</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties#getBase_Property()
	 * @see #getAttributeProperties()
	 * @generated
	 */
	EReference getAttributeProperties_Base_Property();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CapsuleProperties <em>Capsule Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Capsule Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CapsuleProperties
	 * @generated
	 */
	EClass getCapsuleProperties();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties <em>Dependency Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dependency Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties
	 * @generated
	 */
	EClass getDependencyProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties#getKindInHeader <em>Kind In Header</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind In Header</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties#getKindInHeader()
	 * @see #getDependencyProperties()
	 * @generated
	 */
	EAttribute getDependencyProperties_KindInHeader();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties#getKindInImplementation <em>Kind In Implementation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind In Implementation</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties#getKindInImplementation()
	 * @see #getDependencyProperties()
	 * @generated
	 */
	EAttribute getDependencyProperties_KindInImplementation();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties#getBase_Dependency <em>Base Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Dependency</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties#getBase_Dependency()
	 * @see #getDependencyProperties()
	 * @generated
	 */
	EReference getDependencyProperties_Base_Dependency();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.EnumerationProperties <em>Enumeration Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumeration Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.EnumerationProperties
	 * @generated
	 */
	EClass getEnumerationProperties();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.EnumerationProperties#getBase_Enumeration <em>Base Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Enumeration</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.EnumerationProperties#getBase_Enumeration()
	 * @see #getEnumerationProperties()
	 * @generated
	 */
	EReference getEnumerationProperties_Base_Enumeration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties <em>Operation Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties
	 * @generated
	 */
	EClass getOperationProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties#isInline <em>Inline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Inline</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties#isInline()
	 * @see #getOperationProperties()
	 * @generated
	 */
	EAttribute getOperationProperties_Inline();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties#isPolymorphic <em>Polymorphic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Polymorphic</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties#isPolymorphic()
	 * @see #getOperationProperties()
	 * @generated
	 */
	EAttribute getOperationProperties_Polymorphic();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties#getKind()
	 * @see #getOperationProperties()
	 * @generated
	 */
	EAttribute getOperationProperties_Kind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties#isGenerateDefinition <em>Generate Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Definition</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties#isGenerateDefinition()
	 * @see #getOperationProperties()
	 * @generated
	 */
	EAttribute getOperationProperties_GenerateDefinition();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties#getBase_Operation <em>Base Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Operation</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties#getBase_Operation()
	 * @see #getOperationProperties()
	 * @generated
	 */
	EReference getOperationProperties_Base_Operation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.TypeProperties <em>Type Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.TypeProperties
	 * @generated
	 */
	EClass getTypeProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.TypeProperties#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.TypeProperties#getName()
	 * @see #getTypeProperties()
	 * @generated
	 */
	EAttribute getTypeProperties_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.TypeProperties#getDefinitionFile <em>Definition File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Definition File</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.TypeProperties#getDefinitionFile()
	 * @see #getTypeProperties()
	 * @generated
	 */
	EAttribute getTypeProperties_DefinitionFile();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties <em>Parameter Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties
	 * @generated
	 */
	EClass getParameterProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties#getType()
	 * @see #getParameterProperties()
	 * @generated
	 */
	EAttribute getParameterProperties_Type();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties#getBase_Parameter <em>Base Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Parameter</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties#getBase_Parameter()
	 * @see #getParameterProperties()
	 * @generated
	 */
	EReference getParameterProperties_Base_Parameter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties#isPointsToType <em>Points To Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Points To Type</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties#isPointsToType()
	 * @see #getParameterProperties()
	 * @generated
	 */
	EAttribute getParameterProperties_PointsToType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties#isPointsToConst <em>Points To Const</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Points To Const</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties#isPointsToConst()
	 * @see #getParameterProperties()
	 * @generated
	 */
	EAttribute getParameterProperties_PointsToConst();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties#isPointsToVolatile <em>Points To Volatile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Points To Volatile</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties#isPointsToVolatile()
	 * @see #getParameterProperties()
	 * @generated
	 */
	EAttribute getParameterProperties_PointsToVolatile();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerelizationProperties <em>Generelization Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generelization Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerelizationProperties
	 * @generated
	 */
	EClass getGenerelizationProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerelizationProperties#isVirtual <em>Virtual</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Virtual</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerelizationProperties#isVirtual()
	 * @see #getGenerelizationProperties()
	 * @generated
	 */
	EAttribute getGenerelizationProperties_Virtual();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerelizationProperties#getBase_Generalization <em>Base Generalization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Generalization</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerelizationProperties#getBase_Generalization()
	 * @see #getGenerelizationProperties()
	 * @generated
	 */
	EReference getGenerelizationProperties_Base_Generalization();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAttributeProperties <em>RTS Attribute Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RTS Attribute Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAttributeProperties
	 * @generated
	 */
	EClass getRTSAttributeProperties();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAttributeProperties#getBase_Property <em>Base Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Property</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAttributeProperties#getBase_Property()
	 * @see #getRTSAttributeProperties()
	 * @generated
	 */
	EReference getRTSAttributeProperties_Base_Property();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties <em>RTS Abstract Attribute Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RTS Abstract Attribute Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties
	 * @generated
	 */
	EClass getRTSAbstractAttributeProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties#isGenerateTypeModifier <em>Generate Type Modifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Type Modifier</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties#isGenerateTypeModifier()
	 * @see #getRTSAbstractAttributeProperties()
	 * @generated
	 */
	EAttribute getRTSAbstractAttributeProperties_GenerateTypeModifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties#getNumElementsFunctionBody <em>Num Elements Function Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Num Elements Function Body</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties#getNumElementsFunctionBody()
	 * @see #getRTSAbstractAttributeProperties()
	 * @generated
	 */
	EAttribute getRTSAbstractAttributeProperties_NumElementsFunctionBody();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties#getTypeDescriptor <em>Type Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Descriptor</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties#getTypeDescriptor()
	 * @see #getRTSAbstractAttributeProperties()
	 * @generated
	 */
	EAttribute getRTSAbstractAttributeProperties_TypeDescriptor();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSDescriptorProperties <em>RTS Descriptor Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RTS Descriptor Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSDescriptorProperties
	 * @generated
	 */
	EClass getRTSDescriptorProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSDescriptorProperties#isGenerateDescriptor <em>Generate Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Descriptor</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSDescriptorProperties#isGenerateDescriptor()
	 * @see #getRTSDescriptorProperties()
	 * @generated
	 */
	EAttribute getRTSDescriptorProperties_GenerateDescriptor();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassProperties <em>RTS Class Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RTS Class Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassProperties
	 * @generated
	 */
	EClass getRTSClassProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassProperties#getTypeDescriptorHint <em>Type Descriptor Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Descriptor Hint</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassProperties#getTypeDescriptorHint()
	 * @see #getRTSClassProperties()
	 * @generated
	 */
	EAttribute getRTSClassProperties_TypeDescriptorHint();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassProperties#getBase_Class <em>Base Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Class</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassProperties#getBase_Class()
	 * @see #getRTSClassProperties()
	 * @generated
	 */
	EReference getRTSClassProperties_Base_Class();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties <em>RTS Classifier Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RTS Classifier Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties
	 * @generated
	 */
	EClass getRTSClassifierProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties#getCopyFunctionBody <em>Copy Function Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Copy Function Body</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties#getCopyFunctionBody()
	 * @see #getRTSClassifierProperties()
	 * @generated
	 */
	EAttribute getRTSClassifierProperties_CopyFunctionBody();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties#getDecodeFunctionBody <em>Decode Function Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Decode Function Body</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties#getDecodeFunctionBody()
	 * @see #getRTSClassifierProperties()
	 * @generated
	 */
	EAttribute getRTSClassifierProperties_DecodeFunctionBody();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties#getDestroyFunctionBody <em>Destroy Function Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Destroy Function Body</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties#getDestroyFunctionBody()
	 * @see #getRTSClassifierProperties()
	 * @generated
	 */
	EAttribute getRTSClassifierProperties_DestroyFunctionBody();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties#getEncodeFunctionBody <em>Encode Function Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Encode Function Body</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties#getEncodeFunctionBody()
	 * @see #getRTSClassifierProperties()
	 * @generated
	 */
	EAttribute getRTSClassifierProperties_EncodeFunctionBody();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties#getInitFunctionBody <em>Init Function Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Init Function Body</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties#getInitFunctionBody()
	 * @see #getRTSClassifierProperties()
	 * @generated
	 */
	EAttribute getRTSClassifierProperties_InitFunctionBody();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties#getVersion()
	 * @see #getRTSClassifierProperties()
	 * @generated
	 */
	EAttribute getRTSClassifierProperties_Version();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationProperties <em>RTS Enumeration Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RTS Enumeration Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationProperties
	 * @generated
	 */
	EClass getRTSEnumerationProperties();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationProperties#getBase_Enumeration <em>Base Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Enumeration</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationProperties#getBase_Enumeration()
	 * @see #getRTSEnumerationProperties()
	 * @generated
	 */
	EReference getRTSEnumerationProperties_Base_Enumeration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationLiteralProperties <em>RTS Enumeration Literal Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RTS Enumeration Literal Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationLiteralProperties
	 * @generated
	 */
	EClass getRTSEnumerationLiteralProperties();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationLiteralProperties#getBase_EnumerationLiteral <em>Base Enumeration Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Enumeration Literal</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationLiteralProperties#getBase_EnumerationLiteral()
	 * @see #getRTSEnumerationLiteralProperties()
	 * @generated
	 */
	EReference getRTSEnumerationLiteralProperties_Base_EnumerationLiteral();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ArtifactProperties <em>Artifact Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Artifact Properties</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ArtifactProperties
	 * @generated
	 */
	EClass getArtifactProperties();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ArtifactProperties#getBase_Artifact <em>Base Artifact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Artifact</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ArtifactProperties#getBase_Artifact()
	 * @see #getArtifactProperties()
	 * @generated
	 */
	EReference getArtifactProperties_Base_Artifact();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ArtifactProperties#getIncludeFile <em>Include File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Include File</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ArtifactProperties#getIncludeFile()
	 * @see #getArtifactProperties()
	 * @generated
	 */
	EAttribute getArtifactProperties_IncludeFile();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ArtifactProperties#getSourceFile <em>Source File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source File</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ArtifactProperties#getSourceFile()
	 * @see #getArtifactProperties()
	 * @generated
	 */
	EAttribute getArtifactProperties_SourceFile();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassKind <em>Class Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Class Kind</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassKind
	 * @generated
	 */
	EEnum getClassKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeKind <em>Attribute Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Attribute Kind</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeKind
	 * @generated
	 */
	EEnum getAttributeKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.InitializationKind <em>Initialization Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Initialization Kind</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.InitializationKind
	 * @generated
	 */
	EEnum getInitializationKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind <em>Dependency Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Dependency Kind</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind
	 * @generated
	 */
	EEnum getDependencyKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationKind <em>Operation Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Operation Kind</em>'.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationKind
	 * @generated
	 */
	EEnum getOperationKind();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RTCppPropertiesFactory getRTCppPropertiesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl <em>Passive Class Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getPassiveClassProperties()
		 * @generated
		 */
		EClass PASSIVE_CLASS_PROPERTIES = eINSTANCE.getPassiveClassProperties();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PASSIVE_CLASS_PROPERTIES__KIND = eINSTANCE.getPassiveClassProperties_Kind();

		/**
		 * The meta object literal for the '<em><b>Implementation Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_TYPE = eINSTANCE.getPassiveClassProperties_ImplementationType();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassPropertiesImpl <em>Class Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getClassProperties()
		 * @generated
		 */
		EClass CLASS_PROPERTIES = eINSTANCE.getClassProperties();

		/**
		 * The meta object literal for the '<em><b>Base Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_PROPERTIES__BASE_CLASS = eINSTANCE.getClassProperties_Base_Class();

		/**
		 * The meta object literal for the '<em><b>Private Declarations</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_PROPERTIES__PRIVATE_DECLARATIONS = eINSTANCE.getClassProperties_PrivateDeclarations();

		/**
		 * The meta object literal for the '<em><b>Protected Declarations</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_PROPERTIES__PROTECTED_DECLARATIONS = eINSTANCE.getClassProperties_ProtectedDeclarations();

		/**
		 * The meta object literal for the '<em><b>Public Declarations</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_PROPERTIES__PUBLIC_DECLARATIONS = eINSTANCE.getClassProperties_PublicDeclarations();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CppFilePropertiesImpl <em>Cpp File Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CppFilePropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getCppFileProperties()
		 * @generated
		 */
		EClass CPP_FILE_PROPERTIES = eINSTANCE.getCppFileProperties();

		/**
		 * The meta object literal for the '<em><b>Header Preface</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CPP_FILE_PROPERTIES__HEADER_PREFACE = eINSTANCE.getCppFileProperties_HeaderPreface();

		/**
		 * The meta object literal for the '<em><b>Header Ending</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CPP_FILE_PROPERTIES__HEADER_ENDING = eINSTANCE.getCppFileProperties_HeaderEnding();

		/**
		 * The meta object literal for the '<em><b>Implementation Preface</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CPP_FILE_PROPERTIES__IMPLEMENTATION_PREFACE = eINSTANCE.getCppFileProperties_ImplementationPreface();

		/**
		 * The meta object literal for the '<em><b>Implementation Ending</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CPP_FILE_PROPERTIES__IMPLEMENTATION_ENDING = eINSTANCE.getCppFileProperties_ImplementationEnding();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.GenerationPropertiesImpl <em>Generation Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.GenerationPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getGenerationProperties()
		 * @generated
		 */
		EClass GENERATION_PROPERTIES = eINSTANCE.getGenerationProperties();

		/**
		 * The meta object literal for the '<em><b>Generate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERATION_PROPERTIES__GENERATE = eINSTANCE.getGenerationProperties_Generate();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.FileGenerationPropertiesImpl <em>File Generation Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.FileGenerationPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getFileGenerationProperties()
		 * @generated
		 */
		EClass FILE_GENERATION_PROPERTIES = eINSTANCE.getFileGenerationProperties();

		/**
		 * The meta object literal for the '<em><b>Generate Header</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_GENERATION_PROPERTIES__GENERATE_HEADER = eINSTANCE.getFileGenerationProperties_GenerateHeader();

		/**
		 * The meta object literal for the '<em><b>Generate Implementation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_GENERATION_PROPERTIES__GENERATE_IMPLEMENTATION = eINSTANCE.getFileGenerationProperties_GenerateImplementation();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassGenerationPropertiesImpl <em>Class Generation Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassGenerationPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getClassGenerationProperties()
		 * @generated
		 */
		EClass CLASS_GENERATION_PROPERTIES = eINSTANCE.getClassGenerationProperties();

		/**
		 * The meta object literal for the '<em><b>Generate State Machine</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_GENERATION_PROPERTIES__GENERATE_STATE_MACHINE = eINSTANCE.getClassGenerationProperties_GenerateStateMachine();

		/**
		 * The meta object literal for the '<em><b>Generate Assignment Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_GENERATION_PROPERTIES__GENERATE_ASSIGNMENT_OPERATOR = eINSTANCE.getClassGenerationProperties_GenerateAssignmentOperator();

		/**
		 * The meta object literal for the '<em><b>Generate Copy Constructor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_GENERATION_PROPERTIES__GENERATE_COPY_CONSTRUCTOR = eINSTANCE.getClassGenerationProperties_GenerateCopyConstructor();

		/**
		 * The meta object literal for the '<em><b>Generate Default Constructor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_GENERATION_PROPERTIES__GENERATE_DEFAULT_CONSTRUCTOR = eINSTANCE.getClassGenerationProperties_GenerateDefaultConstructor();

		/**
		 * The meta object literal for the '<em><b>Generate Destructor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_GENERATION_PROPERTIES__GENERATE_DESTRUCTOR = eINSTANCE.getClassGenerationProperties_GenerateDestructor();

		/**
		 * The meta object literal for the '<em><b>Generate Equality Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_GENERATION_PROPERTIES__GENERATE_EQUALITY_OPERATOR = eINSTANCE.getClassGenerationProperties_GenerateEqualityOperator();

		/**
		 * The meta object literal for the '<em><b>Generate Extraction Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_GENERATION_PROPERTIES__GENERATE_EXTRACTION_OPERATOR = eINSTANCE.getClassGenerationProperties_GenerateExtractionOperator();

		/**
		 * The meta object literal for the '<em><b>Generate Inequality Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_GENERATION_PROPERTIES__GENERATE_INEQUALITY_OPERATOR = eINSTANCE.getClassGenerationProperties_GenerateInequalityOperator();

		/**
		 * The meta object literal for the '<em><b>Generate Insertion Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_GENERATION_PROPERTIES__GENERATE_INSERTION_OPERATOR = eINSTANCE.getClassGenerationProperties_GenerateInsertionOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.AttributePropertiesImpl <em>Attribute Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.AttributePropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getAttributeProperties()
		 * @generated
		 */
		EClass ATTRIBUTE_PROPERTIES = eINSTANCE.getAttributeProperties();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_PROPERTIES__KIND = eINSTANCE.getAttributeProperties_Kind();

		/**
		 * The meta object literal for the '<em><b>Volatile</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_PROPERTIES__VOLATILE = eINSTANCE.getAttributeProperties_Volatile();

		/**
		 * The meta object literal for the '<em><b>Initialization</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_PROPERTIES__INITIALIZATION = eINSTANCE.getAttributeProperties_Initialization();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_PROPERTIES__TYPE = eINSTANCE.getAttributeProperties_Type();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_PROPERTIES__SIZE = eINSTANCE.getAttributeProperties_Size();

		/**
		 * The meta object literal for the '<em><b>Points To Const Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_PROPERTIES__POINTS_TO_CONST_TYPE = eINSTANCE.getAttributeProperties_PointsToConstType();

		/**
		 * The meta object literal for the '<em><b>Points To Volatile Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_PROPERTIES__POINTS_TO_VOLATILE_TYPE = eINSTANCE.getAttributeProperties_PointsToVolatileType();

		/**
		 * The meta object literal for the '<em><b>Points To Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_PROPERTIES__POINTS_TO_TYPE = eINSTANCE.getAttributeProperties_PointsToType();

		/**
		 * The meta object literal for the '<em><b>Base Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_PROPERTIES__BASE_PROPERTY = eINSTANCE.getAttributeProperties_Base_Property();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CapsulePropertiesImpl <em>Capsule Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CapsulePropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getCapsuleProperties()
		 * @generated
		 */
		EClass CAPSULE_PROPERTIES = eINSTANCE.getCapsuleProperties();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.DependencyPropertiesImpl <em>Dependency Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.DependencyPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getDependencyProperties()
		 * @generated
		 */
		EClass DEPENDENCY_PROPERTIES = eINSTANCE.getDependencyProperties();

		/**
		 * The meta object literal for the '<em><b>Kind In Header</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPENDENCY_PROPERTIES__KIND_IN_HEADER = eINSTANCE.getDependencyProperties_KindInHeader();

		/**
		 * The meta object literal for the '<em><b>Kind In Implementation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPENDENCY_PROPERTIES__KIND_IN_IMPLEMENTATION = eINSTANCE.getDependencyProperties_KindInImplementation();

		/**
		 * The meta object literal for the '<em><b>Base Dependency</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCY_PROPERTIES__BASE_DEPENDENCY = eINSTANCE.getDependencyProperties_Base_Dependency();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.EnumerationPropertiesImpl <em>Enumeration Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.EnumerationPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getEnumerationProperties()
		 * @generated
		 */
		EClass ENUMERATION_PROPERTIES = eINSTANCE.getEnumerationProperties();

		/**
		 * The meta object literal for the '<em><b>Base Enumeration</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUMERATION_PROPERTIES__BASE_ENUMERATION = eINSTANCE.getEnumerationProperties_Base_Enumeration();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.OperationPropertiesImpl <em>Operation Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.OperationPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getOperationProperties()
		 * @generated
		 */
		EClass OPERATION_PROPERTIES = eINSTANCE.getOperationProperties();

		/**
		 * The meta object literal for the '<em><b>Inline</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_PROPERTIES__INLINE = eINSTANCE.getOperationProperties_Inline();

		/**
		 * The meta object literal for the '<em><b>Polymorphic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_PROPERTIES__POLYMORPHIC = eINSTANCE.getOperationProperties_Polymorphic();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_PROPERTIES__KIND = eINSTANCE.getOperationProperties_Kind();

		/**
		 * The meta object literal for the '<em><b>Generate Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_PROPERTIES__GENERATE_DEFINITION = eINSTANCE.getOperationProperties_GenerateDefinition();

		/**
		 * The meta object literal for the '<em><b>Base Operation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_PROPERTIES__BASE_OPERATION = eINSTANCE.getOperationProperties_Base_Operation();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.TypePropertiesImpl <em>Type Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.TypePropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getTypeProperties()
		 * @generated
		 */
		EClass TYPE_PROPERTIES = eINSTANCE.getTypeProperties();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_PROPERTIES__NAME = eINSTANCE.getTypeProperties_Name();

		/**
		 * The meta object literal for the '<em><b>Definition File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_PROPERTIES__DEFINITION_FILE = eINSTANCE.getTypeProperties_DefinitionFile();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ParameterPropertiesImpl <em>Parameter Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ParameterPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getParameterProperties()
		 * @generated
		 */
		EClass PARAMETER_PROPERTIES = eINSTANCE.getParameterProperties();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_PROPERTIES__TYPE = eINSTANCE.getParameterProperties_Type();

		/**
		 * The meta object literal for the '<em><b>Base Parameter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER_PROPERTIES__BASE_PARAMETER = eINSTANCE.getParameterProperties_Base_Parameter();

		/**
		 * The meta object literal for the '<em><b>Points To Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_PROPERTIES__POINTS_TO_TYPE = eINSTANCE.getParameterProperties_PointsToType();

		/**
		 * The meta object literal for the '<em><b>Points To Const</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_PROPERTIES__POINTS_TO_CONST = eINSTANCE.getParameterProperties_PointsToConst();

		/**
		 * The meta object literal for the '<em><b>Points To Volatile</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_PROPERTIES__POINTS_TO_VOLATILE = eINSTANCE.getParameterProperties_PointsToVolatile();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.GenerelizationPropertiesImpl <em>Generelization Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.GenerelizationPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getGenerelizationProperties()
		 * @generated
		 */
		EClass GENERELIZATION_PROPERTIES = eINSTANCE.getGenerelizationProperties();

		/**
		 * The meta object literal for the '<em><b>Virtual</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERELIZATION_PROPERTIES__VIRTUAL = eINSTANCE.getGenerelizationProperties_Virtual();

		/**
		 * The meta object literal for the '<em><b>Base Generalization</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERELIZATION_PROPERTIES__BASE_GENERALIZATION = eINSTANCE.getGenerelizationProperties_Base_Generalization();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSAttributePropertiesImpl <em>RTS Attribute Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSAttributePropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSAttributeProperties()
		 * @generated
		 */
		EClass RTS_ATTRIBUTE_PROPERTIES = eINSTANCE.getRTSAttributeProperties();

		/**
		 * The meta object literal for the '<em><b>Base Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RTS_ATTRIBUTE_PROPERTIES__BASE_PROPERTY = eINSTANCE.getRTSAttributeProperties_Base_Property();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSAbstractAttributePropertiesImpl <em>RTS Abstract Attribute Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSAbstractAttributePropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSAbstractAttributeProperties()
		 * @generated
		 */
		EClass RTS_ABSTRACT_ATTRIBUTE_PROPERTIES = eINSTANCE.getRTSAbstractAttributeProperties();

		/**
		 * The meta object literal for the '<em><b>Generate Type Modifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__GENERATE_TYPE_MODIFIER = eINSTANCE.getRTSAbstractAttributeProperties_GenerateTypeModifier();

		/**
		 * The meta object literal for the '<em><b>Num Elements Function Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__NUM_ELEMENTS_FUNCTION_BODY = eINSTANCE.getRTSAbstractAttributeProperties_NumElementsFunctionBody();

		/**
		 * The meta object literal for the '<em><b>Type Descriptor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__TYPE_DESCRIPTOR = eINSTANCE.getRTSAbstractAttributeProperties_TypeDescriptor();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSDescriptorPropertiesImpl <em>RTS Descriptor Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSDescriptorPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSDescriptorProperties()
		 * @generated
		 */
		EClass RTS_DESCRIPTOR_PROPERTIES = eINSTANCE.getRTSDescriptorProperties();

		/**
		 * The meta object literal for the '<em><b>Generate Descriptor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR = eINSTANCE.getRTSDescriptorProperties_GenerateDescriptor();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassPropertiesImpl <em>RTS Class Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSClassProperties()
		 * @generated
		 */
		EClass RTS_CLASS_PROPERTIES = eINSTANCE.getRTSClassProperties();

		/**
		 * The meta object literal for the '<em><b>Type Descriptor Hint</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RTS_CLASS_PROPERTIES__TYPE_DESCRIPTOR_HINT = eINSTANCE.getRTSClassProperties_TypeDescriptorHint();

		/**
		 * The meta object literal for the '<em><b>Base Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RTS_CLASS_PROPERTIES__BASE_CLASS = eINSTANCE.getRTSClassProperties_Base_Class();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassifierPropertiesImpl <em>RTS Classifier Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassifierPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSClassifierProperties()
		 * @generated
		 */
		EClass RTS_CLASSIFIER_PROPERTIES = eINSTANCE.getRTSClassifierProperties();

		/**
		 * The meta object literal for the '<em><b>Copy Function Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RTS_CLASSIFIER_PROPERTIES__COPY_FUNCTION_BODY = eINSTANCE.getRTSClassifierProperties_CopyFunctionBody();

		/**
		 * The meta object literal for the '<em><b>Decode Function Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RTS_CLASSIFIER_PROPERTIES__DECODE_FUNCTION_BODY = eINSTANCE.getRTSClassifierProperties_DecodeFunctionBody();

		/**
		 * The meta object literal for the '<em><b>Destroy Function Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RTS_CLASSIFIER_PROPERTIES__DESTROY_FUNCTION_BODY = eINSTANCE.getRTSClassifierProperties_DestroyFunctionBody();

		/**
		 * The meta object literal for the '<em><b>Encode Function Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RTS_CLASSIFIER_PROPERTIES__ENCODE_FUNCTION_BODY = eINSTANCE.getRTSClassifierProperties_EncodeFunctionBody();

		/**
		 * The meta object literal for the '<em><b>Init Function Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RTS_CLASSIFIER_PROPERTIES__INIT_FUNCTION_BODY = eINSTANCE.getRTSClassifierProperties_InitFunctionBody();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RTS_CLASSIFIER_PROPERTIES__VERSION = eINSTANCE.getRTSClassifierProperties_Version();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSEnumerationPropertiesImpl <em>RTS Enumeration Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSEnumerationPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSEnumerationProperties()
		 * @generated
		 */
		EClass RTS_ENUMERATION_PROPERTIES = eINSTANCE.getRTSEnumerationProperties();

		/**
		 * The meta object literal for the '<em><b>Base Enumeration</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RTS_ENUMERATION_PROPERTIES__BASE_ENUMERATION = eINSTANCE.getRTSEnumerationProperties_Base_Enumeration();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSEnumerationLiteralPropertiesImpl <em>RTS Enumeration Literal Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSEnumerationLiteralPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getRTSEnumerationLiteralProperties()
		 * @generated
		 */
		EClass RTS_ENUMERATION_LITERAL_PROPERTIES = eINSTANCE.getRTSEnumerationLiteralProperties();

		/**
		 * The meta object literal for the '<em><b>Base Enumeration Literal</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RTS_ENUMERATION_LITERAL_PROPERTIES__BASE_ENUMERATION_LITERAL = eINSTANCE.getRTSEnumerationLiteralProperties_Base_EnumerationLiteral();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ArtifactPropertiesImpl <em>Artifact Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ArtifactPropertiesImpl
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getArtifactProperties()
		 * @generated
		 */
		EClass ARTIFACT_PROPERTIES = eINSTANCE.getArtifactProperties();

		/**
		 * The meta object literal for the '<em><b>Base Artifact</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARTIFACT_PROPERTIES__BASE_ARTIFACT = eINSTANCE.getArtifactProperties_Base_Artifact();

		/**
		 * The meta object literal for the '<em><b>Include File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT_PROPERTIES__INCLUDE_FILE = eINSTANCE.getArtifactProperties_IncludeFile();

		/**
		 * The meta object literal for the '<em><b>Source File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT_PROPERTIES__SOURCE_FILE = eINSTANCE.getArtifactProperties_SourceFile();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassKind <em>Class Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassKind
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getClassKind()
		 * @generated
		 */
		EEnum CLASS_KIND = eINSTANCE.getClassKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeKind <em>Attribute Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeKind
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getAttributeKind()
		 * @generated
		 */
		EEnum ATTRIBUTE_KIND = eINSTANCE.getAttributeKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.InitializationKind <em>Initialization Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.InitializationKind
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getInitializationKind()
		 * @generated
		 */
		EEnum INITIALIZATION_KIND = eINSTANCE.getInitializationKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind <em>Dependency Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getDependencyKind()
		 * @generated
		 */
		EEnum DEPENDENCY_KIND = eINSTANCE.getDependencyKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationKind <em>Operation Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationKind
		 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTCppPropertiesPackageImpl#getOperationKind()
		 * @generated
		 */
		EEnum OPERATION_KIND = eINSTANCE.getOperationKind();

	}

} //RTCppPropertiesPackage
