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
 *   Christian W. Damus - bugs 467545, 510189
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguageService;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageSet;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Utility class for UMLRT::Protocols
 */
public class ProtocolUtils {

	/**
	 * Name of "internal" package, containing for instance the base protocol
	 */
	public static final String INTERNAL = "Internal"; //$NON-NLS-1$

	/**
	 * NAME of base protocol
	 */
	public static final String UMLRT_BASE_COMM_PROTOCOL = "UMLRTBaseCommProtocol"; //$NON-NLS-1$

	/**
	 * Returns the package that corresponds to the protocol container. There should be a check here for the applied stereotype on the package.
	 *
	 * @param protocol
	 *            the collaboration for which the protocol container is searched
	 * @return the collaboration for which the protocol container is searched or <code>null</code> if none is found
	 */
	public static Package getProtocolContainer(Collaboration protocol) {
		return protocol.getNearestPackage();
	}

	/**
	 * Returns <code>true</code> if the context element is a Protocol (Collaboration stereotyped by "protocol")
	 *
	 * @param context
	 *            the eobject to test
	 * @return <code>true</code> if the context element is a Protocol (Collaboration stereotyped by "protocol")
	 */
	public static Boolean isProtocol(EObject context) {
		return ElementTypeUtils.matches(context, IUMLRTElementTypes.PROTOCOL_ID);
	}

	/**
	 * Queries the messages of a {@code protocol} in the given {@code direction}, even
	 * those that are excluded in the {@code protocol} context.
	 * 
	 * @param protocol
	 *            a protocol
	 * @param direction
	 *            the direction of messages to rerieve
	 * @param includeInherited
	 *            whether to retrieve inherited messages, also. If {@code false},
	 *            then only local definitions and redefinitions will be returned
	 */
	public static List<Operation> getRTMessages(Collaboration protocol, RTMessageKind direction, boolean includeInherited) {
		List<Operation> result;

		UMLRTProtocol facade = UMLRTProtocol.getInstance(protocol);
		if (facade == null) {
			result = Collections.emptyList();
		} else {
			Stream<UMLRTProtocolMessage> messages = facade.getMessages(direction, true).stream();
			if (!includeInherited) {
				messages = messages.filter(m -> !m.isVirtualRedefinition());
			}
			result = messages.map(UMLRTProtocolMessage::toUML).collect(Collectors.toList());
		}

		return result;
	}

	/**
	 * Obtains the message-set interface of a {@code protocol} for the given {@code direction}.
	 * 
	 * @param protocol
	 *            a protocol
	 * @param direction
	 *            the message direction for which to get the interface
	 * 
	 * @return the corresponding message-set interface, or {@code null} if the
	 *         {@code protocol} is not a well-formed protocol
	 */
	public static Interface getMessageSet(Collaboration protocol, RTMessageKind direction) {
		Package protocolContainer = getProtocolContainer(protocol);

		if (protocolContainer != null) {
			for (PackageableElement packageableElement : protocolContainer.getPackagedElements()) {
				// look each interface to find the right one with the stereotype message set
				if (packageableElement instanceof Interface) {
					RTMessageSet messageSet = UMLUtil.getStereotypeApplication(packageableElement, RTMessageSet.class);
					if ((messageSet != null) && (messageSet.getRtMsgKind() == direction)) {
						return (Interface) packageableElement;
					}
				}
			}
		}

		return null;
	}

	/**
	 * @param editContext
	 * @return
	 */
	public static Interface getMessageSetIn(Collaboration editContext) {
		return getMessageSet(editContext, RTMessageKind.IN);
	}

	/**
	 * @param editContext
	 * @return
	 */
	public static Interface getMessageSetOut(Collaboration editContext) {
		return getMessageSet(editContext, RTMessageKind.OUT);
	}

	/**
	 * @param editContext
	 * @return
	 */
	public static Interface getMessageSetInOut(Collaboration editContext) {
		return getMessageSet(editContext, RTMessageKind.IN_OUT);
	}

	/**
	 * Get all protocols that are associated with a protocol: baseProtocols and
	 * inherited protocols. The following order is applied: base protocols first,
	 * root super protocol, then more specific protocols
	 *
	 * @param protocol
	 *            a UMLRT protocol (from facade)
	 * @return all protocols.
	 */
	public static List<UMLRTProtocol> getAllProtocols(UMLRTProtocol protocol) {
		List<UMLRTProtocol> allProtocols = new ArrayList<>();
		allProtocols.addAll(protocol.getAncestry());

		Collaboration baseProtocol = getBaseProtocol(protocol.toUML());
		if ((baseProtocol != null) && (protocol != baseProtocol)) {
			allProtocols.add(UMLRTProtocol.getInstance(baseProtocol));
		}
		Collections.reverse(allProtocols);
		return allProtocols;
	}

	/**
	 * Check whether a given protocol candidate is a common protocol in a set of protocols (including inherited protocols)
	 *
	 * @param protocolSet
	 *            a list of protocols for which we examine
	 * @param protocolCandidate
	 *            a candidate for a protocol that may be common
	 * @return true, iff the protocol is a common protocol
	 */
	public static boolean isCommonProtocol(Collection<UMLRTProtocol> protocolSet, UMLRTProtocol protocolCandidate) {
		for (UMLRTProtocol protocol : protocolSet) {
			if (!ProtocolUtils.getAllProtocols(protocol).contains(protocolCandidate)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Obtain the base protocol from the default language framework
	 *
	 * @param anElement
	 *            an arbitrary element from the model
	 * @return the base protocol
	 */
	public static Collaboration getBaseProtocol(Element anElement) {
		if (anElement != null) {
			// get the default language
			IDefaultLanguage language = null;
			try {
				IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, anElement);
				language = service.getActiveDefaultLanguage(anElement);
			} catch (ServiceException e) {
				Activator.log.error(e);
			}
			if (language != null) {
				// We know there's an editing domain if we had a service registry
				// to get the default language service
				TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(anElement);
				// get the base protocol related to the default language
				return language.getBaseProtocol(domain.getResourceSet());
			}
		}
		return null;
	}
}
