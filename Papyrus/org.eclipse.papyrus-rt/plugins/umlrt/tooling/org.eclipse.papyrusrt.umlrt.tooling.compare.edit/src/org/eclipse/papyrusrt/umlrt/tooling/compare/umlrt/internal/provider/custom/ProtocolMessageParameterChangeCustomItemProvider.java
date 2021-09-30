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

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.collect.Iterables.tryFind;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.onFeature;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.provider.ExtendedAdapterFactoryItemDelegator;
import org.eclipse.emf.compare.provider.utils.IStyledString.IComposedStyledString;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageParameterChange;

import com.google.common.base.Optional;

/**
 * Custom {@link UMLRTDiffCustomItemProvider} for differences of type {@link ProtocolMessageParameterChange}.
 * 
 * @author Alexandra Buzila
 *
 */
public class ProtocolMessageParameterChangeCustomItemProvider extends UMLRTDiffCustomItemProvider {

	/** The item delegator to reuse root adapter factory (if any). */
	private final ExtendedAdapterFactoryItemDelegator itemDelegator;

	/**
	 * Constructor.
	 *
	 * @param adapterFactory
	 *            the adapter factory to use.
	 */
	public ProtocolMessageParameterChangeCustomItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
		itemDelegator = new ExtendedAdapterFactoryItemDelegator(getRootAdapterFactory());

	}

	@SuppressWarnings("restriction")
	@Override
	public String getText(Object object) {
		Optional<Diff> refiningDiff = getRefiningDiff((ProtocolMessageParameterChange) object);
		if (refiningDiff.isPresent()) {
			return itemDelegator.getText(refiningDiff.get());
		}
		return super.getText(object);
	}

	@SuppressWarnings("restriction")
	@Override
	public IComposedStyledString getStyledText(Object object) {
		Optional<Diff> refiningDiff = getRefiningDiff((ProtocolMessageParameterChange) object);
		if (refiningDiff.isPresent()) {
			return itemDelegator.getStyledText(refiningDiff.get());
		}
		return super.getStyledText(object);
	}

	@Override
	public Object getImage(Object object) {
		Optional<Diff> refiningDiff = getRefiningDiff((ProtocolMessageParameterChange) object);
		if (refiningDiff.isPresent()) {
			return itemDelegator.getImage(refiningDiff.get());
		}
		return super.getImage(object);
	}

	/**
	 * Returns an Optional containing the reference change on the feature <i>ownedParameter</i>
	 * that refines the given {@link ProtocolMessageParameterChange} or, if not available, the
	 * first diff that refines this reference change.
	 * 
	 * @param extension
	 *            the {@link ProtocolMessageParameterChange}
	 * @return the {@link Optional}
	 */
	private Optional<Diff> getRefiningDiff(ProtocolMessageParameterChange extension) {
		return tryFind(extension.getRefinedBy(), and(instanceOf(ReferenceChange.class), onFeature("ownedParameter"))) //$NON-NLS-1$
				.or(tryFind(extension.getRefinedBy(), instanceOf(Diff.class)));
	}
}
