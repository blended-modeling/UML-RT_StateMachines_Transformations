/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.papyrusrt.codegen.lang.cpp.Name;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class NameTest extends LangCppBaseTest
{
    @Test
    public void test01_simple()
    {
        Name n = new Name( "n" );

        assertTrue( out.decl().write( n ) );
        assertDeclFormattedAs( "n" );
    }

    @Test
    public void test02_qualified()
    {
        Name n = new Name( "n" );

        CppClass p = new CppClass( "p" );
        n.setParent( p );

        assertTrue( out.decl().write( n ) );
        assertDeclFormattedAs( "p::n" );
    }
}
