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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.tools.utils.NameResolutionHelper;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.google.common.base.Strings;

/**
 * A name-resolution helper for types in UML-RT models for use in cases where the user
 * should be able to type complete on the simple, unqualified name of the type.
 */
public class TypeNameResolutionHelper extends NameResolutionHelper {

	/**
	 * Initializes me with the typed {@link element} for which I resolve the
	 * names of potential types.
	 * 
	 * @param element
	 *            a typed element
	 */
	public TypeNameResolutionHelper(TypedElement element) {
		super(element.getNamespace(), UMLPackage.Literals.TYPE);
	}

	@Override
	protected void computeAllNames() {
		// Compute names directly available in the scope
		computeNames("", scope, true); // $NON-NLS-1

		// Compute names relative to enclosing namepaces of the scope
		Namespace enclosingNamespace = scope.getNamespace();
		String prefix = ""; // $NON-NLS-1
		while (enclosingNamespace != null) {
			// prefix += enclosingNamespace.getName() + NamedElementUtil.QUALIFIED_NAME_SEPARATOR;
			prefix = ""; //$NON-NLS-1$
			computeNames(prefix, enclosingNamespace, false);
			enclosingNamespace = enclosingNamespace.getNamespace();
		}

		// Compute type names in every other available namespace in the resource set
		for (TreeIterator<?> iter = EcoreUtil.getAllProperContents(EMFHelper.getResourceSet(scope), false); iter.hasNext();) {
			Object next = iter.next();

			// We only look for types in packages: profiles, components, nested classifiers
			// are not supported by UML-RT
			if ((next instanceof Package) && !(next instanceof Profile)) {
				Package package_ = (Package) next;
				prefix = package_.getQualifiedName();

				// Don't support unnamed packages
				if (prefix != null) {
					prefix = prefix + NamedElement.SEPARATOR;
					computeNames(prefix, package_, false);
				}
			} else if (!(next instanceof Resource)) {
				iter.prune();
			}
		}
	}

	/**
	 * Overrides the superclass definition to match also the simple name.
	 */
	@Override
	public List<Object> getMatchingElements(String aString) {
		if (this.allNames == null) {
			this.allNames = new HashMap<>();
			this.computeAllNames();
		}

		// We don't want duplicates in the result
		Collection<Object> result = new HashSet<>();

		for (Entry<String, List<NamedElement>> current : this.allNames.entrySet()) {
			if (Strings.isNullOrEmpty(aString) || current.getKey().startsWith(aString)
					|| lastSegmentStartsWith(current.getKey(), aString)) {

				result.addAll(current.getValue());
			}
		}

		return new ArrayList<>(result);
	}

	/**
	 * Queries whether the last segment of a qualified name starts with the given {@code prefix}.
	 * 
	 * @param qualifiedName
	 *            a qualified name
	 * @param prefix
	 *            a prefix to search for
	 * 
	 * @return {@code false} if either the {@code qualifiedName} is not actually qualified but
	 *         has only one segment or its last segment does not start with the
	 *         {@code prefix}; {@code true}, otherwise
	 */
	static boolean lastSegmentStartsWith(String qualifiedName, String prefix) {
		boolean result = false;

		// Don't consider the case of only a single segment, because the starts-with
		// criterion already covered that
		int index = qualifiedName.lastIndexOf(NamedElement.SEPARATOR);
		if (index >= 0) {
			index += NamedElement.SEPARATOR.length();

			// Don't bother to create a substring if the prefix is too long
			result = ((qualifiedName.length() - index) >= prefix.length())
					&& qualifiedName.substring(index).startsWith(prefix);
		}

		return result;
	}
}
