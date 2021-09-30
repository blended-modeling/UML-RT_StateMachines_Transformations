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
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Macro;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MacroParameter;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.BinaryOperation;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.MacroExpansion;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.DefineDirective;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.UndefDirective;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class MacroTest extends LangCppBaseTest
{
    @Test
    public void test01_directives()
    {
        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "func" );
        assertNotNull( function );

        // #define M0a
        Macro m0a = new Macro( "M0a" );
        function.add( new DefineDirective( m0a ) );

        // #define M0b()
        Macro m0b = new Macro( "M0b" );
        m0b.addParameter( null );
        function.add( new DefineDirective( m0b ) );

        // #define M1a ( 1 )
        Macro m1a = new Macro( "M1a", new IntegralLiteral( "1" ) );
        function.add( new DefineDirective( m1a ) );

        // #define M1b() ( 1 )
        Macro m1b = new Macro( "M1b", new IntegralLiteral( "1" ) );
        m1b.addParameter( null );
        function.add( new DefineDirective( m1b ) );

        // #define M2( P ) ( 2 )
        Macro m2 = new Macro( "M2", new IntegralLiteral( "2" ) );
        m2.addParameter( new MacroParameter( "P" ) );
        function.add( new DefineDirective( m2 ) );

        function.add( new UndefDirective( m1b ) );
        function.add( new UndefDirective( m1a ) );
        function.add( new UndefDirective( m0b ) );
        function.add( new UndefDirective( m0a ) );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static void func()",
                "{",
                "    #define M0a",
                "    #define M0b()",
                "    #define M1a ( 1 )",
                "    #define M1b() ( 1 )",
                "    #define M2( P ) ( 2 )",
                "    #undef M1b",
                "    #undef M1a",
                "    #undef M0b",
                "    #undef M0a",
                "}" );
    }

    @Test
    public void test02_noParamNoArgs()
    {
        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "func" );
        assertNotNull( function );

        Macro m0 = new Macro( "M0" );
        function.add( new MacroExpansion( m0 ) );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static void func()",
                "{",
                "    M0;",
                "}" );
    }

    @Test
    public void test03_emptyParamNoArgs()
    {
        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "func" );
        assertNotNull( function );

        Macro m0 = new Macro( "M0" );
        m0.addParameter( null );
        function.add( new MacroExpansion( m0 ) );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static void func()",
                "{",
                "    M0();",
                "}" );
    }

    @Test
    public void test04_args()
    {
        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "func" );
        assertNotNull( function );

        MacroParameter param = new MacroParameter( "P" );
        Macro macro
            = new Macro(
                "M",
                new BinaryOperation(
                    new IntegralLiteral( 2 ),
                    BinaryOperation.Operator.MULTIPLY,
                    new ElementAccess( param ) ) );
        macro.addParameter( param );
        function.add( new DefineDirective( macro ) );

        MacroExpansion expansion = new MacroExpansion( macro );
        expansion.addArgument( new IntegralLiteral( 2 ) );
        function.add( expansion );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static void func()",
                "{",
                "    #define M( P ) ( 2 * P )",
                "    M( 2 );",
                "}" );
    }
}
