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

package org.eclipse.papyrusrt.umlrt.uml.internal.util;

import static org.eclipse.papyrusrt.umlrt.uml.internal.util.NotificationForwarder.isForwarded;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.UMLRTUMLPlugin;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.util.ReificationListener;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLMetamodelUtil;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTResourcesUtil;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * An adapter that detects changes to an implicit element and automatically
 * reifies it.
 */
public class ReificationAdapter extends EContentAdapter {

	private static final Object TYPE = new Object();

	private final Map<EReference, Boolean> redefinitionReferences = new ConcurrentHashMap<>();

	// Re-entrant auto-reification suppression for all model elements
	// touched during the scope of some action
	private final AtomicInteger doNotReify = new AtomicInteger();
	private final Map<NamedElement, Boolean> awaitingReification = new WeakHashMap<>();
	private final Map<NamedElement, Object> listeners = new HashMap<>();

	private BooleanSupplier undoRedoQuery;

	/**
	 * Initializes me.
	 */
	public ReificationAdapter() {
		super();

		setUndoRedoQuery(null);
	}

	public static final ReificationAdapter getInstance(Notifier notifier) {
		// Easiest case
		ReificationAdapter result = (ReificationAdapter) EcoreUtil.getExistingAdapter(notifier, TYPE);

		if ((result == null) && (notifier instanceof InternalUMLRTElement)) {
			// Work a bit harder
			ExtensionResource extent = ExtensionResource.getExtensionExtent((InternalUMLRTElement) notifier);
			if (extent != null) {
				result = extent.getReificationAdapter();
			}
		}

		return result;
	}

	public static final ReificationAdapter getInstance(ResourceSet resourceSet) {
		ReificationAdapter result = (ReificationAdapter) EcoreUtil.getExistingAdapter(resourceSet, TYPE);
		if (result == null) {
			result = new ReificationAdapter();
			result.addAdapter(resourceSet);
		}
		return result;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == TYPE;
	}

	@Override
	public void basicSetTarget(Notifier newTarget) {
		// Don't track my targets
	}

	@Override
	public void notifyChanged(Notification msg) {
		// Ignore forwards
		if (isForwarded(msg)) {
			return;
		}

		// Handle propagation to contents
		super.notifyChanged(msg);

		if (!msg.isTouch() && !isUndoRedoNotification(msg)) {
			if (msg.getNotifier() instanceof InternalUMLRTElement) {
				InternalUMLRTElement element = (InternalUMLRTElement) msg.getNotifier();
				if (element.rtIsVirtual()) {
					if (!isBaseElementReference(msg.getFeature()) && !isForwarded(msg)
							&& !isRedefinitionReference(msg.getFeature())) {
						// Any real change to my element reifies it, unless
						// we have recorded a do-not-reify action for it.
						// Note that the stereotype-application events injected by
						// Papyrus are not real changes to the element, nor are
						// events forwarded from inherited features. And, as a special
						// case, changes in the reference to the redefined element are
						// excluded also, because if the imply a change to its
						// redefined-ness, then we are handling that anyways
						int eventType = msg.getEventType();
						if ((eventType >= 0) && (eventType < Notification.EVENT_TYPE_COUNT)) {
							if (doNotReify.get() <= 0) {
								element.rtReify();
							}
						}
					}
				}
			} else if (msg.getFeature() == ExtUMLExtPackage.Literals.ELEMENT__EXTENDED_ELEMENT) {
				switch (msg.getEventType()) {
				case Notification.SET:
				case Notification.UNSET:
					if (msg.getOldValue() instanceof InternalUMLRTElement) {
						// Removing an extension
						removeAdapter((EObject) msg.getOldValue());
					}
					if (msg.getNewValue() != null) {
						// Adding an extension
						addAdapter((EObject) msg.getNewValue());
					}
					break;
				}
			}
		}
	}

	@Override
	protected void setTarget(EObject target) {
		super.setTarget(target);

		if (target instanceof ExtElement) {
			// Adding an extension
			ExtElement extension = (ExtElement) target;
			if (extension.getExtendedElement() != null) {
				addAdapter(extension.getExtendedElement());
			}
		}
	}

	@Override
	protected void unsetTarget(EObject target) {
		super.unsetTarget(target);

		if (target instanceof ExtElement) {
			// Removing an extension
			ExtElement extension = (ExtElement) target;
			if (extension.getExtendedElement() != null) {
				removeAdapter(extension.getExtendedElement());
			}
		}
	}

	@Override
	protected void removeAdapter(Notifier notifier, boolean checkContainer, boolean checkResource) {
		// Virtual elements have eContainer and eInternalContainer not in
		// agreement, so we always have to check the container
		super.removeAdapter(notifier,
				checkContainer || (notifier instanceof InternalUMLRTElement),
				checkResource);
	}

	/**
	 * I stick to extension objects.
	 */
	@Override
	protected void removeAdapter(Notifier notifier) {
		if (notifier instanceof ExtElement) {
			return;
		}

		super.removeAdapter(notifier);
	}

	@Override
	protected void handleContainment(Notification notification) {
		// Don't propagate to contents of extended elements in the model
		Object notifier = notification.getNotifier();

		// Check resource first because CDOResources are EObjects
		if (notifier instanceof Resource) {
			super.handleContainment(notification);
		} else if (notifier instanceof InternalEObject) {
			if (((InternalEObject) notifier).eInternalResource() instanceof ExtensionResource) {
				super.handleContainment(notification);

				if (notifier instanceof ExtElement) {
					// An extension element changed. Did we virtualize UML elements?
					EReference reference = (EReference) notification.getFeature();

					if (UMLPackage.Literals.NAMED_ELEMENT.isSuperTypeOf(reference.getEReferenceType())) {
						// Added/removed something
						switch (notification.getEventType()) {
						case Notification.ADD:
							notifyReification((NamedElement) notification.getNewValue(), false);
							break;
						case Notification.ADD_MANY:
							@SuppressWarnings("unchecked")
							Collection<? extends NamedElement> added = (Collection<? extends NamedElement>) notification.getNewValue();
							added.stream().forEach(e -> notifyReification(e, false));
							break;
						case Notification.REMOVE:
							awaitingReification.put((NamedElement) notification.getOldValue(), true);
							break;
						case Notification.REMOVE_MANY:
							@SuppressWarnings("unchecked")
							Collection<? extends NamedElement> removed = (Collection<? extends NamedElement>) notification.getOldValue();
							removed.stream().forEach(e -> awaitingReification.put(e, true));
							break;
						case Notification.SET:
						case Notification.UNSET:
							// Reference lists don't allow nulls, but scalar
							// containments references do
							if (notification.getOldValue() != null) {
								awaitingReification.put((NamedElement) notification.getOldValue(), true);
							}
							if (notification.getNewValue() != null) {
								notifyReification((NamedElement) notification.getNewValue(), false);
							}
							break;
						}
					}
				}
			} else if (notifier instanceof InternalUMLRTElement) {
				// A real model element changed. Did we reify UML elements?
				EReference reference = (EReference) notification.getFeature();
				if (UMLPackage.Literals.NAMED_ELEMENT.isSuperTypeOf(reference.getEReferenceType())) {
					switch (notification.getEventType()) {
					case Notification.ADD:
						notifyReification((NamedElement) notification.getNewValue(), true);
						break;
					case Notification.ADD_MANY:
						@SuppressWarnings("unchecked")
						Collection<? extends NamedElement> added = (Collection<? extends NamedElement>) notification.getNewValue();
						added.stream().forEach(e -> notifyReification(e, true));
						break;
					case Notification.SET:
					case Notification.UNSET:
						// Reference lists don't allow nulls, but scalar
						// containments references do
						if (notification.getNewValue() != null) {
							notifyReification((NamedElement) notification.getNewValue(), true);
						}
						break;
					}
				}
			}
		} else {
			super.handleContainment(notification);
		}
	}

	@Override
	public void addAdapter(Notifier notifier) {
		// We only track the contents of the extension extent and selected elements in the model
		if (!(notifier instanceof Resource) || (notifier instanceof ExtensionResource)) {
			ListIterator<Adapter> adapters = notifier.eAdapters().listIterator();
			boolean more = adapters.hasNext();
			while (more) {
				Adapter next = adapters.next();
				if (next == this) {
					break;
				} else if (next instanceof ReificationAdapter) {
					// Replace it (it is left over from another extension extent)
					adapters.set(this);
					break;
				}

				more = adapters.hasNext();
				if (!more) {
					// Add me now
					adapters.add(this);
					break;
				}
			}
		}
	}

	private void notifyReification(NamedElement element, boolean reified) {
		if (reified) {
			removeAdapter(element);
			if (awaitingReification.remove(element) == null) {
				// An element is not reified that never was virtual
				return;
			}
		} else {
			// Listen for changes that trigger reification
			addAdapter(element);
		}

		List<ReificationListener> listeners = getListeners(element);
		if ((listeners != null) && !listeners.isEmpty()) {
			UMLRTNamedElement facade = UMLRTFactory.create(element);
			if (facade != null) {
				// This is safe against comodification
				listeners.forEach(l -> {
					try {
						l.reificationStateChanged(facade, reified);
					} catch (Exception e) {
						UMLRTUMLPlugin.INSTANCE.log(e);
					}
				});
			}
		}
	}

	public void run(Runnable action) {
		disableAutoReification();

		try {
			action.run();
		} finally {
			enableAutoReification();
		}
	}

	public <V> V run(Callable<V> action) {
		V result;

		disableAutoReification();

		try {
			result = action.call();
		} catch (Exception e) {
			throw new WrappedException(e);
		} finally {
			enableAutoReification();
		}

		return result;
	}

	public void disableAutoReification() {
		doNotReify.incrementAndGet();
	}

	public void enableAutoReification() {
		doNotReify.decrementAndGet();
	}

	protected boolean isBaseElementReference(Object feature) {
		boolean result = false;

		if (feature instanceof EReference) {
			EReference reference = (EReference) feature;
			result = (reference.getName() != null)
					&& reference.getName().startsWith("base_")
					&& UMLPackage.Literals.ELEMENT.isSuperTypeOf(reference.getEReferenceType())
					&& !UMLPackage.Literals.ELEMENT.isSuperTypeOf(reference.getEContainingClass());
		}

		return result;
	}

	protected boolean isRedefinitionReference(Object feature) {
		boolean result = false;

		if (feature instanceof EReference) {
			EReference reference = (EReference) feature;
			result = redefinitionReferences.computeIfAbsent(reference,
					ref -> UMLMetamodelUtil.isSubsetOf(ref, UMLPackage.Literals.REDEFINABLE_ELEMENT__REDEFINED_ELEMENT));
		}

		return result;
	}

	public void addListener(NamedElement element, ReificationListener listener) {
		Object existing = listeners.get(element);
		if (existing == null) {
			listeners.put(element, listener);
		} else if (existing != listener) {
			if (existing instanceof List<?>) {
				@SuppressWarnings("unchecked")
				CopyOnWriteArrayList<ReificationListener> list = (CopyOnWriteArrayList<ReificationListener>) existing;
				list.addIfAbsent(listener);
			} else {
				CopyOnWriteArrayList<ReificationListener> list = new CopyOnWriteArrayList<>(
						new ReificationListener[] { (ReificationListener) existing, listener });
				listeners.put(element, list);
			}
		}
	}

	public void removeListener(NamedElement element, ReificationListener listener) {
		Object existing = listeners.get(element);
		if (existing != null) {
			if (existing == listener) {
				listeners.remove(element);
			} else if (existing instanceof List<?>) {
				((List<?>) existing).remove(listener);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<ReificationListener> getListeners(NamedElement element) {
		Object result = listeners.get(element);

		return (result == null)
				? null
				: (result instanceof List<?>)
						? (List<ReificationListener>) result
						: Collections.singletonList((ReificationListener) result);
	}

	/**
	 * <p>
	 * Some adapters in the UML-RT Façade API react to changes in the model to maintain
	 * a correct (for UML-RT) semantic structure in the UML model, but those adaptations
	 * are best avoided when changes result from undo/redo of recorded commands. This
	 * API provides the façade API with the means to recognize changes that are triggered
	 * by undo or redo (such as in a transactional editing domain).
	 * </p>
	 * <p>
	 * <b>Note</b> that this will replace any {@link #setUndoRedoQuery query} previously
	 * installed.
	 * </p>
	 * 
	 * @param undoRedoRecognizer
	 *            a predicate that matches notifications triggered by undo/redo
	 * 
	 * @see UMLRTResourcesUtil#setUndoRedoRecognizer(ResourceSet, Predicate)
	 * @deprecated Use the {@link #setUndoRedoQuery(BooleanSupplier)} API, instead
	 *             to handle scenarios where changes are occurring (for example, by change
	 *             application) that have not yet produced notifications and it is necessary to
	 *             know <em>a priori</em> whether they are in the context of undo/redo
	 */
	@Deprecated
	public final void setUndoRedoRecognizer(Predicate<? super Notification> undoRedoRecognizer) {
		setUndoRedoQuery(undoRedoRecognizer == null ? null : new LegacyUndoRedoQuery(undoRedoRecognizer));
	}

	/**
	 * Some adapters in the UML-RT Façade API react to changes in the model to maintain
	 * a correct (for UML-RT) semantic structure in the UML model, but those adaptations
	 * are best avoided when changes result from undo/redo of recorded commands. This
	 * API provides the façade API with the means to query whether undo/redo is currently
	 * in progress, which should already account for all consequences of the changes
	 * that are being observed.
	 * 
	 * @param resourceSet
	 *            a resource set to configure for UML-RT editing
	 * @param isUndoRedo
	 *            indicates whether undo or redo is currently in progress in the context of
	 *            the resource set. In the case of a Transactional Editing Domain, this
	 *            must also cover the case of transaction roll-back
	 */
	public final void setUndoRedoQuery(BooleanSupplier isUndoRedo) {
		this.undoRedoQuery = (isUndoRedo == null) ? () -> false : isUndoRedo;
	}

	/**
	 * Queries whether a {@code notification} was triggered by undo or redo
	 * of an editing command.
	 * 
	 * @param notification
	 *            a notification
	 * @return whether it signals an undo or redo of an edit
	 */
	public boolean isUndoRedoNotification(Notification notification) {
		return (undoRedoQuery instanceof LegacyUndoRedoQuery)
				? ((LegacyUndoRedoQuery) undoRedoQuery).test(notification)
				: undoRedoQuery.getAsBoolean();
	}

	/**
	 * Queries whether an undo or redo operation is currently in progress in the
	 * context of my resource set.
	 * 
	 * @return whether undo or redo is currently in progress
	 */
	public boolean isUndoRedoInProgress() {
		return undoRedoQuery.getAsBoolean();
	}

	/**
	 * Queries whether an undo or redo operation is currently in progress in the
	 * context of the given object.
	 * 
	 * @return whether undo or redo is currently in progress
	 */
	public static boolean isUndoRedoInProgress(Notifier object) {
		ReificationAdapter adapter = getInstance(object);
		return (adapter != null) && adapter.isUndoRedoInProgress();
	}

	//
	// Nested types
	//

	private static final class LegacyUndoRedoQuery implements BooleanSupplier, Predicate<Notification> {
		private final Notification fakeNotification = new NotificationImpl(Notification.UNSET, null, null);
		private final Predicate<? super Notification> undoRedoRecognizer;

		LegacyUndoRedoQuery(Predicate<? super Notification> undoRedoRecognizer) {
			super();

			this.undoRedoRecognizer = undoRedoRecognizer;
		}

		@Override
		public boolean getAsBoolean() {
			return test(fakeNotification);
		}

		@Override
		public boolean test(Notification notification) {
			return undoRedoRecognizer.test(notification);
		}
	}
}
