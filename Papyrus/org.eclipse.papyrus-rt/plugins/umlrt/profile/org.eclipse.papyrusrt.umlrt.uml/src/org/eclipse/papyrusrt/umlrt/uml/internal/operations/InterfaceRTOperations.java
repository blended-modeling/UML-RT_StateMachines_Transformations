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

package org.eclipse.papyrusrt.umlrt.uml.internal.operations;

import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InterfaceRTImpl;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Externalized operations for {@link Interface}s
 */
public class InterfaceRTOperations extends UMLUtil {

	// Not meant to be instantiable by clients
	protected InterfaceRTOperations() {
		super();
	}

	public static <T extends InternalUMLRTClassifier & Interface> void rtInherit(InterfaceRTImpl interface_, T superinterface) {
		interface_.rtInherit(superinterface, UMLPackage.Literals.INTERFACE__OWNED_OPERATION,
				ExtUMLExtPackage.Literals.INTERFACE__IMPLICIT_OPERATION,
				Operation.class, null);

		// TODO: anything else to inherit?
	}

	public static <T extends InternalUMLRTClassifier & Interface> void rtDisinherit(InterfaceRTImpl interface_, T superinterface) {
		interface_.rtDisinherit(superinterface, UMLPackage.Literals.INTERFACE__OWNED_OPERATION,
				ExtUMLExtPackage.Literals.INTERFACE__IMPLICIT_OPERATION);

		// TODO: anything else to disinherit?

		// Don't need the extension for anything, now
		interface_.rtDestroyExtension();
	}


}
