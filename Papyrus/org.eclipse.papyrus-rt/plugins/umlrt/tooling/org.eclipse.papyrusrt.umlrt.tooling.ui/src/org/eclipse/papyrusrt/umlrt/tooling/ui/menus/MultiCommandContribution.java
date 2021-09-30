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

package org.eclipse.papyrusrt.umlrt.tooling.ui.menus;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.commands.Command;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;

/**
 * Dynamic contributions of command-based actions to a menu that otherwise
 * ignores the command's enablement conditions. This contribution only
 * provides items for commands that are enabled. The following parameters
 * are required in the {@literal <class>} element in the extension XML:
 * <dl>
 * <dt>commandIDs</dt>
 * <dd>Command-separated list of command IDs to contribute, in order.
 * For convenience, an ID that does not contain a '.' is appended
 * to the contributing plug-in's symbolic name with a dot separator</dd>
 * </dl>
 */
public class MultiCommandContribution extends CompoundContributionItem implements IWorkbenchContribution, IExecutableExtension {

	private IServiceLocator services;
	private List<String> commandIDs = Collections.emptyList();

	public MultiCommandContribution() {
		super();
	}

	public MultiCommandContribution(String id) {
		super(id);
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		@SuppressWarnings("unchecked")
		Map<String, String> params = (Map<String, String>) data;
		String commandIDs = params.get("commandIDs"); //$NON-NLS-1$
		if (commandIDs != null) {
			String contributor = config.getContributor().getName();
			Function<String, String> qualify = id -> id.indexOf('.') < 0 ? contributor + '.' + id : id;

			this.commandIDs = Stream.of(commandIDs.split(","))
					.map(String::trim)
					.filter(((Predicate<String>) String::isEmpty).negate())
					.map(qualify)
					.collect(Collectors.toList());
		}
	}

	@Override
	public void initialize(IServiceLocator serviceLocator) {
		this.services = serviceLocator;
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		return commandIDs.stream()
				.map(this::getCommandAction)
				.filter(Objects::nonNull)
				.toArray(IContributionItem[]::new);
	}

	protected IContributionItem getCommandAction(String commandID) {
		ICommandService commands = services.getService(ICommandService.class);
		if (commands == null) {
			return null;
		}

		return Optional.ofNullable(commands.getCommand(commandID))
				.filter(Command::isEnabled)
				.map(command -> new CommandContributionItemParameter(
						services,
						command.getId(), command.getId(),
						CommandContributionItem.STYLE_PUSH))
				.map(CommandContributionItem::new)
				.orElse(null);
	}
}
