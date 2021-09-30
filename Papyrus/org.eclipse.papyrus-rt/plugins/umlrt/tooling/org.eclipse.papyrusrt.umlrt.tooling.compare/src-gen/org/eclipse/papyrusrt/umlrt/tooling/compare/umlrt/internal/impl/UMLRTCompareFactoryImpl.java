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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.*;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTCompareFactory;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTComparePackage;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiff;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class UMLRTCompareFactoryImpl extends EFactoryImpl implements UMLRTCompareFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public static UMLRTCompareFactory init() {
		try {
			UMLRTCompareFactory theUMLRTCompareFactory = (UMLRTCompareFactory) EPackage.Registry.INSTANCE.getEFactory(UMLRTComparePackage.eNS_URI);
			if (theUMLRTCompareFactory != null) {
				return theUMLRTCompareFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new UMLRTCompareFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public UMLRTCompareFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case UMLRTComparePackage.UMLRT_DIFF:
			return createUMLRTDiff();
		case UMLRTComparePackage.PROTOCOL_CHANGE:
			return createProtocolChange();
		case UMLRTComparePackage.PROTOCOL_MESSAGE_CHANGE:
			return createProtocolMessageChange();
		case UMLRTComparePackage.PROTOCOL_MESSAGE_PARAMETER_CHANGE:
			return createProtocolMessageParameterChange();
		case UMLRTComparePackage.UMLRT_DIAGRAM_CHANGE:
			return createUMLRTDiagramChange();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public UMLRTDiff createUMLRTDiff() {
		UMLRTDiffImpl umlrtDiff = new UMLRTDiffImpl();
		return umlrtDiff;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolChange createProtocolChange() {
		ProtocolChangeImpl protocolChange = new ProtocolChangeImpl();
		return protocolChange;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolMessageChange createProtocolMessageChange() {
		ProtocolMessageChangeImpl protocolMessageChange = new ProtocolMessageChangeImpl();
		return protocolMessageChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolMessageParameterChange createProtocolMessageParameterChange() {
		ProtocolMessageParameterChangeImpl protocolMessageParameterChange = new ProtocolMessageParameterChangeImpl();
		return protocolMessageParameterChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UMLRTDiagramChange createUMLRTDiagramChange() {
		UMLRTDiagramChangeImpl umlrtDiagramChange = new UMLRTDiagramChangeImpl();
		return umlrtDiagramChange;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public UMLRTComparePackage getUMLRTComparePackage() {
		return (UMLRTComparePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static UMLRTComparePackage getPackage() {
		return UMLRTComparePackage.eINSTANCE;
	}

} // UMLRTCompareFactoryImpl
