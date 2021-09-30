/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.core.IConfiguredHintedElementType;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.infra.ui.util.UIUtil;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.junit.Assert;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * A rule that makes element types from some model(s) available during the
 * execution of a test. This ensures that the types are registered for that
 * test to use but that they will not then potentially interfere with other
 * tests. Using this rule without any custom element types models will
 * nonetheless ensure the availability of the core UML-RT element types.
 * 
 * @see ElementTypesResource
 */
public class ElementTypesRule implements TestRule {

	@Override
	public Statement apply(Statement base, Description description) {
		Collection<URI> uris = getTypesResources(description);

		// Regardless of whether we have any types resources, we need to
		// ensure initialization of the registry
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				ElementTypeSetConfigurationRegistry reg = initializeElementTypesRegistry();

				ResourceSet rset = new ResourceSetImpl();
				Collection<ElementTypeSetConfiguration> configs = loadTypes(uris, rset);
				configs.forEach(config -> reg.loadElementTypeSetConfiguration("org.eclipse.papyrusrt.umlrt.architecture", config));

				try {
					base.evaluate();
				} finally {
					configs.forEach(config -> reg.unload("org.eclipse.papyrusrt.umlrt.architecture", config.getIdentifier()));
					EMFHelper.unload(rset);
				}
			}
		};
	}

	/**
	 * Queries the element-types resource URIs with which the test is annotated.
	 * 
	 * @param description
	 *            the test description
	 * 
	 * @return its element-types resource URIs (empty if none)
	 */
	static Collection<URI> getTypesResources(Description description) {
		Collection<URI> result = new HashSet<>();
		ElementTypesResource annotation = JUnitUtils.getAnnotation(description, ElementTypesResource.class);

		Optional<Bundle> testBundle = Optional.ofNullable(annotation)
				.map(__ -> JUnitUtils.getTestClass(description))
				.map(FrameworkUtil::getBundle);
		testBundle.ifPresent(bundle -> {
			Stream.of(annotation.value())
					.map(path -> getURI(bundle, path))
					.filter(Objects::nonNull)
					.forEach(result::add);
		});

		return result;
	}

	/**
	 * Gets a resource URI from a bundle.
	 * 
	 * @param bundle
	 *            the bundle
	 * @param path
	 *            the path to the resource, relative to the root of the bundle
	 * 
	 * @return the URI
	 */
	static URI getURI(Bundle bundle, String path) {
		path = path.replaceAll("^/+", "");
		return URI.createPlatformPluginURI(String.format("%s/%s", bundle.getSymbolicName(), path), true);
	}

	/**
	 * Loads the element-types in the indicated resources into the given resource-set.
	 * 
	 * @param uris
	 *            indicate the resources to load
	 * @param rset
	 *            the resource-set in which to load them
	 * 
	 * @return the element-type sets found in those resources
	 */
	static Collection<ElementTypeSetConfiguration> loadTypes(Collection<? extends URI> uris, ResourceSet rset) {
		return uris.stream()
				.map(uri -> rset.getResource(uri, true))
				.filter(Objects::nonNull)
				.flatMap(res -> res.getContents().stream()
						.filter(ElementTypeSetConfiguration.class::isInstance)
						.map(ElementTypeSetConfiguration.class::cast))
				.collect(Collectors.toList());
	}

	/**
	 * Initializes the element-type configuration registry on the UI thread.
	 * 
	 * @return the registry
	 * 
	 * @throws Exception
	 *             on failure to synchronize with the UI thread
	 */
	static ElementTypeSetConfigurationRegistry initializeElementTypesRegistry() throws Exception {
		// force load of the element type registry. Will need to load it in UI thread
		// because of some advice in communication diag:
		// see org.eclipse.gmf.tooling.runtime.providers.DiagramElementTypeImages

		return UIUtil.syncCall(() -> {
			ElementTypeSetConfigurationRegistry result = ElementTypeSetConfigurationRegistry.getInstance();
			Assert.assertNotNull("No element-type configuration registry", result);

			IElementType classType = UMLElementTypes.CLASS;
			assertThat("Class element-type is not dynamic", classType, instanceOf(IConfiguredHintedElementType.class));
			ElementTypeConfiguration classTypeConfig = ((IConfiguredHintedElementType) classType).getConfiguration();
			assertThat("Class type configuration is invalid", classTypeConfig.eResource(), notNullValue());

			return result;
		}).get();
	}
}
