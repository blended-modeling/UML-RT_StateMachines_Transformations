/*******************************************************************************
* Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.trans.preproc

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.papyrusrt.xtumlrt.common.Model
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.trans.Transformation
import org.eclipse.papyrusrt.xtumlrt.trans.TransformationContext
import static extension org.eclipse.papyrusrt.xtumlrt.external.predefined.RTSModelLibraryUtils.*

class AnnotateSystemElementsPreprocessor implements Transformation<Model, Model>
{
    
    override transform( Model model, TransformationContext context )
    {
        val allContents = EcoreUtil.getAllContents( model, true )
        for (element : allContents.toIterable)
        {
            if (element instanceof NamedElement)
            {
                if (element.isTextualSystemElement)
                {
                    addSysAnnotation( element )
                }
            }
        }
        return model    // This is an in-place transformation
    }

}