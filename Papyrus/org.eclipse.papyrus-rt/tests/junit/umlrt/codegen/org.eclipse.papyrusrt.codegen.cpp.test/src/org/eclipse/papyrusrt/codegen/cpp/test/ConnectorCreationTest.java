/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrusrt.codegen.cpp.ConnectorReporter;
import org.eclipse.papyrusrt.codegen.instance.model.CapsuleInstance;
import org.eclipse.papyrusrt.codegen.instance.model.ICapsuleInstance;
import org.eclipse.papyrusrt.codegen.instance.model.IPortInstance;
import org.eclipse.papyrusrt.codegen.instance.model.PortInstance.FarEnd;
import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart;
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory;
import org.eclipse.papyrusrt.xtumlrt.common.Connector;
import org.eclipse.papyrusrt.xtumlrt.common.ConnectorEnd;
import org.eclipse.papyrusrt.xtumlrt.common.LiteralInteger;
import org.eclipse.papyrusrt.xtumlrt.common.Port;
import org.eclipse.papyrusrt.xtumlrt.common.Protocol;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConnectorCreationTest {
	/**
	 * A utility for holding a model that is defined in the pattern of the connector
	 * requirements. This is a Top level with two parts, each with a single port.
	 * The two ports are connection. The variation points are the capsule and port
	 * replication factors.
	 */
	private static class Model {
		public final String label;
		public Capsule top;
		public Capsule capsule1;
		public Capsule capsule2;
		public Port port1;
		public Port port2;
		public CapsulePart part1;
		public CapsulePart part2;

		private Model(String label) {
			this.label = label;
		}

		private static LiteralInteger makeLiteralInt(int val) {
			LiteralInteger result = CommonFactory.eINSTANCE.createLiteralInteger();
			result.setValue(val);
			return result;
		}

		public static Model create(String label, int part1Upper, int port1Upper, int part2Upper, int port2Upper) {
			Model model = new Model(label);

			Protocol protocol = CommonFactory.eINSTANCE.createProtocol();
			protocol.setName("Protocol");

			model.port1 = CommonFactory.eINSTANCE.createPort();
			model.port1.setName("p1");
			model.port1.setType(protocol);
			model.port1.setUpperBound(makeLiteralInt(port1Upper));
			model.port2 = CommonFactory.eINSTANCE.createPort();
			model.port2.setName("p2");
			model.port2.setType(protocol);
			model.port2.setUpperBound(makeLiteralInt(port2Upper));

			model.top = CommonFactory.eINSTANCE.createCapsule();
			model.capsule1 = CommonFactory.eINSTANCE.createCapsule();
			model.capsule2 = CommonFactory.eINSTANCE.createCapsule();
			model.top.setName("Top");
			model.capsule1.setName("Capsule1");
			model.capsule2.setName("Capsule2");
			model.capsule1.getPorts().add(model.port1);
			model.capsule2.getPorts().add(model.port2);

			model.part1 = CommonFactory.eINSTANCE.createCapsulePart();
			model.part2 = CommonFactory.eINSTANCE.createCapsulePart();
			model.part1.setName("part1");
			model.part1.setType(model.capsule1);
			model.part1.setLowerBound(makeLiteralInt(1));
			model.part1.setUpperBound(makeLiteralInt(part1Upper));
			model.part2.setName("part2");
			model.part2.setType(model.capsule2);
			model.part2.setLowerBound(makeLiteralInt(1));
			model.part2.setUpperBound(makeLiteralInt(part2Upper));
			model.top.getParts().add(model.part1);
			model.top.getParts().add(model.part2);

			ConnectorEnd end1 = CommonFactory.eINSTANCE.createConnectorEnd();
			ConnectorEnd end2 = CommonFactory.eINSTANCE.createConnectorEnd();
			end1.setPartWithPort(model.part1);
			end1.setRole(model.port1);
			end2.setPartWithPort(model.part2);
			end2.setRole(model.port2);

			Connector conn = CommonFactory.eINSTANCE.createConnector();
			conn.setName(label);
			conn.getEnds().add(end1);
			conn.getEnds().add(end2);
			model.top.getConnectors().add(conn);

			return model;
		}

		public CapsuleInstance createConnectedCapsule() {
			CapsuleInstance topInstance = new CapsuleInstance(top);
			ConnectorReporter connReporter = new ConnectorReporter(topInstance);
			topInstance.connect(connReporter, false);
			connReporter.log(System.out);
			return topInstance;
		}
	}

	@SuppressWarnings("unchecked")
	private void assertBinding(Model model, CapsuleInstance top, int cap0, int port0, int cap1, int port1) {
		List<? extends ICapsuleInstance> part1Instances = top.getContained(model.part1);
		List<? extends ICapsuleInstance> part2Instances = top.getContained(model.part2);
		assertNotNull(part1Instances);
		assertNotNull(part2Instances);

		ICapsuleInstance c0 = part1Instances.get(cap0);
		ICapsuleInstance c1 = part2Instances.get(cap1);
		assertNotNull(c0);
		assertNotNull(c1);

		IPortInstance p0 = c0.getPort(model.port1);
		IPortInstance p1 = c1.getPort(model.port2);
		assertNotNull(p0);
		assertNotNull(p1);

		ArrayList<FarEnd> farEnds0 = (ArrayList<FarEnd>) p0.getFarEnds();
		ArrayList<FarEnd> farEnds1 = (ArrayList<FarEnd>) p1.getFarEnds();
		assertNotNull(farEnds0);
		assertNotNull(farEnds1);

		FarEnd farEnd0 = farEnds0.get(port0);
		FarEnd farEnd1 = farEnds1.get(port1);
		assertNotNull(farEnd0);
		assertNotNull(farEnd1);

		assertEquals(farEnd0.getContainer(), c1);
		assertEquals(farEnd0.getOwner(), p1);
		assertEquals(farEnd1.getContainer(), c0);
		assertEquals(farEnd1.getOwner(), p0);
	}

	@Test
	public void caseA() {
		Model model = Model.create("Case_A", 3, 3, 2, 1);
		assertNotNull(model);

		CapsuleInstance topInstance = model.createConnectedCapsule();
		assertNotNull(topInstance);
	}

	@Test
	public void caseB() {
		Model model = Model.create("Case_B", 3, 3, 2, 2);
		assertNotNull(model);

		CapsuleInstance topInstance = model.createConnectedCapsule();
		assertNotNull(topInstance);

		assertBinding(model, topInstance, 0, 0, 0, 0);
		assertBinding(model, topInstance, 1, 0, 0, 1);
		assertBinding(model, topInstance, 2, 0, 1, 0);
	}

	@Test
	public void caseC() {
		Model model = Model.create("Case_C", 3, 3, 2, 3);
		assertNotNull(model);

		CapsuleInstance topInstance = model.createConnectedCapsule();
		assertNotNull(topInstance);

		assertBinding(model, topInstance, 0, 0, 0, 0);
		assertBinding(model, topInstance, 0, 1, 0, 1);
		assertBinding(model, topInstance, 1, 0, 0, 2);
		assertBinding(model, topInstance, 1, 1, 1, 0);
		assertBinding(model, topInstance, 2, 0, 1, 1);
		assertBinding(model, topInstance, 2, 1, 1, 2);
	}

	@Test
	public void caseD() {
		Model model = Model.create("Case_D", 3, 3, 2, 4);
		assertNotNull(model);

		CapsuleInstance topInstance = model.createConnectedCapsule();
		assertNotNull(topInstance);

		assertBinding(model, topInstance, 0, 0, 0, 0);
		assertBinding(model, topInstance, 0, 1, 0, 1);
		assertBinding(model, topInstance, 1, 0, 0, 2);
		assertBinding(model, topInstance, 1, 1, 0, 3);
		assertBinding(model, topInstance, 2, 0, 1, 0);
		assertBinding(model, topInstance, 2, 1, 1, 1);
	}

	@Test
	public void caseE() {
		Model model = Model.create("Case_E", 3, 3, 2, 5);
		assertNotNull(model);

		CapsuleInstance topInstance = model.createConnectedCapsule();
		assertNotNull(topInstance);

		assertBinding(model, topInstance, 0, 0, 0, 0);
		assertBinding(model, topInstance, 0, 1, 0, 1);
		assertBinding(model, topInstance, 0, 2, 0, 2);
		assertBinding(model, topInstance, 1, 0, 0, 3);
		assertBinding(model, topInstance, 1, 1, 1, 0);
		assertBinding(model, topInstance, 1, 2, 1, 1);
		assertBinding(model, topInstance, 2, 0, 1, 2);
		assertBinding(model, topInstance, 2, 1, 1, 3);
	}

	@Test
	public void caseF() {
		Model model = Model.create("Case_F", 3, 3, 4, 1);
		assertNotNull(model);

		CapsuleInstance topInstance = model.createConnectedCapsule();
		assertNotNull(topInstance);

		assertBinding(model, topInstance, 0, 0, 0, 0);
		assertBinding(model, topInstance, 1, 0, 1, 0);
		assertBinding(model, topInstance, 2, 0, 2, 0);
	}

	@Test
	public void caseG() {
		Model model = Model.create("Case_G", 3, 3, 4, 2);
		assertNotNull(model);

		CapsuleInstance topInstance = model.createConnectedCapsule();
		assertNotNull(topInstance);

		assertBinding(model, topInstance, 0, 0, 0, 0);
		assertBinding(model, topInstance, 0, 1, 0, 1);
		assertBinding(model, topInstance, 1, 0, 1, 0);
		assertBinding(model, topInstance, 1, 1, 1, 1);
		assertBinding(model, topInstance, 2, 0, 2, 0);
		assertBinding(model, topInstance, 2, 1, 2, 1);
	}

	@Test
	public void caseH() {
		Model model = Model.create("Case_H", 3, 3, 4, 3);
		assertNotNull(model);

		CapsuleInstance topInstance = model.createConnectedCapsule();
		assertNotNull(topInstance);

		assertBinding(model, topInstance, 0, 0, 0, 0);
		assertBinding(model, topInstance, 0, 1, 0, 1);
		assertBinding(model, topInstance, 0, 2, 1, 0);
		assertBinding(model, topInstance, 1, 0, 1, 1);
		assertBinding(model, topInstance, 1, 1, 2, 0);
		assertBinding(model, topInstance, 1, 2, 2, 1);
		assertBinding(model, topInstance, 2, 0, 3, 0);
		assertBinding(model, topInstance, 2, 1, 3, 1);
	}
}
