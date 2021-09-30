/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Onder Gurcan <onder.gurcan@cea.fr> (CEA LIST) - Initial API and implementation
 *   Christian W. Damus - bugs 476984, 513793
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types;

import java.util.Arrays;
import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.AbstractElementTypeEnumerator;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;

/**
 * Static list of UML-RT specific element types
 */
public class UMLRTElementTypesEnumerator extends AbstractElementTypeEnumerator implements IUMLRTElementTypes {

	public static final IHintedType RT_MESSAGE_SET = (IHintedType) getElementType(RT_MESSAGE_SET_ID);

	public static final IHintedType PROTOCOL_CONTAINER = (IHintedType) getElementType(PROTOCOL_CONTAINER_ID);

	public static final IHintedType PROTOCOL = (IHintedType) getElementType(PROTOCOL_ID);

	public static final IHintedType CAPSULE = (IHintedType) getElementType(CAPSULE_ID);

	public static final IHintedType CAPSULE_PART = (IHintedType) getElementType(CAPSULE_PART_ID);

	public static final IHintedType RT_PORT = (IHintedType) getElementType(RT_PORT_ID);

	public static final IHintedType RT_CONNECTOR = (IHintedType) getElementType(RT_CONNECTOR_ID);

	public static final IHintedType RT_EXCLUDED_ELEMENT = (IHintedType) getElementType(RT_EXCLUDED_ELEMENT_ID);

	public static final IHintedType PROTOCOL_MESSAGE = (IHintedType) getElementType(PROTOCOL_MESSAGE_ID);

	public static final IHintedType PROTOCOL_MESSAGE_IN = (IHintedType) getElementType(PROTOCOL_MESSAGE_IN_ID);

	public static final IHintedType PROTOCOL_MESSAGE_OUT = (IHintedType) getElementType(PROTOCOL_MESSAGE_OUT_ID);

	public static final IHintedType PROTOCOL_MESSAGE_INOUT = (IHintedType) getElementType(PROTOCOL_MESSAGE_INOUT_ID);

	public static final IHintedType PROTOCOL_MESSAGE_PARAMETER = (IHintedType) getElementType(PROTOCOL_MESSAGE_PARAMETER_ID);

	public static final IHintedType RT_STATE_MACHINE = (IHintedType) getElementType(RT_STATE_MACHINE_ID);

	public static final IHintedType RT_REGION = (IHintedType) getElementType(RT_REGION_ID);

	public static final IHintedType RT_STATE = (IHintedType) getElementType(RT_STATE_ID);

	public static final IHintedType RT_PSEUDO_STATE = (IHintedType) getElementType(RT_PSEUDO_STATE_ID);

	public static final IHintedType RT_PSEUDO_STATE_CHOICE = (IHintedType) getElementType(RT_PSEUDO_STATE_CHOICE_ID);
	public static final IHintedType RT_PSEUDO_STATE_DEEP_HISTORY = (IHintedType) getElementType(RT_PSEUDO_STATE_DEEP_HISTORY_ID);
	public static final IHintedType RT_PSEUDO_STATE_ENTRY_POINT = (IHintedType) getElementType(RT_PSEUDO_STATE_ENTRY_POINT_ID);
	public static final IHintedType RT_PSEUDO_STATE_EXIT_POINT = (IHintedType) getElementType(RT_PSEUDO_STATE_EXIT_POINT_ID);
	public static final IHintedType RT_PSEUDO_STATE_FORK = (IHintedType) getElementType(RT_PSEUDO_STATE_FORK_ID);
	public static final IHintedType RT_PSEUDO_STATE_INITIAL = (IHintedType) getElementType(RT_PSEUDO_STATE_INITIAL_ID);
	public static final IHintedType RT_PSEUDO_STATE_JOIN = (IHintedType) getElementType(RT_PSEUDO_STATE_JOIN_ID);
	public static final IHintedType RT_PSEUDO_STATE_JUNCTION = (IHintedType) getElementType(RT_PSEUDO_STATE_JUNCTION_ID);
	public static final IHintedType RT_PSEUDO_STATE_SHALLOW_HISTORY = (IHintedType) getElementType(RT_PSEUDO_STATE_SHALLOW_HISTORY_ID);
	public static final IHintedType RT_PSEUDO_STATE_TERMINATE = (IHintedType) getElementType(RT_PSEUDO_STATE_TERMINATE_ID);

	public static final IHintedType TRANSITION_INTERNAL = (IHintedType) getElementType(TRANSITION_INTERNAL_ID);
	public static final IHintedType TRIGGER = (IHintedType) getElementType(TRIGGER_ID);

	private static final List<IHintedType> rtTypes = Arrays.asList(RT_MESSAGE_SET, PROTOCOL_CONTAINER, PROTOCOL, CAPSULE, CAPSULE_PART, RT_PORT, RT_CONNECTOR, RT_EXCLUDED_ELEMENT, PROTOCOL_MESSAGE_IN, PROTOCOL_MESSAGE_OUT, PROTOCOL_MESSAGE_INOUT,
			PROTOCOL_MESSAGE, RT_STATE_MACHINE, RT_REGION, RT_STATE, RT_PSEUDO_STATE_CHOICE, RT_PSEUDO_STATE_DEEP_HISTORY, RT_PSEUDO_STATE_ENTRY_POINT, RT_PSEUDO_STATE_EXIT_POINT, RT_PSEUDO_STATE_FORK, RT_PSEUDO_STATE_INITIAL,
			RT_PSEUDO_STATE_JOIN, RT_PSEUDO_STATE_JUNCTION, RT_PSEUDO_STATE_SHALLOW_HISTORY, RT_PSEUDO_STATE_TERMINATE, RT_PSEUDO_STATE, TRANSITION_INTERNAL, TRIGGER);

	/**
	 * Returns all the specific semantic element types for UML-RT
	 * 
	 * @return all the specific semantic element types for UML-RT
	 */
	public static List<IHintedType> getAllRTTypes() {
		return rtTypes;
	}

}
