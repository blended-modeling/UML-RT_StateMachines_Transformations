/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.core.utils;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.google.common.base.Strings;

/**
 * Static utilities for working with newly created elements.
 */
public class NewElementUtil {

	/**
	 * Marks the given {@code object} as having been created during the process
	 * of the current transaction. Assumes that the {@code object} is already
	 * attached to the model.
	 * 
	 * @param object
	 *            a newly created element
	 * 
	 * @see #elementCreated(EObject, Supplier)
	 * @see #isCreatedElement(EObject)
	 */
	public static void elementCreated(EObject object) {
		elementCreated(object, Optional::empty);
	}

	/**
	 * Marks the given {@code object} as having been created during the process
	 * of the current transaction.
	 * 
	 * @param object
	 *            a newly created element
	 * @param editingDomainSupplier
	 *            a supplier of the editing domain context, to support
	 *            the case that the {@code object} is not yet attached to the model
	 * 
	 * @see #isCreatedElement(EObject)
	 */
	public static void elementCreated(EObject object, Supplier<Optional<? extends TransactionalEditingDomain>> editingDomainSupplier) {
		// Don't repeat
		if (!isCreatedElement(object)) {
			object.eAdapters().add(new NewElementAdapter(editingDomainSupplier));
		}
	}

	/**
	 * Queries whether the specified {@code object} was created during the current
	 * transaction.
	 * 
	 * @param object
	 *            an object
	 * 
	 * @return whether it was recorded earlier in the current transaction as having
	 *         been created
	 * 
	 * @see #elementCreated(EObject)
	 */
	public static boolean isCreatedElement(EObject object) {
		return EcoreUtil.getExistingAdapter(object, NewElementAdapter.class) != null;
	}

	/**
	 * Obtains an unique name for a new {@code element} in its namespace that
	 * is the {@code baseName} suffixed as necessary to distinguish it from
	 * other elements of the same name in the same namespace, starting any
	 * disambiguating suffix with <tt>2</tt>.
	 * 
	 * @param element
	 *            a new named element in some namespace
	 * @param baseName
	 *            the base of the names to try for the {@code element}
	 * 
	 * @return a default unique name for the {@code element}, with a numeric distinguishing
	 *         suffix if necessary, or {@code null} if the {@code baseName} is {@code null}
	 */
	public static String getUniqueName(NamedElement element, String baseName) {
		return getUniqueName(element, baseName, 2);
	}

	/**
	 * Obtains an unique name for a new {@code element} in its namespace that
	 * is the {@code baseName} suffixed as necessary to distinguish it from
	 * other elements of the same name in the same namespace, starting with
	 * the given disambiguating index.
	 * 
	 * @param element
	 *            a new named element in some namespace
	 * @param baseName
	 *            the base of the names to try for the {@code element}
	 * @param startIndex
	 *            when disambiguating integer suffix is needed, the suffix to start with
	 * 
	 * @return a default unique name for the {@code element}, with a numeric distinguishing
	 *         suffix if necessary, or {@code null} if the {@code baseName} is {@code null}
	 */
	public static String getUniqueName(NamedElement element, String baseName, int startIndex) {
		return getUniqueName(element, baseName, startIndex, false);
	}

	/**
	 * Obtains an unique name for a new {@code element} in its namespace that
	 * is the {@code baseName} suffixed as necessary to distinguish it from
	 * other elements of the same name in the same namespace, starting with
	 * the given disambiguating index.
	 * 
	 * @param element
	 *            a new named element in some namespace
	 * @param baseName
	 *            the base of the names to try for the {@code element}
	 * @param startIndex
	 *            when disambiguating integer suffix is used, the suffix to start with
	 * @param forceSuffix
	 *            whether to force the use of a disambiguating suffix
	 * 
	 * @return a default unique name for the {@code element}, with a numeric distinguishing
	 *         suffix if necessary, or {@code null} if the {@code baseName} is {@code null}
	 */
	public static String getUniqueName(NamedElement element, String baseName, int startIndex, boolean forceSuffix) {
		String result = null;

		if (!Strings.isNullOrEmpty(baseName)) {
			result = baseName;

			if (element.getNamespace() != null) {
				// Ensure uniqueness
				String nameBase = result;
				boolean done = false;

				if (forceSuffix) {
					// Actually start with this one
					result = nameBase + startIndex;
					startIndex = startIndex + 1;
				}

				for (int suffix = startIndex; !done; suffix++) {
					done = true; // Be optimistic

					for (NamedElement next : element.getNamespace().getMembers()) {
						// Not the same element but of a common metaclass
						if ((next != element) && (next.eClass().isSuperTypeOf(element.eClass()) || element.eClass().isSuperTypeOf(next.eClass()))) {
							if (result.equals(next.getName())) {
								result = nameBase + suffix;
								done = false; // Repeat
								break;
							}
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * Obtains an unique name for a new {@code property} of a given {@code type} that
	 * is the initial-lower-case name of the {@code type} suffixed as necessary to
	 * distinguish it from other properties of the same context class.
	 * 
	 * @param property
	 *            a new property in some class
	 * @param type
	 *            the type of the new property
	 * 
	 * @return a default unique name for the property, with a numeric distinguishing
	 *         suffix if necessary, or {@code null} if the {@code type} has no name
	 */
	public static String getUniqueName(Property property, Type type) {
		String result = null;

		String typeName = type.getName();
		if (!Strings.isNullOrEmpty(typeName)) {
			result = getUniqueName(property, Character.toLowerCase(typeName.charAt(0)) + typeName.substring(1));
		}

		return result;
	}

	//
	// Nested types
	//

	private static final class NewElementAdapter extends AdapterImpl {

		private final Supplier<Optional<? extends TransactionalEditingDomain>> editingDomainSupplier;

		/**
		 * Initializes me.
		 */
		NewElementAdapter(Supplier<Optional<? extends TransactionalEditingDomain>> editingDomainSupplier) {
			super();

			this.editingDomainSupplier = editingDomainSupplier;
		}

		@Override
		public void setTarget(Notifier newTarget) {
			super.setTarget(newTarget);

			if (newTarget instanceof EObject) {
				// Schedule clean-up at the end of the transaction
				EObject element = (EObject) newTarget;

				// Prefer the executor associated with the supplied editing domain, if any
				Executor exec = editingDomainSupplier.get()
						.map(PostTransactionExecutor::getInstance)
						// Infer from the contextual editing domain, or fall-back
						.orElseGet(() -> PostTransactionExecutor.getInstance(element));

				exec.execute(
						() -> element.eAdapters().remove(this));
			}
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return type == NewElementAdapter.class;
		}
	}
}
