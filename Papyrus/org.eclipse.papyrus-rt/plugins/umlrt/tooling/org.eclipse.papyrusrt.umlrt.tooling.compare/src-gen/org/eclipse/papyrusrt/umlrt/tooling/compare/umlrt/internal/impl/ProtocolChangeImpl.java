/**
 * Copyright (c) 2016 EclipseSource Services GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Philip Langer (EclipseSource) - Initial API and implementation
 */
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTComparePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Protocol Change</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ProtocolChangeImpl extends UMLRTDiffImpl implements ProtocolChange {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ProtocolChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTComparePackage.Literals.PROTOCOL_CHANGE;
	}
	
	@Override
	public Diff getPrimeRefining() {
		return null;
	}

} // ProtocolChangeImpl
