/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.uml2.uml.Pseudostate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RT Pseudostate</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * let knd = base_Pseudostate.kind in 
 *    (knd <> UML::PseudostateKind::shallowHistory) and
 *    (knd <> UML::PseudostateKind::fork) and
 *    (knd <> UML::PseudostateKind::join) and
 *    (knd <> UML::PseudostateKind::terminate)
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate#getBase_Pseudostate <em>Base Pseudostate</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage#getRTPseudostate()
 * @model
 * @generated
 */
public interface RTPseudostate extends EObject {
	/**
	 * Returns the value of the '<em><b>Base Pseudostate</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Pseudostate</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Pseudostate</em>' reference.
	 * @see #setBase_Pseudostate(Pseudostate)
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage#getRTPseudostate_Base_Pseudostate()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Pseudostate getBase_Pseudostate();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate#getBase_Pseudostate <em>Base Pseudostate</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Pseudostate</em>' reference.
	 * @see #getBase_Pseudostate()
	 * @generated
	 */
	void setBase_Pseudostate(Pseudostate value);

} // RTPseudostate
