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

import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.getProtocolRenameAttributeChange;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.provider.IItemDescriptionProvider;
import org.eclipse.emf.compare.provider.IItemStyledLabelProvider;
import org.eclipse.emf.compare.provider.ISemanticObjectLabelProvider;
import org.eclipse.emf.compare.provider.utils.IStyledString.IComposedStyledString;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange;

import com.google.common.base.Optional;

@SuppressWarnings("restriction")
public class ProtocolChangeCustomItemProvider extends UMLRTDiffCustomItemProvider {

	public ProtocolChangeCustomItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public IComposedStyledString getStyledText(Object object) {
		final ProtocolChange protocolChange = (ProtocolChange) object;

		// if this is a protocol rename, forward to provider of rename diff
		final Optional<Diff> protocolRename = getProtocolRenameAttributeChange(protocolChange);
		if (protocolRename.isPresent()) {
			final IItemStyledLabelProvider delegate = (IItemStyledLabelProvider) adapt(protocolRename.get(),
					IItemStyledLabelProvider.class);
			return delegate.getStyledText(protocolRename.get());
		}

		return super.getStyledText(object);
	}

	@Override
	public Object getImage(Object object) {
		final ProtocolChange protocolChange = (ProtocolChange) object;

		// if this is a protocol rename, forward to provider of rename diff
		final Optional<Diff> protocolRename = getProtocolRenameAttributeChange(protocolChange);
		if (protocolRename.isPresent()) {
			final IItemLabelProvider delegate = (IItemLabelProvider) adapt(protocolRename.get(),
					IItemLabelProvider.class);
			return delegate.getImage(protocolRename.get());
		}

		return super.getImage(object);
	}

	@Override
	public String getDescription(Object object) {
		final ProtocolChange protocolChange = (ProtocolChange) object;

		// if this is a protocol rename, forward to provider of rename diff
		final Optional<Diff> protocolRename = getProtocolRenameAttributeChange(protocolChange);
		if (protocolRename.isPresent()) {
			final IItemDescriptionProvider delegate = (IItemDescriptionProvider) adapt(protocolRename.get(),
					IItemDescriptionProvider.class);
			return delegate.getDescription(protocolRename.get());
		}

		return super.getDescription(object);
	}

	@Override
	public String getSemanticObjectLabel(Object object) {
		final ProtocolChange protocolChange = (ProtocolChange) object;

		// if this is a protocol rename, forward to provider of rename diff
		final Optional<Diff> protocolRename = getProtocolRenameAttributeChange(protocolChange);
		if (protocolRename.isPresent()) {
			final ISemanticObjectLabelProvider delegate = (ISemanticObjectLabelProvider) adapt(protocolRename.get(),
					ISemanticObjectLabelProvider.class);
			return delegate.getSemanticObjectLabel(protocolRename.get());
		}

		return super.getSemanticObjectLabel(object);
	}

	private Object adapt(Diff diff, Class<?> type) {
		return getRootAdapterFactory().adapt(diff, type);
	}
}
