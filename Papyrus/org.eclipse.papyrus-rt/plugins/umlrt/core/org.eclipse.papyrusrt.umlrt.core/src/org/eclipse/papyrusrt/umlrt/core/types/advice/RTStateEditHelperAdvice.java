/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 493866, 510315, 515179, 512362, 518290
 *  Young-Soo Roh - bug 510024
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import static org.eclipse.papyrusrt.umlrt.core.utils.UMLRTCommandUtils.destroy;
import static org.eclipse.papyrusrt.umlrt.core.utils.UMLRTCommandUtils.set;
import static org.eclipse.papyrusrt.umlrt.core.utils.UMLRTCommandUtils.unset;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.commands.ExcludeDependentsRequest;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.RegionUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.StateMachineUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTCommandUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Edit Helper Advice for State machines.
 */
public class RTStateEditHelperAdvice extends AbstractEditHelperAdvice implements IInheritanceEditHelperAdvice {

	/**
	 * Constructor.
	 */
	public RTStateEditHelperAdvice() {
		// empty
	}

	/**
	 * Dispatches to specific methods, or super otherwise.
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		Boolean result = null;

		if (request instanceof CreateElementRequest) {
			result = approveCreateElementRequest((CreateElementRequest) request);
		} else if (request instanceof MoveRequest) {
			result = approveMoveRequest((MoveRequest) request);
		} else if (request instanceof SetRequest) {
			result = approveSetRequest((SetRequest) request);
		}

		if (result == null) {
			result = super.approveRequest(request);
		}

		return result;
	}

	/**
	 * Approves the specified set request.
	 * 
	 * @param request
	 *            the request to approve
	 * @return <code>true</code> if that advice accepts this request
	 */
	protected boolean approveSetRequest(SetRequest request) {
		EStructuralFeature feature = request.getFeature();
		for (Object o : request.getElementsToEdit()) {
			if (o instanceof State &&
					UMLPackage.Literals.STATE__CONNECTION_POINT.equals(feature)) {
				// prevent drag & drop of entry/exit point
				return false;
			}
		}
		return super.approveRequest(request);
	}

	/**
	 * Approves the specified move request or reject it.
	 * 
	 * @param request
	 *            the request to approve.
	 * @return <code>true</code> if the specified request is approved
	 */
	protected boolean approveMoveRequest(MoveRequest request) {
		if (!(request.getTargetContainer() instanceof Region)) {
			// Can move if all are pseudostates being pasted to the state
			Collection<?> moved = request.getElementsToMove().keySet();
			Predicate<Object> isConnectionPoint = StateMachineUtils::isConnectionPoint;
			Predicate<Object> isPastedConnectionPoint = isConnectionPoint.and(
					// The first condition ensures that it's an EObject
					o -> ((EObject) o).eResource() == null);
			return (request.getTargetContainer() instanceof State)
					&& moved.stream().allMatch(isPastedConnectionPoint);
		}

		return RegionUtils.shouldApproveMoveRequest(request);
	}

	/**
	 * Disallow creation of another region in a composite state: composite
	 * states have exactly one region.
	 * 
	 * @param request
	 *            an element creation request
	 * 
	 * @return approval status if the {@code request} is creating a region in a state;
	 *         {@code null} to delegate to the superclass approval, otherwise
	 */
	private Boolean approveCreateElementRequest(CreateElementRequest request) {
		Boolean result = null;

		IElementType typeToCreate = request.getElementType();
		if (ElementTypeUtils.isTypeCompatible(typeToCreate, UMLElementTypes.REGION)) {
			// Creating some kind of region. In a state?
			if (request.getContainer() instanceof State) {
				State stateToEdit = (State) request.getContainer();
				result = !stateToEdit.isComposite();
			}
		} else if (ElementTypeUtils.isTypeCompatible(typeToCreate, UMLElementTypes.PSEUDOSTATE)) {
			// Pseudostates (usually connection points) must be stereotyped for UML-RT
			result = UMLRTElementTypesEnumerator.getAllRTTypes().stream()
					.anyMatch(rtType -> ElementTypeUtils.isTypeCompatible(typeToCreate, rtType));
		}

		return result;
	}

	@Override
	public ICommand getBeforeEditCommand(IEditCommandRequest request) {
		ICommand result = getInheritanceEditCommand(request);

		if (result == null) {
			result = super.getBeforeEditCommand(request);
		}

		return result;
	}

	@Override
	public ICommand getExcludeDependentsCommand(ExcludeDependentsRequest request) {
		ICommand result = IInheritanceEditHelperAdvice.super.getExcludeDependentsCommand(request);

		if (request.isExclude() && (request.getElementToExclude() instanceof State)) {
			// Exclude incoming and outgoing transitions, also
			UMLRTState state = UMLRTState.getInstance((State) request.getElementToExclude());
			if (state != null) {
				Predicate<UMLRTNamedElement> alreadyExcluded = UMLRTNamedElement::isExcluded;
				List<Transition> transitionsToExclude = Stream.concat(
						state.getIncomings().stream(),
						state.getOutgoings().stream()).distinct()
						.filter(alreadyExcluded.negate())
						.map(UMLRTTransition::toUML)
						.collect(Collectors.toList());

				// And for composite states, transitions into entry-points and out of exit-points.
				// Don't need to worry about transitions on the inside, because they are encapsuled
				// within the composite state, which is excluded
				List<Transition> connectionPointTransitions = Stream.concat(
						state.getEntryPoints().stream().flatMap(cp -> cp.getIncomings().stream()),
						state.getExitPoints().stream().flatMap(cp -> cp.getOutgoings().stream())).distinct()
						.filter(alreadyExcluded.negate())
						.map(UMLRTTransition::toUML)
						.collect(Collectors.toList());

				if (!connectionPointTransitions.isEmpty()) {
					if (transitionsToExclude.isEmpty()) {
						// This is the usual case for composite states
						transitionsToExclude = connectionPointTransitions;
					} else {
						transitionsToExclude.addAll(connectionPointTransitions);
					}
				}

				if (!transitionsToExclude.isEmpty()) {
					ICommand excludeTransitions = request.getExcludeDependentsCommand(transitionsToExclude);
					if (excludeTransitions != null) {
						result = CompositeCommand.compose(result, excludeTransitions);
					}
				}
			}
		} else if (!request.isExclude() && (request.getElementToExclude() instanceof State)) {
			// We are re-inheriting the state.
			// Handle transitions on connection points that will disappear
			ICommand refactorTransitions = getTransitionRefactoring(request);
			if (refactorTransitions != null) {
				// Do that before the state's own re-inherit actions
				result = UMLRTCommandUtils.flatCompose(refactorTransitions, result);
			}
		}

		return result;
	}

	@Override
	public void configureRequest(IEditCommandRequest request) {
		if (request instanceof MoveRequest) {
			StateMachineUtils.retargetToRegion((MoveRequest) request);
		}
	}

	protected ICommand getTransitionRefactoring(ExcludeDependentsRequest reinherit) {
		ICommand result = null;

		UMLRTState state = UMLRTState.getInstance((State) reinherit.getElementToExclude());
		if ((state != null) && state.isInherited()) {
			Predicate<UMLRTNamedElement> isinherited = UMLRTNamedElement::isInherited;
			Predicate<UMLRTNamedElement> isLocal = isinherited.negate();

			// Gather incomings on local entry points to re-target them onto the state
			result = state.getEntryPoints().stream()
					.filter(isLocal)
					.map(UMLRTVertex::getIncomings).flatMap(List::stream)
					.map(t -> getRefactoring(reinherit, t, state))
					.reduce(result, UMLRTCommandUtils::flatCompose);

			// Gather outgoings on local exit points to re-source them onto the state
			result = state.getExitPoints().stream()
					.filter(isLocal)
					.map(UMLRTVertex::getOutgoings).flatMap(List::stream)
					.map(t -> getRefactoring(reinherit, t, state))
					.reduce(result, UMLRTCommandUtils::flatCompose);
		}

		return result;
	}

	protected ICommand getRefactoring(ExcludeDependentsRequest reinherit, UMLRTTransition transition, UMLRTState reinherited) {
		ICommand result = null;

		UMLRTTransition parent = transition.getRedefinedTransition();
		Set<EStructuralFeature> unsetFeatures = new HashSet<>();

		// Will the re-inherited state remain a composite or become a simple state?
		boolean willBeComposite = reinherited.getRedefinedState().isComposite();

		if (transition.getTarget() instanceof UMLRTConnectionPoint) {
			if (((parent == null) || !reinherited.redefines(parent.getTarget())) && !willBeComposite) {
				// Local transition or this end was redefined
				UMLRTConnectionPoint target = (UMLRTConnectionPoint) transition.getTarget();
				if (target.getState() == reinherited) {
					result = UMLRTCommandUtils.flatCompose(result, setTarget(reinherit, transition, reinherited));
				}
			} else if (parent == null) {
				// Just delete the transition because there is no longer any way for it to
				// connect to the state. It must use an entry point because the state
				// is still composite, but the entry point is no longer available because
				// the state is re-inherited
				result = UMLRTCommandUtils.flatCompose(result, delete(reinherit, transition));
			} else {
				// Just unset the target to re-inherit it because there is no longer a connection
				// point for it to connect to
				result = UMLRTCommandUtils.flatCompose(result, unsetTarget(reinherit, transition));
				unsetFeatures.add(UMLPackage.Literals.TRANSITION__TARGET);
			}
		}

		if (transition.getSource() instanceof UMLRTConnectionPoint) {
			if (((parent == null) || !reinherited.redefines(parent.getSource())) && !willBeComposite) {
				// Local transition or this end was redefined
				UMLRTConnectionPoint source = (UMLRTConnectionPoint) transition.getSource();
				if (source.getState() == reinherited) {
					result = UMLRTCommandUtils.flatCompose(result, setSource(reinherit, transition, reinherited));
				}
			} else if (parent == null) {
				// Just delete the transition because there is no longer any way for it to
				// connect to the state. It must use an exit point because the state
				// is still composite, but the exit point is no longer available because
				// the state is re-inherited
				result = UMLRTCommandUtils.flatCompose(result, delete(reinherit, transition));
			} else {
				// Just unset the source to re-inherit it
				result = UMLRTCommandUtils.flatCompose(result, unsetSource(reinherit, transition));
				unsetFeatures.add(UMLPackage.Literals.TRANSITION__SOURCE);
			}
		}

		// Will the end result be an entirely unset transition? If so, just re-inherit it altogether
		if (transition.isInherited() && !unsetFeatures.isEmpty()) {
			boolean canReinherit = true;

			Transition uml = transition.toUML();
			for (EStructuralFeature next : uml.eClass().getEAllStructuralFeatures()) {
				// Yes, we have to worry about containments because can be inherited (containers cannot).
				// And, of course, the redefinedTransition reference is always there
				if (next.isChangeable() && !next.isDerived() && (!(next instanceof EReference) || !((EReference) next).isContainer())) {
					if ((next != UMLPackage.Literals.TRANSITION__REDEFINED_TRANSITION)
							&& uml.eIsSet(next) && !unsetFeatures.contains(next)) {

						// Cannot re-inherit
						canReinherit = false;
						break;
					}
				}
			}

			if (canReinherit) {
				// Do that, instead
				result = reinherit.getExcludeDependentCommand(uml);
			}
		}

		return result;
	}

	protected ICommand setTarget(IEditCommandRequest trigger, UMLRTTransition transition, UMLRTVertex newTarget) {
		return set(trigger, transition.toUML(), UMLPackage.Literals.TRANSITION__TARGET, newTarget.toUML());
	}

	protected ICommand setSource(IEditCommandRequest trigger, UMLRTTransition transition, UMLRTVertex newSource) {
		return set(trigger, transition.toUML(), UMLPackage.Literals.TRANSITION__SOURCE, newSource.toUML());
	}

	protected ICommand unsetTarget(IEditCommandRequest trigger, UMLRTTransition transition) {
		return unset(trigger, transition.toUML(), UMLPackage.Literals.TRANSITION__TARGET);
	}

	protected ICommand unsetSource(IEditCommandRequest trigger, UMLRTTransition transition) {
		return unset(trigger, transition.toUML(), UMLPackage.Literals.TRANSITION__SOURCE);
	}

	protected ICommand delete(IEditCommandRequest trigger, UMLRTTransition transition) {
		return destroy(trigger, transition.toUML());
	}
}
