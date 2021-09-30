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

package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.infra.ui.emf.dialog.NestedEditingDialogContext;
import org.eclipse.papyrus.infra.widgets.databinding.ReferenceDialogObservableValue;
import org.eclipse.papyrus.infra.widgets.editors.ReferenceDialog;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.commands.InteractiveSetTypedElementTypeCommand;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Parameter;

/**
 * Reference "dialog" editing widget implementation for the type of a message parameter.
 * It uses the same pop-up menu as the parameters table widget for setting the type
 * of a parameter (so, not exactly a dialog).
 * 
 * @see MessageParameterTypeEditor
 */
public class MessageParameterTypeDialog extends ReferenceDialog {

	private static final String CREATION_DIALOGS_CTX = "ppe:/context/org.eclipse.papyrusrt.umlrt.tooling.ui/dialogs/CreationDialogs.ctx"; //$NON-NLS-1$
	private static final String PARAMETER_TYPE_CREATION_VIEW = "Protocol Msg Parameter Creation Dialog"; //$NON-NLS-1$

	public MessageParameterTypeDialog(Composite parent, int style) {
		super(parent, style);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected IObservableValue createWidgetObservable(IObservableValue modelProperty) {
		// Ensure that null values also go through the label provider
		return new ReferenceDialogObservableValue(this, this.currentValueLabel, modelProperty, labelProvider) {
			@Override
			protected void doSetValue(Object value) {
				super.doSetValue(value);

				if (value == null) {
					label.setText(labelProvider.getText(value));
					label.setImage(labelProvider.getImage(value));
				}
			}
		};
	}

	/**
	 * Shows the standard pop-up menu for parameter type selection and
	 * creation.
	 */
	@Override
	protected void browseAction() {
		Parameter parameter = (Parameter) getContextElement();

		// Create the interactive parameter type editing command that pops up the menu
		InteractiveSetTypedElementTypeCommand command = new InteractiveSetTypedElementTypeCommand(
				parameter,
				UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_PARAMETER,
				UMLElementTypes.CLASSIFIER,
				type -> (type == UMLElementTypes.CLASSIFIER)
						? "Type"
						: type.getEClass().getName());

		// Tell the command about the property-sheet views to use to fill the
		// dialog for creation of a new parameter type
		try {
			Context context = PropertiesRuntime.getConfigurationManager().getContext(URI.createURI(CREATION_DIALOGS_CTX));
			Optional<Set<View>> parameterTypeView = context.getViews().stream()
					.filter(view -> PARAMETER_TYPE_CREATION_VIEW.equals(view.getName()))
					.findFirst()
					.map(Collections::singleton);
			parameterTypeView.ifPresent(views -> command.setNewTypeViews(views));
		} catch (IOException e) {
			Activator.log.error("Failed to load property sheet views for new parameter types", e); //$NON-NLS-1$
		}

		// Find the editing domain in which to execute the command
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(parameter);
		if (domain == null) {
			// Are we editing an object that is being created new?
			ResourceSet rset = NestedEditingDialogContext.getInstance().getResourceSet();
			if (rset != null) {
				domain = TransactionUtil.getEditingDomain(rset);
			}
		}

		if (domain != null) {
			// Ensure that cancellation of the command doesn't leave a dud on the
			// undo history in the edit menu
			Command wrapper = new GMFtoEMFCommandWrapper(command) {
				@Override
				public void execute() {
					super.execute();

					CommandResult commandResult = command.getCommandResult();
					if ((commandResult != null) && (commandResult.getStatus() != null)
							&& (commandResult.getStatus().getSeverity() >= IStatus.CANCEL)) {
						// The pop-up menu was cancelled
						throw new OperationCanceledException();
					}
				}
			};

			domain.getCommandStack().execute(wrapper);
		}
	}
}
