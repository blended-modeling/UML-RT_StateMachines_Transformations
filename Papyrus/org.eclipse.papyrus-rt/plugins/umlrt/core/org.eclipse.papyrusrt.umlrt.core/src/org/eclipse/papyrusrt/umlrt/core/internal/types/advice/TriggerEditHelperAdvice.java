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

package org.eclipse.papyrusrt.umlrt.core.internal.types.advice;

import java.util.function.Function;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrusrt.umlrt.core.commands.ExcludeDependentsRequest;
import org.eclipse.papyrusrt.umlrt.core.types.advice.IInheritanceEditHelperAdvice;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTCommandUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.uml2.uml.Trigger;

/**
 * Edit-helper advice for triggers of UML-RT transitions.
 */
public class TriggerEditHelperAdvice extends AbstractEditHelperAdvice implements IInheritanceEditHelperAdvice {

	public TriggerEditHelperAdvice() {
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

		if (!request.isExclude() && (request.getElementToExclude() instanceof Trigger)) {
			// Re-inherit the guard constraint, if any, also
			UMLRTTrigger trigger = UMLRTTrigger.getInstance((Trigger) request.getElementToExclude());
			if (trigger != null) {
				UMLRTGuard guard = trigger.getGuard();

				if (guard != null) {
					ICommand reinheritGuard = guard.getInheritanceKind() == UMLRTInheritanceKind.NONE
							? getDestroyGuardCommand(request, guard)
							: request.getExcludeDependentCommand(guard.toUML());
					if (reinheritGuard != null) {
						result = UMLRTCommandUtils.flatCompose(result, reinheritGuard);
					}
				}
			}
		}

		return result;
	}

	protected ICommand getDestroyGuardCommand(ExcludeDependentsRequest request, UMLRTGuard guard) {
		DestroyElementRequest destroyRequest = new DestroyElementRequest(request.getEditingDomain(),
				guard.toUML(), false);
		destroyRequest.setClientContext(request.getClientContext());
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(guard.toUML().getContext());
		return (edit != null) ? edit.getEditCommand(destroyRequest) : null;
	}

	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {
		ICommand result = super.getBeforeDestroyDependentsCommand(request);

		if (request.getElementToDestroy() instanceof Trigger) {
			// If the trigger has a guard, destroy it also
			UMLRTTrigger trigger = UMLRTTrigger.getInstance((Trigger) request.getElementToDestroy());
			if (trigger != null) {
				UMLRTGuard guard = trigger.getGuard();

				if (guard != null) {
					ICommand destroyGuard = request.getDestroyDependentCommand(guard.toUML());
					if (destroyGuard != null) {
						result = UMLRTCommandUtils.flatCompose(result, destroyGuard);
					}
				}
			}
		}

		return result;
	}
}
