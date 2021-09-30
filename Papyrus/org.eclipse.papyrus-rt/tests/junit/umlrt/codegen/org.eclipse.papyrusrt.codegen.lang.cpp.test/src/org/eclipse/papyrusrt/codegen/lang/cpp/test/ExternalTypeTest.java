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

import org.eclipse.papyrusrt.codegen.lang.cpp.Name;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.ElementList;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable;
import org.eclipse.papyrusrt.codegen.lang.cpp.external.ExternalElement;
import org.eclipse.papyrusrt.codegen.lang.cpp.external.ExternalFileName;
import org.eclipse.papyrusrt.codegen.lang.cpp.external.ExternalFwdDeclarable;
import org.eclipse.papyrusrt.codegen.lang.cpp.external.ExternalHeaderFile;
import org.eclipse.papyrusrt.codegen.lang.cpp.external.ExternalTemplateName;
import org.eclipse.papyrusrt.codegen.lang.cpp.external.SystemHeaderFile;
import org.eclipse.papyrusrt.codegen.lang.cpp.name.FileName;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class ExternalTypeTest extends LangCppBaseTest
{
    @Test
    public void test01_simpleFwdDecl()
    {
        ExternalHeaderFile header = new ExternalHeaderFile( "external.hh" );
        ExternalFwdDeclarable extStruct = new ExternalFwdDeclarable( header, "aStruct", "struct aStruct" );

        // Make sure that external elements cannot be written

        assertTrue( extStruct.writeForwardDeclaration( out.decl() ) );
        assertDeclFormattedAs( "struct aStruct;" );
    }

    @Test
    public void test02_systemHeader()
    {
        SystemHeaderFile header = new SystemHeaderFile( "stdio.h" );
        assertTrue( header.writeInclude( out.decl() ) );
        assertDeclFormattedAs( "#include <stdio.h>" );
    }

    @Test
    public void test03_writeAsIncludeDependency()
    {
        ExternalHeaderFile header = new ExternalHeaderFile( new ExternalFileName( "aStruct.hh" ) );
        ExternalElement extStruct = new ExternalElement( header, "aStruct" );

        Variable var = new Variable( extStruct.getType(), "var" );
        ElementList file = new ElementList( new FileName( "varFile" ) );
        file.addElement( var );

        assertTrue( file.write( out ) );
        assertDeclFormattedAs(
                "",
                "#ifndef VARFILE_HH",
                "#define VARFILE_HH",
                "",
                "#include \"aStruct.hh\"",
                "",
                "aStruct var;",
                "",
                "#endif" );
    }

    @Test
    public void test04_writeAsFwdDecl()
    {
        ExternalHeaderFile header = new ExternalHeaderFile( "aStruct.hh" );
        ExternalFwdDeclarable extStruct = new ExternalFwdDeclarable( header, "aStruct", "struct aStruct" );

        Variable var = new Variable( extStruct.getType().ptr(), "var" );
        ElementList file = new ElementList( new FileName( "varFile" ) );
        file.addElement( var );

        assertTrue( file.write( out ) );
        assertDeclFormattedAs(
                "",
                "#ifndef VARFILE_HH",
                "#define VARFILE_HH",
                "",
                "struct aStruct;",
                "",
                "aStruct * var;",
                "",
                "#endif" );
    }

    @Test
    public void test05_simpleTemplate()
    {
        Name aClass = new Name( "AClass" );

        ExternalTemplateName T_n = new ExternalTemplateName( "T", aClass );
        assertEquals( "T<AClass>", T_n.getIdentifier() );

        T_n.writeQualified( out.decl(), null );
        assertDeclFormattedAs( "T<AClass>" );
    }
}
