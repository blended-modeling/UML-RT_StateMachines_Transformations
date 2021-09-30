/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.profile.tests.legacy;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Profile;
import org.junit.Assert;
import org.junit.Test;

/**
 * Integration test for Papyrus/Papyrus-RT
 *
 * Since both projects evolve on a separate schedule, with interdependencies,
 * we need to ensure a few compatibility rules
 *
 * @author Camille Letavernier
 *
 */
public class LegacyTest {

	@Test
	public void testPathmap() {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource profileResource = resourceSet.getResource(URI.createURI("pathmap://UML_RT_PROFILE/uml-rt.profile.uml"), true);
		Profile profile = (Profile)profileResource.getEObject("_1h74oEeVEeO0lv5O1DTHOQ");
		Assert.assertNotNull("Cannot find the UML-RT Profile", profile);
		Assert.assertFalse("The UML-RT Profile shouldn't be a proxy", profile.eIsProxy());
	}

	@Test
	public void testStaticProfile() {
		EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/papyrus/umlrt");
		Assert.assertNotNull("Cannot find the static definition for UML-RT", ePackage);
		Assert.assertFalse("The UML-RT EPackage could not be resolved", ePackage.eIsProxy());
	}

	@Test
	public void testPathmapStateMachine() {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource profileResource = resourceSet.getResource(URI.createURI("pathmap://UML_RT_PROFILE/UMLRealTimeSM-addendum.profile.uml"), true);
		Profile profile = (Profile)profileResource.getEObject("_KLcn0FDtEeOA4ecmvfqvaw");
		Assert.assertNotNull("Cannot find the UML-RT Profile", profile);
		Assert.assertFalse("The UML-RT Profile shouldn't be a proxy", profile.eIsProxy());
	}

	@Test
	public void testStaticProfileStateMachine() {
		EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/papyrus/umlrt/statemachine");
		Assert.assertNotNull("Cannot find the static definition for UML-RT", ePackage);
		Assert.assertFalse("The UML-RT EPackage could not be resolved", ePackage.eIsProxy());
	}
}
