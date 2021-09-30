/**
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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
package org.eclipse.papyrusrt.umlrt.uml.internal.umlext;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtFactory
 * @model kind="package"
 * @generated
 */
public interface ExtUMLExtPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "umlext"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/umlrt/uml-ext"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "umlext"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExtUMLExtPackage eINSTANCE = org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtNamespaceImpl <em>Namespace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtNamespaceImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getNamespace()
	 * @generated
	 */
	int NAMESPACE = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStructuredClassifierImpl <em>Structured Classifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStructuredClassifierImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getStructuredClassifier()
	 * @generated
	 */
	int STRUCTURED_CLASSIFIER = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtEncapsulatedClassifierImpl <em>Encapsulated Classifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtEncapsulatedClassifierImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getEncapsulatedClassifier()
	 * @generated
	 */
	int ENCAPSULATED_CLASSIFIER = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtClassImpl <em>Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtClassImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getClass_()
	 * @generated
	 */
	int CLASS = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtElementImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getElement()
	 * @generated
	 */
	int ELEMENT = 0;


	/**
	 * The feature id for the '<em><b>Extended Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__EXTENDED_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__EXTENSION = 1;

	/**
	 * The feature id for the '<em><b>Implicit Owned Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__IMPLICIT_OWNED_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__EXCLUDED_ELEMENT = 3;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_FEATURE_COUNT = 4;

	/**
	 * The operation id for the '<em>Get Extension</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT___GET_EXTENSION = 0;

	/**
	 * The operation id for the '<em>Get Excluded Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT___GET_EXCLUDED_ELEMENTS = 1;

	/**
	 * The number of operations of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_OPERATION_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Extended Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__EXTENDED_ELEMENT = ELEMENT__EXTENDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__EXTENSION = ELEMENT__EXTENSION;

	/**
	 * The feature id for the '<em><b>Implicit Owned Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__IMPLICIT_OWNED_ELEMENT = ELEMENT__IMPLICIT_OWNED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__EXCLUDED_ELEMENT = ELEMENT__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Implicit Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__IMPLICIT_OWNED_MEMBER = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Excluded Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__EXCLUDED_MEMBER = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Implicit Owned Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__IMPLICIT_OWNED_RULE = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Namespace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Extension</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___GET_EXTENSION = ELEMENT___GET_EXTENSION;

	/**
	 * The operation id for the '<em>Get Excluded Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___GET_EXCLUDED_ELEMENTS = ELEMENT___GET_EXCLUDED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Excluded Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___GET_EXCLUDED_MEMBERS = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Namespace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtBehavioredClassifierImpl <em>Behaviored Classifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtBehavioredClassifierImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getBehavioredClassifier()
	 * @generated
	 */
	int BEHAVIORED_CLASSIFIER = 2;

	/**
	 * The feature id for the '<em><b>Extended Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIORED_CLASSIFIER__EXTENDED_ELEMENT = NAMESPACE__EXTENDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIORED_CLASSIFIER__EXTENSION = NAMESPACE__EXTENSION;

	/**
	 * The feature id for the '<em><b>Implicit Owned Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIORED_CLASSIFIER__IMPLICIT_OWNED_ELEMENT = NAMESPACE__IMPLICIT_OWNED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIORED_CLASSIFIER__EXCLUDED_ELEMENT = NAMESPACE__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Implicit Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIORED_CLASSIFIER__IMPLICIT_OWNED_MEMBER = NAMESPACE__IMPLICIT_OWNED_MEMBER;

	/**
	 * The feature id for the '<em><b>Excluded Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIORED_CLASSIFIER__EXCLUDED_MEMBER = NAMESPACE__EXCLUDED_MEMBER;

	/**
	 * The feature id for the '<em><b>Implicit Owned Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIORED_CLASSIFIER__IMPLICIT_OWNED_RULE = NAMESPACE__IMPLICIT_OWNED_RULE;

	/**
	 * The feature id for the '<em><b>Implicit Behavior</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR = NAMESPACE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Behaviored Classifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIORED_CLASSIFIER_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Extension</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIORED_CLASSIFIER___GET_EXTENSION = NAMESPACE___GET_EXTENSION;

	/**
	 * The operation id for the '<em>Get Excluded Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIORED_CLASSIFIER___GET_EXCLUDED_ELEMENTS = NAMESPACE___GET_EXCLUDED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Excluded Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIORED_CLASSIFIER___GET_EXCLUDED_MEMBERS = NAMESPACE___GET_EXCLUDED_MEMBERS;

	/**
	 * The number of operations of the '<em>Behaviored Classifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIORED_CLASSIFIER_OPERATION_COUNT = NAMESPACE_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Extended Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER__EXTENDED_ELEMENT = NAMESPACE__EXTENDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER__EXTENSION = NAMESPACE__EXTENSION;

	/**
	 * The feature id for the '<em><b>Implicit Owned Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER__IMPLICIT_OWNED_ELEMENT = NAMESPACE__IMPLICIT_OWNED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER__EXCLUDED_ELEMENT = NAMESPACE__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Implicit Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER__IMPLICIT_OWNED_MEMBER = NAMESPACE__IMPLICIT_OWNED_MEMBER;

	/**
	 * The feature id for the '<em><b>Excluded Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER__EXCLUDED_MEMBER = NAMESPACE__EXCLUDED_MEMBER;

	/**
	 * The feature id for the '<em><b>Implicit Owned Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER__IMPLICIT_OWNED_RULE = NAMESPACE__IMPLICIT_OWNED_RULE;

	/**
	 * The feature id for the '<em><b>Implicit Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE = NAMESPACE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Implicit Connector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR = NAMESPACE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Structured Classifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Extension</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER___GET_EXTENSION = NAMESPACE___GET_EXTENSION;

	/**
	 * The operation id for the '<em>Get Excluded Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER___GET_EXCLUDED_ELEMENTS = NAMESPACE___GET_EXCLUDED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Excluded Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER___GET_EXCLUDED_MEMBERS = NAMESPACE___GET_EXCLUDED_MEMBERS;

	/**
	 * The number of operations of the '<em>Structured Classifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_CLASSIFIER_OPERATION_COUNT = NAMESPACE_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Extended Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER__EXTENDED_ELEMENT = STRUCTURED_CLASSIFIER__EXTENDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER__EXTENSION = STRUCTURED_CLASSIFIER__EXTENSION;

	/**
	 * The feature id for the '<em><b>Implicit Owned Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER__IMPLICIT_OWNED_ELEMENT = STRUCTURED_CLASSIFIER__IMPLICIT_OWNED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER__EXCLUDED_ELEMENT = STRUCTURED_CLASSIFIER__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Implicit Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER__IMPLICIT_OWNED_MEMBER = STRUCTURED_CLASSIFIER__IMPLICIT_OWNED_MEMBER;

	/**
	 * The feature id for the '<em><b>Excluded Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER__EXCLUDED_MEMBER = STRUCTURED_CLASSIFIER__EXCLUDED_MEMBER;

	/**
	 * The feature id for the '<em><b>Implicit Owned Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER__IMPLICIT_OWNED_RULE = STRUCTURED_CLASSIFIER__IMPLICIT_OWNED_RULE;

	/**
	 * The feature id for the '<em><b>Implicit Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER__IMPLICIT_ATTRIBUTE = STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Implicit Connector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER__IMPLICIT_CONNECTOR = STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR;

	/**
	 * The feature id for the '<em><b>Implicit Port</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT = STRUCTURED_CLASSIFIER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Encapsulated Classifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER_FEATURE_COUNT = STRUCTURED_CLASSIFIER_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Extension</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER___GET_EXTENSION = STRUCTURED_CLASSIFIER___GET_EXTENSION;

	/**
	 * The operation id for the '<em>Get Excluded Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER___GET_EXCLUDED_ELEMENTS = STRUCTURED_CLASSIFIER___GET_EXCLUDED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Excluded Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER___GET_EXCLUDED_MEMBERS = STRUCTURED_CLASSIFIER___GET_EXCLUDED_MEMBERS;

	/**
	 * The number of operations of the '<em>Encapsulated Classifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENCAPSULATED_CLASSIFIER_OPERATION_COUNT = STRUCTURED_CLASSIFIER_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Extended Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__EXTENDED_ELEMENT = ENCAPSULATED_CLASSIFIER__EXTENDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__EXTENSION = ENCAPSULATED_CLASSIFIER__EXTENSION;

	/**
	 * The feature id for the '<em><b>Implicit Owned Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__IMPLICIT_OWNED_ELEMENT = ENCAPSULATED_CLASSIFIER__IMPLICIT_OWNED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__EXCLUDED_ELEMENT = ENCAPSULATED_CLASSIFIER__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Implicit Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__IMPLICIT_OWNED_MEMBER = ENCAPSULATED_CLASSIFIER__IMPLICIT_OWNED_MEMBER;

	/**
	 * The feature id for the '<em><b>Excluded Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__EXCLUDED_MEMBER = ENCAPSULATED_CLASSIFIER__EXCLUDED_MEMBER;

	/**
	 * The feature id for the '<em><b>Implicit Owned Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__IMPLICIT_OWNED_RULE = ENCAPSULATED_CLASSIFIER__IMPLICIT_OWNED_RULE;

	/**
	 * The feature id for the '<em><b>Implicit Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__IMPLICIT_ATTRIBUTE = ENCAPSULATED_CLASSIFIER__IMPLICIT_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Implicit Connector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__IMPLICIT_CONNECTOR = ENCAPSULATED_CLASSIFIER__IMPLICIT_CONNECTOR;

	/**
	 * The feature id for the '<em><b>Implicit Port</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__IMPLICIT_PORT = ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT;

	/**
	 * The feature id for the '<em><b>Implicit Behavior</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__IMPLICIT_BEHAVIOR = ENCAPSULATED_CLASSIFIER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Implicit Operation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__IMPLICIT_OPERATION = ENCAPSULATED_CLASSIFIER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_FEATURE_COUNT = ENCAPSULATED_CLASSIFIER_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Extension</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS___GET_EXTENSION = ENCAPSULATED_CLASSIFIER___GET_EXTENSION;

	/**
	 * The operation id for the '<em>Get Excluded Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS___GET_EXCLUDED_ELEMENTS = ENCAPSULATED_CLASSIFIER___GET_EXCLUDED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Excluded Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS___GET_EXCLUDED_MEMBERS = ENCAPSULATED_CLASSIFIER___GET_EXCLUDED_MEMBERS;

	/**
	 * The number of operations of the '<em>Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OPERATION_COUNT = ENCAPSULATED_CLASSIFIER_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtInterfaceImpl <em>Interface</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtInterfaceImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getInterface()
	 * @generated
	 */
	int INTERFACE = 6;

	/**
	 * The feature id for the '<em><b>Extended Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE__EXTENDED_ELEMENT = NAMESPACE__EXTENDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE__EXTENSION = NAMESPACE__EXTENSION;

	/**
	 * The feature id for the '<em><b>Implicit Owned Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE__IMPLICIT_OWNED_ELEMENT = NAMESPACE__IMPLICIT_OWNED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE__EXCLUDED_ELEMENT = NAMESPACE__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Implicit Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE__IMPLICIT_OWNED_MEMBER = NAMESPACE__IMPLICIT_OWNED_MEMBER;

	/**
	 * The feature id for the '<em><b>Excluded Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE__EXCLUDED_MEMBER = NAMESPACE__EXCLUDED_MEMBER;

	/**
	 * The feature id for the '<em><b>Implicit Owned Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE__IMPLICIT_OWNED_RULE = NAMESPACE__IMPLICIT_OWNED_RULE;

	/**
	 * The feature id for the '<em><b>Implicit Operation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE__IMPLICIT_OPERATION = NAMESPACE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Extension</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE___GET_EXTENSION = NAMESPACE___GET_EXTENSION;

	/**
	 * The operation id for the '<em>Get Excluded Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE___GET_EXCLUDED_ELEMENTS = NAMESPACE___GET_EXCLUDED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Excluded Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE___GET_EXCLUDED_MEMBERS = NAMESPACE___GET_EXCLUDED_MEMBERS;

	/**
	 * The number of operations of the '<em>Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_OPERATION_COUNT = NAMESPACE_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStateMachineImpl <em>State Machine</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStateMachineImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getStateMachine()
	 * @generated
	 */
	int STATE_MACHINE = 7;

	/**
	 * The feature id for the '<em><b>Extended Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__EXTENDED_ELEMENT = NAMESPACE__EXTENDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__EXTENSION = NAMESPACE__EXTENSION;

	/**
	 * The feature id for the '<em><b>Implicit Owned Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__IMPLICIT_OWNED_ELEMENT = NAMESPACE__IMPLICIT_OWNED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__EXCLUDED_ELEMENT = NAMESPACE__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Implicit Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__IMPLICIT_OWNED_MEMBER = NAMESPACE__IMPLICIT_OWNED_MEMBER;

	/**
	 * The feature id for the '<em><b>Excluded Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__EXCLUDED_MEMBER = NAMESPACE__EXCLUDED_MEMBER;

	/**
	 * The feature id for the '<em><b>Implicit Owned Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__IMPLICIT_OWNED_RULE = NAMESPACE__IMPLICIT_OWNED_RULE;

	/**
	 * The feature id for the '<em><b>Implicit Region</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__IMPLICIT_REGION = NAMESPACE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Implicit Connection Point</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__IMPLICIT_CONNECTION_POINT = NAMESPACE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>State Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Extension</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE___GET_EXTENSION = NAMESPACE___GET_EXTENSION;

	/**
	 * The operation id for the '<em>Get Excluded Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE___GET_EXCLUDED_ELEMENTS = NAMESPACE___GET_EXCLUDED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Excluded Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE___GET_EXCLUDED_MEMBERS = NAMESPACE___GET_EXCLUDED_MEMBERS;

	/**
	 * The number of operations of the '<em>State Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_OPERATION_COUNT = NAMESPACE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtRegionImpl <em>Region</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtRegionImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getRegion()
	 * @generated
	 */
	int REGION = 8;

	/**
	 * The feature id for the '<em><b>Extended Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__EXTENDED_ELEMENT = NAMESPACE__EXTENDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__EXTENSION = NAMESPACE__EXTENSION;

	/**
	 * The feature id for the '<em><b>Implicit Owned Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__IMPLICIT_OWNED_ELEMENT = NAMESPACE__IMPLICIT_OWNED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__EXCLUDED_ELEMENT = NAMESPACE__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Implicit Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__IMPLICIT_OWNED_MEMBER = NAMESPACE__IMPLICIT_OWNED_MEMBER;

	/**
	 * The feature id for the '<em><b>Excluded Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__EXCLUDED_MEMBER = NAMESPACE__EXCLUDED_MEMBER;

	/**
	 * The feature id for the '<em><b>Implicit Owned Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__IMPLICIT_OWNED_RULE = NAMESPACE__IMPLICIT_OWNED_RULE;

	/**
	 * The feature id for the '<em><b>Implicit Subvertex</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__IMPLICIT_SUBVERTEX = NAMESPACE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Implicit Transition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__IMPLICIT_TRANSITION = NAMESPACE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Region</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Extension</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___GET_EXTENSION = NAMESPACE___GET_EXTENSION;

	/**
	 * The operation id for the '<em>Get Excluded Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___GET_EXCLUDED_ELEMENTS = NAMESPACE___GET_EXCLUDED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Excluded Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___GET_EXCLUDED_MEMBERS = NAMESPACE___GET_EXCLUDED_MEMBERS;

	/**
	 * The number of operations of the '<em>Region</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION_OPERATION_COUNT = NAMESPACE_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtTransitionImpl <em>Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtTransitionImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 9;

	/**
	 * The feature id for the '<em><b>Extended Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__EXTENDED_ELEMENT = NAMESPACE__EXTENDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__EXTENSION = NAMESPACE__EXTENSION;

	/**
	 * The feature id for the '<em><b>Implicit Owned Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__IMPLICIT_OWNED_ELEMENT = NAMESPACE__IMPLICIT_OWNED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__EXCLUDED_ELEMENT = NAMESPACE__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Implicit Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__IMPLICIT_OWNED_MEMBER = NAMESPACE__IMPLICIT_OWNED_MEMBER;

	/**
	 * The feature id for the '<em><b>Excluded Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__EXCLUDED_MEMBER = NAMESPACE__EXCLUDED_MEMBER;

	/**
	 * The feature id for the '<em><b>Implicit Owned Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__IMPLICIT_OWNED_RULE = NAMESPACE__IMPLICIT_OWNED_RULE;

	/**
	 * The feature id for the '<em><b>Implicit Trigger</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__IMPLICIT_TRIGGER = NAMESPACE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Extension</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___GET_EXTENSION = NAMESPACE___GET_EXTENSION;

	/**
	 * The operation id for the '<em>Get Excluded Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___GET_EXCLUDED_ELEMENTS = NAMESPACE___GET_EXCLUDED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Excluded Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___GET_EXCLUDED_MEMBERS = NAMESPACE___GET_EXCLUDED_MEMBERS;

	/**
	 * The number of operations of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_OPERATION_COUNT = NAMESPACE_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStateImpl
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getState()
	 * @generated
	 */
	int STATE = 10;

	/**
	 * The feature id for the '<em><b>Extended Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__EXTENDED_ELEMENT = NAMESPACE__EXTENDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__EXTENSION = NAMESPACE__EXTENSION;

	/**
	 * The feature id for the '<em><b>Implicit Owned Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__IMPLICIT_OWNED_ELEMENT = NAMESPACE__IMPLICIT_OWNED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__EXCLUDED_ELEMENT = NAMESPACE__EXCLUDED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Implicit Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__IMPLICIT_OWNED_MEMBER = NAMESPACE__IMPLICIT_OWNED_MEMBER;

	/**
	 * The feature id for the '<em><b>Excluded Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__EXCLUDED_MEMBER = NAMESPACE__EXCLUDED_MEMBER;

	/**
	 * The feature id for the '<em><b>Implicit Owned Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__IMPLICIT_OWNED_RULE = NAMESPACE__IMPLICIT_OWNED_RULE;

	/**
	 * The feature id for the '<em><b>Implicit Region</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__IMPLICIT_REGION = NAMESPACE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Implicit Connection Point</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__IMPLICIT_CONNECTION_POINT = NAMESPACE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Extension</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___GET_EXTENSION = NAMESPACE___GET_EXTENSION;

	/**
	 * The operation id for the '<em>Get Excluded Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___GET_EXCLUDED_ELEMENTS = NAMESPACE___GET_EXCLUDED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Excluded Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___GET_EXCLUDED_MEMBERS = NAMESPACE___GET_EXCLUDED_MEMBERS;

	/**
	 * The number of operations of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_OPERATION_COUNT = NAMESPACE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Namespace</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace
	 * @generated
	 */
	EClass getNamespace();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getImplicitOwnedMembers <em>Implicit Owned Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Implicit Owned Member</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getImplicitOwnedMembers()
	 * @see #getNamespace()
	 * @generated
	 */
	EReference getNamespace_ImplicitOwnedMember();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getExcludedMembers <em>Excluded Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Excluded Member</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getExcludedMembers()
	 * @see #getNamespace()
	 * @generated
	 */
	EReference getNamespace_ExcludedMember();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getImplicitOwnedRules <em>Implicit Owned Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implicit Owned Rule</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getImplicitOwnedRules()
	 * @see #getNamespace()
	 * @generated
	 */
	EReference getNamespace_ImplicitOwnedRule();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getExcludedMembers() <em>Get Excluded Members</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Excluded Members</em>' operation.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getExcludedMembers()
	 * @generated
	 */
	EOperation getNamespace__GetExcludedMembers();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtBehavioredClassifier <em>Behaviored Classifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Behaviored Classifier</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtBehavioredClassifier
	 * @generated
	 */
	EClass getBehavioredClassifier();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtBehavioredClassifier#getImplicitBehaviors <em>Implicit Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implicit Behavior</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtBehavioredClassifier#getImplicitBehaviors()
	 * @see #getBehavioredClassifier()
	 * @generated
	 */
	EReference getBehavioredClassifier_ImplicitBehavior();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStructuredClassifier <em>Structured Classifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Structured Classifier</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStructuredClassifier
	 * @generated
	 */
	EClass getStructuredClassifier();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStructuredClassifier#getImplicitAttributes <em>Implicit Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implicit Attribute</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStructuredClassifier#getImplicitAttributes()
	 * @see #getStructuredClassifier()
	 * @generated
	 */
	EReference getStructuredClassifier_ImplicitAttribute();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStructuredClassifier#getImplicitConnectors <em>Implicit Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implicit Connector</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStructuredClassifier#getImplicitConnectors()
	 * @see #getStructuredClassifier()
	 * @generated
	 */
	EReference getStructuredClassifier_ImplicitConnector();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtEncapsulatedClassifier <em>Encapsulated Classifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Encapsulated Classifier</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtEncapsulatedClassifier
	 * @generated
	 */
	EClass getEncapsulatedClassifier();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtEncapsulatedClassifier#getImplicitPorts <em>Implicit Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Implicit Port</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtEncapsulatedClassifier#getImplicitPorts()
	 * @see #getEncapsulatedClassifier()
	 * @generated
	 */
	EReference getEncapsulatedClassifier_ImplicitPort();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtClass <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtClass
	 * @generated
	 */
	EClass getClass_();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtClass#getImplicitOperations <em>Implicit Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implicit Operation</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtClass#getImplicitOperations()
	 * @see #getClass_()
	 * @generated
	 */
	EReference getClass_ImplicitOperation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtInterface <em>Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interface</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtInterface
	 * @generated
	 */
	EClass getInterface();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtInterface#getImplicitOperations <em>Implicit Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implicit Operation</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtInterface#getImplicitOperations()
	 * @see #getInterface()
	 * @generated
	 */
	EReference getInterface_ImplicitOperation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Machine</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStateMachine
	 * @generated
	 */
	EClass getStateMachine();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStateMachine#getImplicitRegions <em>Implicit Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implicit Region</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStateMachine#getImplicitRegions()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_ImplicitRegion();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStateMachine#getImplicitConnectionPoints <em>Implicit Connection Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implicit Connection Point</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStateMachine#getImplicitConnectionPoints()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_ImplicitConnectionPoint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion <em>Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Region</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion
	 * @generated
	 */
	EClass getRegion();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion#getImplicitSubvertices <em>Implicit Subvertex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implicit Subvertex</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion#getImplicitSubvertices()
	 * @see #getRegion()
	 * @generated
	 */
	EReference getRegion_ImplicitSubvertex();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion#getImplicitTransitions <em>Implicit Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implicit Transition</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion#getImplicitTransitions()
	 * @see #getRegion()
	 * @generated
	 */
	EReference getRegion_ImplicitTransition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtTransition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtTransition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtTransition#getImplicitTriggers <em>Implicit Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implicit Trigger</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtTransition#getImplicitTriggers()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_ImplicitTrigger();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtState
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtState#getImplicitRegions <em>Implicit Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implicit Region</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtState#getImplicitRegions()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_ImplicitRegion();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtState#getImplicitConnectionPoints <em>Implicit Connection Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implicit Connection Point</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtState#getImplicitConnectionPoints()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_ImplicitConnectionPoint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement
	 * @generated
	 */
	EClass getElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExtendedElement <em>Extended Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Extended Element</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExtendedElement()
	 * @see #getElement()
	 * @generated
	 */
	EReference getElement_ExtendedElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExtension <em>Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Extension</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExtension()
	 * @see #getElement()
	 * @generated
	 */
	EReference getElement_Extension();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getImplicitOwnedElements <em>Implicit Owned Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Implicit Owned Element</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getImplicitOwnedElements()
	 * @see #getElement()
	 * @generated
	 */
	EReference getElement_ImplicitOwnedElement();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExcludedElements <em>Excluded Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Excluded Element</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExcludedElements()
	 * @see #getElement()
	 * @generated
	 */
	EReference getElement_ExcludedElement();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExtension() <em>Get Extension</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Extension</em>' operation.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExtension()
	 * @generated
	 */
	EOperation getElement__GetExtension();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExcludedElements() <em>Get Excluded Elements</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Excluded Elements</em>' operation.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExcludedElements()
	 * @generated
	 */
	EOperation getElement__GetExcludedElements();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ExtUMLExtFactory getUMLExtFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtNamespaceImpl <em>Namespace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtNamespaceImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getNamespace()
		 * @generated
		 */
		EClass NAMESPACE = eINSTANCE.getNamespace();

		/**
		 * The meta object literal for the '<em><b>Implicit Owned Member</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAMESPACE__IMPLICIT_OWNED_MEMBER = eINSTANCE.getNamespace_ImplicitOwnedMember();

		/**
		 * The meta object literal for the '<em><b>Excluded Member</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAMESPACE__EXCLUDED_MEMBER = eINSTANCE.getNamespace_ExcludedMember();

		/**
		 * The meta object literal for the '<em><b>Implicit Owned Rule</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAMESPACE__IMPLICIT_OWNED_RULE = eINSTANCE.getNamespace_ImplicitOwnedRule();

		/**
		 * The meta object literal for the '<em><b>Get Excluded Members</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMESPACE___GET_EXCLUDED_MEMBERS = eINSTANCE.getNamespace__GetExcludedMembers();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtBehavioredClassifierImpl <em>Behaviored Classifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtBehavioredClassifierImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getBehavioredClassifier()
		 * @generated
		 */
		EClass BEHAVIORED_CLASSIFIER = eINSTANCE.getBehavioredClassifier();

		/**
		 * The meta object literal for the '<em><b>Implicit Behavior</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR = eINSTANCE.getBehavioredClassifier_ImplicitBehavior();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStructuredClassifierImpl <em>Structured Classifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStructuredClassifierImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getStructuredClassifier()
		 * @generated
		 */
		EClass STRUCTURED_CLASSIFIER = eINSTANCE.getStructuredClassifier();

		/**
		 * The meta object literal for the '<em><b>Implicit Attribute</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE = eINSTANCE.getStructuredClassifier_ImplicitAttribute();

		/**
		 * The meta object literal for the '<em><b>Implicit Connector</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR = eINSTANCE.getStructuredClassifier_ImplicitConnector();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtEncapsulatedClassifierImpl <em>Encapsulated Classifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtEncapsulatedClassifierImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getEncapsulatedClassifier()
		 * @generated
		 */
		EClass ENCAPSULATED_CLASSIFIER = eINSTANCE.getEncapsulatedClassifier();

		/**
		 * The meta object literal for the '<em><b>Implicit Port</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT = eINSTANCE.getEncapsulatedClassifier_ImplicitPort();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtClassImpl <em>Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtClassImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getClass_()
		 * @generated
		 */
		EClass CLASS = eINSTANCE.getClass_();

		/**
		 * The meta object literal for the '<em><b>Implicit Operation</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS__IMPLICIT_OPERATION = eINSTANCE.getClass_ImplicitOperation();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtInterfaceImpl <em>Interface</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtInterfaceImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getInterface()
		 * @generated
		 */
		EClass INTERFACE = eINSTANCE.getInterface();

		/**
		 * The meta object literal for the '<em><b>Implicit Operation</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE__IMPLICIT_OPERATION = eINSTANCE.getInterface_ImplicitOperation();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStateMachineImpl <em>State Machine</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStateMachineImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getStateMachine()
		 * @generated
		 */
		EClass STATE_MACHINE = eINSTANCE.getStateMachine();

		/**
		 * The meta object literal for the '<em><b>Implicit Region</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_MACHINE__IMPLICIT_REGION = eINSTANCE.getStateMachine_ImplicitRegion();

		/**
		 * The meta object literal for the '<em><b>Implicit Connection Point</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_MACHINE__IMPLICIT_CONNECTION_POINT = eINSTANCE.getStateMachine_ImplicitConnectionPoint();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtRegionImpl <em>Region</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtRegionImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getRegion()
		 * @generated
		 */
		EClass REGION = eINSTANCE.getRegion();

		/**
		 * The meta object literal for the '<em><b>Implicit Subvertex</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGION__IMPLICIT_SUBVERTEX = eINSTANCE.getRegion_ImplicitSubvertex();

		/**
		 * The meta object literal for the '<em><b>Implicit Transition</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGION__IMPLICIT_TRANSITION = eINSTANCE.getRegion_ImplicitTransition();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtTransitionImpl <em>Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtTransitionImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>Implicit Trigger</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__IMPLICIT_TRIGGER = eINSTANCE.getTransition_ImplicitTrigger();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStateImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Implicit Region</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__IMPLICIT_REGION = eINSTANCE.getState_ImplicitRegion();

		/**
		 * The meta object literal for the '<em><b>Implicit Connection Point</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__IMPLICIT_CONNECTION_POINT = eINSTANCE.getState_ImplicitConnectionPoint();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtElementImpl
		 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtUMLExtPackageImpl#getElement()
		 * @generated
		 */
		EClass ELEMENT = eINSTANCE.getElement();

		/**
		 * The meta object literal for the '<em><b>Extended Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT__EXTENDED_ELEMENT = eINSTANCE.getElement_ExtendedElement();

		/**
		 * The meta object literal for the '<em><b>Extension</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT__EXTENSION = eINSTANCE.getElement_Extension();

		/**
		 * The meta object literal for the '<em><b>Implicit Owned Element</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT__IMPLICIT_OWNED_ELEMENT = eINSTANCE.getElement_ImplicitOwnedElement();

		/**
		 * The meta object literal for the '<em><b>Excluded Element</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT__EXCLUDED_ELEMENT = eINSTANCE.getElement_ExcludedElement();

		/**
		 * The meta object literal for the '<em><b>Get Extension</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ELEMENT___GET_EXTENSION = eINSTANCE.getElement__GetExtension();

		/**
		 * The meta object literal for the '<em><b>Get Excluded Elements</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ELEMENT___GET_EXCLUDED_ELEMENTS = eINSTANCE.getElement__GetExcludedElements();

	}

} //UMLExtPackage
