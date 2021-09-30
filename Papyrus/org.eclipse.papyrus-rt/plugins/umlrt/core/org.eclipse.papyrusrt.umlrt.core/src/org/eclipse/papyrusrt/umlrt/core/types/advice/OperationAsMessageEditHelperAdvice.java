/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Onder Gurcan <onder.gurcan@cea.fr> - Initial API and implementation
 *   Christian W. Damus - bugs 507282, 512666, 517130
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.types.advice;

import static org.eclipse.papyrusrt.umlrt.core.types.advice.MessageSetEditHelperAdvice.getRelatedCallEvent;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.edit.utils.GMFCommandUtils;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.commands.IExcludeElementRequest;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;


/**
 * The helperadvice class used for UMLRealTime::Operation.
 *
 * @author Onder Gurcan <onder.gurcan@cea.fr>
 *
 */
public class OperationAsMessageEditHelperAdvice extends AbstractEditHelperAdvice implements IInheritanceEditHelperAdvice {

	private enum Relation {
		CHILD, SIBLING, PARENT;
	}

	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		if (request instanceof IExcludeElementRequest) {
			return approveExcludeRequest((IExcludeElementRequest) request);
		} else if (request instanceof CreateElementRequest) {
			return approveCreateElementRequest((CreateElementRequest) request);
		} else {
			return super.approveRequest(request);
		}
	}

	/**
	 * Don't permit exclusion of inherited parameters.
	 */
	protected boolean approveExcludeRequest(IExcludeElementRequest request) {
		return !(request.getElementToExclude() instanceof Parameter);
	}

	/**
	 * Don't permit creation of parameters in an inherited operation.
	 */
	protected boolean approveCreateElementRequest(CreateElementRequest request) {
		return !ElementTypeUtils.isTypeCompatible(request.getElementType(), UMLElementTypes.PARAMETER)
				|| !UMLRTExtensionUtil.isInherited((Operation) request.getContainer());
	}

	@Override
	public ICommand getBeforeEditCommand(IEditCommandRequest request) {
		ICommand result = getInheritanceEditCommand(request);

		if (result == null) {
			result = super.getBeforeEditCommand(request);
		}

		return result;
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
		/* @noname final String name = NamedElementUtil.getDefaultNameWithIncrementFromBase("CallEvent", operation.eContainer().eContainer().eContents()); */
		final String name = null;
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
			private CallEvent createCallEvent(final Operation operation, final String name) throws ExecutionException {
				// find the corresponding protocol container
				Package protocolContainer = operation.getNearestPackage();
				// then create the call event
				CallEvent callEvent = (CallEvent) createElement(protocolContainer, name, UMLElementTypes.CALL_EVENT, Relation.CHILD);
				// finally associate the operation with this call event
				callEvent.setOperation(operation);
				return callEvent;
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
				if ((referenceElement == null) /* @noname || (name == null) */) {
					throw new ExecutionException("Either the referenceElement or the name parameter is null. ");
				}

				EObject newElement = null;

				Package container = referenceElement.getNearestPackage();
				CreateElementRequest createElementRequest = new CreateElementRequest(container, elementType);
				// get command from edit service
				IElementEditService provider = ElementEditServiceUtils.getCommandProvider(container);
				if (provider == null) {
					throw new ExecutionException("Impossible to get the provider from " + container);
				}

				ICommand createGMFCommand = provider.getEditCommand(createElementRequest);
				if (createGMFCommand != null) {
					if (createGMFCommand.canExecute()) {
						IStatus status = createGMFCommand.execute(progressMonitor, info);
						if (status.isOK()) {
							newElement = GMFCommandUtils.getCommandEObjectResult(createGMFCommand);

							if (newElement == null) {
								throw new ExecutionException("Element creation problem for " + elementType.getDisplayName() + ".");
							}

							// if we do not add this , the default name is set : CallEvent1,2...
							// ((NamedElement) newElement).setName(name);

							return newElement;
						} else {
							throw new ExecutionException("Impossible to create the callEvent");
						}
					} else {
						throw new ExecutionException("Command to create the callEvent is not executable");
					}
				} else {
					throw new ExecutionException("Impossible to find a command to create the callEvent");
				}
			}
		};
	}

	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {
		ICommand result = super.getBeforeDestroyDependentsCommand(request);

		if (request.getElementToDestroy() instanceof Operation) {
			Operation operation = (Operation) request.getElementToDestroy();
			CallEvent relatedCallEvent = getRelatedCallEvent(operation.getNearestPackage(), operation);

			if (relatedCallEvent != null) {
				ICommand command = request.getDestroyDependentCommand(relatedCallEvent);
				if (command != null) {
					result = (result == null) ? command : result.compose(command);
				}
			}
		}

		return result;
	}
}
