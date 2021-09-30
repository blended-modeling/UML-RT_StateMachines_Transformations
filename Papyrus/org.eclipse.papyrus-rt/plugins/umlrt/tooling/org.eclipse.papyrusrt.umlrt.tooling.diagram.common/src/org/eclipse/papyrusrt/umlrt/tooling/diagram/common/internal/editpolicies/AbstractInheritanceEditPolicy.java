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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies;

import static org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil.getDiagramEditPart;
import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.PapyrusRTCanonicalEditPolicy.isCanonicalEditPolicyRequest;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EContentsEList.FeatureIterator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationPreCommitListener;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.gef.ui.internal.editpolicies.GraphicalEditPolicyEx;
import org.eclipse.gmf.runtime.notation.Bendpoints;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramHelper;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;

/**
 * An abstract edit policy that manages the graphical characteristics
 * characteristics of inherited elements in a Capsule Composite Structure Diagram.
 */
public abstract class AbstractInheritanceEditPolicy<V extends View> extends GraphicalEditPolicyEx {
	public static final String INHERITANCE_ROLE = "umlrtInheritance"; //$NON-NLS-1$

	private final Class<V> viewType;
	private final NotificationPreCommitListener redefinedViewListener;
	private final CopyOnWriteArrayList<EObject> listeningTo = new CopyOnWriteArrayList<>();

	private V redefinedView;

	private volatile Future<?> pendingRefresh;

	public AbstractInheritanceEditPolicy(Class<V> viewType) {
		super();

		this.viewType = viewType;
		this.redefinedViewListener = this::redefinedViewChanged;
	}

	IInheritableEditPart inheritableHost() {
		return (IInheritableEditPart) getHost();
	}

	boolean isInherited() {
		return getRedefinedElementView() != null;
	}

	@Override
	public void deactivate() {
		setRedefinedElementView(null);

		super.deactivate();
	}

	V getRedefinedElementView() {
		return redefinedView;
	}

	private void setRedefinedElementView(V newRedefinedView) {
		if (redefinedView != newRedefinedView) {
			if (redefinedView != null) {
				getEventBroker().ifPresent(this::removeUpdateListeners);
			}
			redefinedView = newRedefinedView;
			if (redefinedView != null) {
				getEventBroker().ifPresent(this::addUpdateListeners);
			}
		}
	}

	protected final void addUpdateListeners(DiagramEventBroker broker) {
		V redefined = getRedefinedElementView();
		listen(broker, redefined);

		// Only one level of contents depth for now
		for (FeatureIterator<EObject> iter = (FeatureIterator<EObject>) redefined.eContents().iterator(); iter.hasNext();) {
			EObject next = iter.next();
			if (shouldListen(redefined, (EReference) iter.feature())) {
				listen(broker, next);
			}
		}
	}

	/**
	 * Queries whether refresh of the visuals should be triggered by changes to objects
	 * contained in the host edit-part's view via the given {@code containment} reference,
	 * usually {@link LayoutConstraint}s, {@link Bendpoints}, and the like.
	 * 
	 * @param view
	 *            the redefined view to follow, from the superclass diagram
	 * @param containment
	 *            a containment reference
	 * @return whether to track changes in this {@code containment} reference of the {@code view}
	 */
	protected abstract boolean shouldListen(V view, EReference containment);

	private boolean listen(DiagramEventBroker broker, EObject object) {
		boolean result = listeningTo.addIfAbsent(object);
		if (result) {
			broker.addNotificationListener(object, redefinedViewListener);
		}
		return result;
	}

	private boolean unlisten(DiagramEventBroker broker, EObject object) {
		boolean result = listeningTo.remove(object);
		if (result) {
			broker.removeNotificationListener(object, redefinedViewListener);
		}
		return result;
	}

	protected final void removeUpdateListeners(DiagramEventBroker broker) {
		listeningTo.forEach(e -> broker.removeNotificationListener(e, redefinedViewListener));
		listeningTo.clear();
	}

	@SuppressWarnings("unchecked")
	protected final Command redefinedViewChanged(Notification msg) {
		if (!checkInheritance()) {
			return null;
		}

		CompoundCommand result = new CompoundCommand();

		// We only listen to EObjects
		EObject notifier = (EObject) msg.getNotifier();
		V redefined = getRedefinedElementView();

		if ((notifier == redefined) || (notifier.eContainer() == redefined)) {
			Object feature = msg.getFeature();
			if (feature instanceof EReference) {
				EReference reference = (EReference) feature;
				V view = getRedefinedElementView();
				if (reference.isContainment() && shouldListen(view, reference)) {
					// We can't be responding to the broker if it isn't there
					DiagramEventBroker broker = getEventBroker().get();

					Consumer<EObject> processNew = added -> {
						if (listen(broker, added)) {
							Command refresh = update(view, added, reference,
									msg.getPosition(), false);
							if (refresh != null) {
								result.append(refresh);
							}
						}
					};

					Consumer<EObject> processOld = removed -> {
						if (unlisten(broker, removed)) {
							Command refresh = update(view, removed, reference,
									msg.getPosition(), true);
							if (refresh != null) {
								result.append(refresh);
							}
						}
					};

					// Discover changed targets for listening
					switch (msg.getEventType()) {
					case Notification.ADD:
						processNew.accept((EObject) msg.getNewValue());
						break;
					case Notification.ADD_MANY:
						((Collection<? extends EObject>) msg.getNewValue()).forEach(processNew);
						break;
					case Notification.REMOVE:
						processOld.accept((EObject) msg.getOldValue());
						break;
					case Notification.REMOVE_MANY:
						((Collection<? extends EObject>) msg.getOldValue()).forEach(processOld);
						break;
					case Notification.SET:
						processOld.accept((EObject) msg.getOldValue());
						processNew.accept((EObject) msg.getNewValue());
						break;
					// No RESOLVE because we don't have cross-resource containment in notation
					}
				}
			}
		}

		// Refresh this changed object, including other attributes of the view, itself
		Command refresh = update(notifier);
		if (refresh != null) {
			result.append(refresh);
		}

		if (result.canExecute()) {
			// we will be changing something, so a refresh will be
			// needed to repaint the diagram
			postRefresh();
		}

		return result.isEmpty() ? null : result.unwrap();
	}

	protected Command update(V redefinedView, EObject contained, EReference containment, int position, boolean isRemoved) {
		Command result = null;

		V view = getView();
		if (isRemoved) {
			// In the general case, we want to remove the corresponding thing in our view
			if (!containment.isMany()) {
				// Easy case
				EObject myContained = (EObject) view.eGet(containment);
				if (myContained != null) {
					result = RemoveCommand.create(getEditingDomain(), view,
							containment, myContained);
				}
			} else {
				@SuppressWarnings("unchecked")
				EList<? extends EObject> myList = (EList<? extends EObject>) view.eGet(containment);
				if ((position >= 0) && (position < myList.size())) {
					result = RemoveCommand.create(getEditingDomain(), view,
							containment, myList.get(position));
				}
			}
		} else {
			// In the general case, we want to add the corresponding thing in our view
			if (!containment.isMany()) {
				// Easy case
				EObject myContained = (EObject) view.eGet(containment);
				EObject newContained = EcoreUtil.copy(contained);
				if (myContained != null) {
					result = SetCommand.create(getEditingDomain(), view,
							containment, newContained);
				} else {
					result = AddCommand.create(getEditingDomain(), view,
							containment, newContained);
				}
			} else {
				@SuppressWarnings("unchecked")
				EList<? extends EObject> myList = (EList<? extends EObject>) view.eGet(containment);
				if ((position >= 0) && (position <= myList.size())) {
					EObject newContained = EcoreUtil.copy(contained);
					result = AddCommand.create(getEditingDomain(), view,
							containment, newContained, position);
				}
			}
		}

		return result;

	}

	protected abstract Command update(EObject inherited);

	private boolean checkInheritance() {
		setRedefinedElementView(computeRedefinedElementView());
		return isInherited();
	}

	private V computeRedefinedElementView() {
		return !inheritableHost().isInherited()
				? null
				: viewType.cast(inheritableHost().getRedefinedView());
	}

	private Optional<DiagramEventBroker> getEventBroker() {
		return Optional.ofNullable(((IGraphicalEditPart) getHost()).getEditingDomain())
				.map(DiagramEventBroker::getInstance);
	}

	@Override
	public org.eclipse.gef.commands.Command getCommand(Request request) {
		org.eclipse.gef.commands.Command result = super.getCommand(request);

		if (isInherited() && isVisualEdit(request) && !isCanonicalEditPolicyRequest(request)) {
			org.eclipse.gef.commands.Command reifyView = inheritableHost().getReifyViewCommand();
			if (reifyView != null) {
				if (result != null) {
					result = reifyView.chain(result);
				} else {
					result = reifyView;
				}
			}
		}

		return result;
	}

	protected abstract boolean isVisualEdit(Request request);

	@Override
	public void refresh() {
		checkInheritance();

		if (isInherited()) {
			refreshInheritance();
		}

		super.refresh();
	}

	protected final void refreshInheritance() {
		CompoundCommand refresh = new CompoundCommand();

		listeningTo.forEach(e -> {
			Command update = update(e);
			if (update != null) {
				refresh.append(update);
			}
		});

		if (refresh.canExecute()) {
			CommandUtil.executeUnsafeCommand(refresh.unwrap(), getHost());
		}
	}

	V getView() {
		IGraphicalEditPart host = (IGraphicalEditPart) getHost();
		return viewType.cast(host.getNotationView());
	}

	Optional<UMLRTNamedElement> getUMLRTElement() {
		return inheritableHost().getUMLRTElement();
	}

	protected TransactionalEditingDomain getEditingDomain() {
		return ((IGraphicalEditPart) getHost()).getEditingDomain();
	}

	private void postRefresh() {
		if (pendingRefresh == null) {
			EditPart toRefresh = getHost();
			pendingRefresh = DiagramHelper.submit(toRefresh, new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					pendingRefresh = null;

					if (toRefresh.isActive()) {
						// Work-around for targeted refresh of the edit-part
						// not working for port shapes on the border
						DiagramHelper.scheduleRefresh(getDiagramEditPart(toRefresh));
					}

					return null;
				}
			});
		}
	}
}
