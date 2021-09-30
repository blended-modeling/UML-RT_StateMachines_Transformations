/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.papyrusrt.codegen.lang.cpp.element.ElementList;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Function;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.LinkageSpec;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.AbstractFunctionCall;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.Sizeof;
import org.eclipse.papyrusrt.codegen.lang.cpp.external.StandardLibrary;
import org.eclipse.papyrusrt.codegen.lang.cpp.name.FileName;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class StandardLibraryTest extends LangCppBaseTest
{
    @Test
    public void test01_memset()
    {
        Variable var = new Variable( PrimitiveType.CHAR.arrayOf( null ), "buff" );

        AbstractFunctionCall call
            = StandardLibrary.memset(
                    new ElementAccess( var ),
                    new IntegralLiteral( 5 ),
                    new Sizeof( var ) );

        Function func = new Function( LinkageSpec.STATIC, PrimitiveType.VOID, "func" );
        func.add( call );

        ElementList file = new ElementList( new FileName( "file" ) );
        file.addElement( func );

        assertTrue( file.write( out ) );
        assertDeclFormattedAs(
                "",
                "#ifndef FILE_HH",
                "#define FILE_HH",
                "",
                "",
                "#endif" );
        assertDefnFormattedAs(
                "",
                "#include \"file.hh\"",
                "",
                "#include <string.h>",
                "",
                "static void func()",
                "{",
                "    memset( buff, 5, sizeof( buff ) );",
                "}" );
    }
}
