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

import org.eclipse.papyrusrt.codegen.lang.cpp.element.AbstractFunction;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Function;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.LinkageSpec;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.Literal;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.CodeBlock;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ConditionalStatement;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ExpressionStatement;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class ConditionalStatementTest extends LangCppBaseTest
{
    @Test
    public void test01_simpleCondition()
    {
        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "func" );
        assertNotNull( function );

        Literal one = new IntegralLiteral( "1" );
        assertNotNull( one );

        ConditionalStatement cond = new ConditionalStatement();
        cond.add( one );
        function.add( cond );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static void func()",
                "{",
                "    if( 1 )",
                "    {",
                "    }",
                "}" );
    }

    @Test
    public void test02_conditionWithElse()
    {
        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "func" );
        assertNotNull( function );

        Literal one = new IntegralLiteral( "1" );
        assertNotNull( one );

        ConditionalStatement cond = new ConditionalStatement();
        cond.add( one );
        cond.defaultBlock().add( new ExpressionStatement( one ) );

        function.add( cond );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static void func()",
                "{",
                "    if( 1 )",
                "    {",
                "    }",
                "    else",
                "        1;",
                "}" );
    }

    @Test
    public void test03_conditionWithElseIf()
    {
        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "func" );
        assertNotNull( function );

        Literal one = new IntegralLiteral( "1" );
        assertNotNull( one );

        ConditionalStatement cond = new ConditionalStatement();
        cond.add( one );
        cond.add( one );

        function.add( cond );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static void func()",
                "{",
                "    if( 1 )",
                "    {",
                "    }",
                "    else if( 1 )",
                "    {",
                "    }",
                "}" );
    }

    @Test
    public void test04_danglingElse()
    {
        /*  This     can be this         or this
         *  if( 1 )         if( 1 )         if( 1 )
         *                  {               {
         *      if( 2 )         if( 2 )         if( 2 )
         *          ;               ;               ;
         *                                  }
         *      else            else        else
         *          ;               ;           ;
         *                  }
         */

        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "func" );
        assertNotNull( function );

        Literal one = new IntegralLiteral( "1" );
        Literal two = new IntegralLiteral( "2" );

        ConditionalStatement outerCond = new ConditionalStatement();
        CodeBlock outerBlock = outerCond.add( one );

        ConditionalStatement innerCond = new ConditionalStatement();
        innerCond.add( two );
        innerCond.defaultBlock().add( new ExpressionStatement( one ) );

        outerBlock.add( innerCond );

        function.add( outerCond );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static void func()",
                "{",
                "    if( 1 )",
                "    {",
                "        if( 2 )",
                "        {",
                "        }",
                "        else",
                "            1;",
                "    }",
                "}" );
    }
}
