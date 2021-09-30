/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp;

import org.eclipse.papyrusrt.codegen.cpp.rts.UMLRTRuntime;
import org.eclipse.papyrusrt.codegen.lang.cpp.Expression;
import org.eclipse.papyrusrt.codegen.lang.cpp.Type;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.external.StandardLibrary;
import org.eclipse.papyrusrt.codegen.utils.QualifiedNames;
import org.eclipse.papyrusrt.codegen.xtumlrt.trans.UML2xtumlrtCppProfileTranslator;
import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.Entity;
import org.eclipse.papyrusrt.xtumlrt.common.Enumeration;
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement;
import org.eclipse.papyrusrt.xtumlrt.platformcppmodel.CppPtr;
import org.eclipse.papyrusrt.xtumlrt.platformmodel.PlatformElement;
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPassiveClass;

public class TypesUtil
{
    public static Type createCppType( CppCodePattern cpp, NamedElement element, org.eclipse.papyrusrt.xtumlrt.common.Type modelType )
    {
        Type type = createType( cpp, modelType );
        if( type == null )
            throw new RuntimeException( "unable to create C++ type for " + QualifiedNames.fullName(element) );

        // TODO what other C_Cpp stereotypes could be applied?

        if (element.getAnnotations() == null)
            return type;
        for( PlatformElement annotation : UML2xtumlrtCppProfileTranslator.getPlatformElements(element) )
        {
            if (annotation instanceof CppPtr)
                type = type.ptr();
        }

        return type;
    }

    /**
     * The RTS uses instances of UMLRTObject_class to manage serialization of types.  This
     * utility function uses the element's C++ type to create an expression that accesses
     * the correct RTObject_class instance.
     */
    public static Expression createRTTypeAccess( CppCodePattern cpp, NamedElement element, org.eclipse.papyrusrt.xtumlrt.common.Type modelType )
    {
        // Some types have descriptors provided by the RTS.
        Expression rtTypeAccess = UMLRTRuntime.UMLRTObject.UMLRTType( createCppType( cpp, element, modelType ) );
        if( rtTypeAccess != null )
            return rtTypeAccess;

        // Only some system-defined types can be serialized.
        Type systemType = UMLRTRuntime.getSystemType( modelType );
        if( systemType != null )
        {
            rtTypeAccess = UMLRTRuntime.UMLRTObject.UMLRTType( systemType );
            if( rtTypeAccess != null )
                return rtTypeAccess;

            throw new RuntimeException( "attempt to serialize unsupported UMLRTS-defined type as part of " + element.getName() );
        }

        // Capsules cannot be serialized.
        if( element instanceof Capsule )
            throw new RuntimeException( "invalid attempt to serialize a <<Capsule>> " + element.getName() );

        // Otherwise return an expression to access the generated descriptor.
        Variable rtTypeVar = cpp.getVariable( CppCodePattern.Output.UMLRTTypeDescriptor, modelType );
        if( rtTypeVar == null )
            throw new RuntimeException( "invalid attempt to serialize non-serializable type " + modelType.getName() );

        return new ElementAccess( cpp.getVariable( CppCodePattern.Output.UMLRTTypeDescriptor, modelType ) );
    }

    private static Type createType( CppCodePattern cpp, org.eclipse.papyrusrt.xtumlrt.common.Type modelType )
    {
        // If this is a system-defined type then do not look at the model.
        Type type = UMLRTRuntime.getSystemType( modelType );
        if( type != null )
            return type;

        // Otherwise find an appropriate type using the content of the model.
        if( modelType instanceof org.eclipse.papyrusrt.xtumlrt.common.PrimitiveType )
            return createType( (org.eclipse.papyrusrt.xtumlrt.common.PrimitiveType)modelType );
        if( modelType instanceof Entity )
            return createType( cpp, (Entity)modelType );
        if( modelType instanceof org.eclipse.papyrusrt.xtumlrt.common.StructuredType )
            return createType( cpp, (org.eclipse.papyrusrt.xtumlrt.common.StructuredType)modelType );
        if( modelType instanceof Enumeration )
            return createType( cpp, (Enumeration)modelType );
        return PrimitiveType.VOID.ptr();
    }

    private static Type createType( CppCodePattern cpp, org.eclipse.papyrusrt.xtumlrt.common.StructuredType type )
    {
        CppClass cls = cpp.getCppClass( CppCodePattern.Output.BasicClass, type );
        cls.setKind( CppClass.Kind.STRUCT );
        return cls.getType();
    }

    private static Type createType( CppCodePattern cpp, Entity cls )
    {
        return cpp.getCppClass( CppCodePattern.Output.BasicClass, cls ).getType();
    }

    private static Type createType( CppCodePattern cpp, Enumeration element )
    {
        return cpp.getCppEnum( CppCodePattern.Output.UserEnum, element ).getType();
    }

    private static Type createBuiltInType( String name )
    {
        if( "char".equals( name ) )           return PrimitiveType.CHAR;
        if( "double".equals( name ) )         return PrimitiveType.DOUBLE;
        if( "float".equals( name ) )          return PrimitiveType.FLOAT;
        if( "int".equals( name ) )            return PrimitiveType.INT;
        if( "void".equals( name ) )           return PrimitiveType.VOID;
        if( "long".equals( name ) )           return PrimitiveType.LONG;
        if( "long double".equals( name ) )    return PrimitiveType.LONGDOUBLE;
        if( "short".equals( name ) )          return PrimitiveType.SHORT;
        if( "unsigned int".equals( name ) )   return PrimitiveType.UINT;
        if( "unsigned short".equals( name ) ) return PrimitiveType.USHORT;
        if( "unsigned char".equals( name ) )  return PrimitiveType.UCHAR;
        if( "unsigned long".equals( name ) )  return PrimitiveType.ULONG;
        if( "bool".equals( name ) )           return PrimitiveType.BOOL;
        return null;
    }

    private static Type createUMLType( String name )
    {
        if( "Boolean".equals( name ) ) return PrimitiveType.BOOL;
        if( "Integer".equals( name ) ) return PrimitiveType.INT;
        if( "Real".equals( name ) )    return PrimitiveType.DOUBLE;
        if( "String".equals( name ) )  return PrimitiveType.CHAR.ptr();
        if( "UnlimitedNatural".equals( name ) ) return PrimitiveType.ULONGLONG;
        return null;
    }

    private static Type createAnsiCLibraryType( String name )
    {
        if( "int8_t".equals( name ) ) return StandardLibrary.int8_t;
        if( "int16_t".equals( name ) ) return StandardLibrary.int16_t;
        if( "int32_t".equals( name ) ) return StandardLibrary.int32_t;
        if( "int64_t".equals( name ) ) return StandardLibrary.int64_t;
        if( "uint8_t".equals( name ) ) return StandardLibrary.uint8_t;
        if( "uint16_t".equals( name ) ) return StandardLibrary.uint16_t;
        if( "uint32_t".equals( name ) ) return StandardLibrary.uint32_t;
        if( "uint64_t".equals( name ) ) return StandardLibrary.uint64_t;
        if( "wchar_t".equals( name ) ) return StandardLibrary.wchar_t;
        return null;
    }

    private static Type createType( org.eclipse.papyrusrt.xtumlrt.common.PrimitiveType xtumlrtType )
    {
        String name = xtumlrtType.getName();

        Type type = createBuiltInType( name );
        if( type != null )
            return type;

        type = createUMLType( name );
        if( type != null )
            return type;

        type = createAnsiCLibraryType( name );
        if( type != null )
            return type;

        return PrimitiveType.VOID.ptr();
    }
}

