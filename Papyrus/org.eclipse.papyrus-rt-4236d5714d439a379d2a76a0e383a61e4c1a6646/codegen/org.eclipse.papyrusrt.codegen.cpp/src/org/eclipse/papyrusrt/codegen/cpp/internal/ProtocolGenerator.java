/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.internal;

import org.eclipse.papyrusrt.codegen.cpp.AbstractCppGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern.Output;
import org.eclipse.papyrusrt.codegen.cpp.TypesUtil;
import org.eclipse.papyrusrt.codegen.cpp.rts.UMLRTRuntime;
import org.eclipse.papyrusrt.codegen.lang.cpp.Expression;
import org.eclipse.papyrusrt.codegen.lang.cpp.Type;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Constructor;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Enumerator;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberFunction;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Parameter;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Typedef;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.AbstractFunctionCall;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.AddressOfExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.MemberFunctionCall;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ReturnStatement;
import org.eclipse.papyrusrt.codegen.utils.XTUMLRTUtil;
import org.eclipse.papyrusrt.xtumlrt.common.Protocol;
import org.eclipse.papyrusrt.xtumlrt.common.Signal;

public class ProtocolGenerator extends AbstractCppGenerator
{
    private final Protocol protocol;

    public ProtocolGenerator( CppCodePattern cpp, Protocol protocol )
    {
        super( cpp );
        this.protocol = protocol;
    }

    @Override
    public String getLabel()
    {
        return super.getLabel() + ' ' + protocol.getName();
    }

    @Override
    public boolean generate()
    {
        CppClass cls = cpp.getWritableCppClass( CppCodePattern.Output.ProtocolClass, protocol );
        cls.addBase( CppClass.Access.PUBLIC, UMLRTRuntime.UMLRTProtocol.Element );
        cpp.getIdEnum( Output.SignalId, protocol );

        CppClass outSignals = cpp.getCppClass( CppCodePattern.Output.OutSignals, protocol );
        CppClass inSignals = cpp.getCppClass( CppCodePattern.Output.InSignals, protocol );

        Typedef baseRoleTypedef = new Typedef( outSignals.getType(), "Base" );
        cls.addMember( CppClass.Visibility.PUBLIC, baseRoleTypedef );

        Typedef conjRoleTypedef = new Typedef( inSignals.getType(), "Conjugate" );
        cls.addMember( CppClass.Visibility.PUBLIC, conjRoleTypedef );

        // The default interpretation is to send signal, so Base is for the set of signals
        // that can be sent.
        CppClass sendRole = getRole( CppCodePattern.Output.ProtocolBaseRole, baseRoleTypedef );
        CppClass recvRole = getRole( CppCodePattern.Output.ProtocolConjugateRole, conjRoleTypedef );

        for( Signal signal : XTUMLRTUtil.getAllUserSignals( protocol ) )
        {
            // Within the generated protocol class the signalId enumerator is accessed directly.
            // Using the code pattern's access factory function would produce a fully qualified
            // access expression.
            Enumerator sigEnumerator = cpp.getEnumerator( CppCodePattern.Output.SignalId, signal, XTUMLRTUtil.getOwner( signal ) ); // TODO: Which should be the context?
            switch( signal.getKind() )
            {
            case IN:
            {
                MemberFunction signalFunction = getSignalFunction( signal, sigEnumerator );
                inSignals.addMember( CppClass.Visibility.PUBLIC, signalFunction );
                recvRole.addMember( CppClass.Visibility.PUBLIC, getSignalRoleFunction( conjRoleTypedef, signalFunction, signal ) );
                break;
            }
            case OUT:
            {
                MemberFunction signalFunction = getSignalFunction( signal, sigEnumerator );
                outSignals.addMember( CppClass.Visibility.PUBLIC, signalFunction );
                sendRole.addMember( CppClass.Visibility.PUBLIC, getSignalRoleFunction( baseRoleTypedef, signalFunction, signal ) );
                break;
            }
            case INOUT:
            {
                MemberFunction signalFunction = getSignalFunction( signal, sigEnumerator );
                inSignals.addMember( CppClass.Visibility.PUBLIC, signalFunction );
                recvRole.addMember( CppClass.Visibility.PUBLIC, getSignalRoleFunction( conjRoleTypedef, signalFunction, signal ) );
    
                signalFunction = getSignalFunction( signal, sigEnumerator );
                outSignals.addMember( CppClass.Visibility.PUBLIC, signalFunction );
                sendRole.addMember( CppClass.Visibility.PUBLIC, getSignalRoleFunction( baseRoleTypedef, signalFunction, signal ) );
                break;
            }
            }
        }

        return true;
    }

    /* E.g.,
    class PingPongProtocol_baserole : protected UMLRTPortRole, private PingPongProtocol::Base
    {
    public:
        PingPongProtocol_baserole( UMLRTCommsPort * srcPort ) : UMLRTPortRole( srcPort ) { }
        UMLRTSignal pingSignal( int ball, int volley ) const
        {
            return PingPongProtocol::Base::pingSignal( srcPort, ball, volley );
        }
    }; */
    private CppClass getRole( CppCodePattern.Output kind, Typedef roleTypedef )
    {
        CppClass cls = cpp.getCppClass( kind, protocol );
        cls.addBase( CppClass.Access.PROTECTED, UMLRTRuntime.UMLRTProtocol.Element );
        cls.addBase( CppClass.Access.PRIVATE, roleTypedef );

        Constructor ctor = cpp.getConstructor( kind, protocol );
        Parameter param = new Parameter( UMLRTRuntime.UMLRTCommsPort.getType().ptr().const_(), "srcPort" );
        ctor.add( param );

        AbstractFunctionCall baseCtorCall = UMLRTRuntime.UMLRTProtocol.Ctor();
        baseCtorCall.addArgument( new ElementAccess( param ) );
        ctor.addBaseInitializer( baseCtorCall );

        return cls;
    }

    private MemberFunction getSignalFunction( Signal signal, Enumerator sigEnumerator )
    {
        Parameter sourcePort = new Parameter( UMLRTRuntime.UMLRTCommsPort.getType().ptr().const_(), "sourcePort" );

        MemberFunction f = new MemberFunction( UMLRTRuntime.UMLRTOutSignal.getType(), signal.getName(), Type.CVQualifier.CONST );
        f.add( sourcePort );

        Variable signalVar = new Variable( UMLRTRuntime.UMLRTOutSignal.getType(), "signal" );
        f.add( signalVar );

        Expression sizeofPayload = null;
        Expression encodePayload = null;
        if( ! signal.getParameters().isEmpty() )
        {
            org.eclipse.papyrusrt.xtumlrt.common.Parameter param = signal.getParameters().get( 0 );

            Parameter p = createSignalParameter( param );
            f.add( p );

            sizeofPayload
                = UMLRTRuntime.UMLRTObject.getSize(
                        TypesUtil.createRTTypeAccess( cpp, param, param.getType() ) );
            encodePayload
                = UMLRTRuntime.UMLRTOutSignal.encode(
                    new ElementAccess( signalVar ),
                    TypesUtil.createRTTypeAccess( cpp, param, param.getType() ),
                    new AddressOfExpr( new ElementAccess( p ) ) );
        }

        MemberFunctionCall call = new MemberFunctionCall( new ElementAccess( signalVar ), UMLRTRuntime.UMLRTSignal.initialize );
        call.addArgument( new ElementAccess( sigEnumerator ) );
        call.addArgument( new ElementAccess( sourcePort ) );
        call.addArgument( sizeofPayload == null ? new IntegralLiteral( "0" ) : sizeofPayload );
        f.add( call );

        if( encodePayload != null )
            f.add( encodePayload );

        f.add( new ReturnStatement( new ElementAccess( signalVar ) ) );
        return f;
    }

    private MemberFunction getSignalRoleFunction( Typedef roleTypedef, MemberFunction signalFunction, Signal signal )
    {
        MemberFunction f = new MemberFunction( UMLRTRuntime.UMLRTOutSignal.getType(), signal.getName(), Type.CVQualifier.CONST );

        MemberFunctionCall call = new MemberFunctionCall( roleTypedef, signalFunction );
        call.addArgument( new ElementAccess( UMLRTRuntime.UMLRTProtocol.srcPort ) );

        if( ! signal.getParameters().isEmpty() )
        {
            org.eclipse.papyrusrt.xtumlrt.common.Parameter param = signal.getParameters().get( 0 );

            Parameter p = createSignalParameter( param );
            f.add( p );

            call.addArgument( new ElementAccess( p ) );
        }

        f.add( new ReturnStatement( call ) );

        return f;
    }

    private Parameter createSignalParameter( org.eclipse.papyrusrt.xtumlrt.common.Parameter param )
    {
        Type paramType = TypesUtil.createCppType( cpp, param, param.getType() );

        // Non-primitive types should be passed to the signal function as const references.
        if( ! ( paramType instanceof PrimitiveType )
         && ! paramType.isIndirect() )
            paramType = paramType.const_().ref();

        return new Parameter( paramType, param.getName() );
    }
}
