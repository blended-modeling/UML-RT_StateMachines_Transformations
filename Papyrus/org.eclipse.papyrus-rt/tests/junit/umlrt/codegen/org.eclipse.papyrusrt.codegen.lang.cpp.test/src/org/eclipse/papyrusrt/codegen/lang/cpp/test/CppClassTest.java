/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.papyrusrt.codegen.lang.cpp.Type;
import org.eclipse.papyrusrt.codegen.lang.cpp.Type.CVQualifier;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Constructor;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppEnum;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Destructor;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Function;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.LinkageSpec;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberField;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberFunction;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Parameter;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Typedef;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.UserElement.GenerationTarget;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ConstructorCall;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.MemberAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.MemberFunctionCall;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ReturnStatement;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CppClassTest extends LangCppBaseTest
{
    @Test
    public void test01_simple()
    {
        CppClass cppClass = new CppClass( "T" );
        assertNotNull( cppClass );

        assertEquals( "T", cppClass.getName().getIdentifier() );

        assertTrue( cppClass.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "};" );
    }

    @Test
    public void test02_publicFunction()
    {
        CppClass cppClass = new CppClass( "T" );
        assertNotNull( cppClass );

        MemberFunction f = new MemberFunction( PrimitiveType.VOID, "f" );
        cppClass.addMember( CppClass.Visibility.PUBLIC, f );

        assertTrue( cppClass.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "public:",
                "    void f();",
                "};" );
        assertDefnFormattedAs(
                "void T::f()",
                "{",
                "}" );
    }

    @Test
    public void test03_constFunction()
    {
        CppClass cppClass = new CppClass( "T" );
        assertNotNull( cppClass );

        MemberFunction f = new MemberFunction( PrimitiveType.VOID, "f", CVQualifier.CONST );
        cppClass.addMember( CppClass.Visibility.PRIVATE, f );

        assertTrue( cppClass.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "    void f() const;",
                "};" );
        assertDefnFormattedAs(
                "void T::f() const",
                "{",
                "}" );
    }

    @Test
    public void test04_constRetType()
    {
        CppClass cppClass = new CppClass( "T" );
        assertNotNull( cppClass );

        Type constInt = PrimitiveType.INT.const_();
        assertNotNull( constInt );

        MemberFunction f = new MemberFunction( constInt, "f", CVQualifier.CONST );
        cppClass.addMember( CppClass.Visibility.PRIVATE, f );

        assertTrue( cppClass.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "    const int f() const;",
                "};" );
        assertDefnFormattedAs(
                "const int T::f() const",
                "{",
                "}" );
    }

    @Test
    public void test05_mergeVisibilitySections()
    {
        CppClass cppClass = new CppClass( "T" );
        assertNotNull( cppClass );

        MemberFunction f = new MemberFunction( PrimitiveType.VOID, "f" );
        assertNotNull( f );

        cppClass.addMember( CppClass.Visibility.PRIVATE, f );
        cppClass.addMember( CppClass.Visibility.PUBLIC, f );
        cppClass.addMember( CppClass.Visibility.PUBLIC, f );
        cppClass.addMember( CppClass.Visibility.PROTECTED, f );

        assertTrue( cppClass.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "    void f();",
                "public:",
                "    void f();",
                "    void f();",
                "protected:",
                "    void f();",
                "};" );
    }

    @Test
    public void test06_publicVar()
    {
        CppClass cppClass = new CppClass( "T" );
        assertNotNull( cppClass );

        MemberField field = new MemberField( PrimitiveType.INT, "i" );
        assertNotNull( field );

        cppClass.addMember( CppClass.Visibility.PUBLIC, field );

        assertTrue( cppClass.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "public:",
                "    int i;",
                "};" );
    }

    @Test
    public void test07_statics()
    {
        CppClass cppClass = new CppClass( "T" );
        assertNotNull( cppClass );

        cppClass.addStaticMember( CppClass.Visibility.PUBLIC, new MemberField( PrimitiveType.INT, "i" ) );
        cppClass.addStaticMember( CppClass.Visibility.PUBLIC, new MemberField( PrimitiveType.INT, "i5", new IntegralLiteral( 5 ) ) );
        cppClass.addStaticMember( CppClass.Visibility.PUBLIC, new MemberFunction( PrimitiveType.VOID, "f" ) );

        assertTrue( cppClass.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "public:",
                "    static int i;",
                "    static int i5;",
                "    static void f();",
                "};" );
        assertDefnFormattedAs(
                "int T::i;",
                "int T::i5 = 5;",
                "",
                "",
                "void T::f()",
                "{",
                "}" );
    }

    @Test
    public void test08_struct()
    {
        CppClass cppStruct = new CppClass( CppClass.Kind.STRUCT, "S" );
        assertNotNull( cppStruct );

        cppStruct.addMember( CppClass.Visibility.PUBLIC, new MemberField( PrimitiveType.INT, "i" ) );
        cppStruct.addMember( CppClass.Visibility.PRIVATE, new MemberFunction( PrimitiveType.VOID, "f" ) );

        assertTrue( cppStruct.write( out ) );
        assertDeclFormattedAs(
                "struct S",
                "{",
                "    int i;",
                "private:",
                "    void f();",
                "};" );
    }

    @Test
    public void test09_memberEnum()
    {
        CppEnum member = new CppEnum( "E" );
        CppClass cls = new CppClass( "T" );

        cls.addMember( CppClass.Visibility.PUBLIC, member );

        assertTrue( cls.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "public:",
                "    enum E",
                "    {",
                "    };",
                "};" );
    }

    @Test
    public void test10_memberClass()
    {
        CppClass member = new CppClass( "member_t" );
        member.addMember( CppClass.Visibility.PRIVATE, new MemberFunction( PrimitiveType.VOID, "f" ) );

        CppClass cls = new CppClass( "T" );

        cls.addMember( CppClass.Visibility.PUBLIC, member );

        assertTrue( cls.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "public:",
                "    class member_t",
                "    {",
                "        void f();",
                "    };",
                "};" );
        assertDefnFormattedAs(
                "void T::member_t::f()",
                "{",
                "}" );
    }

    @Test
    public void test11_fieldAccess()
    {
        MemberField field = new MemberField( PrimitiveType.INT, "field" );
        CppClass cls = new CppClass( "T" );
        cls.addMember( CppClass.Visibility.PUBLIC, field );

        Variable val = new Variable( cls.getType(), "val" );
        Variable ptr = new Variable( cls.getType().ptr(), "ptr" );

        ElementAccess valAccess = new ElementAccess( val );
        ElementAccess ptrAccess = new ElementAccess( ptr );

        MemberAccess fieldFromVal = new MemberAccess( valAccess, field );
        MemberAccess fieldFromPtr = new MemberAccess( ptrAccess, field );

        assertTrue( fieldFromVal.write( out.decl() ) );
        out.decl().newline();
        assertTrue( fieldFromPtr.write( out.decl() ) );
        assertDeclFormattedAs(
                "val.field",
                "ptr->field" );
    }

    @Test
    public void test12_functionAccess()
    {
        MemberFunction func = new MemberFunction( PrimitiveType.INT, "func" );
        CppClass cls = new CppClass( "T" );
        cls.addMember( CppClass.Visibility.PUBLIC, func );

        Variable val = new Variable( cls.getType(), "val" );
        Variable ptr = new Variable( cls.getType().ptr(), "ptr" );

        ElementAccess valAccess = new ElementAccess( val );
        ElementAccess ptrAccess = new ElementAccess( ptr );

        MemberAccess funcFromVal = new MemberAccess( valAccess, func );
        MemberAccess funcFromPtr = new MemberAccess( ptrAccess, func );

        assertTrue( funcFromVal.write( out.decl() ) );
        out.decl().newline();
        assertTrue( funcFromPtr.write( out.decl() ) );
        assertDeclFormattedAs(
                "val.func",
                "ptr->func" );
    }

    @Test
    public void test13_functionCall()
    {
        MemberFunction func = new MemberFunction( PrimitiveType.INT, "func" );
        CppClass cls = new CppClass( "T" );
        cls.addMember( CppClass.Visibility.PUBLIC, func );

        Variable val = new Variable( cls.getType(), "val" );
        Variable ptr = new Variable( cls.getType().ptr(), "ptr" );

        ElementAccess valAccess = new ElementAccess( val );
        ElementAccess ptrAccess = new ElementAccess( ptr );

        MemberFunctionCall callVal = new MemberFunctionCall( valAccess, func );
        MemberFunctionCall callPtr = new MemberFunctionCall( ptrAccess, func );

        assertTrue( callVal.write( out.decl() ) );
        out.decl().newline();
        assertTrue( callPtr.write( out.decl() ) );
        assertDeclFormattedAs(
                "val.func()",
                "ptr->func()" );
    }

    @Test
    public void test14_typedefMember()
    {
        Typedef typedef = new Typedef( PrimitiveType.INT, "INT" );
        CppClass cls = new CppClass( "T" );
        cls.addMember( CppClass.Visibility.PRIVATE, typedef );

        assertTrue( cls.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "    typedef int INT;",
                "};" );
    }

    @Test
    public void test15_virtualFunction()
    {
        CppClass cppClass = new CppClass( "T" );
        assertNotNull( cppClass );

        MemberFunction f = new MemberFunction( PrimitiveType.VOID, "f" );
        f.setVirtual();
        assertNotNull( f );

        cppClass.addMember( CppClass.Visibility.PUBLIC, f );

        assertTrue( cppClass.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "public:",
                "    virtual void f();",
                "};" );
    }

    @Test
    public void test16_inheritance()
    {
        CppClass b1 = new CppClass( "B1" );
        CppClass b2 = new CppClass( "B2" );
        CppClass b3 = new CppClass( "B3" );

        CppClass cppClass = new CppClass( "T" );
        cppClass.addBase( CppClass.Access.PUBLIC, b1 );
        cppClass.addBase( CppClass.Access.PROTECTED, b2 );
        cppClass.addBase( CppClass.Access.PRIVATE, b3 );

        assertTrue( cppClass.write( out ) );
        assertDeclFormattedAs(
                "class T : public B1, protected B2, private B3",
                "{",
                "};" );
    }

    @Test
    public void test17_nestedBase()
    {
        CppClass nested = new CppClass( "Nested" );
        CppClass base = new CppClass( "Base" );
        base.addMember( CppClass.Visibility.PUBLIC, nested );

        CppClass derived = new CppClass( "Derived" );
        derived.addBase( CppClass.Access.PUBLIC, nested );

        assertTrue( derived.write( out ) );
        assertDeclFormattedAs(
                "class Derived : public Base::Nested",
                "{",
                "};" );
    }

    @Test
    public void test18_ctor()
    {
        CppClass t = new CppClass( "T" );
        Constructor ctor = new Constructor();
        t.addMember( CppClass.Visibility.PUBLIC, ctor );

        assertTrue( t.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "public:",
                "    T();",
                "};" );
        assertDefnFormattedAs(
                "T::T()",
                "{",
                "}" );
    }

    @Test
    public void test19_baseCtor()
    {
        CppClass b = new CppClass( "B" );
        Constructor bCtor = new Constructor();
        b.addMember( CppClass.Visibility.PUBLIC, bCtor );

        CppClass d = new CppClass( "D" );
        d.addBase( CppClass.Access.PUBLIC, b );
        Constructor dCtor = new Constructor();
        dCtor.addBaseInitializer( new ConstructorCall( bCtor, new IntegralLiteral( "5" ) ) );
        d.addMember( CppClass.Visibility.PUBLIC, dCtor );

        assertTrue( d.write( out ) );
        assertDeclFormattedAs(
                "class D : public B",
                "{",
                "public:",
                "    D();",
                "};" );
        assertDefnFormattedAs(
                "D::D()",
                ": B( 5 )",
                "{",
                "}" );
    }

    @Test
    public void test20_nonStaticFieldInit()
    {
        MemberField field1 = new MemberField( PrimitiveType.INT, "field1" );
        MemberField field2 = new MemberField( PrimitiveType.INT, "field2" );

        CppClass t = new CppClass( "T" );
        t.addMember( CppClass.Visibility.PRIVATE, field1 );
        t.addMember( CppClass.Visibility.PRIVATE, field2 );

        Constructor ctor = new Constructor();
        ctor.addFieldInitializer( field1, new IntegralLiteral( "5" ) );
        ctor.addFieldInitializer( field2, new IntegralLiteral( "42" ) );
        t.addMember( CppClass.Visibility.PUBLIC, ctor );

        assertTrue( t.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "public:",
                "    T();",
                "private:",
                "    int field1;",
                "    int field2;",
                "};" );
        assertDefnFormattedAs(
                "T::T()",
                ": field1( 5 )",
                ", field2( 42 )",
                "{",
                "}" );
    }

    @Test
    public void test21_469517()
    {
        CppClass cppClass = new CppClass( "T" );
        assertNotNull( cppClass );

        assertEquals( "T", cppClass.getName().getIdentifier() );

        MemberFunction f = new MemberFunction( cppClass.getType(), "oper" );
        f.add( new Parameter( cppClass.getType(), "param" ) );
        cppClass.addMember( CppClass.Visibility.PUBLIC, f );

        assertTrue( cppClass.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "public:",
                "    T oper( T param );",
                "};" );
    }

    @Test
    public void test22_friendFunction()
    {
        CppClass cppClass = new CppClass( "T" );
        assertNotNull( cppClass );

        Function f = new Function( LinkageSpec.UNSPECIFIED, PrimitiveType.VOID, "f" );
        f.add( new Parameter( cppClass.getType(), "param" ) );
        assertNotNull( f );

        cppClass.addFriendFunction( f );

        assertTrue( cppClass.write( out ) );
        assertDeclFormattedAs("class T",
                              "{",
                              "public:",
                              "    friend void f( T param );",
                              "};" );
        assertDefnFormattedAs("void f( T param )",
                              "{",
                              "}" );
    }

    @Test
    public void test23_inlineMemberFunction()
    {
        CppClass cppClass = new CppClass( "T" );
        assertNotNull( cppClass );

        MemberFunction f = new MemberFunction( PrimitiveType.VOID, "f" );
        assertNotNull( f );

        f.setInline();
        f.add( new ReturnStatement() );

        cppClass.addMember( CppClass.Visibility.PUBLIC, f );

        assertTrue( cppClass.write( out ) );
        assertDeclFormattedAs("class T",
                              "{",
                              "public:",
                              "    void f();",
                              "};" );
        assertDefnFormattedAs("inline void T::f()",
                              "{",
                              "    return;",
                              "}" );
    }

    @Test
    public void test24_virtualinheritance()
    {
        CppClass b1 = new CppClass( "B1" );
        CppClass b2 = new CppClass( "B2" );
        CppClass b3 = new CppClass( "B3" );

        CppClass cppClass = new CppClass( "T" );
        cppClass.addBase( CppClass.Access.PUBLIC, b1 );
        cppClass.addVirtualBase( CppClass.Access.PROTECTED, b2 );
        cppClass.addVirtualBase( CppClass.Access.PRIVATE, b3 );

        assertTrue( cppClass.write( out ) );
        assertDeclFormattedAs(
                "class T : public B1, protected virtual B2, private virtual B3",
                "{",
                "};" );
    }

    @Test
    public void test25_oneFileOnly()
    {
        CppClass declOnly = new CppClass( GenerationTarget.DECL_ONLY, "DeclOnly" );
        CppClass defnOnly = new CppClass( GenerationTarget.DEFN_ONLY, "DefnOnly" );

        declOnly.addMember( CppClass.Visibility.PUBLIC, new MemberField( PrimitiveType.INT, "field" ) );
        defnOnly.addMember( CppClass.Visibility.PUBLIC, new MemberField( PrimitiveType.INT, "field" ) );

        assertTrue( declOnly.write( out ) );
        assertTrue( defnOnly.write( out ) );
        assertDeclFormattedAs(
                "class DeclOnly",
                "{",
                "public:",
                "    int field;",
                "};" );
        assertDefnFormattedAs(
                "class DefnOnly",
                "{",
                "public:",
                "    int field;",
                "};" );
    }

    @Test
    public void test26_copyctor()
    {
        CppClass t = new CppClass( "T" );
        Constructor ctor = new Constructor();
        Parameter p = ctor.setCopyConstructor( t );
        t.addMember( CppClass.Visibility.PUBLIC, ctor );

        assertTrue( t.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "public:",
                "    T( const T & other );",
                "};" );
        assertDefnFormattedAs(
                "T::T( const T & other )",
                "{",
                "}" );
    }

    @Test
    public void test27_dtor()
    {
        CppClass t = new CppClass( "T" );
        Destructor dtor = new Destructor();
        t.addMember( CppClass.Visibility.PUBLIC, dtor );

        assertTrue( t.write( out ) );
        assertDeclFormattedAs(
                "class T",
                "{",
                "public:",
                "    ~T();",
                "};" );
        assertDefnFormattedAs(
                "T::~T()",
                "{",
                "}" );
    }

}
