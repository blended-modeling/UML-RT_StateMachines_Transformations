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

import org.eclipse.papyrusrt.codegen.lang.cpp.element.AbstractFunction;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.ElementList;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Function;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.LinkageSpec;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.name.FileName;
import org.eclipse.papyrusrt.codegen.lang.cpp.name.FolderName;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class ElementListTest extends LangCppBaseTest
{
    @Test
    public void test01_emptyFile()
    {
        FolderName folder = new FolderName( "src" );
        assertNotNull( folder );
        FileName filename = new FileName( folder, "file01" );
        assertNotNull( filename );

        ElementList file = new ElementList( filename );
        assertNotNull( file );

        Variable variable = new Variable( LinkageSpec.EXTERN, PrimitiveType.INT, "simpleVar" );
        assertNotNull( variable );
        file.addElement( variable );

        AbstractFunction function = new Function( LinkageSpec.EXTERN, PrimitiveType.VOID, "simpleFunction" );
        assertNotNull( function );
        file.addElement( function );

        assertTrue( file.write( out ) );
        assertDeclFormattedAs(
                "",
                "#ifndef SRC_FILE01_HH",
                "#define SRC_FILE01_HH",
                "",
                "",
                "extern int simpleVar;",
                "extern void simpleFunction();",
                "",
                "#endif" );
        assertDefnFormattedAs(
                "",
                "#include \"src/file01.hh\"",
                "",
                "int simpleVar;",
                "void simpleFunction()",
                "{",
                "}" );
    }

    @Test
    public void test02_selfInclude()
    {
        // Make sure that elements are ordered such that things that are used are earlier
        // than things that use them.
        Variable var1 = new Variable( LinkageSpec.STATIC, PrimitiveType.INT, "var1" );
        Variable var2 = new Variable( LinkageSpec.STATIC, PrimitiveType.INT, "var2", new ElementAccess( var1 ) );

        ElementList file = new ElementList( new FileName( "file" ) );
        file.addElement( var1 );
        file.addElement( var2 );

        assertTrue( file.write( out ) );
        assertDefnFormattedAs(
                "",
                "#include \"file.hh\"",
                "",
                "",
                "static int var1;",
                "static int var2 = var1;" );
    }
}
