/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Internal protocol for namespaces.
 */
public interface InternalUMLRTNamespace extends InternalUMLRTElement, Namespace {

	default Constraint rtCreateExclusionConstraint(Element excluded) {
		Constraint result = createOwnedRule(null);
		result.createSpecification(null, null, UMLPackage.Literals.LITERAL_BOOLEAN); // false
		result.getConstrainedElements().add(excluded);

		return result;
	}
}
