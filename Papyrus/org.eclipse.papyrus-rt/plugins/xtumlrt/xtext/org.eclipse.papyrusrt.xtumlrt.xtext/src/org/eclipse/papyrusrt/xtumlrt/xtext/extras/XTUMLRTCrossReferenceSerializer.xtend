/*****************************************************************************
 * Copyright (c) 2016 Zeligsoft and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ernesto Posse - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.xtext.extras

import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import static extension org.eclipse.papyrusrt.xtumlrt.external.predefined.RTSModelLibraryUtils.*
import org.eclipse.xtext.CrossReference
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor
import org.eclipse.xtext.serializer.tokens.CrossReferenceSerializer

/**
 * @author epp
 *
 */
class XTUMLRTCrossReferenceSerializer extends CrossReferenceSerializer {
	
	override serializeCrossRef(EObject semanticObject, CrossReference crossref, EObject target, INode node, Acceptor errors) {
		if (target instanceof NamedElement && (target as NamedElement).isSystemElement) {
			(target as NamedElement).name
		} else {
			super.serializeCrossRef(semanticObject, crossref, target, node, errors)
		}
	}
	
}