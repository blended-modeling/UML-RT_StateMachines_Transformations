/*****************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Ltd and others.
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
import org.eclipse.emf.ecore.ENamedElement
import static extension org.eclipse.papyrusrt.xtumlrt.util.ContainmentUtils.*

/**
 * @author epp
 *
 */
final class NamesUtil {

	static val INSTANCE = new NamesUtil
	static val NAME_FEATURE = "name"
	static val SEPARATOR = "::"

	static def isNamedElement(EObject element) {
		if (element !== null) {
			element instanceof ENamedElement
			|| element instanceof org.eclipse.papyrusrt.xtumlrt.common.NamedElement
			|| element instanceof org.eclipse.uml2.uml.NamedElement
			|| element.eClass?.EAllStructuralFeatures?.contains(NAME_FEATURE)
		}
		else {
			false
		}
	}
	
	static def String basicGetName(EObject element) {
		val nameFeature = element?.eClass?.getEStructuralFeature(NAME_FEATURE)
		if (nameFeature !== null) {
			val nameValue = element?.eGet(nameFeature)
			if (nameValue instanceof String) {
				nameValue
			} else {
				null
			}
		}
	}
	
	static def hasName(EObject element) {
		if (element.isNamedElement) {
			val name = basicGetName(element)
			name !== null && !name.trim.empty
		}
		else {
			false
		}
	}
	
	static def String getEffectiveName(EObject element) {
		if (element.hasName) {
			basicGetName(element)
		} else {
			val container = element.eContainer
			if (container === null) {
				element.eClass.name
			} else {
				val feature = element.eContainingFeature
				if (feature.upperBound === 1) {
					singular(feature.name)
				} else {
					val featureValue = container.eGet(feature)
					if (featureValue instanceof List<?>) {
						singular(feature.name) + featureValue.indexOf(element)
					} else {
						feature.name + "_value"
					}
				}
			}
		}
	}
	
	static def String getEffectiveQualifiedName(EObject element) {
		element.getAllEContainers.map[effectiveName].reduce[a, b | a + SEPARATOR + b]
	}
	
	static def String getEffectiveRelativeQualifiedName(EObject element, EObject context) {
		element.getAllEContainersUpTo(context).map[effectiveName].reduce[a, b | a + SEPARATOR + b]
	}
	
	static def EObject getFirstNamedContainer(EObject element) {
		if (element.hasName) {
			element
		} else {
			element.eContainer?.firstNamedContainer
		}
	}
	
	static def getCachedEffectiveName(EObject element) { INSTANCE.cachedEffectiveName(element) }
	
	static def singular(String string) {
		if (string !== null) {
			val trimmed = string.trim
			if (!trimmed.empty) {
				val len = trimmed.length
				if (trimmed.endsWith("ies")) {
					trimmed.substring(0, len - 3) + "y"
				} else if (trimmed.endsWith("ves")) {
					trimmed.substring(0, len - 3) + "f"
				} else if (trimmed.endsWith("es")) {
					trimmed.substring(0, len - 2)
				} else if (trimmed.endsWith("s")) {
					trimmed.substring(0, len - 1)
				} else {
					trimmed
				}
			}
		}
	}

	private def create { getEffectiveName(element) } cachedEffectiveName(EObject element) {}

}