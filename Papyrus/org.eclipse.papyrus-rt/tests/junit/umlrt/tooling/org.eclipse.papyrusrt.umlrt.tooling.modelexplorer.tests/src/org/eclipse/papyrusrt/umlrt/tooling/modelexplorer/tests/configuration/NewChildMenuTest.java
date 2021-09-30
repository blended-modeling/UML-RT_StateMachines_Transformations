/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 495140
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.tests.configuration;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreationMenu;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.junit.utils.tests.AbstractEMFResourceTest;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.Activator;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test the new child model :
 * - checks that all element type are registered
 * - validate the model
 */
public class NewChildMenuTest extends AbstractEMFResourceTest {

	public static final String NEW_CHILD_MENU_PATH = Activator.PLUGIN_ID + "/newchildmenu/newChildMenu.creationmenumodel"; // $NON-NLS-0$

	@ClassRule
	public static final TestRule elementTypes = new ElementTypesRule();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFileUri() {
		return NEW_CHILD_MENU_PATH;
	}

	@Test
	public void validateResource() {
		doValidateResource();
	}

	/**
	 * Check that referenced element type are in the registry
	 * 
	 * @throws ServiceException
	 */
	@Test
	public void checkMenuNewChildElementTypeIdRefs() throws ServiceException {
		URI createPlatformPluginURI = URI.createPlatformPluginURI(NEW_CHILD_MENU_PATH, true);
		ResourceSetImpl resourceSetImpl = new ResourceSetImpl();
		Resource resource = resourceSetImpl.getResource(createPlatformPluginURI, true);

		TreeIterator<EObject> allContents = resource.getAllContents();
		while (allContents.hasNext()) {
			EObject eObject = allContents.next();

			if (eObject instanceof CreationMenu) {
				CreationMenu p = (CreationMenu) eObject;
				ElementTypeConfiguration elementType = p.getElementType();
				assertThat("element type unknown: " + elementType, p, notNullValue());

				String iconPath = p.getIcon();
				if (iconPath != null && !"".equals(iconPath)) {
					try {
						URL url = new URL(iconPath);
						Assert.assertNotNull("The icon " + iconPath + " can't be found", FileLocator.find(url));
					} catch (MalformedURLException e) {
						Assert.fail("The new child menu is refering to a malformed url " + iconPath);
					}
				}
			}
		}
	}

}
