/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Services GmbH
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Philip Langer (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.internal.postprocessor.factories.IChangeFactory;
import org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram.UMLRTDiagramChangeFactory;

@SuppressWarnings("restriction")
public class UMLRTExtensionFactoryRegistry {

	public static Map<Class<? extends Diff>, IChangeFactory> createExtensionFactories() {
		final Map<Class<? extends Diff>, IChangeFactory> dataset = new LinkedHashMap<>();
		final List<IChangeFactory> factories = new ArrayList<>();

		{
			/* UML changes */
			factories.add(new UMLRTProtocolChangeFactory());
			factories.add(new UMLRTProtocolMessageChangeFactory());
			factories.add(new UMLRTProtocolMessageParameterChangeFactory());
			/* Notation changes */
			factories.add(new UMLRTDiagramChangeFactory());
		}

		for (IChangeFactory iDiffExtensionFactory : factories) {
			dataset.put(iDiffExtensionFactory.getExtensionKind(), iDiffExtensionFactory);
		}
		return Collections.unmodifiableMap(dataset);
	}

}
