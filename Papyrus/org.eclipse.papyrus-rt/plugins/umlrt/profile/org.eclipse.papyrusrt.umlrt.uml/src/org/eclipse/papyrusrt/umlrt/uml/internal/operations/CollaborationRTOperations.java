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

package org.eclipse.papyrusrt.umlrt.uml.internal.operations;

import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageSet;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTCollaboration;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Externalized operations for {@link Collaboration}s
 */
public class CollaborationRTOperations extends UMLUtil {

	// Not meant to be instantiable by clients
	protected CollaborationRTOperations() {
		super();
	}

	public static void rtInherit(InternalUMLRTCollaboration collaboration, InternalUMLRTCollaboration supercollab) {
		// Ensure that the message-sets likewise inherit
		collaboration.rtMessageSets()
				.filter(InternalUMLRTClassifier.class::isInstance)
				.forEach(set -> {
					RTMessageKind kind = getKind(set);
					if (kind != null) {
						Interface super_ = getMessageSet(supercollab, kind);
						if (super_ instanceof InternalUMLRTClassifier) {
							((InternalUMLRTClassifier) set).rtSetAncestor((InternalUMLRTClassifier) super_);
						}
					}
				});
	}

	public static void rtDisinherit(InternalUMLRTCollaboration collaboration, InternalUMLRTCollaboration supercollab) {
		// Ensure that the message-sets likewise disinherit
		collaboration.rtMessageSets()
				.filter(InternalUMLRTClassifier.class::isInstance)
				.forEach(set -> {
					RTMessageSet stereo = getStereotypeApplication(set, RTMessageSet.class);
					if (stereo != null) {
						Interface super_ = getMessageSet(supercollab, stereo.getRtMsgKind());
						if (super_ instanceof InternalUMLRTClassifier) {
							InternalUMLRTClassifier specific = (InternalUMLRTClassifier) set;
							if (specific.rtGetAncestor() == super_) {
								specific.rtSetAncestor(null);
							}
						}
					}
				});
	}

	protected static Interface getMessageSet(InternalUMLRTCollaboration collaboration, RTMessageKind kind) {
		return collaboration.rtMessageSets()
				.filter(set -> getKind(set) == kind)
				.findFirst().orElse(null);
	}

	protected static RTMessageKind getKind(Interface messageSet) {
		RTMessageSet stereo = getStereotypeApplication(messageSet, RTMessageSet.class);
		return (stereo == null) ? null : stereo.getRtMsgKind();
	}
}
