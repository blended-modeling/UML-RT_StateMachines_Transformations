/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.CollaborationRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.uml2.uml.Collaboration;

/**
 * UML-RT semantics for {@link Collaboration}.
 */
public class CollaborationRTImpl extends org.eclipse.uml2.uml.internal.impl.CollaborationImpl implements InternalUMLRTCollaboration {

	protected CollaborationRTImpl() {
		super();
	}

	@Override
	public EObject create(EClass eClass) {
		EObject result;

		if (eClass.getEPackage() == eClass().getEPackage()) {
			result = UMLRTUMLFactoryImpl.eINSTANCE.create(eClass);
		} else {
			result = super.create(eClass);
		}

		return result;
	}

	@Override
	public void rtInherit(InternalUMLRTClassifier supertype) {
		if (supertype instanceof InternalUMLRTCollaboration) {
			CollaborationRTOperations.rtInherit(this, (InternalUMLRTCollaboration) supertype);
		}
	}

	@Override
	public void rtDisinherit(InternalUMLRTClassifier supertype) {
		if (supertype instanceof Collaboration) {
			CollaborationRTOperations.rtDisinherit(this, (InternalUMLRTCollaboration) supertype);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		return (T) super.eGet(featureID, true, true);
	}

	@Override
	public void rtUnsetAll() {
		// I inherit no features
	}

	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		return ElementRTOperations.toString(this, super.toString());
	}

}
