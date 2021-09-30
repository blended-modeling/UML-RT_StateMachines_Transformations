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

import static org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource.demandExtensionExtent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Capsule;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Protocol;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.ProtocolContainer;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTConnector;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageSet;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTRedefinedElement;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.util.UMLRealTimeSwitch;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTGuard;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTRegion;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTState;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTStateMachine;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.util.UMLRTStateMachinesSwitch;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.FacadeAdapter;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTRedefinitionContext;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTRegion;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.UMLRTUMLPlugin;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Partial implementation of an adapter that maintains inherited features
 * for an UML-RT classifier.
 */
public abstract class InheritanceAdapter extends AdapterImpl {
	InheritanceAdapter() {
		super();
	}

	public static InheritanceAdapter getInstance(Notifier notifier) {
		InheritanceAdapter result = (InheritanceAdapter) EcoreUtil.getExistingAdapter(
				notifier, InheritanceAdapter.class);

		if (result == null) {
			if (notifier instanceof Resource) {
				// Resources should have these
				result = new InheritanceAdapter.ForResource((Resource) notifier);
				result.adapt(notifier);
			} else if (notifier instanceof InternalUMLRTClassifier) {
				// As should capsules, protocols, and message-sets
				Resource resource = ((InternalUMLRTClassifier) notifier).eResource();
				if (resource != null) {
					result = getInstance(resource);
				}
				if (result != null) {
					result.getClassifierAdapter().adapt(notifier);
				}
			} else if ((notifier instanceof InternalUMLRTStateMachine)
					|| (notifier instanceof InternalUMLRTRegion)
					|| (notifier instanceof InternalUMLRTState)
					|| (notifier instanceof InternalUMLRTTransition)) {
				// And state machines and regions
				Resource resource = ((EObject) notifier).eResource();
				if (resource != null) {
					result = getInstance(resource);
				}
				if (result != null) {
					result.getStateMachineAdapter().adapt(notifier);
				}
			}
		}

		return result;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == InheritanceAdapter.class;
	}

	public boolean adapt(Notifier notifier) {
		boolean result = false;
		EList<Adapter> adapters = notifier.eAdapters();

		if (!adapters.contains(this)) {
			result = notifier.eAdapters().add(this);
		}

		return result;
	}

	public boolean unadapt(Notifier notifier) {
		return notifier.eAdapters().remove(this);
	}

	@Override
	public final void notifyChanged(Notification msg) {
		if (msg.isTouch()) {
			return;
		}

		// Don't prune inheritance in undo/redo because undo/redo will have
		// recorded those inheritance changes, too
		if (!isUndoRedoNotification(msg)) {
			prune(msg);
		}

		// But we do need to react to some undo/redo notifications, especially
		// concerning deletion and restoration of stereotype applications,
		// because keeping the façade in sync depends on those
		handleNotification(msg);
	}

	InheritanceAdapter getResourceAdapter() {
		return null;
	}

	InheritanceAdapter getClassifierAdapter() {
		return getResourceAdapter().getClassifierAdapter();
	}

	InheritanceAdapter getGeneralizationAdapter() {
		return getResourceAdapter().getGeneralizationAdapter();
	}

	InheritanceAdapter getStateMachineAdapter() {
		return getResourceAdapter().getStateMachineAdapter();
	}

	InheritanceAdapter getStructureStereotypeAdapter() {
		return getResourceAdapter().getStructureStereotypeAdapter();
	}

	InheritanceAdapter getStateMachineStereotypeAdapter() {
		return getResourceAdapter().getStructureStereotypeAdapter();
	}

	protected abstract void handleNotification(Notification msg);

	protected void touch(Notifier notifier) {
		// Pass
	}

	protected void prune(Notification msg) {
		if ((msg.getFeature() instanceof EReference)
				&& ((EReference) msg.getFeature()).isContainment()
				&& (msg.getNotifier() instanceof InternalUMLRTRedefinitionContext<?>)) {

			// If an inheritable element was deleted, prune its virtual redefinitions
			switch (msg.getEventType()) {
			case Notification.SET:
			case Notification.UNSET:
				if (msg.getOldValue() == null) {
					// Not a removal
					break;
				}
				// Fall through
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				Object oldValue = msg.getOldValue();
				@SuppressWarnings("unchecked")
				Collection<EObject> removed = (oldValue instanceof Collection<?>)
						? new ArrayList<>((Collection<? extends EObject>) oldValue)
						: (oldValue != null)
								? new ArrayList<>(Collections.singletonList((EObject) oldValue))
								: Collections.emptyList();

				// Any objects that are already re-attached (because they were just moved,
				// for example to reify or virtualize them) are not to be pruned
				removed.removeIf(e -> e.eResource() != null);
				if (!removed.isEmpty()) {
					((InternalUMLRTRedefinitionContext<?>) msg.getNotifier()).rtPruneInheritance(removed);
				}
				break;
			}
		}
	}

	protected final <T extends InternalUMLRTRedefinitionContext<T>> void processInheritance(T root) {
		if (throttleInheritance(root)) {
			return;
		}

		processInheritance(root, null);
	}

	final <T extends InternalUMLRTRedefinitionContext<T>> void processInheritance(T root, Consumer<? super T> postAction) {
		ExtensionResource.demandExtensionExtent(root).run(root, () -> {
			// Beware of generalization cycles
			Set<T> seen = new HashSet<>();
			Queue<T> breadthFirst = new ArrayDeque<>();

			// We start at the root of this hierarchy
			seen.add(root);
			breadthFirst.offer(root);

			for (T next = breadthFirst.poll(); next != null; next = breadthFirst.poll()) {
				T ancestor = next;
				next.rtDescendants().forEach(descendant -> {
					if (seen.add(descendant)) {
						breadthFirst.offer(descendant);
						descendant.rtInherit(ancestor);
					}
				});

				if (postAction != null) {
					postAction.accept(ancestor);
				}
			}
		});
	}

	/**
	 * Queries whether inheritance from a given {@code root} has already been handled
	 * in the current context and so needn't be repeated.
	 * 
	 * @param root
	 *            the point at which to start inheriting elements
	 * @return {@code true} if this {@code root} element may be skipped; {@code false}
	 *         if it needs to be processed
	 */
	protected final boolean throttleInheritance(InternalUMLRTRedefinitionContext<?> root) {
		return ((ForResource) getResourceAdapter()).doThrottleInheritance(root);
	}

	/**
	 * Tracks requests by an {@code action} to process inheritance of UML-RT elements
	 * and defers them to the end of that {@code action} to process them in an optimal
	 * fashion.
	 * 
	 * @param action
	 *            an action to run, e.g., discovery of the stuff newly loaded into a resource
	 */
	protected final void whileTrackingInheritance(Runnable action) {
		((ForResource) getResourceAdapter()).doWhileTrackingInheritance(action);
	}

	protected <T extends InternalUMLRTRedefinitionContext<T>> void handleAncestorChanged(
			T descendant, Object oldObject, Object newObject, java.lang.Class<T> type) {

		T oldAncestor = null;
		T newAncestor = null;
		if (type.isInstance(oldObject)) {
			oldAncestor = type.cast(oldObject);
			descendant.rtDisinherit(oldAncestor);
			unadapt(oldAncestor);
		}
		if (type.isInstance(newObject)) {
			newAncestor = type.cast(newObject);
			adapt(newAncestor);
			descendant.rtInherit(newAncestor);
		}

	}

	boolean isResourceLoading() {
		return getResourceAdapter().isResourceLoading();
	}

	protected boolean isUndoRedoNotification(Notification msg) {
		return getResourceAdapter().isUndoRedoNotification(msg);
	}

	boolean isStructureStereotype(EObject object) {
		return object.eClass().getEPackage() == UMLRealTimePackage.eINSTANCE;
	}

	boolean isStateMachineStereotype(EObject object) {
		return object.eClass().getEPackage() == UMLRTStateMachinesPackage.eINSTANCE;
	}

	//
	// Nested types
	//

	private static class ForClassifier extends InheritanceAdapter {
		private final ForResource forResource;

		ForClassifier(ForResource forResource) {
			super();

			this.forResource = forResource;
		}

		@Override
		InheritanceAdapter getResourceAdapter() {
			return forResource;
		}

		@Override
		InheritanceAdapter getClassifierAdapter() {
			return this;
		}

		@Override
		public void setTarget(Notifier newTarget) {
			if (newTarget instanceof InternalUMLRTClassifier) {
				// Discover existing structure
				InternalUMLRTClassifier specific = (InternalUMLRTClassifier) newTarget;
				List<Generalization> generalizations = specific.getGeneralizations();
				if (!generalizations.isEmpty()) {
					getGeneralizationAdapter().adapt(generalizations.get(0));
					getGeneralizationAdapter().touch(generalizations.get(0));
				}
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (oldTarget instanceof InternalUMLRTClassifier) {
				// Forget existing structure
				InternalUMLRTClassifier specific = (InternalUMLRTClassifier) oldTarget;
				specific.getGeneralizations().forEach(getGeneralizationAdapter()::touch);
				specific.getGeneralizations().forEach(getGeneralizationAdapter()::unadapt);
			}
		}

		@Override
		protected void handleNotification(Notification msg) {
			if ((msg.getFeature() instanceof EReference)) {
				handleReference(msg, (EReference) msg.getFeature());
			}
		}

		protected void handleReference(Notification msg, EReference reference) {
			boolean notUndoRedo = !isUndoRedoNotification(msg);

			if (reference == UMLPackage.Literals.CLASSIFIER__GENERALIZATION) {
				// We only listen to RT classifiers
				InternalUMLRTClassifier specific = (InternalUMLRTClassifier) msg.getNotifier();

				// Handle addition/removal of generalizations
				switch (msg.getEventType()) {
				case Notification.ADD:
					// We only support single inheritance
					if (specific.getGeneralizations().size() == 1) {
						Generalization gen = (Generalization) msg.getNewValue();
						getGeneralizationAdapter().adapt(gen);
						if (notUndoRedo) {
							getGeneralizationAdapter().touch(gen);
						}
					}
					break;
				case Notification.REMOVE:
					// We only support single inheritance
					if (specific.getGeneralizations().isEmpty()) {
						Generalization gen = (Generalization) msg.getOldValue();
						if (notUndoRedo) {
							getGeneralizationAdapter().touch(gen);
						}
						getGeneralizationAdapter().unadapt(gen);
					}
					break;
				case Notification.SET:
					// We only support single inheritance
					if (specific.getGeneralizations().size() == 1) {
						Generalization oldGen = (Generalization) msg.getOldValue();
						Generalization newGen = (Generalization) msg.getNewValue();
						if (notUndoRedo) {
							getGeneralizationAdapter().touch(oldGen);
						}
						getGeneralizationAdapter().unadapt(oldGen);
						getGeneralizationAdapter().adapt(newGen);
						if (notUndoRedo) {
							getGeneralizationAdapter().touch(newGen);
						}
					}
					break;
				}
			} else if (reference.isContainment()
					&& UMLPackage.Literals.OPERATION.isSuperTypeOf(reference.getEReferenceType())
					&& (msg.getNotifier() instanceof Interface)
					&& notUndoRedo) {

				// Protocol Messages don't have a stereotype application to trigger this
				switch (msg.getEventType()) {
				case Notification.ADD:
				case Notification.ADD_MANY:
				case Notification.SET:
					// Inherit the new features
					touch((Notifier) msg.getNotifier());
					break;
				// TODO: What about removals?
				}
			} else if (reference == UMLPackage.Literals.BEHAVIORED_CLASSIFIER__CLASSIFIER_BEHAVIOR) {
				if ((msg.getNotifier() instanceof Class) && notUndoRedo) {
					Class class_ = (Class) msg.getNotifier();
					if (UMLUtil.getStereotypeApplication(class_, Capsule.class) != null) {

						// It's a capsule. Handle state machine redefinition
						switch (msg.getEventType()) {
						case Notification.SET:
						case Notification.UNSET:
						case Notification.RESOLVE:
							// Ensure state machine redefinition for its subclasses
							touch(class_);
							break;
						}
					}
				}
			}
		}

		@Override
		protected void touch(Notifier notifier) {
			if (!isResourceLoading() && (notifier instanceof InternalUMLRTClassifier)) {
				InternalUMLRTClassifier root = (InternalUMLRTClassifier) notifier;
				processInheritance(root);
			}
		}
	}

	private static class ForGeneralization extends InheritanceAdapter {
		private final ForResource forResource;

		ForGeneralization(ForResource forResource) {
			super();

			this.forResource = forResource;
		}

		@Override
		InheritanceAdapter getResourceAdapter() {
			return forResource;
		}

		@Override
		InheritanceAdapter getGeneralizationAdapter() {
			return this;
		}

		@Override
		protected void touch(Notifier notifier) {
			if (notifier instanceof Generalization) {
				// Handle the general of the new generalization
				Generalization gen = (Generalization) notifier;
				if (gen.getSpecific() != null) {
					// Generalization was added
					if (isCompatible(gen.getSpecific(), gen.getGeneral())) {
						handleGeneralAdded((InternalUMLRTClassifier) gen.getSpecific(),
								(InternalUMLRTClassifier) gen.getGeneral());
					}
				} else {
					// Generalization was deleted.
					// Don't tear down structure unless the generalization is deleted
					// or the general unset, which is handled elsewhere
				}
			}
		}

		boolean isCompatible(Classifier specific, Classifier general) {
			return (specific instanceof InternalUMLRTClassifier)
					&& (general instanceof InternalUMLRTClassifier)
					&& (specific.eClass() == general.eClass());
		}

		@Override
		protected void handleNotification(Notification msg) {
			if ((msg.getFeature() instanceof EReference) && !isUndoRedoNotification(msg)) {
				handleReference(msg, (EReference) msg.getFeature());
			}
		}

		protected void handleReference(Notification msg, EReference reference) {
			if (reference == UMLPackage.Literals.GENERALIZATION__GENERAL) {
				// We only listen to RT classifiers' generalizations
				Generalization generalization = (Generalization) msg.getNotifier();
				InternalUMLRTClassifier specific = (InternalUMLRTClassifier) generalization.getSpecific();

				// Handle change of general
				switch (msg.getEventType()) {
				case Notification.SET:
				case Notification.UNSET:
					Classifier oldGeneral = (Classifier) msg.getOldValue();
					if (isCompatible(specific, oldGeneral)) {
						handleGeneralRemoved(specific, (InternalUMLRTClassifier) oldGeneral);
					}
					Classifier newGeneral = (Classifier) msg.getNewValue();
					if (isCompatible(specific, newGeneral)) {
						handleGeneralAdded(specific, (InternalUMLRTClassifier) newGeneral);
					}
				}
			}
		}

		void handleGeneralAdded(InternalUMLRTClassifier specific, InternalUMLRTClassifier general) {
			// Nothing we do here must auto-reify any objects
			UMLRTExtensionUtil.run(specific,
					() -> demandExtensionExtent(specific).run(specific, () -> specific.rtInherit(general)));
		}

		void handleGeneralRemoved(InternalUMLRTClassifier specific, InternalUMLRTClassifier general) {
			// Nothing we do here must auto-reify any objects
			UMLRTExtensionUtil.run(specific,
					() -> demandExtensionExtent(specific).run(specific, () -> specific.rtDisinherit(general)));
		}
	}

	private static class ForStateMachine extends InheritanceAdapter {
		private final ForResource forResource;

		ForStateMachine(ForResource forResource) {
			super();

			this.forResource = forResource;
		}

		@Override
		InheritanceAdapter getResourceAdapter() {
			return forResource;
		}

		@Override
		InheritanceAdapter getStateMachineAdapter() {
			return this;
		}

		@Override
		public void setTarget(Notifier newTarget) {
			if (newTarget instanceof InternalUMLRTStateMachine) {
				// Discover existing structure
				InternalUMLRTStateMachine machine = (InternalUMLRTStateMachine) newTarget;
				InternalUMLRTStateMachine extended = machine.rtGetAncestor();
				if (extended != null) {
					getStateMachineAdapter().adapt(extended);

					// Inherit from it, if appropriate
					touch(extended);
				}

				List<Region> regions = machine.getRegions();
				if (!regions.isEmpty()) {
					regions.forEach(getStateMachineAdapter()::adapt);
				}
			} else if (newTarget instanceof InternalUMLRTRegion) {
				// Discover existing structure
				InternalUMLRTRegion region = (InternalUMLRTRegion) newTarget;
				InternalUMLRTRegion extended = region.rtGetAncestor();
				if (extended != null) {
					getStateMachineAdapter().adapt(extended);

					// Inherit from it, if appropriate
					touch(extended);

					// And discover its transitions, because they don't have
					// any stereotype to trigger discovery
					UMLRTExtensionUtil.<Transition> getUMLRTContents(region, UMLPackage.Literals.REGION__TRANSITION)
							.forEach(getStateMachineAdapter()::adapt);
				}

				List<NamedElement> members = region.getOwnedMembers();
				if (!members.isEmpty()) {
					// The states can be composite, so look to propagate to nested regions.
					// And transitions have triggers to inherit
					Predicate<NamedElement> isState = State.class::isInstance;
					Predicate<NamedElement> isTransition = Transition.class::isInstance;
					members.stream()
							.filter(isState.or(isTransition))
							.forEach(getStateMachineAdapter()::adapt);
				}
			} else if (newTarget instanceof InternalUMLRTState) {
				// Discover existing structure
				InternalUMLRTState state = (InternalUMLRTState) newTarget;
				InternalUMLRTState redefined = state.rtGetAncestor();
				if (redefined != null) {
					getStateMachineAdapter().adapt(redefined);

					// Inherit from it, if appropriate
					touch(redefined);
				}

				List<Region> regions = state.getRegions();
				if (!regions.isEmpty()) {
					regions.forEach(getStateMachineAdapter()::adapt);
				}
			} else if (newTarget instanceof InternalUMLRTTransition) {
				// Discover existing structure
				InternalUMLRTTransition transition = (InternalUMLRTTransition) newTarget;
				InternalUMLRTTransition redefined = transition.rtGetAncestor();
				if (redefined != null) {
					getStateMachineAdapter().adapt(redefined);

					// Inherit from it, if appropriate
					touch(redefined);
				}
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (oldTarget instanceof InternalUMLRTStateMachine) {
				// Forget existing structure
				InternalUMLRTStateMachine machine = (InternalUMLRTStateMachine) oldTarget;
				InternalUMLRTStateMachine redefined = machine.rtGetAncestor();
				if (redefined != null) {
					getStateMachineAdapter().unadapt(redefined);
				}
				List<Region> regions = machine.getRegions();
				if (!regions.isEmpty()) {
					regions.forEach(getStateMachineAdapter()::unadapt);
				}
			}
		}

		@Override
		protected void handleNotification(Notification msg) {
			if (msg.getFeature() instanceof EReference) {
				EReference reference = (EReference) msg.getFeature();
				if (!isUndoRedoNotification(msg)) {
					handleReference(msg, reference);
				} else if ((msg.getNotifier() instanceof Transition)
						&& UMLPackage.Literals.CONSTRAINT.isSuperTypeOf(reference.getEReferenceType())) {

					// Constraints were changed. Could be false-constraints for exclusion of triggers.
					// Tickle them all
					Transition transition = (Transition) msg.getNotifier();
					FacadeAdapter.getInstance(transition).ifPresent(fa -> {
						UMLRTExtensionUtil.<Trigger> getUMLRTContents(transition, UMLPackage.Literals.TRANSITION__TRIGGER)
								.forEach(fa::tickle);
					});
				}
			}
		}

		protected void handleReference(Notification msg, EReference reference) {
			if (reference == UMLPackage.Literals.STATE_MACHINE__EXTENDED_STATE_MACHINE) {
				// We only listen to RT state machines and regions
				InternalUMLRTStateMachine machine = (InternalUMLRTStateMachine) msg.getNotifier();

				// Handle change of extended machine
				switch (msg.getEventType()) {
				case Notification.ADD:
				case Notification.SET:
				case Notification.UNSET:
				case Notification.RESOLVE:
					handleAncestorChanged(machine, msg.getOldValue(), msg.getNewValue(), InternalUMLRTStateMachine.class);
					break;
				}
			} else if (reference == UMLPackage.Literals.REGION__EXTENDED_REGION) {
				// We only listen to RT state machines and regions
				InternalUMLRTRegion region = (InternalUMLRTRegion) msg.getNotifier();

				// Handle change of extended region
				switch (msg.getEventType()) {
				case Notification.SET:
				case Notification.UNSET:
				case Notification.RESOLVE:
					handleAncestorChanged(region, msg.getOldValue(), msg.getNewValue(), InternalUMLRTRegion.class);
					break;
				}
			} else if (reference == UMLPackage.Literals.STATE__REDEFINED_STATE) {
				// We only listen to RT states
				InternalUMLRTState state = (InternalUMLRTState) msg.getNotifier();

				// Handle change of extended region
				switch (msg.getEventType()) {
				case Notification.SET:
				case Notification.UNSET:
				case Notification.RESOLVE:
					handleAncestorChanged(state, msg.getOldValue(), msg.getNewValue(), InternalUMLRTState.class);
					break;
				}
			} else if (reference == UMLPackage.Literals.TRANSITION__REDEFINED_TRANSITION) {
				// We only listen to RT transitions
				InternalUMLRTTransition transition = (InternalUMLRTTransition) msg.getNotifier();

				// Handle change of extended region
				switch (msg.getEventType()) {
				case Notification.SET:
				case Notification.UNSET:
				case Notification.RESOLVE:
					handleAncestorChanged(transition, msg.getOldValue(), msg.getNewValue(), InternalUMLRTTransition.class);
					break;
				}
			} else if (reference.isContainment()) {
				Notifier notifier = (Notifier) msg.getNotifier();

				if (((notifier instanceof Region) && UMLPackage.Literals.TRANSITION.isSuperTypeOf(reference.getEReferenceType()))
						|| ((notifier instanceof Transition) && UMLPackage.Literals.TRIGGER.isSuperTypeOf(reference.getEReferenceType()))
						|| ((notifier instanceof Transition) && UMLPackage.Literals.CONSTRAINT.isSuperTypeOf(reference.getEReferenceType()))) {

					// Transitions, triggers, and constraints don't have stereotype applications to trigger this

					if (UMLPackage.Literals.TRANSITION.isSuperTypeOf(reference.getEReferenceType())) {
						// Listen for triggers and constraints
						if (msg.getNewValue() instanceof Transition) {
							getStateMachineAdapter().adapt((Transition) msg.getNewValue());
						} else if (msg.getNewValue() instanceof Collection<?>) {
							@SuppressWarnings("unchecked")
							Collection<Transition> newTransitions = (Collection<Transition>) msg.getNewValue();
							newTransitions.forEach(getStateMachineAdapter()::adapt);
						}
					}

					switch (msg.getEventType()) {
					case Notification.ADD:
					case Notification.ADD_MANY:
					case Notification.SET:
						// Inherit the new transitions/triggers
						touch(notifier);
						break;
					// TODO: What about removals?
					}
				}
			}
		}

		@Override
		protected void touch(Notifier notifier) {
			if (!isResourceLoading()) {
				if (notifier instanceof InternalUMLRTStateMachine) {
					InternalUMLRTStateMachine root = (InternalUMLRTStateMachine) notifier;
					processInheritance(root);
				} else if (notifier instanceof InternalUMLRTRegion) {
					InternalUMLRTRegion root = (InternalUMLRTRegion) notifier;
					processInheritance(root);
				} else if (notifier instanceof InternalUMLRTState) {
					InternalUMLRTState root = (InternalUMLRTState) notifier;
					processInheritance(root);
				} else if (notifier instanceof InternalUMLRTTransition) {
					InternalUMLRTTransition root = (InternalUMLRTTransition) notifier;
					processInheritance(root);
				}
			}
		}
	}

	private static class ForResource extends InheritanceAdapter {
		private final ForClassifier forClassifier = new ForClassifier(this);
		private final ForGeneralization forGeneralization = new ForGeneralization(this);
		private final ForStateMachine forStateMachine = new ForStateMachine(this);

		private final ForStructureStereotype forStructureStereotype = new ForStructureStereotype(this);
		private final ForStateMachineStereotype forStateMachineStereotype = new ForStateMachineStereotype(this);

		private ReificationAdapter reification;

		private InheritanceTracker inheritanceTracker;

		ForResource(Resource resource) {
			super();
		}

		@Override
		final InheritanceAdapter getResourceAdapter() {
			return this;
		}

		@Override
		final InheritanceAdapter getClassifierAdapter() {
			return forClassifier;
		}

		@Override
		final InheritanceAdapter getGeneralizationAdapter() {
			return forGeneralization;
		}

		@Override
		final InheritanceAdapter getStateMachineAdapter() {
			return forStateMachine;
		}

		@Override
		final ForStructureStereotype getStructureStereotypeAdapter() {
			return forStructureStereotype;
		}

		@Override
		final ForStateMachineStereotype getStateMachineStereotypeAdapter() {
			return forStateMachineStereotype;
		}

		@Override
		protected boolean isUndoRedoNotification(Notification msg) {
			return getReificationAdapter().isUndoRedoNotification(msg);
		}

		final ReificationAdapter getReificationAdapter() {
			if (reification == null) {
				// What is the reification adapter for this resource?
				reification = ExtensionResource.demandExtensionExtent(getTarget()).getReificationAdapter();
			}
			return reification;
		}

		@Override
		boolean isResourceLoading() {
			return (target != null) && ((Resource.Internal) target).isLoading();
		}

		final boolean doThrottleInheritance(InternalUMLRTRedefinitionContext<?> root) {
			return (inheritanceTracker != null) && inheritanceTracker.track(root);
		}

		final void doWhileTrackingInheritance(Runnable action) {
			boolean postProcess;

			// Only do this at the top level of reëntrance
			if (inheritanceTracker == null) {
				inheritanceTracker = new InheritanceTracker();
				postProcess = true;
			} else {
				postProcess = false;
			}

			try {
				action.run();
			} finally {
				if (postProcess) {
					InheritanceTracker deferred = inheritanceTracker;

					// Clear this now so that we won't throttle
					inheritanceTracker = null;

					deferred.processInheritance();
				}
			}
		}

		boolean isUMLRTStereotype(EObject object) {
			EPackage ePackage = object.eClass().getEPackage();
			return (ePackage == UMLRealTimePackage.eINSTANCE)
					|| (ePackage == UMLRTStateMachinesPackage.eINSTANCE);
		}

		@Override
		public void setTarget(Notifier newTarget) {
			super.setTarget(newTarget);
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (oldTarget == target) {
				// Un-discover stereotype applications
				Resource resource = (Resource) oldTarget;
				resource.getContents().stream()
						.filter(this::isUMLRTStereotype)
						.forEach(stereo -> stereo.eAdapters().remove(this));

				super.unsetTarget(oldTarget);
			}
		}

		@Override
		protected void handleNotification(Notification msg) {
			if (msg.getNotifier() instanceof Resource) {
				if (msg.getFeatureID(Resource.class) == Resource.RESOURCE__CONTENTS) {
					// Handle addition/removal of stereotype applications
					switch (msg.getEventType()) {
					case Notification.ADD: {
						EObject newObject = (EObject) msg.getNewValue();
						if (isStructureStereotype(newObject)) {
							getStructureStereotypeAdapter().adapt(newObject);
							getStructureStereotypeAdapter().handleStereotypeAdded(newObject, msg);
						} else if (isStateMachineStereotype(newObject)) {
							getStateMachineStereotypeAdapter().adapt(newObject);
							getStateMachineStereotypeAdapter().handleStereotypeAdded(newObject, msg);
						}
						break;
					}
					case Notification.REMOVE: {
						EObject oldObject = (EObject) msg.getOldValue();
						if (isStructureStereotype(oldObject)) {
							getStructureStereotypeAdapter().unadapt(oldObject);
							getStructureStereotypeAdapter().handleStereotypeRemoved(oldObject, msg);
						} else if (isStateMachineStereotype(oldObject)) {
							getStateMachineStereotypeAdapter().unadapt(oldObject);
							getStateMachineStereotypeAdapter().handleStereotypeRemoved(oldObject, msg);
						}
						break;
					}
					case Notification.SET: {
						EObject oldObject = (EObject) msg.getOldValue();
						if (isStructureStereotype(oldObject)) {
							getStructureStereotypeAdapter().unadapt(oldObject);
							getStructureStereotypeAdapter().handleStereotypeRemoved(oldObject, msg);
						} else if (isStateMachineStereotype(oldObject)) {
							getStateMachineStereotypeAdapter().unadapt(oldObject);
							getStateMachineStereotypeAdapter().handleStereotypeRemoved(oldObject, msg);
						}
						EObject newObject = (EObject) msg.getNewValue();
						if (isStructureStereotype(newObject)) {
							getStructureStereotypeAdapter().adapt(newObject);
							getStructureStereotypeAdapter().handleStereotypeAdded(newObject, msg);
						} else if (isStateMachineStereotype(newObject)) {
							getStateMachineStereotypeAdapter().adapt(newObject);
							getStateMachineStereotypeAdapter().handleStereotypeAdded(newObject, msg);
						}
						break;
					}
					case Notification.ADD_MANY:
						for (Object next : (Collection<?>) msg.getNewValue()) {
							EObject newObject = (EObject) next;
							if (isStructureStereotype(newObject)) {
								getStructureStereotypeAdapter().adapt(newObject);
								getStructureStereotypeAdapter().handleStereotypeAdded(newObject, msg);
							} else if (isStateMachineStereotype(newObject)) {
								getStateMachineStereotypeAdapter().adapt(newObject);
								getStateMachineStereotypeAdapter().handleStereotypeAdded(newObject, msg);
							}
						}
						break;
					case Notification.REMOVE_MANY:
						for (Object next : (Collection<?>) msg.getOldValue()) {
							EObject oldObject = (EObject) next;
							if (isStructureStereotype(oldObject)) {
								getStructureStereotypeAdapter().unadapt(oldObject);
								getStructureStereotypeAdapter().handleStereotypeRemoved(oldObject, msg);
							} else if (isStateMachineStereotype(oldObject)) {
								getStateMachineStereotypeAdapter().unadapt(oldObject);
								getStateMachineStereotypeAdapter().handleStereotypeRemoved(oldObject, msg);
							}
						}
						break;
					}
				} else if (msg.getFeatureID(Resource.class) == Resource.RESOURCE__IS_LOADED) {
					if (!msg.getOldBooleanValue() && msg.getNewBooleanValue()) {
						// The resource has loaded. Process its contents, now
						whileTrackingInheritance(
								() -> discoverContents((Resource) msg.getNotifier()));
					}
				}
			}
		}

		void discoverContents(Resource resource) {
			Switch<EObject> smDiscoverySwitch = new UMLRTStateMachinesSwitch<EObject>() {
				@Override
				public EObject caseRTStateMachine(RTStateMachine object) {
					if (object.getBase_StateMachine() != null) {
						getStateMachineAdapter().touch(object.getBase_StateMachine());
					}
					return object;
				}

				@Override
				public EObject caseRTRegion(RTRegion object) {
					Region region = object.getBase_Region();
					if (region != null) {
						getStateMachineAdapter().touch(region);

						// And discover its transitions
						UMLRTExtensionUtil.<Transition> getUMLRTContents(region, UMLPackage.Literals.REGION__TRANSITION)
								.forEach(getStateMachineAdapter()::touch);
					}
					return object;
				}

				@Override
				public EObject caseRTState(RTState object) {
					if (object.getBase_State() != null) {
						getStateMachineAdapter().touch(object.getBase_State());
					}
					return object;
				}

				@Override
				public EObject caseRTGuard(RTGuard object) {
					Constraint base = object.getBase_Constraint();
					if (base != null) {
						Namespace context = base.getContext();
						if (context instanceof Transition) {
							getStateMachineAdapter().touch(context);
						}
					}
					return object;
				}
			};
			Switch<EObject> discoverySwitch = new UMLRealTimeSwitch<EObject>() {
				@Override
				public EObject caseCapsule(Capsule object) {
					if (object.getBase_Class() != null) {
						getClassifierAdapter().touch(object.getBase_Class());
					}
					return object;
				}

				@Override
				public EObject caseProtocol(Protocol object) {
					if (object.getBase_Collaboration() != null) {
						getClassifierAdapter().touch(object.getBase_Collaboration());
					}
					return object;
				}

				@Override
				public EObject caseRTMessageSet(RTMessageSet object) {
					if (object.getBase_Interface() != null) {
						getClassifierAdapter().touch(object.getBase_Interface());
					}
					return object;
				}

				@Override
				public EObject defaultCase(EObject object) {
					// Try state machines
					return smDiscoverySwitch.doSwitch(object);
				}
			};

			// Discovery of contents can add new stereotype applications
			final List<EObject> contents = resource.getContents();
			List<EObject> range = new ArrayList<>(contents);
			do {
				int count = contents.size();

				List<EObject> range_ = range;
				ExtensionResource extent = ExtensionResource.demandExtensionExtent(resource);
				extent.run(resource, () -> range_.forEach(discoverySwitch::doSwitch));

				// Look for new stereotype applications
				range = contents.subList(count, contents.size());
				if (!range.isEmpty()) {
					// Safe iteration, of course
					range = new ArrayList<>(range);
				}
			} while (!range.isEmpty());
		}
	}

	private static class ForStructureStereotype extends InheritanceAdapter {
		private final ForResource forResource;

		ForStructureStereotype(ForResource forResource) {
			super();

			this.forResource = forResource;
		}

		@Override
		InheritanceAdapter getResourceAdapter() {
			return forResource;
		}

		@Override
		InheritanceAdapter getStructureStereotypeAdapter() {
			return this;
		}

		@Override
		protected void handleNotification(Notification msg) {
			// Was base element reference changed?
			switch (msg.getEventType()) {
			case Notification.SET:
			case Notification.UNSET:
				if (msg.getFeature() instanceof EReference) {
					EReference ref = (EReference) msg.getFeature();
					if (ref.getName().startsWith("base_")) { //$NON-NLS-1$
						EObject application = (EObject) msg.getNotifier();

						if (msg.getOldValue() != null) {
							handleStereotypeRemoved(application, msg);
						}
						if (msg.getNewValue() != null) {
							handleStereotypeAdded(application, msg);
						}
					} else if (!isResourceLoading()) {
						if (ref == UMLRealTimePackage.Literals.RT_REDEFINED_ELEMENT__ROOT_FRAGMENT) {
							// Special case: for generating exclusion notifications
							notifyExclusion((RTRedefinedElement) msg.getNotifier(),
									(RedefinableElement) msg.getNewValue());
						}
					}
					break;
				}
			}
		}

		/**
		 * Handle addition of a structure stereotype by
		 * <ul>
		 * <li>in the case of an UML-RT classifier (capsule/protocol), initializing its façade
		 * for inheritance etc.</li>
		 * <li>in the case of a capsule feature (port/part/connector), updating the capsule
		 * hierarchy to inherit it. This is necessary because the addition of the feature,
		 * itself, is not a sufficient trigger as it may not yet have the stereotype.
		 * It depends on the order of operations in the creation of the new feature</li>
		 * </ul>
		 */
		void handleStereotypeAdded(EObject stereotypeApplication, Notification msg) {
			boolean isUndoRedo = isUndoRedoNotification(msg);

			switch (stereotypeApplication.eClass().getClassifierID()) {
			case UMLRealTimePackage.CAPSULE: {
				Capsule capsule = (Capsule) stereotypeApplication;
				Class base = capsule.getBase_Class();
				if (base != null) {
					// Initialize it
					getClassifierAdapter().adapt(base);

					// Kick the package façade, if any
					org.eclipse.uml2.uml.Package package_ = base.getNearestPackage();
					if (package_ != null) {
						FacadeAdapter.getInstance(package_).ifPresent(a -> a.tickle(base));
					}
				}
				break;
			}
			case UMLRealTimePackage.RT_PORT: {
				RTPort port = (RTPort) stereotypeApplication;
				Port base = port.getBase_Port();
				Class capsule = (base == null) ? null : base.getClass_();
				if (capsule != null) {
					if (!isUndoRedo) {
						// Initialize it
						getClassifierAdapter().touch(capsule);
					}

					// Kick the façade, if any
					FacadeAdapter.getInstance(capsule).ifPresent(a -> a.tickle(base));
				}
				break;
			}
			case UMLRealTimePackage.CAPSULE_PART: {
				CapsulePart part = (CapsulePart) stereotypeApplication;
				Property base = part.getBase_Property();
				Class capsule = (base == null) ? null : base.getClass_();
				if (capsule != null) {
					if (!isUndoRedo) {
						// Initialize it
						getClassifierAdapter().touch(capsule);
					}

					// Kick the façade, if any
					FacadeAdapter.getInstance(capsule).ifPresent(a -> a.tickle(base));
				}
				break;
			}
			case UMLRealTimePackage.RT_CONNECTOR: {
				RTConnector connector = (RTConnector) stereotypeApplication;
				Connector base = connector.getBase_Connector();
				Namespace capsule = (base == null) ? null : base.getNamespace();
				if (capsule instanceof Class) {
					if (!isUndoRedo) {
						// Initialize it
						getClassifierAdapter().touch(capsule);
					}

					// Kick the façade, if any
					FacadeAdapter.getInstance(capsule).ifPresent(a -> a.tickle(base));
				}
				break;
			}
			case UMLRealTimePackage.PROTOCOL: {
				Protocol protocol = (Protocol) stereotypeApplication;
				Collaboration base = protocol.getBase_Collaboration();
				if (base != null) {
					// Initialize it
					getClassifierAdapter().adapt(base);

					// Kick the package façade, if any
					org.eclipse.uml2.uml.Package package_ = base.getNearestPackage();
					if (package_ != null) {
						package_ = package_.getNestingPackage();
						if (package_ != null) {
							FacadeAdapter.getInstance(package_).ifPresent(a -> a.tickle(base));
						}
					}
				}
				break;
			}
			case UMLRealTimePackage.RT_MESSAGE_SET: {
				RTMessageSet messageSet = (RTMessageSet) stereotypeApplication;
				Interface base = messageSet.getBase_Interface();
				if (base != null) {
					// Initialize it
					getClassifierAdapter().adapt(base);

					// Kick the protocol façade, if any
					FacadeAdapter.getInstance(base).ifPresent(a -> a.tickle(base));
				}
				break;
			}
			case UMLRealTimePackage.PROTOCOL_CONTAINER: {
				ProtocolContainer protocolContainer = (ProtocolContainer) stereotypeApplication;
				org.eclipse.uml2.uml.Package base = protocolContainer.getBase_Package();
				if (base != null) {
					// Kick the containing package façade, if any
					org.eclipse.uml2.uml.Package package_ = base.getNestingPackage();
					if (package_ != null) {
						FacadeAdapter.getInstance(package_).ifPresent(a -> a.tickle(base));
					}
				}
				break;
			}
			case UMLRealTimePackage.RT_REDEFINED_ELEMENT: {
				RTRedefinedElement redefinedElement = (RTRedefinedElement) stereotypeApplication;
				RedefinableElement base = redefinedElement.getBase_RedefinableElement();
				if ((base != null) && !isResourceLoading()) {
					// Kick the containing façade, if any
					notifyExclusion(redefinedElement, redefinedElement.getRootFragment());
				}
			}
			}
		}

		void handleStereotypeRemoved(EObject stereotypeApplication, Notification msg) {
			// TODO: Does it make sense to handle removal of a mandatory stereotype?
		}

		void notifyExclusion(RTRedefinedElement rtRedef, RedefinableElement newRoot) {
			RedefinableElement uml = rtRedef.getBase_RedefinableElement();
			if (uml != null) {
				// The notifications injected here are on derived features, so
				// the EMF ChangeRecorder (if any) will ignore them, which is good
				// because otherwise trying to apply the change would cause trouble

				Notification exclusionMsg = null;
				Namespace namespace = null;

				if (newRoot != null) {
					// Re-inherited
					namespace = uml.getNamespace();
					// Notify on the namespace's excluded members
					if (namespace instanceof InternalUMLRTElement) {
						InternalEObject ext = (InternalEObject) namespace.eGet(ExtUMLExtPackage.Literals.ELEMENT__EXTENSION);
						exclusionMsg = new ENotificationImpl(ext, Notification.REMOVE,
								ExtUMLExtPackage.Literals.ELEMENT__EXCLUDED_ELEMENT,
								uml, null, Notification.NO_INDEX);
						if (ext == null) {
							// There isn't actually an extension to forward this notification,
							// so do it here
							exclusionMsg = NotificationForwarder.wrap((InternalEObject) namespace, exclusionMsg);
						}
					}
				} else if (newRoot == null) {
					// Excluded
					namespace = uml.getNamespace();
					// Notify on the namespace's excluded members
					if (namespace instanceof InternalUMLRTElement) {
						InternalEObject ext = (InternalEObject) namespace.eGet(ExtUMLExtPackage.Literals.ELEMENT__EXTENSION);
						exclusionMsg = new ENotificationImpl(ext, Notification.ADD,
								ExtUMLExtPackage.Literals.ELEMENT__EXCLUDED_ELEMENT,
								null, uml, Notification.NO_INDEX);
						if (ext == null) {
							// There isn't actually an extension to forward this notification,
							// so do it here
							exclusionMsg = NotificationForwarder.wrap((InternalEObject) namespace, exclusionMsg);
						}
					}
				}

				if (exclusionMsg != null) {
					// And notify on the element's owner to trigger refresh
					// of icons etc. in the UI
					NotificationChain msgs = new ENotificationImpl((InternalEObject) uml, Notification.SET,
							UMLPackage.Literals.NAMED_ELEMENT__QUALIFIED_NAME,
							uml.getQualifiedName(), uml.getQualifiedName()) {

						@Override
						public boolean isTouch() {
							// Don't let clients filtering on isTouch() ignore this
							return false;
						}
					};
					msgs.add(exclusionMsg);
					msgs.dispatch();

					// And notify any façade
					if (namespace != null) {
						Consumer<FacadeAdapter> action = (newRoot == null)
								? a -> a.excluded(uml)
								: a -> a.reinherited(uml);

						FacadeAdapter.getInstance(namespace).ifPresent(action);
					}
				}
			}
		}
	}

	private static class ForStateMachineStereotype extends InheritanceAdapter {
		private final ForResource forResource;

		ForStateMachineStereotype(ForResource forResource) {
			super();

			this.forResource = forResource;
		}

		@Override
		InheritanceAdapter getResourceAdapter() {
			return forResource;
		}

		@Override
		InheritanceAdapter getStateMachineStereotypeAdapter() {
			return this;
		}

		@Override
		protected void handleNotification(Notification msg) {
			// Was base element reference changed?
			switch (msg.getEventType()) {
			case Notification.SET:
			case Notification.UNSET:
				if (msg.getFeature() instanceof EReference) {
					EReference ref = (EReference) msg.getFeature();
					if (ref.getName().startsWith("base_")) { //$NON-NLS-1$
						EObject application = (EObject) msg.getNotifier();

						if (msg.getOldValue() != null) {
							handleStereotypeRemoved(application, msg);
						}
						if (msg.getNewValue() != null) {
							handleStereotypeAdded(application, msg);
						}
					}
					break;
				}
			}
		}

		/**
		 * Handle addition of a structure stereotype by
		 * <ul>
		 * <li>in the case of a state machine or region, initializing its façade
		 * for inheritance etc.</li>
		 * <li>in the case of a vertex (state/pseudostate) or transition, updating the state machine
		 * hierarchy to inherit it. This is necessary because the addition of the element,
		 * itself, is not a sufficient trigger as it may not yet have the stereotype.
		 * It depends on the order of operations in the creation of the new element</li>
		 * </ul>
		 */
		void handleStereotypeAdded(EObject stereotypeApplication, Notification msg) {
			boolean isUndoRedo = isUndoRedoNotification(msg);

			switch (stereotypeApplication.eClass().getClassifierID()) {
			case UMLRTStateMachinesPackage.RT_STATE_MACHINE: {
				RTStateMachine machine = (RTStateMachine) stereotypeApplication;
				StateMachine base = machine.getBase_StateMachine();
				if (base != null) {
					// Initialize it
					getStateMachineAdapter().adapt(machine.getBase_StateMachine());

					if (!isUndoRedo) {
						// And handle inheritance by specializing capsules
						if (base.getContext() != null) {
							getClassifierAdapter().touch(base.getContext());
						}
					}
				}
				break;
			}
			case UMLRTStateMachinesPackage.RT_REGION: {
				RTRegion region = (RTRegion) stereotypeApplication;
				Region base = region.getBase_Region();
				if (base != null) {
					// Initialize it
					getStateMachineAdapter().adapt(base);

					if (!isUndoRedo) {
						// And handle inheritance by redefining state machines
						if (base.getStateMachine() != null) {
							getStateMachineAdapter().touch(base.getStateMachine());
						}
						if (base.getState() != null) {
							getStateMachineAdapter().touch(base.getState());
						}
					}
				}
				break;
			}
			case UMLRTStateMachinesPackage.RT_STATE: {
				RTState state = (RTState) stereotypeApplication;
				State base = state.getBase_State();
				if ((base != null) && (base.getContainer() != null)) {
					// Initialize it for the regions it may contain
					getStateMachineAdapter().adapt(state.getBase_State());

					if (!isUndoRedo) {
						// Inherit it in redefining state machines
						getStateMachineAdapter().touch(base.getContainer());
					}

					// Kick the state/state-machine façade, if any
					Region container = base.getContainer();
					if (container != null) {
						FacadeAdapter.getInstance(container).ifPresent(a -> a.tickle(base));
					}
				}
				break;
			}
			case UMLRTStateMachinesPackage.RT_PSEUDOSTATE: {
				RTPseudostate pseudo = (RTPseudostate) stereotypeApplication;
				Pseudostate base = pseudo.getBase_Pseudostate();
				if (base != null) {
					if (base.getContainer() != null) {
						if (!isUndoRedo) {
							// Inherit it in redefining state machines
							getStateMachineAdapter().touch(base.getContainer());
						}

						// Kick the state/state-machine façade, if any
						FacadeAdapter.getInstance(base.getContainer()).ifPresent(a -> a.tickle(base));
					} else if (base.getState() != null) {
						if (!isUndoRedo) {
							// Inherit it in redefining states
							getStateMachineAdapter().touch(base.getState());
						}

						// Kick the state façade, if any
						FacadeAdapter.getInstance(base.getState()).ifPresent(a -> a.tickle(base));
					} else if (base.getStateMachine() != null) {
						if (!isUndoRedo) {
							// Inherit it in redefining state machines
							getStateMachineAdapter().touch(base.getStateMachine());
						}

						// Kick the state machine façade, if any
						FacadeAdapter.getInstance(base.getStateMachine()).ifPresent(a -> a.tickle(base));
					}
				}
				break;
			}
			case UMLRTStateMachinesPackage.RT_GUARD: {
				RTGuard guard = (RTGuard) stereotypeApplication;
				Constraint base = guard.getBase_Constraint();
				if (base != null) {
					Namespace context = base.getContext();
					if (context instanceof Transition) {
						if (!isUndoRedo) {
							// Inherit it in redefining state machines
							getStateMachineAdapter().touch(context);
						}

						// Kick the transition façade, if any
						FacadeAdapter.getInstance(context).ifPresent(a -> a.tickle(base));
					}
				}
				break;
			}
			// TODO etc.
			}
		}

		void handleStereotypeRemoved(EObject stereotypeApplication, Notification msg) {
			// TODO: Does it make sense to handle removal of a mandatory stereotype?
		}
	}

	private class InheritanceTracker {
		private final Set<InternalUMLRTRedefinitionContext<?>> elements = new HashSet<>();

		private final boolean deferred;

		InheritanceTracker(boolean deferred) {
			super();

			this.deferred = deferred;
		}

		InheritanceTracker() {
			this(true);
		}

		boolean track(InternalUMLRTRedefinitionContext<?> element) {
			return !elements.add(element) || deferred;
		}

		void processInheritance() {
			if (!deferred || elements.isEmpty()) {
				// It has already been done along the way or there's nothing to do
				return;
			}

			// At this point, we depend on the UML API being able to provide
			// an accurate view of stereotype applications for all elements, but
			// depending on when the CacheAdapter was attached, it may have
			// cached empty stereotype-application lists that didn't subsequently
			// get cleared out after the stereotype applications were loaded and
			// attached to their base elements. So, clear the CacheAdapter to make
			// sure that cross-references are used to compute everything afresh
			Resource resource = (Resource) getResourceAdapter().getTarget();
			CacheAdapter cache = CacheAdapter.getCacheAdapter(resource);
			if (cache != null) {
				cache.clear(resource);
			}

			// Start with root elements
			List<InternalUMLRTRedefinitionContext<?>> roots = elements.stream()
					.filter(e -> e.rtGetAncestor() == null)
					.collect(Collectors.toList());
			roots.forEach(this::doProcessInheritance);

			// There shouldn't be any left
			if (!elements.isEmpty()) {
				UMLRTUMLPlugin.INSTANCE.log("Deferred inheritance processing has left-overs: " + elements);
				new ArrayList<>(elements).forEach(this::doProcessInheritance);
			}
		}

		@SuppressWarnings("unchecked")
		private void doProcessInheritance(@SuppressWarnings("rawtypes") InternalUMLRTRedefinitionContext root) {
			InheritanceAdapter.this.processInheritance(root, elements::remove);
		}
	}
}
