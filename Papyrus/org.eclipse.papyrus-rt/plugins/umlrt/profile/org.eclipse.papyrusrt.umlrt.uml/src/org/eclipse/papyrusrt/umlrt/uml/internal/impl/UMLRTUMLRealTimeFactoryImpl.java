/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import java.lang.reflect.Proxy;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimeFactory;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.impl.UMLRealTimeFactoryImpl;

/**
 * @author damus
 *
 */
public class UMLRTUMLRealTimeFactoryImpl extends UMLRealTimeFactoryImpl {

	public static final UMLRealTimeFactory eINSTANCE = new UMLRTUMLRealTimeFactoryImpl();

	private final UMLRealTimePackage umlRealTimePackage;

	/**
	 * Initializes me.
	 */
	public UMLRTUMLRealTimeFactoryImpl() {
		super();

		// A shim for the UMLRealTimePackage that provides me as its factory
		this.umlRealTimePackage = (UMLRealTimePackage) Proxy.newProxyInstance(getClass().getClassLoader(),
				new java.lang.Class[] {
						UMLRealTimePackage.class,
						BasicExtendedMetaData.EPackageExtendedMetaData.Holder.class,
				},
				(proxy, method, args) -> {
					switch (method.getName()) {
					case "getEFactoryInstance": //$NON-NLS-1$
					case "getUMLRealTimeFactory": //$NON-NLS-1$
						return UMLRTUMLRealTimeFactoryImpl.eINSTANCE;
					default:
						return method.invoke(UMLRealTimePackage.eINSTANCE, args);
					}
				});
	}

	@Override
	public EPackage getEPackage() {
		return umlRealTimePackage;
	}

	@Override
	public RTPort createRTPort() {
		return new RTPortRTImpl();
	}
}
