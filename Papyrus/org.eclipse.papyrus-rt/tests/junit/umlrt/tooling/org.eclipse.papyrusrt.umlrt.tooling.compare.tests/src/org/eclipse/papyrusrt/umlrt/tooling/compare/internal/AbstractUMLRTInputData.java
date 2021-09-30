/**
 * Copyright (c) 2013, 2017 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *     Philip Langer - UML-RT-specific extensions and support for loading entire model (uml and notation)
 */
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTResourcesUtil;

/**
 * Implementations of this class can be used to load models from the class'
 * class loader.
 * <p>
 * This abstract test is based on
 * org.eclipse.emf.compare.uml2.tests.AbstractUMLInputData.
 * </p>
 */
public abstract class AbstractUMLRTInputData extends AbstractInputData {

	private static final String MODEL_NAME = "model";
	private static final String UML_EXT = ".uml";
	private static final String NOTATION_EXT = ".notation";

	/** Store the set of the resource sets of the input data. */
	private Set<ResourceSet> sets = new LinkedHashSet<>();

	public Set<ResourceSet> getSets() {
		return sets;
	}

	/**
	 * Loads the entire Papyrus model (notation and uml) identified by the given <code>filePath</code>.
	 * <p>
	 * The returned resource set contains resources for the notation file and uml file. The resources will
	 * always be named <em>model.notation</em>, <em>model.uml</em> and <em>model.di</em>.
	 * </p>
	 * 
	 * @param filePath
	 *            The path to the Papyrus model without extension (notation, uml, or di). This method assumes
	 *            that a uml file and a notation exists with the specified <code>filePath</code>.
	 * @return The resource set with the resources for the notation, uml, and potentially di file.
	 */
	protected ResourceSet loadPapyrusModel(String filePath) throws IOException {
		final ResourceSet resourceSet = createResourceSet();
		loadResource(filePath + NOTATION_EXT, MODEL_NAME + NOTATION_EXT, resourceSet);
		loadResource(filePath + UML_EXT, MODEL_NAME + UML_EXT, resourceSet);
		return resourceSet;
	}

	private void loadResource(String filePath, String resourceUri, ResourceSet resourceSet) throws IOException {
		final URL fileURL = getClass().getResource(filePath);
		final InputStream str = fileURL.openStream();
		final URI uri = URI.createURI(resourceUri);

		final Resource resource = resourceSet.createResource(uri);
		resource.load(str, Collections.emptyMap());
		str.close();
	}

	@Override
	protected Resource loadFromClassLoader(String string) throws IOException {
		final ResourceSet resourceSet = createResourceSet();

		final URL fileURL = getClass().getResource(string);
		final InputStream str = fileURL.openStream();
		final URI uri = URI.createURI(fileURL.toString());

		final Resource resource = resourceSet.createResource(uri);
		resource.load(str, Collections.emptyMap());
		str.close();

		return resource;
	}

	private ResourceSet createResourceSet() {
		final ResourceSet resourceSet = new ResourceSetImpl();

		getSets().add(resourceSet);

		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			// Standalone
			UMLRTResourcesUtil.init(resourceSet);
		}

		return resourceSet;
	}
}
