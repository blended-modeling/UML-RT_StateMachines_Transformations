/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 476126
 *  Christian W. Damus - bug 476984
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.utils;


/**
 * This Class regroups all the Constants related to the Real Time context.
 */
public interface IRealTimeConstants {

	/** The name of the custom property to type a Port with a protocol . */
	public static final String PROTOCOL_TYPE = "type";// $NON-NLS-1$

	/** The name of the custom property KIND to define RTPort and CapsulePart kind. */
	public final static String KIND = "kind";// $NON-NLS-1$

	/** The name of the custom property multiplicity not directly related to the UML Multiplicity */
	public final static String CAPSULE_PART_MULTIPLICITY = "RTmultiplicity"; // $NON-NLS-1$

	/** The name of Parameter used in the Request to retrieve the Operation linked to the Call Event */
	public final static String CALL_EVENT_OPERATION_PARAMETER_NAME = "OwnedOperation"; // $NON-NLS-1$

	/** name of the custom property on Packages to select the default language. */
	String PACKAGE_LANGUAGE = "language"; // $NON-NLS-1$

	/** The name of the replication property for capsule part and port */
	public final static String PROPERTY_REPLICATION = "replication"; // $NON-NLS-0$

}
