/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.cpp.tests.lifecycle;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguageService;
import org.eclipse.papyrusrt.umlrt.core.internal.defaultlanguage.NoDefautLanguage;
import org.eclipse.papyrusrt.umlrt.cpp.tests.AbstractPapyrusRTCppLibraryTest;
import org.eclipse.uml2.uml.Package;
import org.junit.Test;

/**
 * Tests on loading / unloading model with Cpp language.
 */
public class CppModelLifecycleTest extends AbstractPapyrusRTCppLibraryTest {

	private static final int NUMBER_OF_CPP_PRIMITIVE_TYPES = 22;
	private static final int NUMBER_OF_SYSTEM_PROTOCOLS = 3;
	private static final int NUMBER_OF_SYSTEM_CLASSES = 4;
	private static final String UMLRT_CPP_ID = "umlrt-cpp";

	@Test
	public void testNoLanguage() throws Exception {
		IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, cppRootModel);
		assertNotNull("DefaultLanguage service should not be null", service);
		IDefaultLanguage language = service.getActiveDefaultLanguage(noCppRootModel);
		assertEquals("DefaultLanguage should be noDefault", NoDefautLanguage.INSTANCE.getId(), language.getId());
	}

	@Test
	public void testLoadCppModel_LoadedLibraries() throws Exception {
		IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, cppRootModel);
		assertNotNull("DefaultLanguage service should not be null", service);
		IDefaultLanguage language = service.getActiveDefaultLanguage(cppRootModel);
		assertEquals("DefaultLanguage should be cpp", UMLRT_CPP_ID, language.getId());

		// retrieve all cpp primitive types
		Resource cppResource = null;
		for (Resource resource : cppRootModel.eResource().getResourceSet().getResources()) {
			if (CPP_LIBRARY_URI.equals(resource.getURI())) {
				cppResource = resource;
			}
		}

		assertNotNull("Impossible to find Cpp library from cppRootModel", cppResource);
		assertEquals("Cpp Library should not be empty", 2, cppResource.getContents().size()); // package and stereotype "ModelLibrary"
		assertTrue("root element should be a Package", cppResource.getContents().get(0) instanceof Package);
		Package cpplibrary = (Package) cppResource.getContents().get(0);
		assertEquals("root element should be named AnsiCLibrary", "AnsiCLibrary", cpplibrary.getName());
		assertEquals("root element should have Types", NUMBER_OF_CPP_PRIMITIVE_TYPES, cpplibrary.getOwnedTypes().size());
	}

	@Test
	public void testLoadCppModel_AccessibleTypes() throws Exception {
		IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, cppRootModel);
		assertNotNull("DefaultLanguage service should not be null", service);
		IDefaultLanguage language = service.getActiveDefaultLanguage(cppRootModel);
		assertEquals("DefaultLanguage should be cpp", UMLRT_CPP_ID, language.getId());
		assertEquals("Unexpected number of types in the cpp library", NUMBER_OF_CPP_PRIMITIVE_TYPES, language.getSpecificPrimitiveTypes(cppRootModel.eResource().getResourceSet()).size());
	}


	@Test
	public void testLoadCppModel_SystemProtocols() throws Exception {
		IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, cppRootModel);
		assertNotNull("DefaultLanguage service should not be null", service);
		IDefaultLanguage language = service.getActiveDefaultLanguage(cppRootModel);
		assertEquals("DefaultLanguage should be cpp", UMLRT_CPP_ID, language.getId());
		assertEquals("Unexpected number of system protocols in the cpp library", NUMBER_OF_SYSTEM_PROTOCOLS, language.getSystemProtocols(cppRootModel.eResource().getResourceSet()).size());
	}

	@Test
	public void testLoadCppModel_BaseProtocol() throws Exception {
		IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, cppRootModel);
		assertNotNull("DefaultLanguage service should not be null", service);
		IDefaultLanguage language = service.getActiveDefaultLanguage(cppRootModel);
		assertEquals("DefaultLanguage should be cpp", UMLRT_CPP_ID, language.getId());
		assertNotNull("Base protocol should not be null in the cpp library", language.getBaseProtocol(cppRootModel.eResource().getResourceSet()));
		assertTrue("Base protocol should be abstract in the cpp library", language.getBaseProtocol(cppRootModel.eResource().getResourceSet()).isAbstract());
	}

	@Test
	public void testLoadCppModel_SystemClasses() throws Exception {
		IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, cppRootModel);
		assertNotNull("DefaultLanguage service should not be null", service);
		IDefaultLanguage language = service.getActiveDefaultLanguage(cppRootModel);
		assertEquals("DefaultLanguage should be cpp", UMLRT_CPP_ID, language.getId());
		assertEquals("Unexpected number of system protocols in the cpp library", NUMBER_OF_SYSTEM_CLASSES, language.getSystemClasses(cppRootModel.eResource().getResourceSet()).size());
	}

	@Test
	public void testApplyCppLanguage() throws Exception {
		IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, noCppRootModel);
		assertNotNull("DefaultLanguage service should not be null", service);
		IDefaultLanguage language = service.getActiveDefaultLanguage(noCppRootModel);
		assertEquals("DefaultLanguage should be noDefault", NoDefautLanguage.INSTANCE.getId(), language.getId());

		// apply cpp language
		Optional<? extends IDefaultLanguage> cpp = service.getAvailableDefaultLanguages().stream()
				.filter(l -> "umlrt-cpp".equals(l.getId()))
				.findAny();

		assumeThat("C++ language is not available", cpp.isPresent(), is(true));

		TransactionalEditingDomain transactionalEditingDomain = TransactionUtil.getEditingDomain(noCppRootModel);

		Command setLanguage = new RecordingCommand(transactionalEditingDomain, "Set Language") {

			@Override
			protected void doExecute() {
				service.installLanguage(noCppRootModel, cpp.get());
			}
		};

		ResourceSet modelset = noCppRootModel.eResource().getResourceSet();

		int originalResourceCount = modelset.getResources().size();
		List<Resource> newResources = null;

		try {
			// Set the language
			transactionalEditingDomain.getCommandStack().execute(setLanguage);

			// Check that the library was loaded
			newResources = modelset.getResources().subList(
					originalResourceCount, modelset.getResources().size());
			Optional<Resource> lib = newResources.stream()
					.filter(r -> "AnsiCLibrary.uml".equals(r.getURI().lastSegment()))
					.findAny();
			assertThat("C++ language library not loaded", lib.isPresent(), is(true));
			Optional<Resource> rtslib = newResources.stream()
					.filter(r -> "UMLRT-RTS.uml".equals(r.getURI().lastSegment()))
					.findAny();
			assertThat("UMLRT-RTS library not loaded", rtslib.isPresent(), is(true));
		} finally {
			// Clean up all that we did so as not to interfere with other tests

			try {
				if (transactionalEditingDomain.getCommandStack().canUndo()) {
					transactionalEditingDomain.getCommandStack().undo();
				}
			} finally {
				// Undo does not unload resources that were loaded by the command
				newResources = modelset.getResources().subList(
						originalResourceCount, modelset.getResources().size());

				newResources.forEach(Resource::unload);
				newResources.clear();
			}
		}

	}

}
