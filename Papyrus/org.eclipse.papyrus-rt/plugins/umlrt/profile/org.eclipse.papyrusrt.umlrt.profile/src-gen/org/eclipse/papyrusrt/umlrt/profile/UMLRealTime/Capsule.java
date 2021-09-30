/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.umlrt.profile.UMLRealTime;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Capsule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * base_Class.isActive
 * base_Class.ownedReception->isEmpty()
 * not(base_Class.isFinalSpecialization)
 * (base_Class.generalization->size()) <= 1
 * (base_Class.generalization->notEmpty()) implies (base_Class.generalization->forAll (g | g.isSubstitutable))
 * base_Class.ownedAttribute->forAll(at | at.type->size() <= 1)
 * base_Class.ownedOperation->notEmpty() implies 
 *    (base_Class.ownedOperation->forAll(op | op.concurrency = UML::CallConcurrencyKind::guarded))
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Capsule#getBase_Class <em>Base Class</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage#getCapsule()
 * @model
 * @generated
 */
public interface Capsule extends EObject {
	/**
	 * Returns the value of the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Class</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Class</em>' reference.
	 * @see #setBase_Class(org.eclipse.uml2.uml.Class)
	 * @see org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage#getCapsule_Base_Class()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	org.eclipse.uml2.uml.Class getBase_Class();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Capsule#getBase_Class <em>Base Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Class</em>' reference.
	 * @see #getBase_Class()
	 * @generated
	 */
	void setBase_Class(org.eclipse.uml2.uml.Class value);

} // Capsule
