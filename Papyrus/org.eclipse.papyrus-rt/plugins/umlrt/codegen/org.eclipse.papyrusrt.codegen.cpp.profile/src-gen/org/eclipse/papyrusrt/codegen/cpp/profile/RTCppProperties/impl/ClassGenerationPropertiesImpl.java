/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassGenerationProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class Generation Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassGenerationPropertiesImpl#isGenerateStateMachine <em>Generate State Machine</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassGenerationPropertiesImpl#isGenerateAssignmentOperator <em>Generate Assignment Operator</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassGenerationPropertiesImpl#isGenerateCopyConstructor <em>Generate Copy Constructor</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassGenerationPropertiesImpl#isGenerateDefaultConstructor <em>Generate Default Constructor</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassGenerationPropertiesImpl#isGenerateDestructor <em>Generate Destructor</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassGenerationPropertiesImpl#isGenerateEqualityOperator <em>Generate Equality Operator</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassGenerationPropertiesImpl#isGenerateExtractionOperator <em>Generate Extraction Operator</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassGenerationPropertiesImpl#isGenerateInequalityOperator <em>Generate Inequality Operator</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassGenerationPropertiesImpl#isGenerateInsertionOperator <em>Generate Insertion Operator</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ClassGenerationPropertiesImpl extends MinimalEObjectImpl.Container implements ClassGenerationProperties {
	/**
	 * The default value of the '{@link #isGenerateStateMachine() <em>Generate State Machine</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateStateMachine()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_STATE_MACHINE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerateStateMachine() <em>Generate State Machine</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateStateMachine()
	 * @generated
	 * @ordered
	 */
	protected boolean generateStateMachine = GENERATE_STATE_MACHINE_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateAssignmentOperator() <em>Generate Assignment Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateAssignmentOperator()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_ASSIGNMENT_OPERATOR_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerateAssignmentOperator() <em>Generate Assignment Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateAssignmentOperator()
	 * @generated
	 * @ordered
	 */
	protected boolean generateAssignmentOperator = GENERATE_ASSIGNMENT_OPERATOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateCopyConstructor() <em>Generate Copy Constructor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateCopyConstructor()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_COPY_CONSTRUCTOR_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerateCopyConstructor() <em>Generate Copy Constructor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateCopyConstructor()
	 * @generated
	 * @ordered
	 */
	protected boolean generateCopyConstructor = GENERATE_COPY_CONSTRUCTOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateDefaultConstructor() <em>Generate Default Constructor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateDefaultConstructor()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_DEFAULT_CONSTRUCTOR_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerateDefaultConstructor() <em>Generate Default Constructor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateDefaultConstructor()
	 * @generated
	 * @ordered
	 */
	protected boolean generateDefaultConstructor = GENERATE_DEFAULT_CONSTRUCTOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateDestructor() <em>Generate Destructor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateDestructor()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_DESTRUCTOR_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerateDestructor() <em>Generate Destructor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateDestructor()
	 * @generated
	 * @ordered
	 */
	protected boolean generateDestructor = GENERATE_DESTRUCTOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateEqualityOperator() <em>Generate Equality Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateEqualityOperator()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_EQUALITY_OPERATOR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGenerateEqualityOperator() <em>Generate Equality Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateEqualityOperator()
	 * @generated
	 * @ordered
	 */
	protected boolean generateEqualityOperator = GENERATE_EQUALITY_OPERATOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateExtractionOperator() <em>Generate Extraction Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateExtractionOperator()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_EXTRACTION_OPERATOR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGenerateExtractionOperator() <em>Generate Extraction Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateExtractionOperator()
	 * @generated
	 * @ordered
	 */
	protected boolean generateExtractionOperator = GENERATE_EXTRACTION_OPERATOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateInequalityOperator() <em>Generate Inequality Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateInequalityOperator()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_INEQUALITY_OPERATOR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGenerateInequalityOperator() <em>Generate Inequality Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateInequalityOperator()
	 * @generated
	 * @ordered
	 */
	protected boolean generateInequalityOperator = GENERATE_INEQUALITY_OPERATOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateInsertionOperator() <em>Generate Insertion Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateInsertionOperator()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_INSERTION_OPERATOR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGenerateInsertionOperator() <em>Generate Insertion Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateInsertionOperator()
	 * @generated
	 * @ordered
	 */
	protected boolean generateInsertionOperator = GENERATE_INSERTION_OPERATOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassGenerationPropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.CLASS_GENERATION_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateStateMachine() {
		return generateStateMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateStateMachine(boolean newGenerateStateMachine) {
		boolean oldGenerateStateMachine = generateStateMachine;
		generateStateMachine = newGenerateStateMachine;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_STATE_MACHINE, oldGenerateStateMachine, generateStateMachine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateAssignmentOperator() {
		return generateAssignmentOperator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateAssignmentOperator(boolean newGenerateAssignmentOperator) {
		boolean oldGenerateAssignmentOperator = generateAssignmentOperator;
		generateAssignmentOperator = newGenerateAssignmentOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_ASSIGNMENT_OPERATOR, oldGenerateAssignmentOperator, generateAssignmentOperator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateCopyConstructor() {
		return generateCopyConstructor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateCopyConstructor(boolean newGenerateCopyConstructor) {
		boolean oldGenerateCopyConstructor = generateCopyConstructor;
		generateCopyConstructor = newGenerateCopyConstructor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_COPY_CONSTRUCTOR, oldGenerateCopyConstructor, generateCopyConstructor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateDefaultConstructor() {
		return generateDefaultConstructor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateDefaultConstructor(boolean newGenerateDefaultConstructor) {
		boolean oldGenerateDefaultConstructor = generateDefaultConstructor;
		generateDefaultConstructor = newGenerateDefaultConstructor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_DEFAULT_CONSTRUCTOR, oldGenerateDefaultConstructor, generateDefaultConstructor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateDestructor() {
		return generateDestructor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateDestructor(boolean newGenerateDestructor) {
		boolean oldGenerateDestructor = generateDestructor;
		generateDestructor = newGenerateDestructor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_DESTRUCTOR, oldGenerateDestructor, generateDestructor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateEqualityOperator() {
		return generateEqualityOperator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateEqualityOperator(boolean newGenerateEqualityOperator) {
		boolean oldGenerateEqualityOperator = generateEqualityOperator;
		generateEqualityOperator = newGenerateEqualityOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_EQUALITY_OPERATOR, oldGenerateEqualityOperator, generateEqualityOperator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateExtractionOperator() {
		return generateExtractionOperator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateExtractionOperator(boolean newGenerateExtractionOperator) {
		boolean oldGenerateExtractionOperator = generateExtractionOperator;
		generateExtractionOperator = newGenerateExtractionOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_EXTRACTION_OPERATOR, oldGenerateExtractionOperator, generateExtractionOperator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateInequalityOperator() {
		return generateInequalityOperator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateInequalityOperator(boolean newGenerateInequalityOperator) {
		boolean oldGenerateInequalityOperator = generateInequalityOperator;
		generateInequalityOperator = newGenerateInequalityOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_INEQUALITY_OPERATOR, oldGenerateInequalityOperator, generateInequalityOperator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateInsertionOperator() {
		return generateInsertionOperator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateInsertionOperator(boolean newGenerateInsertionOperator) {
		boolean oldGenerateInsertionOperator = generateInsertionOperator;
		generateInsertionOperator = newGenerateInsertionOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_INSERTION_OPERATOR, oldGenerateInsertionOperator, generateInsertionOperator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_STATE_MACHINE:
				return isGenerateStateMachine();
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_ASSIGNMENT_OPERATOR:
				return isGenerateAssignmentOperator();
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_COPY_CONSTRUCTOR:
				return isGenerateCopyConstructor();
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_DEFAULT_CONSTRUCTOR:
				return isGenerateDefaultConstructor();
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_DESTRUCTOR:
				return isGenerateDestructor();
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_EQUALITY_OPERATOR:
				return isGenerateEqualityOperator();
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_EXTRACTION_OPERATOR:
				return isGenerateExtractionOperator();
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_INEQUALITY_OPERATOR:
				return isGenerateInequalityOperator();
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_INSERTION_OPERATOR:
				return isGenerateInsertionOperator();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_STATE_MACHINE:
				setGenerateStateMachine((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_ASSIGNMENT_OPERATOR:
				setGenerateAssignmentOperator((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_COPY_CONSTRUCTOR:
				setGenerateCopyConstructor((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_DEFAULT_CONSTRUCTOR:
				setGenerateDefaultConstructor((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_DESTRUCTOR:
				setGenerateDestructor((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_EQUALITY_OPERATOR:
				setGenerateEqualityOperator((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_EXTRACTION_OPERATOR:
				setGenerateExtractionOperator((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_INEQUALITY_OPERATOR:
				setGenerateInequalityOperator((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_INSERTION_OPERATOR:
				setGenerateInsertionOperator((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_STATE_MACHINE:
				setGenerateStateMachine(GENERATE_STATE_MACHINE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_ASSIGNMENT_OPERATOR:
				setGenerateAssignmentOperator(GENERATE_ASSIGNMENT_OPERATOR_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_COPY_CONSTRUCTOR:
				setGenerateCopyConstructor(GENERATE_COPY_CONSTRUCTOR_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_DEFAULT_CONSTRUCTOR:
				setGenerateDefaultConstructor(GENERATE_DEFAULT_CONSTRUCTOR_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_DESTRUCTOR:
				setGenerateDestructor(GENERATE_DESTRUCTOR_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_EQUALITY_OPERATOR:
				setGenerateEqualityOperator(GENERATE_EQUALITY_OPERATOR_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_EXTRACTION_OPERATOR:
				setGenerateExtractionOperator(GENERATE_EXTRACTION_OPERATOR_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_INEQUALITY_OPERATOR:
				setGenerateInequalityOperator(GENERATE_INEQUALITY_OPERATOR_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_INSERTION_OPERATOR:
				setGenerateInsertionOperator(GENERATE_INSERTION_OPERATOR_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_STATE_MACHINE:
				return generateStateMachine != GENERATE_STATE_MACHINE_EDEFAULT;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_ASSIGNMENT_OPERATOR:
				return generateAssignmentOperator != GENERATE_ASSIGNMENT_OPERATOR_EDEFAULT;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_COPY_CONSTRUCTOR:
				return generateCopyConstructor != GENERATE_COPY_CONSTRUCTOR_EDEFAULT;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_DEFAULT_CONSTRUCTOR:
				return generateDefaultConstructor != GENERATE_DEFAULT_CONSTRUCTOR_EDEFAULT;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_DESTRUCTOR:
				return generateDestructor != GENERATE_DESTRUCTOR_EDEFAULT;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_EQUALITY_OPERATOR:
				return generateEqualityOperator != GENERATE_EQUALITY_OPERATOR_EDEFAULT;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_EXTRACTION_OPERATOR:
				return generateExtractionOperator != GENERATE_EXTRACTION_OPERATOR_EDEFAULT;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_INEQUALITY_OPERATOR:
				return generateInequalityOperator != GENERATE_INEQUALITY_OPERATOR_EDEFAULT;
			case RTCppPropertiesPackage.CLASS_GENERATION_PROPERTIES__GENERATE_INSERTION_OPERATOR:
				return generateInsertionOperator != GENERATE_INSERTION_OPERATOR_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (generateStateMachine: ");
		result.append(generateStateMachine);
		result.append(", generateAssignmentOperator: ");
		result.append(generateAssignmentOperator);
		result.append(", generateCopyConstructor: ");
		result.append(generateCopyConstructor);
		result.append(", generateDefaultConstructor: ");
		result.append(generateDefaultConstructor);
		result.append(", generateDestructor: ");
		result.append(generateDestructor);
		result.append(", generateEqualityOperator: ");
		result.append(generateEqualityOperator);
		result.append(", generateExtractionOperator: ");
		result.append(generateExtractionOperator);
		result.append(", generateInequalityOperator: ");
		result.append(generateInequalityOperator);
		result.append(", generateInsertionOperator: ");
		result.append(generateInsertionOperator);
		result.append(')');
		return result.toString();
	}

} //ClassGenerationPropertiesImpl
