/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Services GmbH
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alexandra Buzila (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;

public class UMLRTProtocolMessageChangeTestData extends AbstractUMLRTInputData {

	private static final String SEP = "/"; //$NON-NLS-1$
	private static final String BASE_PATH = "data" + SEP + "protocol-message-change"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String PROTOCOL_MESSAGE_DATA = BASE_PATH + SEP + "protocol-message"; //$NON-NLS-1$
	private static final String PROTOCOL_MESSAGE_WITH_PARAMETER_DATA = BASE_PATH + SEP
			+ "protocol-message-with-parameter";
	private static final String PROTOCOL_MESSAGE_PARAMETER_DATA = BASE_PATH + SEP + "message-parameter";

	public Resource getAddDeleteProtocolMessageLeft() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_DATA + SEP + "add-delete" + SEP + "left.uml"); //$NON-NLS-1$
	}

	public Resource getAddDeleteProtocolMessageRight() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_DATA + SEP + "add-delete" + SEP + "right.uml"); //$NON-NLS-1$
	}

	public Resource getAddDeleteManyProtocolMessagesLeft() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_DATA + SEP + "add-delete-many" + SEP + "left.uml"); //$NON-NLS-1$
	}

	public Resource getAddDeleteManyProtocolMessagesRight() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_DATA + SEP + "add-delete-many" + SEP + "right.uml"); //$NON-NLS-1$
	}

	public Resource getRenameProtocolMessageWithoutParameterLeft() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_DATA + SEP + "rename" + SEP + "left.uml");
	}

	public Resource getRenameProtocolMessageWithoutParameterRight() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_DATA + SEP + "rename" + SEP + "right.uml");
	}

	public Resource getAddDeleteProtocolMessageWithParameterLeft() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_WITH_PARAMETER_DATA + SEP + "add-delete" + SEP + "left.uml"); //$NON-NLS-1$
	}

	public Resource getAddDeleteProtocolMessageWithParameterRight() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_WITH_PARAMETER_DATA + SEP + "add-delete" + SEP + "right.uml"); //$NON-NLS-1$
	}

	public Resource getRenameProtocolMessageWithParameterLeft() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_WITH_PARAMETER_DATA + SEP + "rename" + SEP + "left.uml");
	}

	public Resource getRenameProtocolMessageWithParameterRight() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_WITH_PARAMETER_DATA + SEP + "rename" + SEP + "right.uml");
	}

	public Resource getAddDeleteProtocolMessageParameterLeft() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_PARAMETER_DATA + SEP + "add-delete" + SEP + "left.uml"); //$NON-NLS-1$
	}

	public Resource getAddDeleteProtocolMessageParameterRight() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_PARAMETER_DATA + SEP + "add-delete" + SEP + "right.uml"); //$NON-NLS-1$
	}

	public Resource getRenameProtocolParameterLeft() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_PARAMETER_DATA + SEP + "rename" + SEP + "left.uml");
	}

	public Resource getRenameProtocolParameterRight() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_PARAMETER_DATA + SEP + "rename" + SEP + "right.uml");
	}

	public Resource getAddProtocolMessageDifferentInterfacesOrigin() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_DATA + SEP + "multiple-1" + SEP + "origin.uml");
	}

	public Resource getAddProtocolMessageDifferentInterfacesLeft() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_DATA + SEP + "multiple-1" + SEP + "left.uml");
	}

	public Resource getAddProtocolMessageDifferentInterfacesRight() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_DATA + SEP + "multiple-1" + SEP + "right.uml");
	}

	public Resource getAddProtocolMessageSameInterfacesOrigin() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_DATA + SEP + "multiple-2" + SEP + "origin.uml");
	}

	public Resource getAddProtocolMessageSameInterfacesLeft() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_DATA + SEP + "multiple-2" + SEP + "left.uml");
	}

	public Resource getAddProtocolMessageSameInterfacesRight() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_DATA + SEP + "multiple-2" + SEP + "right.uml");
	}

	public Resource getAddParameterSameProtocolMessageOrigin() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_PARAMETER_DATA + SEP + "multiple-1" + SEP + "origin.uml");
	}

	public Resource getAddParameterSameProtocolMessageLeft() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_PARAMETER_DATA + SEP + "multiple-1" + SEP + "left.uml");
	}

	public Resource getAddParameterSameProtocolMessageRight() throws IOException {
		return loadFromClassLoader(PROTOCOL_MESSAGE_PARAMETER_DATA + SEP + "multiple-1" + SEP + "right.uml");
	}

	public Resource getEmptyModel() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "empty.uml");
	}

	public Resource getModelWithProtocol() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocol.uml");
	}

	public Resource getProtocolWithProtocolMessage() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocolmessage.uml");
	}

	public Resource getProtocolWithTwoProtocolMessages() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocolmessages.uml");
	}

	public Resource getProtocolWithProtocolMessageRename() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocolmessage_rename.uml");
	}

	public Resource getProtocolWithProtocolMessageRename2() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocolmessage_rename2.uml");
	}

	public Resource getProtocolMessageWithParameter() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocolmessageparameter.uml");
	}

	public Resource getProtocolMessageWithParameterRename() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocolmessageparameter_rename.uml");
	}

	public Resource getProtocolMessageSameName() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocolmessage_same_name.uml");
	}

	public Resource getProtocolMessage2() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocolmessage2.uml");
	}

	public Resource getProtocolMessage2Rename() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocolmessage2_rename.uml");
	}

	public Resource getProtocolMessages1and2() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocolmessage1and2.uml");
	}

	public Resource getProtocolRename() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocol_rename.uml");
	}

	public Resource getProtocolRenameWithProtocolMessage() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocol_rename_with_protocol_message.uml");
	}

	public Resource getProtocolMessages1Renameand2() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocolmessage1renameand2.uml");
	}

	public Resource getProtocolMessages1and2Rename() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocolmessage1and2rename.uml");
	}

	public Resource getProtocolMessageRenameWithParameter() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + "multiple" + SEP + "protocolmessage_rename_withparameter.uml");
	}
}
