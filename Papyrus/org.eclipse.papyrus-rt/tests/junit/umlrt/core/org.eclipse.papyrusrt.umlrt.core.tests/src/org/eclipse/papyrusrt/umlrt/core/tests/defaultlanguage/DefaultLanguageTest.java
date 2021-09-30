/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 501119
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.defaultlanguage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguageService;
import org.eclipse.papyrusrt.umlrt.core.internal.defaultlanguage.NoDefautLanguage;
import org.eclipse.papyrusrt.umlrt.core.tests.AbstractPapyrusRTCoreTest;
import org.junit.Test;

/**
 * Tests on model loading for default language
 */
public class DefaultLanguageTest extends AbstractPapyrusRTCoreTest {

	@Test
	public void testDefaultLanguageNoLanguageApplied() throws Exception {
		IDefaultLanguage language = getService().getActiveDefaultLanguage(rootModel);
		assertEquals("Default language should be the no language", NoDefautLanguage.INSTANCE, language);
	}

	/**
	 * Verifies, if the C++ language plug-in is installed in the current configuration, that
	 * its libraries are loaded in the resource set when the language is set.
	 * 
	 * @see <a href="http://eclip.se/501119">bug 501119</a>
	 */
	@Test
	@FailingTest("Removed dependency to CPP")
	public void librariesLoadedWhenLanguageSet() throws Exception {
		IDefaultLanguageService service = getService();
		Optional<? extends IDefaultLanguage> cpp = service.getAvailableDefaultLanguages().stream()
				.filter(l -> "umlrt-cpp".equals(l.getId()))
				.findAny();

		assumeThat("C++ language is not available", cpp.isPresent(), is(true));

		Command setLanguage = new RecordingCommand(modelSet.getEditingDomain(), "Set Language") {

			@Override
			protected void doExecute() {
				service.installLanguage(rootModel, cpp.get());
			}
		};

		int originalResourceCount = modelSet.getResourceSet().getResources().size();
		List<Resource> newResources = null;

		try {
			// Set the language
			getCommandStack().execute(setLanguage);

			// Check that the library was loaded
			newResources = modelSet.getResourceSet().getResources().subList(
					originalResourceCount, modelSet.getResourceSet().getResources().size());
			Optional<Resource> lib = newResources.stream()
					.filter(r -> r.getURI().lastSegment().equals("AnsiCLibrary.uml"))
					.findAny();
			assertThat("C++ language library not loaded", lib.isPresent(), is(true));
		} finally {
			// Clean up all that we did so as not to interfere with other tests

			try {
				if (getCommandStack().canUndo()) {
					getCommandStack().undo();
				}
			} finally {
				// Undo does not unload resources that were loaded by the command
				newResources = modelSet.getResourceSet().getResources().subList(
						originalResourceCount, modelSet.getResourceSet().getResources().size());

				newResources.forEach(Resource::unload);
				newResources.clear();
			}
		}
	}

	//
	// Test framework
	//

	IDefaultLanguageService getService() {
		IDefaultLanguageService result = null;

		try {
			result = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, rootModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertThat("Could not get Default Language Service", result, notNullValue());

		return result;
	}
}
