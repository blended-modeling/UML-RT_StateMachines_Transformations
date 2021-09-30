/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 		Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 * 		Christian W. Damus - bugs 492408, 467545, 510188
 * 		Young-Soo Roh - bug 483636
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.StaticContentProvider;
import org.eclipse.papyrus.uml.properties.modelelement.StereotypeModelElement;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.IRealTimeConstants;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.system.profile.util.SystemElementsUtils;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.CompositeValidator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.ChangeListenerUtils;
import org.eclipse.papyrusrt.umlrt.tooling.ui.widgets.CapsulePartKindObservableValue;
import org.eclipse.papyrusrt.umlrt.tooling.ui.widgets.PortRTKindObservableValue;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.provider.UMLRTEditPlugin;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Model Element for Real Time Stereotype properties.
 * Used for RTPort properties like "isWired", "isPublish", "isNotification"
 * Used for CapsulePart
 * 
 * @author Céline JANSSENS
 *
 */
public class RTStereotypeModelElement extends StereotypeModelElement {

	public RTStereotypeModelElement(final EObject stereoApplication, final Stereotype stereotype, final EditingDomain domain) {
		super(stereoApplication, stereotype, domain);
	}

	@Override
	protected boolean isFeatureEditable(final String propertyPath) {
		boolean editable = true;
		EStructuralFeature feature = getFeature(propertyPath);

		EObject source = getSource();
		if (source instanceof RTPort) {
			RTPort rtPort = (RTPort) source;
			UMLRTPort port = UMLRTPort.getInstance(rtPort.getBase_Port());
			if (null != port) {
				UMLRTProtocol protocol = port.getType();
				if ((protocol != null) && SystemElementsUtils.isSystemProtocol(protocol.toUML())) {
					// disable all RT properties if typed with system protocol Bug#483636
					editable = false;
				} else if (IRealTimeConstants.KIND.equals(propertyPath)) {
					editable = true;
				} else if (feature == UMLRealTimePackage.Literals.RT_PORT__IS_NOTIFICATION) {
					editable = port.isBehavior(); // isNotification disabled if !isBehavior
				} else if (feature == UMLRealTimePackage.Literals.RT_PORT__IS_WIRED) {
					editable = !port.isInherited(); // Cannot re-define isWired
					editable = editable && port.isBehavior() && !port.isConnected() // isWired disabled if connected || !isBehavior || isLegacySpp || isLegacySap
							&& !RTPortUtils.isLegacySpp(port.toUML()) && !RTPortUtils.isLegacySap(port.toUML());
				} else if (feature == UMLRealTimePackage.Literals.RT_PORT__IS_PUBLISH) {
					editable = false; // isPublish always disabled
				} else if (feature == UMLRealTimePackage.Literals.RT_PORT__REGISTRATION) {
					editable = !rtPort.isWired() && !port.isInherited(); // registration kind disabled if isWired or redefinition
				} else if (feature == UMLRealTimePackage.Literals.RT_PORT__REGISTRATION_OVERRIDE) {
					editable = !rtPort.isWired() && !port.isInherited(); // registration override disabled if isWired or redefinition
				} else {
					editable = super.isFeatureEditable(propertyPath);
				}
			}
		} else if (source instanceof CapsulePart) {
			editable = true;
		} else {
			editable = super.isFeatureEditable(propertyPath);
		}
		return editable;

	}


	@Override
	public IObservable doGetObservable(final String propertyPath) {
		IObservable observe = null;

		// Case of Kind value in the Port RT Property View
		if (IRealTimeConstants.KIND.equals(propertyPath)) {
			EObject source = getSource();
			if (source instanceof RTPort) {
				observe = new PortRTKindObservableValue(source, (TransactionalEditingDomain) domain);
			} else if (source instanceof CapsulePart) {
				observe = new CapsulePartKindObservableValue(source, (TransactionalEditingDomain) domain);
			}

		} else {
			observe = super.doGetObservable(propertyPath);
		}
		if (getSource() instanceof RTPort) {
			observe.addChangeListener(new RTStereotypeModelElementChangeListener());
		}
		return observe;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IStaticContentProvider getContentProvider(String propertyPath) {
		IStaticContentProvider provider = null;
		EObject source = getSource();
		if (IRealTimeConstants.KIND.equals(propertyPath) && source instanceof RTPort) {
			List<UMLRTPortKind> validKinds = new ArrayList<>(UMLRTPortKind.VALUES);
			validKinds.remove(UMLRTPortKind.NULL);
			provider = new StaticContentProvider(validKinds.toArray());
		} else if (IRealTimeConstants.KIND.equals(propertyPath) && source instanceof CapsulePart) {
			List<UMLRTCapsulePartKind> validKinds = new ArrayList<>(UMLRTCapsulePartKind.VALUES);
			validKinds.remove(UMLRTCapsulePartKind.NULL);
			provider = new StaticContentProvider(validKinds.toArray());
		} else {
			provider = super.getContentProvider(propertyPath);
		}
		return provider;
	}

	@Override
	public IValidator getValidator(String propertyPath) {
		// Include inherited validator(s), also
		IValidator result = super.getValidator(propertyPath);

		if (IRealTimeConstants.KIND.equals(propertyPath)) {
			result = CompositeValidator.of(result, enumValue -> {
				if ((source instanceof RTPort) && (enumValue instanceof UMLRTPortKind)) {
					if (RTPortUtils.isKindEditable(((RTPort) source).getBase_Port(), (UMLRTPortKind) enumValue)) {
						return Status.OK_STATUS;
					}
				} else if ((source instanceof CapsulePart) && (enumValue instanceof UMLRTCapsulePartKind)) {
					if (CapsulePartUtils.isKindEditable(((CapsulePart) source).getBase_Property(), (UMLRTCapsulePartKind) enumValue)) {
						return Status.OK_STATUS;
					}
				}

				return Status.CANCEL_STATUS;
			});
		}

		return result;
	}

	@SuppressWarnings("restriction")
	@Override
	public ILabelProvider getLabelProvider(String propertyPath) {
		switch (propertyPath) {
		case "registration": //$NON-NLS-1$
			if (source instanceof RTPort) {
				return new EnumLabelProvider(UMLRealTimePackage.Literals.PORT_REGISTRATION_TYPE);
			}
			break;
		case IRealTimeConstants.KIND:
			EEnum enumType;

			if (source instanceof RTPort) {
				enumType = org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage.Literals.UMLRT_PORT_KIND;
			} else if (source instanceof CapsulePart) {
				enumType = org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage.Literals.UMLRT_CAPSULE_PART_KIND;
			} else {
				enumType = null;
			}

			if (enumType != null) {
				return new EnumLabelProvider(enumType);
			}
			break;
		}
		return super.getLabelProvider(propertyPath);
	}

	@Override
	public boolean forceRefresh(String propertyPath) {
		return true;
	}

	/**
	 * Queries whether my data-source encapsulates an object that is not
	 * deleted.
	 * 
	 * @return whether the model element or diagram view that I encapsulate is still viable
	 */
	boolean isViable() {
		boolean result;

		Object selected = dataSource != null ? dataSource.getSelection().getFirstElement() : null;
		if (selected == null) {
			// Obvious case
			result = false;
		} else {
			// If it's an edit-part, it's not viable if its view is detached
			View view = PlatformHelper.getAdapter(selected, View.class);
			result = (view == null) || (view.eResource() != null);

			if (result) {
				// Not an edit-part or attached. Check the semantic element
				EObject source = getSource();
				result = (source != null) && (source.eResource() != null);
			}
		}

		return result;
	}

	public class RTStereotypeModelElementChangeListener implements IChangeListener {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void handleChange(ChangeEvent event) {
			// Don't notify for a UML element or notation view that was deleted
			if (isViable()) {
				try {
					ChangeListenerUtils.fireDataSourceChanged(dataSource);
				} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | RuntimeException e) {
					Activator.log.debug(e.getMessage());
				}
			}
		}
	}

	protected static class EnumLabelProvider extends LabelProvider {
		private final EEnum enumType;

		public EnumLabelProvider(EEnum enumType) {
			super();

			this.enumType = enumType;
		}

		@Override
		public String getText(Object element) {
			if (element instanceof Enumerator) {
				Enumerator literal = (Enumerator) element;
				String key = String.format("_UI_%s_%s_literal", enumType.getName(), literal.getName()); //$NON-NLS-1$
				return UMLRTEditPlugin.INSTANCE.getString(key);
			}

			return super.getText(element);
		}
	}

}
