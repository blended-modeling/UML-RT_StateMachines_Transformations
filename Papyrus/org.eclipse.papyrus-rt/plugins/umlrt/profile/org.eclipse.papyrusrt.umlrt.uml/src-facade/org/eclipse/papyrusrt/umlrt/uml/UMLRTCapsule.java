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

import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl;
import org.eclipse.uml2.uml.Class;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Capsule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Representation of the UML-RT concept of a <em>Capsule</em>.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getSuperclass <em>Superclass</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getSubclasses <em>Subclass</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getPorts <em>Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getCapsuleParts <em>Capsule Part</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getConnectors <em>Connector</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getHierarchy <em>Hierarchy</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getStateMachine <em>State Machine</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getPackage <em>Package</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsule()
 * @generated
 */
public interface UMLRTCapsule extends UMLRTClassifier {
	/**
	 * Obtains the canonical instance of the capsule façade for a class.
	 *
	 * @param class_
	 *            a class in the UML model
	 *
	 * @return its capsule façade, or {@code null} if the class is not a valid capsule
	 */
	public static UMLRTCapsule getInstance(Class class_) {
		return UMLRTCapsuleImpl.getInstance(class_);
	}

	/**
	 * Returns the value of the '<em><b>Superclass</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getSubclasses <em>Subclass</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getGeneral() <em>General</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The superclass of this capsule.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Superclass</em>' reference.
	 * @see #setSuperclass(UMLRTCapsule)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsule_Superclass()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getSubclasses
	 * @generated
	 */
	UMLRTCapsule getSuperclass();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getSuperclass <em>Superclass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Superclass</em>' reference.
	 * @see #getSuperclass()
	 * @generated
	 */
	void setSuperclass(UMLRTCapsule value);

	/**
	 * Returns the value of the '<em><b>Subclass</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getSuperclass <em>Superclass</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getSpecifics() <em>Specific</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The known direct subclasses of this capsule. Note that this
	 * may be incomplete if some subclasses are not known because
	 * they are not currently loaded in the resource set.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Subclass</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsule_Subclass()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getSuperclass
	 * @generated
	 */
	List<UMLRTCapsule> getSubclasses();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule} with the specified '<em><b>Name</b></em>' from the '<em><b>Subclass</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getSubclasses()
	 * @generated
	 */
	UMLRTCapsule getSubclass(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule} with the specified '<em><b>Name</b></em>' from the '<em><b>Subclass</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getSubclasses()
	 * @generated
	 */
	UMLRTCapsule getSubclass(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Port</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getCapsule <em>Capsule</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The ports of this capsule, including any that are inherited
	 * or redefined, but not those that are redefined as excluded.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Port</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsule_Port()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort#getCapsule
	 * @generated
	 */
	List<UMLRTPort> getPorts();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort} with the specified '<em><b>Name</b></em>' from the '<em><b>Port</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getPorts()
	 * @generated
	 */
	UMLRTPort getPort(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort} with the specified '<em><b>Name</b></em>' from the '<em><b>Port</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getPorts()
	 * @generated
	 */
	UMLRTPort getPort(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Capsule Part</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getCapsule <em>Capsule</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The capsule-parts of this capsule, including any that are
	 * inherited or redefined, but not those that are redefined as excluded.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Capsule Part</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsule_CapsulePart()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart#getCapsule
	 * @generated
	 */
	List<UMLRTCapsulePart> getCapsuleParts();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart} with the specified '<em><b>Name</b></em>' from the '<em><b>Capsule Part</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getCapsuleParts()
	 * @generated
	 */
	UMLRTCapsulePart getCapsulePart(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart} with the specified '<em><b>Name</b></em>' from the '<em><b>Capsule Part</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getCapsuleParts()
	 * @generated
	 */
	UMLRTCapsulePart getCapsulePart(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Connector</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getCapsule <em>Capsule</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The connectors of this capsule, including any that are
	 * inherited or redefined but not those that are redefined
	 * as excluded.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Connector</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsule_Connector()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getCapsule
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
	 * Returns the value of the '<em><b>Hierarchy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A stream over the class hierarchy root at and including
	 * this capsule, in breadth-first order.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Hierarchy</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsule_Hierarchy()
	 * @generated
	 */
	@Override
	Stream<UMLRTCapsule> getHierarchy();

	/**
	 * Returns the value of the '<em><b>State Machine</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getCapsule <em>Capsule</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The state machine that defines the capsule's behavior.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>State Machine</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsule_StateMachine()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getCapsule
	 * @generated
	 */
	UMLRTStateMachine getStateMachine();

	/**
	 * Returns the value of the '<em><b>Package</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getCapsules <em>Capsule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The package containing this capsule.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Package</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getCapsule_Package()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getCapsules
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
	 * Obtains the class that represents this capsule in the
	 * underlying UML model.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	org.eclipse.uml2.uml.Class toUML();

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
	 * Obtains the superclass chain of this capsule, from itself
	 * to the root type.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	List<UMLRTCapsule> getAncestry();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains all of the ports of the capsule, optionally including
	 * also those that are redefined as excluded.
	 *
	 * @param withExclusions
	 *            Whether to retrieve also excluded ports
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	List<UMLRTPort> getPorts(boolean withExclusions);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains all of the capsule-parts of the capsule, optionally including
	 * also those that are redefined as excluded.
	 *
	 * @param withExclusions
	 *            Whether to retrieve also excluded capsule-parts
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	List<UMLRTCapsulePart> getCapsuleParts(boolean withExclusions);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains all of the connectors of the capsule, optionally also including
	 * those that are redefined as excluded.
	 *
	 * @param withExclusions
	 *            Whether to retrieve also excluded connectors
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	List<UMLRTConnector> getConnectors(boolean withExclusions);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new external behavior port of the given protocol type.
	 *
	 * @param type
	 *            The protocol type of the port. If this protocol is the conjugate
	 *            view, then the port will be created as conjugated.
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTPort createPort(UMLRTProtocol type);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new port of the given protocol type and port-kind.
	 *
	 * @param type
	 *            The protocol type of the port. If this protocol is the conjugate
	 *            view, then the port will be created as conjugated.
	 * @param kind
	 *            The kind of port to create
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTPort createPort(UMLRTProtocol type, UMLRTPortKind kind);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new part of the given capsule type.
	 *
	 * @param type
	 *            The capsule type of the part
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTCapsulePart createCapsulePart(UMLRTCapsule type);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new connector in this capsule.
	 *
	 * @param name
	 *            An optional name for the connector.
	 * @param source
	 *            The source port of the connector, which for delegation connectors
	 *            is conventionally the service port.
	 * @param sourcePartWithPort
	 *            If the source port is a port on a capsule-part, the source
	 *            capsule-part of the connector
	 * @param target
	 *            The target port of the connector, which for delegation connectors
	 *            is conventionally an internal behavior port of the capsule or a
	 *            service port of some capsule-part.
	 * @param targetPartWithPort
	 *            If the target port is a port on a capsule-part, the target
	 *            capsule-part of the connector
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTConnector createConnector(String name, UMLRTPort source, UMLRTCapsulePart sourcePartWithPort, UMLRTPort target, UMLRTCapsulePart targetPartWithPort);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new state machine for the capsule if it does not already have one.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	UMLRTStateMachine createStateMachine();

} // UMLRTCapsule
