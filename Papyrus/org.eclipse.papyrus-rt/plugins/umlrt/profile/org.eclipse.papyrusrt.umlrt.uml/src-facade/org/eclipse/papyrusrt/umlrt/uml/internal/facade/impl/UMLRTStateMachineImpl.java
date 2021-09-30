/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl;

import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.getExtensionFeature;
import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.getUMLRTContents;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTRegion;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTState;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTStateMachine;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.FacadeContentsEList;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTSwitch;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateMachineImpl#getRedefinableElements <em>Redefinable Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateMachineImpl#getRedefinedElement <em>Redefined Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateMachineImpl#getRedefinitionContext <em>Redefinition Context</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateMachineImpl#getVertices <em>Vertex</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateMachineImpl#getTransitions <em>Transition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateMachineImpl#getRedefinedStateMachine <em>Redefined State Machine</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateMachineImpl#getCapsule <em>Capsule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTStateMachineImpl extends UMLRTNamedElementImpl implements UMLRTStateMachine {
	private static final String DEFAULT_REGION_NAME = "Region"; //$NON-NLS-1$

	private static final String DEFAULT_STATE_NAME = "State1"; //$NON-NLS-1$

	static final FacadeType<StateMachine, RTStateMachine, UMLRTStateMachineImpl> TYPE = new FacadeType<>(
			UMLRTStateMachineImpl.class,
			UMLPackage.Literals.STATE_MACHINE,
			UMLRTStateMachinesPackage.Literals.RT_STATE_MACHINE,
			UMLRTStateMachineImpl::getInstance,
			sm -> (UMLRTStateMachineImpl) sm.getRedefinedStateMachine(),
			UMLRTStateMachineImpl::new);

	private static final FacadeReference<Vertex, EObject, UMLRTVertex, UMLRTVertexImpl> VERTEX_REFERENCE = new FacadeReference.Indirect<>(
			UMLRTVertexImpl.TYPE,
			UMLRTUMLRTPackage.STATE_MACHINE__VERTEX,
			UMLRTVertex.class,
			UMLPackage.Literals.REGION__SUBVERTEX,
			ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX,
			UMLPackage.Literals.VERTEX,
			sm -> getRegion((StateMachine) sm));

	private static final FacadeReference<Transition, EObject, UMLRTTransition, UMLRTTransitionImpl> TRANSITION_REFERENCE = new FacadeReference.Indirect<>(
			UMLRTTransitionImpl.TYPE,
			UMLRTUMLRTPackage.STATE_MACHINE__TRANSITION,
			UMLRTTransition.class,
			UMLPackage.Literals.REGION__TRANSITION,
			ExtUMLExtPackage.Literals.REGION__IMPLICIT_TRANSITION,
			UMLPackage.Literals.TRANSITION,
			sm -> getRegion((StateMachine) sm));

	protected static class StateMachineAdapter<F extends UMLRTStateMachineImpl> extends NamedElementAdapter<F> {

		StateMachineAdapter(F facade) {
			super(facade);
		}

		@Override
		public void setTarget(Notifier newTarget) {
			super.setTarget(newTarget);

			if (newTarget instanceof StateMachine) {
				// Go for the region, too
				Region region = getRegion((StateMachine) newTarget);
				if (region != null) {
					adaptAdditional(region);
				}
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (oldTarget instanceof StateMachine) {
				unadaptAdditional();
			}

			super.unsetTarget(oldTarget);
		}

		@Override
		protected void handleObjectAdded(Notification msg, int position, EObject object) {
			if ((msg.getFeature() == UMLPackage.Literals.STATE_MACHINE__REGION)
					|| (msg.getFeature() == ExtUMLExtPackage.Literals.STATE_MACHINE__IMPLICIT_REGION)) {

				Region region = getRegion((StateMachine) msg.getNotifier());
				if ((object == region) || (region == null)) {
					// This is or will become our region
					discoverRegion((Region) object);
				}
			} else {
				super.handleObjectAdded(msg, position, object);
			}
		}

		protected void discoverRegion(Region region) {
			// Listen for the vertices and transitions in this region
			adaptAdditional(region);

			// And discover new contents
			discover(region, UMLPackage.Literals.REGION__SUBVERTEX);
			discover(region, UMLPackage.Literals.REGION__TRANSITION);
		}

		@Override
		protected void handleObjectRemoved(Notification msg, int position, EObject object) {
			if ((msg.getFeature() == UMLPackage.Literals.STATE_MACHINE__REGION)
					|| (msg.getFeature() == ExtUMLExtPackage.Literals.STATE_MACHINE__IMPLICIT_REGION)) {

				undiscoverRegion((Region) object);

				// See whether a new region has slid into its place
				Region region = getRegion((StateMachine) msg.getNotifier());
				if ((region != null) && (region != object)) {
					discoverRegion(region);
				}
			} else {
				super.handleObjectRemoved(msg, position, object);
			}
		}

		protected void undiscoverRegion(Region region) {
			// Undiscover old contents
			undiscover(region, UMLPackage.Literals.REGION__SUBVERTEX);
			undiscover(region, UMLPackage.Literals.REGION__TRANSITION);

			// Stop listening for changes
			unadaptAdditional(region);
		}

		@Override
		protected List<? extends FacadeObject> getFacadeList(EObject owner, EReference reference, EObject object) {
			List<? extends FacadeObject> result;

			if ((reference == UMLPackage.Literals.REGION__SUBVERTEX)
					|| (reference == ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX)) {

				result = get().vertices;
			} else if ((reference == UMLPackage.Literals.REGION__TRANSITION)
					|| (reference == ExtUMLExtPackage.Literals.REGION__IMPLICIT_TRANSITION)) {

				result = get().transitions;
			} else {
				result = super.getFacadeList(owner, reference, object);
			}

			return result;
		}

		@Override
		protected InternalFacadeEList<? extends FacadeObject> validateObject(EObject owner, EReference reference, FacadeObject object) {
			InternalFacadeEList<? extends FacadeObject> result = null;

			if (object instanceof UMLRTVertex) {
				if ((reference == UMLPackage.Literals.REGION__SUBVERTEX)
						|| (reference == ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX)) {
					result = get().vertices;
				}
			} else if (object instanceof UMLRTTransition) {
				if ((reference == UMLPackage.Literals.REGION__TRANSITION)
						|| (reference == ExtUMLExtPackage.Literals.REGION__IMPLICIT_TRANSITION)) {
					result = get().transitions;
				}
			} else {
				result = super.validateObject(owner, reference, object);
			}

			return result;
		}

		@Override
		protected void handleObjectAdded(Notification msg, int position, FacadeObject object) {
			if (msg.getFeature() == UMLPackage.Literals.STATE_MACHINE__EXTENDED_STATE_MACHINE) {
				// In case I can ditch some default contents
				if (!isUndoRedoNotification(msg)) {
					get().initializeContents((UMLRTStateMachine) object);
				}
			} else {
				super.handleObjectAdded(msg, position, object);
			}
		}
	}

	InternalFacadeEList<UMLRTVertex> vertices;
	InternalFacadeEList<UMLRTTransition> transitions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTStateMachineImpl() {
		super();
	}

	/**
	 * Obtains the canonical instance of the façade for a state machine.
	 *
	 * @param stateMachine
	 *            a state machine in the UML model
	 *
	 * @return its façade, or {@code null} if the class is not a valid state machine
	 */
	public static UMLRTStateMachineImpl getInstance(StateMachine stateMachine) {
		return getFacade(stateMachine, TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.STATE_MACHINE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public List<UMLRTNamedElement> getRedefinableElements() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			List<UMLRTNamedElement> redefinableElements = (List<UMLRTNamedElement>) cache.get(eResource, this, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			if (redefinableElements == null) {
				cache.put(eResource, this, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT,
						redefinableElements = new DerivedUnionEObjectEList<>(UMLRTNamedElement.class, this, UMLRTUMLRTPackage.STATE_MACHINE__REDEFINABLE_ELEMENT, REDEFINABLE_ELEMENT_ESUBSETS));
			}
			return redefinableElements;
		}
		return new DerivedUnionEObjectEList<>(UMLRTNamedElement.class, this, UMLRTUMLRTPackage.STATE_MACHINE__REDEFINABLE_ELEMENT, REDEFINABLE_ELEMENT_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getRedefinableElements() <em>Redefinable Element</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRedefinableElements()
	 * @generated
	 * @ordered
	 */
	protected static final int[] REDEFINABLE_ELEMENT_ESUBSETS = new int[] { UMLRTUMLRTPackage.STATE_MACHINE__VERTEX, UMLRTUMLRTPackage.STATE_MACHINE__TRANSITION };

	@Override
	protected BasicFacadeAdapter<? extends UMLRTStateMachineImpl> createFacadeAdapter() {
		return new StateMachineAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinedElement() {
		UMLRTStateMachine redefinedStateMachine = getRedefinedStateMachine();
		if (redefinedStateMachine != null) {
			return redefinedStateMachine;
		}
		return super.getRedefinedElement();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinitionContext() {
		UMLRTCapsule capsule = getCapsule();
		if (capsule != null) {
			return capsule;
		}
		return super.getRedefinitionContext();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTVertex> getVertices() {
		if (vertices == null) {
			vertices = (InternalFacadeEList<UMLRTVertex>) getFacades(VERTEX_REFERENCE);
		}
		return vertices;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTVertex getVertex(String name) {
		return getVertex(name, false, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTVertex getVertex(String name, boolean ignoreCase, EClass eClass) {
		vertexLoop: for (UMLRTVertex vertex : getVertices()) {
			if (eClass != null && !eClass.isInstance(vertex)) {
				continue vertexLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(vertex.getName()) : name.equals(vertex.getName()))) {
				continue vertexLoop;
			}
			return vertex;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTTransition> getTransitions() {
		if (transitions == null) {
			transitions = (InternalFacadeEList<UMLRTTransition>) getFacades(TRANSITION_REFERENCE);
		}
		return transitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTransition getTransition(String name) {
		return getTransition(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTransition getTransition(String name, boolean ignoreCase) {
		transitionLoop: for (UMLRTTransition transition : getTransitions()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(transition.getName()) : name.equals(transition.getName()))) {
				continue transitionLoop;
			}
			return transition;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTStateMachine getRedefinedStateMachine() {
		UMLRTStateMachine result;

		if (toUML() instanceof InternalUMLRTElement) {
			StateMachine superSM = (StateMachine) ((InternalUMLRTElement) toUML()).rtGetRedefinedElement();
			result = UMLRTStateMachine.getInstance(superSM);
		} else {
			StateMachine superSM = toUML().getExtendedStateMachines().isEmpty()
					? null
					: toUML().getExtendedStateMachines().get(0);
			if (superSM != null) {
				result = UMLRTStateMachine.getInstance(superSM);
			} else {
				UMLRTNamedElement redef = super.getRedefinedElement();
				result = (redef instanceof UMLRTStateMachine) ? (UMLRTStateMachine) redef : null;
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTCapsule getCapsule() {
		BehavioredClassifier result = toUML().getContext();
		return (result instanceof Class) ? UMLRTCapsule.getInstance((Class) result) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public StateMachine toUML() {
		return (StateMachine) super.toUML();
	}

	private static Region getRegion(StateMachine stateMachine) {
		List<Region> regions = UMLRTExtensionUtil.getUMLRTContents(stateMachine, UMLPackage.Literals.STATE_MACHINE__REGION);

		return regions.isEmpty() ? null : regions.get(0);
	}

	protected Region demandRegion() {
		Region result = getRegion(toUML());

		if (result == null) {
			result = toUML().createRegion(DEFAULT_REGION_NAME);
			applyStereotype(result, RTRegion.class);
		}

		return result;
	}

	void initializeContents(UMLRTStateMachine redefined) {
		Region region = getRegion(toUML());

		// Check for redefinition
		if (redefined != null) {
			@SuppressWarnings("unchecked")
			EList<Region> implicitRegions = (EList<Region>) toUML().eGet(getExtensionFeature(UMLPackage.Literals.STATE_MACHINE__REGION));

			if (!implicitRegions.isEmpty()) {
				InternalUMLRTElement region_ = (InternalUMLRTElement) region;

				Region implicit = implicitRegions.get(0);
				// The region and everything else will be inherited, unless I already
				// have non-default content
				if (isDefaultContent()) {
					// Replace the region's content with inherited stuff
					ElementRTOperations.destroyAll((InternalUMLRTElement & Region) region, region.getOwnedElements());

					// And let our region be inherited
					if (!region_.rtIsVirtual()) {
						region_.rtVirtualize();
					}
				}

				// Re-use inherited initial pseudostate, at least. A pre-requisite is to
				// take all of the inherited region's extension content into the real
				// (now redefining) region
				List<Transition> inheritedTransitions = getUMLRTContents(implicit, UMLPackage.Literals.REGION__TRANSITION);
				List<Vertex> inheritedVertices = getUMLRTContents(implicit, UMLPackage.Literals.REGION__SUBVERTEX);
				List<Constraint> inheritedConstraints = getUMLRTContents(implicit, UMLPackage.Literals.NAMESPACE__OWNED_RULE);
				if (!inheritedTransitions.isEmpty() || !inheritedVertices.isEmpty() || !inheritedConstraints.isEmpty()) {
					region_.rtCreateExtension();
					ExtRegion ext = region_.rtExtension(ExtRegion.class);

					ext.getImplicitTransitions().addAll(inheritedTransitions);
					ext.getImplicitSubvertices().addAll(inheritedVertices);
					ext.getImplicitOwnedRules().addAll(inheritedConstraints);
				}

				// Let our existing region now redefine the inherited region
				Region extended = implicit.getExtendedRegion();
				implicit.destroy();
				region.setExtendedRegion(extended);

				Pseudostate inheritedInitial = getPseudostate(redefined, PseudostateKind.INITIAL_LITERAL);
				if (inheritedInitial != null) {
					Pseudostate myInitial = getPseudostate(this, PseudostateKind.INITIAL_LITERAL);
					if ((myInitial != null) && !UMLRTExtensionUtil.redefines(myInitial, inheritedInitial)) {
						myInitial.getOutgoings().forEach(t -> t.setSource(inheritedInitial));
						myInitial.getIncomings().forEach(t -> t.setTarget(inheritedInitial));
						myInitial.destroy();
					}
				}
			}
		} else if (region == null) {
			// Create the default transition
			createDefaultContent();
		}
	}

	protected void createDefaultContent() {
		createPseudostate(UMLRTPseudostateKind.INITIAL).createTransitionTo(createState(DEFAULT_STATE_NAME));
	}

	public boolean isDefaultContent() {
		boolean result = false;

		List<UMLRTVertex> vertices = getVertices();
		List<UMLRTTransition> transitions = getTransitions();
		if ((transitions.size() == 1) && (vertices.size() == 2)) {
			UMLRTTransition transition = transitions.get(0);
			if ((transition.getSource() instanceof UMLRTPseudostate) && (transition.getTarget() instanceof UMLRTState)) {
				UMLRTPseudostate initial = (UMLRTPseudostate) transition.getSource();
				UMLRTState state1 = (UMLRTState) transition.getTarget();

				if ((initial.getKind() == UMLRTPseudostateKind.INITIAL) && DEFAULT_STATE_NAME.equals(state1.getName())) {
					State umlState = state1.toUML();
					Transition umlTransition = transition.toUML();

					result = !umlState.isComposite()
							&& (umlState.getEntry() == null)
							&& (umlState.getDoActivity() == null)
							&& (umlState.getExit() == null)
							&& (umlState.getStateInvariant() == null)
							&& umlState.getConnectionPoints().isEmpty()
							&& umlState.getDeferrableTriggers().isEmpty()
							&& umlTransition.getTriggers().isEmpty()
							&& umlTransition.getOwnedRules().isEmpty()
							&& (umlTransition.getEffect() == null);
				}
			}
		}

		return result;
	}

	private static Pseudostate getPseudostate(UMLRTStateMachine sm, PseudostateKind kind) {
		Pseudostate result = null;
		Region region = getRegion(sm.toUML());
		if (region != null) {
			result = UMLRTExtensionUtil.<Vertex> getUMLRTContents(region, UMLPackage.Literals.REGION__SUBVERTEX).stream()
					.filter(Pseudostate.class::isInstance).map(Pseudostate.class::cast)
					.filter(ps -> ps.getKind() == kind)
					.findFirst().orElse(null);
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTState createState(String name) {
		State result = (State) demandRegion().createSubvertex(name, UMLPackage.Literals.STATE);
		applyStereotype(result, RTState.class);
		return UMLRTState.getInstance(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTPseudostate createPseudostate(UMLRTPseudostateKind kind) {
		Pseudostate result = (Pseudostate) demandRegion().createSubvertex(null, UMLPackage.Literals.PSEUDOSTATE);
		result.setKind(kind.toUML());
		applyStereotype(result, RTPseudostate.class);
		return UMLRTPseudostate.getInstance(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void ensureDefaultContents() {
		initializeContents(getRedefinedStateMachine());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Region toRegion() {
		return getRegion(toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Stream<? extends UMLRTStateMachine> allRedefinitions() {
		Predicate<UMLRTStateMachine> excluded = UMLRTStateMachine::isExcluded;
		UMLRTCapsule capsule = getCapsule();
		return (capsule == null) ? Stream.of(this) : capsule.getHierarchy()
				.map(c -> c.getRedefinitionOf(this))
				.filter(Objects::nonNull)
				.filter(excluded.negate());
	}

	@Override
	protected Stream<NamedElement> exclusions() {
		StateMachine uml = toUML();
		Stream<NamedElement> smExclusions = exclusions(uml);

		// We pull elements from our region, too
		Region region = getRegion(uml);
		Stream<NamedElement> regionExclusions = (region == null)
				? Stream.empty()
				: exclusions(region);

		return Stream.concat(smExclusions, regionExclusions);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UMLRTUMLRTPackage.STATE_MACHINE__VERTEX:
			return getVertices();
		case UMLRTUMLRTPackage.STATE_MACHINE__TRANSITION:
			return getTransitions();
		case UMLRTUMLRTPackage.STATE_MACHINE__REDEFINED_STATE_MACHINE:
			return getRedefinedStateMachine();
		case UMLRTUMLRTPackage.STATE_MACHINE__CAPSULE:
			return getCapsule();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	protected Object facadeGetAll(int referenceID) {
		switch (referenceID) {
		case UMLRTUMLRTPackage.STATE_MACHINE__VERTEX:
			return getFacades(VERTEX_REFERENCE, true);
		case UMLRTUMLRTPackage.STATE_MACHINE__TRANSITION:
			return getFacades(TRANSITION_REFERENCE, true);
		case UMLRTUMLRTPackage.STATE_MACHINE__REDEFINABLE_ELEMENT:
			return new FacadeContentsEList<>(this, true, REDEFINABLE_ELEMENT_ESUBSETS);
		default:
			return eGet(referenceID, true, true);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLRTUMLRTPackage.STATE_MACHINE__REDEFINABLE_ELEMENT:
			return isSetRedefinableElements();
		case UMLRTUMLRTPackage.STATE_MACHINE__REDEFINED_ELEMENT:
			return isSetRedefinedElement();
		case UMLRTUMLRTPackage.STATE_MACHINE__REDEFINITION_CONTEXT:
			return isSetRedefinitionContext();
		case UMLRTUMLRTPackage.STATE_MACHINE__VERTEX:
			return !getVertices().isEmpty();
		case UMLRTUMLRTPackage.STATE_MACHINE__TRANSITION:
			return !getTransitions().isEmpty();
		case UMLRTUMLRTPackage.STATE_MACHINE__REDEFINED_STATE_MACHINE:
			return getRedefinedStateMachine() != null;
		case UMLRTUMLRTPackage.STATE_MACHINE__CAPSULE:
			return getCapsule() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetRedefinableElements() {
		return super.isSetRedefinableElements()
				|| eIsSet(UMLRTUMLRTPackage.STATE_MACHINE__VERTEX)
				|| eIsSet(UMLRTUMLRTPackage.STATE_MACHINE__TRANSITION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetRedefinedElement() {
		return super.isSetRedefinedElement()
				|| eIsSet(UMLRTUMLRTPackage.STATE_MACHINE__REDEFINED_STATE_MACHINE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetRedefinitionContext() {
		return super.isSetRedefinitionContext()
				|| eIsSet(UMLRTUMLRTPackage.STATE_MACHINE__CAPSULE);
	}

	@Override
	public <T extends UMLRTNamedElement> T getRedefinitionOf(T element) {
		T result = super.getRedefinitionOf(element);

		if (result == null) {
			// Maybe it's in a composite state?
			Optional<UMLRTState> composite = new UMLRTSwitch<Optional<UMLRTState>>() {
				@Override
				public Optional<UMLRTState> caseVertex(UMLRTVertex vertex) {
					return Optional.ofNullable(vertex.getState());
				}

				@Override
				public Optional<UMLRTState> caseTransition(UMLRTTransition transition) {
					return Optional.ofNullable(transition.getState());
				}

				@Override
				public Optional<UMLRTState> defaultCase(EObject object) {
					return Optional.empty();
				}
			}.doSwitch(element);

			result = composite
					.map(this::getRedefinitionOf) // Get the local composite
					.map(c -> c.getRedefinitionOf(element)) // Resolve the element in it
					.orElse(element);
		}

		return result;
	}

} // UMLRTStateMachineImpl
