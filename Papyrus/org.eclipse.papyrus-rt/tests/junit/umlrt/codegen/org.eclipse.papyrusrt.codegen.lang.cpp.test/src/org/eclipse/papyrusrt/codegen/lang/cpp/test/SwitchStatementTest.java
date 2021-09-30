/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.papyrusrt.codegen.lang.cpp.Expression;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.AbstractFunction;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Function;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.LinkageSpec;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.BreakStatement;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ExpressionStatement;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ReturnStatement;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.SwitchClause;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.SwitchStatement;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class SwitchStatementTest extends LangCppBaseTest
{
    @Test
    public void test01_simpleSwitch()
    {
        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "func" );
        assertNotNull( function );

        Expression one = new IntegralLiteral( "1" );
        assertNotNull( one );

        SwitchStatement switchStmt = new SwitchStatement( one );
        function.add( switchStmt );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static void func()",
                "{",
                "    switch( 1 )",
                "    {",
                "    }",
                "}" );
    }

    @Test
    public void test02_simpleCase()
    {
        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "func" );
        assertNotNull( function );

        Expression one = new IntegralLiteral( "1" );
        assertNotNull( one );

        SwitchStatement switchStmt = new SwitchStatement( one );
        assertNotNull( switchStmt );
        function.add( switchStmt );

        SwitchClause case1 = new SwitchClause( one );
        assertNotNull( case1 );
        case1.add( new ReturnStatement() );
        switchStmt.add( case1 );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static void func()",
                "{",
                "    switch( 1 )",
                "    {",
                "    case 1:",
                "        return;",
                "    }",
                "}" );
    }

    @Test
    public void test03_simpleDefault()
    {
        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "func" );
        assertNotNull( function );

        Expression one = new IntegralLiteral( "1" );
        assertNotNull( one );

        SwitchStatement switchStmt = new SwitchStatement( one );
        assertNotNull( switchStmt );
        function.add( switchStmt );

        SwitchClause def = new SwitchClause();
        assertNotNull( def );
        def.add( new ReturnStatement() );
        switchStmt.add( def );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static void func()",
                "{",
                "    switch( 1 )",
                "    {",
                "    default:",
                "        return;",
                "    }",
                "}" );
    }

    @Test
    public void test04_caseFallthrough()
    {
        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "func" );
        assertNotNull( function );

        Expression one = new IntegralLiteral( "1" );
        assertNotNull( one );

        SwitchStatement switchStmt = new SwitchStatement( one );
        assertNotNull( switchStmt );
        function.add( switchStmt );

        SwitchClause case_a = new SwitchClause( one );
        assertNotNull( case_a );
        case_a.setFallthrough( true );
        assertTrue( case_a.isFallthrough() );
        case_a.add( new ExpressionStatement( one ) );
        switchStmt.add( case_a );

        SwitchClause case_b = new SwitchClause( one );
        assertNotNull( case_b );
        case_b.add( new BreakStatement() );
        switchStmt.add( case_b );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static void func()",
                "{",
                "    switch( 1 )",
                "    {",
                "    case 1:",
                "        1;",
                "        // break intentionally omitted",
                "    case 1:",
                "        break;",
                "    }",
                "}" );
    }
}
