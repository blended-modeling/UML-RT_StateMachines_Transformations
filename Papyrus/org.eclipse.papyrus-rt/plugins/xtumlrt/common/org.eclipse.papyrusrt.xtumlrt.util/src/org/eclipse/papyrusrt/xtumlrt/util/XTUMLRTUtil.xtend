/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.util

import java.util.Comparator
import java.util.LinkedHashSet
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrusrt.xtumlrt.common.Attribute
import org.eclipse.papyrusrt.xtumlrt.common.BaseContainer
import org.eclipse.papyrusrt.xtumlrt.common.Behaviour
import org.eclipse.papyrusrt.xtumlrt.common.Capsule
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory
import org.eclipse.papyrusrt.xtumlrt.common.Connector
import org.eclipse.papyrusrt.xtumlrt.common.Model
import org.eclipse.papyrusrt.xtumlrt.common.MultiplicityElement
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.common.OpaqueExpression
import org.eclipse.papyrusrt.xtumlrt.common.Package
import org.eclipse.papyrusrt.xtumlrt.common.Port
import org.eclipse.papyrusrt.xtumlrt.common.Protocol
import org.eclipse.papyrusrt.xtumlrt.common.ProtocolBehaviourFeatureKind
import org.eclipse.papyrusrt.xtumlrt.common.Signal
import org.eclipse.papyrusrt.xtumlrt.common.StructuredType
import org.eclipse.papyrusrt.xtumlrt.common.ValueSpecification
import org.eclipse.papyrusrt.xtumlrt.common.LiteralNull
import org.eclipse.papyrusrt.xtumlrt.common.LiteralBoolean
import org.eclipse.papyrusrt.xtumlrt.common.LiteralInteger
import org.eclipse.papyrusrt.xtumlrt.common.LiteralReal
import org.eclipse.papyrusrt.xtumlrt.common.LiteralString
import org.eclipse.papyrusrt.xtumlrt.common.LiteralNatural
import org.eclipse.papyrusrt.xtumlrt.common.LiteralUnlimited
import org.eclipse.papyrusrt.xtumlrt.common.EnumerationLiteral
import org.eclipse.papyrusrt.xtumlrt.umlrt.PortKind
import org.eclipse.papyrusrt.xtumlrt.umlrt.PortRegistration
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPort
import static extension org.eclipse.papyrusrt.xtumlrt.util.GeneralUtil.*
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTAnnotations.*
import org.eclipse.papyrusrt.xtumlrt.common.Annotation

class XTUMLRTUtil
{
    static class NameComparator implements Comparator<NamedElement>
    {
        override int compare( NamedElement o1, NamedElement o2 )
        {
            // null sorts earlier
            if (o1 === null)
                return if (o2 === null) 0 else -1
            if (o2 === null)
                return 1

            val name1 = o1.name
            val name2 = o2.name
            if (name1 === null)
                return if (name2 === null) 0 else -1
            if (name2 === null)
                return 1

            return name1.compareTo(name2);
        }
    }

	static def List<CommonElement> getContainerList( CommonElement element )
	{
		val result = newArrayList
		var elem = element
		while (elem !== null)
		{
			result.add(0, elem)
			elem = elem.owner
		}
		result
	}


    /**
     * Returns the list of all elements in the containment chain from the root element
     * to the given element.
     */
    static def Iterable<CommonElement> getAllContainers( CommonElement element )
    {
        val List<CommonElement> list = newArrayList
        var elem = element
        while (elem !== null)
        {
            list.add( 0, elem )
            elem = elem.owner
        }
        list
    }

    /**
     * Return the given capsule's parts that have a type.
     */
    static def Iterable<Attribute> getClassAttributes( StructuredType struct ) {
        // Ports are put into a sorted list to make sure that the order is
        // stable between
        // different parts of the generator (as well as between invocations).
        val attrs = new LinkedHashSet<Attribute>()
        attrs.addAll( struct.attributes.filter [ !isExcluded ] ) // type may be null if specified with the RtCppProperties.AttributeProperties stereotype
        attrs
    }

    /**
     * Return the given capsule's parts that have a type.
     */
    static def Iterable<CapsulePart> getCapsuleParts( Capsule capsule ) {
        // Ports are put into a sorted list to make sure that the order is
        // stable between
        // different parts of the generator (as well as between invocations).
        val parts = new LinkedHashSet<CapsulePart>()
        parts.addAll( capsule.parts.filter [ type !== null && !isExcluded ] )
        parts
    }

    /**
     * A filter that produces only Ports that are properly stereotyped as RTPort
     * and have a UML-RT protocol.
     */
    static def Iterable<Port> getRTPorts( Capsule capsule ) {
        // Ports are put into a sorted list to make sure that the order is
        // stable between
        // different parts of the generator (as well as between invocations).
        val ports = new LinkedHashSet<Port>()
        ports.addAll( capsule.ports.filter [ type !== null && !isExcluded ] )
        ports
    }

    /**
     * Return the given capsule's connectors.
     */
    static def Iterable<Connector> getCapsuleConnectors( Capsule capsule ) {
        // Connectors are put into a sorted list to make sure that the order is
        // stable between
        // different parts of the generator (as well as between invocations).
        val connectors = new LinkedHashSet<Connector>()
        connectors.addAll( capsule.connectors.filter [ !isExcluded ] )
        connectors
    }

    /**
     * Return the first container up the containing chain that is a {@link NamedElement}.
     */
    static def NamedElement firstNamedContainer(EObject element)
    {
        if (element === null)
        {
            null
        }
        else if (element instanceof NamedElement)
        {
            element
        }
        else
        {
            element.eContainer?.firstNamedContainer
        }
    }

    /**
     * Return the first container up the containing chain that is a {@link NamedElement}.
     */
    static def NamedElement firstNamespaceContainer(EObject element)
    {
        if (element === null)
        {
            null
        }
        else if (element instanceof NamedElement)
        {
            if (element.isNamespace)
            {
                element
            }
            else
            {
                element.owner.firstNamespaceContainer
            }
        }
        else
        {
            element.eContainer?.firstNamespaceContainer
        }
    }

    static def Iterable<Signal> getInSignals( Protocol protocol )
    {
        protocol.protocolBehaviourFeatures
            .filter [ it instanceof Signal && it.kind == ProtocolBehaviourFeatureKind.IN ]
            .map [it as Signal]
    }

    static def Iterable<Signal> getInOutSignals( Protocol protocol )
    {
        protocol.protocolBehaviourFeatures
            .filter [ it instanceof Signal && it.kind == ProtocolBehaviourFeatureKind.INOUT ]
            .map [it as Signal]
    }

    /**
     * Returns the lowest common ancestor to the given elements, i.e. the element 
     * that contains (directly or indirectly) both elements and which doesn't contain any other common 
     * ancestor of both elements.
     */
    static def CommonElement getLowestCommonAncestor( CommonElement element1, CommonElement element2 )
    {
        val prefix = getLowestCommonAncestorPrefix(element1, element2)
        prefix.last
    }

    /**
     * Returns the container list of the lowest common ancestor to the given elements, i.e. the element 
     * that contains (directly or indirectly) both elements and which doesn't contain any other common 
     * ancestor of both elements.
     */
    static def Iterable<CommonElement> getLowestCommonAncestorPrefix( CommonElement element1, CommonElement element2 )
    {
        longestCommonPrefix(element1.allContainers, element2.allContainers)
    }

    static def Iterable<Signal> getOutSignals( Protocol protocol )
    {
        protocol.protocolBehaviourFeatures
            .filter [ it instanceof Signal && it.kind == ProtocolBehaviourFeatureKind.OUT ]
            .map [it as Signal]
    }

    static def NamedElement getOwner( CommonElement element )
    {
        if (element.eContainer === null) null else element.eContainer as NamedElement
    }

    static def BaseContainer getRoot( CommonElement element )
    {
        var elem = element
        while ( elem !== null && elem.owner !== null )
            elem = elem.owner
        if (elem instanceof BaseContainer) elem else null
    }

    static def Iterable<Signal> getSignals( Protocol protocol )
    {
        protocol.protocolBehaviourFeatures.filter[ it instanceof Signal && !isExcluded ].map [ it as Signal ]
    }

    static def isExternalPort( Port port )
    {
        port instanceof RTPort && (port as RTPort).kind == PortKind.EXTERNAL
    }

	static def isInternalPort( Port port )
	{
		port instanceof RTPort && (port as RTPort).kind == PortKind.INTERNAL
	}

    static def isRelayPort( Port port )
    {
        port instanceof RTPort && (port as RTPort).kind == PortKind.RELAY
    }

    static def isSAP( Port port )
    {
        port instanceof RTPort && (port as RTPort).kind == PortKind.SAP
        // && ( !(port as RTPort).wired ) && ( !(port as RTPort).publish )
    }
    
    static def isSPP( Port port )
    {
        port instanceof RTPort && (port as RTPort).kind == PortKind.SPP
        // && ( !(port as RTPort).wired ) && ( (port as RTPort).publish )
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

    /** True if the port is automatic-locked. */
    static def isAutomaticLocked( Port port )
    {
        port instanceof RTPort && (port as RTPort).registration == PortRegistration.AUTOMATICLOCKED;
    }

    /** True iff the element corresponds to a UML Namespace element. */
    static def isNamespace( CommonElement element )
    {
        element instanceof StructuredType
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

    static def isBorderPort( Port port )
    {
        port.isExternalPort || port.isRelayPort
    }

    static def isNonBorderPort( Port port )
    {
        !port.isBorderPort
        // Bug 507005: due to a terminology mismatch, what the runtime calls "internal ports" includes also SAPs and SPPs. 
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
     * Return the String value of a given ValueSpecification if it can be computed
     */
    static dispatch def String getStringValue( ValueSpecification valSpec )
    {
        null
    }

    static dispatch def String getStringValue( LiteralNull valSpec )
    {
        String.valueOf(null as Object)
    }

    static dispatch def String getStringValue( LiteralBoolean valSpec )
    {
        String.valueOf(valSpec.value)
    }

    static dispatch def String getStringValue( LiteralInteger valSpec )
    {
        String.valueOf(valSpec.value)
    }

    static dispatch def String getStringValue( LiteralReal valSpec )
    {
        String.valueOf(valSpec.value)
    }

    static dispatch def String getStringValue( LiteralString valSpec )
    {
        '"' + String.valueOf(valSpec.value) + '"'
    }

    static dispatch def String getStringValue( LiteralNatural valSpec )
    {
        String.valueOf(valSpec.value)
    }

    static dispatch def String getStringValue( LiteralUnlimited valSpec )
    {
        "*"
    }

    static dispatch def String getStringValue( OpaqueExpression valSpec )
    {
        valSpec?.body ?: "0"
    }

    static dispatch def String getStringValue( EnumerationLiteral valSpec )
    {
        valSpec.name
    }

    static dispatch def getCopy(CommonElement element)
    {
        null
    }

    static dispatch def getCopy(LiteralInteger element)
    {
        CommonFactory.eINSTANCE.createLiteralInteger => [ value = element.value ]
    }

    static dispatch def getCopy(LiteralNatural element)
    {
        CommonFactory.eINSTANCE.createLiteralNatural => [ value = element.value ]
    }

    static dispatch def getCopy(OpaqueExpression element)
    {
        CommonFactory.eINSTANCE.createOpaqueExpression => [ body = element.body ]
    }

    /**
     * Returns true iff the given element is marked as excluded
     */
    static def isExcluded( CommonElement element )
    {
        val redefinedElementStereotype = getAnnotation( element, "RTRedefinedElement" )
        if (redefinedElementStereotype !== null)
        {
            val rootFragment = getAnnotationProperty( redefinedElementStereotype, "rootFragment" )
            if (rootFragment === null)
                return true
        }
        return false
    }

    static def excludedElements( NamedElement container )
    {
        var Iterable<CommonElement> elements = newArrayList
        val ann = getAnnotation( container, XTUMLRTAnnotations.ANN_EXCLUSION )
        if (ann instanceof Annotation)
        {
            val param = ann.parameters.findFirst[ name == XTUMLRTAnnotations.ANN_PARAM_EXCLUDED ]
            elements = param.value as Iterable<CommonElement>
        }
        return elements
    }

    static def excludes( NamedElement container, CommonElement element )
    {
        container.excludedElements.exists[ it == element ]
    }

}