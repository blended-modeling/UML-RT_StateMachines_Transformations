/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.core.sync.capsule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.core.sync.UMLSyncRegistry;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;

/**
 * Capsule behavior inheritance synchronization registry.
 */
public class CapsuleSyncRegistry extends UMLSyncRegistry<org.eclipse.uml2.uml.Class> {

	public CapsuleSyncRegistry() {
		super();
	}

	@Override
	public org.eclipse.uml2.uml.Class getModelOf(EObject backend) {
		org.eclipse.uml2.uml.Class result = null;

		if (backend instanceof org.eclipse.uml2.uml.Class) {
			result = CapsuleUtils.getSuperCapsule((org.eclipse.uml2.uml.Class) backend);
		}

		return result;
	}
}
