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
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class ConnectorItemProvider extends NamedElementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ConnectorItemProvider(AdapterFactory adapterFactory) {
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

			addRedefinedConnectorPropertyDescriptor(object);
			addCapsulePropertyDescriptor(object);
			addSourcePropertyDescriptor(object);
			addSourcePartWithPortPropertyDescriptor(object);
			addSourceReplicationFactorPropertyDescriptor(object);
			addTargetPropertyDescriptor(object);
			addTargetPartWithPortPropertyDescriptor(object);
			addTargetReplicationFactorPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Redefined Connector feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addRedefinedConnectorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Connector_redefinedConnector_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Connector_redefinedConnector_feature", "_UI_Connector_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.CONNECTOR__REDEFINED_CONNECTOR,
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
				getString("_UI_Connector_capsule_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Connector_capsule_feature", "_UI_Connector_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.CONNECTOR__CAPSULE,
				false,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Source feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addSourcePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Connector_source_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Connector_source_feature", "_UI_Connector_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.CONNECTOR__SOURCE,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Source Part With Port feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addSourcePartWithPortPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Connector_sourcePartWithPort_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Connector_sourcePartWithPort_feature", "_UI_Connector_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.CONNECTOR__SOURCE_PART_WITH_PORT,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Source Replication Factor feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addSourceReplicationFactorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Connector_sourceReplicationFactor_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Connector_sourceReplicationFactor_feature", "_UI_Connector_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.CONNECTOR__SOURCE_REPLICATION_FACTOR,
				true,
				false,
				false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Target feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addTargetPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Connector_target_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Connector_target_feature", "_UI_Connector_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.CONNECTOR__TARGET,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Target Part With Port feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addTargetPartWithPortPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Connector_targetPartWithPort_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Connector_targetPartWithPort_feature", "_UI_Connector_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.CONNECTOR__TARGET_PART_WITH_PORT,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Target Replication Factor feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addTargetReplicationFactorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Connector_targetReplicationFactor_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Connector_targetReplicationFactor_feature", "_UI_Connector_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTUMLRTPackage.Literals.CONNECTOR__TARGET_REPLICATION_FACTOR,
				true,
				false,
				false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This returns Connector.gif.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Connector")); //$NON-NLS-1$
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
		String label = ((UMLRTConnector) object).getName();
		StyledString styledLabel = new StyledString();
		if (label == null || label.length() == 0) {
			styledLabel.append(getString("_UI_Connector_type"), StyledString.Style.QUALIFIER_STYLER); //$NON-NLS-1$
		} else {
			styledLabel.append(getString("_UI_Connector_type"), StyledString.Style.QUALIFIER_STYLER).append(" " + label); //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(UMLRTConnector.class)) {
		case UMLRTUMLRTPackage.CONNECTOR__SOURCE_REPLICATION_FACTOR:
		case UMLRTUMLRTPackage.CONNECTOR__TARGET_REPLICATION_FACTOR:
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
