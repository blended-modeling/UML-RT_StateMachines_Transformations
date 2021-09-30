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
package org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtBehavioredClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtClass;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtEncapsulatedClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtInterface;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtState;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStructuredClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtTransition;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtFactory;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExtUMLExtPackageImpl extends EPackageImpl implements ExtUMLExtPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namespaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass behavioredClassifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structuredClassifierEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass encapsulatedClassifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interfaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateMachineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass regionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateEClass = null;

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
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ExtUMLExtPackageImpl() {
		super(eNS_URI, ExtUMLExtFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ExtUMLExtPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ExtUMLExtPackage init() {
		if (isInited) {
			return (ExtUMLExtPackage) EPackage.Registry.INSTANCE.getEPackage(ExtUMLExtPackage.eNS_URI);
		}

		// Obtain or create and register package
		ExtUMLExtPackageImpl theUMLExtPackage = (ExtUMLExtPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ExtUMLExtPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ExtUMLExtPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		UMLPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theUMLExtPackage.createPackageContents();

		// Initialize created meta-data
		theUMLExtPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUMLExtPackage.freeze();


		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ExtUMLExtPackage.eNS_URI, theUMLExtPackage);
		return theUMLExtPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNamespace() {
		return namespaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNamespace_ImplicitOwnedMember() {
		return (EReference) namespaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNamespace_ExcludedMember() {
		return (EReference) namespaceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNamespace_ImplicitOwnedRule() {
		return (EReference) namespaceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getNamespace__GetExcludedMembers() {
		return namespaceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBehavioredClassifier() {
		return behavioredClassifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBehavioredClassifier_ImplicitBehavior() {
		return (EReference) behavioredClassifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStructuredClassifier() {
		return structuredClassifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructuredClassifier_ImplicitAttribute() {
		return (EReference) structuredClassifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructuredClassifier_ImplicitConnector() {
		return (EReference) structuredClassifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEncapsulatedClassifier() {
		return encapsulatedClassifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEncapsulatedClassifier_ImplicitPort() {
		return (EReference) encapsulatedClassifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getClass_() {
		return classEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getClass_ImplicitOperation() {
		return (EReference) classEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInterface() {
		return interfaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInterface_ImplicitOperation() {
		return (EReference) interfaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStateMachine() {
		return stateMachineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStateMachine_ImplicitRegion() {
		return (EReference) stateMachineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStateMachine_ImplicitConnectionPoint() {
		return (EReference) stateMachineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRegion() {
		return regionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRegion_ImplicitSubvertex() {
		return (EReference) regionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRegion_ImplicitTransition() {
		return (EReference) regionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTransition() {
		return transitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTransition_ImplicitTrigger() {
		return (EReference) transitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getState() {
		return stateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getState_ImplicitRegion() {
		return (EReference) stateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getState_ImplicitConnectionPoint() {
		return (EReference) stateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getElement() {
		return elementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getElement_ExtendedElement() {
		return (EReference) elementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getElement_Extension() {
		return (EReference) elementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getElement_ImplicitOwnedElement() {
		return (EReference) elementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getElement_ExcludedElement() {
		return (EReference) elementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getElement__GetExtension() {
		return elementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getElement__GetExcludedElements() {
		return elementEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExtUMLExtFactory getUMLExtFactory() {
		return (ExtUMLExtFactory) getEFactoryInstance();
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
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		elementEClass = createEClass(ELEMENT);
		createEReference(elementEClass, ELEMENT__EXTENDED_ELEMENT);
		createEReference(elementEClass, ELEMENT__EXTENSION);
		createEReference(elementEClass, ELEMENT__IMPLICIT_OWNED_ELEMENT);
		createEReference(elementEClass, ELEMENT__EXCLUDED_ELEMENT);
		createEOperation(elementEClass, ELEMENT___GET_EXTENSION);
		createEOperation(elementEClass, ELEMENT___GET_EXCLUDED_ELEMENTS);

		namespaceEClass = createEClass(NAMESPACE);
		createEReference(namespaceEClass, NAMESPACE__IMPLICIT_OWNED_MEMBER);
		createEReference(namespaceEClass, NAMESPACE__EXCLUDED_MEMBER);
		createEReference(namespaceEClass, NAMESPACE__IMPLICIT_OWNED_RULE);
		createEOperation(namespaceEClass, NAMESPACE___GET_EXCLUDED_MEMBERS);

		behavioredClassifierEClass = createEClass(BEHAVIORED_CLASSIFIER);
		createEReference(behavioredClassifierEClass, BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR);

		structuredClassifierEClass = createEClass(STRUCTURED_CLASSIFIER);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR);

		encapsulatedClassifierEClass = createEClass(ENCAPSULATED_CLASSIFIER);
		createEReference(encapsulatedClassifierEClass, ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT);

		classEClass = createEClass(CLASS);
		createEReference(classEClass, CLASS__IMPLICIT_OPERATION);

		interfaceEClass = createEClass(INTERFACE);
		createEReference(interfaceEClass, INTERFACE__IMPLICIT_OPERATION);

		stateMachineEClass = createEClass(STATE_MACHINE);
		createEReference(stateMachineEClass, STATE_MACHINE__IMPLICIT_REGION);
		createEReference(stateMachineEClass, STATE_MACHINE__IMPLICIT_CONNECTION_POINT);

		regionEClass = createEClass(REGION);
		createEReference(regionEClass, REGION__IMPLICIT_SUBVERTEX);
		createEReference(regionEClass, REGION__IMPLICIT_TRANSITION);

		transitionEClass = createEClass(TRANSITION);
		createEReference(transitionEClass, TRANSITION__IMPLICIT_TRIGGER);

		stateEClass = createEClass(STATE);
		createEReference(stateEClass, STATE__IMPLICIT_REGION);
		createEReference(stateEClass, STATE__IMPLICIT_CONNECTION_POINT);
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
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		UMLPackage theUMLPackage = (UMLPackage) EPackage.Registry.INSTANCE.getEPackage(UMLPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		namespaceEClass.getESuperTypes().add(this.getElement());
		behavioredClassifierEClass.getESuperTypes().add(this.getNamespace());
		structuredClassifierEClass.getESuperTypes().add(this.getNamespace());
		encapsulatedClassifierEClass.getESuperTypes().add(this.getStructuredClassifier());
		classEClass.getESuperTypes().add(this.getEncapsulatedClassifier());
		classEClass.getESuperTypes().add(this.getBehavioredClassifier());
		interfaceEClass.getESuperTypes().add(this.getNamespace());
		stateMachineEClass.getESuperTypes().add(this.getNamespace());
		regionEClass.getESuperTypes().add(this.getNamespace());
		transitionEClass.getESuperTypes().add(this.getNamespace());
		stateEClass.getESuperTypes().add(this.getNamespace());

		// Initialize classes, features, and operations; add parameters
		initEClass(elementEClass, ExtElement.class, "Element", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getElement_ExtendedElement(), theUMLPackage.getElement(), null, "extendedElement", null, 1, 1, ExtElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getElement_Extension(), this.getElement(), null, "extension", null, 1, 1, ExtElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getElement_ImplicitOwnedElement(), theUMLPackage.getElement(), null, "implicitOwnedElement", null, 0, -1, ExtElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getElement_ExcludedElement(), theUMLPackage.getElement(), null, "excludedElement", null, 0, -1, ExtElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);

		initEOperation(getElement__GetExtension(), this.getElement(), "getExtension", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEOperation(getElement__GetExcludedElements(), theUMLPackage.getElement(), "getExcludedElements", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(namespaceEClass, ExtNamespace.class, "Namespace", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getNamespace_ImplicitOwnedMember(), theUMLPackage.getNamedElement(), null, "implicitOwnedMember", null, 0, -1, ExtNamespace.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);
		initEReference(getNamespace_ExcludedMember(), theUMLPackage.getNamedElement(), null, "excludedMember", null, 0, -1, ExtNamespace.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getNamespace_ImplicitOwnedRule(), theUMLPackage.getConstraint(), null, "implicitOwnedRule", null, 0, -1, ExtNamespace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				!IS_DERIVED, !IS_ORDERED);

		initEOperation(getNamespace__GetExcludedMembers(), theUMLPackage.getNamedElement(), "getExcludedMembers", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(behavioredClassifierEClass, ExtBehavioredClassifier.class, "BehavioredClassifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getBehavioredClassifier_ImplicitBehavior(), theUMLPackage.getBehavior(), null, "implicitBehavior", null, 0, -1, ExtBehavioredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, //$NON-NLS-1$
				IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(structuredClassifierEClass, ExtStructuredClassifier.class, "StructuredClassifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getStructuredClassifier_ImplicitAttribute(), theUMLPackage.getProperty(), null, "implicitAttribute", null, 0, -1, ExtStructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, //$NON-NLS-1$
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getStructuredClassifier_ImplicitConnector(), theUMLPackage.getConnector(), null, "implicitConnector", null, 0, -1, ExtStructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, //$NON-NLS-1$
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(encapsulatedClassifierEClass, ExtEncapsulatedClassifier.class, "EncapsulatedClassifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEncapsulatedClassifier_ImplicitPort(), theUMLPackage.getPort(), null, "implicitPort", null, 0, -1, ExtEncapsulatedClassifier.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, //$NON-NLS-1$
				IS_UNIQUE, IS_DERIVED, !IS_ORDERED);

		initEClass(classEClass, ExtClass.class, "Class", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getClass_ImplicitOperation(), theUMLPackage.getOperation(), null, "implicitOperation", null, 0, -1, ExtClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);

		initEClass(interfaceEClass, ExtInterface.class, "Interface", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInterface_ImplicitOperation(), theUMLPackage.getOperation(), null, "implicitOperation", null, 0, -1, ExtInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				!IS_DERIVED, !IS_ORDERED);

		initEClass(stateMachineEClass, ExtStateMachine.class, "StateMachine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getStateMachine_ImplicitRegion(), theUMLPackage.getRegion(), null, "implicitRegion", null, 0, -1, ExtStateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				!IS_DERIVED, !IS_ORDERED);
		initEReference(getStateMachine_ImplicitConnectionPoint(), theUMLPackage.getPseudostate(), null, "implicitConnectionPoint", null, 0, -1, ExtStateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, //$NON-NLS-1$
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(regionEClass, ExtRegion.class, "Region", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRegion_ImplicitSubvertex(), theUMLPackage.getVertex(), null, "implicitSubvertex", null, 0, -1, ExtRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getRegion_ImplicitTransition(), theUMLPackage.getTransition(), null, "implicitTransition", null, 0, -1, ExtRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				!IS_DERIVED, !IS_ORDERED);

		initEClass(transitionEClass, ExtTransition.class, "Transition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTransition_ImplicitTrigger(), theUMLPackage.getTrigger(), null, "implicitTrigger", null, 0, -1, ExtTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				!IS_DERIVED, !IS_ORDERED);

		initEClass(stateEClass, ExtState.class, "State", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getState_ImplicitRegion(), theUMLPackage.getRegion(), null, "implicitRegion", null, 0, -1, ExtState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getState_ImplicitConnectionPoint(), theUMLPackage.getPseudostate(), null, "implicitConnectionPoint", null, 0, -1, ExtState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				!IS_DERIVED, !IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// union
		createUnionAnnotations();
		// subsets
		createSubsetsAnnotations();
	}

	/**
	 * Initializes the annotations for <b>union</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createUnionAnnotations() {
		String source = "union"; //$NON-NLS-1$
		addAnnotation(getElement_ImplicitOwnedElement(),
				source,
				new String[] {
				});
		addAnnotation(getNamespace_ImplicitOwnedMember(),
				source,
				new String[] {
				});
	}

	/**
	 * Initializes the annotations for <b>subsets</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createSubsetsAnnotations() {
		String source = "subsets"; //$NON-NLS-1$
		addAnnotation(getNamespace_ImplicitOwnedMember(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Element/implicitOwnedElement") //$NON-NLS-1$
				});
		addAnnotation(getNamespace_ExcludedMember(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Element/excludedElement") //$NON-NLS-1$
				});
		addAnnotation(getNamespace_ImplicitOwnedRule(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Namespace/implicitOwnedMember") //$NON-NLS-1$
				});
		addAnnotation(getBehavioredClassifier_ImplicitBehavior(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Namespace/implicitOwnedMember") //$NON-NLS-1$
				});
		addAnnotation(getStructuredClassifier_ImplicitAttribute(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Namespace/implicitOwnedMember") //$NON-NLS-1$
				});
		addAnnotation(getStructuredClassifier_ImplicitConnector(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Namespace/implicitOwnedMember") //$NON-NLS-1$
				});
		addAnnotation(getEncapsulatedClassifier_ImplicitPort(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//StructuredClassifier/implicitAttribute") //$NON-NLS-1$
				});
		addAnnotation(getClass_ImplicitOperation(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Namespace/implicitOwnedMember") //$NON-NLS-1$
				});
		addAnnotation(getInterface_ImplicitOperation(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Namespace/implicitOwnedMember") //$NON-NLS-1$
				});
		addAnnotation(getStateMachine_ImplicitRegion(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Namespace/implicitOwnedMember") //$NON-NLS-1$
				});
		addAnnotation(getStateMachine_ImplicitConnectionPoint(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Namespace/implicitOwnedMember") //$NON-NLS-1$
				});
		addAnnotation(getRegion_ImplicitSubvertex(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Namespace/implicitOwnedMember") //$NON-NLS-1$
				});
		addAnnotation(getRegion_ImplicitTransition(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Namespace/implicitOwnedMember") //$NON-NLS-1$
				});
		addAnnotation(getTransition_ImplicitTrigger(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Element/implicitOwnedElement") //$NON-NLS-1$
				});
		addAnnotation(getState_ImplicitRegion(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Namespace/implicitOwnedMember") //$NON-NLS-1$
				});
		addAnnotation(getState_ImplicitConnectionPoint(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Namespace/implicitOwnedMember") //$NON-NLS-1$
				});
	}

} //UMLExtPackageImpl
