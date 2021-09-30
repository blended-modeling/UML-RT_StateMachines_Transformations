/******************************************************************************
 * Copyright (c) 2002, 2016 IBM Corporation, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 *    Christian W. Damus - bug 500743
 *    
 ****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.layout;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.ExecutionStrategy;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.core.service.ProviderPriority;
import org.eclipse.gmf.runtime.common.core.service.Service;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.layout.CanLayoutNodesOperation;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.layout.LayoutNodesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNode;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNodeProvider;

import com.google.common.collect.Lists;

/**
 * A private service that provides for diagram layout based on a specific
 * list of providers.
 * 
 * @see #INSTANCE
 * @see #PrivateLayoutService(List)
 */
@SuppressWarnings("restriction")
public class PrivateLayoutService extends Service implements ILayoutNodeProvider {

	/**
	 * A convenient shareable instance that includes all layout providers registered
	 * on the standard GMF {@code layoutProviders} extension point, except for a few
	 * that are known to cause difficulties in integration with the {@link CanonicalEditPolicy},
	 * in particular.
	 */
	public static final PrivateLayoutService INSTANCE = new PrivateLayoutService();

	/**
	 * Initializes me with a specific list of layout providers, in priority
	 * order.
	 *
	 * @param providers
	 *            my layout providers
	 */
	public PrivateLayoutService(List<? extends ILayoutNodeProvider> providers) {
		super();

		providers.forEach(this::addProvider);
	}

	/**
	 * Initializes me with a specific list of layout providers, in priority order.
	 *
	 * @param provider
	 *            the highest-priority provider (must not be {@code null})
	 * @param more
	 *            additional lower-priority providers
	 */
	public PrivateLayoutService(ILayoutNodeProvider provider, ILayoutNodeProvider... more) {
		this(Lists.asList(provider, more));
	}

	/**
	 * Initializes me with providers loaded from the extension point, excluding
	 * the ELK layout provider (and possibly others, as needs warrant).
	 *
	 * @see <a href="http://eclip.se/500743">bug 500743</a>
	 */
	private PrivateLayoutService() {
		super();

		List<IConfigurationElement> configs = Lists.newArrayList(Platform.getExtensionRegistry()
				.getExtensionPoint("org.eclipse.gmf.runtime.diagram.ui", "layoutProviders") //$NON-NLS-1$//$NON-NLS-2$
				.getConfigurationElements());

		// Filter out providers that we don't want
		configs.removeIf(c -> c.getContributor().getName().startsWith("org.eclipse.elk")); //$NON-NLS-1$

		configureProviders(configs.toArray(new IConfigurationElement[configs.size()]));
	}

	private void addProvider(ILayoutNodeProvider theProvider) {
		// They may as well all be highest priority, as they are a single list
		addProvider(ProviderPriority.HIGHEST, new ProviderDescriptor(null) {
			{
				policyInitialized = true; // We won't have a policy
				this.provider = theProvider;
			}
		});
	}

	/**
	 * Execute an {@code operation} using the {@link ExecutionStrategy#FIRST FIRST}
	 * execution strategy.
	 * 
	 * @param operation
	 *            the operation to execute
	 * @return the operation result
	 */
	private Object execute(IOperation operation) {
		List<?> results = execute(ExecutionStrategy.FIRST, operation);
		return results.isEmpty() ? null : results.get(0);
	}

	private void checkValidLayoutNodes(List<?> nodes) {
		if (null == nodes) {
			throw new NullPointerException("Argument 'nodes' is null"); //$NON-NLS-1$
		}
		if (nodes.size() == 0) {
			throw new IllegalArgumentException("Argument 'nodes' is empty"); //$NON-NLS-1$
		}

		EObject parent = null;
		for (Object next : nodes) {
			if (!(next instanceof ILayoutNode)) {
				throw new IllegalArgumentException(
						"Argument 'nodes' contains objects which aren't of type 'ILayoutNode'"); //$NON-NLS-1$
			}

			ILayoutNode node = (ILayoutNode) next;

			if (parent == null) {
				parent = ViewUtil.getContainerView(node.getNode());
			} else if (ViewUtil.getContainerView(node.getNode()) != parent) {
				throw new IllegalArgumentException(
						"Argument 'nodes' contains objects which have a different parent containment"); //$NON-NLS-1$
			}
		}
	}

	@Override
	public Runnable layoutLayoutNodes(@SuppressWarnings("rawtypes") List layoutNodes, boolean offsetFromBoundingBox, IAdaptable layoutHint) {
		if (null == layoutHint) {
			throw new NullPointerException("Argument 'layoutHint' is null"); //$NON-NLS-1$
		}

		checkValidLayoutNodes(layoutNodes);

		Assert.isNotNull(layoutNodes);
		Assert.isNotNull(layoutHint);

		return (Runnable) execute(new LayoutNodesOperation(layoutNodes, offsetFromBoundingBox, layoutHint));
	}

	@Override
	public boolean canLayoutNodes(@SuppressWarnings("rawtypes") List layoutNodes, boolean shouldOffsetFromBoundingBox, IAdaptable layoutHint) {
		return execute(new CanLayoutNodesOperation(layoutNodes, shouldOffsetFromBoundingBox, layoutHint)) == Boolean.TRUE;
	}
}
