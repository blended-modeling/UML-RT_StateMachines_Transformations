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

import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTOpaqueBehaviorImpl;
import org.eclipse.uml2.uml.OpaqueBehavior;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Opaque Behavior</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation of action code snippets used in UML-RT for
 * transition effects and state entry/exit behavior.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getBodies <em>Bodies</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getEnteredState <em>Entered State</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getExitedState <em>Exited State</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getRedefinedBehavior <em>Redefined Behavior</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getTransition <em>Transition</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getOpaqueBehavior()
 * @generated
 */
public interface UMLRTOpaqueBehavior extends UMLRTNamedElement {
	/**
	 * Obtains the canonical instance of the façade for a transition effect or state entry/exit behavior.
	 *
	 * @param behavior
	 *            an opaque behavior in the UML model
	 *
	 * @return its façade, or {@code null} if the state is not a valid UML-RT transition effect or
	 *         state entry/exit behavior
	 */
	public static UMLRTOpaqueBehavior getInstance(OpaqueBehavior behavior) {
		return UMLRTOpaqueBehaviorImpl.getInstance(behavior);
	}

	/**
	 * Returns the value of the '<em><b>Bodies</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The opaque behavior code snippets, by language.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Bodies</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getOpaqueBehavior_Bodies()
	 * @generated
	 */
	Map<String, String> getBodies();

	/**
	 * Returns the value of the '<em><b>Entered State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getEntry <em>Entry</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entered State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Entered State</em>' reference.
	 * @see #setEnteredState(UMLRTState)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getOpaqueBehavior_EnteredState()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getEntry
	 * @generated
	 */
	UMLRTState getEnteredState();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getEnteredState <em>Entered State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Entered State</em>' reference.
	 * @see #getEnteredState()
	 * @generated
	 */
	void setEnteredState(UMLRTState value);

	/**
	 * Returns the value of the '<em><b>Exited State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getExit <em>Exit</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exited State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Exited State</em>' reference.
	 * @see #setExitedState(UMLRTState)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getOpaqueBehavior_ExitedState()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getExit
	 * @generated
	 */
	UMLRTState getExitedState();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getExitedState <em>Exited State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Exited State</em>' reference.
	 * @see #getExitedState()
	 * @generated
	 */
	void setExitedState(UMLRTState value);

	/**
	 * Returns the value of the '<em><b>Redefined Behavior</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinedElement() <em>Redefined Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The guard of the redefined transition or trigger that this guard redefines.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefined Behavior</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getOpaqueBehavior_RedefinedBehavior()
	 * @generated
	 */
	UMLRTOpaqueBehavior getRedefinedBehavior();

	/**
	 * Returns the value of the '<em><b>Transition</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getEffect <em>Effect</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Transition</em>' reference.
	 * @see #setTransition(UMLRTTransition)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getOpaqueBehavior_Transition()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getEffect
	 * @generated
	 */
	UMLRTTransition getTransition();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getTransition <em>Transition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Transition</em>' reference.
	 * @see #getTransition()
	 * @generated
	 */
	void setTransition(UMLRTTransition value);

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
	 * Obtains the underlying UML representation of this opaque behavior.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	OpaqueBehavior toUML();

	@Override
	Stream<? extends UMLRTOpaqueBehavior> allRedefinitions();

} // UMLRTOpaqueBehavior
