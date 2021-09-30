/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.core.types.advice;

import static org.eclipse.papyrusrt.umlrt.core.commands.ExcludeRequest.EXCLUDE_DEPENDENTS_REQUEST_PARAMETER;
import static org.eclipse.papyrusrt.umlrt.core.commands.ExcludeRequest.INITIAL_ELEMENT_TO_EXCLUDE_PARAMETER;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrusrt.umlrt.core.commands.ExcludeDependentsRequest;
import org.eclipse.papyrusrt.umlrt.core.commands.ExcludeRequest;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.uml2.uml.Element;

/**
 * A protocol for the edit-helper advice of elements that have UML-RT inheritance semantics,
 * which supports edit requests peculiar to them.
 */
public interface IInheritanceEditHelperAdvice {
	/**
	 * Handles requests peculiar to UML-RT inheritable elements.
	 * 
	 * @param request
	 *            a request that may or may not be inheritance-related
	 * 
	 * @return the command, or {@code null} if the {@code request} is not inheritance-related
	 *         or there simply is no command required to satisfy it
	 */
	default ICommand getInheritanceEditCommand(IEditCommandRequest request) {
		ICommand result = null;

		if (request instanceof ExcludeRequest) {
			result = getExcludeCommand((ExcludeRequest) request);
		} else if (request instanceof ExcludeDependentsRequest) {
			result = getExcludeDependentsCommand((ExcludeDependentsRequest) request);
		}

		return result;
	}

	/**
	 * Obtains a command that excludes the {@code request}ed element and its dependents.
	 * 
	 * @param request
	 *            an exclude request
	 * @return the command to exclude the element ad its dependents
	 */
	default ICommand getExcludeCommand(ExcludeRequest request) {
		ICommand result = getBasicExcludeCommand(request);

		Element initial = (Element) request.getParameter(INITIAL_ELEMENT_TO_EXCLUDE_PARAMETER);

		if (initial == null) {
			// Set the parameter to keep track of the initial element to exclude
			request.setParameter(INITIAL_ELEMENT_TO_EXCLUDE_PARAMETER, request.getElementToExclude());
		}

		// Get elements dependent on the element we are excluding that
		// must also be excluded
		ExcludeDependentsRequest dependents = (ExcludeDependentsRequest) request.getParameter(EXCLUDE_DEPENDENTS_REQUEST_PARAMETER);
		if (dependents == null) {
			// Create the exclude-dependents request that will be propagated to
			// exclude requests for all elements excluded in this operation
			dependents = new ExcludeDependentsRequest(request);

			// Propagate the parameters, including the initial element to exclude
			dependents.addParameters(request.getParameters());
			dependents.setClientContext(request.getClientContext());
			request.setParameter(EXCLUDE_DEPENDENTS_REQUEST_PARAMETER, dependents);
		} else {
			dependents.setElementToExclude(request.getElementToExclude());
		}

		IElementType typeToEdit = ElementTypeRegistry.getInstance().getElementType(
				dependents.getEditHelperContext());

		if (typeToEdit != null) {
			// Get the dependents-exclusion command
			ICommand command = typeToEdit.getEditCommand(dependents);

			if (command != null) {
				// Exclude dependents before the element they depend on
				result = command.compose(result);
			}
		}

		return result;
	}

	/**
	 * Obtains a command that excludes the {@code request}ed element only,
	 * not also its dependents.
	 * 
	 * @param request
	 *            an exclude request
	 * @return the command to exclude the element only
	 */
	default ICommand getBasicExcludeCommand(ExcludeRequest request) {
		Element toExclude = request.getElementToExclude();

		if (request.getParameter(INITIAL_ELEMENT_TO_EXCLUDE_PARAMETER) == null) {
			request.setParameter(INITIAL_ELEMENT_TO_EXCLUDE_PARAMETER, toExclude);
		}

		if (request.isExclude() && !UMLRTProfileUtils.canExclude(toExclude)) {
			return UnexecutableCommand.INSTANCE;
		}
		if (!request.isExclude() && !UMLRTProfileUtils.canReinherit(toExclude)) {
			return UnexecutableCommand.INSTANCE;
		}
		return request.getDefaultCommand();
	}

	/**
	 * Obtains a command that excludes the dependents of the {@code request}ed element.
	 * 
	 * @param request
	 *            an exclude request
	 * @return the command to exclude the dependents
	 */
	default ICommand getExcludeDependentsCommand(ExcludeDependentsRequest request) {
		return null;
	}
}
