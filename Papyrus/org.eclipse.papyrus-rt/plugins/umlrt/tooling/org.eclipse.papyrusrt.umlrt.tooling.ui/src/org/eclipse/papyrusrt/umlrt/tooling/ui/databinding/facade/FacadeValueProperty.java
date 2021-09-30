/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.SimplePropertyEvent;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.requests.UnsetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;

/**
 * A property for a {@link UMLRTNamedElement}'s inheritable singular value feature.
 */
class FacadeValueProperty<S extends UMLRTNamedElement, T> extends SimpleValueProperty<S, T> implements IFacadeProperty {
	private final Class<T> elementType;
	private final List<? extends EStructuralFeature> features;
	private final Function<? super S, ? extends T> accessor;

	FacadeValueProperty(Class<T> elementType, List<? extends EStructuralFeature> features,
			Function<? super S, ? extends T> accessor) {

		super();

		this.elementType = elementType;
		this.features = features;
		this.accessor = accessor;
	}

	@Override
	public Object getValueType() {
		return elementType;
	}

	@Override
	public String getPropertyName() {
		return features.get(0).getName();
	}

	@Override
	protected T doGetValue(S source) {
		return accessor.apply(source);
	}

	T fromModel(Object o) {
		return elementType.cast(o);
	}

	Object toModel(T value) {
		return value;
	}

	@Override
	protected void doSetValue(S source, T value) {
		List<? extends ICommand> commands = getCommands(source, Diffs.createValueDiff(getValue(source), value));
		if (!commands.isEmpty()) {
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(source.toUML());
			CompositeTransactionalCommand command = new CompositeTransactionalCommand(
					domain, "Edit " + getPropertyName(), commands);
			if (command.canExecute()) {
				domain.getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(command.reduce()));
			}
		}
	}

	protected EObject getValueOwner(S source) {
		return source.toUML();
	}

	@Override
	public INativePropertyListener<S> adaptListener(ISimplePropertyListener<S, ValueDiff<? extends T>> listener) {
		class NativeListener extends AdapterImpl implements INativePropertyListener<S> {

			private S source;

			@Override
			public void addTo(S source) {
				EObject listOwner = getValueOwner(source);
				listOwner.eAdapters().add(this);
				this.source = source;
			}

			@Override
			public void removeFrom(S source) {
				if (source != null) {
					EObject listOwner = getValueOwner(source);
					if (listOwner != null) {
						listOwner.eAdapters().remove(this);
					}
					source = null;
				}
			}

			@Override
			public void notifyChanged(Notification msg) {
				ValueDiff<T> diff = null;

				if (features.contains(msg.getFeature())) {
					switch (msg.getEventType()) {
					case Notification.SET:
					case Notification.UNSET:
					case Notification.RESOLVE:
						diff = Diffs.createValueDiff(
								fromModel(msg.getOldValue()),
								fromModel(msg.getNewValue()));
						break;
					}
				}

				if (diff != null) {
					listener.handleEvent(new SimplePropertyEvent<>(SimplePropertyEvent.CHANGE,
							source, FacadeValueProperty.this, diff));
				}
			}

		}

		return new NativeListener();
	}

	protected List<? extends ICommand> getCommands(S source, ValueDiff<T> diff) {
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(source.toUML());
		List<ICommand> result = new ArrayList<>(1);
		Optional<T> newValue = Optional.ofNullable(diff.getNewValue());

		result.add(getSetCommand(domain, source, newValue));

		return result;
	}

	protected ICommand getSetCommand(TransactionalEditingDomain domain, S source, Optional<T> newValue) {
		EObject valueOwner = getValueOwner(source);
		IEditCommandRequest request = newValue
				.<IEditCommandRequest> map(elem -> new SetRequest(domain, valueOwner, features.get(0), toModel(elem)))
				.orElseGet(() -> new UnsetRequest(domain, valueOwner, features.get(0)));
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(valueOwner);
		return edit.getEditCommand(request);
	}

}
