/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 493869, 510315, 514392
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.requests.UnsetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrusrt.umlrt.core.commands.ExcludeDependentsRequest;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;

import com.google.common.base.Strings;

/**
 * Generic class for RTPseudoState edit helper advices
 */
public abstract class AbstractRTPseudoStateEditHelperAdvice extends AbstractEditHelperAdvice implements IInheritanceEditHelperAdvice {

	protected abstract PseudostateKind getKind();

	@Override
	public ICommand getBeforeEditCommand(IEditCommandRequest request) {
		ICommand result = getInheritanceEditCommand(request);

		if (result == null) {
			result = super.getBeforeEditCommand(request);
		}

		return result;
	}

	@Override
	public ICommand getExcludeDependentsCommand(ExcludeDependentsRequest request) {
		ICommand result = IInheritanceEditHelperAdvice.super.getExcludeDependentsCommand(request);

		if (request.isExclude() && (request.getElementToExclude() instanceof Vertex)) {
			// Exclude incoming and outgoing transitions, also
			UMLRTVertex vertex = UMLRTVertex.getInstance((Vertex) request.getElementToExclude());
			if (vertex != null) {
				Predicate<UMLRTNamedElement> alreadyExcluded = UMLRTNamedElement::isExcluded;
				List<Transition> transitionsToExclude = Stream.concat(
						vertex.getIncomings().stream(),
						vertex.getOutgoings().stream()).distinct()
						.filter(alreadyExcluded.negate())
						.map(UMLRTTransition::toUML)
						.collect(Collectors.toList());

				if (!transitionsToExclude.isEmpty()) {
					ICommand excludeTransitions = request.getExcludeDependentsCommand(transitionsToExclude);
					if (excludeTransitions != null) {
						result = CompositeCommand.compose(result, excludeTransitions);
					}
				}
			}
		}

		return result;
	}

	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {
		// sets the pseudo state to initial
		ICommand command = new ConfigureElementCommand(request) {

			/**
			 * {@inheritDoc}
			 */
			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) {
				EObject elementToConfigure = request.getElementToConfigure();
				if (!(elementToConfigure instanceof Pseudostate)) {
					return CommandResult.newErrorCommandResult("Element to configure is not a pseudostate");
				}
				Pseudostate pseudoState = (Pseudostate) elementToConfigure;
				try {
					SetRequest setKindRequest = new SetRequest(pseudoState, UMLPackage.eINSTANCE.getPseudostate_Kind(), getKind());
					IElementEditService provider = ElementEditServiceUtils.getCommandProvider(pseudoState);
					if (provider == null) {
						throw new ExecutionException("Impossible to get the provider from " + pseudoState);
					}

					ICommand setKindCommand = provider.getEditCommand(setKindRequest);
					if (setKindCommand != null) {
						if (setKindCommand.canExecute()) {
							IStatus status = setKindCommand.execute(monitor, info);
							if (status.isOK()) {
								return CommandResult.newOKCommandResult(pseudoState);
							} else {
								throw new ExecutionException(status.getMessage());
							}
						} else {
							throw new ExecutionException("Command to set the pseudo state kind is not executable");
						}
					} else {
						throw new ExecutionException("Impossible to find a command to set the kind of the pseudo state");
					}
				} catch (ExecutionException e) {
					return CommandResult.newErrorCommandResult(e);
				}
			}

		};

		ICommand superCommand = super.getBeforeConfigureCommand(request);
		if (superCommand != null) {
			// draw the diagram, with initial state displayed
			return command.compose(superCommand).reduce();
		}
		return command.reduce();

	}

	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				final Pseudostate pseudostate = (Pseudostate) request.getElementToConfigure();
				String name = getInitialName(pseudostate);
				IElementEditService provider = ElementEditServiceUtils.getCommandProvider(pseudostate);
				if (provider == null) {
					throw new ExecutionException("Impossible to get the provider from " + pseudostate);
				}
				if (Strings.isNullOrEmpty(name)) {
					// reset the name feature
					UnsetRequest unsetRequest = new UnsetRequest(pseudostate, UMLPackage.Literals.NAMED_ELEMENT__NAME);
					ICommand resetNameCommand = provider.getEditCommand(unsetRequest);
					if ((resetNameCommand != null) && resetNameCommand.canExecute()) {
						resetNameCommand.execute(progressMonitor, info);
					}
				} else {
					SetRequest setNameRequest = new SetRequest(pseudostate, UMLPackage.Literals.NAMED_ELEMENT__NAME, name);
					ICommand setNameCommand = provider.getEditCommand(setNameRequest);
					if ((setNameCommand != null) && setNameCommand.canExecute()) {
						setNameCommand.execute(progressMonitor, info);
					}
				}
				return CommandResult.newOKCommandResult(pseudostate);
			}

		};
	}

	/**
	 * Obtains an initial name for the new {@code pseudostate}.
	 * 
	 * @param pseudostate
	 *            a new pseudostate
	 * 
	 * @return its initial name, or {@code null} if none particular is required
	 */
	protected String getInitialName(Pseudostate pseudostate) {
		String result;

		switch (pseudostate.getKind()) {
		default:
			// No name for pseudostates, see Bug 494292
			result = null;
			break;
		}

		return result;
	}
}
