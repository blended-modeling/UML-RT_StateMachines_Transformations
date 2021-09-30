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

public class RTCppPropertiesProfileMetadata extends RegisteredPackageMetadata
{
    public static final RTCppPropertiesProfileMetadata INSTANCE = new RTCppPropertiesProfileMetadata();
    private static final String RTCPP_PROFILE_ID        = "org.eclipse.papyrusrt.codegen.cpp.profile";
    private static final String RTCPP_NS_URI            = "http://www.eclipse.org/papyrusrt/codegen/cpp/profile";
    private static final EPackage RTCPP_PKG             = null;
    private static final String RTCPP_PATHMAP           = "pathmap://UMLRT_CPP/RTCppProperties.profile.uml";
    private static final String RTCPP_ROOT_ID           = "_vl5LALs8EeSTjNEQkASznQ"; // TODO: Find out if this can be extracted programatically
    private static final String RTCPP_ROOT_URI          = RTCPP_PATHMAP + "#" + RTCPP_ROOT_ID;
    private static final String RTCPP_PROFILE_SUBDIR    = "profiles";
    private static final String RTCPP_PROFILE_MODEL     = "RTCppProperties.profile.uml";
    @Override
    public String getPackageId()
    {
        return RTCPP_PROFILE_ID;
    }
    @Override
    public String getNS_URI()
    {
        return RTCPP_NS_URI;
    }
    @Override
    public EPackage getPackage()
    {
        return RTCPP_PKG;
    }
    @Override
    public String getPathmap()
    {
        return RTCPP_PATHMAP;
    }
    @Override
    public String getRootId()
    {
        return RTCPP_ROOT_ID;
    }
    @Override
    public String getRootURI()
    {
        return RTCPP_ROOT_URI;
    }
    @Override
    public String getPackageSubdirectory()
    {
        return RTCPP_PROFILE_SUBDIR;
    }
    @Override
    public String getPackageModel()
    {
        return RTCPP_PROFILE_MODEL;
    }

}
