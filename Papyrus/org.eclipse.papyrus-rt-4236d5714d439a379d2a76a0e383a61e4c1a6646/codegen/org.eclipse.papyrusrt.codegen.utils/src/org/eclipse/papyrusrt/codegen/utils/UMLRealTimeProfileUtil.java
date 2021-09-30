/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.eclipse.papyrus.umlrt.UMLRealTime.Capsule;
import org.eclipse.papyrus.umlrt.UMLRealTime.CapsulePart;
import org.eclipse.papyrus.umlrt.UMLRealTime.Protocol;
import org.eclipse.papyrus.umlrt.UMLRealTime.ProtocolContainer;
import org.eclipse.papyrus.umlrt.UMLRealTime.RTConnector;
import org.eclipse.papyrus.umlrt.UMLRealTime.RTMessageSet;
import org.eclipse.papyrus.umlrt.UMLRealTime.RTPort;
import org.eclipse.papyrus.umlrt.UMLRealTime.RTRedefinedElement;
//import org.eclipse.papyrus.umlrt.UMLRealTime.RTRExcludedElement;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.EncapsulatedClassifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

public class UMLRealTimeProfileUtil
{

    private UMLRealTimeProfileUtil()
    {
    }

    public static boolean isCapsule(Element el) {
        Stereotype s = el.getApplicableStereotype("UMLRealTime::Capsule");
        if (s != null) {
            return el.isStereotypeApplied(s);
        }
        return false;
    }

    public static Capsule getCapsule(Element el) {
        Stereotype s = el.getAppliedStereotype("UMLRealTime::Capsule");
        if (s == null) {
            return null;
        }
        return (Capsule) el.getStereotypeApplication(s);
    }

    public static boolean isCapsulePart(Element el) {
        Stereotype s = el.getApplicableStereotype("UMLRealTime::CapsulePart");
        if (s != null) {
            return el.isStereotypeApplied(s);
        }
        return false;
    }

    public static CapsulePart getCapsulePart(Element el) {
        Stereotype s = el.getAppliedStereotype("UMLRealTime::CapsulePart");
        if (s == null) {
            return null;
        }
        return (CapsulePart) el.getStereotypeApplication(s);
    }

    public static boolean isProtocol(Element el) {
        Stereotype s = el.getApplicableStereotype("UMLRealTime::Protocol");
        if (s != null) {
            return el.isStereotypeApplied(s);
        }
        return false;
    }

    public static Protocol getProtocol(Element el) {
        Stereotype s = el.getAppliedStereotype("UMLRealTime::Protocol");
        if (s == null) {
            return null;
        }
        return (Protocol) el.getStereotypeApplication(s);
    }

    public static Collaboration getProtocolCollaboration(Package protocolPackage) {
        if (!isProtocolContainer(protocolPackage)) {
            return null;
        }
        Collaboration collaboration = null;
        for (Element element: protocolPackage.getOwnedElements()) {
            if (element instanceof Collaboration && isProtocol(element)) {
                collaboration = (Collaboration) element;
                break;
            }
        }
        return collaboration;
    }

    public static boolean isRTPort(Element el) {
        Stereotype s = el.getApplicableStereotype("UMLRealTime::RTPort");
        if (s != null) {
            return el.isStereotypeApplied(s);
        }
        return false;
    }

    public static RTPort getRTPort(Element el) {
        Stereotype s = el.getAppliedStereotype("UMLRealTime::RTPort");
        if (s == null) {
            return null;
        }
        return (RTPort) el.getStereotypeApplication(s);
    }

    public static boolean isRTConnector(Connector connector) {
        Stereotype s = connector.getApplicableStereotype("UMLRealTime::RTConnector");
        if (s != null) {
            return connector.isStereotypeApplied(s);
        }
        return false;
    }

    public static RTConnector getRTConnector(Element el) {
        Stereotype s = el.getAppliedStereotype("UMLRealTime::RTConnector");
        if (s == null) {
            return null;
        }
        return (RTConnector) el.getStereotypeApplication(s);
    }

    public static boolean isProtocolContainer(Element el) {
        Stereotype s = el
                .getApplicableStereotype("UMLRealTime::ProtocolContainer");
        if (s != null) {
            return el.isStereotypeApplied(s);
        }
        return false;
    }

    public static ProtocolContainer getProtocolContainer(Element el) {
        Stereotype s = el
                .getAppliedStereotype("UMLRealTime::ProtocolContainer");
        if (s == null) {
            return null;
        }
        return (ProtocolContainer) el.getStereotypeApplication(s);
    }

    public static boolean isRTRedefinedElement(Element el) {
        Stereotype s = el
                .getApplicableStereotype("UMLRealTime::RTRedefinedElement");
        if (s != null) {
            return el.isStereotypeApplied(s);
        }
        return false;
    }

    public static RTRedefinedElement getRTRedefinedElement(Element el) {
        Stereotype s = el
                .getAppliedStereotype("UMLRealTime::RTRedefinedElement");
        if (s == null) {
            return null;
        }
        return (RTRedefinedElement) el.getStereotypeApplication(s);
    }

    public static boolean isRTMessageSet(Element el) {
        Stereotype s = el.getApplicableStereotype("UMLRealTime::RTMessageSet");
        if (s != null) {
            return el.isStereotypeApplied(s);
        }
        return false;
    }

    public static RTMessageSet getRTMessageSet(Element el) {
        Stereotype s = el.getAppliedStereotype("UMLRealTime::RTMessageSet");
        if (s == null) {
            return null;
        }
        return (RTMessageSet) el.getStereotypeApplication(s);
    }

    public static boolean isSignal(Element el) {
        if (el != null && el instanceof Operation) {
            Operation op = (Operation)el;
            Interface iface = op.getInterface();
            return iface != null && isRTMessageSet(iface);
        }
        return false;
    }

    public static Package getProtocol(Port umlPort) {
        Type type = umlPort.getType();
        if (type == null)
            return null;

        Package pkg = type.getPackage();
        if (pkg == null)
            return null;

        return isProtocolContainer(pkg) ? pkg : null;
    }

    /**
     * A filter that produces only Properties that are properly stereotyped as
     * CapsulePart.
     */
    public static Iterable<Property> getCapsuleParts(
            EncapsulatedClassifier classifier) {
        // Ports are put into a sorted list to make sure that the order is
        // stable between
        // different parts of the generator (as well as between invocations).
        final TreeSet<Property> parts = new TreeSet<Property>(
                new UML2Util.NameComparator());
        for (Property attr : classifier.getOwnedAttributes())
            if (isCapsulePart(attr))
                parts.add(attr);
        return parts;
    }

    /**
     * A filter that produces only Ports that are properly stereotyped as RTPort
     * and have a UML-RT protocol.
     */
    public static Iterable<Port> getRTPorts(EncapsulatedClassifier classifier) {
        // Ports are put into a sorted list to make sure that the order is
        // stable between
        // different parts of the generator (as well as between invocations).
        final TreeSet<Port> ports = new TreeSet<Port>(new UML2Util.NameComparator());
        for (Port port : classifier.getOwnedPorts())
            if (isRTPort(port) && getProtocol(port) != null)
                ports.add(port);
        return ports;
    }

    /**
     * Returns the set of all ports in the class, including those which have been inherited
     * and those which are redefinitions of inherited ports.
     */
    public static Iterable<Port> getAllRTPorts(Class klass) {
        final TreeSet<Port> allPorts = new TreeSet<Port>(new UML2Util.NameComparator());
        if (klass != null) {
            for (Port p : getRTPorts(klass)) {
                allPorts.add(p);
            }
            Class parent = getBaseRTCapsule(klass);
            if (parent != null) {
                for (Port p : getAllRTPorts(parent)) {
                    if (!redefines(klass, p)) {
                        allPorts.add(p);
                    }
                }
            }
        }
        return allPorts;
    }

    /**
     * A filter that produces only Ports that are properly stereotyped as RTPort
     * and have a UML-RT protocol.
     */
    public static Iterable<Connector> getRTConnectors(EncapsulatedClassifier classifier) {
        // Connectors are put into a sorted list to make sure that the order is
        // stable between
        // different parts of the generator (as well as between invocations).
        final TreeSet<Connector> connectors = new TreeSet<Connector>(new UML2Util.NameComparator());
        for (Connector connector : classifier.getOwnedConnectors())
            if (isRTConnector(connector))
                connectors.add(connector);
        return connectors;
    }

    /**
     * Returns the set of all ports in the class, including those which have been inherited
     * and those which are redefinitions of inherited ports.
     */
    public static Iterable<Connector> getAllRTConnectors(Class klass) {
        final TreeSet<Connector> allConnectors = new TreeSet<Connector>(new UML2Util.NameComparator());
        if (klass != null) {
            for (Connector c : getRTConnectors(klass)) {
                allConnectors.add(c);
            }
            Class parent = getBaseRTCapsule(klass);
            if (parent != null) {
                for (Connector c : getAllRTConnectors(parent)) {
                    if (!redefines(klass, c)) {
                        allConnectors.add(c);
                    }
                }
            }
        }
        return allConnectors;
    }

    private static Class getBaseRTCapsule(Class klass) {
        if (klass != null && !klass.getSuperClasses().isEmpty()) {
            for (Class kls : klass.getSuperClasses()) {
                if (isCapsule(kls))
                    return kls;
            }
        }
        return null;
    }

    /**
     * Returns true iff the given port is inherited and redefined by the given class.
     */
    private static boolean redefines(Class klass, Port port) {
        if (port == null) return false;
        for (Port p : getRedefinedPorts(klass)) {
            if (p == port || port.equals(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true iff the given port is inherited and redefined by the given class.
     */
    private static boolean redefines(Class klass, Connector connector) {
        if (connector == null) return false;
        for (Connector c : getRedefinedConnectors(klass)) {
            if (c == connector || connector.equals(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an iterable over the ports redefined by the given class.
     */
    private static Iterable<Port> getRedefinedPorts(Class klass) {
        List<Port> ports = new ArrayList<Port>();
        for (Port p : getPortRedefinitions(klass)) {
            Port redefined = p.getRedefinedPorts().get(0); // Assume a port redefines only one port.
            ports.add(redefined);
        }
        return ports;
    }

    /**
     * Returns an iterable over the ports of the given class which redefine some inherited port.
     */
    private static Iterable<Port> getPortRedefinitions(Class klass) {
        List<Port> ports = new ArrayList<Port>();
        for (Port p : getRTPorts(klass)) {
            if (p.getRedefinedPorts() != null && !p.getRedefinedPorts().isEmpty()) {
                ports.add(p);
            }
        }
        return ports;
    }

    /**
     * Returns an iterable over the ports redefined by the given class.
     */
    private static Iterable<Connector> getRedefinedConnectors(Class klass) {
        List<Connector> connectors = new ArrayList<Connector>();
        for (Connector c : getConnectorRedefinitions(klass)) {
            Connector redefined = c.getRedefinedConnectors().get(0); // Assume a port redefines only one port.
            connectors.add(redefined);
        }
        return connectors;
    }

    /**
     * Returns an iterable over the ports of the given class which redefine some inherited port.
     */
    private static Iterable<Connector> getConnectorRedefinitions(Class klass) {
        List<Connector> connectors = new ArrayList<Connector>();
        for (Connector c : getRTConnectors(klass)) {
            if (c.getRedefinedConnectors() != null && !c.getRedefinedConnectors().isEmpty()) {
                connectors.add(c);
            }
        }
        return connectors;
    }

}
