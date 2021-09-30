/*******************************************************************************
 * Copyright (c) 2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
 
package org.eclipse.papyrusrt.xtumlrt.trans.to.uml.umlrt

import java.util.List
import java.util.Map
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.transaction.RecordingCommand
import org.eclipse.gmf.runtime.emf.type.core.IHintedType
import org.eclipse.papyrus.infra.core.resource.ModelSet
import org.eclipse.papyrus.uml.tools.model.UmlModel
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage
import org.eclipse.papyrusrt.xtumlrt.common.ActionCode
import org.eclipse.papyrusrt.xtumlrt.common.Annotation
import org.eclipse.papyrusrt.xtumlrt.common.AnnotationParameter
import org.eclipse.papyrusrt.xtumlrt.common.Attribute
import org.eclipse.papyrusrt.xtumlrt.common.Capsule
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement
import org.eclipse.papyrusrt.xtumlrt.common.Connector
import org.eclipse.papyrusrt.xtumlrt.common.DirectionKind
import org.eclipse.papyrusrt.xtumlrt.common.Entity
import org.eclipse.papyrusrt.xtumlrt.common.Generalization
import org.eclipse.papyrusrt.xtumlrt.common.Model
import org.eclipse.papyrusrt.xtumlrt.common.MultiplicityElement
import org.eclipse.papyrusrt.xtumlrt.common.Operation
import org.eclipse.papyrusrt.xtumlrt.common.Parameter
import org.eclipse.papyrusrt.xtumlrt.common.PrimitiveType
import org.eclipse.papyrusrt.xtumlrt.common.Protocol
import org.eclipse.papyrusrt.xtumlrt.common.ProtocolBehaviourFeatureKind
import org.eclipse.papyrusrt.xtumlrt.common.RedefinableElement
import org.eclipse.papyrusrt.xtumlrt.common.Signal
import org.eclipse.papyrusrt.xtumlrt.common.StructuredType
import org.eclipse.papyrusrt.xtumlrt.external.ExternalPackageManager
import org.eclipse.papyrusrt.xtumlrt.external.predefined.RTSModelLibraryUtils
import org.eclipse.papyrusrt.xtumlrt.interactions.Interaction
import org.eclipse.papyrusrt.xtumlrt.interactions.Lifeline
import org.eclipse.papyrusrt.xtumlrt.interactions.Message
import org.eclipse.papyrusrt.xtumlrt.interactions.MessageOccurrenceSpecification
import org.eclipse.papyrusrt.xtumlrt.statemach.ChoicePoint
import org.eclipse.papyrusrt.xtumlrt.statemach.CompositeState
import org.eclipse.papyrusrt.xtumlrt.statemach.DeepHistory
import org.eclipse.papyrusrt.xtumlrt.statemach.EntryPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.ExitPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.InitialPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.JunctionPoint
import org.eclipse.papyrusrt.xtumlrt.statemach.SimpleState
import org.eclipse.papyrusrt.xtumlrt.trans.to.uml.IXtumlrt2UMLTranslator
import org.eclipse.papyrusrt.xtumlrt.umlrt.PortKind
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTModel
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPassiveClass
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPort
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTTrigger
import org.eclipse.uml2.uml.AggregationKind
import org.eclipse.uml2.uml.CallEvent
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Collaboration
import org.eclipse.uml2.uml.ConnectableElement
import org.eclipse.uml2.uml.ConnectorEnd
import org.eclipse.uml2.uml.Dependency
import org.eclipse.uml2.uml.Element
import org.eclipse.uml2.uml.Event
import org.eclipse.uml2.uml.InteractionFragment
import org.eclipse.uml2.uml.MessageEnd
import org.eclipse.uml2.uml.MessageSort
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.OpaqueBehavior
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.Port
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.Pseudostate
import org.eclipse.uml2.uml.PseudostateKind
import org.eclipse.uml2.uml.Region
import org.eclipse.uml2.uml.State
import org.eclipse.uml2.uml.StateMachine
import org.eclipse.uml2.uml.Transition
import org.eclipse.uml2.uml.Trigger
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.UMLPackage
import org.eclipse.uml2.uml.Vertex
import org.eclipse.uml2.uml.VisibilityKind

import static extension org.eclipse.papyrusrt.umlrt.core.utils.PackageUtils.*
import static extension org.eclipse.papyrusrt.xtumlrt.trans.to.uml.umlrt.CreateElementUtil.*
import static extension org.eclipse.papyrusrt.xtumlrt.trans.to.uml.umlrt.Xtumlrt2UMLTranslatorUtils.*
import static extension org.eclipse.papyrusrt.xtumlrt.aexpr.uml.XTUMLRTBoundsEvaluator.*

class Xtumlrt2UMLTranslator implements IXtumlrt2UMLTranslator {

	private org.eclipse.uml2.uml.Model umlModel
	private ModelSet modelSet
	private Map<CommonElement, EObject> translated

	override generate(RTModel model) {
		initializeRequiredLibraries
		val uri = model.eResource.URI.trimFileExtension().appendFileExtension("di")
		val registry = createServicesRegistry
		if(uri.modelExists) {
			val result = overwrite
			if(result != Status.OK){
				return result
			}
		}
		
		modelSet = registry.createPapyrusModel(uri)

		// Start transformation
		val command = new RecordingCommand(modelSet.transactionalEditingDomain) {
			override protected doExecute() {
				translated = newHashMap
				transform(model)
				transformAllAnnotations(model)
			}
		}

		modelSet.transactionalEditingDomain.commandStack.execute(command)
		modelSet.save(new NullProgressMonitor)

		registry.disposeRegistry
		return Status.OK
	}
	
	public def getTranslated(CommonElement element)
	{
		translated.get(element)
	}

	private def void transformAllAnnotations(org.eclipse.papyrusrt.xtumlrt.common.NamedElement originalElement) {
		val translatedElement = getTranslated(originalElement)
		if (translatedElement instanceof Element) {
			transformAnnotations(originalElement.annotations, translatedElement)
			for (ownedElement : (originalElement as EObject).eContents) {
				if (ownedElement instanceof org.eclipse.papyrusrt.xtumlrt.common.NamedElement) {
					transformAllAnnotations(ownedElement)
				}
			}
		}
	}

	/**
	 * Set stereotypes from annotations.
	 */
	def private transformAnnotations(List<Annotation> annotations, Element element) {
		for (anno : annotations) {
			anno.transformAnnotation(element)
		}
	}

	/**
	 * Set stereotypes from annotations.
	 */
	def private transformAnnotation(Annotation annotation, Element element) {
		val stereotypes = element.applicableStereotypes
		for (st : stereotypes) {
			if (st.name == annotation.name && !element.appliedStereotypes.contains(st)) {
				element.applyStereotype(st);
				for (param : annotation.parameters) {
					element.setValue(st, param.name, transformAnnotationParameterValue(annotation, param))
				}
			}
		}
	}
	
	def transformAnnotationParameterValue(Annotation annotation, AnnotationParameter param) {
		param.value
	}

	def getUMLModel() {
		if (umlModel == null) {
			if (modelSet !== null) {
				val papyrusModel = modelSet.getModel(UmlModel.MODEL_ID) as UmlModel
				umlModel = papyrusModel.lookupRoot as org.eclipse.uml2.uml.Model
			}
		}
		umlModel
	}

	def dispatch transform(Object object) {
	}

	def dispatch create result : getUMLModel transform(RTModel model) {
		result.name = model.name
		val mapped = model.protocols.map[transform.nearestPackage].filter(PackageableElement).toList
		mapped.addAll(model.entities.map[transform].filter(PackageableElement).toList)
		mapped.addAll(model.packages.map[transform].filter(Package).toList)
		mapped.addAll(model.interactions.map[transform].filter(PackageableElement).toList)
		result.eSet(UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT, mapped)
		result.createSequenceDiagrams
		translated.put(model, result)
	}

	def dispatch create result : getUMLModel.createNestedPackage(pkg.elementName) transform(org.eclipse.papyrusrt.xtumlrt.common.Package pkg) {
		result.name = pkg.name
		val mapped = pkg.protocols.map[transform.nearestPackage].filter(PackageableElement).toList
		mapped.addAll(pkg.entities.map[transform].filter(PackageableElement).toList)
		mapped.addAll(pkg.packages.map[transform].filter(Package).toList)
		result.eSet(UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT, mapped)
		translated.put(pkg, result)
	}

	def dispatch transform(PrimitiveType type) {
		val result = type.name.primitiveType
		translated.put(type, result)
		result
	}

	def dispatch create result : getUMLModel.createElement(UMLRTElementTypesEnumerator.CAPSULE, capsule.name) as org.eclipse.uml2.uml.Class 
	transform(Capsule capsule) {
		capsule.transformBasicClass(result)

		// handle ports
		val mappedPorts = capsule.ports.map[transform].filter(Port)
		result.ownedPorts.addAllIfValid(mappedPorts)

		// handle parts
		val mappedParts = capsule.parts.map[transform].filter(Property)
		result.ownedAttributes.addAllIfValid(mappedParts)

		// handle connections
		val mappedConnections = capsule.connectors.map[transform].filter(org.eclipse.uml2.uml.Connector)
		result.ownedConnectors.addAllIfValid(mappedConnections)

		translated.put(capsule, result)
	}

	def dispatch create result : UMLFactory.eINSTANCE.createConnector transform(Connector connector) {
		val owner = connector.eContainer.transform as Class
		owner.ownedConnectors.add(result)
		result.applyStereotype(UMLRealTimePackage.Literals.RT_CONNECTOR)

		result.name = connector.name
		val mappedEnds = connector.ends.map[e|e.transform].filter(typeof(ConnectorEnd))
		result.ends.addAll(mappedEnds)
		translated.put(connector, result)
	}

	def dispatch create result : UMLFactory.eINSTANCE.createConnectorEnd transform(
		org.eclipse.papyrusrt.xtumlrt.common.ConnectorEnd end) {
		result.role = end.role.transform as Port
		result.partWithPort = end.partWithPort?.transform as Property
		translated.put(end, result)
	}

	def dispatch create result : UMLFactory.eINSTANCE.createProperty transform(CapsulePart part) {
		val owner = part.eContainer.transform() as Class
		owner.ownedAttributes.add(result)
		result.name = part.name
		result.applyStereotype(UMLRealTimePackage.Literals.CAPSULE_PART)
		result.type = part.type.transform as Type
		var kind = AggregationKind.COMPOSITE_LITERAL
		setReplication(part, result)
		switch (part.kind) {
			case OPTIONAL: {
				result.lower = 0
			}
			case PLUGIN: {
				kind = AggregationKind.SHARED_LITERAL
				result.lower = 0
			}
		}
		result.aggregation = kind
		translated.put(part, result)
	}

	def dispatch create result : UMLFactory.eINSTANCE.createPort transform(
		RTPort port) {
		val owner = port.eContainer.transform() as Class
		owner.ownedPorts.add(result)
		result.name = port.name
		val st = result.applyStereotype(UMLRealTimePackage.Literals.RT_PORT) as org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort
		result.type = port.type.transform.realType as Type
		result.isConjugated = port.conjugate
		result.isComposite = true
		st.registration = PortRegistrationType.getByName(port.registration.getName)
		st.registrationOverride = port.registrationOverride
		if (port.kind == PortKind.EXTERNAL) {
			result.isService = true
			result.isBehavior = true
			st.isWired = true
			st.isPublish = false
			result.visibility = VisibilityKind.PUBLIC_LITERAL
		} else if (port.kind == PortKind.INTERNAL) {
			result.isService = false
			result.isBehavior = true
			st.isWired = true
			st.isPublish = false
			result.visibility = VisibilityKind.PROTECTED_LITERAL
		} else if (port.kind == PortKind.RELAY) {
			result.isService = true
			result.isBehavior = false
			st.isWired = true
			st.isPublish = false
			result.visibility = VisibilityKind.PUBLIC_LITERAL
		} else if (port.kind == PortKind.SAP) {
			result.isService = false
			result.isBehavior = true
			st.isWired = false
			st.isPublish = false
			result.visibility = VisibilityKind.PROTECTED_LITERAL
		} else if (port.kind == PortKind.SPP) {
			result.isService = true
			result.isBehavior = true
			st.isWired = false
			st.isPublish = true
			result.visibility = VisibilityKind.PUBLIC_LITERAL
		}
		setReplication(port, result)
		translated.put(port, result)
	}

	def dispatch create UMLFactory.eINSTANCE.createInteraction transform(Interaction interaction) {
		name = interaction.name
		val mappedLifelines = interaction.lifelines.map[transform].filter(typeof(org.eclipse.uml2.uml.Lifeline))
		lifelines.addAllIfValid(mappedLifelines)
		val mappedMessages = interaction.messages.map[transform].filter(typeof(org.eclipse.uml2.uml.Message))
		messages.addAllIfValid(mappedMessages)
		val mappedFragments = interaction.fragments.map[transform].filter(typeof(InteractionFragment))
		fragments.addAllIfValid(mappedFragments)
		translated.put(interaction, it)
	}
	
	def dispatch create UMLFactory.eINSTANCE.createLifeline transform(Lifeline lifeline) {
		name = lifeline.name
		val repr = lifeline.represents
		represents = if (repr !== null) repr.transform as ConnectableElement // We probably need to validate here
		translated.put(lifeline, it)
	}

	def dispatch create UMLFactory.eINSTANCE.createMessage transform(Message message) {
		name = message.name
		val sig = message.signature
		val send = message.sendEvent
		val recv = message.receiveEvent
		messageSort = 
			switch (message.messageSort) {
				case org.eclipse.papyrusrt.xtumlrt.interactions.MessageSort.ASYNCH_CALL:    MessageSort.ASYNCH_CALL_LITERAL
				case org.eclipse.papyrusrt.xtumlrt.interactions.MessageSort.SYNCH_CALL:     MessageSort.SYNCH_CALL_LITERAL
				case org.eclipse.papyrusrt.xtumlrt.interactions.MessageSort.ASYNCH_SIGNAL:  MessageSort.ASYNCH_SIGNAL_LITERAL
				case org.eclipse.papyrusrt.xtumlrt.interactions.MessageSort.REPLY:          MessageSort.REPLY_LITERAL
				case org.eclipse.papyrusrt.xtumlrt.interactions.MessageSort.CREATE_MESSAGE: MessageSort.CREATE_MESSAGE_LITERAL
				case org.eclipse.papyrusrt.xtumlrt.interactions.MessageSort.DELETE_MESSAGE: MessageSort.DELETE_MESSAGE_LITERAL
				default: MessageSort.ASYNCH_SIGNAL_LITERAL
			}
		if (sig !== null) {
			val callEvent = sig.transform as CallEvent
			signature = callEvent.operation
		}
		if(send !== null) {
			sendEvent = send.transform as MessageEnd
			sendEvent.message = it
		}
		if (recv !== null){
			receiveEvent =  recv.transform as MessageEnd
			receiveEvent.message = it
		}
		translated.put(message, it)
	}

	def dispatch create UMLFactory.eINSTANCE.createMessageOccurrenceSpecification transform(MessageOccurrenceSpecification messageOccurrence) {
		name = messageOccurrence.name
		val mappedCovered = messageOccurrence.covered.map[transform].filter(typeof(org.eclipse.uml2.uml.Lifeline))
		covereds.addAllIfValid(mappedCovered)
		translated.put(messageOccurrence, it)
	}

	def dispatch private getRealType(EObject element) {
		return element
	}

	/**
	 * Find protocol if the type is protocol container
	 */
	def dispatch private getRealType(Package container) {
		return container.getMember(container.name, false, UMLPackage.Literals.COLLABORATION)
	}

	def dispatch create result: owner.createElement(type, name) createNewElement(EObject owner, IHintedType type,
		String name) {
	}

	def dispatch createNewElement(Object owner, IHintedType type, String name) {
	}

	def dispatch create result : {
		if (RTSModelLibraryUtils.isTextualSystemElement(protocol)) {
			protocol.name.getRTSProtocol
		}
		else {
			createProtocol(protocol)
		}
	}
	transform(Protocol protocol) {
		translated.put(protocol, result)
	}

	def private create result: getUMLModel.createElement(UMLRTElementTypesEnumerator.PROTOCOL, protocol.name) as Collaboration createProtocol(
		Protocol protocol) {

		// create call events
		protocol.protocolBehaviourFeatures.map[transform].filter(CallEvent).toList
		if (protocol.redefines !== null) {
			val redefinedProtocol = protocol.redefines.transform as Collaboration
			(result as Collaboration).redefinedClassifiers.add(redefinedProtocol)
		}

		//protocol.annotations.transformAnnotations(result)
	}

	def dispatch create { createProtocolMessage_(signal) }
	transform(Signal signal) {
		translated.put(signal, it)
	}
	
	private def createProtocolMessage_(Signal signal) {
		val protocol = signal.eContainer.transform as Collaboration

		var elementType = UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_INOUT
		switch (signal.kind.RTMessageKind) {
			case IN: {
				elementType = UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_IN
			}
			case OUT: {
				elementType = UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_OUT
			}
			default: {
			}
		}

		val container = protocol.nearestPackage

		if (RTSModelLibraryUtils.isTextualSystemElement(signal)) {
			for (pe : container.packagedElements) {
				if (pe instanceof CallEvent) {
					val op = (pe as CallEvent).operation
					if (op.name == signal.name) {
						return pe
					}
				}
			}
			return null
		}

		val operation = protocol.createProtocolMessage(elementType, signal) as org.eclipse.uml2.uml.Operation

		for (e : container.packagedElements) {
			if (e instanceof CallEvent) {
				if (e.operation === operation) {
					//signal.annotations.transformAnnotations(e)
					return e
				}
			}
		}
	}

	def private create result :  protocol.createElement(type, signal.name) as org.eclipse.uml2.uml.Operation createProtocolMessage(
		Collaboration protocol, IHintedType type, Signal signal) {
		// parameters
		signal.parameters.map[result.transformParameter(it)].filter(org.eclipse.uml2.uml.Parameter).toList
	}

	def private create result : op.createOwnedParameter(param.name, param.type?.transform as Type) transformParameter(
		org.eclipse.uml2.uml.Operation op, Parameter param) {
		result.direction = param.direction.directionKind
	}

	def private ParameterDirectionKind getDirectionKind(DirectionKind kind) {
		switch (kind) {
			case IN: {
				return ParameterDirectionKind.IN_LITERAL
			}
			case OUT: {
				return ParameterDirectionKind.OUT_LITERAL
			}
			case IN_OUT: {
				return ParameterDirectionKind.INOUT_LITERAL
			}
			default: {
				return ParameterDirectionKind.IN_LITERAL
			}
		}
	}

	def private RTMessageKind getRTMessageKind(ProtocolBehaviourFeatureKind kind) {
		switch (kind) {
			case IN: {
				return RTMessageKind.IN
			}
			case OUT: {
				return RTMessageKind.OUT
			}
			case INOUT: {
				return RTMessageKind.IN_OUT
			}
			default: {
				return RTMessageKind.IN
			}
		}
	}

	def private transformBasicClass(Entity entity, Class clazz) {
		entity.transformStructuredType(clazz)
		val sm = entity.behaviour?.transform
		if (sm !== null) {
			clazz.ownedBehaviors.add(
				sm as StateMachine)
		}
	}

	def private create result : owner.createElement(UMLRTElementTypesEnumerator.RT_STATE_MACHINE) as StateMachine transformStateMachine(
		EObject owner, org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine sm) {

		result.name = sm.elementName

		if (!result.regions.empty) {
			// Use default region created by tooling
			val region = result.regions.get(0)
			while (!region.subvertices.empty) {
				// Remove default initial entry point if created by tooling
				region.subvertices.get(0).destroy
			}
			while(!region.transitions.empty){
				// remove default initial transition created by tooling
				region.transitions.get(0).destroy
			}
			sm.top.transformRegion(region)
			region.name = region.eClass.name
		} else {
			val region = sm.top.transformRegion
			result.regions.add(region)
		}
		if (sm.redefines !== null) {
			result.redefinedElements.add(sm.redefines.transform as org.eclipse.uml2.uml.RedefinableElement)
		}
	}

	def dispatch EObject transform(org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine sm) {
		val owner = sm.eContainer.transform as Class
		val result = owner.transformStateMachine(sm)
		translated.put(sm, result)
		return result
	}

	def dispatch String getElementName(org.eclipse.papyrusrt.xtumlrt.common.NamedElement element) {
		return element.name
	}

	def dispatch String getElementName(Object element) {
		""
	}

	def private Region transformRegion(CompositeState cs) {
		val owner = cs.eContainer.transform() as Element
		if (owner instanceof StateMachine) {
			val sm = owner as StateMachine
			if (!sm.regions.empty) {
				return sm.regions.get(0)
			}
		}
		val region = owner.transformRegion(cs)
		return region
	}

	def private create result : owner.createElement(UMLRTElementTypesEnumerator.RT_REGION) as Region transformRegion(
		EObject owner, CompositeState cs) {
		result.name = cs.elementName
		cs.transformRegion(result)
	}

	def private void transformRegion(CompositeState compositeState, Region newRegion) {

		newRegion.subvertices.add(compositeState.initial.transform as Pseudostate)
		val deepHistory = compositeState.deepHistory?.transform
		if (deepHistory !== null) {
			newRegion.subvertices.add(deepHistory as Pseudostate)
		}
		val mappedChoicePoints = compositeState.choicePoints.map[transform].filter(Pseudostate)
		newRegion.subvertices.addAll(mappedChoicePoints)

		val mappedJunctionPoints = compositeState.junctionPoints.map[transform].filter(Pseudostate)
		newRegion.subvertices.addAll(mappedJunctionPoints)

		val mappedStates = compositeState.substates.map[transform].filter(State)
		newRegion.subvertices.addAll(mappedStates)

		val mappedTransitions = compositeState.transitions.map[transform].filter(Transition)
		newRegion.transitions.addAll(mappedTransitions)

		if (compositeState.redefines !== null) {
			newRegion.redefinedElements.add(
				compositeState.redefines.transform as org.eclipse.uml2.uml.RedefinableElement)
		}
	}

	def dispatch create result: UMLFactory.eINSTANCE.createTransition transform(
		org.eclipse.papyrusrt.xtumlrt.statemach.Transition transition) {
		result.name = transition.elementName
		val sVertex = transition.sourceVertex.transform
		result.source = sVertex as Vertex
		val tVertex = transition.targetVertex.transform
		result.target = tVertex as Vertex

		// triggers
		val mappedTriggers = transition.triggers.map[transform].filter(Trigger)
		result.triggers.addAll(mappedTriggers)

		val actionChain = transition.actionChain
		if (actionChain !== null) {
			val actions = actionChain.actions
			if (actions !== null) {
				val mappedActions = actions.map[transform].filter(OpaqueBehavior)
				if (mappedActions !== null && !mappedActions.empty) {
					result.effect = mappedActions.head
				}
			}
		}
		translated.put(transition, result)
	}

	def dispatch create result: UMLFactory.eINSTANCE.createTrigger transform(RTTrigger trigger) {
		result.event = trigger.signal.transform as Event
		val mappedPorts = trigger.ports.map[transform].filter(Port)
		result.ports.addAll(mappedPorts)
		translated.put(trigger, result)
	}

	def private create result: owner.createElement(UMLRTElementTypesEnumerator.RT_STATE) as State transformState(
		EObject owner, org.eclipse.papyrusrt.xtumlrt.statemach.State state) {

		result.name = state.elementName

		val entryAction = state.entryAction?.transform
		if (entryAction !== null) {
			(entryAction as OpaqueBehavior).name = state.entryAction.name
			result.eSet(UMLPackage.Literals.STATE__ENTRY, entryAction)
		}
		val exitAction = state.exitAction?.transform
		if (exitAction !== null) {
			(exitAction as OpaqueBehavior).name = state.exitAction.name
			result.eSet(UMLPackage.Literals.STATE__EXIT, exitAction)
		}
		val mappedEntryPoints = state.entryPoints.map[transform].filter(Pseudostate)
		result.connectionPoints.addAll(mappedEntryPoints)

		val mappedExitPoints = state.exitPoints.map[transform].filter(Pseudostate)
		result.connectionPoints.addAll(mappedExitPoints)

		if (state.redefines !== null) {
			result.redefinedElements.add(state.redefines.transform as org.eclipse.uml2.uml.RedefinableElement)
		}
	}

	def dispatch create result: UMLFactory.eINSTANCE.createOpaqueBehavior transform(ActionCode action) {
		val source = action.source
		if (source !== null) {
			result.languages.add("C++")
			result.bodies.add(source);
		}
	}

	def dispatch State transform(SimpleState st) {
		return st.transformState
	}

	def dispatch State transform(CompositeState st) {
		return st.transformState
	}

	def private State transformState(org.eclipse.papyrusrt.xtumlrt.statemach.State st) {
		var State result = null
		if (st.eContainer instanceof CompositeState) {
			val owner = (st.eContainer as CompositeState).transformRegion
			result = owner.transformState(st)

		} else {
			val owner = st.eContainer.transform
			result = owner.transformState(st)
		}

		translated.put(st, result)
		return result
	}

	def dispatch create result : UMLFactory.eINSTANCE.createPseudostate transform(
		org.eclipse.papyrusrt.xtumlrt.statemach.Pseudostate st) {
		if (st.eContainer instanceof CompositeState) {
			val owner = (st.eContainer as CompositeState).transformRegion
			owner.subvertices.add(result)
			result.applyStereotype(UMLRTStateMachinesPackage.Literals.RT_PSEUDOSTATE)
		}
		result.name = st.name
		if (st instanceof InitialPoint) {
			result.kind = PseudostateKind.INITIAL_LITERAL
		} else if (st instanceof DeepHistory) {
			result.kind = PseudostateKind.DEEP_HISTORY_LITERAL
		} else if (st instanceof EntryPoint) {
			result.kind = PseudostateKind.ENTRY_POINT_LITERAL
		} else if (st instanceof ExitPoint) {
			result.kind = PseudostateKind.EXIT_POINT_LITERAL
		} else if (st instanceof ChoicePoint) {
			result.kind = PseudostateKind.CHOICE_LITERAL
		} else if (st instanceof JunctionPoint) {
			result.kind = PseudostateKind.JUNCTION_LITERAL
		}
		
		translated.put(st, result)
	}

	def private transformStructuredType(StructuredType type, Class clazz) {
		// Redefined element
		val redefine = type.redefines?.transform
		if (redefine !== null) {
			clazz.redefinedClassifiers.add(redefine as Classifier)
		}

		// Generalization
		val generalizations = type.generalizations?.map[g|g.transform].filter(typeof(Classifier)).toList
		if (generalizations !== null && !generalizations.empty) {
			for (gen : generalizations) {
				clazz.eSet(UMLPackage.Literals.CLASSIFIER__GENERALIZATION, gen)
			}
		}

		// Dependencies
		val dependencies = type.dependencies?.map[g|g.transform].filter(typeof(Dependency)).toList
		if (!dependencies.empty) {
			clazz.eSet(UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT, dependencies)
		}

		// Attributes
		val attributes = type.attributes.map[a|a.transform].toList
		if (!attributes.empty) {
			clazz.eSet(UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE, attributes)
		}

		// Operations
		val operations = type.operations.map[a|a.transform].toList
		if (!operations.empty) {
			clazz.eSet(UMLPackage.Literals.CLASS__OWNED_OPERATION, operations)
		}
	}

	def dispatch create result : UMLFactory.eINSTANCE.createOperation transform(Operation op) {
		result.name = op.name
		val returnParam = op.returnType.transform as org.eclipse.uml2.uml.Parameter
		returnParam.direction = ParameterDirectionKind.RETURN_LITERAL
		returnParam.name = "ReturnParamerter"
		result.ownedParameters.add(returnParam)
		val mappedParams = op.parameters.map[p | p.transform].filter(typeof(org.eclipse.uml2.uml.Parameter))
		result.ownedParameters.addAll(mappedParams)
		
		// user code
		if(op.body != null){
			val method = (op.eContainer.transform as Class).createOwnedBehavior(null, UMLPackage.Literals.OPAQUE_BEHAVIOR) as OpaqueBehavior
			method.bodies.add(((op.body) as ActionCode).source)
			method.languages.add("C++")
			result.methods.add(method)
		}
		
		result.isAbstract = op.abstract
		result.isQuery = op.query
		translated.put(op, result)
	}
	
	def dispatch create result : UMLFactory.eINSTANCE.createParameter transform(Parameter param) {
		result.name = param.name
		result.type = param.type.transform as Type
		result.direction = ParameterDirectionKind.getByName(param.direction.getName.toLowerCase)
		translated.put(param, result)
	}

	def dispatch create result : UMLFactory.eINSTANCE.createProperty transform(Attribute attr) {
		result.name = attr.name
		val type = attr.type.transform as Type
		if (type !== null) {
			result.type = type
		}
		setReplication(attr, result)
		translated.put(attr, result)
	}

	def dispatch create result : UMLFactory.eINSTANCE.createGeneralization transform(Generalization gen) {
		result.general = gen.^super.transform as Classifier
		translated.put(gen, result)
	}

	def dispatch create result : UMLFactory.eINSTANCE.createDependency transform(
		org.eclipse.papyrusrt.xtumlrt.common.Dependency dep) {
		result.suppliers.add(dep.supplier.transform as NamedElement)
		result.clients.add(dep.client.transform as NamedElement)
		translated.put(dep, result)
	}

	def dispatch transform(RedefinableElement model) {
	}

	def dispatch create result : UMLFactory.eINSTANCE.createClass transform(RTPassiveClass clazz) {
		result.name = clazz.name
		clazz.transformBasicClass(result)
		translated.put(clazz, result)
	}

	def private <T> addAllIfValid(List<T> list, Iterable<T> elements) {
		val itor = elements.iterator
		while (itor.hasNext) {
			list.addIfValid(itor.next)
		}
	}

	def private <T> addIfValid(List<T> list, T element) {
		if (element !== null && !list.contains(element)) {
			list.add(element)
		}
	}

	def private setReplication(MultiplicityElement element, org.eclipse.uml2.uml.MultiplicityElement umlElement) {
		val lower = evaluateBound( element, element.lowerBound, false )
		var upper = evaluateBound( element, element.upperBound, true )
		if (lower > upper && upper != -1) {
			upper = lower
		}
		umlElement.lower = lower
		umlElement.upper = upper
	}
	
}
