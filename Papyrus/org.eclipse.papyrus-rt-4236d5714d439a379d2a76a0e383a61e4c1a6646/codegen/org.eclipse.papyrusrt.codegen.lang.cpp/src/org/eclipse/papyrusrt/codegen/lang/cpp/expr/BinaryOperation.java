/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.expr;

import org.eclipse.papyrusrt.codegen.lang.cpp.Expression;
import org.eclipse.papyrusrt.codegen.lang.cpp.Type;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.DependencyList;
import org.eclipse.papyrusrt.codegen.lang.cpp.internal.CppFormatter;

public class BinaryOperation extends Expression
{
    private final Operator operator;
    private final Expression lhs;
    private final Expression rhs;

    public BinaryOperation( Expression lhs, Operator operator, Expression rhs )
    {
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public static enum Operator
    {
        ASSIGN(      '=', Precedence.Precedence15 ),
        ADD(         '+', Precedence.Precedence06 ),
        SUBTRACT(    '-', Precedence.Precedence06 ),
        MULTIPLY(    '*', Precedence.Precedence05 ),
        DIVIDE(      '/', Precedence.Precedence05 ),
        BITWISE_OR(  '|', Precedence.Precedence12 ),
        BITWISE_AND( '&', Precedence.Precedence10 );

        private Operator( char token, Precedence precedence ) { this.token = token; this.precedence = precedence; }
        private final char token;
        public final Precedence precedence;

        public boolean write( CppFormatter fmt )
        {
            return fmt.write( token );
        }
    }

    @Override
    protected Type createType()
    {
        return lhs.getType();
    }

    @Override public Precedence getPrecedence() { return operator.precedence; }

    @Override
    public boolean addDependencies( DependencyList deps )
    {
        return lhs.addDependencies( deps )
            && rhs.addDependencies( deps );
    }

    @Override
    public boolean write( CppFormatter fmt )
    {
        return lhs.write( fmt, this )
            && fmt.space()
            && operator.write( fmt )
            && fmt.space()
            && rhs.write( fmt, this );
    }
}
