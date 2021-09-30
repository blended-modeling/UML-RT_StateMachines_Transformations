/*****************************************************************************
 * Copyright (c) 2017 EclipseSource Services GmbH
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Philip Langer (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram;

import static org.eclipse.emf.compare.utils.MatchUtil.getValue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.EObjectValueStyle;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Abstract super class for capsule diagram change handlers.
 * 
 * @author Philip Langer <planger@eclipsesource.com>
 */
public abstract class AbstractCapsuleDiagramChangeHandler implements DiagramChangesHandler {

	/** Papyrus CSS force value. */
	public static final String PAPYRUS_CSS_FORCE_VALUE_KEY = "PapyrusCSSForceValue"; //$NON-NLS-1$

	/** Stereotype comment type name. */
	public static final String STEREOTYPE_COMMENT = "StereotypeComment"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * <p>
	 * Adds the PapyrsuCSSForceValue annotation, as well as the stereotype comment and
	 * edges to the stereotype comment.
	 * </p>
	 */
	@Override
	public Set<EObject> getAutomaticallyCreatedShapes(Diff diff) {
		final Set<EObject> shapes = new LinkedHashSet<>();
		final Object changedObject = getValue(diff);
		if (changedObject instanceof Shape) {
			final Shape changedShape = (Shape) changedObject;
			final EObject containerShape = changedShape.eContainer();
			if (containerShape instanceof View) {
				// get Papyrus CSS Force Value annotation
				final View containerView = (View) containerShape;
				final EAnnotation annotation = containerView.getEAnnotation(PAPYRUS_CSS_FORCE_VALUE_KEY);
				if (annotation != null) {
					shapes.add(annotation);
				}

				// get Stereotype comment shapes
				final Diagram diagram = changedShape.getDiagram();
				final EObject elementContainer = changedShape.getElement().eContainer();
				for (Object child : diagram.getChildren()) {
					if (child instanceof EObject) {
						EObject eObject = (EObject) child;
						if (isShapeWithElement(eObject, elementContainer)
								&& STEREOTYPE_COMMENT.equals(((Shape) eObject).getType())) {
							shapes.add(eObject);
						}
					}
				}

				// get Stereotype comment edges
				for (Object edge : changedShape.getDiagram().getEdges()) {
					if (edge instanceof EObject && isStereotypeCommentEdge(edge, elementContainer)) {
						shapes.add((EObject) edge);
					}
				}
			}
		}
		return shapes;
	}

	/**
	 * Specifies whether <code>edgeObject</code> is an edge from the given <code>element</code>
	 * to a stereotype comment.
	 * 
	 * @param edgeObject
	 *            The edge object.
	 * @param element
	 *            The element.
	 * @return <code>true</code> if <code>edgeObject</code> is an edge from <code>element</code>
	 *         to a stereotype comment, <code>false</code> otherwise.
	 */
	protected boolean isStereotypeCommentEdge(Object edgeObject, EObject element) {
		if (!(edgeObject instanceof Edge)) {
			return false;
		}

		final Edge edge = (Edge) edgeObject;
		final View target = edge.getTarget();
		final View source = edge.getSource();

		return source != null && element.equals(source.getElement())
				&& target != null && STEREOTYPE_COMMENT.contentEquals(target.getType());
	}

	/**
	 * Specifies whether <code>shapeObject</code> is a shape that represents the given <code>element</code>.
	 * <p>
	 * This method also returns <code>true</code>, if has an {@link EObjectValueStyle} as its
	 * {@link Shape#getStyle(org.eclipse.emf.ecore.EClass) style} that corresponds to <code>element</code>.
	 * 
	 * @param shapeObject
	 *            The shape object.
	 * @param element
	 *            The semantic model element.
	 * @return <code>true</code> if <code>shapeObject</code> is a shape representing <code>element</code>,
	 *         <code>false</code> otherwise.
	 */
	protected boolean isShapeWithElement(EObject shapeObject, EObject element) {
		if (!(shapeObject instanceof Shape)) {
			return false;
		}

		final Shape shape = (Shape) shapeObject;
		if (shape.getElement() != null) {
			return shape.getElement().equals(element);
		}

		final EObjectValueStyle valueStyle = (EObjectValueStyle) shape.getStyle(
				NotationPackage.eINSTANCE.getEObjectValueStyle());
		return valueStyle != null && element.equals(valueStyle.getEObjectValue());
	}

}