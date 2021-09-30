/*****************************************************************************
 * Copyright (c) 2017 Zeligsoft and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ernesto Posse - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.util

import java.util.LinkedHashSet
import org.eclipse.papyrusrt.xtumlrt.common.Attribute
import org.eclipse.papyrusrt.xtumlrt.common.BaseContainer
import org.eclipse.papyrusrt.xtumlrt.common.Behaviour
import org.eclipse.papyrusrt.xtumlrt.common.Capsule
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart
import org.eclipse.papyrusrt.xtumlrt.common.Connector
import org.eclipse.papyrusrt.xtumlrt.common.Port
import org.eclipse.papyrusrt.xtumlrt.common.Protocol
import org.eclipse.papyrusrt.xtumlrt.common.ProtocolContainer
import org.eclipse.papyrusrt.xtumlrt.common.Signal
import org.eclipse.papyrusrt.xtumlrt.common.StructuredType
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTVirtualInheritanceExtensions
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.*


/**
 * This class is intended to replace the extension methods from 
 * {@link XTUMLRTVirtualInheritanceExtensions}.
 * 
 * <p>The introduction of the new UML-RT implementation in 
 * {@link org.eclipse.papyrusrt.umlrt.uml} already accounts for inheritance,
 * which means that accessing the owned members of a 
 * {@link org.eclipse.uml2.uml.Namespace}, or more precisely of a
 * {@link org.eclipse.papyrusrt.umlrt.uml.internal.impl#InternalUMLRTNamespace}
 * with {@link org.eclipse.uml2.uml.Namespace#getOwnedMembers} will return not 
 * only the directly owned members but also those inherited, or more precisely, it
 * will return "virtual redefinitions" (aka "shadows") of the inherited members.
 * 
 * <p>Since the translator from UML to XtUML-RT uses 
 * {@link org.eclipse.uml2.uml.Namespace#getOwnedMembers} when translating each
 * element (as well as 
 * {@link org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil#getUMLRTContents})
 * then the resulting XtUML-RT model elements are "flattened", i.e. they already 
 * include all the inherited elements. The consequence of this is that the
 * "virtual inheritance" extension methods from 
 * {@link XTUMLRTVirtualInheritanceExtensions} are redundant, and may even 
 * lead to incorrect results if certain elements created during code generation
 * do not have their "refines" reference correctly set, as it happened in Bug 
 * 516446, or if a client access an inherited element which is not a "virtual 
 * redefinition" but the "real" element in a parent classifier. So this class is 
 * intended to override all the {@code getAll*} methods from its superclass to 
 * directly return the corresponding list of members of the relevant XtUML-RT, 
 * since they should already have all inherited elements.
 * 
 * <p>As a result, most methods here are trivial delegation methods, but they
 * are provided nevertheless to support the API that current clients of
 * {@link XTUMLRTVirtualInheritanceExtensions} use. Eventually the code
 * generator should replace this API and use directly the API from the new
 * UML-RT implementation.
 * 
 * @author epp
 */
class XTUMLRTExtensions extends XTUMLRTVirtualInheritanceExtensions {

	
	/**
	 * Returns the owned behaviour of the given capsule if it has one. Otherwise,
	 * returns any inherited behaviour.
	 */
	static def Behaviour getActualBehaviour(Capsule capsule) {
		capsule.behaviour
	}

	/**
	 * Returns the set of all attributed in the structured type element, including those which have been inherited
	 * and those which are redefinitions of inherited attributes.
	 */
	static def Iterable<Attribute> getAllAttributes(StructuredType struct) {
		struct.classAttributes
	}

	/**
	 * Returns the set of all capsules in a model or package, recursively.
	 */
	static def Iterable<Capsule> getAllCapsules(BaseContainer packge) {
		val set = new LinkedHashSet<Capsule>()
		set.addAll(packge.entities.filter(Capsule))
		for (pkg : packge.packages) {
			set.addAll(pkg.allCapsules)
		}
		set
	}

	/**
	 * Returns the set of all ports in the class, including those which have been inherited
	 * and those which are redefinitions of inherited ports.
	 */
	static def Iterable<Port> getAllRTPorts(Capsule capsule) {
		capsule.RTPorts
	}

	/**
	 * Returns the set of all parts in the capsule, including those which have been inherited
	 * and those which are redefinitions of inherited parts.
	 */
	static def Iterable<CapsulePart> getAllCapsuleParts(Capsule capsule) {
		capsule.capsuleParts
	}

	/**
	 * Returns the set of all connectors in the capsule, including those which have been inherited
	 * and those which are redefinitions of inherited connectors.
	 */
	static def Iterable<Connector> getAllConnectors(Capsule capsule) {
		capsule.capsuleConnectors
	}

	/**
	 * Returns the set of all protocols in a model.
	 */
	static def Iterable<Protocol> getAllProtocols(ProtocolContainer packge) {
		val set = new LinkedHashSet<Protocol>()
		set.addAll(packge.protocols)
		for (pkg : packge.packages) {
			set.addAll(pkg.allProtocols)
		}
		set
	}

	/**
	 * Returns the set of all signals in the protocol, including those which have been inherited
	 * and those which are redefinitions of inherited signals.
	 */
	static def Iterable<Signal> getAllSignals(Protocol protocol) {
		protocol.signals
	}
	
}