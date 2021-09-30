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

package org.eclipse.papyrusrt.umlrt.core.internal.types.advice;

import static org.eclipse.papyrusrt.umlrt.core.utils.UMLRTCommandUtils.destroy;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.advice.RTStateMachineEditHelperAdvice;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTCommandUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Classifier;

/**
 * Advice for reconfiguration of state machines in a capsule hierarchy when
 * inheritance relationships change.
 */
public class StateMachineInheritanceAdvice extends AbstractEditHelperAdvice {

	public StateMachineInheritanceAdvice() {
		super();
	}

	@Override
	protected ICommand getBeforeCreateCommand(CreateElementRequest request) {
		ICommand result = null;

		if (ElementTypeUtils.isTypeCompatible(request.getElementType(), UMLElementTypes.STATE_MACHINE)) {
			result = getBeforeCreateStateMachineCommand(request);
		}

		return result;
	}

	@Override
	protected ICommand getAfterCreateCommand(CreateElementRequest request) {
		ICommand result = null;

		if (ElementTypeUtils.isTypeCompatible(request.getElementType(), UMLElementTypes.STATE_MACHINE)) {
			result = getAfterCreateStateMachineCommand(request);
		}

		return result;
	}

	@Override
	protected ICommand getBeforeCreateRelationshipCommand(CreateRelationshipRequest request) {
		ICommand result = null;

		if (ElementTypeUtils.isTypeCompatible(request.getElementType(), UMLElementTypes.GENERALIZATION)) {
			result = getBeforeCreateGeneralizationCommand(request);
		}

		return result;
	}

	protected ICommand getBeforeCreateStateMachineCommand(CreateElementRequest request) {
		ICommand result = null;

		if ((request.getContainer() instanceof BehavioredClassifier)
				&& CapsuleUtils.isCapsule((Classifier) request.getContainer())) {

			UMLRTCapsule general = UMLRTCapsule.getInstance((org.eclipse.uml2.uml.Class) request.getContainer());

			if (general != null) {
				// Then all of the default state machines in the specific's hierarchy will replace
				// their contents with what they will inherit. Only needing to concern ourselves
				// with those that heretofore have been root state machine definitions
				result = deleteDefaultSMContents(request,
						general.getHierarchy().skip(1L)); // Don't worry about the one that's creating the state machine
			}
		}

		return result;
	}

	protected ICommand getAfterCreateStateMachineCommand(CreateElementRequest request) {
		ICommand result = null;

		if ((request.getContainer() instanceof BehavioredClassifier)
				&& CapsuleUtils.isCapsule((Classifier) request.getContainer())) {

			UMLRTCapsule general = UMLRTCapsule.getInstance((org.eclipse.uml2.uml.Class) request.getContainer());

			if (general != null) {
				// Then all of the default state machines in the specific's hierarchy will replace
				// their contents with what they will inherit. Only needing to concern ourselves
				// with those that heretofore have been root state machine definitions
				result = initializeContents(request,
						general.getHierarchy().skip(1L)); // Don't worry about the one that's creating the state machine
			}
		}

		return result;
	}

	protected ICommand getBeforeCreateGeneralizationCommand(CreateRelationshipRequest request) {
		ICommand result = null;

		if ((request.getSource() instanceof Classifier)
				&& (request.getTarget() instanceof Classifier)
				&& CapsuleUtils.isCapsule((Classifier) request.getSource())
				&& CapsuleUtils.isCapsule((Classifier) request.getTarget())) {

			UMLRTCapsule specific = UMLRTCapsule.getInstance((org.eclipse.uml2.uml.Class) request.getSource());
			UMLRTCapsule general = UMLRTCapsule.getInstance((org.eclipse.uml2.uml.Class) request.getTarget());

			if ((specific != null) && (general != null)) {
				// Will the specific be inheriting a state machine?
				if (general.getStateMachine() != null) {
					// Then all of the default state machines in the specific's hierarchy will replace
					// their contents with what they will inherit. Only needing to concern ourselves
					// with those that heretofore have been root state machine definitions
					result = deleteDefaultSMContents(request, specific.getHierarchy());
				}
			}
		}

		return result;
	}

	ICommand deleteDefaultSMContents(IEditCommandRequest trigger, Stream<UMLRTCapsule> capsuleHierarchy) {
		return capsuleHierarchy
				.map(UMLRTCapsule::getStateMachine)
				.filter(Objects::nonNull)
				.filter(isRootDefinition())
				.filter(UMLRTStateMachine::isDefaultContent)
				.map(sm -> deleteContents(trigger, sm))
				.reduce(UMLRTCommandUtils::flatCompose)
				.orElse(null);
	}

	static Predicate<UMLRTNamedElement> isRootDefinition() {
		Predicate<UMLRTNamedElement> isInherited = UMLRTNamedElement::isInherited;
		return isInherited.negate();
	}

	protected ICommand deleteContents(IEditCommandRequest trigger, UMLRTStateMachine stateMachine) {
		return stateMachine.toRegion().getOwnedElements().stream()
				.map(e -> destroy(trigger, e))
				.reduce(UMLRTCommandUtils::flatCompose)
				.orElse(null);
	}

	ICommand initializeContents(IEditCommandRequest trigger, Stream<UMLRTCapsule> capsuleHierarchy) {
		return capsuleHierarchy
				.flatMap(capsule -> initializeCapsule(trigger, capsule))
				.reduce(UMLRTCommandUtils::flatCompose)
				.orElse(null);
	}

	private Stream<ICommand> initializeCapsule(IEditCommandRequest trigger, UMLRTCapsule capsule) {
		Stream.Builder<ICommand> result = Stream.builder();

		Optional.ofNullable(capsule.getStateMachine())
				.filter(isRootDefinition())
				.map(sm -> initializeContents(trigger, sm))
				.ifPresent(result);

		Optional.of(capsule)
				.filter(c -> capsule.getStateMachine() == null) // Will get one later
				.map(c -> initializeStateMachineDiagram(trigger, capsule))
				.ifPresent(result);

		return result.build();
	}

	protected ICommand initializeContents(IEditCommandRequest trigger, UMLRTStateMachine stateMachine) {
		return new AbstractTransactionalCommand(trigger.getEditingDomain(), "Initialize State Machine", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				stateMachine.ensureDefaultContents();
				return CommandResult.newOKCommandResult(stateMachine.toUML());
			}
		};
	}

	protected ICommand initializeStateMachineDiagram(IEditCommandRequest trigger, UMLRTCapsule capsule) {
		return new AbstractTransactionalCommand(trigger.getEditingDomain(), "Initialize State Machine Diagram", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				Diagram result = null;

				UMLRTStateMachine sm = capsule.getStateMachine();
				if (sm != null) {
					result = RTStateMachineEditHelperAdvice.createStateMachineDiagram(sm.toUML(), null);
				}

				return (result == null)
						? CommandResult.newOKCommandResult()
						: CommandResult.newOKCommandResult(result);
			}
		};
	}
}
