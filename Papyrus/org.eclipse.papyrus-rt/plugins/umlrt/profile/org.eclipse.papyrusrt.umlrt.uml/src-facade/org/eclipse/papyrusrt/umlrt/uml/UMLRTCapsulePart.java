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

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsulePartImpl;
import org.eclipse.uml2.uml.Property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Capsule Part</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation of the UML-RT concept of a <em>Capsule Part</em> in the
 * structural decomposition of a capsule.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getKind <em>Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#isOptional <em>Optional</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getRedefinedPart <em>Redefined Part</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getCapsule <em>Capsule</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsulePart()
 * @generated
 */
public interface UMLRTCapsulePart extends UMLRTReplicatedElement {
	/**
	 * Obtains the canonical instance of the capsule-part façade for a property.
	 *
	 * @param part
	 *            a property in the UML model
	 *
	 * @return its capsule-part façade, or {@code null} if the property is not a valid capsule-part
	 */
	public static UMLRTCapsulePart getInstance(Property part) {
		return UMLRTCapsulePartImpl.getInstance(part);
	}

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The kind of capsule-part.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind
	 * @see #setKind(UMLRTCapsulePartKind)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsulePart_Kind()
	 * @generated
	 */
	UMLRTCapsulePartKind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(UMLRTCapsulePartKind value);

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the capsule-part is optional.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see #setOptional(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsulePart_Optional()
	 * @generated
	 */
	boolean isOptional();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#isOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Optional</em>' attribute.
	 * @see #isOptional()
	 * @generated
	 */
	void setOptional(boolean value);

	/**
	 * Returns the value of the '<em><b>Redefined Part</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinedElement() <em>Redefined Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The inherited part that this part redefines, if any.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefined Part</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsulePart_RedefinedPart()
	 * @generated
	 */
	UMLRTCapsulePart getRedefinedPart();

	/**
	 * Returns the value of the '<em><b>Capsule</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getCapsuleParts <em>Capsule Part</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The capsule that contains this part (not its type).
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Capsule</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsulePart_Capsule()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getCapsuleParts
	 * @generated
	 */
	@Override
	UMLRTCapsule getCapsule();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The capsule that is the type of this part (not its containing capsule).
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(UMLRTCapsule)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsulePart_Type()
	 * @generated
	 */
	UMLRTCapsule getType();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(UMLRTCapsule value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries the "outside" connectors of a given port exposed by this part.
	 *
	 * @param port
	 *            A port that this part exposes.
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	List<UMLRTConnector> getConnectorsOf(UMLRTPort port);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries all of the "outside" connectors of ports that this
	 * part exposes, that connect this part.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	List<UMLRTConnector> getConnectorsOfPorts();

	@Override
	Stream<? extends UMLRTCapsulePart> allRedefinitions();

} // UMLRTCapsulePart
