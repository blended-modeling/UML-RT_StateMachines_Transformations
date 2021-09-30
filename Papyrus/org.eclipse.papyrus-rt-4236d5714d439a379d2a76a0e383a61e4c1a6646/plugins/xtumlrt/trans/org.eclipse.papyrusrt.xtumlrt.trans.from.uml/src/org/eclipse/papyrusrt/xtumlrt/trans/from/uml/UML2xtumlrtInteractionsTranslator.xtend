/*******************************************************************************
* Copyright (c) 2015-2016 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.trans.from.uml

import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement
import org.eclipse.papyrusrt.xtumlrt.common.Connector
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.interactions.Gate
import org.eclipse.papyrusrt.xtumlrt.interactions.Interaction
import org.eclipse.papyrusrt.xtumlrt.interactions.InteractionsFactory
import org.eclipse.papyrusrt.xtumlrt.interactions.InteractionFragment
import org.eclipse.papyrusrt.xtumlrt.interactions.Lifeline
import org.eclipse.papyrusrt.xtumlrt.interactions.Message
import org.eclipse.papyrusrt.xtumlrt.interactions.MessageEnd
import org.eclipse.papyrusrt.xtumlrt.interactions.MessageOccurrenceSpecification
import org.eclipse.papyrusrt.xtumlrt.interactions.MessageSort
import org.eclipse.papyrusrt.xtumlrt.interactions.OccurrenceSpecification
import org.eclipse.papyrusrt.xtumlrt.trans.from.uml.UML2xtumlrtTranslator
import org.eclipse.uml2.uml.ConnectableElement
import org.eclipse.uml2.uml.Element

import static extension org.eclipse.papyrusrt.xtumlrt.util.GeneralUtil.*
import org.eclipse.papyrusrt.xtumlrt.interactions.MessageKind

/**
 * @author Ernesto Posse
 */
class UML2xtumlrtInteractionsTranslator extends UML2xtumlrtTranslator {

    UML2xtumlrtModelTranslator uml2xtumlrtTranslator

    new (UML2xtumlrtModelTranslator uml2xtumlrtTranslator)
    {
        this.uml2xtumlrtTranslator = uml2xtumlrtTranslator
    }

	dispatch def CommonElement translate( Element element ) {
	}

	dispatch def Interaction
	create InteractionsFactory.eINSTANCE.createInteraction 
	translate(org.eclipse.uml2.uml.Interaction element) {
		name = element.name
		for (gate : element.formalGates) {
			formalGates.addIfNotPresent(translateElement(gate) as Gate)
		}
		for (lifeline : element.lifelines) {
			lifelines.addIfNotPresent(translateElement(lifeline) as Lifeline)
		}
		for (message : element.messages) {
			messages.addIfNotPresent(translateElement(message) as Message)
		}
		for (fragment : element.fragments) {
			fragments.addIfNotPresent(translateElement(fragment) as InteractionFragment)
		}
	}
	
	dispatch def Gate
	create InteractionsFactory.eINSTANCE.createGate
	translate(org.eclipse.uml2.uml.Gate element) {
		translateMessageEnd(element, it)
	}

	dispatch def Lifeline
	create InteractionsFactory.eINSTANCE.createLifeline
	translate(org.eclipse.uml2.uml.Lifeline element) {
		name = element.name
		represents = uml2xtumlrtTranslator.translateFeature(element, "represents", ConnectableElement, NamedElement, true) as NamedElement
	}
	
	dispatch def Message
	create InteractionsFactory.eINSTANCE.createMessage
	translate(org.eclipse.uml2.uml.Message element) {
		name = element.name
		messageSort = translateEnumFeature(element, "messageSort", org.eclipse.uml2.uml.MessageSort, MessageSort) as MessageSort
		signature = uml2xtumlrtTranslator.translateFeature(element, "signature", org.eclipse.uml2.uml.NamedElement, NamedElement, true) as NamedElement
		connector = uml2xtumlrtTranslator.translateFeature(element, "connector", org.eclipse.uml2.uml.Connector, Connector, true) as Connector
		sendEvent = translateFeature(element, "sendEvent", org.eclipse.uml2.uml.MessageEnd, MessageEnd, true) as MessageEnd
		receiveEvent = translateFeature(element, "receiveEvent", org.eclipse.uml2.uml.MessageEnd, MessageEnd, true) as MessageEnd
	}
	
	dispatch def MessageOccurrenceSpecification
	create InteractionsFactory.eINSTANCE.createMessageOccurrenceSpecification
	translate(org.eclipse.uml2.uml.MessageOccurrenceSpecification element) {
		translateMessageEnd(element, it)
		translateOccurrenceSpecification(element, it)
	}
	
	protected def translateMessageEnd(org.eclipse.uml2.uml.MessageEnd originalElement, MessageEnd newElement) {
		newElement.name = originalElement.name
	}
	
	protected def translateOccurrenceSpecification(org.eclipse.uml2.uml.OccurrenceSpecification originalElement, OccurrenceSpecification newElement) {
		translateInteractionFragment(originalElement, newElement)
	}
	
	protected def translateInteractionFragment(org.eclipse.uml2.uml.InteractionFragment originalElement, InteractionFragment newElement) {
		newElement.name = originalElement.name
		for (lifeline : originalElement.covereds) {
			newElement.covered.addIfNotNull(translateElement(lifeline) as Lifeline)
		}
	}
	
	dispatch def translateEnum( org.eclipse.uml2.uml.MessageSort messageSort) {
		switch (messageSort) {
			case ASYNCH_SIGNAL_LITERAL:  MessageSort.ASYNCH_SIGNAL
			case ASYNCH_CALL_LITERAL:    MessageSort.ASYNCH_CALL
			case SYNCH_CALL_LITERAL:     MessageSort.SYNCH_CALL
			case REPLY_LITERAL:          MessageSort.REPLY
			case CREATE_MESSAGE_LITERAL: MessageSort.CREATE_MESSAGE
			case DELETE_MESSAGE_LITERAL: MessageSort.DELETE_MESSAGE
		}
	}
	
	dispatch def translateEnum( org.eclipse.uml2.uml.MessageKind messageKind) {
		switch (messageKind) {
			case COMPLETE_LITERAL: MessageKind.COMPLETE
			case FOUND_LITERAL:    MessageKind.FOUND
			case LOST_LITERAL:     MessageKind.LOST
			default:               MessageKind.UNKNOWN
		}
	}
	
	override resetTranslateCache(EObject element) {
		val key = newArrayList( element )
		_createCache_translate.remove( key )
	}
		
}