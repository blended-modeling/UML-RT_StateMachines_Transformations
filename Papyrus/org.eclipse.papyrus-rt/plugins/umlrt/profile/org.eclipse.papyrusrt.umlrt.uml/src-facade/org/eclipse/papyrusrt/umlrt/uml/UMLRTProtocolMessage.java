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

import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolMessageImpl;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Protocol Message</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation of the UML-RT concept of <em>Protocol Message</em>.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getKind <em>Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getRedefinedMessage <em>Redefined Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getParameters <em>Parameter</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#isConjugate <em>Is Conjugate</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getConjugate <em>Conjugate</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getProtocol <em>Protocol</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocolMessage()
 * @generated
 */
public interface UMLRTProtocolMessage extends UMLRTNamedElement {

	/**
	 * Obtains the protocol message façade for the message represented by the given
	 * UML operation.
	 *
	 * @param operation
	 *            a UML operation representing a protocol message
	 * @return the base UML-RT protocol-message façade, or {@code null} if the operation
	 *         is not actually a protocol message
	 */
	public static UMLRTProtocolMessage getInstance(Operation operation) {
		return UMLRTProtocolMessageImpl.getInstance(operation);
	}

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The protocol message's direction kind.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind
	 * @see #setKind(RTMessageKind)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocolMessage_Kind()
	 * @generated
	 */
	RTMessageKind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(RTMessageKind value);

	/**
	 * Returns the value of the '<em><b>Redefined Message</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinedElement() <em>Redefined Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The inherited message that this protocol message redefines, if any.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefined Message</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocolMessage_RedefinedMessage()
	 * @generated
	 */
	UMLRTProtocolMessage getRedefinedMessage();

	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.uml.Parameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The parameters of the protocol message.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Parameter</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocolMessage_Parameter()
	 * @generated
	 */
	List<Parameter> getParameters();

	/**
	 * Retrieves the first parameter with the specified {@code name}.
	 *
	 * @param name
	 *            the name of a parameter to retrieve
	 * @return the first parameter with the given {@code name}
	 *
	 * @see #getParameters()
	 * @see #getParameter(String, Type, boolean)
	 *
	 */
	default Parameter getParameter(String name) {
		return getParameter(name, null);
	}

	/**
	 * Retrieves the first parameter with the specified {@code type}.
	 *
	 * @param type
	 *            the type of a parameter to retrieve
	 * @return the first parameter with the given {@code type}
	 *
	 * @see #getParameters()
	 * @see #getParameter(String, Type, boolean)
	 *
	 */
	default Parameter getParameter(Type type) {
		return getParameter(null, type);
	}

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Parameter} with the specified '<em><b>Name</b></em>', and '<em><b>Type</b></em>' from the '<em><b>Parameter</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Parameter} to retrieve, or <code>null</code>.
	 * @param type
	 *            The '<em><b>Type</b></em>' of the {@link org.eclipse.uml2.uml.Parameter} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.uml2.uml.Parameter} with the specified '<em><b>Name</b></em>', and '<em><b>Type</b></em>', or <code>null</code>.
	 * @see #getParameters()
	 * @generated
	 */
	Parameter getParameter(String name, Type type);

	/**
	 * Retrieves the first {@link org.eclipse.uml2.uml.Parameter} with the specified '<em><b>Name</b></em>', and '<em><b>Type</b></em>' from the '<em><b>Parameter</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.uml.Parameter} to retrieve, or <code>null</code>.
	 * @param type
	 *            The '<em><b>Type</b></em>' of the {@link org.eclipse.uml2.uml.Parameter} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.uml2.uml.Parameter} with the specified '<em><b>Name</b></em>', and '<em><b>Type</b></em>', or <code>null</code>.
	 * @see #getParameters()
	 * @generated
	 */
	Parameter getParameter(String name, Type type, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Is Conjugate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the protocol message is a conjugate view of the base
	 * protocol's corresponding message. All of the protocol messages
	 * in the conjugate view of a protocol are the conjugate views of
	 * its messages.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Is Conjugate</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocolMessage_IsConjugate()
	 * @generated
	 */
	boolean isConjugate();

	/**
	 * Returns the value of the '<em><b>Conjugate</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conjugate</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Conjugate</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocolMessage_Conjugate()
	 * @generated
	 */
	UMLRTProtocolMessage getConjugate();

	/**
	 * Returns the value of the '<em><b>Protocol</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getMessages <em>Message</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The protocol that defines, inherits, redefines, or excludes this message.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Protocol</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocolMessage_Protocol()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getMessages
	 * @generated
	 */
	UMLRTProtocol getProtocol();

	/**
	 * <p>
	 * This operation redefines the following operations:
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#toUML() <em>To UML</em>}'</li>
	 * </ul>
	 * </p>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the underlying UML representation of this protocol message.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	Operation toUML();

	/**
	 * <p>
	 * This operation redefines the following operations:
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#toUML() <em>To UML</em>}'</li>
	 * </ul>
	 * </p>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the UML event signalling receipt of this message,
	 * useful for specification of transition triggers in state machines.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	CallEvent toReceiveEvent();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new parameter in the protocol message.
	 *
	 * @param name
	 *            The parameter name
	 * @param type
	 *            The parameter type, as a UML type
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	Parameter createParameter(String name, Type type);

	@Override
	Stream<? extends UMLRTProtocolMessage> allRedefinitions();

} // UMLRTProtocolMessage
