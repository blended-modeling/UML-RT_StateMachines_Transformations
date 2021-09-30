/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.tests.utils;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrusrt.umlrt.core.utils.ModelUtils;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTResourcesUtil;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * Test cases for the {@link ModelUtils} class.
 */
public class ModelUtilsTest {

	public ModelUtilsTest() {
		super();
	}

	@Test
	public void createWithEObjectContext() {
		EClass eclass = UMLPackage.Literals.PORT;

		EObject object = ModelUtils.create((EObject) null, eclass);
		assertThat("No result for null context", object, instanceOf(Port.class));
		assertThat("No result for free-floating context", ModelUtils.create(object, eclass), instanceOf(Port.class));
	}

	@Test
	public void createWithResourceContext() {
		EClass eclass = UMLPackage.Literals.PORT;

		Resource resource = new ResourceImpl(URI.createURI("test://bogus"));

		assertThat("No result for free resource context", ModelUtils.create(resource, eclass), instanceOf(Port.class));
	}

	@Test
	public void createWithUMLResourceSetContext() {
		ResourceSet rset = new ResourceSetImpl();
		Resource resource = rset.createResource(URI.createURI("test://bogus.uml"));

		EClass eclass = UMLPackage.Literals.PORT;

		assertThat("No result for UML resource-set context", ModelUtils.create(resource, eclass), instanceOf(Port.class));
	}

	@SuppressWarnings("restriction")
	@Test
	public void createWithUMLRTResourceSetContext() {
		ResourceSet rset = new ResourceSetImpl();
		UMLRTResourcesUtil.init(rset);
		Resource resource = rset.createResource(URI.createURI("test://bogus.uml"));

		EClass eclass = UMLPackage.Literals.PORT;

		EObject object = ModelUtils.create(resource, eclass);
		assertThat("No result for UML-RT resource-set context", object, instanceOf(Port.class));
		assertThat("Not an instance of the UML-RT implementation", object,
				instanceOf(org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement.class));
	}
}
