/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.uml;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTState;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTStateMachine;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTTrigger;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Generic factory for UML-RT façade objects on the UML model representation.
 */
public class UMLRTFactory {

	private static final Switch<? extends UMLRTNamedElement> FACTORY_SWITCH = createFactorySwitch();

	/**
	 * Not instantiable by clients.
	 */
	private UMLRTFactory() {
		super();
	}

	public static FacadeObject create(EObject object) {
		return (object instanceof NamedElement) ? create((NamedElement) object) : null;
	}

	/**
	 * Obtains the façade object for a given {@code element} in the UML
	 * model. Note that this does not necessarily always create an
	 * unique instance; façades are cached.
	 * 
	 * @param element
	 *            an element in the model
	 * 
	 * @return the {@code element}'s façade, if it has one, otherwise
	 *         {@code null}, in which case it does not have special semantics
	 *         in UML-RT
	 */
	public static UMLRTNamedElement create(NamedElement element) {
		return FACTORY_SWITCH.doSwitch(element);
	}

	/**
	 * Creates a capsule façade.
	 * 
	 * @param class_
	 *            a capsule
	 * @return its façade, or {@code null} if the {@code class_} is not a valid capsule
	 */
	public static UMLRTCapsule createCapsule(Class class_) {
		return UMLRTCapsule.getInstance(class_);
	}

	/**
	 * Creates a protocol façade.
	 * 
	 * @param collaboration
	 *            a protocol
	 * @return its façade, or {@code null} if the {@code collaboration} is not a valid protocol
	 */
	public static UMLRTProtocol createProtocol(Collaboration collaboration) {
		return UMLRTProtocol.getInstance(collaboration);
	}

	/**
	 * Creates a port façade.
	 * 
	 * @param port
	 *            a port
	 * @return its façade, or {@code null} if the {@code port} is not a valid UML-RT port
	 */
	public static UMLRTPort createPort(Port port) {
		return UMLRTPort.getInstance(port);
	}

	/**
	 * Creates a capsule-part façade.
	 * 
	 * @param property
	 *            a capsule-part
	 * @return its façade, or {@code null} if the {@code property} is not a valid capsule-part
	 */
	public static UMLRTCapsulePart createCapsulePart(Property property) {
		return UMLRTCapsulePart.getInstance(property);
	}

	/**
	 * Creates a connector façade.
	 * 
	 * @param connector
	 *            a connector
	 * @return its façade, or {@code null} if the {@code connector} is not a valid UML-RT connector
	 */
	public static UMLRTConnector createConnector(Connector connector) {
		return UMLRTConnector.getInstance(connector);
	}

	/**
	 * Creates a protocol message façade.
	 * 
	 * @param operation
	 *            an operation
	 * @return its façade, or {@code null} if the {@code operation} is not a valid protocol message
	 */
	public static UMLRTProtocolMessage createProtocolMessage(Operation operation) {
		return UMLRTProtocolMessage.getInstance(operation);
	}

	/**
	 * Creates a package façade.
	 * 
	 * @param package_
	 *            a package
	 * @return its façade, or {@code null} if the {@code package_} is not a valid package,
	 *         for example if it is a protocol container or does not have or inherit an application
	 *         of the UML-RT profile
	 */
	public static UMLRTPackage createPackage(Package package_) {
		return UMLRTPackage.getInstance(package_);
	}

	/**
	 * Creates a state machine façade.
	 * 
	 * @param stateMachine
	 *            a state machine
	 * @return its façade, or {@code null} if the {@code stateMachine} is not a valid UML-RT
	 *         state machine, for example if it is not the classifier behavior
	 *         of a capsule or class or it does not have the {@link RTStateMachine}
	 *         stereotype applied
	 */
	public static UMLRTStateMachine createStateMachine(StateMachine stateMachine) {
		return UMLRTStateMachine.getInstance(stateMachine);
	}

	/**
	 * Creates a state façade.
	 * 
	 * @param state
	 *            a state in a state machine
	 * @return its façade, or {@code null} if the {@code state} is not a valid UML-RT
	 *         state, for example if it does not have the {@link RTState}
	 *         stereotype applied
	 */
	public static UMLRTState createState(State state) {
		return UMLRTState.getInstance(state);
	}

	/**
	 * Creates a pseudostate façade.
	 * 
	 * @param pseudostate
	 *            a pseudostate in a state machine
	 * @return its façade, or {@code null} if the {@code pseudostate} is not a valid UML-RT
	 *         pseudostate, for example if it does not have the {@link RTPseudostate}
	 *         stereotype applied or if it has a {@link Pseudostate#getKind() kind} that
	 *         is not recognized by UML-RT
	 */
	public static UMLRTVertex createVertex(Pseudostate pseudostate) {
		return UMLRTVertex.getInstance(pseudostate);
	}

	/**
	 * Creates a pseudostate façade.
	 * 
	 * @param pseudostate
	 *            a pseudostate in a state machine
	 * @return its façade, or {@code null} if the {@code pseudostate} is not a valid UML-RT
	 *         pseudostate, for example if it does not have the {@link RTPseudostate}
	 *         stereotype applied or if it has a {@link Pseudostate#getKind() kind} that
	 *         indicates a connection point or that is not recognized by UML-RT
	 */
	public static UMLRTPseudostate createPseudostate(Pseudostate pseudostate) {
		return UMLRTPseudostate.getInstance(pseudostate);
	}

	/**
	 * Creates a connection point façade.
	 * 
	 * @param connectionPoint
	 *            a composite state connection point in a state machine
	 * @return its façade, or {@code null} if the {@code connectionPoint} is not a valid UML-RT
	 *         connection point, for example if it does not have the {@link RTPseudostate}
	 *         stereotype applied or if it has a {@link Pseudostate#getKind() kind} that
	 *         does not indicate a connection point
	 */
	public static UMLRTConnectionPoint createConnectionPoint(Pseudostate connectionPoint) {
		return UMLRTConnectionPoint.getInstance(connectionPoint);
	}

	/**
	 * Creates a transition façade.
	 * 
	 * @param transition
	 *            a transition in a state machine
	 * @return its façade, or {@code null} if the {@code transition} is not a valid UML-RT
	 *         transition
	 */
	public static UMLRTTransition createTransition(Transition transition) {
		return UMLRTTransition.getInstance(transition);
	}

	/**
	 * Creates a transition trigger façade.
	 * 
	 * @param trigger
	 *            a transition trigger in a state machine
	 * @return its façade, or {@code null} if the {@code trigger} is not a valid UML-RT
	 *         trigger
	 */
	public static UMLRTTrigger createTrigger(Trigger trigger) {
		return UMLRTTrigger.getInstance(trigger);
	}

	/**
	 * Creates a transition or trigger guard façade.
	 * 
	 * @param guard
	 *            a transition or trigger guard condition in a state machine
	 * @return its façade, or {@code null} if the {@code guard} is not a valid UML-RT
	 *         transition or trigger guard, for example if it is a transition's
	 *         owned rule that is not its {@link Transition#getGuard() guard}
	 *         and is not stereotyped as {@link RTTrigger}
	 */
	public static UMLRTGuard createGuard(Constraint guard) {
		return UMLRTGuard.getInstance(guard);
	}

	/**
	 * Creates a transition effect or state entry/exit façade.
	 * 
	 * @param behavior
	 *            a transition effect or state entry/exit behavior in a state machine
	 * @return its façade, or {@code null} if the {@code behavior} is not a valid UML-RT
	 *         transition effect or state entry/exit
	 */
	public static UMLRTOpaqueBehavior createOpaqueBehavior(OpaqueBehavior behavior) {
		return UMLRTOpaqueBehavior.getInstance(behavior);
	}

	private static Switch<? extends UMLRTNamedElement> createFactorySwitch() {
		return new UMLSwitch<UMLRTNamedElement>() {
			@Override
			public UMLRTNamedElement caseClass(Class object) {
				return UMLRTFactory.createCapsule(object);
			}

			@Override
			public UMLRTNamedElement caseCollaboration(Collaboration object) {
				return UMLRTFactory.createProtocol(object);
			}

			@Override
			public UMLRTNamedElement caseConnector(Connector object) {
				return UMLRTFactory.createConnector(object);
			}

			@Override
			public UMLRTNamedElement casePort(Port object) {
				return UMLRTFactory.createPort(object);
			}

			@Override
			public UMLRTNamedElement caseProperty(Property object) {
				return UMLRTFactory.createCapsulePart(object);
			}

			@Override
			public UMLRTNamedElement caseOperation(Operation object) {
				return UMLRTFactory.createProtocolMessage(object);
			}

			@Override
			public UMLRTNamedElement casePackage(Package object) {
				return UMLRTFactory.createPackage(object);
			}

			@Override
			public UMLRTNamedElement caseStateMachine(StateMachine object) {
				return UMLRTFactory.createStateMachine(object);
			}

			@Override
			public UMLRTNamedElement caseState(State object) {
				return UMLRTFactory.createState(object);
			}

			@Override
			public UMLRTNamedElement casePseudostate(Pseudostate object) {
				return UMLRTFactory.createVertex(object);
			}

			@Override
			public UMLRTNamedElement caseTransition(Transition object) {
				return UMLRTFactory.createTransition(object);
			}

			@Override
			public UMLRTNamedElement caseTrigger(Trigger object) {
				return UMLRTFactory.createTrigger(object);
			}

			@Override
			public UMLRTNamedElement caseConstraint(Constraint object) {
				return UMLRTFactory.createGuard(object);
			}

			@Override
			public UMLRTNamedElement caseOpaqueBehavior(OpaqueBehavior object) {
				return UMLRTFactory.createOpaqueBehavior(object);
			}
		};
	}

	//
	// Nested types
	//

	/**
	 * A specific factory for creation only of elements of Capsule Structure. That is,
	 * <ul>
	 * <li>{@link UMLRTFactory#createCapsule(Class) UMLRTCapsule}</li>
	 * <li>{@link UMLRTFactory#createPort(Port) UMLRTPort}</li>
	 * <li>{@link UMLRTFactory#createCapsulePart(Property) UMLRTCapsulePart}</li>
	 * <li>{@link UMLRTFactory#createConnector(Connector) UMLRTConnector}</li>
	 * </ul>
	 */
	public static class CapsuleFactory {

		private static final Switch<? extends UMLRTNamedElement> FACTORY_SWITCH = createFactorySwitch();

		/**
		 * Not instantiable by clients.
		 */
		private CapsuleFactory() {
			super();
		}

		/**
		 * Obtains the façade object for a given {@code element} in the UML
		 * model. Note that this does not necessarily always create an
		 * unique instance; façades are cached.
		 * 
		 * @param element
		 *            an element in the model
		 * 
		 * @return the {@code element}'s façade, if it has one, otherwise
		 *         {@code null}, in which case it does not have special semantics
		 *         in UML-RT
		 */
		public static UMLRTNamedElement create(NamedElement element) {
			return FACTORY_SWITCH.doSwitch(element);
		}

		private static Switch<? extends UMLRTNamedElement> createFactorySwitch() {
			return new UMLSwitch<UMLRTNamedElement>() {
				@Override
				public UMLRTNamedElement caseClass(Class object) {
					return UMLRTFactory.createCapsule(object);
				}

				@Override
				public UMLRTNamedElement caseConnector(Connector object) {
					return UMLRTFactory.createConnector(object);
				}

				@Override
				public UMLRTNamedElement casePort(Port object) {
					return UMLRTFactory.createPort(object);
				}

				@Override
				public UMLRTNamedElement caseProperty(Property object) {
					return UMLRTFactory.createCapsulePart(object);
				}
			};
		}

	}

	/**
	 * A specific factory for creation only of elements of Protocol Structure. That is,
	 * <ul>
	 * <li>{@link UMLRTFactory#createProtocol(Collaboration) UMLRTProtocol}</li>
	 * <li>{@link UMLRTFactory#createProtocolMessage(Operation) UMLRTProtocolMessage}</li>
	 * </ul>
	 */
	public static class ProtocolFactory {

		private static final Switch<? extends UMLRTNamedElement> FACTORY_SWITCH = createFactorySwitch();

		/**
		 * Not instantiable by clients.
		 */
		private ProtocolFactory() {
			super();
		}

		/**
		 * Obtains the façade object for a given {@code element} in the UML
		 * model. Note that this does not necessarily always create an
		 * unique instance; façades are cached.
		 * 
		 * @param element
		 *            an element in the model
		 * 
		 * @return the {@code element}'s façade, if it has one, otherwise
		 *         {@code null}, in which case it does not have special semantics
		 *         in UML-RT
		 */
		public static UMLRTNamedElement create(NamedElement element) {
			return FACTORY_SWITCH.doSwitch(element);
		}

		private static Switch<? extends UMLRTNamedElement> createFactorySwitch() {
			return new UMLSwitch<UMLRTNamedElement>() {
				@Override
				public UMLRTNamedElement caseCollaboration(Collaboration object) {
					return UMLRTFactory.createProtocol(object);
				}

				@Override
				public UMLRTNamedElement caseOperation(Operation object) {
					return UMLRTFactory.createProtocolMessage(object);
				}
			};
		}

	}

	/**
	 * A specific factory for creation only of elements of State Machines. That is,
	 * <ul>
	 * <li>{@link UMLRTFactory#createStateMachine(StateMachine) UMLRTStateMachine}</li>
	 * <li>{@link UMLRTFactory#createState(State) UMLRTState}</li>
	 * <li>{@link UMLRTFactory#createPseudostate(Pseudostate) UMLRTPseudostate}</li>
	 * <li>{@link UMLRTFactory#createConnectionPoint(Pseudostate) UMLRTConnectionPoint}</li>
	 * <li>{@link UMLRTFactory#createTransition(Transition) UMLRTTransition}</li>
	 * <li>{@link UMLRTFactory#createTrigger(Trigger) UMLRTTrigger}</li>
	 * <li>{@link UMLRTFactory#createGuard(Constraint) UMLRTGuard}</li>
	 * </ul>
	 */
	public static class StateMachineFactory {

		private static final Switch<? extends UMLRTNamedElement> FACTORY_SWITCH = createFactorySwitch();

		/**
		 * Not instantiable by clients.
		 */
		private StateMachineFactory() {
			super();
		}

		/**
		 * Obtains the façade object for a given {@code element} in the UML
		 * model. Note that this does not necessarily always create an
		 * unique instance; façades are cached.
		 * 
		 * @param element
		 *            an element in the model
		 * 
		 * @return the {@code element}'s façade, if it has one, otherwise
		 *         {@code null}, in which case it does not have special semantics
		 *         in UML-RT
		 */
		public static UMLRTNamedElement create(NamedElement element) {
			return FACTORY_SWITCH.doSwitch(element);
		}

		private static Switch<? extends UMLRTNamedElement> createFactorySwitch() {
			return new UMLSwitch<UMLRTNamedElement>() {
				@Override
				public UMLRTNamedElement caseStateMachine(StateMachine object) {
					return UMLRTFactory.createStateMachine(object);
				}

				@Override
				public UMLRTNamedElement caseState(State object) {
					return UMLRTFactory.createState(object);
				}

				@Override
				public UMLRTNamedElement casePseudostate(Pseudostate object) {
					return UMLRTFactory.createVertex(object);
				}

				@Override
				public UMLRTNamedElement caseTransition(Transition object) {
					return UMLRTFactory.createTransition(object);
				}

				@Override
				public UMLRTNamedElement caseTrigger(Trigger object) {
					return UMLRTFactory.createTrigger(object);
				}

				@Override
				public UMLRTNamedElement caseConstraint(Constraint object) {
					return UMLRTFactory.createGuard(object);
				}

				@Override
				public UMLRTNamedElement caseOpaqueBehavior(OpaqueBehavior object) {
					return UMLRTFactory.createOpaqueBehavior(object);
				}
			};
		}

	}

}
