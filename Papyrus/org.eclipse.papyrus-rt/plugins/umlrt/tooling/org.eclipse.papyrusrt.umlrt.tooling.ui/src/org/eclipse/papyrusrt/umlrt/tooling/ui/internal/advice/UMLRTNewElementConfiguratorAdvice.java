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

import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.emf.types.internal.ui.advice.RuntimeValuesAdviceEditHelperAdvice;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.creation.EditionDialog;
import org.eclipse.papyrus.infra.services.edit.utils.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTNewElementConfigurator;
import org.eclipse.swt.widgets.Display;

/**
 * The UML-RT new element configurator advice.
 */
public class UMLRTNewElementConfiguratorAdvice extends RuntimeValuesAdviceEditHelperAdvice {
	private static final String DEFAULT_DIALOG_TITLE_PATTERN = "Create {0}";

	private final String dialogTitlePattern;

	/**
	 * Initializes me.
	 * 
	 * @param adviceConfiguration
	 *            my configuration model
	 */
	public UMLRTNewElementConfiguratorAdvice(UMLRTNewElementConfigurator adviceConfiguration) {
		super(adviceConfiguration);

		this.dialogTitlePattern = (adviceConfiguration.getDialogTitlePattern() == null)
				? DEFAULT_DIALOG_TITLE_PATTERN
				: adviceConfiguration.getDialogTitlePattern();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		ICommand result = null;

		EObject newElement = request.getElementToConfigure();
		if (newElement != null) {
			result = edit(newElement, ElementTypeUtils.dialogCancellable(request), request);
		}

		return result;
	}

	public String getDialogTitlePattern() {
		return dialogTitlePattern;
	}

	protected ICommand edit(EObject newElement, boolean allowCancel, ConfigureRequest request) {
		IElementType elementType = request.getTypeToConfigure();

		String dialogTitle = NLS.bind(getDialogTitlePattern(), elementType.getDisplayName());

		class Command extends AbstractTransactionalCommand {
			/**
			 * Initializes me with my request.
			 * 
			 * @param request
			 *            the configure request
			 */
			Command(IEditCommandRequest request) {
				super(request.getEditingDomain(), NLS.bind("Configure {0}", elementType.getDisplayName()), getWorkspaceFiles(newElement));
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				int dialogResult = Window.OK;
				Set<View> dialogContent = getViewsToDisplay();
				if (!dialogContent.isEmpty()) {
					EditionDialog dialog = new ConfiguratorDialog(
							Display.getCurrent().getActiveShell(),
							allowCancel);

					dialog.setTitle(dialogTitle);
					dialog.setViews(dialogContent);
					dialog.setInput(newElement);

					dialogResult = dialog.open();
				}

				return (dialogResult == Window.OK)
						? CommandResult.newOKCommandResult(newElement)
						: CommandResult.newCancelledCommandResult();
			}
		}

		return new Command(request);
	}

}
