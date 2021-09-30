/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package org.eclipse.papyrusrt.codegen.xtumlrt.trans

import org.eclipse.papyrus.C_Cpp.Include
import org.eclipse.papyrus.C_Cpp.Ptr
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.platformcppmodel.PlatformcppmodelFactory
import org.eclipse.papyrusrt.xtumlrt.platformmodel.PlatformElement
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.papyrus.C_Cpp.C_CppPackage
import org.eclipse.papyrus.C_Cpp.NoCodeGen
import org.eclipse.papyrusrt.xtumlrt.platformcppmodel.CppInclude
import org.eclipse.papyrusrt.xtumlrt.platformcppmodel.CppPtr
import org.eclipse.papyrusrt.xtumlrt.platformcppmodel.GenerationProperties
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement

/**
 * This class translates stereotypes from the Papyrus C++ profile into
 * PlatformElements from xtumlrt.platform.cpp.model
 *
 * @author Ernesto Posse
 */
class UML2xtumlrtCppProfileTranslator
{

    static val INSTANCE = new UML2xtumlrtCppProfileTranslator

    static def EList<PlatformElement> getPlatformElements( NamedElement element )
    {
        INSTANCE.getCachedPlatformElements( element )
    }

    /**
     * Given a UML2 NamedElement we translate each of its C++ stereotypes (from
     * the Papyrus C++ profile) into corresponding {@link PlatformElement}s in the
     * xtumlrt.platform.cpp.model.
     *
     * <p>We add a reference from each new {@link PlatformElement} to the
     * (translated xtumlrt) {@link NamedElement}, more precisely the
     * {@link NamedElement} is added to the {@link PlatformElement}'s list of
     * {@code referredElements}.
     *
     * <p>We also add the newly created {@link PlatformElement} to the list of
     * platform elements associated to the {@link NamedElement}. Since the
     * {@link NamedElement} doesn't actually hold this list, we keep an internal
     * cache with these associations. The list of platform elements of a given
     * {@link NamedElement} can be accessed with the static
     * {@link #getPlatformElements} method.
     */
    static def translateStereotypedElement
    (
        org.eclipse.uml2.uml.Element element,
        CommonElement newElement
    )
    {
        if (element === null || newElement === null || element.applicableStereotypes === null)
            return;
        for (stereotype : element.appliedStereotypes)
        {
            switch (stereotype.profile.name)
            {
                case "RTCppProperties":
                    translateRTCppProfileStereotype( element, newElement, stereotype )
                case "C_Cpp":
                    translatePapyrusCppProfileStereotype( element, newElement, stereotype )
            }
        }
    }
    dispatch def create PlatformcppmodelFactory.eINSTANCE.createCppInclude
    translateStereotype( Include include )
    {
        header = include.header
        body = include.body
    }

    dispatch def create PlatformcppmodelFactory.eINSTANCE.createCppPtr
    translateStereotype( Ptr ptr )
    {
        declaration = ptr.declaration
    }

    dispatch def create PlatformcppmodelFactory.eINSTANCE.createGenerationProperties
    translateStereotype( NoCodeGen nocodegen )
    {
        generate = false
    }

    def EList<PlatformElement>
    create new BasicEList<PlatformElement>
    getCachedPlatformElements( CommonElement element )
    {
    }

    static def translatePapyrusCppProfileStereotype
    (
        org.eclipse.uml2.uml.Element element,
        CommonElement newElement,
        org.eclipse.uml2.uml.Stereotype stereotype
    )
    {
        var PlatformElement platformElement = null
        switch (stereotype.definition.classifierID)
        {
            case C_CppPackage.INCLUDE:
            {
                val include = element.getStereotypeApplication( stereotype ) as Include
                if (include !== null)
                    platformElement = INSTANCE.translateStereotype( include )
            }
            case C_CppPackage.PTR:
            {
                val ptr = element.getStereotypeApplication( stereotype ) as Ptr
                if (ptr !== null)
                    platformElement = INSTANCE.translateStereotype( ptr )
            }
            case C_CppPackage.NO_CODE_GEN:
            {
                val nocodegen = element.getStereotypeApplication( stereotype ) as NoCodeGen
                if (nocodegen !== null)
                    platformElement = INSTANCE.translateStereotype( nocodegen )
            }
        }
        if (platformElement !== null)
        {
            platformElement.referredElements.add( newElement )
            INSTANCE.getCachedPlatformElements( newElement ).add( platformElement )
        }
    }

    static def translateRTCppProfileStereotype
    (
        org.eclipse.uml2.uml.Element element,
        CommonElement newElement,
        org.eclipse.uml2.uml.Stereotype stereotype
    )
    {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }

    static def getCppInclude( NamedElement element )
    {
        val annotations = UML2xtumlrtCppProfileTranslator.getPlatformElements(element);
        if( annotations == null || annotations.isEmpty() )
            return null
        annotations.findFirst[ it instanceof CppInclude ] as CppInclude
    }

    static def getCppPtr( NamedElement element )
    {
        val annotations = UML2xtumlrtCppProfileTranslator.getPlatformElements(element);
        if( annotations == null || annotations.isEmpty() )
            return null
        annotations.findFirst[ it instanceof CppPtr ] as CppPtr
    }

    static def getGenerationProperties( NamedElement element )
    {
        val annotations = UML2xtumlrtCppProfileTranslator.getPlatformElements(element);
        if( annotations == null || annotations.isEmpty() )
            return null
        annotations.findFirst[ it instanceof GenerationProperties ] as GenerationProperties
    }


}