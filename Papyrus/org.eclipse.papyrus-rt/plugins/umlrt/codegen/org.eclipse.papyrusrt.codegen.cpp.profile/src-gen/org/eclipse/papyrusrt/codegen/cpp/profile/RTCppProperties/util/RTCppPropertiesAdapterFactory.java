/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage
 * @generated
 */
public class RTCppPropertiesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RTCppPropertiesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTCppPropertiesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = RTCppPropertiesPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RTCppPropertiesSwitch<Adapter> modelSwitch =
		new RTCppPropertiesSwitch<Adapter>() {
			@Override
			public Adapter casePassiveClassProperties(PassiveClassProperties object) {
				return createPassiveClassPropertiesAdapter();
			}
			@Override
			public Adapter caseClassProperties(ClassProperties object) {
				return createClassPropertiesAdapter();
			}
			@Override
			public Adapter caseCppFileProperties(CppFileProperties object) {
				return createCppFilePropertiesAdapter();
			}
			@Override
			public Adapter caseGenerationProperties(GenerationProperties object) {
				return createGenerationPropertiesAdapter();
			}
			@Override
			public Adapter caseFileGenerationProperties(FileGenerationProperties object) {
				return createFileGenerationPropertiesAdapter();
			}
			@Override
			public Adapter caseClassGenerationProperties(ClassGenerationProperties object) {
				return createClassGenerationPropertiesAdapter();
			}
			@Override
			public Adapter caseAttributeProperties(AttributeProperties object) {
				return createAttributePropertiesAdapter();
			}
			@Override
			public Adapter caseCapsuleProperties(CapsuleProperties object) {
				return createCapsulePropertiesAdapter();
			}
			@Override
			public Adapter caseDependencyProperties(DependencyProperties object) {
				return createDependencyPropertiesAdapter();
			}
			@Override
			public Adapter caseEnumerationProperties(EnumerationProperties object) {
				return createEnumerationPropertiesAdapter();
			}
			@Override
			public Adapter caseOperationProperties(OperationProperties object) {
				return createOperationPropertiesAdapter();
			}
			@Override
			public Adapter caseTypeProperties(TypeProperties object) {
				return createTypePropertiesAdapter();
			}
			@Override
			public Adapter caseParameterProperties(ParameterProperties object) {
				return createParameterPropertiesAdapter();
			}
			@Override
			public Adapter caseGenerelizationProperties(GenerelizationProperties object) {
				return createGenerelizationPropertiesAdapter();
			}
			@Override
			public Adapter caseRTSAttributeProperties(RTSAttributeProperties object) {
				return createRTSAttributePropertiesAdapter();
			}
			@Override
			public Adapter caseRTSAbstractAttributeProperties(RTSAbstractAttributeProperties object) {
				return createRTSAbstractAttributePropertiesAdapter();
			}
			@Override
			public Adapter caseRTSDescriptorProperties(RTSDescriptorProperties object) {
				return createRTSDescriptorPropertiesAdapter();
			}
			@Override
			public Adapter caseRTSClassProperties(RTSClassProperties object) {
				return createRTSClassPropertiesAdapter();
			}
			@Override
			public Adapter caseRTSClassifierProperties(RTSClassifierProperties object) {
				return createRTSClassifierPropertiesAdapter();
			}
			@Override
			public Adapter caseRTSEnumerationProperties(RTSEnumerationProperties object) {
				return createRTSEnumerationPropertiesAdapter();
			}
			@Override
			public Adapter caseRTSEnumerationLiteralProperties(RTSEnumerationLiteralProperties object) {
				return createRTSEnumerationLiteralPropertiesAdapter();
			}
			@Override
			public Adapter caseArtifactProperties(ArtifactProperties object) {
				return createArtifactPropertiesAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.PassiveClassProperties <em>Passive Class Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.PassiveClassProperties
	 * @generated
	 */
	public Adapter createPassiveClassPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties <em>Class Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties
	 * @generated
	 */
	public Adapter createClassPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties <em>Cpp File Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties
	 * @generated
	 */
	public Adapter createCppFilePropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerationProperties <em>Generation Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerationProperties
	 * @generated
	 */
	public Adapter createGenerationPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.FileGenerationProperties <em>File Generation Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.FileGenerationProperties
	 * @generated
	 */
	public Adapter createFileGenerationPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties <em>Class Generation Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties
	 * @generated
	 */
	public Adapter createClassGenerationPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties <em>Attribute Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties
	 * @generated
	 */
	public Adapter createAttributePropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CapsuleProperties <em>Capsule Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CapsuleProperties
	 * @generated
	 */
	public Adapter createCapsulePropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties <em>Dependency Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties
	 * @generated
	 */
	public Adapter createDependencyPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.EnumerationProperties <em>Enumeration Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.EnumerationProperties
	 * @generated
	 */
	public Adapter createEnumerationPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties <em>Operation Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties
	 * @generated
	 */
	public Adapter createOperationPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.TypeProperties <em>Type Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.TypeProperties
	 * @generated
	 */
	public Adapter createTypePropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties <em>Parameter Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties
	 * @generated
	 */
	public Adapter createParameterPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerelizationProperties <em>Generelization Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerelizationProperties
	 * @generated
	 */
	public Adapter createGenerelizationPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAttributeProperties <em>RTS Attribute Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAttributeProperties
	 * @generated
	 */
	public Adapter createRTSAttributePropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties <em>RTS Abstract Attribute Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties
	 * @generated
	 */
	public Adapter createRTSAbstractAttributePropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSDescriptorProperties <em>RTS Descriptor Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSDescriptorProperties
	 * @generated
	 */
	public Adapter createRTSDescriptorPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassProperties <em>RTS Class Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassProperties
	 * @generated
	 */
	public Adapter createRTSClassPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties <em>RTS Classifier Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties
	 * @generated
	 */
	public Adapter createRTSClassifierPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationProperties <em>RTS Enumeration Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationProperties
	 * @generated
	 */
	public Adapter createRTSEnumerationPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationLiteralProperties <em>RTS Enumeration Literal Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationLiteralProperties
	 * @generated
	 */
	public Adapter createRTSEnumerationLiteralPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ArtifactProperties <em>Artifact Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ArtifactProperties
	 * @generated
	 */
	public Adapter createArtifactPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //RTCppPropertiesAdapterFactory
