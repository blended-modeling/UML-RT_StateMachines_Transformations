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
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl;
import org.eclipse.uml2.uml.AnyReceiveEvent;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Type;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Protocol</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation of the UML-RT concept of <em>Protocol</em>.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getPackage <em>Package</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getSuperProtocol <em>Super Protocol</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getSubProtocols <em>Sub Protocol</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getMessages <em>Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getInMessages <em>In Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getOutMessages <em>Out Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getInOutMessages <em>In Out Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#isConjugate <em>Is Conjugate</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getConjugate <em>Conjugate</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getHierarchy <em>Hierarchy</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocol()
 * @generated
 */
public interface UMLRTProtocol extends UMLRTClassifier {

	/**
	 * Obtains the protocol façade for the base protocol type represented by the given
	 * UML collaboration.
	 *
	 * @param protocol
	 *            a UML collaboration representing a protocol
	 * @return the base UML-RT protocol façade, or {@code null} if the collaboration
	 *         is not actually a protocol
	 */
	public static UMLRTProtocol getInstance(Collaboration protocol) {
		return UMLRTProtocolImpl.getInstance(protocol);
	}

	/**
	 * Obtains the protocol façade of the given conjugation for a UML collaboration.
	 *
	 * @param protocol
	 *            a UML collaboration representing a protocol
	 * @param conjugate
	 *            {@code true} to obtain the conjugate protocol type; {@code false}
	 *            for the base protocol type
	 *
	 * @return the protocol type, or {@code null} if the UML collaboration is not a protocol
	 */
	public static UMLRTProtocol getInstance(Collaboration protocol, boolean conjugate) {
		UMLRTProtocol result = UMLRTProtocolImpl.getInstance(protocol);
		return (conjugate && (result != null)) ? result.getConjugate() : result;
	}

	/**
	 * Returns the value of the '<em><b>Super Protocol</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getSubProtocols <em>Sub Protocol</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getGeneral() <em>General</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The protocol, if any, that is the supertype of this protocol.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Super Protocol</em>' reference.
	 * @see #setSuperProtocol(UMLRTProtocol)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocol_SuperProtocol()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getSubProtocols
	 * @generated
	 */
	UMLRTProtocol getSuperProtocol();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getSuperProtocol <em>Super Protocol</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Super Protocol</em>' reference.
	 * @see #getSuperProtocol()
	 * @generated
	 */
	void setSuperProtocol(UMLRTProtocol value);

	/**
	 * Returns the value of the '<em><b>Sub Protocol</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getSuperProtocol <em>Super Protocol</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getSpecifics() <em>Specific</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The known direct subtypes of this protocol. Note that this may
	 * not be complete if some subtypes are in resource that are not
	 * currently loaded in this resource set.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Sub Protocol</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocol_SubProtocol()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getSuperProtocol
	 * @generated
	 */
	List<UMLRTProtocol> getSubProtocols();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol} with the specified '<em><b>Name</b></em>' from the '<em><b>Sub Protocol</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getSubProtocols()
	 * @generated
	 */
	UMLRTProtocol getSubProtocol(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol} with the specified '<em><b>Name</b></em>' from the '<em><b>Sub Protocol</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getSubProtocols()
	 * @generated
	 */
	UMLRTProtocol getSubProtocol(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Message</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getProtocol <em>Protocol</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * This feature is a derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The protocol messages defined, inherited, or redefined by this protocol,
	 * but not those that are redefined to exclude an inherited message.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Message</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocol_Message()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage#getProtocol
	 * @generated
	 */
	List<UMLRTProtocolMessage> getMessages();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>' from the '<em><b>Message</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getMessages()
	 * @generated
	 */
	UMLRTProtocolMessage getMessage(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>' from the '<em><b>Message</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getMessages()
	 * @generated
	 */
	UMLRTProtocolMessage getMessage(String name, boolean ignoreCase);

	/**
	 * Retrieves the first message with the specified {@code kind} and {@code name}.
	 *
	 * @param kind
	 *            the kind of message to retrieve, or {@code null} to get any kind
	 * @param name
	 *            the name of the message to retrieve, or {@code null} to get
	 *            any message of the given {@code kind}
	 *
	 * @return the first message with the matching {@code kind} and {@code name}
	 *
	 * @see #getMessages()
	 * @see #getMessage(String, RTMessageKind)
	 */
	default UMLRTProtocolMessage getMessage(RTMessageKind kind, String name) {
		switch (kind) {
		case IN:
			return getInMessage(name);
		case OUT:
			return getOutMessage(name);
		case IN_OUT:
			return getInOutMessage(name);
		default:
			throw new IllegalArgumentException(kind.getName());
		}
	}

	/**
	 * Returns the value of the '<em><b>In Message</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getMessages() <em>Message</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The subset of protocol messages are that are incoming messages.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>In Message</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocol_InMessage()
	 * @generated
	 */
	List<UMLRTProtocolMessage> getInMessages();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>' from the '<em><b>In Message</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getInMessages()
	 * @generated
	 */
	UMLRTProtocolMessage getInMessage(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>' from the '<em><b>In Message</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getInMessages()
	 * @generated
	 */
	UMLRTProtocolMessage getInMessage(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Out Message</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getMessages() <em>Message</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The subset of protocol messages are that are outgoing messages.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Out Message</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocol_OutMessage()
	 * @generated
	 */
	List<UMLRTProtocolMessage> getOutMessages();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>' from the '<em><b>Out Message</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOutMessages()
	 * @generated
	 */
	UMLRTProtocolMessage getOutMessage(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>' from the '<em><b>Out Message</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOutMessages()
	 * @generated
	 */
	UMLRTProtocolMessage getOutMessage(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>In Out Message</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getMessages() <em>Message</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The subset of protocol messages are that are symmetric messages.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>In Out Message</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocol_InOutMessage()
	 * @generated
	 */
	List<UMLRTProtocolMessage> getInOutMessages();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>' from the '<em><b>In Out Message</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getInOutMessages()
	 * @generated
	 */
	UMLRTProtocolMessage getInOutMessage(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>' from the '<em><b>In Out Message</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getInOutMessages()
	 * @generated
	 */
	UMLRTProtocolMessage getInOutMessage(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Is Conjugate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether this protocol is the conjugate view of the modelled protocol.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Is Conjugate</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocol_IsConjugate()
	 * @generated
	 */
	boolean isConjugate();

	/**
	 * Returns the value of the '<em><b>Conjugate</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The conjugate view of this protocol, in which all incoming messages
	 * are outgoing messages of the base protocol and vice versa. In the
	 * case that this protocol is a conjugate, its conjugate is the base protocol.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Conjugate</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocol_Conjugate()
	 * @generated
	 */
	UMLRTProtocol getConjugate();

	/**
	 * Returns the value of the '<em><b>Hierarchy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A stream over the type hierarchy root at this protocol, including
	 * this protocol first and then all of its specializations in breadth-first
	 * order.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Hierarchy</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocol_Hierarchy()
	 * @generated
	 */
	@Override
	Stream<UMLRTProtocol> getHierarchy();

	/**
	 * Returns the value of the '<em><b>Package</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getProtocols <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The package namespace containing this protocol.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Package</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getProtocol_Package()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getProtocols
	 * @generated
	 */
	@Override
	UMLRTPackage getPackage();

	/**
	 * <p>
	 * This operation redefines the following operations:
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#toUML() <em>To UML</em>}'</li>
	 * </ul>
	 * </p>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the underlying UML representation of this protocol.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	Collaboration toUML();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the UML event that represents the reception of any protocol message.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	AnyReceiveEvent getAnyReceiveEvent();

	/**
	 * <p>
	 * This operation redefines the following operations:
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getAncestry() <em>Get Ancestry</em>}'</li>
	 * </ul>
	 * </p>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the supertype chain of this protocol, from itself
	 * to the root type.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	List<UMLRTProtocol> getAncestry();

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
	 * Queries the subset of this protocol's messages of the given direction kind.
	 *
	 * @param kind
	 *            The protocol message direction kind
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	List<UMLRTProtocolMessage> getMessages(RTMessageKind kind);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries the subset of this protocol's messages of
	 * the given direction kind, optionally also retrieving
	 * excluded protocol messages.
	 *
	 * @param kind
	 *            The protocol message direction kind
	 * @param withExclusions
	 *            Whether to retrieve also excluded protocol messages
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	List<UMLRTProtocolMessage> getMessages(RTMessageKind kind, boolean withExclusions);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries the this protocol's messages, optionally also retrieving
	 * excluded protocol messages.
	 *
	 * @param withExclusions
	 *            Whether to retrieve also excluded protocol messages
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	List<UMLRTProtocolMessage> getMessages(boolean withExclusions);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new protocol message.
	 *
	 * @param kind
	 *            The direction kind for the message
	 * @param name
	 *            The name of the message
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTProtocolMessage createMessage(RTMessageKind kind, String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new protocol message with a single parameter
	 * named "data".
	 *
	 * @param kind
	 *            The direction kind for the message
	 * @param name
	 *            The name of the message
	 * @param dataType
	 *            The type of the data parameter, as a UML type.
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTProtocolMessage createMessage(RTMessageKind kind, String name, Type dataType);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new protocol message with any number of parameters.
	 *
	 * @param kind
	 *            The direction kind for the message
	 * @param name
	 *            The name of the message
	 * @param parameterName
	 *            The names of the protocol message parameters
	 * @param parameterType
	 *            The parameter types, as UML types, which must correspond one-for-one
	 *            to the parameter names
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTProtocolMessage createMessage(RTMessageKind kind, String name, List<String> parameterName, List<Type> parameterType);

} // UMLRTProtocol
