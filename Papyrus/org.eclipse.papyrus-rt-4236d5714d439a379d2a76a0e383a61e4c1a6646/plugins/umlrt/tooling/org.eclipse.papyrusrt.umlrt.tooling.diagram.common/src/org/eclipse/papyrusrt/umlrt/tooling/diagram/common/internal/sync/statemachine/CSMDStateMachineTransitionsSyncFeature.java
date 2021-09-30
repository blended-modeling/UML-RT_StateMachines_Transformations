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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.sync.statemachine;

import java.util.Iterator;
import java.util.Queue;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.sync.SyncBucket;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.sync.UMLRTDiagramEdgesSyncFeature;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.sync.UMLRTSyncRegistry;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Synchronization feature for the edit-parts visualizing the transitions of a state machine
 */
public class CSMDStateMachineTransitionsSyncFeature extends UMLRTDiagramEdgesSyncFeature<StateMachine, Transition> {

	public CSMDStateMachineTransitionsSyncFeature(SyncBucket<StateMachine, EditPart, Notification> bucket) {
		super(bucket);
	}

	@Override
	protected Class<? extends UMLRTSyncRegistry<Transition>> getNestedSyncRegistryType() {
		return CSMDTransitionSyncRegistry.class;
	}

	@Override
	protected SyncBucket<Transition, EditPart, Notification> createNestedSyncBucket(Transition model, EditPart editPart) {
		return new CSMDTransitionSyncBucket(model, editPart);
	}

	@Override
	protected EditPart getTargetEditPart(EditPart parentEditPart, DropObjectsRequest dropObjectsRequest) {
		// Get the parent edit-part of the source vertex's edit-part
		Transition transition = (Transition) dropObjectsRequest.getObjects().get(0);
		Vertex source = transition.getSource();
		EditPart sourceEditPart = findEditPart(parentEditPart, source);
		return sourceEditPart.getParent();
	}

	@Override
	protected Iterable<? extends Transition> getModelContents(final StateMachine model) {
		return new Iterable<Transition>() {
			@Override
			public Iterator<Transition> iterator() {
				return new AbstractIterator<Transition>() {
					// Breadth-first walk of the regions in the state machine and its composite states
					Queue<Region> regions = Lists.newLinkedList(model.getRegions());
					Iterator<Transition> current;

					@Override
					protected Transition computeNext() {
						Transition result = null;

						while (result == null) {
							if ((current == null) || !current.hasNext()) {
								current = null; // In case it was just exhausted

								Region nextRegion = regions.poll();
								if (nextRegion != null) {
									// Add regions of composite states
									for (State next : Iterables.filter(nextRegion.getSubvertices(), State.class)) {
										if (next.isComposite()) {
											regions.addAll(next.getRegions());
										}
									}

									current = nextRegion.getTransitions().iterator();
								}
							}

							if (current == null) {
								break;
							} else if (current.hasNext()) {
								result = current.next();
							}
						}

						return (result == null) ? endOfData() : result;
					}
				};
			}
		};
	}

	EditPart findEditPart(EditPart diagram, EObject object) {
		EditPart result = null;

		for (TreeIterator<EditPart> iter = DiagramEditPartsUtil.getAllContents(diagram, false); (result == null) && iter.hasNext();) {
			EditPart next = iter.next();
			Object view = next.getModel();
			if (!(view instanceof View)) {
				iter.prune();
			} else {
				EObject model = ((View) view).getElement();
				if ((model == null) || !EcoreUtil.isAncestor(model, object)) {
					iter.prune();
				} else if (model == object) {
					result = next;
				}
			}
		}

		return result;
	}
}
