/*******************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alexandra Buzila - initial API and implementation
 *******************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * Input data for {@link UMLRTProtocolMessageParameterChangeTest}.
 * 
 * @author Alexandra Buzila
 *
 */
public class UMLRTProtocolMessageParameterChangeTestData extends AbstractUMLRTInputData {

	private static final String SEP = "/"; //$NON-NLS-1$
	private static final String BASE_PATH = "data" + SEP + "protocol-message-parameter-change"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String MULTIPLE = BASE_PATH + SEP + "multiple" + SEP;

	/** Constructor. */
	public UMLRTProtocolMessageParameterChangeTestData() {
	}

	public Resource getEmptyProtocolMessage() throws IOException {
		return loadFromClassLoader(MULTIPLE + "empty_protocol_messages.uml");
	}

	public Resource getProtocolMessageWithParameter() throws IOException {
		return loadFromClassLoader(MULTIPLE + "add_param1_outPM1.uml");
	}

	public Resource getParameterRename() throws IOException {
		return loadFromClassLoader(MULTIPLE + "rename_param1_outPM1.uml");
	}

	public Resource getParameterRename2() throws IOException {
		return loadFromClassLoader(MULTIPLE + "rename2_param1_outPM1.uml");
	}

	public Resource getParameterTypeChange() throws IOException {
		return loadFromClassLoader(MULTIPLE + "typeChange_param1_outPM1.uml");
	}

	public Resource getParameterTypeChange2() throws IOException {
		return loadFromClassLoader(MULTIPLE + "typeChange2_param1_outPM1.uml");
	}

	public Resource getDeleteTypeClass3() throws IOException {
		return loadFromClassLoader(MULTIPLE + "delete_type_class3.uml");
	}

	public Resource getDeleteProtocolMessage() throws IOException {
		return loadFromClassLoader(MULTIPLE + "delete_protocol_message.uml");
	}
}
