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
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppEnum;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Enumerator;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.MemberAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CppEnumTest extends LangCppBaseTest
{
    @Test
    public void test01_simple()
    {
        CppEnum cppEnum = new CppEnum( "Ids" );
        assertNotNull( cppEnum );

        assertTrue( cppEnum.write( out ) );
        assertDeclFormattedAs(
                "enum Ids",
                "{",
                "};" );
    }

    @Test
    public void test02_enumerators()
    {
        CppEnum cppEnum = new CppEnum( "Ids" );
        assertNotNull( cppEnum );

        IntegralLiteral one = new IntegralLiteral( "1" );
        assertNotNull( one );

        Enumerator enumerator_1 = new Enumerator( "one", one );
        assertNotNull( enumerator_1 );
        cppEnum.add( enumerator_1 );

        Enumerator enumerator_2 = new Enumerator( "two" );
        assertNotNull( enumerator_2 );
        cppEnum.add( enumerator_2 );

        assertTrue( cppEnum.write( out ) );
        assertDeclFormattedAs(
                "enum Ids",
                "{",
                "    one = 1,",
                "    two",
                "};" );
    }

    @Test
    public void test03_bug47_accessing_enumerators()
    {
        CppEnum cppEnum = new CppEnum( "E" );
        Enumerator enumerator_1 = new Enumerator( "e" );
        cppEnum.add( enumerator_1 );

        assertTrue( new ElementAccess( enumerator_1 ).write( out.decl() ) );
        assertDeclFormattedAs( "e" );
    }

    @Test
    public void test04_accessingMemberEnumerators()
    {
        CppEnum cppEnum = new CppEnum( "E" );
        Enumerator enumerator_1 = new Enumerator( "e" );
        cppEnum.add( enumerator_1 );

        CppClass cls = new CppClass( "T" );
        cls.addMember( CppClass.Visibility.PRIVATE, cppEnum );
        enumerator_1.setParent( cls );

        assertTrue( new MemberAccess( cls, enumerator_1 ).write( out.decl() ) );
        assertDeclFormattedAs( "T::e" );
    }

    @Test
    public void test05_firstLiteral()
    {
        Expression two = new IntegralLiteral( 2 );

        CppClass cls = new CppClass( "T" );

        CppEnum cppEnum = new CppEnum( cls, "Ids", two );
        assertNotNull( cppEnum );

        cls.addMember( CppClass.Visibility.PRIVATE, cppEnum );

        Enumerator enumerator = cppEnum.add( "first" );
        assertNotNull( enumerator );

        assertNotNull( cppEnum.add( "second" ) );

        assertTrue( cls.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "    enum Ids",
                "    {",
                "        first = 2,",
                "        second",
                "    };",
                "};" );
    }
}
