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
package org.eclipse.papyrusrt.umlrt.uml.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTModel;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage
 * @generated
 */
public class UMLRTSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static UMLRTUMLRTPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTSwitch() {
		if (modelPackage == null) {
			modelPackage = UMLRTUMLRTPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param ePackage
	 *            the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Dispatches the target object to the appropriate <code>caseXXX</code> methods.
	 *
	 * @param object
	 *            the model object to pass to the appropriate <code>caseXXX</code>.
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 */
	public T doSwitch(Object object) {
		T result = null;

		if (object instanceof UMLRTModel) {
			result = caseModel((UMLRTModel) object);
		}
		if (result == null) {
			if (object instanceof EObject) {
				result = doSwitch((EObject) object);
			}
			if (result == null) {
				result = defaultCase(object);
			}
		}
		return result;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case UMLRTUMLRTPackage.NAMED_ELEMENT: {
			UMLRTNamedElement namedElement = (UMLRTNamedElement) theEObject;
			T result = caseNamedElement(namedElement);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.PACKAGE: {
			UMLRTPackage package_ = (UMLRTPackage) theEObject;
			T result = casePackage(package_);
			if (result == null) {
				result = caseNamedElement(package_);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.CAPSULE: {
			UMLRTCapsule capsule = (UMLRTCapsule) theEObject;
			T result = caseCapsule(capsule);
			if (result == null) {
				result = caseClassifier(capsule);
			}
			if (result == null) {
				result = caseNamedElement(capsule);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.CLASSIFIER: {
			UMLRTClassifier classifier = (UMLRTClassifier) theEObject;
			T result = caseClassifier(classifier);
			if (result == null) {
				result = caseNamedElement(classifier);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.PORT: {
			UMLRTPort port = (UMLRTPort) theEObject;
			T result = casePort(port);
			if (result == null) {
				result = caseReplicatedElement(port);
			}
			if (result == null) {
				result = caseNamedElement(port);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.REPLICATED_ELEMENT: {
			UMLRTReplicatedElement replicatedElement = (UMLRTReplicatedElement) theEObject;
			T result = caseReplicatedElement(replicatedElement);
			if (result == null) {
				result = caseNamedElement(replicatedElement);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.PROTOCOL: {
			UMLRTProtocol protocol = (UMLRTProtocol) theEObject;
			T result = caseProtocol(protocol);
			if (result == null) {
				result = caseClassifier(protocol);
			}
			if (result == null) {
				result = caseNamedElement(protocol);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE: {
			UMLRTProtocolMessage protocolMessage = (UMLRTProtocolMessage) theEObject;
			T result = caseProtocolMessage(protocolMessage);
			if (result == null) {
				result = caseNamedElement(protocolMessage);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.CAPSULE_PART: {
			UMLRTCapsulePart capsulePart = (UMLRTCapsulePart) theEObject;
			T result = caseCapsulePart(capsulePart);
			if (result == null) {
				result = caseReplicatedElement(capsulePart);
			}
			if (result == null) {
				result = caseNamedElement(capsulePart);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.CONNECTOR: {
			UMLRTConnector connector = (UMLRTConnector) theEObject;
			T result = caseConnector(connector);
			if (result == null) {
				result = caseNamedElement(connector);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.STATE_MACHINE: {
			UMLRTStateMachine stateMachine = (UMLRTStateMachine) theEObject;
			T result = caseStateMachine(stateMachine);
			if (result == null) {
				result = caseNamedElement(stateMachine);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.VERTEX: {
			UMLRTVertex vertex = (UMLRTVertex) theEObject;
			T result = caseVertex(vertex);
			if (result == null) {
				result = caseNamedElement(vertex);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.STATE: {
			UMLRTState state = (UMLRTState) theEObject;
			T result = caseState(state);
			if (result == null) {
				result = caseVertex(state);
			}
			if (result == null) {
				result = caseNamedElement(state);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.TRANSITION: {
			UMLRTTransition transition = (UMLRTTransition) theEObject;
			T result = caseTransition(transition);
			if (result == null) {
				result = caseNamedElement(transition);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.TRIGGER: {
			UMLRTTrigger trigger = (UMLRTTrigger) theEObject;
			T result = caseTrigger(trigger);
			if (result == null) {
				result = caseNamedElement(trigger);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.GUARD: {
			UMLRTGuard guard = (UMLRTGuard) theEObject;
			T result = caseGuard(guard);
			if (result == null) {
				result = caseNamedElement(guard);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR: {
			UMLRTOpaqueBehavior opaqueBehavior = (UMLRTOpaqueBehavior) theEObject;
			T result = caseOpaqueBehavior(opaqueBehavior);
			if (result == null) {
				result = caseNamedElement(opaqueBehavior);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.CONNECTION_POINT: {
			UMLRTConnectionPoint connectionPoint = (UMLRTConnectionPoint) theEObject;
			T result = caseConnectionPoint(connectionPoint);
			if (result == null) {
				result = caseVertex(connectionPoint);
			}
			if (result == null) {
				result = caseNamedElement(connectionPoint);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case UMLRTUMLRTPackage.PSEUDOSTATE: {
			UMLRTPseudostate pseudostate = (UMLRTPseudostate) theEObject;
			T result = casePseudostate(pseudostate);
			if (result == null) {
				result = caseVertex(pseudostate);
			}
			if (result == null) {
				result = caseNamedElement(pseudostate);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedElement(UMLRTNamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Package</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Package</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePackage(UMLRTPackage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClassifier(UMLRTClassifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Capsule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Capsule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCapsule(UMLRTCapsule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Replicated Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Replicated Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReplicatedElement(UMLRTReplicatedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Port</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Port</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePort(UMLRTPort object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Capsule Part</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Capsule Part</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCapsulePart(UMLRTCapsulePart object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connector</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connector</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnector(UMLRTConnector object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Machine</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateMachine(UMLRTStateMachine object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Vertex</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Vertex</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVertex(UMLRTVertex object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseState(UMLRTState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransition(UMLRTTransition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Trigger</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrigger(UMLRTTrigger object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Guard</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Guard</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGuard(UMLRTGuard object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Opaque Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Opaque Behavior</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOpaqueBehavior(UMLRTOpaqueBehavior object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connection Point</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connection Point</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnectionPoint(UMLRTConnectionPoint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pseudostate</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pseudostate</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePseudostate(UMLRTPseudostate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Protocol</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Protocol</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProtocol(UMLRTProtocol object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Protocol Message</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Protocol Message</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProtocolMessage(UMLRTProtocolMessage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model</em>'.
	 * @see #doSwitch(java.lang.Object) doSwitch(Object)
	 * @generated NOT
	 */
	public T caseModel(UMLRTModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is almost the last case anyway.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated NOT
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated NOT
	 */
	public T defaultCase(Object object) {
		return null;
	}

} // UMLRTSwitch
