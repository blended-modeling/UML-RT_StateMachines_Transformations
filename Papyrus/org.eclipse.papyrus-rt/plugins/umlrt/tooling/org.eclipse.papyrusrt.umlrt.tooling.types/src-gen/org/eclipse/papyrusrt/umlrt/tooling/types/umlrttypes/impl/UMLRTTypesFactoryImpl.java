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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.*;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class UMLRTTypesFactoryImpl extends EFactoryImpl implements UMLRTTypesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public static UMLRTTypesFactory init() {
		try {
			UMLRTTypesFactory theUMLRTTypesFactory = (UMLRTTypesFactory) EPackage.Registry.INSTANCE.getEFactory(UMLRTTypesPackage.eNS_URI);
			if (theUMLRTTypesFactory != null) {
				return theUMLRTTypesFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new UMLRTTypesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTTypesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case UMLRTTypesPackage.UMLRT_NEW_ELEMENT_CONFIGURATOR:
			return createUMLRTNewElementConfigurator();
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION:
			return createUMLRTSetTypeAdviceConfiguration();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNewElementConfigurator createUMLRTNewElementConfigurator() {
		UMLRTNewElementConfiguratorImpl umlrtNewElementConfigurator = new UMLRTNewElementConfiguratorImpl();
		return umlrtNewElementConfigurator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTSetTypeAdviceConfiguration createUMLRTSetTypeAdviceConfiguration() {
		UMLRTSetTypeAdviceConfigurationImpl umlrtSetTypeAdviceConfiguration = new UMLRTSetTypeAdviceConfigurationImpl();
		return umlrtSetTypeAdviceConfiguration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTypesPackage getUMLRTTypesPackage() {
		return (UMLRTTypesPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static UMLRTTypesPackage getPackage() {
		return UMLRTTypesPackage.eINSTANCE;
	}

} // UMLRTTypesFactoryImpl
