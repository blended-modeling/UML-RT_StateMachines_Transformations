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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtClass;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtInterface;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtState;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtTransition;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtFactory;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExtUMLExtFactoryImpl extends EFactoryImpl implements ExtUMLExtFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ExtUMLExtFactory init() {
		try {
			ExtUMLExtFactory theUMLExtFactory = (ExtUMLExtFactory) EPackage.Registry.INSTANCE.getEFactory(ExtUMLExtPackage.eNS_URI);
			if (theUMLExtFactory != null) {
				return theUMLExtFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ExtUMLExtFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtUMLExtFactoryImpl() {
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
		case ExtUMLExtPackage.CLASS:
			return createClass();
		case ExtUMLExtPackage.INTERFACE:
			return createInterface();
		case ExtUMLExtPackage.STATE_MACHINE:
			return createStateMachine();
		case ExtUMLExtPackage.REGION:
			return createRegion();
		case ExtUMLExtPackage.TRANSITION:
			return createTransition();
		case ExtUMLExtPackage.STATE:
			return createState();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExtClass createClass() {
		ExtClassImpl class_ = new ExtClassImpl();
		return class_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExtInterface createInterface() {
		ExtInterfaceImpl interface_ = new ExtInterfaceImpl();
		return interface_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExtStateMachine createStateMachine() {
		ExtStateMachineImpl stateMachine = new ExtStateMachineImpl();
		return stateMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExtRegion createRegion() {
		ExtRegionImpl region = new ExtRegionImpl();
		return region;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExtTransition createTransition() {
		ExtTransitionImpl transition = new ExtTransitionImpl();
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExtState createState() {
		ExtStateImpl state = new ExtStateImpl();
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExtUMLExtPackage getUMLExtPackage() {
		return (ExtUMLExtPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ExtUMLExtPackage getPackage() {
		return ExtUMLExtPackage.eINSTANCE;
	}

} //UMLExtFactoryImpl
