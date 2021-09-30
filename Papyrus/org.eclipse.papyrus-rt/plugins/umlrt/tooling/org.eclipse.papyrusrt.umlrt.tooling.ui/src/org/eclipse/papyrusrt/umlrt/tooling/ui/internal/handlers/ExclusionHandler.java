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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.handlers;

import static org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper.wrap;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrusrt.umlrt.core.commands.ExclusionCommand;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.ui.ISources;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.uml2.uml.Element;

/**
 * Default handler for the exclude and reinherit commands.
 */
public class ExclusionHandler extends AbstractHandler implements IElementUpdater, IExecutableExtension {

	private final boolean doUpdate;
	private boolean isExclude = true;

	/**
	 * Initializes me.
	 */
	public ExclusionHandler() {
		this(false);
	}

	/**
	 * Initializes me with the option of updating the text of the command that I
	 * handle to indicate exclusion, instead.
	 * 
	 * @param doUpdate
	 *            whether to update my command to indicate exclusion semantics
	 */
	public ExclusionHandler(boolean doUpdate) {
		super();

		this.doUpdate = doUpdate;
	}

	/**
	 * Queries whether I exclude elements.
	 * 
	 * @return {@code true} if I exclude elements; {@code false} if I re-inherit them
	 */
	public boolean isExclude() {
		return isExclude;
	}

	/**
	 * Sets whether I exclude elements.
	 * 
	 * @param exclude
	 *            {@code true} if I exclude elements; {@code false} if I re-inherit them
	 */
	public void setExclude(boolean exclude) {
		this.isExclude = exclude;
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		if (data instanceof String) {
			setExclude(Boolean.valueOf(((String) data).trim()));
		} else if (data instanceof Map<?, ?>) {
			String isExclude = (String) ((Map<?, ?>) data).get("isExclude"); //$NON-NLS-1$
			if (isExclude != null) {
				setExclude(Boolean.valueOf(isExclude.trim()));
			}
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Object result = null;

		List<Element> elements = getSelection(event.getApplicationContext());
		if (!elements.isEmpty()) {
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(elements.get(0));
			if (domain != null) {
				Command command = wrap(ExclusionCommand.getExclusionCommand(domain, elements, isExclude()));
				domain.getCommandStack().execute(command);
				result = command.getResult().isEmpty()
						? null
						: command.getResult().iterator().next();
			}
		}

		return result;
	}

	protected List<Element> getSelection(Object evaluationContext) {
		IStructuredSelection sel = TypeUtils.as(
				HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME),
				StructuredSelection.EMPTY);

		List<Element> result = ((List<?>) sel.toList()).stream()
				.map(e -> PlatformHelper.getAdapter(e, EObject.class))
				.filter(Element.class::isInstance).map(Element.class::cast)
				.collect(Collectors.toList());

		return result;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		List<Element> elements = getSelection(evaluationContext);
		Predicate<Element> condition = isExclude()
				? UMLRTProfileUtils::canExclude
				: UMLRTProfileUtils::canReinherit;
		setBaseEnabled(elements.stream().allMatch(condition));
	}

	@Override
	public void updateElement(UIElement element, @SuppressWarnings("rawtypes") Map parameters) {
		if (doUpdate) {
			element.setText("Exclude Element");
			element.setTooltip("Undefine the inherited element in the inheriting namespace");
		}
	}
}
