/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Services GmbH
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Philip Langer (EclipseSource) - Initial API and implementation
 * Stefan Dirix (EclipseSource) - Modify for the general case
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.ui.internal.differenceGroup;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EObjectTreeElement;
import org.eclipse.papyrusrt.umlrt.tooling.compare.ui.internal.facet.PapyrusFacetContentProviderWrapperAdapterFactory;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Filters matches in the SMV if they are not shown in Papyrus Model Explorer.
 * 
 * @author Philip Langer
 */
public class PapyrusFilteringDifferenceGroupExtender extends AbstractFilteringGroupExtender {

	/**
	 * Unwraps the {@link EObject}s which are returned by the Papyrus Facet ContentProvider.
	 */
	private static final Function<Object, EObject> UN_WRAP = new Function<Object, EObject>() {
		@Override
		public EObject apply(Object input) {
			if (EObjectTreeElement.class.isInstance(input)) {
				return EObjectTreeElement.class.cast(input).getEObject();
			}
			return null;
		}
	};

	public PapyrusFilteringDifferenceGroupExtender() {
		super();
	}

	@Override
	public boolean handle(TreeNode treeNode) {
		return true;
	}

	@Override
	protected boolean shouldFilter(TreeNode treeNode, AdapterFactory adapterFactory) {
		if (Match.class.isInstance(treeNode.getData())
				&& Match.class.isInstance(treeNode.getParent().getData())) {
			final Collection<Match> contentProviderSubmatches;
			if (Match.class.isInstance(treeNode.getParent().getData())) {
				final Match parentMatch = Match.class.cast(treeNode.getParent().getData());
				contentProviderSubmatches = getContentProviderSubmatches(parentMatch, adapterFactory);
			} else {
				contentProviderSubmatches = Collections.emptyList();
			}
			final Match match = Match.class.cast(treeNode.getData());

			return !contentProviderSubmatches.contains(match);
		}
		return false;
	}

	/**
	 * Determines all submatches of the given {@link Match} by:
	 * <ul>
	 * <li>Collecting all sides of the given {@code match}.
	 * <li>Determining the children of the sides via {@link ITreeItemContentProvider}
	 * <li>Collecting the matches of the determined children
	 * </ul>
	 * 
	 * @param match
	 *            the {@link Match} which's submatches are to be determined.
	 * @param adapterFactory
	 *            the {@link AdapterFactory} to adapt to {@link ITreeItemContentProvider}.
	 * @return a collection of submatches provided via the {@link ITreeItemContentProvider} children of the
	 *         given {@code match}'s sides. An empty collection if there are no children or matches.
	 */
	private Collection<Match> getContentProviderSubmatches(Match match, AdapterFactory adapterFactory) {
		final List<Match> matches = Lists.newArrayList();
		for (EObject eObject : getElementsOfMatch(match)) {
			matches.addAll(getMatchChildren(eObject, match.getComparison(), adapterFactory));
		}
		return matches;
	}

	/**
	 * Returns the left, right and origin side of the given {@link Match} which are not {@code null}.
	 * 
	 * @param match
	 *            the {@link Match} from which the sides are extracted.
	 * @return an {@link Iterable} over the non-{@code null} sides of the given {@code match}.
	 */
	private static Iterable<EObject> getElementsOfMatch(final Match match) {
		return Iterables.filter(Lists.newArrayList(match.getLeft(), match.getRight(), match.getOrigin()), Predicates.notNull());
	}

	/**
	 * Determines all matches of the children of the given {@link EObject}.
	 * 
	 * @param object
	 *            the {@link EObject} for which the children are determined.
	 * @param comparison
	 *            the {@link Comparison} used to determine the matches.
	 * @param adapterFactory
	 *            the {@link AdapterFactory} to adapt to {@link ITreeItemContentProvider}.
	 * @return A collection of matches of the children of the given {@code object}. An empty collection if
	 *         there are none.
	 */
	private Collection<Match> getMatchChildren(EObject object, Comparison comparison,
			AdapterFactory adapterFactory) {
		final List<Match> matches = Lists.newArrayList();
		for (EObject child : Iterables.filter(Iterables.transform(getChildren(object, adapterFactory), UN_WRAP), Predicates.notNull())) {
			matches.add(comparison.getMatch(child));
		}
		return matches;
	}

	/**
	 * Determines all children of the given {@link EObject} by adapting to {@link ITreeItemContentProvider}.
	 * 
	 * @param object
	 *            the {@link EObject} whose children are to be determined.
	 * @param adapterFactory
	 *            the {@link AdapterFactory} to adapt to {@link ITreeItemContentProvider}.
	 * @return The collection of children. An empty collection if there are none.
	 */
	private Collection<?> getChildren(EObject object, AdapterFactory adapterFactory) {
		final Adapter adapter = adapterFactory.adapt(object, ITreeItemContentProvider.class);
		if (adapter != null) {
			return ITreeItemContentProvider.class.cast(adapter).getChildren(object);
		}
		return Collections.emptyList();
	}

	/**
	 * {@inheritDoc}. Adds the {@link PapyrusFacetContentProviderWrapperAdapterFactory} to the default
	 * composed adapter factory.
	 */
	@Override
	protected ComposedAdapterFactory getAdapterFactory(TreeNode treeNode) {
		final ComposedAdapterFactory adapterFactory = super.getAdapterFactory(treeNode);
		adapterFactory.addAdapterFactory(new PapyrusFacetContentProviderWrapperAdapterFactory());
		return adapterFactory;
	}

}
