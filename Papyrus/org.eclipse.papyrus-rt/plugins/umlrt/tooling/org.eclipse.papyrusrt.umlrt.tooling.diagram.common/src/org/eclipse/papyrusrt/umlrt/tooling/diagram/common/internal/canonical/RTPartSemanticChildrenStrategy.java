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
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.composite.custom.canonical.PropertyPartCompartmentSemanticChildrenStrategy;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Semantic children strategy for parts, defining specific restrictions on
 * the inclusion of ports on parts.
 */
public class RTPartSemanticChildrenStrategy extends PropertyPartCompartmentSemanticChildrenStrategy {

	public RTPartSemanticChildrenStrategy() {
		super();
	}

	/**
	 * Excludes non-service ports from the canonical children. Note that
	 * non-service ports are nonetheless monitored as dependencies because their
	 * disposition can be changed at any time.
	 */
	@Override
	public List<? extends EObject> getCanonicalSemanticChildren(EObject semanticFromEditPart, View viewFromEditPart) {
		List<Element> result = null;

		UMLRTCapsulePart capsulePart = UMLRTCapsulePart.getInstance((Property) semanticFromEditPart);
		if ((capsulePart != null) && (capsulePart.getType() != null)) {
			Stream<Port> ports = capsulePart.getType().getPorts().stream()
					.peek(p -> watch(p, viewFromEditPart))
					.filter(p -> p.isService())
					.map(UMLRTPort::toUML);

			result = ports.map(UMLRTExtensionUtil::getRootDefinition)
					.collect(Collectors.toList());
		}

		return result;
	}

	protected void watch(UMLRTPort port, View capsulePartView) {
		Port uml = port.toUML();
		DiagramEventBroker.getInstance(TransactionUtil.getEditingDomain(capsulePartView))
				.addNotificationListener(uml, UMLPackage.Literals.PORT__IS_SERVICE,
						new CanonicalRefreshListener<>(capsulePartView, uml));
	}

}
