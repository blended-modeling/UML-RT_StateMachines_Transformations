/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.codegen.xtumlrt.trans

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Collection
import java.util.Collections
import java.util.List
import java.util.Set
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.MultiStatus
import org.eclipse.emf.common.util.Enumerator
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.papyrusrt.codegen.CodeGenPlugin
import org.eclipse.papyrusrt.codegen.DetailedThrowable
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement
import org.eclipse.uml2.uml.Element
import org.eclipse.uml2.uml.Enumeration
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.xtend.lib.annotations.Data

import static extension org.eclipse.papyrusrt.codegen.utils.UMLRealTimeProfileUtil.*

/**
 * This is the base class for translators from UML2 to xtUMLrt.
 *
 * <p> The goal of this class is to provide functionality to deal with potentially
 * malformed input models.
 *
 * <p>The main idea is that a translator must provide a {@code translateElement}
 * method which receives a UML2 model element as input and returns an xtUMLrt
 * element as output.
 *
 * <p>Such a method, implemented by subclasses as visitors would normally be
 * recursive, but instead of invoking {@code translateElement} directly, each
 * case method should invoke the {@code translateFeature} provided by this base
 * class, on each relevant sub-feature of the UML element being translated. This
 * {@code translateFeature} provides the necessary sanity checks and other
 * required generation-time validation.
 *
 * @author Ernesto Posse
 */
abstract class UML2xtumlrtTranslator
{
    static val XTUMLRT_EXTENSION = "xtumlrt"
    BiMap<Element, CommonElement> generated
    Set<Element>                  changed
    List<EObject>                 targets
    Path                          outputPath

    new()
    {
        resetAll
        init
    }

    new ( List<EObject> targets, Path outputPath )
    {
        this()
        this.targets = targets
        this.outputPath = outputPath
    }

    private static def init()
    {
        val registry = Resource.Factory.Registry.INSTANCE
        registry.extensionToFactoryMap.put( XTUMLRT_EXTENSION, new XMIResourceFactoryImpl )
    }

    def resetAll()
    {
        if (generated !== null)
            for (umlElement : generated.keySet)
                resetTranslateCache( umlElement )
        generated = HashBiMap.create
        changed = newHashSet
    }

    def List<EObject> getAllGenerated()
    {
        generated.values.map[ it as EObject ].toList
    }

    def CommonElement getGenerated( Element umlElement )
    {
        generated.get( umlElement )
    }

    def Element getSource( CommonElement element )
    {
        generated.inverse.get( element )
    }

    def resetGeneratedCache( Element umlElement )
    {
        generated.remove( umlElement )
    }

    def setChangeSet( Collection<EObject> changedElements )
    {
        changed.addAll( changedElements.filter[ it instanceof Element ].map[ it as Element ] )
    }

    def setTargets( List<EObject> targets )
    {
        this.targets = targets
    }

    def setOutputPath( Path outputPath )
    {
        this.outputPath = outputPath
    }

    def IStatus generate()
    {
        var MultiStatus result = new MultiStatus( CodeGenPlugin.ID, IStatus.INFO, "UML-RT to xtUMLrt translator invoked", null );
        try
        {
            for (element : changed)
            {
                resetGeneratedCache( element )
                resetTranslateCache( element )
            }
            var start = System.currentTimeMillis();
            for (target : targets)
            {
                if ( !(target instanceof Element) )
                {
                    result.add( CodeGenPlugin.info( "Ignoring element " + target.toString() + ": it is not a UML2 element." ) )
                }
                else
                {
                    translateElement( target as Element )
                }
            }
            changed = newHashSet
            result.add( CodeGenPlugin.info("Translated model to xtUMLrt sucessfully " + (System.currentTimeMillis() - start) + "ms") )
        }
        catch (Exception e)
        {
            result.add( CodeGenPlugin.error("Error while translating model to xtUMLrt", e) )
        }
        result
    }

    def boolean write()
    {
        var boolean success = true
        val ResourceSet resourceSet = new ResourceSetImpl
        for (target : targets)
        {
            if (target instanceof Element)
            {
                val uri = target.eResource().getURI();
                val elementPath = Paths.get( uri.path() );
                val fileName = elementPath.fileName.toString
                val fileNameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'))
                val newFileName = fileNameWithoutExt + "." + XTUMLRT_EXTENSION
                val fullPath = outputPath.resolve( newFileName ).toString
                val resource = resourceSet.createResource( URI.createFileURI( fullPath ))
                resource.contents.add( getGenerated( target ) )
                try
                {
                    resource.save( Collections.EMPTY_MAP )
                }
                catch (IOException e)
                {
                    success = false
                }
            }
        }
        success
    }

    abstract def CommonElement translate( Element umlElement )
    abstract def Enumerator translateEnum( Enum<?> kind )
    abstract def void resetTranslateCache( EObject element )

    def CommonElement translateElement( Element umlElement )
    {
        if (generated.containsKey( umlElement ))
            return generated.get( umlElement )
        val translated = translate( umlElement )
        if (!umlElement.isProtocolContainer)
            generated.put( umlElement, translated )
        translated
    }

    def Enumerator translateKind( Enum<?> kind )
    {
        val translated = translateEnum( kind )
        if (!(translated instanceof Enumerator))
            throw new TranslationException( kind, "-", Enum , Enumerator, kind.class, translated?.class, "The result of translating this element did not yield an EMF Enumerator")
        translated as Enumerator
    }

    def translateFeature
    (
        Element umlElement,
        String featureName,
        Class<?> expectedSourceType,
        Class<?> expectedTargetType
    )
    {
        translateFeature( umlElement, featureName, expectedSourceType, expectedTargetType, false )
    }

    def translateFeature
    (
        Element umlElement,
        String featureName,
        Class<?> expectedSourceType,
        Class<?> expectedTargetType,
        boolean sourceMaybeNull
    )
    {
        checkFeature( umlElement, featureName, expectedSourceType, expectedTargetType )
        val feature = getFeature( umlElement, featureName, expectedSourceType, expectedTargetType )
        val featureContent = umlElement.eGet( feature )
        if (sourceMaybeNull && featureContent === null) return null
        checkFeatureContent( umlElement, featureName, expectedSourceType, expectedTargetType, featureContent )
        val result = translateElement( featureContent as Element )
        checkFeatureResult( umlElement, featureName, expectedSourceType, expectedTargetType, featureContent.class, result )
        result
    }



    def translateEnumFeature
    (
        Element umlElement,
        String featureName,
        Class<?> expectedSourceType,
        Class<?> expectedTargetType
    )
    {
        checkFeature( umlElement, featureName, expectedSourceType, expectedTargetType )
        val feature = getFeature( umlElement, featureName, expectedSourceType, expectedTargetType )
        val featureContent = umlElement.eGet( feature )
        checkKindFeatureContent( umlElement, featureName, expectedSourceType, expectedTargetType, featureContent )
        val result = translateKind( featureContent as Enum<?> )
        checkFeatureResult( umlElement, featureName, expectedSourceType, expectedTargetType, featureContent.class, result )
        result
    }

    def getFeature
    (
        Element umlElement,
        String featureName,
        Class<?> expectedSourceType,
        Class<?> expectedTargetType
    )
    {
        val feature = umlElement.eClass.EAllStructuralFeatures.findFirst[ it.name == featureName ]
        if (feature === null)
            throw new TranslationException( umlElement, featureName, expectedSourceType, expectedSourceType, null, null, "Attempting to translate a feature which doesn't exist in a UML element")
        feature
    }

    def checkFeature
    (
        Element umlElement,
        String featureName,
        Class<?> expectedSourceType,
        Class<?> expectedTargetType
    )
    {
        if (umlElement === null && featureName === null)
            throw new TranslationException( umlElement, featureName, expectedSourceType, expectedTargetType, null, null, "Attempting to translate a null feature of a null UML element" )
        if (umlElement === null && featureName !== null)
            throw new TranslationException( umlElement, featureName, expectedSourceType, expectedTargetType, null, null, "Attempting to translate a feature of a null UML element" )
        if (featureName === null)
            throw new TranslationException( umlElement, featureName, expectedSourceType, expectedTargetType, null, null, "Attempting to translate a null feature of a UML element")
    }

    def checkFeatureContent
    (
        Element umlElement,
        String featureName,
        Class<?> expectedSourceType,
        Class<?> expectedTargetType,
        Object featureContent
    )
    {
        if ( featureContent === null )
            throw new TranslationException( umlElement, featureName, expectedSourceType, expectedTargetType, null, null, "The value of this UML element feature is null but should not be null" )
        if ( !(featureContent instanceof Element) )
            throw new TranslationException( umlElement, featureName, expectedSourceType, expectedTargetType, featureContent.class, null, "Feature value is not a UML element" )
        if ( !expectedSourceType.isInstance( featureContent ))
            throw new TranslationException( umlElement, featureName, expectedSourceType, expectedTargetType, featureContent.class, null, "The actual type of this feature's value does not match its expected type" )
    }

    def checkKindFeatureContent
    (
        Element umlElement,
        String featureName,
        Class<?> expectedSourceType,
        Class<?> expectedTargetType,
        Object featureContent
    )
    {
        if ( featureContent === null )
            throw new TranslationException( umlElement, featureName, expectedSourceType, expectedTargetType, null, null, "The value of a UML element feature is null" )
        if ( !(featureContent instanceof Enumeration) && !(featureContent instanceof Enum<?>) )
            throw new TranslationException( umlElement, featureName, expectedSourceType, expectedTargetType, featureContent.class, null, "Feature value is not a UML enumeration" )
        if ( !expectedSourceType.isInstance( featureContent ))
            throw new TranslationException( umlElement, featureName, expectedSourceType, expectedTargetType, featureContent.class, null, "The actual type of this feature's value does not match its expected type" )
    }

    def checkFeatureResult
    (
        Element umlElement,
        String featureName,
        Class<?> expectedSourceType,
        Class<?> expectedTargetType,
        Class<?> actualSourceType,
        Object result
    )
    {
        if (!expectedTargetType.isInstance( result ))
            throw new TranslationException( umlElement, featureName, expectedSourceType, expectedTargetType, actualSourceType, result.class, "The translated element's type does not match the expected type")
    }


    @Data
    static class TranslationException extends DetailedThrowable
    {
        Object element
        String feature
        Class<?> expectedSourceType
        Class<?> expectedTargetType
        Class<?> actualSourceType
        Class<?> actualTargetType
        String msg

        override String toString()
            '''
            Translation error: «msg»
              UML element: «getUMLElementInfoStr»
              Feature: «feature»
              Expected UML type: «maybeNullStr(expectedSourceType)»
              Actual UML type: «maybeNullStr(actualSourceType)»
              «compatibleTypes( expectedSourceType, actualSourceType, "UML element")»
              Expected translated type: «maybeNullStr(expectedTargetType)»
              Actual translated type: «maybeNullStr(actualTargetType)»
              «compatibleTypes( expectedTargetType, actualTargetType, "translated element")»
            '''

        def maybeNullStr( Class<?> c )
            '''«IF c === null»<undefined>«ELSE»«c.name»«ENDIF»'''

        def String getUMLElementInfoStr()
        {
            if (element === null) return "null UML element"
            if (element instanceof NamedElement)
                return element.qualifiedName
            if (element instanceof Enum<?>)
                return element.name
            return "non-UML element: " + element.toString
        }

        def compatibleTypes( Class<?> source, Class<?> target, String item )
            '''«IF source !== null && target !== null»«IF source.isAssignableFrom(target)»[OK] «ELSE»[ERROR] «ENDIF»The actual «item» type is «IF !source.isAssignableFrom(target)»NOT «ENDIF»compatible with the expected «item» type«ENDIF»'''

    }

}