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
package org.eclipse.papyrusrt.umlrt.uml.internal.facade;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTFactory
 * @generated
 */
public interface UMLRTUMLRTPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "umlrt"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus-rt/2017/UML-RT"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "umlrt"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	UMLRTUMLRTPackage eINSTANCE = org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__MODEL = 0;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__INHERITANCE_KIND = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = 2;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__QUALIFIED_NAME = 3;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__IS_INHERITED = 4;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION = 5;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__IS_REDEFINITION = 6;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__IS_EXCLUDED = 7;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__REDEFINED_ELEMENT = 8;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__REDEFINITION_CONTEXT = 9;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__REDEFINABLE_ELEMENT = 10;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__INHERITED_ELEMENT = 11;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__ROOT_DEFINITION = 12;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__EXCLUDED_ELEMENT = 13;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 14;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPackageImpl <em>Package</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPackageImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getPackage()
	 * @generated
	 */
	int PACKAGE = 1;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__MODEL = NAMED_ELEMENT__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__INHERITANCE_KIND = NAMED_ELEMENT__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__IS_INHERITED = NAMED_ELEMENT__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__IS_VIRTUAL_REDEFINITION = NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__IS_REDEFINITION = NAMED_ELEMENT__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__IS_EXCLUDED = NAMED_ELEMENT__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__REDEFINED_ELEMENT = NAMED_ELEMENT__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__REDEFINITION_CONTEXT = NAMED_ELEMENT__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__REDEFINABLE_ELEMENT = NAMED_ELEMENT__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__INHERITED_ELEMENT = NAMED_ELEMENT__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__ROOT_DEFINITION = NAMED_ELEMENT__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__EXCLUDED_ELEMENT = NAMED_ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Nested Package</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__NESTED_PACKAGE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Nesting Package</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__NESTING_PACKAGE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Capsule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__CAPSULE = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Protocol</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE__PROTOCOL = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Package</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PACKAGE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTClassifierImpl <em>Classifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTClassifierImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getClassifier()
	 * @generated
	 */
	int CLASSIFIER = 3;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__MODEL = NAMED_ELEMENT__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__INHERITANCE_KIND = NAMED_ELEMENT__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__IS_INHERITED = NAMED_ELEMENT__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__IS_VIRTUAL_REDEFINITION = NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__IS_REDEFINITION = NAMED_ELEMENT__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__IS_EXCLUDED = NAMED_ELEMENT__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__REDEFINED_ELEMENT = NAMED_ELEMENT__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__REDEFINITION_CONTEXT = NAMED_ELEMENT__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__REDEFINABLE_ELEMENT = NAMED_ELEMENT__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__INHERITED_ELEMENT = NAMED_ELEMENT__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__ROOT_DEFINITION = NAMED_ELEMENT__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__EXCLUDED_ELEMENT = NAMED_ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>General</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__GENERAL = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specific</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__SPECIFIC = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Classifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl <em>Capsule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getCapsule()
	 * @generated
	 */
	int CAPSULE = 2;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__MODEL = CLASSIFIER__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__INHERITANCE_KIND = CLASSIFIER__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__NAME = CLASSIFIER__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__QUALIFIED_NAME = CLASSIFIER__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__IS_INHERITED = CLASSIFIER__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__IS_VIRTUAL_REDEFINITION = CLASSIFIER__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__IS_REDEFINITION = CLASSIFIER__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__IS_EXCLUDED = CLASSIFIER__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__REDEFINED_ELEMENT = CLASSIFIER__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__REDEFINITION_CONTEXT = CLASSIFIER__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__REDEFINABLE_ELEMENT = CLASSIFIER__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__INHERITED_ELEMENT = CLASSIFIER__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__ROOT_DEFINITION = CLASSIFIER__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__EXCLUDED_ELEMENT = CLASSIFIER__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>General</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__GENERAL = CLASSIFIER__GENERAL;

	/**
	 * The feature id for the '<em><b>Specific</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__SPECIFIC = CLASSIFIER__SPECIFIC;

	/**
	 * The feature id for the '<em><b>Superclass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__SUPERCLASS = CLASSIFIER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Subclass</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__SUBCLASS = CLASSIFIER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Port</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__PORT = CLASSIFIER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Capsule Part</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__CAPSULE_PART = CLASSIFIER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Connector</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__CONNECTOR = CLASSIFIER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Hierarchy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__HIERARCHY = CLASSIFIER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__STATE_MACHINE = CLASSIFIER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Package</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__PACKAGE = CLASSIFIER_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Capsule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_FEATURE_COUNT = CLASSIFIER_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl <em>Protocol</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getProtocol()
	 * @generated
	 */
	int PROTOCOL = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTReplicatedElementImpl <em>Replicated Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTReplicatedElementImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getReplicatedElement()
	 * @generated
	 */
	int REPLICATED_ELEMENT = 5;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__MODEL = NAMED_ELEMENT__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__INHERITANCE_KIND = NAMED_ELEMENT__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__IS_INHERITED = NAMED_ELEMENT__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__IS_VIRTUAL_REDEFINITION = NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__IS_REDEFINITION = NAMED_ELEMENT__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__IS_EXCLUDED = NAMED_ELEMENT__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__REDEFINED_ELEMENT = NAMED_ELEMENT__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__REDEFINITION_CONTEXT = NAMED_ELEMENT__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__REDEFINABLE_ELEMENT = NAMED_ELEMENT__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__INHERITED_ELEMENT = NAMED_ELEMENT__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__ROOT_DEFINITION = NAMED_ELEMENT__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__EXCLUDED_ELEMENT = NAMED_ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Replication Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__REPLICATION_FACTOR = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Replication Factor As String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Symbolic Replication Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT__SYMBOLIC_REPLICATION_FACTOR = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Replicated Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REPLICATED_ELEMENT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getPort()
	 * @generated
	 */
	int PORT = 4;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__MODEL = REPLICATED_ELEMENT__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__INHERITANCE_KIND = REPLICATED_ELEMENT__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__NAME = REPLICATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__QUALIFIED_NAME = REPLICATED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__IS_INHERITED = REPLICATED_ELEMENT__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__IS_VIRTUAL_REDEFINITION = REPLICATED_ELEMENT__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__IS_REDEFINITION = REPLICATED_ELEMENT__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__IS_EXCLUDED = REPLICATED_ELEMENT__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__REDEFINED_ELEMENT = REPLICATED_ELEMENT__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__REDEFINITION_CONTEXT = REPLICATED_ELEMENT__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__REDEFINABLE_ELEMENT = REPLICATED_ELEMENT__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__INHERITED_ELEMENT = REPLICATED_ELEMENT__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__ROOT_DEFINITION = REPLICATED_ELEMENT__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__EXCLUDED_ELEMENT = REPLICATED_ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Replication Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__REPLICATION_FACTOR = REPLICATED_ELEMENT__REPLICATION_FACTOR;

	/**
	 * The feature id for the '<em><b>Replication Factor As String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__REPLICATION_FACTOR_AS_STRING = REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING;

	/**
	 * The feature id for the '<em><b>Symbolic Replication Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__SYMBOLIC_REPLICATION_FACTOR = REPLICATED_ELEMENT__SYMBOLIC_REPLICATION_FACTOR;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__KIND = REPLICATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Redefined Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__REDEFINED_PORT = REPLICATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__TYPE = REPLICATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Parts With Port</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__PARTS_WITH_PORT = REPLICATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Service</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__SERVICE = REPLICATED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Behavior</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__BEHAVIOR = REPLICATED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Conjugated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__CONJUGATED = REPLICATED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Wired</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__WIRED = REPLICATED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Publish</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__PUBLISH = REPLICATED_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Notification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__NOTIFICATION = REPLICATED_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Registration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__REGISTRATION = REPLICATED_ELEMENT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Registration Override</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__REGISTRATION_OVERRIDE = REPLICATED_ELEMENT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Is Connected</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__IS_CONNECTED = REPLICATED_ELEMENT_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Is Connected Inside</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__IS_CONNECTED_INSIDE = REPLICATED_ELEMENT_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Is Connected Outside</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__IS_CONNECTED_OUTSIDE = REPLICATED_ELEMENT_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Connector</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__CONNECTOR = REPLICATED_ELEMENT_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Inside Connector</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__INSIDE_CONNECTOR = REPLICATED_ELEMENT_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Outside Connector</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__OUTSIDE_CONNECTOR = REPLICATED_ELEMENT_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Capsule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__CAPSULE = REPLICATED_ELEMENT_FEATURE_COUNT + 18;

	/**
	 * The number of structural features of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = REPLICATED_ELEMENT_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__MODEL = CLASSIFIER__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__INHERITANCE_KIND = CLASSIFIER__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__NAME = CLASSIFIER__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__QUALIFIED_NAME = CLASSIFIER__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__IS_INHERITED = CLASSIFIER__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__IS_VIRTUAL_REDEFINITION = CLASSIFIER__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__IS_REDEFINITION = CLASSIFIER__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__IS_EXCLUDED = CLASSIFIER__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__REDEFINED_ELEMENT = CLASSIFIER__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__REDEFINITION_CONTEXT = CLASSIFIER__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__REDEFINABLE_ELEMENT = CLASSIFIER__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__INHERITED_ELEMENT = CLASSIFIER__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__ROOT_DEFINITION = CLASSIFIER__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__EXCLUDED_ELEMENT = CLASSIFIER__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>General</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__GENERAL = CLASSIFIER__GENERAL;

	/**
	 * The feature id for the '<em><b>Specific</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__SPECIFIC = CLASSIFIER__SPECIFIC;

	/**
	 * The feature id for the '<em><b>Package</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__PACKAGE = CLASSIFIER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Super Protocol</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__SUPER_PROTOCOL = CLASSIFIER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Sub Protocol</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__SUB_PROTOCOL = CLASSIFIER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Message</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__MESSAGE = CLASSIFIER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>In Message</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__IN_MESSAGE = CLASSIFIER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Out Message</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__OUT_MESSAGE = CLASSIFIER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>In Out Message</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__IN_OUT_MESSAGE = CLASSIFIER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Is Conjugate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__IS_CONJUGATE = CLASSIFIER_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Conjugate</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__CONJUGATE = CLASSIFIER_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Hierarchy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__HIERARCHY = CLASSIFIER_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Protocol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_FEATURE_COUNT = CLASSIFIER_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsulePartImpl <em>Capsule Part</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsulePartImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getCapsulePart()
	 * @generated
	 */
	int CAPSULE_PART = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl <em>Connector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getConnector()
	 * @generated
	 */
	int CONNECTOR = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolMessageImpl <em>Protocol Message</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolMessageImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getProtocolMessage()
	 * @generated
	 */
	int PROTOCOL_MESSAGE = 7;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__MODEL = NAMED_ELEMENT__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__INHERITANCE_KIND = NAMED_ELEMENT__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__IS_INHERITED = NAMED_ELEMENT__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__IS_VIRTUAL_REDEFINITION = NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__IS_REDEFINITION = NAMED_ELEMENT__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__IS_EXCLUDED = NAMED_ELEMENT__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__REDEFINED_ELEMENT = NAMED_ELEMENT__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__REDEFINITION_CONTEXT = NAMED_ELEMENT__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__REDEFINABLE_ELEMENT = NAMED_ELEMENT__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__INHERITED_ELEMENT = NAMED_ELEMENT__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__ROOT_DEFINITION = NAMED_ELEMENT__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__EXCLUDED_ELEMENT = NAMED_ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__KIND = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Redefined Message</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__REDEFINED_MESSAGE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__PARAMETER = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Is Conjugate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__IS_CONJUGATE = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Conjugate</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__CONJUGATE = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Protocol</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE__PROTOCOL = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Protocol Message</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__MODEL = REPLICATED_ELEMENT__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__INHERITANCE_KIND = REPLICATED_ELEMENT__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__NAME = REPLICATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__QUALIFIED_NAME = REPLICATED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__IS_INHERITED = REPLICATED_ELEMENT__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__IS_VIRTUAL_REDEFINITION = REPLICATED_ELEMENT__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__IS_REDEFINITION = REPLICATED_ELEMENT__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__IS_EXCLUDED = REPLICATED_ELEMENT__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__REDEFINED_ELEMENT = REPLICATED_ELEMENT__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__REDEFINITION_CONTEXT = REPLICATED_ELEMENT__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__REDEFINABLE_ELEMENT = REPLICATED_ELEMENT__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__INHERITED_ELEMENT = REPLICATED_ELEMENT__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__ROOT_DEFINITION = REPLICATED_ELEMENT__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__EXCLUDED_ELEMENT = REPLICATED_ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Replication Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__REPLICATION_FACTOR = REPLICATED_ELEMENT__REPLICATION_FACTOR;

	/**
	 * The feature id for the '<em><b>Replication Factor As String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__REPLICATION_FACTOR_AS_STRING = REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING;

	/**
	 * The feature id for the '<em><b>Symbolic Replication Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__SYMBOLIC_REPLICATION_FACTOR = REPLICATED_ELEMENT__SYMBOLIC_REPLICATION_FACTOR;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__KIND = REPLICATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__OPTIONAL = REPLICATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Redefined Part</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__REDEFINED_PART = REPLICATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Capsule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__CAPSULE = REPLICATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__TYPE = REPLICATED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Capsule Part</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART_FEATURE_COUNT = REPLICATED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__MODEL = NAMED_ELEMENT__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__INHERITANCE_KIND = NAMED_ELEMENT__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__IS_INHERITED = NAMED_ELEMENT__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__IS_VIRTUAL_REDEFINITION = NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__IS_REDEFINITION = NAMED_ELEMENT__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__IS_EXCLUDED = NAMED_ELEMENT__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__REDEFINED_ELEMENT = NAMED_ELEMENT__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__REDEFINITION_CONTEXT = NAMED_ELEMENT__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__REDEFINABLE_ELEMENT = NAMED_ELEMENT__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__INHERITED_ELEMENT = NAMED_ELEMENT__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__ROOT_DEFINITION = NAMED_ELEMENT__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__EXCLUDED_ELEMENT = NAMED_ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefined Connector</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__REDEFINED_CONNECTOR = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Capsule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__CAPSULE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__SOURCE = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Source Part With Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__SOURCE_PART_WITH_PORT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Source Replication Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__SOURCE_REPLICATION_FACTOR = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__TARGET = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Target Part With Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__TARGET_PART_WITH_PORT = NAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Target Replication Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__TARGET_REPLICATION_FACTOR = NAMED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateMachineImpl <em>State Machine</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateMachineImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getStateMachine()
	 * @generated
	 */
	int STATE_MACHINE = 10;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__MODEL = NAMED_ELEMENT__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__INHERITANCE_KIND = NAMED_ELEMENT__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__IS_INHERITED = NAMED_ELEMENT__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__IS_VIRTUAL_REDEFINITION = NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__IS_REDEFINITION = NAMED_ELEMENT__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__IS_EXCLUDED = NAMED_ELEMENT__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__REDEFINED_ELEMENT = NAMED_ELEMENT__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__REDEFINITION_CONTEXT = NAMED_ELEMENT__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__REDEFINABLE_ELEMENT = NAMED_ELEMENT__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__INHERITED_ELEMENT = NAMED_ELEMENT__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__ROOT_DEFINITION = NAMED_ELEMENT__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__EXCLUDED_ELEMENT = NAMED_ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Vertex</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__VERTEX = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Transition</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__TRANSITION = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Redefined State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__REDEFINED_STATE_MACHINE = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Capsule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__CAPSULE = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>State Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTVertexImpl <em>Vertex</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTVertexImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getVertex()
	 * @generated
	 */
	int VERTEX = 11;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__MODEL = NAMED_ELEMENT__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__INHERITANCE_KIND = NAMED_ELEMENT__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__IS_INHERITED = NAMED_ELEMENT__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__IS_VIRTUAL_REDEFINITION = NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__IS_REDEFINITION = NAMED_ELEMENT__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__IS_EXCLUDED = NAMED_ELEMENT__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__REDEFINED_ELEMENT = NAMED_ELEMENT__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__REDEFINITION_CONTEXT = NAMED_ELEMENT__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__REDEFINABLE_ELEMENT = NAMED_ELEMENT__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__INHERITED_ELEMENT = NAMED_ELEMENT__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__ROOT_DEFINITION = NAMED_ELEMENT__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__EXCLUDED_ELEMENT = NAMED_ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__STATE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__INCOMING = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__OUTGOING = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Redefined Vertex</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__REDEFINED_VERTEX = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX__STATE_MACHINE = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Vertex</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VERTEX_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getState()
	 * @generated
	 */
	int STATE = 12;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__MODEL = VERTEX__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__INHERITANCE_KIND = VERTEX__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__NAME = VERTEX__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__QUALIFIED_NAME = VERTEX__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__IS_INHERITED = VERTEX__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__IS_VIRTUAL_REDEFINITION = VERTEX__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__IS_REDEFINITION = VERTEX__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__IS_EXCLUDED = VERTEX__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__REDEFINED_ELEMENT = VERTEX__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__REDEFINITION_CONTEXT = VERTEX__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__REDEFINABLE_ELEMENT = VERTEX__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__INHERITED_ELEMENT = VERTEX__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__ROOT_DEFINITION = VERTEX__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__EXCLUDED_ELEMENT = VERTEX__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__STATE = VERTEX__STATE;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__INCOMING = VERTEX__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__OUTGOING = VERTEX__OUTGOING;

	/**
	 * The feature id for the '<em><b>Redefined Vertex</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__REDEFINED_VERTEX = VERTEX__REDEFINED_VERTEX;

	/**
	 * The feature id for the '<em><b>State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__STATE_MACHINE = VERTEX__STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Subtransition</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__SUBTRANSITION = VERTEX_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__ENTRY = VERTEX_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Exit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__EXIT = VERTEX_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Connection Point</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__CONNECTION_POINT = VERTEX_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Entry Point</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__ENTRY_POINT = VERTEX_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Exit Point</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__EXIT_POINT = VERTEX_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Redefined State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__REDEFINED_STATE = VERTEX_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Composite</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__COMPOSITE = VERTEX_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Simple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__SIMPLE = VERTEX_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Subvertex</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__SUBVERTEX = VERTEX_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = VERTEX_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl <em>Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 13;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__MODEL = NAMED_ELEMENT__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__INHERITANCE_KIND = NAMED_ELEMENT__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__IS_INHERITED = NAMED_ELEMENT__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__IS_VIRTUAL_REDEFINITION = NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__IS_REDEFINITION = NAMED_ELEMENT__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__IS_EXCLUDED = NAMED_ELEMENT__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__REDEFINED_ELEMENT = NAMED_ELEMENT__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__REDEFINITION_CONTEXT = NAMED_ELEMENT__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__REDEFINABLE_ELEMENT = NAMED_ELEMENT__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__INHERITED_ELEMENT = NAMED_ELEMENT__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ROOT_DEFINITION = NAMED_ELEMENT__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__EXCLUDED_ELEMENT = NAMED_ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__STATE_MACHINE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__SOURCE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TARGET = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Trigger</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TRIGGER = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__GUARD = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Redefined Transition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__REDEFINED_TRANSITION = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__KIND = NAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__INTERNAL = NAMED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Effect</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__EFFECT = NAMED_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__STATE = NAMED_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTriggerImpl <em>Trigger</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTriggerImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getTrigger()
	 * @generated
	 */
	int TRIGGER = 14;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__MODEL = NAMED_ELEMENT__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__INHERITANCE_KIND = NAMED_ELEMENT__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__IS_INHERITED = NAMED_ELEMENT__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__IS_VIRTUAL_REDEFINITION = NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__IS_REDEFINITION = NAMED_ELEMENT__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__IS_EXCLUDED = NAMED_ELEMENT__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__REDEFINED_ELEMENT = NAMED_ELEMENT__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__REDEFINITION_CONTEXT = NAMED_ELEMENT__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__REDEFINABLE_ELEMENT = NAMED_ELEMENT__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__INHERITED_ELEMENT = NAMED_ELEMENT__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__ROOT_DEFINITION = NAMED_ELEMENT__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__EXCLUDED_ELEMENT = NAMED_ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Protocol Message</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__PROTOCOL_MESSAGE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Port</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__PORT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__GUARD = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Redefined Trigger</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__REDEFINED_TRIGGER = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Receive Any Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__RECEIVE_ANY_MESSAGE = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Transition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__TRANSITION = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Trigger</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTGuardImpl <em>Guard</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTGuardImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getGuard()
	 * @generated
	 */
	int GUARD = 15;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__MODEL = NAMED_ELEMENT__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__INHERITANCE_KIND = NAMED_ELEMENT__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__IS_INHERITED = NAMED_ELEMENT__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__IS_VIRTUAL_REDEFINITION = NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__IS_REDEFINITION = NAMED_ELEMENT__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__IS_EXCLUDED = NAMED_ELEMENT__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__REDEFINED_ELEMENT = NAMED_ELEMENT__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__REDEFINITION_CONTEXT = NAMED_ELEMENT__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__REDEFINABLE_ELEMENT = NAMED_ELEMENT__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__INHERITED_ELEMENT = NAMED_ELEMENT__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__ROOT_DEFINITION = NAMED_ELEMENT__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__EXCLUDED_ELEMENT = NAMED_ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Bodies</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__BODIES = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body Entry</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__BODY_ENTRY = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Transition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__TRANSITION = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Redefined Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__REDEFINED_GUARD = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Trigger</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD__TRIGGER = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Guard</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUARD_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTOpaqueBehaviorImpl <em>Opaque Behavior</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTOpaqueBehaviorImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getOpaqueBehavior()
	 * @generated
	 */
	int OPAQUE_BEHAVIOR = 16;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__MODEL = NAMED_ELEMENT__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__INHERITANCE_KIND = NAMED_ELEMENT__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__IS_INHERITED = NAMED_ELEMENT__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__IS_VIRTUAL_REDEFINITION = NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__IS_REDEFINITION = NAMED_ELEMENT__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__IS_EXCLUDED = NAMED_ELEMENT__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__REDEFINED_ELEMENT = NAMED_ELEMENT__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__REDEFINITION_CONTEXT = NAMED_ELEMENT__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__REDEFINABLE_ELEMENT = NAMED_ELEMENT__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__INHERITED_ELEMENT = NAMED_ELEMENT__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__ROOT_DEFINITION = NAMED_ELEMENT__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__EXCLUDED_ELEMENT = NAMED_ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Bodies</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__BODIES = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body Entry</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__BODY_ENTRY = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Entered State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__ENTERED_STATE = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Exited State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__EXITED_STATE = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Redefined Behavior</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__REDEFINED_BEHAVIOR = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Transition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR__TRANSITION = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Opaque Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPAQUE_BEHAVIOR_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectionPointImpl <em>Connection Point</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectionPointImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getConnectionPoint()
	 * @generated
	 */
	int CONNECTION_POINT = 17;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__MODEL = VERTEX__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__INHERITANCE_KIND = VERTEX__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__NAME = VERTEX__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__QUALIFIED_NAME = VERTEX__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__IS_INHERITED = VERTEX__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__IS_VIRTUAL_REDEFINITION = VERTEX__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__IS_REDEFINITION = VERTEX__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__IS_EXCLUDED = VERTEX__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__REDEFINED_ELEMENT = VERTEX__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__REDEFINITION_CONTEXT = VERTEX__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__REDEFINABLE_ELEMENT = VERTEX__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__INHERITED_ELEMENT = VERTEX__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__ROOT_DEFINITION = VERTEX__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__EXCLUDED_ELEMENT = VERTEX__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__STATE = VERTEX__STATE;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__INCOMING = VERTEX__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__OUTGOING = VERTEX__OUTGOING;

	/**
	 * The feature id for the '<em><b>Redefined Vertex</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__REDEFINED_VERTEX = VERTEX__REDEFINED_VERTEX;

	/**
	 * The feature id for the '<em><b>State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__STATE_MACHINE = VERTEX__STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__KIND = VERTEX_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Redefined Connection Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT__REDEFINED_CONNECTION_POINT = VERTEX_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Connection Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_FEATURE_COUNT = VERTEX_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPseudostateImpl <em>Pseudostate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPseudostateImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getPseudostate()
	 * @generated
	 */
	int PSEUDOSTATE = 18;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__MODEL = VERTEX__MODEL;

	/**
	 * The feature id for the '<em><b>Inheritance Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__INHERITANCE_KIND = VERTEX__INHERITANCE_KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__NAME = VERTEX__NAME;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__QUALIFIED_NAME = VERTEX__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__IS_INHERITED = VERTEX__IS_INHERITED;

	/**
	 * The feature id for the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__IS_VIRTUAL_REDEFINITION = VERTEX__IS_VIRTUAL_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__IS_REDEFINITION = VERTEX__IS_REDEFINITION;

	/**
	 * The feature id for the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__IS_EXCLUDED = VERTEX__IS_EXCLUDED;

	/**
	 * The feature id for the '<em><b>Redefined Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__REDEFINED_ELEMENT = VERTEX__REDEFINED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Redefinition Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__REDEFINITION_CONTEXT = VERTEX__REDEFINITION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__REDEFINABLE_ELEMENT = VERTEX__REDEFINABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__INHERITED_ELEMENT = VERTEX__INHERITED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__ROOT_DEFINITION = VERTEX__ROOT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__EXCLUDED_ELEMENT = VERTEX__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__STATE = VERTEX__STATE;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__INCOMING = VERTEX__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__OUTGOING = VERTEX__OUTGOING;

	/**
	 * The feature id for the '<em><b>Redefined Vertex</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__REDEFINED_VERTEX = VERTEX__REDEFINED_VERTEX;

	/**
	 * The feature id for the '<em><b>State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__STATE_MACHINE = VERTEX__STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__KIND = VERTEX_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Redefined Pseudostate</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__REDEFINED_PSEUDOSTATE = VERTEX_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Pseudostate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE_FEATURE_COUNT = VERTEX_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind <em>Inheritance Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getUMLRTInheritanceKind()
	 * @generated
	 */
	int UMLRT_INHERITANCE_KIND = 19;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind <em>Port Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getUMLRTPortKind()
	 * @generated
	 */
	int UMLRT_PORT_KIND = 20;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind <em>Capsule Part Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getUMLRTCapsulePartKind()
	 * @generated
	 */
	int UMLRT_CAPSULE_PART_KIND = 21;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind <em>Connection Point Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getUMLRTConnectionPointKind()
	 * @generated
	 */
	int UMLRT_CONNECTION_POINT_KIND = 22;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind <em>Pseudostate Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getUMLRTPseudostateKind()
	 * @generated
	 */
	int UMLRT_PSEUDOSTATE_KIND = 23;

	/**
	 * The meta object id for the '<em>Model</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTModel
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getModel()
	 * @generated
	 */
	int MODEL = 24;


	/**
	 * The meta object id for the '<em>List</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see java.util.List
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getList()
	 * @generated
	 */
	int LIST = 26;

	/**
	 * The meta object id for the '<em>Stream</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see java.util.stream.Stream
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getStream()
	 * @generated
	 */
	int STREAM = 25;


	/**
	 * The meta object id for the '<em>Map</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see java.util.Map
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getMap()
	 * @generated
	 */
	int MAP = 27;


	/**
	 * The meta object id for the '<em>Illegal State Exception</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see java.lang.IllegalStateException
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getIllegalStateException()
	 * @generated
	 */
	int ILLEGAL_STATE_EXCEPTION = 28;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Model</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getModel()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Model();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getInheritanceKind <em>Inheritance Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Inheritance Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getInheritanceKind()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_InheritanceKind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getQualifiedName <em>Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Qualified Name</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getQualifiedName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_QualifiedName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#isInherited <em>Is Inherited</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Inherited</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#isInherited()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_IsInherited();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#isVirtualRedefinition <em>Is Virtual Redefinition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Virtual Redefinition</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#isVirtualRedefinition()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_IsVirtualRedefinition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#isRedefinition <em>Is Redefinition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Redefinition</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#isRedefinition()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_IsRedefinition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#isExcluded <em>Is Excluded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Excluded</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#isExcluded()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_IsExcluded();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinedElement <em>Redefined Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined Element</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinedElement()
	 * @see #getNamedElement()
	 * @generated
	 */
	EReference getNamedElement_RedefinedElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext <em>Redefinition Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefinition Context</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext()
	 * @see #getNamedElement()
	 * @generated
	 */
	EReference getNamedElement_RedefinitionContext();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements <em>Redefinable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Redefinable Element</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements()
	 * @see #getNamedElement()
	 * @generated
	 */
	EReference getNamedElement_RedefinableElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getInheritedElement <em>Inherited Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Inherited Element</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getInheritedElement()
	 * @see #getNamedElement()
	 * @generated
	 */
	EReference getNamedElement_InheritedElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRootDefinition <em>Root Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Root Definition</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRootDefinition()
	 * @see #getNamedElement()
	 * @generated
	 */
	EReference getNamedElement_RootDefinition();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getExcludedElements <em>Excluded Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Excluded Element</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getExcludedElements()
	 * @see #getNamedElement()
	 * @generated
	 */
	EReference getNamedElement_ExcludedElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Package</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage
	 * @generated
	 */
	EClass getPackage();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getNestedPackages <em>Nested Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Nested Package</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getNestedPackages()
	 * @see #getPackage()
	 * @generated
	 */
	EReference getPackage_NestedPackage();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getNestingPackage <em>Nesting Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Nesting Package</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getNestingPackage()
	 * @see #getPackage()
	 * @generated
	 */
	EReference getPackage_NestingPackage();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getCapsules <em>Capsule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Capsule</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getCapsules()
	 * @see #getPackage()
	 * @generated
	 */
	EReference getPackage_Capsule();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getProtocols <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Protocol</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getProtocols()
	 * @see #getPackage()
	 * @generated
	 */
	EReference getPackage_Protocol();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule <em>Capsule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Capsule</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule
	 * @generated
	 */
	EClass getCapsule();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getSuperclass <em>Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Superclass</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getSuperclass()
	 * @see #getCapsule()
	 * @generated
	 */
	EReference getCapsule_Superclass();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getSubclasses <em>Subclass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Subclass</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getSubclasses()
	 * @see #getCapsule()
	 * @generated
	 */
	EReference getCapsule_Subclass();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getPorts <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Port</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getPorts()
	 * @see #getCapsule()
	 * @generated
	 */
	EReference getCapsule_Port();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getConnectors <em>Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Connector</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getConnectors()
	 * @see #getCapsule()
	 * @generated
	 */
	EReference getCapsule_Connector();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getCapsuleParts <em>Capsule Part</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Capsule Part</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getCapsuleParts()
	 * @see #getCapsule()
	 * @generated
	 */
	EReference getCapsule_CapsulePart();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getHierarchy <em>Hierarchy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Hierarchy</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getHierarchy()
	 * @see #getCapsule()
	 * @generated
	 */
	EAttribute getCapsule_Hierarchy();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>State Machine</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getStateMachine()
	 * @see #getCapsule()
	 * @generated
	 */
	EReference getCapsule_StateMachine();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Package</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getPackage()
	 * @see #getCapsule()
	 * @generated
	 */
	EReference getCapsule_Package();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier <em>Classifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Classifier</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier
	 * @generated
	 */
	EClass getClassifier();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getGeneral <em>General</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>General</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getGeneral()
	 * @see #getClassifier()
	 * @generated
	 */
	EReference getClassifier_General();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getSpecifics <em>Specific</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Specific</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getSpecifics()
	 * @see #getClassifier()
	 * @generated
	 */
	EReference getClassifier_Specific();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Protocol</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol
	 * @generated
	 */
	EClass getProtocol();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getSuperProtocol <em>Super Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Super Protocol</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getSuperProtocol()
	 * @see #getProtocol()
	 * @generated
	 */
	EReference getProtocol_SuperProtocol();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getSubProtocols <em>Sub Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Sub Protocol</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getSubProtocols()
	 * @see #getProtocol()
	 * @generated
	 */
	EReference getProtocol_SubProtocol();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getMessages <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Message</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getMessages()
	 * @see #getProtocol()
	 * @generated
	 */
	EReference getProtocol_Message();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getInMessages <em>In Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>In Message</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getInMessages()
	 * @see #getProtocol()
	 * @generated
	 */
	EReference getProtocol_InMessage();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getOutMessages <em>Out Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Out Message</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getOutMessages()
	 * @see #getProtocol()
	 * @generated
	 */
	EReference getProtocol_OutMessage();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getInOutMessages <em>In Out Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>In Out Message</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getInOutMessages()
	 * @see #getProtocol()
	 * @generated
	 */
	EReference getProtocol_InOutMessage();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#isConjugate <em>Is Conjugate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Conjugate</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#isConjugate()
	 * @see #getProtocol()
	 * @generated
	 */
	EAttribute getProtocol_IsConjugate();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getConjugate <em>Conjugate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Conjugate</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getConjugate()
	 * @see #getProtocol()
	 * @generated
	 */
	EReference getProtocol_Conjugate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getHierarchy <em>Hierarchy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Hierarchy</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getHierarchy()
	 * @see #getProtocol()
	 * @generated
	 */
	EAttribute getProtocol_Hierarchy();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Package</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getPackage()
	 * @see #getProtocol()
	 * @generated
	 */
	EReference getProtocol_Package();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement <em>Replicated Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Replicated Element</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement
	 * @generated
	 */
	EClass getReplicatedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement#getReplicationFactor <em>Replication Factor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Replication Factor</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement#getReplicationFactor()
	 * @see #getReplicatedElement()
	 * @generated
	 */
	EAttribute getReplicatedElement_ReplicationFactor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement#getReplicationFactorAsString <em>Replication Factor As String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Replication Factor As String</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement#getReplicationFactorAsString()
	 * @see #getReplicatedElement()
	 * @generated
	 */
	EAttribute getReplicatedElement_ReplicationFactorAsString();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement#isSymbolicReplicationFactor <em>Symbolic Replication Factor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Symbolic Replication Factor</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement#isSymbolicReplicationFactor()
	 * @see #getReplicatedElement()
	 * @generated
	 */
	EAttribute getReplicatedElement_SymbolicReplicationFactor();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Port</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getKind()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Kind();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getRedefinedPort <em>Redefined Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined Port</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getRedefinedPort()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_RedefinedPort();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getType()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_Type();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getPartsWithPort <em>Parts With Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Parts With Port</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getPartsWithPort()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_PartsWithPort();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isService <em>Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Service</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isService()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Service();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isBehavior <em>Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Behavior</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isBehavior()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Behavior();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isConjugated <em>Conjugated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Conjugated</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isConjugated()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Conjugated();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isWired <em>Wired</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Wired</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isWired()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Wired();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isPublish <em>Publish</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Publish</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isPublish()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Publish();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isNotification <em>Notification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Notification</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isNotification()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Notification();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getRegistration <em>Registration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Registration</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getRegistration()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Registration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getRegistrationOverride <em>Registration Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Registration Override</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getRegistrationOverride()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_RegistrationOverride();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isConnected <em>Is Connected</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Connected</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isConnected()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_IsConnected();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isConnectedInside <em>Is Connected Inside</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Connected Inside</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isConnectedInside()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_IsConnectedInside();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isConnectedOutside <em>Is Connected Outside</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Connected Outside</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isConnectedOutside()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_IsConnectedOutside();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getConnectors <em>Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Connector</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getConnectors()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_Connector();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getInsideConnectors <em>Inside Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Inside Connector</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getInsideConnectors()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_InsideConnector();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getOutsideConnectors <em>Outside Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Outside Connector</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getOutsideConnectors()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_OutsideConnector();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getCapsule <em>Capsule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Capsule</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getCapsule()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_Capsule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart <em>Capsule Part</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Capsule Part</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart
	 * @generated
	 */
	EClass getCapsulePart();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getKind()
	 * @see #getCapsulePart()
	 * @generated
	 */
	EAttribute getCapsulePart_Kind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#isOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#isOptional()
	 * @see #getCapsulePart()
	 * @generated
	 */
	EAttribute getCapsulePart_Optional();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getRedefinedPart <em>Redefined Part</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined Part</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getRedefinedPart()
	 * @see #getCapsulePart()
	 * @generated
	 */
	EReference getCapsulePart_RedefinedPart();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getType()
	 * @see #getCapsulePart()
	 * @generated
	 */
	EReference getCapsulePart_Type();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getCapsule <em>Capsule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Capsule</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getCapsule()
	 * @see #getCapsulePart()
	 * @generated
	 */
	EReference getCapsulePart_Capsule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector <em>Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Connector</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector
	 * @generated
	 */
	EClass getConnector();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getRedefinedConnector <em>Redefined Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined Connector</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getRedefinedConnector()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_RedefinedConnector();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getCapsule <em>Capsule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Capsule</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getCapsule()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_Capsule();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getSource()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_Source();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getSourcePartWithPort <em>Source Part With Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Source Part With Port</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getSourcePartWithPort()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_SourcePartWithPort();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getSourceReplicationFactor <em>Source Replication Factor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Source Replication Factor</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getSourceReplicationFactor()
	 * @see #getConnector()
	 * @generated
	 */
	EAttribute getConnector_SourceReplicationFactor();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getTarget()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_Target();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getTargetPartWithPort <em>Target Part With Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Target Part With Port</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getTargetPartWithPort()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_TargetPartWithPort();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getTargetReplicationFactor <em>Target Replication Factor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Target Replication Factor</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getTargetReplicationFactor()
	 * @see #getConnector()
	 * @generated
	 */
	EAttribute getConnector_TargetReplicationFactor();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>State Machine</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine
	 * @generated
	 */
	EClass getStateMachine();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getVertices <em>Vertex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Vertex</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getVertices()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_Vertex();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getTransitions <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Transition</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getTransitions()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_Transition();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getRedefinedStateMachine <em>Redefined State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined State Machine</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getRedefinedStateMachine()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_RedefinedStateMachine();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getCapsule <em>Capsule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Capsule</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getCapsule()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_Capsule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex <em>Vertex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Vertex</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex
	 * @generated
	 */
	EClass getVertex();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>State</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getState()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_State();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getOutgoings <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Outgoing</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getOutgoings()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Outgoing();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getIncomings <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Incoming</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getIncomings()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Incoming();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getRedefinedVertex <em>Redefined Vertex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined Vertex</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getRedefinedVertex()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_RedefinedVertex();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>State Machine</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getStateMachine()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_StateMachine();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>State</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getSubtransitions <em>Subtransition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Subtransition</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getSubtransitions()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Subtransition();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getConnectionPoints <em>Connection Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Connection Point</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getConnectionPoints()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_ConnectionPoint();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getEntryPoints <em>Entry Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Entry Point</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getEntryPoints()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_EntryPoint();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getExitPoints <em>Exit Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Exit Point</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getExitPoints()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_ExitPoint();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getRedefinedState <em>Redefined State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined State</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getRedefinedState()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_RedefinedState();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#isComposite <em>Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Composite</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#isComposite()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_Composite();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#isSimple <em>Simple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Simple</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#isSimple()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_Simple();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Entry</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getEntry()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Entry();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getExit <em>Exit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Exit</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getExit()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Exit();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getSubvertices <em>Subvertex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Subvertex</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getSubvertices()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Subvertex();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>State Machine</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getStateMachine()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_StateMachine();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getSource()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Source();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getTarget()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Target();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getTriggers <em>Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Trigger</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getTriggers()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Trigger();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Guard</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getGuard()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Guard();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getRedefinedTransition <em>Redefined Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined Transition</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getRedefinedTransition()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_RedefinedTransition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getKind()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Kind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#isInternal <em>Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Internal</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#isInternal()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Internal();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getEffect <em>Effect</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Effect</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getEffect()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Effect();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>State</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getState()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_State();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger <em>Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Trigger</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger
	 * @generated
	 */
	EClass getTrigger();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getProtocolMessage <em>Protocol Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Protocol Message</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getProtocolMessage()
	 * @see #getTrigger()
	 * @generated
	 */
	EReference getTrigger_ProtocolMessage();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getPorts <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Port</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getPorts()
	 * @see #getTrigger()
	 * @generated
	 */
	EReference getTrigger_Port();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Guard</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getGuard()
	 * @see #getTrigger()
	 * @generated
	 */
	EReference getTrigger_Guard();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getRedefinedTrigger <em>Redefined Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined Trigger</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getRedefinedTrigger()
	 * @see #getTrigger()
	 * @generated
	 */
	EReference getTrigger_RedefinedTrigger();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#isReceiveAnyMessage <em>Receive Any Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Receive Any Message</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#isReceiveAnyMessage()
	 * @see #getTrigger()
	 * @generated
	 */
	EAttribute getTrigger_ReceiveAnyMessage();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getTransition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Transition</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getTransition()
	 * @see #getTrigger()
	 * @generated
	 */
	EReference getTrigger_Transition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Guard</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard
	 * @generated
	 */
	EClass getGuard();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getBodies <em>Bodies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Bodies</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getBodies()
	 * @see #getGuard()
	 * @generated
	 */
	EAttribute getGuard_Bodies();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard <em>Body Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Body Entry</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard
	 * @see #getGuard()
	 * @generated
	 */
	EReference getGuard_BodyEntry();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getTransition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Transition</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getTransition()
	 * @see #getGuard()
	 * @generated
	 */
	EReference getGuard_Transition();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getRedefinedGuard <em>Redefined Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined Guard</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getRedefinedGuard()
	 * @see #getGuard()
	 * @generated
	 */
	EReference getGuard_RedefinedGuard();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getTrigger <em>Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Trigger</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getTrigger()
	 * @see #getGuard()
	 * @generated
	 */
	EReference getGuard_Trigger();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior <em>Opaque Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Opaque Behavior</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior
	 * @generated
	 */
	EClass getOpaqueBehavior();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getBodies <em>Bodies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Bodies</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getBodies()
	 * @see #getOpaqueBehavior()
	 * @generated
	 */
	EAttribute getOpaqueBehavior_Bodies();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior <em>Body Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Body Entry</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior
	 * @see #getOpaqueBehavior()
	 * @generated
	 */
	EReference getOpaqueBehavior_BodyEntry();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getEnteredState <em>Entered State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Entered State</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getEnteredState()
	 * @see #getOpaqueBehavior()
	 * @generated
	 */
	EReference getOpaqueBehavior_EnteredState();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getExitedState <em>Exited State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Exited State</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getExitedState()
	 * @see #getOpaqueBehavior()
	 * @generated
	 */
	EReference getOpaqueBehavior_ExitedState();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getRedefinedBehavior <em>Redefined Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined Behavior</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getRedefinedBehavior()
	 * @see #getOpaqueBehavior()
	 * @generated
	 */
	EReference getOpaqueBehavior_RedefinedBehavior();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getTransition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Transition</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getTransition()
	 * @see #getOpaqueBehavior()
	 * @generated
	 */
	EReference getOpaqueBehavior_Transition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint <em>Connection Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Connection Point</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint
	 * @generated
	 */
	EClass getConnectionPoint();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint#getKind()
	 * @see #getConnectionPoint()
	 * @generated
	 */
	EAttribute getConnectionPoint_Kind();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint#getRedefinedConnectionPoint <em>Redefined Connection Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined Connection Point</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint#getRedefinedConnectionPoint()
	 * @see #getConnectionPoint()
	 * @generated
	 */
	EReference getConnectionPoint_RedefinedConnectionPoint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate <em>Pseudostate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Pseudostate</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate
	 * @generated
	 */
	EClass getPseudostate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate#getKind()
	 * @see #getPseudostate()
	 * @generated
	 */
	EAttribute getPseudostate_Kind();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate#getRedefinedPseudostate <em>Redefined Pseudostate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined Pseudostate</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate#getRedefinedPseudostate()
	 * @see #getPseudostate()
	 * @generated
	 */
	EReference getPseudostate_RedefinedPseudostate();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage <em>Protocol Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Protocol Message</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage
	 * @generated
	 */
	EClass getProtocolMessage();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getKind()
	 * @see #getProtocolMessage()
	 * @generated
	 */
	EAttribute getProtocolMessage_Kind();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getRedefinedMessage <em>Redefined Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined Message</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getRedefinedMessage()
	 * @see #getProtocolMessage()
	 * @generated
	 */
	EReference getProtocolMessage_RedefinedMessage();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getParameters <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Parameter</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getParameters()
	 * @see #getProtocolMessage()
	 * @generated
	 */
	EReference getProtocolMessage_Parameter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#isConjugate <em>Is Conjugate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Conjugate</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#isConjugate()
	 * @see #getProtocolMessage()
	 * @generated
	 */
	EAttribute getProtocolMessage_IsConjugate();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getConjugate <em>Conjugate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Conjugate</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getConjugate()
	 * @see #getProtocolMessage()
	 * @generated
	 */
	EReference getProtocolMessage_Conjugate();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Protocol</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getProtocol()
	 * @see #getProtocolMessage()
	 * @generated
	 */
	EReference getProtocolMessage_Protocol();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind <em>Inheritance Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Inheritance Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind
	 * @generated
	 */
	EEnum getUMLRTInheritanceKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind <em>Port Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Port Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind
	 * @generated
	 */
	EEnum getUMLRTPortKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind <em>Capsule Part Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Capsule Part Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind
	 * @generated
	 */
	EEnum getUMLRTCapsulePartKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind <em>Connection Point Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Connection Point Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind
	 * @generated
	 */
	EEnum getUMLRTConnectionPointKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind <em>Pseudostate Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Pseudostate Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind
	 * @generated
	 */
	EEnum getUMLRTPseudostateKind();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for data type '<em>Model</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTModel
	 * @generated
	 */
	EDataType getModel();

	/**
	 * Returns the meta object for data type '{@link java.util.List <em>List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for data type '<em>List</em>'.
	 * @see java.util.List
	 * @generated
	 */
	EDataType getList();

	/**
	 * Returns the meta object for data type '{@link java.util.stream.Stream <em>Stream</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for data type '<em>Stream</em>'.
	 * @see java.util.stream.Stream
	 * @generated
	 */
	EDataType getStream();

	/**
	 * Returns the meta object for data type '{@link java.util.Map <em>Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for data type '<em>Map</em>'.
	 * @see java.util.Map
	 * @generated
	 */
	EDataType getMap();

	/**
	 * Returns the meta object for data type '{@link java.lang.IllegalStateException <em>Illegal State Exception</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for data type '<em>Illegal State Exception</em>'.
	 * @see java.lang.IllegalStateException
	 * @generated
	 */
	EDataType getIllegalStateException();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UMLRTUMLRTFactory getUMLRTFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
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
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl <em>Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Model</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__MODEL = eINSTANCE.getNamedElement_Model();

		/**
		 * The meta object literal for the '<em><b>Inheritance Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__INHERITANCE_KIND = eINSTANCE.getNamedElement_InheritanceKind();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

		/**
		 * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__QUALIFIED_NAME = eINSTANCE.getNamedElement_QualifiedName();

		/**
		 * The meta object literal for the '<em><b>Is Inherited</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__IS_INHERITED = eINSTANCE.getNamedElement_IsInherited();

		/**
		 * The meta object literal for the '<em><b>Is Virtual Redefinition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION = eINSTANCE.getNamedElement_IsVirtualRedefinition();

		/**
		 * The meta object literal for the '<em><b>Is Redefinition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__IS_REDEFINITION = eINSTANCE.getNamedElement_IsRedefinition();

		/**
		 * The meta object literal for the '<em><b>Is Excluded</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__IS_EXCLUDED = eINSTANCE.getNamedElement_IsExcluded();

		/**
		 * The meta object literal for the '<em><b>Redefined Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference NAMED_ELEMENT__REDEFINED_ELEMENT = eINSTANCE.getNamedElement_RedefinedElement();

		/**
		 * The meta object literal for the '<em><b>Redefinition Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference NAMED_ELEMENT__REDEFINITION_CONTEXT = eINSTANCE.getNamedElement_RedefinitionContext();

		/**
		 * The meta object literal for the '<em><b>Redefinable Element</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference NAMED_ELEMENT__REDEFINABLE_ELEMENT = eINSTANCE.getNamedElement_RedefinableElement();

		/**
		 * The meta object literal for the '<em><b>Inherited Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference NAMED_ELEMENT__INHERITED_ELEMENT = eINSTANCE.getNamedElement_InheritedElement();

		/**
		 * The meta object literal for the '<em><b>Root Definition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference NAMED_ELEMENT__ROOT_DEFINITION = eINSTANCE.getNamedElement_RootDefinition();

		/**
		 * The meta object literal for the '<em><b>Excluded Element</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference NAMED_ELEMENT__EXCLUDED_ELEMENT = eINSTANCE.getNamedElement_ExcludedElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPackageImpl <em>Package</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPackageImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getPackage()
		 * @generated
		 */
		EClass PACKAGE = eINSTANCE.getPackage();

		/**
		 * The meta object literal for the '<em><b>Nested Package</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PACKAGE__NESTED_PACKAGE = eINSTANCE.getPackage_NestedPackage();

		/**
		 * The meta object literal for the '<em><b>Nesting Package</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PACKAGE__NESTING_PACKAGE = eINSTANCE.getPackage_NestingPackage();

		/**
		 * The meta object literal for the '<em><b>Capsule</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PACKAGE__CAPSULE = eINSTANCE.getPackage_Capsule();

		/**
		 * The meta object literal for the '<em><b>Protocol</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PACKAGE__PROTOCOL = eINSTANCE.getPackage_Protocol();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl <em>Capsule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getCapsule()
		 * @generated
		 */
		EClass CAPSULE = eINSTANCE.getCapsule();

		/**
		 * The meta object literal for the '<em><b>Superclass</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CAPSULE__SUPERCLASS = eINSTANCE.getCapsule_Superclass();

		/**
		 * The meta object literal for the '<em><b>Subclass</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CAPSULE__SUBCLASS = eINSTANCE.getCapsule_Subclass();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CAPSULE__PORT = eINSTANCE.getCapsule_Port();

		/**
		 * The meta object literal for the '<em><b>Connector</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CAPSULE__CONNECTOR = eINSTANCE.getCapsule_Connector();

		/**
		 * The meta object literal for the '<em><b>Capsule Part</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CAPSULE__CAPSULE_PART = eINSTANCE.getCapsule_CapsulePart();

		/**
		 * The meta object literal for the '<em><b>Hierarchy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CAPSULE__HIERARCHY = eINSTANCE.getCapsule_Hierarchy();

		/**
		 * The meta object literal for the '<em><b>State Machine</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CAPSULE__STATE_MACHINE = eINSTANCE.getCapsule_StateMachine();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CAPSULE__PACKAGE = eINSTANCE.getCapsule_Package();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTClassifierImpl <em>Classifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTClassifierImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getClassifier()
		 * @generated
		 */
		EClass CLASSIFIER = eINSTANCE.getClassifier();

		/**
		 * The meta object literal for the '<em><b>General</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CLASSIFIER__GENERAL = eINSTANCE.getClassifier_General();

		/**
		 * The meta object literal for the '<em><b>Specific</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CLASSIFIER__SPECIFIC = eINSTANCE.getClassifier_Specific();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl <em>Protocol</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getProtocol()
		 * @generated
		 */
		EClass PROTOCOL = eINSTANCE.getProtocol();

		/**
		 * The meta object literal for the '<em><b>Super Protocol</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL__SUPER_PROTOCOL = eINSTANCE.getProtocol_SuperProtocol();

		/**
		 * The meta object literal for the '<em><b>Sub Protocol</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL__SUB_PROTOCOL = eINSTANCE.getProtocol_SubProtocol();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL__MESSAGE = eINSTANCE.getProtocol_Message();

		/**
		 * The meta object literal for the '<em><b>In Message</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL__IN_MESSAGE = eINSTANCE.getProtocol_InMessage();

		/**
		 * The meta object literal for the '<em><b>Out Message</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL__OUT_MESSAGE = eINSTANCE.getProtocol_OutMessage();

		/**
		 * The meta object literal for the '<em><b>In Out Message</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL__IN_OUT_MESSAGE = eINSTANCE.getProtocol_InOutMessage();

		/**
		 * The meta object literal for the '<em><b>Is Conjugate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PROTOCOL__IS_CONJUGATE = eINSTANCE.getProtocol_IsConjugate();

		/**
		 * The meta object literal for the '<em><b>Conjugate</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL__CONJUGATE = eINSTANCE.getProtocol_Conjugate();

		/**
		 * The meta object literal for the '<em><b>Hierarchy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PROTOCOL__HIERARCHY = eINSTANCE.getProtocol_Hierarchy();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL__PACKAGE = eINSTANCE.getProtocol_Package();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTReplicatedElementImpl <em>Replicated Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTReplicatedElementImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getReplicatedElement()
		 * @generated
		 */
		EClass REPLICATED_ELEMENT = eINSTANCE.getReplicatedElement();

		/**
		 * The meta object literal for the '<em><b>Replication Factor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute REPLICATED_ELEMENT__REPLICATION_FACTOR = eINSTANCE.getReplicatedElement_ReplicationFactor();

		/**
		 * The meta object literal for the '<em><b>Replication Factor As String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING = eINSTANCE.getReplicatedElement_ReplicationFactorAsString();

		/**
		 * The meta object literal for the '<em><b>Symbolic Replication Factor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute REPLICATED_ELEMENT__SYMBOLIC_REPLICATION_FACTOR = eINSTANCE.getReplicatedElement_SymbolicReplicationFactor();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl <em>Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getPort()
		 * @generated
		 */
		EClass PORT = eINSTANCE.getPort();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__KIND = eINSTANCE.getPort_Kind();

		/**
		 * The meta object literal for the '<em><b>Redefined Port</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PORT__REDEFINED_PORT = eINSTANCE.getPort_RedefinedPort();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PORT__TYPE = eINSTANCE.getPort_Type();

		/**
		 * The meta object literal for the '<em><b>Parts With Port</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PORT__PARTS_WITH_PORT = eINSTANCE.getPort_PartsWithPort();

		/**
		 * The meta object literal for the '<em><b>Service</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__SERVICE = eINSTANCE.getPort_Service();

		/**
		 * The meta object literal for the '<em><b>Behavior</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__BEHAVIOR = eINSTANCE.getPort_Behavior();

		/**
		 * The meta object literal for the '<em><b>Conjugated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__CONJUGATED = eINSTANCE.getPort_Conjugated();

		/**
		 * The meta object literal for the '<em><b>Wired</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__WIRED = eINSTANCE.getPort_Wired();

		/**
		 * The meta object literal for the '<em><b>Publish</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__PUBLISH = eINSTANCE.getPort_Publish();

		/**
		 * The meta object literal for the '<em><b>Notification</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__NOTIFICATION = eINSTANCE.getPort_Notification();

		/**
		 * The meta object literal for the '<em><b>Registration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__REGISTRATION = eINSTANCE.getPort_Registration();

		/**
		 * The meta object literal for the '<em><b>Registration Override</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__REGISTRATION_OVERRIDE = eINSTANCE.getPort_RegistrationOverride();

		/**
		 * The meta object literal for the '<em><b>Is Connected</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__IS_CONNECTED = eINSTANCE.getPort_IsConnected();

		/**
		 * The meta object literal for the '<em><b>Is Connected Inside</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__IS_CONNECTED_INSIDE = eINSTANCE.getPort_IsConnectedInside();

		/**
		 * The meta object literal for the '<em><b>Is Connected Outside</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__IS_CONNECTED_OUTSIDE = eINSTANCE.getPort_IsConnectedOutside();

		/**
		 * The meta object literal for the '<em><b>Connector</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PORT__CONNECTOR = eINSTANCE.getPort_Connector();

		/**
		 * The meta object literal for the '<em><b>Inside Connector</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PORT__INSIDE_CONNECTOR = eINSTANCE.getPort_InsideConnector();

		/**
		 * The meta object literal for the '<em><b>Outside Connector</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PORT__OUTSIDE_CONNECTOR = eINSTANCE.getPort_OutsideConnector();

		/**
		 * The meta object literal for the '<em><b>Capsule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PORT__CAPSULE = eINSTANCE.getPort_Capsule();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsulePartImpl <em>Capsule Part</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsulePartImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getCapsulePart()
		 * @generated
		 */
		EClass CAPSULE_PART = eINSTANCE.getCapsulePart();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CAPSULE_PART__KIND = eINSTANCE.getCapsulePart_Kind();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CAPSULE_PART__OPTIONAL = eINSTANCE.getCapsulePart_Optional();

		/**
		 * The meta object literal for the '<em><b>Redefined Part</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CAPSULE_PART__REDEFINED_PART = eINSTANCE.getCapsulePart_RedefinedPart();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CAPSULE_PART__TYPE = eINSTANCE.getCapsulePart_Type();

		/**
		 * The meta object literal for the '<em><b>Capsule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CAPSULE_PART__CAPSULE = eINSTANCE.getCapsulePart_Capsule();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl <em>Connector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getConnector()
		 * @generated
		 */
		EClass CONNECTOR = eINSTANCE.getConnector();

		/**
		 * The meta object literal for the '<em><b>Redefined Connector</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTOR__REDEFINED_CONNECTOR = eINSTANCE.getConnector_RedefinedConnector();

		/**
		 * The meta object literal for the '<em><b>Capsule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTOR__CAPSULE = eINSTANCE.getConnector_Capsule();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTOR__SOURCE = eINSTANCE.getConnector_Source();

		/**
		 * The meta object literal for the '<em><b>Source Part With Port</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTOR__SOURCE_PART_WITH_PORT = eINSTANCE.getConnector_SourcePartWithPort();

		/**
		 * The meta object literal for the '<em><b>Source Replication Factor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONNECTOR__SOURCE_REPLICATION_FACTOR = eINSTANCE.getConnector_SourceReplicationFactor();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTOR__TARGET = eINSTANCE.getConnector_Target();

		/**
		 * The meta object literal for the '<em><b>Target Part With Port</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTOR__TARGET_PART_WITH_PORT = eINSTANCE.getConnector_TargetPartWithPort();

		/**
		 * The meta object literal for the '<em><b>Target Replication Factor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONNECTOR__TARGET_REPLICATION_FACTOR = eINSTANCE.getConnector_TargetReplicationFactor();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateMachineImpl <em>State Machine</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateMachineImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getStateMachine()
		 * @generated
		 */
		EClass STATE_MACHINE = eINSTANCE.getStateMachine();

		/**
		 * The meta object literal for the '<em><b>Vertex</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STATE_MACHINE__VERTEX = eINSTANCE.getStateMachine_Vertex();

		/**
		 * The meta object literal for the '<em><b>Transition</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STATE_MACHINE__TRANSITION = eINSTANCE.getStateMachine_Transition();

		/**
		 * The meta object literal for the '<em><b>Redefined State Machine</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STATE_MACHINE__REDEFINED_STATE_MACHINE = eINSTANCE.getStateMachine_RedefinedStateMachine();

		/**
		 * The meta object literal for the '<em><b>Capsule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STATE_MACHINE__CAPSULE = eINSTANCE.getStateMachine_Capsule();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTVertexImpl <em>Vertex</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTVertexImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getVertex()
		 * @generated
		 */
		EClass VERTEX = eINSTANCE.getVertex();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference VERTEX__STATE = eINSTANCE.getVertex_State();

		/**
		 * The meta object literal for the '<em><b>Outgoing</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference VERTEX__OUTGOING = eINSTANCE.getVertex_Outgoing();

		/**
		 * The meta object literal for the '<em><b>Incoming</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference VERTEX__INCOMING = eINSTANCE.getVertex_Incoming();

		/**
		 * The meta object literal for the '<em><b>Redefined Vertex</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference VERTEX__REDEFINED_VERTEX = eINSTANCE.getVertex_RedefinedVertex();

		/**
		 * The meta object literal for the '<em><b>State Machine</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference VERTEX__STATE_MACHINE = eINSTANCE.getVertex_StateMachine();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Subtransition</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STATE__SUBTRANSITION = eINSTANCE.getState_Subtransition();

		/**
		 * The meta object literal for the '<em><b>Connection Point</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STATE__CONNECTION_POINT = eINSTANCE.getState_ConnectionPoint();

		/**
		 * The meta object literal for the '<em><b>Entry Point</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STATE__ENTRY_POINT = eINSTANCE.getState_EntryPoint();

		/**
		 * The meta object literal for the '<em><b>Exit Point</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STATE__EXIT_POINT = eINSTANCE.getState_ExitPoint();

		/**
		 * The meta object literal for the '<em><b>Redefined State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STATE__REDEFINED_STATE = eINSTANCE.getState_RedefinedState();

		/**
		 * The meta object literal for the '<em><b>Composite</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute STATE__COMPOSITE = eINSTANCE.getState_Composite();

		/**
		 * The meta object literal for the '<em><b>Simple</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute STATE__SIMPLE = eINSTANCE.getState_Simple();

		/**
		 * The meta object literal for the '<em><b>Entry</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STATE__ENTRY = eINSTANCE.getState_Entry();

		/**
		 * The meta object literal for the '<em><b>Exit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STATE__EXIT = eINSTANCE.getState_Exit();

		/**
		 * The meta object literal for the '<em><b>Subvertex</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STATE__SUBVERTEX = eINSTANCE.getState_Subvertex();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl <em>Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>State Machine</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSITION__STATE_MACHINE = eINSTANCE.getTransition_StateMachine();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSITION__SOURCE = eINSTANCE.getTransition_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSITION__TARGET = eINSTANCE.getTransition_Target();

		/**
		 * The meta object literal for the '<em><b>Trigger</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSITION__TRIGGER = eINSTANCE.getTransition_Trigger();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSITION__GUARD = eINSTANCE.getTransition_Guard();

		/**
		 * The meta object literal for the '<em><b>Redefined Transition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSITION__REDEFINED_TRANSITION = eINSTANCE.getTransition_RedefinedTransition();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TRANSITION__KIND = eINSTANCE.getTransition_Kind();

		/**
		 * The meta object literal for the '<em><b>Internal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TRANSITION__INTERNAL = eINSTANCE.getTransition_Internal();

		/**
		 * The meta object literal for the '<em><b>Effect</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSITION__EFFECT = eINSTANCE.getTransition_Effect();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSITION__STATE = eINSTANCE.getTransition_State();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTriggerImpl <em>Trigger</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTriggerImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getTrigger()
		 * @generated
		 */
		EClass TRIGGER = eINSTANCE.getTrigger();

		/**
		 * The meta object literal for the '<em><b>Protocol Message</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRIGGER__PROTOCOL_MESSAGE = eINSTANCE.getTrigger_ProtocolMessage();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRIGGER__PORT = eINSTANCE.getTrigger_Port();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRIGGER__GUARD = eINSTANCE.getTrigger_Guard();

		/**
		 * The meta object literal for the '<em><b>Redefined Trigger</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRIGGER__REDEFINED_TRIGGER = eINSTANCE.getTrigger_RedefinedTrigger();

		/**
		 * The meta object literal for the '<em><b>Receive Any Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TRIGGER__RECEIVE_ANY_MESSAGE = eINSTANCE.getTrigger_ReceiveAnyMessage();

		/**
		 * The meta object literal for the '<em><b>Transition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRIGGER__TRANSITION = eINSTANCE.getTrigger_Transition();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTGuardImpl <em>Guard</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTGuardImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getGuard()
		 * @generated
		 */
		EClass GUARD = eINSTANCE.getGuard();

		/**
		 * The meta object literal for the '<em><b>Bodies</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute GUARD__BODIES = eINSTANCE.getGuard_Bodies();

		/**
		 * The meta object literal for the '<em><b>Body Entry</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference GUARD__BODY_ENTRY = eINSTANCE.getGuard_BodyEntry();

		/**
		 * The meta object literal for the '<em><b>Transition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference GUARD__TRANSITION = eINSTANCE.getGuard_Transition();

		/**
		 * The meta object literal for the '<em><b>Redefined Guard</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference GUARD__REDEFINED_GUARD = eINSTANCE.getGuard_RedefinedGuard();

		/**
		 * The meta object literal for the '<em><b>Trigger</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference GUARD__TRIGGER = eINSTANCE.getGuard_Trigger();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTOpaqueBehaviorImpl <em>Opaque Behavior</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTOpaqueBehaviorImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getOpaqueBehavior()
		 * @generated
		 */
		EClass OPAQUE_BEHAVIOR = eINSTANCE.getOpaqueBehavior();

		/**
		 * The meta object literal for the '<em><b>Bodies</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute OPAQUE_BEHAVIOR__BODIES = eINSTANCE.getOpaqueBehavior_Bodies();

		/**
		 * The meta object literal for the '<em><b>Body Entry</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference OPAQUE_BEHAVIOR__BODY_ENTRY = eINSTANCE.getOpaqueBehavior_BodyEntry();

		/**
		 * The meta object literal for the '<em><b>Entered State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference OPAQUE_BEHAVIOR__ENTERED_STATE = eINSTANCE.getOpaqueBehavior_EnteredState();

		/**
		 * The meta object literal for the '<em><b>Exited State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference OPAQUE_BEHAVIOR__EXITED_STATE = eINSTANCE.getOpaqueBehavior_ExitedState();

		/**
		 * The meta object literal for the '<em><b>Redefined Behavior</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference OPAQUE_BEHAVIOR__REDEFINED_BEHAVIOR = eINSTANCE.getOpaqueBehavior_RedefinedBehavior();

		/**
		 * The meta object literal for the '<em><b>Transition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference OPAQUE_BEHAVIOR__TRANSITION = eINSTANCE.getOpaqueBehavior_Transition();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectionPointImpl <em>Connection Point</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectionPointImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getConnectionPoint()
		 * @generated
		 */
		EClass CONNECTION_POINT = eINSTANCE.getConnectionPoint();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONNECTION_POINT__KIND = eINSTANCE.getConnectionPoint_Kind();

		/**
		 * The meta object literal for the '<em><b>Redefined Connection Point</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTION_POINT__REDEFINED_CONNECTION_POINT = eINSTANCE.getConnectionPoint_RedefinedConnectionPoint();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPseudostateImpl <em>Pseudostate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPseudostateImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getPseudostate()
		 * @generated
		 */
		EClass PSEUDOSTATE = eINSTANCE.getPseudostate();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PSEUDOSTATE__KIND = eINSTANCE.getPseudostate_Kind();

		/**
		 * The meta object literal for the '<em><b>Redefined Pseudostate</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PSEUDOSTATE__REDEFINED_PSEUDOSTATE = eINSTANCE.getPseudostate_RedefinedPseudostate();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolMessageImpl <em>Protocol Message</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolMessageImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getProtocolMessage()
		 * @generated
		 */
		EClass PROTOCOL_MESSAGE = eINSTANCE.getProtocolMessage();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PROTOCOL_MESSAGE__KIND = eINSTANCE.getProtocolMessage_Kind();

		/**
		 * The meta object literal for the '<em><b>Redefined Message</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL_MESSAGE__REDEFINED_MESSAGE = eINSTANCE.getProtocolMessage_RedefinedMessage();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL_MESSAGE__PARAMETER = eINSTANCE.getProtocolMessage_Parameter();

		/**
		 * The meta object literal for the '<em><b>Is Conjugate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PROTOCOL_MESSAGE__IS_CONJUGATE = eINSTANCE.getProtocolMessage_IsConjugate();

		/**
		 * The meta object literal for the '<em><b>Conjugate</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL_MESSAGE__CONJUGATE = eINSTANCE.getProtocolMessage_Conjugate();

		/**
		 * The meta object literal for the '<em><b>Protocol</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL_MESSAGE__PROTOCOL = eINSTANCE.getProtocolMessage_Protocol();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind <em>Inheritance Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getUMLRTInheritanceKind()
		 * @generated
		 */
		EEnum UMLRT_INHERITANCE_KIND = eINSTANCE.getUMLRTInheritanceKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind <em>Port Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getUMLRTPortKind()
		 * @generated
		 */
		EEnum UMLRT_PORT_KIND = eINSTANCE.getUMLRTPortKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind <em>Capsule Part Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getUMLRTCapsulePartKind()
		 * @generated
		 */
		EEnum UMLRT_CAPSULE_PART_KIND = eINSTANCE.getUMLRTCapsulePartKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind <em>Connection Point Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getUMLRTConnectionPointKind()
		 * @generated
		 */
		EEnum UMLRT_CONNECTION_POINT_KIND = eINSTANCE.getUMLRTConnectionPointKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind <em>Pseudostate Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getUMLRTPseudostateKind()
		 * @generated
		 */
		EEnum UMLRT_PSEUDOSTATE_KIND = eINSTANCE.getUMLRTPseudostateKind();

		/**
		 * The meta object literal for the '<em>Model</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTModel
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getModel()
		 * @generated
		 */
		EDataType MODEL = eINSTANCE.getModel();

		/**
		 * The meta object literal for the '<em>List</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see java.util.List
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getList()
		 * @generated
		 */
		EDataType LIST = eINSTANCE.getList();

		/**
		 * The meta object literal for the '<em>Stream</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see java.util.stream.Stream
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getStream()
		 * @generated
		 */
		EDataType STREAM = eINSTANCE.getStream();

		/**
		 * The meta object literal for the '<em>Map</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see java.util.Map
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getMap()
		 * @generated
		 */
		EDataType MAP = eINSTANCE.getMap();

		/**
		 * The meta object literal for the '<em>Illegal State Exception</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see java.lang.IllegalStateException
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTUMLRTPackageImpl#getIllegalStateException()
		 * @generated
		 */
		EDataType ILLEGAL_STATE_EXCEPTION = eINSTANCE.getIllegalStateException();

	}

} // UMLRTUMLRTPackage
