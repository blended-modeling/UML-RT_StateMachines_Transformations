/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 476984, 513793
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types;

/**
 * Declaration of all constants for UML-RT Element Types.
 */
public interface IUMLRTElementTypes {

	public static final String PROTOCOL_CONTAINER_ID = "org.eclipse.papyrusrt.umlrt.core.ProtocolContainer"; //$NON-NLS-1$

	public static final String PROTOCOL_ID = "org.eclipse.papyrusrt.umlrt.core.Protocol"; //$NON-NLS-1$

	public static final String RT_MESSAGE_SET_ID = "org.eclipse.papyrusrt.umlrt.core.RTMessageSet"; //$NON-NLS-1$

	public static final String CAPSULE_ID = "org.eclipse.papyrusrt.umlrt.core.Capsule"; //$NON-NLS-1$

	public static final String CAPSULE_PART_ID = "org.eclipse.papyrusrt.umlrt.core.CapsulePart";//$NON-NLS-1$

	public static final String RT_PORT_ID = "org.eclipse.papyrusrt.umlrt.core.RTPort";//$NON-NLS-1$

	public static final String RT_CONNECTOR_ID = "org.eclipse.papyrusrt.umlrt.core.RTConnector";//$NON-NLS-1$

	public static final String RT_EXCLUDED_ELEMENT_ID = "org.eclipse.papyrusrt.umlrt.core.RTRExcludedElement";//$NON-NLS-1$

	public static final String PROTOCOL_MESSAGE_ID = "org.eclipse.papyrusrt.umlrt.core.ProtocolMessage";//$NON-NLS-1$

	public static final String PROTOCOL_MESSAGE_IN_ID = "org.eclipse.papyrusrt.umlrt.core.ProtocolMessageIn";//$NON-NLS-1$

	public static final String PROTOCOL_MESSAGE_OUT_ID = "org.eclipse.papyrusrt.umlrt.core.ProtocolMessageOut";//$NON-NLS-1$

	public static final String PROTOCOL_MESSAGE_INOUT_ID = "org.eclipse.papyrusrt.umlrt.core.ProtocolMessageInOut";//$NON-NLS-1$

	public static final String PROTOCOL_MESSAGE_PARAMETER_ID = "org.eclipse.papyrusrt.umlrt.core.RTMessageParameter";//$NON-NLS-1$


	// specific ports
	public static final String EXTERNAL_BEHAVIOR_PORT_ID = "org.eclipse.papyrusrt.umlrt.core.ExternalBehaviorPort";//$NON-NLS-1$
	public static final String INTERNAL_BEHAVIOR_PORT_ID = "org.eclipse.papyrusrt.umlrt.core.InternalBehaviorPort";//$NON-NLS-1$
	public static final String RELAY_PORT_ID = "org.eclipse.papyrusrt.umlrt.core.RelayPort";//$NON-NLS-1$
	public static final String SERVICE_ACCESS_POINT_ID = "org.eclipse.papyrusrt.umlrt.core.ServiceAccessPoint";//$NON-NLS-1$
	public static final String SERVICE_PROVISION_POINT_ID = "org.eclipse.papyrusrt.umlrt.core.ServiceProvisionPoint";//$NON-NLS-1$

	// state machine additional elements
	public static final String RT_STATE_MACHINE_ID = "org.eclipse.papyrusrt.umlrt.core.RTStateMachine";//$NON-NLS-1$
	public static final String RT_REGION_ID = "org.eclipse.papyrusrt.umlrt.core.RTRegion";//$NON-NLS-1$
	public static final String RT_STATE_ID = "org.eclipse.papyrusrt.umlrt.core.RTState";//$NON-NLS-1$
	public static final String RT_PSEUDO_STATE_ID = "org.eclipse.papyrusrt.umlrt.core.RTPseudoState";//$NON-NLS-1$
	public static final String RT_PSEUDO_STATE_CHOICE_ID = "org.eclipse.papyrusrt.umlrt.core.RTPseudoState_Choice";//$NON-NLS-1$
	public static final String RT_PSEUDO_STATE_DEEP_HISTORY_ID = "org.eclipse.papyrusrt.umlrt.core.RTPseudoState_DeepHistory";//$NON-NLS-1$
	public static final String RT_PSEUDO_STATE_ENTRY_POINT_ID = "org.eclipse.papyrusrt.umlrt.core.RTPseudoState_EntryPoint";//$NON-NLS-1$
	public static final String RT_PSEUDO_STATE_EXIT_POINT_ID = "org.eclipse.papyrusrt.umlrt.core.RTPseudoState_ExitPoint";//$NON-NLS-1$
	public static final String RT_PSEUDO_STATE_FORK_ID = "org.eclipse.papyrusrt.umlrt.core.RTPseudoState_Fork";//$NON-NLS-1$
	public static final String RT_PSEUDO_STATE_INITIAL_ID = "org.eclipse.papyrusrt.umlrt.core.RTPseudoState_Initial";//$NON-NLS-1$
	public static final String RT_PSEUDO_STATE_JOIN_ID = "org.eclipse.papyrusrt.umlrt.core.RTPseudoState_Join";//$NON-NLS-1$
	public static final String RT_PSEUDO_STATE_JUNCTION_ID = "org.eclipse.papyrusrt.umlrt.core.RTPseudoState_Junction";//$NON-NLS-1$
	public static final String RT_PSEUDO_STATE_SHALLOW_HISTORY_ID = "org.eclipse.papyrusrt.umlrt.core.RTPseudoState_ShallowHistory";//$NON-NLS-1$
	public static final String RT_PSEUDO_STATE_TERMINATE_ID = "org.eclipse.papyrusrt.umlrt.core.RTPseudoState_Terminate";//$NON-NLS-1$

	public static final String TRANSITION_INTERNAL_ID = "org.eclipse.papyrusrt.umlrt.core.TransitionInternal";//$NON-NLS-1$
	public static final String TRIGGER_ID = "org.eclipse.papyrusrt.umlrt.core.Trigger"; //$NON-NLS-1$

}
