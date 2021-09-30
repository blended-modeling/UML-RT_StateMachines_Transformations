/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.internal;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern.Output;
import org.eclipse.papyrusrt.codegen.cpp.rts.UMLRTRuntime;
import org.eclipse.papyrusrt.codegen.instance.model.CapsuleInstance;
import org.eclipse.papyrusrt.codegen.instance.model.ICapsuleInstance;
import org.eclipse.papyrusrt.codegen.instance.model.IPortInstance;
import org.eclipse.papyrusrt.codegen.lang.cpp.Expression;
import org.eclipse.papyrusrt.codegen.lang.cpp.Type;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Constructor;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.ElementList;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Enumerator;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Function;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.LinkageSpec;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberField;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberFunction;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Parameter;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.AbstractFunctionCall;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.AddressOfExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.BinaryOperation;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.BlockInitializer;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.BooleanLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ConstructorCall;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.CppEnumOrderedInitializer;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ExpressionBlob;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IndexExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.MemberAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.MemberFunctionCall;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.NewExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.StringLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.external.StandardLibrary;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.CodeBlock;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ConditionalStatement;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ReturnStatement;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.SwitchClause;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.SwitchStatement;
import org.eclipse.papyrusrt.codegen.utils.XTUMLRTUtil;
import org.eclipse.papyrusrt.xtumlrt.common.Attribute;
import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.CapsuleKind;
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart;
import org.eclipse.papyrusrt.xtumlrt.common.Port;
import org.eclipse.papyrusrt.xtumlrt.common.Protocol;
import org.eclipse.papyrusrt.xtumlrt.common.RedefinableElement;

public class CapsuleGenerator extends BasicClassGenerator
{
    private final Capsule capsule;

    private Boolean internalPortsFound;

    public CapsuleGenerator( CppCodePattern cpp, Capsule capsule )
    {
        super( cpp, capsule );
        this.capsule = capsule;
    }

    @Override
    public String getLabel()
    {
        return super.getLabel() + ' ' + capsule.getName();
    }

    @Override
    public boolean generate()
    {
        CppClass cls = cpp.getWritableCppClass( CppCodePattern.Output.CapsuleClass, capsule );
        RedefinableElement element = capsule.getRedefines();
        Capsule modelSuperClass = null;
        if (element != null && element instanceof Capsule) modelSuperClass = (Capsule)element;
        CppClass superClass = null;
        if (modelSuperClass == null) {
            cls.addBase( CppClass.Access.PUBLIC, UMLRTRuntime.UMLRTCapsule.Element );
        }
        else {
            // In UML-RT a capsule may have one super-class at most.
            superClass = cpp.getWritableCppClass(CppCodePattern.Output.CapsuleClass, modelSuperClass);
            cls.addBase( CppClass.Access.PUBLIC, superClass);
        }

        Parameter param_cd = new Parameter( UMLRTRuntime.UMLRTCapsuleClass.getType().const_().ptr(), "cd" );
        Parameter param_st = new Parameter( UMLRTRuntime.UMLRTSlot.getType().ptr(), "st" );
        Parameter param_bp = new Parameter( UMLRTRuntime.UMLRTCommsPort.getType().ptr().ptr().const_(), "border" );
        Parameter param_internal = new Parameter( UMLRTRuntime.UMLRTCommsPort.getType().ptr().const_(), "internal" );
        Parameter param_stat = new Parameter( PrimitiveType.BOOL, "isStat" );

        Constructor ctor = cpp.getConstructor( CppCodePattern.Output.CapsuleClass, capsule );
        ctor.add( param_cd );
        ctor.add( param_st );
        ctor.add( param_bp  );
        ctor.add( param_internal );
        ctor.add( param_stat );

        AbstractFunctionCall baseCtorCall = null;
        if( modelSuperClass == null )
            baseCtorCall
                = UMLRTRuntime.UMLRTCapsule.Ctor(
                    StandardLibrary.NULL(), // we don't use the RTS interface
                    new ElementAccess( param_cd ),
                    new ElementAccess( param_st ),
                    new ElementAccess( param_bp ),
                    new ElementAccess( param_internal ),
                    new ElementAccess( param_stat ) );
        else
        {
            baseCtorCall = new ConstructorCall( cpp.getConstructor( CppCodePattern.Output.CapsuleClass, modelSuperClass ) );
            baseCtorCall.addArgument( new ElementAccess( param_cd ) );
            baseCtorCall.addArgument( new ElementAccess( param_st ) );
            baseCtorCall.addArgument( new ElementAccess( param_bp ) );
            baseCtorCall.addArgument( new ElementAccess( param_internal ) );
            baseCtorCall.addArgument( new ElementAccess( param_stat ) );
        }
        ctor.addBaseInitializer( baseCtorCall );

        // Connect all ports using a shallowly connected capsule instance.
        CapsuleInstance instance = new CapsuleInstance( capsule );
        instance.connect( null, true );

        // If the Capsule generation has been successful then add on the operations and attributes
        // in the base generator.
        return generatePorts( cls )
            && generateParts( cls, instance )
            && generateRTSFunctions( cls, instance )
            && super.generate( cls );
    }

    @Override
    protected MemberField generate( CppClass cls, Attribute attr )
    {
        MemberField field = super.generate( cls, attr );
        if( field == null )
            return null;

        Constructor ctor = cpp.getConstructor( CppCodePattern.Output.CapsuleClass, capsule );
        if( ctor == null )
            return field;

        // Add initializers for all non-static attributes that have initial values.
        if( ! attr.isStatic() )
        {
            String def = attr.getDefault();
            if( def != null && ! def.isEmpty() )
                ctor.addFieldInitializer( field, new ExpressionBlob( def ) );
        }

        return field;
    }

    /* E.g., Each contained part gets
     * const UMLRTCapsuleRole * role_name() const
     * {
     *     return slot->capsuleClass->subcapsuleRoles[...];
     * }
     */
    private boolean generateParts( CppClass capsuleClass, CapsuleInstance instance )
    {
        CppEnumOrderedInitializer subCapsuleInit
            = new CppEnumOrderedInitializer(
                cpp.getIdEnum( CppCodePattern.Output.PartId, capsule ),
                UMLRTRuntime.UMLRTCapsuleRole.getType().arrayOf( null ) );
        Variable subCapsules = new Variable( LinkageSpec.STATIC, UMLRTRuntime.UMLRTCapsuleRole.getType().arrayOf( null ).const_(), "roles", subCapsuleInit );

        for( CapsulePart part : XTUMLRTUtil.getAllCapsuleParts( capsule ) )
        {
            // The RTS does not allow an optional plugin role.
            boolean isPlugin = part.getKind() == CapsuleKind.PLUGIN;
            subCapsuleInit.putExpression(
                    cpp.getEnumerator( CppCodePattern.Output.PartId, part, capsule ),
                    new BlockInitializer(
                        UMLRTRuntime.UMLRTCapsulePart.getType(),
                        new StringLiteral( part.getName() ),
                        new AddressOfExpr( new ElementAccess( cpp.getVariable( CppCodePattern.Output.UMLRTCapsuleClass, part.getType() ) ) ),
                        new IntegralLiteral( XTUMLRTUtil.getLowerBound( part ) ),
                        new IntegralLiteral( XTUMLRTUtil.getUpperBound( part ) ),
                        new BooleanLiteral( ! isPlugin && XTUMLRTUtil.getUpperBound( part ) > XTUMLRTUtil.getLowerBound( part ) ),
                        new BooleanLiteral( isPlugin ) ) );

            MemberFunction function
                = new MemberFunction(
                        UMLRTRuntime.UMLRTCapsulePart.getType().const_().ptr(),
                        part.getName(),
                        Type.CVQualifier.CONST );

            Enumerator partId = cpp.getEnumerator( CppCodePattern.Output.PartId, part, capsule );
            function.add(
                new ReturnStatement(
                    new AddressOfExpr( 
                        new IndexExpr(
                            new MemberAccess( UMLRTRuntime.UMLRTCapsule.slot(), UMLRTRuntime.UMLRTSlot.parts ),
                            new ElementAccess( partId ) ) ) ) );

            capsuleClass.addMember( CppClass.Visibility.PROTECTED, function );
        }

        if( subCapsuleInit.getNumInitializers() > 0 )
            cpp.getElementList( CppCodePattern.Output.UMLRTCapsuleClass, capsule ).addElement( subCapsules );
        else
            subCapsules = null;

        generateUMLRTCapsuleClass( subCapsules, instance );
        return true;
    }

    private static enum PortKind
    {
        Border( CppCodePattern.Output.BorderPortId, UMLRTRuntime.UMLRTCapsule.borderPortsField ),
        Internal( CppCodePattern.Output.InternalPortId, UMLRTRuntime.UMLRTCapsule.internalPortsField );

        private PortKind( CppCodePattern.Output cppOutput, org.eclipse.papyrusrt.codegen.lang.cpp.element.NamedElement inheritedField )
        {
            this.cppOutput = cppOutput;
            this.inheritedField = inheritedField;
        }

        public final CppCodePattern.Output cppOutput;
        private final org.eclipse.papyrusrt.codegen.lang.cpp.element.NamedElement inheritedField;

        public Expression generatePortIdAccess( CppCodePattern cpp, Capsule capsule, Port port )
        {
            return cpp.getEnumeratorAccess( cppOutput, port, capsule );
        }

        /**
         * Generates an expression to access the specified port.  This overrides the default case
         * to access the given array variable.  This is used when generating non-member functions
         * like instantiate.
         */
        public Expression generatePortAccess( CppCodePattern cpp, Expression arrayVarAccess, Capsule capsule, Port port )
        {
            Expression expr = new IndexExpr( arrayVarAccess, generatePortIdAccess( cpp, capsule, port ) );

            // Internal ports are stored in an array of values, border ports in an array of pointers.
            return this == Internal ? new AddressOfExpr( expr ) : expr;
        }

        public Expression createArrayVarAccess()
        {
            return new ElementAccess( inheritedField );
        }

        /**
         * Generates an expression to access the specified port.  This default case uses the
         * port array variable that is inherited from the UMLRTCapsule base class.
         */
        public Expression generatePortAccess( CppCodePattern cpp, Capsule capsule, Port port )
        {
            return generatePortAccess( cpp, createArrayVarAccess(), capsule, port );
        }
    }

    private Expression generatePortAccess( Port port )
    {
        return ( XTUMLRTUtil.isInternalPort( port ) ? PortKind.Internal : PortKind.Border ).generatePortAccess( cpp, capsule, port );
    }

    private Expression generateSlotAccess( Expression slot, ICapsuleInstance capsuleInstance )
    {
        Expression partArrayAccess
            = new IndexExpr(
                new MemberAccess( slot, UMLRTRuntime.UMLRTSlot.parts ),
                cpp.getEnumeratorAccess( CppCodePattern.Output.PartId, capsuleInstance.getCapsulePart(), capsule ) );
        Expression slotAccess
            = new IndexExpr(
                    new MemberAccess( partArrayAccess, UMLRTRuntime.UMLRTCapsulePart.slots ),
                    new IntegralLiteral( capsuleInstance.getIndex() ) );
        return slotAccess;
    }

    private Expression generatePortAccess( Expression slot, ICapsuleInstance capsuleInstance, Port port )
    {
        if( XTUMLRTUtil.isInternalPort( port ) )
            return PortKind.Internal.generatePortAccess( cpp, capsule, port );

        Expression portsAccess
            = new MemberAccess(
                    generateSlotAccess( slot, capsuleInstance ),
                    UMLRTRuntime.UMLRTSlot.ports );
        return new AddressOfExpr( PortKind.Border.generatePortAccess( cpp, portsAccess, capsuleInstance.getType(), port ) );
    }

    private Expression generatePortAccess( PortKind portKind, org.eclipse.papyrusrt.codegen.lang.cpp.element.NamedElement arrayVar, IPortInstance port )
    {
        return portKind.generatePortAccess( cpp, new ElementAccess( arrayVar ), port.getContainer().getType(), port.getType() );
    }

    private Expression generateBorderPortAccess( org.eclipse.papyrusrt.codegen.lang.cpp.element.NamedElement borderPorts, IPortInstance port )
    {
        return generatePortAccess( PortKind.Border, borderPorts, port );
    }

    private boolean generatePorts( CppClass capsuleClass )
    {
        for( Port port : XTUMLRTUtil.getAllRTPorts( capsule ) )
        {
            Protocol protocol = port.getType();
            if( protocol == null )
                continue;

            CppCodePattern.Output roleKind
                = port.isConjugate() ? CppCodePattern.Output.ProtocolConjugateRole : CppCodePattern.Output.ProtocolBaseRole;

            Type protocolRoleType = null;
            AbstractFunctionCall roleCtorCall = null;
            Expression portAccess = generatePortAccess( port );

            // If this is a system-defined protocol, then generate an external function call.
            if( XTUMLRTUtil.isSystemElement( protocol ) )
            {
                protocolRoleType = UMLRTRuntime.getSystemProtocolRole( protocol, roleKind == Output.ProtocolBaseRole );

                // If this system-defined protocol does not apply to this PortRole, then ignore
                // it and continue to the next port.
                if( protocolRoleType == null )
                    continue;

                roleCtorCall = UMLRTRuntime.getSystemProtocolRoleCtor( protocol, roleKind == Output.ProtocolBaseRole, portAccess );
            }
            else
            {
                // Otherwise this is a model-defined role.
                protocolRoleType = cpp.getCppClass( roleKind, protocol ).getType();

                Constructor roleCtor = cpp.getConstructor( roleKind, protocol );
                roleCtorCall = new ConstructorCall( roleCtor, portAccess );
            }

            MemberFunction function = new MemberFunction( protocolRoleType, port.getName(), Type.CVQualifier.CONST );
            function.add( new ReturnStatement( roleCtorCall ) );

            capsuleClass.addMember( CppClass.Visibility.PROTECTED, function );
        }

        return true;
    }

    private boolean hasInternalPorts()
    {
        if( internalPortsFound != null )
            return internalPortsFound.booleanValue();

        for( Port port : XTUMLRTUtil.getAllRTPorts( capsule ) )
            if( XTUMLRTUtil.isInternalPort( port ) )
                return internalPortsFound = Boolean.TRUE;
        return internalPortsFound = Boolean.FALSE;
    }

    /*
     * The instantiate function creates a new instance of the user's capsule.
     */
    private Function generateInstantiate( CapsuleInstance instance )
    {
        ElementList elementList = cpp.getElementList( CppCodePattern.Output.UMLRTCapsuleClass, capsule );

        Parameter slot = new Parameter( UMLRTRuntime.UMLRTSlot.getType().ptr(), "slot" );
        Parameter borderPorts = new Parameter( UMLRTRuntime.UMLRTCommsPort.getType().ptr().ptr().const_(), "borderPorts" );

        Function instantiate = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "instantiate_" + capsule.getName() );
        instantiate.add( new Parameter( UMLRTRuntime.UMLRTRtsInterface.getType().const_().ptr(), "rts" ) );
        instantiate.add( slot );
        instantiate.add( borderPorts );

        Variable internalPorts = null;
        if( hasInternalPorts() )
        {
            Variable capsuleClass = cpp.getVariable( CppCodePattern.Output.UMLRTCapsuleClass, capsule );
            internalPorts
                = new Variable(
                        UMLRTRuntime.UMLRTCommsPort.getType().ptr().const_(),
                        "internalPorts",
                        UMLRTRuntime.UMLRTFrameService.createPorts(
                            new ElementAccess( slot ),
                            new AddressOfExpr( new ElementAccess( capsuleClass ) ),
                            new MemberAccess(
                                new ElementAccess( capsuleClass ),
                                UMLRTRuntime.UMLRTCapsuleClass.numPortRolesInternal ),
                            new MemberAccess(
                                new ElementAccess( capsuleClass ),
                                UMLRTRuntime.UMLRTCapsuleClass.portRolesInternal ),
                            BooleanLiteral.FALSE() ) );
            instantiate.add( internalPorts );
        }

        ConstructorCall ctorCall = new ConstructorCall( cpp.getConstructor( CppCodePattern.Output.CapsuleClass, capsule ) );
        ctorCall.addArgument(
            new AddressOfExpr(
                new ElementAccess( cpp.getVariable( CppCodePattern.Output.UMLRTCapsuleClass, capsule ) ) ) );
        ctorCall.addArgument( new ElementAccess( slot ) );
        ctorCall.addArgument( new ElementAccess( borderPorts ) );
        ctorCall.addArgument( internalPorts == null ? StandardLibrary.NULL() : new ElementAccess( internalPorts ) );
        ctorCall.addArgument( BooleanLiteral.FALSE() ); // instantiate produces only non-static instances

        // Connect all border and internal ports as needed.
        for( IPortInstance port : instance.getPorts() )
        {
            int localIndex = 0;
            if( XTUMLRTUtil.isInternalPort( port.getType() ) )
                for( IPortInstance.IFarEnd far : port.getFarEnds() )
                    instantiate.add(
                        UMLRTRuntime.UMLRTFrameService.connectPorts(
                            generatePortAccess( PortKind.Internal, internalPorts, port ),
                            new IntegralLiteral( localIndex++ ),
                            generatePortAccess( new ElementAccess( slot ), far.getContainer(), far.getType() ),
                            new IntegralLiteral( far.getIndex() ) ) );
            else if( port.isRelay() )
                for( IPortInstance.IFarEnd far : port.getFarEnds() )
                    instantiate.add(
                        UMLRTRuntime.UMLRTFrameService.connectRelayPort(
                            generateBorderPortAccess( borderPorts, port ),
                            new IntegralLiteral( localIndex++ ),
                            far.getContainer().getCapsulePart() == null
                                ? generateBorderPortAccess( borderPorts, far.getOwner() )
                                : generatePortAccess( new ElementAccess( slot ), far.getContainer(), far.getType() ),
                            new IntegralLiteral( far.getIndex() ) ) );
            else
                for( IPortInstance.IFarEnd far : port.getFarEnds() )
                    instantiate.add(
                        UMLRTRuntime.UMLRTFrameService.connectPorts(
                            generateBorderPortAccess( borderPorts, port ),
                            new IntegralLiteral( localIndex++ ),
                            far.getContainer().getCapsulePart() == null
                                ? generateBorderPortAccess( borderPorts, far.getOwner() )
                                : generatePortAccess( new ElementAccess( slot ), far.getContainer(), far.getType() ),
                            new IntegralLiteral( far.getIndex() ) ) );
        }

        // Connect all part border ports to each other.
        Set<ICapsuleInstance> connected = new HashSet<ICapsuleInstance>();
        connected.add( instance );
        for( ICapsuleInstance sub : instance.getContained() )
        {
            for( IPortInstance port : sub.getPorts() )
            {
                if( XTUMLRTUtil.isInternalPort( port.getType() ) )
                    continue;

                int localId = -1;
                for( IPortInstance.IFarEnd far : port.getFarEnds() )
                {
                    // This is incremented first so that it will allow for farEnds that are skipped.
                    ++localId;

                    // Ignore farEnds that are not connected ones that are for ports on parts that
                    // have already been considered.
                    if( connected.contains( far.getContainer() ) )
                        continue;

                    AbstractFunctionCall connectCall
                        = UMLRTRuntime.UMLRTFrameService.connectPorts(
                            generatePortAccess( new ElementAccess( slot ), sub, port.getType() ),
                            new IntegralLiteral( localId ),
                            generatePortAccess( new ElementAccess( slot ), far.getContainer(), far.getType() ),
                            new IntegralLiteral( far.getIndex() ) );

                    instantiate.add( connectCall );
                }
            }

            connected.add( sub );
        }

        // Instantiate all non-optional instances.
        for( ICapsuleInstance sub : instance.getContained() )
        {
            if( sub.isDynamic() )
                continue;

            AbstractFunctionCall instantiateCall
                = new MemberFunctionCall(
                    new ElementAccess( cpp.getVariable( CppCodePattern.Output.UMLRTCapsuleClass, sub.getType() ) ),
                    UMLRTRuntime.UMLRTCapsuleClass.instantiate );
            instantiateCall.addArgument( StandardLibrary.NULL() ); // we don't use the rts interface
            instantiateCall.addArgument(
                new IndexExpr(
                    new MemberAccess(
                        new IndexExpr(
                            new MemberAccess( new ElementAccess( slot ), UMLRTRuntime.UMLRTSlot.parts ),
                            cpp.getEnumeratorAccess( CppCodePattern.Output.PartId, sub.getCapsulePart(), capsule ) ),
                        UMLRTRuntime.UMLRTCapsulePart.slots ),
                    new IntegralLiteral( sub.getIndex() ) ) );
            instantiateCall.addArgument(
                UMLRTRuntime.UMLRTFrameService.createBorderPorts(
                    new IndexExpr(
                        new MemberAccess(
                            new IndexExpr(
                                new MemberAccess( new ElementAccess( slot ), UMLRTRuntime.UMLRTSlot.parts ),
                                cpp.getEnumeratorAccess( CppCodePattern.Output.PartId, sub.getCapsulePart(), capsule ) ),
                            UMLRTRuntime.UMLRTCapsulePart.slots ),
                        new IntegralLiteral( sub.getIndex() ) ),
                    new MemberAccess(
                        new ElementAccess( cpp.getVariable( CppCodePattern.Output.UMLRTCapsuleClass, sub.getCapsulePart().getType() ) ),
                        UMLRTRuntime.UMLRTCapsuleClass.numPortRolesBorder ) ) );

            instantiate.add( instantiateCall );
        }

        instantiate.add(
            new BinaryOperation(
                new MemberAccess( new ElementAccess( slot ), UMLRTRuntime.UMLRTSlot.capsule ),
                BinaryOperation.Operator.ASSIGN,
                new NewExpr( ctorCall ) ) );

        elementList.addElement( instantiate );

        return instantiate;
    }

    private boolean generateBindPortFunction( CppClass cls, CapsuleInstance instance )
    {
        MemberFunction func = new MemberFunction( PrimitiveType.VOID, "bindPort" );
        func.setVirtual();
        cls.addMember( CppClass.Visibility.PUBLIC, func );

        Parameter isBorder = new Parameter( PrimitiveType.BOOL, "isBorder" );
        Parameter portId = new Parameter( PrimitiveType.INT, "portId" );
        Parameter index = new Parameter( PrimitiveType.INT, "index" );
        func.add( isBorder );
        func.add( portId );
        func.add( index );

        ConditionalStatement isBorderCond = new ConditionalStatement();

        CodeBlock isBorderBlock = isBorderCond.add( new ElementAccess( isBorder ) );
        SwitchStatement isBorderSwitch = new SwitchStatement( new ElementAccess( portId ) );
        isBorderBlock.add( isBorderSwitch );

        CodeBlock isNotBorderBlock = null;

        boolean hasContent = false;
        for( IPortInstance port : instance.getPorts() )
        {
            if( ! XTUMLRTUtil.isWired( port.getType() ) )
                continue;

            if( XTUMLRTUtil.isInternalPort( port.getType() ) )
            {
                hasContent = true;

                if( isNotBorderBlock == null )
                    isNotBorderBlock = isBorderCond.defaultBlock();

                isNotBorderBlock.add(
                    UMLRTRuntime.UMLRTFrameService.sendBoundUnbound(
                        PortKind.Internal.createArrayVarAccess(),
                        PortKind.Internal.generatePortIdAccess( cpp, capsule, port.getType() ),
                        new ElementAccess( index ),
                        BooleanLiteral.TRUE() ) );
            }
            else if( port.isRelay() )
            {
                SwitchClause clause = new SwitchClause( cpp.getEnumeratorAccess( CppCodePattern.Output.BorderPortId, port.getType(), capsule ) );

                for( IPortInstance.IFarEnd far : port.getFarEnds() )
                {
                    hasContent = true;
                    clause.add(
                        UMLRTRuntime.UMLRTFrameService.connectRelayPort(
                            generatePortAccess( port.getType() ),
                            new ElementAccess( index ),
                            generatePortAccess(
                                UMLRTRuntime.UMLRTCapsule.slot(),
                                far.getContainer(),
                                far.getOwner().getType() ),
                            new IntegralLiteral( far.getIndex() ) ) );
                    clause.add(
                        UMLRTRuntime.UMLRTFrameService.bindSubcapsulePort(
                            BooleanLiteral.TRUE(), // isBorder
                            new MemberAccess(
                                generateSlotAccess( UMLRTRuntime.UMLRTCapsule.slot(), far.getContainer() ),
                                UMLRTRuntime.UMLRTSlot.capsule ),
                            new ElementAccess( portId ),
                            new ElementAccess( index ) ) );
                }

                isBorderSwitch.add( clause );
            }
            else
            {
                SwitchClause clause = new SwitchClause( cpp.getEnumeratorAccess( CppCodePattern.Output.BorderPortId, port.getType(), capsule ) );
                clause.add(
                    UMLRTRuntime.UMLRTFrameService.sendBoundUnbound(
                        PortKind.Border.createArrayVarAccess(),
                        PortKind.Border.generatePortIdAccess( cpp, capsule, port.getType() ),
                        new ElementAccess( index ),
                        BooleanLiteral.TRUE() ) );

                hasContent = true;
                isBorderSwitch.add( clause );
            }
        }

        if( hasContent )
            func.add( isBorderCond );

        return true;
    }

    private boolean generateUnbindPortFunction( CppClass cls, CapsuleInstance instance )
    {
        MemberFunction func = new MemberFunction( PrimitiveType.VOID, "unbindPort" );
        func.setVirtual();
        cls.addMember( CppClass.Visibility.PUBLIC, func );

        Parameter isBorder = new Parameter( PrimitiveType.BOOL, "isBorder" );
        Parameter portId = new Parameter( PrimitiveType.INT, "portId" );
        Parameter index = new Parameter( PrimitiveType.INT, "index" );
        func.add( isBorder );
        func.add( portId );
        func.add( index );

        ConditionalStatement isBorderCond = new ConditionalStatement();

        CodeBlock isBorderBlock = isBorderCond.add( new ElementAccess( isBorder ) );
        SwitchStatement isBorderSwitch = new SwitchStatement( new ElementAccess( portId ) );
        isBorderBlock.add( isBorderSwitch );

        CodeBlock isNotBorderBlock = null;

        boolean hasContent = false;
        for( IPortInstance port : instance.getPorts() )
        {
            if( ! XTUMLRTUtil.isWired( port.getType() ) )
                continue;

            if( XTUMLRTUtil.isInternalPort( port.getType() ) )
            {
                hasContent = true;

                if( isNotBorderBlock == null )
                    isNotBorderBlock = isBorderCond.defaultBlock();

                isNotBorderBlock.add(
                    UMLRTRuntime.UMLRTFrameService.sendBoundUnbound(
                        PortKind.Internal.createArrayVarAccess(),
                        PortKind.Internal.generatePortIdAccess( cpp, capsule, port.getType() ),
                        new ElementAccess( index ),
                        BooleanLiteral.FALSE() ) );
                isNotBorderBlock.add(
                    UMLRTRuntime.UMLRTFrameService.disconnectPort(
                        generatePortAccess( port.getType() ),
                        new ElementAccess( index ) ) );
            }
            else if( port.isRelay() )
            {
                SwitchClause clause = new SwitchClause( cpp.getEnumeratorAccess( CppCodePattern.Output.BorderPortId, port.getType(), capsule ) );

                for( IPortInstance.IFarEnd far : port.getFarEnds() )
                {
                    hasContent = true;
                    clause.add(
                        UMLRTRuntime.UMLRTFrameService.unbindSubcapsulePort(
                            BooleanLiteral.TRUE(), // isBorder
                            new MemberAccess(
                                generateSlotAccess( UMLRTRuntime.UMLRTCapsule.slot(), far.getContainer() ),
                                UMLRTRuntime.UMLRTSlot.capsule ),
                            new ElementAccess( portId ),
                            new ElementAccess( index ) ) );
                }

                isBorderSwitch.add( clause );
            }
            else
            {
                SwitchClause clause = new SwitchClause( cpp.getEnumeratorAccess( CppCodePattern.Output.BorderPortId, port.getType(), capsule ) );
                clause.add(
                    UMLRTRuntime.UMLRTFrameService.sendBoundUnbound(
                        PortKind.Border.createArrayVarAccess(),
                        PortKind.Border.generatePortIdAccess( cpp, capsule, port.getType() ),
                        new ElementAccess( index ),
                        BooleanLiteral.FALSE() ) );
                clause.add(
                    UMLRTRuntime.UMLRTFrameService.disconnectPort(
                        generatePortAccess( port.getType() ),
                        new ElementAccess( index ) ) );

                hasContent = true;
                isBorderSwitch.add( clause );
            }
        }

        if( hasContent )
            func.add( isBorderCond );

        return true;
    }

    private boolean generateRTSFunctions( CppClass cls, CapsuleInstance instance )
    {
        return generateBindPortFunction( cls, instance )
            && generateUnbindPortFunction( cls, instance );
    }

    private Variable generateUMLRTCapsuleClass( Variable subCapsules, CapsuleInstance instance )
    {
        CppEnumOrderedInitializer border_init = null;
        CppEnumOrderedInitializer internal_init = null;
        for( Port port : XTUMLRTUtil.getAllRTPorts( capsule ) )
        {
            String regOverride = XTUMLRTUtil.getRegistrationOverride( port );

            // TODO some fields need values
            BlockInitializer init = new BlockInitializer( UMLRTRuntime.UMLRTCommsPortRole.getType() );
            init.addExpression( cpp.getEnumeratorAccess( CppCodePattern.Output.PortId, port, capsule ) ); 
            init.addExpression( new StringLiteral( port.getType().getName() ) );
            init.addExpression( new StringLiteral( port.getName() ) );
            init.addExpression( regOverride == null ? StandardLibrary.NULL() : new StringLiteral( regOverride ) );
            init.addExpression( new IntegralLiteral( XTUMLRTUtil.getLowerBound( port ) ) );
            init.addExpression( BooleanLiteral.from( XTUMLRTUtil.isAutomatic( port ) ) );
            init.addExpression( BooleanLiteral.from( port.isConjugate() ) );
            init.addExpression( BooleanLiteral.from( XTUMLRTUtil.isApplicationLocked( port ) ) );
            init.addExpression( BooleanLiteral.from( XTUMLRTUtil.isNotification( port ) ) );
            init.addExpression( BooleanLiteral.FALSE() ); // unsigned sap : 1; // True if the port is an SAP.
            init.addExpression( BooleanLiteral.FALSE() ); // unsigned spp : 1; // True if the port is an SPP.
            init.addExpression( BooleanLiteral.from( XTUMLRTUtil.isWired( port ) ) );

            if( ! XTUMLRTUtil.isInternalPort( port ) )
            {
                if( border_init == null )
                    border_init
                        = new CppEnumOrderedInitializer(
                            cpp.getIdEnum( CppCodePattern.Output.BorderPortId, capsule ),
                            UMLRTRuntime.UMLRTCommsPortRole.getType().arrayOf( null ) );

                border_init.putExpression(
                    cpp.getEnumerator( CppCodePattern.Output.BorderPortId, port, capsule ),
                    init );
            }
            else
            {
                if( internal_init == null )
                    internal_init
                        = new CppEnumOrderedInitializer(
                            cpp.getIdEnum( CppCodePattern.Output.InternalPortId, capsule ),
                            UMLRTRuntime.UMLRTCommsPortRole.getType().arrayOf( null ) );

                internal_init.putExpression(
                    cpp.getEnumerator( CppCodePattern.Output.InternalPortId, port, capsule ),
                    init );
            }
        }

        Variable portroles_border = null;
        if( border_init != null
         && border_init.getNumInitializers() > 0 )
        {
            portroles_border
                = new Variable(
                    LinkageSpec.STATIC,
                    UMLRTRuntime.UMLRTCommsPortRole.getType().const_().arrayOf( null ),
                    "portroles_border",
                    border_init );
            cpp.getElementList( CppCodePattern.Output.UMLRTCapsuleClass, capsule ).addElement( portroles_border );
        }

        Variable portroles_internal = null;
        if( internal_init != null
         && internal_init.getNumInitializers() > 0 )
        {
            portroles_internal
                = new Variable(
                    LinkageSpec.STATIC,
                    UMLRTRuntime.UMLRTCommsPortRole.getType().const_().arrayOf( null ),
                    "portroles_internal",
                    internal_init );
            cpp.getElementList( CppCodePattern.Output.UMLRTCapsuleClass, capsule ).addElement( portroles_internal );
        }

        RedefinableElement element = capsule.getRedefines();
        Capsule baseClass = null;
        if( element != null ) baseClass = (Capsule)element;
        BlockInitializer init
            = new BlockInitializer(
                    UMLRTRuntime.UMLRTCapsuleClass.getType(),
                    new StringLiteral( capsule.getName() ),
                    baseClass == null
                        ? StandardLibrary.NULL()
                        : new AddressOfExpr( new ElementAccess( cpp.getVariable( CppCodePattern.Output.UMLRTCapsuleClass, baseClass ) ) ),
                    new ElementAccess( generateInstantiate( instance ) ),
                    new IntegralLiteral( subCapsules == null ? 0 : subCapsules.getNumInitializedInstances() ),
                    subCapsules == null ? StandardLibrary.NULL() : new ElementAccess( subCapsules ),
                    new IntegralLiteral( border_init == null ? 0 : border_init.getNumInitializers() ),
                    portroles_border == null ? StandardLibrary.NULL() : new ElementAccess( portroles_border ),
                    new IntegralLiteral( internal_init == null ? 0 : internal_init.getNumInitializers() ),
                    portroles_internal == null ? StandardLibrary.NULL() : new ElementAccess( portroles_internal ) );

        Variable var = cpp.getVariable( CppCodePattern.Output.UMLRTCapsuleClass, capsule );
        cpp.getElementList( CppCodePattern.Output.UMLRTCapsuleClass, capsule ).addElement( var );
        var.setInitializer( init );
        return var;
    }
}
