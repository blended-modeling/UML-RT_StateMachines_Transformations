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

/**
 * Unit tests for C++ types.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TypeTest extends LangCppBaseTest {
	@Test
	public void test01_simple() {
		Type type = PrimitiveType.INT;
		assertNotNull(type);

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("int");
	}

	@Test
	public void test02_ptr() {
		Type type = PrimitiveType.INT.ptr();
		assertNotNull(type);

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("int *");
	}

	@Test
	public void test03_const() {
		Type type = PrimitiveType.INT.const_();
		assertNotNull(type);

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("const int");
	}

	@Test
	public void test04_ptrToConst() {
		Type type = PrimitiveType.INT.ptr().const_();
		assertNotNull(type);

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("const int *");
	}

	@Test
	public void test05_typeofMemberField() {
		MemberField field = new MemberField(PrimitiveType.INT.ptr(), "field");
		assertTrue(field.getType().isIndirect());
	}

	@Test
	public void test06_ref() {
		Type ref = PrimitiveType.INT.ref();
		assertNotNull(ref);

		Type ptrRef = PrimitiveType.INT.ptr().ref();
		assertNotNull(ptrRef);

		assertTrue(ref.write(out.decl(), null));
		assertTrue(out.decl().newline());
		assertTrue(ptrRef.write(out.decl(), null));
		assertDeclFormattedAs("int &", "int * &");
	}

	@Test
	public void test07_constPtr() {
		Type type = PrimitiveType.INT.constPtr();
		assertNotNull(type);

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("int * const");
	}

	@Test
	public void test08_constPtrToConst() {
		Type type = PrimitiveType.INT.constPtr().const_();
		assertNotNull(type);

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("const int * const");
	}

	@Test
	public void test09_constPtrToConst() {
		Type type = PrimitiveType.INT.const_().constPtr();
		assertNotNull(type);

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("const int * const");
	}

	@Test
	public void test10_volatilePtrToConst() {
		Type type = PrimitiveType.INT.volatilePtr().const_();
		assertNotNull(type);

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("const int * volatile");
	}

	@Test
	public void test11_volatilePtrToConst() {
		Type type = PrimitiveType.INT.const_().volatilePtr();
		assertNotNull(type);

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("const int * volatile");
	}

	@Test
	public void test12_constPtrToVolatile() {
		Type type = PrimitiveType.INT.constPtr().volatile_();
		assertNotNull(type);

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("volatile int * const");
	}

	@Test
	public void test13_constPtrToVolatile() {
		Type type = PrimitiveType.INT.volatile_().constPtr();
		assertNotNull(type);

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("volatile int * const");
	}

	@Test
	public void test13_ptrToConstVolatile() {
		Type type = PrimitiveType.INT.ptr().constVolatile();
		assertNotNull(type);
		System.out.println("ptr to const volatile");

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("const volatile int *");
	}

	@Test
	public void test13_constVolatilePtr() {
		Type type = PrimitiveType.INT.constVolatilePtr();
		assertNotNull(type);
		System.out.println("const volatile ptr");

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("int * const volatile");
	}

	@Test
	public void test14_constVolatileWrong() {
		Type type = PrimitiveType.INT.const_().volatile_();
		assertNotNull(type);
		System.out.println("const volatile");

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("volatile int");
	}

	@Test
	public void test15_constVolatileRight() {
		Type type = PrimitiveType.INT.constVolatile();
		assertNotNull(type);
		System.out.println("const volatile");

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("const volatile int");
	}

	@Test
	public void test16_ptrToConst2() {
		Type type1 = PrimitiveType.INT.ptr().const_();
		assertNotNull(type1);
		Type type2 = PrimitiveType.INT.const_().ptr();
		assertNotNull(type1);

		out.decl().writeLn("type1");
		assertTrue(type1.write(out.decl(), null));
		out.decl().write('\n');
		out.decl().writeLn("type2");
		assertTrue(type2.write(out.decl(), null));
		assertDeclFormattedAs("type1",
				"const int *",
				"type2",
				"const int *");

		assertTrue(type1.equals(type2));
	}

	@Test
	public void test17_constPtrToVolatile() {
		Type type = PrimitiveType.INT.volatile_().constPtr();
		assertNotNull(type);
		System.out.println("const ptr to volatile");

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("volatile int * const");
	}

	@Test
	public void test18_constconst() {
		Type type = PrimitiveType.INT.const_().const_();
		assertNotNull(type);

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("const int");
	}

	@Test
	public void test19_refToConst() {
		Type type = PrimitiveType.INT.ref().const_();
		assertNotNull(type);

		assertTrue(type.write(out.decl(), null));
		assertDeclFormattedAs("const int &");
	}

}
