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

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrusrt.umlrt.core.commands.ExcludeDependentsRequest;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Port;

/**
 * Common advice for the various UML-RT port types.
 */
public abstract class AbstractPortEditHelperAdvice extends AbstractEditHelperAdvice implements IInheritanceEditHelperAdvice {

	public AbstractPortEditHelperAdvice() {
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

		if (request.isExclude() && (request.getElementToExclude() instanceof Port)) {
			// Exclude connectors, also
			UMLRTPort port = UMLRTPort.getInstance((Port) request.getElementToExclude());
			if (port != null) {
				Predicate<UMLRTNamedElement> alreadyExcluded = UMLRTNamedElement::isExcluded;
				List<Connector> connectorsToExclude = Stream.concat(
						port.getInsideConnectors().stream(), port.getOutsideConnectors().stream())
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

}
