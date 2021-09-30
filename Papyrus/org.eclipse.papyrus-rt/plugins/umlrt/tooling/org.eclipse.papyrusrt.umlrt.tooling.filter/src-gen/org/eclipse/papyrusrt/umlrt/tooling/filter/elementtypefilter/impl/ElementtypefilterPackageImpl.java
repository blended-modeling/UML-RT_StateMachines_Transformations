/**
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.infra.filters.FiltersPackage;

import org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ElementtypefilterFactory;
import org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ElementtypefilterPackage;
import org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ParentMatcherFilter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ElementtypefilterPackageImpl extends EPackageImpl implements ElementtypefilterPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parentMatcherFilterEClass = null;

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
	 * @see org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ElementtypefilterPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ElementtypefilterPackageImpl() {
		super(eNS_URI, ElementtypefilterFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ElementtypefilterPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ElementtypefilterPackage init() {
		if (isInited) return (ElementtypefilterPackage)EPackage.Registry.INSTANCE.getEPackage(ElementtypefilterPackage.eNS_URI);

		// Obtain or create and register package
		ElementtypefilterPackageImpl theElementtypefilterPackage = (ElementtypefilterPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ElementtypefilterPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ElementtypefilterPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		FiltersPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theElementtypefilterPackage.createPackageContents();

		// Initialize created meta-data
		theElementtypefilterPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theElementtypefilterPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ElementtypefilterPackage.eNS_URI, theElementtypefilterPackage);
		return theElementtypefilterPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParentMatcherFilter() {
		return parentMatcherFilterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParentMatcherFilter_ParentType() {
		return (EAttribute)parentMatcherFilterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParentMatcherFilter_SubtypesCheck() {
		return (EAttribute)parentMatcherFilterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementtypefilterFactory getElementtypefilterFactory() {
		return (ElementtypefilterFactory)getEFactoryInstance();
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
		parentMatcherFilterEClass = createEClass(PARENT_MATCHER_FILTER);
		createEAttribute(parentMatcherFilterEClass, PARENT_MATCHER_FILTER__PARENT_TYPE);
		createEAttribute(parentMatcherFilterEClass, PARENT_MATCHER_FILTER__SUBTYPES_CHECK);
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
		FiltersPackage theFiltersPackage = (FiltersPackage)EPackage.Registry.INSTANCE.getEPackage(FiltersPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		parentMatcherFilterEClass.getESuperTypes().add(theFiltersPackage.getFilter());

		// Initialize classes, features, and operations; add parameters
		initEClass(parentMatcherFilterEClass, ParentMatcherFilter.class, "ParentMatcherFilter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getParentMatcherFilter_ParentType(), ecorePackage.getEString(), "parentType", null, 1, 1, ParentMatcherFilter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getParentMatcherFilter_SubtypesCheck(), ecorePackage.getEBoolean(), "subtypesCheck", null, 0, 1, ParentMatcherFilter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //ElementtypefilterPackageImpl
