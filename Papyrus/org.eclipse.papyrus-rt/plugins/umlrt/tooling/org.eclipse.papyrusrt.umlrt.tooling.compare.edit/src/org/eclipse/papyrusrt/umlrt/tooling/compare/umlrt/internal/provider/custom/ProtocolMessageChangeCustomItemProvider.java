/*******************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alexandra Buzila - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.provider.custom;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.provider.IItemDescriptionProvider;
import org.eclipse.emf.compare.provider.ISemanticObjectLabelProvider;
import org.eclipse.emf.compare.provider.utils.ComposedStyledString;
import org.eclipse.emf.compare.provider.utils.IStyledString;
import org.eclipse.emf.compare.provider.utils.IStyledString.Style;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange;
import org.eclipse.uml2.uml.Operation;

@SuppressWarnings("restriction")
public class ProtocolMessageChangeCustomItemProvider extends UMLRTDiffCustomItemProvider {

	public ProtocolMessageChangeCustomItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public IStyledString.IComposedStyledString getStyledText(Object object) {
		final ProtocolMessageChange protocolMessageChange = (ProtocolMessageChange) object;

		ComposeableAdapterFactory rootAdapterFactory = ((ComposeableAdapterFactory) adapterFactory)
				.getRootAdapterFactory();
		IItemLabelProvider itemLabelProvider = (IItemLabelProvider) rootAdapterFactory
				.adapt(object, IItemLabelProvider.class);

		String valueText = itemLabelProvider.getText(object);

		String referenceText = getReferenceText(protocolMessageChange);

		ComposedStyledString ret = new ComposedStyledString(valueText);
		ret.append(" [" + referenceText, Style.DECORATIONS_STYLER); //$NON-NLS-1$
		switch (protocolMessageChange.getKind()) {
		case ADD:
			ret.append(" add", Style.DECORATIONS_STYLER); //$NON-NLS-1$
			break;
		case DELETE:
			ret.append(" delete", Style.DECORATIONS_STYLER); //$NON-NLS-1$
			break;
		case CHANGE:
			ret.append(" change", Style.DECORATIONS_STYLER); //$NON-NLS-1$
			break;
		case MOVE:
			ret.append(" move", Style.DECORATIONS_STYLER); //$NON-NLS-1$
			break;
		default:
			throw new IllegalStateException(
					"Unsupported " + DifferenceKind.class.getSimpleName() //$NON-NLS-1$
							+ " value: " + protocolMessageChange.getKind()); //$NON-NLS-1$
		}
		ret.append("]", Style.DECORATIONS_STYLER); //$NON-NLS-1$

		return ret;
	}

	/**
	 * @param value
	 *            the {@link Operation}
	 * @return the reference text for the {@link ProtocolMessageChange}
	 */
	private String getReferenceText(ProtocolMessageChange change) {
		String referenceText = " protocol message"; //$NON-NLS-1$
		EObject discriminant = change.getDiscriminant();
		if (!(discriminant instanceof Operation)) {
			return referenceText;
		}
		RTMessageKind messageKind = RTMessageUtils.getMessageKind(discriminant);
		if (messageKind != null) {
			return messageKind.toString() + referenceText;
		}
		return referenceText;
	}

	@Override
	public Object getImage(Object object) {
		ComposeableAdapterFactory rootAdapterFactory = ((ComposeableAdapterFactory) adapterFactory)
				.getRootAdapterFactory();
		IItemLabelProvider itemLabelProvider = (IItemLabelProvider) rootAdapterFactory
				.adapt(object, IItemLabelProvider.class);

		return itemLabelProvider.getImage(object);
	}

	@Override
	public String getDescription(Object object) {
		final ProtocolMessageChange protocolMessageChange = (ProtocolMessageChange) object;
		final IItemDescriptionProvider delegate = (IItemDescriptionProvider) adapt(
				protocolMessageChange, IItemDescriptionProvider.class);
		return delegate.getDescription(object);
	}

	@Override
	public String getSemanticObjectLabel(Object object) {
		final ProtocolMessageChange protocolMessageChange = (ProtocolMessageChange) object;
		final ISemanticObjectLabelProvider delegate = (ISemanticObjectLabelProvider) adapt(
				protocolMessageChange, ISemanticObjectLabelProvider.class);
		return delegate.getSemanticObjectLabel(protocolMessageChange);
	}

	private Object adapt(Notifier diff, Class<?> type) {
		return getRootAdapterFactory().adapt(diff, type);
	}
}
