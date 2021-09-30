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

import org.eclipse.papyrusrt.codegen.lang.cpp.Expression;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.UnaryOperation;
import org.eclipse.papyrusrt.codegen.lang.cpp.internal.CppFormatter;
import org.eclipse.papyrusrt.codegen.lang.cpp.test.util.LangCppBaseTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class UnaryOperationTest extends LangCppBaseTest
{
    @Test
    public void test()
    {
        Expression one = new IntegralLiteral( "1" );
        CppFormatter fmt = out.decl();

        UnaryOperation op = new UnaryOperation( UnaryOperation.Operator.PRE_INCREMENT, one );
        assertNotNull( op );
        assertEquals( one.getType(), op.getType() );
        assertTrue( op.write( fmt ) );

        op = new UnaryOperation( UnaryOperation.Operator.PRE_DECREMENT, one );
        assertNotNull( op );
        assertEquals( one.getType(), op.getType() );
        assertTrue( fmt.space() );
        assertTrue( op.write( fmt ) );

        op = new UnaryOperation( UnaryOperation.Operator.POST_INCREMENT, one );
        assertNotNull( op );
        assertEquals( one.getType(), op.getType() );
        assertTrue( fmt.space() );
        assertTrue( op.write( fmt ) );

        op = new UnaryOperation( UnaryOperation.Operator.POST_DECREMENT, one );
        assertNotNull( op );
        assertEquals( one.getType(), op.getType() );
        assertTrue( fmt.space() );
        assertTrue( op.write( fmt ) );

        op = new UnaryOperation( UnaryOperation.Operator.LOGICAL_NOT, one );
        assertNotNull( op );
        assertEquals( PrimitiveType.BOOL, op.getType() );
        assertTrue( fmt.space() );
        assertTrue( op.write( fmt ) );

        op = new UnaryOperation( UnaryOperation.Operator.BITWISE_NOT, one );
        assertNotNull( op );
        assertEquals( one.getType(), op.getType() );
        assertTrue( fmt.space() );
        assertTrue( op.write( fmt ) );

        assertDeclFormattedAs( "++1 --1 1++ 1-- ! 1 ~1" );
    }
}
