/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml.internal.operations;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableSingleContainment;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Externalized operations for {@link State}s
 */
public class StateRTOperations extends UMLUtil {

	// Not meant to be instantiable by clients
	protected StateRTOperations() {
		super();
	}

	static Entry getInheritableEntry(InternalUMLRTState owner) {
		return (Entry) EcoreUtil.getExistingAdapter(owner, Entry.class);
	}

	static Entry demandInheritableEntry(InternalUMLRTState owner) {
		Entry result = getInheritableEntry(owner);

		if (result == null) {
			result = new Entry(owner);
			owner.eAdapters().add(0, result);
		}

		return result;
	}

	public static Behavior getEntry(InternalUMLRTState owner) {
		return demandInheritableEntry(owner).getInheritable();
	}

	public static void setEntry(InternalUMLRTState owner, Behavior newEntry) {
		demandInheritableEntry(owner).set(newEntry);
	}

	public static boolean isSetEntry(InternalUMLRTState owner) {
		Entry entry = getInheritableEntry(owner);
		return (entry != null) && entry.isSet();
	}

	public static void unsetEntry(InternalUMLRTState owner) {
		Entry entry = getInheritableEntry(owner);
		if (entry != null) {
			entry.unset();
		}
	}

	static Exit getInheritableExit(InternalUMLRTState owner) {
		return (Exit) EcoreUtil.getExistingAdapter(owner, Exit.class);
	}

	static Exit demandInheritableExit(InternalUMLRTState owner) {
		Exit result = getInheritableExit(owner);

		if (result == null) {
			result = new Exit(owner);
			owner.eAdapters().add(0, result);
		}

		return result;
	}

	public static Behavior getExit(InternalUMLRTState owner) {
		return demandInheritableExit(owner).getInheritable();
	}

	public static void setExit(InternalUMLRTState owner, Behavior newExit) {
		demandInheritableExit(owner).set(newExit);
	}

	public static boolean isSetExit(InternalUMLRTState owner) {
		Exit exit = getInheritableExit(owner);
		return (exit != null) && exit.isSet();
	}

	public static void unsetExit(InternalUMLRTState owner) {
		Exit exit = getInheritableExit(owner);
		if (exit != null) {
			exit.unset();
		}
	}

	//
	// Nested types
	//

	private static final class Entry extends InheritableSingleContainment<Behavior> {
		Entry(InternalUMLRTState owner) {
			super(owner.eDerivedStructuralFeatureID(UMLPackage.STATE__ENTRY, Behavior.class));
		}

		@Override
		public Object get(boolean resolve) {
			return ((InternalUMLRTState) getTarget()).umlGetEntry(resolve);
		}

		@Override
		protected NotificationChain basicSet(Behavior newEntry, NotificationChain msgs) {
			return ((InternalUMLRTState) getTarget()).umlBasicSetEntry(newEntry, msgs);
		}

		@Override
		protected Behavior createRedefinition(Behavior inherited) {
			InternalUMLRTOpaqueBehavior result = (InternalUMLRTOpaqueBehavior) super.createRedefinition(inherited);
			result.umlSetRedefinedElement((InternalUMLRTElement) inherited);
			result.handleRedefinedState(
					((InternalUMLRTState) getTarget()).rtGetRedefinedElement());
			return result;
		}
	}

	private static final class Exit extends InheritableSingleContainment<Behavior> {
		Exit(InternalUMLRTState owner) {
			super(owner.eDerivedStructuralFeatureID(UMLPackage.STATE__EXIT, Behavior.class));
		}

		@Override
		public Object get(boolean resolve) {
			return ((InternalUMLRTState) getTarget()).umlGetExit(resolve);
		}

		@Override
		protected NotificationChain basicSet(Behavior newExit, NotificationChain msgs) {
			return ((InternalUMLRTState) getTarget()).umlBasicSetExit(newExit, msgs);
		}

		@Override
		protected Behavior createRedefinition(Behavior inherited) {
			InternalUMLRTOpaqueBehavior result = (InternalUMLRTOpaqueBehavior) super.createRedefinition(inherited);
			result.umlSetRedefinedElement((InternalUMLRTElement) inherited);
			result.handleRedefinedState(
					((InternalUMLRTState) getTarget()).rtGetRedefinedElement());
			return result;
		}
	}

}
