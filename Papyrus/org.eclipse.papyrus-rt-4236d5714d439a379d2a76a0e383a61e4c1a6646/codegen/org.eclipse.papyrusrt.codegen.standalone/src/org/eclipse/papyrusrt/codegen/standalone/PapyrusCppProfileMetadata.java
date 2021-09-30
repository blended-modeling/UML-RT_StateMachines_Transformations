/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.standalone;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrus.C_Cpp.C_CppPackage;

public class PapyrusCppProfileMetadata extends RegisteredPackageMetadata
{
    public static final PapyrusCppProfileMetadata INSTANCE = new PapyrusCppProfileMetadata();
    private static final String CPP_PROFILE_ID        = "org.eclipse.papyrus.cpp.profile";
    private static final String CPP_NS_URI            = C_CppPackage.eNS_URI; //"http://www.eclipse.org/papyrus/C_Cpp/1"
    private static final C_CppPackage CPP_PKG         = C_CppPackage.eINSTANCE;
    private static final String CPP_PATHMAP           = "pathmap://PapyrusC_Cpp_PROFILE/C_Cpp.profile.uml";
    private static final String CPP_ROOT_ID           = "_j9REUByGEduN1bTiWJ0lyw"; // TODO: Find out if this can be extracted programatically
    private static final String CPP_ROOT_URI          = CPP_PATHMAP + "#" + CPP_ROOT_ID;
    private static final String CPP_PROFILE_SUBDIR    = "profiles";
    private static final String CPP_PROFILE_MODEL     = "C_Cpp.profile.uml";
    @Override
    public String getPackageId()
    {
        return CPP_PROFILE_ID;
    }
    @Override
    public String getNS_URI()
    {
        return CPP_NS_URI;
    }
    @Override
    public EPackage getPackage()
    {
        return CPP_PKG;
    }
    @Override
    public String getPathmap()
    {
        return CPP_PATHMAP;
    }
    @Override
    public String getRootId()
    {
        return CPP_ROOT_ID;
    }
    @Override
    public String getRootURI()
    {
        return CPP_ROOT_URI;
    }
    @Override
    public String getPackageSubdirectory()
    {
        return CPP_PROFILE_SUBDIR;
    }
    @Override
    public String getPackageModel()
    {
        return CPP_PROFILE_MODEL;
    }

}
