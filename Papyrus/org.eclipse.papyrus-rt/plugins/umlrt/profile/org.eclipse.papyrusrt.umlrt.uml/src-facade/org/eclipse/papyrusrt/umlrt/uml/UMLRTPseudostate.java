/**
 * Copyright (c) 2017 Christian W. Damus and others.
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
package org.eclipse.papyrusrt.umlrt.uml;

import java.util.stream.Stream;

import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPseudostateImpl;
import org.eclipse.uml2.uml.Pseudostate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pseudostate</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation of the UML-RT concept of <em>Pseudostate</em> in a state machine.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate#getKind <em>Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate#getRedefinedPseudostate <em>Redefined Pseudostate</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPseudostate()
 * @generated
 */
public interface UMLRTPseudostate extends UMLRTVertex {
	/**
	 * Obtains the canonical instance of the façade for a pseudostate.
	 *
	 * @param pseudostate
	 *            a pseudostate in the UML model
	 *
	 * @return its façade, or {@code null} if the state is not a valid UML-RT pseudostate
	 */
	public static UMLRTPseudostate getInstance(Pseudostate pseudostate) {
		return UMLRTPseudostateImpl.getInstance(pseudostate);
	}

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The kind of pseudostate.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind
	 * @see #setKind(UMLRTPseudostateKind)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPseudostate_Kind()
	 * @generated
	 */
	UMLRTPseudostateKind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(UMLRTPseudostateKind value);

	/**
	 * Returns the value of the '<em><b>Redefined Pseudostate</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getRedefinedVertex() <em>Redefined Vertex</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Redefined Pseudostate</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Redefined Pseudostate</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPseudostate_RedefinedPseudostate()
	 * @generated
	 */
	UMLRTPseudostate getRedefinedPseudostate();

	/**
	 * <p>
	 * This operation redefines the following operations:
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#toUML() <em>To UML</em>}'</li>
	 * </ul>
	 * </p>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the underlying UML representation of this pseudostate.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	Pseudostate toUML();

	@Override
	Stream<? extends UMLRTPseudostate> allRedefinitions();

} // UMLRTPseudostate
