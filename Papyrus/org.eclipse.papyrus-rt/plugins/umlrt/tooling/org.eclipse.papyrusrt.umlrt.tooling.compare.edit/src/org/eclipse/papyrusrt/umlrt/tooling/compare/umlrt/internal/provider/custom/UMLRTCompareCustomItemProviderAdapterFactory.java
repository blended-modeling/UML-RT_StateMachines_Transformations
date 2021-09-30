/*******************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Stefan Dirix - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.provider.custom;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.compare.diagram.internal.extensions.DiagramChange;
import org.eclipse.emf.compare.provider.IItemDescriptionProvider;
import org.eclipse.emf.compare.provider.IItemStyledLabelProvider;
import org.eclipse.emf.compare.provider.ISemanticObjectLabelProvider;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.Disposable;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.provider.UMLRTDiagramChangeItemProvider;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.util.UMLRTCompareAdapterFactory;


/** Custom Adapter Factory providing custom adapters for UMLRT differences. */
@SuppressWarnings({ "restriction" })
public class UMLRTCompareCustomItemProviderAdapterFactory extends UMLRTCompareAdapterFactory implements
		ComposeableAdapterFactory, IChangeNotifier, IDisposable {

	/**
	 * This keeps track of all the item providers created, so that they can be
	 * {@link #dispose disposed}.
	 */
	protected Disposable disposable = new Disposable();

	/**
	 * This keeps track of the root adapter factory that delegates to this
	 * adapter factory.
	 */
	private ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement
	 * {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 */
	private IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by
	 * {@link #isFactoryForType isFactoryForType}.
	 */
	private Collection<Object> supportedTypes = new ArrayList<>();

	/** ItemProviderAdapter for {@link DiagramChange} differences. */
	private ItemProviderAdapter diagramChangeItemProvider;

	/**
	 * This constructs an instance.
	 */
	public UMLRTCompareCustomItemProviderAdapterFactory() {
		supportedTypes.add(IItemStyledLabelProvider.class);
		supportedTypes.add(IItemDescriptionProvider.class);
		supportedTypes.add(ISemanticObjectLabelProvider.class);
	}

	@Override
	public Adapter createUMLRTDiffAdapter() {
		return new UMLRTDiffCustomItemProvider(this);
	}

	@Override
	public Adapter createProtocolChangeAdapter() {
		return new ProtocolChangeCustomItemProvider(this);
	}

	@Override
	public Adapter createProtocolMessageChangeAdapter() {
		return new ProtocolMessageChangeCustomItemProvider(this);
	}

	@Override
	public Adapter createProtocolMessageParameterChangeAdapter() {
		return new ProtocolMessageParameterChangeCustomItemProvider(this);
	}

	@Override
	public Adapter createUMLRTDiagramChangeAdapter() {
		return new UMLRTDiagramChangeCustomItemProvider(getDiagramChangeAdapter());
	}

	@Override
	public void dispose() {
		disposable.dispose();
	}

	/**
	 * Default item provider adapter for {@link UMLRTDiagramChange UMLRTDiagramChanges}.
	 * 
	 * @return the item provider adapter
	 */
	protected ItemProviderAdapter getDiagramChangeAdapter() {
		if (diagramChangeItemProvider == null) {
			diagramChangeItemProvider = new UMLRTDiagramChangeItemProvider(this);
		}
		return diagramChangeItemProvider;
	}

	@Override
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
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
	public ComposeableAdapterFactory getRootAdapterFactory() {
		if (parentAdapterFactory == null) {
			return this;
		} else {
			return parentAdapterFactory.getRootAdapterFactory();
		}
	}

	@Override
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
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
	protected void associate(Adapter adapter, Notifier target) {
		super.associate(adapter, target);
		if (adapter != null) {
			disposable.add(adapter);
		}
	}
}
