/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.papyrusrt.codegen.lang.cpp.Expression;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Constructor;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberFunction;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Parameter;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.BinaryOperation;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.CastExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.CharacterLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ConditionalOperator;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ConstructorCall;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.DeleteExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.MemberFunctionCall;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.NewExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class ExpressionTest extends LangCppBaseTest
{
    @Test
    public void test01_new()
    {
        CppClass t = new CppClass( "T" );

        Constructor ctor1 = new Constructor();
        t.addMember( CppClass.Visibility.PUBLIC, ctor1 );
        NewExpr expr1 = new NewExpr( new ConstructorCall( ctor1 ) );

        Constructor ctor2 = new Constructor();
        ctor2.add( new Parameter( PrimitiveType.INT, "i" ) );
        t.addMember( CppClass.Visibility.PUBLIC, ctor2 );
        NewExpr expr2 = new NewExpr( new ConstructorCall( ctor2, new IntegralLiteral( 5 ) ) );

        assertTrue( expr1.write( out.defn() ) );
        assertTrue( out.defn().newline() );
        assertTrue( expr2.write( out.defn() ) );
        assertDefnFormattedAs(
                "new T",
                "new T( 5 )" );
    }

    @Test
    public void test02_delete()
    {
        Variable var = new Variable( PrimitiveType.INT, "var" );
        DeleteExpr expr1 = new DeleteExpr( new ElementAccess( var ) );

        assertTrue( expr1.write( out.defn() ) );
        assertDefnFormattedAs(
                "delete var" );
    }

    @Test
    public void test03_conditionalOperator()
    {
        Expression expr
            = new ConditionalOperator(
                    new IntegralLiteral( 1 ),
                    new CharacterLiteral( "a" ),
                    new CharacterLiteral( "b" ) );
        assertTrue( expr.write( out.defn() ) );
        assertDefnFormattedAs(
                "1 ? 'a' : 'b'" );
    }

    @Test
    public void test04_paren()
    {
        assertTrue(
            new BinaryOperation(
                new IntegralLiteral( 3 ),
                BinaryOperation.Operator.MULTIPLY,
                new BinaryOperation(
                    new IntegralLiteral( 1 ),
                    BinaryOperation.Operator.ADD,
                    new IntegralLiteral( 2 ) ) )
            .write( out.defn() ) );
        assertTrue( out.defn().newline() );
        assertTrue(
                new BinaryOperation(
                    new IntegralLiteral( 3 ),
                    BinaryOperation.Operator.ADD,
                    new BinaryOperation(
                        new IntegralLiteral( 1 ),
                        BinaryOperation.Operator.MULTIPLY,
                        new IntegralLiteral( 2 ) ) )
                .write( out.defn() ) );
            assertTrue( out.defn().newline() );

        // E.g., ( (UMLRTInSignal & )msg.signal ).decode( &rts_decodeInfo, (void *)&rtdata, NULL );
        Variable var = new Variable( PrimitiveType.INT, "var" );
        MemberFunction func = new MemberFunction( PrimitiveType.VOID, "func" );
        MemberFunctionCall call
            = new MemberFunctionCall(
                new CastExpr( PrimitiveType.BOOL, new ElementAccess( var ) ),
                func );
        assertTrue( call.write( out.defn() ) );

        assertDefnFormattedAs(
                "3 * ( 1 + 2 )",
                "3 + 1 * 2",
                "( (bool)var ).func()" );
    }
}
