/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.statemachines.flat

import org.eclipse.papyrusrt.codegen.lang.cpp.element.AbstractFunction
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable
import org.eclipse.papyrusrt.codegen.lang.cpp.external.StandardLibrary
import org.eclipse.papyrusrt.codegen.lang.cpp.Type
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ConditionalStatement
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess
import org.eclipse.papyrusrt.codegen.cpp.rts.UMLRTRuntime
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.CastExpr
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.MemberAccess
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Parameter
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.AddressOfExpr

class StateMachineCppGeneratorUtil
{
    public static def generateDecode( AbstractFunction func, Parameter param, Type rtDataType )
    {
        var Variable rtdata = null
        if( rtDataType != null )
        {
            rtdata = new Variable( rtDataType, "rtdata" )
        }
        else
        {
            rtdata = new Variable( PrimitiveType.VOID.ptr(), "rtdata", StandardLibrary.NULL() );
        }
        func.add( rtdata );

        func.add(
            UMLRTRuntime.UMLRTInMessage.decode(
                new ElementAccess( param ),
                new AddressOfExpr( new ElementAccess( rtdata ) )
            ) );
    }
}
