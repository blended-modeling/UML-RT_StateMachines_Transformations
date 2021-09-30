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

import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTriggerImpl;
import org.eclipse.uml2.uml.Trigger;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trigger</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation of the UML-RT concept of transition <em>Trigger</em>.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getProtocolMessage <em>Protocol Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getPorts <em>Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getGuard <em>Guard</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getRedefinedTrigger <em>Redefined Trigger</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#isReceiveAnyMessage <em>Receive Any Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getTransition <em>Transition</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTrigger()
 * @generated
 */
public interface UMLRTTrigger extends UMLRTNamedElement {
	/**
	 * Obtains the canonical instance of the façade for a transition trigger.
	 *
	 * @param trigger
	 *            a transition trigger in the UML model
	 *
	 * @return its façade, or {@code null} if the trigger is not a valid UML-RT trigger
	 */
	public static UMLRTTrigger getInstance(Trigger trigger) {
		return UMLRTTriggerImpl.getInstance(trigger);
	}

	/**
	 * Returns the value of the '<em><b>Protocol Message</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The protocol message that triggers the transition. This
	 * is always represented as the base view of the message
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Protocol Message</em>' reference.
	 * @see #setProtocolMessage(UMLRTProtocolMessage)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTrigger_ProtocolMessage()
	 * @generated
	 */
	UMLRTProtocolMessage getProtocolMessage();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getProtocolMessage <em>Protocol Message</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Protocol Message</em>' reference.
	 * @see #getProtocolMessage()
	 * @generated
	 */
	void setProtocolMessage(UMLRTProtocolMessage value);

	/**
	 * Returns the value of the '<em><b>Port</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The ports on which the triggering protocol message is received.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Port</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTrigger_Port()
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A convenience for the common case of a trigger that is associated
	 * with only one port, to retrieve that port. Fails if the trigger has
	 * more than one port.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	UMLRTPort getPort() throws IllegalStateException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A convenience for the common case of triggering on receipt
	 * of a protocol message from a single port, sets the collection
	 * of ports to contain this <code>port</code> only.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	void setPort(UMLRTPort port);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries whether the trigger has multiple ports, in which case
	 * the <code>getPort()</code> operation is not supported.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	boolean hasMultiplePorts();

	/**
	 * Returns the value of the '<em><b>Guard</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getTrigger <em>Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The guard condition of the trigger.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Guard</em>' reference.
	 * @see #setGuard(UMLRTGuard)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTrigger_Guard()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getTrigger
	 * @generated
	 */
	UMLRTGuard getGuard();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getGuard <em>Guard</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Guard</em>' reference.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(UMLRTGuard value);

	/**
	 * Returns the value of the '<em><b>Redefined Trigger</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinedElement() <em>Redefined Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The trigger in the redefined transition that this trigger redefines.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefined Trigger</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTrigger_RedefinedTrigger()
	 * @generated
	 */
	UMLRTTrigger getRedefinedTrigger();

	/**
	 * Returns the value of the '<em><b>Receive Any Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the transition triggers on receipt of any message
	 * on its <code>port</code>. In such case, the
	 * <code>protocolMessage</code> association is empty.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Receive Any Message</em>' attribute.
	 * @see #setReceiveAnyMessage(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTrigger_ReceiveAnyMessage()
	 * @generated
	 */
	boolean isReceiveAnyMessage();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#isReceiveAnyMessage <em>Receive Any Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Receive Any Message</em>' attribute.
	 * @see #isReceiveAnyMessage()
	 * @generated
	 */
	void setReceiveAnyMessage(boolean value);

	/**
	 * Returns the value of the '<em><b>Transition</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getTriggers <em>Trigger</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The triggered transition.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Transition</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTrigger_Transition()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getTriggers
	 * @generated
	 */
	UMLRTTransition getTransition();

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
	 * Obtains the underlying UML representation of this trigger.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	Trigger toUML();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new guard for this trigger, if it does not already have one.
	 *
	 * @param language
	 *            An optional language for the guard condition
	 * @param body
	 *            The guard condition expression
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTGuard createGuard(String language, String body);

	@Override
	Stream<? extends UMLRTTrigger> allRedefinitions();

} // UMLRTTrigger
