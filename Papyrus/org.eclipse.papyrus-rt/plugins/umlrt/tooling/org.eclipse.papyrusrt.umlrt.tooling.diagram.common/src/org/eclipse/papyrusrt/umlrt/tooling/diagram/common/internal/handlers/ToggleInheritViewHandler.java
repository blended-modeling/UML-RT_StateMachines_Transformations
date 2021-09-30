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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.handlers;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.ui.util.EclipseCommandUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler for the toggling of view position/routing/etc.&nbsp;inheritance.
 */
public class ToggleInheritViewHandler extends AbstractHandler {

	public ToggleInheritViewHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		List<IInheritableEditPart> editParts = getInheritableEditParts(selection);

		if (!editParts.isEmpty()) {
			TransactionalEditingDomain domain = editParts.get(0).getEditingDomain();
			List<Boolean> states = getInheritanceStates(editParts);
			if ((domain != null) && !states.isEmpty()) {
				boolean groundState = states.get(0);

				Function<View, Command> commandFuntion = groundState
						? IInheritableEditPart.StyleUtil::getCreateInheritedStyle
						: IInheritableEditPart.StyleUtil::getDeleteInheritedStyle;

				Predicate<IInheritableEditPart> needsChange = iep -> iep.isInherited() == groundState;

				Command command = editParts.stream()
						.filter(needsChange)
						.map(IGraphicalEditPart::getNotationView)
						.map(commandFuntion)
						.reduce(Command::chain)
						.orElse(UnexecutableCommand.INSTANCE);

				if (command.canExecute()) {
					domain.getCommandStack().execute(command);
				}
			}
		}

		return null;
	}

	private List<IInheritableEditPart> getInheritableEditParts(IStructuredSelection selection) {
		return inheritableEditParts(selection).collect(Collectors.toList());
	}

	private Stream<IInheritableEditPart> inheritableEditParts(IStructuredSelection selection) {
		Predicate<IInheritableEditPart> isDependent = IInheritableEditPart::isDependentChild;
		return ((List<?>) selection.toList()).stream()
				.filter(IInheritableEditPart.class::isInstance)
				.map(IInheritableEditPart.class::cast)
				.filter(IInheritableEditPart::canInherit)
				.filter(isDependent.negate());
	}

	private List<Boolean> getInheritanceStates(IStructuredSelection selection) {
		return inheritableEditParts(selection)
				.map(IInheritableEditPart::isInherited)
				.distinct()
				.sorted(followBias())
				.collect(Collectors.toList());
	}


	private List<Boolean> getInheritanceStates(List<? extends IInheritableEditPart> editParts) {
		return editParts.stream()
				.map(IInheritableEditPart::isInherited)
				.distinct()
				.sorted(followBias())
				.collect(Collectors.toList());
	}

	/**
	 * Obtains a comparator that sorts the inheritance states according to the
	 * preferred bias for heterogenous selections: to follow or to un-follow.
	 * 
	 * @return the inheritance state bias comparator
	 */
	private static Comparator<Boolean> followBias() {
		// Bias towards toggling from unfollow to follow
		return Comparator.naturalOrder();
	}

	@Override
	public void setEnabled(Object context) {
		Object sel = HandlerUtil.getVariable(context, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		IStructuredSelection selection = (sel instanceof IStructuredSelection)
				? (IStructuredSelection) sel
				: StructuredSelection.EMPTY;
		List<Boolean> states = getInheritanceStates(selection);

		// I can toggle even an heterogeneous selection because I will just
		// set all views to follow their ancestors according to my follow bias
		boolean enable = !states.isEmpty();

		if (enable) {
			org.eclipse.core.commands.Command command = EclipseCommandUtils.getCommandService().getCommand("org.eclipse.papyrusrt.umlrt.tooling.diagram.common.toggleInheritView");
			if (command == null) {
				enable = false;
			} else {
				EclipseCommandUtils.updateToggleCommandState(command, states.get(0));
			}
		}

		setBaseEnabled(enable);
	}
}
