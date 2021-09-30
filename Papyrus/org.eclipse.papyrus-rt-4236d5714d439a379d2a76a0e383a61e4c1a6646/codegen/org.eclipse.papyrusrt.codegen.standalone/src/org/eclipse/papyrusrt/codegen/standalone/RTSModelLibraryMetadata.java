/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.standalone;

import org.eclipse.emf.ecore.EPackage;

public class RTSModelLibraryMetadata extends RegisteredPackageMetadata
{
    public static final RTSModelLibraryMetadata INSTANCE = new RTSModelLibraryMetadata();
    private static final String RTS_MODLIB_PACKAGE_ID        = "org.eclipse.papyrusrt.rts";
    private static final String RTS_MODLIB_NS_URI            = "http://www.eclipse.org/papyrusrt/rts";
    private static final EPackage RTS_MODLIB_PKG             = null;
    private static final String RTS_MODLIB_PATHMAP           = "pathmap://UMLRTRTSLIB/UMLRT-RTS.uml";
    private static final String RTS_MODLIB_ROOT_ID           = "_mPjAgGXmEeS_4daqvwyFrg"; // TODO: Find out if this can be extracted programatically
    private static final String RTS_MODLIB_ROOT_URI          = RTS_MODLIB_PATHMAP + "#" + RTS_MODLIB_ROOT_ID;
    private static final String RTS_MODLIB_PACKAGE_SUBDIR    = "libraries";
    private static final String RTS_MODLIB_PACKAGE_MODEL     = "UMLRT-RTS.uml";
    @Override
    public String getPackageId()
    {
        return RTS_MODLIB_PACKAGE_ID;
    }
    @Override
    public String getNS_URI()
    {
        return RTS_MODLIB_NS_URI;
    }
    @Override
    public EPackage getPackage()
    {
        return RTS_MODLIB_PKG;
    }
    @Override
    public String getPathmap()
    {
        return RTS_MODLIB_PATHMAP;
    }
    @Override
    public String getRootId()
    {
        return RTS_MODLIB_ROOT_ID;
    }
    @Override
    public String getRootURI()
    {
        return RTS_MODLIB_ROOT_URI;
    }
    @Override
    public String getPackageSubdirectory()
    {
        return RTS_MODLIB_PACKAGE_SUBDIR;
    }
    @Override
    public String getPackageModel()
    {
        return RTS_MODLIB_PACKAGE_MODEL;
    }

}
