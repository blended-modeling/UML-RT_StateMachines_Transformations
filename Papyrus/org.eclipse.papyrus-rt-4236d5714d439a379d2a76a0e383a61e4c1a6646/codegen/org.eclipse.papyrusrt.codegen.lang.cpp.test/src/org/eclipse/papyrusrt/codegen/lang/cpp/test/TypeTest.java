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

import org.eclipse.papyrusrt.codegen.lang.cpp.Type;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberField;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class TypeTest extends LangCppBaseTest
{
    @Test
    public void test01_simple()
    {
        Type type = PrimitiveType.INT;
        assertNotNull( type );

        assertTrue( type.write( out.decl(), null ) );
        assertDeclFormattedAs( "int" );
    }

    @Test
    public void test02_ptr()
    {
        Type type = PrimitiveType.INT.ptr();
        assertNotNull( type );

        assertTrue( type.write( out.decl(), null ) );
        assertDeclFormattedAs( "int *" );
    }

    @Test
    public void test03_const()
    {
        Type type = PrimitiveType.INT.const_();
        assertNotNull( type );

        assertTrue( type.write( out.decl(), null ) );
        assertDeclFormattedAs( "const int" );
    }

    @Test
    public void test04_ptrToConst()
    {
        Type type = PrimitiveType.INT.ptr().const_();
        assertNotNull( type );

        assertTrue( type.write( out.decl(), null ) );
        assertDeclFormattedAs( "const int *" );
    }

    @Test
    public void test05_typeofMemberField()
    {
        MemberField field = new MemberField( PrimitiveType.INT.ptr(), "field" );
        assertTrue( field.getType().isIndirect() );
    }

    @Test
    public void test06_ref()
    {
        Type ref = PrimitiveType.INT.ref();
        assertNotNull( ref );

        Type ptrRef = PrimitiveType.INT.ptr().ref();
        assertNotNull( ptrRef );

        assertTrue( ref.write( out.decl(), null ) );
        assertTrue( out.decl().newline() );
        assertTrue( ptrRef.write( out.decl(), null ) );
        assertDeclFormattedAs( "int &", "int * &" );
    }
}
