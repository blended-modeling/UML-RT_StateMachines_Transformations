/**
 * Copyright (c) 2016 CEA LIST and others.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *     CEA LIST - Initial API and implementation
 * 
 */
package org.eclipse.papyrusrt.umlrt.system.profile.systemelements;

import org.eclipse.emf.ecore.EClass;
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
 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemElementsFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/uml2/2.0.0/UML originalName='system elements'"
 * @generated
 */
public interface SystemElementsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "systemelements";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/umlrt/systemelements";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "SystemElements";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SystemElementsPackage eINSTANCE = org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemElementsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemProtocolImpl <em>System Protocol</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemProtocolImpl
	 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemElementsPackageImpl#getSystemProtocol()
	 * @generated
	 */
	int SYSTEM_PROTOCOL = 0;

	/**
	 * The feature id for the '<em><b>Base Collaboration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_PROTOCOL__BASE_COLLABORATION = 0;

	/**
	 * The number of structural features of the '<em>System Protocol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_PROTOCOL_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>System Protocol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_PROTOCOL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.BaseProtocolImpl <em>Base Protocol</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.BaseProtocolImpl
	 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemElementsPackageImpl#getBaseProtocol()
	 * @generated
	 */
	int BASE_PROTOCOL = 1;

	/**
	 * The feature id for the '<em><b>Base Collaboration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_PROTOCOL__BASE_COLLABORATION = 0;

	/**
	 * The number of structural features of the '<em>Base Protocol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_PROTOCOL_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Base Protocol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_PROTOCOL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemClassImpl <em>System Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemClassImpl
	 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemElementsPackageImpl#getSystemClass()
	 * @generated
	 */
	int SYSTEM_CLASS = 2;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_CLASS__BASE_CLASS = 0;

	/**
	 * The number of structural features of the '<em>System Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_CLASS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>System Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_CLASS_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemProtocol <em>System Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>System Protocol</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemProtocol
	 * @generated
	 */
	EClass getSystemProtocol();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemProtocol#getBase_Collaboration <em>Base Collaboration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Collaboration</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemProtocol#getBase_Collaboration()
	 * @see #getSystemProtocol()
	 * @generated
	 */
	EReference getSystemProtocol_Base_Collaboration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.BaseProtocol <em>Base Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base Protocol</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.BaseProtocol
	 * @generated
	 */
	EClass getBaseProtocol();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.BaseProtocol#getBase_Collaboration <em>Base Collaboration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Collaboration</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.BaseProtocol#getBase_Collaboration()
	 * @see #getBaseProtocol()
	 * @generated
	 */
	EReference getBaseProtocol_Base_Collaboration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemClass <em>System Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>System Class</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemClass
	 * @generated
	 */
	EClass getSystemClass();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemClass#getBase_Class <em>Base Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Class</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemClass#getBase_Class()
	 * @see #getSystemClass()
	 * @generated
	 */
	EReference getSystemClass_Base_Class();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SystemElementsFactory getSystemElementsFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemProtocolImpl <em>System Protocol</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemProtocolImpl
		 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemElementsPackageImpl#getSystemProtocol()
		 * @generated
		 */
		EClass SYSTEM_PROTOCOL = eINSTANCE.getSystemProtocol();

		/**
		 * The meta object literal for the '<em><b>Base Collaboration</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYSTEM_PROTOCOL__BASE_COLLABORATION = eINSTANCE.getSystemProtocol_Base_Collaboration();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.BaseProtocolImpl <em>Base Protocol</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.BaseProtocolImpl
		 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemElementsPackageImpl#getBaseProtocol()
		 * @generated
		 */
		EClass BASE_PROTOCOL = eINSTANCE.getBaseProtocol();

		/**
		 * The meta object literal for the '<em><b>Base Collaboration</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASE_PROTOCOL__BASE_COLLABORATION = eINSTANCE.getBaseProtocol_Base_Collaboration();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemClassImpl <em>System Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemClassImpl
		 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemElementsPackageImpl#getSystemClass()
		 * @generated
		 */
		EClass SYSTEM_CLASS = eINSTANCE.getSystemClass();

		/**
		 * The meta object literal for the '<em><b>Base Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYSTEM_CLASS__BASE_CLASS = eINSTANCE.getSystemClass_Base_Class();

	}

} //SystemElementsPackage
