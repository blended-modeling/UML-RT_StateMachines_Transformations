/**
 * Copyright (c) 2016 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdvicePackage;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTNewElementConfigurator;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesFactory;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class UMLRTTypesPackageImpl extends EPackageImpl implements UMLRTTypesPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass umlrtNewElementConfiguratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass umlrtSetTypeAdviceConfigurationEClass = null;

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
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UMLRTTypesPackageImpl() {
		super(eNS_URI, UMLRTTypesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link UMLRTTypesPackage#eINSTANCE}
	 * when that field is accessed. Clients should not invoke it directly.
	 * Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UMLRTTypesPackage init() {
		if (isInited) {
			return (UMLRTTypesPackage) EPackage.Registry.INSTANCE.getEPackage(UMLRTTypesPackage.eNS_URI);
		}

		// Obtain or create and register package
		UMLRTTypesPackageImpl theUMLRTTypesPackage = (UMLRTTypesPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof UMLRTTypesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new UMLRTTypesPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		RuntimeValuesAdvicePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theUMLRTTypesPackage.createPackageContents();

		// Initialize created meta-data
		theUMLRTTypesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUMLRTTypesPackage.freeze();


		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UMLRTTypesPackage.eNS_URI, theUMLRTTypesPackage);
		return theUMLRTTypesPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getUMLRTNewElementConfigurator() {
		return umlrtNewElementConfiguratorEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getUMLRTNewElementConfigurator_DialogTitlePattern() {
		return (EAttribute) umlrtNewElementConfiguratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getUMLRTSetTypeAdviceConfiguration() {
		return umlrtSetTypeAdviceConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getUMLRTSetTypeAdviceConfiguration_ElementType() {
		return (EAttribute) umlrtSetTypeAdviceConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getUMLRTSetTypeAdviceConfiguration_NewTypeViewsToDisplay() {
		return (EReference) umlrtSetTypeAdviceConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getUMLRTSetTypeAdviceConfiguration_NewTypeViews() {
		return (EReference) umlrtSetTypeAdviceConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getUMLRTSetTypeAdviceConfiguration_ElementTypeLabel() {
		return (EAttribute) umlrtSetTypeAdviceConfigurationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTypesFactory getUMLRTTypesFactory() {
		return (UMLRTTypesFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		umlrtNewElementConfiguratorEClass = createEClass(UMLRT_NEW_ELEMENT_CONFIGURATOR);
		createEAttribute(umlrtNewElementConfiguratorEClass, UMLRT_NEW_ELEMENT_CONFIGURATOR__DIALOG_TITLE_PATTERN);

		umlrtSetTypeAdviceConfigurationEClass = createEClass(UMLRT_SET_TYPE_ADVICE_CONFIGURATION);
		createEAttribute(umlrtSetTypeAdviceConfigurationEClass, UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE);
		createEReference(umlrtSetTypeAdviceConfigurationEClass, UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS_TO_DISPLAY);
		createEReference(umlrtSetTypeAdviceConfigurationEClass, UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS);
		createEAttribute(umlrtSetTypeAdviceConfigurationEClass, UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_LABEL);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
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
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		RuntimeValuesAdvicePackage theRuntimeValuesAdvicePackage = (RuntimeValuesAdvicePackage) EPackage.Registry.INSTANCE.getEPackage(RuntimeValuesAdvicePackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		ElementTypesConfigurationsPackage theElementTypesConfigurationsPackage = (ElementTypesConfigurationsPackage) EPackage.Registry.INSTANCE.getEPackage(ElementTypesConfigurationsPackage.eNS_URI);
		ContextsPackage theContextsPackage = (ContextsPackage) EPackage.Registry.INSTANCE.getEPackage(ContextsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		umlrtNewElementConfiguratorEClass.getESuperTypes().add(theRuntimeValuesAdvicePackage.getRuntimeValuesAdviceConfiguration());
		umlrtSetTypeAdviceConfigurationEClass.getESuperTypes().add(theElementTypesConfigurationsPackage.getAbstractAdviceBindingConfiguration());

		// Initialize classes, features, and operations; add parameters
		initEClass(umlrtNewElementConfiguratorEClass, UMLRTNewElementConfigurator.class, "UMLRTNewElementConfigurator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getUMLRTNewElementConfigurator_DialogTitlePattern(), theEcorePackage.getEString(), "dialogTitlePattern", null, 0, 1, UMLRTNewElementConfigurator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, //$NON-NLS-1$
				!IS_DERIVED, IS_ORDERED);

		initEClass(umlrtSetTypeAdviceConfigurationEClass, UMLRTSetTypeAdviceConfiguration.class, "UMLRTSetTypeAdviceConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getUMLRTSetTypeAdviceConfiguration_ElementType(), theEcorePackage.getEString(), "elementType", null, 1, 1, UMLRTSetTypeAdviceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, //$NON-NLS-1$
				IS_ORDERED);
		initEReference(getUMLRTSetTypeAdviceConfiguration_NewTypeViewsToDisplay(), theRuntimeValuesAdvicePackage.getViewToDisplay(), null, "newTypeViewsToDisplay", null, 0, -1, UMLRTSetTypeAdviceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUMLRTSetTypeAdviceConfiguration_NewTypeViews(), theContextsPackage.getView(), null, "newTypeViews", null, 0, -1, UMLRTSetTypeAdviceConfiguration.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, //$NON-NLS-1$
				!IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getUMLRTSetTypeAdviceConfiguration_ElementTypeLabel(), theEcorePackage.getEString(), "elementTypeLabel", null, 0, 1, UMLRTSetTypeAdviceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, //$NON-NLS-1$
				!IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // UMLRTTypesPackageImpl
