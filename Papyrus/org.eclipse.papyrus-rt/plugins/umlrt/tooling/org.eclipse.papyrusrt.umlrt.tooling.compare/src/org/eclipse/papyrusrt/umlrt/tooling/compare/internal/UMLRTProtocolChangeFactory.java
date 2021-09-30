/*****************************************************************************
 * Copyright (c) 2016, 2017 EclipseSource Services GmbH, Christian W. Damus, and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Philip Langer (EclipseSource) - Initial API and implementation
 *   Christian W. Damus - bug 510189
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.collect.Iterables.addAll;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.find;
import static com.google.common.collect.Iterables.transform;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.fromSide;
import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.NAME_ATTRIBUTE_CHANGE;
import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.isAttributeChangeOfProtocol;
import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.isProtocolRenameAttributeChange;
import static org.eclipse.uml2.uml.UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.internal.utils.ComparisonUtil;
import org.eclipse.emf.compare.uml2.internal.UMLDiff;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTCompareFactory;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Package;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Lists;

/**
 * Factory for {@link ProtocolChange protocol changes}.
 * <p>
 * {@link ProtocolChange Protocol changes} are either additions, deletions, and
 * moves of protocols, as well as changes of attributes of protocols. Therefore,
 * we refine the following atomic differences:
 * <ul>
 * <li>Reference changes that concern a protocol container (i.e., the UML
 * Packages): <br>
 * These represent additions, deletions, and moves of protocols.</li>
 * <li>Attribute changes of protocols (i.e., the UML Collaborations)<br>
 * These are currently only renames of protocols.</li>
 * </ul>
 * 
 * @author Philip Langer <planger@eclipsesource.com>
 *
 */
@SuppressWarnings("restriction")
public class UMLRTProtocolChangeFactory extends AbstractUMLRTChangeFactory {

	private static final Function<Diff, List<Diff>> TO_REFINING_DIFFS = new Function<Diff, List<Diff>>() {
		@Override
		public List<Diff> apply(Diff input) {
			return input.getRefines();
		}
	};

	@Override
	public boolean handles(Diff input) {
		return isAddDeleteOrMoveOfProtocolContainer(input) || isAttributeChangeOfProtocol(input);
	}

	private boolean isAddDeleteOrMoveOfProtocolContainer(Diff diff) {
		if (diff instanceof ReferenceChange) {
			final ReferenceChange referenceChange = (ReferenceChange) diff;
			final EObject value = referenceChange.getValue();
			final EReference reference = referenceChange.getReference();
			return isPackagedElementReference(reference) && ProtocolContainerUtils.isProtocolContainer(value);
		}
		return false;
	}

	private boolean isPackagedElementReference(EReference reference) {
		return PACKAGE__PACKAGED_ELEMENT.equals(reference);
	}

	@Override
	public Class<? extends UMLDiff> getExtensionKind() {
		return ProtocolChange.class;
	}

	@Override
	public UMLDiff createExtension() {
		return UMLRTCompareFactory.eINSTANCE.createProtocolChange();
	}

	@Override
	protected EObject getDiscriminant(Diff diff) {
		return find(getDiscriminants(diff), instanceOf(Collaboration.class), null);
	}

	@Override
	protected Switch<Set<EObject>> getDiscriminantsGetter() {
		return new DiscriminantsGetter() {
			@Override
			public Set<EObject> caseCollaboration(Collaboration protocol) {
				return casePackage(ProtocolUtils.getProtocolContainer(protocol));
			}

			@Override
			public Set<EObject> casePackage(Package protocolContainer) {
				final Builder<EObject> builder = ImmutableSet.builder();
				builder.add(protocolContainer);
				builder.addAll(protocolContainer.getStereotypeApplications());

				Collaboration protocol = ProtocolContainerUtils.getProtocol(protocolContainer);
				builder.add(protocol);
				builder.addAll(protocol.getStereotypeApplications());

				// If there is no protocol, then there can be no valid message-set interfaces
				if (protocol != null) {
					Interface messageSetIn = ProtocolUtils.getMessageSetIn(protocol);
					builder.add(messageSetIn);
					builder.addAll(messageSetIn.getStereotypeApplications());

					Interface messageSetInOut = ProtocolUtils.getMessageSetInOut(protocol);
					builder.add(messageSetInOut);
					builder.addAll(messageSetInOut.getStereotypeApplications());

					Interface messageSetOut = ProtocolUtils.getMessageSetOut(protocol);
					builder.add(messageSetOut);
					builder.addAll(messageSetOut.getStereotypeApplications());
				}

				return builder.build();
			}
		};
	}

	@Override
	protected DifferenceKind getRelatedExtensionKind(Diff input) {
		// we always use the difference kind of the original refining diff
		return input.getKind();
	}

	@Override
	public void setRefiningChanges(Diff extension, DifferenceKind extensionKind, Diff refiningDiff) {
		// super.setRefiningChanges adds all changes on the discriminants
		super.setRefiningChanges(extension, extensionKind, refiningDiff);
		extension.getRefinedBy().add(refiningDiff);

		final List<Diff> allRefiningDiffs = Lists.newArrayList(extension.getRefinedBy());
		extension.getRefinedBy().clear();

		// However, two types of protocol changes may have been applied to the
		// same protocol in the same revision (rename vs. add/delete/move)
		// they can be distinguished by the type of diff
		// protocol rename only consists of attribute changes
		// protocol add/delete/move only consists of reference changes
		// Thus, we filter by diff type and only use those that have the same as
		// the refining Diff
		final Iterable<? extends Diff> refiningDiffs = filter(allRefiningDiffs, refiningDiff.getClass());
		// We add all refining diffs of the refining diffs to the refining diffs
		final Iterable<Diff> refiningDiffsOfRefiningDiffs = concat(transform(refiningDiffs, TO_REFINING_DIFFS));
		addAll(extension.getRefinedBy(), concat(refiningDiffs, refiningDiffsOfRefiningDiffs));

		// remove extension itself from its refined
		extension.getRefinedBy().remove(extension);

		if (isProtocolRenameAttributeChange(refiningDiff)) {
			extension.getRefinedBy().addAll(getRefiningDiffsOfProtocolRenameIfItIsOne(refiningDiff));
		}
	}

	@Override
	public void fillRequiredDifferences(Comparison comparison, Diff extension) {
		super.fillRequiredDifferences(comparison, extension);
		// don't add yourself as a required/requiredBy diff
		extension.getRequires().remove(extension);
		extension.getRequiredBy().remove(extension);
	}

	private Collection<Diff> getRefiningDiffsOfProtocolRenameIfItIsOne(Diff diff) {
		final Builder<Diff> builder = ImmutableSet.builder();
		final Comparison comparison = ComparisonUtil.getComparison(diff);
		for (EObject discriminant : getDiscriminants(diff)) {
			final Match match = comparison.getMatch(discriminant);
			final EList<Diff> differences = match.getDifferences();
			final Iterable<Diff> refiningDiffs = filter(differences, and(NAME_ATTRIBUTE_CHANGE, fromSide(diff.getSource())));
			builder.addAll(refiningDiffs);
		}
		return builder.build();
	}
}
