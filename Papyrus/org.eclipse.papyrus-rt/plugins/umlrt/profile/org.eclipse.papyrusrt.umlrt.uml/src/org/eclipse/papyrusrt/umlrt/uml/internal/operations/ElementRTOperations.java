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

package org.eclipse.papyrusrt.umlrt.uml.internal.operations;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Externalized operations for {@link Element}s
 */
public class ElementRTOperations extends UMLUtil {

	// Not meant to be instantiable by clients
	protected ElementRTOperations() {
		super();
	}

	public static <T extends InternalUMLRTElement & Element> boolean rtApplyStereotype(T element, T prototype) {
		boolean result = false;

		for (EObject next : prototype.getStereotypeApplications()) {
			EClass stereotype = next.eClass();

			// The RTRedefinedElement is not a canonical stereotype and is
			// handled independently for every element
			if ((stereotype != UMLRealTimePackage.Literals.RT_REDEFINED_ELEMENT)
					&& ((stereotype.getEPackage() == UMLRealTimePackage.eINSTANCE)
							|| (stereotype.getEPackage() == UMLRTStateMachinesPackage.eINSTANCE))) {

				applyStereotype(element, stereotype);
				result = true;
			}
		}

		return result;
	}

	/**
	 * Destroys all of the {@code contents} of a containment reference of an {@link element}.
	 * 
	 * @param element
	 *            an element whose {@code contents} are to be destroyed
	 * @param contents
	 *            the contained elements to destroy
	 */
	public static <T extends InternalUMLRTElement & Element> void destroyAll(T element, EList<? extends Element> contents) {
		if (!contents.isEmpty()) {
			new ArrayList<>(contents).forEach(ElementRTOperations::delete);
		}
	}

	/**
	 * Destroys objects in a GMF-compatible way: unlike the UML2 destruction
	 * algorithm, GMF does not remove adapters because destruction is not a
	 * permanent operation as it is in UML2. Rather, destroyed objects are
	 * retained in a transaction's change description for potential restoration.
	 * In this sense, it is more like {@link EcoreUtil#delete(EObject)}.
	 * 
	 * @param object
	 *            an object to delete
	 * 
	 * @see EcoreUtil#delete(EObject)
	 */
	public static void delete(EObject object) {
		if (object.eContents().isEmpty()) {
			if (object instanceof Element) {
				((Element) object).getStereotypeApplications().forEach(ElementRTOperations::delete);
			}

			removeReferences(object, object);
		} else {
			for (Iterator<EObject> allContents = getAllContents(object, true, false); allContents.hasNext();) {
				EObject next = allContents.next();

				if (next instanceof Element) {
					((Element) next).getStereotypeApplications().forEach(ElementRTOperations::delete);
				}

				removeReferences(next, object);
			}
		}

		EcoreUtil.remove(object);
	}

	/**
	 * An enhanced string representation that succinctly indicates inheritance disposition
	 * and references the redefined element (if any) by qualified name.
	 * 
	 * @param element
	 *            an element
	 * @param baseToString
	 *            its EMF/UML2-generated string representation
	 * 
	 * @return the enhanced string representation which, for local/root definitions, is
	 *         just the {@code baseToString} again
	 */
	public static <T extends InternalUMLRTElement & Element> String toString(T element, String baseToString) {
		String result = baseToString;

		UMLRTInheritanceKind inheritance = UMLRTInheritanceKind.of(element);
		if (inheritance != UMLRTInheritanceKind.NONE) {
			StringBuilder buf = new StringBuilder(baseToString.length() + 32);
			int at = baseToString.indexOf('@');
			int paren = baseToString.indexOf('(', at + 1); // Works even if no @

			InternalUMLRTElement redefined = element.rtGetRedefinedElement();
			String redef = (redefined instanceof NamedElement)
					? getQualifiedName((NamedElement) redefined, NamedElement.SEPARATOR)
					: (redefined != null)
							? redefined.eClass().getName()
							: null;

			String inhMarker;
			switch (inheritance) {
			case INHERITED:
				inhMarker = "[I]"; //$NON-NLS-1$
				break;
			case REDEFINED:
				inhMarker = "[R]"; //$NON-NLS-1$
				break;
			case EXCLUDED:
				inhMarker = "[X]"; //$NON-NLS-1$
				break;
			default:
				// Shouldn't have the local case here, of course
				inhMarker = "[L]"; //$NON-NLS-1$
				break;
			}


			if (at < 0) {
				// Just append
				buf.append(baseToString);
				buf.append(inhMarker);

				if (redefined != null) {
					buf.append(" {redefines ").append(redef).append("}"); //$NON-NLS-1$//$NON-NLS-2$
				}
			} else {
				buf.append(baseToString.substring(0, at));
				buf.append(inhMarker);

				if (paren < 0) {
					buf.append(baseToString.substring(at));
				} else {
					buf.append(baseToString.substring(at, paren));
				}

				if (redefined != null) {
					buf.append("{redefines ").append(redef).append("} "); //$NON-NLS-1$//$NON-NLS-2$
				}

				if (paren >= 0) {
					buf.append(baseToString.substring(paren));
				}
			}

			result = buf.toString();
		}

		return result;
	}
}
