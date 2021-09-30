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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.uml2.uml.Collaboration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Base Protocol</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * base_Collaboration.isAbstract
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.BaseProtocol#getBase_Collaboration <em>Base Collaboration</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemElementsPackage#getBaseProtocol()
 * @model
 * @generated
 */
public interface BaseProtocol extends EObject {
	/**
	 * Returns the value of the '<em><b>Base Collaboration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Collaboration</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Collaboration</em>' reference.
	 * @see #setBase_Collaboration(Collaboration)
	 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemElementsPackage#getBaseProtocol_Base_Collaboration()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Collaboration getBase_Collaboration();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.BaseProtocol#getBase_Collaboration <em>Base Collaboration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Collaboration</em>' reference.
	 * @see #getBase_Collaboration()
	 * @generated
	 */
	void setBase_Collaboration(Collaboration value);

} // BaseProtocol
