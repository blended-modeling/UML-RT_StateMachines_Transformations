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
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.accessor;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getFirst;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.ResourceAttachmentChange;
import org.eclipse.emf.compare.rcp.ui.contentmergeviewer.accessor.ICompareAccessor;
import org.eclipse.emf.compare.rcp.ui.contentmergeviewer.accessor.legacy.impl.AbstractTypedElementAdapter;
import org.eclipse.emf.compare.rcp.ui.internal.contentmergeviewer.TypeConstants;
import org.eclipse.emf.compare.rcp.ui.internal.mergeviewer.item.impl.MergeViewerItem;
import org.eclipse.emf.compare.rcp.ui.mergeviewer.IMergeViewer.MergeViewerSide;
import org.eclipse.emf.compare.rcp.ui.mergeviewer.item.IMergeViewerItem;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiff;
import org.eclipse.swt.graphics.Image;

import com.google.common.collect.ImmutableList;

/**
 * Accessor for {@link UMLRTDiff}s.
 * 
 * @author Stefan Dirix
 *
 */
@SuppressWarnings("restriction")
public class UMLRTDiffAccessor extends AbstractTypedElementAdapter implements ICompareAccessor {

	private final MergeViewerSide side;
	private final UMLRTDiff diff;

	/**
	 * Creates a specialized accessor for UMLRT differences.
	 * 
	 * @param adapterFactory
	 *            The adapter factory used by the accessor.
	 * @param diff
	 *            The diff for which we need an accessor.
	 * @param side
	 *            The side on which this accessor will be used.
	 */
	public UMLRTDiffAccessor(AdapterFactory adapterFactory, UMLRTDiff diff, MergeViewerSide side) {
		super(adapterFactory);
		this.side = side;
		this.diff = diff;
	}

	/**
	 * The {@link MergeViewerSide} of this accessor.
	 * 
	 * @return the {@link MergeViewerSide} of this accessor.
	 */
	protected MergeViewerSide getSide() {
		return side;
	}

	/**
	 * The {@link UMLRTDiff} of this accessor.
	 * 
	 * @return the {@link UMLRTDiff} of this accessor.
	 */
	protected UMLRTDiff getUMLRTDiff() {
		return diff;
	}

	@Override
	public ImmutableList<? extends IMergeViewerItem> getItems() {
		final ImmutableList.Builder<IMergeViewerItem> ret = ImmutableList.builder();
		final Collection<Match> matches = getComparison().getMatches();

		for (Match match : matches) {
			final boolean showSide = getSide() != MergeViewerSide.ANCESTOR
					|| (getSide() == MergeViewerSide.ANCESTOR && match.getOrigin() != null);
			final boolean showMatch = checkMatch(match);
			if (showSide && showMatch) {
				final ResourceAttachmentChange diff = getFirst(
						filter(match.getDifferences(), ResourceAttachmentChange.class), null);
				final MergeViewerItem container = new MergeViewerItem.Container(getComparison(), diff, match.getLeft(),
						match.getRight(), match.getOrigin(), getSide(), getRootAdapterFactory());
				ret.add(container);
			}
		}
		return ret.build();
	}

	/**
	 * Determines whether this match should be displayed. The default
	 * implementation calls {@link #checkEObject(EObject)} for each side of the
	 * match.
	 * 
	 * @param match
	 *            The {@link Match} to check.
	 * @return {@code true} if the match shall be displayed, {@code false}
	 *         otherwise.
	 * 
	 */
	protected boolean checkMatch(Match match) {
		return checkEObject(match.getLeft()) || checkEObject(match.getRight()) || checkEObject(match.getOrigin());
	}

	/**
	 * Determines whether the given {@link EObject} shall be displayed.
	 * 
	 * @param object
	 *            The {@link EObject} to check.
	 * @return {@code true} if the eObject shall be displayed, {@code false}
	 *         otherwise.
	 */
	protected boolean checkEObject(EObject object) {
		if (object == null) {
			return false;
		}
		return diff.getDiscriminant() == object || EcoreUtil.isAncestor(object, diff.getDiscriminant());
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public Image getImage() {
		return ExtendedImageRegistry.INSTANCE.getImage(getItemDelegator().getImage(diff.getDiscriminant()));
	}

	@Override
	public String getType() {
		return TypeConstants.TYPE_ETREE_DIFF;
	}

	@Override
	public Comparison getComparison() {
		return diff.getMatch().getComparison();
	}

	@Override
	public IMergeViewerItem getInitialItem() {
		final Diff diffToReturn = getInitialDiff(getUMLRTDiff());
		Match match = null;
		if (ReferenceChange.class.isInstance(diffToReturn)) {
			match = getComparison().getMatch(ReferenceChange.class.cast(diffToReturn).getValue());
		}
		if (match == null){
			match = diff.getMatch();
		}
		return new MergeViewerItem.Container(getComparison(), diffToReturn, match, getSide(), getRootAdapterFactory());
	}

	/**
	 * Determines the initial diff to show in CMV
	 * 
	 * @param diff
	 *            the {@link UMLRTDiff} which's refinedBy diffs are checked.
	 * @return
	 * 		the initial {@link Diff} if there is one, the original {@link UMLRTDiff} otherwise.
	 */
	private Diff getInitialDiff(UMLRTDiff diff) {
		for (Diff refBy : diff.getRefinedBy()) {
			if (refBy instanceof ReferenceChange) {
				ReferenceChange rc = (ReferenceChange) refBy;
				if (rc.getReference().isContainment() && (diff.getDiscriminant() == rc.getValue())) {
					return rc;
				}
			}
		}
		return diff;
	}

}
