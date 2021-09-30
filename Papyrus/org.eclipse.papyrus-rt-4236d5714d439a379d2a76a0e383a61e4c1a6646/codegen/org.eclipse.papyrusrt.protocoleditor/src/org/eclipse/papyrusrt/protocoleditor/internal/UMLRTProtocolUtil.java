/*******************************************************************************
 * Copyright (c) 2014 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.protocoleditor.internal;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.umlrt.UMLRealTime.RTMessageKind;
import org.eclipse.papyrus.umlrt.UMLRealTime.RTMessageSet;
import org.eclipse.papyrusrt.codegen.utils.UMLRealTimeProfileUtil;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Utility class for UMLRT protocol
 * 
 * @author ysroh
 *
 */
public class UMLRTProtocolUtil {
	/**
	 * Create or repair protocol container elements. Child element name must
	 * follow the name of the protocol container.
	 * 
	 * @param container
	 *            protocol container
	 * @param oldName
	 *            name of the old protocol in case this is rename
	 * @param newName
	 *            name of the protocol
	 */
	public static void createProtocolContainerContent(Package pContainer,
			String oldName, String newName) {

		// Setup protocol
		PackageableElement protocol = (Collaboration) pContainer
				.getPackagedElement(oldName, false,
						UMLPackage.Literals.COLLABORATION, false);

		if (protocol == null) {
			protocol = pContainer.getPackagedElement(newName, false,
					UMLPackage.Literals.COLLABORATION, true);
		} else {
			protocol.setName(newName);
		}
		applyStereoType(protocol, "UMLRealTime::Protocol");

		// setup message sets
		addRtMessageSet(pContainer, protocol, oldName, newName,
				RTMessageKind.IN);
		addRtMessageSet(pContainer, protocol,
				getMessageSetName(oldName, RTMessageKind.OUT),
				getMessageSetName(newName, RTMessageKind.OUT),
				RTMessageKind.OUT);
		addRtMessageSet(pContainer, protocol,
				getMessageSetName(oldName, RTMessageKind.IN_OUT),
				getMessageSetName(newName, RTMessageKind.IN_OUT),
				RTMessageKind.IN_OUT);

		// Setup any receive event
		pContainer.getPackagedElement("*", false,
				UMLPackage.Literals.ANY_RECEIVE_EVENT, true);

		pContainer.setName(newName);

	}

	private static Interface addRtMessageSet(Package container,
			PackageableElement protocol, String oldName, String newName,
			RTMessageKind kind) {

		Interface messageSet = getMessageSet(container, kind);
		if (messageSet != null) {
			messageSet.setName(newName);
			return messageSet;
		}
		messageSet = (Interface) container.getPackagedElement(oldName, false,
				UMLPackage.Literals.INTERFACE, false);
		if (messageSet == null) {
			messageSet = (Interface) container.getPackagedElement(newName,
					false, UMLPackage.Literals.INTERFACE, true);
		} else {
			messageSet.setName(newName);
		}
		applyStereoType(messageSet, "UMLRealTime::RTMessageSet");
		RTMessageSet rtMessageSet = UMLRealTimeProfileUtil
				.getRTMessageSet(messageSet);
		rtMessageSet.setRtMsgKind(kind);

		// setup relations
		if (kind.equals(RTMessageKind.IN)) {
			addProtocolDependency(container,
					"ProtocolRealizesIncomingInterface",
					UMLPackage.Literals.REALIZATION, protocol, messageSet);
		} else if (kind.equals(RTMessageKind.OUT)) {
			addProtocolDependency(container, "ProtocolUsesOutgoingInterface",
					UMLPackage.Literals.USAGE, protocol, messageSet);
		} else if (kind.equals(RTMessageKind.IN_OUT)) {
			addProtocolDependency(container, "ProtocolRealizesSymInterface",
					UMLPackage.Literals.REALIZATION, protocol, messageSet);
			addProtocolDependency(container, "ProtocolUsesSymInterface",
					UMLPackage.Literals.USAGE, protocol, messageSet);
		}

		return messageSet;
	}

	/**
	 * Create or modify existing dependency to add dependency between client and
	 * supplier
	 * 
	 * @param container
	 * @param name
	 * @param eClass
	 * @param client
	 * @param supplier
	 */
	private static void addProtocolDependency(Package container, String name,
			EClass eClass, NamedElement client, NamedElement supplier) {
		for (Element e : container.getOwnedElements()) {
			if (e.eClass().equals(eClass)) {
				Dependency d = (Dependency) e;
				if (d.getClients().contains(client)
						&& d.getSuppliers().contains(supplier)) {
					d.setName(name);
					return;
				}
			}
		}
		Dependency dependency = (Dependency) container.getPackagedElement(name,
				false, eClass, true);
		EList<NamedElement> clients = dependency.getClients();
		EList<NamedElement> suppliers = dependency.getSuppliers();

		clients.clear();
		clients.add(client);

		suppliers.clear();
		suppliers.add(supplier);
	}

	/**
	 * Add stereotype with given qualified name of the stereotype if not already
	 * applied.
	 * 
	 * @param element
	 * @param qualifiedName
	 */
	public static void applyStereoType(Element element, String qualifiedName) {
		Stereotype protocolStereotype = element
				.getAppliedStereotype(qualifiedName);
		if (protocolStereotype == null) {
			protocolStereotype = element.getApplicableStereotype(qualifiedName);
			element.applyStereotype(protocolStereotype);
		}
	}

	/**
	 * Queries the protocol container
	 * 
	 * @param eObject
	 * @return
	 */
	public static Package getProtocolContainer(EObject eObject) {
		if (eObject == null) {
			return null;
		}
		if (eObject instanceof Element
				&& UMLRealTimeProfileUtil
						.isProtocolContainer((Element) eObject)) {
			return (Package) eObject;
		}

		return getProtocolContainer(eObject.eContainer());
	}

	/**
	 * Queries message set
	 * 
	 * @param protocolContainer
	 * @param kind
	 * @return
	 */
	public static Interface getMessageSet(Package protocolContainer,
			RTMessageKind kind) {
		String protocolName = protocolContainer.getName();
		Collaboration protocol = (Collaboration) protocolContainer
				.getPackagedElement(protocolName, false,
						UMLPackage.Literals.COLLABORATION, false);
		if (protocol == null) {
			return null;
		}
		if (kind.equals(RTMessageKind.IN)) {
			EList<Interface> provides = getProvideds(protocol);
			if (!provides.isEmpty()) {
				return provides.get(0);
			}
		} else if (kind.equals(RTMessageKind.OUT)) {
			EList<Interface> requires = getRequireds(protocol);
			if (!requires.isEmpty()) {
				return requires.get(0);
			}
		} else {
			EList<Interface> provides = getProvideds(protocol);
			EList<Interface> requires = getRequireds(protocol);
			for (Interface p : provides) {
				if (requires.contains(p)) {
					return p;
				}
			}
		}
		return null;
	}

	private static String getMessageSetName(String protocolName,
			RTMessageKind kind) {
		if (kind.equals(RTMessageKind.OUT)) {
			return protocolName + "~";
		} else if (kind.equals(RTMessageKind.IN_OUT)) {
			return protocolName + "Sym";
		}
		return protocolName;
	}

	public static EList<Interface> getProvideds(Collaboration source) {
		EList<Interface> provideds = new BasicEList<Interface>();
		for (DirectedRelationship directedRelation : ((Collaboration) source)
				.getSourceDirectedRelationships()) {
			if (directedRelation instanceof Realization) {
				EList<NamedElement> suppliers = ((Realization) directedRelation)
						.getSuppliers();
				if (suppliers.size() > 0) {
					NamedElement supplier = suppliers.get(0);
					if (supplier instanceof Interface) {
						provideds.add((Interface) supplier);
					}
				}
			}
		}
		return provideds;
	}

	public static EList<Interface> getRequireds(Collaboration source) {
		return ((Collaboration) source).getUsedInterfaces();
	}
}
