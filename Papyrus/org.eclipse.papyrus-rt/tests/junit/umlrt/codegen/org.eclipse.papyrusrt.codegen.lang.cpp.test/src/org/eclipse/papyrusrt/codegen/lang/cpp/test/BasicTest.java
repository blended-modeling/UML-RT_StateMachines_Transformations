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
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.AddressOfExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.BinaryOperation;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.CastExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.DereferenceExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IndexExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.LogicalComparison;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.UnaryOperation;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ExpressionStatement;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ForStatement;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.WhileStatement;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class BasicTest extends LangCppBaseTest
{
    @Test
    public void test01_while()
    {
        IntegralLiteral cond = new IntegralLiteral( 0 );
        WhileStatement whileStmt = new WhileStatement( cond );

        assertTrue( whileStmt.write( out.decl() ) );
        assertDeclFormattedAs(
                "while( 0 )",
                "{",
                "}" );
    }

    @Test
    public void test02_simpleFor()
    {
        Variable i = new Variable( PrimitiveType.INT, "i", new IntegralLiteral( 0 ) );
        Expression cond
            = new LogicalComparison(
                    new ElementAccess( i ),
                    LogicalComparison.Operator.LESS_THAN,
                    new IntegralLiteral( 5 ) );
        ForStatement forStmt
            = new ForStatement(
                    i,
                    cond,
                    new UnaryOperation(
                            UnaryOperation.Operator.PRE_INCREMENT,
                            new ElementAccess( i ) ) );

        assertTrue( forStmt.write( out.decl() ) );
        assertDeclFormattedAs(
                "for( int i = 0; i < 5; ++i )",
                "{",
                "}" );
    }

    @Test
    public void test03_forWithPtr()
    {
        Variable array = new Variable( PrimitiveType.INT.arrayOf( new IntegralLiteral( 5 ) ), "array" );
        Variable i = new Variable( PrimitiveType.INT.ptr(), "i", new ElementAccess( array ) );
        Variable e = new Variable( PrimitiveType.INT.ptr(), "e", new AddressOfExpr( new IndexExpr( new ElementAccess( array ), new IntegralLiteral( 5 ) ) ) );
        Expression cond = new LogicalComparison( new ElementAccess( i ), LogicalComparison.Operator.LESS_THAN, new ElementAccess( e ) );
        ForStatement forStmt
            = new ForStatement(
                    i,
                    cond,
                    new UnaryOperation(
                            UnaryOperation.Operator.PRE_INCREMENT,
                            new ElementAccess( i ) ) );
        assertTrue( forStmt.addLoopVar( e ) );
        forStmt.add(
                new ExpressionStatement(
                    new BinaryOperation(
                        new DereferenceExpr( new ElementAccess( i ) ),
                        BinaryOperation.Operator.ASSIGN,
                        new IntegralLiteral( 42 ) ) ) );

        assertTrue( array.write( out.decl() ) );
        assertTrue( forStmt.write( out.decl() ) );
        assertDeclFormattedAs(
                "int array[5];",
                "for( int * i = array, e = &array[5]; i < e; ++i )",
                "    *i = 42;" );
    }

    @Test
    public void test04_cast()
    {
        CastExpr cast = new CastExpr( PrimitiveType.INT, new IntegralLiteral( 5 ) );

        assertTrue( cast.write( out.decl() ) );
        assertDeclFormattedAs( "(int)5" );
    }
}
