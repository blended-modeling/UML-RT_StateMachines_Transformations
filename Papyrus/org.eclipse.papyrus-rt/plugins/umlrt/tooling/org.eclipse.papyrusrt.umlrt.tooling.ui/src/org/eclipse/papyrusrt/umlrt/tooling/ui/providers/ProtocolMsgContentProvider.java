/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.providers;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.AnyReceiveEvent;
import org.eclipse.uml2.uml.Event;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Trigger;

/**
 * A provider for protocol messages that can be selected depending on ports specified in a trigger
 * (to be precise, the provider returns the events corresponding to protocol messages)
 */
public class ProtocolMsgContentProvider implements IStaticContentProvider, ITreeContentProvider {

	public ProtocolMsgContentProvider(Trigger trigger) {
		this.trigger = trigger;
		errorShown = false;
		updateProtocols();
	}

	Trigger trigger;

	// retrieved from port selection in trigger
	EList<UMLRTProtocol> protocols;

	boolean errorShown;

	@Override
	public Object[] getElements(Object inputElement) {
		return getElements();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		updateProtocols();
		if (viewer instanceof TreeViewer) {
			final TreeViewer treeViewer = (TreeViewer) viewer;
			Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {
					treeViewer.expandAll();
				}
			});
		}
	}

	/**
	 * Update the information about the used protocols, split into conjugated and "normal" protocols
	 */
	protected void updateProtocols() {
		protocols = new UniqueEList<>();
		if (trigger != null) {
			for (Port port : trigger.getPorts()) {
				UMLRTPort facade = UMLRTPort.getInstance(port);
				if (facade != null) {
					UMLRTProtocol protocol = facade.getType();
					if (protocol != null) {
						protocols.add(protocol);
					}
				}
			}
		}
	}

	/**
	 * Top-level element list
	 * 
	 * @return
	 */
	@Override
	public Object[] getElements() {
		ArrayList<Event> events = new ArrayList<>();
		// take "any" event from first protocol, if there is one (arbitrary choice)
		if (protocols.size() > 0) {
			events.add(protocols.get(0).getAnyReceiveEvent());
		}
		return events.toArray();
	}

	/**
	 * Get the specific protocol messages (grouped below the singleAnyReceiveElement as parent)
	 */
	@Override
	public Object[] getChildren(Object parent) {
		// get specific messages of protocols
		ArrayList<UMLRTProtocolMessage> protocolMsgs = new ArrayList<>();
		EList<UMLRTProtocol> allProtocols = new UniqueEList<>();
		for (UMLRTProtocol protocol : protocols) {
			allProtocols.addAll(ProtocolUtils.getAllProtocols(protocol));
		}
		for (UMLRTProtocol protocol : allProtocols) {
			// only add call events of protocols that are common among the protocols
			// referenced by a port
			if (ProtocolUtils.isCommonProtocol(protocols, protocol)) {
				addProtocolMsgs(protocolMsgs, protocol);
			}
		}
		return protocolMsgs.stream()
				.sorted(UMLRTInheritanceKind.facadeComparator())
				.map(UMLRTProtocolMessage::toReceiveEvent)
				.toArray();
	}

	@Override
	public Object getParent(Object parent) {
		return null;
	}

	@Override
	public boolean hasChildren(Object event) {
		if (event instanceof AnyReceiveEvent) {
			return true;
		}
		return false;
	}


	/**
	 * Add the messages of a protocol to the passed list of events. Conjugation
	 * is taking into account by the UMLRTProtocol facade.
	 * 
	 * @param events
	 *            The passed event list
	 * @param protocol
	 *            an UML/RT protocol (from facade)
	 */
	protected void addProtocolMsgs(ArrayList<UMLRTProtocolMessage> events, UMLRTProtocol protocol) {
		for (UMLRTProtocolMessage message : protocol.getMessages()) {
			RTMessageKind kind = message.getKind();
			if (kind == RTMessageKind.IN_OUT || kind == RTMessageKind.IN) {
				if (message.toReceiveEvent() != null) {
					events.add(message);
				}
			}
		}
	}
}
