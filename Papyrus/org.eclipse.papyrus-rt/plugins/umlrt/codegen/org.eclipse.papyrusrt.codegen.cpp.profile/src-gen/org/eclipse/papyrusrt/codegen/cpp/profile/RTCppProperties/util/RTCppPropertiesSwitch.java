/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage
 * @generated
 */
public class RTCppPropertiesSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RTCppPropertiesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTCppPropertiesSwitch() {
		if (modelPackage == null) {
			modelPackage = RTCppPropertiesPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES: {
				PassiveClassProperties passiveClassProperties = (PassiveClassProperties)theEObject;
				T result = casePassiveClassProperties(passiveClassProperties);
				if (result == null) result = caseClassGenerationProperties(passiveClassProperties);
				if (result == null) result = caseClassProperties(passiveClassProperties);
				if (result == null) result = caseFileGenerationProperties(passiveClassProperties);
				if (result == null) result = caseGenerationProperties(passiveClassProperties);
				if (result == null) result = caseCppFileProperties(passiveClassProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.CLASS_PROPERTIES: {
				ClassProperties classProperties = (ClassProperties)theEObject;
				T result = caseClassProperties(classProperties);
				if (result == null) result = caseCppFileProperties(classProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES: {
				CppFileProperties cppFileProperties = (CppFileProperties)theEObject;
				T result = caseCppFileProperties(cppFileProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.GENERATION_PROPERTIES: {
				GenerationProperties generationProperties = (GenerationProperties)theEObject;
				T result = caseGenerationProperties(generationProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.FILE_GENERATION_PROPERTIES: {
				FileGenerationProperties fileGenerationProperties = (FileGenerationProperties)theEObject;
				T result = caseFileGenerationProperties(fileGenerationProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES: {
				ClassGenerationProperties classGenerationProperties = (ClassGenerationProperties)theEObject;
				T result = caseClassGenerationProperties(classGenerationProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES: {
				AttributeProperties attributeProperties = (AttributeProperties)theEObject;
				T result = caseAttributeProperties(attributeProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.CAPSULE_PROPERTIES: {
				CapsuleProperties capsuleProperties = (CapsuleProperties)theEObject;
				T result = caseCapsuleProperties(capsuleProperties);
				if (result == null) result = caseClassProperties(capsuleProperties);
				if (result == null) result = caseFileGenerationProperties(capsuleProperties);
				if (result == null) result = caseCppFileProperties(capsuleProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES: {
				DependencyProperties dependencyProperties = (DependencyProperties)theEObject;
				T result = caseDependencyProperties(dependencyProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.ENUMERATION_PROPERTIES: {
				EnumerationProperties enumerationProperties = (EnumerationProperties)theEObject;
				T result = caseEnumerationProperties(enumerationProperties);
				if (result == null) result = caseCppFileProperties(enumerationProperties);
				if (result == null) result = caseGenerationProperties(enumerationProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.OPERATION_PROPERTIES: {
				OperationProperties operationProperties = (OperationProperties)theEObject;
				T result = caseOperationProperties(operationProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.TYPE_PROPERTIES: {
				TypeProperties typeProperties = (TypeProperties)theEObject;
				T result = caseTypeProperties(typeProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES: {
				ParameterProperties parameterProperties = (ParameterProperties)theEObject;
				T result = caseParameterProperties(parameterProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.GENERELIZATION_PROPERTIES: {
				GenerelizationProperties generelizationProperties = (GenerelizationProperties)theEObject;
				T result = caseGenerelizationProperties(generelizationProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES: {
				RTSAttributeProperties rtsAttributeProperties = (RTSAttributeProperties)theEObject;
				T result = caseRTSAttributeProperties(rtsAttributeProperties);
				if (result == null) result = caseRTSAbstractAttributeProperties(rtsAttributeProperties);
				if (result == null) result = caseRTSDescriptorProperties(rtsAttributeProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES: {
				RTSAbstractAttributeProperties rtsAbstractAttributeProperties = (RTSAbstractAttributeProperties)theEObject;
				T result = caseRTSAbstractAttributeProperties(rtsAbstractAttributeProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.RTS_DESCRIPTOR_PROPERTIES: {
				RTSDescriptorProperties rtsDescriptorProperties = (RTSDescriptorProperties)theEObject;
				T result = caseRTSDescriptorProperties(rtsDescriptorProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES: {
				RTSClassProperties rtsClassProperties = (RTSClassProperties)theEObject;
				T result = caseRTSClassProperties(rtsClassProperties);
				if (result == null) result = caseRTSClassifierProperties(rtsClassProperties);
				if (result == null) result = caseRTSDescriptorProperties(rtsClassProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES: {
				RTSClassifierProperties rtsClassifierProperties = (RTSClassifierProperties)theEObject;
				T result = caseRTSClassifierProperties(rtsClassifierProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.RTS_ENUMERATION_PROPERTIES: {
				RTSEnumerationProperties rtsEnumerationProperties = (RTSEnumerationProperties)theEObject;
				T result = caseRTSEnumerationProperties(rtsEnumerationProperties);
				if (result == null) result = caseRTSClassifierProperties(rtsEnumerationProperties);
				if (result == null) result = caseRTSDescriptorProperties(rtsEnumerationProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES: {
				RTSEnumerationLiteralProperties rtsEnumerationLiteralProperties = (RTSEnumerationLiteralProperties)theEObject;
				T result = caseRTSEnumerationLiteralProperties(rtsEnumerationLiteralProperties);
				if (result == null) result = caseRTSAbstractAttributeProperties(rtsEnumerationLiteralProperties);
				if (result == null) result = caseRTSDescriptorProperties(rtsEnumerationLiteralProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES: {
				ArtifactProperties artifactProperties = (ArtifactProperties)theEObject;
				T result = caseArtifactProperties(artifactProperties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Passive Class Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Passive Class Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePassiveClassProperties(PassiveClassProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Class Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Class Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClassProperties(ClassProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cpp File Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cpp File Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCppFileProperties(CppFileProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generation Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generation Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenerationProperties(GenerationProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File Generation Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File Generation Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFileGenerationProperties(FileGenerationProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Class Generation Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Class Generation Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClassGenerationProperties(ClassGenerationProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeProperties(AttributeProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Capsule Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Capsule Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCapsuleProperties(CapsuleProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dependency Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dependency Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDependencyProperties(DependencyProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enumeration Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enumeration Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumerationProperties(EnumerationProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Operation Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operation Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOperationProperties(OperationProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeProperties(TypeProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameterProperties(ParameterProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generelization Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generelization Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenerelizationProperties(GenerelizationProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RTS Attribute Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RTS Attribute Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRTSAttributeProperties(RTSAttributeProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RTS Abstract Attribute Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RTS Abstract Attribute Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRTSAbstractAttributeProperties(RTSAbstractAttributeProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RTS Descriptor Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RTS Descriptor Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRTSDescriptorProperties(RTSDescriptorProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RTS Class Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RTS Class Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRTSClassProperties(RTSClassProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RTS Classifier Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RTS Classifier Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRTSClassifierProperties(RTSClassifierProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RTS Enumeration Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RTS Enumeration Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRTSEnumerationProperties(RTSEnumerationProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RTS Enumeration Literal Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RTS Enumeration Literal Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRTSEnumerationLiteralProperties(RTSEnumerationLiteralProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Artifact Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Artifact Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArtifactProperties(ArtifactProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //RTCppPropertiesSwitch
