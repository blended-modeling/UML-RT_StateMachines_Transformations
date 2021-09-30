/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alexandra Buzila (EclipseSource) - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import java.util.Set;

import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageParameterChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTCompareFactory;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.UMLPackage.Literals;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Iterables;

/**
 * Factory for creating {@link ProtocolMessageParameterChange} differences.
 * 
 * @author Alexandra Buzila
 *
 */
@SuppressWarnings("restriction")
public class UMLRTProtocolMessageParameterChangeFactory extends AbstractUMLRTChangeFactory {

	/** Constructor. */
	public UMLRTProtocolMessageParameterChangeFactory() {
		super();
	}

	@Override
	public boolean handles(Diff input) {
		return isAddDeleteOfProtocolMessageParameter(input) ||
				isTypeChangeOfProtocolMessageParameter(input) ||
				isAttributeChangeOfProtocolMessageParameter(input);
	}

	private boolean isTypeChangeOfProtocolMessageParameter(Diff input) {
		if (input instanceof ReferenceChange) {
			final ReferenceChange referenceChange = (ReferenceChange) input;
			final EReference reference = referenceChange.getReference();
			EObject changedEObject = UMLRTCompareUtil.getChangedEObject(input);
			return Literals.TYPED_ELEMENT__TYPE == reference &&
					RTMessageUtils.isRTMessageParameter(changedEObject) &&
					!requiresAddDeleteOfParameter(input);
		}
		return false;
	}

	/**
	 * Checks if the given difference is a reference change of a protocol message parameter.
	 * 
	 * @param input
	 *            the {@link Diff} to check
	 * @return <code>true</code> if the input represents a reference change of a protocol message parameter
	 */
	private boolean isAddDeleteOfProtocolMessageParameter(Diff input) {
		return addDeleteOfParameter().apply(input);
	}

	/**
	 * Checks if the given difference is a reference type change of a protocol message parameter that requires a diff of type add.
	 * 
	 * @param input
	 *            the {@link Diff} to check
	 * @return <code>true</code> if the input represents a reference type change of a protocol message parameter that requires a diff of type add
	 */
	private boolean requiresAddDeleteOfParameter(Diff input) {
		return Iterables.any(input.getRequires(), addDeleteOfParameter()) || Iterables.any(input.getRequiredBy(), addDeleteOfParameter());
	}

	/**
	 * Predicate used to check that a given Diff represents an add or delete of a protocol message parameter.
	 * 
	 * @return The created predicate.
	 */
	private Predicate<? super Diff> addDeleteOfParameter() {
		return new Predicate<Diff>() {
			@Override
			public boolean apply(Diff input) {
				if (!(input instanceof ReferenceChange)) {
					return false;
				}
				final ReferenceChange referenceChange = (ReferenceChange) input;
				final EObject value = referenceChange.getValue();
				final EReference reference = referenceChange.getReference();
				return Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER == reference && RTMessageUtils.isRTMessageParameter(value) &&
						(referenceChange.getKind() == DifferenceKind.ADD || referenceChange.getKind() == DifferenceKind.DELETE);
			}
		};
	}


	/**
	 * Checks if the given difference is an attribute change of a protocol message parameter.
	 * 
	 * @param input
	 *            the {@link Diff} to check
	 * @return <code>true</code> if the input represents an attribute change of a protocol message parameter
	 */

	private boolean isAttributeChangeOfProtocolMessageParameter(Diff input) {
		if (input instanceof AttributeChange) {
			EObject changedEObject = UMLRTCompareUtil.getChangedEObject(input);
			boolean result = RTMessageUtils.isRTMessageParameter(changedEObject);
			return result;
		}
		return false;
	}

	@Override
	public void setRefiningChanges(Diff extension, DifferenceKind extensionKind, Diff refiningDiff) {
		super.setRefiningChanges(extension, extensionKind, refiningDiff);
		if (refiningDiff instanceof AttributeChange) {
			extension.getRefinedBy().add(refiningDiff);
		}
	}

	@Override
	protected Switch<Set<EObject>> getDiscriminantsGetter() {
		return new DiscriminantsGetter() {

			@Override
			public Set<EObject> caseParameter(Parameter parameter) {
				final Builder<EObject> builder = ImmutableSet.builder();
				builder.add(parameter);
				return builder.build();
			}

		};
	}

	@Override
	protected DifferenceKind getRelatedExtensionKind(Diff input) {
		return input.getKind();
	}

	@Override
	protected EObject getDiscriminant(Diff input) {
		return Iterables.find(getDiscriminants(input), Predicates.instanceOf(Parameter.class), null);
	}

	@Override
	public Diff createExtension() {
		return UMLRTCompareFactory.eINSTANCE.createProtocolMessageParameterChange();
	}

}
