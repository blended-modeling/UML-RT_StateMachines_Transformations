/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.papyrus.internal;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.papyrusrt.codegen.papyrus.UMLRTGenerator;

public class UMLRTCppCodeReGen extends UMLRTCppCodeGen
{

    @Override
    public Object execute( ExecutionEvent event ) throws ExecutionException
    {
        UMLRTGenerator.setRegenerate( true );
        try
        {
            super.execute( event );
        }
        finally
        {
            UMLRTGenerator.setRegenerate( false );
        }
        return null;
    }

}
