/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Onder Gurcan <onder.gurcan@cea.fr>
 * Christian W. Damus - bugs 476984, 507552
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.types;

import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.AbstractElementTypeEnumerator;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;

import com.google.common.collect.ImmutableList;

/**
 * Enumeration of UI-specific element types, such as the "creation element types"
 * that are not intended to match existing elements by are used to invoke edit-helper
 * behaviour for specific creation scenarios.
 */
public class UMLRTUIElementTypesEnumerator extends AbstractElementTypeEnumerator implements IUMLRTUIElementTypes {

	/**
	 * Creation element-type for protocol message parameter, with prompt for type.
	 */
	public static final IHintedType PROTOCOL_MESSAGE_PARAMETER_CREATION_WITH_UI = (IHintedType) getElementType(PROTOCOL_MESSAGE_PARAMETER_CREATION_WITH_UI_ID);

	/**
	 * Creation element-type for external or SAP port (according to selected protocol), with prompt for type.
	 */
	public static final IHintedType PORT_CREATION_WITH_UI = (IHintedType) getElementType(PORT_CREATION_WITH_UI_ID);

	/**
	 * Creation element-type for external port, with prompt for type.
	 */
	public static final IHintedType EXTERNAL_PORT_CREATION_WITH_UI = (IHintedType) getElementType(EXTERNAL_PORT_CREATION_WITH_UI_ID);

	/**
	 * Creation element-type for capsule-part, with prompt for type.
	 */
	public static final IHintedType CAPSULE_PART_CREATION_WITH_UI = (IHintedType) getElementType(CAPSULE_PART_CREATION_WITH_UI_ID);

	private static final List<IHintedType> types = ImmutableList.of(
			PROTOCOL_MESSAGE_PARAMETER_CREATION_WITH_UI,
			PORT_CREATION_WITH_UI, EXTERNAL_PORT_CREATION_WITH_UI,
			CAPSULE_PART_CREATION_WITH_UI);

	/**
	 * Returns all of the specific UI element types for UML-RT.
	 * 
	 * @return all of the specific UI element types for UML-RT
	 */
	public static List<IHintedType> getAllUITypes() {
		return types;
	}

}
