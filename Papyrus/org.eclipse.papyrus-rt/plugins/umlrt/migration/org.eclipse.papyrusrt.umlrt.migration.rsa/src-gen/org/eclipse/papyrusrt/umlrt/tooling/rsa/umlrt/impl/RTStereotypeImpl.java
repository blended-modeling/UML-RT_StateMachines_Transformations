/**
 *  * Copyright (c) 2013, 2014 CEA LIST.
 *  *
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  * Contributors:
 *  *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTStereotype;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RT Stereotype</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 *
 * @generated
 */
public abstract class RTStereotypeImpl extends MinimalEObjectImpl.Container implements RTStereotype {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RTStereotypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRealTimePackage.Literals.RT_STEREOTYPE;
	}

} // RTStereotypeImpl
