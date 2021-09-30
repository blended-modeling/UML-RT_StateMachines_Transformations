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

package org.eclipse.papyrusrt.umlrt.uml.tests;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTDiagnostician;
import org.eclipse.uml2.common.edit.provider.IItemQualifiedTextProvider;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Regression tests for validation of UML-RT models, in particular the standard
 * UML metamodel constraints.
 */
@RunWith(Parameterized.class)
@NoFacade
@Category(CapsuleTests.class)
public class ValidationTest {

	@Rule
	public final ModelFixture model;

	private final boolean expectValid;

	public ValidationTest(String modelPath, String name, boolean expectValid) {
		super();

		this.model = new ModelFixture(modelPath);
		this.expectValid = expectValid;
	}

	@Test
	public void validateModel() {
		Map<Object, Object> context = new HashMap<>();
		context.put(EValidator.SubstitutionLabelProvider.class, new Labeller());
		Diagnostic diagnostic = UMLRTDiagnostician.INSTANCE.validate(model.getModel(), context);

		if (expectValid) {
			assertThat("Validation found problems", diagnostic, isOK());
		} else {
			assertThat("No validation problem found", diagnostic, not(isOK()));
		}
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(
				"validation/inheritance.uml",
				"validation/redefinition.uml",
				"validation/statemachines/inheritance.uml",
				"validation/statemachines/redefinition.uml",
				"validation/statemachines/invalid.uml",
				"validation/connectors.uml",
				"no_inheritance.uml")
				.stream().map(e -> new Object[] {
						e,
						e.substring(e.lastIndexOf('/') + 1),
						!e.matches(".+\\binvalid\\b.+") })
				.collect(Collectors.toList());
	}

	static Matcher<Diagnostic> isOK() {
		return new BaseMatcher<Diagnostic>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("is OK");
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof Diagnostic)
						&& (((Diagnostic) item).getSeverity() <= Diagnostic.OK);
			}
		};
	}

	static final class Labeller implements EValidator.SubstitutionLabelProvider {
		private AdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
				new UMLItemProviderAdapterFactory(),
				new EcoreItemProviderAdapterFactory(),
				new ReflectiveItemProviderAdapterFactory(),
		});

		private String getText(Object object) {
			String result = null;

			IItemQualifiedTextProvider itemQualifiedTextProvider = (IItemQualifiedTextProvider) adapterFactory
					.adapt(object, IItemQualifiedTextProvider.class);

			if (itemQualifiedTextProvider != null) {
				result = itemQualifiedTextProvider.getQualifiedText(object);
			} else {
				IItemLabelProvider labelProvider = (IItemLabelProvider) adapterFactory.adapt(object,
						IItemLabelProvider.class);
				if (labelProvider != null) {
					result = labelProvider.getText(object);
				}
			}

			return result;
		}

		@Override
		public String getObjectLabel(EObject eObject) {
			return getText(eObject);
		}

		@Override
		public String getFeatureLabel(EStructuralFeature eStructuralFeature) {
			return getText(eStructuralFeature);
		}

		@Override
		public String getValueLabel(EDataType eDataType, Object value) {
			return EcoreUtil.convertToString(eDataType, value);
		}

	}
}
