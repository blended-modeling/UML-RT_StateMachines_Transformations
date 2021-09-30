/*******************************************************************************
 * Copyright (c) 2014-2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.xtext.codegen.ui.cdt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.codegen.UserEditableRegion;
import org.eclipse.papyrusrt.codegen.UserEditableRegion.CommitResult;
import org.eclipse.papyrusrt.codegen.UserEditableRegion.Label;
import org.eclipse.papyrusrt.codegen.UserEditableRegion.TriggerDetail;
import org.eclipse.papyrusrt.xtumlrt.common.ActionCode;
import org.eclipse.papyrusrt.xtumlrt.common.BaseContainer;
import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.Entity;
import org.eclipse.papyrusrt.xtumlrt.common.Model;
import org.eclipse.papyrusrt.xtumlrt.common.Port;
import org.eclipse.papyrusrt.xtumlrt.statemach.CompositeState;
import org.eclipse.papyrusrt.xtumlrt.statemach.State;
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine;
import org.eclipse.papyrusrt.xtumlrt.statemach.Transition;
import org.eclipse.papyrusrt.xtumlrt.statemach.Trigger;
import org.eclipse.papyrusrt.xtumlrt.statemach.Vertex;
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPassiveClass;
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPort;
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTTrigger;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * 
 * @author ysroh
 *
 */
public class UmlrtXtextEObjectLocator implements org.eclipse.papyrusrt.codegen.IEObjectLocator {

	/**
	 * Language.
	 */
	public static final String LANGUAGE = "C++";

	/** Resource set. */
	private ResourceSet rset;

	/**
	 * Constructor.
	 *
	 */
	public UmlrtXtextEObjectLocator() {
	}

	/**
	 * Retrieve root element of given resource uri.
	 * 
	 * @param uri
	 *            resource uri
	 * @return root package
	 */
	private Model getRoot(String uri) {

		Model result = null;
		URI resourceUri = URI.createURI(uri);
		if ("umlrt".equals(resourceUri.fileExtension())) {
			Resource r = rset.getResource(resourceUri, true);
			result = (Model) r.getContents().get(0);
		}
		return result;
	}

	@Override
	public EObject getEObject(UserEditableRegion.Label label) {

		List<String> qualifiedNames = new ArrayList<>();
		qualifiedNames.addAll(Arrays.asList(label.getQualifiedName().split(UserEditableRegion.SEPARATOR)));

		BaseContainer nextPackage = getRoot(label.getUri());
		if (nextPackage == null) {
			// resource not found
			return null;
		}
		Entity capsuleOrClass = null;
		qualifiedNames.remove(0);
		while (!qualifiedNames.isEmpty()) {
			String qname = qualifiedNames.remove(0);
			BaseContainer child = getChildPackage(nextPackage, qname);
			if (child == null) {
				capsuleOrClass = getChildEntity(nextPackage, qname);
				break;
			}
			nextPackage = child;
		}

		EObject result = null;

		if (capsuleOrClass == null) {
			CodeGenPlugin.error("Element not found : " + label.getQualifiedName());
		} else {
			if (label.getType().equals(UMLPackage.Literals.OPERATION.getName().toLowerCase())) {
				RTPassiveClass clazz = ((RTPassiveClass) capsuleOrClass);
				org.eclipse.papyrusrt.xtumlrt.common.Operation operation = null;
				for (org.eclipse.papyrusrt.xtumlrt.common.Operation op : clazz.getOperations()) {
					if (label.getDetails().equals(op.getName())) {
						operation = op;
						break;
					}
				}
				result = operation.getBody();
			} else {
				// This is element under statemachine.
				EObject smElement = getSMElement((Capsule) capsuleOrClass, qualifiedNames);
				result = getUserCodeElement(smElement, label.getType(), label.getDetails());

			}
		}

		return result;
	}

	/**
	 * Return child entity with given name.
	 * 
	 * @param container
	 *            Parent container
	 * @param name
	 *            child entity name
	 * @return child entity or null if not found
	 */
	private Entity getChildEntity(BaseContainer container, String name) {
		Entity result = null;
		for (Entity e : container.getEntities()) {
			if (name.equals(e.getName())) {
				result = e;
				break;
			}
		}
		return result;
	}

	/**
	 * Return child package with given name.
	 * 
	 * @param container
	 *            Parent container
	 * @param name
	 *            child package name
	 * @return child package or null if not found
	 */
	private org.eclipse.papyrusrt.xtumlrt.common.Package getChildPackage(BaseContainer container, String name) {
		org.eclipse.papyrusrt.xtumlrt.common.Package result = null;
		for (org.eclipse.papyrusrt.xtumlrt.common.Package e : container.getPackages()) {
			if (name.equals(e.getName())) {
				result = e;
				break;
			}
		}
		return result;
	}

	/**
	 * Queries the element that holds the user code.
	 * 
	 * @param container
	 *            cotainer
	 * @param type
	 *            element type
	 * @param details
	 *            element details
	 * @return element containing user code
	 */
	private EObject getUserCodeElement(EObject container, String type, String details) {
		EObject result = null;
		if (type.equals(UMLPackage.Literals.TRANSITION.getName().toLowerCase())) {
			Transition t = getTransition(container, details);
			if (t != null && t.getActionChain() != null) {
				if (t.getActionChain().getActions().isEmpty()) {
					CodeGenPlugin.warning("No transition action found for " + getSMQualifiedName(t));
				} else {
					result = t.getActionChain().getActions().get(0);
				}
			}
		} else if (type.equals(UMLPackage.Literals.STATE__ENTRY.getName())) {
			if (container instanceof State) {
				State s = (State) container;
				result = s.getEntryAction();
			}
		} else if (type.equals(UMLPackage.Literals.STATE__EXIT.getName())) {
			if (container instanceof State) {
				State s = (State) container;
				result = s.getExitAction();
			}
		} else if (type.equals(UMLPackage.Literals.TRANSITION__GUARD.getName())) {
			Transition t = getTransition(container, details);
			if (t != null) {
				result = t.getGuard().getBody();
			}
		} else if (type.equals(UMLPackage.Literals.TRANSITION__TRIGGER.getName())) {
			// trigger guard
			String[] tokens = details.split(">>");
			Transition transition = getTransition(container, tokens[0]);
			TriggerDetail tdetail = new TriggerDetail(tokens[1]);
			for (Trigger t : transition.getTriggers()) {
				RTTrigger trigger = (RTTrigger) t;
				if (trigger.getSignal().getName().equals(tdetail.getSignal()) && comparetriggerPorts(trigger.getPorts(), tdetail.getPorts())) {
					result = trigger;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Find element under State machine.
	 * 
	 * @param capsule
	 *            Capsule or class containing state machine
	 * @param qnameRelativeToSM
	 *            qualified name
	 * @return EObject
	 */
	private EObject getSMElement(Capsule capsule, List<String> qnameRelativeToSM) {
		StateMachine sm = (StateMachine) capsule.getBehaviour();
		CompositeState nextCompositeState = sm.getTop();
		EObject result = sm;

		while (!qnameRelativeToSM.isEmpty()) {
			State subState = null;
			String stateName = qnameRelativeToSM.remove(0);
			for (State s : nextCompositeState.getSubstates()) {
				if (s.getName().equals(stateName)) {
					subState = s;
					break;
				}
			}
			if (subState == null) {
				break;
			} else if (subState instanceof CompositeState) {
				nextCompositeState = (CompositeState) subState;
			}
			result = subState;
		}

		if (!qnameRelativeToSM.isEmpty()) {
			result = null;
		}

		if (result == null) {
			CodeGenPlugin.error("No matching state machine element found.");
		}

		return result;
	}

	/**
	 * Retrieve Transition from Capsule or Class.
	 * 
	 * @param container
	 *            State machine or State that contains this transition
	 * @param transitionDetails
	 *            Transition details
	 * @return Transition
	 */
	private Transition getTransition(EObject container, String transitionDetails) {
		Transition result = null;
		CompositeState region = null;
		if (container instanceof StateMachine) {
			region = ((StateMachine) container).getTop();
		} else {
			region = (CompositeState) container;
		}

		if (region != null) {
			UserEditableRegion.TransitionDetails details = new UserEditableRegion.TransitionDetails(transitionDetails);

			for (Transition transition : region.getTransitions()) {
				if (getSMQualifiedName(transition.getSourceVertex()).equals(details.getSourceQname()) && getSMQualifiedName(transition.getTargetVertex()).equals(details.getTargetQname())) {
					if (details.getTriggerDetails().isEmpty()) {
						result = transition;
						break;
					}
					boolean found = false;
					for (TriggerDetail detail : details.getTriggerDetails()) {
						for (Trigger t : transition.getTriggers()) {
							RTTrigger trigger = (RTTrigger) t;
							if (trigger.getSignal().getName().equals(detail.getSignal()) && comparetriggerPorts(trigger.getPorts(), detail.getPorts())) {
								found = true;
								break;
							}
							if (!found) {
								break;
							}
						}
						if (found) {
							result = transition;
							break;
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * Compare actual trigger ports with expected port names.
	 * 
	 * @param ports
	 *            actual ports
	 * @param expectedPorts
	 *            expected port names
	 * @return boolean
	 */
	private boolean comparetriggerPorts(List<RTPort> ports, List<String> expectedPorts) {

		List<String> actualPorts = new ArrayList<>();
		for (Port p : ports) {
			actualPorts.add(p.getName());
		}
		actualPorts.retainAll(expectedPorts);
		return actualPorts.size() == expectedPorts.size();

	}

	/**
	 * Calculate qualified name relative to State Machine.
	 * 
	 * @param eObject
	 *            EObject
	 * @return QualifiedName
	 */
	public static String getSMQualifiedName(EObject eObject) {
		String result = UML2Util.EMPTY_STRING;
		EObject container = eObject;
		while (container != null && !(container.eContainer() instanceof StateMachine)) {
			if (container instanceof Vertex) {
				if (result.length() != 0) {
					result = UserEditableRegion.SEPARATOR + result;
				}
				result = ((Vertex) container).getName() + result;
			}
			container = container.eContainer();
		}
		return result;
	}

	/**
	 * @see org.eclipse.papyrusrt.codegen.IEObjectLocator#saveSource(org.eclipse.papyrusrt.codegen.UserEditableRegion.Label, java.lang.String)
	 *
	 * @param label
	 * @param source
	 * @return
	 */
	@Override
	public CommitResult saveSource(Label label, String source) {
		EObject eo = getEObject(label);
		if (eo instanceof ActionCode) {
			ActionCode action = (ActionCode) eo;
			action.setSource(source);
		}
		CommitResult result = new CommitResult(eo, null, true);
		return result;
	}

	/**
	 * @see org.eclipse.papyrusrt.codegen.IEObjectLocator#initialize()
	 *
	 */
	@Override
	public void initialize() {
		rset = new ResourceSetImpl();
	}

	/**
	 * @see org.eclipse.papyrusrt.codegen.IEObjectLocator#cleanUp()
	 *
	 */
	@Override
	public void cleanUp() {
		if (rset != null) {
			for (Resource r : rset.getResources()) {
				r.unload();
			}
		}
		rset = null;
	}
}
