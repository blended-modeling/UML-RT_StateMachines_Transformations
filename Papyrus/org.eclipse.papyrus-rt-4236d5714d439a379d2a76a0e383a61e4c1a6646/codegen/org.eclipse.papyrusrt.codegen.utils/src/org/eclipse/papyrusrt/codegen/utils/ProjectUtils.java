/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.utils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

public class ProjectUtils
{
    public static String getProjectName( EObject eobj )
    {
        URI uri = eobj.eResource().getURI();
        // Default project name is same as the name of the folder containing the .uml file
        // TODO: this should be refined to look up the folder tree, since the .uml file
        // may be nested inside one or more folders in the project.
        String[] segments = uri.segments();
        if( segments.length > 1 )
            return segments[segments.length - 2];
        else if( segments.length == 1 )
            if( ! segments[0].isEmpty() )
                return segments[0].replace( ".uml", "" );
        return null;
    }
}
