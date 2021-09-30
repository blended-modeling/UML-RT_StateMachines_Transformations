/**
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
 */
package org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdviceFactory;
import org.eclipse.papyrus.infra.types.provider.AbstractAdviceBindingConfigurationItemProvider;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class UMLRTSetTypeAdviceConfigurationItemProvider extends AbstractAdviceBindingConfigurationItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTSetTypeAdviceConfigurationItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addElementTypePropertyDescriptor(object);
			addNewTypeViewsPropertyDescriptor(object);
			addElementTypeLabelPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Element Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addElementTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_UMLRTSetTypeAdviceConfiguration_elementType_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_UMLRTSetTypeAdviceConfiguration_elementType_feature", "_UI_UMLRTSetTypeAdviceConfiguration_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTTypesPackage.Literals.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the New Type Views feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addNewTypeViewsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_UMLRTSetTypeAdviceConfiguration_newTypeViews_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_UMLRTSetTypeAdviceConfiguration_newTypeViews_feature", "_UI_UMLRTSetTypeAdviceConfiguration_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTTypesPackage.Literals.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS,
				false,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Element Type Label feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addElementTypeLabelPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_UMLRTSetTypeAdviceConfiguration_elementTypeLabel_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_UMLRTSetTypeAdviceConfiguration_elementTypeLabel_feature", "_UI_UMLRTSetTypeAdviceConfiguration_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				UMLRTTypesPackage.Literals.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_LABEL,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(UMLRTTypesPackage.Literals.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS_TO_DISPLAY);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns UMLRTSetTypeAdviceConfiguration.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/UMLRTSetTypeAdviceConfiguration")); //$NON-NLS-1$
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected boolean shouldComposeCreationImage() {
		return true;
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((UMLRTSetTypeAdviceConfiguration) object).getIdentifier();
		return label == null || label.length() == 0 ? getString("_UI_UMLRTSetTypeAdviceConfiguration_type") : //$NON-NLS-1$
				getString("_UI_UMLRTSetTypeAdviceConfiguration_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}


	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(UMLRTSetTypeAdviceConfiguration.class)) {
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE:
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_LABEL:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS_TO_DISPLAY:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(UMLRTTypesPackage.Literals.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS_TO_DISPLAY,
				RuntimeValuesAdviceFactory.eINSTANCE.createViewToDisplay()));
	}

}
