/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.core.sync.statemachine;

import java.util.Map;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.papyrusrt.umlrt.core.sync.UMLSyncRegistry;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;

import com.google.common.collect.MapMaker;

/**
 * Capsule statemachine vertex synchronization registry.
 */
public class VertexSyncRegistry extends UMLSyncRegistry<Vertex> {

	private final Map<Vertex, Vertex> redefinitions = new MapMaker().weakKeys().weakValues().makeMap();

	public VertexSyncRegistry() {
		super();
	}

	@Override
	public Vertex getModelOf(EObject backend) {
		Vertex result = redefinitions.get(backend);
		return (result != null) ? result : (backend instanceof Vertex) ? CapsuleUtils.getSuperVertex((Vertex) backend) : null;
	}

	/**
	 * Externally maps a vertex as redefining another, in the case that it is a {@link State}, which is the only kind of
	 * vertex that is a {@link RedefinableElement}.
	 * 
	 * @param redefiningVertex
	 *            the redefining vertex. Must not be {@code null}
	 * @param redefinedVertex
	 *            the redefined vertex. May be {@code null} to clear the redefinition association
	 */
	void setRedefinedVertex(Vertex redefiningVertex, Vertex redefinedVertex) {
		if (redefinedVertex == null) {
			redefinitions.remove(redefiningVertex);
		} else {
			redefinitions.put(redefiningVertex, redefinedVertex);
		}
	}

	/**
	 * Queries whether a {@code vertex} redefines an{@code other}.
	 * 
	 * @param vertex
	 *            a vertex
	 * @param other
	 *            another vertex
	 * 
	 * @return whether the {@code vertex} redefines the {@code other}
	 */
	boolean redefines(Vertex vertex, Vertex other) {
		Vertex superVertex = (vertex instanceof State) ? CapsuleUtils.getSuperVertex(vertex) : redefinitions.get(vertex);
		return other == superVertex;
	}

	Command createSetRedefinedVertexCommand(Vertex redefiningVertex, Vertex redefinedVertex) {
		return (redefiningVertex instanceof State)
				? SetCommand.create(getEditingDomain(), redefiningVertex, UMLPackage.Literals.STATE__REDEFINED_STATE, redefinedVertex)
				: new RedefinitionCommand(redefiningVertex, redefinedVertex);
	}

	//
	// Nested types
	//

	private final class RedefinitionCommand extends AbstractCommand implements AbstractCommand.NonDirtying {
		private final Vertex redefiningVertex;
		private final Vertex redefinedVertex;

		private Vertex oldRedefinedVertex;

		RedefinitionCommand(final Vertex redefiningVertex, final Vertex redefinedVertex) {
			super();

			this.redefiningVertex = redefiningVertex;
			this.redefinedVertex = redefinedVertex;
		}

		@Override
		protected boolean prepare() {
			oldRedefinedVertex = redefinitions.get(redefiningVertex);
			return true;
		}

		@Override
		public void execute() {
			setRedefinedVertex(redefiningVertex, redefinedVertex);
		}

		@Override
		public void undo() {
			setRedefinedVertex(redefiningVertex, oldRedefinedVertex);
		}

		@Override
		public void redo() {
			setRedefinedVertex(redefiningVertex, redefinedVertex);
		}
	}

}
