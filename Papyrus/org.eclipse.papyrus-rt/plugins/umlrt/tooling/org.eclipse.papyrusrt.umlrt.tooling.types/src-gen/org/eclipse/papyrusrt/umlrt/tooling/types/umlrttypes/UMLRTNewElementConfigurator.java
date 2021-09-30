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
package org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes;

import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdviceConfiguration;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>UMLRT
 * New Element Configurator</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTNewElementConfigurator#getDialogTitlePattern <em>Dialog Title Pattern</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage#getUMLRTNewElementConfigurator()
 * @model
 * @generated
 */
public interface UMLRTNewElementConfigurator extends RuntimeValuesAdviceConfiguration {

	/**
	 * Returns the value of the '<em><b>Dialog Title Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Message pattern for the dialog title. A '{0}' is replaced with the
	 * user-friendly name of the element-type being configured. If not
	 * specified, a default title is computed as in the
	 * RuntimeValuesAdviceConfiguration.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Dialog Title Pattern</em>' attribute.
	 * @see #setDialogTitlePattern(String)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage#getUMLRTNewElementConfigurator_DialogTitlePattern()
	 * @model
	 * @generated
	 */
	String getDialogTitlePattern();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTNewElementConfigurator#getDialogTitlePattern <em>Dialog Title Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Dialog Title Pattern</em>' attribute.
	 * @see #getDialogTitlePattern()
	 * @generated
	 */
	void setDialogTitlePattern(String value);

} // UMLRTNewElementConfigurator
