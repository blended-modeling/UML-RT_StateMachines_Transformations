/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppEnum;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Enumerator;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.BlockInitializer;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.CppEnumOrderedInitializer;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class InitializerTest extends LangCppBaseTest
{
    @Test
    public void test01_BlockInitializer()
    {
        CppEnum cppEnum = new CppEnum( "E" );
        Enumerator e2 = new Enumerator( "e2" );
        Enumerator e1 = new Enumerator( "e1" );
        Enumerator e3 = new Enumerator( "e3" );
        cppEnum.add( e2 );
        cppEnum.add( e1 );
        cppEnum.add( e3 );

        BlockInitializer init = new BlockInitializer( cppEnum.getType() );
        init.addExpression( new ElementAccess( e3 ) );
        init.addExpression( new ElementAccess( e1 ) );
        init.addExpression( new ElementAccess( e2 ) );

        assertTrue( init.write( out.decl() ) );
        assertDeclFormattedAs(
                "",
                "{",
                "    e3,",
                "    e1,",
                "    e2",
                "}" );
    }

    @Test
    public void test02_CppEnumOrderedInitializer()
    {
        CppEnum cppEnum = new CppEnum( "E" );
        Enumerator e2 = new Enumerator( "e2" );
        Enumerator e1 = new Enumerator( "e1" );
        Enumerator e3 = new Enumerator( "e3" );
        cppEnum.add( e2 );
        cppEnum.add( e1 );
        cppEnum.add( e3 );

        CppEnumOrderedInitializer init = new CppEnumOrderedInitializer( cppEnum, cppEnum.getType() );
        init.putExpression( e3, new ElementAccess( e3 ) );
        init.putExpression( e1, new ElementAccess( e1 ) );
        init.putExpression( e2, new ElementAccess( e2 ) );

        assertTrue( init.write( out.decl() ) );
        assertDeclFormattedAs(
                "",
                "{",
                "    e2,",
                "    e1,",
                "    e3",
                "}" );
    }
}
