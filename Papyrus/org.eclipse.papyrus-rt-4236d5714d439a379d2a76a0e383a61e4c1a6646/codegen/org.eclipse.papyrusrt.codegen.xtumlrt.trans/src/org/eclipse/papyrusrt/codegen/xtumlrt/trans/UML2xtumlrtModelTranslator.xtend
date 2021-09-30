/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.codegen.xtumlrt.trans

import java.util.List
import java.nio.file.Path

import org.eclipse.emf.ecore.EObject

import org.eclipse.papyrus.umlrt.UMLRealTime.RTMessageKind
import org.eclipse.papyrusrt.codegen.utils.UML2CppUtil
import org.eclipse.papyrusrt.xtumlrt.common.Attribute
import org.eclipse.papyrusrt.xtumlrt.common.Capsule
import org.eclipse.papyrusrt.xtumlrt.common.CapsuleKind
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory
import org.eclipse.papyrusrt.xtumlrt.common.Connector
import org.eclipse.papyrusrt.xtumlrt.common.ConnectorEnd
import org.eclipse.papyrusrt.xtumlrt.common.DirectionKind
import org.eclipse.papyrusrt.xtumlrt.common.Entity
import org.eclipse.papyrusrt.xtumlrt.common.Enumeration
import org.eclipse.papyrusrt.xtumlrt.common.EnumerationLiteral
import org.eclipse.papyrusrt.xtumlrt.common.Model
import org.eclipse.papyrusrt.xtumlrt.common.MultiplicityElement
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.common.Operation
import org.eclipse.papyrusrt.xtumlrt.common.Package
import org.eclipse.papyrusrt.xtumlrt.common.Parameter
import org.eclipse.papyrusrt.xtumlrt.common.Port
import org.eclipse.papyrusrt.xtumlrt.common.PrimitiveType
import org.eclipse.papyrusrt.xtumlrt.common.Protocol
import org.eclipse.papyrusrt.xtumlrt.common.ProtocolBehaviourFeatureKind
import org.eclipse.papyrusrt.xtumlrt.common.RedefinableElement
import org.eclipse.papyrusrt.xtumlrt.common.Signal
import org.eclipse.papyrusrt.xtumlrt.common.StateMachine
import org.eclipse.papyrusrt.xtumlrt.common.StructuredType
import org.eclipse.papyrusrt.xtumlrt.common.Type
import org.eclipse.papyrusrt.xtumlrt.common.TypeDefinition
import org.eclipse.papyrusrt.xtumlrt.common.VisibilityKind
import org.eclipse.papyrusrt.xtumlrt.umlrt.PortRegistration
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPassiveClass
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPort
import org.eclipse.papyrusrt.xtumlrt.umlrt.UmlrtFactory
import static extension org.eclipse.papyrusrt.codegen.utils.UMLRealTimeProfileUtil.*
import static extension org.eclipse.papyrusrt.codegen.utils.XTUMLRTUtil.*
import static extension org.eclipse.papyrusrt.codegen.utils.GeneralUtil.*
import org.eclipse.papyrusrt.codegen.cpp.rts.UMLRTRuntime
import org.eclipse.papyrusrt.codegen.utils.XTUMLRTUtil
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.papyrusrt.xtumlrt.common.TypedMultiplicityElement

/**
 * This class translates models from the UML2 meta-model (org.eclipse.uml2) into the xtUMLrt
 * meta-model (org.eclipse.papyrusrt.xtumlrt).
 *
 * <p>Each element is mapped to the corresponding element by a method. There are a few cases where
 * the same input element may be mapped into more than one kind of output element. For example
 * a UML2 Package may be mapped to an xtUMLrt Package, but a UML2 Package which is a protocol
 * container, as defined in the UML-RT profile, is mapped to an xtUMLrt protocol. Similarly,
 * a UML2 Class may be mapped to a Capsule, an rtPassiveClass or a xtClass. In these cases, the
 * output type of the corresponding method is the least-common ancestor in the (meta-class)
 * type hierarchy.
 *
 * <p>The translation is recursive on the structure of elements, this is, the translation of
 * composite elements (elements which may contain other elements), also translates its
 * contained elements. This rule applies not only for "Composition" references: for example,
 * when translating a capsule, we need to translate its ports, which have a protocol as type,
 * so we need to get the translation of the protocol as well, even though it is not owned by
 * neither the port nor the capsule. In order to avoid translating the same elements twice, and
 * to avoid generation cycles, many of these methods are 'create' methods, which only create
 * the relevant element the first time and cache the result so that future invocations of the
 * translate method return the same translated element.
 *
 * @author Ernesto Posse
 */
 @SuppressWarnings( "deprecated" )
class UML2xtumlrtModelTranslator extends UML2xtumlrtTranslator
{

    UML2xtumlrtSMTranslator stateMachineTranslator

    new ()
    {
        super()
        this.stateMachineTranslator = new UML2xtumlrtSMTranslator( this )
    }

    new ( List<EObject> targets, Path outputPath )
    {
        super( targets, outputPath )
        this.stateMachineTranslator = new UML2xtumlrtSMTranslator( this )
    }

    /**
     * Sets the 'redefines' reference of a RedefinableElement in the target meta-model to the
     * translation of the element it redefines (assuming it's only one at most).
     *
     * <p>This method does not create the RedefinableElement in the target meta-model. That
     * instance is created by any of the other {@code translate} methods, and this one just
     * updates the {@code refines} reference to the (translated) element being refined.
     */
    protected def translateRedefinableElement
    (
        org.eclipse.uml2.uml.RedefinableElement element,
        RedefinableElement newElement
    )
    {
        if (element.redefinedElements !== null && !element.redefinedElements.empty)
        {
            val redefinedElement = element.redefinedElements.get(0)
            newElement.redefines = translateElement( redefinedElement ) as RedefinableElement
        }
    }

    protected def translateGeneralization
    (
        org.eclipse.uml2.uml.Classifier element,
        RedefinableElement newElement
    )
    {
        if (element.generals !== null && !element.generals.empty)
        {
            var org.eclipse.uml2.uml.Classifier superClassifier = null
            if (element.isCapsule)
                superClassifier = element.generals.findFirst[ it.isCapsule ] as org.eclipse.uml2.uml.Class
            else if (element.isProtocol)
                superClassifier = element.generals.get(0) as org.eclipse.uml2.uml.Collaboration
            else
                superClassifier = element.generals.get(0) as org.eclipse.uml2.uml.Class
            if (superClassifier !== null)
                newElement.redefines = translateElement( superClassifier ) as RedefinableElement
        }
    }

    protected def translateMultiplicityElement
    (
        org.eclipse.uml2.uml.MultiplicityElement element,
        MultiplicityElement newElement
    )
    {
        newElement.unique   = element.unique
        newElement.ordered  = element.ordered
        newElement.lowerBound = element.lowerBound
        newElement.upperBound = element.upperBound
    }

    /**
     * Translates a package.
     *
     * <p>In UML-RT, protocols are represented as a collection of elements (a collaboration,
     * some interfaces, etc.) grouped together in a package container. This method decides
     * whether it is dealing with a "normal" package, or a "protocol container" package, and
     * invokes the relevant translation.
     *
     * @param packge    - A {@link org.eclipse.uml2.uml.Package}
     * @return Either an xtUMLrt {@link Package} or a {@link Protocol} if the input is a
     *          protocol container.
     */
    dispatch def NamedElement
    create
        if (packge instanceof org.eclipse.uml2.uml.Model)
            CommonFactory.eINSTANCE.createModel
        else if (packge.isProtocolContainer)
            translateProtocolContainer( packge )
        else
            CommonFactory.eINSTANCE.createPackage
    translate( org.eclipse.uml2.uml.Package packge )
    {
        name = packge.name
        if (packge instanceof org.eclipse.uml2.uml.Model)
        {
            initModel( packge, it as Model )
            if (packge.isRTSModelLibrary)
                addSysAnnotation
        }
        else if (packge.isProtocolContainer)
            {}
        else
            initPackage( packge, it as Package )
        UML2xtumlrtCppProfileTranslator.translateStereotypedElement( packge, it )
    }

    protected def initModel( org.eclipse.uml2.uml.Model model, Model newModel )
    {
        val it = newModel
        for (element : model.packagedElements)
        {
            if (element instanceof org.eclipse.uml2.uml.Class)
                entities.addIfNotPresent( translateElement(element) as Entity )
            else if (element.isProtocolContainer)
                protocols.addIfNotPresent( translateProtocolContainer(element as org.eclipse.uml2.uml.Package) as Protocol )
            else if (element instanceof org.eclipse.uml2.uml.DataType)
                typeDefinitions.addIfNotPresent( translateTypeDefinition(element) )
            else if (element instanceof org.eclipse.uml2.uml.Package)
                packages.addIfNotPresent( translateElement(element) as Package )
        }
    }

    protected def Protocol translateProtocolContainer( org.eclipse.uml2.uml.Package packge )
    {
        translateElement( packge.protocolCollaboration ) as Protocol
    }

    protected def initPackage( org.eclipse.uml2.uml.Package packge, Package newPackage )
    {
        val it = newPackage
        for (element : packge.packagedElements)
        {
            if (element instanceof org.eclipse.uml2.uml.Class)
                entities.addIfNotPresent( translateElement(element) as Entity )
            else if (element.isProtocolContainer)
                protocols.addIfNotPresent( translateProtocolContainer(element as org.eclipse.uml2.uml.Package) as Protocol )
            else if (element instanceof org.eclipse.uml2.uml.DataType)
                typeDefinitions.addIfNotPresent( translateTypeDefinition(element) )
            else if (element instanceof org.eclipse.uml2.uml.Package)
                packages.addIfNotPresent( translateElement(element) as Package )
        }
    }

    /**
     * @param collaboration     - A {@link org.eclipse.uml2.uml.Collaboration}
     * @return A {@link Protocol} if the collaboration is a protocol, {@code null} otherwise
     */
    dispatch def Protocol
    create
        if (collaboration.isProtocol)
            CommonFactory.eINSTANCE.createProtocol
    translate( org.eclipse.uml2.uml.Collaboration collaboration )
    {
        name = collaboration.name
        initProtocol( collaboration, it )
        if (collaboration.isSystemElement)
            addSysAnnotation
        translateGeneralization( collaboration, it )
        translateRedefinableElement( collaboration, it )
    }

    protected def Protocol initProtocol( org.eclipse.uml2.uml.Collaboration collaboration, Protocol newProtocol )
    {
        val it = newProtocol
        name = collaboration.name
        for (iface : collaboration.directlyRealizedInterfaces) // Q: what's the difference between this and allImplementedInterfaces?
        {
            if (iface.isRTMessageSet)
            {
                val rtMsgKind = iface.getRTMessageSet.rtMsgKind
                if (rtMsgKind == RTMessageKind.IN || rtMsgKind == RTMessageKind.IN_OUT)
                {
                    for (operation : iface.ownedOperations)
                    {
                        protocolBehaviourFeatures.add( translateElement(operation) as Signal )
                    }
                }
            }
        }
        for (iface : collaboration.directlyUsedInterfaces())
        {
            if (iface.isRTMessageSet)
            {
                val rtMsgKind = iface.getRTMessageSet.rtMsgKind
                if (rtMsgKind == RTMessageKind.OUT || rtMsgKind == RTMessageKind.IN_OUT)
                {
                    for (operation : iface.ownedOperations)
                    {
                        protocolBehaviourFeatures.add( translateElement(operation) as Signal )
                    }
                }
            }
        }
        it
    }

    /**
     * @param operation     - A {@link org.eclipse.uml2.uml.Operation}
     * @return A {@link Operation}
     */
    dispatch def NamedElement
    create
        if (operation.interface !== null && operation.interface.isRTMessageSet)
            CommonFactory.eINSTANCE.createSignal
        else
            CommonFactory.eINSTANCE.createOperation
    translate( org.eclipse.uml2.uml.Operation operation )
    {
        name = operation.name
        if (operation.interface !== null && operation.interface.isRTMessageSet)
        {
            initSignal( operation, it as Signal )
            if (operation.isSystemElement)
                addSysAnnotation
        }
        else
            initOperation( operation, it as Operation )
        UML2xtumlrtCppProfileTranslator.translateStereotypedElement( operation, it )
    }

    protected def initOperation( org.eclipse.uml2.uml.Operation operation, Operation newOperation )
    {
        val it = newOperation
        var org.eclipse.uml2.uml.Parameter returnResult = null
        for (parameter : operation.ownedParameters)
        {
            if (parameter.direction !== org.eclipse.uml2.uml.ParameterDirectionKind.RETURN_LITERAL)
                parameters.add( translateElement(parameter) as Parameter )
            else
                returnResult = parameter
        }
        if (returnResult !== null)
            returnType = translateElement( returnResult ) as TypedMultiplicityElement
        val actionCode = CommonFactory.eINSTANCE.createActionCode
        actionCode.source = UML2CppUtil.getCppCode( operation )
        body = actionCode
    }

    protected def initSignal( org.eclipse.uml2.uml.Operation operation, Signal newSignal )
    {
        val it = newSignal
        for (parameter : operation.ownedParameters)
        {
            parameters.add( translateElement(parameter) as Parameter )
        }
        if (operation.interface.isRTMessageSet)
        {
            val rtMsgKind = operation.interface.getRTMessageSet.rtMsgKind
            switch (rtMsgKind)
            {
                case RTMessageKind.IN:      kind = ProtocolBehaviourFeatureKind.IN
                case RTMessageKind.OUT:     kind = ProtocolBehaviourFeatureKind.OUT
                case RTMessageKind.IN_OUT:  kind = ProtocolBehaviourFeatureKind.INOUT
            }
        }
    }

    /**
     * @param klass     - A {@link org.eclipse.uml2.uml.Class}
     * @return An {@link Entity}: either a {@link Capsule}, {@link RTPassiveClass} or
     *          {@link xtClass}.
     */
    dispatch def Type
    create
        if (klass.isCapsule)
            CommonFactory.eINSTANCE.createCapsule
        else
            UmlrtFactory.eINSTANCE.createRTPassiveClass
    translate( org.eclipse.uml2.uml.Class klass )
    {
        name = klass.name
        if (klass.isCapsule)
            initCapsule( klass, it as Capsule )
        else
            initRTPassiveClass( klass, it as RTPassiveClass )
        if (klass.isSystemElement)
            addSysAnnotation
        UML2xtumlrtCppProfileTranslator.translateStereotypedElement( klass, it )
    }

    protected dispatch def initStructuredType( org.eclipse.uml2.uml.Class klass, StructuredType newType )
    {
        translateGeneralization( klass, newType )
        for (attribute : klass.ownedAttributes)
        {
            if (!attribute.isRTPort && !attribute.isCapsulePart)
                newType.attributes.add( translateElement(attribute) as Attribute )
        }
        for (operation : klass.ownedOperations)
        {
            newType.operations.add( translateElement(operation) as Operation )
        }
    }

    protected def initBasicClass( org.eclipse.uml2.uml.Class klass, Entity newClass )
    {
        initStructuredType( klass, newClass )
        var org.eclipse.uml2.uml.StateMachine stateMachine = null
        if (klass.classifierBehavior !== null
            && klass.classifierBehavior instanceof org.eclipse.uml2.uml.StateMachine)
        {
            stateMachine = klass.classifierBehavior as org.eclipse.uml2.uml.StateMachine
        }
        else if (klass.ownedBehaviors !== null
                 && !klass.ownedBehaviors.empty)
        {
            stateMachine = klass.ownedBehaviors.findFirst[ it instanceof org.eclipse.uml2.uml.StateMachine ]
                           as org.eclipse.uml2.uml.StateMachine
        }
        if (stateMachine !== null)
            newClass.behaviour = translateElement( stateMachine ) as StateMachine
    }

    protected def initCapsule( org.eclipse.uml2.uml.Class klass, Capsule newCapsule )
    {
        val it = newCapsule
        initBasicClass( klass, it )
        for (part : klass.capsuleParts)
        {
            parts.add( translateElement(part) as CapsulePart )
        }
        for (port : klass.RTPorts)
        {
            ports.add( translateElement(port) as Port )
        }
        for (connector : klass.RTConnectors)
        {
            connectors.add( translateElement(connector) as Connector )
        }
    }

    protected def initRTPassiveClass( org.eclipse.uml2.uml.Class klass, RTPassiveClass newClass )
    {
        val it = newClass
        initBasicClass( klass, it )
        translateRedefinableElement( klass, it )
    }

    /**
     * @param property  - A {@link org.eclipse.uml2.uml.Property}
     * @return An {@link Attribute} or {@link CapsulePart}
     */
    dispatch def NamedElement
    create
    {
        val it =
            if (property.isCapsulePart)
                CommonFactory.eINSTANCE.createCapsulePart
            else if (property.isRTPort)
                UmlrtFactory.eINSTANCE.createRTPort
            else
                CommonFactory.eINSTANCE.createAttribute
        it as NamedElement
    }
    translate( org.eclipse.uml2.uml.Property property )
    {
        name = property.name
        if (property.isCapsulePart)
            initCapsulePart( property, it as CapsulePart )
        else if (property.isRTPort)
            initRTPort( property, it as RTPort )
        else
            initAttribute( property, it as Attribute )
        translateMultiplicityElement( property, it as MultiplicityElement )
        UML2xtumlrtCppProfileTranslator.translateStereotypedElement( property, it )
    }

    protected def initAttribute( org.eclipse.uml2.uml.Property property, Attribute newAttribute )
    {
        val it = newAttribute
        type = translateFeature( property, "type", org.eclipse.uml2.uml.Type, Type ) as Type
        visibility = translateEnumFeature( property, "visibility", org.eclipse.uml2.uml.VisibilityKind, VisibilityKind ) as VisibilityKind
        setDefault( property.getDefault )
        setStatic( property.isStatic )
        unique = property.unique
        ordered = property.ordered
        lowerBound = property.lowerBound
        upperBound = property.upperBound
        it
    }

    protected def initCapsulePart( org.eclipse.uml2.uml.Property property, CapsulePart newCapsulePart )
    {
        val it = newCapsulePart
        type = translateFeature( property, "type", org.eclipse.uml2.uml.Class, Capsule ) as Capsule
        if (property.lower == 0)
        {
            if (property.aggregation == org.eclipse.uml2.uml.AggregationKind.SHARED_LITERAL)
            {
                kind = CapsuleKind.PLUGIN
            }
            else
            {
                kind = CapsuleKind.OPTIONAL
            }
        }
        else
        {
            kind = CapsuleKind.FIXED
        }
    }

    protected def initPort( org.eclipse.uml2.uml.Property property, Port newPort )
    {
        val it = newPort
        type = translateFeature( property, "type", org.eclipse.uml2.uml.Collaboration, Protocol ) as Protocol
        conjugate = (property as org.eclipse.uml2.uml.Port).conjugated
        it
    }

    protected def initRTPort( org.eclipse.uml2.uml.Property property, RTPort newRTPort )
    {
        val it = newRTPort
        type = translateFeature( property, "type", org.eclipse.uml2.uml.Collaboration, Protocol ) as Protocol
        conjugate = (property as org.eclipse.uml2.uml.Port).conjugated
        val rtPort = property.getRTPort
        if (rtPort !== null)
        {
            notification = rtPort.notification
            publish = rtPort.publish
            wired = rtPort.wired
            switch ( property.visibility )
            {
                case PUBLIC_LITERAL:    visibility = VisibilityKind.PUBLIC
                case PACKAGE_LITERAL:   visibility = VisibilityKind.PROTECTED
                case PROTECTED_LITERAL: visibility = VisibilityKind.PROTECTED
                case PRIVATE_LITERAL:   visibility = VisibilityKind.PRIVATE
            }
            switch ( rtPort.registration )
            {
                case AUTOMATIC:             registration = PortRegistration.AUTOMATIC
                case APPLICATION:           registration = PortRegistration.APPLICATION
                case AUTOMATIC_LOCKED:      registration = PortRegistration.APPLICATIONLOCKED
            }
        }
    }

    /**
     * @param connector     - A {@link org.eclipse.uml2.uml.Connector}
     * @return A {@link Connector}
     */
    dispatch def Connector
    create CommonFactory.eINSTANCE.createConnector
    translate( org.eclipse.uml2.uml.Connector connector )
    {
        name = connector.name
        for (end : connector.ends)
        {
            ends.add( translateElement(end) as ConnectorEnd )
        }
    }

    /**
     * @param connectorEnd     - A {@link org.eclipse.uml2.uml.ConnectorEnd}
     * @return A {@link ConnectorEnd}
     */
    dispatch def ConnectorEnd
    create CommonFactory.eINSTANCE.createConnectorEnd
    translate( org.eclipse.uml2.uml.ConnectorEnd connectorEnd )
    {
        
        role = translateFeature( connectorEnd, "role", org.eclipse.uml2.uml.Port, Port ) as Port
        partWithPort =
            if (connectorEnd.partWithPort === null)
                null
            else
                translateFeature( connectorEnd, "partWithPort", org.eclipse.uml2.uml.Property, CapsulePart ) as CapsulePart
    }

    /**
     * @param parameter     - A {@link org.eclipse.uml2.uml.Parameter}
     * @return A {@link Parameter}
     */
    dispatch def TypedMultiplicityElement
    create
        if (parameter.direction == ParameterDirectionKind.RETURN_LITERAL)
            CommonFactory.eINSTANCE.createTypedMultiplicityElement
        else
            CommonFactory.eINSTANCE.createParameter
    translate( org.eclipse.uml2.uml.Parameter parameter )
    {
        if ( it instanceof Parameter )
        {
            name = parameter.name
            direction = translateEnumFeature( parameter, "direction", org.eclipse.uml2.uml.ParameterDirectionKind, DirectionKind ) as DirectionKind
        }
        type = translateFeature( parameter, "type", org.eclipse.uml2.uml.Type, Type ) as Type
        unique = parameter.unique
        ordered = parameter.ordered
        lowerBound = parameter.lowerBound
        upperBound = parameter.upperBound
        UML2xtumlrtCppProfileTranslator.translateStereotypedElement( parameter, it )
    }

    /**
     * @param type     - A {@link org.eclipse.uml2.uml.DataType}
     * @return A {@link StructType} or {@link PrimitiveType}
     */
    dispatch def Type
    create
        if (dataType instanceof org.eclipse.uml2.uml.PrimitiveType)
            CommonFactory.eINSTANCE.createPrimitiveType
        else if (dataType instanceof org.eclipse.uml2.uml.Enumeration)
            CommonFactory.eINSTANCE.createEnumeration
        else
            CommonFactory.eINSTANCE.createStructuredType
    translate( org.eclipse.uml2.uml.DataType dataType )
    {
        name = dataType.name
        if (dataType instanceof org.eclipse.uml2.uml.PrimitiveType)
            initPrimitiveType( dataType, it as PrimitiveType  )
        else if (dataType instanceof org.eclipse.uml2.uml.Enumeration)
        {
            initEnumeration( dataType, it as Enumeration )
            if (dataType.isSystemElement)
                addSysAnnotation
        }
        else
        {
            initStructuredType( dataType, it as StructuredType )
            if (dataType.isSystemElement)
                addSysAnnotation
        }
    }

    protected dispatch def initStructuredType( org.eclipse.uml2.uml.DataType dataType, StructuredType newType )
    {
        for (attribute : dataType.ownedAttributes)
        {
            newType.attributes.add( translateElement(attribute) as Attribute )
        }
        for (operation : dataType.ownedOperations)
        {
            newType.operations.add( translateElement(operation) as Operation )
        }
    }

    protected def initPrimitiveType
    (
        org.eclipse.uml2.uml.PrimitiveType primitiveType,
        PrimitiveType newType
    )
    {
        switch (primitiveType)
        {
            case org.eclipse.uml2.types.TypesPackage.eINSTANCE.boolean:             newType.name = "Boolean"
            case org.eclipse.uml2.types.TypesPackage.eINSTANCE.integer:             newType.name = "Integer"
            case org.eclipse.uml2.types.TypesPackage.eINSTANCE.string:              newType.name = "String"
            case org.eclipse.uml2.types.TypesPackage.eINSTANCE.real:                newType.name = "Real"
            case org.eclipse.uml2.types.TypesPackage.eINSTANCE.unlimitedNatural:    newType.name = "UnlimitedNatural"
            default: newType.name = primitiveType.name
        }
    }

    protected def initEnumeration
    (
        org.eclipse.uml2.uml.Enumeration enumeration,
        Enumeration newEnumeration
    )
    {
        val it = newEnumeration
        for ( literal : enumeration.ownedLiterals )
        {
            literals.add( translateEnumerationLiteral( literal )  )
        }
    }

    protected def EnumerationLiteral translateEnumerationLiteral
    (
        org.eclipse.uml2.uml.EnumerationLiteral literal
    )
    {
        val it = CommonFactory.eINSTANCE.createEnumerationLiteral
        name = literal.label
        it
    }

    /**
     * @param type     - A {@link org.eclipse.uml2.uml.Type}
     * @return A {@link Type}
     */
    dispatch def Type translate( org.eclipse.uml2.uml.Type type )
    {
        // Do not translate unknown types
    }

    protected def
    create CommonFactory.eINSTANCE.createTypeDefinition
    translateTypeDefinition( org.eclipse.uml2.uml.DataType dataType )
    {
        type = translateElement( dataType ) as Type
    }


    /**
     * @param stateMachine  - A {@link org.eclipse.uml2.uml.StateMachine}
     * @return A {@link StateMachine}
     */
    dispatch def StateMachine translate( org.eclipse.uml2.uml.StateMachine stateMachine )
    {
        stateMachineTranslator.translateStateMachine( stateMachine )
    }

    /**
     * @param visibilityKind    - A {@link org.eclipse.uml2.uml.VisibilityKind}
     * @return The corresponding {@link VisibilityKind}
     */
    dispatch def translateEnum( org.eclipse.uml2.uml.VisibilityKind visibilityKind )
    {
        switch (visibilityKind)
        {
            case org.eclipse.uml2.uml.VisibilityKind.PUBLIC_LITERAL:    VisibilityKind.PUBLIC
            case org.eclipse.uml2.uml.VisibilityKind.PRIVATE_LITERAL:   VisibilityKind.PRIVATE
            case org.eclipse.uml2.uml.VisibilityKind.PROTECTED_LITERAL: VisibilityKind.PROTECTED
            default: VisibilityKind.PUBLIC
        }
    }

    /**
     * @param directionKind     - A {@link org.eclipse.uml2.uml.ParameterDirectionKind}
     * @return The corresponding {@link DirectionKind}
     */
    dispatch def translateEnum
    (
        org.eclipse.uml2.uml.ParameterDirectionKind directionKind
    )
    {
        switch (directionKind)
        {
            case org.eclipse.uml2.uml.ParameterDirectionKind.IN_LITERAL:    DirectionKind.IN
            case org.eclipse.uml2.uml.ParameterDirectionKind.OUT_LITERAL:   DirectionKind.OUT
            case org.eclipse.uml2.uml.ParameterDirectionKind.INOUT_LITERAL: DirectionKind.IN_OUT
            // The other possibility is that the ParameterDirectionKind is RETURN_LITERAL
            // which is detected by the translation of the operation and set as the
            // operation's returnType. Furthermore, there is a constraint that at most
            // one parameter can have ParameterDirectionKind as RETURN_LITERAL,
            // so the default case should never be reached.
            default: DirectionKind.OUT
        }
    }

    /**
     * @param behavior  - A {@link org.eclipse.uml2.uml.Behavior}
     * @return An {@link ActionCode} instance.
     */
    protected def translateActionCode( org.eclipse.uml2.uml.Behavior behavior )
    {
        val it = CommonFactory.eINSTANCE.createActionCode
        source = UML2CppUtil.getCppCode( behavior )
        it
    }

    protected def addSysAnnotation( NamedElement element )
    {
        val sysAnnotation = CommonFactory.eINSTANCE.createAnnotation
        sysAnnotation.name = XTUMLRTUtil.RTS_LIBRARY_NAME;
        val sysAnnotationParam = CommonFactory.eINSTANCE.createAnnotationParameter
        sysAnnotationParam.value = XTUMLRTUtil.RTS_LIBRARY_URI_STR
        sysAnnotation.parameters.add( sysAnnotationParam )
        element.annotations.add( sysAnnotation )
    }

    override resetTranslateCache( EObject element )
    {
        val key = newArrayList( element )
        _createCache_translate.remove( key )
    }

}
