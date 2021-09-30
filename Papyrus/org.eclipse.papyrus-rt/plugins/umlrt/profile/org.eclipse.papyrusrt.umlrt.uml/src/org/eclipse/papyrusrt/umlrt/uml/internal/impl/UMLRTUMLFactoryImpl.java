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

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import java.lang.reflect.Proxy;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.FinalState;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;

/**
 * @author damus
 *
 */
public class UMLRTUMLFactoryImpl extends UMLFactoryImpl {

	public static final UMLFactory eINSTANCE = new UMLRTUMLFactoryImpl();

	private final UMLPackage umlPackage;

	/**
	 * Initializes me.
	 */
	public UMLRTUMLFactoryImpl() {
		super();

		// A shim for the UMLPackage that provides me as its factory
		this.umlPackage = (UMLPackage) Proxy.newProxyInstance(getClass().getClassLoader(),
				new java.lang.Class[] {
						UMLPackage.class,
						BasicExtendedMetaData.EPackageExtendedMetaData.Holder.class,
				},
				(proxy, method, args) -> {
					switch (method.getName()) {
					case "getEFactoryInstance": //$NON-NLS-1$
					case "getUMLFactory": //$NON-NLS-1$
						return UMLRTUMLFactoryImpl.eINSTANCE;
					default:
						return method.invoke(UMLPackage.eINSTANCE, args);
					}
				});
	}

	@Override
	public EPackage getEPackage() {
		return umlPackage;
	}

	@Override
	public Class createClass() {
		return new ClassRTImpl();
	}

	@Override
	public Collaboration createCollaboration() {
		return new CollaborationRTImpl();
	}

	@Override
	public Connector createConnector() {
		return new ConnectorRTImpl();
	}

	@Override
	public ConnectorEnd createConnectorEnd() {
		return new ConnectorEndRTImpl();
	}

	@Override
	public Constraint createConstraint() {
		return new ConstraintRTImpl();
	}

	@Override
	public FinalState createFinalState() {
		return new FinalStateRTImpl();
	}

	@Override
	public Interface createInterface() {
		return new InterfaceRTImpl();
	}

	@Override
	public LiteralInteger createLiteralInteger() {
		return new LiteralIntegerRTImpl();
	}

	@Override
	public LiteralUnlimitedNatural createLiteralUnlimitedNatural() {
		return new LiteralUnlimitedNaturalRTImpl();
	}

	@Override
	public LiteralString createLiteralString() {
		return new LiteralStringRTImpl();
	}

	@Override
	public Model createModel() {
		return new ModelRTImpl();
	}

	@Override
	public OpaqueExpression createOpaqueExpression() {
		return new OpaqueExpressionRTImpl();
	}

	@Override
	public OpaqueBehavior createOpaqueBehavior() {
		return new OpaqueBehaviorRTImpl();
	}

	@Override
	public Operation createOperation() {
		return new OperationRTImpl();
	}

	@Override
	public Package createPackage() {
		return new PackageRTImpl();
	}

	@Override
	public Parameter createParameter() {
		return new ParameterRTImpl();
	}

	@Override
	public Port createPort() {
		return new PortRTImpl();
	}

	@Override
	public Property createProperty() {
		return new PropertyRTImpl();
	}

	@Override
	public Pseudostate createPseudostate() {
		return new PseudostateRTImpl();
	}

	@Override
	public Region createRegion() {
		return new RegionRTImpl();
	}

	@Override
	public State createState() {
		return new StateRTImpl();
	}

	@Override
	public StateMachine createStateMachine() {
		return new StateMachineRTImpl();
	}

	@Override
	public Transition createTransition() {
		return new TransitionRTImpl();
	}

	@Override
	public Trigger createTrigger() {
		return new TriggerRTImpl();
	}
}
