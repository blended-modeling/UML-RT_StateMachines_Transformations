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

import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation of the UML-RT concept of <em>Transition</em> in a state machine.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getStateMachine <em>State Machine</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getSource <em>Source</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getTarget <em>Target</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getTriggers <em>Trigger</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getGuard <em>Guard</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getRedefinedTransition <em>Redefined Transition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getKind <em>Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#isInternal <em>Internal</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getEffect <em>Effect</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getState <em>State</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTransition()
 * @generated
 */
public interface UMLRTTransition extends UMLRTNamedElement {
	/**
	 * Obtains the canonical instance of the façade for a transition.
	 *
	 * @param transition
	 *            a transition in the UML model
	 *
	 * @return its façade, or {@code null} if the transition is not a valid UML-RT transition
	 */
	public static UMLRTTransition getInstance(Transition transition) {
		return UMLRTTransitionImpl.getInstance(transition);
	}

	/**
	 * Returns the value of the '<em><b>State Machine</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getTransitions <em>Transition</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The state machine that directly contains a transition, if it is not in
	 * a composite state.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>State Machine</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTransition_StateMachine()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine#getTransitions
	 * @generated
	 */
	UMLRTStateMachine getStateMachine();

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The source vertex of this transition.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(UMLRTVertex)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTransition_Source()
	 * @generated
	 */
	UMLRTVertex getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(UMLRTVertex value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The target vertex of this transition.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(UMLRTVertex)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTransition_Target()
	 * @generated
	 */
	UMLRTVertex getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(UMLRTVertex value);

	/**
	 * Returns the value of the '<em><b>Trigger</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getTransition <em>Transition</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The trigger event specification of the transition.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Trigger</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTransition_Trigger()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getTransition
	 * @generated
	 */
	List<UMLRTTrigger> getTriggers();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger} with the specified '<em><b>Name</b></em>' from the '<em><b>Trigger</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getTriggers()
	 * @generated
	 */
	UMLRTTrigger getTrigger(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger} with the specified '<em><b>Name</b></em>' from the '<em><b>Trigger</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getTriggers()
	 * @generated
	 */
	UMLRTTrigger getTrigger(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Guard</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getTransition <em>Transition</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The guard condition of the transition.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Guard</em>' reference.
	 * @see #setGuard(UMLRTGuard)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTransition_Guard()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getTransition
	 * @generated
	 */
	UMLRTGuard getGuard();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getGuard <em>Guard</em>}' reference.
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
	 * Returns the value of the '<em><b>Redefined Transition</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinedElement() <em>Redefined Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The transition in the redefined state machine or composite state
	 * that this transition redefines.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefined Transition</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTransition_RedefinedTransition()
	 * @generated
	 */
	UMLRTTransition getRedefinedTransition();

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The default value is <code>"external"</code>.
	 * The literals are from the enumeration {@link org.eclipse.uml2.uml.TransitionKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The kind of transition.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.uml2.uml.TransitionKind
	 * @see #setKind(TransitionKind)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTransition_Kind()
	 * @generated
	 */
	TransitionKind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.uml2.uml.TransitionKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(TransitionKind value);

	/**
	 * Returns the value of the '<em><b>Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * As a special case of the <code>kind</code>, whether the
	 * transition is an internal transition.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Internal</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTransition_Internal()
	 * @generated
	 */
	boolean isInternal();

	/**
	 * Returns the value of the '<em><b>Effect</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getTransition <em>Transition</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Effect</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Effect</em>' reference.
	 * @see #setEffect(UMLRTOpaqueBehavior)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTransition_Effect()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getTransition
	 * @generated
	 */
	UMLRTOpaqueBehavior getEffect();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getEffect <em>Effect</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Effect</em>' reference.
	 * @see #getEffect()
	 * @generated
	 */
	void setEffect(UMLRTOpaqueBehavior value);

	/**
	 * Returns the value of the '<em><b>State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getSubtransitions <em>Subtransition</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The composite state that contains the transition, in which case
	 * its <code>stateMachine</code> is empty.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>State</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getTransition_State()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getSubtransitions
	 * @generated
	 */
	UMLRTState getState();

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
	 * Obtains the underlying UML representation of this transition.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	Transition toUML();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries the state machine that contains this transition.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	UMLRTStateMachine containingStateMachine();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new trigger for this transition.
	 *
	 * @param protocolMessage
	 *            The protocol message that triggers the transition. It does not
	 *            matter whether it is a conjugate view of the message or the
	 *            base view. If omitted, then the trigger uses the <em>AnyReceiveEvent</em>
	 *            of the protocol type of the {@code port}
	 * @param port
	 *            The port on which the triggering message is received
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTTrigger createTrigger(UMLRTProtocolMessage protocolMessage, UMLRTPort port);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new guard for this transition, if it does not already have one.
	 *
	 * @param language
	 *            An optional language for the guard condition
	 * @param body
	 *            The guard condition expression
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTGuard createGuard(String language, String body);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new effect for this transition, if it does not already have one.
	 *
	 * @param language
	 *            An optional language for the effect behavior
	 * @param body
	 *            The effect code snippet
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTOpaqueBehavior createEffect(String language, String body);

	@Override
	Stream<? extends UMLRTTransition> allRedefinitions();

} // UMLRTTransition
