/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTModel;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.uml2.types.TypesPackage;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class UMLRTUMLRTPackageImpl extends EPackageImpl implements UMLRTUMLRTPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass namedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass packageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass capsuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass classifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass protocolEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass replicatedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass portEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass capsulePartEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass connectorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass stateMachineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass vertexEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass stateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass transitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass triggerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass guardEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass opaqueBehaviorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass connectionPointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass pseudostateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass protocolMessageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum umlrtInheritanceKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum umlrtPortKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum umlrtCapsulePartKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum umlrtConnectionPointKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum umlrtPseudostateKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EDataType modelEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EDataType listEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EDataType streamEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EDataType mapEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EDataType illegalStateExceptionEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UMLRTUMLRTPackageImpl() {
		super(eNS_URI, UMLRTUMLRTFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link UMLRTUMLRTPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UMLRTUMLRTPackage init() {
		if (isInited) {
			return (UMLRTUMLRTPackage) EPackage.Registry.INSTANCE.getEPackage(UMLRTUMLRTPackage.eNS_URI);
		}

		// Obtain or create and register package
		UMLRTUMLRTPackageImpl theUMLRTPackage = (UMLRTUMLRTPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof UMLRTUMLRTPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new UMLRTUMLRTPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		UMLRealTimePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theUMLRTPackage.createPackageContents();

		// Initialize created meta-data
		theUMLRTPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUMLRTPackage.freeze();


		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UMLRTUMLRTPackage.eNS_URI, theUMLRTPackage);
		return theUMLRTPackage;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getNamedElement() {
		return namedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getNamedElement_Model() {
		return (EAttribute) namedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getNamedElement_InheritanceKind() {
		return (EAttribute) namedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getNamedElement_Name() {
		return (EAttribute) namedElementEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getNamedElement_QualifiedName() {
		return (EAttribute) namedElementEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getNamedElement_IsInherited() {
		return (EAttribute) namedElementEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getNamedElement_IsVirtualRedefinition() {
		return (EAttribute) namedElementEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getNamedElement_IsRedefinition() {
		return (EAttribute) namedElementEClass.getEStructuralFeatures().get(6);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getNamedElement_IsExcluded() {
		return (EAttribute) namedElementEClass.getEStructuralFeatures().get(7);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getNamedElement_RedefinedElement() {
		return (EReference) namedElementEClass.getEStructuralFeatures().get(8);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getNamedElement_RedefinitionContext() {
		return (EReference) namedElementEClass.getEStructuralFeatures().get(9);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getNamedElement_RedefinableElement() {
		return (EReference) namedElementEClass.getEStructuralFeatures().get(10);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getNamedElement_InheritedElement() {
		return (EReference) namedElementEClass.getEStructuralFeatures().get(11);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getNamedElement_RootDefinition() {
		return (EReference) namedElementEClass.getEStructuralFeatures().get(12);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getNamedElement_ExcludedElement() {
		return (EReference) namedElementEClass.getEStructuralFeatures().get(13);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getPackage() {
		return packageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPackage_NestedPackage() {
		return (EReference) packageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPackage_NestingPackage() {
		return (EReference) packageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPackage_Capsule() {
		return (EReference) packageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPackage_Protocol() {
		return (EReference) packageEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getCapsule() {
		return capsuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCapsule_Superclass() {
		return (EReference) capsuleEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCapsule_Subclass() {
		return (EReference) capsuleEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCapsule_Port() {
		return (EReference) capsuleEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCapsule_Connector() {
		return (EReference) capsuleEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCapsule_CapsulePart() {
		return (EReference) capsuleEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getCapsule_Hierarchy() {
		return (EAttribute) capsuleEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCapsule_StateMachine() {
		return (EReference) capsuleEClass.getEStructuralFeatures().get(6);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCapsule_Package() {
		return (EReference) capsuleEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getClassifier() {
		return classifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getClassifier_General() {
		return (EReference) classifierEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getClassifier_Specific() {
		return (EReference) classifierEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getProtocol() {
		return protocolEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocol_SuperProtocol() {
		return (EReference) protocolEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocol_SubProtocol() {
		return (EReference) protocolEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocol_Message() {
		return (EReference) protocolEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocol_InMessage() {
		return (EReference) protocolEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocol_OutMessage() {
		return (EReference) protocolEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocol_InOutMessage() {
		return (EReference) protocolEClass.getEStructuralFeatures().get(6);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getProtocol_IsConjugate() {
		return (EAttribute) protocolEClass.getEStructuralFeatures().get(7);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocol_Conjugate() {
		return (EReference) protocolEClass.getEStructuralFeatures().get(8);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getProtocol_Hierarchy() {
		return (EAttribute) protocolEClass.getEStructuralFeatures().get(9);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocol_Package() {
		return (EReference) protocolEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getReplicatedElement() {
		return replicatedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getReplicatedElement_ReplicationFactor() {
		return (EAttribute) replicatedElementEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getReplicatedElement_ReplicationFactorAsString() {
		return (EAttribute) replicatedElementEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getReplicatedElement_SymbolicReplicationFactor() {
		return (EAttribute) replicatedElementEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getPort() {
		return portEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPort_Kind() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPort_RedefinedPort() {
		return (EReference) portEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPort_Type() {
		return (EReference) portEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPort_PartsWithPort() {
		return (EReference) portEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPort_Service() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPort_Behavior() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPort_Conjugated() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(6);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPort_Wired() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(7);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPort_Publish() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(8);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPort_Notification() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(9);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPort_Registration() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(10);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPort_RegistrationOverride() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(11);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPort_IsConnected() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(12);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPort_IsConnectedInside() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(13);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPort_IsConnectedOutside() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(14);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPort_Connector() {
		return (EReference) portEClass.getEStructuralFeatures().get(15);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPort_InsideConnector() {
		return (EReference) portEClass.getEStructuralFeatures().get(16);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPort_OutsideConnector() {
		return (EReference) portEClass.getEStructuralFeatures().get(17);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPort_Capsule() {
		return (EReference) portEClass.getEStructuralFeatures().get(18);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getCapsulePart() {
		return capsulePartEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getCapsulePart_Kind() {
		return (EAttribute) capsulePartEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getCapsulePart_Optional() {
		return (EAttribute) capsulePartEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCapsulePart_RedefinedPart() {
		return (EReference) capsulePartEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCapsulePart_Type() {
		return (EReference) capsulePartEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCapsulePart_Capsule() {
		return (EReference) capsulePartEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getConnector() {
		return connectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConnector_RedefinedConnector() {
		return (EReference) connectorEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConnector_Capsule() {
		return (EReference) connectorEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConnector_Source() {
		return (EReference) connectorEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConnector_SourcePartWithPort() {
		return (EReference) connectorEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getConnector_SourceReplicationFactor() {
		return (EAttribute) connectorEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConnector_Target() {
		return (EReference) connectorEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConnector_TargetPartWithPort() {
		return (EReference) connectorEClass.getEStructuralFeatures().get(6);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getConnector_TargetReplicationFactor() {
		return (EAttribute) connectorEClass.getEStructuralFeatures().get(7);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getStateMachine() {
		return stateMachineEClass;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStateMachine_Vertex() {
		return (EReference) stateMachineEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStateMachine_Transition() {
		return (EReference) stateMachineEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStateMachine_RedefinedStateMachine() {
		return (EReference) stateMachineEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStateMachine_Capsule() {
		return (EReference) stateMachineEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getVertex() {
		return vertexEClass;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getVertex_State() {
		return (EReference) vertexEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getVertex_Outgoing() {
		return (EReference) vertexEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getVertex_Incoming() {
		return (EReference) vertexEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getVertex_RedefinedVertex() {
		return (EReference) vertexEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getVertex_StateMachine() {
		return (EReference) vertexEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getState() {
		return stateEClass;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getState_Subtransition() {
		return (EReference) stateEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getState_ConnectionPoint() {
		return (EReference) stateEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getState_EntryPoint() {
		return (EReference) stateEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getState_ExitPoint() {
		return (EReference) stateEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getState_RedefinedState() {
		return (EReference) stateEClass.getEStructuralFeatures().get(6);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getState_Composite() {
		return (EAttribute) stateEClass.getEStructuralFeatures().get(7);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getState_Simple() {
		return (EAttribute) stateEClass.getEStructuralFeatures().get(8);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getState_Entry() {
		return (EReference) stateEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getState_Exit() {
		return (EReference) stateEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getState_Subvertex() {
		return (EReference) stateEClass.getEStructuralFeatures().get(9);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getTransition() {
		return transitionEClass;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTransition_StateMachine() {
		return (EReference) transitionEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTransition_Source() {
		return (EReference) transitionEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTransition_Target() {
		return (EReference) transitionEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTransition_Trigger() {
		return (EReference) transitionEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTransition_Guard() {
		return (EReference) transitionEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTransition_RedefinedTransition() {
		return (EReference) transitionEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getTransition_Kind() {
		return (EAttribute) transitionEClass.getEStructuralFeatures().get(6);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getTransition_Internal() {
		return (EAttribute) transitionEClass.getEStructuralFeatures().get(7);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTransition_Effect() {
		return (EReference) transitionEClass.getEStructuralFeatures().get(8);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTransition_State() {
		return (EReference) transitionEClass.getEStructuralFeatures().get(9);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getTrigger() {
		return triggerEClass;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTrigger_ProtocolMessage() {
		return (EReference) triggerEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTrigger_Port() {
		return (EReference) triggerEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTrigger_Guard() {
		return (EReference) triggerEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTrigger_RedefinedTrigger() {
		return (EReference) triggerEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getTrigger_ReceiveAnyMessage() {
		return (EAttribute) triggerEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTrigger_Transition() {
		return (EReference) triggerEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getGuard() {
		return guardEClass;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getGuard_Bodies() {
		return (EAttribute) guardEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getGuard_BodyEntry() {
		return (EReference) guardEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getGuard_Transition() {
		return (EReference) guardEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getGuard_RedefinedGuard() {
		return (EReference) guardEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getGuard_Trigger() {
		return (EReference) guardEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getOpaqueBehavior() {
		return opaqueBehaviorEClass;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getOpaqueBehavior_Bodies() {
		return (EAttribute) opaqueBehaviorEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getOpaqueBehavior_BodyEntry() {
		return (EReference) opaqueBehaviorEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getOpaqueBehavior_EnteredState() {
		return (EReference) opaqueBehaviorEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getOpaqueBehavior_ExitedState() {
		return (EReference) opaqueBehaviorEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getOpaqueBehavior_RedefinedBehavior() {
		return (EReference) opaqueBehaviorEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getOpaqueBehavior_Transition() {
		return (EReference) opaqueBehaviorEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getConnectionPoint() {
		return connectionPointEClass;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getConnectionPoint_Kind() {
		return (EAttribute) connectionPointEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConnectionPoint_RedefinedConnectionPoint() {
		return (EReference) connectionPointEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getPseudostate() {
		return pseudostateEClass;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPseudostate_Kind() {
		return (EAttribute) pseudostateEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPseudostate_RedefinedPseudostate() {
		return (EReference) pseudostateEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getProtocolMessage() {
		return protocolMessageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getProtocolMessage_Kind() {
		return (EAttribute) protocolMessageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocolMessage_RedefinedMessage() {
		return (EReference) protocolMessageEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocolMessage_Parameter() {
		return (EReference) protocolMessageEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getProtocolMessage_IsConjugate() {
		return (EAttribute) protocolMessageEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocolMessage_Conjugate() {
		return (EReference) protocolMessageEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocolMessage_Protocol() {
		return (EReference) protocolMessageEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getUMLRTInheritanceKind() {
		return umlrtInheritanceKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getUMLRTPortKind() {
		return umlrtPortKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getUMLRTCapsulePartKind() {
		return umlrtCapsulePartKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getUMLRTConnectionPointKind() {
		return umlrtConnectionPointKindEEnum;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getUMLRTPseudostateKind() {
		return umlrtPseudostateKindEEnum;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EDataType getModel() {
		return modelEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EDataType getList() {
		return listEDataType;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EDataType getStream() {
		return streamEDataType;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EDataType getMap() {
		return mapEDataType;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EDataType getIllegalStateException() {
		return illegalStateExceptionEDataType;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTUMLRTFactory getUMLRTFactory() {
		return (UMLRTUMLRTFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		namedElementEClass = createEClass(NAMED_ELEMENT);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__MODEL);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__INHERITANCE_KIND);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__QUALIFIED_NAME);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__IS_INHERITED);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__IS_REDEFINITION);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__IS_EXCLUDED);
		createEReference(namedElementEClass, NAMED_ELEMENT__REDEFINED_ELEMENT);
		createEReference(namedElementEClass, NAMED_ELEMENT__REDEFINITION_CONTEXT);
		createEReference(namedElementEClass, NAMED_ELEMENT__REDEFINABLE_ELEMENT);
		createEReference(namedElementEClass, NAMED_ELEMENT__INHERITED_ELEMENT);
		createEReference(namedElementEClass, NAMED_ELEMENT__ROOT_DEFINITION);
		createEReference(namedElementEClass, NAMED_ELEMENT__EXCLUDED_ELEMENT);

		packageEClass = createEClass(PACKAGE);
		createEReference(packageEClass, PACKAGE__NESTED_PACKAGE);
		createEReference(packageEClass, PACKAGE__NESTING_PACKAGE);
		createEReference(packageEClass, PACKAGE__CAPSULE);
		createEReference(packageEClass, PACKAGE__PROTOCOL);

		capsuleEClass = createEClass(CAPSULE);
		createEReference(capsuleEClass, CAPSULE__SUPERCLASS);
		createEReference(capsuleEClass, CAPSULE__SUBCLASS);
		createEReference(capsuleEClass, CAPSULE__PORT);
		createEReference(capsuleEClass, CAPSULE__CAPSULE_PART);
		createEReference(capsuleEClass, CAPSULE__CONNECTOR);
		createEAttribute(capsuleEClass, CAPSULE__HIERARCHY);
		createEReference(capsuleEClass, CAPSULE__STATE_MACHINE);
		createEReference(capsuleEClass, CAPSULE__PACKAGE);

		classifierEClass = createEClass(CLASSIFIER);
		createEReference(classifierEClass, CLASSIFIER__GENERAL);
		createEReference(classifierEClass, CLASSIFIER__SPECIFIC);

		portEClass = createEClass(PORT);
		createEAttribute(portEClass, PORT__KIND);
		createEReference(portEClass, PORT__REDEFINED_PORT);
		createEReference(portEClass, PORT__TYPE);
		createEReference(portEClass, PORT__PARTS_WITH_PORT);
		createEAttribute(portEClass, PORT__SERVICE);
		createEAttribute(portEClass, PORT__BEHAVIOR);
		createEAttribute(portEClass, PORT__CONJUGATED);
		createEAttribute(portEClass, PORT__WIRED);
		createEAttribute(portEClass, PORT__PUBLISH);
		createEAttribute(portEClass, PORT__NOTIFICATION);
		createEAttribute(portEClass, PORT__REGISTRATION);
		createEAttribute(portEClass, PORT__REGISTRATION_OVERRIDE);
		createEAttribute(portEClass, PORT__IS_CONNECTED);
		createEAttribute(portEClass, PORT__IS_CONNECTED_INSIDE);
		createEAttribute(portEClass, PORT__IS_CONNECTED_OUTSIDE);
		createEReference(portEClass, PORT__CONNECTOR);
		createEReference(portEClass, PORT__INSIDE_CONNECTOR);
		createEReference(portEClass, PORT__OUTSIDE_CONNECTOR);
		createEReference(portEClass, PORT__CAPSULE);

		replicatedElementEClass = createEClass(REPLICATED_ELEMENT);
		createEAttribute(replicatedElementEClass, REPLICATED_ELEMENT__REPLICATION_FACTOR);
		createEAttribute(replicatedElementEClass, REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING);
		createEAttribute(replicatedElementEClass, REPLICATED_ELEMENT__SYMBOLIC_REPLICATION_FACTOR);

		protocolEClass = createEClass(PROTOCOL);
		createEReference(protocolEClass, PROTOCOL__PACKAGE);
		createEReference(protocolEClass, PROTOCOL__SUPER_PROTOCOL);
		createEReference(protocolEClass, PROTOCOL__SUB_PROTOCOL);
		createEReference(protocolEClass, PROTOCOL__MESSAGE);
		createEReference(protocolEClass, PROTOCOL__IN_MESSAGE);
		createEReference(protocolEClass, PROTOCOL__OUT_MESSAGE);
		createEReference(protocolEClass, PROTOCOL__IN_OUT_MESSAGE);
		createEAttribute(protocolEClass, PROTOCOL__IS_CONJUGATE);
		createEReference(protocolEClass, PROTOCOL__CONJUGATE);
		createEAttribute(protocolEClass, PROTOCOL__HIERARCHY);

		protocolMessageEClass = createEClass(PROTOCOL_MESSAGE);
		createEAttribute(protocolMessageEClass, PROTOCOL_MESSAGE__KIND);
		createEReference(protocolMessageEClass, PROTOCOL_MESSAGE__REDEFINED_MESSAGE);
		createEReference(protocolMessageEClass, PROTOCOL_MESSAGE__PARAMETER);
		createEAttribute(protocolMessageEClass, PROTOCOL_MESSAGE__IS_CONJUGATE);
		createEReference(protocolMessageEClass, PROTOCOL_MESSAGE__CONJUGATE);
		createEReference(protocolMessageEClass, PROTOCOL_MESSAGE__PROTOCOL);

		capsulePartEClass = createEClass(CAPSULE_PART);
		createEAttribute(capsulePartEClass, CAPSULE_PART__KIND);
		createEAttribute(capsulePartEClass, CAPSULE_PART__OPTIONAL);
		createEReference(capsulePartEClass, CAPSULE_PART__REDEFINED_PART);
		createEReference(capsulePartEClass, CAPSULE_PART__CAPSULE);
		createEReference(capsulePartEClass, CAPSULE_PART__TYPE);

		connectorEClass = createEClass(CONNECTOR);
		createEReference(connectorEClass, CONNECTOR__REDEFINED_CONNECTOR);
		createEReference(connectorEClass, CONNECTOR__CAPSULE);
		createEReference(connectorEClass, CONNECTOR__SOURCE);
		createEReference(connectorEClass, CONNECTOR__SOURCE_PART_WITH_PORT);
		createEAttribute(connectorEClass, CONNECTOR__SOURCE_REPLICATION_FACTOR);
		createEReference(connectorEClass, CONNECTOR__TARGET);
		createEReference(connectorEClass, CONNECTOR__TARGET_PART_WITH_PORT);
		createEAttribute(connectorEClass, CONNECTOR__TARGET_REPLICATION_FACTOR);

		stateMachineEClass = createEClass(STATE_MACHINE);
		createEReference(stateMachineEClass, STATE_MACHINE__VERTEX);
		createEReference(stateMachineEClass, STATE_MACHINE__TRANSITION);
		createEReference(stateMachineEClass, STATE_MACHINE__REDEFINED_STATE_MACHINE);
		createEReference(stateMachineEClass, STATE_MACHINE__CAPSULE);

		vertexEClass = createEClass(VERTEX);
		createEReference(vertexEClass, VERTEX__STATE);
		createEReference(vertexEClass, VERTEX__INCOMING);
		createEReference(vertexEClass, VERTEX__OUTGOING);
		createEReference(vertexEClass, VERTEX__REDEFINED_VERTEX);
		createEReference(vertexEClass, VERTEX__STATE_MACHINE);

		stateEClass = createEClass(STATE);
		createEReference(stateEClass, STATE__SUBTRANSITION);
		createEReference(stateEClass, STATE__ENTRY);
		createEReference(stateEClass, STATE__EXIT);
		createEReference(stateEClass, STATE__CONNECTION_POINT);
		createEReference(stateEClass, STATE__ENTRY_POINT);
		createEReference(stateEClass, STATE__EXIT_POINT);
		createEReference(stateEClass, STATE__REDEFINED_STATE);
		createEAttribute(stateEClass, STATE__COMPOSITE);
		createEAttribute(stateEClass, STATE__SIMPLE);
		createEReference(stateEClass, STATE__SUBVERTEX);

		transitionEClass = createEClass(TRANSITION);
		createEReference(transitionEClass, TRANSITION__STATE_MACHINE);
		createEReference(transitionEClass, TRANSITION__SOURCE);
		createEReference(transitionEClass, TRANSITION__TARGET);
		createEReference(transitionEClass, TRANSITION__TRIGGER);
		createEReference(transitionEClass, TRANSITION__GUARD);
		createEReference(transitionEClass, TRANSITION__REDEFINED_TRANSITION);
		createEAttribute(transitionEClass, TRANSITION__KIND);
		createEAttribute(transitionEClass, TRANSITION__INTERNAL);
		createEReference(transitionEClass, TRANSITION__EFFECT);
		createEReference(transitionEClass, TRANSITION__STATE);

		triggerEClass = createEClass(TRIGGER);
		createEReference(triggerEClass, TRIGGER__PROTOCOL_MESSAGE);
		createEReference(triggerEClass, TRIGGER__PORT);
		createEReference(triggerEClass, TRIGGER__GUARD);
		createEReference(triggerEClass, TRIGGER__REDEFINED_TRIGGER);
		createEAttribute(triggerEClass, TRIGGER__RECEIVE_ANY_MESSAGE);
		createEReference(triggerEClass, TRIGGER__TRANSITION);

		guardEClass = createEClass(GUARD);
		createEAttribute(guardEClass, GUARD__BODIES);
		createEReference(guardEClass, GUARD__BODY_ENTRY);
		createEReference(guardEClass, GUARD__TRANSITION);
		createEReference(guardEClass, GUARD__REDEFINED_GUARD);
		createEReference(guardEClass, GUARD__TRIGGER);

		opaqueBehaviorEClass = createEClass(OPAQUE_BEHAVIOR);
		createEAttribute(opaqueBehaviorEClass, OPAQUE_BEHAVIOR__BODIES);
		createEReference(opaqueBehaviorEClass, OPAQUE_BEHAVIOR__BODY_ENTRY);
		createEReference(opaqueBehaviorEClass, OPAQUE_BEHAVIOR__ENTERED_STATE);
		createEReference(opaqueBehaviorEClass, OPAQUE_BEHAVIOR__EXITED_STATE);
		createEReference(opaqueBehaviorEClass, OPAQUE_BEHAVIOR__REDEFINED_BEHAVIOR);
		createEReference(opaqueBehaviorEClass, OPAQUE_BEHAVIOR__TRANSITION);

		connectionPointEClass = createEClass(CONNECTION_POINT);
		createEAttribute(connectionPointEClass, CONNECTION_POINT__KIND);
		createEReference(connectionPointEClass, CONNECTION_POINT__REDEFINED_CONNECTION_POINT);

		pseudostateEClass = createEClass(PSEUDOSTATE);
		createEAttribute(pseudostateEClass, PSEUDOSTATE__KIND);
		createEReference(pseudostateEClass, PSEUDOSTATE__REDEFINED_PSEUDOSTATE);

		// Create enums
		umlrtInheritanceKindEEnum = createEEnum(UMLRT_INHERITANCE_KIND);
		umlrtPortKindEEnum = createEEnum(UMLRT_PORT_KIND);
		umlrtCapsulePartKindEEnum = createEEnum(UMLRT_CAPSULE_PART_KIND);
		umlrtConnectionPointKindEEnum = createEEnum(UMLRT_CONNECTION_POINT_KIND);
		umlrtPseudostateKindEEnum = createEEnum(UMLRT_PSEUDOSTATE_KIND);

		// Create data types
		modelEDataType = createEDataType(MODEL);
		streamEDataType = createEDataType(STREAM);
		listEDataType = createEDataType(LIST);
		mapEDataType = createEDataType(MAP);
		illegalStateExceptionEDataType = createEDataType(ILLEGAL_STATE_EXCEPTION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		TypesPackage theTypesPackage = (TypesPackage) EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
		UMLPackage theUMLPackage = (UMLPackage) EPackage.Registry.INSTANCE.getEPackage(UMLPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		UMLRealTimePackage theUMLRealTimePackage = (UMLRealTimePackage) EPackage.Registry.INSTANCE.getEPackage(UMLRealTimePackage.eNS_URI);

		// Create type parameters
		addETypeParameter(streamEDataType, "E"); //$NON-NLS-1$
		addETypeParameter(listEDataType, "E"); //$NON-NLS-1$
		addETypeParameter(mapEDataType, "K"); //$NON-NLS-1$
		addETypeParameter(mapEDataType, "V"); //$NON-NLS-1$

		// Set bounds for type parameters

		// Add supertypes to classes
		packageEClass.getESuperTypes().add(this.getNamedElement());
		capsuleEClass.getESuperTypes().add(this.getClassifier());
		classifierEClass.getESuperTypes().add(this.getNamedElement());
		portEClass.getESuperTypes().add(this.getReplicatedElement());
		replicatedElementEClass.getESuperTypes().add(this.getNamedElement());
		protocolEClass.getESuperTypes().add(this.getClassifier());
		protocolMessageEClass.getESuperTypes().add(this.getNamedElement());
		capsulePartEClass.getESuperTypes().add(this.getReplicatedElement());
		connectorEClass.getESuperTypes().add(this.getNamedElement());
		stateMachineEClass.getESuperTypes().add(this.getNamedElement());
		vertexEClass.getESuperTypes().add(this.getNamedElement());
		stateEClass.getESuperTypes().add(this.getVertex());
		transitionEClass.getESuperTypes().add(this.getNamedElement());
		triggerEClass.getESuperTypes().add(this.getNamedElement());
		guardEClass.getESuperTypes().add(this.getNamedElement());
		opaqueBehaviorEClass.getESuperTypes().add(this.getNamedElement());
		connectionPointEClass.getESuperTypes().add(this.getVertex());
		pseudostateEClass.getESuperTypes().add(this.getVertex());

		// Initialize classes and features; add operations and parameters
		initEClass(namedElementEClass, UMLRTNamedElement.class, "NamedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getNamedElement_Model(), this.getModel(), "model", null, 1, 1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getNamedElement_InheritanceKind(), this.getUMLRTInheritanceKind(), "inheritanceKind", null, 1, 1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getNamedElement_Name(), theTypesPackage.getString(), "name", null, 0, 1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getNamedElement_QualifiedName(), theTypesPackage.getString(), "qualifiedName", null, 0, 1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getNamedElement_IsInherited(), theTypesPackage.getBoolean(), "isInherited", null, 1, 1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getNamedElement_IsVirtualRedefinition(), theTypesPackage.getBoolean(), "isVirtualRedefinition", null, 1, 1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getNamedElement_IsRedefinition(), theTypesPackage.getBoolean(), "isRedefinition", null, 1, 1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getNamedElement_IsExcluded(), theTypesPackage.getBoolean(), "isExcluded", null, 1, 1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getNamedElement_RedefinedElement(), this.getNamedElement(), null, "redefinedElement", null, 0, 1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getNamedElement_RedefinitionContext(), this.getNamedElement(), this.getNamedElement_RedefinableElement(), "redefinitionContext", null, 0, 1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, //$NON-NLS-1$
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEReference(getNamedElement_RedefinableElement(), this.getNamedElement(), this.getNamedElement_RedefinitionContext(), "redefinableElement", null, 0, -1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, //$NON-NLS-1$
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEReference(getNamedElement_InheritedElement(), this.getNamedElement(), null, "inheritedElement", null, 0, 1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getNamedElement_RootDefinition(), this.getNamedElement(), null, "rootDefinition", null, 0, 1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getNamedElement_ExcludedElement(), this.getNamedElement(), null, "excludedElement", null, 0, -1, UMLRTNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);

		addEOperation(namedElementEClass, theUMLPackage.getNamedElement(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		EOperation op = addEOperation(namedElementEClass, theTypesPackage.getBoolean(), "redefines", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getNamedElement(), "element", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(namedElementEClass, null, "getRedefinitionOf", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		ETypeParameter t1 = addETypeParameter(op, "T"); //$NON-NLS-1$
		EGenericType g1 = createEGenericType(this.getNamedElement());
		t1.getEBounds().add(g1);
		g1 = createEGenericType(t1);
		addEParameter(op, g1, "element", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(t1);
		initEOperation(op, g1);

		addEOperation(namedElementEClass, this.getNamedElement(), "getRedefinitionChain", 1, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(namedElementEClass, null, "getExcludedElement", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		t1 = addETypeParameter(op, "T"); //$NON-NLS-1$
		g1 = createEGenericType(this.getNamedElement());
		t1.getEBounds().add(g1);
		addEParameter(op, theTypesPackage.getString(), "name", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(theEcorePackage.getEJavaClass());
		EGenericType g2 = createEGenericType(t1);
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "type", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(t1);
		initEOperation(op, g1);

		op = addEOperation(namedElementEClass, null, "allRedefinitions", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getStream());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		EGenericType g3 = createEGenericType(this.getNamedElement());
		g2.setEUpperBound(g3);
		initEOperation(op, g1);

		addEOperation(namedElementEClass, theTypesPackage.getBoolean(), "exclude", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(namedElementEClass, theTypesPackage.getBoolean(), "reinherit", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(namedElementEClass, null, "reify", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(namedElementEClass, null, "destroy", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(packageEClass, UMLRTPackage.class, "Package", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getPackage_NestedPackage(), this.getPackage(), this.getPackage_NestingPackage(), "nestedPackage", null, 0, -1, UMLRTPackage.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getPackage_NestingPackage(), this.getPackage(), this.getPackage_NestedPackage(), "nestingPackage", null, 0, 1, UMLRTPackage.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getPackage_Capsule(), this.getCapsule(), this.getCapsule_Package(), "capsule", null, 0, -1, UMLRTPackage.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getPackage_Protocol(), this.getProtocol(), this.getProtocol_Package(), "protocol", null, 0, -1, UMLRTPackage.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);

		addEOperation(packageEClass, theUMLPackage.getPackage(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(packageEClass, this.getPackage(), "createNestedPackage", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "name", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(packageEClass, this.getCapsule(), "createCapsule", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "name", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(packageEClass, this.getProtocol(), "createProtocol", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "name", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(capsuleEClass, UMLRTCapsule.class, "Capsule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getCapsule_Superclass(), this.getCapsule(), this.getCapsule_Subclass(), "superclass", null, 0, 1, UMLRTCapsule.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getCapsule_Subclass(), this.getCapsule(), this.getCapsule_Superclass(), "subclass", null, 0, -1, UMLRTCapsule.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getCapsule_Port(), this.getPort(), this.getPort_Capsule(), "port", null, 0, -1, UMLRTCapsule.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getCapsule_CapsulePart(), this.getCapsulePart(), this.getCapsulePart_Capsule(), "capsulePart", null, 0, -1, UMLRTCapsule.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getCapsule_Connector(), this.getConnector(), this.getConnector_Capsule(), "connector", null, 0, -1, UMLRTCapsule.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		g1 = createEGenericType(this.getStream());
		g2 = createEGenericType(this.getCapsule());
		g1.getETypeArguments().add(g2);
		initEAttribute(getCapsule_Hierarchy(), g1, "hierarchy", null, 1, 1, UMLRTCapsule.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getCapsule_StateMachine(), this.getStateMachine(), this.getStateMachine_Capsule(), "stateMachine", null, 0, 1, UMLRTCapsule.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getCapsule_Package(), this.getPackage(), this.getPackage_Capsule(), "package", null, 1, 1, UMLRTCapsule.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);

		addEOperation(capsuleEClass, theUMLPackage.getClass_(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(capsuleEClass, this.getCapsule(), "getAncestry", 1, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(capsuleEClass, this.getPort(), "getPorts", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getBoolean(), "withExclusions", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(capsuleEClass, this.getCapsulePart(), "getCapsuleParts", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getBoolean(), "withExclusions", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(capsuleEClass, this.getConnector(), "getConnectors", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getBoolean(), "withExclusions", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(capsuleEClass, this.getPort(), "createPort", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getProtocol(), "type", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(capsuleEClass, this.getPort(), "createPort", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getProtocol(), "type", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getUMLRTPortKind(), "kind", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(capsuleEClass, this.getCapsulePart(), "createCapsulePart", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getCapsule(), "type", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(capsuleEClass, this.getConnector(), "createConnector", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "name", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getPort(), "source", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getCapsulePart(), "sourcePartWithPort", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getPort(), "target", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getCapsulePart(), "targetPartWithPort", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(capsuleEClass, this.getStateMachine(), "createStateMachine", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(classifierEClass, UMLRTClassifier.class, "Classifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getClassifier_General(), this.getClassifier(), this.getClassifier_Specific(), "general", null, 0, 1, UMLRTClassifier.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getClassifier_Specific(), this.getClassifier(), this.getClassifier_General(), "specific", null, 0, -1, UMLRTClassifier.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);

		addEOperation(classifierEClass, theUMLPackage.getClassifier(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(classifierEClass, this.getPackage(), "getPackage", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(classifierEClass, null, "getAncestry", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getList());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType(this.getClassifier());
		g2.setEUpperBound(g3);
		initEOperation(op, g1);

		op = addEOperation(classifierEClass, null, "getHierarchy", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getStream());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType(this.getClassifier());
		g2.setEUpperBound(g3);
		initEOperation(op, g1);

		op = addEOperation(classifierEClass, theTypesPackage.getBoolean(), "isSuperTypeOf", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getClassifier(), "classifier", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(classifierEClass, null, "setGeneral", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getClassifier(), "general", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(portEClass, UMLRTPort.class, "Port", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPort_Kind(), this.getUMLRTPortKind(), "kind", null, 1, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPort_RedefinedPort(), this.getPort(), null, "redefinedPort", null, 0, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPort_Type(), this.getProtocol(), null, "type", null, 1, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPort_PartsWithPort(), this.getCapsulePart(), null, "partsWithPort", null, 0, -1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPort_Service(), theTypesPackage.getBoolean(), "service", null, 1, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPort_Behavior(), theTypesPackage.getBoolean(), "behavior", null, 1, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPort_Conjugated(), theTypesPackage.getBoolean(), "conjugated", null, 1, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPort_Wired(), theTypesPackage.getBoolean(), "wired", null, 1, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPort_Publish(), theTypesPackage.getBoolean(), "publish", null, 1, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPort_Notification(), theTypesPackage.getBoolean(), "notification", null, 1, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPort_Registration(), theUMLRealTimePackage.getPortRegistrationType(), "registration", null, 1, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPort_RegistrationOverride(), theTypesPackage.getString(), "registrationOverride", null, 0, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPort_IsConnected(), theTypesPackage.getBoolean(), "isConnected", null, 1, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPort_IsConnectedInside(), theTypesPackage.getBoolean(), "isConnectedInside", null, 1, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPort_IsConnectedOutside(), theTypesPackage.getBoolean(), "isConnectedOutside", null, 1, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPort_Connector(), this.getConnector(), null, "connector", null, 0, -1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPort_InsideConnector(), this.getConnector(), null, "insideConnector", null, 0, -1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPort_OutsideConnector(), this.getConnector(), null, "outsideConnector", null, 0, -1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPort_Capsule(), this.getCapsule(), this.getCapsule_Port(), "capsule", null, 1, 1, UMLRTPort.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(portEClass, theUMLPackage.getPort(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(replicatedElementEClass, UMLRTReplicatedElement.class, "ReplicatedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getReplicatedElement_ReplicationFactor(), theTypesPackage.getUnlimitedNatural(), "replicationFactor", null, 1, 1, UMLRTReplicatedElement.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEAttribute(getReplicatedElement_ReplicationFactorAsString(), theTypesPackage.getString(), "replicationFactorAsString", null, 1, 1, UMLRTReplicatedElement.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEAttribute(getReplicatedElement_SymbolicReplicationFactor(), theTypesPackage.getBoolean(), "symbolicReplicationFactor", null, 1, 1, UMLRTReplicatedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);

		addEOperation(replicatedElementEClass, theUMLPackage.getProperty(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(replicatedElementEClass, this.getCapsule(), "getCapsule", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(protocolEClass, UMLRTProtocol.class, "Protocol", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getProtocol_Package(), this.getPackage(), this.getPackage_Protocol(), "package", null, 1, 1, UMLRTProtocol.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getProtocol_SuperProtocol(), this.getProtocol(), this.getProtocol_SubProtocol(), "superProtocol", null, 0, 1, UMLRTProtocol.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getProtocol_SubProtocol(), this.getProtocol(), this.getProtocol_SuperProtocol(), "subProtocol", null, 0, -1, UMLRTProtocol.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getProtocol_Message(), this.getProtocolMessage(), this.getProtocolMessage_Protocol(), "message", null, 0, -1, UMLRTProtocol.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getProtocol_InMessage(), this.getProtocolMessage(), null, "inMessage", null, 0, -1, UMLRTProtocol.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getProtocol_OutMessage(), this.getProtocolMessage(), null, "outMessage", null, 0, -1, UMLRTProtocol.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getProtocol_InOutMessage(), this.getProtocolMessage(), null, "inOutMessage", null, 0, -1, UMLRTProtocol.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEAttribute(getProtocol_IsConjugate(), theTypesPackage.getBoolean(), "isConjugate", null, 1, 1, UMLRTProtocol.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getProtocol_Conjugate(), this.getProtocol(), null, "conjugate", null, 1, 1, UMLRTProtocol.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(this.getStream());
		g2 = createEGenericType(this.getProtocol());
		g1.getETypeArguments().add(g2);
		initEAttribute(getProtocol_Hierarchy(), g1, "hierarchy", null, 1, 1, UMLRTProtocol.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(protocolEClass, theUMLPackage.getCollaboration(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(protocolEClass, theUMLPackage.getAnyReceiveEvent(), "getAnyReceiveEvent", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(protocolEClass, this.getProtocol(), "getAncestry", 1, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(protocolEClass, this.getProtocolMessage(), "getMessages", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theUMLRealTimePackage.getRTMessageKind(), "kind", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(protocolEClass, this.getProtocolMessage(), "getMessages", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theUMLRealTimePackage.getRTMessageKind(), "kind", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getBoolean(), "withExclusions", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(protocolEClass, this.getProtocolMessage(), "getMessages", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getBoolean(), "withExclusions", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(protocolEClass, this.getProtocolMessage(), "createMessage", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theUMLRealTimePackage.getRTMessageKind(), "kind", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "name", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(protocolEClass, this.getProtocolMessage(), "createMessage", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theUMLRealTimePackage.getRTMessageKind(), "kind", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "name", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theUMLPackage.getType(), "dataType", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(protocolEClass, this.getProtocolMessage(), "createMessage", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theUMLRealTimePackage.getRTMessageKind(), "kind", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "name", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "parameterName", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theUMLPackage.getType(), "parameterType", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(protocolMessageEClass, UMLRTProtocolMessage.class, "ProtocolMessage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getProtocolMessage_Kind(), theUMLRealTimePackage.getRTMessageKind(), "kind", null, 1, 1, UMLRTProtocolMessage.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getProtocolMessage_RedefinedMessage(), this.getProtocolMessage(), null, "redefinedMessage", null, 0, 1, UMLRTProtocolMessage.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getProtocolMessage_Parameter(), theUMLPackage.getParameter(), null, "parameter", null, 0, -1, UMLRTProtocolMessage.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEAttribute(getProtocolMessage_IsConjugate(), theTypesPackage.getBoolean(), "isConjugate", null, 1, 1, UMLRTProtocolMessage.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getProtocolMessage_Conjugate(), this.getProtocolMessage(), null, "conjugate", null, 1, 1, UMLRTProtocolMessage.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getProtocolMessage_Protocol(), this.getProtocol(), this.getProtocol_Message(), "protocol", null, 1, 1, UMLRTProtocolMessage.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);

		addEOperation(protocolMessageEClass, theUMLPackage.getOperation(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(protocolMessageEClass, theUMLPackage.getCallEvent(), "toReceiveEvent", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(protocolMessageEClass, theUMLPackage.getParameter(), "createParameter", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "name", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theUMLPackage.getType(), "type", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(capsulePartEClass, UMLRTCapsulePart.class, "CapsulePart", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getCapsulePart_Kind(), this.getUMLRTCapsulePartKind(), "kind", null, 1, 1, UMLRTCapsulePart.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getCapsulePart_Optional(), theTypesPackage.getBoolean(), "optional", null, 1, 1, UMLRTCapsulePart.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getCapsulePart_RedefinedPart(), this.getCapsulePart(), null, "redefinedPart", null, 0, 1, UMLRTCapsulePart.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getCapsulePart_Capsule(), this.getCapsule(), this.getCapsule_CapsulePart(), "capsule", null, 1, 1, UMLRTCapsulePart.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getCapsulePart_Type(), this.getCapsule(), null, "type", null, 1, 1, UMLRTCapsulePart.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(capsulePartEClass, this.getConnector(), "getConnectorsOf", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getPort(), "port", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(capsulePartEClass, this.getConnector(), "getConnectorsOfPorts", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(connectorEClass, UMLRTConnector.class, "Connector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getConnector_RedefinedConnector(), this.getConnector(), null, "redefinedConnector", null, 0, 1, UMLRTConnector.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getConnector_Capsule(), this.getCapsule(), this.getCapsule_Connector(), "capsule", null, 1, 1, UMLRTConnector.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getConnector_Source(), this.getPort(), null, "source", null, 1, 1, UMLRTConnector.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getConnector_SourcePartWithPort(), this.getCapsulePart(), null, "sourcePartWithPort", null, 0, 1, UMLRTConnector.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEAttribute(getConnector_SourceReplicationFactor(), theTypesPackage.getUnlimitedNatural(), "sourceReplicationFactor", null, 1, 1, UMLRTConnector.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getConnector_Target(), this.getPort(), null, "target", null, 1, 1, UMLRTConnector.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getConnector_TargetPartWithPort(), this.getCapsulePart(), null, "targetPartWithPort", null, 0, 1, UMLRTConnector.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEAttribute(getConnector_TargetReplicationFactor(), theTypesPackage.getUnlimitedNatural(), "targetReplicationFactor", null, 1, 1, UMLRTConnector.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);

		addEOperation(connectorEClass, theUMLPackage.getConnector(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(stateMachineEClass, UMLRTStateMachine.class, "StateMachine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getStateMachine_Vertex(), this.getVertex(), this.getVertex_StateMachine(), "vertex", null, 0, -1, UMLRTStateMachine.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getStateMachine_Transition(), this.getTransition(), this.getTransition_StateMachine(), "transition", null, 0, -1, UMLRTStateMachine.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, //$NON-NLS-1$
				IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEReference(getStateMachine_RedefinedStateMachine(), this.getStateMachine(), null, "redefinedStateMachine", null, 0, 1, UMLRTStateMachine.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, //$NON-NLS-1$
				IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEReference(getStateMachine_Capsule(), this.getCapsule(), this.getCapsule_StateMachine(), "capsule", null, 0, 1, UMLRTStateMachine.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);

		addEOperation(stateMachineEClass, theUMLPackage.getStateMachine(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(stateMachineEClass, this.getState(), "createState", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "name", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(stateMachineEClass, this.getPseudostate(), "createPseudostate", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getUMLRTPseudostateKind(), "kind", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(stateMachineEClass, null, "ensureDefaultContents", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(stateMachineEClass, theUMLPackage.getRegion(), "toRegion", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(vertexEClass, UMLRTVertex.class, "Vertex", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getVertex_State(), this.getState(), this.getState_Subvertex(), "state", null, 0, 1, UMLRTVertex.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getVertex_Incoming(), this.getTransition(), null, "incoming", null, 0, -1, UMLRTVertex.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getVertex_Outgoing(), this.getTransition(), null, "outgoing", null, 0, -1, UMLRTVertex.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getVertex_RedefinedVertex(), this.getVertex(), null, "redefinedVertex", null, 0, 1, UMLRTVertex.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getVertex_StateMachine(), this.getStateMachine(), this.getStateMachine_Vertex(), "stateMachine", null, 0, 1, UMLRTVertex.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);

		addEOperation(vertexEClass, theUMLPackage.getVertex(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(vertexEClass, this.getStateMachine(), "containingStateMachine", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(vertexEClass, this.getTransition(), "createTransitionTo", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getVertex(), "target", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(stateEClass, UMLRTState.class, "State", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getState_Subtransition(), this.getTransition(), this.getTransition_State(), "subtransition", null, 0, -1, UMLRTState.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getState_Entry(), this.getOpaqueBehavior(), this.getOpaqueBehavior_EnteredState(), "entry", null, 0, 1, UMLRTState.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getState_Exit(), this.getOpaqueBehavior(), this.getOpaqueBehavior_ExitedState(), "exit", null, 0, 1, UMLRTState.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getState_ConnectionPoint(), this.getConnectionPoint(), null, "connectionPoint", null, 0, -1, UMLRTState.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getState_EntryPoint(), this.getConnectionPoint(), null, "entryPoint", null, 0, -1, UMLRTState.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getState_ExitPoint(), this.getConnectionPoint(), null, "exitPoint", null, 0, -1, UMLRTState.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getState_RedefinedState(), this.getState(), null, "redefinedState", null, 0, 1, UMLRTState.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getState_Composite(), theTypesPackage.getBoolean(), "composite", null, 1, 1, UMLRTState.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getState_Simple(), theTypesPackage.getBoolean(), "simple", null, 1, 1, UMLRTState.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getState_Subvertex(), this.getVertex(), this.getVertex_State(), "subvertex", null, 0, -1, UMLRTState.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);

		addEOperation(stateEClass, theUMLPackage.getState(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(stateEClass, this.getState(), "createSubstate", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "name", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(stateEClass, this.getPseudostate(), "createSubpseudostate", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getUMLRTPseudostateKind(), "kind", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(stateEClass, this.getConnectionPoint(), "createConnectionPoint", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getUMLRTConnectionPointKind(), "kind", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(stateEClass, theUMLPackage.getRegion(), "toRegion", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(stateEClass, this.getOpaqueBehavior(), "createEntry", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "language", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "body", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(stateEClass, this.getOpaqueBehavior(), "createExit", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "language", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "body", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(transitionEClass, UMLRTTransition.class, "Transition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTransition_StateMachine(), this.getStateMachine(), this.getStateMachine_Transition(), "stateMachine", null, 0, 1, UMLRTTransition.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, //$NON-NLS-1$
				IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEReference(getTransition_Source(), this.getVertex(), null, "source", null, 1, 1, UMLRTTransition.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getTransition_Target(), this.getVertex(), null, "target", null, 1, 1, UMLRTTransition.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getTransition_Trigger(), this.getTrigger(), this.getTrigger_Transition(), "trigger", null, 0, -1, UMLRTTransition.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getTransition_Guard(), this.getGuard(), this.getGuard_Transition(), "guard", null, 0, 1, UMLRTTransition.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getTransition_RedefinedTransition(), this.getTransition(), null, "redefinedTransition", null, 0, 1, UMLRTTransition.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEAttribute(getTransition_Kind(), theUMLPackage.getTransitionKind(), "kind", "external", 1, 1, UMLRTTransition.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getTransition_Internal(), theTypesPackage.getBoolean(), "internal", null, 1, 1, UMLRTTransition.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getTransition_Effect(), this.getOpaqueBehavior(), this.getOpaqueBehavior_Transition(), "effect", null, 0, 1, UMLRTTransition.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getTransition_State(), this.getState(), this.getState_Subtransition(), "state", null, 0, 1, UMLRTTransition.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);

		addEOperation(transitionEClass, theUMLPackage.getTransition(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(transitionEClass, this.getStateMachine(), "containingStateMachine", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(transitionEClass, this.getTrigger(), "createTrigger", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getProtocolMessage(), "protocolMessage", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getPort(), "port", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(transitionEClass, this.getGuard(), "createGuard", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "language", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "body", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(transitionEClass, this.getOpaqueBehavior(), "createEffect", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "language", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "body", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(triggerEClass, UMLRTTrigger.class, "Trigger", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTrigger_ProtocolMessage(), this.getProtocolMessage(), null, "protocolMessage", null, 0, 1, UMLRTTrigger.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getTrigger_Port(), this.getPort(), null, "port", null, 1, -1, UMLRTTrigger.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getTrigger_Guard(), this.getGuard(), this.getGuard_Trigger(), "guard", null, 0, 1, UMLRTTrigger.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getTrigger_RedefinedTrigger(), this.getTrigger(), null, "redefinedTrigger", null, 0, 1, UMLRTTrigger.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEAttribute(getTrigger_ReceiveAnyMessage(), theTypesPackage.getBoolean(), "receiveAnyMessage", null, 1, 1, UMLRTTrigger.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getTrigger_Transition(), this.getTransition(), this.getTransition_Trigger(), "transition", null, 1, 1, UMLRTTrigger.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);

		addEOperation(triggerEClass, theUMLPackage.getTrigger(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(triggerEClass, this.getGuard(), "createGuard", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "language", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "body", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(triggerEClass, this.getPort(), "getPort", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEException(op, this.getIllegalStateException());

		op = addEOperation(triggerEClass, null, "setPort", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getPort(), "port", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(triggerEClass, theTypesPackage.getBoolean(), "hasMultiplePorts", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(guardEClass, UMLRTGuard.class, "Guard", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		g1 = createEGenericType(this.getMap());
		g2 = createEGenericType(theTypesPackage.getString());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theTypesPackage.getString());
		g1.getETypeArguments().add(g2);
		initEAttribute(getGuard_Bodies(), g1, "bodies", null, 1, 1, UMLRTGuard.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getGuard_BodyEntry(), theEcorePackage.getEStringToStringMapEntry(), null, "bodyEntry", null, 0, -1, UMLRTGuard.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getGuard_Transition(), this.getTransition(), this.getTransition_Guard(), "transition", null, 0, 1, UMLRTGuard.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getGuard_RedefinedGuard(), this.getGuard(), null, "redefinedGuard", null, 0, 1, UMLRTGuard.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getGuard_Trigger(), this.getTrigger(), this.getTrigger_Guard(), "trigger", null, 0, 1, UMLRTGuard.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(guardEClass, theUMLPackage.getConstraint(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(opaqueBehaviorEClass, UMLRTOpaqueBehavior.class, "OpaqueBehavior", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		g1 = createEGenericType(this.getMap());
		g2 = createEGenericType(theTypesPackage.getString());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theTypesPackage.getString());
		g1.getETypeArguments().add(g2);
		initEAttribute(getOpaqueBehavior_Bodies(), g1, "bodies", null, 1, 1, UMLRTOpaqueBehavior.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getOpaqueBehavior_BodyEntry(), theEcorePackage.getEStringToStringMapEntry(), null, "bodyEntry", null, 0, -1, UMLRTOpaqueBehavior.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, //$NON-NLS-1$
				IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEReference(getOpaqueBehavior_EnteredState(), this.getState(), this.getState_Entry(), "enteredState", null, 0, 1, UMLRTOpaqueBehavior.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getOpaqueBehavior_ExitedState(), this.getState(), this.getState_Exit(), "exitedState", null, 0, 1, UMLRTOpaqueBehavior.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getOpaqueBehavior_RedefinedBehavior(), this.getOpaqueBehavior(), null, "redefinedBehavior", null, 0, 1, UMLRTOpaqueBehavior.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getOpaqueBehavior_Transition(), this.getTransition(), this.getTransition_Effect(), "transition", null, 0, 1, UMLRTOpaqueBehavior.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, //$NON-NLS-1$
				IS_UNIQUE, IS_DERIVED, !IS_ORDERED);

		addEOperation(opaqueBehaviorEClass, theUMLPackage.getOpaqueBehavior(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(connectionPointEClass, UMLRTConnectionPoint.class, "ConnectionPoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getConnectionPoint_Kind(), this.getUMLRTConnectionPointKind(), "kind", null, 1, 1, UMLRTConnectionPoint.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getConnectionPoint_RedefinedConnectionPoint(), this.getConnectionPoint(), null, "redefinedConnectionPoint", null, 0, 1, UMLRTConnectionPoint.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, //$NON-NLS-1$
				!IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);

		addEOperation(connectionPointEClass, theUMLPackage.getPseudostate(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(pseudostateEClass, UMLRTPseudostate.class, "Pseudostate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPseudostate_Kind(), this.getUMLRTPseudostateKind(), "kind", null, 1, 1, UMLRTPseudostate.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPseudostate_RedefinedPseudostate(), this.getPseudostate(), null, "redefinedPseudostate", null, 0, 1, UMLRTPseudostate.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);

		addEOperation(pseudostateEClass, theUMLPackage.getPseudostate(), "toUML", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(umlrtInheritanceKindEEnum, UMLRTInheritanceKind.class, "UMLRTInheritanceKind"); //$NON-NLS-1$
		addEEnumLiteral(umlrtInheritanceKindEEnum, UMLRTInheritanceKind.INHERITED);
		addEEnumLiteral(umlrtInheritanceKindEEnum, UMLRTInheritanceKind.REDEFINED);
		addEEnumLiteral(umlrtInheritanceKindEEnum, UMLRTInheritanceKind.EXCLUDED);
		addEEnumLiteral(umlrtInheritanceKindEEnum, UMLRTInheritanceKind.NONE);

		initEEnum(umlrtPortKindEEnum, UMLRTPortKind.class, "UMLRTPortKind"); //$NON-NLS-1$
		addEEnumLiteral(umlrtPortKindEEnum, UMLRTPortKind.EXTERNAL_BEHAVIOR);
		addEEnumLiteral(umlrtPortKindEEnum, UMLRTPortKind.INTERNAL_BEHAVIOR);
		addEEnumLiteral(umlrtPortKindEEnum, UMLRTPortKind.RELAY);
		addEEnumLiteral(umlrtPortKindEEnum, UMLRTPortKind.SAP);
		addEEnumLiteral(umlrtPortKindEEnum, UMLRTPortKind.SPP);
		addEEnumLiteral(umlrtPortKindEEnum, UMLRTPortKind.NULL);

		initEEnum(umlrtCapsulePartKindEEnum, UMLRTCapsulePartKind.class, "UMLRTCapsulePartKind"); //$NON-NLS-1$
		addEEnumLiteral(umlrtCapsulePartKindEEnum, UMLRTCapsulePartKind.FIXED);
		addEEnumLiteral(umlrtCapsulePartKindEEnum, UMLRTCapsulePartKind.OPTIONAL);
		addEEnumLiteral(umlrtCapsulePartKindEEnum, UMLRTCapsulePartKind.PLUG_IN);
		addEEnumLiteral(umlrtCapsulePartKindEEnum, UMLRTCapsulePartKind.NULL);

		initEEnum(umlrtConnectionPointKindEEnum, UMLRTConnectionPointKind.class, "UMLRTConnectionPointKind"); //$NON-NLS-1$
		addEEnumLiteral(umlrtConnectionPointKindEEnum, UMLRTConnectionPointKind.ENTRY);
		addEEnumLiteral(umlrtConnectionPointKindEEnum, UMLRTConnectionPointKind.EXIT);

		initEEnum(umlrtPseudostateKindEEnum, UMLRTPseudostateKind.class, "UMLRTPseudostateKind"); //$NON-NLS-1$
		addEEnumLiteral(umlrtPseudostateKindEEnum, UMLRTPseudostateKind.INITIAL);
		addEEnumLiteral(umlrtPseudostateKindEEnum, UMLRTPseudostateKind.HISTORY);
		addEEnumLiteral(umlrtPseudostateKindEEnum, UMLRTPseudostateKind.CHOICE);
		addEEnumLiteral(umlrtPseudostateKindEEnum, UMLRTPseudostateKind.JUNCTION);

		// Initialize data types
		initEDataType(modelEDataType, UMLRTModel.class, "Model", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(streamEDataType, Stream.class, "Stream", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(listEDataType, List.class, "List", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(mapEDataType, Map.class, "Map", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(illegalStateExceptionEDataType, IllegalStateException.class, "IllegalStateException", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://schema.omg.org/spec/MOF/2.0/emof.xml#Property.oppositeRoleName
		createEmofAnnotations();
		// union
		createUnionAnnotations();
		// http://www.eclipse.org/uml2/2.0.0/UML
		createUMLAnnotations();
		// redefines
		createRedefinesAnnotations();
		// subsets
		createSubsetsAnnotations();
		// duplicates
		createDuplicatesAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/uml2/2.0.0/UML</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createUMLAnnotations() {
		String source = "http://www.eclipse.org/uml2/2.0.0/UML"; //$NON-NLS-1$
		addAnnotation(umlrtInheritanceKindEEnum,
				source,
				new String[] {
						"originalName", "InheritanceKind" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getPort_PartsWithPort(),
				source,
				new String[] {
						"originalName", "partWithPort" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getPort_Service(),
				source,
				new String[] {
						"originalName", "isService" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getPort_Behavior(),
				source,
				new String[] {
						"originalName", "isBehavior" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getPort_Conjugated(),
				source,
				new String[] {
						"originalName", "isConjugated" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getPort_Wired(),
				source,
				new String[] {
						"originalName", "isWired" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getPort_Publish(),
				source,
				new String[] {
						"originalName", "isPublish" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getPort_Notification(),
				source,
				new String[] {
						"originalName", "isNotification" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getReplicatedElement_SymbolicReplicationFactor(),
				source,
				new String[] {
						"originalName", "isSymbolicReplicationFactor" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(umlrtPortKindEEnum,
				source,
				new String[] {
						"originalName", "PortKind" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getCapsulePart_Optional(),
				source,
				new String[] {
						"originalName", "isOptional" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(umlrtCapsulePartKindEEnum,
				source,
				new String[] {
						"originalName", "CapsulePartKind" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(umlrtCapsulePartKindEEnum.getELiterals().get(2),
				source,
				new String[] {
						"originalName", "plugin" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getState_Composite(),
				source,
				new String[] {
						"originalName", "isComposite" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getState_Simple(),
				source,
				new String[] {
						"originalName", "isSimple" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getTransition_Internal(),
				source,
				new String[] {
						"originalName", "isInternal" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getTrigger_ReceiveAnyMessage(),
				source,
				new String[] {
						"originalName", "isReceiveAnyMessage" //$NON-NLS-1$ //$NON-NLS-2$
				});
	}

	/**
	 * Initializes the annotations for <b>redefines</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createRedefinesAnnotations() {
		String source = "redefines"; //$NON-NLS-1$
		addAnnotation(packageEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(capsuleEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Classifier/toUML") //$NON-NLS-1$
				});
		addAnnotation(capsuleEClass.getEOperations().get(1),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Classifier/getAncestry") //$NON-NLS-1$
				});
		addAnnotation(classifierEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(portEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//ReplicatedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(replicatedElementEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(protocolEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Classifier/toUML") //$NON-NLS-1$
				});
		addAnnotation(protocolEClass.getEOperations().get(2),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Classifier/getAncestry") //$NON-NLS-1$
				});
		addAnnotation(protocolEClass.getEOperations().get(3),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(protocolMessageEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(protocolMessageEClass.getEOperations().get(1),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(connectorEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(stateMachineEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(vertexEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(stateEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Vertex/toUML") //$NON-NLS-1$
				});
		addAnnotation(transitionEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(triggerEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(guardEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(opaqueBehaviorEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/toUML") //$NON-NLS-1$
				});
		addAnnotation(connectionPointEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Vertex/toUML") //$NON-NLS-1$
				});
		addAnnotation(pseudostateEClass.getEOperations().get(0),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Vertex/toUML") //$NON-NLS-1$
				});
	}


	/**
	 * Initializes the annotations for <b>http://schema.omg.org/spec/MOF/2.0/emof.xml#Property.oppositeRoleName</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createEmofAnnotations() {
		String source = "http://schema.omg.org/spec/MOF/2.0/emof.xml#Property.oppositeRoleName"; //$NON-NLS-1$
		addAnnotation(getNamedElement_RedefinedElement(),
				source,
				new String[] {
						"body", "redefiningElement" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getNamedElement_InheritedElement(),
				source,
				new String[] {
						"body", "inheritingElement" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getNamedElement_RootDefinition(),
				source,
				new String[] {
						"body", "redefinitionTree" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getNamedElement_ExcludedElement(),
				source,
				new String[] {
						"body", "excludingElement" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getPort_RedefinedPort(),
				source,
				new String[] {
						"body", "redefiningPort" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getPort_PartsWithPort(),
				source,
				new String[] {
						"body", "portOnPart" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getPort_InsideConnector(),
				source,
				new String[] {
						"body", "inside" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getPort_OutsideConnector(),
				source,
				new String[] {
						"body", "outside" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getProtocol_InMessage(),
				source,
				new String[] {
						"body", "inProtocol" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getProtocol_OutMessage(),
				source,
				new String[] {
						"body", "outProtocol" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getProtocol_InOutMessage(),
				source,
				new String[] {
						"body", "inOutProtocol" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getProtocol_Conjugate(),
				source,
				new String[] {
						"body", "base" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getProtocolMessage_RedefinedMessage(),
				source,
				new String[] {
						"body", "redefiningMessage" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getProtocolMessage_Parameter(),
				source,
				new String[] {
						"body", "message" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getProtocolMessage_Conjugate(),
				source,
				new String[] {
						"body", "base" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getCapsulePart_RedefinedPart(),
				source,
				new String[] {
						"body", "redefiningPart" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getConnector_RedefinedConnector(),
				source,
				new String[] {
						"body", "redefiningConnector" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getConnector_Source(),
				source,
				new String[] {
						"body", "sourceOf" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getConnector_SourcePartWithPort(),
				source,
				new String[] {
						"body", "sourceOf" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getConnector_Target(),
				source,
				new String[] {
						"body", "targetOf" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getConnector_TargetPartWithPort(),
				source,
				new String[] {
						"body", "targetOf" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getStateMachine_RedefinedStateMachine(),
				source,
				new String[] {
						"body", "redefiningStateMachine" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getVertex_Incoming(),
				source,
				new String[] {
						"body", "target" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getVertex_Outgoing(),
				source,
				new String[] {
						"body", "source" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getVertex_RedefinedVertex(),
				source,
				new String[] {
						"body", "redefiningVertex" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getState_EntryPoint(),
				source,
				new String[] {
						"body", "entryTo" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getState_ExitPoint(),
				source,
				new String[] {
						"body", "exitFrom" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getState_RedefinedState(),
				source,
				new String[] {
						"body", "redefiningState" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getTransition_Source(),
				source,
				new String[] {
						"body", "outgoing" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getTransition_Target(),
				source,
				new String[] {
						"body", "incoming" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getTransition_RedefinedTransition(),
				source,
				new String[] {
						"body", "redefiningTransition" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getTrigger_ProtocolMessage(),
				source,
				new String[] {
						"body", "trigger" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getTrigger_Port(),
				source,
				new String[] {
						"body", "trigger" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getTrigger_RedefinedTrigger(),
				source,
				new String[] {
						"body", "redefiningTrigger" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getGuard_BodyEntry(),
				source,
				new String[] {
						"body", "guard" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getGuard_RedefinedGuard(),
				source,
				new String[] {
						"body", "redefiningGuard" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getOpaqueBehavior_BodyEntry(),
				source,
				new String[] {
						"body", "behavior" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getOpaqueBehavior_RedefinedBehavior(),
				source,
				new String[] {
						"body", "redefiningBehavior" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getConnectionPoint_RedefinedConnectionPoint(),
				source,
				new String[] {
						"body", "redefiningConnectionPoint" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getPseudostate_RedefinedPseudostate(),
				source,
				new String[] {
						"body", "redefiningPseudostate" //$NON-NLS-1$ //$NON-NLS-2$
				});
	}


	/**
	 * Initializes the annotations for <b>union</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createUnionAnnotations() {
		String source = "union"; //$NON-NLS-1$
		addAnnotation(getNamedElement_RedefinedElement(),
				source,
				new String[] {
				});
		addAnnotation(getNamedElement_RedefinitionContext(),
				source,
				new String[] {
				});
		addAnnotation(getNamedElement_RedefinableElement(),
				source,
				new String[] {
				});
		addAnnotation(getClassifier_General(),
				source,
				new String[] {
				});
		addAnnotation(getClassifier_Specific(),
				source,
				new String[] {
				});
		addAnnotation(getPort_Connector(),
				source,
				new String[] {
				});
		addAnnotation(getProtocol_Message(),
				source,
				new String[] {
				});
		addAnnotation(getVertex_RedefinedVertex(),
				source,
				new String[] {
				});
	}


	/**
	 * Initializes the annotations for <b>subsets</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createSubsetsAnnotations() {
		String source = "subsets"; //$NON-NLS-1$
		addAnnotation(getCapsule_Superclass(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Classifier/general") //$NON-NLS-1$
				});
		addAnnotation(getCapsule_Subclass(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Classifier/specific") //$NON-NLS-1$
				});
		addAnnotation(getCapsule_Port(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getCapsule_CapsulePart(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getCapsule_Connector(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getCapsule_StateMachine(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getPort_RedefinedPort(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinedElement") //$NON-NLS-1$
				});
		addAnnotation(getPort_InsideConnector(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Port/connector") //$NON-NLS-1$
				});
		addAnnotation(getPort_OutsideConnector(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Port/connector") //$NON-NLS-1$
				});
		addAnnotation(getPort_Capsule(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getProtocol_SuperProtocol(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Classifier/general") //$NON-NLS-1$
				});
		addAnnotation(getProtocol_SubProtocol(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Classifier/specific") //$NON-NLS-1$
				});
		addAnnotation(getProtocol_Message(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getProtocol_InMessage(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Protocol/message") //$NON-NLS-1$
				});
		addAnnotation(getProtocol_OutMessage(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Protocol/message") //$NON-NLS-1$
				});
		addAnnotation(getProtocol_InOutMessage(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Protocol/message") //$NON-NLS-1$
				});
		addAnnotation(getProtocolMessage_RedefinedMessage(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinedElement") //$NON-NLS-1$
				});
		addAnnotation(getProtocolMessage_Protocol(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getCapsulePart_RedefinedPart(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinedElement") //$NON-NLS-1$
				});
		addAnnotation(getCapsulePart_Capsule(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getConnector_RedefinedConnector(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinedElement") //$NON-NLS-1$
				});
		addAnnotation(getConnector_Capsule(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getStateMachine_Vertex(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getStateMachine_Transition(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getStateMachine_RedefinedStateMachine(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinedElement") //$NON-NLS-1$
				});
		addAnnotation(getStateMachine_Capsule(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getVertex_State(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getVertex_RedefinedVertex(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinedElement") //$NON-NLS-1$
				});
		addAnnotation(getVertex_StateMachine(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getState_Subtransition(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getState_Entry(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getState_Exit(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getState_ConnectionPoint(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getState_EntryPoint(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//State/connectionPoint") //$NON-NLS-1$
				});
		addAnnotation(getState_ExitPoint(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//State/connectionPoint") //$NON-NLS-1$
				});
		addAnnotation(getState_RedefinedState(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Vertex/redefinedVertex") //$NON-NLS-1$
				});
		addAnnotation(getState_Subvertex(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getTransition_StateMachine(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getTransition_Trigger(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getTransition_Guard(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getTransition_RedefinedTransition(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinedElement") //$NON-NLS-1$
				});
		addAnnotation(getTransition_Effect(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinableElement") //$NON-NLS-1$
				});
		addAnnotation(getTransition_State(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getTrigger_RedefinedTrigger(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinedElement") //$NON-NLS-1$
				});
		addAnnotation(getTrigger_Transition(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getGuard_Transition(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getGuard_RedefinedGuard(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinedElement") //$NON-NLS-1$
				});
		addAnnotation(getGuard_Trigger(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getOpaqueBehavior_EnteredState(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getOpaqueBehavior_ExitedState(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getOpaqueBehavior_RedefinedBehavior(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinedElement") //$NON-NLS-1$
				});
		addAnnotation(getOpaqueBehavior_Transition(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//NamedElement/redefinitionContext") //$NON-NLS-1$
				});
		addAnnotation(getConnectionPoint_RedefinedConnectionPoint(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Vertex/redefinedVertex") //$NON-NLS-1$
				});
		addAnnotation(getPseudostate_RedefinedPseudostate(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Vertex/redefinedVertex") //$NON-NLS-1$
				});
	}


	/**
	 * Initializes the annotations for <b>duplicates</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createDuplicatesAnnotations() {
		String source = "duplicates"; //$NON-NLS-1$
		addAnnotation(portEClass,
				source,
				new String[] {
				});
		addAnnotation(protocolMessageEClass,
				source,
				new String[] {
				});
		addAnnotation(capsulePartEClass,
				source,
				new String[] {
				});
		addAnnotation(connectorEClass,
				source,
				new String[] {
				});
		addAnnotation(stateMachineEClass,
				source,
				new String[] {
				});
		addAnnotation(stateEClass,
				source,
				new String[] {
				});
		addAnnotation(transitionEClass,
				source,
				new String[] {
				});
		addAnnotation(triggerEClass,
				source,
				new String[] {
				});
		addAnnotation(guardEClass,
				source,
				new String[] {
				});
		addAnnotation(opaqueBehaviorEClass,
				source,
				new String[] {
				});
		addAnnotation(connectionPointEClass,
				source,
				new String[] {
				});
		addAnnotation(pseudostateEClass,
				source,
				new String[] {
				});
	}

} // UMLRTUMLRTPackageImpl
