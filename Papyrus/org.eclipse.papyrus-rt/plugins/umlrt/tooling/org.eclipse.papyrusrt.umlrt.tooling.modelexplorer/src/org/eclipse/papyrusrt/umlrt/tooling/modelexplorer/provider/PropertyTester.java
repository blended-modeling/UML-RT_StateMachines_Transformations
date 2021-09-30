/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.provider;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;

/**
 * @author RS211865
 *
 */
public class PropertyTester extends org.eclipse.core.expressions.PropertyTester {

	/** property to test if the selected elements is an eObject */
	public static final String IS_RT_ELEMENT = "isRTElement"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (IS_RT_ELEMENT.equals(property) && receiver instanceof IStructuredSelection) {
			boolean answer = isRTElement((IStructuredSelection) receiver);
			return Boolean.valueOf(answer).equals(expectedValue);
		}
		return false;
	}

	/**
	 * @param receiver
	 * @return
	 */
	protected boolean isRTElement(IStructuredSelection selection) {
		boolean isSelectionOK = false;
		for (IElementType type : UMLRTElementTypesEnumerator.getAllRTTypes()) {
			if (type instanceof ISpecializationType && ((ISpecializationType) type).getMatcher() != null) {
				if (!selection.isEmpty()) {
					Iterator<?> iter = selection.iterator();
					while (iter.hasNext()) {
						EObject current = EMFHelper.getEObject(iter.next());
						if (((ISpecializationType) type).getMatcher().matches(current)) {
							isSelectionOK = true;
						}
					}
				}
			}
			if (isSelectionOK) {
				return true;
			}
		}

		return false;
	}

}
