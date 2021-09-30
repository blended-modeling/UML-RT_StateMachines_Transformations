/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.validation.tests.rules;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.validation.commands.ValidateModelCommand;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrusrt.umlrt.core.validation.tests.Activator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.util.UMLValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Tests to verify that the validation environment for UML-RT State Machines is correct.
 */
public class StateMachineValidationTest extends AbstractValidationEditorTest {

	private static final String PATH = "resources/statemachines/"; //$NON-NLS-1$

	private static final String PROJECT_NAME = "StateMachineValidationTest"; //$NON-NLS-1$

	public static final String CAPSULE = "Top";

	private Diagnostic diagnostic;

	@Rule
	public final TestName testName = new TestName();

	@Rule
	public final HouseKeeper housekeeper = new HouseKeeper();

	Model model;

	public StateMachineValidationTest() {
		super();
	}

	@Test
	public void inheritance() throws Exception {
		assertThat("Validation found problems", diagnostic, isOK());
	}

	@Test
	public void redefinition() throws Exception {
		assertThat("Validation found problems", diagnostic, isOK());
	}

	@Test
	public void invalid() throws Exception {
		assertThat("Validation did not find problems", diagnostic, hasSeverity(Diagnostic.WARNING));

		// The problems are all invalid transition sources
		List<Diagnostic> problems = diagnostic.getChildren().stream()
				.filter(d -> d.getCode() == UMLValidator.REDEFINABLE_ELEMENT__REDEFINITION_CONSISTENT)
				.filter(d -> d.getData().get(0) instanceof Transition)
				.collect(Collectors.toList());
		assertThat("Wrong number of inconsistent transition redefinitions", problems.size(), is(2));
	}

	//
	// Test framework
	//

	@Before
	public void setup() throws Exception {
		initModel(PROJECT_NAME, testName.getMethodName(), Activator.getDefault().getBundle());

		// validate the new model

		Assert.assertNotNull("RootModel is null", getRootUMLModel()); //$NON-NLS-1$
		model = (Model) getRootUMLModel();

		EditingDomain domain = TransactionUtil.getEditingDomain(model);

		// Don't give the command a diagnostician, because we want to be sure that it can find
		// the right one on its extension point
		ValidateModelCommand validateModelCommand = new ValidateModelCommand(model);
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				domain.getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(validateModelCommand));
			}
		});

		diagnostic = validateModelCommand.getDiagnostic();
	}

	@Override
	protected String getSourcePath() {
		return PATH;
	}

}
