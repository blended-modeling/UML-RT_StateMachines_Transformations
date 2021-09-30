/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alexandra Buzila (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram;

import static org.eclipse.emf.compare.utils.MatchUtil.getValue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Stereotype;

/**
 * {@link DiagramChangesHandler} for diagram changes of a {@link Port} diagram shape.
 * 
 * @author Alexandra Buzila
 */
public class PortDiagramChangeHandler extends AbstractCapsuleDiagramChangeHandler {

	@Override
	public double isHandlerFor(Diff diff) {
		if (isReferenceChangeOfPortShape(diff) &&
				UMLRTCompareUtil.isViewPersistedChildrenReference(((ReferenceChange) diff).getReference())) {
			return 10.0;
		}
		return Double.NaN;
	}

	/**
	 * Checks whether the given difference represents a {@link ReferenceChange} of a {@link Port} diagram shape.
	 * 
	 * @param input
	 *            the {@link Diff} to check
	 * 
	 * @return <code>true</code> if the given difference is a reference change of a port shape
	 */
	private boolean isReferenceChangeOfPortShape(Diff input) {
		if (!(input instanceof ReferenceChange)) {
			return false;
		}

		final EObject value = ((ReferenceChange) input).getValue();
		if (!(value instanceof Shape)) {
			return false;
		}

		final EObject element = ((Shape) value).getElement();
		return element instanceof Port && RTPortUtils.isRTPort(element);
	}

	@Override
	public Set<EObject> getShapes(Diff diff) {
		final Set<EObject> shapes = new LinkedHashSet<>();
		final ReferenceChange refChange = (ReferenceChange) diff;
		final Shape portShape = (Shape) refChange.getValue();
		shapes.add(portShape);
		shapes.addAll(collectAdditionalShapesForElement(portShape));
		return shapes;
	}

	@Override
	public Set<EObject> getAutomaticallyCreatedShapes(Diff diff) {
		final Set<EObject> shapes = super.getAutomaticallyCreatedShapes(diff);
		final EObject portShape = (EObject) getValue(diff);
		final View container = (View) portShape.eContainer();

		for (Object child : container.getChildren()) {
			if (child instanceof View) {
				final View view = (View) child;
				if (isStereotypeBraceBasicCompartment(view)
						|| isStereotypeCompartmentBasicCompartment(view)
						|| isStereotypeLabelDecorationNode(view)
						|| isCompartmentShapeDisplayBasicCompartment(view)) {
					shapes.add(view);
				}
			}
		}

		return shapes;
	}

	private Collection<EObject> collectAdditionalShapesForElement(Shape portShape) {
		final Set<EObject> shapes = new LinkedHashSet<>();
		final EObject port = portShape.getElement();
		final Diagram diagram = portShape.getDiagram();
		for (Object edge : diagram.getEdges()) {
			if (isStereotypeCommentEdge(edge, port)) {
				final Edge stereotypeEdge = (Edge) edge;
				shapes.add(stereotypeEdge);
				final View stereotypeCommentShape = stereotypeEdge.getTarget();
				shapes.add(stereotypeCommentShape);
			}
		}
		return shapes;
	}

	private boolean hasUMLRealTimeStereotypeElement(View view) {
		final EObject element = view.getElement();
		return element instanceof Stereotype
				&& "UMLRealTime".equals(((Stereotype) element).getProfile().getName()); //$NON-NLS-1$
	}

	private boolean isStereotypeLabelDecorationNode(View view) {
		return hasUMLRealTimeStereotypeElement(view)
				&& view instanceof DecorationNode
				&& "StereotypeLabel".equals(view.getType()); //$NON-NLS-1$
	}

	private boolean isStereotypeBraceBasicCompartment(View view) {
		return hasUMLRealTimeStereotypeElement(view)
				&& view instanceof BasicCompartment
				&& "StereotypeBrace".equals(view.getType()); //$NON-NLS-1$
	}

	private boolean isStereotypeCompartmentBasicCompartment(View view) {
		return hasUMLRealTimeStereotypeElement(view)
				&& view instanceof BasicCompartment
				&& "StereotypeCompartment".equals(view.getType()); //$NON-NLS-1$
	}

	private boolean isCompartmentShapeDisplayBasicCompartment(View view) {
		return view instanceof BasicCompartment
				&& "compartment_shape_display".equals(view.getType()); //$NON-NLS-1$
	}
}
