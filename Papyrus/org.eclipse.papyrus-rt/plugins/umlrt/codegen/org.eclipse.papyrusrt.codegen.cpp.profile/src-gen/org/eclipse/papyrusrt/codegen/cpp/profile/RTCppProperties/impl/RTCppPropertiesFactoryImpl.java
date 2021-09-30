/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RTCppPropertiesFactoryImpl extends EFactoryImpl implements RTCppPropertiesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RTCppPropertiesFactory init() {
		try {
			RTCppPropertiesFactory theRTCppPropertiesFactory = (RTCppPropertiesFactory)EPackage.Registry.INSTANCE.getEFactory(RTCppPropertiesPackage.eNS_URI);
			if (theRTCppPropertiesFactory != null) {
				return theRTCppPropertiesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RTCppPropertiesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTCppPropertiesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES: return createPassiveClassProperties();
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES: return createAttributeProperties();
			case RTCppPropertiesPackage.CAPSULE_PROPERTIES: return createCapsuleProperties();
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES: return createDependencyProperties();
			case RTCppPropertiesPackage.ENUMERATION_PROPERTIES: return createEnumerationProperties();
			case RTCppPropertiesPackage.OPERATION_PROPERTIES: return createOperationProperties();
			case RTCppPropertiesPackage.TYPE_PROPERTIES: return createTypeProperties();
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES: return createParameterProperties();
			case RTCppPropertiesPackage.GENERELIZATION_PROPERTIES: return createGenerelizationProperties();
			case RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES: return createRTSAttributeProperties();
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES: return createRTSAbstractAttributeProperties();
			case RTCppPropertiesPackage.RTS_DESCRIPTOR_PROPERTIES: return createRTSDescriptorProperties();
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES: return createRTSClassProperties();
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES: return createRTSClassifierProperties();
			case RTCppPropertiesPackage.RTS_ENUMERATION_PROPERTIES: return createRTSEnumerationProperties();
			case RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES: return createRTSEnumerationLiteralProperties();
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES: return createArtifactProperties();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case RTCppPropertiesPackage.CLASS_KIND:
				return createClassKindFromString(eDataType, initialValue);
			case RTCppPropertiesPackage.ATTRIBUTE_KIND:
				return createAttributeKindFromString(eDataType, initialValue);
			case RTCppPropertiesPackage.INITIALIZATION_KIND:
				return createInitializationKindFromString(eDataType, initialValue);
			case RTCppPropertiesPackage.DEPENDENCY_KIND:
				return createDependencyKindFromString(eDataType, initialValue);
			case RTCppPropertiesPackage.OPERATION_KIND:
				return createOperationKindFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case RTCppPropertiesPackage.CLASS_KIND:
				return convertClassKindToString(eDataType, instanceValue);
			case RTCppPropertiesPackage.ATTRIBUTE_KIND:
				return convertAttributeKindToString(eDataType, instanceValue);
			case RTCppPropertiesPackage.INITIALIZATION_KIND:
				return convertInitializationKindToString(eDataType, instanceValue);
			case RTCppPropertiesPackage.DEPENDENCY_KIND:
				return convertDependencyKindToString(eDataType, instanceValue);
			case RTCppPropertiesPackage.OPERATION_KIND:
				return convertOperationKindToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PassiveClassProperties createPassiveClassProperties() {
		PassiveClassPropertiesImpl passiveClassProperties = new PassiveClassPropertiesImpl();
		return passiveClassProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeProperties createAttributeProperties() {
		AttributePropertiesImpl attributeProperties = new AttributePropertiesImpl();
		return attributeProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CapsuleProperties createCapsuleProperties() {
		CapsulePropertiesImpl capsuleProperties = new CapsulePropertiesImpl();
		return capsuleProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependencyProperties createDependencyProperties() {
		DependencyPropertiesImpl dependencyProperties = new DependencyPropertiesImpl();
		return dependencyProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumerationProperties createEnumerationProperties() {
		EnumerationPropertiesImpl enumerationProperties = new EnumerationPropertiesImpl();
		return enumerationProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationProperties createOperationProperties() {
		OperationPropertiesImpl operationProperties = new OperationPropertiesImpl();
		return operationProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeProperties createTypeProperties() {
		TypePropertiesImpl typeProperties = new TypePropertiesImpl();
		return typeProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterProperties createParameterProperties() {
		ParameterPropertiesImpl parameterProperties = new ParameterPropertiesImpl();
		return parameterProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenerelizationProperties createGenerelizationProperties() {
		GenerelizationPropertiesImpl generelizationProperties = new GenerelizationPropertiesImpl();
		return generelizationProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTSAttributeProperties createRTSAttributeProperties() {
		RTSAttributePropertiesImpl rtsAttributeProperties = new RTSAttributePropertiesImpl();
		return rtsAttributeProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTSAbstractAttributeProperties createRTSAbstractAttributeProperties() {
		RTSAbstractAttributePropertiesImpl rtsAbstractAttributeProperties = new RTSAbstractAttributePropertiesImpl();
		return rtsAbstractAttributeProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTSDescriptorProperties createRTSDescriptorProperties() {
		RTSDescriptorPropertiesImpl rtsDescriptorProperties = new RTSDescriptorPropertiesImpl();
		return rtsDescriptorProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTSClassProperties createRTSClassProperties() {
		RTSClassPropertiesImpl rtsClassProperties = new RTSClassPropertiesImpl();
		return rtsClassProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTSClassifierProperties createRTSClassifierProperties() {
		RTSClassifierPropertiesImpl rtsClassifierProperties = new RTSClassifierPropertiesImpl();
		return rtsClassifierProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTSEnumerationProperties createRTSEnumerationProperties() {
		RTSEnumerationPropertiesImpl rtsEnumerationProperties = new RTSEnumerationPropertiesImpl();
		return rtsEnumerationProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTSEnumerationLiteralProperties createRTSEnumerationLiteralProperties() {
		RTSEnumerationLiteralPropertiesImpl rtsEnumerationLiteralProperties = new RTSEnumerationLiteralPropertiesImpl();
		return rtsEnumerationLiteralProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactProperties createArtifactProperties() {
		ArtifactPropertiesImpl artifactProperties = new ArtifactPropertiesImpl();
		return artifactProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassKind createClassKindFromString(EDataType eDataType, String initialValue) {
		ClassKind result = ClassKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertClassKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeKind createAttributeKindFromString(EDataType eDataType, String initialValue) {
		AttributeKind result = AttributeKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAttributeKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitializationKind createInitializationKindFromString(EDataType eDataType, String initialValue) {
		InitializationKind result = InitializationKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertInitializationKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependencyKind createDependencyKindFromString(EDataType eDataType, String initialValue) {
		DependencyKind result = DependencyKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDependencyKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationKind createOperationKindFromString(EDataType eDataType, String initialValue) {
		OperationKind result = OperationKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOperationKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTCppPropertiesPackage getRTCppPropertiesPackage() {
		return (RTCppPropertiesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RTCppPropertiesPackage getPackage() {
		return RTCppPropertiesPackage.eINSTANCE;
	}

} //RTCppPropertiesFactoryImpl
