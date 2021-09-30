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

import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

public class UmlRTViewConstants {
	public static final String UML_RT = "uml-rt"; //$NON-NLS-1$

	public static final IElementType PORT_CREATE_WITH_UI = ElementTypeRegistry.getInstance().getType(
			"org.eclipse.papyrusrt.umlrt.tooling.ui.rtportcreationwithui"); //$NON-NLS-1$

	public static final String SINGLE_RT_PROTOCOL_MSG_VIEW = "Single ProtocolMessage"; //$NON-NLS-1$
}
