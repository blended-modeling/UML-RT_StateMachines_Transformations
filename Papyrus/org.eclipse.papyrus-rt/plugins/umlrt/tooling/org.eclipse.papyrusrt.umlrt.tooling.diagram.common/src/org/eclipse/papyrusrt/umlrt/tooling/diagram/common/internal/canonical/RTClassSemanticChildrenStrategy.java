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
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.canonical;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.composite.custom.canonical.StructuredClassifierSemanticChildrenStrategy;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Port;

/**
 * Semantic children strategy for parts in an RT composite, defining specific
 * restrictions on the inclusion of parts and accounting for capsule structure
 * inheritance.
 */
public class RTClassSemanticChildrenStrategy extends StructuredClassifierSemanticChildrenStrategy {

	public RTClassSemanticChildrenStrategy() {
		super();
	}

	/**
	 * Excludes attributes that are not {@link Port}s and are not
	 * {@link CapsulePart}s.
	 */
	@Override
	public List<? extends EObject> getCanonicalSemanticChildren(EObject semanticFromEditPart, View viewFromEditPart) {
		List<EObject> result = null;

		UMLRTCapsule capsule = UMLRTCapsule.getInstance((org.eclipse.uml2.uml.Class) semanticFromEditPart);
		if (capsule != null) {
			Stream<? extends UMLRTNamedElement> children;

			if (ClassCompositeEditPart.VISUAL_ID.equals(viewFromEditPart.getType())) {
				// Yes, the CanonicalEditPolicy expects to get capsule-parts also
				// for children of the capsule frame, even though they are created
				// eventually as children of its structure compartment
				children = Stream.concat(
						capsule.getPorts().stream(),
						capsule.getCapsuleParts().stream());
			} else if (ClassCompositeCompartmentEditPart.VISUAL_ID.equals(viewFromEditPart.getType())) {
				children = capsule.getCapsuleParts().stream();
			} else {
				children = Stream.empty();
			}

			result = children.map(UMLRTNamedElement::toUML)
					.map(UMLRTExtensionUtil::getRootDefinition)
					.collect(Collectors.toList());
		}

		return result;
	}
}
