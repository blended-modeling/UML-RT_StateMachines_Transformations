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
package org.eclipse.papyrusrt.umlrt.uml.internal.umlext;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extension Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExtendedElement <em>Extended Element</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExtension <em>Extension</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getImplicitOwnedElements <em>Implicit Owned Element</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExcludedElements <em>Excluded Element</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getElement()
 * @model abstract="true"
 * @generated
 */
public interface ExtElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Extended Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The UML model element that is extended by my features.
	 * This is a non-resolving reference to ensure that attempts
	 * to access the extension of an unloaded element does not
	 * re-load its resource.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Extended Element</em>' reference.
	 * @see #setExtendedElement(Element)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getElement_ExtendedElement()
	 * @model resolveProxies="false" required="true" ordered="false"
	 * @generated
	 */
	Element getExtendedElement();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExtendedElement <em>Extended Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extended Element</em>' reference.
	 * @see #getExtendedElement()
	 * @generated
	 */
	void setExtendedElement(Element value);

	/**
	 * Returns the value of the '<em><b>Extension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This looks odd as a required self-reference, but it is actually meant
	 * to be accessed as an extension feature of the extended UML model
	 * element, in order to access the extension object.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Extension</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getElement_Extension()
	 * @model resolveProxies="false" required="true" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	ExtElement getExtension();

	/**
	 * Returns the value of the '<em><b>Implicit Owned Element</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.uml.Element}.
	 * This feature is a derived union.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implicit Owned Element</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implicit Owned Element</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getElement_ImplicitOwnedElement()
	 * @model transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList<Element> getImplicitOwnedElements();

	/**
	 * Returns the value of the '<em><b>Excluded Element</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.uml.Element}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Excluded Element</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Excluded Element</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage#getElement_ExcludedElement()
	 * @model transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList<Element> getExcludedElements();

} // ExtensionElement
