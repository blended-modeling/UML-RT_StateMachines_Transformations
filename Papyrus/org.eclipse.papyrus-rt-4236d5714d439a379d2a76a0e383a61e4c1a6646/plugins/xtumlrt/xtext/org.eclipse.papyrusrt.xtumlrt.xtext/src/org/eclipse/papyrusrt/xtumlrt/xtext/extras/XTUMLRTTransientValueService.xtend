/*****************************************************************************
 * Copyright (c) 2016 Zeligsoft (2009) Ltd. and others.
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

import java.util.LinkedHashSet
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.papyrusrt.xtumlrt.common.CommonPackage
import org.eclipse.papyrusrt.xtumlrt.statemach.StatemachPackage
import org.eclipse.papyrusrt.xtumlrt.statemachext.StatemachextPackage
import org.eclipse.papyrusrt.xtumlrt.umlrt.UmlrtPackage
import org.eclipse.xtext.parsetree.reconstr.impl.DefaultTransientValueService
import org.eclipse.papyrusrt.xtumlrt.interactions.InteractionsPackage

/**
 * Extends the default transient value service to mark certain XTUMLRT attributes as transient in order
 * to allow serialization of models that were not created with the textual syntax.
 * 
 * @author Ernesto Posse
 */
class XTUMLRTTransientValueService extends DefaultTransientValueService {
	
	static val CORE_TRANSIENTS = #{
		CommonPackage.Literals.ANNOTATION           -> #["parameters"],
		CommonPackage.Literals.ANNOTATION_PARAMETER -> #["value"],
//		CommonPackage.Literals.ATTRIBUTE            -> #["visibility", "default", "static", "readOnly"],
//		CommonPackage.Literals.BASE_CONTAINER       -> #["entities","packages","typeDefinitions"],
//		CommonPackage.Literals.CAPSULE              -> #["parts","ports","connectors"],
//		CommonPackage.Literals.CAPSULE_PART         -> #["kind"],
//		CommonPackage.Literals.CONNECTOR_END        -> #["partWithPort"],
		CommonPackage.Literals.DEPENDENCY           -> #["client"],
//		CommonPackage.Literals.ENTITY               -> #["behaviour"],
//		CommonPackage.Literals.ENUMERATION          -> #["defaultValue","literals"],
		CommonPackage.Literals.MULTIPLICITY_ELEMENT -> #["unique", "ordered", "lowerBound"],
		CommonPackage.Literals.NAMED_ELEMENT        -> #["name","description","annotations","dependencies","artifacts"],
//		CommonPackage.Literals.OPERATION_SIGNATURE  -> #["visibility", "static", "abstract", "query", "parameters", "returnType"],
//		CommonPackage.Literals.PARAMETER            -> #["direction"],
//		CommonPackage.Literals.PORT                 -> #["conjugate","visibility"],
//		CommonPackage.Literals.PROTOCOL             -> #["protocolBehaviourFeatures"],
//		CommonPackage.Literals.PROTOCOL_CONTAINER   -> #["protocols"],
		CommonPackage.Literals.REDEFINABLE_ELEMENT  -> #["redefines"],
//		CommonPackage.Literals.SIGNAL               -> #["parameters"],
//		CommonPackage.Literals.STRUCTURED_TYPE      -> #["attributes","operations","generalizations"],
		CommonPackage.Literals.TYPE_DEFINITION      -> #["type"],
		CommonPackage.Literals.USER_DEFINED_TYPE    -> #["baseType","constraints"],
		InteractionsPackage.Literals.LIFELINE       -> #["coveredBy"],
//		StatemachPackage.Literals.ACTION_CHAIN      -> #["actions"],
//		StatemachPackage.Literals.COMPOSITE_STATE   -> #["initial","deepHistory","choicePoints","junctionPoints","substates","transitions","terminatePoint"],
//		StatemachPackage.Literals.STATE_MACHINE     -> #["top"],
//		StatemachPackage.Literals.STATE             -> #["entryPoints", "exitPoints", "entryAction", "exitAction"],
//		StatemachPackage.Literals.TRANSITION        -> #["triggers","guard","actionChain"],
		StatemachPackage.Literals.VERTEX            -> #["incommingTransitions", "outgoingTransitions"],
		StatemachPackage.Literals.INITIAL_POINT     -> #["name"],
		UmlrtPackage.Literals.RT_MODEL              -> #["imports","containedArtifacts"],
		UmlrtPackage.Literals.RT_PORT               -> #["kind","notification","publish","wired","registration","registrationOverride"]
	}

	static val NON_TRANSIENTS = #{
		CommonPackage.Literals.ATTRIBUTE            -> #["name"],
		CommonPackage.Literals.CAPSULE              -> #["name"],
		CommonPackage.Literals.CAPSULE_PART         -> #["name"],
		CommonPackage.Literals.PORT                 -> #["name"],
		CommonPackage.Literals.ENTITY               -> #["name"],
		CommonPackage.Literals.ENUMERATION          -> #["name"],
		CommonPackage.Literals.ENUMERATION_LITERAL  -> #["name"],
		CommonPackage.Literals.OPERATION            -> #["name"],
		CommonPackage.Literals.PACKAGE              -> #["name"],
		CommonPackage.Literals.PARAMETER            -> #["name"],
		CommonPackage.Literals.PROTOCOL             -> #["name"],
		CommonPackage.Literals.SIGNAL               -> #["name"],
		CommonPackage.Literals.STRUCTURED_TYPE      -> #["name"],
		CommonPackage.Literals.TYPE_DEFINITION      -> #["name"],
		CommonPackage.Literals.USER_DEFINED_TYPE    -> #["name"],
		InteractionsPackage.Literals.INTERACTION    -> #["name"],
		InteractionsPackage.Literals.LIFELINE       -> #["name"],
		InteractionsPackage.Literals.MESSAGE        -> #["name"],
		InteractionsPackage.Literals.MESSAGE_OCCURRENCE_SPECIFICATION -> #["name"],
		StatemachPackage.Literals.COMPOSITE_STATE   -> #["name"],
		StatemachPackage.Literals.CHOICE_POINT      -> #["name"],
		StatemachPackage.Literals.DEEP_HISTORY      -> #["name"],
		StatemachPackage.Literals.ENTRY_POINT       -> #["name"],
		StatemachPackage.Literals.EXIT_POINT        -> #["name"],
		StatemachPackage.Literals.INITIAL_POINT     -> #["name"],
		StatemachPackage.Literals.JUNCTION_POINT    -> #["name"],
		StatemachPackage.Literals.SIMPLE_STATE      -> #["name"],
		StatemachPackage.Literals.STATE             -> #["name"],
		StatemachPackage.Literals.TERMINATE_POINT   -> #["name"],
		StatemachPackage.Literals.TRANSITION        -> #["name"],
		UmlrtPackage.Literals.RT_MODEL              -> #["name"],
		UmlrtPackage.Literals.RT_PORT               -> #["name"]
	}

	static val INSTANCE = new XTUMLRTTransientValueService

	new () {
		super()
		// We pre-compute all transient features so that we don't spend time computing them when the
		// #isTransient method is invoked.
		//allCoreTransients = CORE_TRANSIENTS
		if (_createCache_getAllTransients.empty) {
			val epkgs = #[
				CommonPackage.eINSTANCE, 
				StatemachPackage.eINSTANCE, 
				StatemachextPackage.eINSTANCE, 
				UmlrtPackage.eINSTANCE
			]
			for (epkg : epkgs) {
				for (ecls : epkg.EClassifiers) {
					if (ecls instanceof EClass) {
						getAllTransients(ecls)
					}
				}
			}
		}
	}
	
	private def create new LinkedHashSet<String> getAllTransients(EClass ecls) {
		if (ecls !== null) {
			// Add all core transient features of this ecls if it has any
			val coreTransients = CORE_TRANSIENTS.get(ecls)
			if (coreTransients !== null) {
				addAll(coreTransients)
			}
			// Recursively add all transient features from each super-type of ecls
			// addAll(ecls.ESuperTypes.map[allTransients].flatten)
			for (superType : ecls.ESuperTypes) {
				addAll(superType.allTransients)
			}
			// Remove the features explicitly marked as non-transient for this particular ecls
			val nonTransients = NON_TRANSIENTS.get(ecls)
			if (nonTransients !== null) {
				for (feature : nonTransients) {
					remove(feature)
				}
			}
		}
	}
	
	static def getInstance() {
		INSTANCE
	}
	
	override isTransient(EObject owner, EStructuralFeature feature, int index) {
		val transientAttributes = owner.eClass.allTransients
		val isThisFeatureTransient = transientAttributes !== null && transientAttributes.contains(feature.name)
		isThisFeatureTransient
		||
		super.isTransient(owner, feature, index)
	}
	
}