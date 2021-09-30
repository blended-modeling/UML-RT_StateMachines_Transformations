/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ArtifactProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeKind;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CapsuleProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassKind;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.EnumerationProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.FileGenerationProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerationProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerelizationProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.InitializationKind;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationKind;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.PassiveClassProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesFactory;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAttributeProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSDescriptorProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationLiteralProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.TypeProperties;

import org.eclipse.uml2.types.TypesPackage;

import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RTCppPropertiesPackageImpl extends EPackageImpl implements RTCppPropertiesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass passiveClassPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cppFilePropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass generationPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileGenerationPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classGenerationPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributePropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass capsulePropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dependencyPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumerationPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typePropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass generelizationPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rtsAttributePropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rtsAbstractAttributePropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rtsDescriptorPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rtsClassPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rtsClassifierPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rtsEnumerationPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rtsEnumerationLiteralPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass artifactPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum classKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum attributeKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum initializationKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dependencyKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum operationKindEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RTCppPropertiesPackageImpl() {
		super(eNS_URI, RTCppPropertiesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link RTCppPropertiesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RTCppPropertiesPackage init() {
		if (isInited) return (RTCppPropertiesPackage)EPackage.Registry.INSTANCE.getEPackage(RTCppPropertiesPackage.eNS_URI);

		// Obtain or create and register package
		RTCppPropertiesPackageImpl theRTCppPropertiesPackage = (RTCppPropertiesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof RTCppPropertiesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new RTCppPropertiesPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		UMLPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRTCppPropertiesPackage.createPackageContents();

		// Initialize created meta-data
		theRTCppPropertiesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRTCppPropertiesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RTCppPropertiesPackage.eNS_URI, theRTCppPropertiesPackage);
		return theRTCppPropertiesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPassiveClassProperties() {
		return passiveClassPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPassiveClassProperties_Kind() {
		return (EAttribute)passiveClassPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPassiveClassProperties_ImplementationType() {
		return (EAttribute)passiveClassPropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassProperties() {
		return classPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassProperties_Base_Class() {
		return (EReference)classPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassProperties_PrivateDeclarations() {
		return (EAttribute)classPropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassProperties_ProtectedDeclarations() {
		return (EAttribute)classPropertiesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassProperties_PublicDeclarations() {
		return (EAttribute)classPropertiesEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCppFileProperties() {
		return cppFilePropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCppFileProperties_HeaderPreface() {
		return (EAttribute)cppFilePropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCppFileProperties_HeaderEnding() {
		return (EAttribute)cppFilePropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCppFileProperties_ImplementationPreface() {
		return (EAttribute)cppFilePropertiesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCppFileProperties_ImplementationEnding() {
		return (EAttribute)cppFilePropertiesEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenerationProperties() {
		return generationPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenerationProperties_Generate() {
		return (EAttribute)generationPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFileGenerationProperties() {
		return fileGenerationPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileGenerationProperties_GenerateHeader() {
		return (EAttribute)fileGenerationPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileGenerationProperties_GenerateImplementation() {
		return (EAttribute)fileGenerationPropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassGenerationProperties() {
		return classGenerationPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassGenerationProperties_GenerateStateMachine() {
		return (EAttribute)classGenerationPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassGenerationProperties_GenerateAssignmentOperator() {
		return (EAttribute)classGenerationPropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassGenerationProperties_GenerateCopyConstructor() {
		return (EAttribute)classGenerationPropertiesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassGenerationProperties_GenerateDefaultConstructor() {
		return (EAttribute)classGenerationPropertiesEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassGenerationProperties_GenerateDestructor() {
		return (EAttribute)classGenerationPropertiesEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassGenerationProperties_GenerateEqualityOperator() {
		return (EAttribute)classGenerationPropertiesEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassGenerationProperties_GenerateExtractionOperator() {
		return (EAttribute)classGenerationPropertiesEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassGenerationProperties_GenerateInequalityOperator() {
		return (EAttribute)classGenerationPropertiesEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassGenerationProperties_GenerateInsertionOperator() {
		return (EAttribute)classGenerationPropertiesEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeProperties() {
		return attributePropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeProperties_Kind() {
		return (EAttribute)attributePropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeProperties_Volatile() {
		return (EAttribute)attributePropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeProperties_Initialization() {
		return (EAttribute)attributePropertiesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeProperties_Type() {
		return (EAttribute)attributePropertiesEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeProperties_Size() {
		return (EAttribute)attributePropertiesEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeProperties_PointsToConstType() {
		return (EAttribute)attributePropertiesEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeProperties_PointsToVolatileType() {
		return (EAttribute)attributePropertiesEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeProperties_PointsToType() {
		return (EAttribute)attributePropertiesEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeProperties_Base_Property() {
		return (EReference)attributePropertiesEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCapsuleProperties() {
		return capsulePropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDependencyProperties() {
		return dependencyPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDependencyProperties_KindInHeader() {
		return (EAttribute)dependencyPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDependencyProperties_KindInImplementation() {
		return (EAttribute)dependencyPropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependencyProperties_Base_Dependency() {
		return (EReference)dependencyPropertiesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumerationProperties() {
		return enumerationPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnumerationProperties_Base_Enumeration() {
		return (EReference)enumerationPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationProperties() {
		return operationPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationProperties_Inline() {
		return (EAttribute)operationPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationProperties_Polymorphic() {
		return (EAttribute)operationPropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationProperties_Kind() {
		return (EAttribute)operationPropertiesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationProperties_GenerateDefinition() {
		return (EAttribute)operationPropertiesEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationProperties_Base_Operation() {
		return (EReference)operationPropertiesEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTypeProperties() {
		return typePropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTypeProperties_Name() {
		return (EAttribute)typePropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTypeProperties_DefinitionFile() {
		return (EAttribute)typePropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameterProperties() {
		return parameterPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameterProperties_Type() {
		return (EAttribute)parameterPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameterProperties_Base_Parameter() {
		return (EReference)parameterPropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameterProperties_PointsToType() {
		return (EAttribute)parameterPropertiesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameterProperties_PointsToConst() {
		return (EAttribute)parameterPropertiesEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameterProperties_PointsToVolatile() {
		return (EAttribute)parameterPropertiesEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenerelizationProperties() {
		return generelizationPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenerelizationProperties_Virtual() {
		return (EAttribute)generelizationPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenerelizationProperties_Base_Generalization() {
		return (EReference)generelizationPropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRTSAttributeProperties() {
		return rtsAttributePropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRTSAttributeProperties_Base_Property() {
		return (EReference)rtsAttributePropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRTSAbstractAttributeProperties() {
		return rtsAbstractAttributePropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRTSAbstractAttributeProperties_GenerateTypeModifier() {
		return (EAttribute)rtsAbstractAttributePropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRTSAbstractAttributeProperties_NumElementsFunctionBody() {
		return (EAttribute)rtsAbstractAttributePropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRTSAbstractAttributeProperties_TypeDescriptor() {
		return (EAttribute)rtsAbstractAttributePropertiesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRTSDescriptorProperties() {
		return rtsDescriptorPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRTSDescriptorProperties_GenerateDescriptor() {
		return (EAttribute)rtsDescriptorPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRTSClassProperties() {
		return rtsClassPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRTSClassProperties_TypeDescriptorHint() {
		return (EAttribute)rtsClassPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRTSClassProperties_Base_Class() {
		return (EReference)rtsClassPropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRTSClassifierProperties() {
		return rtsClassifierPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRTSClassifierProperties_CopyFunctionBody() {
		return (EAttribute)rtsClassifierPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRTSClassifierProperties_DecodeFunctionBody() {
		return (EAttribute)rtsClassifierPropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRTSClassifierProperties_DestroyFunctionBody() {
		return (EAttribute)rtsClassifierPropertiesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRTSClassifierProperties_EncodeFunctionBody() {
		return (EAttribute)rtsClassifierPropertiesEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRTSClassifierProperties_InitFunctionBody() {
		return (EAttribute)rtsClassifierPropertiesEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRTSClassifierProperties_Version() {
		return (EAttribute)rtsClassifierPropertiesEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRTSEnumerationProperties() {
		return rtsEnumerationPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRTSEnumerationProperties_Base_Enumeration() {
		return (EReference)rtsEnumerationPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRTSEnumerationLiteralProperties() {
		return rtsEnumerationLiteralPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRTSEnumerationLiteralProperties_Base_EnumerationLiteral() {
		return (EReference)rtsEnumerationLiteralPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArtifactProperties() {
		return artifactPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifactProperties_Base_Artifact() {
		return (EReference)artifactPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifactProperties_IncludeFile() {
		return (EAttribute)artifactPropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifactProperties_SourceFile() {
		return (EAttribute)artifactPropertiesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getClassKind() {
		return classKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getAttributeKind() {
		return attributeKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getInitializationKind() {
		return initializationKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDependencyKind() {
		return dependencyKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getOperationKind() {
		return operationKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTCppPropertiesFactory getRTCppPropertiesFactory() {
		return (RTCppPropertiesFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		passiveClassPropertiesEClass = createEClass(PASSIVE_CLASS_PROPERTIES);
		createEAttribute(passiveClassPropertiesEClass, PASSIVE_CLASS_PROPERTIES__KIND);
		createEAttribute(passiveClassPropertiesEClass, PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_TYPE);

		classPropertiesEClass = createEClass(CLASS_PROPERTIES);
		createEReference(classPropertiesEClass, CLASS_PROPERTIES__BASE_CLASS);
		createEAttribute(classPropertiesEClass, CLASS_PROPERTIES__PRIVATE_DECLARATIONS);
		createEAttribute(classPropertiesEClass, CLASS_PROPERTIES__PROTECTED_DECLARATIONS);
		createEAttribute(classPropertiesEClass, CLASS_PROPERTIES__PUBLIC_DECLARATIONS);

		cppFilePropertiesEClass = createEClass(CPP_FILE_PROPERTIES);
		createEAttribute(cppFilePropertiesEClass, CPP_FILE_PROPERTIES__HEADER_PREFACE);
		createEAttribute(cppFilePropertiesEClass, CPP_FILE_PROPERTIES__HEADER_ENDING);
		createEAttribute(cppFilePropertiesEClass, CPP_FILE_PROPERTIES__IMPLEMENTATION_PREFACE);
		createEAttribute(cppFilePropertiesEClass, CPP_FILE_PROPERTIES__IMPLEMENTATION_ENDING);

		generationPropertiesEClass = createEClass(GENERATION_PROPERTIES);
		createEAttribute(generationPropertiesEClass, GENERATION_PROPERTIES__GENERATE);

		fileGenerationPropertiesEClass = createEClass(FILE_GENERATION_PROPERTIES);
		createEAttribute(fileGenerationPropertiesEClass, FILE_GENERATION_PROPERTIES__GENERATE_HEADER);
		createEAttribute(fileGenerationPropertiesEClass, FILE_GENERATION_PROPERTIES__GENERATE_IMPLEMENTATION);

		classGenerationPropertiesEClass = createEClass(CLASS_GENERATION_PROPERTIES);
		createEAttribute(classGenerationPropertiesEClass, CLASS_GENERATION_PROPERTIES__GENERATE_STATE_MACHINE);
		createEAttribute(classGenerationPropertiesEClass, CLASS_GENERATION_PROPERTIES__GENERATE_ASSIGNMENT_OPERATOR);
		createEAttribute(classGenerationPropertiesEClass, CLASS_GENERATION_PROPERTIES__GENERATE_COPY_CONSTRUCTOR);
		createEAttribute(classGenerationPropertiesEClass, CLASS_GENERATION_PROPERTIES__GENERATE_DEFAULT_CONSTRUCTOR);
		createEAttribute(classGenerationPropertiesEClass, CLASS_GENERATION_PROPERTIES__GENERATE_DESTRUCTOR);
		createEAttribute(classGenerationPropertiesEClass, CLASS_GENERATION_PROPERTIES__GENERATE_EQUALITY_OPERATOR);
		createEAttribute(classGenerationPropertiesEClass, CLASS_GENERATION_PROPERTIES__GENERATE_EXTRACTION_OPERATOR);
		createEAttribute(classGenerationPropertiesEClass, CLASS_GENERATION_PROPERTIES__GENERATE_INEQUALITY_OPERATOR);
		createEAttribute(classGenerationPropertiesEClass, CLASS_GENERATION_PROPERTIES__GENERATE_INSERTION_OPERATOR);

		attributePropertiesEClass = createEClass(ATTRIBUTE_PROPERTIES);
		createEAttribute(attributePropertiesEClass, ATTRIBUTE_PROPERTIES__KIND);
		createEAttribute(attributePropertiesEClass, ATTRIBUTE_PROPERTIES__VOLATILE);
		createEAttribute(attributePropertiesEClass, ATTRIBUTE_PROPERTIES__INITIALIZATION);
		createEAttribute(attributePropertiesEClass, ATTRIBUTE_PROPERTIES__TYPE);
		createEAttribute(attributePropertiesEClass, ATTRIBUTE_PROPERTIES__SIZE);
		createEAttribute(attributePropertiesEClass, ATTRIBUTE_PROPERTIES__POINTS_TO_CONST_TYPE);
		createEAttribute(attributePropertiesEClass, ATTRIBUTE_PROPERTIES__POINTS_TO_VOLATILE_TYPE);
		createEAttribute(attributePropertiesEClass, ATTRIBUTE_PROPERTIES__POINTS_TO_TYPE);
		createEReference(attributePropertiesEClass, ATTRIBUTE_PROPERTIES__BASE_PROPERTY);

		capsulePropertiesEClass = createEClass(CAPSULE_PROPERTIES);

		dependencyPropertiesEClass = createEClass(DEPENDENCY_PROPERTIES);
		createEAttribute(dependencyPropertiesEClass, DEPENDENCY_PROPERTIES__KIND_IN_HEADER);
		createEAttribute(dependencyPropertiesEClass, DEPENDENCY_PROPERTIES__KIND_IN_IMPLEMENTATION);
		createEReference(dependencyPropertiesEClass, DEPENDENCY_PROPERTIES__BASE_DEPENDENCY);

		enumerationPropertiesEClass = createEClass(ENUMERATION_PROPERTIES);
		createEReference(enumerationPropertiesEClass, ENUMERATION_PROPERTIES__BASE_ENUMERATION);

		operationPropertiesEClass = createEClass(OPERATION_PROPERTIES);
		createEAttribute(operationPropertiesEClass, OPERATION_PROPERTIES__INLINE);
		createEAttribute(operationPropertiesEClass, OPERATION_PROPERTIES__POLYMORPHIC);
		createEAttribute(operationPropertiesEClass, OPERATION_PROPERTIES__KIND);
		createEAttribute(operationPropertiesEClass, OPERATION_PROPERTIES__GENERATE_DEFINITION);
		createEReference(operationPropertiesEClass, OPERATION_PROPERTIES__BASE_OPERATION);

		typePropertiesEClass = createEClass(TYPE_PROPERTIES);
		createEAttribute(typePropertiesEClass, TYPE_PROPERTIES__NAME);
		createEAttribute(typePropertiesEClass, TYPE_PROPERTIES__DEFINITION_FILE);

		parameterPropertiesEClass = createEClass(PARAMETER_PROPERTIES);
		createEAttribute(parameterPropertiesEClass, PARAMETER_PROPERTIES__TYPE);
		createEReference(parameterPropertiesEClass, PARAMETER_PROPERTIES__BASE_PARAMETER);
		createEAttribute(parameterPropertiesEClass, PARAMETER_PROPERTIES__POINTS_TO_TYPE);
		createEAttribute(parameterPropertiesEClass, PARAMETER_PROPERTIES__POINTS_TO_CONST);
		createEAttribute(parameterPropertiesEClass, PARAMETER_PROPERTIES__POINTS_TO_VOLATILE);

		generelizationPropertiesEClass = createEClass(GENERELIZATION_PROPERTIES);
		createEAttribute(generelizationPropertiesEClass, GENERELIZATION_PROPERTIES__VIRTUAL);
		createEReference(generelizationPropertiesEClass, GENERELIZATION_PROPERTIES__BASE_GENERALIZATION);

		rtsAttributePropertiesEClass = createEClass(RTS_ATTRIBUTE_PROPERTIES);
		createEReference(rtsAttributePropertiesEClass, RTS_ATTRIBUTE_PROPERTIES__BASE_PROPERTY);

		rtsAbstractAttributePropertiesEClass = createEClass(RTS_ABSTRACT_ATTRIBUTE_PROPERTIES);
		createEAttribute(rtsAbstractAttributePropertiesEClass, RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__GENERATE_TYPE_MODIFIER);
		createEAttribute(rtsAbstractAttributePropertiesEClass, RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__NUM_ELEMENTS_FUNCTION_BODY);
		createEAttribute(rtsAbstractAttributePropertiesEClass, RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__TYPE_DESCRIPTOR);

		rtsDescriptorPropertiesEClass = createEClass(RTS_DESCRIPTOR_PROPERTIES);
		createEAttribute(rtsDescriptorPropertiesEClass, RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR);

		rtsClassPropertiesEClass = createEClass(RTS_CLASS_PROPERTIES);
		createEAttribute(rtsClassPropertiesEClass, RTS_CLASS_PROPERTIES__TYPE_DESCRIPTOR_HINT);
		createEReference(rtsClassPropertiesEClass, RTS_CLASS_PROPERTIES__BASE_CLASS);

		rtsClassifierPropertiesEClass = createEClass(RTS_CLASSIFIER_PROPERTIES);
		createEAttribute(rtsClassifierPropertiesEClass, RTS_CLASSIFIER_PROPERTIES__COPY_FUNCTION_BODY);
		createEAttribute(rtsClassifierPropertiesEClass, RTS_CLASSIFIER_PROPERTIES__DECODE_FUNCTION_BODY);
		createEAttribute(rtsClassifierPropertiesEClass, RTS_CLASSIFIER_PROPERTIES__DESTROY_FUNCTION_BODY);
		createEAttribute(rtsClassifierPropertiesEClass, RTS_CLASSIFIER_PROPERTIES__ENCODE_FUNCTION_BODY);
		createEAttribute(rtsClassifierPropertiesEClass, RTS_CLASSIFIER_PROPERTIES__INIT_FUNCTION_BODY);
		createEAttribute(rtsClassifierPropertiesEClass, RTS_CLASSIFIER_PROPERTIES__VERSION);

		rtsEnumerationPropertiesEClass = createEClass(RTS_ENUMERATION_PROPERTIES);
		createEReference(rtsEnumerationPropertiesEClass, RTS_ENUMERATION_PROPERTIES__BASE_ENUMERATION);

		rtsEnumerationLiteralPropertiesEClass = createEClass(RTS_ENUMERATION_LITERAL_PROPERTIES);
		createEReference(rtsEnumerationLiteralPropertiesEClass, RTS_ENUMERATION_LITERAL_PROPERTIES__BASE_ENUMERATION_LITERAL);

		artifactPropertiesEClass = createEClass(ARTIFACT_PROPERTIES);
		createEReference(artifactPropertiesEClass, ARTIFACT_PROPERTIES__BASE_ARTIFACT);
		createEAttribute(artifactPropertiesEClass, ARTIFACT_PROPERTIES__INCLUDE_FILE);
		createEAttribute(artifactPropertiesEClass, ARTIFACT_PROPERTIES__SOURCE_FILE);

		// Create enums
		classKindEEnum = createEEnum(CLASS_KIND);
		attributeKindEEnum = createEEnum(ATTRIBUTE_KIND);
		initializationKindEEnum = createEEnum(INITIALIZATION_KIND);
		dependencyKindEEnum = createEEnum(DEPENDENCY_KIND);
		operationKindEEnum = createEEnum(OPERATION_KIND);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
		UMLPackage theUMLPackage = (UMLPackage)EPackage.Registry.INSTANCE.getEPackage(UMLPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		passiveClassPropertiesEClass.getESuperTypes().add(this.getClassGenerationProperties());
		passiveClassPropertiesEClass.getESuperTypes().add(this.getClassProperties());
		passiveClassPropertiesEClass.getESuperTypes().add(this.getFileGenerationProperties());
		passiveClassPropertiesEClass.getESuperTypes().add(this.getGenerationProperties());
		classPropertiesEClass.getESuperTypes().add(this.getCppFileProperties());
		capsulePropertiesEClass.getESuperTypes().add(this.getClassProperties());
		capsulePropertiesEClass.getESuperTypes().add(this.getFileGenerationProperties());
		enumerationPropertiesEClass.getESuperTypes().add(this.getCppFileProperties());
		enumerationPropertiesEClass.getESuperTypes().add(this.getGenerationProperties());
		rtsAttributePropertiesEClass.getESuperTypes().add(this.getRTSAbstractAttributeProperties());
		rtsAttributePropertiesEClass.getESuperTypes().add(this.getRTSDescriptorProperties());
		rtsClassPropertiesEClass.getESuperTypes().add(this.getRTSClassifierProperties());
		rtsClassPropertiesEClass.getESuperTypes().add(this.getRTSDescriptorProperties());
		rtsEnumerationPropertiesEClass.getESuperTypes().add(this.getRTSClassifierProperties());
		rtsEnumerationPropertiesEClass.getESuperTypes().add(this.getRTSDescriptorProperties());
		rtsEnumerationLiteralPropertiesEClass.getESuperTypes().add(this.getRTSAbstractAttributeProperties());
		rtsEnumerationLiteralPropertiesEClass.getESuperTypes().add(this.getRTSDescriptorProperties());

		// Initialize classes, features, and operations; add parameters
		initEClass(passiveClassPropertiesEClass, PassiveClassProperties.class, "PassiveClassProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPassiveClassProperties_Kind(), this.getClassKind(), "kind", null, 1, 1, PassiveClassProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getPassiveClassProperties_ImplementationType(), theTypesPackage.getString(), "implementationType", null, 0, 1, PassiveClassProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(classPropertiesEClass, ClassProperties.class, "ClassProperties", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getClassProperties_Base_Class(), theUMLPackage.getClass_(), null, "base_Class", null, 1, 1, ClassProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getClassProperties_PrivateDeclarations(), theTypesPackage.getString(), "privateDeclarations", null, 0, 1, ClassProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getClassProperties_ProtectedDeclarations(), theTypesPackage.getString(), "protectedDeclarations", null, 0, 1, ClassProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getClassProperties_PublicDeclarations(), theTypesPackage.getString(), "publicDeclarations", null, 0, 1, ClassProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(cppFilePropertiesEClass, CppFileProperties.class, "CppFileProperties", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCppFileProperties_HeaderPreface(), theTypesPackage.getString(), "headerPreface", null, 0, 1, CppFileProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getCppFileProperties_HeaderEnding(), theTypesPackage.getString(), "headerEnding", null, 0, 1, CppFileProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getCppFileProperties_ImplementationPreface(), theTypesPackage.getString(), "implementationPreface", null, 0, 1, CppFileProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getCppFileProperties_ImplementationEnding(), theTypesPackage.getString(), "implementationEnding", null, 0, 1, CppFileProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(generationPropertiesEClass, GenerationProperties.class, "GenerationProperties", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGenerationProperties_Generate(), theTypesPackage.getBoolean(), "generate", "true", 1, 1, GenerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(fileGenerationPropertiesEClass, FileGenerationProperties.class, "FileGenerationProperties", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFileGenerationProperties_GenerateHeader(), theTypesPackage.getBoolean(), "generateHeader", "true", 1, 1, FileGenerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getFileGenerationProperties_GenerateImplementation(), theTypesPackage.getBoolean(), "generateImplementation", "true", 1, 1, FileGenerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(classGenerationPropertiesEClass, ClassGenerationProperties.class, "ClassGenerationProperties", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getClassGenerationProperties_GenerateStateMachine(), theTypesPackage.getBoolean(), "generateStateMachine", "true", 1, 1, ClassGenerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getClassGenerationProperties_GenerateAssignmentOperator(), theTypesPackage.getBoolean(), "generateAssignmentOperator", "true", 1, 1, ClassGenerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getClassGenerationProperties_GenerateCopyConstructor(), theTypesPackage.getBoolean(), "generateCopyConstructor", "true", 1, 1, ClassGenerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getClassGenerationProperties_GenerateDefaultConstructor(), theTypesPackage.getBoolean(), "generateDefaultConstructor", "true", 1, 1, ClassGenerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getClassGenerationProperties_GenerateDestructor(), theTypesPackage.getBoolean(), "generateDestructor", "true", 1, 1, ClassGenerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getClassGenerationProperties_GenerateEqualityOperator(), theTypesPackage.getBoolean(), "generateEqualityOperator", "false", 1, 1, ClassGenerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getClassGenerationProperties_GenerateExtractionOperator(), theTypesPackage.getBoolean(), "generateExtractionOperator", "false", 1, 1, ClassGenerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getClassGenerationProperties_GenerateInequalityOperator(), theTypesPackage.getBoolean(), "generateInequalityOperator", "false", 1, 1, ClassGenerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getClassGenerationProperties_GenerateInsertionOperator(), theTypesPackage.getBoolean(), "generateInsertionOperator", "false", 1, 1, ClassGenerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(attributePropertiesEClass, AttributeProperties.class, "AttributeProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributeProperties_Kind(), this.getAttributeKind(), "kind", null, 1, 1, AttributeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getAttributeProperties_Volatile(), theTypesPackage.getBoolean(), "volatile", "false", 1, 1, AttributeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getAttributeProperties_Initialization(), this.getInitializationKind(), "initialization", null, 1, 1, AttributeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getAttributeProperties_Type(), theTypesPackage.getString(), "type", null, 0, 1, AttributeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getAttributeProperties_Size(), theTypesPackage.getString(), "size", null, 0, 1, AttributeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getAttributeProperties_PointsToConstType(), theTypesPackage.getBoolean(), "pointsToConstType", "false", 1, 1, AttributeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getAttributeProperties_PointsToVolatileType(), theTypesPackage.getBoolean(), "pointsToVolatileType", "false", 1, 1, AttributeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getAttributeProperties_PointsToType(), theTypesPackage.getBoolean(), "pointsToType", null, 1, 1, AttributeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getAttributeProperties_Base_Property(), theUMLPackage.getProperty(), null, "base_Property", null, 1, 1, AttributeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(capsulePropertiesEClass, CapsuleProperties.class, "CapsuleProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dependencyPropertiesEClass, DependencyProperties.class, "DependencyProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDependencyProperties_KindInHeader(), this.getDependencyKind(), "KindInHeader", null, 1, 1, DependencyProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getDependencyProperties_KindInImplementation(), this.getDependencyKind(), "KindInImplementation", null, 1, 1, DependencyProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getDependencyProperties_Base_Dependency(), theUMLPackage.getDependency(), null, "base_Dependency", null, 1, 1, DependencyProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(enumerationPropertiesEClass, EnumerationProperties.class, "EnumerationProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEnumerationProperties_Base_Enumeration(), theUMLPackage.getEnumeration(), null, "base_Enumeration", null, 1, 1, EnumerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(operationPropertiesEClass, OperationProperties.class, "OperationProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOperationProperties_Inline(), theTypesPackage.getBoolean(), "Inline", "false", 1, 1, OperationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getOperationProperties_Polymorphic(), theTypesPackage.getBoolean(), "Polymorphic", "false", 1, 1, OperationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getOperationProperties_Kind(), this.getOperationKind(), "kind", null, 1, 1, OperationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getOperationProperties_GenerateDefinition(), theTypesPackage.getBoolean(), "generateDefinition", "true", 1, 1, OperationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getOperationProperties_Base_Operation(), theUMLPackage.getOperation(), null, "base_Operation", null, 1, 1, OperationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(typePropertiesEClass, TypeProperties.class, "TypeProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeProperties_Name(), theTypesPackage.getString(), "name", "", 1, 1, TypeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getTypeProperties_DefinitionFile(), theTypesPackage.getString(), "definitionFile", "", 1, 1, TypeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(parameterPropertiesEClass, ParameterProperties.class, "ParameterProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameterProperties_Type(), theTypesPackage.getString(), "type", "", 0, 1, ParameterProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getParameterProperties_Base_Parameter(), theUMLPackage.getParameter(), null, "base_Parameter", null, 1, 1, ParameterProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getParameterProperties_PointsToType(), theTypesPackage.getBoolean(), "pointsToType", null, 1, 1, ParameterProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getParameterProperties_PointsToConst(), theTypesPackage.getBoolean(), "pointsToConst", null, 1, 1, ParameterProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getParameterProperties_PointsToVolatile(), theTypesPackage.getBoolean(), "pointsToVolatile", null, 1, 1, ParameterProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(generelizationPropertiesEClass, GenerelizationProperties.class, "GenerelizationProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGenerelizationProperties_Virtual(), theTypesPackage.getBoolean(), "virtual", "false", 1, 1, GenerelizationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getGenerelizationProperties_Base_Generalization(), theUMLPackage.getGeneralization(), null, "base_Generalization", null, 1, 1, GenerelizationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(rtsAttributePropertiesEClass, RTSAttributeProperties.class, "RTSAttributeProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRTSAttributeProperties_Base_Property(), theUMLPackage.getProperty(), null, "base_Property", null, 1, 1, RTSAttributeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(rtsAbstractAttributePropertiesEClass, RTSAbstractAttributeProperties.class, "RTSAbstractAttributeProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRTSAbstractAttributeProperties_GenerateTypeModifier(), theTypesPackage.getBoolean(), "generateTypeModifier", "true", 1, 1, RTSAbstractAttributeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getRTSAbstractAttributeProperties_NumElementsFunctionBody(), theTypesPackage.getString(), "numElementsFunctionBody", null, 1, 1, RTSAbstractAttributeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getRTSAbstractAttributeProperties_TypeDescriptor(), theTypesPackage.getString(), "typeDescriptor", null, 1, 1, RTSAbstractAttributeProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(rtsDescriptorPropertiesEClass, RTSDescriptorProperties.class, "RTSDescriptorProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRTSDescriptorProperties_GenerateDescriptor(), theTypesPackage.getBoolean(), "generateDescriptor", "true", 1, 1, RTSDescriptorProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(rtsClassPropertiesEClass, RTSClassProperties.class, "RTSClassProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRTSClassProperties_TypeDescriptorHint(), theTypesPackage.getString(), "typeDescriptorHint", null, 1, 1, RTSClassProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getRTSClassProperties_Base_Class(), theUMLPackage.getClass_(), null, "base_Class", null, 1, 1, RTSClassProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(rtsClassifierPropertiesEClass, RTSClassifierProperties.class, "RTSClassifierProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRTSClassifierProperties_CopyFunctionBody(), theTypesPackage.getString(), "copyFunctionBody", null, 1, 1, RTSClassifierProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getRTSClassifierProperties_DecodeFunctionBody(), theTypesPackage.getString(), "decodeFunctionBody", null, 1, 1, RTSClassifierProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getRTSClassifierProperties_DestroyFunctionBody(), theTypesPackage.getString(), "destroyFunctionBody", null, 1, 1, RTSClassifierProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getRTSClassifierProperties_EncodeFunctionBody(), theTypesPackage.getString(), "encodeFunctionBody", null, 1, 1, RTSClassifierProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getRTSClassifierProperties_InitFunctionBody(), theTypesPackage.getString(), "initFunctionBody", null, 1, 1, RTSClassifierProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getRTSClassifierProperties_Version(), theTypesPackage.getInteger(), "version", "0", 1, 1, RTSClassifierProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(rtsEnumerationPropertiesEClass, RTSEnumerationProperties.class, "RTSEnumerationProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRTSEnumerationProperties_Base_Enumeration(), theUMLPackage.getEnumeration(), null, "base_Enumeration", null, 1, 1, RTSEnumerationProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(rtsEnumerationLiteralPropertiesEClass, RTSEnumerationLiteralProperties.class, "RTSEnumerationLiteralProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRTSEnumerationLiteralProperties_Base_EnumerationLiteral(), theUMLPackage.getEnumerationLiteral(), null, "base_EnumerationLiteral", null, 1, 1, RTSEnumerationLiteralProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(artifactPropertiesEClass, ArtifactProperties.class, "ArtifactProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArtifactProperties_Base_Artifact(), theUMLPackage.getArtifact(), null, "base_Artifact", null, 1, 1, ArtifactProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getArtifactProperties_IncludeFile(), theTypesPackage.getString(), "includeFile", null, 1, 1, ArtifactProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getArtifactProperties_SourceFile(), theTypesPackage.getString(), "sourceFile", null, 1, 1, ArtifactProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(classKindEEnum, ClassKind.class, "ClassKind");
		addEEnumLiteral(classKindEEnum, ClassKind.CLASS);
		addEEnumLiteral(classKindEEnum, ClassKind.STRUCT);
		addEEnumLiteral(classKindEEnum, ClassKind.UNION);
		addEEnumLiteral(classKindEEnum, ClassKind.TYPEDEF);

		initEEnum(attributeKindEEnum, AttributeKind.class, "AttributeKind");
		addEEnumLiteral(attributeKindEEnum, AttributeKind.MEMBER);
		addEEnumLiteral(attributeKindEEnum, AttributeKind.GLOBAL);
		addEEnumLiteral(attributeKindEEnum, AttributeKind.MUTABLE_MEMBER);
		addEEnumLiteral(attributeKindEEnum, AttributeKind.DEFINE);

		initEEnum(initializationKindEEnum, InitializationKind.class, "InitializationKind");
		addEEnumLiteral(initializationKindEEnum, InitializationKind.ASSIGNMENT);
		addEEnumLiteral(initializationKindEEnum, InitializationKind.CONSTANT);
		addEEnumLiteral(initializationKindEEnum, InitializationKind.CONSTRUCTOR);

		initEEnum(dependencyKindEEnum, DependencyKind.class, "DependencyKind");
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.FORWARD_REFERENCE);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.INCLUSION);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.NONE);

		initEEnum(operationKindEEnum, OperationKind.class, "OperationKind");
		addEEnumLiteral(operationKindEEnum, OperationKind.MEMBER);
		addEEnumLiteral(operationKindEEnum, OperationKind.FRIEND);
		addEEnumLiteral(operationKindEEnum, OperationKind.GLOBAL);

		// Create resource
		createResource(eNS_URI);
	}

} //RTCppPropertiesPackageImpl
