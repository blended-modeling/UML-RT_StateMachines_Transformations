/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Protocol;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Internal protocol for collaborations.
 */
public interface InternalUMLRTCollaboration extends InternalUMLRTClassifier, Collaboration {
	default Stream<Interface> rtMessageSets() {
		Package protocolContainer = (UMLUtil.getStereotypeApplication(this, Protocol.class) == null)
				? null
				: getPackage();

		return Optional.ofNullable(protocolContainer)
				.map(package_ -> package_.getOwnedTypes().stream()
						.filter(Interface.class::isInstance).map(Interface.class::cast)
						.filter(interface_ -> (interface_.getName() != null) && interface_.getName().startsWith(getName())))
				.orElseGet(Stream::empty);
	}
}
