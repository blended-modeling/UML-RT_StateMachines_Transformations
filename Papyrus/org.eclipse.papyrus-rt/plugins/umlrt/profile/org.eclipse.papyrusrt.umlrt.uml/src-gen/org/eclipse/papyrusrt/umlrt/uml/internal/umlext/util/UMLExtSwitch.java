/**
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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
package org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtBehavioredClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtClass;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtEncapsulatedClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtInterface;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtState;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStructuredClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtTransition;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;

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
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage
 * @generated
 */
public class UMLExtSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ExtUMLExtPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UMLExtSwitch() {
		if (modelPackage == null) {
			modelPackage = ExtUMLExtPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case ExtUMLExtPackage.ELEMENT: {
			ExtElement element = (ExtElement) theEObject;
			T result = caseElement(element);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtUMLExtPackage.NAMESPACE: {
			ExtNamespace namespace = (ExtNamespace) theEObject;
			T result = caseNamespace(namespace);
			if (result == null) {
				result = caseElement(namespace);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtUMLExtPackage.BEHAVIORED_CLASSIFIER: {
			ExtBehavioredClassifier behavioredClassifier = (ExtBehavioredClassifier) theEObject;
			T result = caseBehavioredClassifier(behavioredClassifier);
			if (result == null) {
				result = caseNamespace(behavioredClassifier);
			}
			if (result == null) {
				result = caseElement(behavioredClassifier);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtUMLExtPackage.STRUCTURED_CLASSIFIER: {
			ExtStructuredClassifier structuredClassifier = (ExtStructuredClassifier) theEObject;
			T result = caseStructuredClassifier(structuredClassifier);
			if (result == null) {
				result = caseNamespace(structuredClassifier);
			}
			if (result == null) {
				result = caseElement(structuredClassifier);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtUMLExtPackage.ENCAPSULATED_CLASSIFIER: {
			ExtEncapsulatedClassifier encapsulatedClassifier = (ExtEncapsulatedClassifier) theEObject;
			T result = caseEncapsulatedClassifier(encapsulatedClassifier);
			if (result == null) {
				result = caseStructuredClassifier(encapsulatedClassifier);
			}
			if (result == null) {
				result = caseNamespace(encapsulatedClassifier);
			}
			if (result == null) {
				result = caseElement(encapsulatedClassifier);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtUMLExtPackage.CLASS: {
			ExtClass class_ = (ExtClass) theEObject;
			T result = caseClass(class_);
			if (result == null) {
				result = caseEncapsulatedClassifier(class_);
			}
			if (result == null) {
				result = caseBehavioredClassifier(class_);
			}
			if (result == null) {
				result = caseStructuredClassifier(class_);
			}
			if (result == null) {
				result = caseNamespace(class_);
			}
			if (result == null) {
				result = caseElement(class_);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtUMLExtPackage.INTERFACE: {
			ExtInterface interface_ = (ExtInterface) theEObject;
			T result = caseInterface(interface_);
			if (result == null) {
				result = caseNamespace(interface_);
			}
			if (result == null) {
				result = caseElement(interface_);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtUMLExtPackage.STATE_MACHINE: {
			ExtStateMachine stateMachine = (ExtStateMachine) theEObject;
			T result = caseStateMachine(stateMachine);
			if (result == null) {
				result = caseNamespace(stateMachine);
			}
			if (result == null) {
				result = caseElement(stateMachine);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtUMLExtPackage.REGION: {
			ExtRegion region = (ExtRegion) theEObject;
			T result = caseRegion(region);
			if (result == null) {
				result = caseNamespace(region);
			}
			if (result == null) {
				result = caseElement(region);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtUMLExtPackage.TRANSITION: {
			ExtTransition transition = (ExtTransition) theEObject;
			T result = caseTransition(transition);
			if (result == null) {
				result = caseNamespace(transition);
			}
			if (result == null) {
				result = caseElement(transition);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtUMLExtPackage.STATE: {
			ExtState state = (ExtState) theEObject;
			T result = caseState(state);
			if (result == null) {
				result = caseNamespace(state);
			}
			if (result == null) {
				result = caseElement(state);
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
	 * Returns the result of interpreting the object as an instance of '<em>Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseElement(ExtElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Namespace</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Namespace</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamespace(ExtNamespace object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Behaviored Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Behaviored Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBehavioredClassifier(ExtBehavioredClassifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Structured Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Structured Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStructuredClassifier(ExtStructuredClassifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Encapsulated Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Encapsulated Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEncapsulatedClassifier(ExtEncapsulatedClassifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Class</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClass(ExtClass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Interface</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Interface</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInterface(ExtInterface object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Machine</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateMachine(ExtStateMachine object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Region</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Region</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRegion(ExtRegion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransition(ExtTransition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseState(ExtState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //UMLExtSwitch
