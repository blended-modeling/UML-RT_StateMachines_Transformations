/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.RedefinableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RT Redefinable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement#getBase_RedefinableElement <em>Base Redefinable Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement#getRootFragment <em>Root Fragment</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTRedefinableElement()
 * @model
 * @generated
 */
public interface RTRedefinableElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Base Redefinable Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Redefinable Element</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Base Redefinable Element</em>' reference.
	 * @see #setBase_RedefinableElement(RedefinableElement)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTRedefinableElement_Base_RedefinableElement()
	 * @model required="true"
	 * @generated
	 */
	RedefinableElement getBase_RedefinableElement();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement#getBase_RedefinableElement <em>Base Redefinable Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Base Redefinable Element</em>' reference.
	 * @see #getBase_RedefinableElement()
	 * @generated
	 */
	void setBase_RedefinableElement(RedefinableElement value);

	/**
	 * Returns the value of the '<em><b>Root Fragment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Fragment</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Root Fragment</em>' reference.
	 * @see #setRootFragment(RedefinableElement)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#getRTRedefinableElement_RootFragment()
	 * @model
	 * @generated
	 */
	RedefinableElement getRootFragment();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement#getRootFragment <em>Root Fragment</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Root Fragment</em>' reference.
	 * @see #getRootFragment()
	 * @generated
	 */
	void setRootFragment(RedefinableElement value);

} // RTRedefinableElement
