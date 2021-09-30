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
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTComparePackage
 * @generated
 */
public interface UMLRTCompareFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	UMLRTCompareFactory eINSTANCE = org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTCompareFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>UMLRT Diff</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>UMLRT Diff</em>'.
	 * @generated
	 */
	UMLRTDiff createUMLRTDiff();

	/**
	 * Returns a new object of class '<em>Protocol Change</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Protocol Change</em>'.
	 * @generated
	 */
	ProtocolChange createProtocolChange();

	/**
	 * Returns a new object of class '<em>Protocol Message Change</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Protocol Message Change</em>'.
	 * @generated
	 */
	ProtocolMessageChange createProtocolMessageChange();

	/**
	 * Returns a new object of class '<em>Protocol Message Parameter Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Protocol Message Parameter Change</em>'.
	 * @generated
	 */
	ProtocolMessageParameterChange createProtocolMessageParameterChange();

	/**
	 * Returns a new object of class '<em>UMLRT Diagram Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>UMLRT Diagram Change</em>'.
	 * @generated
	 */
	UMLRTDiagramChange createUMLRTDiagramChange();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	UMLRTComparePackage getUMLRTComparePackage();

} // UMLRTCompareFactory
