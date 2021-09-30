/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.eclipse.papyrusrt.codegen.lang.cpp.Expression;
import org.eclipse.papyrusrt.codegen.lang.cpp.HeaderFile;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.Dependency;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.DependencyList;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.ElementDependency;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.TypeDependency;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.ElementList;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberField;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberFunction;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass.Visibility;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.BlockInitializer;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IndexExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.MemberAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.name.FileName;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class DependencyTest extends LangCppBaseTest
{
    @Test
    public void test01_simpleForwardDecl()
    {
        CppClass cls = new CppClass( "T" );
        Variable var = new Variable( cls.getType().ptr(), "t" );

        ElementList elements = new ElementList( new FileName( "file" ) );
        elements.addElement( var );

        assertTrue( elements.write( out ) );
        assertDeclFormattedAs(
                "",
                "#ifndef FILE_HH",
                "#define FILE_HH",
                "",
                "class T;",
                "",
                "T * t;",
                "",
                "#endif" );
    }

    @Test
    public void test02_simpleInclude()
    {
        ElementList elements1 = new ElementList( new FileName( "file1" ) );
        CppClass cls = new CppClass( "T" );
        elements1.addElement( cls );

        ElementList elements2 = new ElementList( new FileName( "file2" ) );
        Variable var = new Variable( cls.getType(), "t" );
        elements2.addElement( var );

        assertTrue( elements2.write( out ) );
        assertDeclFormattedAs(
                "",
                "#ifndef FILE2_HH",
                "#define FILE2_HH",
                "",
                "#include \"file1.hh\"",
                "",
                "T t;",
                "",
                "#endif" );
    }

    @Test
    public void test03_bug61_indirectDependencies()
    {
        ElementList elements1 = new ElementList( new FileName( "file1" ) );
        CppClass cls = new CppClass( "T" );
        elements1.addElement( cls );

        ElementList elements1b = new ElementList( new FileName( "file1b" ) );
        CppClass clsb = new CppClass( "Tb" );
        elements1b.addElement( clsb );

        ElementList elements2 = new ElementList( new FileName( "file2" ) );
        CppClass cls2 = new CppClass( "T2" );
        cls2.addMember( CppClass.Visibility.PRIVATE, new MemberFunction( cls.getType(), "func" ) );
        cls2.addMember( CppClass.Visibility.PRIVATE, new MemberField( clsb.getType(), "field" ) );
        elements2.addElement( cls2 );

        assertTrue( elements2.write( out ) );
        assertDeclFormattedAs(
                "",
                "#ifndef FILE2_HH",
                "#define FILE2_HH",
                "",
                "#include \"file1.hh\"",
                "#include \"file1b.hh\"",
                "",
                "class T2",
                "{",
                "    T func();",
                "    Tb field;",
                "};",
                "",
                "#endif" );
    }

    @Test
    public void test04_sortingOfUse()
    {
        // The element names sort in a different order than the files.  This checks that
        // the sort is actually based on the file.

        CppClass Tb = new CppClass( "Tb" );
        ElementList file1 = new ElementList( new FileName( "file1" ) );
        file1.addElement( Tb );
        Dependency dep1 = new TypeDependency( Tb.getType() );

        CppClass Ta = new CppClass( "Ta" );
        ElementList file2 = new ElementList( new FileName( "file2" ) );
        file2.addElement( Ta );
        Dependency dep2 = new TypeDependency( Ta.getType() );
        
        assertEquals( -1, dep1.compareTo( dep2 ) );
    }

    @Test
    public void test05_sortingOfReference()
    {
        // The element names sort in a different order than the files.  This checks that
        // the sort is actually based on the file.

        CppClass T1 = new CppClass( "T1" );
        ElementList filea = new ElementList( new FileName( "filea" ) );
        filea.addElement( T1 );
        Dependency dep1 = new TypeDependency( T1.getType().ptr() );

        CppClass T2 = new CppClass( "T2" );
        ElementList fileb = new ElementList( new FileName( "fileb" ) );
        fileb.addElement( T2 );
        Dependency dep2 = new TypeDependency( T2.getType().ptr() );

        assertEquals( -1, dep1.compareTo( dep2 ) );
    }

    @Test
    public void test06_sortedDepList()
    {
        // Add elements in the inverse order and make sure they are generated properly.

        CppClass T1 = new CppClass( "T1" );
        ElementList file1 = new ElementList( new FileName( "file1" ) );
        file1.addElement( T1 );
        Variable varb1 = new Variable( T1.getType(), "varb1" );
        Variable varb2 = new Variable( T1.getType(), "varb2" );

        CppClass T2 = new CppClass( "T2" );
        ElementList file2 = new ElementList( new FileName( "file2" ) );
        file2.addElement( T2 );
        Variable vara = new Variable( T2.getType(), "vara" );

        ElementList elements = new ElementList( new FileName( "file" ) );
        elements.addElement( vara );
        elements.addElement( varb1 );
        elements.addElement( varb2 );

        assertTrue( elements.write( out ) );
        assertDeclFormattedAs(
                "",
                "#ifndef FILE_HH",
                "#define FILE_HH",
                "",
                "#include \"file1.hh\"",
                "#include \"file2.hh\"",
                "",
                "T2 vara;",
                "T1 varb1;",
                "T1 varb2;",
                "",
                "#endif" );
    }

    @Test
    public void test07_filteredDepList()
    {
        // Make sure the forward declaration is filtered out when the #include is already
        // present.

        CppClass T1 = new CppClass( "T1" );
        ElementList file1 = new ElementList( new FileName( "file1" ) );
        file1.addElement( T1 );

        CppClass T2 = new CppClass( "T2" );
        ElementList file2 = new ElementList( new FileName( "file2" ) );
        file2.addElement( T2 );

        Variable var0 = new Variable( T2.getType(), "var0" );
        Variable var1 = new Variable( T1.getType(), "var1" );
        Variable var2 = new Variable( T1.getType().ptr(), "var2" );

        ElementList elements = new ElementList( new FileName( "file" ) );
        elements.addElement( var0 );
        elements.addElement( var1 );
        elements.addElement( var2 );

        assertTrue( elements.write( out ) );
        assertDeclFormattedAs(
                "",
                "#ifndef FILE_HH",
                "#define FILE_HH",
                "",
                "#include \"file1.hh\"",
                "#include \"file2.hh\"",
                "",
                "T2 var0;",
                "T1 var1;",
                "T1 * var2;",
                "",
                "#endif" );
    }

    @Test
    public void test08_simpleTypeDep()
    {
        CppClass T = new CppClass( "T" );
        Variable v = new Variable( T.getType(), "v" );

        DependencyList deps = new DependencyList();
        assertTrue( v.addDependencies( deps ) );
        assertFalse( deps.isEmpty() );

        Iterator<Dependency> i = deps.iterator();
        assertNotNull( i );
        assertTrue( i.hasNext() );

        Dependency dep = i.next();
        assertNotNull( dep );
        assertTrue( dep instanceof TypeDependency );

        assertFalse( i.hasNext() );
    }

    @Test
    public void test09_elementAccessDep()
    {
        CppClass T = new CppClass( "T" );

        ElementAccess access = new ElementAccess( T );

        DependencyList deps = new DependencyList();
        assertTrue( access.addDependencies( deps ) );
        assertFalse( deps.isEmpty() );

        Iterator<Dependency> i = deps.iterator();
        assertNotNull( i );
        assertTrue( i.hasNext() );

        Dependency dep = i.next();
        assertNotNull( dep );
        assertTrue( dep instanceof ElementDependency );

        assertFalse( i.hasNext() );
    }

    @Test
    public void test10_memberAccessDep()
    {
        HeaderFile fieldFile = new HeaderFile( "field" );
        CppClass fieldType = new CppClass( "FieldClass" );
        MemberField field = new MemberField( fieldType.getType(), "field" );
        fieldType.setDefinedIn( fieldFile );

        HeaderFile containerFile = new HeaderFile( "container" );
        CppClass container = new CppClass( "ContainerClass" );
        container.addMember( CppClass.Visibility.PUBLIC, field );
        container.setDefinedIn( containerFile );

        Expression access = new MemberAccess( new ElementAccess( container ), field );

        DependencyList deps = new DependencyList();
        assertTrue( access.addDependencies( deps ) );
        assertFalse( deps.isEmpty() );

        Iterator<Dependency> i = deps.iterator();
        assertNotNull( i );
        assertTrue( i.hasNext() );

        Dependency dep = i.next();
        assertNotNull( dep );
        assertTrue( dep instanceof ElementDependency );

        assertFalse( i.hasNext() );

        assertTrue( deps.write( out.decl() ) );
        assertDeclFormattedAs( "#include \"container.hh\"" );
    }

    @Test
    public void test11_ptrInitializer()
    {
        // Bug 180: array initializers should introduce the type they are initializing.
        HeaderFile containerFile = new HeaderFile( "container" );

        CppClass cls = new CppClass( "cls" );
        cls.setDefinedIn( containerFile );

        BlockInitializer init2 = new BlockInitializer( cls.getType().ptr() );
        DependencyList deps2 = new DependencyList();
        assertTrue( init2.addDependencies( deps2 ) );
        Iterator<Dependency> i = deps2.iterator();
        assertNotNull( i );
        assertTrue( i.hasNext() );
        Dependency dep = i.next();
        assertNotNull( dep );
        assertFalse( i.hasNext() );
        assertTrue( deps2.write( out.decl() ) );
        assertDeclFormattedAs( "class cls;" );
    }

    @Test
    public void test12_arrayInitializer()
    {
        // Bug 180: array initializers should introduce the type they are initializing.
        HeaderFile containerFile = new HeaderFile( "container" );

        CppClass cls = new CppClass( "cls" );
        cls.setDefinedIn( containerFile );

        BlockInitializer init2 = new BlockInitializer( cls.getType().arrayOf( null ) );
        DependencyList deps2 = new DependencyList();
        assertTrue( init2.addDependencies( deps2 ) );
        Iterator<Dependency> i = deps2.iterator();
        assertNotNull( i );
        assertTrue( i.hasNext() );
        Dependency dep = i.next();
        assertNotNull( dep );
        assertFalse( i.hasNext() );
        assertTrue( deps2.write( out.decl() ) );
        assertDeclFormattedAs( "#include \"container.hh\"" );
    }

    @Test
    public void test13_indexedVarAccess()
    {
        // The following must depend on the struct definition of the variable.
        // &var->field[0]
        HeaderFile containerFile = new HeaderFile( "container" );

        CppClass cls = new CppClass( "cls" );
        cls.setDefinedIn( containerFile );
        MemberField field = new MemberField( PrimitiveType.INT, "field" );
        cls.addMember( Visibility.PUBLIC, field );

        Variable var = new Variable( cls.getType().ptr(), "var" );
        MemberAccess access = new MemberAccess( new ElementAccess( var ), field );

        IndexExpr indexed = new IndexExpr( access, new IntegralLiteral( 0 ) );

        // There should be 3 dependencies, one on the variable, one on the class (to
        // get the field) and one on the the field type (to index into the array).
        DependencyList deps = new DependencyList();
        assertTrue( indexed.addDependencies( deps ) );
        Iterator<Dependency> i = deps.iterator();
        assertNotNull( i );
        assertTrue( i.hasNext() );
        Dependency dep = i.next();
        assertNotNull( dep );
        assertTrue( i.hasNext() );
        dep = i.next();
        assertNotNull( dep );
        assertTrue( i.hasNext() );
        dep = i.next();
        assertNotNull( dep );
        assertFalse( i.hasNext() );
        assertTrue( deps.write( out.decl() ) );
        assertDeclFormattedAs( "#include \"container.hh\"" );
    }
}
