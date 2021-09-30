/*****************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Ltd. and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Ernesto Posse - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.util

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import static extension org.eclipse.papyrusrt.xtumlrt.util.GeneralUtil.*
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.*

/**
 * Utilities related to containment relationships.
 * 
 * <p>Utilities here are useful to record containment chains, since containment structure may be changed by flattening.
 * 
 * @author epp
 */
class ContainmentUtils {

	static val INSTANCE = new ContainmentUtils

	def List<NamedElement> create {
		val owner = element.owner
		var List<NamedElement> chain
		if (owner === null) {
			chain = newArrayList
		} else {
			chain = newArrayList(cachedFullContainmentChainOf( owner as NamedElement ))
		}
		chain.add( element )
		chain
	}
	cachedFullContainmentChainOf(NamedElement element) {
	}

	static def cachedFullContainmentChain(NamedElement element) {
		INSTANCE.cachedFullContainmentChainOf(element)
	}

	static def Iterable<EObject> getAllEContainers(EObject element) {
		val container = element.eContainer
		if (container === null) {
			#[element]
		} else {
			container.allEContainers + #[element]
		}
	}

	static def Iterable<EObject> getAllEContainersUpTo(EObject element, EObject context) {
		val container = element.eContainer
		if (container === null || container == context) {
			#[element]
		} else {
			container.getAllEContainersUpTo(context) + #[element]
		}
	}

	static def Iterable<org.eclipse.uml2.uml.Element> getAllOwningElementsUptoType(org.eclipse.uml2.uml.Element element, Class<?> type) {
		if (element.owner === null || type !== null && type.isInstance(element)) {
			#[element]
		} else {
			getAllOwningElementsUptoType(element.owner, type) + #[element]
		}
	}

	static def Iterable<org.eclipse.uml2.uml.Element> getAllOwningElements(org.eclipse.uml2.uml.Element element) {
		getAllOwningElementsUptoType(element, null)
	}

	static def lowestCommonAncestor(EObject eobj1, EObject eobj2) {
		lowestCommonAncestorPath(eobj1, eobj2)?.last
	}

	static def lowestCommonAncestorPath(EObject eobj1, EObject eobj2) {
		longestCommonPrefix(eobj1.allEContainers, eobj1.allEContainers)
	}

	static def lowestCommonAncestor(Iterable<? extends EObject> eobjs) {
		lowestCommonAncestorPath(eobjs)?.last
	}

	static def lowestCommonAncestorPath(Iterable<? extends EObject> eobjs) {
		longestCommonPrefix(eobjs.map[allEContainers])
	}

}
