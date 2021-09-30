/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Services GmbH
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Philip Langer (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import static com.google.common.collect.Iterables.tryFind;
import static org.eclipse.uml2.uml.UMLPackage.Literals.INTERFACE__OWNED_OPERATION;
import static org.eclipse.uml2.uml.UMLPackage.Literals.NAMED_ELEMENT__NAME;

import java.util.Collection;

import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.FeatureMapChange;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.diagram.internal.extensions.DiagramDiff;
import org.eclipse.emf.compare.internal.utils.ComparisonUtil;
import org.eclipse.emf.compare.uml2.internal.UMLDiff;
import org.eclipse.emf.compare.utils.MatchUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Protocol;
import org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram.AutoDiagramChangeMarker;
import org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram.IAutoDiagramChangeMarker;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Operation;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

/**
 * Util class for Compare related topics.
 * 
 * @author Philip Langer <planger@eclipsesource.com>
 */
@SuppressWarnings("restriction")
public final class UMLRTCompareUtil {

	/**
	 * Util classes must not be instantiated.
	 */
	private UMLRTCompareUtil() {
		// hidden constructor
	}

	/**
	 * Checks whether a given {@link Diff} renames a {@link Protocol}.
	 */
	public static final Predicate<Diff> PROTOCOL_RENAME_ATTRIBUTE_CHANGE = new Predicate<Diff>() {
		@Override
		public boolean apply(Diff input) {
			return isProtocolRenameAttributeChange(input);
		}
	};

	/**
	 * Checks whether a given {@link Diff} renames a {@link Operation ProtocolMessage}.
	 */
	public static final Predicate<Diff> PROTOCOL_MESSAGE_RENAME_ATTRIBUTE_CHANGE = new Predicate<Diff>() {
		@Override
		public boolean apply(Diff input) {
			return isProtocolMessageRenameAttributeChange(input);
		}
	};

	/**
	 * Checks whether a given {@link Diff} renames an {@link EObject}.
	 */
	public static final Predicate<Diff> NAME_ATTRIBUTE_CHANGE = new Predicate<Diff>() {
		@Override
		public boolean apply(Diff input) {
			return isNameAttributeChange(input);
		}
	};

	/**
	 * Checks whether a given {@link Diff} has an {@link IAutoDiagramChangeMarker}.
	 */
	public static final Predicate<Diff> AUTO_DIAGRAM_CHANGE = new Predicate<Diff>() {
		@Override
		public boolean apply(Diff input) {
			return isAutoDiagramChange(input);
		}
	};

	/**
	 * Checks specifying whether a {@link Match} is a match of a capsule diagram.
	 */
	public static final Predicate<Match> CAPSULE_DIAGRAM_MATCH = new Predicate<Match>() {
		@Override
		public boolean apply(Match input) {
			if (input != null && getAnyMatchValue(input) instanceof Diagram) {
				final Diagram diagram = (Diagram) getAnyMatchValue(input);
				return diagram.getElement() instanceof Classifier
						&& CapsuleUtils.isCapsule((Classifier) diagram.getElement());
			}
			return false;
		}
	};

	/**
	 * Determines if the given {@link Diff} is an {@link AttributeChange}
	 * concerning a {@link Protocol}.
	 * 
	 * @param diff
	 *            the {@link Diff} to check.
	 * @return {@code true} if the given {@code diff} is an
	 *         {@link AttributeChange} concerning a {@link Protocol},
	 *         {@code false} otherwise.
	 */
	public static boolean isAttributeChangeOfProtocol(Diff diff) {
		if (diff instanceof AttributeChange) {
			return ProtocolUtils.isProtocol(getChangedEObject(diff));
		}
		return false;
	}

	/**
	 * Determines if the given {@link Diff} is an {@link AttributeChange}
	 * concerning a {@link ProtocolMessage}.
	 * 
	 * @param diff
	 *            the {@link Diff} to check.
	 * @return {@code true} if the given {@code diff} is an
	 *         {@link AttributeChange} concerning a {@link ProtocolMessage},
	 *         {@code false} otherwise.
	 */
	public static boolean isAttributeChangeOfProtocolMessage(Diff diff) {
		if (diff instanceof AttributeChange) {
			return RTMessageUtils.isRTMessage(getChangedEObject(diff));
		}
		return false;
	}

	/**
	 * Determines if the given {@link Diff} is an {@link AttributeChange}
	 * concerning the name of an {@link EObject}.
	 * 
	 * @param diff
	 *            the {@link Diff} to check.
	 * @return {@code true} if the given {@code diff} is an
	 *         {@link AttributeChange} concerning the name of an {@link EObject}
	 *         , {@code false} otherwise.
	 */
	public static boolean isNameAttributeChange(Diff diff) {
		if (diff instanceof AttributeChange) {
			final AttributeChange attributeChange = (AttributeChange) diff;
			return NAMED_ELEMENT__NAME.equals(attributeChange.getAttribute());
		}
		return false;
	}

	/**
	 * Determines the business model object containing the given difference.
	 * 
	 * @param diff
	 *            the {@link Diff}.
	 * @return the business model object containing the given difference.
	 */
	public static EObject getChangedEObject(Diff diff) {
		return MatchUtil.getContainer(ComparisonUtil.getComparison(diff), diff);
	}

	/**
	 * Determines if the given {@link Diff} is an {@link AttributeChange}
	 * concerning the name of a {@link Protocol}.
	 * 
	 * @param diff
	 *            the {@link Diff} to check.
	 * @return {@code true} if the given {@code diff} is an
	 *         {@link AttributeChange} concerning the name of a {@link Protocol}
	 *         , {@code false} otherwise.
	 */
	public static boolean isProtocolRenameAttributeChange(Diff diff) {
		return isAttributeChangeOfProtocol(diff) && NAME_ATTRIBUTE_CHANGE.apply(diff)
				&& DifferenceKind.CHANGE.equals(diff.getKind());
	}

	/**
	 * Determines if the given {@link Diff} is an {@link AttributeChange}
	 * concerning the name of a {@link ProtocolMessage}.
	 * 
	 * @param diff
	 *            the {@link Diff} to check.
	 * @return {@code true} if the given {@code diff} is an
	 *         {@link AttributeChange} concerning the name of a {@link ProtocolMessage}
	 *         , {@code false} otherwise.
	 */
	public static boolean isProtocolMessageRenameAttributeChange(Diff diff) {
		return isAttributeChangeOfProtocolMessage(diff) && NAME_ATTRIBUTE_CHANGE.apply(diff)
				&& DifferenceKind.CHANGE.equals(diff.getKind());
	}

	/**
	 * Determines the {@link AttributeChange} concerning the name of a
	 * {@link Protocol} within the refinedBy diffs of the given
	 * {@link ProtocolChange}.
	 * 
	 * @param protocolChange
	 *            the {@link ProtocolChange} which's refinedBy diffs are to be
	 *            checked.
	 * @return an {@link Optional} containing the {@link AttributeChange} the
	 *         name of a {@link Protocol}.
	 */
	public static Optional<Diff> getProtocolRenameAttributeChange(ProtocolChange protocolChange) {
		if (DifferenceKind.CHANGE.equals(protocolChange.getKind())) {
			return tryFind(protocolChange.getRefinedBy(), PROTOCOL_RENAME_ATTRIBUTE_CHANGE);
		}
		return Optional.absent();
	}

	/**
	 * Determines the {@link AttributeChange} concerning the name of a
	 * {@link ProtocolMessage} within the refinedBy diffs of the given
	 * {@link ProtocolMessageChange}.
	 * 
	 * @param protocolMessageChange
	 *            the {@link ProtocolMessageChange} which's refinedBy diffs are to be
	 *            checked.
	 * @return an {@link Optional} containing the {@link AttributeChange} the
	 *         name of a {@link ProtocolMessage}.
	 */
	public static Optional<Diff> getProtocolMessageRenameAttributeChange(ProtocolMessageChange protocolMessageChange) {
		if (DifferenceKind.CHANGE.equals(protocolMessageChange.getKind())) {
			return tryFind(protocolMessageChange.getRefinedBy(), PROTOCOL_RENAME_ATTRIBUTE_CHANGE);
		}
		return Optional.absent();
	}

	public static final Predicate<Diff> PROTOCOL_MESSAGE_REFERENCE_CHANGE = new Predicate<Diff>() {
		@Override
		public boolean apply(Diff input) {
			return isProtocolMessageReferenceChange(input);
		}
	};

	public static boolean isProtocolMessageReferenceChange(Diff input) {
		if (!(input instanceof ReferenceChange)) {
			return false;
		}
		ReferenceChange referenceChange = (ReferenceChange) input;
		if (INTERFACE__OWNED_OPERATION != referenceChange.getReference()) {
			return false;
		}
		return RTMessageUtils.isRTMessage(referenceChange.getValue());
	}

	public static Optional<Diff> getProtocolMessageReferenceChange(ProtocolMessageChange protocolMessageChange) {
		if (protocolMessageChange == null) {
			return Optional.absent();
		}
		return tryFind(protocolMessageChange.getRefinedBy(), PROTOCOL_MESSAGE_REFERENCE_CHANGE);
	}

	public static Operation getRTProtocolMessage(ProtocolMessageChange protocolMessageChange) {
		Optional<Diff> optional = getProtocolMessageReferenceChange(protocolMessageChange);
		if (optional.isPresent() && optional.get() instanceof ReferenceChange) {
			return (Operation) ((ReferenceChange) optional.get()).getValue();
		}
		return null;

	}

	public static EObject getAnyMatchValue(Match match) {
		if (match.getLeft() != null) {
			return match.getLeft();
		} else if (match.getRight() != null) {
			return match.getRight();
		}
		return match.getOrigin();
	}

	/**
	 * Checks if the given reference is the {@link NotationPackage#VIEW__PERSISTED_CHILDREN} reference.
	 * 
	 * @param reference
	 *            the {@link EReference} to check
	 * @return <code>true</code> if the reference verifies the condition
	 */
	public static boolean isViewPersistedChildrenReference(EReference reference) {
		return NotationPackage.eINSTANCE.getView_PersistedChildren().equals(reference);
	}

	/**
	 * Return the value on which the given difference was detected.
	 * 
	 * @param diff
	 *            the difference to check
	 * 
	 * @return the value
	 */
	public static Object getChangedValue(Diff diff) {
		if (diff instanceof AttributeChange) {
			return ((AttributeChange) diff).getValue();
		} else if (diff instanceof ReferenceChange) {
			return ((ReferenceChange) diff).getValue();
		} else if (diff instanceof FeatureMapChange) {
			return ((FeatureMapChange) diff).getValue();
		} else if (diff instanceof DiagramDiff) {
			return ((DiagramDiff) diff).getView();
		} else if (diff instanceof UMLDiff) {
			return ((UMLDiff) diff).getDiscriminant();
		} else {
			return null;
		}
	}

	/**
	 * Marks the given <code>diff</code> with an {@link IAutoDiagramChangeMarker}.
	 * 
	 * @param diff
	 *            The diff to mark.
	 */
	public static void markAsAutoDiagramChange(Diff diff) {
		diff.eAdapters().add(new AutoDiagramChangeMarker());
	}

	/**
	 * Marks the given <code>diffs</code> with an {@link IAutoDiagramChangeMarker}.
	 * 
	 * @param diffs
	 *            The diffs to mark.
	 */
	public static void markAsAutoDiagramChanges(Collection<Diff> diffs) {
		for (Diff diff : diffs) {
			markAsAutoDiagramChange(diff);
		}
	}

	/**
	 * Specifies whether the given <code>diff</code> is an {@link IAutoDiagramChangeMarker}.
	 * 
	 * @param diff
	 *            The diff to check.
	 * @return <code>true</code> if it is an {@link IAutoDiagramChangeMarker}, <code>false</code> otherwise.
	 */
	public static boolean isAutoDiagramChange(EObject diff) {
		return EcoreUtil.getExistingAdapter(diff, IAutoDiagramChangeMarker.class) != null;
	}

}
