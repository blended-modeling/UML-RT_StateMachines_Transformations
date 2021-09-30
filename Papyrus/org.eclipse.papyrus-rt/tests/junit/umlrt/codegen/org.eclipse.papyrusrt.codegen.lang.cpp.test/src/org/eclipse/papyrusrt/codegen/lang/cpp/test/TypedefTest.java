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
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Typedef;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class TypedefTest extends LangCppBaseTest
{
    @Test
    public void test01_simple()
    {
        Type intPtr = PrimitiveType.INT.ptr();
        assertNotNull( intPtr );

        Typedef typedef = new Typedef( intPtr, "int_ptr" );

        assertTrue( typedef.write( out ) );
        assertDeclFormattedAs( "typedef int * int_ptr;" );
    }

    @Test
    public void test02_reference()
    {
        Type intPtr = PrimitiveType.INT.ptr();
        assertNotNull( intPtr );

        Typedef typedef = new Typedef( intPtr, "int_ptr" );
        Type type = typedef.getType();
        assertNotNull( type );

        assertTrue( type.write( out.decl(), null ) );
        assertDeclFormattedAs( "int_ptr" );
    }
}
