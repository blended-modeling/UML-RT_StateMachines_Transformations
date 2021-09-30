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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.compare.Diff;

/** Service for handling and providing of {@link DiagramChangesHandler diagram change handlers}. */
public final class DiagramChangesHandlerService {

	/** The service instance. */
	private static final DiagramChangesHandlerService INSTANCE = new DiagramChangesHandlerService();

	/** The diagram change handlers registered with this service. */
	private final Set<DiagramChangesHandler> diagramChangesHandlers = new HashSet<>();

	/** This singleton class has a hidden constructor. */
	private DiagramChangesHandlerService() {
		// hidden constructor
	}

	public static DiagramChangesHandlerService getService() {
		return INSTANCE;
	}

	/**
	 * Add the given handler to the collection of handlers managed by this service.
	 * 
	 * @param handler
	 *            the handler to add
	 * @return <code>true</code> if the service did not already contain the specified element
	 */
	public boolean registerDiagramChangesHandler(DiagramChangesHandler handler) {
		return diagramChangesHandlers.add(handler);
	}

	/**
	 * Removes the given handler to the collection of handlers managed by this service.
	 * 
	 * @param handler
	 *            the handler to remove
	 * @return <code>true</code> if the service contained the specified handler and it was removed successfully
	 */
	public boolean unregisterNotationChangesHandler(DiagramChangesHandler handler) {
		return diagramChangesHandlers.remove(handler);
	}

	/**
	 * Returns the {@link DiagramChangesHandler} with the highest priority that handles the given diff, if any.
	 * 
	 * @param diff
	 *            the difference to check
	 * @return the handler
	 */
	public DiagramChangesHandler getHandlerForDiff(Diff diff) {
		DiagramChangesHandler bestMatchingHandler = null;
		double highestPriority = Double.MIN_VALUE;
		for (DiagramChangesHandler handler : diagramChangesHandlers) {
			double prio = handler.isHandlerFor(diff);
			if (prio > highestPriority) {
				bestMatchingHandler = handler;
				highestPriority = prio;
			}
		}
		return bestMatchingHandler;
	}
}
