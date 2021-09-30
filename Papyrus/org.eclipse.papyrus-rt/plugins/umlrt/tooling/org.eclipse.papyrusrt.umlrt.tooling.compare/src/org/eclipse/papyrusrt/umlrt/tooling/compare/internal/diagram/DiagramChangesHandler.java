/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alexandra Buzila (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram;

import java.util.Set;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange;

/**
 * Provides a collection of diagram shapes and differences affected by a {@link UMLRTDiagramChange}.
 * 
 * @author Alexandra Buzila
 */
public interface DiagramChangesHandler {

	/**
	 * Checks if the given diff can be handled by this diagram changes handler.
	 * 
	 * @param diff
	 *            the {@link Diff} to check
	 * @return a priority of this handler for the given diff. If the handler
	 *         can handle the difference, a positive number should be returned.
	 */
	double isHandlerFor(Diff diff);

	/**
	 * Provides a set of shapes affected by the {@link UMLRTDiagramChange} extension that the given diff refines.
	 * 
	 * @param diff
	 *            the extension refining diff
	 * @return the affected shapes
	 */
	Set<EObject> getShapes(Diff diff);

	/**
	 * Provides a set of shapes that are automatically created alongside the {@link UMLRTDiagramChange} that the given diff refines.
	 * <p>
	 * Such automatically created shapes might be, for example, styles applied to the diagram, that are not directly related
	 * to the {@link UMLRTDiagramChange} extension. The changes regarding those automatically created shapes will be set as
	 * required changes by the {@link UMLRTDiagramChange} and {@link UMLRTCompareUtil#markAsAutoDiagramChange(Diff) marked}
	 * as automatically created diagram change, so that they can later be filtered from the diff viewers specifically handled during
	 * the merge.
	 * </p>
	 * 
	 * @param diff
	 *            the extension refining diff
	 * @return the implied shapes
	 */
	Set<EObject> getAutomaticallyCreatedShapes(Diff diff);

}
