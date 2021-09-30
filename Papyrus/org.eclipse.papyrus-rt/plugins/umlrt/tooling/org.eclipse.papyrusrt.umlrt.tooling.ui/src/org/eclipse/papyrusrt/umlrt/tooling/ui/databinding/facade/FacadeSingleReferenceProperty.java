/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrusrt.umlrt.core.commands.ExclusionCommand;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.uml2.uml.NamedElement;

/**
 * A property for a {@link UMLRTNamedElement}'s inheritable singular reference feature.
 */
class FacadeSingleReferenceProperty<S extends UMLRTNamedElement, T extends UMLRTNamedElement> extends FacadeValueProperty<S, T> {

	FacadeSingleReferenceProperty(Class<T> elementType, List<? extends EStructuralFeature> features,
			Function<? super S, ? extends T> accessor) {

		super(elementType, features, accessor);
	}

	@Override
	T fromModel(Object o) {
		return super.fromModel(UMLRTFactory.create((NamedElement) o));
	}

	@Override
	Object toModel(T value) {
		return (value == null) ? null : value.toUML();
	}

	@Override
	protected List<? extends ICommand> getCommands(S source, ValueDiff<T> diff) {
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(source.toUML());
		List<ICommand> result = new ArrayList<>();

		if (diff.getOldValue() != null) {
			T element = diff.getOldValue();

			switch (element.getInheritanceKind()) {
			case INHERITED:
			case REDEFINED:
				// Exclude, don't destroy
				result.add(ExclusionCommand.getExclusionCommand(domain, element.toUML(), true));
				break;
			case NONE:
				// Actually destroy it
				IEditCommandRequest request = new DestroyElementRequest(domain, element.toUML(), false);
				IElementEditService edit = ElementEditServiceUtils.getCommandProvider(element.toUML());
				ICommand destroy = edit.getEditCommand(request);
				result.add(destroy);
				break;
			default:
				// Pass
				break;
			}
		}

		result.add(getSetCommand(domain, source, Optional.ofNullable(diff.getNewValue())));

		return result;
	}

}
