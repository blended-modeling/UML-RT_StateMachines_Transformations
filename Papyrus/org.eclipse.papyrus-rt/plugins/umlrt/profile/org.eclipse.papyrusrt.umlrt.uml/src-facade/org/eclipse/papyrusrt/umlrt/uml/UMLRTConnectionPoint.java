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

import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectionPointImpl;
import org.eclipse.uml2.uml.Pseudostate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint#getKind <em>Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint#getRedefinedConnectionPoint <em>Redefined Connection Point</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getConnectionPoint()
 * @generated
 */
public interface UMLRTConnectionPoint extends UMLRTVertex {
	/**
	 * Obtains the canonical instance of the façade for a connection-point pseudostate.
	 *
	 * @param connectionPoint
	 *            a connection point in the UML model
	 *
	 * @return its façade, or {@code null} if the state is not a valid UML-RT connection point
	 */
	public static UMLRTConnectionPoint getInstance(Pseudostate connectionPoint) {
		return UMLRTConnectionPointImpl.getInstance(connectionPoint);
	}

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The kind of connection point.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind
	 * @see #setKind(UMLRTConnectionPointKind)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getConnectionPoint_Kind()
	 * @generated
	 */
	UMLRTConnectionPointKind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(UMLRTConnectionPointKind value);

	/**
	 * Returns the value of the '<em><b>Redefined Connection Point</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getRedefinedVertex() <em>Redefined Vertex</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Redefined Connection Point</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Redefined Connection Point</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getConnectionPoint_RedefinedConnectionPoint()
	 * @generated
	 */
	UMLRTConnectionPoint getRedefinedConnectionPoint();

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
	 * Obtains the underlying UML representation of this connection point.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	Pseudostate toUML();

	@Override
	Stream<? extends UMLRTConnectionPoint> allRedefinitions();

} // UMLRTConnectionPoint
