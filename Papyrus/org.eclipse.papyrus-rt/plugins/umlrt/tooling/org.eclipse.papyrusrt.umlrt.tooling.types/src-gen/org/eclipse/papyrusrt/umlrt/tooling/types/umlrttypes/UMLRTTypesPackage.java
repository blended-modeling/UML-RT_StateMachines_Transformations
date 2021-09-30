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
package org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdvicePackage;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesFactory
 * @model kind="package"
 * @generated
 */
public interface UMLRTTypesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "umlrttypes"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus-rt/umlrt/types/1.0.0"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "umlrttypes"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	UMLRTTypesPackage eINSTANCE = org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTTypesPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTNewElementConfiguratorImpl <em>UMLRT New Element Configurator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTNewElementConfiguratorImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTTypesPackageImpl#getUMLRTNewElementConfigurator()
	 * @generated
	 */
	int UMLRT_NEW_ELEMENT_CONFIGURATOR = 0;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_NEW_ELEMENT_CONFIGURATOR__IDENTIFIER = RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_NEW_ELEMENT_CONFIGURATOR__DESCRIPTION = RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Before</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_NEW_ELEMENT_CONFIGURATOR__BEFORE = RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__BEFORE;

	/**
	 * The feature id for the '<em><b>After</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_NEW_ELEMENT_CONFIGURATOR__AFTER = RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__AFTER;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_NEW_ELEMENT_CONFIGURATOR__TARGET = RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__TARGET;

	/**
	 * The feature id for the '<em><b>Container Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_NEW_ELEMENT_CONFIGURATOR__CONTAINER_CONFIGURATION = RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__CONTAINER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Matcher Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_NEW_ELEMENT_CONFIGURATOR__MATCHER_CONFIGURATION = RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__MATCHER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Inheritance</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_NEW_ELEMENT_CONFIGURATOR__INHERITANCE = RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__INHERITANCE;

	/**
	 * The feature id for the '<em><b>Views To Display</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_NEW_ELEMENT_CONFIGURATOR__VIEWS_TO_DISPLAY = RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__VIEWS_TO_DISPLAY;

	/**
	 * The feature id for the '<em><b>Dialog Title Pattern</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_NEW_ELEMENT_CONFIGURATOR__DIALOG_TITLE_PATTERN = RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>UMLRT New Element Configurator</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_NEW_ELEMENT_CONFIGURATOR_FEATURE_COUNT = RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>UMLRT New Element Configurator</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_NEW_ELEMENT_CONFIGURATOR_OPERATION_COUNT = RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTSetTypeAdviceConfigurationImpl <em>UMLRT Set Type Advice Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTSetTypeAdviceConfigurationImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTTypesPackageImpl#getUMLRTSetTypeAdviceConfiguration()
	 * @generated
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION = 1;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION__IDENTIFIER = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION__DESCRIPTION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Before</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION__BEFORE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__BEFORE;

	/**
	 * The feature id for the '<em><b>After</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION__AFTER = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__AFTER;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION__TARGET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__TARGET;

	/**
	 * The feature id for the '<em><b>Container Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION__CONTAINER_CONFIGURATION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__CONTAINER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Matcher Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION__MATCHER_CONFIGURATION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__MATCHER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Inheritance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION__INHERITANCE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__INHERITANCE;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>New Type Views To Display</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS_TO_DISPLAY = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>New Type Views</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Element Type Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_LABEL = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>UMLRT Set Type Advice Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION_FEATURE_COUNT = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>UMLRT Set Type Advice Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UMLRT_SET_TYPE_ADVICE_CONFIGURATION_OPERATION_COUNT = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTNewElementConfigurator <em>UMLRT New Element Configurator</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the meta object for class '<em>UMLRT New Element Configurator</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTNewElementConfigurator
	 * @generated
	 */
	EClass getUMLRTNewElementConfigurator();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTNewElementConfigurator#getDialogTitlePattern <em>Dialog Title Pattern</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Dialog Title Pattern</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTNewElementConfigurator#getDialogTitlePattern()
	 * @see #getUMLRTNewElementConfigurator()
	 * @generated
	 */
	EAttribute getUMLRTNewElementConfigurator_DialogTitlePattern();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration <em>UMLRT Set Type Advice Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>UMLRT Set Type Advice Configuration</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration
	 * @generated
	 */
	EClass getUMLRTSetTypeAdviceConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getElementType <em>Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Element Type</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getElementType()
	 * @see #getUMLRTSetTypeAdviceConfiguration()
	 * @generated
	 */
	EAttribute getUMLRTSetTypeAdviceConfiguration_ElementType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getNewTypeViewsToDisplay <em>New Type Views To Display</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>New Type Views To Display</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getNewTypeViewsToDisplay()
	 * @see #getUMLRTSetTypeAdviceConfiguration()
	 * @generated
	 */
	EReference getUMLRTSetTypeAdviceConfiguration_NewTypeViewsToDisplay();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getNewTypeViews <em>New Type Views</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>New Type Views</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getNewTypeViews()
	 * @see #getUMLRTSetTypeAdviceConfiguration()
	 * @generated
	 */
	EReference getUMLRTSetTypeAdviceConfiguration_NewTypeViews();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getElementTypeLabel <em>Element Type Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Element Type Label</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getElementTypeLabel()
	 * @see #getUMLRTSetTypeAdviceConfiguration()
	 * @generated
	 */
	EAttribute getUMLRTSetTypeAdviceConfiguration_ElementTypeLabel();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UMLRTTypesFactory getUMLRTTypesFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTNewElementConfiguratorImpl <em>UMLRT New Element Configurator</em>}' class.
		 * <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTNewElementConfiguratorImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTTypesPackageImpl#getUMLRTNewElementConfigurator()
		 * @generated
		 */
		EClass UMLRT_NEW_ELEMENT_CONFIGURATOR = eINSTANCE.getUMLRTNewElementConfigurator();

		/**
		 * The meta object literal for the '<em><b>Dialog Title Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute UMLRT_NEW_ELEMENT_CONFIGURATOR__DIALOG_TITLE_PATTERN = eINSTANCE.getUMLRTNewElementConfigurator_DialogTitlePattern();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTSetTypeAdviceConfigurationImpl <em>UMLRT Set Type Advice Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTSetTypeAdviceConfigurationImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTTypesPackageImpl#getUMLRTSetTypeAdviceConfiguration()
		 * @generated
		 */
		EClass UMLRT_SET_TYPE_ADVICE_CONFIGURATION = eINSTANCE.getUMLRTSetTypeAdviceConfiguration();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE = eINSTANCE.getUMLRTSetTypeAdviceConfiguration_ElementType();

		/**
		 * The meta object literal for the '<em><b>New Type Views To Display</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS_TO_DISPLAY = eINSTANCE.getUMLRTSetTypeAdviceConfiguration_NewTypeViewsToDisplay();

		/**
		 * The meta object literal for the '<em><b>New Type Views</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS = eINSTANCE.getUMLRTSetTypeAdviceConfiguration_NewTypeViews();

		/**
		 * The meta object literal for the '<em><b>Element Type Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_LABEL = eINSTANCE.getUMLRTSetTypeAdviceConfiguration_ElementTypeLabel();

	}

} // UMLRTTypesPackage
