/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.profile.facade

import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeKind
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassKind
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.InitializationKind
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationKind
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTAnnotations

/**
 * This class provides a facade to the stereotypes in the profile so that they can be easily accessed from any
 * xtUML-RT element.
 * 
 * @author Ernesto Posse
 */
class RTCppGenerationProperties extends XTUMLRTAnnotations
{

    static val ALL_CLASS_STEREOTYPES =
    #[
        "ClassProperties",
        "PassiveClassProperties",
        "CapsuleProperties"
    ]

    static val ALL_CPP_FILE_STEREOTYPES =
    #[
        "ClassProperties",
        "PassiveClassProperties",
        "CapsuleProperties",
        "EnumerationProperties",
        "CppFileProperties"
    ]

    static val ALL_FILE_STEREOTYPES =
    #[
        "PassiveClassProperties",
        "CapsuleProperties",
        "FileGenerationProperties"
    ]

    static val ALL_GENERATION_STEREOTYPES =
    #[
        "PassiveClassProperties",
        "EnumerationProperties",
        "GenerationProperties"
    ]

    static val ALL_PASSIVE_CLASS_STEREOTYPES =
    #[
        "PassiveClassProperties",
        "ClassGenerationProperties"
    ]

    static def getArtifactProperties( CommonElement element )
    {
        element?.allAnnotations?.findFirst [ it.isArtifactProperties ]
    }

    static def getArtifactPropIncludeFile( CommonElement element )
    {
        getProperty( element, "ArtifactProperties", "includeFile" ) as String
    }

    static def getArtifactPropSourceFile( CommonElement element )
    {
        getProperty( element, "ArtifactProperties", "sourceFile" ) as String
    }

    static def getAttributeProperties( CommonElement element )
    {
        element?.allAnnotations?.findFirst [ it.isAttributeProperties ]
    }

    static def getAttributePropKind( CommonElement element )
    {
        val eEnumLiteral = getProperty( element, "AttributeProperties", "kind" ) as AttributeKind
        return eEnumLiteral
    }

    static def getAttributePropInitialization( CommonElement element )
    {
        val eEnumLiteral = getProperty( element, "AttributeProperties", "initialization" ) as InitializationKind
        return eEnumLiteral
    }

    static def getAttributePropVolatile( CommonElement element )
    {
        getProperty( element, "AttributeProperties", "volatile" ) as Boolean
    }

    static def getAttributePropType( CommonElement element )
    {
        getProperty( element, "AttributeProperties", "type" ) as String
    }

    static def getAttributePropSize( CommonElement element )
    {
        getProperty( element, "AttributeProperties", "size" ) as String
    }

    static def getAttributePropPointsTo( CommonElement element )
    {
        getProperty( element, "AttributeProperties", "pointsToType" ) as Boolean
    }

    static def getAttributePropPointsToConst( CommonElement element )
    {
        getProperty( element, "AttributeProperties", "pointsToConstType" ) as Boolean
    }

    static def getAttributePropPointsToVolatile( CommonElement element )
    {
        getProperty( element, "AttributeProperties", "pointsToVolatileType" ) as Boolean
    }

    static def getClassGenerationProperties( CommonElement element )
    {
        element?.allAnnotations?.findFirst [ it.isClassGenerationProperties ]
    }

    static def getClassGenerationPropGenerateStateMachine( CommonElement element )
    {
        getProperty( element, ALL_PASSIVE_CLASS_STEREOTYPES, "generateStateMachine" ) as Boolean
    }

    static def getClassGenerationPropGenerateAssignmentOperator( CommonElement element )
    {
        getProperty( element, ALL_PASSIVE_CLASS_STEREOTYPES, "generateAssignmentOperator" ) as Boolean
    }

    static def getClassGenerationPropGenerateCopyConstructor( CommonElement element )
    {
        getProperty( element, ALL_PASSIVE_CLASS_STEREOTYPES, "generateCopyConstructor" ) as Boolean
    }

    static def getClassGenerationPropGenerateDefaultConstructor( CommonElement element )
    {
        getProperty( element, ALL_PASSIVE_CLASS_STEREOTYPES, "generateDefaultConstructor" ) as Boolean
    }

    static def getClassGenerationPropGenerateDestructor( CommonElement element )
    {
        getProperty( element, ALL_PASSIVE_CLASS_STEREOTYPES, "generateDestructor" ) as Boolean
    }

    static def getClassGenerationPropGenerateEqualityOperator( CommonElement element )
    {
        getProperty( element, ALL_PASSIVE_CLASS_STEREOTYPES, "generateEqualityOperator" ) as Boolean
    }

    static def getClassGenerationPropGenerateExtractionOperator( CommonElement element )
    {
        getProperty( element, ALL_PASSIVE_CLASS_STEREOTYPES, "generateExtractionOperator" ) as Boolean
    }

    static def getClassGenerationPropGenerateInequalityOperator( CommonElement element )
    {
        getProperty( element, ALL_PASSIVE_CLASS_STEREOTYPES, "generateInequalityOperator" ) as Boolean
    }

    static def getClassGenerationPropGenerateInsertionOperator( CommonElement element )
    {
        getProperty( element, ALL_PASSIVE_CLASS_STEREOTYPES, "generateInsertionOperator" ) as Boolean
    }

    static def getClassProperties( CommonElement element )
    {
        element?.allAnnotations?.findFirst [ it.isClassProperties ]
    }

    static def getClassPropPrivateDeclarations( CommonElement element )
    {
        getProperty( element, ALL_CLASS_STEREOTYPES, "privateDeclarations" ) as String
    }

    static def getClassPropProtectedDeclarations( CommonElement element )
    {
        getProperty( element, ALL_CLASS_STEREOTYPES, "protectedDeclarations" ) as String
    }

    static def getClassPropPublicDeclarations( CommonElement element )
    {
        getProperty( element, ALL_CLASS_STEREOTYPES, "publicDeclarations" ) as String
    }

    static def getCppFileProperties( CommonElement element )
    {
        element?.allAnnotations?.findFirst [ it.isCppFileProperties ]
    }

    static def getCppFileHeaderPreface( CommonElement element )
    {
        getProperty( element, ALL_CPP_FILE_STEREOTYPES, "headerPreface" ) as String
    }

    static def getCppFileHeaderEnding( CommonElement element )
    {
        getProperty( element, ALL_CPP_FILE_STEREOTYPES, "headerEnding" ) as String
    }

    static def getCppFileImplementationPreface( CommonElement element )
    {
        getProperty( element, ALL_CPP_FILE_STEREOTYPES, "implementationPreface" ) as String
    }

    static def getCppFileImplementationEnding( CommonElement element )
    {
        getProperty( element, ALL_CPP_FILE_STEREOTYPES, "implementationEnding" ) as String
    }

    static def getDependencyProperties( CommonElement element )
    {
        element?.allAnnotations?.findFirst [ it.isDependencyProperties ]
    }

    static def getDependencyPropKindInHeader( CommonElement element )
    {
        val eEnumLiteral = getProperty( element, "DependencyProperties", "KindInHeader" ) as DependencyKind
        return eEnumLiteral
    }

    static def getDependencyPropKindInImplementation( CommonElement element )
    {
        val eEnumLiteral = getProperty( element, "DependencyProperties", "KindInImplementation" ) as DependencyKind
        return eEnumLiteral
    }

    static def getFileGenerationProperties( CommonElement element )
    {
        element?.allAnnotations?.findFirst [ it.isFileGenerationProperties ]
    }

    static def getFileGenerationPropGenerateHeader( CommonElement element )
    {
        getProperty( element, ALL_FILE_STEREOTYPES, "generateHeader" ) as Boolean
    }

    static def getFileGenerationPropGenerateImplementation( CommonElement element )
    {
        getProperty( element, ALL_FILE_STEREOTYPES, "generateImplementation" ) as Boolean
    }

    static def getGeneralizationProperties( CommonElement element )
    {
        element?.allAnnotations?.findFirst [ it.isGeneralizationProperties ]
    }

    static def getGeneralizationPropVirtual( CommonElement element )
    {
        getProperty( element, "GeneralizationProperties", "virtual" ) as Boolean
    }

    static def getGenerationProperties( CommonElement element )
    {
        element?.allAnnotations?.findFirst [ it.isGenerationProperties ]
    }

    static def getGenerationPropGenerate( CommonElement element )
    {
        getProperty( element, ALL_GENERATION_STEREOTYPES, "generate" ) as Boolean
    }

    static def getOperationProperties( CommonElement element )
    {
        element?.allAnnotations?.findFirst [ it.isOperationProperties ]
    }

    static def getOperationPropKind( CommonElement element )
    {
        val eEnumLiteral = getProperty( element, "OperationProperties", "kind" ) as OperationKind
        return eEnumLiteral
    }

    static def getOperationPropInline( CommonElement element )
    {
        getProperty( element, "OperationProperties", "Inline" ) as Boolean
    }

    static def getOperationPropPolymorphic( CommonElement element )
    {
        getProperty( element, "OperationProperties", "Polymorphic" ) as Boolean
    }

    static def getOperationPropGenDef( CommonElement element )
    {
        getProperty( element, "OperationProperties", "generateDefinition" ) as Boolean
    }

    static def getParameterProperties( CommonElement element )
    {
        element?.allAnnotations?.findFirst [ it.isParameterProperties ]
    }

    static def getParameterPropType( CommonElement element )
    {
        getProperty( element, "ParameterProperties", "type" ) as String
    }

    static def getParameterPropPointsTo( CommonElement element )
    {
        getProperty( element, "ParameterProperties", "pointsToType" ) as Boolean
    }

    static def getParameterPropPointsToConst( CommonElement element )
    {
        getProperty( element, "ParameterProperties", "pointsToConst" ) as Boolean
    }

    static def getParameterPropPointsToVolatile( CommonElement element )
    {
        getProperty( element, "ParameterProperties", "pointsToVolatile" ) as Boolean
    }

    static def getPassiveClassProperties( CommonElement element )
    {
        element?.allAnnotations?.findFirst [ it.isPassiveClassProperties ]
    }

    static def getPassiveClassPropKind( CommonElement element )
    {
        val eEnumLiteral = getProperty( element, "PassiveClassProperties", "kind" ) as ClassKind
        return eEnumLiteral
    }

    static def getPassiveClassPropImplementationType( CommonElement element )
    {
        getProperty( element, "PassiveClassProperties", "implementationType" ) as String
    }

    static def hasAttributeKind( CommonElement element, AttributeKind expectedKind )
    {
        val kind = element?.getAttributePropKind
        kind !== null && kind == expectedKind
    }

    static def hasAttributeKindMember( CommonElement element )
    {
        element.hasAttributeKind( AttributeKind.MEMBER )
    }

    static def hasAttributeKindGlobal( CommonElement element )
    {
        element.hasAttributeKind( AttributeKind.GLOBAL )
    }

    static def hasAttributeKindMutableMember( CommonElement element )
    {
        element.hasAttributeKind( AttributeKind.MUTABLE_MEMBER )
    }

    static def hasAttributeKindDefine( CommonElement element )
    {
        element.hasAttributeKind( AttributeKind.DEFINE )
    }

    static def hasOperationKind( CommonElement element, OperationKind expectedKind )
    {
        val kind = element?.getOperationPropKind
        kind !== null && kind == expectedKind
    }

    static def hasOperationKindMember( CommonElement element )
    {
        element.hasOperationKind( OperationKind.MEMBER )
    }

    static def hasOperationKindFriend( CommonElement element )
    {
        element.hasOperationKind( OperationKind.FRIEND )
    }

    static def hasOperationKindGlobal( CommonElement element )
    {
        element.hasOperationKind( OperationKind.GLOBAL )
    }

    static def isArtifactProperties( EObject stereotypeApplication )
    {
        stereotypeApplication.eClass.name == "ArtifactProperties"
    }

    static def isAttributeProperties( EObject stereotypeApplication )
    {
        stereotypeApplication.eClass.name == "AttributeProperties"
    }

    static def isClassGenerationProperties( EObject stereotypeApplication )
    {
        stereotypeApplication.eClass.name == "ClassGenerationProperties"
    }

    static def isClassProperties( EObject stereotypeApplication )
    {
        stereotypeApplication.eClass.name == "ClassProperties"
    }

    static def isCppFileProperties( EObject stereotypeApplication )
    {
        stereotypeApplication.eClass.name == "CppFileProperties"
    }

    static def isDependencyProperties( EObject stereotypeApplication )
    {
        stereotypeApplication.eClass.name == "DependencyProperties"
    }

    static def isFileGenerationProperties( EObject stereotypeApplication )
    {
        stereotypeApplication.eClass.name == "FileGenerationProperties"
    }

    static def isGeneralizationProperties( EObject stereotypeApplication )
    {
        stereotypeApplication.eClass.name == "GeneralizationProperties"
    }

    static def isGenerationProperties( EObject stereotypeApplication )
    {
        stereotypeApplication.eClass.name == "GenerationProperties"
    }

    static def isOperationProperties( EObject stereotypeApplication )
    {
        stereotypeApplication.eClass.name == "OperationProperties"
    }

    static def isParameterProperties( EObject stereotypeApplication )
    {
        stereotypeApplication.eClass.name == "ParameterProperties"
    }

    static def isPassiveClassProperties( EObject stereotypeApplication )
    {
        stereotypeApplication.eClass.name == "PassiveClassProperties"
    }

}