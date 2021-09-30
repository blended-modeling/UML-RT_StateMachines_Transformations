/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Onder Gurcan <onder.gurcan@cea.fr>
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

	private static final List<IHintedType> rtTypes = Arrays.asList(RT_MESSAGE_SET, PROTOCOL_CONTAINER, PROTOCOL, CAPSULE, CAPSULE_PART, RT_PORT, RT_CONNECTOR, RT_EXCLUDED_ELEMENT, PROTOCOL_MESSAGE_IN, PROTOCOL_MESSAGE_OUT, PROTOCOL_MESSAGE_INOUT, PROTOCOL_MESSAGE);

	/**
	 * Returns all the specific semantic element types for UML-RT
	 * 
	 * @return all the specific semantic element types for UML-RT
	 */
	public static List<IHintedType> getAllRTTypes() {
		return rtTypes;
	}

}
