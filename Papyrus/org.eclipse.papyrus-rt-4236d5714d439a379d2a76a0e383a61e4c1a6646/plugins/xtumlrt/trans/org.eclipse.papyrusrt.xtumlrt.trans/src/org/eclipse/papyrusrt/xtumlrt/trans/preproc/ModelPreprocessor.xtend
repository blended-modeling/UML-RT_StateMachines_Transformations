/*******************************************************************************
* Copyright (c) 2014-2016 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.trans.preproc

import org.eclipse.papyrusrt.xtumlrt.common.NamedElement

class ModelPreprocessor
{
    val structureDefaultsPreprocessor = new StructureDefaultsPreprocessor
    //val annotateSysElemPreprocessor = new AnnotateSystemElementsPreprocessor
    
    def preprocess( NamedElement model )
    {
        structureDefaultsPreprocessor.transform( model , null )
        //annotateSysElemPreprocessor.transform( model, null )
    }
}