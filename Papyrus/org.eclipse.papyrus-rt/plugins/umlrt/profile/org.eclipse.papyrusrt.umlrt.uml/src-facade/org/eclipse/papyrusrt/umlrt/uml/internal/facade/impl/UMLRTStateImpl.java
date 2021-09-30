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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTRegion;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTState;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.FacadeContentsEList;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedSubsetEObjectEList;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl#getRedefinableElements <em>Redefinable Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl#getRedefinedVertex <em>Redefined Vertex</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl#getSubtransitions <em>Subtransition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl#getEntry <em>Entry</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl#getExit <em>Exit</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl#getConnectionPoints <em>Connection Point</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl#getEntryPoints <em>Entry Point</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl#getExitPoints <em>Exit Point</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl#getRedefinedState <em>Redefined State</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl#isComposite <em>Composite</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl#isSimple <em>Simple</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl#getSubvertices <em>Subvertex</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTStateImpl extends UMLRTVertexImpl implements UMLRTState {
	/**
	 * The default value of the '{@link #isComposite() <em>Composite</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isComposite()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COMPOSITE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isSimple() <em>Simple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSimple()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SIMPLE_EDEFAULT = false;

	static final FacadeType<State, RTState, UMLRTStateImpl> TYPE = new FacadeType<>(
			UMLRTStateImpl.class,
			UMLPackage.Literals.STATE,
			UMLRTStateMachinesPackage.Literals.RT_STATE,
			UMLRTStateImpl::getInstance,
			state -> (UMLRTStateImpl) state.getRedefinedState(),
			UMLRTStateImpl::new);

	private static final FacadeReference<Vertex, EObject, UMLRTVertex, UMLRTVertexImpl> SUBVERTEX_REFERENCE = new FacadeReference.Indirect<>(
			UMLRTVertexImpl.TYPE,
			UMLRTUMLRTPackage.STATE__SUBVERTEX,
			UMLRTVertex.class,
			UMLPackage.Literals.REGION__SUBVERTEX,
			ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX,
			UMLPackage.Literals.VERTEX,
			state -> getCompositeRegion((State) state));

	private static final FacadeReference<Transition, EObject, UMLRTTransition, UMLRTTransitionImpl> SUBTRANSITION_REFERENCE = new FacadeReference.Indirect<>(
			UMLRTTransitionImpl.TYPE,
			UMLRTUMLRTPackage.STATE__SUBTRANSITION,
			UMLRTTransition.class,
			UMLPackage.Literals.REGION__TRANSITION,
			ExtUMLExtPackage.Literals.REGION__IMPLICIT_TRANSITION,
			UMLPackage.Literals.TRANSITION,
			state -> getCompositeRegion((State) state));

	private static final FacadeReference<Pseudostate, RTPseudostate, UMLRTConnectionPoint, UMLRTConnectionPointImpl> CONNECTION_POINT_REFERENCE = new FacadeReference<>(
			UMLRTConnectionPointImpl.TYPE,
			UMLRTUMLRTPackage.STATE__CONNECTION_POINT,
			UMLRTConnectionPoint.class,
			UMLPackage.Literals.STATE__CONNECTION_POINT,
			ExtUMLExtPackage.Literals.STATE__IMPLICIT_CONNECTION_POINT,
			UMLPackage.Literals.PSEUDOSTATE);

	protected static class StateAdapter<F extends UMLRTStateImpl> extends VertexAdapter<F> {

		StateAdapter(F facade) {
			super(facade);
		}

		@Override
		public void setTarget(Notifier newTarget) {
			super.setTarget(newTarget);

			if (newTarget instanceof State) {
				// Go for the region, too
				Region region = getCompositeRegion((State) newTarget);
				if (region != null) {
					adaptAdditional(region);
				}
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (oldTarget instanceof State) {
				unadaptAdditional();
			}

			super.unsetTarget(oldTarget);
		}

		@Override
		protected void handleObjectAdded(Notification msg, int position, EObject object) {
			if ((msg.getFeature() == UMLPackage.Literals.STATE__REGION)
					|| (msg.getFeature() == ExtUMLExtPackage.Literals.STATE__IMPLICIT_REGION)) {

				Region region = getCompositeRegion((State) msg.getNotifier());
				if (region == object) {
					// This is our region
					discoverRegion((Region) object);

					if (get().eNotificationRequired()) {
						get().eNotify(new ENotificationImpl(get(), Notification.SET, UMLRTUMLRTPackage.STATE__COMPOSITE, false, true));
					}
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
			if ((msg.getFeature() == UMLPackage.Literals.STATE__REGION)
					|| (msg.getFeature() == ExtUMLExtPackage.Literals.STATE__IMPLICIT_REGION)) {

				undiscoverRegion((Region) object);

				// See whether a new region has slid into its place
				Region region = getCompositeRegion((State) msg.getNotifier());
				if ((region != null) && (region != object)) {
					discoverRegion(region);
				} else {
					if (get().eNotificationRequired()) {
						get().eNotify(new ENotificationImpl(get(), Notification.SET, UMLRTUMLRTPackage.STATE__COMPOSITE, true, false));
					}
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
		protected void handleObjectReplaced(Notification msg, int position, EObject oldObject, EObject newObject) {
			if (msg.getFeature() == UMLPackage.Literals.STATE__REGION) {
				unadaptAdditional(oldObject);

				if (position == 0) {
					adaptAdditional(newObject);
				}
			} else {
				super.handleObjectReplaced(msg, position, oldObject, newObject);
			}
		}

		@Override
		protected List<? extends FacadeObject> getFacadeList(EObject owner, EReference reference, EObject object) {
			List<? extends FacadeObject> result;

			if ((reference == UMLPackage.Literals.STATE__CONNECTION_POINT)
					|| (reference == ExtUMLExtPackage.Literals.STATE__IMPLICIT_CONNECTION_POINT)) {

				// Have to check this case first because it is a subset of the UMLRTVertex case
				result = get().connectionPoints;
			} else if ((reference == UMLPackage.Literals.REGION__SUBVERTEX)
					|| (reference == ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX)) {

				result = get().subvertices;
			} else if ((reference == UMLPackage.Literals.REGION__TRANSITION)
					|| (reference == ExtUMLExtPackage.Literals.REGION__IMPLICIT_TRANSITION)) {

				result = get().subtransitions;
			} else {
				result = super.getFacadeList(owner, reference, object);
			}

			return result;
		}

		@Override
		protected InternalFacadeEList<? extends FacadeObject> validateObject(EObject owner, EReference reference, FacadeObject object) {
			InternalFacadeEList<? extends FacadeObject> result = null;

			if (object instanceof UMLRTConnectionPoint) {
				// Have to check this case first because it is a subset of the UMLRTVertex case
				if ((reference == UMLPackage.Literals.STATE__CONNECTION_POINT)
						|| (reference == ExtUMLExtPackage.Literals.STATE__IMPLICIT_CONNECTION_POINT)) {
					result = get().connectionPoints;
				}
			} else if (object instanceof UMLRTVertex) {
				if ((reference == UMLPackage.Literals.REGION__SUBVERTEX)
						|| (reference == ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX)) {
					result = get().subvertices;
				}
			} else if (object instanceof UMLRTTransition) {
				if ((reference == UMLPackage.Literals.REGION__TRANSITION)
						|| (reference == ExtUMLExtPackage.Literals.REGION__IMPLICIT_TRANSITION)) {
					result = get().subtransitions;
				}
			} else {
				result = super.validateObject(owner, reference, object);
			}

			return result;
		}
	}

	InternalFacadeEList<UMLRTVertex> subvertices;
	InternalFacadeEList<UMLRTTransition> subtransitions;
	InternalFacadeEList<UMLRTConnectionPoint> connectionPoints;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTStateImpl() {
		super();
	}

	/**
	 * Obtains the canonical instance of the façade for a state.
	 *
	 * @param state
	 *            a state in the UML model
	 *
	 * @return its façade, or {@code null} if the class is not a valid state
	 */
	public static UMLRTStateImpl getInstance(State state) {
		return getFacade(state, TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.STATE;
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
						redefinableElements = new DerivedUnionEObjectEList<>(UMLRTNamedElement.class, this, UMLRTUMLRTPackage.STATE__REDEFINABLE_ELEMENT, REDEFINABLE_ELEMENT_ESUBSETS));
			}
			return redefinableElements;
		}
		return new DerivedUnionEObjectEList<>(UMLRTNamedElement.class, this, UMLRTUMLRTPackage.STATE__REDEFINABLE_ELEMENT, REDEFINABLE_ELEMENT_ESUBSETS);
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
	protected static final int[] REDEFINABLE_ELEMENT_ESUBSETS = new int[] { UMLRTUMLRTPackage.STATE__SUBTRANSITION, UMLRTUMLRTPackage.STATE__ENTRY, UMLRTUMLRTPackage.STATE__EXIT, UMLRTUMLRTPackage.STATE__CONNECTION_POINT, UMLRTUMLRTPackage.STATE__SUBVERTEX };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTVertex getRedefinedVertex() {
		UMLRTState redefinedState = getRedefinedState();
		if (redefinedState != null) {
			return redefinedState;
		}
		return super.getRedefinedVertex();
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTStateImpl> createFacadeAdapter() {
		return new StateAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTTransition> getSubtransitions() {
		if (subtransitions == null) {
			subtransitions = (InternalFacadeEList<UMLRTTransition>) getFacades(SUBTRANSITION_REFERENCE);
		}
		return subtransitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTransition getSubtransition(String name) {
		return getSubtransition(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTransition getSubtransition(String name, boolean ignoreCase) {
		subtransitionLoop: for (UMLRTTransition subtransition : getSubtransitions()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(subtransition.getName()) : name.equals(subtransition.getName()))) {
				continue subtransitionLoop;
			}
			return subtransition;
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
	public List<UMLRTConnectionPoint> getConnectionPoints() {
		if (connectionPoints == null) {
			connectionPoints = (InternalFacadeEList<UMLRTConnectionPoint>) getFacades(CONNECTION_POINT_REFERENCE);
		}
		return connectionPoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTConnectionPoint getConnectionPoint(String name) {
		return getConnectionPoint(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTConnectionPoint getConnectionPoint(String name, boolean ignoreCase) {
		connectionPointLoop: for (UMLRTConnectionPoint connectionPoint : getConnectionPoints()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(connectionPoint.getName()) : name.equals(connectionPoint.getName()))) {
				continue connectionPointLoop;
			}
			return connectionPoint;
		}
		return null;
	}

	protected static class ConnectionPointList extends DerivedSubsetEObjectEList<UMLRTConnectionPoint> {
		protected final UMLRTConnectionPointKind kind;

		ConnectionPointList(InternalEObject owner, UMLRTConnectionPointKind kind, int featureID, int sourceFeatureID) {
			this(owner, kind, featureID, new int[] { sourceFeatureID });
		}

		ConnectionPointList(InternalEObject owner, UMLRTConnectionPointKind kind, int featureID, int[] sourceFeatureIDs) {
			super(UMLRTConnectionPoint.class, owner, featureID, sourceFeatureIDs);

			this.kind = kind;
		}

		@Override
		public List<UMLRTConnectionPoint> basicList() {
			return new ConnectionPointList(owner, kind, featureID, sourceFeatureIDs) {

				@Override
				public ListIterator<UMLRTConnectionPoint> listIterator(int index) {
					return basicListIterator(index);
				}
			};
		}

		@Override
		protected boolean isIncluded(EStructuralFeature feature) {
			return false;
		}

		@Override
		protected boolean isIncluded(Object object) {
			return ((UMLRTConnectionPoint) object).getKind() == kind;
		}

		@Override
		protected UMLRTConnectionPoint validate(int index, UMLRTConnectionPoint object) {
			if (object.getKind() != kind) {
				throw new IllegalArgumentException(String.valueOf(object));
			}
			return object;
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTConnectionPoint> getEntryPoints() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			@SuppressWarnings("unchecked")
			List<UMLRTConnectionPoint> entryPoints = (List<UMLRTConnectionPoint>) cache.get(this,
					UMLRTUMLRTPackage.Literals.STATE__ENTRY_POINT);
			if (entryPoints == null) {
				cache.put(this, UMLRTUMLRTPackage.Literals.STATE__ENTRY_POINT,
						entryPoints = new ConnectionPointList(this,
								UMLRTConnectionPointKind.ENTRY,
								UMLRTUMLRTPackage.STATE__ENTRY_POINT,
								UMLRTUMLRTPackage.STATE__CONNECTION_POINT));
			}
			return entryPoints;
		}

		return new ConnectionPointList(this,
				UMLRTConnectionPointKind.ENTRY,
				UMLRTUMLRTPackage.STATE__ENTRY_POINT,
				UMLRTUMLRTPackage.STATE__CONNECTION_POINT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTConnectionPoint getEntryPoint(String name) {
		return getEntryPoint(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTConnectionPoint getEntryPoint(String name, boolean ignoreCase) {
		entryPointLoop: for (UMLRTConnectionPoint entryPoint : getEntryPoints()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(entryPoint.getName()) : name.equals(entryPoint.getName()))) {
				continue entryPointLoop;
			}
			return entryPoint;
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
	public List<UMLRTConnectionPoint> getExitPoints() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			@SuppressWarnings("unchecked")
			List<UMLRTConnectionPoint> entryPoints = (List<UMLRTConnectionPoint>) cache.get(this,
					UMLRTUMLRTPackage.Literals.STATE__EXIT_POINT);
			if (entryPoints == null) {
				cache.put(this, UMLRTUMLRTPackage.Literals.STATE__EXIT_POINT,
						entryPoints = new ConnectionPointList(this,
								UMLRTConnectionPointKind.EXIT,
								UMLRTUMLRTPackage.STATE__EXIT_POINT,
								UMLRTUMLRTPackage.STATE__CONNECTION_POINT));
			}
			return entryPoints;
		}

		return new ConnectionPointList(this,
				UMLRTConnectionPointKind.EXIT,
				UMLRTUMLRTPackage.STATE__EXIT_POINT,
				UMLRTUMLRTPackage.STATE__CONNECTION_POINT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTConnectionPoint getExitPoint(String name) {
		return getExitPoint(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTConnectionPoint getExitPoint(String name, boolean ignoreCase) {
		exitPointLoop: for (UMLRTConnectionPoint exitPoint : getExitPoints()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(exitPoint.getName()) : name.equals(exitPoint.getName()))) {
				continue exitPointLoop;
			}
			return exitPoint;
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
	public UMLRTState getRedefinedState() {
		UMLRTState result;

		if (toUML() instanceof InternalUMLRTElement) {
			State superState = (State) ((InternalUMLRTElement) toUML()).rtGetRedefinedElement();
			result = UMLRTState.getInstance(superState);
		} else {
			State superState = toUML().getRedefinedState();
			if (superState != null) {
				result = UMLRTState.getInstance(superState);
			} else {
				result = null;
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
	public boolean isComposite() {
		return toUML().isComposite();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setComposite(boolean newComposite) {
		if (newComposite != isComposite()) {
			if (!newComposite) {
				// Destroy any regions
				new ArrayList<>(toUML().getRegions()).forEach(Element::destroy);
			} else {
				// Create a region
				Region region = toUML().createRegion("Region"); //$NON-NLS-1$
				applyStereotype(region, RTRegion.class);
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isSimple() {
		return toUML().isSimple();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTOpaqueBehavior getEntry() {
		Behavior result = toUML().getEntry();
		return (result instanceof OpaqueBehavior) ? UMLRTOpaqueBehavior.getInstance((OpaqueBehavior) result) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setEntry(UMLRTOpaqueBehavior newEntry) {
		if (newEntry != getEntry()) {
			if (newEntry == null) {
				toUML().setEntry(null);
			} else {
				toUML().setEntry(newEntry.toUML());
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTOpaqueBehavior getExit() {
		Behavior result = toUML().getExit();
		return (result instanceof OpaqueBehavior) ? UMLRTOpaqueBehavior.getInstance((OpaqueBehavior) result) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setExit(UMLRTOpaqueBehavior newExit) {
		if (newExit != getExit()) {
			if (newExit == null) {
				toUML().setExit(null);
			} else {
				toUML().setExit(newExit.toUML());
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTVertex> getSubvertices() {
		if (subvertices == null) {
			subvertices = (InternalFacadeEList<UMLRTVertex>) getFacades(SUBVERTEX_REFERENCE);
		}
		return subvertices;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTVertex getSubvertex(String name) {
		return getSubvertex(name, false, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTVertex getSubvertex(String name, boolean ignoreCase, EClass eClass) {
		subvertexLoop: for (UMLRTVertex subvertex : getSubvertices()) {
			if (eClass != null && !eClass.isInstance(subvertex)) {
				continue subvertexLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(subvertex.getName()) : name.equals(subvertex.getName()))) {
				continue subvertexLoop;
			}
			return subvertex;
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
	public State toUML() {
		return (State) super.toUML();
	}

	static Region getCompositeRegion(State state) {
		List<Region> regions = UMLRTExtensionUtil.getUMLRTContents(state, UMLPackage.Literals.STATE__REGION);

		return regions.isEmpty() ? null : regions.get(0);
	}

	protected Region demandCompositeRegion() {
		Region result = getCompositeRegion(toUML());

		if (result == null) {
			result = toUML().createRegion("Region"); //$NON-NLS-1$
			applyStereotype(result, RTRegion.class);
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
	public UMLRTState createSubstate(String name) {
		State result = (State) demandCompositeRegion().createSubvertex(name, UMLPackage.Literals.STATE);
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
	public UMLRTPseudostate createSubpseudostate(UMLRTPseudostateKind kind) {
		Pseudostate result = (Pseudostate) demandCompositeRegion().createSubvertex(null, UMLPackage.Literals.PSEUDOSTATE);
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
	public UMLRTConnectionPoint createConnectionPoint(UMLRTConnectionPointKind kind) {
		Pseudostate result = toUML().createConnectionPoint(null);
		result.setKind(kind.toUML());
		applyStereotype(result, RTPseudostate.class);
		return UMLRTConnectionPoint.getInstance(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Region toRegion() {
		return getCompositeRegion(toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTOpaqueBehavior createEntry(String language, String body) {
		return UMLRTOpaqueBehaviorImpl.createAction(this, UMLPackage.Literals.STATE__ENTRY, language, body);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTOpaqueBehavior createExit(String language, String body) {
		return UMLRTOpaqueBehaviorImpl.createAction(this, UMLPackage.Literals.STATE__EXIT, language, body);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Stream<? extends UMLRTState> allRedefinitions() {
		Predicate<UMLRTNamedElement> excluded = UMLRTNamedElement::isExcluded;
		UMLRTNamedElement context = getRedefinitionContext();
		return (context == null) ? Stream.of(this) : context.allRedefinitions()
				.map(c -> c.getRedefinitionOf(this))
				.filter(Objects::nonNull)
				.filter(excluded.negate());
	}

	@Override
	protected Stream<NamedElement> exclusions() {
		State uml = toUML();
		Stream<NamedElement> stateExclusions = exclusions(uml);

		// We pull elements from our region, too
		Region region = getCompositeRegion(uml);
		Stream<NamedElement> regionExclusions = (region == null)
				? Stream.empty()
				: exclusions(region);

		return Stream.concat(stateExclusions, regionExclusions);
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
		case UMLRTUMLRTPackage.STATE__SUBTRANSITION:
			return getSubtransitions();
		case UMLRTUMLRTPackage.STATE__ENTRY:
			return getEntry();
		case UMLRTUMLRTPackage.STATE__EXIT:
			return getExit();
		case UMLRTUMLRTPackage.STATE__CONNECTION_POINT:
			return getConnectionPoints();
		case UMLRTUMLRTPackage.STATE__ENTRY_POINT:
			return getEntryPoints();
		case UMLRTUMLRTPackage.STATE__EXIT_POINT:
			return getExitPoints();
		case UMLRTUMLRTPackage.STATE__REDEFINED_STATE:
			return getRedefinedState();
		case UMLRTUMLRTPackage.STATE__COMPOSITE:
			return isComposite();
		case UMLRTUMLRTPackage.STATE__SIMPLE:
			return isSimple();
		case UMLRTUMLRTPackage.STATE__SUBVERTEX:
			return getSubvertices();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	protected Object facadeGetAll(int referenceID) {
		switch (referenceID) {
		case UMLRTUMLRTPackage.STATE__SUBTRANSITION:
			return getFacades(SUBTRANSITION_REFERENCE, true);
		case UMLRTUMLRTPackage.STATE__SUBVERTEX:
			return getFacades(SUBVERTEX_REFERENCE, true);
		case UMLRTUMLRTPackage.STATE__CONNECTION_POINT:
			return getFacades(CONNECTION_POINT_REFERENCE, true);
		case UMLRTUMLRTPackage.STATE__ENTRY_POINT:
			return new FacadeObjectEList<>(this, UMLRTConnectionPoint.class, referenceID,
					CONNECTION_POINT_REFERENCE.facades(toUML(), true)
							.filter(cp -> cp.getKind() == UMLRTConnectionPointKind.ENTRY)
							.collect(Collectors.toList()));
		case UMLRTUMLRTPackage.STATE__EXIT_POINT:
			return new FacadeObjectEList<>(this, UMLRTConnectionPoint.class, referenceID,
					CONNECTION_POINT_REFERENCE.facades(toUML(), true)
							.filter(cp -> cp.getKind() == UMLRTConnectionPointKind.EXIT)
							.collect(Collectors.toList()));
		case UMLRTUMLRTPackage.STATE__REDEFINABLE_ELEMENT:
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLRTUMLRTPackage.STATE__ENTRY:
			setEntry((UMLRTOpaqueBehavior) newValue);
			return;
		case UMLRTUMLRTPackage.STATE__EXIT:
			setExit((UMLRTOpaqueBehavior) newValue);
			return;
		case UMLRTUMLRTPackage.STATE__COMPOSITE:
			setComposite((Boolean) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLRTUMLRTPackage.STATE__ENTRY:
			setEntry((UMLRTOpaqueBehavior) null);
			return;
		case UMLRTUMLRTPackage.STATE__EXIT:
			setExit((UMLRTOpaqueBehavior) null);
			return;
		case UMLRTUMLRTPackage.STATE__COMPOSITE:
			setComposite(COMPOSITE_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
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
		case UMLRTUMLRTPackage.STATE__REDEFINABLE_ELEMENT:
			return isSetRedefinableElements();
		case UMLRTUMLRTPackage.STATE__REDEFINED_VERTEX:
			return isSetRedefinedVertex();
		case UMLRTUMLRTPackage.STATE__SUBTRANSITION:
			return !getSubtransitions().isEmpty();
		case UMLRTUMLRTPackage.STATE__ENTRY:
			return getEntry() != null;
		case UMLRTUMLRTPackage.STATE__EXIT:
			return getExit() != null;
		case UMLRTUMLRTPackage.STATE__CONNECTION_POINT:
			return !getConnectionPoints().isEmpty();
		case UMLRTUMLRTPackage.STATE__ENTRY_POINT:
			return !getEntryPoints().isEmpty();
		case UMLRTUMLRTPackage.STATE__EXIT_POINT:
			return !getExitPoints().isEmpty();
		case UMLRTUMLRTPackage.STATE__REDEFINED_STATE:
			return getRedefinedState() != null;
		case UMLRTUMLRTPackage.STATE__COMPOSITE:
			return isComposite() != COMPOSITE_EDEFAULT;
		case UMLRTUMLRTPackage.STATE__SIMPLE:
			return isSimple() != SIMPLE_EDEFAULT;
		case UMLRTUMLRTPackage.STATE__SUBVERTEX:
			return !getSubvertices().isEmpty();
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
				|| eIsSet(UMLRTUMLRTPackage.STATE__SUBTRANSITION)
				|| eIsSet(UMLRTUMLRTPackage.STATE__ENTRY)
				|| eIsSet(UMLRTUMLRTPackage.STATE__EXIT)
				|| eIsSet(UMLRTUMLRTPackage.STATE__CONNECTION_POINT)
				|| eIsSet(UMLRTUMLRTPackage.STATE__SUBVERTEX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetRedefinedVertex() {
		return super.isSetRedefinedVertex()
				|| eIsSet(UMLRTUMLRTPackage.STATE__REDEFINED_STATE);
	}

} // UMLRTStateImpl
