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

package org.eclipse.papyrusrt.umlrt.core.internal.types.advice;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Edit-helper advice for {@link MoveRequest}s that brings moved elements'
 * applied stereotype applications along if the destination is persisted in
 * a different resource than any of the sources.
 */
public class AppliedStereotypesMoveAdvice extends AbstractEditHelperAdvice {

	/**
	 * Initializes me.
	 */
	public AppliedStereotypesMoveAdvice() {
		super();
	}

	/**
	 * Advises a move request for bringing along all stereotype applications of the
	 * element sub-trees being moved, which stereotype applications are stored in the
	 * same resources as their base elements (not externalized) and where the elements
	 * being moved and the container into which they are being moved are in UML-RT models.
	 */
	@Override
	protected ICommand getAfterMoveCommand(MoveRequest request) {
		ICommand result = null;

		EObject destination = request.getTargetContainer();
		if ((destination instanceof Element)
				&& UMLRTProfileUtils.isUMLRTProfileApplied((Element) destination)) {

			Resource destinationResource = destination.eResource();

			@SuppressWarnings("unchecked")
			Set<EObject> elementsToMove = request.getElementsToMove().keySet();
			List<Element> crossResourceMoves = elementsToMove.stream()
					// Changing the container of a resource root doesn't move it into another resource
					.filter(e -> (((InternalEObject) e).eDirectResource() == null) && (e.eResource() != destinationResource))
					.filter(Element.class::isInstance).map(Element.class::cast)
					.filter(UMLRTProfileUtils::isUMLRTProfileApplied)
					.collect(Collectors.toList());

			if (!crossResourceMoves.isEmpty()) {
				// We will have stereotype applications to move.
				Multimap<Element, EObject> stereotypeApplications = ArrayListMultimap.create();
				crossResourceMoves.forEach(moved -> collectStereotypeApplications(moved, stereotypeApplications));

				if (!stereotypeApplications.isEmpty()) {
					// Don't worry about affected resources because we won't affect any more resources
					// than are already moving elements
					result = new AbstractTransactionalCommand(request.getEditingDomain(), "Move Stereotype Applications", null) {

						@Override
						protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
							for (Element next : stereotypeApplications.keySet()) {
								StereotypeApplicationHelper helper = StereotypeApplicationHelper.getInstance(next);

								// Somebody else may have moved some of the stereotype applications by now
								stereotypeApplications.get(next).stream()
										.forEach(a -> helper.addToContainmentList(next, a));
							}
							return CommandResult.newOKCommandResult();
						}
					};
				}
			}
		}

		return result;
	}

	/**
	 * Collects the stereotype applications of all proper contents of an element.
	 * 
	 * @param movedElement
	 *            an element being moved
	 * @param result
	 *            collector of the mappings of moved elements to their stereotype
	 *            applications that also need to be moved
	 */
	private void collectStereotypeApplications(Element movedElement, Multimap<Element, EObject> result) {
		// Iterate proper contents only because cross-resource-contained children are not
		// being moved into another resource: they stay in the resource that stores them
		for (Iterator<EObject> iter = EcoreUtil.getAllProperContents(Collections.singleton(movedElement), false); iter.hasNext();) {
			EObject next = iter.next();
			if (next instanceof Element) {
				Element element = (Element) next;
				Resource resource = element.eResource();

				// Only move stereotype applications that are co-stored with their base element
				// (i.e., that aren't in externalized profile applications)
				element.getStereotypeApplications().stream()
						.filter(a -> a.eResource() == resource)
						.forEach(a -> result.put(element, a));
			}
		}
	}
}
