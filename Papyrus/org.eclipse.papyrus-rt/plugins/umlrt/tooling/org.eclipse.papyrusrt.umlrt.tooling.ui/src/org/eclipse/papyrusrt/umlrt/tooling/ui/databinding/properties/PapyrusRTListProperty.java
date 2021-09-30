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

package org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.properties;

import static java.util.stream.Collectors.toList;
import static org.eclipse.core.databinding.observable.Diffs.createListDiff;
import static org.eclipse.core.databinding.observable.Diffs.createListDiffEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListDiff;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.SimplePropertyEvent;
import org.eclipse.core.databinding.property.list.SimpleListProperty;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrusrt.umlrt.core.commands.ExclusionCommand;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.IFilteredObservableList;
import org.eclipse.papyrusrt.umlrt.tooling.ui.util.InheritanceUIComparator;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Element;

/**
 * A property for an inheritable list reference, supporting filtering.
 */
public abstract class PapyrusRTListProperty<S, T> extends SimpleListProperty<S, T> implements IFilteredListProperty<S, T> {
	protected final Class<T> elementType;
	protected final EStructuralFeature sourceFeature;
	protected final EStructuralFeature feature;

	private List<T> listView;

	public PapyrusRTListProperty(Class<T> elementType, EStructuralFeature sourceFeature, EStructuralFeature feature) {
		super();

		this.elementType = elementType;
		this.sourceFeature = sourceFeature;
		this.feature = feature;
	}

	@Override
	public Object getElementType() {
		return elementType;
	}

	public abstract String getPropertyName();

	@Override
	public IObservableList<T> observe(Realm realm, S source) {
		return IFilteredObservableList.wrap(super.observe(realm, source));
	}

	@Override
	protected List<T> doGetList(S source) {
		if (listView == null) {
			Stream<T> elements = stream(source);

			listView = elements.sorted(inheritanceComparator())
					.collect(toList());
		}
		return listView;
	}

	protected Stream<T> stream(S source) {
		EList<T> result = UMLRTExtensionUtil.getUMLRTContents(unwrapSource(source), sourceFeature);
		return result.stream();
	}

	protected UMLRTInheritanceKind getInheritanceKindOf(T element) {
		return UMLRTInheritanceKind.of(unwrap(element));
	}

	protected Comparator<T> inheritanceComparator() {
		return Comparator.comparing(this::unwrap, new InheritanceUIComparator());
	}

	protected EList<T> eList(S source) {
		return eList((EObject) source, sourceFeature);
	}

	protected abstract Element unwrapSource(S source);

	protected abstract Element unwrap(T element);

	protected abstract Object wrap(Element element);

	protected <E> EList<E> eList(Element uml) {
		return eList(umlOwner(uml), feature);
	}

	protected EObject umlOwner(Element uml) {
		return uml;
	}

	@SuppressWarnings("unchecked")
	private <E> EList<E> eList(EObject source, EStructuralFeature feature) {
		return (EList<E>) source.eGet(feature);
	}

	@Override
	protected void doSetList(S source, List<T> list, ListDiff<T> diff) {
		List<? extends ICommand> commands = getCommands(source, diff);
		if (!commands.isEmpty()) {
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(unwrapSource(source));
			CompositeTransactionalCommand command = new CompositeTransactionalCommand(
					domain, "Edit " + getPropertyName(), commands);
			if (command.canExecute()) {
				domain.getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(command.reduce()));

				// Recalculate the content
				listView = null;
				doGetList(source);
			}
		}
	}

	@Override
	public INativePropertyListener<S> adaptListener(ISimplePropertyListener<S, ListDiff<T>> listener) {
		return new NativeListener(listener);
	}

	protected List<? extends ICommand> getCommands(S source, ListDiff<T> diff) {
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(unwrapSource(source));
		List<ICommand> result = new ArrayList<>();

		// We cannot use GMF edit-helpers for these complex properties
		diff.accept(new ListDiffVisitor<T>() {

			@Override
			public void handleMove(int oldIndex, int newIndex, T element) {
				// Support for moves is very restrictive: the elements must be in the
				// same EMF containment list. That means that whatever the old index
				// is, the element that's there now would have to be in the same
				// containment list, so we can use it as a reference
				EObject elementAtOldPosition = unwrap(listView.get(oldIndex));
				EObject elementAtNewPosition = unwrap(listView.get(newIndex));
				EList<?> containment = (EList<?>) elementAtOldPosition.eContainer().eGet(elementAtOldPosition.eContainmentFeature());
				EList<?> newContainment = (EList<?>) elementAtNewPosition.eContainer().eGet(elementAtNewPosition.eContainmentFeature());
				if (containment == newContainment) {
					// Convert to this list's coordinates
					oldIndex = containment.indexOf(elementAtOldPosition);
					newIndex = containment.indexOf(elementAtNewPosition);
					if ((oldIndex >= 0) && (newIndex >= 0)) {
						class MoveCommand extends AbstractTransactionalCommand {
							private EList<?> list;
							private int oldIndex;
							private int newIndex;

							MoveCommand(TransactionalEditingDomain domain, String label, EList<?> list, int oldIndex, int newIndex) {
								super(domain, label, getWorkspaceFiles(unwrapSource(source)));

								this.list = list;
								this.oldIndex = oldIndex;
								this.newIndex = newIndex;
							}

							@Override
							protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
								list.move(newIndex, oldIndex);
								return CommandResult.newOKCommandResult(element);
							}
						}

						result.add(new MoveCommand(domain, "Move " + getPropertyName(), containment, oldIndex, newIndex));
					}
				}
			}

			@Override
			public void handleRemove(int index, T element) {
				switch (getInheritanceKindOf(element)) {
				case INHERITED:
				case REDEFINED:
					// Exclude, don't destroy
					result.add(ExclusionCommand.getExclusionCommand(domain, unwrap(element), true));
					break;
				case NONE:
					// Actually destroy it
					IEditCommandRequest request = new DestroyElementRequest(domain, unwrap(element), false);
					IElementEditService edit = ElementEditServiceUtils.getCommandProvider(unwrap(element));
					ICommand destroy = edit.getEditCommand(request);
					result.add(destroy);
					break;
				default:
					// Pass
					break;
				}
			}

			@Override
			public void handleAdd(int index, T element) {
				EList<T> list = eList(source);

				// We can't really honour the index. And we can't use an AddCommand because it doesn't
				// work with the ownedPort derived list implementation for undo/redo
				class AddCommand extends AbstractTransactionalCommand {
					private T element;

					AddCommand(TransactionalEditingDomain domain, String label, T element) {
						super(domain, label, getWorkspaceFiles(unwrapSource(source)));

						this.element = element;
					}

					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
						if (!list.contains(element)) {
							eList(unwrapSource(source)).add(unwrap(element));
						}
						return CommandResult.newOKCommandResult(element);
					}
				}

				result.add(new AddCommand(domain, "Add " + getPropertyName(), element));
			}
		});

		return result;
	}

	//
	// Nested types
	//

	protected class NativeListener extends AdapterImpl implements INativePropertyListener<S> {

		private final ISimplePropertyListener<S, ListDiff<T>> listener;

		private S source;

		protected NativeListener(ISimplePropertyListener<S, ListDiff<T>> listener) {
			super();

			this.listener = listener;
		}

		protected final S getSource() {
			return source;
		}

		@Override
		public void addTo(S source) {
			((Notifier) source).eAdapters().add(this);
			this.source = source;

			addToMembers(source);
		}

		@Override
		public void removeFrom(S source) {
			if (this.source == source) {
				removeFromMembers(source);
				((Notifier) source).eAdapters().remove(this);
				this.source = null;
			}
		}

		protected void addToMembers(S source) {
			PapyrusRTListProperty.this.eList(source).forEach(this::addToElement);
		}

		protected void removeFromMembers(S source) {
			PapyrusRTListProperty.this.eList(source).forEach(this::removeFromElement);
		}

		protected void addToElement(T element) {
			addAdapter(unwrap(element));
		}

		protected void addAdapter(Notifier notifier) {
			List<Adapter> adapters = notifier.eAdapters();
			if (!adapters.contains(this)) {
				adapters.add(this);
			}
		}

		protected void removeFromElement(T element) {
			removeAdapter(unwrap(element));
		}

		protected void removeAdapter(Notifier notifier) {
			notifier.eAdapters().remove(this);
		}

		@Override
		public void notifyChanged(Notification msg) {
			// Process removals by UML representation because after they have been removed
			// from the model, it may not be possible to obtain their façade
			List<T> added = Collections.emptyList();
			List<T> removed = Collections.emptyList();
			List<T> changed = Collections.emptyList();

			// If our list has never been accessed, then there's no point in any of it
			if ((listView != null) && !msg.isTouch()) {
				if ((msg.getNotifier() == source) && (msg.getFeature() == sourceFeature)) {
					switch (msg.getEventType()) {
					case Notification.ADD: {
						T newElement = elementType.cast(msg.getNewValue());
						// We need to listen to this new element for refresh
						addToElement(newElement);

						added = Collections.singletonList(newElement);
						break;
					}
					case Notification.ADD_MANY: {
						@SuppressWarnings("unchecked")
						List<T> newElements = (List<T>) msg.getNewValue();
						// We need to listen to these new elements for refresh
						newElements.forEach(this::addToElement);

						added = newElements;
						break;
					}
					case Notification.REMOVE: {
						T oldElement = elementType.cast(msg.getOldValue());
						// We need to stop listening to this old element for refresh
						removeFromElement(oldElement);

						removed = Collections.singletonList(oldElement);
						break;
					}
					case Notification.REMOVE_MANY: {
						@SuppressWarnings("unchecked")
						List<T> oldElements = (List<T>) msg.getOldValue();
						// We need to stop to listening to these old elements for refresh
						oldElements.forEach(this::removeFromElement);

						removed = oldElements;
						break;
					}
					case Notification.MOVE: {
						T movedElement = elementType.cast(msg.getNewValue());
						removed = Collections.singletonList(movedElement);
						added = Collections.singletonList(movedElement);
						break;
					}
					case Notification.SET:
					case Notification.RESOLVE: {
						T newElement = elementType.cast(msg.getNewValue());
						// We need to listen to this new element for refresh
						addToElement(newElement);
						T oldElement = elementType.cast(msg.getOldValue());
						// We need to stop listening to this old element for refresh
						removeFromElement(oldElement);

						removed = Collections.singletonList(oldElement);
						added = Collections.singletonList(newElement);
						break;
					}
					}
				} else if ((msg.getNotifier() != source) || !handleAdditionalFeature(msg)) {
					// Does it affect an object in this list?
					T affected = getAffectedElement(msg);

					if (affected != null) {
						// UI presentation of the list may need to be refreshed
						changed = Collections.singletonList(affected);
					}
				}
			}

			if (!added.isEmpty() || !removed.isEmpty()) {
				List<T> oldListView = new ArrayList<>(listView);

				// Rebuild the list view from the model
				listView = null;

				ListDiff<T> diff = Diffs.computeListDiff(oldListView, doGetList(source));

				if (!diff.isEmpty()) {
					listener.handleEvent(new SimplePropertyEvent<>(SimplePropertyEvent.CHANGE,
							source, PapyrusRTListProperty.this, diff));
				}
			} else if (!changed.isEmpty()) {
				T changed_ = changed.get(0);
				int position = listView.indexOf(changed_);

				// Don't notify for an object not in the list (getAffectedElement may go haywire)
				if (position >= 0) {
					listener.handleEvent(new SimplePropertyEvent<>(SimplePropertyEvent.CHANGE,
							source, PapyrusRTListProperty.this,
							createListDiff(createListDiffEntry(position, false, changed_),
									createListDiffEntry(position, true, changed_))));
				}
			}
		}

		/**
		 * Hook for subclasses to handle additional features of the source element
		 * than the feature primarily being observed.
		 * 
		 * @param notification
		 *            some notification from the source object but not
		 *            the source feature
		 * 
		 * @return {@code true} if the notification was handled; {@code false} to
		 *         delegate to further processing
		 */
		protected boolean handleAdditionalFeature(Notification notification) {
			return false;
		}

		/**
		 * Obtains the element in this list that is (most) affected by the given
		 * {@code notification}.
		 * 
		 * @param notification
		 *            a notification, usually from and element in the list
		 *            (because we adapt them) or from the owner of the list, in case it directly
		 *            affects some specific element in the list
		 * 
		 * @return the affected element, or {@code null} if none of the elements in
		 *         this list is practically affected by the {@code notification}
		 */
		protected T getAffectedElement(Notification notification) {
			T result = null;

			if (notification.getNotifier() instanceof Element) {
				result = TypeUtils.as(wrap((Element) notification.getNotifier()), elementType);
			}

			return result;
		}
	}

}
