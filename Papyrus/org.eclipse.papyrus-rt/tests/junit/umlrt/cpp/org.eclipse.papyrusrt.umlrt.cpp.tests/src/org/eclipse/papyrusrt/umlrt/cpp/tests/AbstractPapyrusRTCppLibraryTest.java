package org.eclipse.papyrusrt.umlrt.cpp.tests;
/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ModelUtils;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.PapyrusProjectUtils;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrus.uml.tools.model.UmlUtils;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;

/**
 * Generic class test for edition tests
 */
public abstract class AbstractPapyrusRTCppLibraryTest extends AbstractPapyrusTest {

	@ClassRule
	public static final HouseKeeper.Static houseKeeper = new HouseKeeper.Static();

	protected static IProject createProject;

	protected static IFile cppPapyrusModel;
	protected static IFile noCppPapyrusModel;

	protected static IMultiDiagramEditor openCppPapyrusEditor;
	protected static IMultiDiagramEditor openNoCppPapyrusEditor;

	protected static ModelSet cppModelset;
	protected static ModelSet noCppModelset;

	protected static UmlModel cppUmlIModel;
	protected static UmlModel noCppUmlIModel;

	protected static Model cppRootModel;
	protected static Model noCppRootModel;

	protected static TransactionalEditingDomain cppTransactionalEditingDomain;
	protected static TransactionalEditingDomain noCppTransactionalEditingDomain;

	public static URI CPP_LIBRARY_URI = URI.createURI("pathmap://PapyrusC_Cpp_LIBRARIES/AnsiCLibrary.uml");

	/**
	 * Init test class
	 */
	@BeforeClass
	public static void initCreateElementTest() throws Exception {

		// create Project
		createProject = houseKeeper.createProject("UMLRTLibraryCppTests");

		// import test model
		try {
			cppPapyrusModel = PapyrusProjectUtils.copyPapyrusModel(createProject, Platform.getBundle("org.eclipse.papyrusrt.umlrt.cpp.tests"), "/resource/", "CppLanguage");
			noCppPapyrusModel = PapyrusProjectUtils.copyPapyrusModel(createProject, Platform.getBundle("org.eclipse.papyrusrt.umlrt.cpp.tests"), "/resource/", "NoLanguage");
		} catch (CoreException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// // ensure viewpoint...
		// PapyrusConfiguration loadConfigurationFrom = PolicyChecker.loadConfigurationFrom("platform:/plugin/org.eclipse.papyrusrt.umlrt.tooling.diagram.common/configuration/UMLRT.configuration");
		// PolicyChecker.setCurrent(new PolicyChecker(loadConfigurationFrom, (PapyrusViewpoint) loadConfigurationFrom.getStakeholders().get(0).getViewpoints().get(0), false));
		// Display.getDefault().syncExec(new Runnable() {
		//
		// @Override
		// public void run() {
		// // force loading of the state machine plugin, but in UI thread, as the element types require some UI...
		// ElementTypeSetConfigurationRegistry registry = ElementTypeSetConfigurationRegistry.getInstance();
		// }
		// });
		//
		// Assert.assertTrue("Invalid viewpoint", PolicyChecker.getCurrent().getConfiguration().equals(loadConfigurationFrom));

		// open project
		openCppPapyrusEditor = houseKeeper.openPapyrusEditor(cppPapyrusModel);
		openNoCppPapyrusEditor = houseKeeper.openPapyrusEditor(noCppPapyrusModel);

		cppTransactionalEditingDomain = openCppPapyrusEditor.getAdapter(TransactionalEditingDomain.class);
		assertTrue("Impossible to init editing domain", cppTransactionalEditingDomain instanceof TransactionalEditingDomain);

		noCppTransactionalEditingDomain = openNoCppPapyrusEditor.getAdapter(TransactionalEditingDomain.class);
		assertTrue("Impossible to init editing domain", noCppTransactionalEditingDomain instanceof TransactionalEditingDomain);

		// retrieve UML model from this editor
		try {
			cppModelset = ModelUtils.getModelSetChecked(openCppPapyrusEditor.getServicesRegistry());
			cppUmlIModel = UmlUtils.getUmlModel(cppModelset);
			cppRootModel = (Model) cppUmlIModel.lookupRoot();

			noCppModelset = ModelUtils.getModelSetChecked(openNoCppPapyrusEditor.getServicesRegistry());
			noCppUmlIModel = UmlUtils.getUmlModel(noCppModelset);
			noCppRootModel = (Model) noCppUmlIModel.lookupRoot();
		} catch (ServiceException e) {
			fail(e.getMessage());
		} catch (NotFoundException e) {
			fail(e.getMessage());
		} catch (ClassCastException e) {
			fail(e.getMessage());
		}
		try {
			initExistingElements();
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// force load of the element type registry. Will need to load in in UI thread because of some advice in communication diag: see org.eclipse.gmf.tooling.runtime.providers.DiagramElementTypeImages
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				ElementTypeSetConfigurationRegistry registry = ElementTypeSetConfigurationRegistry.getInstance();
				Assert.assertNotNull("registry should not be null after init", registry);
				Assert.assertNotNull("element type should not be null", UMLRTElementTypesEnumerator.CAPSULE);
			}
		});
	}

	/**
	 * Init fields corresponding to element in the test model
	 */
	protected static void initExistingElements() throws Exception {
		// none to init yet
	}
}
