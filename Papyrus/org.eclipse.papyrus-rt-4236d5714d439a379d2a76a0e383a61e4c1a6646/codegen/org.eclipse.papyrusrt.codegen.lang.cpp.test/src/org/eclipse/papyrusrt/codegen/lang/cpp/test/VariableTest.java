/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.papyrusrt.codegen.lang.cpp.element.Constructor;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.LinkageSpec;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Parameter;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.BlockInitializer;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ConstructorCall;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class VariableTest extends LangCppBaseTest
{
    @Test
    public void test01_simple()
    {
        Variable variable = new Variable( LinkageSpec.STATIC, PrimitiveType.INT, "simpleVar" );
        assertNotNull( variable );

        assertEquals( LinkageSpec.STATIC, variable.getLinkage() );
        assertEquals( PrimitiveType.INT, variable.getType() );
        assertEquals( "simpleVar", variable.getName().getIdentifier() );

        assertTrue( variable.write( out ) );
        assertDefnFormattedAs( "static int simpleVar;" );
    }

    @Test
    public void test02_initializer()
    {
        IntegralLiteral five = new IntegralLiteral( "5" );
        Variable variable = new Variable( LinkageSpec.STATIC, PrimitiveType.INT, "initVar", five );
        assertNotNull( variable );

        assertTrue( variable.write( out ) );
        assertDefnFormattedAs( "static int initVar = 5;" );
    }

    @Test
    public void test03_access()
    {
        Variable val = new Variable( PrimitiveType.INT, "val" );
        Variable ptr = new Variable( PrimitiveType.INT.ptr(), "ptr" );

        assertFalse( val.getType().isIndirect() );
        assertTrue( ptr.getType().isIndirect() );
    }

    @Test
    public void test04_initWithCtor()
    {
        CppClass cls = new CppClass( "T" );
        Constructor ctor = new Constructor();
        ctor.add( new Parameter( PrimitiveType.INT, "p" ) );
        cls.addMember( CppClass.Visibility.PUBLIC, ctor );

        ConstructorCall call = new ConstructorCall( ctor, new IntegralLiteral( 5 ) );
        Variable var = new Variable( cls.getType(), "t", call );

        assertTrue( var.write( out ) );
        assertDeclFormattedAs( "T t;" );
        assertDefnFormattedAs( "T t( 5 );" );
    }

    @Test
    public void test05_array()
    {
        Variable val = new Variable( PrimitiveType.INT.arrayOf( null ), "val" );

        assertTrue( val.write( out ) );
        assertDeclFormattedAs( "int val[];" );
    }

    @Test
    public void test06_numInitializers()
    {
        assertEquals( 1, new Variable( PrimitiveType.INT, "var" ).getNumInitializedInstances() );

        assertEquals( 0, new Variable( PrimitiveType.INT.arrayOf( null ), "var" ).getNumInitializedInstances() );

        assertEquals(
            1,
            new Variable(
                PrimitiveType.INT.arrayOf( null ),
                "var",
                new IntegralLiteral( 0 ) ).getNumInitializedInstances() );
        assertEquals(
            2,
            new Variable(
                PrimitiveType.INT.arrayOf( null ),
                "var",
                new BlockInitializer( PrimitiveType.INT.arrayOf( null ),
                    new IntegralLiteral( 0 ),
                    new IntegralLiteral( 0 ) ) ).getNumInitializedInstances() );
    }
}
