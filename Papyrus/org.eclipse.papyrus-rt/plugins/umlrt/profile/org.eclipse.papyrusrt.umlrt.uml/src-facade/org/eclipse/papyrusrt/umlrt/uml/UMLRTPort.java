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

import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl;
import org.eclipse.uml2.uml.Port;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Representation of the UML-RT concept of a <em>Port</em> of a capsule.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getKind <em>Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getRedefinedPort <em>Redefined Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getPartsWithPort <em>Parts With Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isService <em>Service</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isBehavior <em>Behavior</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isConjugated <em>Conjugated</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isWired <em>Wired</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isPublish <em>Publish</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isNotification <em>Notification</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getRegistration <em>Registration</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getRegistrationOverride <em>Registration Override</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isConnected <em>Is Connected</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isConnectedInside <em>Is Connected Inside</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isConnectedOutside <em>Is Connected Outside</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getConnectors <em>Connector</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getInsideConnectors <em>Inside Connector</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getOutsideConnectors <em>Outside Connector</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getCapsule <em>Capsule</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort()
 * @generated
 */
public interface UMLRTPort extends UMLRTReplicatedElement {

	/**
	 * Obtains the canonical instance of the port façade for a port.
	 *
	 * @param port
	 *            a port in the UML model
	 *
	 * @return its port façade, or {@code null} if the port is not a valid UML-RT port
	 */
	public static UMLRTPort getInstance(Port port) {
		return UMLRTPortImpl.getInstance(port);
	}

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The kind of port.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind
	 * @see #setKind(UMLRTPortKind)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_Kind()
	 * @generated
	 */
	UMLRTPortKind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(UMLRTPortKind value);

	/**
	 * Returns the value of the '<em><b>Redefined Port</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinedElement() <em>Redefined Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The inherited port that this port redefines, if any.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefined Port</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_RedefinedPort()
	 * @generated
	 */
	UMLRTPort getRedefinedPort();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The protocol type of this port. If this port is conjugated, then
	 * the type is the conjugate view of that protocol.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(UMLRTProtocol)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_Type()
	 * @generated
	 */
	UMLRTProtocol getType();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(UMLRTProtocol value);

	/**
	 * Returns the value of the '<em><b>Parts With Port</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * All of the known parts in other capsules that expose this port.
	 * Note that this may not be the complete set of capsule-parts
	 * if some are in resources that are not currently loaded in the
	 * resource set.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Parts With Port</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_PartsWithPort()
	 * @generated
	 */
	List<UMLRTCapsulePart> getPartsWithPort();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart} with the specified '<em><b>Name</b></em>' from the '<em><b>Parts With Port</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getPartsWithPort()
	 * @generated
	 */
	UMLRTCapsulePart getPartsWithPort(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart} with the specified '<em><b>Name</b></em>' from the '<em><b>Parts With Port</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getPartsWithPort()
	 * @generated
	 */
	UMLRTCapsulePart getPartsWithPort(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Service</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether this is a service port.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Service</em>' attribute.
	 * @see #setService(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_Service()
	 * @generated
	 */
	boolean isService();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isService <em>Service</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Service</em>' attribute.
	 * @see #isService()
	 * @generated
	 */
	void setService(boolean value);

	/**
	 * Returns the value of the '<em><b>Behavior</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether this is a behavior port.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Behavior</em>' attribute.
	 * @see #setBehavior(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_Behavior()
	 * @generated
	 */
	boolean isBehavior();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isBehavior <em>Behavior</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Behavior</em>' attribute.
	 * @see #isBehavior()
	 * @generated
	 */
	void setBehavior(boolean value);

	/**
	 * Returns the value of the '<em><b>Conjugated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether this port's protocol type is conjugated.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Conjugated</em>' attribute.
	 * @see #setConjugated(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_Conjugated()
	 * @generated
	 */
	boolean isConjugated();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isConjugated <em>Conjugated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Conjugated</em>' attribute.
	 * @see #isConjugated()
	 * @generated
	 */
	void setConjugated(boolean value);

	/**
	 * Returns the value of the '<em><b>Wired</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether this is a wired port.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Wired</em>' attribute.
	 * @see #setWired(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_Wired()
	 * @generated
	 */
	boolean isWired();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isWired <em>Wired</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Wired</em>' attribute.
	 * @see #isWired()
	 * @generated
	 */
	void setWired(boolean value);

	/**
	 * Returns the value of the '<em><b>Publish</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether this is a published (unwired) port.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Publish</em>' attribute.
	 * @see #setPublish(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_Publish()
	 * @generated
	 */
	boolean isPublish();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isPublish <em>Publish</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Publish</em>' attribute.
	 * @see #isPublish()
	 * @generated
	 */
	void setPublish(boolean value);

	/**
	 * Returns the value of the '<em><b>Notification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether this port receives notifications of connection
	 * and disconnection from the run-time.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Notification</em>' attribute.
	 * @see #setNotification(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_Notification()
	 * @generated
	 */
	boolean isNotification();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#isNotification <em>Notification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Notification</em>' attribute.
	 * @see #isNotification()
	 * @generated
	 */
	void setNotification(boolean value);

	/**
	 * Returns the value of the '<em><b>Registration</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The port registration kind.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Registration</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType
	 * @see #setRegistration(PortRegistrationType)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_Registration()
	 * @generated
	 */
	PortRegistrationType getRegistration();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getRegistration <em>Registration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Registration</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType
	 * @see #getRegistration()
	 * @generated
	 */
	void setRegistration(PortRegistrationType value);

	/**
	 * Returns the value of the '<em><b>Registration Override</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The port's registration override.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Registration Override</em>' attribute.
	 * @see #setRegistrationOverride(String)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_RegistrationOverride()
	 * @generated
	 */
	String getRegistrationOverride();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getRegistrationOverride <em>Registration Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Registration Override</em>' attribute.
	 * @see #getRegistrationOverride()
	 * @generated
	 */
	void setRegistrationOverride(String value);

	/**
	 * Returns the value of the '<em><b>Is Connected</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the port has any connectors.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Is Connected</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_IsConnected()
	 * @generated
	 */
	boolean isConnected();

	/**
	 * Returns the value of the '<em><b>Is Connected Inside</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the port has any connectors "on the inside".
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Is Connected Inside</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_IsConnectedInside()
	 * @generated
	 */
	boolean isConnectedInside();

	/**
	 * Returns the value of the '<em><b>Is Connected Outside</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the port has any connectors "on the outside".
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Is Connected Outside</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_IsConnectedOutside()
	 * @generated
	 */
	boolean isConnectedOutside();

	/**
	 * Returns the value of the '<em><b>Connector</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector}.
	 * This feature is a derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The connectors that connect this port to other ports.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Connector</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_Connector()
	 * @generated
	 */
	List<UMLRTConnector> getConnectors();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} with the specified '<em><b>Name</b></em>' from the '<em><b>Connector</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getConnectors()
	 * @generated
	 */
	UMLRTConnector getConnector(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} with the specified '<em><b>Name</b></em>' from the '<em><b>Connector</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getConnectors()
	 * @generated
	 */
	UMLRTConnector getConnector(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Inside Connector</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getConnectors() <em>Connector</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The subset of this port's connectors that are connected "inside",
	 * in the context of the port's containing capsule.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Inside Connector</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_InsideConnector()
	 * @generated
	 */
	List<UMLRTConnector> getInsideConnectors();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} with the specified '<em><b>Name</b></em>' from the '<em><b>Inside Connector</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getInsideConnectors()
	 * @generated
	 */
	UMLRTConnector getInsideConnector(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} with the specified '<em><b>Name</b></em>' from the '<em><b>Inside Connector</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getInsideConnectors()
	 * @generated
	 */
	UMLRTConnector getInsideConnector(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Outside Connector</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getConnectors() <em>Connector</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The subset of this port's connectors that are connected "outside",
	 * in the context of capsules that have parts that expose this port.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Outside Connector</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_OutsideConnector()
	 * @generated
	 */
	List<UMLRTConnector> getOutsideConnectors();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} with the specified '<em><b>Name</b></em>' from the '<em><b>Outside Connector</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOutsideConnectors()
	 * @generated
	 */
	UMLRTConnector getOutsideConnector(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} with the specified '<em><b>Name</b></em>' from the '<em><b>Outside Connector</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOutsideConnectors()
	 * @generated
	 */
	UMLRTConnector getOutsideConnector(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Capsule</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getPorts <em>Port</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The capsule containing this port. If this port is inherited, then
	 * the capsule that inherits it, not the capsule from which it is
	 * inherited.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Capsule</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPort_Capsule()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getPorts
	 * @generated
	 */
	@Override
	UMLRTCapsule getCapsule();

	/**
	 * <p>
	 * This operation redefines the following operations:
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement#toUML() <em>To UML</em>}'</li>
	 * </ul>
	 * </p>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the underlying UML representation of this port.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	Port toUML();

	@Override
	Stream<? extends UMLRTPort> allRedefinitions();

} // UMLRTPort
