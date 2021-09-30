/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Christian W. Damus - bug 476984
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.types.advice;

import java.util.List;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.NewElementUtil;
import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.UMLPackage;

import com.google.common.base.Strings;

/**
 * Edit Helper advice for the Parameter in Papyrus RT.
 * 
 * @author Céline JANSSENS
 *
 */
public class ParameterEditHelperAdvice extends AbstractEditHelperAdvice {
	/** Default Name of the first parameter. */
	private static final String DEFAULT_PARAMETER_NAME = "data";// $NON-NLS-1$

	/**
	 * Constructor.
	 *
	 */
	public ParameterEditHelperAdvice() {
		super();

	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 */
	@Override
	public boolean approveRequest(final IEditCommandRequest request) {
		boolean approve = true;
		List<?> elementsToEdit = request.getElementsToEdit();

		if (1 == elementsToEdit.size()) {
			Object element = elementsToEdit.get(0);
			if (element instanceof Parameter) {
				// This helper only works if the Parameter is an RTMessage Parameter.
				approve = RTMessageUtils.isRTMessage(((Parameter) element).getOperation());
			}
		}
		return approve;

	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {
		ICommand command = super.getBeforeConfigureCommand(request);
		Parameter parameter = TypeUtils.as(request.getElementToConfigure(), Parameter.class);

		// if the Parameter being created has no name defined yet then set the default name
		if ((parameter != null) && Strings.isNullOrEmpty(parameter.getName())) {
			String initialName;

			// if this is the first parameter in the operation, then use the default name
			if (!hasAnyOtherParameter(parameter.getOperation(), parameter)) {
				initialName = DEFAULT_PARAMETER_NAME;
			} else {
				initialName = NewElementUtil.getUniqueName(parameter, "parameter", 2, true);
			}

			IElementEditService commandProvider = ElementEditServiceUtils.getCommandProvider(parameter);

			IEditCommandRequest setNameRequest = new SetRequest(parameter, UMLPackage.eINSTANCE.getNamedElement_Name(), initialName);
			ICommand editCommand = commandProvider.getEditCommand(setNameRequest);

			if ((editCommand != null) && editCommand.canExecute()) {
				command = editCommand;
			}
		}

		return command;
	}



	/**
	 * Test if an {@code operation} does not yet have a non-return parameter
	 * different to {@code parameter}.
	 * 
	 * @param operation
	 *            The operation under test
	 * @param thanParameter
	 *            a parameter to be excluded from consideration
	 * @return whether it has no other parameters that are not return results
	 */
	private boolean hasAnyOtherParameter(final Operation operation, final Parameter thanParameter) {
		// If there's more than one other, then at least one of them
		// is not the return result
		return operation.getOwnedParameters().stream()
				.filter(p -> p != thanParameter)
				.anyMatch(p -> p.getDirection() != ParameterDirectionKind.RETURN_LITERAL);
	}

}
