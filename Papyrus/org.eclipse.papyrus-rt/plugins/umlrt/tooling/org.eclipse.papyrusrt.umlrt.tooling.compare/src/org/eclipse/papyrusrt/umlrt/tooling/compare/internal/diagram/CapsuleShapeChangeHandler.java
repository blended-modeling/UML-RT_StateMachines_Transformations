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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.utils.MatchUtil;
import org.eclipse.emf.compare.utils.ReferenceUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Capsule;
import org.eclipse.uml2.uml.Classifier;

/**
 * {@link DiagramChangesHandler} for diagram changes of a {@link Capsule} diagram shape.
 * 
 * @author Alexandra Buzila
 */
public class CapsuleShapeChangeHandler extends AbstractCapsuleDiagramChangeHandler {

	@Override
	public double isHandlerFor(Diff diff) {
		if (isReferenceChangeOfCapsuleShape(diff)) {
			return 10.0;
		}
		return Double.NaN;
	}

	/**
	 * Checks whether the given difference represents a {@link ReferenceChange} of a {@link Capsule} diagram shape.
	 * 
	 * @param input
	 *            the {@link Diff} to check
	 * 
	 * @return if the given difference is a reference change of a capsule shape
	 */
	private boolean isReferenceChangeOfCapsuleShape(Diff input) {
		if (!(input instanceof ReferenceChange) || !hasDiagramContainer(input)) {
			return false;
		}

		final EObject value = ((ReferenceChange) input).getValue();
		if (!(value instanceof Shape)) {
			return false;
		}

		final EObject element = ((Shape) value).getElement();
		return element instanceof Classifier && CapsuleUtils.isCapsule((Classifier) element);

	}

	/**
	 * Checks whether the given difference changes an object contained in a {@link Diagram}.
	 * 
	 * @param input
	 *            the {@link Diff} to check
	 * 
	 * @return <code>true</code> if the given satisfies the condition
	 */
	private boolean hasDiagramContainer(Diff input) {
		final EObject container = MatchUtil.getContainer(input.getMatch().getComparison(), input);
		return container instanceof Diagram
				&& ReferenceUtil.safeEGet(container, NotationPackage.Literals.VIEW__ELEMENT) != null;
	}

	@Override
	public Set<EObject> getShapes(Diff diff) {
		final Set<EObject> shapes = new LinkedHashSet<>();
		final ReferenceChange refChange = (ReferenceChange) diff;
		final Shape capsuleShape = (Shape) refChange.getValue();
		shapes.add(capsuleShape);
		shapes.addAll(collectAdditionalShapesForElement(capsuleShape.getDiagram(), (Classifier) capsuleShape.getElement()));
		return shapes;
	}

	@Override
	public Set<EObject> getAutomaticallyCreatedShapes(Diff diff) {
		return Collections.emptySet();
	}

	private Collection<? extends EObject> collectAdditionalShapesForElement(Diagram container, Classifier capsule) {
		final Set<EObject> shapes = new LinkedHashSet<>();
		for (Object child : container.getChildren()) {
			if (child instanceof EObject) {
				final EObject eObject = (EObject) child;
				if (isShapeWithElement(eObject, capsule)) {
					shapes.add(eObject);
				}
			}
		}
		for (Object edge : container.getEdges()) {
			if (edge instanceof Edge && isStereotypeCommentEdge((EObject) edge, capsule)) {
				shapes.add((EObject) edge);
			}
		}
		return shapes;
	}

}
