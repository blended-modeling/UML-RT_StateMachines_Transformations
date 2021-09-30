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

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils.getContextCapsule;
import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils.resolvePort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.canonical.strategy.ISemanticChildrenStrategy;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.composite.custom.canonical.StructuredClassifierSemanticChildrenStrategy;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;

/**
 * Semantic children strategy for parts in an RT composite, defining specific
 * restrictions on the inclusion of parts and accounting for capsule structure
 * inheritance.
 */
public class RTPortSemanticChildrenStrategy extends StructuredClassifierSemanticChildrenStrategy {

	public RTPortSemanticChildrenStrategy() {
		super();
	}

	@Override
	public Collection<? extends EObject> getCanonicalDependents(EObject semanticFromEditPart, View viewFromEditPart) {
		Collection<? extends EObject> result = super.getCanonicalDependents(semanticFromEditPart, viewFromEditPart);

		UMLRTCapsule capsule = getContextCapsule(viewFromEditPart);
		if (capsule != null) {
			// Any change in the capsule could add/remove connectors, so we need to be
			// prepared to refresh the port
			if (result == null) {
				result = Collections.singletonList(capsule.toUML());
			} else {
				List<EObject> newResult = new ArrayList<>(result.size() + 1);
				newResult.add(capsule.toUML());
				newResult.addAll(result);
				result = newResult;
			}
		}

		return result;
	}

	@Override
	public List<? extends EObject> getCanonicalSemanticConnections(EObject semanticFromEditPart, View viewFromEditPart) {
		List<Element> result = null;

		UMLRTCapsule capsule = getContextCapsule(viewFromEditPart);
		if (capsule != null) {
			UMLRTPort port = resolvePort((Port) semanticFromEditPart, capsule, viewFromEditPart);
			if (port != null) {
				// Look for connectors that are not excluded, then
				// get the root definitions back because those are what the
				// inherited views actually reference

				List<UMLRTConnector> connectors;
				Predicate<UMLRTConnector> partWithPortFilter = __ -> true;
				Property partWithPort = TypeUtils.as(ViewUtil.getViewContainer(viewFromEditPart).getElement(), Property.class);
				if (partWithPort != null) {
					// Port on part
					connectors = port.getOutsideConnectors();
					// But only on this part
					UMLRTCapsulePart part = UMLRTCapsulePart.getInstance(partWithPort);
					if (part != null) {
						// The local definition, of course
						UMLRTCapsulePart localPart = capsule.getRedefinitionOf(part);
						partWithPortFilter = connector -> (connector.getSourcePartWithPort() == localPart)
								|| (connector.getTargetPartWithPort() == localPart);
					}
				} else {
					// Port on capsule
					connectors = port.getInsideConnectors();
				}

				result = connectors.stream()
						.filter(partWithPortFilter)
						.map(conn -> resolveConnector(conn, capsule))
						.filter(Objects::nonNull)
						.map(UMLRTExtensionUtil::getRootDefinition)
						.distinct()
						.collect(Collectors.toList());
			}
		}

		return result;
	}

	Connector resolveConnector(UMLRTConnector connector, UMLRTCapsule capsule) {
		Connector result = connector.toUML();

		if (connector.getCapsule() != capsule) {
			// It's inherited? If it's excluded, then we want a null result
			result = Optional.ofNullable(capsule.getRedefinitionOf(connector))
					.map(UMLRTConnector::toUML)
					.orElse(null);
		} else if (connector.isExcluded()) {
			// If it is locally redefined but that is an exclusion, then
			// we don't want it
			result = null;
		}

		return result;
	}

	@Override
	public IGraphicalEditPart resolveSourceEditPart(EObject connectionElement, IGraphicalEditPart context) {
		if (connectionElement instanceof Connector) {
			// Resolve it in our inheriting Capsule context
			connectionElement = resolveConnector((Connector) connectionElement, context);
		}

		return super.resolveSourceEditPart(connectionElement, context);
	}

	@Override
	public IGraphicalEditPart resolveTargetEditPart(EObject connectionElement, IGraphicalEditPart context) {
		if (connectionElement instanceof Connector) {
			// Resolve it in our inheriting Capsule context
			connectionElement = resolveConnector((Connector) connectionElement, context);
		}

		return super.resolveTargetEditPart(connectionElement, context);
	}

	private Connector resolveConnector(Connector umlConnector, IGraphicalEditPart context) {
		Connector result = umlConnector;

		UMLRTConnector connector = UMLRTConnector.getInstance(umlConnector);
		UMLRTCapsule capsule = getContextCapsule(context.getNotationView());
		if ((connector != null) && (capsule != null)) {
			result = capsule.getRedefinitionOf(connector).toUML();
		}

		return result;
	}


	@Override
	public Object getSource(EObject connectionElement) {
		Object result = super.getSource(connectionElement);

		if (connectionElement instanceof Connector) {
			Connector connector = (Connector) connectionElement;

			Object source = getPort(connector,
					UMLRTConnector::getSource, UMLRTConnector::getSourcePartWithPort);
			if (source != null) {
				result = source;
			}
		}

		return result;
	}

	private Object getPort(Connector uml, Function<UMLRTConnector, UMLRTPort> roleFunction, Function<UMLRTConnector, UMLRTCapsulePart> partWithPortFunction) {
		Object result = null;

		UMLRTConnector connector = UMLRTConnector.getInstance(uml);
		if (connector != null) {
			UMLRTCapsule capsule = connector.getCapsule();
			if (capsule != null) {
				UMLRTPort port = roleFunction.apply(connector);
				if (port != null) {
					UMLRTCapsulePart part = partWithPortFunction.apply(connector);
					if (part == null) {
						// It's just a port on the border of the capsule
						result = capsule.getRedefinitionOf(port).toUML();
					} else {
						// It's a port on a part
						part = capsule.getRedefinitionOf(part);
						port = part.getType().getRedefinitionOf(port);
						result = ISemanticChildrenStrategy.createVisualStack(part.toUML(), port.toUML());
					}
				}
			}
		}

		return result;
	}

	@Override
	public Object getTarget(EObject connectionElement) {
		Object result = super.getTarget(connectionElement);

		if (connectionElement instanceof Connector) {
			Object target = getPort((Connector) connectionElement,
					UMLRTConnector::getTarget, UMLRTConnector::getTargetPartWithPort);
			if (target != null) {
				result = target;
			}
		}

		return result;
	}

}
