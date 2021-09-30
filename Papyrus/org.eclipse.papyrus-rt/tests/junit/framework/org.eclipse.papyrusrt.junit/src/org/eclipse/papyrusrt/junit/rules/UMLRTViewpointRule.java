/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.junit.rules;

import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * A rule that ensures the UML RT viewpoint is active for the duration of its
 * scope.
 */
public class UMLRTViewpointRule extends TestWatcher {
	private PolicyChecker original;

	public UMLRTViewpointRule() {
		super();
	}

	@Override
	protected void starting(Description description) {
		// original = PolicyChecker.getCurrent();
		// PapyrusConfiguration config = PolicyChecker.loadConfigurationFrom(
		// "platform:/plugin/org.eclipse.papyrusrt.umlrt.tooling.diagram.common/configuration/UMLRT.configuration");
		// Stakeholder modeler = config.getDefaultStakeholder();
		// PapyrusViewpoint umlrt = (PapyrusViewpoint) modeler.getViewpoints().get(0);
		// PolicyChecker umlrtPolicy = new PolicyChecker(config, umlrt, false);
		// PolicyChecker.setCurrent(umlrtPolicy);
	}

	@Override
	protected void finished(Description description) {
		// PolicyChecker.setCurrent(original);
		// original = null;
	}
}
