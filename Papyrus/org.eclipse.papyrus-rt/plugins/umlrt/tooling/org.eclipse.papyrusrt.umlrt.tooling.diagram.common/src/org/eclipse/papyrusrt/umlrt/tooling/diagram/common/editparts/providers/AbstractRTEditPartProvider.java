/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *  
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.providers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.CreateGraphicEditPartOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.IEditPartOperation;
import org.eclipse.gmf.runtime.notation.View;


/**
 * Abstract edit-part provider class for UML-RT diagrams.
 */
public abstract class AbstractRTEditPartProvider extends AbstractEditPartProvider {

	/** The provider only provides for this diagram type. */
	protected final String diagramType;

	/** Map containing node view types supported by this provider. */
	protected final Map<String, Function<? super View, Class<?>>> nodeMap = new HashMap<>();

	/** Map containing edge view types supported by this provider. */
	protected final Map<String, Function<? super View, Class<?>>> edgeMap = new HashMap<>();

	/**
	 * Initializes me with my diagram type.
	 * 
	 * @param diagramType
	 *            the type of diagram for which I provide edit-parts
	 */
	protected AbstractRTEditPartProvider(String diagramType) {
		super();

		this.diagramType = diagramType;
	}

	/**
	 * A mapping that always provides the given {@code value}.
	 * 
	 * @param value
	 *            the value to provide
	 * 
	 * @return the mapping function
	 */
	protected static <F, T> Function<F, T> always(T value) {
		return __ -> value;
	}

	/**
	 * A mapping that provides the given {@code value} for container views of
	 * a specified {@code type}.
	 * 
	 * @param type
	 *            the container view type for which to provide the {@code value}
	 * @param value
	 *            the value to provide
	 * 
	 * @return the mapping function
	 */
	protected static <T> Function<View, T> ifContainerView(String type, T value) {
		return v -> ((v.eContainer() instanceof View) && type.equals(((View) v.eContainer()).getType()))
				? value
				: null;
	}

	/**
	 * A disjunction of multiple {@code mappings}.
	 * 
	 * @param mappings
	 *            the mappings to combine
	 * 
	 * @return a function that returns the non-{@code null} result of any of the {@code mappings}
	 *         that has a non-{@code null} value
	 */
	@SafeVarargs
	protected static <T> Function<View, T> anyOf(Function<View, T>... mappings) {
		return v -> Stream.of(mappings)
				.map(m -> m.apply(v))
				.filter(Objects::nonNull)
				.findAny().orElse(null);
	}

	/**
	 * Provides.
	 *
	 * @param operation
	 *            the operation
	 * @return true, if successful
	 * @see org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider#provides(org.eclipse.gmf.runtime.common.core.service.IOperation)
	 */
	@Override
	public boolean provides(IOperation operation) {
		boolean result = false;

		EObject referenceElement = ((IEditPartOperation) operation).getView().getElement();

		if ((operation instanceof CreateGraphicEditPartOperation) && accept(referenceElement)) {
			result = provides((IEditPartOperation) operation, referenceElement);
		}

		return result;
	}

	protected boolean provides(IEditPartOperation createOperation, EObject element) {
		boolean result = false;

		String currentDiagramType = createOperation.getView().getDiagram().getType();

		if ((diagramType != null) && diagramType.equals(currentDiagramType)) {
			result = super.provides(createOperation);
		}

		return result;
	}

	/**
	 * Overridden by subclasses to exclude semantic elements for which edit-parts are not
	 * provided, irrespective of the notation view for which the edit-part is needed.
	 * This default implementation just returns {@code true}.
	 * 
	 * @param element
	 *            the semantic element to be visualized
	 * 
	 * @return whether the {@code element} can be visualized by an edit-part that I provide
	 */
	protected boolean accept(EObject element) {
		return true;
	}

	/**
	 * Gets the node edit part class.
	 *
	 * @param view
	 *            the view
	 * @return the node edit part class
	 */
	@Override
	@SuppressWarnings("rawtypes")
	protected Class getNodeEditPartClass(View view) {
		return map(nodeMap, view);
	}

	@SuppressWarnings("rawtypes")
	private Class map(Map<String, Function<? super View, Class<?>>> table, View view) {
		Function<? super View, Class<?>> mapping = table.get(view.getType());
		return (mapping == null) ? null : mapping.apply(view);
	}

	/**
	 * Gets the edge edit part class.
	 *
	 * @param view
	 *            the view
	 * @return the edge edit part class
	 */
	@Override
	@SuppressWarnings("rawtypes")
	protected Class getEdgeEditPartClass(View view) {
		return map(edgeMap, view);
	}
}
