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
 *  Christian W. Damus - bugs 474481, 467545
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrusrt.umlrt.core.commands.ExcludeDependentsRequest;
import org.eclipse.papyrusrt.umlrt.core.utils.NewElementUtil;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.VisibilityKind;

/**
 * Edit Helper Advice for {@link CapsulePart}
 */
public class CapsulePartEditHelperAdvice extends AbstractEditHelperAdvice implements IInheritanceEditHelperAdvice {

	public CapsulePartEditHelperAdvice() {
		super();
	}

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

		if (request.isExclude() && (request.getElementToExclude() instanceof Property)) {
			// Exclude connectors, also
			UMLRTCapsulePart part = UMLRTCapsulePart.getInstance((Property) request.getElementToExclude());
			if (part != null) {
				Predicate<UMLRTNamedElement> alreadyExcluded = UMLRTNamedElement::isExcluded;
				List<Connector> connectorsToExclude = part.getConnectorsOfPorts().stream()
						.filter(alreadyExcluded.negate())
						.map(UMLRTConnector::toUML)
						.collect(Collectors.toList());

				if (!connectorsToExclude.isEmpty()) {
					ICommand excludeConnectors = request.getExcludeDependentsCommand(connectorsToExclude);
					if (excludeConnectors != null) {
						result = CompositeCommand.compose(result, excludeConnectors);
					}
				}
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeCreateRelationshipCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest)
	 */
	@Override
	protected ICommand getBeforeCreateRelationshipCommand(CreateRelationshipRequest request) {
		return super.getBeforeCreateRelationshipCommand(request);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		final Property capsulePart = (Property) request.getElementToConfigure();
		request.setParameter(RequestParameterConstants.DIALOG_CANCELLABLE, true);

		return new ConfigureElementCommand(request) {

			/**
			 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
			 *
			 */
			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				capsulePart.setAggregation(AggregationKind.COMPOSITE_LITERAL);
				// capsulePart.setLower(1);
				// capsulePart.setUpper(1);
				capsulePart.setName(null);// set to null to let it be named when type is set
				capsulePart.setIsOrdered(true);
				capsulePart.setVisibility(VisibilityKind.PROTECTED_LITERAL);
				return CommandResult.newOKCommandResult(capsulePart);
			}

		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		final Property property = (Property) request.getElementToConfigure();

		// This capsule-part is new if it is being configured
		NewElementUtil.elementCreated(property);

		CompositeCommand compositeCommand = new CompositeCommand("Before Configure CapsulePart");

		// If the newly create Port has no multiplicity set it to the default Value
		// if (null == property.getLowerValue()) {
		// // Set the new value based on the kind set
		// LiteralInteger newLowerValue = UMLFactory.eINSTANCE.createLiteralInteger();
		// newLowerValue.setValue(1);
		//
		// SetRequest setLowerBounds = new SetRequest(property, UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue(), newLowerValue);
		// IElementEditService commandProvider = ElementEditServiceUtils.getCommandProvider(property);
		// compositeCommand.add(commandProvider.getEditCommand(setLowerBounds));
		//
		// }
		//
		// if (null == property.getUpperValue()) {
		// LiteralUnlimitedNatural newUpperValue = UMLFactory.eINSTANCE.createLiteralUnlimitedNatural();
		// newUpperValue.setValue(1);
		//
		// SetRequest setUpperBounds = new SetRequest(property, UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue(), newUpperValue);
		// IElementEditService commandProvider = ElementEditServiceUtils.getCommandProvider(property);
		// compositeCommand.add(commandProvider.getEditCommand(setUpperBounds));
		// }

		return compositeCommand.isEmpty() ? super.getBeforeConfigureCommand(request) : compositeCommand;
	}




}
