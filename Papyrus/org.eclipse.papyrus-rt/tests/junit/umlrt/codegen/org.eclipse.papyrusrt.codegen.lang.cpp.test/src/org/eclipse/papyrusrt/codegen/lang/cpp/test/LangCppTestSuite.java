/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith( Suite.class )
@SuiteClasses(
{
    BasicTest.class,
    ConditionalStatementTest.class,
    CppClassTest.class,
    CppEnumTest.class,
    CppNamespaceTest.class,
    DependencyTest.class,
    ElementListTest.class,
    ExpressionTest.class,
    ExternalTypeTest.class,
    FunctionTest.class,
    InitializerTest.class,
    MacroTest.class,
    NameTest.class,
    StandardLibraryTest.class,
    SwitchStatementTest.class,
    TypedefTest.class,
    TypeTest.class,
    UnaryOperationTest.class,
    VariableTest.class,
} )
public class LangCppTestSuite { }
