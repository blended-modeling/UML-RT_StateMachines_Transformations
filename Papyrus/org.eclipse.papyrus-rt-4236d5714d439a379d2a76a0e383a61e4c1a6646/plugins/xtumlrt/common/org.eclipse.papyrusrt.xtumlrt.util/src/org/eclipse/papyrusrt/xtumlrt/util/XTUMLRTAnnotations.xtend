/*******************************************************************************
* Copyright (c) 2015-2016 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.util

import java.util.HashMap
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement
import java.util.Collection
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.common.Annotation
import org.eclipse.uml2.uml.Stereotype
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory

/**
 * This class provides an API for accessing annotations given to
 * xtUMLrt elements vie the stereotypes of the corresponding UML elements.
 *
 * @author Ernesto Posse
 */
class XTUMLRTAnnotations
{
    public static val ANN_EXCLUSION = "Excluded"
    public static val ANN_PARAM_EXCLUDED = "excluded"
    
    /** Maps each xtumlrt element to the list of UML stereotype applications from the C++ Properties Set profile */
    static val annotationsMap = new HashMap<CommonElement, EList<EObject>>

    /**
     * Sets the stereotypes applied to a given UML element as annotations to a corresponding XtUML-RT element.
     * 
     * @param umlElement - A {@link org.eclipse.uml2.uml.Element}
     * @param element - An XtUML-RT {@link CommonElement}
     */
    static def setAnnotations
    (
        org.eclipse.uml2.uml.Element umlElement,
        CommonElement element
    )
    {
        if (umlElement === null || element === null || umlElement.applicableStereotypes === null)
            return;
        val stereotypeApplications =
            if (umlElement.stereotypeApplications !== null)
                umlElement.stereotypeApplications
            else
                new BasicEList<EObject>
        annotationsMap.put( element, stereotypeApplications )
        if (element instanceof NamedElement)
            translateStereotypes( element, stereotypeApplications )
    }

    /**
     * Returns the annotation of the given element with the given name, if it exists.
     * 
     * @param element - An XtUML-RT {@link CommonElement}
     * @param name - The name of an annotation.
     */
    static def getAnnotation( CommonElement element, String name )
    {
        if (element === null || name === null) return null;
        if (element instanceof NamedElement)
        {
            val annotations = element.annotations
            if (annotations !== null && ! annotations.empty)
            {
                val annotation = annotations.findFirst [ annotationName == name ]
                if (annotation !== null)
                    return annotation
            }
            if (annotationsMap.containsKey( element ))
            {
                val otherAnnotations = annotationsMap.get( element )
                if (otherAnnotations !== null)
                    return otherAnnotations.findFirst [ annotationName == name ]
            }
        }
    }

    /**
     * Returns all annotations of the given element.
     * 
     * @param element - An XtUML-RT {@link CommonElement}
     */
    static def getAllAnnotations( CommonElement element )
    {
        annotationsMap.get( element )
    }

    /**
     * Returns the value of a property in an annotation of the given element.
     * 
     * @param element - An XtUML-RT {@link CommonElement}
     * @param annotation - The name of an annotation
     * @param property - The name of a property
     */
    static dispatch def getProperty( CommonElement element, String annotation, String property )
    {
        if (element === null || annotation === null || property === null) return null;
        var Object propertyValue = null
        if (element instanceof NamedElement)
        {
            val annotations = element.annotations
            propertyValue = findProperty( annotation, property, annotations )
        }
        if (propertyValue === null && annotationsMap.containsKey( element ))
        {
            val elemStereotypes = annotationsMap.get( element )
            propertyValue = findProperty( annotation, property, elemStereotypes )
        }
        return propertyValue
    }

    protected static dispatch def Object findProperty( String annotation, String property, Iterable<? extends EObject> annotations )
    {
        if (annotations !== null && ! annotations.empty)
        {
            val theAnnotation = annotations.findFirst [ annotationName == annotation ]
            if (theAnnotation !== null)
                getAnnotationProperty( theAnnotation, property )
        }
    }

    /**
     * Returns the value of a property in a collection of annotations of the given element.
     * 
     * @param element - An XtUML-RT {@link CommonElement}
     * @param annotation - The name of an annotation
     * @param property - The name of a property
     */
    static dispatch def getProperty( CommonElement element, Collection<String> annotations, String property )
    {
        if (annotations === null || property === null) return null;
        var Object propertyValue = null
        if (element instanceof NamedElement)
        {
            val annotationsList = element.annotations
            propertyValue = findProperty( annotations, property, annotationsList )
        }
        if (propertyValue === null && annotationsMap.containsKey( element ))
        {
            val annotationsList = annotationsMap.get( element )
            propertyValue = findProperty( annotations, property, annotationsList )
        }
        return propertyValue
    }

    protected static dispatch def Object findProperty( Collection<String> stereotypes, String property, Iterable<? extends EObject> annotations )
    {
        if (annotations !== null && ! annotations.empty)
        {
            val theAnnotation = annotations.findFirst [ stereotypes.contains( annotationName ) ]
            if (theAnnotation !== null)
                getAnnotationProperty( theAnnotation, property )
        }
    }

    protected static dispatch def getAnnotationName( Annotation annotation )
    {
        annotation.name
    }

    protected static dispatch def getAnnotationName( Stereotype stereotype )
    {
        stereotype.eClass.name
    }

    protected static dispatch def getAnnotationName( EObject eobj )
    {
        eobj.eClass.name
    }

    protected static dispatch def getAnnotationProperty( Annotation annotation, String property )
    {
        val param = annotation?.parameters?.findFirst[ it.name == property ]
        param?.value
    }

    protected static dispatch def getAnnotationProperty( Stereotype stereotype, String property )
    {
        val feature = stereotype?.eClass?.EAllStructuralFeatures?.findFirst[ it.name == property ]
        if (feature !== null)
            stereotype?.eGet( feature )
    }

    protected static dispatch def getAnnotationProperty( EObject eobj, String property )
    {
        val feature = eobj?.eClass?.EAllStructuralFeatures?.findFirst[ it.name == property ]
        if (feature !== null)
            eobj?.eGet( feature )
    }

    protected static def translateStereotypes( NamedElement element, Iterable<EObject> stereotypeApplications )
    {
        for (stereotypeApplication : stereotypeApplications)
        {
            val stereotype = stereotypeApplication.eClass
            if (stereotype instanceof Stereotype)
            {
                val stereotypeName = stereotype.name
                val currentAnnotations = element.annotations
                if (!currentAnnotations.exists[name == stereotypeName])
                {
                    val newAnnotation = CommonFactory.eINSTANCE.createAnnotation
                    newAnnotation.name = stereotypeName
                    for (stereotypeProperty : stereotype.attributes)
                    {
                        val stereotypePropertyName = stereotypeProperty.name
                        val newAnnotationParameter = CommonFactory.eINSTANCE.createAnnotationParameter
                        newAnnotationParameter.name = stereotypePropertyName
                        val value = getAnnotationProperty(stereotype, stereotypePropertyName )
                        newAnnotationParameter.value = if (value instanceof String) value else ""
                    }
                    element.annotations.add( newAnnotation )
                }
            }
        }
    }

}