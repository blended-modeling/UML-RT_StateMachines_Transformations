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
package org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.papyrus.infra.filters.FiltersPackage;

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
 * @see org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ElementtypefilterFactory
 * @model kind="package"
 * @generated
 */
public interface ElementtypefilterPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "elementtypefilter"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/filters/elementtype/1.2.0"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "elementtypefilter"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ElementtypefilterPackage eINSTANCE = org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.impl.ElementtypefilterPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.impl.ParentMatcherFilterImpl <em>Parent Matcher Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.impl.ParentMatcherFilterImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.impl.ElementtypefilterPackageImpl#getParentMatcherFilter()
	 * @generated
	 */
	int PARENT_MATCHER_FILTER = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_MATCHER_FILTER__NAME = FiltersPackage.FILTER__NAME;

	/**
	 * The feature id for the '<em><b>Parent Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_MATCHER_FILTER__PARENT_TYPE = FiltersPackage.FILTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Subtypes Check</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_MATCHER_FILTER__SUBTYPES_CHECK = FiltersPackage.FILTER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Parent Matcher Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_MATCHER_FILTER_FEATURE_COUNT = FiltersPackage.FILTER_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_MATCHER_FILTER___MATCHES__OBJECT = FiltersPackage.FILTER___MATCHES__OBJECT;

	/**
	 * The number of operations of the '<em>Parent Matcher Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_MATCHER_FILTER_OPERATION_COUNT = FiltersPackage.FILTER_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ParentMatcherFilter <em>Parent Matcher Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parent Matcher Filter</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ParentMatcherFilter
	 * @generated
	 */
	EClass getParentMatcherFilter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ParentMatcherFilter#getParentType <em>Parent Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parent Type</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ParentMatcherFilter#getParentType()
	 * @see #getParentMatcherFilter()
	 * @generated
	 */
	EAttribute getParentMatcherFilter_ParentType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ParentMatcherFilter#isSubtypesCheck <em>Subtypes Check</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subtypes Check</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ParentMatcherFilter#isSubtypesCheck()
	 * @see #getParentMatcherFilter()
	 * @generated
	 */
	EAttribute getParentMatcherFilter_SubtypesCheck();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ElementtypefilterFactory getElementtypefilterFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.impl.ParentMatcherFilterImpl <em>Parent Matcher Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.impl.ParentMatcherFilterImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.impl.ElementtypefilterPackageImpl#getParentMatcherFilter()
		 * @generated
		 */
		EClass PARENT_MATCHER_FILTER = eINSTANCE.getParentMatcherFilter();

		/**
		 * The meta object literal for the '<em><b>Parent Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARENT_MATCHER_FILTER__PARENT_TYPE = eINSTANCE.getParentMatcherFilter_ParentType();

		/**
		 * The meta object literal for the '<em><b>Subtypes Check</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARENT_MATCHER_FILTER__SUBTYPES_CHECK = eINSTANCE.getParentMatcherFilter_SubtypesCheck();

	}

} //ElementtypefilterPackage
