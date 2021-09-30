/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Services GmbH
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alexandra Buzila (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.collect.Iterables.find;
import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.isAttributeChangeOfProtocolMessage;
import static org.eclipse.uml2.uml.UMLPackage.Literals.INTERFACE__OWNED_OPERATION;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.uml2.internal.UMLDiff;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTCompareFactory;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Operation;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

/**
 * Factory for creating {@link ProtocolMessageChange} differences.
 * 
 * @author Alexandra Buzila
 *
 */
@SuppressWarnings("restriction")
public class UMLRTProtocolMessageChangeFactory extends AbstractUMLRTChangeFactory {

	@Override
	public boolean handles(Diff input) {
		return isAddDeleteOfProtocolMessage(input) || isAttributeChangeOfProtocolMessage(input);
	}

	private boolean isAddDeleteOfProtocolMessage(Diff diff) {
		if (diff instanceof ReferenceChange) {
			final ReferenceChange referenceChange = (ReferenceChange) diff;
			final EObject value = referenceChange.getValue();
			final EReference reference = referenceChange.getReference();
			return isInterfaceOwnedOperationReference(reference) && RTMessageUtils.isRTMessage(value);
		}
		return false;
	}

	private boolean isInterfaceOwnedOperationReference(EReference reference) {
		return INTERFACE__OWNED_OPERATION.equals(reference);
	}

	@Override
	public Class<? extends UMLDiff> getExtensionKind() {
		return ProtocolMessageChange.class;
	}

	@Override
	public UMLDiff createExtension() {
		return UMLRTCompareFactory.eINSTANCE.createProtocolMessageChange();
	}

	@Override
	protected DifferenceKind getRelatedExtensionKind(Diff input) {
		// we always use the difference kind of the original refining diff
		return input.getKind();
	}

	@Override
	public void setRefiningChanges(Diff extension, DifferenceKind extensionKind, Diff refiningDiff) {
		super.setRefiningChanges(extension, extensionKind, refiningDiff);
		// collect additional refining changes
		Set<Diff> additionalChanges = new HashSet<>();
		for (Diff diff : extension.getRefinedBy()) {
			additionalChanges.addAll(diff.getRefines());
		}
		additionalChanges.addAll(refiningDiff.getRefines());
		// do not add yourself
		additionalChanges.remove(extension);
		extension.getRefinedBy().addAll(additionalChanges);

		extension.getRefinedBy().add(refiningDiff);
	}

	@Override
	protected Switch<Set<EObject>> getDiscriminantsGetter() {
		return new DiscriminantsGetter() {
			@Override
			public Set<EObject> caseOperation(Operation operation) {
				final Builder<EObject> builder = ImmutableSet.builder();
				builder.add(operation);
				CallEvent callEvent = MessageUtils.getCallEvent(operation);
				if (callEvent != null)
					builder.add(callEvent);
				return builder.build();
			}

			@Override
			public Set<EObject> caseCallEvent(CallEvent callEvent) {
				final Builder<EObject> builder = ImmutableSet.builder();
				Operation operation = callEvent.getOperation();
				builder.add(operation);
				builder.add(callEvent);
				return builder.build();
			}
		};
	}

	@Override
	protected EObject getDiscriminant(Diff input) {
		return find(getDiscriminants(input), instanceOf(Operation.class), null);
	}
}
