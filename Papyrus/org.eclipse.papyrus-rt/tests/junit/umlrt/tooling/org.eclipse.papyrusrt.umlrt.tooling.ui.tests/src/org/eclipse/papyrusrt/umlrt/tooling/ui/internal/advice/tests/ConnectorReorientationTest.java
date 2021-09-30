/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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
package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.advice.tests;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.eclipse.papyrusrt.junit.matchers.StatusMatchers.hasSeverity;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.function.Consumer;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test class for connector port compatibility advice.
 */
@PluginResource("/resource/advice/ReorientConnectorTest.di")
@ActiveDiagram("Capsule1")
public class ConnectorReorientationTest {

	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	@Rule
	public final PapyrusRTEditorFixture editor = new PapyrusRTEditorFixture();

	/**
	 * Verifies that the connector compatibility advice checks also attempts to
	 * re-orient an existing connector as it does creation of new connectors.
	 * 
	 * @see <a href="http://eclip.se/511465">bug 511465</a>
	 */
	@Test
	public void reorientConnectorTargetBlockedByCompatibility() {
		reorientConnectorTestTemplate(true, false, null);
	}

	void reorientConnectorTestTemplate(boolean targetEnd, boolean expectReconnected, Consumer<? super UMLRTPort> newPortAction) {
		UMLRTConnector connector = getConnector();

		UMLRTPort oldPort = targetEnd
				? getCapsule("Capsule3").getPort("protocol1")
				: getCapsule("Capsule2").getPort("protocol1");
		UMLRTPort newPort = getCapsule("Capsule4").getPort("protocol1");

		if (newPortAction != null) {
			editor.execute(() -> newPortAction.accept(newPort));
		}

		ReorientRelationshipRequest request = new ReorientRelationshipRequest(connector.toUML(),
				newPort.toUML(), oldPort.toUML(),
				targetEnd ? ReorientRequest.REORIENT_TARGET : ReorientRequest.REORIENT_SOURCE);
		request.setParameter(RequestParameterConstants.GRAPHICAL_RECONNECTED_EDGE, getEdge(connector.toUML()));
		request.setParameter(RequestParameterConstants.EDGE_REORIENT_REQUEST_END_VIEW, getView(newPort.toUML()));
		request.setParameter(RequestParameterConstants.USE_GUI, false); // Don't prompt

		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(connector.toUML());
		ICommand command = edit.getEditCommand(request);
		// The edit command is executable in any case; the verification happens in execution
		assertThat("Command not executable", command, isExecutable());

		IStatus status = editor.execute(command);

		if (expectReconnected) {
			assertThat("Command was cancelled", status, not(hasSeverity(IStatus.CANCEL)));
			assertThat("Connector was not re-oriented",
					targetEnd ? connector.getTarget() : connector.getSource(),
					is(newPort));
		} else {
			assertThat("Command was not cancelled", status, hasSeverity(IStatus.CANCEL));
			assertThat("Connector was re-oriented",
					targetEnd ? connector.getTarget() : connector.getSource(),
					is(oldPort));
		}
	}

	/**
	 * Verifies that the connector compatibility advice allows attempts to
	 * re-orient an existing connector to a new compatible target.
	 * 
	 * @see <a href="http://eclip.se/511465">bug 511465</a>
	 */
	@Test
	public void reorientConnectorTargetAllowedByCompatibility() {
		// Make the target port unconjugated so that it will be compatible
		reorientConnectorTestTemplate(true, true, port -> port.setConjugated(false));
	}

	/**
	 * Verifies that the connector compatibility advice checks also attempts to
	 * re-orient an existing connector as it does creation of new connectors.
	 * 
	 * @see <a href="http://eclip.se/511465">bug 511465</a>
	 */
	@Test
	public void reorientConnectorSourceBlockedByCompatibility() {
		// The conjugated port is a compatible source as the current source is
		reorientConnectorTestTemplate(false, true, null);
	}

	/**
	 * Verifies that the connector compatibility advice allows attempts to
	 * re-orient an existing connector to a new compatible target.
	 * 
	 * @see <a href="http://eclip.se/511465">bug 511465</a>
	 */
	@Test
	public void reorientConnectorSourceAllowedByCompatibility() {
		// Make the source port unconjugated so that it will not be compatible
		reorientConnectorTestTemplate(false, false, port -> port.setConjugated(false));
	}

	//
	// Test framework
	//

	UMLRTCapsule getCapsule() {
		IGraphicalEditPart context = editor.getActiveDiagram();
		return UMLRTCapsule.getInstance((org.eclipse.uml2.uml.Class) context.resolveSemanticElement());
	}

	UMLRTCapsule getCapsule(String name) {
		return UMLRTPackage.getInstance(editor.getModel()).getCapsule(name);
	}

	UMLRTConnector getConnector() {
		return getCapsule().getConnectors().get(0);
	}

	Edge getEdge(EObject element) {
		return (Edge) editor.findEditPart(element).getModel();
	}

	View getView(EObject element) {
		return (View) editor.findEditPart(element).getModel();
	}
}
