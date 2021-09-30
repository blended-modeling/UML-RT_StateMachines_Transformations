/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.standalone;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrus.umlrt.UMLRealTime.UMLRealTimePackage;

public class UMLRTProfileMetadata extends RegisteredPackageMetadata
{
    public static final UMLRTProfileMetadata INSTANCE = new UMLRTProfileMetadata();
    private static final String UMLRT_PROFILE_ID        = "org.eclipse.papyrus.umlrt";
    private static final String UMLRT_NS_URI            = UMLRealTimePackage.eNS_URI; //"http://www.eclipse.org/papyrus/umlrt"
    private static final UMLRealTimePackage UMLRT_PKG   = UMLRealTimePackage.eINSTANCE;
    private static final String UMLRT_PATHMAP           = "pathmap://UML_RT_PROFILE/uml-rt.profile.uml";
    private static final String UMLRT_ROOT_ID           = "_1h74oEeVEeO0lv5O1DTHOQ"; // TODO: Find out if this can be extracted programatically
    private static final String UMLRT_ROOT_URI          = UMLRT_PATHMAP + "#" + UMLRT_ROOT_ID;
    private static final String UMLRT_PROFILE_SUBDIR    = "umlProfile";
    private static final String UMLRT_PROFILE_MODEL     = "uml-rt.profile.uml";
    @Override
    public String getPackageId()
    {
        return UMLRT_PROFILE_ID;
    }
    @Override
    public String getNS_URI()
    {
        return UMLRT_NS_URI;
    }
    @Override
    public EPackage getPackage()
    {
        return UMLRT_PKG;
    }
    @Override
    public String getPathmap()
    {
        return UMLRT_PATHMAP;
    }
    @Override
    public String getRootId()
    {
        return UMLRT_ROOT_ID;
    }
    @Override
    public String getRootURI()
    {
        return UMLRT_ROOT_URI;
    }
    @Override
    public String getPackageSubdirectory()
    {
        return UMLRT_PROFILE_SUBDIR;
    }
    @Override
    public String getPackageModel()
    {
        return UMLRT_PROFILE_MODEL;
    }

}
