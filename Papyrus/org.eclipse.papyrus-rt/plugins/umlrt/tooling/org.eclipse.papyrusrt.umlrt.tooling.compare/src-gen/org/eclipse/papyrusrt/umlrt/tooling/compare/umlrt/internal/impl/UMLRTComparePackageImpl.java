/**
 * Copyright (c) 2016 EclipseSource Services GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Philip Langer (EclipseSource) - Initial API and implementation
 */
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl;

import org.eclipse.emf.compare.diagram.internal.extensions.ExtensionsPackage;
import org.eclipse.emf.compare.uml2.internal.UMLComparePackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageParameterChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTCompareFactory;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTComparePackage;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiff;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class UMLRTComparePackageImpl extends EPackageImpl implements UMLRTComparePackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass umlrtDiffEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass protocolChangeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass protocolMessageChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass protocolMessageParameterChangeEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass umlrtDiagramChangeEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory
	 * method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTComparePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UMLRTComparePackageImpl() {
		super(eNS_URI, UMLRTCompareFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link UMLRTComparePackage#eINSTANCE}
	 * when that field is accessed. Clients should not invoke it directly.
	 * Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UMLRTComparePackage init() {
		if (isInited)
			return (UMLRTComparePackage) EPackage.Registry.INSTANCE.getEPackage(UMLRTComparePackage.eNS_URI);

		// Obtain or create and register package
		UMLRTComparePackageImpl theUMLRTComparePackage = (UMLRTComparePackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof UMLRTComparePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new UMLRTComparePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ExtensionsPackage.eINSTANCE.eClass();
		UMLComparePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theUMLRTComparePackage.createPackageContents();

		// Initialize created meta-data
		theUMLRTComparePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUMLRTComparePackage.freeze();


		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UMLRTComparePackage.eNS_URI, theUMLRTComparePackage);
		return theUMLRTComparePackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUMLRTDiff() {
		return umlrtDiffEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProtocolChange() {
		return protocolChangeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProtocolMessageChange() {
		return protocolMessageChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProtocolMessageParameterChange() {
		return protocolMessageParameterChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUMLRTDiagramChange() {
		return umlrtDiagramChangeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public UMLRTCompareFactory getUMLRTCompareFactory() {
		return (UMLRTCompareFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		umlrtDiffEClass = createEClass(UMLRT_DIFF);

		protocolChangeEClass = createEClass(PROTOCOL_CHANGE);

		protocolMessageChangeEClass = createEClass(PROTOCOL_MESSAGE_CHANGE);

		protocolMessageParameterChangeEClass = createEClass(PROTOCOL_MESSAGE_PARAMETER_CHANGE);

		umlrtDiagramChangeEClass = createEClass(UMLRT_DIAGRAM_CHANGE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		UMLComparePackage theUMLComparePackage = (UMLComparePackage) EPackage.Registry.INSTANCE.getEPackage(UMLComparePackage.eNS_URI);
		ExtensionsPackage theExtensionsPackage = (ExtensionsPackage) EPackage.Registry.INSTANCE.getEPackage(ExtensionsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		umlrtDiffEClass.getESuperTypes().add(theUMLComparePackage.getUMLDiff());
		protocolChangeEClass.getESuperTypes().add(this.getUMLRTDiff());
		protocolMessageChangeEClass.getESuperTypes().add(this.getUMLRTDiff());
		protocolMessageParameterChangeEClass.getESuperTypes().add(this.getUMLRTDiff());
		umlrtDiagramChangeEClass.getESuperTypes().add(theExtensionsPackage.getDiagramChange());

		// Initialize classes and features; add operations and parameters
		initEClass(umlrtDiffEClass, UMLRTDiff.class, "UMLRTDiff", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(protocolChangeEClass, ProtocolChange.class, "ProtocolChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(protocolMessageChangeEClass, ProtocolMessageChange.class, "ProtocolMessageChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(protocolMessageParameterChangeEClass, ProtocolMessageParameterChange.class, "ProtocolMessageParameterChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(umlrtDiagramChangeEClass, UMLRTDiagramChange.class, "UMLRTDiagramChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // UMLRTComparePackageImpl
