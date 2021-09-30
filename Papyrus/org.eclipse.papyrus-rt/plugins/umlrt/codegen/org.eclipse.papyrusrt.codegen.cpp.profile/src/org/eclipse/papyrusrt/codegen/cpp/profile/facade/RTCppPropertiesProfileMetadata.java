/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.profile.facade;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;
import org.eclipse.papyrusrt.xtumlrt.external.ExternalPackageMetadata;

/**
 * Meta-data for the RTCppProperties profile.
 * 
 * @see ExternalPackageMetadata
 * @author epp
 */
public final class RTCppPropertiesProfileMetadata extends ExternalPackageMetadata {

	/** Common instance. */
	public static final RTCppPropertiesProfileMetadata INSTANCE = new RTCppPropertiesProfileMetadata();

	/** Profile id. */
	private static final String ID = "org.eclipse.papyrusrt.codegen.cpp.profile";

	/** Name-space URI. */
	private static final String NS_URI = RTCppPropertiesPackage.eNS_URI;

	/** Pathmap. */
	private static final String BASEPATHMAP = "pathmap://UMLRT_CPP";

	/** Root node id. */
	// TODO: Find out if this can be extracted programatically
	private static final String ROOT_ID = "_vl5LALs8EeSTjNEQkASznQ";

	/** Subdirectory containing the model. */
	private static final String FOLDER = "profiles";

	/** File containing the model. */
	private static final String FILE = "RTCppProperties.profile.uml";

	/** {@link EPackage}. */
	private static final RTCppPropertiesPackage EPACKAGE = RTCppPropertiesPackage.eINSTANCE;

	/**
	 * Default constructor. Private as this class should not be extended.
	 *
	 */
	private RTCppPropertiesProfileMetadata() {
		super(ID, Kind.Profile, NS_URI, BASEPATHMAP, ROOT_ID, FOLDER, FILE, EPACKAGE);
	}

}
