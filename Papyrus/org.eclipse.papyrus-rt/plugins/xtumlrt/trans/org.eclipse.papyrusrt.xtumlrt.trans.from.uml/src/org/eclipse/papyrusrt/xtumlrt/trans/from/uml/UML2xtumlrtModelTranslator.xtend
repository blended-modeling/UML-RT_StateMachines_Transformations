/*******************************************************************************
* Copyright (c) 2015-2017 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.trans.from.uml

import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrusrt.xtumlrt.util.DetailedException
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind
import org.eclipse.papyrusrt.xtumlrt.common.ActionCode
import org.eclipse.papyrusrt.xtumlrt.common.Artifact
import org.eclipse.papyrusrt.xtumlrt.common.Attribute
import org.eclipse.papyrusrt.xtumlrt.common.Capsule
import org.eclipse.papyrusrt.xtumlrt.common.CapsuleKind
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory
import org.eclipse.papyrusrt.xtumlrt.common.Connector
import org.eclipse.papyrusrt.xtumlrt.common.ConnectorEnd
import org.eclipse.papyrusrt.xtumlrt.common.Dependency
import org.eclipse.papyrusrt.xtumlrt.common.DirectionKind
import org.eclipse.papyrusrt.xtumlrt.common.Entity
import org.eclipse.papyrusrt.xtumlrt.common.Enumeration
import org.eclipse.papyrusrt.xtumlrt.common.EnumerationLiteral
import org.eclipse.papyrusrt.xtumlrt.common.Generalization
import org.eclipse.papyrusrt.xtumlrt.common.LiteralNull
import org.eclipse.papyrusrt.xtumlrt.common.LiteralBoolean
import org.eclipse.papyrusrt.xtumlrt.common.LiteralInteger
import org.eclipse.papyrusrt.xtumlrt.common.LiteralReal
import org.eclipse.papyrusrt.xtumlrt.common.LiteralString
import org.eclipse.papyrusrt.xtumlrt.common.LiteralUnlimitedNatural
import org.eclipse.papyrusrt.xtumlrt.common.LiteralNatural
import org.eclipse.papyrusrt.xtumlrt.common.MultiplicityElement
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.common.OpaqueExpression
import org.eclipse.papyrusrt.xtumlrt.common.Operation
import org.eclipse.papyrusrt.xtumlrt.common.Package
import org.eclipse.papyrusrt.xtumlrt.common.Parameter
import org.eclipse.papyrusrt.xtumlrt.common.Port
import org.eclipse.papyrusrt.xtumlrt.common.PrimitiveType
import org.eclipse.papyrusrt.xtumlrt.common.Protocol
import org.eclipse.papyrusrt.xtumlrt.common.ProtocolBehaviourFeatureKind
import org.eclipse.papyrusrt.xtumlrt.common.RedefinableElement
import org.eclipse.papyrusrt.xtumlrt.common.Signal
import org.eclipse.papyrusrt.xtumlrt.common.StructuredType
import org.eclipse.papyrusrt.xtumlrt.common.Type
import org.eclipse.papyrusrt.xtumlrt.common.TypedMultiplicityElement
import org.eclipse.papyrusrt.xtumlrt.common.VisibilityKind
import org.eclipse.papyrusrt.xtumlrt.common.ValueSpecification
import org.eclipse.papyrusrt.xtumlrt.interactions.Interaction
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import org.eclipse.papyrusrt.xtumlrt.umlrt.PortKind
import org.eclipse.papyrusrt.xtumlrt.umlrt.PortRegistration
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTModel
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPassiveClass
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPort
import org.eclipse.papyrusrt.xtumlrt.umlrt.UmlrtFactory
import org.eclipse.papyrusrt.xtumlrt.util.ActionLangUtils
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTAnnotations
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTLogger
import org.eclipse.uml2.types.TypesPackage
import org.eclipse.uml2.uml.AggregationKind
import org.eclipse.uml2.uml.Behavior
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Collaboration
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.Property

import static extension org.eclipse.papyrusrt.xtumlrt.util.GeneralUtil.*
import static extension org.eclipse.papyrusrt.xtumlrt.external.predefined.RTSModelLibraryUtils.*
import static extension org.eclipse.papyrusrt.xtumlrt.external.predefined.UMLRTProfileUtil.*
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.getCopy
import org.eclipse.uml2.uml.Element

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

    static val ONE = CommonFactory.eINSTANCE.createLiteralInteger => [ value = 1 ]
    UML2xtumlrtSMTranslator stateMachineTranslator
    UML2xtumlrtInteractionsTranslator interactionTranslator

    new ()
    {
        super()
        this.stateMachineTranslator = new UML2xtumlrtSMTranslator( this )
        this.interactionTranslator = new UML2xtumlrtInteractionsTranslator( this )
    }

    def getStateMachineTranslator() { stateMachineTranslator }

    override getGenerated(Element umlElement)
    {
        // check delegates first
        stateMachineTranslator.getGenerated( umlElement ) 
            ?: interactionTranslator.getGenerated( umlElement )
            ?: super.getGenerated( umlElement )
    }

    override getSource( CommonElement element )
    {
        // check delegates first
        stateMachineTranslator.getSource( element )
            ?: interactionTranslator.getSource( element )
            ?: super.getSource( element )
    }

    protected def translateGeneralizationToRedefinition
    (
        Classifier element,
        RedefinableElement newElement
    )
    {
        if (element.generals !== null && !element.generals.empty)
        {
            var Classifier superClassifier = null
            if (element.isCapsule)
                superClassifier = element.generals.findFirst[ it.isCapsule ] as Class
            else if (element.isProtocol)
                superClassifier = element.generals.get(0) as Collaboration
            else
                superClassifier = element.generals.get(0) as Class
            if (superClassifier !== null)
                newElement.redefines = translateElement( superClassifier ) as RedefinableElement
        }
    }

    protected def translateDependencies
    (
        org.eclipse.uml2.uml.NamedElement element,
        NamedElement newElement
    )
    {
        if (element.clientDependencies !== null && !element.clientDependencies.empty)
        {
            for (dependency : element.clientDependencies)
            {
                if ( ! element.isCapsule 
                    || ! dependency.suppliers.exists[ it.isProtocol ] )
                    newElement.dependencies.add( translateElement( dependency ) as Dependency )
            }
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
        newElement.lowerBound = if (element.lowerValue !== null) getBoundValSpec( element.lowerValue ) else ONE.getCopy
        newElement.upperBound = if (element.upperValue !== null) getBoundValSpec( element.upperValue ) else ONE.getCopy
    }

    protected dispatch def getBoundValSpec( org.eclipse.uml2.uml.LiteralInteger valSpec )
    {
        CommonFactory.eINSTANCE.createLiteralInteger => [ value = valSpec.value ]
    }

    protected dispatch def getBoundValSpec( org.eclipse.uml2.uml.LiteralString valSpec )
    {
        CommonFactory.eINSTANCE.createLiteralString => [ value = valSpec.value?.trim ?: "1" ]
    }

    protected dispatch def getBoundValSpec( org.eclipse.uml2.uml.LiteralUnlimitedNatural valSpec )
    {
        CommonFactory.eINSTANCE.createLiteralNatural => [ value = valSpec.value ]
    }

    protected dispatch def getBoundValSpec( org.eclipse.uml2.uml.OpaqueExpression valSpec )
    {
        CommonFactory.eINSTANCE.createOpaqueExpression => [ body = ActionLangUtils.getCode(actionLanguage, valSpec) ]
    }

    protected dispatch def getBoundValSpec( org.eclipse.uml2.uml.ValueSpecification valSpec )
    {
        CommonFactory.eINSTANCE.createOpaqueExpression => [ body = valSpec.stringValue ?: "1" ]
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
            UmlrtFactory.eINSTANCE.createRTModel
        else if (packge.isProtocolContainer)
            translateProtocolContainer( packge )
        else
            CommonFactory.eINSTANCE.createPackage
    translate( org.eclipse.uml2.uml.Package packge )
    {
        name = packge.name
        if (packge instanceof org.eclipse.uml2.uml.Model)
        {
            initModel( packge, it as RTModel )
            if (packge.isRTSModelLibrary)
                addSysAnnotation
        }
        else if (packge.isProtocolContainer)
            {}
        else
            initPackage( packge, it as Package )
    }

    protected def initModel( org.eclipse.uml2.uml.Model model, RTModel newModel )
    {
        val it = newModel
        for (element : model.packagedElements)
        {
            if (element instanceof org.eclipse.uml2.uml.Interaction)
                interactions.addIfNotPresent( translateElement(element) as Interaction)
            else if (element instanceof Class)
                entities.addIfNotPresent( translateElement(element) as Entity )
            else if (element.isProtocolContainer)
                protocols.addIfNotPresent( translateProtocolContainer(element as org.eclipse.uml2.uml.Package) as Protocol )
            else if (element instanceof DataType)
                typeDefinitions.addIfNotPresent( translateTypeDefinition(element) )
            else if (element instanceof org.eclipse.uml2.uml.Package)
                packages.addIfNotPresent( translateElement(element) as Package )
            else if (element instanceof org.eclipse.uml2.uml.Artifact)
                containedArtifacts.addIfNotPresent( translateElement(element) as Artifact )
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
            if (element instanceof Class)
                entities.addIfNotPresent( translateElement(element) as Entity )
            else if (element.isProtocolContainer)
                protocols.addIfNotPresent( translateProtocolContainer(element as org.eclipse.uml2.uml.Package) as Protocol )
            else if (element instanceof DataType)
                typeDefinitions.addIfNotPresent( translateTypeDefinition(element) )
            else if (element instanceof org.eclipse.uml2.uml.Package)
                packages.addIfNotPresent( translateElement(element) as Package )
            else if (element instanceof org.eclipse.uml2.uml.Artifact)
                artifacts.addIfNotPresent( translateElement(element) as Artifact )
        }
    }

    /**
     * @param collaboration     - A {@link Collaboration}
     * @return A {@link Protocol} if the collaboration is a protocol, {@code null} otherwise
     */
    dispatch def Protocol
    create
        if (collaboration.isProtocol)
            CommonFactory.eINSTANCE.createProtocol
        else
        {
            XTUMLRTLogger.warning( "Non-protocol collaborations are not supported. Collaboration '" + collaboration.qualifiedName + "' will be ignored.")
            null
        }
    translate( Collaboration collaboration )
    {
        if (it === null) return;
        name = collaboration.name
        initProtocol( collaboration, it )
        val isSystemElement = collaboration.isSystemElement
        if (isSystemElement)
            addSysAnnotation
        translateGeneralizationToRedefinition( collaboration, it )
        translateRedefinableElement( collaboration, it )
        if (redefines === null && !isSystemElement)
        {
            val BaseCommProtocol = getBaseCommProtocol
            if (BaseCommProtocol === null)
            {
                XTUMLRTLogger.warning( "UMLRTBaseCommProtocol from the RTS Model Library was not found. This may cause errors in elements using rtBound and rtUnbound protocol messages.")
            }
            else if (collaboration !== BaseCommProtocol)
            {
                redefines = translateElement( BaseCommProtocol ) as Protocol
            }
        }
    }

    protected def Protocol initProtocol( Collaboration collaboration, Protocol newProtocol )
    {
        val it = newProtocol
        for (operation : collaboration.getInProtocolMessages( true ))
        {
            protocolBehaviourFeatures.add( translateElement(operation) as Signal )
        }
        for (operation : collaboration.getOutProtocolMessages( true ))
        {
            protocolBehaviourFeatures.add( translateElement(operation) as Signal )
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
        XTUMLRTAnnotations.setAnnotations( operation, it )
        if (operation.interface !== null && operation.interface.isRTMessageSet)
        {
            initSignal( operation, it as Signal )
            if (operation.isSystemElement)
                addSysAnnotation
        }
        else
            initOperation( operation, it as Operation )
        translateRedefinableElement( operation, it )
    }

    protected def initOperation( org.eclipse.uml2.uml.Operation operation, Operation newOperation )
    {
        val it = newOperation
        var org.eclipse.uml2.uml.Parameter returnResult = null
        for (parameter : operation.ownedParameters)
        {
            if (parameter.direction !== ParameterDirectionKind.RETURN_LITERAL)
                parameters.add( translateElement(parameter) as Parameter )
            else
                returnResult = parameter
        }
        if (returnResult !== null)
            returnType = translateElement( returnResult ) as TypedMultiplicityElement
        val actionCode = CommonFactory.eINSTANCE.createActionCode
        actionCode.source = ActionLangUtils.getCode( actionLanguage, operation )
        body = actionCode
        setAbstract( operation.isAbstract )
        setQuery( operation.isQuery )
        setStatic( operation.isStatic )
    }

    protected def initSignal( org.eclipse.uml2.uml.Operation operation, Signal newSignal )
    {
        val it = newSignal
        for (parameter : operation.ownedParameters)
        {
            parameters.add( translateElement(parameter) as Parameter )
        }
        val interface = operation.interface
        if (interface.isRTMessageSet)
        {
            val rtMsgKind = operation.interface.getRTMessageSet.rtMsgKind
            switch (rtMsgKind)
            {
                case RTMessageKind.IN:      kind = ProtocolBehaviourFeatureKind.IN
                case RTMessageKind.OUT:     kind = ProtocolBehaviourFeatureKind.OUT
                case RTMessageKind.IN_OUT:  kind = ProtocolBehaviourFeatureKind.INOUT
            }
            // Ensure that we translate its container protocol
            translateElement( operation.protocol )
        }
    }

    /**
     * @param klass     - A {@link Class}
     * @return An {@link Entity}: either a {@link Capsule}, {@link RTPassiveClass} or
     *          {@link xtClass}.
     */
    dispatch def Type
    create
        if (klass.isCapsule)
            CommonFactory.eINSTANCE.createCapsule
        else
            UmlrtFactory.eINSTANCE.createRTPassiveClass
    translate( Class klass )
    {
        name = klass.name
        XTUMLRTAnnotations.setAnnotations( klass, it )
        if (klass.isCapsule)
            initCapsule( klass, it as Capsule )
        else
            initRTPassiveClass( klass, it as RTPassiveClass )
        if (klass.isSystemElement)
            addSysAnnotation
        translateRedefinableElement( klass, it )
        translateGeneralizationToRedefinition( klass, it )
        translateDependencies( klass, it )
    }

    protected dispatch def initStructuredType( Class klass, StructuredType newType )
    {
        for (attribute : klass.ownedMembers.filter(org.eclipse.uml2.uml.Property))
        {
            if (!attribute.isRTPort && !attribute.isCapsulePart)
                newType.attributes.add( translateElement(attribute) as Attribute )
        }
        for (operation : klass.ownedMembers.filter(org.eclipse.uml2.uml.Operation))
        {
            newType.operations.add( translateElement(operation) as Operation )
        }
        for (generalization : klass.generalizations)
        {
            newType.generalizations.add( translateElement(generalization) as Generalization )
        }
    }

    protected def initBasicClass( Class klass, Entity newClass )
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
            stateMachine = klass.ownedMembers.findFirst[ it instanceof org.eclipse.uml2.uml.StateMachine ]
                           as org.eclipse.uml2.uml.StateMachine
        }
        if (stateMachine !== null)
            newClass.behaviour = translateElement( stateMachine ) as StateMachine
    }

    protected def initCapsule( Class klass, Capsule newCapsule )
    {
        val it = newCapsule
        initBasicClass( klass, it )
        for (part : klass.capsuleParts)
        {
            parts.add( translateElement(part) as CapsulePart )
        }
        for (port : klass.getRTPorts)
        {
            ports.add( translateElement(port) as Port )
        }
        for (connector : klass.getRTConnectors)
        {
            connectors.add( translateElement(connector) as Connector )
        }
    }

    protected def initRTPassiveClass( Class klass, RTPassiveClass newClass )
    {
        val it = newClass
        initBasicClass( klass, it )
    }

    /**
     * @param generalization  - A {@link org.eclipse.uml2.uml.Generalization}
     * @return A {@link Generalization}
     */
    dispatch def Generalization
    create CommonFactory.eINSTANCE.createGeneralization
    translate( org.eclipse.uml2.uml.Generalization generalization )
    {
        XTUMLRTAnnotations.setAnnotations( generalization, it )
        setSuper( translateFeature( generalization, "general", Class, StructuredType ) as StructuredType )
        setSub( translateFeature( generalization, "specific", Class, StructuredType ) as StructuredType )
    }

    /**
     * @param dependency  - A {@link org.eclipse.uml2.uml.Dependency}
     * @return A {@link Dependency}
     */
    dispatch def Dependency
    create CommonFactory.eINSTANCE.createDependency
    translate( org.eclipse.uml2.uml.Dependency dependency )
    {
        XTUMLRTAnnotations.setAnnotations( dependency, it )
        if (dependency.suppliers === null || dependency.suppliers.empty)
            XTUMLRTLogger.error( "Dependency '" + dependency.qualifiedName + "' has no suppliers" )
        if (dependency.clients === null || dependency.clients.empty)
            XTUMLRTLogger.error( "Dependency '" + dependency.qualifiedName + "' has no clients" )

        supplier = translateElement( dependency.suppliers.get( 0 ) ) as NamedElement
        client = translateElement( dependency.clients.get( 0 ) ) as NamedElement
    }

    /**
     * @param property  - A {@link Property}
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
    translate( Property property )
    {
        name = property.name
        XTUMLRTAnnotations.setAnnotations( property, it )
        if (property.isCapsulePart)
            initCapsulePart( property, it as CapsulePart )
        else if (property.isRTPort)
            initRTPort( property, it as RTPort )
        else
        {
            if ( property instanceof org.eclipse.uml2.uml.Port )
            {
                XTUMLRTLogger.warning("Translating port '" + property.qualifiedName + "' as an attribute. This may cause errors. This may be caused by missing the RTPort stereotype.")
            }
            initAttribute( property, it as Attribute )
        }
        translateRedefinableElement( property, it as RedefinableElement )
        translateMultiplicityElement( property, it as MultiplicityElement )
    }

    protected def initAttribute( Property property, Attribute newAttribute )
    {
        val it = newAttribute
        val typeMaybeNull = XTUMLRTAnnotations.getProperty( it, "AttributeProperties", "type" ) !== null
        type = translateFeature( property, "type", org.eclipse.uml2.uml.Type, Type, typeMaybeNull ) as Type
        visibility = translateEnumFeature( property, "visibility", org.eclipse.uml2.uml.VisibilityKind, VisibilityKind ) as VisibilityKind
        setDefault( translateFeature( property, "defaultValue", org.eclipse.uml2.uml.ValueSpecification, ValueSpecification, true ) as ValueSpecification )
        setStatic( property.isStatic )
        readOnly = property.readOnly
        it
    }

    protected def initCapsulePart( Property property, CapsulePart newCapsulePart )
    {
        val it = newCapsulePart
        type = translateFeature( property, "type", Class, Capsule ) as Capsule
        if (property.lower == 0)
        {
            if (property.aggregation == AggregationKind.SHARED_LITERAL)
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
            //If the lower bound is not zero and not equal to the upper bound, 
            //this is flagged as an error.            
            if(property.lower != property.upper)
            {
               throw new DetailedException( property.name + " : capsule part multiplicity - the lower bound is not zero, and not equal to the upper bound" )                
            }
            kind = CapsuleKind.FIXED
        }
    }

    protected def initPort( Property property, Port newPort )
    {
        val it = newPort
        type = translateFeature( property, "type", Collaboration, Protocol ) as Protocol
        conjugate = (property as org.eclipse.uml2.uml.Port).conjugated
        it
    }

    protected def initRTPort( Property property, RTPort newRTPort )
    {
        val it = newRTPort
        type = translateFeature( property, "type", Collaboration, Protocol ) as Protocol
        conjugate = (property as org.eclipse.uml2.uml.Port).conjugated
        val rtPortStereotype = property.getRTPort
        if (rtPortStereotype !== null)
        {
            // workaround - publish not always set correctly in UI tooling
            // 481943 
            var isPublish = rtPortStereotype.publish
            publish = isPublish || (!rtPortStereotype.wired && rtPortStereotype.base_Port.service)

            notification = rtPortStereotype.notification
            wired = rtPortStereotype.wired
            switch ( property.visibility )
            {
                case PUBLIC_LITERAL:    visibility = VisibilityKind.PUBLIC
                case PACKAGE_LITERAL:   visibility = VisibilityKind.PROTECTED
                case PROTECTED_LITERAL: visibility = VisibilityKind.PROTECTED
                case PRIVATE_LITERAL:   visibility = VisibilityKind.PRIVATE
            }
            switch ( rtPortStereotype.registration )
            {
                case AUTOMATIC:             registration = PortRegistration.AUTOMATIC
                case APPLICATION:           registration = PortRegistration.APPLICATION
                case AUTOMATIC_LOCKED:      registration = PortRegistration.AUTOMATICLOCKED
            }
            registrationOverride = rtPortStereotype.registrationOverride
            setPortKind( rtPortStereotype, it )
        }
    }

    protected def setPortKind( org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort stereotype, RTPort newRTPort )
    {
        val basePort = stereotype.base_Port
        val isBehaviour = basePort.behavior
        val isService = basePort.service
        val isWired = newRTPort.wired
        val visibility = newRTPort.visibility
        if ( isService && isBehaviour && isWired && visibility == VisibilityKind.PUBLIC )
            newRTPort.kind = PortKind.EXTERNAL
        else if ( ! isService && isBehaviour && isWired && visibility == VisibilityKind.PROTECTED )
            newRTPort.kind = PortKind.INTERNAL
        else if ( isService && ! isBehaviour && isWired && visibility == VisibilityKind.PUBLIC )
            newRTPort.kind = PortKind.RELAY
        else if ( ! isService && isBehaviour && ! isWired && visibility == VisibilityKind.PROTECTED )
            newRTPort.kind = PortKind.SAP
        else if ( isService && isBehaviour && ! isWired && visibility == VisibilityKind.PUBLIC )
            newRTPort.kind = PortKind.SPP
    }

    dispatch def LiteralNull
    create CommonFactory.eINSTANCE.createLiteralNull
    translate( org.eclipse.uml2.uml.LiteralNull valueSpec )
    {
    }

    dispatch def LiteralBoolean
    create CommonFactory.eINSTANCE.createLiteralBoolean
    translate( org.eclipse.uml2.uml.LiteralBoolean valueSpec )
    {
        value = valueSpec.value
    }

    dispatch def LiteralInteger
    create CommonFactory.eINSTANCE.createLiteralInteger
    translate( org.eclipse.uml2.uml.LiteralInteger valueSpec )
    {
        value = valueSpec.value
    }

    dispatch def LiteralReal
    create CommonFactory.eINSTANCE.createLiteralReal
    translate( org.eclipse.uml2.uml.LiteralReal valueSpec )
    {
        value = valueSpec.value
    }

    dispatch def LiteralString
    create CommonFactory.eINSTANCE.createLiteralString
    translate( org.eclipse.uml2.uml.LiteralString valueSpec )
    {
        value = valueSpec.value
    }

    dispatch def OpaqueExpression
    create CommonFactory.eINSTANCE.createOpaqueExpression
    translate( org.eclipse.uml2.uml.OpaqueExpression valueSpec )
    {
        body = ActionLangUtils.getCode(actionLanguage, valueSpec)
    }


    dispatch def LiteralUnlimitedNatural
    create
    {
        if (valueSpec.value == org.eclipse.uml2.uml.LiteralUnlimitedNatural.UNLIMITED)
        {
            CommonFactory.eINSTANCE.createLiteralUnlimited
        }
        else
        {
            CommonFactory.eINSTANCE.createLiteralNatural
        }
    }
    translate( org.eclipse.uml2.uml.LiteralUnlimitedNatural valueSpec )
    {
        if (it instanceof LiteralNatural)
        {
            value = valueSpec.value
        }
    }

    dispatch def ValueSpecification
    create 
    {
        if ( valueSpec.instance instanceof org.eclipse.uml2.uml.EnumerationLiteral)
        {
            translateEnumerationLiteral( valueSpec.instance as org.eclipse.uml2.uml.EnumerationLiteral )
        }
        else
        {
            CommonFactory.eINSTANCE.createLiteralString
        }
    }
    translate( org.eclipse.uml2.uml.InstanceValue valueSpec )
    {
        if ( valueSpec.instance instanceof org.eclipse.uml2.uml.EnumerationLiteral)
        {
        }
        else
        {
            val result = it as LiteralString
            val strVal = valueSpec.stringValue
            if (strVal !== null)
            {
                val l = strVal.length
                val char doubleQuotes = '"'
                if (strVal.charAt(0) === doubleQuotes && strVal.charAt(l - 1) === doubleQuotes)
                {
                    result.value = strVal.substring(1, l - 1)
                }
                else
                {
                    result.value = strVal
                }
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
        XTUMLRTAnnotations.setAnnotations( connector, it )
        for (end : connector.ends)
        {
            ends.add( translateElement(end) as ConnectorEnd )
        }
        translateRedefinableElement( connector, it )
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
                translateFeature( connectorEnd, "partWithPort", Property, CapsulePart ) as CapsulePart
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
        var maybenull = true
        XTUMLRTAnnotations.setAnnotations( parameter, it )
        if ( it instanceof Parameter )
        {
            name = parameter.name
            direction = translateEnumFeature( parameter, "direction", ParameterDirectionKind, DirectionKind ) as DirectionKind
            // Bug 479131: the type of a signal parameter may be null
            if ( parameter.operation.interface == null || ! parameter.operation.interface.isRTMessageSet )
                maybenull = false
        }
        type = translateFeature( parameter, "type", org.eclipse.uml2.uml.Type, Type, maybenull ) as Type
        translateMultiplicityElement( parameter, it )
    }

    /**
     * @param type     - A {@link DataType}
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
    translate( DataType dataType )
    {
        name = dataType.name
        XTUMLRTAnnotations.setAnnotations( dataType, it )
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
        translateRedefinableElement( dataType, it )
    }

    protected dispatch def initStructuredType( DataType dataType, StructuredType newType )
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
            case TypesPackage.eINSTANCE.boolean:             newType.name = "Boolean"
            case TypesPackage.eINSTANCE.integer:             newType.name = "Integer"
            case TypesPackage.eINSTANCE.string:              newType.name = "String"
            case TypesPackage.eINSTANCE.real:                newType.name = "Real"
            case TypesPackage.eINSTANCE.unlimitedNatural:    newType.name = "UnlimitedNatural"
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
    translateTypeDefinition( DataType dataType )
    {
        type = translateElement( dataType ) as Type
    }

    /**
     * @param artifact     - An {@link org.eclipse.uml2.uml.Artifact}
     * @return An {@link Artifact}
     */
    dispatch def Artifact 
    create CommonFactory.eINSTANCE.createArtifact
    translate( org.eclipse.uml2.uml.Artifact artifact )
    {
        name = artifact.name
        fileName = artifact.fileName
        XTUMLRTAnnotations.setAnnotations( artifact, it )
    }


    /**
     * @param element  - A {@link org.eclipse.uml2.uml.StateMachine}
     * @return A {@link StateMachine}
     */
    dispatch def StateMachine translate( org.eclipse.uml2.uml.StateMachine element )
    {
        stateMachineTranslator.translate( element ) as StateMachine
    }

    /**
     * @param element  - A {@link org.eclipse.uml2.uml.Interaction}
     * @return A {@link Interaction}
     */
    dispatch def Interaction translate( org.eclipse.uml2.uml.Interaction element )
    {
        interactionTranslator.translate( element ) as Interaction
    }

    // We add cases for UML elements which do not have a corresponding xtumlrt
    // element and/or which should not be generated.
    //
    // Note that since 'translate' is a dispatch method, the instance checking
    // done in the generated Java code is such that more specific classes are
    // checked before more general classes. Hence if the element is a state
    // machine, the method above will be executed, instead of the method below,
    // since StateMachine is a subclass of Behavior in UML. 
    dispatch def CommonElement translate( Behavior behavior ) {}

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
     * @param directionKind     - A {@link ParameterDirectionKind}
     * @return The corresponding {@link DirectionKind}
     */
    dispatch def translateEnum
    (
        ParameterDirectionKind directionKind
    )
    {
        switch (directionKind)
        {
            case ParameterDirectionKind.IN_LITERAL:    DirectionKind.IN
            case ParameterDirectionKind.OUT_LITERAL:   DirectionKind.OUT
            case ParameterDirectionKind.INOUT_LITERAL: DirectionKind.IN_OUT
            // The other possibility is that the ParameterDirectionKind is RETURN_LITERAL
            // which is detected by the translation of the operation and set as the
            // operation's returnType. Furthermore, there is a constraint that at most
            // one parameter can have ParameterDirectionKind as RETURN_LITERAL,
            // so the default case should never be reached.
            default: DirectionKind.OUT
        }
    }

    /**
     * @param behavior  - A {@link Behavior}
     * @return An {@link ActionCode} instance.
     */
    protected def translateActionCode( Behavior behavior )
    {
        val it = CommonFactory.eINSTANCE.createActionCode
        source = ActionLangUtils.getCode( actionLanguage, behavior )
        it
    }

    override resetTranslateCache( EObject element )
    {
        val key = newArrayList( element )
        _createCache_translate.remove( key )
    }

}
