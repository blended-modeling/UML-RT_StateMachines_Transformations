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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.advice;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration;
import org.eclipse.papyrusrt.umlrt.tooling.ui.commands.InteractiveSetTypedElementTypeCommand;
import org.eclipse.uml2.uml.TypedElement;

import com.google.common.base.Suppliers;

/**
 * The UML-RT new element configurator advice.
 */
public class UMLRTSetTypeAdvice extends AbstractEditHelperAdvice {

	private String typeElementType;

	// Not all element-types are known at the time of the advice instantiation,
	// so defer the request for the element-type label by way of a supplier
	private Supplier<String> typeElementTypeLabel;

	private Set<View> newTypeViews;

	/**
	 * Initializes me.
	 * 
	 * @param adviceConfiguration
	 *            my configuration model
	 */
	public UMLRTSetTypeAdvice(UMLRTSetTypeAdviceConfiguration adviceConfiguration) {
		super();

		this.typeElementType = adviceConfiguration.getElementType();
		this.typeElementTypeLabel = Suppliers.memoize(adviceConfiguration::getElementTypeLabel)::get;
		this.newTypeViews = new HashSet<>(adviceConfiguration.getNewTypeViews());
	}

	public IElementType getTypeElementType() {
		IElementType result = null;
		if (typeElementType != null) {
			result = ElementTypeRegistry.getInstance().getType(typeElementType);
		}
		return result;
	}

	public Set<View> getNewTypeViews() {
		return Collections.unmodifiableSet(newTypeViews);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		ICommand result = null;
		IElementType typeElementType = getTypeElementType();
		if (typeElementType != null) {
			IElementType elementTypeToConfigure = request.getTypeToConfigure();
			EObject elementToConfigure = request.getElementToConfigure();
			TransactionalEditingDomain editingDomain = request.getEditingDomain();
			if ((editingDomain != null) && (elementToConfigure instanceof TypedElement)) {
				InteractiveSetTypedElementTypeCommand command = new InteractiveSetTypedElementTypeCommand(
						(TypedElement) elementToConfigure,
						elementTypeToConfigure,
						typeElementType,
						getElementTypeLabel(request));
				result = command;
				command.setNewTypeViews(getNewTypeViews());
				command.setCreateNew(true); // I am configuring a new element
			}
		}

		return result;
	}

	private Function<IElementType, String> getElementTypeLabel(ConfigureRequest request) {
		return type -> {
			String result;

			if (type == getTypeElementType()) {
				result = typeElementTypeLabel.get();
			} else if (type == request.getTypeToConfigure()) {
				// Use the RT-specific name of the type
				result = type.getDisplayName();
			} else {
				// It's from Papyrus and the name is not presentable
				result = type.getEClass().getName();
			}

			return result;
		};
	}
}
