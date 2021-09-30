/*****************************************************************************
 * Copyright (c) 2016, 2017 EclipseSource Services GmbH
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Stefan Dirix (EclipseSource) - Initial API and implementation
 * Philip Langer (EclipseSource) - Support for comparison context in EMF Compare
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.ui.internal.differenceGroup;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.adapterfactory.context.IContextTester;
import org.eclipse.emf.compare.rcp.EMFCompareRCPPlugin;
import org.eclipse.emf.compare.rcp.ui.structuremergeviewer.groups.extender.IDifferenceGroupExtender;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.tree.TreeNode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public abstract class AbstractFilteringGroupExtender implements IDifferenceGroupExtender {

	@Override
	public void addChildren(TreeNode treeNode) {
		final AdapterFactory adapterFactory = getAdapterFactory(treeNode);
		final Builder<TreeNode> collectedGrandChildren = ImmutableList.builder();
		final Iterator<TreeNode> iterator = treeNode.getChildren().iterator();
		while (iterator.hasNext()) {
			final TreeNode childTreeNode = iterator.next();
			if (shouldFilter(childTreeNode, adapterFactory)) {
				collectedGrandChildren.addAll(childTreeNode.getChildren());
				iterator.remove();
			}
		}
		treeNode.getChildren().addAll(collectedGrandChildren.build());
		if (IDisposable.class.isInstance(adapterFactory)) {
			IDisposable.class.cast(adapterFactory).dispose();
		}
	}

	/**
	 * Indicates whether the given {@code treeNode} shall be filtered.
	 * 
	 * @param treeNode
	 *            the {@link TreeNode} for which it is to be determined if it shall be filtered.
	 * @param adapterFactory
	 *            the {@link AdapterFactory} which is returned by {@link #getAdapterFactory()}.
	 * @return
	 * 		{@code true} if the given {@code treeNode} shall be filtered, {@code false} otherwise.
	 */
	protected abstract boolean shouldFilter(TreeNode treeNode, AdapterFactory adapterFactory);

	/**
	 * The {@link AdapterFactory} which is used in the {@link #shouldFilter(TreeNode, AdapterFactory)} method.
	 * 
	 * The default implementation returns the registry used by EMFCompare.
	 * 
	 * @param treeNode
	 *            The tree node acting as context to get the adapter factory for.
	 * 
	 * @return the {@link AdapterFactory} to use.
	 */
	protected ComposedAdapterFactory getAdapterFactory(TreeNode treeNode) {
		final Map<Object, Object> context = createAdapterFactoryContext(treeNode);
		return new ComposedAdapterFactory(EMFCompareRCPPlugin.getDefault().createFilteredAdapterFactoryRegistry(context));
	}

	protected Map<Object, Object> createAdapterFactoryContext(TreeNode treeNode) {
		final Map<Object, Object> context;
		final Comparison comparison = getComparison(treeNode);
		if (comparison != null) {
			context = ImmutableMap.of(IContextTester.CTX_COMPARISON, comparison);
		} else {
			context = Maps.newLinkedHashMap();
		}
		return context;
	}

	private Comparison getComparison(TreeNode treeNode) {
		final EObject eObject = treeNode.getData();
		final Match match = getMatch(eObject);
		final Comparison comparison;
		if (match != null) {
			comparison = match.getComparison();
		} else {
			comparison = null;
		}

		return comparison;
	}

	private Match getMatch(EObject eObject) {
		final Match match;
		if (Diff.class.isInstance(eObject)) {
			match = Diff.class.cast(eObject).getMatch();
		} else if (Match.class.isInstance(eObject)) {
			match = Match.class.cast(eObject);
		} else {
			match = null;
		}
		return match;
	}
}
