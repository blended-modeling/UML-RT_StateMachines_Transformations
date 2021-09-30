/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.xtext.codegen.ui;

import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.xtumlrt.common.ActionCode;
import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.Operation;
import org.eclipse.papyrusrt.xtumlrt.statemach.ActionChain;
import org.eclipse.papyrusrt.xtumlrt.statemach.Guard;
import org.eclipse.papyrusrt.xtumlrt.statemach.Transition;
import org.eclipse.papyrusrt.xtumlrt.statemachext.EntryAction;
import org.eclipse.papyrusrt.xtumlrt.statemachext.ExitAction;
import org.eclipse.papyrusrt.xtumlrt.statemachext.TransitionAction;
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPassiveClass;
import org.eclipse.papyrusrt.xtumlrt.xtext.ui.outline.UmlrtOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;

/**
 * Property tester for XTUMLRT plugin.
 * 
 * @author ysroh
 */
public class UmlrtXtextPropertyTester extends PropertyTester {

	/** eClass type property name. */
	private static final String ECLASS_PROPERTY = "eClass";

	/** isSourceEditable type property name. */
	private static final String IS_SOURCE_EDITABLE_PROPERTY = "isSourceEditable";

	/**
	 * Constructor.
	 *
	 */
	public UmlrtXtextPropertyTester() {
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean result = false;
		Object element = null;
		if (receiver instanceof List) {
			element = ((List) receiver).get(0);
		}

		EObject eobj = getEObject(element);

		if (eobj != null) {
			if (ECLASS_PROPERTY.equals(property)) {
				result = eobj.eClass().getName().equals(expectedValue);
			} else if (IS_SOURCE_EDITABLE_PROPERTY.equals(property)) {
				result = ((Boolean) expectedValue) == isSourceEditable(eobj);
			}
		}
		return result;
	}

	/**
	 * Queries if the object has editable user code.
	 * 
	 * @param eo
	 *            eobject to test
	 * @return true if source has user code.
	 */
	private boolean isSourceEditable(EObject eo) {
		boolean result = false;

		if (eo instanceof RTPassiveClass || eo instanceof Capsule || eo instanceof Transition
				|| eo instanceof Operation || eo instanceof Guard || eo instanceof ActionChain) {
			result = true;
		}

		if (eo instanceof EntryAction || eo instanceof ExitAction || eo instanceof TransitionAction || eo instanceof ActionCode) {
			result = true;
		}

		return result;
	}

	/**
	 * Obtain the {@link EObject} for a given {@link EObjectNode} in the outline view.
	 * 
	 * @param obj
	 *            - An {@link Object}/
	 * 
	 * @return If obj is not null and is an instance of an {@link EObjectNode}, the corresponding {@link EObject}.
	 */
	private EObject getEObject(Object obj) {
		EObject eobj = null;
		if (obj != null && obj instanceof EObjectNode) {
			EObjectNode on = (EObjectNode) obj;
			EObject baseEobj = UmlrtOutlineTreeProvider.getModelElement();
			Resource resource = baseEobj.eResource();
			eobj = on.getEObject(resource);
		}
		return eobj;
	}
}
