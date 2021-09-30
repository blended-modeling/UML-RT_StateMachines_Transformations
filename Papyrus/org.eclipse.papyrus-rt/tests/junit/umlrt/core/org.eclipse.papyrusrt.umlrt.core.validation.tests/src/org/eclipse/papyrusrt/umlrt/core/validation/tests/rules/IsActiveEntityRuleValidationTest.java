/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.validation.tests.rules;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.validation.commands.ValidateModelCommand;
import org.eclipse.papyrus.uml.service.validation.internal.UMLDiagnostician;
import org.eclipse.papyrusrt.umlrt.core.validation.tests.Activator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Sample test for validation Rule: Capsule should be always active.
 */
public class IsActiveEntityRuleValidationTest extends AbstractValidationEditorTest {

	public static final String CONSTRAINT_PLUGIN = "org.eclipse.papyrusrt.umlrt.core.validation"; //$NON-NLS-1$

	public static final String CONSTRAINT_ID = "UMLRealTime.Capsule.Capsules are always active classifiers"; //$NON-NLS-1$

	public static final String RESOURCES_PATH = "resources/"; //$NON-NLS-1$

	public static final String MODEL_NAME = "IsActiveEntityRule"; //$NON-NLS-1$

	public static final String PROJECT_NAME = "IsActiveEntityRuleValidationTest"; //$NON-NLS-1$

	public final static String CAPSULE_NOT_ACTIVE_NAME = "CapsuleNotActive"; //$NON-NLS-1$

	public final static String CAPSULE_ACTIVE_NAME = "CapsuleActive"; //$NON-NLS-1$

	/** validation diagnostic */
	protected Diagnostic globalDiagnostic;

	/** root model */
	public Model model;

	/** active capsule model */
	public Class activeCapsule;

	/** not active capsule */
	public Class notActiveCapsule;

	public List<Diagnostic> isActiveDiagnostics;

	@Before
	public void initModelForValidationTest() throws Exception {
		initModel(PROJECT_NAME, MODEL_NAME, Activator.getDefault().getBundle());

		// validate the new model

		Assert.assertNotNull("RootModel is null", getRootUMLModel()); //$NON-NLS-1$
		model = (Model) getRootUMLModel();
		notActiveCapsule = (Class) model.getPackagedElement(CAPSULE_NOT_ACTIVE_NAME);
		Assert.assertNotNull("Impossible to find capsule " + CAPSULE_NOT_ACTIVE_NAME, notActiveCapsule);
		activeCapsule = (Class) model.getPackagedElement(CAPSULE_ACTIVE_NAME);
		Assert.assertNotNull("Impossible to find capsule " + CAPSULE_ACTIVE_NAME, activeCapsule);
		final EditingDomain domain = TransactionUtil.getEditingDomain(model);
		final ValidateModelCommand validateModelCommand = new ValidateModelCommand(model, new UMLDiagnostician());
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				domain.getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(validateModelCommand));
			}
		});

		// check that the constraint exist
		ConstraintRegistry instance = ConstraintRegistry.getInstance();
		IConstraintDescriptor descriptor = instance.getDescriptor(CONSTRAINT_PLUGIN, CONSTRAINT_ID);
		Assert.assertNotNull("Constraint is missing", descriptor);

		globalDiagnostic = validateModelCommand.getDiagnostic();
		isActiveDiagnostics = findDiagnosticBySource(globalDiagnostic, CONSTRAINT_PLUGIN + "." + CONSTRAINT_ID);
	}

	@Override
	protected String getSourcePath() {
		return RESOURCES_PATH;
	}

	/**
	 * Simple failing validation for IsActiveEntityRule
	 */
	@Test
	public void validateIsActiveEntityRule_notActiveCapsule() throws Exception {
		// get the diagnostic and check for the given capsule
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(isActiveDiagnostics, notActiveCapsule);
		Assert.assertEquals("The rule isActive should trigger an issue for this capsule", 1, diagnostics.size());
	}

	/**
	 * Simple valid validation for IsActiveEntityRule
	 */
	@Test
	public void validateIsActiveEntityRule_ActiveCapsule() throws Exception {
		// get the diagnostic and check for the given capsule
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(isActiveDiagnostics, activeCapsule);
		Assert.assertEquals("The rule isActive should not trigger an issue for this capsule", 0, diagnostics.size());
	}

}
