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

package org.eclipse.papyrusrt.umlrt.uml.tests.util;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Adapter factory for nice labelling of stereotype applications as distinct objects.
 */
public class StereotypeApplicationItemProviderAdapterFactory extends AdapterFactoryImpl implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {

	private ComposedAdapterFactory parentAdapterFactory;

	private IChangeNotifier changeNotifier = new ChangeNotifier();

	private Collection<Object> supportedTypes = new ArrayList<>();

	private ItemProviderAdapter itemProvider;

	public StereotypeApplicationItemProviderAdapterFactory() {
		super();

		supportedTypes.add(IItemLabelProvider.class);
	}

	protected Adapter createAdapter() {
		if (itemProvider == null) {
			class SAAdapter extends ItemProviderAdapter implements IItemLabelProvider {
				SAAdapter(AdapterFactory factory) {
					super(factory);
				}

				@Override
				public String getText(Object object) {
					String result;
					if (object instanceof EObject) {
						Element element = UMLUtil.getBaseElement((EObject) object);
						if (element != null) {
							EObject appl = (EObject) object;
							String baseLabel = ((IItemLabelProvider) getRootAdapterFactory().adapt(element, IItemLabelProvider.class)).getText(element);
							if (baseLabel.indexOf(">>") > 0) {
								baseLabel = baseLabel.substring(baseLabel.indexOf(">>") + 3, baseLabel.length());
							}
							result = String.format("<<%s>> on %s", appl.eClass().getName(), baseLabel);
						} else {
							result = super.getText(object);
						}
					} else {
						result = super.getText(object);
					}

					return result;
				}
			}

			itemProvider = new SAAdapter(this);
		}
		return itemProvider;
	}

	@Override
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return (parentAdapterFactory == null)
				? this
				: parentAdapterFactory.getRootAdapterFactory();
	}

	@Override
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || isProfilePackage(type);
	}

	protected boolean isProfilePackage(Object type) {
		return (type == UMLRealTimePackage.eINSTANCE)
				|| ((type instanceof EPackage)
						? UMLUtil.getProfile((EPackage) type) != null
						: (type instanceof EObject) && (UMLUtil.getProfile(((EObject) type).eClass().getEPackage()) != null));
	}

	@Override
	protected Adapter createAdapter(Notifier target) {
		Adapter result;

		if ((target instanceof EObject) && (UMLUtil.getBaseElement((EObject) target) != null)) {
			result = createAdapter();
		} else {
			result = super.createAdapter(target);
		}

		return result;
	}

	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>) type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	@Override
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	@Override
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	@Override
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	@Override
	public void dispose() {
		if (itemProvider != null) {
			itemProvider.dispose();
		}
	}
}
