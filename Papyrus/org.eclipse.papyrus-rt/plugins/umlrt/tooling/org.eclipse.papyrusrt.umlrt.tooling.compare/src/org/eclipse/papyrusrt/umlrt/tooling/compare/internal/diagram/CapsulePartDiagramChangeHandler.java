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

import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.isViewPersistedChildrenReference;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.uml2.uml.Property;

/**
 * Diagram change handler that provides the diagram shapes affected by a change of a {@link CapsulePart}.
 * 
 * @author Alexandra Buzila
 */
public class CapsulePartDiagramChangeHandler extends AbstractCapsuleDiagramChangeHandler {

	@Override
	public double isHandlerFor(Diff diff) {
		if (isReferenceChangeOfCapsulePartShape(diff) &&
				isViewPersistedChildrenReference(((ReferenceChange) diff).getReference())) {
			return 10.0;
		}
		return Double.NaN;
	}

	/**
	 * Checks whether the given difference represents a {@link ReferenceChange} of a {@link CapsulePart} diagram shape.
	 * 
	 * @param input
	 *            the {@link Diff} to check
	 * 
	 * @return <code>true</code> if the given difference is a reference change of a capsule part shape
	 */
	private boolean isReferenceChangeOfCapsulePartShape(Diff input) {
		if (!(input instanceof ReferenceChange)) {
			return false;
		}

		final EObject value = ((ReferenceChange) input).getValue();
		if (!(value instanceof Shape)) {
			return false;
		}

		final EObject element = ((Shape) value).getElement();
		return element instanceof Property && CapsulePartUtils.isCapsulePart((Property) element);
	}

	@Override
	public Set<EObject> getShapes(Diff diff) {
		final Set<EObject> shapes = new LinkedHashSet<>();
		final ReferenceChange refChange = (ReferenceChange) diff;
		final Shape capsulePartShape = (Shape) refChange.getValue();
		shapes.add(capsulePartShape);
		shapes.addAll(collectAdditionalShapesForElement(capsulePartShape));
		return shapes;
	}

	private Collection<EObject> collectAdditionalShapesForElement(Shape capsulePartShape) {
		final Set<EObject> shapes = new LinkedHashSet<>();
		final EObject capsulePart = capsulePartShape.getElement();
		final EObject container = capsulePartShape.eContainer();
		for (Object child : container.eContents()) {
			if (child instanceof EObject) {
				final EObject eObject = (EObject) child;
				if (isShapeWithElement(eObject, capsulePartShape.getElement())) {
					shapes.add(eObject);
				}
			}
		}

		final Diagram diagram = capsulePartShape.getDiagram();
		for (Object edge : diagram.getEdges()) {
			if (edge instanceof Edge && isStereotypeCommentEdge(edge, capsulePart)) {
				shapes.add((EObject) edge);
			}
		}
		return shapes;
	}

}
