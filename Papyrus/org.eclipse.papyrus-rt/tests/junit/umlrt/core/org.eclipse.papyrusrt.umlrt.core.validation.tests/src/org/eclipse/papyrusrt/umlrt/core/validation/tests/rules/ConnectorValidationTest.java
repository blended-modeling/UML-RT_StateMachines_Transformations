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

package org.eclipse.papyrusrt.umlrt.core.validation.tests.rules;

import static org.eclipse.papyrusrt.junit.matchers.StatusMatchers.hasSeverity;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Broad test cases for connector validation.
 */
@PluginResource("resources/connectors.di")
@RunWith(Parameterized.class)
public class ConnectorValidationTest {

	@Rule
	public final UMLRTModelSetFixture modelSet = new UMLRTModelSetFixture();

	private final String connectorName;

	public ConnectorValidationTest(String connectorName, String __) {
		super();

		this.connectorName = connectorName;
	}

	@Test
	public void correctConnector() {
		Class capsule1 = (Class) modelSet.getModel().getOwnedType("Capsule1");
		Connector connector = capsule1.getOwnedConnector(connectorName);

		IBatchValidator validator = ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH);
		assertThat(validator.validate(connector), hasSeverity(IStatus.OK));
	}

	@Test
	public void invalidConnector() {
		Class capsule4 = (Class) modelSet.getModel().getOwnedType("Capsule4");
		Connector connector = capsule4.getOwnedConnector(connectorName);

		IBatchValidator validator = ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH);
		assertThat(validator.validate(connector), hasSeverity(IStatus.WARNING | IStatus.ERROR));
	}

	//
	// Test framework
	//

	@Parameters(name = "{1}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ "RTConnector1", "delegation to part" },
				{ "RTConnector2", "delegation to internal behaviour" },
				{ "RTConnector4", "assembly" },
				{ "RTConnector3", "pass-through" },
		});
	}

}
