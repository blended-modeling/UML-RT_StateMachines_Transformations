/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.utils

import java.util.Comparator
import java.util.TreeSet
import org.eclipse.papyrusrt.xtumlrt.common.Capsule
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement
import org.eclipse.papyrusrt.xtumlrt.common.Connector
import org.eclipse.papyrusrt.xtumlrt.common.Entity
import org.eclipse.papyrusrt.xtumlrt.common.Enumeration
import org.eclipse.papyrusrt.xtumlrt.common.DirectionKind
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.common.Model
import org.eclipse.papyrusrt.xtumlrt.common.MultiplicityElement
import org.eclipse.papyrusrt.xtumlrt.common.Package
import org.eclipse.papyrusrt.xtumlrt.common.Port
import org.eclipse.papyrusrt.xtumlrt.common.Protocol
import org.eclipse.papyrusrt.xtumlrt.common.Signal
import org.eclipse.papyrusrt.xtumlrt.common.StateMachine
import org.eclipse.papyrusrt.xtumlrt.common.VisibilityKind
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPort
import org.eclipse.papyrusrt.xtumlrt.umlrt.PortRegistration
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.common.util.URI

class XTUMLRTUtil
{

    static class NameComparator implements Comparator<NamedElement>
    {
        override int compare( NamedElement o1, NamedElement o2 )
        {
            // null sorts earlier
            if (o1 == null)
                return if (o2 == null) 0 else -1
            if (o2 == null)
                return 1

            val name1 = o1.name
            val name2 = o2.name
            if (name1 == null)
                return if (name2 == null) 0 else -1
            if (name2 == null)
                return 1

            return name1.compareTo(name2);
        }
    }

    /**
     * Returns the owned behaviour of the given capsule if it has one. Otherwise,
     * returns any inherited behaviour.
     */
    static def StateMachine getActualBehaviour( Capsule capsule )
    {
        var StateMachine stateMachine = null
        if (capsule !== null)
        {
            if (capsule.behaviour !== null)
                stateMachine = capsule.behaviour
            else if (capsule.redefines !== null && capsule.redefines instanceof Capsule)
                stateMachine = (capsule.redefines as Capsule).actualBehaviour
        }
        stateMachine
    }

    static dispatch def Iterable<Capsule> getAllCapsules( Model model )
    {
        val set = new TreeSet<Capsule>( new NameComparator )
        set.addAll( model.entities.filter( Capsule ) )
        for ( pkg : model.packages )
        {
            set.addAll( pkg.allCapsules )
        }
        set
    }

    static dispatch def Iterable<Capsule> getAllCapsules( Package packge )
    {
        val set = new TreeSet<Capsule>( new NameComparator )
        set.addAll( packge.entities.filter( Capsule ) )
        set
    }

    /**
     * Returns the set of all ports in the class, including those which have been inherited
     * and those which are redefinitions of inherited ports.
     */
    static def Iterable<CapsulePart> getAllCapsuleParts( Capsule capsule )
    {
        val allParts = new TreeSet<CapsulePart>( new NameComparator() )
        if (capsule !== null)
        {
            allParts.addAll( capsule.parts )
            val parentElement = capsule.redefines
            if (parentElement !== null && parentElement instanceof Capsule)
            {
                val parent = parentElement as Capsule
                allParts.addAll( parent.allCapsuleParts.filter [ !redefines( capsule, it) ] )
            }
        }
        allParts
    }

    /**
     * Returns the set of all ports in the class, including those which have been inherited
     * and those which are redefinitions of inherited ports.
     */
    static def Iterable<Connector> getAllConnectors( Capsule capsule )
    {
        val allConnectors = new TreeSet<Connector>( new NameComparator() )
        if (capsule !== null)
        {
            allConnectors.addAll( capsule.connectors )
            val parentElement = capsule.redefines
            if (parentElement !== null && parentElement instanceof Capsule)
            {
                val parent = parentElement as Capsule
                allConnectors.addAll( parent.allConnectors )
            }
        }
        allConnectors
    }

    /**
     * Returns the set of all ports in the class, including those which have been inherited
     * and those which are redefinitions of inherited ports.
     */
    static def Iterable<Port> getAllRTPorts( Capsule capsule )
    {
        val allPorts = new TreeSet<Port>( new NameComparator() )
        if (capsule !== null)
        {
            allPorts.addAll( capsule.RTPorts )
            val parentElement = capsule.redefines
            if (parentElement !== null && parentElement instanceof Capsule)
            {
                val parent = parentElement as Capsule
                allPorts.addAll( parent.allRTPorts.filter [ !redefines( capsule, it) ] )
            }
        }
        allPorts
    }

    /**
     * Returns the set of all signals in the protocol, including those which have been inherited
     * and those which are redefinitions of inherited signals.
     */
    static def Iterable<Signal> getAllSignals( Protocol protocol )
    {
        val allSignals = new TreeSet<Signal>( new NameComparator() )
        if (protocol !== null)
        {
            allSignals.addAll( protocol.signals )
            val parentElement = protocol.redefines
            if (parentElement !== null && parentElement instanceof Capsule)
            {
                val parent = parentElement as Protocol
                allSignals.addAll( parent.allSignals.filter [ !redefines( protocol, it) ] )
            }
        }
        allSignals
    }

    static def Iterable<Signal> getAllUserSignals( Protocol protocol )
    {
        protocol.allSignals.filter[ !(it.isSystemElement) ]
    }

    /**
     * A filter that produces only Parts that are properly stereotyped as RTCapsulePart
     * and have a Capsule as type
     */
    static def Iterable<CapsulePart> getCapsuleParts( Capsule capsule ) {
        // Ports are put into a sorted list to make sure that the order is
        // stable between
        // different parts of the generator (as well as between invocations).
        val parts = new TreeSet<CapsulePart>( new NameComparator() )
        parts.addAll( capsule.parts.filter [ it.type !== null ] )
        parts
    }

    static def Iterable<Signal> getInSignals( Protocol protocol )
    {
        protocol.protocolBehaviourFeatures
            .filter [ it instanceof Signal && it.kind == DirectionKind.IN ]
            .map [it as Signal]
    }

    static def Iterable<Signal> getInOutSignals( Protocol protocol )
    {
        protocol.protocolBehaviourFeatures
            .filter [ it instanceof Signal && it.kind == DirectionKind.IN_OUT ]
            .map [it as Signal]
    }

    /**
     * Find and return the given element's lower bound.  Throws an exception is the
     * element has an invalid lower bound.
     */
    static def <T extends MultiplicityElement & NamedElement> int getLowerBound( T element )
    {
        // published ports always have a lower-bound of 0
        if (element instanceof RTPort && (element as RTPort).isPublish)
            return 0
        val bound = element.getLowerBound()
        if (bound < 0)
            throw new RuntimeException( "lower bound must be specified for " + QualifiedNames.fullName( element ) )
        return bound
    }

    static def Iterable<Signal> getOutSignals( Protocol protocol )
    {
        protocol.protocolBehaviourFeatures
            .filter [ it instanceof Signal && it.kind == DirectionKind.OUT ]
            .map [it as Signal]
    }

    static def NamedElement getOwner( CommonElement element )
    {
        if (element.eContainer === null) null else element.eContainer as NamedElement
    }

    static def Model getModel( CommonElement element )
    {
        var elem = element
        while ( elem !== null && !(elem instanceof Model) && elem.owner !== null )
            elem = elem.owner
        elem as Model
    }

    /**
     * Returns an iterable over the ports of the given class which redefine some inherited port.
     */
    static def Iterable<CapsulePart> getPartRedefinitions( Capsule capsule )
    {
        capsule.parts.filter[ it.redefines !== null && it.redefines instanceof CapsulePart ]
    }

    /**
     * Returns an iterable over the ports of the given class which redefine some inherited port.
     */
    static def Iterable<Port> getPortRedefinitions( Capsule capsule )
    {
        capsule.ports.filter[ it.redefines !== null && it.redefines instanceof Port ]
    }

    /**
     * Returns an iterable over the signals of the given protocol which redefine some inherited signal.
     */
    static def Iterable<Signal> getSignalRedefinitions( Protocol protocol )
    {
        protocol.signals.filter[ it.redefines !== null && it.redefines instanceof Signal ]
    }

    /**
     * Returns an iterable over the ports redefined by the given class.
     */
    static def Iterable<CapsulePart> getRedefinedParts( Capsule capsule )
    {
        capsule.partRedefinitions.map [ it.redefines as CapsulePart ]
    }

    /**
     * Returns an iterable over the ports redefined by the given class.
     */
    static def Iterable<Port> getRedefinedPorts( Capsule capsule )
    {
        capsule.portRedefinitions.map [ it.redefines as Port ]
    }

    /**
     * Returns an iterable over the signals redefined by the given protocol.
     */
    static def Iterable<Signal> getRedefinedSignals( Protocol protocol )
    {
        protocol.signalRedefinitions.map [ it.redefines as Signal ]
    }

    /**
     * A filter that produces only Ports that are properly stereotyped as RTPort
     * and have a UML-RT protocol.
     */
    static def Iterable<Port> getRTPorts( Capsule capsule ) {
        // Ports are put into a sorted list to make sure that the order is
        // stable between
        // different parts of the generator (as well as between invocations).
        val ports = new TreeSet<Port>( new NameComparator() )
        ports.addAll( capsule.ports.filter [ it.type !== null ] )
        ports
    }

    static def Iterable<Signal> getSignals( Protocol protocol )
    {
        protocol.protocolBehaviourFeatures.filter[ it instanceof Signal ].map [ it as Signal ]
    }

    static def Iterable<Signal> getUserSignals( Protocol protocol )
    {
        protocol.signals.filter[ !(it.isSystemElement) ]
    }

    /**
     * Find and return the given element's upper bound.  Throws an exception is the
     * element has an invalid upper bound.
     */
    static def <T extends MultiplicityElement & NamedElement> int getUpperBound( T element )
    {
        val bound = element.getUpperBound()
        if ( bound < 0 )
            throw new RuntimeException( "upper bound must be specified for " + QualifiedNames.fullName( element ) );
        return bound
    }

    static def isInternalPort( Port port )
    {
        // Bug 469777: The port's isWired property needs to be used when searching for a SAP/SPP.
            port.getVisibility() != VisibilityKind.PUBLIC
         || ! port.wired
    }

    static def isWired( Port port )
    {
        port instanceof RTPort && (port as RTPort).wired
    }

    /** True if the port should be registered as SAP/SPP at startup or during creation. */
    static def isAutomatic( Port port )
    {
        port instanceof RTPort && (port as RTPort).registration == PortRegistration.AUTOMATIC;
    }

    /** True if the port is application-locked. */
    static def isApplicationLocked( Port port )
    {
        port instanceof RTPort && (port as RTPort).registration == PortRegistration.APPLICATIONLOCKED;
    }

    /** True when user requested binding notification. */
    static def isNotification( Port port )
    {
        port instanceof RTPort && (port as RTPort).notification
    }

    static def isPublish( Port port )
    {
        port instanceof RTPort && (port as RTPort).publish
    }

    /** Return the model-supplied registered name override. */
    static def getRegistrationOverride( Port port )
    {
        if( ! ( port instanceof RTPort ) )
            null
        else
            (port as RTPort).registrationOverride
    }

    /**
     * Return true if the element is replicated and false otherwise.
     */
    static def <T extends MultiplicityElement & NamedElement> boolean isReplicated( T element )
    {
        element.getLowerBound() != 1 || element.getUpperBound() != 1
    }

    /**
     * Returns true iff the given port is inherited and redefined by the given class.
     */
    private static dispatch def redefines( Capsule capsule, Port port )
    {
        capsule.redefinedPorts.exists[ it == port ]
    }

    /**
     * Returns true iff the given port is inherited and redefined by the given class.
     */
    private static dispatch def redefines( Capsule capsule, CapsulePart part )
    {
        capsule.redefinedParts.exists[ it == part ]
    }

    /**
     * Returns true iff the given signal is inherited and redefined by the given protocol.
     */
    private static dispatch def redefines( Protocol protocol, Signal signal )
    {
        protocol.redefinedSignals.exists[ it == signal ]
    }

    public static val String RTS_LIBRARY_NAME = "UML-RT RTS Model Library"
    public static val URI RTS_LIBRARY_URI = URI.createURI("pathmap://UMLRTRTSLIB/UMLRT-RTS.uml#_mPjAgGXmEeS_4daqvwyFrg")
    public static val String RTS_LIBRARY_URI_STR = RTS_LIBRARY_URI.toPlatformString( false )

    /**
     * Returns true iff the given UML model is the model library.
     */
    static def boolean isRTSModelLibrary( org.eclipse.uml2.uml.Package packge )
    {
        val uriConverter = packge.eResource.resourceSet.URIConverter
        val packageURI = EcoreUtil.getURI( packge )
        val normalizedPackageURI = uriConverter.normalize( packageURI )
        val normalizedRTSLibURI  = uriConverter.normalize( RTS_LIBRARY_URI )

        packge instanceof org.eclipse.uml2.uml.Model
        && packge.getQualifiedName().equals( "RTS" )
        && packge.isModelLibrary()
        && normalizedPackageURI == normalizedRTSLibURI
    }

    /**
     * Returns true iff the given xtUMLrt model is the model library.
     * The translator attaches to such model element, an annotation named with
     * RTS_LIBRARY_ID. This method searches for such annotation.
     */
    static def boolean isRTSModelLibrary( Model model )
    {
        model !== null
        && model.name == "RTS"
        && model.isSystemElement
    }

    /**
     * Return true iff the element belongs to the RTS Model Library
     */
    static def boolean isSystemElement( CommonElement element )
    {
        element !== null
        && element instanceof NamedElement
        && (element as NamedElement).annotations !== null
        && (element as NamedElement).annotations.exists
            [
                it.name == RTS_LIBRARY_NAME
                && it.parameters !== null
                && !it.parameters.empty
                && it.parameters.exists [ it.value == RTS_LIBRARY_URI_STR ]
            ]
    }

    static def boolean isSystemElement( org.eclipse.uml2.uml.Element element )
    {
        element.model.isRTSModelLibrary
    }

}