/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.drop;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.DelegatingEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;

/**
 * A delegating edit-policy that filters drop requests for some criterion.
 * The default criterion is existence of a view of any dropped element
 * as a child of the host edit-part.
 */
public class FilteredDropEditPolicy extends DelegatingEditPolicy {

	private final Predicate<? super EObject> dropFilter;

	/**
	 * Initializes me with my {@code delegate} that provides the default drop
	 * behaviour and a filter matching model elements that must not be dropped.
	 *
	 * @param delegate
	 *            my delegate
	 * @param dropFilter
	 *            filter for elements to drop
	 */
	public FilteredDropEditPolicy(EditPolicy delegate, Predicate<? super EObject> dropFilter) {
		super(delegate);

		this.dropFilter = dropFilter;
	}

	/**
	 * Initializes me with my {@code delegate} that provides the default drop
	 * behaviour and a filter matching model elements that must not be dropped.
	 * 
	 * @param delegate
	 *            my delegate
	 * @param dropFilter
	 *            filter for elements to drop on a given edit part (which will be my host edit part)
	 */
	public FilteredDropEditPolicy(EditPolicy delegate, BiPredicate<? super EObject, ? super EditPart> dropFilter) {
		super(delegate);

		this.dropFilter = o -> dropFilter.test(o, getHost());
	}

	/**
	 * Initializes me with my {@code delegate} that provides the default drop
	 * behaviour and a filter matching model elements that are already visualized
	 * as child views of my host.
	 *
	 * @param delegate
	 *            my delegate
	 * 
	 * @see #hasView(EObject)
	 */
	public FilteredDropEditPolicy(EditPolicy delegate) {
		super(delegate);

		this.dropFilter = this::hasView;
	}

	/**
	 * Filters {@linkplain RequestConstants#REQ_DROP_OBJECTS drop requests} to
	 * exclude those that would drop an object matching my filter.
	 * 
	 * @param request
	 *            a drop request
	 * 
	 * @return the filtered drop command
	 */
	@Override
	public Command getCommand(Request request) {
		Command result;

		if (RequestConstants.REQ_DROP_OBJECTS.equals(request.getType())) {
			// This is the one we want to filter
			List<?> objectsToDrop = ((DropObjectsRequest) request).getObjects();
			result = objectsToDrop.stream()
					.filter(EObject.class::isInstance)
					.map(EObject.class::cast)
					.anyMatch(dropFilter)
							? UnexecutableCommand.INSTANCE
							: super.getCommand(request);
		} else {
			result = super.getCommand(request);
		}

		return result;
	}

	/**
	 * Queries whether an {@code element} has a view presenting it
	 * within my scope.
	 * 
	 * @param element
	 *            an element
	 * @return {@code true} if I have a view of the {@code element}; {@code false}, otherwise
	 */
	protected boolean hasView(EObject element) {
		return hasView(element, getHost());
	}

	/**
	 * Queries whether an {@code element} has a view presenting it
	 * as a child of the given edit-part. This is the default filtering
	 * behaviour installed by the
	 * {@linkplain #FilteredDropEditPolicy(EditPolicy) minimal constructor}.
	 * 
	 * @param element
	 *            an element
	 * @param editPart
	 *            an edit-part in which to look for a view of the {@code element}
	 * 
	 * @return {@code true} if the {@code editPart} has a view of the {@code element};
	 *         {@code false}, otherwise
	 */
	public static boolean hasView(EObject element, EditPart editPart) {
		// Don't just do a findEditPart() because we need to look only in the
		// direct children. Otherwise, we might accidentally find a view
		// of a port that also happens to be visualized legitimately and
		// separately on a part
		return (editPart instanceof IGraphicalEditPart)
				&& UMLRTEditPartUtils.getChild((IGraphicalEditPart) editPart, element).isPresent();
	}
}
