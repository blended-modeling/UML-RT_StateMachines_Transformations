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
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort} object.
 * <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 *
 * @generated
 */
public class PortItemProvider extends ReplicatedElementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public PortItemProvider(AdapterFactory adapterFactory) {
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
			addRedefinedPortPropertyDescriptor(object);
			addTypePropertyDescriptor(object);
			addPartsWithPortPropertyDescriptor(object);
			addServicePropertyDescriptor(object);
			addBehaviorPropertyDescriptor(object);
			addConjugatedPropertyDescriptor(object);
			addWiredPropertyDescriptor(object);
			addPublishPropertyDescriptor(object);
			addNotificationPropertyDescriptor(object);
			addRegistrationPropertyDescriptor(object);
			addRegistrationOverridePropertyDescriptor(object);
			addIsConnectedPropertyDescriptor(object);
			addIsConnectedInsidePropertyDescriptor(object);
			addIsConnectedOutsidePropertyDescriptor(object);
			addConnectorPropertyDescriptor(object);
			addInsideConnectorPropertyDescriptor(object);
			addOutsideConnectorPropertyDescriptor(object);
			addCapsulePropertyDescriptor(object);
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
				getString("_UI_Port_kind_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_kind_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__KIND,
				false,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Redefined Port feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addRedefinedPortPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_redefinedPort_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_redefinedPort_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__REDEFINED_PORT,
				false,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Type feature.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_type_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_type_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__TYPE,
				false,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Parts With Port feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addPartsWithPortPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_partsWithPort_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_partsWithPort_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__PARTS_WITH_PORT,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Service feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addServicePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_service_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_service_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__SERVICE,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Behavior feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addBehaviorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_behavior_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_behavior_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__BEHAVIOR,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Conjugated feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addConjugatedPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_conjugated_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_conjugated_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__CONJUGATED,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Wired feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addWiredPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_wired_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_wired_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__WIRED,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Publish feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addPublishPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_publish_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_publish_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__PUBLISH,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Notification feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addNotificationPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_notification_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_notification_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__NOTIFICATION,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Registration feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addRegistrationPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_registration_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_registration_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__REGISTRATION,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Registration Override feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addRegistrationOverridePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_registrationOverride_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_registrationOverride_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__REGISTRATION_OVERRIDE,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Is Connected feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addIsConnectedPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_isConnected_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_isConnected_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__IS_CONNECTED,
				false,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Is Connected Inside feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addIsConnectedInsidePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_isConnectedInside_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_isConnectedInside_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__IS_CONNECTED_INSIDE,
				false,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Is Connected Outside feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addIsConnectedOutsidePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_isConnectedOutside_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_isConnectedOutside_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__IS_CONNECTED_OUTSIDE,
				false,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Connector feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addConnectorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_connector_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_connector_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__CONNECTOR,
				false,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Inside Connector feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addInsideConnectorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_insideConnector_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_insideConnector_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__INSIDE_CONNECTOR,
				false,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Outside Connector feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addOutsideConnectorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_outsideConnector_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_outsideConnector_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__OUTSIDE_CONNECTOR,
				false,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Capsule feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addCapsulePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Port_capsule_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Port_capsule_feature", "_UI_Port_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.PORT__CAPSULE,
				false,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This returns Port.gif.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Port")); //$NON-NLS-1$
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
		String label = ((UMLRTPort) object).getName();
		StyledString styledLabel = new StyledString();
		if (label == null || label.length() == 0) {
			styledLabel.append(getString("_UI_Port_type"), StyledString.Style.QUALIFIER_STYLER); //$NON-NLS-1$
		} else {
			styledLabel.append(getString("_UI_Port_type"), StyledString.Style.QUALIFIER_STYLER).append(" " + label); //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(UMLRTPort.class)) {
		case UMLRTUMLRTPackage.PORT__KIND:
		case UMLRTUMLRTPackage.PORT__SERVICE:
		case UMLRTUMLRTPackage.PORT__BEHAVIOR:
		case UMLRTUMLRTPackage.PORT__CONJUGATED:
		case UMLRTUMLRTPackage.PORT__WIRED:
		case UMLRTUMLRTPackage.PORT__PUBLISH:
		case UMLRTUMLRTPackage.PORT__NOTIFICATION:
		case UMLRTUMLRTPackage.PORT__REGISTRATION:
		case UMLRTUMLRTPackage.PORT__REGISTRATION_OVERRIDE:
		case UMLRTUMLRTPackage.PORT__IS_CONNECTED:
		case UMLRTUMLRTPackage.PORT__IS_CONNECTED_INSIDE:
		case UMLRTUMLRTPackage.PORT__IS_CONNECTED_OUTSIDE:
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
