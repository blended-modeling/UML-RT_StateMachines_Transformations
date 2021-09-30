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
package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.types;

/**
 * Constants for UI-specific element types, such as the IDs for "creation element types"
 * that are not intended to match existing elements by are used to invoke edit-helper
 * behaviour for specific creation scenarios.
 */
public interface IUMLRTUIElementTypes {

	/**
	 * Creation element-type ID for protocol message parameter, with prompt for type.
	 */
	String PROTOCOL_MESSAGE_PARAMETER_CREATION_WITH_UI_ID = "org.eclipse.papyrusrt.umlrt.tooling.ui.protocolmsgparametercreationwithui"; //$NON-NLS-1$

	/**
	 * Creation element-type ID for external or SAP port (according to selected protocol), with prompt for type.
	 */
	String PORT_CREATION_WITH_UI_ID = "org.eclipse.papyrusrt.umlrt.tooling.ui.rtportcreationwithui"; //$NON-NLS-1$

	/**
	 * Creation element-type ID for external port, with prompt for type.
	 */
	String EXTERNAL_PORT_CREATION_WITH_UI_ID = "org.eclipse.papyrusrt.umlrt.tooling.diagram.capsulestructurediagram.RTPort_Shape"; //$NON-NLS-1$

	/**
	 * Creation element-type ID for capsule-part, with prompt for type.
	 */
	String CAPSULE_PART_CREATION_WITH_UI_ID = "org.eclipse.papyrusrt.umlrt.tooling.diagram.capsulestructurediagram.CapsulePart_Shape"; //$NON-NLS-1$

}
