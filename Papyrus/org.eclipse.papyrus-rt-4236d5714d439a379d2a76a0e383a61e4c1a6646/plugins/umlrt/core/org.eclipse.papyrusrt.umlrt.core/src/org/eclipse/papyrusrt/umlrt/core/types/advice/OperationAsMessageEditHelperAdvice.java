/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Onder Gurcan <onder.gurcan@cea.fr>
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;


/**
 * The helperadvice class used for UMLRealTime::Operation.
 *
 * @author Onder Gurcan <onder.gurcan@cea.fr>
 *
 */
public class OperationAsMessageEditHelperAdvice extends AbstractEditHelperAdvice {

	private CallEvent callEvent;

	private enum Relation {
		CHILD, SIBLING, PARENT;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		final Operation operation = (Operation) request.getElementToConfigure();
		final String name = NamedElementUtil.getDefaultNameWithIncrementFromBase("CallEvent", operation.eContainer().eContainer().eContents());

		return new ConfigureElementCommand(request) {
			private IProgressMonitor progressMonitor;
			private IAdaptable info;

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				this.progressMonitor = progressMonitor;
				this.info = info;

				// Create the UML::CallEvent element
				createCallEvent(operation, name);

				return CommandResult.newOKCommandResult(operation);
			}

			/**
			 * Creates a UML::Usage relation between protocol and rtMessageSet with given name.
			 *
			 * @param protocol
			 * @param name
			 * @param rtMessageSet
			 * @throws ExecutionException
			 */
			private void createCallEvent(final Operation operation, final String name) throws ExecutionException {
				// find the corresponding protocol container
				Package protocolContainer = operation.getNearestPackage();
				// then create the call event
				callEvent = (CallEvent) createElement(protocolContainer, name, UMLElementTypes.CALL_EVENT, Relation.CHILD);
				// finally associate the operation with this call event
				callEvent.setOperation(operation);
			}

			/**
			 *
			 * @param referenceElement
			 * @param name
			 * @param elementType
			 * @param relation
			 * @return created element as EObject
			 * @throws ExecutionException
			 */
			private EObject createElement(Element referenceElement, String name, IElementType elementType, Relation relation) throws ExecutionException {
				if ((referenceElement == null) || (name == null)) {
					throw new ExecutionException("Either the referenceElement or the name parameter is null. ");
				}

				EObject newElement = null;

				CreateElementRequest createElementRequest = new CreateElementRequest(referenceElement.getNearestPackage(), elementType);
				CreateElementCommand command = new CreateElementCommand(createElementRequest);
				command.execute(progressMonitor, info);
				newElement = command.getNewElement();

				if (newElement == null) {
					throw new ExecutionException("Element creation problem for " + elementType.getDisplayName() + ".");
				}

				((NamedElement)newElement).setName(name);

				return newElement;
			}
		};
	}
}
