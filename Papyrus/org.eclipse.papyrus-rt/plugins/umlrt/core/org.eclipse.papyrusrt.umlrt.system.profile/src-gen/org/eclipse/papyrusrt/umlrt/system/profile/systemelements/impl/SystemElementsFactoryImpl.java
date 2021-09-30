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
package org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrusrt.umlrt.system.profile.systemelements.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SystemElementsFactoryImpl extends EFactoryImpl implements SystemElementsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SystemElementsFactory init() {
		try {
			SystemElementsFactory theSystemElementsFactory = (SystemElementsFactory)EPackage.Registry.INSTANCE.getEFactory(SystemElementsPackage.eNS_URI);
			if (theSystemElementsFactory != null) {
				return theSystemElementsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SystemElementsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SystemElementsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SystemElementsPackage.SYSTEM_PROTOCOL: return createSystemProtocol();
			case SystemElementsPackage.BASE_PROTOCOL: return createBaseProtocol();
			case SystemElementsPackage.SYSTEM_CLASS: return createSystemClass();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SystemProtocol createSystemProtocol() {
		SystemProtocolImpl systemProtocol = new SystemProtocolImpl();
		return systemProtocol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseProtocol createBaseProtocol() {
		BaseProtocolImpl baseProtocol = new BaseProtocolImpl();
		return baseProtocol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SystemClass createSystemClass() {
		SystemClassImpl systemClass = new SystemClassImpl();
		return systemClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SystemElementsPackage getSystemElementsPackage() {
		return (SystemElementsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SystemElementsPackage getPackage() {
		return SystemElementsPackage.eINSTANCE;
	}

} //SystemElementsFactoryImpl
