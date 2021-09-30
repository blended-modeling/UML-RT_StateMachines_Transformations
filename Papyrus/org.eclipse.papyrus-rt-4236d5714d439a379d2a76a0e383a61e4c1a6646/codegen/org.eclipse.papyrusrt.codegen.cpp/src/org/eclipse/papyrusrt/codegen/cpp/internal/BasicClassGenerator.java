/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.internal;

import org.eclipse.papyrusrt.codegen.cpp.AbstractCppGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.codegen.cpp.TypesUtil;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberField;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberFunction;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ExpressionBlob;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.UserCode;
import org.eclipse.papyrusrt.xtumlrt.common.ActionCode;
import org.eclipse.papyrusrt.xtumlrt.common.Attribute;
import org.eclipse.papyrusrt.xtumlrt.common.CommonPackage;
import org.eclipse.papyrusrt.xtumlrt.common.Operation;
import org.eclipse.papyrusrt.xtumlrt.common.Parameter;
import org.eclipse.papyrusrt.xtumlrt.common.StructuredType;
import org.eclipse.papyrusrt.xtumlrt.common.Type;
import org.eclipse.papyrusrt.xtumlrt.common.TypedMultiplicityElement;
import org.eclipse.papyrusrt.xtumlrt.common.VisibilityKind;

public class BasicClassGenerator extends AbstractCppGenerator
{
    private final Type element;

    public BasicClassGenerator( CppCodePattern cpp, Type element )
    {
        super( cpp );
        this.element = element;
    }

    @Override
    public String getLabel()
    {
        return super.getLabel() + ' ' + element.getName();
    }

    private CppClass.Visibility getVisibility( VisibilityKind visibility )
    {
        switch( visibility )
        {
        case PUBLIC:
            return CppClass.Visibility.PUBLIC;
        case PROTECTED:
            return CppClass.Visibility.PROTECTED;
        case PRIVATE:
            return CppClass.Visibility.PRIVATE;
        default:
            break;
        }

        return null;
    }

    /**
     * Returns the C++ type created from the return type of the operation.
     * The operation's returnType reference is a TypedMultiplicityElement which
     * itself has a type. This is the type used to obtain the corresponding
     * C++ type.
     */
    private org.eclipse.papyrusrt.codegen.lang.cpp.Type getReturnType( Operation operation )
    {
        TypedMultiplicityElement returnTypedElement = operation.getReturnType();
        if (returnTypedElement == null)
            return PrimitiveType.VOID;

        Type returnType = returnTypedElement.getType();
        if (returnType == null)
            return PrimitiveType.VOID;

        org.eclipse.papyrusrt.codegen.lang.cpp.Type type = TypesUtil.createCppType( cpp, operation, returnType );
        int upperBound = returnTypedElement.getUpperBound();
        if( upperBound > 1 )
            type = type.ptr();
        return type;
    }

    @Override
    public boolean generate()
    {
        CppClass cls = cpp.getWritableCppClass( CppCodePattern.Output.BasicClass, element );
        return generate( cls );
    }

    /**
     * Generate the receiver model object's values into the given C++ class.
     */
    protected boolean generate( CppClass cls )
    {
        if( element.eClass() == CommonPackage.eINSTANCE.getStructuredType() )
        {
            cls.setKind( CppClass.Kind.STRUCT );
        }
        generateAttributes( cls );
        generateOperations( cls );

        return true;
    }

    private boolean generateOperations( CppClass cls )
    {
        for( Operation operation : ((StructuredType)element).getOperations() )
        {
            CppClass.Visibility cppVisibility = getVisibility( operation.getVisibility() );
            if( cppVisibility == null )
                continue;

            org.eclipse.papyrusrt.codegen.lang.cpp.Type returnType = getReturnType( operation );
            if( returnType == null )
                throw new RuntimeException( "could not determine return type for " + operation.toString() );

            MemberFunction function = new MemberFunction( returnType, operation.getName() );
            for( Parameter param : operation.getParameters() )
                switch( param.getDirection() )
                {
                case OUT:
                    break;
                case IN:
                    org.eclipse.papyrusrt.codegen.lang.cpp.Type type = TypesUtil.createCppType( cpp, param, param.getType() );
                    int upperBound = param.getUpperBound();
                    if( upperBound > 1 )
                        type = type.arrayOf( new IntegralLiteral( upperBound ) );
                    function.add( new org.eclipse.papyrusrt.codegen.lang.cpp.element.Parameter( type, param.getName() ) );
                    break;
                default:
                    throw new RuntimeException( "unhandled paramater direction for " + param.toString() );
                }

            // Find some C++ code that is associated with this Operation
            ActionCode code = operation.getBody();
            if( code != null )
            {
                String source = code.getSource();
                if( source != null )
                    function.add( new UserCode( source ) );
            }

            if( operation.isStatic() )
                cls.addStaticMember( cppVisibility, function );
            else
                cls.addMember( cppVisibility, function );
        }
        return true;
    }

    protected MemberField generate( CppClass cls, Attribute attr )
    {
        CppClass.Visibility cppVisibility = getVisibility( attr.getVisibility() );
        if( cppVisibility == null )
            return null;

        org.eclipse.papyrusrt.codegen.lang.cpp.Type type = TypesUtil.createCppType( cpp, attr, attr.getType() );
        int upperBound = attr.getUpperBound();
        if( upperBound > 1 )
            type = type.arrayOf( new IntegralLiteral( upperBound ) );
        MemberField field = null;
        if( attr.getDefault() == null
         || attr.getDefault().isEmpty() )
            field = new MemberField( type, attr.getName() );
        else
            field = new MemberField( type, attr.getName(), new ExpressionBlob( attr.getDefault() ) );

        if( attr.isStatic() )
            cls.addStaticMember( cppVisibility, field );
        else
            cls.addMember( cppVisibility, field );

        return field;
    }

    private boolean generateAttributes( CppClass cls )
    {
        for( Attribute attr : ((StructuredType)element).getAttributes() )
            generate( cls, attr );
        return true;
    }

}
