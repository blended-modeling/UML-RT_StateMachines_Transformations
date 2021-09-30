/*******************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alexandra Buzila - initial API and implementation
 *     Philip Langer - clean up filter behavior that is not required anymore
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.ui.internal.diagram;

import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.isAutoDiagramChange;

import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.rcp.ui.structuremergeviewer.filters.AbstractDifferenceFilter;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.tree.TreeNode;

import com.google.common.base.Predicate;


/**
 * Filters automatic diagram changes in UML-RT.
 * 
 * @author Alexandra Buzila
 * @author Philip Langer <planger@eclipsesource.com>
 */
public class UMLRTAutomaticDiagramChangesFilter extends AbstractDifferenceFilter {

	/**
	 * The predicate use by this filter when it is selected.
	 */
	private static final Predicate<? super EObject> PREDICATE_WHEN_SELECTED = new Predicate<EObject>() {
		@Override
		public boolean apply(EObject input) {
			if (input instanceof TreeNode) {
				final TreeNode node = (TreeNode) input;
				final EObject data = node.getData();
				return isAutoDiagramChange(data);
			}
			return false;
		}
	};

	/**
	 * Constructs the filter with the appropriate predicate.
	 */
	public UMLRTAutomaticDiagramChangesFilter() {
		super();
	}

	@Override
	public Predicate<? super EObject> getPredicateWhenSelected() {
		return PREDICATE_WHEN_SELECTED;
	}

	@Override
	public boolean isEnabled(IComparisonScope scope, Comparison comparison) {
		if (scope != null) {
			for (String nsURI : scope.getNsURIs()) {
				if (nsURI.matches("http://www\\.eclipse\\.org/papyrus.*/umlrt")) { //$NON-NLS-1$
					return true;
				}
			}
		}
		return false;
	}

}
