/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement;

import java.util.function.Predicate;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.papyrus.infra.emf.utils.HistoryUtil;
import org.eclipse.papyrus.infra.properties.ui.providers.FeatureContentProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFGraphicalContentProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;
import org.eclipse.papyrus.infra.ui.emf.utils.ProviderHelper;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElement;
import org.eclipse.papyrus.uml.tools.databinding.NamedElementValidator;
import org.eclipse.papyrus.uml.tools.providers.UMLContainerContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLFilteredLabelProvider;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.CompositeValidator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.facade.CapsuleProperties;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.facade.NamedElementProperties;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.facade.ProtocolProperties;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.properties.FacadeListPropertyEditorFactory;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.properties.UMLRTCapsulePartEditorFactory;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.properties.UMLRTPortEditorFactory;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.properties.UMLRTProtocolMessageEditorFactory;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.providers.FacadeContentProvider;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTSwitch;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A model-element that uses the UML-RT Façade API to access and present properties.
 */
public class UMLRTFacadeModelElement extends UMLModelElement {

	private static final String NAME = "name"; //$NON-NLS-1$

	private static final String PORT = "port"; //$NON-NLS-1$

	private static final String CAPSULE_PART = "capsulePart"; //$NON-NLS-1$

	private static final String INCOMING = "Incoming"; //$NON-NLS-1$

	private static final String OUTGOING = "Outgoing"; //$NON-NLS-1$

	private static final String IN_OUT = "InOut"; //$NON-NLS-1$

	private static final String SUPERCLASS = "superclass"; //$NON-NLS-1$

	private static final String SUPERTYPE = "supertype"; //$NON-NLS-1$

	UMLRTNamedElement element;

	/**
	 * Initializes me with my {@code source} element.
	 * 
	 * @param source
	 *            the source façade element
	 */
	public UMLRTFacadeModelElement(UMLRTNamedElement source) {
		super(source.toUML(), TransactionUtil.getEditingDomain(source.toUML()));

		this.element = source;
	}

	@Override
	public void dispose() {
		super.dispose();

		element = null;
	}

	@Override
	public boolean isOrdered(String propertyPath) {
		switch (propertyPath) {
		case PORT:
			// The EncapsulatedClassifier::ownedPort is an unordered property, but we
			// really edit its superset StructuredClassifier::ownedAttribute, which
			// is an ordered property
			return true;
		}

		return super.isOrdered(propertyPath);
	}

	@Override
	public EStructuralFeature getFeature(String propertyPath) {

		return new UMLRTSwitch<EStructuralFeature>() {

			@Override
			public EStructuralFeature caseCapsule(UMLRTCapsule object) {
				switch (propertyPath) {
				case PORT:
					return UMLPackage.Literals.ENCAPSULATED_CLASSIFIER__OWNED_PORT;
				case CAPSULE_PART:
					return UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE;
				case SUPERCLASS:
					return UMLPackage.Literals.CLASSIFIER__GENERAL;
				default:
					return null;
				}
			}

			@Override
			public EStructuralFeature caseProtocol(UMLRTProtocol object) {
				switch (propertyPath) {
				case INCOMING:
				case IN_OUT:
				case OUTGOING:
					return UMLPackage.Literals.INTERFACE__OWNED_OPERATION;
				case SUPERTYPE:
					return UMLPackage.Literals.CLASSIFIER__GENERAL;
				default:
					return null;
				}
			}

			@Override
			public EStructuralFeature defaultCase(Object object) {
				return UMLRTFacadeModelElement.super.getFeature(propertyPath);
			}

		}.doSwitch(element);
	}

	@Override
	public IObservable doGetObservable(String propertyPath) {
		return new UMLRTSwitch<IObservable>() {

			@Override
			public IObservable caseCapsule(UMLRTCapsule object) {
				switch (propertyPath) {
				case PORT:
					return CapsuleProperties.ports().observe(object);
				case CAPSULE_PART:
					return CapsuleProperties.capsuleParts().observe(object);
				case SUPERCLASS:
					return CapsuleProperties.superclass().observe(object);
				default:
					return null;
				}
			}

			@Override
			public IObservable caseProtocol(UMLRTProtocol object) {
				switch (propertyPath) {
				case INCOMING:
				case IN_OUT:
				case OUTGOING:
					RTMessageKind msgKind = getMessageKind(propertyPath);
					return (msgKind != null) ? ProtocolProperties.messages(msgKind).observe(object) : null;
				case SUPERTYPE:
					return ProtocolProperties.supertype().observe(object);
				default:
					return null;
				}
			}

			@Override
			public IObservable caseNamedElement(UMLRTNamedElement object) {
				switch (propertyPath) {
				case NAME:
					return NamedElementProperties.name().observe(object);
				default:
					return null;
				}
			}

		}.doSwitch(element);
	}

	@Override
	public ReferenceValueFactory getValueFactory(String propertyPath) {
		return new UMLRTSwitch<ReferenceValueFactory>() {

			@Override
			public ReferenceValueFactory caseCapsule(UMLRTCapsule object) {
				ReferenceValueFactory result = null;
				EReference reference;

				switch (propertyPath) {
				case PORT:
					@SuppressWarnings("unchecked")
					IObservableList<UMLRTPort> ports = (IObservableList<UMLRTPort>) getObservable(propertyPath);
					reference = UMLPackage.Literals.ENCAPSULATED_CLASSIFIER__OWNED_PORT;
					result = complete(new UMLRTPortEditorFactory(reference, ports), reference);
					break;
				case CAPSULE_PART:
					@SuppressWarnings("unchecked")
					IObservableList<UMLRTCapsulePart> parts = (IObservableList<UMLRTCapsulePart>) getObservable(propertyPath);
					reference = UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE;
					result = complete(new UMLRTCapsulePartEditorFactory(reference, parts), reference);
					break;
				}

				return result;
			}

			@Override
			public ReferenceValueFactory caseProtocol(UMLRTProtocol object) {
				ReferenceValueFactory result = null;

				RTMessageKind msgKind = getMessageKind(propertyPath);
				if (msgKind != null) {
					@SuppressWarnings("unchecked")
					IObservableList<UMLRTProtocolMessage> messages = (IObservableList<UMLRTProtocolMessage>) getObservable(propertyPath);
					EReference reference = UMLPackage.Literals.INTERFACE__OWNED_OPERATION;
					result = complete(new UMLRTProtocolMessageEditorFactory(reference, msgKind, messages), reference);
				}

				return result;
			}

			private <E extends UMLRTNamedElement> FacadeListPropertyEditorFactory<E> complete(FacadeListPropertyEditorFactory<E> factory, EReference reference) {
				EClass type = reference.getEReferenceType();
				factory.setContainerLabelProvider(new UMLFilteredLabelProvider());
				factory.setReferenceLabelProvider(new EMFLabelProvider());
				ITreeContentProvider contentProvider = new UMLContainerContentProvider(source, reference);

				ResourceSet rs = source == null ? null : source.eResource() == null ? null : source.eResource().getResourceSet();
				EMFGraphicalContentProvider provider = ProviderHelper.encapsulateProvider(contentProvider, rs, HistoryUtil.getHistoryID(source, reference, "container"));

				factory.setContainerContentProvider(provider);
				factory.setReferenceContentProvider(new FeatureContentProvider(type));

				return factory;
			}

		}.doSwitch(element);
	}

	private RTMessageKind getMessageKind(String propertyPath) {
		RTMessageKind result = null;
		if (source instanceof Collaboration) {
			if (propertyPath.endsWith(INCOMING)) { // $NON-NLS-1$
				result = RTMessageKind.IN;
			} else if (propertyPath.endsWith(OUTGOING)) { // $NON-NLS-1$
				result = RTMessageKind.OUT;
			} else if (propertyPath.endsWith(IN_OUT)) { // $NON-NLS-1$
				result = RTMessageKind.IN_OUT;
			}
		}
		return result;
	}

	@Override
	protected boolean isFeatureEditable(String propertyPath) {
		boolean result = true;

		switch (propertyPath) {
		case NAME:
			// Cannot redefine the name of an inherited element
			result = !element.isInherited();
			break;
		}

		return result;
	}

	@Override
	public IStaticContentProvider getContentProvider(String propertyPath) {
		return new UMLRTSwitch<IStaticContentProvider>() {

			@Override
			public IStaticContentProvider caseCapsule(UMLRTCapsule object) {
				switch (propertyPath) {
				case SUPERCLASS:
					Predicate<UMLRTNamedElement> isCapsule = UMLRTCapsule.class::isInstance;
					Predicate<UMLRTNamedElement> isSource = element::equals;

					return new FacadeContentProvider(object, UMLPackage.Literals.CLASSIFIER__GENERAL,
							isCapsule.and(isSource.negate()));
				default:
					return null;
				}
			}

			@Override
			public IStaticContentProvider caseProtocol(UMLRTProtocol object) {
				switch (propertyPath) {
				case SUPERTYPE:
					Predicate<UMLRTNamedElement> isProtocol = UMLRTProtocol.class::isInstance;
					Predicate<UMLRTNamedElement> isSource = element::equals;

					return new FacadeContentProvider(object, UMLPackage.Literals.CLASSIFIER__GENERAL,
							isProtocol.and(isSource.negate()));
				default:
					return null;
				}
			}

			@Override
			public IStaticContentProvider defaultCase(Object object) {
				return UMLRTFacadeModelElement.super.getContentProvider(propertyPath);
			}

		}.doSwitch(element);
	}

	@Override
	public boolean getDirectCreation(String propertyPath) {
		boolean result;

		switch (propertyPath) {
		case PORT:
			// EncapsulatedClassifier::ownedPort isn't EMF-ishly a containment, but it subsets a containment
			result = true;
			break;
		default:
			result = super.getDirectCreation(propertyPath);
			break;
		}

		return result;
	}

	@Override
	public IValidator getValidator(String propertyPath) {
		// Include inherited validator(s), also
		IValidator result = super.getValidator(propertyPath);

		if (propertyPath.equals(NAME)) {
			IValidator specific = new UMLRTSwitch<IValidator>() {

				@Override
				public IValidator caseClassifier(UMLRTClassifier object) {
					// Capsules and protocols must be named
					return value -> {
						boolean valid = (value instanceof String)
								&& !((String) value).trim().isEmpty();

						return valid
								? Status.OK_STATUS
								: new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Name is required");
					};
				}


				@Override
				public IValidator caseProtocol(UMLRTProtocol object) {
					// Custom distinguishability rule for protocols: their names imply their container
					// package names, so check the container package
					IValidator packageValidator = new NamedElementValidator(ProtocolUtils.getProtocolContainer(object.toUML()));
					return CompositeValidator.of(caseClassifier(object), value -> {
						return packageValidator.validate(value);
					});
				}

			}.doSwitch(element);

			result = CompositeValidator.of(result, specific);
		}

		return result;
	}
}
