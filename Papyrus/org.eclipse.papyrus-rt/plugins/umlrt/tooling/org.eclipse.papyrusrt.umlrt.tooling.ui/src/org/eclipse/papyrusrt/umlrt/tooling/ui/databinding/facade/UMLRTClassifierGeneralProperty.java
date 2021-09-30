/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.SimplePropertyEvent;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A property for a {@link UMLRTClassifier}'s general (supertype) feature.
 */
class UMLRTClassifierGeneralProperty<S extends UMLRTClassifier> extends FacadeValueProperty<S, S> {

	UMLRTClassifierGeneralProperty(Class<S> elementType, Function<? super S, ? extends S> accessor) {
		super(elementType, Arrays.asList(UMLPackage.Literals.CLASSIFIER__GENERALIZATION, UMLPackage.Literals.GENERALIZATION__GENERAL), accessor);
	}

	@Override
	public String getPropertyName() {
		return (getValueType() == UMLRTCapsule.class) ? "Superclass" : "Supertype";
	}

	@Override
	S fromModel(Object o) {
		return (o == null)
				? null
				: (o instanceof Generalization)
						? fromModel(((Generalization) o).getGeneral())
						: super.fromModel(UMLRTFactory.create((Classifier) o));
	}

	@Override
	Object toModel(S value) {
		return (value == null) ? null : value.toUML();
	}

	@Override
	public INativePropertyListener<S> adaptListener(ISimplePropertyListener<S, ValueDiff<? extends S>> listener) {
		class NativeListener extends AdapterImpl implements INativePropertyListener<S> {

			private S source;

			@Override
			public void addTo(S source) {
				EObject listOwner = getValueOwner(source);
				listOwner.eAdapters().add(this);
				if (listOwner instanceof Classifier) {
					Classifier classifier = (Classifier) listOwner;
					if (!classifier.getGeneralizations().isEmpty()) {
						classifier.getGeneralizations().get(0).eAdapters().add(this);
					}
				}
				this.source = source;
			}

			@Override
			public void removeFrom(S source) {
				if (source != null) {
					EObject listOwner = getValueOwner(source);
					if (listOwner instanceof Classifier) {
						Classifier classifier = (Classifier) listOwner;
						if (!classifier.getGeneralizations().isEmpty()) {
							classifier.getGeneralizations().get(0).eAdapters().remove(this);
						}
					}
					if (listOwner != null) {
						listOwner.eAdapters().remove(this);
					}
					source = null;
				}
			}

			@Override
			public void notifyChanged(Notification msg) {
				ValueDiff<S> diff = null;

				if (msg.getFeature() == UMLPackage.Literals.GENERALIZATION__GENERAL) {
					switch (msg.getEventType()) {
					case Notification.SET:
					case Notification.UNSET:
					case Notification.RESOLVE:
						diff = Diffs.createValueDiff(
								fromModel(msg.getOldValue()),
								fromModel(msg.getNewValue()));
						break;
					}
				} else if (msg.getFeature() == UMLPackage.Literals.CLASSIFIER__GENERALIZATION) {
					switch (msg.getEventType()) {
					case Notification.ADD:
					case Notification.REMOVE:
					case Notification.SET:
					case Notification.RESOLVE:
						if (msg.getPosition() == 0) {
							Generalization oldValue = (Generalization) msg.getOldValue();
							Generalization newValue = (Generalization) msg.getNewValue();

							diff = Diffs.createValueDiff(fromModel(oldValue), fromModel(newValue));

							if (oldValue != null) {
								oldValue.eAdapters().remove(this);
							}
							if (newValue != null) {
								newValue.eAdapters().add(this);
							}
						}

						break;
					}
				}

				if (diff != null) {
					listener.handleEvent(new SimplePropertyEvent<>(SimplePropertyEvent.CHANGE,
							source, UMLRTClassifierGeneralProperty.this, diff));
				}
			}

		}

		return new NativeListener();
	}

	@Override
	protected ICommand getSetCommand(TransactionalEditingDomain domain, S source, Optional<S> newValue) {
		CompositeTransactionalCommand result = new CompositeTransactionalCommand(domain, "Set " + getPropertyName());

		// Is there even a change to be made?
		UMLRTClassifier currentValue = source.getGeneral();
		if ((newValue.isPresent() && (newValue.get() != currentValue))
				|| (!newValue.isPresent() && (currentValue != null))) {

			final IElementEditService edit = ElementEditServiceUtils.getCommandProvider(source.toUML());
			if (edit != null) {
				List<Generalization> generalizations = source.toUML().getGeneralizations();

				if (!newValue.isPresent()) {
					// Is there a current generalization to destroy?
					generalizations.forEach(gen -> {
						result.add(edit.getEditCommand(new DestroyElementRequest(gen, false)));
					});
				} else {
					// Is there a current generalization to update?
					if (!generalizations.isEmpty()) {
						SetRequest request = new SetRequest(domain, generalizations.get(0),
								UMLPackage.Literals.GENERALIZATION__GENERAL,
								newValue.get().toUML());

						// Need an edit service for the generalization
						IElementEditService editGen = ElementEditServiceUtils.getCommandProvider(generalizations.get(0));
						ICommand updateGeneralization = (editGen == null) ? null : edit.getEditCommand(request);
						if (updateGeneralization == null) {
							// We do need to be able to complete this, so introduce a bomb
							updateGeneralization = UnexecutableCommand.INSTANCE;
						}
						result.add(updateGeneralization);
					} else {
						// We have a new generalization to create
						CreateRelationshipRequest request = new CreateRelationshipRequest(
								source.toUML(), newValue.get().toUML(), UMLElementTypes.GENERALIZATION);

						ICommand createGeneralization = edit.getEditCommand(request);
						if (createGeneralization == null) {
							// We do need to be able to complete this, so introduce a bomb
							createGeneralization = UnexecutableCommand.INSTANCE;
						}
						result.add(createGeneralization);
					}
				}
			}
		}

		return result.reduce();
	}

}
