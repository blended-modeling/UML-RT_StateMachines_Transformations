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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.types.advice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTStateMachineDiagramUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.StateMachine;

/**
 * Advice that creates ancillary structures for state machine diagrams
 * on configuration of a new {@link Generalization} or a new
 * {@link StateMachine} in an environment of capsules with
 * {@link Generalization} relationships.
 */
public class StateMachineInheritanceAdvice extends AbstractEditHelperAdvice {

	/**
	 * Initializes me.
	 */
	public StateMachineInheritanceAdvice() {
		super();
	}

	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		ICommand result = super.getAfterConfigureCommand(request);
		EObject configuree = request.getElementToConfigure();

		Generalization generalization = TypeUtils.as(configuree, Generalization.class);
		if ((generalization != null) && CapsuleUtils.isCapsule(generalization.getSpecific())) {
			ICommand configure = getConfigureCapsuleGeneralizationCommand(generalization, request);
			if (configure != null) {
				result = (result == null) ? configure : result.compose(configure);
			}

			return result;
		}

		StateMachine stateMachine = TypeUtils.as(configuree, StateMachine.class);
		if ((stateMachine != null) && CapsuleUtils.isCapsule(stateMachine.getContext())) {
			ICommand configure = getConfigureInheritedStateMachinesCommand(stateMachine, request);
			if (configure != null) {
				result = (result == null) ? configure : result.compose(configure);
			}

			return result;
		}

		return result;
	}

	protected ICommand getConfigureCapsuleGeneralizationCommand(Generalization generalization, ConfigureRequest request) {
		ICommand result = null;

		// Does the capsule now inherit a state machine that has no diagram?
		UMLRTCapsule capsule = UMLRTCapsule.getInstance((org.eclipse.uml2.uml.Class) generalization.getSpecific());
		if (capsule != null) {
			// At the time of this request, the generalization does not yet have its
			// general classifier, so the state machine cannot yet have been
			// inherited. Therefore, we must defer the matter until execution of this
			// new command. And likewise all of the known subclass hierarchy
			result = new CreateStateMachineDiagramCommand(request.getEditingDomain(), capsule.toUML());
		}

		return result;
	}

	protected ICommand getConfigureInheritedStateMachinesCommand(StateMachine stateMachine, ConfigureRequest request) {
		ICommand result = null;

		// Does the state machine now have redefinitions that have no diagram?
		org.eclipse.uml2.uml.Class class_ = TypeUtils.as(stateMachine.getContext(), org.eclipse.uml2.uml.Class.class);
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(class_);
		if (capsule != null) {
			// At the time of this request, the generalization does not yet have its
			// general classifier, so the state machine cannot yet have been
			// inherited. Therefore, we must defer the matter until execution of this
			// new command
			result = new CreateStateMachineDiagramCommand(request.getEditingDomain(), capsule.toUML());
		}

		return result;
	}

	//
	// Nested types
	//

	protected static class CreateStateMachineDiagramCommand extends AbstractTransactionalCommand {
		private org.eclipse.uml2.uml.Class capsule;

		@SuppressWarnings("unchecked")
		public CreateStateMachineDiagramCommand(TransactionalEditingDomain domain, org.eclipse.uml2.uml.Class capsule) {
			super(domain, "Create State Machine Diagram", withNotation(domain, getWorkspaceFiles(capsule)));

			this.capsule = capsule;
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			List<Diagram> result = Collections.emptyList();

			UMLRTCapsule capsule = UMLRTCapsule.getInstance(this.capsule);
			if (capsule != null) {
				// Process the entire known subclass hierarchy
				result = capsule.getHierarchy()
						.map(UMLRTCapsule::getStateMachine)
						.filter(Objects::nonNull)
						.map(UMLRTStateMachine::toUML)
						.filter(sm -> UMLRTStateMachineDiagramUtils.getStateMachineDiagram(sm) == null)
						.map(sm -> UMLRTStateMachineDiagramUtils.createStateMachineDiagram(sm, null))
						.filter(Objects::nonNull) // Doesn't matter if we didn't get a diagram
						.collect(Collectors.toList());
			}


			return CommandResult.newOKCommandResult(result);
		}

		static List<IFile> withNotation(EditingDomain domain, List<IFile> files) {
			List<IFile> result = Optional.ofNullable(TypeUtils.as(domain.getResourceSet(), ModelSet.class))
					.map(NotationUtils::getNotationResource)
					.map(WorkspaceSynchronizer::getFile)
					.map(f -> {
						List<IFile> newFiles = new ArrayList<>(files);
						newFiles.add(f);
						return newFiles;
					}).orElse(files);

			return result;
		}
	}
}
