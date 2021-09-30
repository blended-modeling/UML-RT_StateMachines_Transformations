/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Onder GURCAN (CEA LIST) onder.gurcan@cea.fr - Initial API and implementation
 *  Christian W. Damus - bugs 491543, 495908, 479628, 497801, 467545, 495157, 507552, 510315, 507282, 512955
 * 	Young-Soo Roh - bug 483636
 *  
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement;

import static org.eclipse.papyrus.uml.tools.util.MultiplicityParser.ONE;
import static org.eclipse.papyrus.uml.tools.util.MultiplicityParser.OPTIONAL;
import static org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils.isRTMessageParameter;
import static org.eclipse.papyrusrt.umlrt.tooling.ui.Messages.NoTypeForTypedElement_Label;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.emf.utils.HistoryUtil;
import org.eclipse.papyrus.infra.properties.ui.providers.FeatureContentProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFGraphicalContentProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;
import org.eclipse.papyrus.infra.ui.emf.utils.ProviderHelper;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.providers.DelegatingLabelProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.StaticContentProvider;
import org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper;
import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;
import org.eclipse.papyrus.uml.properties.creation.UMLPropertyEditorFactory;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElement;
import org.eclipse.papyrus.uml.tools.databinding.ExtendedMultiplicityObservableValue;
import org.eclipse.papyrus.uml.tools.providers.UMLContainerContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLFilteredLabelProvider;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.internal.defaultlanguage.DefaultLanguageRegistry;
import org.eclipse.papyrusrt.umlrt.core.internal.defaultlanguage.NoDefautLanguage;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.IRealTimeConstants;
import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.system.profile.util.SystemElementsUtils;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.CompositeValidator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.properties.TransitionProperties;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.ChangeListenerUtils;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.MessageParameterTypeReferenceConverter;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.NullReferenceValueFactory;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.properties.RTPropertyEditorFactory;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.providers.TypeNameResolutionHelper;
import org.eclipse.papyrusrt.umlrt.tooling.ui.widgets.CapsulePartTypeValueFactory;
import org.eclipse.papyrusrt.umlrt.tooling.ui.widgets.PropertyReplicationObservableValue;
import org.eclipse.papyrusrt.umlrt.tooling.ui.widgets.ProtocolMessageParameterFactory;
import org.eclipse.papyrusrt.umlrt.tooling.ui.widgets.RTPortTypeValueFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A ModelElement provider for UML-RT. In particular, it will take care of UMLRT protocols which reference provided, required and prov/required interfaces.
 * These can not be specified by means of a property path, since they depend on implemented or used interfaces which are not directly provided.
 * The idea of this class is to delegate to UMLModelElement belonging to these interfaces
 */
public class UMLRTExtModelElement extends UMLModelElement {

	private static final String TRIGGER = "trigger"; //$NON-NLS-1$

	private static final String GUARD = "guard"; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public UMLRTExtModelElement(EObject source) {
		this(source, TransactionUtil.getEditingDomain(source));
	}

	/**
	 * Constructor.
	 */
	public UMLRTExtModelElement(EObject source, EditingDomain domain) {
		super(source, domain);
	}

	public class UMLRTExtModelElementChangeListener implements IChangeListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void handleChange(ChangeEvent event) {
			try {
				ChangeListenerUtils.fireDataSourceChanged(dataSource);
			} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | RuntimeException e) {
				Activator.log.debug(e.getMessage());
			}
		}
	}

	@Override
	public IStaticContentProvider getContentProvider(String propertyPath) {
		if (propertyPath.contains(IRealTimeConstants.CAPSULE_PART_MULTIPLICITY)) {
			return new StaticContentProvider(new String[] { ONE, OPTIONAL });
		}
		if (propertyPath.contains(IRealTimeConstants.PACKAGE_LANGUAGE)) {
			// compute all available languages
			List availableLanguages = new ArrayList<>(DefaultLanguageRegistry.getInstance().getLanguages());
			Collections.addAll(availableLanguages, (IDefaultLanguage) NoDefautLanguage.INSTANCE);
			return new StaticContentProvider(availableLanguages.toArray());
		}
		if (propertyPath.contains(IRealTimeConstants.PROPERTY_REPLICATION)) {

			return new StaticContentProvider(new String[] { "None (1)", "1", "*" });

		}

		return super.getContentProvider(propertyPath);
	}

	@Override
	public boolean isMandatory(String propertyPath) {
		if (propertyPath.contains(IRealTimeConstants.PACKAGE_LANGUAGE)) {
			return true;
		}
		// to not display the unset button for the type widget for CapsulePart, RTPort,
		// and message parameter
		if (getFeature(propertyPath) == UMLPackage.Literals.TYPED_ELEMENT__TYPE) {
			if (((source instanceof Property) && CapsulePartUtils.isCapsulePart((Property) source))
					|| RTPortUtils.isRTPort(source)
					|| isRTMessageParameter(source)) {
				return true;
			}
		}
		return super.isMandatory(propertyPath);
	}

	@Override
	public ILabelProvider getLabelProvider(String propertyPath) {
		if (propertyPath.endsWith(IRealTimeConstants.PACKAGE_LANGUAGE)) {
			return new LabelProvider() {
				/**
				 * {@inheritDoc}
				 */
				@Override
				public String getText(Object element) {
					if (element instanceof IDefaultLanguage) {
						return ((IDefaultLanguage) element).getName();
					}
					return super.getText(element);
				}
			};
		}

		EStructuralFeature feature = getFeature(propertyPath);
		if (feature == UMLPackage.Literals.TYPED_ELEMENT__TYPE) {
			if (isRTMessageParameter(source)) {
				// Special handling for the null value, represented as '*'
				ILabelProvider delegate = super.getLabelProvider(propertyPath);
				return new DelegatingLabelProvider(delegate) {

					@Override
					protected String customGetText(Object element) {
						return (element == null) ? NoTypeForTypedElement_Label : null;
					}

				};
			}
		}

		return super.getLabelProvider(propertyPath);
	}

	@Override
	public IObservable doGetObservable(String propertyPath) {
		IObservable observable = null;

		switch (propertyPath) {
		case IRealTimeConstants.CAPSULE_PART_MULTIPLICITY:
			observable = new ExtendedMultiplicityObservableValue(source, domain);
			break;
		case IRealTimeConstants.PACKAGE_LANGUAGE:
			observable = new RTPackageObservableValue((Package) source, (TransactionalEditingDomain) domain);
			break;
		case IRealTimeConstants.PROPERTY_REPLICATION:
			observable = new PropertyReplicationObservableValue(source, (TransactionalEditingDomain) domain);
			break;
		case TRIGGER:
			if (source instanceof Transition) {
				observable = TransitionProperties.triggers().observe((Transition) source);
			} else {
				observable = super.doGetObservable(propertyPath);
			}
			break;
		default:
			observable = super.doGetObservable(propertyPath);
			break;
		}

		if (getSource() instanceof RTPort) {
			observable.addChangeListener(new UMLRTExtModelElementChangeListener());
		}

		return observable;
	}

	@Override
	public ReferenceValueFactory getValueFactory(String propertyPath) {
		ReferenceValueFactory result = null;
		EReference reference = null;
		EClass type = null;

		if (propertyPath.equals("type")) {
			reference = UMLPackage.Literals.TYPED_ELEMENT__TYPE;
			if (source instanceof Port) {
				result = new RTPortTypeValueFactory(reference);
			} else if (source instanceof Property) {
				result = new CapsulePartTypeValueFactory(reference);
			} else if (isRTMessageParameter(source)) {
				// Don't support the plus and pencil buttons because
				// the ellipsis does all the work
				result = NullReferenceValueFactory.INSTANCE;
			}
		} else if (TRIGGER.equals(propertyPath) && (source instanceof Transition)) {
			reference = UMLPackage.Literals.TRANSITION__TRIGGER;
			result = new RTPropertyEditorFactory<>(reference);
		} else if (GUARD.equals(propertyPath) && (source instanceof Transition)) {
			reference = UMLPackage.Literals.TRANSITION__GUARD;
			result = new RTPropertyEditorFactory<Constraint>(reference);
		} else {
			EStructuralFeature feature = getFeature(propertyPath);

			if ((feature == UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER)
					&& RTMessageUtils.isRTMessage(getSource())) {
				reference = (EReference) feature;
				result = new ProtocolMessageParameterFactory();
			}
		}

		if (result instanceof UMLPropertyEditorFactory) {
			UMLPropertyEditorFactory factory = (UMLPropertyEditorFactory) result;
			type = reference.getEReferenceType();
			factory.setContainerLabelProvider(new UMLFilteredLabelProvider());
			factory.setReferenceLabelProvider(new EMFLabelProvider());
			ITreeContentProvider contentProvider = new UMLContainerContentProvider(source, reference);

			ResourceSet rs = source == null ? null : source.eResource() == null ? null : source.eResource().getResourceSet();
			EMFGraphicalContentProvider provider = ProviderHelper.encapsulateProvider(contentProvider, rs, HistoryUtil.getHistoryID(source, reference, "container"));

			factory.setContainerContentProvider(provider);
			factory.setReferenceContentProvider(new FeatureContentProvider(type));
		}

		return (result != null) ? result : super.getValueFactory(propertyPath);
	}

	@Override
	public boolean forceRefresh(String propertyPath) {
		return true;
	}

	@Override
	protected boolean isFeatureEditable(String propertyPath) {
		boolean editable = false;
		EStructuralFeature feature = getFeature(propertyPath);

		if (source instanceof Transition) {
			return true;
		} else if (source instanceof Port) {
			UMLRTPort port = UMLRTPort.getInstance((Port) source);
			if (port == null) {
				// Cannot edit a non-RT port with this model element
				return false;
			}

			UMLRTProtocol protocol = port.getType();
			boolean isSystemProtocol = (protocol != null)
					&& SystemElementsUtils.isSystemProtocol(protocol.toUML());

			if (IRealTimeConstants.PROPERTY_REPLICATION.equals(propertyPath)) {
				editable = true;
			} else if (feature == UMLPackage.Literals.PORT__IS_CONJUGATED) {
				if (isSystemProtocol || port.isInherited()) {
					// disable if typed with system protocol Bug#483636.
					// Cannot redefine conjugation
					editable = false;
				} else {
					editable = super.isFeatureEditable(propertyPath);
				}
			} else if (IRealTimeConstants.PROTOCOL_TYPE.equals(propertyPath)) {
				editable = super.isFeatureEditable(propertyPath);
			} else if (feature == UMLPackage.Literals.PORT__IS_SERVICE) {
				if (isSystemProtocol || port.isInherited()) {
					// disable if typed with system protocol Bug#483636.
					// Cannot redefine isService
					editable = false;
				} else {
					editable = !port.isConnected() && port.isBehavior();
				}
			} else if (feature == UMLPackage.Literals.PORT__IS_BEHAVIOR) {
				if (isSystemProtocol) {
					// disable if typed with system protocol Bug#483636
					editable = false;
				} else {
					// isBehavior disabled if !iswired || (isWired && !isService) || (isWired && isService && !isBehavior && isConnectedInside)
					editable = !(!port.isWired()
							|| (port.isWired() && !port.isService())
							|| (port.isWired() && port.isService() && !port.isBehavior() && port.isConnectedInside()));
				}
			} else {
				editable = super.isFeatureEditable(propertyPath);
			}
		}

		// Rules defined by the Client
		else if (source instanceof Property) {
			UMLRTCapsulePart capsulePart = UMLRTCapsulePart.getInstance((Property) source);
			// The only exceptions for capsule-parts are name and certain values of
			// the part-kind for inherited parts, which are handled elsewhere
			editable = (capsulePart != null) || super.isFeatureEditable(propertyPath);
		} else if (source instanceof Parameter) {
			if (feature == UMLPackage.Literals.NAMED_ELEMENT__NAME) {
				editable = !UMLRTExtensionUtil.isInherited((Parameter) source);
			} else {
				editable = super.isFeatureEditable(propertyPath);
			}
		} else if (source instanceof Package) {
			if (propertyPath.endsWith(IRealTimeConstants.PACKAGE_LANGUAGE)) {
				editable = true;
			} else {
				editable = super.isFeatureEditable(propertyPath);
			}
		} else if (source instanceof Operation) {
			editable = super.isFeatureEditable(propertyPath);
		}

		return editable;
	}

	@Override
	public IValidator getValidator(String propertyPath) {
		// Include inherited validator(s), also
		IValidator result = super.getValidator(propertyPath);

		if (IRealTimeConstants.PROPERTY_REPLICATION.equals(propertyPath)) {
			// the type is String, but we like to prohibit :
			// 1) strings with ".." to not specify a multiplicity in the replication text
			// 2) negative number : -2 is not allowed but -2a is OK
			result = CompositeValidator.of(result,
					new IValidator() {

						@Override
						public IStatus validate(Object value) {


							if (((String) value).matches("-\\d*.?\\d+|\\d*\\.\\d+")) {
								return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Only positive Integers are allowed");
							} else if (((String) value).contains("..")) {
								return new Status(IStatus.ERROR, Activator.PLUGIN_ID, ".. notation is not allowed");
							}
							return Status.OK_STATUS;

						}

					});
		}

		return result;
	}

	@Override
	public IPapyrusConverter getPapyrusConverter(String propertyPath) {
		IPapyrusConverter result;

		EStructuralFeature feature = getFeature(propertyPath);
		if (feature == UMLPackage.Literals.TYPED_ELEMENT__TYPE) {
			if (isRTMessageParameter(source)) {
				// Special handling for the null value, represented as '*'
				INameResolutionHelper helper = getNameResolutionHelper(propertyPath);
				result = new MessageParameterTypeReferenceConverter(helper);
			} else {
				result = super.getPapyrusConverter(propertyPath);
			}
		} else {
			result = super.getPapyrusConverter(propertyPath);
		}

		return result;
	}

	@Override
	public INameResolutionHelper getNameResolutionHelper(String propertyPath) {
		INameResolutionHelper result;

		EStructuralFeature feature = getFeature(propertyPath);
		if (feature == UMLPackage.Literals.TYPED_ELEMENT__TYPE) {
			result = new TypeNameResolutionHelper((TypedElement) source);
		} else {
			result = super.getNameResolutionHelper(propertyPath);
		}

		return result;
	}
}
