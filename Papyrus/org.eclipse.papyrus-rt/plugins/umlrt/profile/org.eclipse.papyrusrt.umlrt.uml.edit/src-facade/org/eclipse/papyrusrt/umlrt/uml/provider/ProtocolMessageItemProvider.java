/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class ProtocolMessageItemProvider extends NamedElementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ProtocolMessageItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addKindPropertyDescriptor(object);
			addRedefinedMessagePropertyDescriptor(object);
			addParameterPropertyDescriptor(object);
			addIsConjugatePropertyDescriptor(object);
			addConjugatePropertyDescriptor(object);
			addProtocolPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Kind feature.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addKindPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ProtocolMessage_kind_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ProtocolMessage_kind_feature", "_UI_ProtocolMessage_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE__KIND,
				false,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Redefined Message feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addRedefinedMessagePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ProtocolMessage_redefinedMessage_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ProtocolMessage_redefinedMessage_feature", "_UI_ProtocolMessage_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE__REDEFINED_MESSAGE,
				false,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Parameter feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addParameterPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ProtocolMessage_parameter_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ProtocolMessage_parameter_feature", "_UI_ProtocolMessage_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE__PARAMETER,
				false,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Is Conjugate feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addIsConjugatePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ProtocolMessage_isConjugate_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ProtocolMessage_isConjugate_feature", "_UI_ProtocolMessage_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE__IS_CONJUGATE,
				false,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Conjugate feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addConjugatePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ProtocolMessage_conjugate_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ProtocolMessage_conjugate_feature", "_UI_ProtocolMessage_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE__CONJUGATE,
				false,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Protocol feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addProtocolPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ProtocolMessage_protocol_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ProtocolMessage_protocol_feature", "_UI_ProtocolMessage_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE__PROTOCOL,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This returns ProtocolMessage.gif.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		String key = "full/obj16/ProtocolMessage"; //$NON-NLS-1$
		RTMessageKind kind = ((UMLRTProtocolMessage) object).getKind();

		if (kind != null) {
			key = String.format("%s_%s", key, kind.getLiteral());
		}

		return overlayImage(object, getResourceLocator().getImage(key));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return ((StyledString) getStyledText(object)).getString();
	}

	/**
	 * This returns the label styled text for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getStyledText(Object object) {
		String label = ((UMLRTProtocolMessage) object).getName();
		StyledString styledLabel = new StyledString();
		if (label == null || label.length() == 0) {
			styledLabel.append(getString("_UI_ProtocolMessage_type"), StyledString.Style.QUALIFIER_STYLER); //$NON-NLS-1$
		} else {
			styledLabel.append(getString("_UI_ProtocolMessage_type"), StyledString.Style.QUALIFIER_STYLER).append(" " + label); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return styledLabel;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(UMLRTProtocolMessage.class)) {
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__KIND:
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__IS_CONJUGATE:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
