/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppEnum;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppNamespace;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Enumerator;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Function;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.LinkageSpec;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CppNamespaceTest extends LangCppBaseTest
{
    @Test
    public void test01_simple()
    {
        CppNamespace cppNamespace = new CppNamespace( "N" );

        assertEquals( "N", cppNamespace.getName().getIdentifier() );

        assertTrue( cppNamespace.write( out ) );
        assertDeclFormattedAs(
                "namespace N",
                "{",
                "};" );
    }

    @Test
    public void test02_memberEnum()
    {
        CppNamespace cppNamespace = new CppNamespace( "N" );

        CppEnum member = new CppEnum( cppNamespace, "E", null );
        cppNamespace.addMember( member );

        Enumerator enumerator = member.add( "enumerator" );
        member.add( new Enumerator( "enumerator2", new ElementAccess( enumerator ) ) );

        assertTrue( cppNamespace.write( out ) );
        assertTrue( out.defn().write( enumerator.getName() ) );
        assertDeclFormattedAs(
                "namespace N",
                "{",
                "    enum E",
                "    {",
                "        enumerator,",
                "        enumerator2 = enumerator",
                "    };",
                "};" );
        assertDefnFormattedAs(
                "N::enumerator" );
    }

    @Test
    public void test03_memberFunction()
    {
        Function function = new Function( LinkageSpec.UNSPECIFIED, PrimitiveType.VOID, "function" );

        CppNamespace cppNamespace = new CppNamespace( "N" );
        cppNamespace.addMember( function );

        assertTrue( cppNamespace.write( out ) );
        assertDeclFormattedAs(
                "namespace N",
                "{",
                "    void function();",
                "};" );
        assertDefnFormattedAs(
                "void N::function()",
                "{",
                "}" );
    }
}
