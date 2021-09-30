/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;

import org.eclipse.papyrusrt.codegen.lang.cpp.CppWriter;
import org.eclipse.papyrusrt.codegen.lang.cpp.internal.CppFormatter;
import org.junit.After;
import org.junit.Before;

public class LangCppBaseTest
{
    private ByteArrayOutputStream declOut;
    private ByteArrayOutputStream defnOut;
    protected CppWriter out;

    @Before
    public void before()
    {
        declOut = new ByteArrayOutputStream();
        defnOut = new ByteArrayOutputStream();
        out = new CppWriter( CppFormatter.createMemoryBased( declOut ), CppFormatter.createMemoryBased( defnOut ) );
    }

    @After
    public void after()
    {
        out.close();
        out = null;

        System.out.println( "INTERFACE:" );
        System.out.println( declOut.toString() );
        System.out.println( "IMPLEMENTATION:" );
        System.out.println( defnOut.toString() );
    }

    private void assertFormattedAs( String generated, String... expected )
    {
        assertNotNull( generated );
        if( generated.isEmpty() )
            assertTrue( expected.length <= 0 || expected[0].isEmpty() );

        String[] actual = generated.split( "\n" );

        int len = Math.min( actual.length, expected.length );
        for( int i = 0; i < len; ++i )
            assertEquals( "line " + (i + 1), expected[i], actual[i] );

        if( actual.length > expected.length )
            fail( "line " + len + ": \"" + actual[len] + "\" was not expected" );
        if( expected.length > actual.length )
            fail( "line " + len + ": \"" + expected[len] + "\" was not generated" );
    }

    protected void assertDeclFormattedAs( String... expected )
    {
        assertFormattedAs( declOut.toString(), expected );
    }

    protected void assertDefnFormattedAs( String... expected )
    {
        assertFormattedAs( defnOut.toString(), expected );
    }
}
