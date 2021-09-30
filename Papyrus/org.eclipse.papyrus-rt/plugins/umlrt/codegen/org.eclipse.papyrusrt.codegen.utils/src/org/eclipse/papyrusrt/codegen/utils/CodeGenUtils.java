package org.eclipse.papyrusrt.codegen.utils;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.Model;
import org.eclipse.papyrusrt.xtumlrt.external.predefined.UMLRTProfileUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;

/**
 * General utilities for code generation.
 * 
 * @author epp
 */
public final class CodeGenUtils {

	/**
	 * Default Constructor. Private as this class is not to be extended.
	 */
	private CodeGenUtils() {
	}

	/**
	 * Search entire resource and find a capsule with the given name.
	 * 
	 * @param context
	 *            - An {@link EObject} used to obtain the resource set where to search.
	 * @param capsuleName
	 *            - The capsule to search for.
	 * @return The capsule found as an {@link EObject}, or null, if not found.
	 */
	public static EObject findCapsule(EObject context, String capsuleName) {
		EObject capsule = null;
		TreeIterator<EObject> itor = context.eResource().getAllContents();
		while (itor.hasNext()) {
			EObject next = itor.next();
			if (next instanceof NamedElement
					&& UMLRTProfileUtil.isCapsule((NamedElement) next)
					&& ((NamedElement) next).getName().equals(capsuleName)
					|| next instanceof Capsule
							&& ((Capsule) next).getName().equals(capsuleName)) {
				capsule = next;
				break;
			}
			if (!(next instanceof Package) && !(next instanceof Class)
					&& !(next instanceof Model) && !(next instanceof Capsule)) {
				itor.prune();
			}
		}
		return capsule;
	}
}
