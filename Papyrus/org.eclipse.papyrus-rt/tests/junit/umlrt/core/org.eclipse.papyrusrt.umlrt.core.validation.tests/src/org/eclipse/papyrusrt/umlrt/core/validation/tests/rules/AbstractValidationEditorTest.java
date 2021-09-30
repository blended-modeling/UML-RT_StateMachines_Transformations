/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 510323
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.validation.tests.rules;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.papyrus.junit.utils.tests.AbstractEditorTest;
import org.eclipse.uml2.uml.Element;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;


/**
 * Abstract test for validat
 * import org.eclipse.uml2.uml.Element;ion rules
 */
public abstract class AbstractValidationEditorTest extends AbstractEditorTest {

	public static final String VALIDATE_COMMAND_ID = "org.eclipse.papyrus.validation.ValidateModelCommand"; //$NON-NLS-1$


	/**
	 * find diagnostic by source
	 * (Should be unique per element if the source is correctly defined)
	 */
	public List<Diagnostic> findDiagnosticBySource(Diagnostic diagnostic, String source) {
		List<Diagnostic> foundDiagnostic = new ArrayList<>();
		List<Diagnostic> children = diagnostic.getChildren();
		if (source.equals(diagnostic.getSource())) {
			foundDiagnostic.add(diagnostic);
		}
		if (children != null && !children.isEmpty()) {
			for (Diagnostic diagnostic2 : children) {
				foundDiagnostic.addAll(findDiagnosticBySource(diagnostic2, source));
			}
		}
		return foundDiagnostic;
	}

	public List<Diagnostic> filterDiagnosticsByElement(List<Diagnostic> diagnostics, Element element) {
		List<Diagnostic> filteredDiagnostics = new ArrayList<>();
		for (Diagnostic diagnostic : diagnostics) {
			List<?> datas = diagnostic.getData();
			if (datas != null && !datas.isEmpty()) {
				// try to get first element. According to Diagnostic#getData() documentation : The first element is typically the object that is the primary source of the problem;
				Object o = datas.get(0);
				if (element.equals(o)) {
					filteredDiagnostics.add(diagnostic);
				}
			}
		}
		return filteredDiagnostics;
	}


	public static Matcher<Diagnostic> isOK() {
		return notWorseThan(Diagnostic.OK);
	}

	public static Matcher<Diagnostic> notWorseThan(int severity) {
		return new BaseMatcher<Diagnostic>() {
			@Override
			public void describeTo(Description description) {
				switch (severity) {
				case Diagnostic.OK:
					description.appendText("is OK");
					break;
				case Diagnostic.INFO:
					description.appendText("is at most INFO");
					break;
				case Diagnostic.WARNING:
					description.appendText("is at most WARNING");
					break;
				case Diagnostic.ERROR:
					description.appendText("is at most ERROR");
					break;
				default:
					fail("Invalid severity value: " + severity);
					break;
				}
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof Diagnostic)
						&& (((Diagnostic) item).getSeverity() <= severity);
			}
		};
	}

	public static Matcher<Diagnostic> notBetterThan(int severity) {
		return new BaseMatcher<Diagnostic>() {
			@Override
			public void describeTo(Description description) {
				switch (severity) {
				case Diagnostic.OK:
					description.appendText("is at least OK");
					break;
				case Diagnostic.INFO:
					description.appendText("is at least INFO");
					break;
				case Diagnostic.WARNING:
					description.appendText("is at least WARNING");
					break;
				case Diagnostic.ERROR:
					description.appendText("is at least ERROR");
					break;
				default:
					fail("Invalid severity value: " + severity);
					break;
				}
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof Diagnostic)
						&& (((Diagnostic) item).getSeverity() >= severity);
			}
		};
	}

	public static Matcher<Diagnostic> hasSeverity(int severity) {
		return new BaseMatcher<Diagnostic>() {
			@Override
			public void describeTo(Description description) {
				switch (severity) {
				case Diagnostic.OK:
					description.appendText("is OK");
					break;
				case Diagnostic.INFO:
					description.appendText("is INFO");
					break;
				case Diagnostic.WARNING:
					description.appendText("is WARNING");
					break;
				case Diagnostic.ERROR:
					description.appendText("is ERROR");
					break;
				default:
					fail("Invalid severity value: " + severity);
					break;
				}
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof Diagnostic)
						&& (((Diagnostic) item).getSeverity() == severity);
			}
		};
	}
}
