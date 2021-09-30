/*******************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ernesto Posse - Initial API and implementation
 *******************************************************************************/
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
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.*

/**
 * This class provides extension methods that implement inheritance as described
 * in the UML-RT profile. This provides operations to, for example, obtain all 
 * the ports and parts inside a capsule including those inherited and those 
 * refined.
 * 
 * @author epp
 */
class XTUMLRTVirtualInheritanceExtensions {
	
	/**
	 * Returns the owned behaviour of the given capsule if it has one. Otherwise,
	 * returns any inherited behaviour.
	 */
	static def Behaviour getActualBehaviour(Capsule capsule) {
		var Behaviour behaviour = null
		if(capsule !== null) {
			if(capsule.behaviour !== null)
				behaviour = capsule.behaviour
			else if(capsule.redefines !== null && capsule.redefines instanceof Capsule)
				behaviour = (capsule.redefines as Capsule).actualBehaviour
		}
		behaviour
	}

	/**
	 * Returns the set of all attributed in the structured type element, including those which have been inherited
	 * and those which are redefinitions of inherited attributes.
	 */
	static def Iterable<Attribute> getAllAttributes(StructuredType struct) {
		val allAttributes = new LinkedHashSet<Attribute>()
		if(struct !== null) {
			allAttributes.addAll(struct.classAttributes)
			val parentElement = struct.redefines
			if(parentElement !== null && parentElement instanceof StructuredType) {
				val parent = parentElement as StructuredType
				allAttributes.addAll(parent.allAttributes.filter[!redefines(struct, it)])
			}
		}
		allAttributes
	}

	/**
	 * Returns the set of all capsules in a model or package.
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
		val allPorts = new LinkedHashSet<Port>()
		if(capsule !== null) {
			allPorts.addAll(capsule.RTPorts)
			val parentElement = capsule.redefines
			if(parentElement !== null && parentElement instanceof Capsule) {
				val parent = parentElement as Capsule
				allPorts.addAll( parent.allRTPorts.filter[!redefines(capsule, it)])
			}
		}
		allPorts
	}

	/**
	 * Returns the set of all parts in the capsule, including those which have been inherited
	 * and those which are redefinitions of inherited parts.
	 */
	static def Iterable<CapsulePart> getAllCapsuleParts(Capsule capsule) {
		val allParts = new LinkedHashSet<CapsulePart>()
		if(capsule !== null) {
			allParts.addAll(capsule.capsuleParts)
			val parentElement = capsule.redefines
			if(parentElement !== null && parentElement instanceof Capsule) {
				val parent = parentElement as Capsule
				allParts.addAll( parent.allCapsuleParts.filter[!redefines(capsule, it)])
			}
		}
		allParts
	}

	/**
	 * Returns the set of all connectors in the capsule, including those which have been inherited
	 * and those which are redefinitions of inherited connectors.
	 */
	static def Iterable<Connector> getAllConnectors(Capsule capsule) {
		val allConnectors = new LinkedHashSet<Connector>()
		if(capsule !== null) {
			allConnectors.addAll(capsule.capsuleConnectors)
			val parentElement = capsule.redefines
			if(parentElement !== null && parentElement instanceof Capsule) {
				val parent = parentElement as Capsule
				allConnectors.addAll( parent.allConnectors.filter[!redefines(capsule, it)])
			}
		}
		allConnectors
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
		val allSignals = new LinkedHashSet<Signal>()
		if(protocol !== null) {
			allSignals.addAll(protocol.signals)
			val parentElement = protocol.redefines
			if(parentElement !== null && parentElement instanceof Capsule) {
				val parent = parentElement as Protocol
				allSignals.addAll( parent.allSignals.filter[!redefines(protocol, it)])
			}
		}
		allSignals
	}

	/**
	 * Returns an iterable over the attributes of the given class which redefine some inherited attribute.
	 */
	static def Iterable<Attribute> getAttributeRedefinitions(StructuredType struct) {
		struct.attributes.filter[it.redefines !== null && it.redefines instanceof Attribute]
	}

	/**
	 * Returns an iterable over the ports of the given class which redefine some inherited port.
	 */
	static def Iterable<CapsulePart> getPartRedefinitions(Capsule capsule) {
		capsule.parts.filter[it.redefines !== null && it.redefines instanceof CapsulePart]
	}

	/**
	 * Returns an iterable over the ports of the given class which redefine some inherited port.
	 */
	static def Iterable<Port> getPortRedefinitions(Capsule capsule) {
		capsule.ports.filter[it.redefines !== null && it.redefines instanceof Port]
	}

	/**
	 * Returns an iterable over the connectors of the given class which redefine some inherited connector.
	 */
	static def Iterable<Connector> getConnectorRedefinitions(Capsule capsule) {
		capsule.connectors.filter[it.redefines !== null && it.redefines instanceof Connector]
	}

	/**
	 * Returns an iterable over the signals of the given protocol which redefine some inherited signal.
	 */
	static def Iterable<Signal> getSignalRedefinitions(Protocol protocol) {
		protocol.signals.filter[it.redefines !== null && it.redefines instanceof Signal]
	}

	/**
	 * Returns an iterable over the attributed redefined by the given class.
	 */
	static def Iterable<Attribute> getRedefinedAttributes(StructuredType struct) {
		struct.attributeRedefinitions.map[it.redefines as Attribute]
	}

	/**
	 * Returns an iterable over the ports redefined by the given class.
	 */
	static def Iterable<CapsulePart> getRedefinedParts(Capsule capsule) {
		capsule.partRedefinitions.map[it.redefines as CapsulePart]
	}

	/**
	 * Returns an iterable over the ports redefined by the given class.
	 */
	static def Iterable<Port> getRedefinedPorts(Capsule capsule) {
		capsule.portRedefinitions.map[it.redefines as Port]
	}

	/**
	 * Returns an iterable over the connectors redefined by the given class.
	 */
	static def Iterable<Connector> getRedefinedConnectors(Capsule capsule) {
		capsule.connectorRedefinitions.map[it.redefines as Connector]
	}

	/**
	 * Returns an iterable over the signals redefined by the given protocol.
	 */
	static def Iterable<Signal> getRedefinedSignals(Protocol protocol) {
		protocol.signalRedefinitions.map[it.redefines as Signal]
	}

	static def Iterable<StructuredType> getSupertypes(StructuredType type) {
		type.generalizations?.map[it.getSuper]
	}

	/**
	 * Returns true iff the given attribute is inherited and redefined by the given class.
	 */
	private static dispatch def redefines(StructuredType struct, Attribute attr) {
		struct.redefinedAttributes.exists[it == attr]
	}

	/**
	 * Returns true iff the given port is inherited and redefined by the given class.
	 */
	private static dispatch def redefines(Capsule capsule, Port port) {
		capsule.redefinedPorts.exists[it == port]
	}

	/**
	 * Returns true iff the given port is inherited and redefined by the given class.
	 */
	private static dispatch def redefines(Capsule capsule, CapsulePart part) {
		capsule.redefinedParts.exists[it == part]
	}

	/**
	 * Returns true iff the given connector is inherited and redefined by the given class.
	 */
	private static dispatch def redefines(Capsule capsule, Connector conn) {
		capsule.redefinedConnectors.exists[it == conn]
	}

	/**
	 * Returns true iff the given signal is inherited and redefined by the given protocol.
	 */
	private static dispatch def redefines(Protocol protocol, Signal signal) {
		protocol.redefinedSignals.exists[it == signal]
	}

}
