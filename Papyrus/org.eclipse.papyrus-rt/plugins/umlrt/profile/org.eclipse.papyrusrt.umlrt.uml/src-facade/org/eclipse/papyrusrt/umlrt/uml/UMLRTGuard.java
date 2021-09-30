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

import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTGuardImpl;
import org.eclipse.uml2.uml.Constraint;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Guard</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation of the UML-RT concept of a <em>Guard</em> condition.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getBodies <em>Bodies</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getTransition <em>Transition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getRedefinedGuard <em>Redefined Guard</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getTrigger <em>Trigger</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getGuard()
 * @generated
 */
public interface UMLRTGuard extends UMLRTNamedElement {
	/**
	 * Returns the value of the '<em><b>Bodies</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bodies</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Bodies</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getGuard_Bodies()
	 * @generated
	 */
	Map<String, String> getBodies();

	/**
	 * Returns the value of the '<em><b>Transition</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getGuard <em>Guard</em>}'.
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
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getGuard_Transition()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getGuard
	 * @generated
	 */
	UMLRTTransition getTransition();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getTransition <em>Transition</em>}' reference.
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
	 * Returns the value of the '<em><b>Redefined Guard</b></em>' reference.
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
	 * @return the value of the '<em>Redefined Guard</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getGuard_RedefinedGuard()
	 * @generated
	 */
	UMLRTGuard getRedefinedGuard();

	/**
	 * Returns the value of the '<em><b>Trigger</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getGuard <em>Guard</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trigger</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Trigger</em>' reference.
	 * @see #setTrigger(UMLRTTrigger)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getGuard_Trigger()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger#getGuard
	 * @generated
	 */
	UMLRTTrigger getTrigger();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard#getTrigger <em>Trigger</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Trigger</em>' reference.
	 * @see #getTrigger()
	 * @generated
	 */
	void setTrigger(UMLRTTrigger value);

	/**
	 * Obtains the canonical instance of the façade for a transition or trigger guard condition.
	 *
	 * @param guard
	 *            a transition or trigger guard condition in the UML model
	 *
	 * @return its façade, or {@code null} if the state is not a valid UML-RT transition or trigger guard
	 */
	public static UMLRTGuard getInstance(Constraint guard) {
		return UMLRTGuardImpl.getInstance(guard);
	}

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
	 * Obtains the underlying UML representation of this guard.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	Constraint toUML();

	@Override
	Stream<? extends UMLRTGuard> allRedefinitions();

} // UMLRTGuard
