/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.papyrusrt.codegen.lang.cpp.Expression;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.AbstractFunction;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Function;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.LinkageSpec;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Parameter;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.DereferenceExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ReturnStatement;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class FunctionTest extends LangCppBaseTest
{
    @Test
    public void test01_Simple()
    {
        Function function = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "simpleFunction" );
        assertNotNull( function );

        assertEquals( LinkageSpec.STATIC, function.getLinkage() );
        assertEquals( PrimitiveType.VOID, function.getReturnType() );
        assertEquals( "simpleFunction", function.getName().getIdentifier() );

        function.add( new ReturnStatement() );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static void simpleFunction()",
                "{",
                "    return;",
                "}" );
    }

    @Test
    public void test02_OneParam()
    {
        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.INT, "oneParamFunction" );
        assertNotNull( function );

        function.add( new Parameter( function.getReturnType(), "param1" ) );

        ElementAccess paramAccess = function.param( 0 );
        assertNotNull( paramAccess );
        function.add( new ReturnStatement( paramAccess ) );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static int oneParamFunction( int param1 )",
                "{",
                "    return param1;",
                "}" );
    }

    @Test
    public void test03_Deref()
    {
        AbstractFunction function = new Function( LinkageSpec.STATIC, PrimitiveType.INT, "derefFunction" );
        assertNotNull( function );

        function.add( new Parameter( function.getReturnType().ptr(), "param1" ) );

        assertEquals( PrimitiveType.INT, function.getReturnType() );

        Expression access = function.param( 0 );
        assertNotNull( access );

        Expression deref = new DereferenceExpr( access );
        assertNotNull( deref );

        function.add( new ReturnStatement( deref ) );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "static int derefFunction( int * param1 )",
                "{",
                "    return *param1;",
                "}" );
    }

    @Test
    public void test04_Inline()
    {
        Function function = new Function( LinkageSpec.UNSPECIFIED, PrimitiveType.VOID, "simpleFunction" );
        assertNotNull( function );

        function.setInline();
        function.add( new ReturnStatement() );

        assertTrue( function.write( out ) );
        assertDefnFormattedAs(
                "inline void simpleFunction()",
                "{",
                "    return;",
                "}" );
    }


}
