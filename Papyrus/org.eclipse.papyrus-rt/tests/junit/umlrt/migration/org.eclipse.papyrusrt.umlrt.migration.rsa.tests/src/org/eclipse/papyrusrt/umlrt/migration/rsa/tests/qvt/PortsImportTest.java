/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.migration.rsa.tests.qvt;

import static org.junit.Assert.assertEquals;

import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Test;

/**
 * @author as247872
 *
 */
public class PortsImportTest extends AbstractTransformationTest {

	/**
	 * Initializes me.
	 */
	public PortsImportTest() {
		super();
	}

	/**
	 * Verify that Ports with isService = false are protected
	 * 
	 * @see <a href="http://eclip.se/508318">bug 508318</a>
	 */
	@Test
	public void checkPortVisibility() throws Exception {
		simpleImport("resources/rt/PortModel.emx");

		openEditor();

		rootPackage.allOwnedElements().stream()
				.filter(Port.class::isInstance)
				.filter(p -> !((Port) p).isService())
				.forEach(port -> assertEquals(" Port visibility Error: port should be protected", VisibilityKind.PROTECTED_LITERAL, ((Port) port).getVisibility()));
	}

}

