/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Ansgar Radermacher  ansgar.radermacher@cea.fr
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.editors;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.papyrus.infra.properties.ui.creation.EditionDialog;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Messages;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;

/**
 * Create a new protocol message, based on XWT definition
 */
public class CreateProtocolMsgDialog extends EditionDialog {

	public static final String CREATE_PROTOCOL_MSG_TITLE = Messages.CreateProtocolMsgDialog_CreateProtocolMsgTitle;

	Operation protocolMsg;

	public CreateProtocolMsgDialog(Shell parent, Package protocolPkg, boolean conjugated) {
		super(parent);
		try {
			setTitle(CREATE_PROTOCOL_MSG_TITLE);

			Collaboration protocol = (Collaboration) protocolPkg.getPackagedElements().get(0);

			IHintedType hintedType = conjugated ? UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_OUT : UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_IN;
			ICommand command = ElementEditServiceUtils.getCreateChildCommandWithContext(protocol, hintedType);
			command.execute(null, null);

			CommandResult result = command.getCommandResult();
			protocolMsg = (Operation) result.getReturnValue();

			setViewData(UmlRTViewConstants.UML_RT, UmlRTViewConstants.SINGLE_RT_PROTOCOL_MSG_VIEW);
			setInput(protocolMsg);
		} catch (ExecutionException e) {
			Activator.log.error(e);
		}
	}
}
